package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import groovy.json.JsonSlurper
import groovy.sql.Sql
import io.swagger.model.BaseResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine

import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.sql.Timestamp

@RestController
@RequestMapping("onebss-csht")
class OneBssCshtService {

    private static final Logger log = LoggerFactory.getLogger(OneBssCshtService)

    private static final int PAGE_SIZE = 5000
    private static final int BATCH_SIZE = 5000
    private static final String TABLE_NAME = "qlsc.user_huy_test" // TODO revert qlsc.user_qlcsht trước commit

    @Autowired
    HttpManaged httpManaged

    @Autowired
    OracleManaged oracleManaged

    @Value('${spring.httpservices.rims.onebss_csht_url}')
    String onebssCshtUrl

    @Value('${spring.httpservices.rims.onebss_csht_token}')
    String onebssCshtToken

    @PostMapping("/sync")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> sync(@RequestBody Map body) {
        String maTinhTp = body?.ma_tinh_tp?.toString()?.trim()
        String systemCode = body?.system_code?.toString() ?: ""
        if (!maTinhTp) {
            return ResponseEntity.ok().body(
                    BaseResponseBuilder.of(null, null, "ma_tinh_tp bắt buộc", ErrorCodeDefine.INVALID_PARAM))
        }

        Timestamp syncStartedAt = new Timestamp(System.currentTimeMillis())
        int totalRecord = 0
        int totalInserted = 0
        try {
            Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
                int pageIndex = 1
                int totalPage = 1
                while (pageIndex <= totalPage) {
                    long tFetchStart = System.currentTimeMillis()
                    def parsed = callRims(maTinhTp, systemCode, pageIndex, PAGE_SIZE)
                    long tFetchEnd = System.currentTimeMillis()
                    if (parsed?.success != true) {
                        throw new RuntimeException("RIMS lỗi: " + (parsed?.errorMsg ?: "unknown"))
                    }
                    if (pageIndex == 1) {
                        totalRecord = (parsed.totalRecord ?: 0) as int
                        totalPage = (parsed.totalPage ?: 0) as int
                        log.info("[onebss-csht] {} totalRecord={} totalPage={}", maTinhTp, totalRecord, totalPage)
                    }
                    List rows = (parsed.data ?: []) as List
                    int inserted = 0
                    long tInsertStart = System.currentTimeMillis()
                    if (!rows.isEmpty()) {
                        inserted = insertBatch(sql, rows, syncStartedAt)
                        totalInserted += inserted
                    }
                    long tInsertEnd = System.currentTimeMillis()
                    log.info("[onebss-csht] {} page={}/{} rows={} fetch={}ms insert={}ms",
                            maTinhTp, pageIndex, totalPage, rows.size(),
                            tFetchEnd - tFetchStart, tInsertEnd - tInsertStart)
                    pageIndex++
                }
                // Soft swap: chỉ xoá dữ liệu cũ sau khi insert toàn bộ thành công
                int deleted = sql.executeUpdate(
                        "DELETE FROM ${TABLE_NAME} WHERE ma_tinh_tp = ? AND sync_at < ?".toString(),
                        [maTinhTp, syncStartedAt])
                log.info("[onebss-csht] {} inserted={} deletedOld={}", maTinhTp, totalInserted, deleted)
            }

            Map result = [
                    ma_tinh_tp   : maTinhTp,
                    totalRecord  : totalRecord,
                    totalInserted: totalInserted,
                    sync_at      : syncStartedAt.toString()
            ]
            return ResponseEntity.ok().body(BaseResponseBuilder.of(result))
        } catch (Exception e) {
            log.error("[onebss-csht] sync {} error", maTinhTp, e)
            return ResponseEntity.ok().body(
                    BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    private Object callRims(String maTinhTp, String systemCode, int pageIndex, int pageSize) {
        Map<String, String> headers = [
                "Authorization": "Bearer " + onebssCshtToken,
                "Content-Type" : "application/json"
        ]
        Map<String, Object> reqBody = [
                ma_tinh_tp : maTinhTp,
                System_code: systemCode,
                page_index : pageIndex,
                page_size  : pageSize
        ]
        String resp = httpManaged.post([url: onebssCshtUrl], headers, new Gson().toJson(reqBody))
        return new JsonSlurper().parseText(resp)
    }

    private int insertBatch(Sql sql, List rows, Timestamp syncAt) {
        String insertSql = """INSERT INTO ${TABLE_NAME}(
            ma_tinh_tp, ten_tinh_tp,
            building_code, building_name, address,
            system_code, system_name, usage_purpose, scope_type,
            management_block_code, role_code,
            user_donvi_id, user_donvi_path, unit_name, ten_don_vi,
            phuongxa_id, ten_phuong_xa,
            full_name, email, phone, note_text,
            src_created_at, src_updated_at, src_created_by, src_updated_by,
            sync_at
        ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"""

        int count = 0
        sql.withBatch(BATCH_SIZE, insertSql) { stmt ->
            rows.each { Map r ->
                stmt.addBatch([
                        r.ma_tinh_tp, r.ten_tinh_tp,
                        r.building_code, r.building_name, r.address,
                        r.system_code, r.system_name, r.usage_purpose, r.scope_type,
                        r.management_block_code, r.role_code,
                        r.user_donvi_id, r.user_donvi_path, r.unit_name, r.ten_don_vi,
                        r.phuongxa_id, r.ten_phuong_xa,
                        r.full_name, r.email, r.phone, r.note_text,
                        r.created_at, r.updated_at, r.created_by, r.updated_by,
                        syncAt
                ])
                count++
            }
        }
        return count
    }
}
