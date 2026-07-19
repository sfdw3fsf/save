package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.model.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.convert.ResultSetToObject
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.ObjectStoragesManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.model.request.B2A_TONGHOP_NV
import vn.vnptit.sapi.oneapp.model.request.LAY_B2A_CHITIET_PHANGIAO
import vn.vnptit.sapi.oneapp.resources.DataUtils
import vn.vnptit.sapi.oneapp.resources.GenericApi
import vn.vnptit.sapi.oneapp.service.VisaService
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.sql.ResultSet

@RestController
@RequestMapping("baocao")
class BaoCaoController {
    private final HttpServletRequest request
    static OutParameter CURSOR_PARAMETER = { OracleTypes.CURSOR }
    private final ObjectMapper objectMapper

    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    GenericApi genericApi
    @Autowired
    OracleManaged oracleManaged

    @Autowired
    ObjectStoragesManaged objectStoragesManaged

    @Autowired
    DataUtils dataUtils

    @Autowired
    ResultSetToObject resultSetToObject

    @Autowired
    VisaService visaService

    ObjectMapper json = new ObjectMapper();

    @Autowired
    BaoCaoController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }

    @PostMapping("/b2aTongHopNV")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "CSS.B2A_TONGHOP_NV", tags = ["Báo cáo"])
    ResponseEntity<BaseResponse<?>> b2aTongHopNV(@RequestBody B2A_TONGHOP_NV body) {
        List data
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CSKH2Controller] - [b2aTongHopNV] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String maNhanVien = session_.get(GenericApi.MA_NV)
            String sqlString = "{ call CSS.LAY_B2A_TONGHOP_NV(?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, body.tungay, body.denngay, body.donvi_id, body.ma_tap,
                                     body.tt_tb, body.loaikenh, body.ma_chiendich, maNhanVien, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[CSKH2Controller] - [b2aTongHopNV] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[CSKH2Controller] - [b2aTongHopNV] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/b2aChiTietPhanGiao")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "CSS.LAY_B2A_CHITIET_PHANGIAO", tags = ["Báo cáo"])
    ResponseEntity<BaseResponse<?>> b2aChiTietPhanGiao(@RequestBody LAY_B2A_CHITIET_PHANGIAO body) {
        List data
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CSKH2Controller] - [b2aChiTietPhanGiao] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_B2A_CHITIET_PHANGIAO(?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, body.tungay, body.denngay, body.loaikenh, body.ma_chiendich,
                                     body.tt_tb, body.ma_kh, body.sdt, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[CSKH2Controller] - [b2aChiTietPhanGiao] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[CSKH2Controller] - [b2aChiTietPhanGiao] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

}
