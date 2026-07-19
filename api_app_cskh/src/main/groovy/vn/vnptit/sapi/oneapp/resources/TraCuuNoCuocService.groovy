package vn.vnptit.sapi.oneapp.resources

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.model.dto.FileApp
import io.swagger.model.response.BaseResponse
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.convert.ResultSetToObject
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import java.sql.ResultSet

@RestController
@RequestMapping("tracuunocuoc")
class TraCuuNoCuocService {
    static OutParameter CURSOR_PARAMETER = { OracleTypes.CURSOR }
    private final HttpServletRequest request

    @Autowired
    OracleManaged oracleManaged

    @Autowired
    GenericApi genericApi

    @Autowired
    ResultSetToObject resultSetToObject

    @Autowired
    DataUtils dataUtils

    @Value("\${spring.object-storages.minio-storage.bucket}")
    String bucketName

    @Autowired
    TraCuuNoCuocService(HttpServletRequest request) {
        this.request = request
    }

    @PostMapping("/sp_lay_danhmuc_hs_cucvt")
    ResponseEntity<BaseResponse<List>> sp_lay_danhmuc_hs_cucvt(@Valid @RequestBody Map body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String phanvung_id = session[GenericApi.ID_TINHTHANH].toString()
            List datas = []
            Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
                sql.call("{ call ADMIN.SP_LAY_DANHMUC_HS_CUCVT(?,?,?) }", [
                        phanvung_id,
                        body.p_tt_bien,
                        CURSOR_PARAMETER
                ]) { ResultSet rs ->
                    datas = resultSetToObject.getDynamicData(rs)
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(datas))
        } catch (Exception err) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, err.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/fn_ins_update_hs_cucvt")
    ResponseEntity<BaseResponse<List>> fn_ins_update_hs_cucvt(@Valid @RequestBody Map body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String phanvung_id = session[GenericApi.ID_TINHTHANH].toString()
            String nguoiDungId = session[GenericApi.NGUOIDUNG_ID].toString()
            String nhanVienId = session[GenericApi.ID_NHANVIEN_DHSX].toString()
            String maNhanVien = session[GenericApi.MA_NV].toString()

            String datas = ""
            String p_tt_nd = JsonOutput.toJson([IP_CN: 'ONEAPP', MAY_CN: 'ONEAPP', NGUOI_CN: maNhanVien, NHANVIEN_ID: nhanVienId, NGUOIDUNG_ID: nguoiDungId]).toString()

            Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
                sql.call("{ ? = call ADMIN.fn_ins_update_hs_cucvt(?,?,?) }", [
                        Sql.VARCHAR,
                        phanvung_id,
                        body.p_ds_para,
                        p_tt_nd,
                ]) { String rs ->
                    datas = rs
                }
            }
            def result = new JsonSlurper().parseText(datas)
            if (result["ERROR_CODE"] == 1) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(result["MESSAGE"]))
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, result["MESSAGE"].toString(), HttpStatus.INTERNAL_SERVER_ERROR))
        } catch (Exception err) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, err.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/get_keys_v4")
    ResponseEntity<BaseResponse<List>> get_keys_v4(@Valid @RequestBody Map body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String phanvung_id = session[GenericApi.ID_TINHTHANH].toString()
            Integer datas = 0
            Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
                sql.call("{ ? = call ADMIN.get_keys_v4(?,?,?,?) }", [
                        Sql.INTEGER,
                        phanvung_id,
                        body.keyname,
                        1, 10
                ]) { Integer rs ->
                    datas = rs
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(datas))
        } catch (Exception err) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, err.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/sp_lay_ds_hs_cucvt")
    ResponseEntity<BaseResponse<List>> sp_lay_ds_hs_cucvt(@Valid @RequestBody Map body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String phanvung_id = session[GenericApi.ID_TINHTHANH].toString()
            List datas = []
            Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
                sql.call("{ call ADMIN.SP_LAY_DS_HS_CUCVT(?,?,?) }", [
                        phanvung_id,
                        body.p_tt_bien,
                        CURSOR_PARAMETER
                ]) { ResultSet rs ->
                    datas = resultSetToObject.getDynamicData1(rs)
                }
            }
            if (datas.size() > 0) {
                JsonSlurper jsonSlurper = new JsonSlurper()
                for (it in datas) {
                    List urlString = []
                    if (it["url"] != null && it["url"] != "") {
                        def urlList = jsonSlurper.parseText(it["url"])
                        for (url in urlList) {
                            if (url["link"] != null && url["link"] != "") {
                                FileApp file = dataUtils.getFileUrls(url["link"].toString(), bucketName)[0];
                                urlString.add([id: file.id, url: file.url, duong_dan: url["link"]]);
                            }
                        }
                    }
                    it["url"] = urlString;
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(datas))
        } catch (Exception err) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, err.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

}
