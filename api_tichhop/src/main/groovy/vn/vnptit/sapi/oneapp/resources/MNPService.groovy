package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse

@RestController
@RequestMapping("mnp")
class MNPService {

    @Autowired
    HttpManaged httpManaged

    final String url = "http://10.165.7.239:3410/mapi"

    @GetMapping("/checkParticipant")
    CommonResponse checkParticipant(@RequestParam String sdt) {
        String telcomType = "01"
        try {
            if (sdt.startsWith("0")) {
                sdt = "84" + sdt.substring(1)
            }
            if (sdt.length() == 11) {
                Map<String, String> headers = new HashMap<>()
                headers.put("Content-Type", "application/json")

                def body = [:]
                body["msisdn"] = sdt

                String resp = httpManaged.post(["url": url + "/dataservices/check_participant"], headers, new Gson().toJson(body))
                def res = new JsonSlurper().parseText(resp)
                if (res["error_code"].toString() == "0") {
                    if (res["result"].toString() == "02") {  // Da chuyen mang sang Vinaphone
                        telcomType = "01"
                    } else if (res["result"].toString() == "0") { // Chua chuyen mang lan nao thi check dau so
                        if (sdt.startsWith("8481") || sdt.startsWith("8482") || sdt.startsWith("8483") || sdt.startsWith("8484") ||
                                sdt.startsWith("8485") || sdt.startsWith("84886") || sdt.startsWith("84888") || sdt.startsWith("84889") ||
                                sdt.startsWith("8491") || sdt.startsWith("8494")) {  // Noi mang
                            telcomType = "01"
                        } else {  // Ngoai mang
                            telcomType = "02"
                        }
                    } else {  // Da chuyen mang sang nha mang khac Vinaphone
                        telcomType = "02"
                    }
                }
            }
        } catch(Exception e) {
            
        }

        return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: telcomType)
    }

}
