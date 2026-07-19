package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import groovy.json.JsonSlurper
import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse

@RestController
@RequestMapping("cucvt")
class CVTService {
    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    HttpManaged httpManaged

    @Value("\${spring.httpservices.cucvt.url}")
    String host

    @Value("\${spring.httpservices.cucvt.token}")
    String token

    @Value("\${spring.httpservices.cucvt.ftp.ip}")
    String ftpIp

    @Value("\${spring.httpservices.cucvt.ftp.username}")
    String ftpUsername

    @Value("\${spring.httpservices.cucvt.ftp.password}")
    String ftpPassword

    @Value("\${spring.httpservices.cucvt.ftp.folder}")
    String ftpFolder

    // @PostMapping("/importDebitSub")
    // CommonResponse importDebitSub(@RequestBody Map<String, Object> body) {
    //     try {
    //         Map<String, String> headers = new HashMap<>()
    //         headers.put("Content-Type", "application/json")
    //         headers.put("Authorization", "Bearer " + token)

    //         String url = host + "importDebitSub"
    //         String resp = httpManaged.post(["url": url], headers, new Gson().toJson(body))
    //         def res = new JsonSlurper().parseText(resp)
    //         if (res["errorCode"].toString() == "0") {
    //             return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "Thành công")
    //         } else {
    //             return new CommonResponse(errorCode: 1, faultCode: "", faultString: res["message"], data: null)
    //         }
    //     } catch (Exception e) {
    //         return CommonResponse.resolveException(e, loggingManaged)
    //     }
    // }

    // @PostMapping("/updateDebitSub")
    // CommonResponse updateDebitSub(@RequestBody Map<String, Object> body) {
    //     try {
    //         Map<String, String> headers = new HashMap<>()
    //         headers.put("Content-Type", "application/json")
    //         headers.put("Authorization", "Bearer " + token)

    //         String url = host + "updateDebitSub"
    //         String resp = httpManaged.post(["url": url], headers, new Gson().toJson(body))
    //         def res = new JsonSlurper().parseText(resp)
    //         if (res["errorCode"].toString() == "0") {
    //             return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "Thành công")
    //         } else {
    //             return new CommonResponse(errorCode: 1, faultCode: "", faultString: res["message"], data: null)
    //         }
    //     } catch (Exception e) {
    //         return CommonResponse.resolveException(e, loggingManaged)
    //     }
    // }

    // @PostMapping("/paymentCustomer")
    // CommonResponse paymentCustomer(@RequestBody Map<String, Object> body) {
    //     try {
    //         Map<String, String> headers = new HashMap<>()
    //         headers.put("Content-Type", "application/json")
    //         headers.put("Authorization", "Bearer " + token)

    //         String url = host + "paymentCustomer"
    //         String resp = httpManaged.post(["url": url], headers, new Gson().toJson(body))
    //         def res = new JsonSlurper().parseText(resp)
    //         if (res["errorCode"].toString() == "0") {
    //             return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "Thành công")
    //         } else {
    //             return new CommonResponse(errorCode: 1, faultCode: "", faultString: res["message"], data: null)
    //         }
    //     } catch (Exception e) {
    //         return CommonResponse.resolveException(e, loggingManaged)
    //     }
    // }

    @GetMapping("/searchDebitData")
    CommonResponse searchDebitData(@RequestParam String numberId) {
        try {
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")
            headers.put("Authorization", "Bearer " + token)

            String url = host + "searchDebitData?numberId=" + numberId
            String resp = httpManaged.get(url, headers)
            def res = new JsonSlurper().parseText(resp)
            if (res["errorCode"].toString() == "0") { // Goi tra cuu tra ve co thong tin
                List data = (List) res["data"]

                // Fake test
                if (numberId == "0102636299") {
                    def itemF = [:]
                    itemF["enterpriseName"] = "Tập đoàn viễn thông quân đội Viettel"
                    itemF["status"] = "DEBIT"
                    data.add(itemF)
                }
                //

                String nomang = ""
                if (data.size() > 0) {
                    for (def item : data) {
                        if (item["status"] == "DEBIT") {
                            if (!nomang.contains(item["enterpriseName"] + ",")) {
                                nomang += item["enterpriseName"] + ","
                            }
                        }
                    }
                }
                if (nomang != "") {  // Co no o cac nha mang => No
                    nomang = nomang.substring(0, nomang.length() - 1)
                    return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Khách hàng đang nợ cước dịch vụ fiber tại nhà mạng: " + nomang, data: res["data"])
                }

                // Co ban ghi nhung la thanh toan => Khong no
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res["data"])
            } else {  // Goi tra cuu tra ve loi
                if (res["message"].toString() == "Không có thông tin") { // Loi khong co thong tin => Khong no
                    return new CommonResponse(errorCode: 0, faultCode: "", faultString: res["message"], data: null)
                }

                // Loi khac thi show ra
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: res["message"], data: null)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

}
