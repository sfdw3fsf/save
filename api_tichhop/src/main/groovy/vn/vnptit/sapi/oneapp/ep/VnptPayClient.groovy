package vn.vnptit.sapi.oneapp.ep

import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import vn.vnptit.sapi.oneapp.managed.HttpManaged

/**
 * @author VietNH*
 */
@Component
class VnptPayClient {

    @Autowired
    HttpManaged httpManaged

    @Value("\${spring.httpservices.vnptpay.url}")
    String vnptpayUrl
    @Value("\${spring.httpservices.vnptpay.username}")
    String vnptpayUser
    @Value("\${spring.httpservices.vnptpay.password}")
    String vnptpayPw

    String getUrl(String funcName) {
        def ctx = vnptpayUrl.replaceAll("/\$", "")
        return "$ctx/$funcName"
    }

    /**
     * @param hrmId
     * @return
     */
    Map<String, Object> login(String hrmId) {
        post(getUrl("login"), "", """{ "aud":"$vnptpayUser", "pws":"$vnptpayPw", "account":"$hrmId" }""")
    }

    Map<String, Object> inquiry(String token, String serverToken) {
        post(getUrl("vnptcab_query_balance"), serverToken, """{"token": "$token"}""")
    }

    /**
     * @Author VietNH
     */
    Map<String, Object> createAccount(String phone, String name, String id, String code, String otpId, String otpValue) {
        post(getUrl("create_account_onebss"), "", """{ "phoneNumber":"$phone", "fullNameVi":"$name",
				 "idNumber":"$id", "referenceCode":"$code", "otpId":"$otpId", "otpValue":"$otpValue" }""")
    }

    /**
     * @Author VietNH
     */
    Map<String, Object> vnptcab_get_link(String action, String serverToken, String token) {
        post(getUrl("vnptcab_get_link"), serverToken, """{ "action":"$action", "token":"$token" }""")
    }

    /**
     * @Author VietNH
     */
    Map<String, Object> vnptcab_auto_login_check(String serverToken) {
        post(getUrl("auto_login_check"), serverToken, """{}""")
    }

    /**
     * @Author VietNH
     */
    Map<String, Object> vnptcab_confirm_auto_login(String action, String serverToken, String token) {
        post(getUrl("smcswallet_auto_login"), serverToken, """{ "agreementSSOStatus":"$action", "token":"$token" }""")
    }

    /**
     * @Author VietNH
     */
    Map<String, Object> vnptcab_debit_without_otp(String requestId, String employeeId, String groupId, String referenceAgencyCode, String sellingDepartmentId, String walletId, String walletAccountId,
                                                  String collaboratorId, String transferType, String transferContent, String customerId, String amount, String token, String serverToken) {
        post(getUrl("vnptcab_debit_without_otp"), serverToken, """{"sendWalletId": "$walletId", "sendWalletAccountId": "$walletAccountId", "transferType": "$transferType", 
			"transferAmount": "$amount", "transferContent": "$transferContent", "referenceAgencyCode": "$referenceAgencyCode", "customerId": "$customerId", "collaboratorId": "$collaboratorId", 
			"employeeId": "$employeeId", "groupId": "$groupId", "sellingDepartmentId": "$sellingDepartmentId", "additionalInfo": "", "token": "$token", "requestId": "$requestId"}""")
    }

    /**
     * @Author VietNH
     */
    Map<String, Object> commitTransaction(String transferResult, String transferType, String transId, String description, String phieu_id, String confirmType, String token, String serverToken) {
        post(getUrl("vnptcab_debit_commit"), serverToken, """{"debitResult": "$transferResult", "transferType": "$transferType", "refTransId": "$transId",
			"description": "$description", "confirmType": "$confirmType", "phieu_id": "$phieu_id", "token": "$token"}""")
    }

    /**
     * @Author VietNH
     */
    Map<String, Object> createQRCode(Integer loaiThanhToan, String billNumber, String amount, String consumerId, String purpose, String province,
                                     String addInfo, String systemInfo, String serverToken) {
        switch (loaiThanhToan) {
            case 0:
                post(getUrl("create_qrcode"), serverToken, """{"billNumber": "$billNumber", "amount": "$amount", "consumerID": "$consumerId", "type": "$loaiThanhToan",
					"term": "", "purpose": "$purpose", "addInfo": [], "systemInfo": "$systemInfo", "province": "$province"}""")
                break
            case 1:
                post(getUrl("create_qrcode"), serverToken, """{"billNumber": "$billNumber", "amount": "$amount", "consumerID": "$consumerId",
				"purpose": "$purpose", "addInfo": $addInfo, "term": "", "province": "$province", "type": "$loaiThanhToan", "systemInfo": "$addInfo"}""")
                break
            case 2:
                post(getUrl("create_qrcode"), serverToken, """{"billNumber": "$billNumber", "amount": "$amount", "consumerID": "$consumerId", "type": "$loaiThanhToan",
								"term": "", "province": "$province", "systemInfo": "$systemInfo", "purpose": "$purpose", "addInfo": $addInfo}""")
                break
        }
    }

