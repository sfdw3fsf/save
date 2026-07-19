package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse

@RestController
@RequestMapping("ccbs")
class CCBSService {

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    HttpManaged httpManaged

    @Autowired
    NativeWebRequest request

    final String url = "https://api-dev-ccbs.vnpt.vn/ccbs-quantri"

    @GetMapping("/createOTP")
    CommonResponse createOTP() {
        try {
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")
            headers.put("Authorization", request.getHeader("Authorization"))

            String urlR = url + "/user/create_otp"
            String resp = httpManaged.post(["url": urlR], headers, "")
            def res = new JsonSlurper().parseText(resp)
            if (res["error_code"].toString() == "BSS-00000000") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res["data"])
            } else {
                return new CommonResponse(errorCode: 1, faultCode: res["error_code"], faultString: res["message"], data: res)
            }
        } catch(Exception e) {
            e.printStackTrace()
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/getToken")
    def getToken(@RequestParam String otp) {
        try {
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")

            def body = [:]
            body["grant_type"] = "password"
            body["client_id"] = "clientapp"
            body["client_secret"] = "password"
            body["otp"] = otp

            String urlR = url + "/oauth/token"
            String resp = httpManaged.post(["url": urlR], headers, new Gson().toJson(body))
            def res = new JsonSlurper().parseText(resp)
            return res
        } catch(Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


}
