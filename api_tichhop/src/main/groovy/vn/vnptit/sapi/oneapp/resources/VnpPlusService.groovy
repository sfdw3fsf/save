package vn.vnptit.sapi.oneapp.resources
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.json.JsonSlurper
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
//import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.vnpplus.HangHoiVienReq
import vn.vnptit.sapi.oneapp.util.GenericApi
import vn.vnptit.sapi.oneapp.util.LoggingInOut

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("vnp_plus")
class VnpPlusService {

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

    @Value("\${spring.httpservices.vnp_plus.url}")
    String url
    @Value("\${spring.httpservices.vnp_plus.headers.username}")
    String username
    @Value("\${spring.httpservices.vnp_plus.headers.password}")
    String password

    @PostMapping("/HangHoiVien")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse HangHoiVien(@Valid @RequestBody HangHoiVienReq body) {
        try {

            def res = post('APIKiemTraHoiVienVinaPhonePlus', body)

            int errorCode = 0
            if (res.ErrorCode != '200') {
                errorCode = 1

            }
            return new CommonResponse(errorCode: errorCode, faultCode: res.ErrorCode, faultString: res.ErrorDesc, data: res)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    Map post(String apiName, Object params) {
        def res = HttpManaged.post([url: url + apiName],
                ['Content-Type': 'application/json;charset=utf-8',
                 'TenDangNhap' : username,
                 'MatKhau'     : password],
                gson.get().toJson(params))
        return (Map<String, Object>) new JsonSlurper().parseText(res)
    }
}
