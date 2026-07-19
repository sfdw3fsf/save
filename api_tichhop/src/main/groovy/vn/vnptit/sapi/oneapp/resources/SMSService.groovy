package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
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
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.sms.SEND_SMS_REQ
import vn.vnptit.sapi.oneapp.models.sms.TraCuuSMSReq
import vn.vnptit.sapi.oneapp.models.sms.VSHOP_SEND_SMS_REQ
import vn.vnptit.sapi.oneapp.service.HopDongService
import vn.vnptit.sapi.oneapp.util.GenericApi
import vn.vnptit.sapi.oneapp.util.LoggingInOut

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("sms")
class SMSService {

    ObjectMapper json = new ObjectMapper();

    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    HttpManaged httpManaged
    @Value("\${spring.httpservices.sms.url}")
    String smsUrl
    @Value("\${spring.httpservices.sms.token}")
    String tokenSMS

    @Value("\${spring.httpservices.sms.vshop.url}")
    String vshopUrl
    @Value("\${spring.httpservices.sms.vshop.user}")
    String vshopUser
    @Value("\${spring.httpservices.sms.vshop.password}")
    String vshopPassword
    @Value("\${spring.httpservices.sms.vshop.key}")
    String vshopKey

    @Autowired
    NativeWebRequest request

    @Autowired
    HopDongService hopDongService

    @Autowired
    GenericApi genericApi

