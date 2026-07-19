package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.json.JsonSlurper
import io.swagger.model.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.khdn.models.ThongTinKhachHang
import vn.vnptit.sapi.oneapp.models.khdn.respones.ThongTinKhRespone
import vn.vnptit.sapi.oneapp.models.qlnt.SEARCH_KHTN_REQ

//import vn.vnptit.sapi.oneapp.managed.OracleManaged

import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.Constant
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.GenericApi

import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("khdn")
class KhdnService {

    static ThreadLocal<Gson> gson = ThreadLocal.withInitial { new GsonBuilder().serializeNulls().create() }

    @Autowired
    LoggingManaged loggingManaged
//    @Autowired
//    OracleManaged oracleManaged
    @Autowired
    HttpManaged httpManaged

    @Autowired
    GenericApi genericApi
    @Autowired
    NativeWebRequest request

//    @Value("\${spring.httpservices.khdn.url}")
    String url = "http://10.156.4.137:5000/mapi/call"

    @GetMapping("/tracuu/thongtin_khachhang")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @Operation(summary = "Tra cứu thông tin khách hàng doanh nghiệp", description = "Tra cứu thông tin khách hàng doanh nghiệp", tags = ["Khách hàng doanh nghiệp"])
    ResponseEntity<BaseResponse<ThongTinKhachHang>> traCuuThongTinKhachHang(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Mã số thuế", required = true, schema = @Schema()) @Valid @RequestParam(value = "mst", required = true) String mst) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            if (mst == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Mã số thuế không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            Map<String, String> param = new HashMap<>([
                    "service" : Constant.Khdn.query_mst,
                    "provider": "default",
                    "type"    : "qry",
                    "mst"     : mst
            ])
            String resp = httpManaged.post(["url": url], ["Content-Type": "application/json"], new Gson().toJson(param))
            ThongTinKhRespone response = new Gson().fromJson(resp, ThongTinKhRespone.class)
            if (response.error_code == "0") {
                if (response.result.size() > 0) {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(response.result.first(), uuidAsString, null, ErrorCodeDefine.SUCCESS))
                } else {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Không tìm thấy thông tin khách hàng", "BSS-00005002"))
                }
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, resp, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[KhdnService] - traCuuThongTinKhachHang: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/search_khtn")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse search_khtn(@Valid @RequestBody SEARCH_KHTN_REQ body) {
        try {
            def row = ["FULL_NAME" : "A", "ADDR" : "B", "DD_NAME" : "C", "PHONE" : "D"]
            def result = [row]
            def data = ["result": result]
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: new ObjectMapper().writeValueAsString(data))
            // def resp = HttpManaged.post(["url": "http://10.70.52.120:4740/api/search_khtn"], 
            //                             ['Content-Type': 'application/json;charset=utf-8'], gson.get().toJson(body))
            // def res = (Map<String, Object>) new JsonSlurper().parseText(resp)
            // if (res["errorCode"].toString() == "0") {
            //     return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res["Message"])
            // } else {
            //     return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Không lấy được thông tin từ hệ thống Khách hàng tiềm năng !", data: null)
            // }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }
}
