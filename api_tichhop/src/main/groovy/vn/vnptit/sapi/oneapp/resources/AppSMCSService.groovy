package vn.vnptit.sapi.oneapp.resources

import groovy.json.JsonSlurper
import groovy.util.slurpersupport.GPathResult
import io.swagger.model.BaseResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.smcs.request.EditDiemBanRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.RoiDiChamSocUSSDRequest
import vn.vnptit.sapi.oneapp.redis.RedisRepository
import vn.vnptit.sapi.oneapp.util.*

@RestController
@RequestMapping("smcs")
class AppSMCSService {

    @Autowired
    HttpManaged httpManaged

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    GenericApi genericApi

    @Autowired
    NativeWebRequest request

    @Value("\${spring.httpservices.smcs.durl2}")
    String smcsUrl

    @Value("\${spring.httpservices.smcs.dusr}")
    String smcsUsr

    @Value("\${spring.httpservices.smcs.dpwd}")
    String smcsPwd

    @Autowired
    RedisRepository redisRepository

    CommonResponse getToken() {
        try {
            def env = post1(smcsUrl,
                    """<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:VNPServices">
                               <soapenv:Header/>
                               <soapenv:Body>
                                  <urn:login soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                                     <login xsi:type="xsd:string">$smcsUsr</login>
                                     <password xsi:type="xsd:string">$smcsPwd</password>
                                  </urn:login>
                               </soapenv:Body>
                            </soapenv:Envelope>""")

            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            String description = env.Body.loginResponse.description.text()
            String responseStatus = env.Body.loginResponse.responseStatus.text()
            String sessionID = env.Body.loginResponse.sessionID.text()
            if (description == "OK" && responseStatus == "1" && sessionID != null && sessionID != "") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: sessionID)
            } else {
                return new CommonResponse(errorCode: 1, faultCode: responseStatus, faultString: description, data: sessionID)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    CommonResponse keepAlive(String sessionIDK) {
        try {
            def env = post1(smcsUrl,
                    """<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:VNPServices">
                               <soapenv:Header/>
                               <soapenv:Body>
                                  <urn:keepAlive soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                                     <sessionID xsi:type="xsd:string">$sessionIDK</sessionID>
                                  </urn:keepAlive>
                               </soapenv:Body>
                            </soapenv:Envelope>""")

            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            String description = env.Body.keepAliveResponse.description.text()
            String responseStatus = env.Body.keepAliveResponse.responseStatus.text()
            String sessionID = env.Body.keepAliveResponse.sessionID.text()
            if (responseStatus == "1") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: sessionID)
            } else {
                return new CommonResponse(errorCode: 1, faultCode: responseStatus, faultString: description, data: sessionID)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    String sessionId = "123456"

    String getSessionId() {
        CommonResponse result = keepAlive(sessionId)
        if (result["errorCode"].toString() != "0") {
            CommonResponse result2 = getToken()
            if (result2["errorCode"].toString() == "0") {
                sessionId = result2["data"].toString()
            }
        }
        sessionId
    }

    @GetMapping("/getStaffInfo")
    CommonResponse getStaffInfo(@RequestParam("account") String account) {
        try {
            def staffInfo = getStaffInfoD(account)
            if (staffInfo["error"] == 1) {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: staffInfo["message"], data: null)
            }

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: staffInfo["data"])
        } catch (Exception e) {
            return new CommonResponse(errorCode: 1, faultCode: "", faultString: e.message, data: null)
        }
    }

