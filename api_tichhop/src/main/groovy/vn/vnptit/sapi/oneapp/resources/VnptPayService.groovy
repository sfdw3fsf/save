package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import io.swagger.model.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.CacheManager
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.ep.VnptPayClient
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse

import vn.vnptit.sapi.oneapp.models.vnptpay.VnptPayInfo
import vn.vnptit.sapi.oneapp.models.vnptpay.WalletInfo
import vn.vnptit.sapi.oneapp.models.vnptpay.request.BanHangTransactionRequest
import vn.vnptit.sapi.oneapp.models.vnptpay.request.CancelTransactionRequest
import vn.vnptit.sapi.oneapp.models.vnptpay.request.CommitTransactionRequest
import vn.vnptit.sapi.oneapp.models.vnptpay.request.CreateAccountVNPTPayRequest
import vn.vnptit.sapi.oneapp.models.vnptpay.request.CreateQRCodeRequest
import vn.vnptit.sapi.oneapp.models.vnptpay.request.InitTransactionRequest
import vn.vnptit.sapi.oneapp.models.vnptpay.request.InquirePartnerTransactionRequest
import vn.vnptit.sapi.oneapp.models.vnptpay.respones.BanHangTransactionResponse
import vn.vnptit.sapi.oneapp.models.vnptpay.respones.InquirePartnerResponse
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.Constant
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.service.HopDongService
import vn.vnptit.sapi.oneapp.util.GenericApi
import vn.vnptit.sapi.oneapp.util.MessageUtils

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

import static vn.vnptit.sapi.oneapp.util.GenericApi.*

@RestController
@RequestMapping("vnptpay")
class VnptPayService {

    @Autowired
    CacheManager cacheManager
    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    VnptPayClient vnptPayClient
    @Autowired
    GenericApi genericApi
    @Autowired
    HopDongService hopDongService

    @Autowired
    VnptPayService(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }

    private final HttpServletRequest request
    private final ObjectMapper objectMapper

