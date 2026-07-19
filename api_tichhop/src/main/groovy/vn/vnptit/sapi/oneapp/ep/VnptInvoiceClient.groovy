package vn.vnptit.sapi.oneapp.ep

import com.google.common.collect.ImmutableMap
import groovy.util.slurpersupport.GPathResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.einvoice.InvCustomer
import vn.vnptit.sapi.oneapp.models.einvoice.InvItem
import vn.vnptit.sapi.oneapp.util.DUtils

import javax.annotation.PostConstruct

/**
 * @author VietNH*
 */
@Component
class VnptInvoiceClient {

    Logger logger = LoggerFactory.getLogger(VnptInvoiceClient.class)

    @Autowired
    private Environment env;

    private String url
    private String password
    private String username
    private String thongTu
    private String account
    private String acpass

    @Value("\${spring.httpservices.vnptinvoice.publicservice}")
    String publicServicePath
    @Value("\${spring.httpservices.vnptinvoice.bizservice}")
    String bizServicePath
    @Value("\${spring.httpservices.vnptinvoice.portalservice}")
    String portalServicePath
    String extVNPTService = "ExtVNPTService.asmx"

    @Value("\${spring.httpservices.vnptinvoice.vtt.username}")
    String lUsernameVTT
    @Value("\${spring.httpservices.vnptinvoice.vtt.password}")
    String lPasswordVTT
    @Value("\${spring.httpservices.vnptinvoice.vtt.account}")
    String lAccountVTT
    @Value("\${spring.httpservices.vnptinvoice.vtt.acpass}")
    String lAcpassVTT

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    HttpManaged httpManaged

    static List<String> lVTT = ["NAN"]

    static Map<String, String> mUrlVTT = ["NAN": "https://testnoibo-tt78admindemo.vnpt-invoice.com.vn"]

    Map<String, String> mUsernameVTT = [:]
    Map<String, String> mPasswordVTT = [:]
    Map<String, String> mAccountVTT = [:]
    Map<String, String> mAcpassVTT = [:]

    @PostConstruct
    void initVTT() {
        mUsernameVTT = lUsernameVTT.split(",").collectEntries { entry ->
            def (key, value) = entry.split(":")
            [(key): value]
        }
        mPasswordVTT = lPasswordVTT.split(",").collectEntries { entry ->
            def (key, value) = entry.split(":")
            [(key): value]
        }
        mAccountVTT = lAccountVTT.split(",").collectEntries { entry ->
            def (key, value) = entry.split(":")
            [(key): value]
        }
        mAcpassVTT = lAcpassVTT.split(",").collectEntries { entry ->
            def (key, value) = entry.split(":")
            [(key): value]
        }
    }

    void init(String maTinh) {
        if (maTinh == null || maTinh == "") {
            maTinh = "khdn"
        }

        url = "https://testnoibo-tt78admindemo.vnpt-invoice.com.vn"
        username = "ccbsservice"
        password = "fPs3LKWVu@"
        thongTu = "1"
        account = "ccbsadmin"
        acpass = "fPs3LKWVu@"
    }