    /**
     * @Author VietNH
     */
    Map<String, Object> banhang_debit_account_immediately(String requestId, String employeeId, String groupId, String paymentCode, String sellingDepartmentId, String walletId, String walletAccountId,
                                                          String transferType, String transferContent, String agencyId, String amount, String addInfo, String token, String serverToken) {
        post(getUrl("banhang_debit_account_immediately"), serverToken, """{"sendWalletId": "$walletId", "sendWalletAccountId": "$walletAccountId", "transferType": "$transferType", 
			"transferAmount": "$amount", "transferContent": "$transferContent", "paymentCode": "$paymentCode", "agencyId": "$agencyId",
			"employeeId": "$employeeId", "groupId": "$groupId", "sellingDepartmentId": "$sellingDepartmentId", "additionalInfo": $addInfo, "token": "$token", "client_request": "$requestId"}""")
    }

    /**
     * @Author VietNH
     */
    Map<String, Object> vnptcab_cancel_transaction(String serverToken, String walletToken, String cancelType, String amount, String description, String refTransId, String username) {
        post(getUrl("vnptcab_cancel_transaction"), serverToken, """{ "token":"$walletToken", "subServiceCode":"", "client_request":"",
							"cancelType":"$cancelType", "amount":"$amount", "description":"$description", "refTransId":"$refTransId", "user":"$username"}""")
    }
/**
 * @Author VietNH
 */
    Map<String, Object> inquire_partner(String paymentCode, String agencyId, String employeeId, String groupId, String sellingDepartmentId, String requestId, String requestDate,
                                        String contractType, String tong_tien, String kh_nv, String thanh_toan, String bill_amount, String payment_method, String phanvung_id,
                                        String user, String identifycode) {
        if (kh_nv != null && kh_nv.trim().length() > 0) {
            kh_nv = kh_nv.replaceAll("\"", "\\\\\"")
        }
        if (thanh_toan != null && thanh_toan.trim().length() > 0) {
            thanh_toan = thanh_toan.replaceAll("\"", "\\\\\"")
        }
        String payment_code = """{\\"paymentCode\\": \\"$paymentCode\\", \\"agencyId\\": \\"$agencyId\\", \\"employeeId\\": \\"$employeeId\\", \\"contractType\\": \\"$contractType\\", \\"tong_tien\\": \\"$tong_tien\\",\\"groupId\\": \\"$groupId\\", \\"sellingDepartmentId\\": \\"$sellingDepartmentId\\", \\"requestId\\": \\"$requestId\\", \\"requestDate\\": \\"$requestDate\\", \\"kh_nv\\": $kh_nv, \\"thanh_toan\\": $thanh_toan}"""
        post(getUrl("inquire_partner"), "", """{"payment_code": "$payment_code", "identifycode": "$identifycode", "bill_amount": "$bill_amount", "payment_method": "$payment_method", "phanvung_id": "$phanvung_id", "user": "$user"}""")
    }

    Map<String, Object> post(String url, String serverToken, String body) {
        String resp = httpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": serverToken], body)
        new JsonSlurper().parseText(resp) as Map<String, Object>
    }

    Map<String, Object> checkUserPayByPhone(String phoneNumber) {
        post(getUrl("check_pay_phonenumber"), "", """{"phoneNumber":"$phoneNumber" }""")
    }

    Map<String, Object> openAppVNPTPayAgent(String phoneNumber, String walletToken, String device) {
        post(getUrl("open_app_agent"), "", """{"phoneNumber":"$phoneNumber","wallet_token":"$walletToken","device":"$device"}""")
    }

    Map<String, Object> checkWalletInfo(String serverToken, String user_vi) {
        post(getUrl("check_wallet_info"), serverToken, """{"sysAcc":"$user_vi"}""")
    }

    String commitTransaction2(String transferResult, String transferType, String transId, String description, String phieu_id, String confirmType, String token, String serverToken) {
        return getUrl("vnptcab_debit_commit") + "|||" + serverToken + "|||" + transferResult+ "|||" +transferType+ "|||" +transId+ "|||" +description+ "|||" +confirmType+ "|||" +phieu_id+ "|||" +token
    }
}