    @Operation(summary = "Đăng nhập ví", description = "Đăng nhập/Kiểm tra đăng nhập ví")
    @PostMapping("/login")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse login() {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String userWallet = session.get(USER_WALLET)
            VnptPayInfo data = null
            String message = ""
            Integer errorCode
            Map<String, Object> resp = vnptPayClient.login(userWallet)
            switch (resp.errorCode) {
                case "00":
                    data = new VnptPayInfo()
                    if (resp.data == null || resp.data.toString().toLowerCase() == "null") {
                        data.url = resp.link + "&appType=web"
                    } else {
                        data.walletId = resp.data?.data?.walletId
                        data.walletAccountId = resp.data?.data?.walletAccountId
                        data.token = resp.data?.tokenRefresh
                    }
                    cacheManager.getCache(Constant.VNPT_PAY_CACHE_NAME).put(userWallet.toUpperCase() + "/serverToken", resp.token as String)
                    errorCode = 0
                    break
                case "94"://Hệ thống bảo trì
                    errorCode = 94
                    message = "VNPT Pay: Hệ thống đang bảo trì"
                    break
                case "99"://Lỗi hệ thống
                    errorCode = 99
                    message = "VNPT Pay: Lỗi hệ thống"
                    break
                case "300"://Lỗi trong quá trình xử lý
                    errorCode = 300
                    message = "VNPT Pay: Lỗi trong quá trình xử lý"
                    break
                case "60"://Token hết hiệu lực
                    errorCode = 60
                    message = "VNPT Pay: Token hết hiệu lực"
                    break
                case "100"://Tài khoản chưa được khai báo hoặc đang bị khóa
                    errorCode = 100
                    message = "VNPT Pay: Tài khoản chưa có ví VNPT Pay"
                    break
                case "400"://Dữ liệu sai định dạng
                    errorCode = 400
                    message = "VNPT Pay: Dữ liệu sai định dạng"
                    break
                default:
                    errorCode = 1
                    message = "VNPT Pay: Tài khoản chưa có ví VNPT Pay"
                    break
            }
            if (errorCode == 0) {
                return new CommonResponse(errorCode: errorCode, faultCode: "", data: data, faultString: "Thành công")
            } else {
                return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message)
            }
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Tạo tài khoản ví", description = "Tạo tài khoản ví")
    @PostMapping("/createAccount")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse createAccount(@RequestBody CreateAccountVNPTPayRequest body) {
        try {
            String data = null
            String message = ""
            Integer errorCode
            Map<String, Object> resp = vnptPayClient.createAccount(body.getPhone(), body.getName(), body.getId(), body.getRefCode(), body.getOtpId(), body.getOtp())
            switch (resp.errorCode) {
                case "00":
                    data = resp.data?.otpId
                    errorCode = 0
                    break
                default:
                    errorCode = 1
                    message = resp.errorDescription
                    break
            }
            if (errorCode == 0) {
                return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: data)
            } else {
                return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message)
            }
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Kiểm tra Auto login ví", description = "Kiểm tra Auto login ví")
    @PostMapping("/checkAutoLogin")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse checkAutoLogin() {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String userWallet = session.get(USER_WALLET)
            Boolean data = null
            String message = ""
            Integer errorCode
            String serverToken = cacheManager.getCache(Constant.VNPT_PAY_CACHE_NAME).get(userWallet.toUpperCase() + "/serverToken", String.class)
            if (serverToken == null) {
                errorCode = -1
                message = "Token hết hạn"
            } else {
                Map<String, Object> resp = vnptPayClient.vnptcab_auto_login_check(serverToken)
                switch (resp.errorCode) {
                    case "00":
                        errorCode = 0
                        data = resp.result
                        break
                    case "94"://Hệ thống bảo trì
                        errorCode = 94
                        message = "VNPT Pay: Hệ thống đang bảo trì"
                        break
                    case "99"://Lỗi hệ thống
                        errorCode = 99
                        message = "VNPT Pay: Lỗi hệ thống"
                        break
                    case "300"://Lỗi trong quá trình xử lý
                        errorCode = 300
                        message = "VNPT Pay: Lỗi trong quá trình xử lý"
                        break
                    case "60"://Token hết hiệu lực
                        errorCode = 60
                        message = "VNPT Pay: Token hết hiệu lực"
                        break
                    case "100"://Tài khoản chưa được khai báo hoặc đang bị khóa
                        errorCode = 100
                        message = "VNPT Pay: Tài khoản chưa có ví VNPT Pay"
                        break
                    case "400"://Dữ liệu sai định dạng
                        errorCode = 400
                        message = "VNPT Pay: Dữ liệu sai định dạng"
                        break
                    default:
                        errorCode = 1
                        message = "VNPT Pay: " + resp.message
                        break
                }
            }
            if (errorCode == 0) {
                return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: data)
            } else {
                return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message)
            }
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Lấy thông tin số dư ví", description = "Lấy thông tin số dư ví")
    @PostMapping("/getBalance")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getBalance() {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String userWallet = session.get(USER_WALLET)
            String walletToken = session.get(WALLET_TOKEN)
            WalletInfo data = null
            String message = ""
            Integer errorCode
            String serverToken = cacheManager.getCache(Constant.VNPT_PAY_CACHE_NAME).get(userWallet.toUpperCase() + "/serverToken", String.class)
            if (serverToken == null) {
                errorCode = -1
                message = "Token hết hạn"
            } else {
                Map<String, Object> resp = vnptPayClient.inquiry(walletToken, serverToken)
                // try {
                //     hopDongService.init(request.getHeader("Authorization"))
                //     hopDongService.logAction("vnptpay_getBalance", userWallet + "|||" + walletToken + "|||" + serverToken + "|||" + new Gson().toJson(resp))
                // } catch (Exception exe) {
                    
                // }
                switch (resp.errorCode) {
                    case "00":
                        if (resp.data) {
                            data = new WalletInfo()
                            data.setBalance(Long.parseLong(resp.data?.availableBalance))
                            data.setName(resp.data?.name)
                            data.setPhone(resp.data?.phoneNumber)
                            data.setWalletId(Long.parseLong(String.valueOf(resp.data?.walletId)))
                            data.setWalletAccountId(Long.parseLong(String.valueOf(resp.data?.walletUserId)))
                            def listAccs = resp.data?.listWalletAccountId
                            if (listAccs) {
                                listAccs.each {
                                    data.setWalletId(Long.parseLong(String.valueOf(it.walletId)))
                                    data.setWalletAccountId(Long.parseLong(String.valueOf(it.id)))
                                }
                            }
                        }
                        errorCode = 0
                        break
                    case "94"://Hệ thống bảo trì
                        errorCode = 94
                        message = "VNPT Pay: Hệ thống đang bảo trì"
                        break
                    case "99"://Lỗi hệ thống
                        errorCode = 99
                        message = "VNPT Pay: Lỗi hệ thống"
                        break
                    case "300"://Lỗi trong quá trình xử lý
                        errorCode = 300
                        message = "VNPT Pay: Lỗi trong quá trình xử lý"
                        break
                    case "60"://Token hết hiệu lực
                        errorCode = 60
                        message = "VNPT Pay: Token hết hiệu lực"
                        break
                    case "100"://Tài khoản chưa được khai báo hoặc đang bị khóa
                        errorCode = 100
                        message = "VNPT Pay: Tài khoản chưa có ví VNPT Pay"
                        break
                    case "400"://Dữ liệu sai định dạng
                        errorCode = 400
                        message = "VNPT Pay: Dữ liệu sai định dạng"
                        break
                    default:
                        errorCode = 1
                        message = "-VNPT Pay: " + resp.message
                        break
                }
            }
            if (errorCode == 0) {
                return new CommonResponse(errorCode: errorCode, faultCode: "", data: data, faultString: "Thành công")
            } else {
                return new CommonResponse(errorCode: errorCode, faultCode: "", data: null, faultString: message)
            }
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Lấy đường link ví", description = "Lấy đường link ví")
    @PostMapping("/getLink/{action}")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getLink(@PathVariable("action") String action) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String userWallet = session.get(USER_WALLET)
            String walletToken = session.get(WALLET_TOKEN)
            String data = null
            String message = ""
            Integer errorCode
            String serverToken = cacheManager.getCache(Constant.VNPT_PAY_CACHE_NAME).get(userWallet.toUpperCase() + "/serverToken", String.class)
            if (serverToken == null) {
                errorCode = -1
                message = "Token hết hạn"
            } else {
                Map<String, Object> resp = vnptPayClient.vnptcab_get_link(action, serverToken, walletToken)
                switch (resp.errorCode) {
                    case "00":
                        if (resp.data) {
                            data = resp.data + "&appType=web"
                            errorCode = 0
                        } else {
                            errorCode = 1
                            message = resp.errorDescription
                        }
                        break
                    case "94"://Hệ thống bảo trì
                        errorCode = 94
                        message = "VNPT Pay: Hệ thống đang bảo trì"
                        break
                    case "99"://Lỗi hệ thống
                        errorCode = 99
                        message = "VNPT Pay: Lỗi hệ thống"
                        break
                    case "300"://Lỗi trong quá trình xử lý
                        errorCode = 300
                        message = "VNPT Pay: Lỗi trong quá trình xử lý"
                        break
                    case "60"://Token hết hiệu lực
                        errorCode = 60
                        message = "VNPT Pay: Token hết hiệu lực"
                        break
                    case "100"://Tài khoản chưa được khai báo hoặc đang bị khóa
                        errorCode = 100
                        message = "VNPT Pay: Tài khoản chưa có ví VNPT Pay"
                        break
                    case "400"://Dữ liệu sai định dạng
                        errorCode = 400
                        message = "VNPT Pay: Dữ liệu sai định dạng"
                        break
                    default:
                        if (resp.data) {
                            data = resp.data + "&appType=web"
                            errorCode = 0
                        } else {
                            errorCode = 1
                            message = resp.errorDescription
                        }
                        break
                }
            }
            if (errorCode == 0) {
                return new CommonResponse(errorCode: errorCode, faultCode: "", data: data, faultString: "Thành công")
            } else {
                return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message)
            }
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Xác nhận/Hủy Auto login ví", description = "Xác nhận/Hủy Auto login ví")
    @PostMapping("/confirmAutoLogin/{action}")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse confirmAutoLogin(@PathVariable("action") String action) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String userWallet = session.get(USER_WALLET)
            String walletToken = session.get(WALLET_TOKEN)
            String message
            Integer errorCode
            String serverToken = cacheManager.getCache(Constant.VNPT_PAY_CACHE_NAME).get(userWallet.toUpperCase() + "/serverToken", String.class)
            if (serverToken == null) {
                errorCode = -1
                message = "Token hết hạn"
            } else {
                Map<String, Object> resp = vnptPayClient.vnptcab_confirm_auto_login(action, serverToken, walletToken)
                switch (resp.errorCode) {
                    case "00":
                        if (resp.data) {
                            errorCode = 0
                            message = MessageUtils.SUCCESS
                        } else {
                            errorCode = 1
                            message = resp.errorDescription
                        }
                        break
                    case "94"://Hệ thống bảo trì
                        errorCode = 94
                        message = "VNPT Pay: Hệ thống đang bảo trì"
                        break
                    case "99"://Lỗi hệ thống
                        errorCode = 99
                        message = "VNPT Pay: Lỗi hệ thống"
                        break
                    case "300"://Lỗi trong quá trình xử lý
                        errorCode = 300
                        message = "VNPT Pay: Lỗi trong quá trình xử lý"
                        break
                    case "60"://Token hết hiệu lực
                        errorCode = 60
                        message = "VNPT Pay: Token hết hiệu lực"
                        break
                    case "100"://Tài khoản chưa được khai báo hoặc đang bị khóa
                        errorCode = 100
                        message = "VNPT Pay: Tài khoản chưa có ví VNPT Pay"
                        break
                    case "400"://Dữ liệu sai định dạng
                        errorCode = 400
                        message = "VNPT Pay: Dữ liệu sai định dạng"
                        break
                    default:
                        errorCode = 1
                        message = resp.errorDescription
                        break
                }
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Tạo QRCode", description = "Tạo QRCode")
    @PostMapping("/createQRCode")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse createQRCode(@RequestBody CreateQRCodeRequest body) {
        try {
            String data = null
            String message = ""
            Integer errorCode
            Map<String, Object> resp = vnptPayClient.createQRCode(body.getLoaiThanhToan(), body.getBillNumber(), body.getAmount(), body.getConsumerId(),
                    body.getPurpose(), body.getProvinceCode(), body.getAddInfo(), body.getSystemInfo(), "")
            switch (resp.errorCode) {
                case "00":
                    errorCode = 0
                    data = resp.qrcodeData
                    break
                case "94"://Hệ thống bảo trì
                    errorCode = 94
                    message = "VNPT Pay: Hệ thống đang bảo trì"
                    break
                case "99"://Lỗi hệ thống
                    errorCode = 99
                    message = "VNPT Pay: Lỗi hệ thống"
                    break
                case "300"://Lỗi trong quá trình xử lý
                    errorCode = 300
                    message = "VNPT Pay: Lỗi trong quá trình xử lý"
                    break
                case "60"://Token hết hiệu lực
                    errorCode = 60
                    message = "VNPT Pay: Token hết hiệu lực"
                    break
                case "100"://Tài khoản chưa được khai báo hoặc đang bị khóa
                    errorCode = 100
                    message = "VNPT Pay: Tài khoản chưa có ví VNPT Pay"
                    break
                case "400"://Dữ liệu sai định dạng
                    errorCode = 400
                    message = "VNPT Pay: Dữ liệu sai định dạng"
                    break
                default:
                    errorCode = 1
                    message = resp.errorDescription
                    break
            }
            if (errorCode == 0) {
                return new CommonResponse(errorCode: errorCode, faultCode: "", data: data, faultString: "Thành công")
            } else {
                return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message)
            }
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Khởi tạo giao dịch", description = "Khởi tạo giao dịch")
    @PostMapping("/initTransaction")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse initTransaction(@RequestBody InitTransactionRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String userWallet = session.get(USER_WALLET)
            String walletToken = session.get(WALLET_TOKEN)
            String employeeId = session.get(ID_NHANVIEN_DHSX)
            String uuidAsString = ((body.getRequestId() != null && body.getRequestId() != "") ? body.getRequestId() : session.get(REQUEST_ID))
            String sellingDepartmentId = body.getSellingDepartmentId() //session.get(SELLING_DEPARTMENT_ID)
            String groupId = body.getGroupId()//session.get(GROUP_ID)
            String username = session.get(USERNAME)
            String serverToken = cacheManager.getCache(Constant.VNPT_PAY_CACHE_NAME).get(userWallet.toUpperCase() + "/serverToken", String.class)
            String data = null
            String message = ""
            Integer errorCode
            if (serverToken == null) {
                errorCode = -1
                message = "Token hết hạn"
            } else {
                Map<String, Object> resp = vnptPayClient.vnptcab_debit_without_otp(uuidAsString, employeeId, groupId, userWallet, sellingDepartmentId, body.getSendWalletId(),
                        body.getSendWalletAccountId(), username, body.getTransferType(), body.getTransferContent(),
                        body.getCustomerId() != null && body.getCustomerId().trim().length() > 0 && body.getCustomerId() != "0" ? body.getCustomerId() : body.getTransferCode(), body.getTransferAmount(), walletToken, serverToken)
                switch (resp.errorCode) {
                    case "00":
                        if (resp.data) {
                            errorCode = 0
                            data = resp.data?.refTransId
                        } else {
                            errorCode = 1
                            message = "VNPT Pay: " + resp.errorDescription
                        }
                        break
                    case "94"://Hệ thống bảo trì
                        errorCode = 94
                        message = "VNPT Pay: Hệ thống đang bảo trì"
                        break
                    case "99"://Lỗi hệ thống
                        errorCode = 99
                        message = "VNPT Pay: Lỗi hệ thống"
                        break
                    case "300"://Lỗi trong quá trình xử lý
                        errorCode = 300
                        message = "VNPT Pay: Lỗi trong quá trình xử lý"
                        break
                    case "60"://Token hết hiệu lực
                        errorCode = 60
                        message = "VNPT Pay: Token hết hiệu lực"
                        break
                    case "100"://Tài khoản chưa được khai báo hoặc đang bị khóa
                        errorCode = 100
                        message = "VNPT Pay: Tài khoản chưa có ví VNPT Pay"
                        break
                    case "400"://Dữ liệu sai định dạng
                        errorCode = 400
                        message = "VNPT Pay: Dữ liệu sai định dạng"
                        break
                    default:
                        errorCode = 1
                        message = resp.errorDescription
                        break
                }
            }
            if (errorCode == 0) {
                return new CommonResponse(errorCode: errorCode, faultCode: "", data: data, faultString: "Thành công")
            } else {
                return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message)
            }
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Xác nhận giao dịch", description = "Xác nhận giao dịch")
    @PostMapping("/commitTransaction")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse commitTransaction(@RequestBody CommitTransactionRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String userWallet = session.get(USER_WALLET)
            String walletToken = session.get(WALLET_TOKEN)
            String serverToken = cacheManager.getCache(Constant.VNPT_PAY_CACHE_NAME).get(userWallet.toUpperCase() + "/serverToken", String.class)
            String message
            Integer errorCode
            if (serverToken == null) {
                errorCode = -1
                message = "Token hết hạn"
            } else {
                Map<String, Object> resp = vnptPayClient.commitTransaction(body.getTransferResult(), body.getTransferType(), body.getTransferId(),
                        body.getTransferContent(), body.getPhieuId(), body.getConfirmType(), walletToken, serverToken)
                switch (resp.errorCode) {
                    case "00":
                        errorCode = 0
                        message = ""
                        break
                    case "94"://Hệ thống bảo trì
                        errorCode = 94
                        message = "VNPT Pay: Hệ thống đang bảo trì"
                        break
                    case "99"://Lỗi hệ thống
                        errorCode = 99
                        message = "VNPT Pay: Lỗi hệ thống"
                        break
                    case "300"://Lỗi trong quá trình xử lý
                        errorCode = 300
                        message = "VNPT Pay: Lỗi trong quá trình xử lý"
                        break
                    case "60"://Token hết hiệu lực
                        errorCode = 60
                        message = "VNPT Pay: Token hết hiệu lực"
                        break
                    case "100"://Tài khoản chưa được khai báo hoặc đang bị khóa
                        errorCode = 100
                        message = "VNPT Pay: Tài khoản chưa có ví VNPT Pay"
                        break
                    case "400"://Dữ liệu sai định dạng
                        errorCode = 400
                        message = "VNPT Pay: Dữ liệu sai định dạng"
                        break
                    default:
                        errorCode = 1
                        message = resp.errorDescription
                        break
                }
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Hoàn ví", description = "Hoàn ví")
    @PostMapping("/cancelTransaction")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse cancelTransaction(@RequestBody CancelTransactionRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String username = session.get(USERNAME)
            String message = ""
            String data = ""
            Integer errorCode
            Map<String, Object> resp = vnptPayClient.vnptcab_cancel_transaction("", "", body.getCancelType(), body.getAmount(),
                    body.getDescription(), body.getRefTransId(), username)
            switch (resp.errorCode) {
                case "00":
                    errorCode = 0
                    data = resp.transactionId
                    break
                case "94"://Hệ thống bảo trì
                    errorCode = 94
                    message = "VNPT Pay: Hệ thống đang bảo trì"
                    break
                case "99"://Lỗi hệ thống
                    errorCode = 99
                    message = "VNPT Pay: Lỗi hệ thống"
                    break
                case "300"://Lỗi trong quá trình xử lý
                    errorCode = 300
                    message = "VNPT Pay: Lỗi trong quá trình xử lý"
                    break
                case "60"://Token hết hiệu lực
                    errorCode = 60
                    message = "VNPT Pay: Token hết hiệu lực"
                    break
                case "100"://Tài khoản chưa được khai báo hoặc đang bị khóa
                    errorCode = 100
                    message = "VNPT Pay: Tài khoản chưa có ví VNPT Pay"
                    break
                case "400"://Dữ liệu sai định dạng
                    errorCode = 400
                    message = "VNPT Pay: Dữ liệu sai định dạng"
                    break
                default:
                    errorCode = 1
                    message = resp.errorDescription
                    break
            }

            if (errorCode == 0) {
                return new CommonResponse(errorCode: errorCode, faultCode: "", data: data, faultString: "Thành công")
            } else {
                return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message)
            }
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    /**
     * @Author VietNH* @Description Thanh toán gạch nợ bán hàng
     * @param hrmId
     * @return
     */
    @Operation(summary = "Bán hàng", description = "Thanh toán gạch nợ bán hàng")
    @PostMapping("/banHangTransaction")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    BanHangTransactionResponse banHangTransaction(@RequestBody BanHangTransactionRequest body) {

        BanHangTransactionResponse ret = new BanHangTransactionResponse()
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String userWallet = session.get(USER_WALLET)
            String walletToken = session.get(WALLET_TOKEN)
            String serverToken = cacheManager.getCache(Constant.VNPT_PAY_CACHE_NAME).get(userWallet.toUpperCase() + "/serverToken", String.class)
            if (serverToken == null) {
                ret.error = ErrorCodeDefine.ThuCuoc.AUTHORIZATION_VNPT_PAY_EMPTY
                ret.message = "OneApp: Token expired"
            } else {
                Map<String, Object> resp = vnptPayClient.banhang_debit_account_immediately(body.getClient_request(), body.getEmployeeId(), body.getGroupId(), body.getPaymentCode(), body.getSellingDepartmentId(), body.getSendWalletId(), body.getSendWalletAccountId(),
                        body.getTransferType(), body.getTransferContent(), body.getAgencyId(), body.getTransferAmount(), new Gson().toJson(body.getAdditionalInfo()), walletToken, serverToken)
                ret.status = resp.errorCode
                ret.statusMessage = resp.errorDescription
                switch (resp.errorCode) {
                    case "00":
                        if (resp.data) {
                            ret.error = ErrorCodeDefine.SUCCESS
                            ret.message = MessageUtils.SUCCESS
                            ret.refTransId = resp.data?.refTransId
                        } else {
                            ret.error = ErrorCodeDefine.ThuCuoc.VNPT_PAY_FAIL
                            ret.message = "VNPT Pay: " + resp.errorDescription
                        }
                        break
                    case "94"://Hệ thống bảo trì
                        ret.error = ErrorCodeDefine.ThuCuoc.VNPT_PAY_MAINTAINING
                        ret.message = "VNPT Pay: Hệ thống đang bảo trì"
                        break
                    case "99"://Lỗi hệ thống
                        ret.error = ErrorCodeDefine.ThuCuoc.VNPT_PAY_ERROR
                        ret.message = "VNPT Pay: Lỗi hệ thống"
                        break
                    case "300"://Lỗi trong quá trình xử lý
                        ret.error = ErrorCodeDefine.ThuCuoc.VNPT_PAY_FAIL
                        ret.message = resp.errorDescription
                        break
                    case "60"://Token hết hiệu lực
                        ret.error = ErrorCodeDefine.ThuCuoc.WALLET_TOKEN_INVALID
                        ret.message = "VNPT Pay: Token hết hiệu lực"
                        break
                    case "400"://Dữ liệu sai định dạng
                        ret.error = ErrorCodeDefine.ThuCuoc.WALLET_BAD_REQUEST
                        ret.message = "VNPT Pay: Dữ liệu sai định dạng"
                        break
                    default:
                        ret.error = ErrorCodeDefine.ThuCuoc.INIT_TRANSACTION
                        ret.message = "VNPTPAY: " + resp.errorDescription
                        break
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace()
            ret.error = ErrorCodeDefine.ThuCuoc.INTERNAL_SERVER_ERROR
            ret.message = "VNPTPAY: " + ex.getMessage()
        }
        return ret

    }

    /**
     * @Author VietNH* @Description Khởi tạo thanh toán
     * @param hrmId
     * @return
     */
    @Operation(summary = "Bán hàng", description = "Thanh toán gạch nợ bán hàng")
    @PostMapping("/inquirePartner")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    InquirePartnerResponse inquire_partner(@RequestBody InquirePartnerTransactionRequest body) {
        InquirePartnerResponse ret = new InquirePartnerResponse()
        try {
            Map<String, Object> resp = vnptPayClient.inquire_partner(body.getPaymentCode(), body.getAgencyId(), body.getEmployeeId(), body.getGroupId(), body.getSellingDepartmentId(), body.getRequestId(), body.getRequestDate(), body.getContractType(), body.getTong_tien(),
                    body.getKh_nv(), body.getThanh_toan(), body.getBill_amount(), body.getPayment_method(), body.getPhanvung_id(), body.getUser(), body.getIdentifycode())
            ret.status = resp.RESPONSE_CODE
            ret.statusMessage = resp.DESCRIPTION
            switch (resp.RESPONSE_CODE) {
                case "00":
                    ret.error = ErrorCodeDefine.SUCCESS
                    ret.message = MessageUtils.SUCCESS
                    ret.description = resp.DESCRIPTION
                    break
                case "94"://Hệ thống bảo trì
                    ret.error = ErrorCodeDefine.ThuCuoc.VNPT_PAY_MAINTAINING
                    ret.message = "VNPT Pay: Hệ thống đang bảo trì"
                    break
                case "99"://Lỗi hệ thống
                    ret.error = ErrorCodeDefine.ThuCuoc.VNPT_PAY_ERROR
                    ret.message = "VNPT Pay: Lỗi hệ thống"
                    break
                case "300"://Lỗi trong quá trình xử lý
                    ret.error = ErrorCodeDefine.ThuCuoc.VNPT_PAY_FAIL
                    ret.message = resp.errorDescription
                    break
                case "60"://Token hết hiệu lực
                    ret.error = ErrorCodeDefine.ThuCuoc.WALLET_TOKEN_INVALID
                    ret.message = "VNPT Pay: Token hết hiệu lực"
                    break
                case "400"://Dữ liệu sai định dạng
                    ret.error = ErrorCodeDefine.ThuCuoc.WALLET_BAD_REQUEST
                    ret.message = "VNPT Pay: Dữ liệu sai định dạng"
                    break
                default:
                    ret.error = ErrorCodeDefine.ThuCuoc.INIT_TRANSACTION
                    ret.message = "VNPTPAY: " + resp.errorDescription
                    break
            }
        } catch (Exception ex) {
            ex.printStackTrace()
            ret.error = ErrorCodeDefine.ThuCuoc.INTERNAL_SERVER_ERROR
            ret.message = "VNPTPAY: " + ex.getMessage()
        }
        return ret
    }

    @Operation(summary = "Ví VNPT PAY", description = "Kiểm tra tài khoản ví VNPT PAY có tồn tại")
    @GetMapping("/checkUserPayByPhone")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse checkUserPayByPhone(@NotNull @Parameter(in = ParameterIn.QUERY, description = "phoneNumber", required = true, schema = @Schema()) @Valid @RequestParam(value = "phoneNumber", required = true) String phoneNumber) {
        try {
            Map<String, Object> resp = vnptPayClient.checkUserPayByPhone(phoneNumber)
            if (resp.errorCode == "00") {
                return new CommonResponse(errorCode: 0, faultCode: "", data: resp.data, faultString: "Thành công")
            } else {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: resp.errorDescription)
            }
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Ví VNPT PAY", description = "Kiểm tra tài khoản ví VNPT PAY có tồn tại")
    @GetMapping("/openAppVNPTPayAgent")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> openAppVNPTPayAgent(@NotNull @Parameter(in = ParameterIn.QUERY, description = "phoneNumber", required = true, schema = @Schema()) @Valid @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                                     @NotNull @Parameter(in = ParameterIn.QUERY, description = "walletToken", required = true, schema = @Schema()) @Valid @RequestParam(value = "walletToken", required = true) String walletToken) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String app_id = session.get(genericApi.APP_ID)
            String device = "AND"
            if (app_id == "2") {
                device = "IOS"
            }
            Map<String, Object> resp = vnptPayClient.openAppVNPTPayAgent(phoneNumber, walletToken, device)
            if (resp.errorCode == "00") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(resp.result, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, resp.errorDescription.toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception ex) {
            ex.printStackTrace()
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, ex.getMessage().toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Operation(summary = "Ví VNPT PAY", description = "Kiểm tra tài khoản ví VNPT PAY có tồn tại")
    @GetMapping("/openAppVNPTPayAgent_v2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> openAppVNPTPayAgent_v2(@NotNull @Parameter(in = ParameterIn.QUERY, description = "phoneNumber", required = true, schema = @Schema()) @Valid @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                                     @NotNull @Parameter(in = ParameterIn.QUERY, description = "walletToken", required = true, schema = @Schema()) @Valid @RequestParam(value = "walletToken", required = true) String walletToken) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String app_id = session.get(genericApi.APP_ID)
            String device = "AND"
            if (app_id == "2") {
                device = "IOS"
            }
            Map<String, Object> resp = vnptPayClient.openAppVNPTPayAgent(phoneNumber, walletToken, device)
            if (resp.errorCode == "00") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(resp.links, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, resp.errorDescription.toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception ex) {
            ex.printStackTrace()
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, ex.getMessage().toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Operation(summary = "Ví VNPT PAY", description = "Kiểm tra user ví")
    @GetMapping("/checkWalletInfo")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> checkWalletInfo(@NotNull @Parameter(in = ParameterIn.QUERY, description = "user_vi", required = true, schema = @Schema()) @Valid @RequestParam(value = "user_vi", required = true) String user_vi) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String serverToken = "eyJ0eXAiOiJqd3QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJ1bmtub3duIiwidHBzIjoiMTAwMCIsInJvbGVzIjpbXSwiaXNzIjoibHVvbmdodiIsInJlc291cmNlcyI6WyJnL2NoZWNrX3dhbGxldF9pbmZvIl0sImNhdGVnb3JpZXMiOltdLCJleHAiOjAsInN5cyI6ImRoc3giLCJpcHMiOlsiKiJdLCJqdGkiOiJkZDZjNzY0ZS1jOGRkLTRkMTUtYWZiOC1lOWI5Mzc1NmJiZjUifQ==.y0mCTQAn+CBQr4LXq4rPFiYodYWmDwkgDBdY4rDRtHOJIZ5WsO95dwb0XI5MQpgp4XL9KfOuCCI4U/3cYcO20w=="
            Map<String, Object> resp = vnptPayClient.checkWalletInfo(serverToken, user_vi)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception ex) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, ex.getMessage().toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

//    @Operation(summary = "Xác nhận giao dịch", description = "Xác nhận giao dịch")
//    @PostMapping("/commitTransaction2")
//    @Consumes([MediaType.APPLICATION_JSON])
//    @Produces([MediaType.APPLICATION_JSON])
//    ResponseEntity<BaseResponse> commitTransaction2(@RequestBody CommitTransactionRequest body) {
//        try {
//            Map<String, Object> session = genericApi.getSessionData(request)
//            String userWallet = session.get(USER_WALLET)
//            String walletToken = session.get(WALLET_TOKEN)
//            String serverToken = cacheManager.getCache(Constant.VNPT_PAY_CACHE_NAME).get(userWallet.toUpperCase() + "/serverToken", String.class)
//            String message
//            Integer errorCode
//            if (serverToken == null) {
//                errorCode = -1
//                message = "Token hết hạn"
//            } else {
//                String resp = vnptPayClient.commitTransaction2(body.getTransferResult(), body.getTransferType(), body.getTransferId(),
//                        body.getTransferContent(), body.getPhieuId(), body.getConfirmType(), walletToken, serverToken)
//                return ResponseEntity.ok().body(resp)
//            }
//            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message)
//        } catch (Exception ex) {
//            ex.printStackTrace()
//            return CommonResponse.resolveException(ex, loggingManaged)
//        }
//    }

}
