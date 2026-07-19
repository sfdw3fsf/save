package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.model.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.convert.ResultSetToObject
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.ObjectStoragesManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.model.input.lay_chitiet_tb_didong_smp_req
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.sql.ResultSet

@RestController
@RequestMapping("quantrithuebao")
class QuanTriThueBaoController {
    private final HttpServletRequest request;
    static OutParameter CURSOR_PARAMETER = { OracleTypes.CURSOR }
    static ThreadLocal<ObjectMapper> objectMappers = ThreadLocal.withInitial { new ObjectMapper() }
    private final ObjectMapper objectMapper;
    @Autowired
    OracleManaged oracleManaged

    @Autowired
    GenericApi genericApi

    @Autowired
    ResultSetToObject resultSetToObject

    @Autowired
    ObjectStoragesManaged objectStoragesManaged

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    DataUtils dataUtils

    @Autowired
    QuanTriThueBaoController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @GetMapping("/lay_ds_pbh_smp")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "Lấy danh sách hợp đồng thuê bao theo hợp đồng khách hàng, UR2.9.002_018", description = "dev: hailm", tags = ["Hợp đồng", "UR2.9.002"])
    ResponseEntity<BaseResponse<List>> lay_ds_pbh_smp() {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            Long phanVungId = Long.parseLong(session.get("id_tinhthanh"))
            Long nguoidung_id = Long.parseLong(session.get("nguoidung_id"))
            List list = []
            Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
                sql.call("{ call CSS.lay_ds_pbh_smp(?,?,?) }", [
                        phanVungId, nguoidung_id , CURSOR_PARAMETER]) { ResultSet rs ->
                    list = resultSetToObject.getDynamicData(rs)
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(list))
        } catch (Exception ex) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, ex.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/lay_ds_nv_pbh_smp")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "Lấy danh sách hợp đồng thuê bao theo hợp đồng khách hàng, UR2.9.002_018", description = "dev: hailm", tags = ["Hợp đồng", "UR2.9.002"])
    ResponseEntity<BaseResponse<List>> lay_ds_nv_pbh_smp(@RequestParam long vdonvi_id) {
        try {
            def session = genericApi.getSessionData(request)
            String phanVungId = session[GenericApi.ID_TINHTHANH]
            Long nhanvien_id = Long.parseLong(session.get("id_nhanvien_dhsx"))
            //Long donvi_id = Long.parseLong(session.get("id_donvi"))
            List list = []
            Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
                sql.call("{ call CSS.lay_ds_nv_pbh_smp(?,?,?,?) }", [
                        phanVungId,
                        vdonvi_id,
                        nhanvien_id,
                        CURSOR_PARAMETER]) { ResultSet rs ->
                    list = resultSetToObject.getDynamicData(rs)
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(list))
        } catch (Exception ex) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, ex.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/lay_soluong_tb_didong_smp")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "Lấy danh sách hợp đồng thuê bao theo hợp đồng khách hàng, UR2.9.002_018", description = "dev: hailm", tags = ["Hợp đồng", "UR2.9.002"])
    ResponseEntity<BaseResponse<List>> lay_soluong_tb_didong_smp(@RequestParam long nhanvien_id, @RequestParam long donvi_id) {
        try {
            def session = genericApi.getSessionData(request)
            String phanVungId = session[GenericApi.ID_TINHTHANH]
            //Long nhanvien_id = Long.parseLong(session.get("id_nhanvien_dhsx"))
            //Long donvi_id = Long.parseLong(session.get("id_donvi"))
            List list = []
            Long nguoidung_id = Long.parseLong(session.get("nguoidung_id"))
            Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
                sql.call("{ call CSS.lay_soluong_TB_DIDONG_SMP(?,?,?,?,?) }", [
                        phanVungId,
                        donvi_id,
                        nhanvien_id,
                        nguoidung_id,
                        CURSOR_PARAMETER]) { ResultSet rs ->
                    list = resultSetToObject.getDynamicData(rs)
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(list))
        } catch (Exception ex) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, ex.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/lay_chitiet_tb_didong_smp")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "Lấy danh sách hợp đồng thuê bao theo hợp đồng khách hàng, UR2.9.002_018", description = "dev: hailm", tags = ["Hợp đồng", "UR2.9.002"])
    ResponseEntity<BaseResponse<List>> lay_chitiet_tb_didong_smp(@RequestBody lay_chitiet_tb_didong_smp_req body) {
        try {
            def session = genericApi.getSessionData(request)
            String phanVungId = session[GenericApi.ID_TINHTHANH]
            Long nhanvien_id = Long.parseLong(session.get("id_nhanvien_dhsx"))
            Long donvi_id = Long.parseLong(session.get("id_donvi"))
            List list = []
            Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
                sql.call("{ call CSS.lay_chitiet_TB_DIDONG_SMP(?,?,?,?) }", [
                        phanVungId,
                        body.vdb_id,
                        body.vkieu,
                        CURSOR_PARAMETER]) { ResultSet rs ->
                    list = resultSetToObject.getDynamicData(rs)
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(list))
        } catch (Exception ex) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, ex.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

}