    @PostMapping("/modifyAgent")
    @LoggingInOut
    ResponseEntity<BaseResponse> modifyAgent(@RequestBody EditDiemBanRequest agent) {
        try {
            def staffInfo = getStaffInfoD(agent["account"].toString())
            if (staffInfo["error"] == 1) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, staffInfo["message"].toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            def staff_result = staffInfo["data"]
            String staff_code = staff_result["staff_code"]
            String AgentName = agent.agentName
            String ParAgentCode = agent.parAgentCode
            String ContactName = agent.contactName
            String Address = agent.address
            String Longitude = agent.longitude
            String Latitude = agent.latitude
            String ContactPhone = agent.contactPhone
            String ContactEmail = agent.contactEmail
            String EloadNumber = agent.eloadNumber
            String MSIN = agent.msin
            String StateCode = agent.stateCode != null ? agent.stateCode : ""
            String CountyCode = agent.countryCode
            String AgentCodeOld = agent.agentCodeOld
            String AgentCodeNew = agent.agentCodeNew
            String body = """<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:VNPServices">
                                <soapenv:Header/>
                                <soapenv:Body>
                                    <urn:modifyAgent2 soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                                        <sessionID xsi:type="xsd:string">${getSessionId()}</sessionID>
                                        <p_AgentName xsi:type="xsd:string">$AgentName</p_AgentName>
                                        <p_AgentCodeOld xsi:type="xsd:string">$AgentCodeOld</p_AgentCodeOld>
                                        <p_AgentCodeNew xsi:type="xsd:string">$AgentCodeNew</p_AgentCodeNew>
                                        <p_ParAgentCode xsi:type="xsd:string">$ParAgentCode</p_ParAgentCode>
                                        <p_StaffCode xsi:type="xsd:string">$staff_code</p_StaffCode>
                                        <p_ContactName xsi:type="xsd:string">$ContactName</p_ContactName>
                                        <p_Address xsi:type="xsd:string">$Address</p_Address>
                                        <p_Longitude xsi:type="xsd:string">$Longitude</p_Longitude>
                                        <p_Latitude xsi:type="xsd:string">$Latitude</p_Latitude>
                                        <p_ContactPhone xsi:type="xsd:string">$ContactPhone</p_ContactPhone>
                                        <p_ContactEmail xsi:type="xsd:string">$ContactEmail</p_ContactEmail>
                                        <p_Tin xsi:type="xsd:string"></p_Tin>
                                        <p_EloadNumber xsi:type="xsd:string">$EloadNumber</p_EloadNumber>
                                        <p_MSIN xsi:type="xsd:string">$MSIN</p_MSIN>
                                        <p_ContractNumber xsi:type="xsd:string"></p_ContractNumber>
                                        <p_ContractDate xsi:type="xsd:string"></p_ContractDate>
                                        <p_ContactTel xsi:type="xsd:string"></p_ContactTel>
                                        <p_SignboardDate xsi:type="xsd:string"></p_SignboardDate>
                                        <p_Birthday xsi:type="xsd:string"></p_Birthday>
                                        <p_StateCode xsi:type="xsd:string">$StateCode</p_StateCode>
                                        <p_CountyCode xsi:type="xsd:string">$CountyCode</p_CountyCode>
                                        <p_AgentLevel xsi:type="xsd:string"></p_AgentLevel>
                                        <p_Province2025 xsi:type="xsd:string">${agent.province2025 != null ? agent.province2025 : ""}</p_Province2025>
                                        <p_County2025 xsi:type="xsd:string">${agent.county2025 != null ? agent.county2025 : ""}</p_County2025>
                                    </urn:modifyAgent2>
                                </soapenv:Body>
                            </soapenv:Envelope>"""
            def env = post1(smcsUrl, body)
            String ke = env.Body.modifyAgent2Response.p_errCode.text()
            if (ke == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                String errContent = env.Body.modifyAgent2Response.p_errContent.text()
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, errContent, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/roiDiChamSocUSSD")
    @LoggingInOut
    ResponseEntity<BaseResponse> roiDiChamSocUSSD(@RequestBody RoiDiChamSocUSSDRequest body) {
        try {
            def staffInfo = getStaffInfoD(body.account)
            if (staffInfo["error"] == 1) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, staffInfo["message"].toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            String xml = """
                    <soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:VNPServices">
                        <soapenv:Header/>
                        <soapenv:Body>
                            <urn:roiDiChamSocUSSD2 soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                                <sessionID xsi:type="xsd:string">${getSessionId()}</sessionID>
                                <p_AgentCode xsi:type="xsd:string">${body.agentCode}</p_AgentCode>
                                <p_StaffCode xsi:type="xsd:string">${staffInfo["data"]["staff_code"]}</p_StaffCode>
                                <p_Longitude xsi:type="xsd:string">${body.longitude}</p_Longitude>
                                <p_Latitude xsi:type="xsd:string">${body.latitude}</p_Latitude>
                                <p_PictureName xsi:type="xsd:string">${body.picture}</p_PictureName>
                            </urn:roiDiChamSocUSSD2>
                        </soapenv:Body>
                    </soapenv:Envelope>
                """
            def env = post1(smcsUrl, xml)
            String errCode = env.Body.roiDiChamSocUSSD2Response.p_errCode.text()
            if (errCode == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                String errContent = env.Body.roiDiChamSocUSSD2Response.p_errContent.text()
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, errContent, ErrorCodeDefine.SUCCESS))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.SUCCESS))
        }
    }

