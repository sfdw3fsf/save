package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.util.GenericApi

import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("myvnpt")
class MyVNPTService {
    static String attrType = "@xsi:type"
    static String attrItemType = "@soapenc:arrayType"
    static String attrHref = "@href"
    static String attrNull = "@xsi:nil"

    static ThreadLocal<Gson> gson = ThreadLocal.withInitial { new GsonBuilder().serializeNulls().create() }

    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    OracleManaged oracleManaged
    @Autowired
    HttpManaged httpManaged

    @Autowired
    GenericApi genericApi
    @Autowired
    NativeWebRequest request

    @Value("\${spring.httpservices.myvnpt.url}")
    String url

    @Value("\${spring.httpservices.myvnpt.token}")
    String token

    @Value("\${spring.httpservices.myvnpt.username}")
    String username

    @Value("\${spring.httpservices.myvnpt.password}")
    String password

    @GetMapping("/myvnpt_tracuu_thongtin_kh/{sdt}")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse myvnpt_tracuu_thongtin_kh(@PathVariable("sdt") String sdt) {
        try {
            String path = "myvnpt_tracuu_thongtin_kh"
            String requestId = UUID.randomUUID().toString()
            Map<String, Object> session = genericApi.getSessionData(request)
            String body = """
                    {
                        "so_dt": "$sdt"
                    }
                """
            Map<String, String> headers = [:]
            headers.put("Content-Type", "application/json")
            headers.put("erp-token", token)
            headers.put("erp-acc", username)
            headers.put("erp-pwd", password)
            String resp = httpManaged.post(["url": url + path], headers, body)
            def res = new JsonSlurper().parseText(resp)
            if (res.errorCode == 0)
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res.Message)
            return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: res.Message)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/myvnpt_tracuu_thongtin_kh_v2/{sdt}")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse myvnpt_tracuu_thongtin_kh_v2(@PathVariable("sdt") String sdt) {
        try {
            String path = "myvnpt_tracuu_thongtin_kh_v2"
            String requestId = UUID.randomUUID().toString()
            Map<String, Object> session = genericApi.getSessionData(request)
            String body = """
                    {
                        "so_dt": "$sdt"
                    }
                """
            Map<String, String> headers = [:]
            headers.put("Content-Type", "application/json")
            headers.put("erp-token", token)
            headers.put("erp-acc", username)
            headers.put("erp-pwd", password)
            String resp = httpManaged.post(["url": url + path], headers, body)
            def res = new JsonSlurper().parseText(resp)
            if (res.errorCode == 0)
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res.Message)
            return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: res.Message)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/myvnpt_ttc_receive_noti_popup")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse myvnpt_ttc_receive_noti_popup(@RequestBody myvnpt_ttc_receive_noti_popup_req body) {
        try {
            String path = "myvnpt_ttc_receive_noti_popup"
            Map<String, String> headers = [:]
            headers.put("Content-Type", "application/json")
            headers.put("erp-token", token)
            headers.put("erp-acc", username)
            headers.put("erp-pwd", password)
            String resp = httpManaged.post(["url": url + path], headers, new Gson().toJson(body))
            def res = new JsonSlurper().parseText(resp)
            if (res.errorCode == 0)
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res.Message)
            return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: res.Message)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }
}

class myvnpt_ttc_receive_noti_popup_req {
    String tongtien
    List thongbao_data
    String service
    Integer loai
    String id
    String so_dt
}