    //Phát hành hóa đơn
    Map<String, Object> importAndPublishInv(String maTinh, InvItem inv, String pattern, String serial, String convert) {
        if (maTinh == null || maTinh == "") {
            maTinh = "khdn"
        }
        String accountX = "ccbsadmin"
        String acPassX = "fPs3LKWVu@"
        String urlX = "https://testnoibo-tt78admindemo.vnpt-invoice.com.vn"
        String usernameX = "ccbsservice"
        String passwordX = "fPs3LKWVu@"

        String body = """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
			  <soap:Body>
				<ImportAndPublishInv xmlns="http://tempuri.org/">
				  <Account><![CDATA[$accountX]]></Account>
				  <ACpass><![CDATA[$acPassX]]></ACpass>
				  <xmlInvData>${inv.toXml78()}</xmlInvData>
				  <username>$usernameX</username>
				  <password>$passwordX</password>
				  <pattern>$pattern</pattern>
				  <serial>$serial</serial>
				  <convert>$convert</convert>
				</ImportAndPublishInv>
			  </soap:Body>
			</soap:Envelope>"""

        def env = post(urlX + "/" + publicServicePath, ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/ImportAndPublishInv"], body)
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.ImportAndPublishInvResponse.ImportAndPublishInvResult
        logger.debug("ImportAndPublishInv response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = retInfo[1]
        def err_messages = [
                "1" : "Tài khoản đăng nhập sai hoặc không có quyền thêm khách hàng",
                "3" : "Dữ liệu xml đầu vào không đúng quy định",
                "5" : "Không phát hành được hóa đơn",
                "7" : "User name không phù hợp, không tìm thấy company tương ứng cho user.",
                "10": "Lô có số hóa đơn vượt quá max cho phép",
                "20": "Pattern và serial không phù hợp, hoặc không tồn tại hóa đơn đã đăng kí có sử dụng Pattern và serial truyền vào"
        ]
        if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
            err_code = "1200" //retInfo[1]
            err_msg = err_messages.get(retInfo[1])
            return ["error_code": "$err_code".toString(), "message": "$err_msg".toString(), "data": ""]
        } else {
            def result = err_code
            err_code = "0"
            return ["error_code": "$err_code".toString(), "message": "$result", "data": "$err_msg".toString()]
        }
    }

    //Xác nhận thanh toán
    Map<String, Object> hddt_confirmPaymentFkey(String fKey, String pattern, String serial) {
        String body = """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
				  <soap:Body>
					<confirmPaymentFkey xmlns="http://tempuri.org/">
					  <lstFkey>$fKey</lstFkey>
					  <userName>$username</userName>
					  <userPass>$password</userPass>
					  <pattern>$pattern</pattern>
					  <serial>$serial</serial>
					</confirmPaymentFkey>
				  </soap:Body>
				</soap:Envelope>"""

        def env = post(url + "/" + bizServicePath, ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/confirmPaymentFkey"], body)
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.confirmPaymentFkeyResponse.confirmPaymentFkeyResult
        logger.debug("confirmPaymentFkey response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền thêm khách hàng",
                    "2" : "Chuỗi token không chính xác",
                    "6" : "Không tìm thấy hóa đơn tương ứng chuỗi đưa vào",
                    "7" : "Không phân phối được",
                    "13": "Hóa đơn đã gạch nợ rồi"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code".toString(), "result": "$vkq.text", "message": "$err_msg".toString()]
    }

    Map<String, Object> hddt_confirmPaymentFkey_VTT(String provinceCode, String fKey, String pattern, String serial) {
        String urlVTT = ""
        String usernameVTT = username
        String passwordVTT = password
        if (lVTT.contains(provinceCode)) {
            urlVTT = mUrlVTT[provinceCode]
            usernameVTT = mUsernameVTT[provinceCode]
            passwordVTT = mPasswordVTT[provinceCode]
        } else {
            return ["error_code": "-99", "result": "$provinceCode", "message": "Chức năng chưa hỗ trợ tỉnh này"]
        }

        String body = """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
				  <soap:Body>
					<confirmPaymentFkey xmlns="http://tempuri.org/">
					  <lstFkey>$fKey</lstFkey>
					  <userName>$usernameVTT</userName>
					  <userPass>$passwordVTT</userPass>
					  <pattern>$pattern</pattern>
					  <serial>$serial</serial>
					</confirmPaymentFkey>
				  </soap:Body>
				</soap:Envelope>"""

        def env = post(urlVTT + "/" + bizServicePath, ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/confirmPaymentFkey"], body)
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.confirmPaymentFkeyResponse.confirmPaymentFkeyResult
        logger.debug("confirmPaymentFkey response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền thêm khách hàng",
                    "2" : "Chuỗi token không chính xác",
                    "6" : "Không tìm thấy hóa đơn tương ứng chuỗi đưa vào",
                    "7" : "Không phân phối được",
                    "13": "Hóa đơn đã gạch nợ rồi"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code".toString(), "result": "$vkq.text", "message": "$err_msg".toString()]
    }

    // Cập nhật thông tin khách hàng
    Map<String, Object> updateCus(InvCustomer invCus, String convert) {
        def env = post(url + "/" + publicServicePath, ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/UpdateCus"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
				  <soap:Body>
					<UpdateCus xmlns="http://tempuri.org/">
					  <XMLCusData>${invCus.toXml()}</XMLCusData>
					  <username>$username</username>
					  <pass>$password</pass>
					  <convert>$convert</convert>
					</UpdateCus>
				  </soap:Body>
				</soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        String err_code = env.Body.UpdateCusResponse.UpdateCusResult.text()
        def err_msg = "success"
        def err_messages = ["-1": "Tài khoản đăng nhập sai hoặc không có quyền thêm khách hàng",
                            "-2": "Không import được khách hàng vào db",
                            "-3": "Dữ liệu xml đầu vào không đúng quy định"]
        if (err_messages.containsKey(err_code)) {
            err_msg = err_messages.get(err_code)
        }
        return ["error_code": err_messages.containsKey(err_code) ? String.valueOf(Math.abs(Integer.parseInt(err_code)) + 1400) : "1200", "message": err_msg]
    }

    Map<String, Object> confirmPayment(String lstInvToken) {
        def env = post(url + "/" + bizServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/confirmPayment"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
					  <soap:Body>
						<confirmPayment xmlns="http://tempuri.org/">
						  <lstInvToken>$lstInvToken</lstInvToken>
						  <userName>$username</userName>
						  <userPass>$password</userPass>
						</confirmPayment>
					  </soap:Body>
					</soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.confirmPaymentResponse.confirmPaymentResult
        logger.debug("confirmPayment response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Chuỗi token không đúng định dạng",
                    "6" : "Không tìm thấy hóa đơn tương ứng chuỗi đưa vào",
                    "7" : "Không tìm thấy thông tin công ty tương ứng, hoặc lỗi không xác định",
                    "13": "Hóa đơn đã được gạch nợ trước đó"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> unConfirmPayment(String lstInvToken) {
        def env = post(url + "/" + bizServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/unConfirmPayment"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
						  <soap:Body>
							<unConfirmPayment xmlns="http://tempuri.org/">
							  <lstInvToken>$lstInvToken</lstInvToken>
							  <userName>$username</userName>
							  <userPass>$password</userPass>
							</unConfirmPayment>
						  </soap:Body>
						</soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.unConfirmPaymentResponse.unConfirmPaymentResult
        logger.debug("unConfirmPayment response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Chuỗi token không đúng định dạng",
                    "6" : "Không tìm thấy hóa đơn tương ứng chuỗi đưa vào",
                    "7" : "Không tìm thấy thông tin công ty tương ứng, hoặc lỗi không xác định",
                    "13": "Hóa đơn đã được bỏ gạch nợ trước đó"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> unConfirmPaymentFkey(String lstFkey) {
        def env = post(url + "/" + bizServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/UnConfirmPaymentFkey"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
						  <soap:Body>
							<UnConfirmPaymentFkey xmlns="http://tempuri.org/">
							  <lstFkey>$lstFkey</lstFkey>
							  <userName>$username</userName>
							  <userPass>$password</userPass>
							</UnConfirmPaymentFkey>
						  </soap:Body>
						</soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.UnConfirmPaymentFkeyResponse.UnConfirmPaymentFkeyResult
        logger.debug("unConfirmPaymentFkey response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Chuỗi token không đúng định dạng",
                    "6" : "Không tìm thấy hóa đơn tương ứng chuỗi đưa vào",
                    "7" : "Không tìm thấy thông tin công ty tương ứng, hoặc lỗi không xác định",
                    "13": "Hóa đơn đã được bỏ gạch nợ trước đó"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> unConfirmPaymentFkey_VTT(String provinceCode, String lstFkey) {
        String urlVTT = ""
        String usernameVTT = username
        String passwordVTT = password
        if (lVTT.contains(provinceCode)) {
            urlVTT = mUrlVTT[provinceCode]
            usernameVTT = mUsernameVTT[provinceCode]
            passwordVTT = mPasswordVTT[provinceCode]
        } else {
            return ["error_code": "-99", "result": "$provinceCode", "message": "Chức năng chưa hỗ trợ tỉnh này"]
        }

        def env = post(urlVTT + "/" + bizServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/UnConfirmPaymentFkey"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
						  <soap:Body>
							<UnConfirmPaymentFkey xmlns="http://tempuri.org/">
							  <lstFkey>$lstFkey</lstFkey>
							  <userName>$usernameVTT</userName>
							  <userPass>$passwordVTT</userPass>
							</UnConfirmPaymentFkey>
						  </soap:Body>
						</soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.UnConfirmPaymentFkeyResponse.UnConfirmPaymentFkeyResult
        logger.debug("unConfirmPaymentFkey response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Chuỗi token không đúng định dạng",
                    "6" : "Không tìm thấy hóa đơn tương ứng chuỗi đưa vào",
                    "7" : "Không tìm thấy thông tin công ty tương ứng, hoặc lỗi không xác định",
                    "13": "Hóa đơn đã được bỏ gạch nợ trước đó"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> adjustInvoice(String maTinh, String account, String acPass, String xmlInvData, String fkey, String attachFile, int convert) {
        String usernameX = "ccbsservice"
        String passwordX = "fPs3LKWVu@"
        String accountX = "ccbsadmin"
        String acPassX = "fPs3LKWVu@"

        if (xmlInvData != null && !xmlInvData?.isEmpty()) {
            xmlInvData = xmlInvData.replaceAll("<!\\[CDATA\\[", "")
            xmlInvData = xmlInvData.replaceAll("]]>", "")
            xmlInvData = xmlInvData.replaceAll("&", "&amp;")
        }
        def env = post(url + "/" + bizServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/AdjustInvoice"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
						  <soap:Body>
							<AdjustInvoice xmlns="http://tempuri.org/">
							  <Account>$accountX</Account>
							  <ACpass><![CDATA[$acPassX]]></ACpass>
							  <xmlInvData><![CDATA[$xmlInvData]]></xmlInvData>
							  <username>$usernameX</username>
							  <pass>$passwordX</pass>
							  <fkey>$fkey</fkey>
							  <AttachFile>$attachFile</AttachFile>
							  <convert>$convert</convert>
							</AdjustInvoice>
						  </soap:Body>
						</soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.AdjustInvoiceResponse.AdjustInvoiceResult
        logger.debug("AdjustInvoice response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Hóa đơn cần điều chỉnh không tồn tại",
                    "3" : "Dữ liệu xml đầu vào không đúng quy định",
                    "5" : "Không phát hành được hóa đơn",
                    "6" : "Dải hóa đơn cũ đã hết",
                    "7" : "User name không phù hợp, không tìm thấy company tương ứng cho user.",
                    "8" : "Hóa đơn cần điều chỉnh đã bị thay thế. Không thể điều chỉnh được nữa.",
                    "9" : "Trạng thái hóa đơn không được điều chỉnh",
                    "13": "Lỗi trùng fkey",
                    "14": "Lỗi trong quá trình thực hiện cấp số hóa đơn",
                    "15": "Lỗi khi thực hiện Deserialize chuỗi hóa đơn đầu vào",
                    "19": "Pattern truyền vào không giống với hóa đơn cần điều chỉnh",
                    "20": "Dải hóa đơn hết, User/Account không có quyền với Serial/Pattern và serial không phù hợp",
                    "21": "Trùng Fkey truyền vào",
                    "29": "Lỗi chứng thư hết hạn",
                    "30": "Danh sách hóa đơn tồn tại ngày hóa đơn nhỏ hơn ngày hóa đơn đã phát hành",
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> replaceInvoice(String maTinh, String account, String acPass, String xmlInvData, String fkey, String attachFile, int convert) {
        String usernameX = "ccbsservice"
        String passwordX = "fPs3LKWVu@"
        String accountX = "ccbsadmin"
        String acPassX = "fPs3LKWVu@"

        if (xmlInvData != null && !xmlInvData?.isEmpty()) {
            xmlInvData = xmlInvData.replaceAll("<!\\[CDATA\\[", "")
            xmlInvData = xmlInvData.replaceAll("]]>", "")
            xmlInvData = xmlInvData.replaceAll("&", "&amp;")
        }
        def env = post(url + "/" + bizServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/ReplaceInvoice"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
						  <soap:Body>
							<ReplaceInvoice xmlns="http://tempuri.org/">
							  <Account>$accountX</Account>
							  <ACpass><![CDATA[$acPassX]]></ACpass>
							  <xmlInvData><![CDATA[$xmlInvData]]></xmlInvData>
							  <username>$usernameX</username>
							  <pass>$passwordX</pass>
							  <fkey>$fkey</fkey>
							  <AttachFile>$attachFile</AttachFile>
							  <convert>$convert</convert>
							</ReplaceInvoice>
						  </soap:Body>
						</soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.ReplaceInvoiceResponse.ReplaceInvoiceResult
        logger.debug("ReplaceInvoice response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Không tồn tại hóa đơn cần thay thế",
                    "3" : "Dữ liệu xml đầu vào không đúng quy định",
                    "5" : "Có lỗi trong quá trình thay thế hóa đơn",
                    "6" : "Dải hóa đơn cũ đã hết",
                    "7" : "User name không phù hợp, không tìm thấy company tương ứng cho user.",
                    "8" : "Hóa đơn đã được thay thế rồi. Không thể thay thế nữa.",
                    "9" : "Trạng thái hóa đơn không được thay thế",
                    "13": "Lỗi trùng fkey",
                    "14": "Lỗi trong quá trình thực hiện cấp số hóa đơn",
                    "15": "Lỗi khi thực hiện Deserialize chuỗi hóa đơn đầu vào",
                    "19": "Pattern truyền vào không giống với hóa đơn cần điều chỉnh",
                    "20": "Dải hóa đơn hết, User/Account không có quyền với Serial/Pattern và serial không phù hợp",
                    "21": "Trùng Fkey truyền vào",
                    "29": "Lỗi chứng thư hết hạn",
                    "30": "Danh sách hóa đơn tồn tại ngày hóa đơn nhỏ hơn ngày hóa đơn đã phát hành",
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }


    Map<String, Object> confirmPaymentDetailFkey(String lstFkey) {
        def env = post(url + "/" + bizServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/confirmPaymentDetailFkey"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
						  <soap:Body>
							<confirmPaymentDetailFkey xmlns="http://tempuri.org/">
							  <lstFkey>$lstFkey</lstFkey>
							  <userName>$username</userName>
							  <userPass>$password</userPass>
							</confirmPaymentDetailFkey>
						  </soap:Body>
						</soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.confirmPaymentDetailFkeyResponse.confirmPaymentDetailFkeyResult
        logger.debug("ConfirmPaymentDetailFkey response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "6" : "Không tìm thấy hóa đơn tương ứng chuỗi đưa vào",
                    "7" : "Không tìm thấy thông tin công ty tương ứng, hoặc lỗi không xác định",
                    "13": "Hóa đơn đã thanh toán trước đó"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> importAndPublishInv(String maTinh, String account, String acPass, String xmlInvData, String pattern, String serial, int convert) {
        if (maTinh == null || maTinh == "") {
            maTinh = "khdn"
        }
        String accountX = "ccbsadmin"
        String acPassX = "fPs3LKWVu@"
        if (xmlInvData != null && !xmlInvData?.isEmpty()) {
            xmlInvData = xmlInvData.replaceAll("<!\\[CDATA\\[", "")
            xmlInvData = xmlInvData.replaceAll("]]>", "")
            xmlInvData = xmlInvData.replaceAll("&", "&amp;")
        }
        xmlInvData = DUtils.replaceSpecChar(xmlInvData, "CusName")
        xmlInvData = DUtils.replaceSpecChar(xmlInvData, "CusAddress")
        xmlInvData = DUtils.replaceSpecChar(xmlInvData, "ProdName")

        String urlX = "https://testnoibo-tt78admindemo.vnpt-invoice.com.vn"
        String usernameX = "ccbsservice"
        String passwordX = "fPs3LKWVu@"
        String bodyX = """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
						  <soap:Body>
							<ImportAndPublishInv xmlns="http://tempuri.org/">
							  <Account>$accountX</Account>
							  <ACpass><![CDATA[$acPassX]]></ACpass>
							  <xmlInvData><![CDATA[$xmlInvData]]></xmlInvData>
							  <username>$usernameX</username>
							  <password>$passwordX</password>
							  <pattern>$pattern</pattern>
							  <serial>$serial</serial>
							  <convert>$convert</convert>
							</ImportAndPublishInv>
						  </soap:Body>
						</soap:Envelope>"""

        def env = post(urlX + "/" + publicServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/ImportAndPublishInv"], bodyX)
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.ImportAndPublishInvResponse.ImportAndPublishInvResult
        logger.debug("ImportAndPublishInv response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền thêm khách hàng",
                    "3" : "Dữ liệu xml đầu vào không đúng quy định",
                    "5" : "Không phát hành được hóa đơn",
                    "6" : "Dải hóa đơn không đủ số hóa đơn cho lô phát hành",
                    "7" : "Thông tin về Username/pass không hợp lệ",
                    "13": "Lỗi trùng fkey",
                    "20": "Pattern và Serial không phù hợp, hoặc không tồn tại hóa đơn đã đăng kí có sử dụng Pattern và Serial truyền vào.",
                    "21": "Lỗi trùng số hóa đơn",
                    "29": "Lỗi chứng thư hết hạn",
                    "30": "Danh sách hóa đơn tồn tại ngày hóa đơn nhỏ hơn ngày hóa đơn đã phát hành"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                if (err_code == "1") {
                    try {
                        loggingManaged.info("[VnptInvoiceClient] - [importAndPublishInv] : ",
                                new ImmutableMap.Builder<String, Object>().put("url", urlX).put("body", bodyX).build())
                    } catch (Exception eee) {}
                }
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> importAndPublishInv_VTT(String provinceCode, String account, String acPass, String xmlInvData, String pattern, String serial, int convert) {
        String urlVTT = ""
        String usernameVTT = ""
        String passwordVTT = ""
        String accountVTT = ""
        String acPassVTT = ""

        if (lVTT.contains(provinceCode)) {
            urlVTT = mUrlVTT[provinceCode]
            usernameVTT = mUsernameVTT[provinceCode]
            passwordVTT = mPasswordVTT[provinceCode]
            accountVTT = mAccountVTT[provinceCode]
            acPassVTT = mAcpassVTT[provinceCode]
        } else {
            return ["error_code": "-99", "result": "$provinceCode", "message": "Chức năng chưa hỗ trợ tỉnh này"]
        }

        if (xmlInvData != null && !xmlInvData?.isEmpty()) {
            xmlInvData = xmlInvData.replaceAll("<!\\[CDATA\\[", "")
            xmlInvData = xmlInvData.replaceAll("]]>", "")
            xmlInvData = xmlInvData.replaceAll("&", "&amp;")
        }
        def env = post(urlVTT + "/" + publicServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/ImportAndPublishInv"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
						  <soap:Body>
							<ImportAndPublishInv xmlns="http://tempuri.org/">
							  <Account>$accountVTT</Account>
							  <ACpass><![CDATA[$acPassVTT]]></ACpass>
							  <xmlInvData><![CDATA[$xmlInvData]]></xmlInvData>
							  <username>$usernameVTT</username>
							  <password>$passwordVTT</password>
							  <pattern>$pattern</pattern>
							  <serial>$serial</serial>
							  <convert>$convert</convert>
							</ImportAndPublishInv>
						  </soap:Body>
						</soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.ImportAndPublishInvResponse.ImportAndPublishInvResult
        logger.debug("ImportAndPublishInv response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền thêm khách hàng",
                    "3" : "Dữ liệu xml đầu vào không đúng quy định",
                    "5" : "Không phát hành được hóa đơn",
                    "6" : "Dải hóa đơn không đủ số hóa đơn cho lô phát hành",
                    "7" : "Thông tin về Username/pass không hợp lệ",
                    "13": "Lỗi trùng fkey",
                    "20": "Pattern và Serial không phù hợp, hoặc không tồn tại hóa đơn đã đăng kí có sử dụng Pattern và Serial truyền vào.",
                    "21": "Lỗi trùng số hóa đơn",
                    "29": "Lỗi chứng thư hết hạn",
                    "30": "Danh sách hóa đơn tồn tại ngày hóa đơn nhỏ hơn ngày hóa đơn đã phát hành"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> cancelInv(String maTinh, String account, String acPass, String fkey) {
        String accountX = "ccbsadmin"
        String acPassX = "fPs3LKWVu@"
        def env = post(url + "/" + bizServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/cancelInv"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
						  <soap:Body>
							<cancelInv xmlns="http://tempuri.org/">
							  <Account>$accountX</Account>
							  <ACpass><![CDATA[$acPassX]]></ACpass>
							  <fkey>$fkey</fkey>
							  <userName>$username</userName>
							  <userPass>$password</userPass>
							</cancelInv>
						  </soap:Body>
						</soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.cancelInvResponse.cancelInvResult
        logger.debug("CancelInv response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Không tìm thấy hóa đơn",
                    "6" : "Lỗi không xác định",
                    "7" : "Không tìm thấy thông tin công ty tương ứng, hoặc lỗi không xác định",
                    "8" : "Hóa đơn đã bị điều chỉnh / hủy / hóa đơn mới tạo không thể hủy được",
                    "9" : "Hóa đơn đã thanh toán, không cho phép hủy",
                    "20": "Dải hóa đơn hết, User/Account không có quyền với Serial/Pattern và serial không phù hợp"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> cancelInvNoPay(String maTinh, String account, String acPass, String fkey) {
        String accountX = "ccbsadmin"
        String acPassX = "fPs3LKWVu@"
        def env = post(url + "/" + bizServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/cancelInvNoPay"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
						  <soap:Body>
							<cancelInvNoPay xmlns="http://tempuri.org/">
							  <Account>$accountX</Account>
							  <ACpass><![CDATA[$acPassX]]></ACpass>
							  <fkey>$fkey</fkey>
							  <userName>$username</userName>
							  <userPass>$password</userPass>
							</cancelInvNoPay>
						  </soap:Body>
						</soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.cancelInvNoPayResponse.cancelInvNoPayResult
        logger.debug("cancelInvNoPay response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Không tìm thấy hóa đơn",
                    "6" : "Lỗi không xác định",
                    "7" : "Không tìm thấy thông tin công ty tương ứng, hoặc lỗi không xác định",
                    "8" : "Hóa đơn đã bị điều chỉnh / hủy / hóa đơn mới tạo không thể hủy được",
                    "9" : "Hóa đơn đã thanh toán, không cho phép hủy",
                    "20": "Dải hóa đơn hết, User/Account không có quyền với Serial/Pattern và serial không phù hợp"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> cancelInv_VTT(String provinceCode, String account, String acPass, String fkey) {
        String urlVTT = ""
        String usernameVTT = ""
        String passwordVTT = ""
        String accountVTT = ""
        String acPassVTT = ""
        if (lVTT.contains(provinceCode)) {
            urlVTT = mUrlVTT[provinceCode]
            usernameVTT = mUsernameVTT[provinceCode]
            passwordVTT = mPasswordVTT[provinceCode]
            accountVTT = mAccountVTT[provinceCode]
            acPassVTT = mAcpassVTT[provinceCode]
        } else {
            return ["error_code": "-99", "result": "$provinceCode", "message": "Chức năng chưa hỗ trợ tỉnh này"]
        }

        def env = post(urlVTT + "/" + bizServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/cancelInv"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
						  <soap:Body>
							<cancelInv xmlns="http://tempuri.org/">
							  <Account>$accountVTT</Account>
							  <ACpass><![CDATA[$acPassVTT]]></ACpass>
							  <fkey>$fkey</fkey>
							  <userName>$usernameVTT</userName>
							  <userPass>$passwordVTT</userPass>
							</cancelInv>
						  </soap:Body>
						</soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.cancelInvResponse.cancelInvResult
        logger.debug("CancelInv response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Không tìm thấy hóa đơn",
                    "6" : "Lỗi không xác định",
                    "7" : "Không tìm thấy thông tin công ty tương ứng, hoặc lỗi không xác định",
                    "8" : "Hóa đơn đã bị điều chỉnh / hủy / hóa đơn mới tạo không thể hủy được",
                    "9" : "Hóa đơn đã thanh toán, không cho phép hủy",
                    "20": "Dải hóa đơn hết, User/Account không có quyền với Serial/Pattern và serial không phù hợp"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> getSerialByPattern(String pattern) {
        def env = post(url + "/" + bizServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/getSerialByPattern"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
						  <soap:Body>
							<getSerialByPattern xmlns="http://tempuri.org/">
							  <userName>$username</userName>
							  <userPass>$password</userPass>
							  <pattern>$pattern</pattern>
							</getSerialByPattern>
						  </soap:Body>
						</soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.getSerialByPatternResponse.getSerialByPatternResult
        logger.debug("getSerialByPattern response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Không tìm thấy hóa đơn",
                    "6" : "Lỗi không xác định",
                    "7" : "Không tìm thấy thông tin công ty tương ứng, hoặc lỗi không xác định",
                    "8" : "Hóa đơn đã bị điều chỉnh / hủy / hóa đơn mới tạo không thể hủy được",
                    "9" : "Hóa đơn đã thanh toán, không cho phép hủy",
                    "20": "Dải hóa đơn hết, User/Account không có quyền với Serial/Pattern và serial không phù hợp"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> deliverInvFkey(String lstFkey) {
        def env = post(url + "/" + bizServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/deliverInvFkey"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
						  <soap:Body>
							<deliverInvFkey xmlns="http://tempuri.org/">
							  <lstFkey>$lstFkey</lstFkey>
							  <userName>$username</userName>
							  <userPass>$password</userPass>
							</deliverInvFkey>
						  </soap:Body>
						</soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.deliverInvFkeyResponse.deliverInvFkeyResult
        logger.debug("deliverInvFkey response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "6" : "Không tìm thấy hóa đơn tương ứng chuỗi đưa vào",
                    "7" : "Không tìm thấy thông tin công ty tương ứng, hoặc lỗi không xác định",
                    "13": "Hóa đơn đã chuyển trước đó"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> listInvByCusFkey(String key, String fromDate, String toDate) {
        def env = post(url + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/listInvByCusFkey"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
						  <soap:Body>
							<listInvByCusFkey xmlns="http://tempuri.org/">
							  <key>$key</key>
							  <fromDate>$fromDate</fromDate>
							  <toDate>$toDate</toDate>
							  <userName>$username</userName>
							  <userPass>$password</userPass>
							</listInvByCusFkey>
						  </soap:Body>
						</soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.listInvByCusFkeyResponse.listInvByCusFkeyResult
        logger.debug("listInvByCusFkey response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1": "Tài khoản đăng nhập sai hoặc không có quyền",
                    "4": "Công ty chưa được đăng kí mẫu hóa đơn nào",
                    "7": "Không tìm thấy thông tin công ty",
                    "" : "Có lỗi xảy ra"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> listInvByCusFkey_VTT(String provinceCode, String key, String fromDate, String toDate) {
        String urlVTT = ""
        String usernameVTT = username
        String passwordVTT = password
        if (lVTT.contains(provinceCode)) {
            urlVTT = mUrlVTT[provinceCode]
            usernameVTT = mUsernameVTT[provinceCode]
            passwordVTT = mPasswordVTT[provinceCode]
        } else {
            return ["error_code": "-99", "result": "$provinceCode", "message": "Chức năng chưa hỗ trợ tỉnh này"]
        }

        def env = post(urlVTT + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/listInvByCusFkey"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
						  <soap:Body>
							<listInvByCusFkey xmlns="http://tempuri.org/">
							  <key>$key</key>
							  <fromDate>$fromDate</fromDate>
							  <toDate>$toDate</toDate>
							  <userName>$usernameVTT</userName>
							  <userPass>$passwordVTT</userPass>
							</listInvByCusFkey>
						  </soap:Body>
						</soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.listInvByCusFkeyResponse.listInvByCusFkeyResult
        logger.debug("listInvByCusFkey response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1": "Tài khoản đăng nhập sai hoặc không có quyền",
                    "4": "Công ty chưa được đăng kí mẫu hóa đơn nào",
                    "7": "Không tìm thấy thông tin công ty",
                    "" : "Có lỗi xảy ra"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> listInvByCus(String maTinh, String cusCode, String fromDate, String toDate) {
        if (maTinh == null || maTinh == "") {
            maTinh = "khdn"
        }
        String urlX = "https://testnoibo-tt78admindemo.vnpt-invoice.com.vn"
        String usernameX = "ccbsservice"
        String passwordX = "fPs3LKWVu@"
        def env = post(urlX + "/" + extVNPTService,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/listInvByCusCode"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
						  <soap:Body>
							<listInvByCusCode xmlns="http://tempuri.org/">
							  <cuscode>$cusCode</cuscode>
							  <fromDate>$fromDate</fromDate>
							  <toDate>$toDate</toDate>
							  <userName>$usernameX</userName>
							  <userPass>$passwordX</userPass>
							</listInvByCusCode>
						  </soap:Body>
						</soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.listInvByCusCodeResponse.listInvByCusCodeResult
        logger.debug("listInvByCus response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1": "Tài khoản đăng nhập sai hoặc không có quyền",
                    "3": "Không tồn tài khách hàng tương ứng với cusCode",
                    "4": "Công ty chưa được đăng kí mẫu hóa đơn nào",
                    "7": "Không tìm thấy thông tin công ty",
                    "" : "Có lỗi xảy ra"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> listInvByCus_VTT(String provinceCode, String cusCode, String fromDate, String toDate) {
        String urlVTT = ""
        String usernameVTT = username
        String passwordVTT = password
        if (lVTT.contains(provinceCode)) {
            urlVTT = mUrlVTT[provinceCode]
            usernameVTT = mUsernameVTT[provinceCode]
            passwordVTT = mPasswordVTT[provinceCode]
        } else {
            return ["error_code": "-99", "result": "$provinceCode", "message": "Chức năng chưa hỗ trợ tỉnh này"]
        }

        def env = post(urlVTT + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/listInvByCus"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
						  <soap:Body>
							<listInvByCus xmlns="http://tempuri.org/">
							  <cusCode>$cusCode</cusCode>
							  <fromDate>$fromDate</fromDate>
							  <toDate>$toDate</toDate>
							  <userName>$usernameVTT</userName>
							  <userPass>$passwordVTT</userPass>
							</listInvByCus>
						  </soap:Body>
						</soap:Envelope>""")
        def kq = env.text()
        String err = ""
        if (kq == "ERR:1")
            return ["error_code": "1", "result": "$kq", "message": "Tài khoản đăng nhập sai"]
        else if (kq == "ERR:3")
            return ["error_code": "3", "result": "$kq", "message": "Không tồn tài khách hàng tương ứng với cusCode"]
        else if (kq == "ERR:4")
            return ["error_code": "4", "result": "$kq", "message": "Công ty chưa được đăng kí mẫu hóa đơn nào"]
        else
            return ["error_code": "0", "result": "$kq", "message": "$kq"]
    }

    Map<String, Object> listInvByCus_v2(String cusCode, String fromDate, String toDate, String pattern) {
        def env = post(url + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/listInvByCusPattern"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
						  <soap:Body>
							<listInvByCusPattern xmlns="http://tempuri.org/">
							  <cusCode>$cusCode</cusCode>
							  <fromDate>$fromDate</fromDate>
							  <toDate>$toDate</toDate>
							  <userName>$username</userName>
							  <userPass>$password</userPass>
                              <pattern>$pattern</pattern>
							</listInvByCusPattern>
						  </soap:Body>
						</soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.listInvByCusPatternResponse.listInvByCusPatternResult
        logger.debug("listInvByCusPattern response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1": "Tài khoản đăng nhập sai hoặc không có quyền",
                    "3": "Không tồn tài khách hàng tương ứng với cusCode",
                    "4": "Công ty chưa được đăng kí mẫu hóa đơn nào",
                    "7": "Không tìm thấy thông tin công ty",
                    "" : "Có lỗi xảy ra"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> listInvByCus_v2_VTT(String provinceCode, String cusCode, String fromDate, String toDate, String pattern) {
        String urlVTT = ""
        String usernameVTT = username
        String passwordVTT = password
        if (lVTT.contains(provinceCode)) {
            urlVTT = mUrlVTT[provinceCode]
            usernameVTT = mUsernameVTT[provinceCode]
            passwordVTT = mPasswordVTT[provinceCode]
        } else {
            return ["error_code": "-99", "result": "$provinceCode", "message": "Chức năng chưa hỗ trợ tỉnh này"]
        }

        def env = post(urlVTT + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/listInvByCusPattern"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
						  <soap:Body>
							<listInvByCusPattern xmlns="http://tempuri.org/">
							  <cusCode>$cusCode</cusCode>
							  <fromDate>$fromDate</fromDate>
							  <toDate>$toDate</toDate>
							  <userName>$usernameVTT</userName>
							  <userPass>$passwordVTT</userPass>
                              <pattern>$pattern</pattern>
							</listInvByCusPattern>
						  </soap:Body>
						</soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.listInvByCusPatternResponse.listInvByCusPatternResult
        logger.debug("listInvByCusPattern response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1": "Tài khoản đăng nhập sai hoặc không có quyền",
                    "3": "Không tồn tài khách hàng tương ứng với cusCode",
                    "4": "Công ty chưa được đăng kí mẫu hóa đơn nào",
                    "7": "Không tìm thấy thông tin công ty",
                    "" : "Có lỗi xảy ra"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> getInvViewNoPay(String invToken) {
        def env = post(url + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/getInvViewNoPay"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <getInvViewNoPay xmlns="http://tempuri.org/">
                              <invToken>$invToken</invToken>
                              <userName>$username</userName>
                              <userPass>$password</userPass>
                            </getInvViewNoPay>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.getInvViewNoPayResponse.getInvViewNoPayResult
        logger.debug("getInvViewNoPay response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Chuỗi token không đúng định dạng",
                    "4" : "Công ty chưa được đăng kí mẫu hóa đơn nào",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "Không tìm thấy thông tin công ty",
                    "11": "Hóa đơn chưa thanh toán nên không xem được",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> getInvViewNoPay_VTT(String provinceCode, String invToken) {
        String urlVTT = ""
        String usernameVTT = username
        String passwordVTT = password
        if (lVTT.contains(provinceCode)) {
            urlVTT = mUrlVTT[provinceCode]
            usernameVTT = mUsernameVTT[provinceCode]
            passwordVTT = mPasswordVTT[provinceCode]
        } else {
            return ["error_code": "-99", "result": "$provinceCode", "message": "Chức năng chưa hỗ trợ tỉnh này"]
        }

        def env = post(urlVTT + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/getInvViewNoPay"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <getInvViewNoPay xmlns="http://tempuri.org/">
                              <invToken>$invToken</invToken>
                              <userName>$usernameVTT</userName>
                              <userPass>$passwordVTT</userPass>
                            </getInvViewNoPay>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.getInvViewNoPayResponse.getInvViewNoPayResult
        logger.debug("getInvViewNoPay response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Chuỗi token không đúng định dạng",
                    "4" : "Công ty chưa được đăng kí mẫu hóa đơn nào",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "Không tìm thấy thông tin công ty",
                    "11": "Hóa đơn chưa thanh toán nên không xem được",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> getInvViewFkeyNoPay(String fkey) {
        def env = post(url + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/getInvViewFkeyNoPay"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <getInvViewFkeyNoPay xmlns="http://tempuri.org/">
                              <fkey>$fkey</fkey>
                              <userName>$username</userName>
                              <userPass>$password</userPass>
                            </getInvViewFkeyNoPay>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.getInvViewFkeyNoPayResponse.getInvViewFkeyNoPayResult
        logger.debug("getInvViewFkeyNoPay response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "4" : "Công ty chưa được đăng kí mẫu hóa đơn nào",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "Không tìm thấy thông tin công ty",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> getInvViewFkeyNoPay_VTT(String provinceCode, String fkey) {
        String urlVTT = ""
        String usernameVTT = username
        String passwordVTT = password
        if (lVTT.contains(provinceCode)) {
            urlVTT = mUrlVTT[provinceCode]
            usernameVTT = mUsernameVTT[provinceCode]
            passwordVTT = mPasswordVTT[provinceCode]
        } else {
            return ["error_code": "-99", "result": "$provinceCode", "message": "Chức năng chưa hỗ trợ tỉnh này"]
        }

        def env = post(urlVTT + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/getInvViewFkeyNoPay"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <getInvViewFkeyNoPay xmlns="http://tempuri.org/">
                              <fkey>$fkey</fkey>
                              <userName>$usernameVTT</userName>
                              <userPass>$passwordVTT</userPass>
                            </getInvViewFkeyNoPay>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.getInvViewFkeyNoPayResponse.getInvViewFkeyNoPayResult
        logger.debug("getInvViewFkeyNoPay response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "4" : "Công ty chưa được đăng kí mẫu hóa đơn nào",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "Không tìm thấy thông tin công ty",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> getInvView(String invToken) {
        def env = post(url + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/getInvView"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <getInvView xmlns="http://tempuri.org/">
                              <invToken>$invToken</invToken>
                              <userName>$username</userName>
                              <userPass>$password</userPass>
                            </getInvView>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.getInvViewResponse.getInvViewResult
        logger.debug("getInvView response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Chuỗi token không đúng định dạng",
                    "4" : "Công ty chưa được đăng kí mẫu hóa đơn nào",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "Không tìm thấy thông tin công ty",
                    "11": "Hóa đơn chưa thanh toán nên không xem được",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> searchInv(String cusCode, String pattern, String serial, String fromDate, String toDate, int invNumber, int invStatus, int page, int cussignStatus, int payment) {
        def env = post(url + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/SearchInv"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <SearchInv xmlns="http://tempuri.org/">
                              <cusCode>$cusCode</cusCode>
                              <pattern>$pattern</pattern>
                              <serial>$serial</serial>
                              <fromDate>$fromDate</fromDate>
                              <toDate>$toDate</toDate>
                              <invNumber>$invNumber</invNumber>
                              <invStatus>$invStatus</invStatus>
                              <page>$page</page>
                              <cussignStatus>$cussignStatus</cussignStatus>
                              <payment>$payment</payment>
                              <userName>$username</userName>
                              <userPass>$password</userPass>
                            </SearchInv>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.SearchInvResponse.SearchInvResult
        logger.debug("searchInv response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Chuỗi token không đúng định dạng",
                    "4" : "Công ty chưa được đăng kí mẫu hóa đơn nào",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "Không tìm thấy thông tin công ty",
                    "11": "Hóa đơn chưa thanh toán nên không xem được",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> downloadInvFkeyNoPay(String fkey) {
        def env = post(url + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/downloadInvFkeyNoPay"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <downloadInvFkeyNoPay xmlns="http://tempuri.org/">
                              <fkey>$fkey</fkey>
                              <userName>$username</userName>
                              <userPass>$password</userPass>
                            </downloadInvFkeyNoPay>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.downloadInvFkeyNoPayResponse.downloadInvFkeyNoPayResult
        logger.debug("downloadInvFkeyNoPay response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền ServiceRole",
                    "2" : "Chuỗi token không đúng định dạng",
                    "4" : "Không tìm thấy dải thông báo phát hành",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "User name không phù hợp, không tìm thấy thông tin công ty tương ứng cho user.",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> downloadInvFkeyNoPay_VTT(String provinceCode, String fkey) {
        String urlVTT = ""
        String usernameVTT = username
        String passwordVTT = password
        if (lVTT.contains(provinceCode)) {
            urlVTT = mUrlVTT[provinceCode]
            usernameVTT = mUsernameVTT[provinceCode]
            passwordVTT = mPasswordVTT[provinceCode]
        } else {
            return ["error_code": "-99", "result": "$provinceCode", "message": "Chức năng chưa hỗ trợ tỉnh này"]
        }

        def env = post(urlVTT + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/downloadInvFkeyNoPay"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <downloadInvFkeyNoPay xmlns="http://tempuri.org/">
                              <fkey>$fkey</fkey>
                              <userName>$usernameVTT</userName>
                              <userPass>$passwordVTT</userPass>
                            </downloadInvFkeyNoPay>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.downloadInvFkeyNoPayResponse.downloadInvFkeyNoPayResult
        logger.debug("downloadInvFkeyNoPay response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền ServiceRole",
                    "2" : "Chuỗi token không đúng định dạng",
                    "4" : "Không tìm thấy dải thông báo phát hành",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "User name không phù hợp, không tìm thấy thông tin công ty tương ứng cho user.",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> downloadInv(String invToken) {
        def env = post(url + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/downloadInv"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <downloadInv xmlns="http://tempuri.org/">
                              <invToken>$invToken</invToken>
                              <userName>$username</userName>
                              <userPass>$password</userPass>
                            </downloadInv>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.downloadInvResponse.downloadInvResult
        logger.debug("downloadInv response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền ServiceRole",
                    "2" : "Chuỗi token không đúng định dạng",
                    "4" : "Không tìm thấy Pattern",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "User name không phù hợp, không tìm thấy thông tin công ty tương ứng cho user.",
                    "11": "Hóa đơn chưa thanh toán nên không xem được",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> downloadInvNoPay(String invToken) {
        def env = post(url + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/downloadInvNoPay"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <downloadInvNoPay xmlns="http://tempuri.org/">
                              <invToken>$invToken</invToken>
                              <userName>$username</userName>
                              <userPass>$password</userPass>
                            </downloadInvNoPay>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.downloadInvNoPayResponse.downloadInvNoPayResult
        logger.debug("downloadInvNoPay response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (!retInfo[0].startsWith("ERR")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền ServiceRole",
                    "2" : "Chuỗi token không đúng định dạng",
                    "4" : "Không tìm thấy Pattern",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "User name không phù hợp, không tìm thấy thông tin công ty tương ứng cho user.",
                    "11": "Hóa đơn chưa thanh toán nên không xem được",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> downloadInvNoPay_VTT(String provinceCode, String invToken) {
        String urlVTT = ""
        String usernameVTT = username
        String passwordVTT = password
        if (lVTT.contains(provinceCode)) {
            urlVTT = mUrlVTT[provinceCode]
            usernameVTT = mUsernameVTT[provinceCode]
            passwordVTT = mPasswordVTT[provinceCode]
        } else {
            return ["error_code": "-99", "result": "$provinceCode", "message": "Chức năng chưa hỗ trợ tỉnh này"]
        }

        def env = post(urlVTT + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/downloadInvNoPay"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <downloadInvNoPay xmlns="http://tempuri.org/">
                              <invToken>$invToken</invToken>
                              <userName>$usernameVTT</userName>
                              <userPass>$passwordVTT</userPass>
                            </downloadInvNoPay>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.downloadInvNoPayResponse.downloadInvNoPayResult
        logger.debug("downloadInvNoPay response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (!retInfo[0].startsWith("ERR")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền ServiceRole",
                    "2" : "Chuỗi token không đúng định dạng",
                    "4" : "Không tìm thấy Pattern",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "User name không phù hợp, không tìm thấy thông tin công ty tương ứng cho user.",
                    "11": "Hóa đơn chưa thanh toán nên không xem được",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> downloadInvPDF(String token) {
        def env = post(url + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/downloadInvPDF"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <downloadInvPDF xmlns="http://tempuri.org/">
                              <token>$token</token>
                              <userName>$username</userName>
                              <userPass>$password</userPass>
                            </downloadInvPDF>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.downloadInvPDFResponse.downloadInvPDFResult
        logger.debug("downloadInvPDF response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền ServiceRole",
                    "2" : "Chuỗi token không đúng định dạng",
                    "4" : "Không tìm thấy dải thông báo phát hành",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "User name không phù hợp, không tìm thấy thông tin công ty tương ứng cho user.",
                    "11": "Hóa đơn chưa thanh toán nên không xem được",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> downloadInvPDFNoPay(String token) {
        def env = post(url + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/downloadInvPDFNoPay"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <downloadInvPDFNoPay xmlns="http://tempuri.org/">
                              <token>$token</token>
                              <userName>$username</userName>
                              <userPass>$password</userPass>
                            </downloadInvPDFNoPay>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.downloadInvPDFNoPayResponse.downloadInvPDFNoPayResult
        logger.debug("downloadInvPDFNoPay response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền ServiceRole",
                    "2" : "Chuỗi token không đúng định dạng",
                    "4" : "Không tìm thấy dải thông báo phát hành",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "User name không phù hợp, không tìm thấy thông tin công ty tương ứng cho user.",
                    "11": "Hóa đơn chưa thanh toán nên không xem được",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> downloadInvPDFNoPay_VTT(String provinceCode, String token) {
        String urlVTT = ""
        String usernameVTT = username
        String passwordVTT = password
        if (lVTT.contains(provinceCode)) {
            urlVTT = mUrlVTT[provinceCode]
            usernameVTT = mUsernameVTT[provinceCode]
            passwordVTT = mPasswordVTT[provinceCode]
        } else {
            return ["error_code": "-99", "result": "$provinceCode", "message": "Chức năng chưa hỗ trợ tỉnh này"]
        }

        def env = post(urlVTT + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/downloadInvPDFNoPay"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <downloadInvPDFNoPay xmlns="http://tempuri.org/">
                              <token>$token</token>
                              <userName>$usernameVTT</userName>
                              <userPass>$passwordVTT</userPass>
                            </downloadInvPDFNoPay>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.downloadInvPDFNoPayResponse.downloadInvPDFNoPayResult
        logger.debug("downloadInvPDFNoPay response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền ServiceRole",
                    "2" : "Chuỗi token không đúng định dạng",
                    "4" : "Không tìm thấy dải thông báo phát hành",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "User name không phù hợp, không tìm thấy thông tin công ty tương ứng cho user.",
                    "11": "Hóa đơn chưa thanh toán nên không xem được",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> convertForVerify(String invToken) {
        def env = post(url + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/convertForVerify"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <convertForVerify xmlns="http://tempuri.org/">
                              <invToken>$invToken</invToken>
                              <userName>$username</userName>
                              <userPass>$password</userPass>
                            </convertForVerify>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.convertForVerifyResponse.convertForVerifyResult
        logger.debug("convertForVerify response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Chuỗi token không đúng định dạng",
                    "5" : "Có lỗi xảy ra",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "User name không phù hợp, không tìm thấy company tương ứng cho user.",
                    "8" : "Hóa đơn đã được chuyển đổi",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> convertForStore(String invToken) {
        def env = post(url + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/convertForStore"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <convertForStore xmlns="http://tempuri.org/">
                              <invToken>$invToken</invToken>
                              <userName>$username</userName>
                              <userPass>$password</userPass>
                            </convertForStore>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.convertForStoreResponse.convertForStoreResult
        logger.debug("convertForStore response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Chuỗi token không chính xác",
                    "5" : "Có lỗi xảy ra",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "Không tìm thấy công ty",
                    "8" : "Hóa đơn đã chuyển đổi",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> convertForStoreFkey(String fkey) {
        def env = post(url + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/convertForStoreFkey"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <convertForStoreFkey xmlns="http://tempuri.org/">
                              <fkey>$fkey</fkey>
                              <userName>$username</userName>
                              <userPass>$password</userPass>
                            </convertForStoreFkey>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.convertForStoreFkeyResponse.convertForStoreFkeyResult
        logger.debug("convertForStoreFkey response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Chuỗi token không chính xác",
                    "5" : "Có lỗi xảy ra",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "Không tìm thấy công ty",
                    "8" : "Hóa đơn đã chuyển đổi",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> convertForStoreFkey_VTT(String provinceCode, String fkey) {
        String urlVTT = ""
        String usernameVTT = username
        String passwordVTT = password
        if (lVTT.contains(provinceCode)) {
            urlVTT = mUrlVTT[provinceCode]
            usernameVTT = mUsernameVTT[provinceCode]
            passwordVTT = mPasswordVTT[provinceCode]
        } else {
            return ["error_code": "-99", "result": "$provinceCode", "message": "Chức năng chưa hỗ trợ tỉnh này"]
        }

        def env = post(urlVTT + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/convertForStoreFkey"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <convertForStoreFkey xmlns="http://tempuri.org/">
                              <fkey>$fkey</fkey>
                              <userName>$usernameVTT</userName>
                              <userPass>$passwordVTT</userPass>
                            </convertForStoreFkey>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.convertForStoreFkeyResponse.convertForStoreFkeyResult
        logger.debug("convertForStoreFkey response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Chuỗi token không chính xác",
                    "5" : "Có lỗi xảy ra",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "Không tìm thấy công ty",
                    "8" : "Hóa đơn đã chuyển đổi",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> updateCus(String XMLCusData, int convert) {
//        if (XMLCusData.startsWith("<Customers>")) {
//            XMLCusData = XMLCusData.replaceAll("<!\\[CDATA\\[", "")
//            XMLCusData = XMLCusData.replaceAll("]]>", "")
//            def parsed = new XmlSlurper().parseText(XMLCusData)
//            def map = ['data': parsed.'**'.findAll { it.name() == 'Customer' }.collect { element ->
//                element.children().breadthFirst()*.name()
//                        .findAll { !element."$it".children().size() }
//                        .collectEntries { [(element."$it".name()): element."$it".text()] }
//            }]
//            def data = map.data.first()
//            String Name = data.Name
//            String Code = data.Code
//            String TaxCode = data.TaxCode
//            String Address = data.Address
//            String BankAccountName = data.BankAccountName
//            String BankName = data.BankName
//            String BankNumber = data.BankNumber
//            String Email = data.Email
//            String Fax = data.Fax
//            String Phone = data.Phone
//            String CusType = data.CusType
//            InvCustomer customer = new InvCustomer()
//            customer.setTen_kh(Name)
//            customer.setBank_acc_name(BankAccountName)
//            customer.setBank_name(BankName)
//            customer.setDiachi_ct(Address)
//            customer.setDienthoai(Phone)
//            customer.setMa_gd(Code)
//            customer.setMst(TaxCode)
//            customer.setSo_tk(BankNumber)
//            customer.setVemail(Email)
//            customer.setVloaikh_id(CusType)
//            customer.setVso_fax(Fax)
//            XMLCusData = customer.toXml()
//
//        }

        if (XMLCusData != null && !XMLCusData?.isEmpty()) {
            XMLCusData = XMLCusData.replaceAll("<!\\[CDATA\\[", "")
            XMLCusData = XMLCusData.replaceAll("]]>", "")
            XMLCusData = XMLCusData.replaceAll("&", "&amp;")
            if (XMLCusData.contains("]]>")) {
                XMLCusData = XMLCusData.replaceAll("]]>", "")
            }
            if (XMLCusData.contains("]]")) {
                XMLCusData = XMLCusData.replaceAll("]]", "")
            }
        }
        XMLCusData = DUtils.replaceSpecChar(XMLCusData, "Name")
        XMLCusData = DUtils.replaceSpecChar(XMLCusData, "Address")

        String body = """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <UpdateCus xmlns="http://tempuri.org/">
                             <XMLCusData><![CDATA[$XMLCusData]]></XMLCusData>
                              <username>$username</username>
                              <pass>$password</pass>
                              <convert>$convert</convert>
                            </UpdateCus>
                          </soap:Body>
                        </soap:Envelope>"""
        def env = post(url + "/" + publicServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/UpdateCus"],
                body)
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.UpdateCusResponse.UpdateCusResult
        logger.debug("UpdateCus response: $vkq")
        String err_code = vkq?.text()
        def err_msg = ""
        if (err_code?.toInteger() > 0) {
            err_code = "0"
            err_msg = "success"
        } else {
            def err_messages = [
                    "-1": "Tài khoản đăng nhập sai hoặc không có quyền thêm khách hàng",
                    "-2": "Không import được khách hàng vào db",
                    "-3": "Dữ liệu xml đầu vào không đúng quy định"
            ]
            if (err_messages.containsKey(err_code)) {
                err_msg = err_messages.get(err_code)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> downloadInvPDFFkeyNoPay(String fkey) {
        def env = post(url + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/downloadInvPDFFkeyNoPay"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <downloadInvPDFFkeyNoPay xmlns="http://tempuri.org/">
                              <fkey>$fkey</fkey>
                              <userName>$username</userName>
                              <userPass>$password</userPass>
                            </downloadInvPDFFkeyNoPay>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.downloadInvPDFFkeyNoPayResponse.downloadInvPDFFkeyNoPayResult
        logger.debug("downloadInvPDFFkeyNoPay response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền ServiceRole",
                    "2" : "Chuỗi token không đúng định dạng",
                    "4" : "Không tìm thấy dải thông báo phát hành",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "User name không phù hợp, không tìm thấy thông tin công ty tương ứng cho user.",
                    "11": "Hóa đơn chưa thanh toán nên không xem được",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> downloadInvPDFFkeyNoPay_VTT(String provinceCode, String fkey) {
        String urlVTT = ""
        String usernameVTT = username
        String passwordVTT = password
        if (lVTT.contains(provinceCode)) {
            urlVTT = mUrlVTT[provinceCode]
            usernameVTT = mUsernameVTT[provinceCode]
            passwordVTT = mPasswordVTT[provinceCode]
        } else {
            return ["error_code": "-99", "result": "$provinceCode", "message": "Chức năng chưa hỗ trợ tỉnh này"]
        }

        def env = post(urlVTT + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/downloadInvPDFFkeyNoPay"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <downloadInvPDFFkeyNoPay xmlns="http://tempuri.org/">
                              <fkey>$fkey</fkey>
                              <userName>$usernameVTT</userName>
                              <userPass>$passwordVTT</userPass>
                            </downloadInvPDFFkeyNoPay>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.downloadInvPDFFkeyNoPayResponse.downloadInvPDFFkeyNoPayResult
        logger.debug("downloadInvPDFFkeyNoPay response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền ServiceRole",
                    "2" : "Chuỗi token không đúng định dạng",
                    "4" : "Không tìm thấy dải thông báo phát hành",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "User name không phù hợp, không tìm thấy thông tin công ty tương ứng cho user.",
                    "11": "Hóa đơn chưa thanh toán nên không xem được",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> convertForVerifyFkey(String fkey) {
        def env = post(url + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/convertForVerifyFkey"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <convertForVerifyFkey xmlns="http://tempuri.org/">
                              <fkey>$fkey</fkey>
                              <userName>$username</userName>
                              <userPass>$password</userPass>
                            </convertForVerifyFkey>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.convertForVerifyFkeyResponse.convertForVerifyFkeyResult
        logger.debug("convertForVerifyFkey response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Chuỗi token không đúng định dạng",
                    "5" : "Có lỗi xảy ra",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "User name không phù hợp, không tìm thấy company tương ứng cho user.",
                    "8" : "Hóa đơn đã được chuyển đổi",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> convertForVerifyFkey_VTT(String provinceCode, String fkey) {
        String urlVTT = ""
        String usernameVTT = username
        String passwordVTT = password
        if (lVTT.contains(provinceCode)) {
            urlVTT = mUrlVTT[provinceCode]
            usernameVTT = mUsernameVTT[provinceCode]
            passwordVTT = mPasswordVTT[provinceCode]
        } else {
            return ["error_code": "-99", "result": "$provinceCode", "message": "Chức năng chưa hỗ trợ tỉnh này"]
        }

        def env = post(urlVTT + "/" + portalServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/convertForVerifyFkey"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <convertForVerifyFkey xmlns="http://tempuri.org/">
                              <fkey>$fkey</fkey>
                              <userName>$usernameVTT</userName>
                              <userPass>$passwordVTT</userPass>
                            </convertForVerifyFkey>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.convertForVerifyFkeyResponse.convertForVerifyFkeyResult
        logger.debug("convertForVerifyFkey response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else if (retInfo?.size() > 1) {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Chuỗi token không đúng định dạng",
                    "5" : "Có lỗi xảy ra",
                    "6" : "Không tìm thấy hóa đơn",
                    "7" : "User name không phù hợp, không tìm thấy company tương ứng cho user.",
                    "8" : "Hóa đơn đã được chuyển đổi",
                    "12": "Hoá đơn có mã chưa được thuế chấp nhận",
                    "13": "Hoá đơn không mã chưa được thuế chấp nhận"
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        } else {
            err_code = "0"
            err_msg = "success"
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> adjustInvoiceAction(String maTinh, String account, String acPass, String xmlInvData, String fkey, String attachFile, int convert, String pattern, String serial) {
        if (maTinh == null || maTinh == "") {
            maTinh = "khdn"
        }
        String urlX = "https://testnoibo-tt78admindemo.vnpt-invoice.com.vn"
        String usernameX = "ccbsservice"
        String passwordX = "fPs3LKWVu@"
        String accountX = "ccbsadmin"
        String acPassX = "fPs3LKWVu@"
        if (xmlInvData != null && !xmlInvData?.isEmpty()) {
            xmlInvData = xmlInvData.replaceAll("<!\\[CDATA\\[", "")
            xmlInvData = xmlInvData.replaceAll("]]>", "")
            xmlInvData = xmlInvData.replaceAll("&", "&amp;")
        }
        xmlInvData = DUtils.replaceSpecChar(xmlInvData, "CusName")
        xmlInvData = DUtils.replaceSpecChar(xmlInvData, "CusAddress")
        def env = post(urlX + "/" + bizServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/AdjustInvoiceAction"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <AdjustInvoiceAction xmlns="http://tempuri.org/">
                              <Account>$accountX</Account>
                              <ACpass><![CDATA[$acPassX]]></ACpass>
                              <xmlInvData><![CDATA[$xmlInvData]]></xmlInvData>
                              <username>$usernameX</username>
                              <pass>$passwordX</pass>
                              <fkey>$fkey</fkey>
                              <AttachFile>$attachFile</AttachFile>
                              <convert>$convert</convert>
                              <pattern>$pattern</pattern>
                              <serial>$serial</serial>
                            </AdjustInvoiceAction>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.AdjustInvoiceActionResponse.AdjustInvoiceActionResult
        logger.debug("adjustInvoiceAction response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Hóa đơn cần điều chỉnh không tồn tại",
                    "3" : "Dữ liệu xml đầu vào không đúng quy định",
                    "5" : "Không phát hành được hóa đơn",
                    "6" : "Dải hóa đơn cũ đã hết",
                    "7" : "User name không phù hợp, không tìm thấy company tương ứng cho user.",
                    "8" : "Hóa đơn cần điều chỉnh đã bị thay thế. Không thể điều chỉnh được nữa.",
                    "9" : "Trạng thái hóa đơn không được điều chỉnh",
                    "13": "Lỗi trùng fkey",
                    "14": "Lỗi trong quá trình thực hiện cấp số hóa đơn",
                    "15": "Lỗi khi thực hiện Deserialize chuỗi hóa đơn đầu vào",
                    "19": "Pattern truyền vào không giống với hóa đơn cần điều chỉnh",
                    "20": "Dải hóa đơn hết, User/Account không có quyền với Serial/Pattern và serial không phù hợp",
                    "21": "Trùng Fkey truyền vào",
                    "29": "Lỗi chứng thư hết hạn",
                    "30": "Danh sách hóa đơn tồn tại ngày hóa đơn nhỏ hơn ngày hóa đơn đã phát hành",
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> adjustInvoiceAction_VTT(String provinceCode, String account, String acPass, String xmlInvData, String fkey, String attachFile, int convert, String pattern, String serial) {
        String urlVTT = ""
        String usernameVTT = ""
        String passwordVTT = ""
        String accountVTT = ""
        String acPassVTT = ""
        if (lVTT.contains(provinceCode)) {
            urlVTT = mUrlVTT[provinceCode]
            usernameVTT = mUsernameVTT[provinceCode]
            passwordVTT = mPasswordVTT[provinceCode]
            accountVTT = mAccountVTT[provinceCode]
            acPassVTT = mAcpassVTT[provinceCode]
        } else {
            return ["error_code": "-99", "result": "$provinceCode", "message": "Chức năng chưa hỗ trợ tỉnh này"]
        }

        if (xmlInvData != null && !xmlInvData?.isEmpty()) {
            xmlInvData = xmlInvData.replaceAll("<!\\[CDATA\\[", "")
            xmlInvData = xmlInvData.replaceAll("]]>", "")
            xmlInvData = xmlInvData.replaceAll("&", "&amp;")
        }
        def env = post(urlVTT + "/" + bizServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/AdjustInvoiceAction"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <AdjustInvoiceAction xmlns="http://tempuri.org/">
                              <Account>$accountVTT</Account>
                              <ACpass><![CDATA[$acPassVTT]]></ACpass>
                              <xmlInvData><![CDATA[$xmlInvData]]></xmlInvData>
                              <username>$usernameVTT</username>
                              <pass>$passwordVTT</pass>
                              <fkey>$fkey</fkey>
                              <AttachFile>$attachFile</AttachFile>
                              <convert>$convert</convert>
                              <pattern>$pattern</pattern>
                              <serial>$serial</serial>
                            </AdjustInvoiceAction>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.AdjustInvoiceActionResponse.AdjustInvoiceActionResult
        logger.debug("adjustInvoiceAction response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Hóa đơn cần điều chỉnh không tồn tại",
                    "3" : "Dữ liệu xml đầu vào không đúng quy định",
                    "5" : "Không phát hành được hóa đơn",
                    "6" : "Dải hóa đơn cũ đã hết",
                    "7" : "User name không phù hợp, không tìm thấy company tương ứng cho user.",
                    "8" : "Hóa đơn cần điều chỉnh đã bị thay thế. Không thể điều chỉnh được nữa.",
                    "9" : "Trạng thái hóa đơn không được điều chỉnh",
                    "13": "Lỗi trùng fkey",
                    "14": "Lỗi trong quá trình thực hiện cấp số hóa đơn",
                    "15": "Lỗi khi thực hiện Deserialize chuỗi hóa đơn đầu vào",
                    "19": "Pattern truyền vào không giống với hóa đơn cần điều chỉnh",
                    "20": "Dải hóa đơn hết, User/Account không có quyền với Serial/Pattern và serial không phù hợp",
                    "21": "Trùng Fkey truyền vào",
                    "29": "Lỗi chứng thư hết hạn",
                    "30": "Danh sách hóa đơn tồn tại ngày hóa đơn nhỏ hơn ngày hóa đơn đã phát hành",
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> replaceInvoiceAction(String maTinh, String account, String acPass, String xmlInvData, String fkey, String attachFile, int convert, String pattern, String serial) {
        String usernameX = "ccbsservice"
        String passwordX = "fPs3LKWVu@"
        String accountX = "ccbsadmin"
        String acPassX = "fPs3LKWVu@"

        if (xmlInvData != null && !xmlInvData?.isEmpty()) {
            xmlInvData = xmlInvData.replaceAll("<!\\[CDATA\\[", "")
            xmlInvData = xmlInvData.replaceAll("]]>", "")
            xmlInvData = xmlInvData.replaceAll("&", "&amp;")
        }
        def env = post(url + "/" + bizServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/ReplaceInvoiceAction"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <ReplaceInvoiceAction xmlns="http://tempuri.org/">
                              <Account>$accountX</Account>
                              <ACpass><![CDATA[$acPassX]]></ACpass>
                              <xmlInvData><![CDATA[$xmlInvData]]></xmlInvData>
                              <username>$usernameX</username>
                              <pass>$passwordX</pass>
                              <fkey>$fkey</fkey>
                              <Attachfile>$attachFile</Attachfile>
                              <convert>$convert</convert>
                              <pattern>$pattern</pattern>
                              <serial>$serial</serial>
                            </ReplaceInvoiceAction>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.ReplaceInvoiceActionResponse.ReplaceInvoiceActionResult
        logger.debug("replaceInvoiceAction response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Không tồn tại hóa đơn cần thay thế",
                    "3" : "Dữ liệu xml đầu vào không đúng quy định",
                    "5" : "Có lỗi trong quá trình thay thế hóa đơn",
                    "6" : "Dải hóa đơn cũ đã hết",
                    "7" : "User name không phù hợp, không tìm thấy company tương ứng cho user.",
                    "8" : "Hóa đơn đã được thay thế rồi. Không thể thay thế nữa.",
                    "9" : "Trạng thái hóa đơn không được thay thế",
                    "13": "Lỗi trùng fkey",
                    "14": "Lỗi trong quá trình thực hiện cấp số hóa đơn",
                    "15": "Lỗi khi thực hiện Deserialize chuỗi hóa đơn đầu vào",
                    "19": "Pattern truyền vào không giống với hóa đơn cần điều chỉnh",
                    "20": "Dải hóa đơn hết, User/Account không có quyền với Serial/Pattern và serial không phù hợp",
                    "21": "Trùng Fkey truyền vào",
                    "29": "Lỗi chứng thư hết hạn",
                    "30": "Danh sách hóa đơn tồn tại ngày hóa đơn nhỏ hơn ngày hóa đơn đã phát hành",
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> replaceInvoiceAction_VTT(String provinceCode, String account, String acPass, String xmlInvData, String fkey, String attachFile, int convert, String pattern, String serial) {
        String urlVTT = ""
        String usernameVTT = ""
        String passwordVTT = ""
        String accountVTT = ""
        String acPassVTT = ""
        if (lVTT.contains(provinceCode)) {
            urlVTT = mUrlVTT[provinceCode]
            usernameVTT = mUsernameVTT[provinceCode]
            passwordVTT = mPasswordVTT[provinceCode]
            accountVTT = mAccountVTT[provinceCode]
            acPassVTT = mAcpassVTT[provinceCode]
        } else {
            return ["error_code": "-99", "result": "$provinceCode", "message": "Chức năng chưa hỗ trợ tỉnh này"]
        }

        if (xmlInvData != null && !xmlInvData?.isEmpty()) {
            xmlInvData = xmlInvData.replaceAll("<!\\[CDATA\\[", "")
            xmlInvData = xmlInvData.replaceAll("]]>", "")
            xmlInvData = xmlInvData.replaceAll("&", "&amp;")
        }
        def env = post(urlVTT + "/" + bizServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/ReplaceInvoiceAction"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <ReplaceInvoiceAction xmlns="http://tempuri.org/">
                              <Account>$accountVTT</Account>
                              <ACpass><![CDATA[$acPassVTT]]></ACpass>
                              <xmlInvData><![CDATA[$xmlInvData]]></xmlInvData>
                              <username>$usernameVTT</username>
                              <pass>$passwordVTT</pass>
                              <fkey>$fkey</fkey>
                              <Attachfile>$attachFile</Attachfile>
                              <convert>$convert</convert>
                              <pattern>$pattern</pattern>
                              <serial>$serial</serial>
                            </ReplaceInvoiceAction>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.ReplaceInvoiceActionResponse.ReplaceInvoiceActionResult
        logger.debug("replaceInvoiceAction response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Không tồn tại hóa đơn cần thay thế",
                    "3" : "Dữ liệu xml đầu vào không đúng quy định",
                    "5" : "Có lỗi trong quá trình thay thế hóa đơn",
                    "6" : "Dải hóa đơn cũ đã hết",
                    "7" : "User name không phù hợp, không tìm thấy company tương ứng cho user.",
                    "8" : "Hóa đơn đã được thay thế rồi. Không thể thay thế nữa.",
                    "9" : "Trạng thái hóa đơn không được thay thế",
                    "13": "Lỗi trùng fkey",
                    "14": "Lỗi trong quá trình thực hiện cấp số hóa đơn",
                    "15": "Lỗi khi thực hiện Deserialize chuỗi hóa đơn đầu vào",
                    "19": "Pattern truyền vào không giống với hóa đơn cần điều chỉnh",
                    "20": "Dải hóa đơn hết, User/Account không có quyền với Serial/Pattern và serial không phù hợp",
                    "21": "Trùng Fkey truyền vào",
                    "29": "Lỗi chứng thư hết hạn",
                    "30": "Danh sách hóa đơn tồn tại ngày hóa đơn nhỏ hơn ngày hóa đơn đã phát hành",
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> adjustInv(String maTinh, String account, String acPass, String xmlInvData, String fkey, int convert) {
        String usernameX = "ccbsservice"
        String passwordX = "fPs3LKWVu@"
        String accountX = "ccbsadmin"
        String acPassX = "fPs3LKWVu@"

        if (xmlInvData != null && !xmlInvData?.isEmpty()) {
            xmlInvData = xmlInvData.replaceAll("<!\\[CDATA\\[", "")
            xmlInvData = xmlInvData.replaceAll("]]>", "")
            xmlInvData = xmlInvData.replaceAll("&", "&amp;")
        }
        def env = post(url + "/" + bizServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/adjustInv"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <adjustInv xmlns="http://tempuri.org/">
                              <Account>$accountX</Account>
                              <ACpass><![CDATA[$acPassX]]></ACpass>
                              <xmlInvData><![CDATA[$xmlInvData]]></xmlInvData>
                              <username>$usernameX</username>
                              <pass>$passwordX</pass>
                              <fkey>$fkey</fkey>
                              <convert>$convert</convert>
                            </adjustInv>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.adjustInvResponse.adjustInvResult
        logger.debug("adjustInv response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Hóa đơn cần điều chỉnh không tồn tại",
                    "3" : "Dữ liệu xml đầu vào không đúng quy định",
                    "5" : "Không phát hành được hóa đơn",
                    "6" : "Dải hóa đơn cũ đã hết",
                    "7" : "User name không phù hợp, không tìm thấy company tương ứng cho user.",
                    "8" : "Hóa đơn cần điều chỉnh đã bị thay thế. Không thể điều chỉnh được nữa.",
                    "9" : "Trạng thái hóa đơn không được điều chỉnh",
                    "13": "Lỗi trùng fkey",
                    "14": "Lỗi trong quá trình thực hiện cấp số hóa đơn",
                    "15": "Lỗi khi thực hiện Deserialize chuỗi hóa đơn đầu vào",
                    "19": "Pattern truyền vào không giống với hóa đơn cần điều chỉnh",
                    "20": "Dải hóa đơn hết, User/Account không có quyền với Serial/Pattern và serial không phù hợp",
                    "21": "Trùng Fkey truyền vào",
                    "29": "Lỗi chứng thư hết hạn",
                    "30": "Danh sách hóa đơn tồn tại ngày hóa đơn nhỏ hơn ngày hóa đơn đã phát hành",
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> adjustInv_v2(String maTinh, String account, String acPass, String xmlInvData, String fkey, int convert, String pattern, String serial) {
        if (maTinh == null || maTinh == "") {
            maTinh = "khdn"
        }
        String urlX = "https://testnoibo-tt78admindemo.vnpt-invoice.com.vn"
        String usernameX = "ccbsservice"
        String passwordX = "fPs3LKWVu@"
        String accountX = "ccbsadmin"
        String acPassX = "fPs3LKWVu@"
        if (xmlInvData != null && !xmlInvData?.isEmpty()) {
            xmlInvData = xmlInvData.replaceAll("<!\\[CDATA\\[", "")
            xmlInvData = xmlInvData.replaceAll("]]>", "")
            xmlInvData = xmlInvData.replaceAll("&", "&amp;")
        }
        def env = post(urlX + "/" + bizServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/adjustInvPattern"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <adjustInvPattern xmlns="http://tempuri.org/">
                              <Account>$accountX</Account>
                              <ACpass><![CDATA[$acPassX]]></ACpass>
                              <xmlInvData><![CDATA[$xmlInvData]]></xmlInvData>
                              <username>$usernameX</username>
                              <pass>$passwordX</pass>
                              <pattern>$pattern</pattern>
                              <serial>$serial</serial>
                              <fkey>$fkey</fkey>
                              <convert>$convert</convert>
                            </adjustInvPattern>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.adjustInvPatternResponse.adjustInvPatternResult
        logger.debug("adjustInvPattern response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Hóa đơn cần điều chỉnh không tồn tại",
                    "3" : "Dữ liệu xml đầu vào không đúng quy định",
                    "5" : "Không phát hành được hóa đơn",
                    "6" : "Dải hóa đơn cũ đã hết",
                    "7" : "User name không phù hợp, không tìm thấy company tương ứng cho user.",
                    "8" : "Hóa đơn cần điều chỉnh đã bị thay thế. Không thể điều chỉnh được nữa.",
                    "9" : "Trạng thái hóa đơn không được điều chỉnh",
                    "10": "Lô có số hóa đơn vượt quá max cho phép",
                    "13": "Lỗi trùng fkey",
                    "14": "Lỗi trong quá trình thực hiện cấp số hóa đơn",
                    "15": "Lỗi khi thực hiện Deserialize chuỗi hóa đơn đầu vào",
                    "19": "Pattern truyền vào không giống với hóa đơn cần điều chỉnh",
                    "20": "Pattern và serial không phù hợp, hoặc không tồn tại hóa đơn đã đăng kí có sử dụng Pattern và serial truyền vào",
                    "21": "Trùng Fkey truyền vào",
                    "29": "Lỗi chứng thư hết hạn",
                    "30": "Danh sách hóa đơn tồn tại ngày hóa đơn nhỏ hơn ngày hóa đơn đã phát hành",
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> adjustInv_v2_VTT(String provinceCode, String account, String acPass, String xmlInvData, String fkey, int convert, String pattern, String serial) {
        String urlVTT = ""
        String usernameVTT = ""
        String passwordVTT = ""
        String accountVTT = ""
        String acPassVTT = ""
        if (lVTT.contains(provinceCode)) {
            urlVTT = mUrlVTT[provinceCode]
            usernameVTT = mUsernameVTT[provinceCode]
            passwordVTT = mPasswordVTT[provinceCode]
            accountVTT = mAccountVTT[provinceCode]
            acPassVTT = mAcpassVTT[provinceCode]
        } else {
            return ["error_code": "-99", "result": "$provinceCode", "message": "Chức năng chưa hỗ trợ tỉnh này"]
        }

        if (xmlInvData != null && !xmlInvData?.isEmpty()) {
            xmlInvData = xmlInvData.replaceAll("<!\\[CDATA\\[", "")
            xmlInvData = xmlInvData.replaceAll("]]>", "")
            xmlInvData = xmlInvData.replaceAll("&", "&amp;")
        }
        def env = post(urlVTT + "/" + bizServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/adjustInvPattern"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <adjustInvPattern xmlns="http://tempuri.org/">
                              <Account>$accountVTT</Account>
                              <ACpass><![CDATA[$acPassVTT]]></ACpass>
                              <xmlInvData><![CDATA[$xmlInvData]]></xmlInvData>
                              <username>$usernameVTT</username>
                              <pass>$passwordVTT</pass>
                              <pattern>$pattern</pattern>
                              <serial>$serial</serial>
                              <fkey>$fkey</fkey>
                              <convert>$convert</convert>
                            </adjustInvPattern>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.adjustInvPatternResponse.adjustInvPatternResult
        logger.debug("adjustInvPattern response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Hóa đơn cần điều chỉnh không tồn tại",
                    "3" : "Dữ liệu xml đầu vào không đúng quy định",
                    "5" : "Không phát hành được hóa đơn",
                    "6" : "Dải hóa đơn cũ đã hết",
                    "7" : "User name không phù hợp, không tìm thấy company tương ứng cho user.",
                    "8" : "Hóa đơn cần điều chỉnh đã bị thay thế. Không thể điều chỉnh được nữa.",
                    "9" : "Trạng thái hóa đơn không được điều chỉnh",
                    "10": "Lô có số hóa đơn vượt quá max cho phép",
                    "13": "Lỗi trùng fkey",
                    "14": "Lỗi trong quá trình thực hiện cấp số hóa đơn",
                    "15": "Lỗi khi thực hiện Deserialize chuỗi hóa đơn đầu vào",
                    "19": "Pattern truyền vào không giống với hóa đơn cần điều chỉnh",
                    "20": "Pattern và serial không phù hợp, hoặc không tồn tại hóa đơn đã đăng kí có sử dụng Pattern và serial truyền vào",
                    "21": "Trùng Fkey truyền vào",
                    "29": "Lỗi chứng thư hết hạn",
                    "30": "Danh sách hóa đơn tồn tại ngày hóa đơn nhỏ hơn ngày hóa đơn đã phát hành",
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> replaceInv(String maTinh, String account, String acPass, String xmlInvData, String fkey, int convert) {
        String usernameX = "ccbsservice"
        String passwordX = "fPs3LKWVu@"
        String accountX = "ccbsadmin"
        String acPassX = "fPs3LKWVu@"

        if (xmlInvData != null && !xmlInvData?.isEmpty()) {
            xmlInvData = xmlInvData.replaceAll("<!\\[CDATA\\[", "")
            xmlInvData = xmlInvData.replaceAll("]]>", "")
            xmlInvData = xmlInvData.replaceAll("&", "&amp;")
        }
        def env = post(url + "/" + bizServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/replaceInv"],
                """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <soap:Body>
                            <replaceInv xmlns="http://tempuri.org/">
                              <Account>$accountX</Account>
                              <ACpass><![CDATA[$acPassX]]></ACpass>
                              <xmlInvData><![CDATA[$xmlInvData]]></xmlInvData>
                              <username>$usernameX</username>
                              <pass>$passwordX</pass>
                              <fkey>$fkey</fkey>
                              <convert>$convert</convert>
                            </replaceInv>
                          </soap:Body>
                        </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.replaceInvResponse.replaceInvResult
        logger.debug("replaceInv response: $vkq")
        String[] retInfo = vkq.text().split("\\:")
        String err_code = retInfo[0]
        def err_msg = ""
        if (retInfo[0].startsWith("OK")) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_msg = retInfo[1]
            def err_messages = [
                    "1" : "Tài khoản đăng nhập sai hoặc không có quyền",
                    "2" : "Không tồn tại hóa đơn cần thay thế",
                    "3" : "Dữ liệu xml đầu vào không đúng quy định",
                    "5" : "Có lỗi trong quá trình thay thế hóa đơn",
                    "6" : "Dải hóa đơn cũ đã hết",
                    "7" : "User name không phù hợp, không tìm thấy company tương ứng cho user.",
                    "8" : "Hóa đơn đã được thay thế rồi. Không thể thay thế nữa.",
                    "9" : "Trạng thái hóa đơn không được thay thế",
                    "13": "Lỗi trùng fkey",
                    "14": "Lỗi trong quá trình thực hiện cấp số hóa đơn",
                    "15": "Lỗi khi thực hiện Deserialize chuỗi hóa đơn đầu vào",
                    "19": "Pattern truyền vào không giống với hóa đơn cần điều chỉnh",
                    "20": "Dải hóa đơn hết, User/Account không có quyền với Serial/Pattern và serial không phù hợp",
                    "21": "Trùng Fkey truyền vào",
                    "29": "Lỗi chứng thư hết hạn",
                    "30": "Danh sách hóa đơn tồn tại ngày hóa đơn nhỏ hơn ngày hóa đơn đã phát hành",
            ]
            if (retInfo[0].startsWith("ERR") && err_messages.containsKey(retInfo[1])) {
                err_code = retInfo[1]
                err_msg = err_messages.get(retInfo[1])
            } else if (retInfo[1] != null && err_messages.containsKey(retInfo[1]?.split(" ")[0])) {
                String keyErr = retInfo[1]?.split(" ")[0]
                err_code = keyErr
                err_msg = err_messages.get(keyErr)
            } else {
                err_code = "0"
                err_msg = "success"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }

    Map<String, Object> updateCus_VTT(String provinceCode, String xmlInvData, int convert) {
        String urlVTT = ""
        String usernameVTT = username
        String passwordVTT = password
        if (lVTT.contains(provinceCode)) {
            urlVTT = mUrlVTT[provinceCode]
            usernameVTT = mUsernameVTT[provinceCode]
            passwordVTT = mPasswordVTT[provinceCode]
        } else {
            return ["error_code": "-99", "result": "$provinceCode", "message": "Chức năng chưa hỗ trợ tỉnh này"]
        }

        String err_code = "", err_msg = ""
        if (xmlInvData != null && !xmlInvData?.isEmpty()) {
            xmlInvData = xmlInvData.replaceAll("<!\\[CDATA\\[", "")
            xmlInvData = xmlInvData.replaceAll("]]>", "")
            xmlInvData = xmlInvData.replaceAll("&", "&amp;")
        }
        def env = post(urlVTT + "/" + publicServicePath,
                ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/UpdateCus"],
                """ <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                        <soap:Body>
                            <UpdateCus xmlns="http://tempuri.org/">
                                <XMLCusData><![CDATA[$xmlInvData]]></XMLCusData>
                                <username>$usernameVTT</username>
                                <pass>$passwordVTT</pass>
                                <convert>$convert</convert>
                            </UpdateCus>
                        </soap:Body>
                    </soap:Envelope>""")
        def fault = env.Body.Fault
        if (!fault.isEmpty()) {
            return ["error_code": fault.faultcode.text(), "message": fault.faultstring.text()]
        }
        def vkq = env.Body.UpdateCusResponse.UpdateCusResult
        int retInfo = Integer.parseInt(vkq.text())
        if (retInfo > 0) {
            err_code = "0"
            err_msg = "success"
        } else {
            err_code = retInfo.toString()
            err_msg = "Xảy ra lỗi"
            if (err_code == "-1") {
                err_msg = "Tài khoản đăng nhập sai hoặc không có quyền thêm khách hàng"
            } else if (err_code == "-2") {
                err_msg = "Không import được khách hàng vào db"
            } else if (err_code == "-3") {
                err_msg = "Dữ liệu xml đầu vào không đúng quy định"
            }
        }
        return ["error_code": "$err_code", "result": "${vkq.text()}", "message": "$err_msg"]
    }


    GPathResult post(String url, Map<String, String> headers, String body) {
        headers.put("Content-Type", "text/xml;charset=UTF-8")
        String resp = httpManaged.post(["url": url], headers, body)
        return new XmlSlurper().parseText(resp)
    }

}