    def getStaffInfoD(String account) {
        def result = [:]
        result["error"] = 1
        result["message"] = "Có lỗi xảy ra khi getStaffInfo"
        try {
            if (redisRepository.getKeyValue("OneBSS_tichhop_SMCS_account_" + account)) {
                return redisRepository.getKeyValue("OneBSS_tichhop_SMCS_account_" + account)
            }

            String data = '{"error_code":"0","result":[{"area_name":"An Giang","area_code":"AGG","area_id":"805"},{"area_name":"Bắc Cạn","area_code":"BCN","area_id":"207"},{"area_name":"Bình Dương","area_code":"BDG","area_id":"711"},{"area_name":"Bình Định","area_code":"BDH","area_id":"507"},{"area_name":"Bắc Giang","area_code":"BGG","area_id":"221"},{"area_name":"Bạc Liêu","area_code":"BLU","area_id":"821"},{"area_name":"Bắc Ninh","area_code":"BNH","area_id":"223"},{"area_name":"Bình Phước","area_code":"BPC","area_id":"707"},{"area_name":"Bến Tre","area_code":"BTE","area_id":"811"},{"area_name":"Bình Thuận","area_code":"BTN","area_id":"715"},{"area_name":"Cao Bằng","area_code":"CBG","area_id":"203"},{"area_name":"Cà Mau","area_code":"CMU","area_id":"823"},{"area_name":"Cần Thơ","area_code":"CTO","area_id":"815"},{"area_name":"Đắk Nông","area_code":"DAN","area_id":"8231318"},{"area_name":"Điện Biên","area_code":"DBN","area_id":"108"},{"area_name":"Đắk Lắk","area_code":"DLC","area_id":"605"},{"area_name":"Đà Nẵng","area_code":"DNG","area_id":"501"},{"area_name":"Đồng Nai","area_code":"DNI","area_id":"713"},{"area_name":"Đồng Tháp","area_code":"DTP","area_id":"803"},{"area_name":"Gia Lai","area_code":"GLI","area_id":"603"},{"area_name":"GPC","area_code":"GPC","area_id":"8232328"},{"area_name":"Hậu Giang","area_code":"HAG","area_id":"8231382"},{"area_name":"Hòa Bình","area_code":"HBH","area_id":"305"},{"area_name":"Hồ Chí Minh","area_code":"HCM","area_id":"701"},{"area_name":"Hải Dương","area_code":"HDG","area_id":"107"},{"area_name":"Hà Giang","area_code":"HGG","area_id":"201"},{"area_name":"Hà Nội","area_code":"HNI","area_id":"101"},{"area_name":"Hà Nam","area_code":"HNM","area_id":"111"},{"area_name":"Hải Phòng","area_code":"HPG","area_id":"103"},{"area_name":"Hà Tĩnh","area_code":"HTH","area_id":"405"},{"area_name":"Thừa Thiên-Huế","area_code":"HUE","area_id":"411"},{"area_name":"Hưng Yên","area_code":"HYN","area_id":"109"},{"area_name":"I-Telecom","area_code":"ITC","area_id":"8254111"},{"area_name":"Kiên Giang","area_code":"KGG","area_id":"813"},{"area_name":"Khánh Hòa","area_code":"KHA","area_id":"511"},{"area_name":"Kon Tum","area_code":"KTM","area_id":"601"},{"area_name":"Long An","area_code":"LAN","area_id":"801"},{"area_name":"Lào Cai","area_code":"LCI","area_id":"205"},{"area_name":"Lai Châu","area_code":"LCU","area_id":"301"},{"area_name":"Lâm Đồng","area_code":"LDG","area_id":"703"},{"area_name":"Lạng Sơn","area_code":"LSN","area_id":"209"},{"area_name":"Nghệ An","area_code":"NAN","area_id":"403"},{"area_name":"Ninh Bình","area_code":"NBH","area_id":"117"},{"area_name":"Nam Định","area_code":"NDH","area_id":"113"},{"area_name":"Ninh Thuận","area_code":"NTN","area_id":"705"},{"area_name":"Phú Thọ","area_code":"PTO","area_id":"217"},{"area_name":"Phú Yên","area_code":"PYN","area_id":"509"},{"area_name":"Quảng Bình","area_code":"QBH","area_id":"407"},{"area_name":"Quảng Ninh","area_code":"QNH","area_id":"225"},{"area_name":"Quảng Ngãi","area_code":"QNI","area_id":"505"},{"area_name":"Quảng Nam","area_code":"QNM","area_id":"503"},{"area_name":"Quảng Trị","area_code":"QTI","area_id":"409"},{"area_name":"Sơn La","area_code":"SLA","area_id":"303"},{"area_name":"Sóc Trăng","area_code":"STG","area_id":"819"},{"area_name":"Thái Bình","area_code":"TBH","area_id":"115"},{"area_name":"Tiền Giang","area_code":"TGG","area_id":"807"},{"area_name":"Thanh Hóa","area_code":"THA","area_id":"401"},{"area_name":"Tây Ninh","area_code":"TNH","area_id":"709"},{"area_name":"Thái Nguyên","area_code":"TNN","area_id":"215"},{"area_name":"Tuyên Quang","area_code":"TQG","area_id":"211"},{"area_name":"Trà Vinh","area_code":"TVH","area_id":"817"},{"area_name":"Vĩnh Long","area_code":"VLG","area_id":"809"},{"area_name":"VNP","area_code":"VNP","area_id":"2"},{"area_name":"Vĩnh Phúc","area_code":"VPC","area_id":"104"},{"area_name":"Bà Rịa - Vũng Tàu","area_code":"VTU","area_id":"717"},{"area_name":"Yên Bái","area_code":"YBI","area_id":"213"}]}'
            def json = new JsonSlurper().parseText(data)
            List lsDataProvince = json["result"] as List
            String body = """<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:VNPServices">
                               <soapenv:Header/>
                               <soapenv:Body>
                                  <urn:getStaffInfo soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                                     <sessionID xsi:type="xsd:string">${getSessionId()}</sessionID>
                                     <p_StaffCode xsi:type="xsd:string">${account.toUpperCase()}</p_StaffCode>
                                  </urn:getStaffInfo>
                               </soapenv:Body>
                            </soapenv:Envelope>"""
            def env = post1(smcsUrl, body)
            def parts = env.text().split("\\|")
            if (parts.size() >= 12) {
                result["error"] = 0
                result["data"] = [
                        staff_id       : parts[0],
                        reseller_id    : parts[1],
                        province_id    : parts[2],
                        reseller_code  : parts[3],
                        staff_code     : parts[4],
                        staff_type     : parts[5],
                        sales_type     : parts[6],
                        eload_no       : "",
                        staff_pre_post : parts[8],
                        hrm_province_id: parts[9],
                        hrm_province   : parts[10],
                        hrm_code       : parts[11],
                        province       : lsDataProvince.find { p -> p["area_id"] == parts[2] }
                ]
            } else {
                result["message"] = env.text().replaceFirst("^[^A-Za-z]+", "")
            }
        } catch (Exception ex) {
            result["message"] = ex.message
        }

        redisRepository.setKeyValue("OneBSS_tichhop_SMCS_account_" + account, result)
        return result
    }

    GPathResult post1(String url, String body) {
        Map<String, String> headers = [:]
        headers.put("SOAPAction", "")
        headers.put("Content-Type", "text/xml; charset=utf-8")
        String resp = httpManaged.post(["url": url], headers, body)
        return new XmlSlurper().parseText(resp)
    }

    GPathResult post2(String url, String body) {
        Map<String, String> headers = [:]
        headers.put("SOAPAction", "")
        headers.put("Content-Type", "text/xml; charset=utf-8")
        String resp = httpManaged.postWithTimeout(["url": url], headers, body, 10000)
        return new XmlSlurper().parseText(resp)
    }

}
