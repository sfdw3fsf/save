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
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CREATE_QRCODE_REQ
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.REFUND_TRANS_REQ
import vn.vnptit.sapi.oneapp.util.LoggingInOut

@RestController
@RequestMapping("vnptpay")
class VNPTPayServiceNew {
    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    HttpManaged httpManaged

    @Value("\${spring.httpservices.vnptpay.url_new}")
    String host

    @Value("\${spring.httpservices.vnptpay.key}")
    String key

    @LoggingInOut
    @PostMapping("/create_vietqr")
    CommonResponse create_vietqr(@RequestBody CREATE_QRCODE_REQ input) {
        try {
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ["qrcodeData": "", "qrcodeImage": ""])
            // Map<String, String> headers = new HashMap<>()
            // headers.put("Content-Type", "application/json")
            // headers.put("Authorization", key)

            // String url = host + "vietqr_create"
            // Gson gson = new GsonBuilder().disableHtmlEscaping().create()
            // String resp = httpManaged.post(["url": url], headers, gson.toJson(input))
            // def res = new JsonSlurper().parseText(resp)
            // if (res["responseCode"].toString() == "00") {
            //     return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
            // } else {
            //     return new CommonResponse(errorCode: 1, faultCode: res["responseCode"], faultString: res["description"], data: res)
            // }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/check_vietqr")
    CommonResponse check_vietqr(@RequestBody CREATE_QRCODE_REQ input) {
        try {
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")
            headers.put("Authorization", key)

            String url = host + "vietqr_query_transaction_by_bill_number"
            String resp = httpManaged.post(["url": url], headers, new Gson().toJson(input))
            def res = new JsonSlurper().parseText(resp)
            if (res["responseCode"].toString() == "00") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
            } else {
                return new CommonResponse(errorCode: 1, faultCode: res["responseCode"], faultString: res["description"], data: res)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/refund_trans")
    CommonResponse refund_trans(@RequestBody REFUND_TRANS_REQ input) {
        try {
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")
            headers.put("Authorization", key)

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

}