    @PostMapping("/send")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse send(@Valid @RequestBody SEND_SMS_REQ body) {
        try {         
            if (body.msisdn.indexOf("84") == 0) {
                body.msisdn = "0" + body.msisdn.substring(2)
            } else if (body.msisdn.indexOf("0") != 0) {
                body.msisdn = "0" + body.msisdn
            }
            if (body.type == null || body.type == "") body.type = "reply"
            hopDongService.init(request.getHeader("Authorization"))
            hopDongService.sendSMS(body.msisdn, body.content, (body.note && body.note != null && body.note != "") ? body.note : "Send từ api tichhop")
            // def req = [:]
            // req.msisdn = body.msisdn
            // req.content = body.content
            // req.mt_type = body.type
            // String bodyString = json.writeValueAsString(req)
            // post(smsUrl, bodyString)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Thành công")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    Object post(String url, String body) {
        String resp = httpManaged.post(["url": url], ["Content-Type": "application/json"], body)
        new JsonSlurper().parseText(resp) as Object
    }

    @PostMapping("/TraCuuSMS")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse TraCuuSMS(@Valid @RequestBody TraCuuSMSReq body) {
        try {
            body.key = "dhsxsms2022"
            body.service = "getNotifyPcrf"
            body.typeid = "VNPT"

            // String url = "http://10.70.52.120:6969/api/TRACUU_SMS"
            // Chua tim thay cho nao lay session cho api goi truc tiep nen tam thoi van goi qua dhsx
            String url = "http://10.156.3.61:83/mapi/g"
            String resp = httpManaged.post(["url": url], ["Content-Type": "application/json; charset=utf-8"], new Gson().toJson(body))
            def ret = new JsonSlurper().parseText(resp.replace('\"','"'))
            if (ret["error_code"] == "0") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret["result"])
            } else {
                return new CommonResponse(errorCode: 1, faultCode: ret["error_code"], faultString: ret["message"], data: null)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    def removeVietnameseAccents(String str) {
        String[][] vietnameseCharMap = [
                ["à", "á", "ả", "ã", "ạ", "ă", "ằ", "ắ", "ẳ", "ẵ", "ặ", "â", "ầ", "ấ", "ẩ", "ẫ", "ậ"],
                ["è", "é", "ẻ", "ẽ", "ẹ", "ê", "ề", "ế", "ể", "ễ", "ệ"],
                ["ì", "í", "ỉ", "ĩ", "ị"],
                ["ò", "ó", "ỏ", "õ", "ọ", "ô", "ồ", "ố", "ổ", "ỗ", "ộ", "ơ", "ờ", "ớ", "ở", "ỡ", "ợ"],
                ["ù", "ú", "ủ", "ũ", "ụ", "ư", "ừ", "ứ", "ử", "ữ", "ự"],
                ["ỳ", "ý", "ỷ", "ỹ", "ỵ"],
                ["đ"],
                ["À", "Á", "Ả", "Ã", "Ạ", "Ă", "Ằ", "Ắ", "Ẳ", "Ẵ", "Ặ", "Â", "Ầ", "Ấ", "Ẩ", "Ẫ", "Ậ"],
                ["È", "É", "Ẻ", "Ẽ", "Ẹ", "Ê", "Ề", "Ế", "Ể", "Ễ", "Ệ"],
                ["Ì", "Í", "Ỉ", "Ĩ", "Ị"],
                ["Ò", "Ó", "Ỏ", "Õ", "Ọ", "Ô", "Ồ", "Ố", "Ổ", "Ỗ", "Ộ", "Ơ", "Ờ", "Ớ", "Ở", "Ỡ", "Ợ"],
                ["Ù", "Ú", "Ủ", "Ũ", "Ụ", "Ư", "Ừ", "Ứ", "Ử", "Ữ", "Ự"],
                ["Ỳ", "Ý", "Ỷ", "Ỹ", "Ỵ"],
                ["Đ"]
        ]
        String[] correspondingCharMap = [
                "a", "e", "i", "o", "u", "y", "d",
                "A", "E", "I", "O", "U", "Y", "D"
        ]
        for (int i = 0; i < vietnameseCharMap.length; i++) {
            vietnameseCharMap[i].each { kytu ->
                str = str.replace(kytu, correspondingCharMap[i])
            }
        }
        return str
    }

    @PostMapping("/vshop_send")
    @LoggingInOut
    CommonResponse vshop_send(@Valid @RequestBody VSHOP_SEND_SMS_REQ input) {
        try {
            if (input.vshopKey != vshopKey) {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Bạn không có quyền thực hiện chức năng này")
            }

            // input.type
            // 1. Thông báo thông tin nhân viên xử lý đơn hàng
            // 2. Thông báo trạng thái đơn hàng  được tiếp nhận đến nhân viên phát triển CTV 
            // 3. Thông báo trạng thái đơn hàng  đã thành công đến nhân viên phát triển CTV LK
            // 4. Thông báo có đơn hàng cần xử lý  cho NV XLDH
            // 5. Thông báo đơn hàng cho nhân viên tư vấn, hỗ trợ bán hàng (SC) + Admin PBH
            // 6. Thông báo số đơn hàng tồn ngày hôm trước và số đơn hàng đến hạn ngày hôm nay
            // 7. Thông báo số đơn hàng tồn trong ngày
            // 8. Thông báo khách hàng hủy đơn hàng thành công (trường hợp KH Hủy đơn do không còn nhu cầu)

            // BSS-119290, BSS-119294
            // 3.6.7. Sửa nội dung sms
            // 9.  Thông báo xác thực mua sim
            // 10. Thông báo chúc mừng khách hàng đã mua thành công sim số
            // 11. Thông báo giới thiệu khách hàng của CTV

            String labelId = "", templateId = "", agentId = ""
            List content = []
            if (input.type == "1" || input.type == "8" || input.type == "9" || input.type == "10") {   // Gui brandname VSHOP
                labelId = "142139"
                agentId = "336"
                if (input.type == "1") {
                    templateId = "1307081"
                } else if (input.type == "8") {
                    templateId = "1307082"
                } else if (input.type == "9") {
                    templateId = "1307080"
                } else if (input.type == "10") {
                    templateId = "1339972"
                }

                if (input.msisdn.indexOf("0") == 0) {
                    input.msisdn = "84" + input.msisdn.substring(1)
                } else if (input.msisdn.indexOf("84") != 0) {
                    input.msisdn = "84" + input.msisdn
                }

                for (int i = 0; i < input.content.size(); i++) {
                    def row = [:]
                    row["NUM"] = (i+1).toString()
                    row["CONTENT"] = removeVietnameseAccents(input.content[i])
                    content.add(row)
                }

                def bodyRQST = [:]
                bodyRQST["name"] = "send_sms_list"
                bodyRQST["REQID"] = "17234833422263683338"
                bodyRQST["LABELID"] = labelId
                bodyRQST["CONTRACTID"] = "13108"
                bodyRQST["CONTRACTTYPEID"] = "1"
                bodyRQST["TEMPLATEID"] = templateId
                bodyRQST["PARAMS"] = content
                bodyRQST["SCHEDULETIME"] = ""
                bodyRQST["MOBILELIST"] = input.msisdn
                bodyRQST["ISTELCOSUB"] = "0"
                bodyRQST["AGENTID"] = agentId
                bodyRQST["APIUSER"] = vshopUser
                bodyRQST["APIPASS"] = vshopPassword
                bodyRQST["USERNAME"] = "TT-KHCN-KM"
                bodyRQST["DATACODING"] = "0"
                bodyRQST["ENCRYPTED"] = "0"

                def body = [:]
                body["RQST"] = bodyRQST

                def resp = post(vshopUrl, new Gson().toJson(body))
                if (resp["RPLY"]) {
                    if (resp["RPLY"]["ERROR"].toString() == "0") {
                        return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Thành công")
                    } else {
                        return new CommonResponse(errorCode: 1, faultCode: resp["RPLY"]["ERROR"], faultString: resp["RPLY"]["ERROR_DESC"])
                    }
                } else {
                    return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Lỗi không xác định")
                }
            } else {    // Gui brandname VNPT
                SEND_SMS_REQ body = new SEND_SMS_REQ()
                body["msisdn"] = input.msisdn
                body["content"] = getContent(input.content, input.type)
                CommonResponse res = send(body)
                return res
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    String getContent(def content, String type) {
        String result = ""
        try {
            switch (type) {
                case "2":
                    result = "[${content[0]}] Don hang SIM SO, ma DH ${content[1]} cua CTV dat hang tu ${content[2]} da duoc phan cong cho nhan vien ${content[3]} so dien thoai ${content[4]} di giao"
                    break
                case "3":
                    result = "[${content[0]}] Don hang ma DH ${content[1]}, ${content[2]}, ${content[3]} dat hang tu ${content[4]} da duoc giao thanh cong cho khach hang ${content[5]}, ${content[6]} tai thoi diem ${content[7]}"
                    break
                case "4":
                    result = """[${content[0]}] Ban co don hang SIMSO TRA TRUOC tu ${content[1]}
-  So DT ĐB/CTV gioi thieu: ${content[2]}
-  Ma don hang ${content[3]}
-  So TB ${content[4]} - ${content[5]} - ${content[6]}
-  Tong tien ${content[7]} d, da bao gom phi giao hang
-  Hinh thuc thanh toan ${content[8]}
Vui long lien he KH ${content[9]}
-  D/C: ${content[10]}
-  So DT LH: ${content[11]} giao hang trong vong ${content[12]}.
Vui long truy cap app Employee de xu ly don hang dung thoi han. Tran trong."""
                    break
                case "5":
                    result = "[${content[0]}] BAN HANG: He thong vua ghi nhan don hang (SIM SO) duoc dat hang thanh cong tren ${content[1]}, thoi gian giao hang ${content[2]} va phan giao den NV xu ly ${content[3]}, thuoc PBH ${content[4]}, TTKD ${content[5]}. Ban vui long theo doi tien trinh xu ly don hang va don doc cac don hang xu ly dung thoi han. Tran trong."
                    break
                case "6":
                    result = """"BAN HANG: He thong ghi nhan tai PBH ${content[0]}, TTKD ${content[1]}:
- Ngay hom qua ${content[2]} co ${content[3]} don hang den han xu ly nhung chua hoan thanh.
- Ngay hom nay ${content[4]} co ${content[5]} don hang den han xu ly. 
De nghi SC PBH/Admin PBH don doc cac don hang xu ly dung thoi han. Tran trong."""
                    break
                case "7":
                    result = """"BAN HANG: He thong ghi nhan tai PBH ${content[0]}, TTKD ${content[1]}:
- Ngay hom nay ${content[2]} con ${content[3]} don hang den han xu ly nhung chua hoan thanh. 
De nghi SC PBH/Admin PBH don doc cac don hang xu ly GAP dung thoi han. Tran trong."""
                    break
                case "11":
                    result = "[${content[0]}] GIOI THIEU KH: He thong vua ghi nhan don gioi thieu khach hang ${content[1]} ${content[2]} cua CTV LK do ma gioi thieu ${content[3]}. Ban vui long tiep nhan thong tin va tu van khach hang."
                default:
                    break
            }
        } catch (Exception ex) {
        }
        result
    }
    
}
