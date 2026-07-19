package vn.vnptit.sapi.oneapp.resources
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.green_online.DangKyReq
import vn.vnptit.sapi.oneapp.models.green_online.DangKyV2Req
import vn.vnptit.sapi.oneapp.models.green_online.GetStatusReq
import vn.vnptit.sapi.oneapp.util.GenericApi
import vn.vnptit.sapi.oneapp.util.LoggingInOut
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("green_online")
class GreenOnlineService {

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

    @Value("\${spring.httpservices.green_online.url}")
    String url

    @PostMapping("/GetStatus")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    @FakeSuccessCommonResponse
    CommonResponse GetStatus(@Valid @RequestBody GetStatusReq body) {
        try {
            def res = post('pn_user_status.do', body)
            String faultString = ""
            int errorCode = 0
            if (res.status != 'success') {
                errorCode = 1
                faultString = res.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: errorCode, faultString: faultString, data: res)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/GetStatusFW")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    @FakeSuccessCommonResponse
    CommonResponse GetStatusFW(@Valid @RequestBody GetStatusReq body) {
        try {
            def params = """{
                            "account": "$body.id"
                        }"""
            def resp = HttpManaged.post(["url": "http://10.70.52.120:4740/api/greennet_subcriber_check"], 
                                        ['Content-Type': 'application/json;charset=utf-8'], params)
            def res = (Map<String, Object>) new JsonSlurper().parseText(resp)
            String faultString = ""
            int errorCode = 0
            if (res.Message.status != "success") {
                errorCode = 1
                faultString = res.Message.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: errorCode, faultString: faultString, data: res.Message)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/DangKyV2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    @FakeSuccessCommonResponse
    CommonResponse DangKyV2(@Valid @RequestBody DangKyV2Req body) {
        try {
            def res = post('pn_user_control.do', body)
            String faultString = ""
            int errorCode = 0
            if (res.status != 'success') {
                errorCode = 1
                faultString = res.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: errorCode, faultString: faultString, data: res)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/DangKyV2FW")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    @FakeSuccessCommonResponse
    CommonResponse DangKyV2FW(@Valid @RequestBody DangKyV2Req body) {
        try {
            def resp = HttpManaged.post(["url": "http://10.70.52.120:4740/api/greennet_subcriber_intergration"], 
                                        ['Content-Type': 'application/json;charset=utf-8'], gson.get().toJson(body))
            def res = (Map<String, Object>) new JsonSlurper().parseText(resp)
            String faultString = ""
            int errorCode = 0
            if (res.Message.status != "success") {
                errorCode = 1
                faultString = res.Message.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: errorCode, faultString: faultString, data: res.Message)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/DangKy")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    @FakeSuccessCommonResponse
    CommonResponse DangKy(@Valid @RequestBody DangKyReq body) {
        try {
            def session = genericApi.getSessionData(request)
            String phanvungId = session[GenericApi.ID_TINHTHANH]
            if (phanvungId == "21") {  // HNI
                String url_api = "http://hn.plantynet.vn/pn_user_control.jsp?";
                String paramter = "";
                paramter += "id=" + body.account + "&flag=" + body.flag + "&operator=" + body.user + "&province=" + body.province
                url_api = url_api + paramter;

                Map<String, String> headers = [:]
                String resp = httpManaged.get(url_api, headers)
                if (resp.contains("SUCCESS")) {
                    return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: resp)
                } else {
                    return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: resp)
                }
            } else {
                String hostUrl = "http://go.plantynet.vn/pn_user_control.jsp?";
                String paramter = "";
                paramter += "id=" + body.account + "&flag=" + body.flag + "&operator=" + body.user + "&province=" + body.province + "&acc_id=" + body.acc_id;
                paramter += "&promotioncode=" + body.promotioncode + "&unit=" + body.unit;
                hostUrl = hostUrl + paramter;

                Map<String, String> headers = [:]
                headers.put("Content-Type", "text/xml; encoding='utf-8'")
                String resp = httpManaged.get(hostUrl, headers)

                //Cần chạy thử để lấy myHttpWebResponse trả về
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: resp)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    Map post(String apiName, Object params) {
        def res = HttpManaged.post([url: url + apiName], ['Content-Type': 'application/json;charset=utf-8'], gson.get().toJson(params))
        (Map<String, Object>) new JsonSlurper().parseText(res)
    }
}
