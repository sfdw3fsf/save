package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.xml.StreamingMarkupBuilder
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.ep.Vnpt34InvoiceClient
import vn.vnptit.sapi.oneapp.ep.VnptInvoiceClient
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.InvoiceCommonResponse
import vn.vnptit.sapi.oneapp.models.einvoice.request.*
import vn.vnptit.sapi.oneapp.service.HopDongService
import vn.vnptit.sapi.oneapp.service.TraCuuService
import vn.vnptit.sapi.oneapp.util.GenericApi
import vn.vnptit.sapi.oneapp.util.LoggingInOut

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

import static vn.vnptit.sapi.oneapp.util.GenericApi.CSS_SCH

@RestController
@RequestMapping("vnptinvoice")
class Vnpt34InvoiceService {

    @Autowired
    Vnpt34InvoiceClient vnptInvoiceClient
    @Autowired
    VnptInvoiceClient vnpt63InvoiceClient
    @Autowired
    GenericApi genericApi
    @Autowired
    HopDongService hopDongService

    @Autowired
    TraCuuService traCuuService
    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    Vnpt34InvoiceService(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }

    private final HttpServletRequest request
    private final ObjectMapper objectMapper

    @Operation(summary = "Phát hành hóa đơn", description = "Phát hành hóa đơn")
    @PostMapping("/importAndPublishInvoice")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse importAndPublishInvoice(@RequestBody ImportAndPublishInvoiceRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.importAndPublishInv(provinceCode, body.getInvItem(), body.getPattern(), body.getSerial(), body.getConvert())
            String faultCode = resp.message
            String data = resp.data
            Integer errorCode
            if (resp.error_code == "0") {
                errorCode = 0
                data = data.split(";")[1]
            } else {
                errorCode = 1
            }
            return new CommonResponse(errorCode: errorCode, faultCode: faultCode, faultString: data, "data": resp.data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Xác nhận thanh toán", description = "Xác nhận thanh toán")
    @PostMapping("/confirmPaymentFkey")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse confirmPaymentFkey(@RequestBody ConfirmPaymentFKeyRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.hddt_confirmPaymentFkey(body.getfKey(), body.getPattern(), body.getSerial())
            String data
            Integer errorCode
            if (resp.error_code == "0") {
                errorCode = 0
                data = resp.result
            } else {
                errorCode = 1
                data = resp.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: data, "data": data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Xác nhận thanh toán VTT", description = "Xác nhận thanh toán VTT")
    @PostMapping("/confirmPaymentFkey_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse confirmPaymentFkey_VTT(@RequestBody ConfirmPaymentFKeyRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.hddt_confirmPaymentFkey_VTT(provinceCode.toUpperCase(), body.getfKey(), body.getPattern(), body.getSerial())
            String data
            Integer errorCode
            if (resp.error_code == "0") {
                errorCode = 0
                data = resp.result
            } else {
                errorCode = 1
                data = resp.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: data, "data": data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Gạch nợ hóa đơn", description = "Gạch nợ hóa đơn")
    @PostMapping("/confirmPayment")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse confirmPayment(@RequestBody ConfirmPaymentRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.confirmPayment(body?.lstInvToken)
            String data
            Integer errorCode
            String message
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Bỏ gạch nợ hóa đơn", description = "Bỏ gạch nợ hóa đơn")
    @PostMapping("/unConfirmPayment")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse unConfirmPayment(@RequestBody ConfirmPaymentRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.unConfirmPayment(body?.lstInvToken)
            String data
            Integer errorCode
            String message
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Bỏ gạch nợ hóa đơn theo fkey", description = "Bỏ gạch nợ hóa đơn theo fkey")
    @PostMapping("/unConfirmPaymentFkey")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse unConfirmPaymentFkey(@RequestBody UnConfirmPaymentFkeyRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.unConfirmPaymentFkey(body?.lstFkey)
            String data
            Integer errorCode
            String message
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Bỏ gạch nợ hóa đơn theo fkey", description = "Bỏ gạch nợ hóa đơn theo fkey")
    @PostMapping("/unConfirmPaymentFkey_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse unConfirmPaymentFkey_VTT(@RequestBody UnConfirmPaymentFkeyRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.unConfirmPaymentFkey_VTT(provinceCode.toUpperCase(), body?.lstFkey)
            String data
            Integer errorCode
            String message
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Điều chỉnh hóa đơn", description = "Điều chỉnh hóa đơn")
    @PostMapping("/adjustInvoice")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse adjustInvoice(@RequestBody AdjustInvoiceRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.adjustInvoice(provinceCode.toLowerCase(), "", "", body?.xmlInvData, body?.fkey, body?.attachFile, body?.convert)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Thay thế hóa đơn", description = "Thay thế hóa đơn")
    @PostMapping("/replaceInvoice")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse replaceInvoice(@RequestBody AdjustInvoiceRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.replaceInvoice(provinceCode.toLowerCase(), "", "", body?.xmlInvData, body?.fkey, body?.attachFile, body?.convert)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Gạch nợ hóa đơn theo fkey", description = "Gạch nợ hóa đơn theo fkey")
    @PostMapping("/confirmPaymentDetailFkey")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse confirmPaymentDetailFkey(@RequestBody ConfirmPaymentDetailFkeyRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.confirmPaymentDetailFkey(body?.lstFkey)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Phát hành hóa đơn – HÀM THAY ĐỔI", description = "Phát hành hóa đơn – HÀM THAY ĐỔI")
    @PostMapping("/importAndPublishInv")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse importAndPublishInv(@RequestBody ImportAndPublishInvRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            vnpt63InvoiceClient.init(provinceCode)

            try {
                if (body.cusCode && body.cusCode != null && body.cusCode != "") {
                    hopDongService.init(request.getHeader("Authorization"))
                    boolean ktra = hopDongService.ktra_hdong_daxuat(body?.cusCode)
                    if (ktra) {
                        return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Hóa đơn này đã được xuất!!!", data: null)
                    }

                    Map<String, Object> respC = vnptInvoiceClient.listInvByCus(provinceCode, body?.cusCode, "", "")
                    if (respC["error_code"].toString() == "0" && respC["result"].toString() != "<Data></Data>"
                            && respC["result"].toString().contains("<status>1</status>")) {
                        return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Hóa đơn này đã được xuất!!!", data: null)
                    }

                    respC = vnpt63InvoiceClient.listInvByCus(provinceCode, body?.cusCode, "", "")
                    if (respC["error_code"].toString() == "0" && respC["result"].toString() != "<Data></Data>"
                            && respC["result"].toString().contains("<status>1</status>")) {
                        return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Hóa đơn này đã được xuất!!!", data: null)
                    }
                }
            } catch (Exception exe) {

            }

            Map<String, Object> resp = vnptInvoiceClient.importAndPublishInv(provinceCode.toLowerCase(), "", "", body?.xmlInvData, body?.pattern, body?.serial, body?.convert)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Phát hành hóa đơn - HÀM THAY ĐỔI - VTT", description = "Phát hành hóa đơn - HÀM THAY ĐỔI - VTT")
    @PostMapping("/importAndPublishInv_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse importAndPublishInv_VTT(@RequestBody ImportAndPublishInvRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.importAndPublishInv_VTT(provinceCode.toUpperCase(), "", "", body?.xmlInvData, body?.pattern, body?.serial, body?.convert)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

//    @Operation(summary = "Hủy hóa đơn theo fkey", description = "Hủy hóa đơn theo fkey")
//    @PostMapping("/cancelInv")
//    @Consumes([MediaType.APPLICATION_JSON])
//    @Produces([MediaType.APPLICATION_JSON])
//    @LoggingInOut
    CommonResponse cancelInv(@RequestBody CancelInvRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.cancelInv(provinceCode.toLowerCase(), "", "", body?.fkey)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

//    @Operation(summary = "Hủy hóa đơn theo fkey", description = "Hủy hóa đơn theo fkey")
//    @PostMapping("/cancelInvNoPay")
//    @Consumes([MediaType.APPLICATION_JSON])
//    @Produces([MediaType.APPLICATION_JSON])
//    @LoggingInOut
    CommonResponse cancelInvNoPay(@RequestBody CancelInvRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.cancelInvNoPay(provinceCode.toLowerCase(), "", "", body?.fkey)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

//    @Operation(summary = "Hủy hóa đơn theo fkey", description = "Hủy hóa đơn theo fkey")
//    @PostMapping("/cancelInv_VTT")
//    @Consumes([MediaType.APPLICATION_JSON])
//    @Produces([MediaType.APPLICATION_JSON])
//    @LoggingInOut
    CommonResponse cancelInv_VTT(@RequestBody CancelInvRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.cancelInv_VTT(provinceCode.toUpperCase(), "", "", body?.fkey)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "getSerialByPattern", description = "getSerialByPattern")
    @PostMapping("/getSerialByPattern")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse getSerialByPattern(@RequestBody GetSerialByPatternRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.getSerialByPattern(body?.pattern)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "deliverInvFkey", description = "deliverInvFkey")
    @PostMapping("/deliverInvFkey")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse deliverInvFkey(@RequestBody ConfirmPaymentDetailFkeyRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.deliverInvFkey(body?.lstFkey)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Tìm kiếm hóa đơn theo fkey", description = "Tìm kiếm hóa đơn theo fkey")
    @PostMapping("/listInvByCusFkey")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse listInvByCusFkey(@RequestBody ListInvByCusFkeyRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)

            int site = 0
            Map<String, Object> resp = [:]
            String ktra = traCuuService.fn_kiemtra_hoadon_tratruoc_sau(request.getHeader("Authorization"), "0", body?.key)
            if (ktra == "1") {  // site cũ
                site = 1
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.listInvByCusFkey(body?.key, body?.fromDate, body?.toDate)
            } else {
                resp = vnptInvoiceClient.listInvByCusFkey(body?.key, body?.fromDate, body?.toDate)
            }

            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data, site: site)
        } catch (Exception ex) {
            ex.printStackTrace()
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null, site: null)
        }
    }

    @Operation(summary = "Tìm kiếm hóa đơn theo fkey", description = "Tìm kiếm hóa đơn theo fkey")
    @PostMapping("/listInvByCusFkeyNew")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse listInvByCusFkeyNew(@RequestBody ListInvByCusFkeyRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)

            int site = 0
            Map<String, Object> resp = [:]
            String ktra = traCuuService.fn_kiemtra_hoadon_tratruoc_sau(request.getHeader("Authorization"), "0", body?.key)
            if (ktra == "1") {  // site cũ
                site = 1
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.listInvByCusFkey(body?.key, body?.fromDate, body?.toDate)
            } else {
                resp = vnptInvoiceClient.listInvByCusFkey(body?.key, body?.fromDate, body?.toDate)
            }

            String message
            String data = ""
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            data = (site == 1 ? mergeXmlWithSite("", data) : mergeXmlWithSite(data, ""))
            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null)
        }
    }

    @Operation(summary = "Tìm kiếm hóa đơn theo fkey VTT", description = "Tìm kiếm hóa đơn theo fkey VTT")
    @PostMapping("/listInvByCusFkey_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse listInvByCusFkey_VTT(@RequestBody ListInvByCusFkeyRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            int site = 0
            Map<String, Object> resp = vnptInvoiceClient.listInvByCusFkey_VTT(provinceCode.toUpperCase(), body?.key, body?.fromDate, body?.toDate)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.listInvByCusFkey_VTT(provinceCode.toUpperCase(), body?.key, body?.fromDate, body?.toDate)
                if (resp?.error_code == "0") {
                    errorCode = 0
                    data = resp?.result
                    message = resp?.message
                    site = 1
                }
            }
            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data, site: site)
        } catch (Exception ex) {
            ex.printStackTrace()
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null, site: null)
        }
    }

    @Operation(summary = "Tìm kiếm hóa đơn theo fkey VTT", description = "Tìm kiếm hóa đơn theo fkey VTT")
    @PostMapping("/listInvByCusFkeyNew_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse listInvByCusFkeyNew_VTT(@RequestBody ListInvByCusFkeyRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            int site = 0
            Map<String, Object> resp = vnptInvoiceClient.listInvByCusFkey_VTT(provinceCode.toUpperCase(), body?.key, body?.fromDate, body?.toDate)
            String message
            String data = ""
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.listInvByCusFkey_VTT(provinceCode.toUpperCase(), body?.key, body?.fromDate, body?.toDate)
                if (resp?.error_code == "0") {
                    errorCode = 0
                    data = resp?.result
                    message = resp?.message
                    site = 1
                }
            }
            data = (site == 1 ? mergeXmlWithSite("", data) : mergeXmlWithSite(data, ""))
            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null)
        }
    }

    @Operation(summary = "Tìm kiếm hóa đơn theo khách hàng", description = "Tìm kiếm hóa đơn theo khách hàng")
    @PostMapping("/listInvByCus")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse listInvByCus(@RequestBody ListInvByCusRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.listInvByCus(provinceCode, body?.cusCode, body?.fromDate, body?.toDate)
            String message
            String data = ""
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }

            // try {
            //     vnpt63InvoiceClient.init(provinceCode)
            //     Map<String, Object> resp63 = vnpt63InvoiceClient.listInvByCus(provinceCode, body?.cusCode, body?.fromDate, body?.toDate)
            //     if (resp63?.error_code == "0") {
            //         data = mergeXml(data, resp63?.result as String)
            //     }
            // } catch (Exception ignored) {
            // }

            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Tìm kiếm hóa đơn theo khách hàng", description = "Tìm kiếm hóa đơn theo khách hàng")
    @PostMapping("/listInvByCus_PV")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse listInvByCus_PV(@RequestBody ListInvByCusRequest body) {
        try {
            if (request.getHeader("Authorization")) {
                return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: "Bạn không có quyền thực hiện chức năng này", data: null, site: null)
            }

            vnptInvoiceClient.init(body.matinh)
            Map<String, Object> resp = vnptInvoiceClient.listInvByCus(body.matinh, body?.cusCode, body?.fromDate, body?.toDate)
            String message
            String data = ""
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }

            // try {
            //     vnpt63InvoiceClient.init(provinceCode)
            //     Map<String, Object> resp63 = vnpt63InvoiceClient.listInvByCus(provinceCode, body?.cusCode, body?.fromDate, body?.toDate)
            //     if (resp63?.error_code == "0") {
            //         data = mergeXml(data, resp63?.result as String)
            //     }
            // } catch (Exception ignored) {
            // }

            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Tìm kiếm hóa đơn theo khách hàng", description = "Tìm kiếm hóa đơn theo khách hàng")
    @PostMapping("/listInvByCusNew")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse listInvByCusNew(@RequestBody ListInvByCusRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.listInvByCus(provinceCode, body?.cusCode, body?.fromDate, body?.toDate)
            String message
            String data = ""
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }

            try {
                vnpt63InvoiceClient.init(provinceCode)
                Map<String, Object> resp63 = vnpt63InvoiceClient.listInvByCus(provinceCode, body?.cusCode, body?.fromDate, body?.toDate)
                if (resp63?.error_code == "0") {
                    data = mergeXmlWithSite(data, resp63?.result as String)
                } else {
                    data = mergeXmlWithSite(data, "")
                }
            } catch (Exception ignored) {
                data = mergeXmlWithSite(data, "")
            }

            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    String mergeXml(String xml1, String xml2) {
        if (xml1 == null || xml1.trim() == "" || xml1.trim() == "<Data></Data>") return xml2
        if (xml2 == null || xml2.trim() == "" || xml2.trim() == "<Data></Data>") return xml1

        try {
            def parser = new XmlSlurper(false, false)
            def data1 = parser.parseText(xml1)
            def data2 = parser.parseText(xml2)

            // Gom tất cả <Item> vào 1 list
            def allItems = []
            allItems.addAll(data1.Item)
            allItems.addAll(data2.Item)

            // Tạo XML mới có 1 <Data>
            def result = {
                Data {
                    allItems.each { item ->
                        mkp.yield item
                    }
                }
            }

            // Xuất ra chuỗi XML
            def finalXml = new StreamingMarkupBuilder().bind(result)
            return finalXml.toString()
        } catch (Exception ignored) {
            return xml1
        }
    }

    String mergeXmlWithSite(String xml1, String xml2) {
        // Hàm kiểm tra rỗng
        def isEmptyXml = { xml ->
            xml == null || xml.trim().isEmpty() || xml.trim().equals("<Data></Data>")
        }

        try {
            def parser = new XmlSlurper(false, false)

            // Nếu cả hai rỗng
            if (isEmptyXml(xml1) && isEmptyXml(xml2)) {
                return "<Data></Data>"
            }

            def allItems = []

            if (!isEmptyXml(xml1)) {
                def data1 = parser.parseText(xml1)
                data1.Item.each { allItems << [node: it, site: "0"] }
            }

            if (!isEmptyXml(xml2)) {
                def data2 = parser.parseText(xml2)
                data2.Item.each { allItems << [node: it, site: "1"] }
            }

            def result = {
                Data {
                    allItems.each { entry ->
                        Item {
                            // copy các node con gốc
                            entry.node.children().each { child ->
                                mkp.yield child
                            }
                            // thêm site có giá trị
                            site(entry.site)
                        }
                    }
                }
            }

            return new StreamingMarkupBuilder().bind(result).toString()

        } catch (Exception ignored) {
            return "<Data></Data>"
        }
    }

    @Operation(summary = "Tìm kiếm hóa đơn theo khách hàng VTT", description = "Tìm kiếm hóa đơn theo khách hàng VTT")
    @PostMapping("/listInvByCus_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse listInvByCus_VTT(@RequestBody ListInvByCusRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.listInvByCus_VTT(provinceCode.toUpperCase(), body?.cusCode, body?.fromDate, body?.toDate)
            String message
            String data = ""
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }

            try {
                vnpt63InvoiceClient.init(provinceCode)
                Map<String, Object> resp63 = vnpt63InvoiceClient.listInvByCus_VTT(provinceCode.toUpperCase(), body?.cusCode, body?.fromDate, body?.toDate)
                if (resp63?.error_code == "0") {
                    data = mergeXml(data, resp63?.result as String)
                }
            } catch (Exception ignored) {
            }

            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Tìm kiếm hóa đơn theo khách hàng VTT", description = "Tìm kiếm hóa đơn theo khách hàng VTT")
    @PostMapping("/listInvByCusNew_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse listInvByCusNew_VTT(@RequestBody ListInvByCusRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.listInvByCus_VTT(provinceCode.toUpperCase(), body?.cusCode, body?.fromDate, body?.toDate)
            String message
            String data = ""
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }

            try {
                vnpt63InvoiceClient.init(provinceCode)
                Map<String, Object> resp63 = vnpt63InvoiceClient.listInvByCus_VTT(provinceCode.toUpperCase(), body?.cusCode, body?.fromDate, body?.toDate)
                if (resp63?.error_code == "0") {
                    data = mergeXmlWithSite(data, resp63?.result as String)
                } else {
                    data = mergeXmlWithSite(data, "")
                }
            } catch (Exception ignored) {
                data = mergeXmlWithSite(data, "")
            }

            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Tìm kiếm hóa đơn theo khách hàng v2", description = "Tìm kiếm hóa đơn theo khách hàng v2")
    @PostMapping("/listInvByCus_v2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse listInvByCus_v2(@RequestBody ListInvByCusV2Request body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.listInvByCus_v2(body?.cusCode, body?.fromDate, body?.toDate, body?.pattern)
            String message
            String data = ""
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }

            try {
                vnpt63InvoiceClient.init(provinceCode)
                Map<String, Object> resp63 = vnpt63InvoiceClient.listInvByCus_v2(body?.cusCode, body?.fromDate, body?.toDate, body?.pattern)
                if (resp63?.error_code == "0") {
                    data = mergeXml(data, resp63?.result as String)
                }
            } catch (Exception ignored) {
            }

            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Tìm kiếm hóa đơn theo khách hàng v2", description = "Tìm kiếm hóa đơn theo khách hàng v2")
    @PostMapping("/listInvByCusNew_v2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse listInvByCusNew_v2(@RequestBody ListInvByCusV2Request body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.listInvByCus_v2(body?.cusCode, body?.fromDate, body?.toDate, body?.pattern)
            String message
            String data = ""
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }

            try {
                vnpt63InvoiceClient.init(provinceCode)
                Map<String, Object> resp63 = vnpt63InvoiceClient.listInvByCus_v2(body?.cusCode, body?.fromDate, body?.toDate, body?.pattern)
                if (resp63?.error_code == "0") {
                    data = mergeXmlWithSite(data, resp63?.result as String)
                } else {
                    data = mergeXmlWithSite(data, "")
                }
            } catch (Exception ignored) {
                data = mergeXmlWithSite(data, "")
            }

            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Tìm kiếm hóa đơn theo khách hàng v2", description = "Tìm kiếm hóa đơn theo khách hàng v2")
    @PostMapping("/listInvByCus_v2_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse listInvByCus_v2_VTT(@RequestBody ListInvByCusV2Request body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.listInvByCus_v2_VTT(provinceCode.toUpperCase(), body?.cusCode, body?.fromDate, body?.toDate, body?.pattern)
            String message
            String data = ""
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }

            try {
                vnpt63InvoiceClient.init(provinceCode)
                Map<String, Object> resp63 = vnpt63InvoiceClient.listInvByCus_v2_VTT(provinceCode.toUpperCase(), body?.cusCode, body?.fromDate, body?.toDate, body?.pattern)
                if (resp63?.error_code == "0") {
                    data = mergeXml(data, resp63?.result as String)
                }
            } catch (Exception ignored) {
            }

            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Tìm kiếm hóa đơn theo khách hàng v2", description = "Tìm kiếm hóa đơn theo khách hàng v2")
    @PostMapping("/listInvByCusNew_v2_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse listInvByCusNew_v2_VTT(@RequestBody ListInvByCusV2Request body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.listInvByCus_v2_VTT(provinceCode.toUpperCase(), body?.cusCode, body?.fromDate, body?.toDate, body?.pattern)
            String message
            String data = ""
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }

            try {
                vnpt63InvoiceClient.init(provinceCode)
                Map<String, Object> resp63 = vnpt63InvoiceClient.listInvByCus_v2_VTT(provinceCode.toUpperCase(), body?.cusCode, body?.fromDate, body?.toDate, body?.pattern)
                if (resp63?.error_code == "0") {
                    data = mergeXmlWithSite(data, resp63?.result as String)
                } else {
                    data = mergeXmlWithSite(data, "")
                }
            } catch (Exception ignored) {
                data = mergeXmlWithSite(data, "")
            }

            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "PortalService", description = "Lấy thông tin chi tiết hóa đơn, không kiểm tra trạng thái thanh toán")
    @PostMapping("/getInvViewNoPay")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getInvViewNoPay(@RequestBody GetInvViewNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)

            int site = 0
            Map<String, Object> resp = [:]
            String ktra = traCuuService.fn_kiemtra_hoadon_tratruoc_sau(request.getHeader("Authorization"), "1", body?.invToken)
            if (ktra == "1") {  // site cũ
                site = 1
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.getInvViewNoPay(body?.invToken)
            } else {
                resp = vnptInvoiceClient.getInvViewNoPay(body?.invToken)
            }

            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data, site: site)
        } catch (Exception ex) {
            ex.printStackTrace()
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null, site: null)
        }
    }

    @Operation(summary = "PortalService", description = "Lấy thông tin chi tiết hóa đơn, không kiểm tra trạng thái thanh toán")
    @PostMapping("/getInvViewNoPay_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getInvViewNoPay_VTT(@RequestBody GetInvViewNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.getInvViewNoPay_VTT(provinceCode.toUpperCase(), body?.invToken)
            String message
            String data
            Integer errorCode
            int site = 0
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.getInvViewNoPay_VTT(provinceCode.toUpperCase(), body?.invToken)
                if (resp?.error_code == "0") {
                    errorCode = 0
                    data = resp?.result
                    message = resp?.message
                    site = 1
                }
            }
            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data, site: site)
        } catch (Exception ex) {
            ex.printStackTrace()
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null, site: null)
        }
    }

    @Operation(summary = "PortalService", description = "Lấy thông tin chi tiết của hóa đơn theo fkey, không kiểm tra trạng thái thanh toán")
    @PostMapping("/getInvViewFkeyNoPay")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getInvViewFkeyNoPay(@RequestBody GetInvViewFkeyNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)

            int site = 0
            Map<String, Object> resp = [:]
            String ktra = traCuuService.fn_kiemtra_hoadon_tratruoc_sau(request.getHeader("Authorization"), "0", body?.fkey)
            if (ktra == "1") {  // site cũ
                site = 1
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.getInvViewFkeyNoPay(body?.fkey)
            } else {
                resp = vnptInvoiceClient.getInvViewFkeyNoPay(body?.fkey)
            }

            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data, site: site)
        } catch (Exception ex) {
            ex.printStackTrace()
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null, site: null)
        }
    }

    @Operation(summary = "PortalService", description = "Lấy thông tin chi tiết của hóa đơn theo fkey, không kiểm tra trạng thái thanh toán")
    @PostMapping("/getInvViewFkeyNoPay_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getInvViewFkeyNoPay_VTT(@RequestBody GetInvViewFkeyNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.getInvViewFkeyNoPay_VTT(provinceCode.toUpperCase(), body?.fkey)
            String message
            String data
            Integer errorCode
            int site = 0
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.getInvViewFkeyNoPay_VTT(provinceCode.toUpperCase(), body?.fkey)
                if (resp?.error_code == "0") {
                    errorCode = 0
                    data = resp?.result
                    message = resp?.message
                    site = 1
                }
            }
            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data, site: site)
        } catch (Exception ex) {
            ex.printStackTrace()
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null, site: null)
        }
    }

    @Operation(summary = "PortalService", description = "Lấy thông tin chi tiết hóa đơn")
    @PostMapping("/getInvView")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getInvView(@RequestBody GetInvViewNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)

            int site = 0
            Map<String, Object> resp = [:]
            String ktra = traCuuService.fn_kiemtra_hoadon_tratruoc_sau(request.getHeader("Authorization"), "1", body?.invToken)
            if (ktra == "1") {  // site cũ
                site = 1
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.getInvView(body?.invToken)
            } else {
                resp = vnptInvoiceClient.getInvView(body?.invToken)
            }

            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data, site: site)
        } catch (Exception ex) {
            ex.printStackTrace()
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null, site: null)
        }
    }


    @Operation(summary = "PortalService", description = "searchInv")
    @PostMapping("/searchInv")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse searchInv(@RequestBody SearchInvRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.searchInv(body?.cusCode, body?.pattern, body?.serial, body?.fromDate, body?.toDate,
                    body?.invNumber, body?.invStatus, body?.page, body?.cussignStatus, body?.payment)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "PortalService", description = "Download hóa đơn theo fkey, không kiểm tra trạng thái thanh toán")
    @PostMapping("/downloadInvFkeyNoPay")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse downloadInvFkeyNoPay(@RequestBody GetInvViewFkeyNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)

            int site = 0
            Map<String, Object> resp = [:]
            String ktra = traCuuService.fn_kiemtra_hoadon_tratruoc_sau(request.getHeader("Authorization"), "0", body?.fkey)
            if (ktra == "1") {  // site cũ
                site = 1
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.downloadInvFkeyNoPay(body?.fkey)
            } else {
                resp = vnptInvoiceClient.downloadInvFkeyNoPay(body?.fkey)
            }

            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data, site: site)
        } catch (Exception ex) {
            ex.printStackTrace()
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null, site: null)
        }
    }

    @Operation(summary = "PortalService", description = "Download hóa đơn theo fkey, không kiểm tra trạng thái thanh toán")
    @PostMapping("/downloadInvFkeyNoPay_PV")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse downloadInvFkeyNoPay_PV(@RequestBody GetInvViewFkeyNoPayRequest body) {
        try {
            if (request.getHeader("Authorization")) {
                return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: "Bạn không có quyền thực hiện chức năng này", data: null, site: null)
            }
            vnptInvoiceClient.init(body.matinh)

            int site = 0
            Map<String, Object> resp = [:]
            String ktra = traCuuService.fn_kiemtra_hoadon_tratruoc_sau(request.getHeader("Authorization"), "0", body?.fkey)
            if (ktra == "1") {  // site cũ
                site = 1
                vnpt63InvoiceClient.init(body.matinh)
                resp = vnpt63InvoiceClient.downloadInvFkeyNoPay(body?.fkey)
            } else {
                resp = vnptInvoiceClient.downloadInvFkeyNoPay(body?.fkey)
            }

            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data, site: site)
        } catch (Exception ex) {
            ex.printStackTrace()
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null, site: null)
        }
    }

    @Operation(summary = "PortalService", description = "Download hóa đơn theo fkey, không kiểm tra trạng thái thanh toán")
    @PostMapping("/downloadInvFkeyNoPay_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse downloadInvFkeyNoPay_VTT(@RequestBody GetInvViewFkeyNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.downloadInvFkeyNoPay_VTT(provinceCode.toUpperCase(), body?.fkey)
            String message
            String data
            Integer errorCode
            int site = 0
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.downloadInvFkeyNoPay_VTT(provinceCode.toUpperCase(), body?.fkey)
                if (resp?.error_code == "0") {
                    errorCode = 0
                    data = resp?.result
                    message = resp?.message
                    site = 1
                }
            }
            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data, site: site)
        } catch (Exception ex) {
            ex.printStackTrace()
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null, site: null)
        }
    }

    @Operation(summary = "PortalService", description = "Download hóa đơn")
    @PostMapping("/downloadInv")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse downloadInv(@RequestBody GetInvViewNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)

            int site = 0
            Map<String, Object> resp = [:]
            String ktra = traCuuService.fn_kiemtra_hoadon_tratruoc_sau(request.getHeader("Authorization"), "1", body?.invToken)
            if (ktra == "1") {  // site cũ
                site = 1
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.downloadInv(body?.invToken)
            } else {
                resp = vnptInvoiceClient.downloadInv(body?.invToken)
            }

            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data, site: site)
        } catch (Exception ex) {
            ex.printStackTrace()
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null, site: null)
        }
    }

    @Operation(summary = "PortalService", description = "Download hóa đơn với cả các hóa đơn chưa thanh toán")
    @PostMapping("/downloadInvNoPay")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse downloadInvNoPay(@RequestBody GetInvViewNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)

            int site = 0
            Map<String, Object> resp = [:]
            String ktra = traCuuService.fn_kiemtra_hoadon_tratruoc_sau(request.getHeader("Authorization"), "1", body?.invToken)
            if (ktra == "1") {  // site cũ
                site = 1
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.downloadInvNoPay(body?.invToken)
            } else {
                resp = vnptInvoiceClient.downloadInvNoPay(body?.invToken)
            }

            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data, site: site)
        } catch (Exception ex) {
            ex.printStackTrace()
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null, site: null)
        }
    }

    @Operation(summary = "PortalService", description = "Download hóa đơn với cả các hóa đơn chưa thanh toán")
    @PostMapping("/downloadInvNoPay_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse downloadInvNoPay_VTT(@RequestBody GetInvViewNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.downloadInvNoPay_VTT(provinceCode.toUpperCase(), body?.invToken)
            String message
            String data
            Integer errorCode
            int site = 0
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.downloadInvNoPay_VTT(provinceCode.toUpperCase(), body?.invToken)
                if (resp?.error_code == "0") {
                    errorCode = 0
                    data = resp?.result
                    message = resp?.message
                    site = 1
                }
            }
            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data, site: site)
        } catch (Exception ex) {
            ex.printStackTrace()
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null, site: null)
        }
    }

    @Operation(summary = "PortalService", description = "Download hóa đơn định dạng Pdf")
    @PostMapping("/downloadInvPDF")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse downloadInvPDF(@RequestBody DownloadInvPDFRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)

            int site = 0
            Map<String, Object> resp = [:]
            String ktra = traCuuService.fn_kiemtra_hoadon_tratruoc_sau(request.getHeader("Authorization"), "1", body?.token)
            if (ktra == "1") {  // site cũ
                site = 1
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.downloadInvPDF(body?.token)
            } else {
                resp = vnptInvoiceClient.downloadInvPDF(body?.token)
            }

            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data, site: site)
        } catch (Exception ex) {
            ex.printStackTrace()
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null, site: null)
        }
    }

    @Operation(summary = "PortalService", description = "Download hóa đơn định dạng Pdf")
    @PostMapping("/downloadInvPDFNoPay")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse downloadInvPDFNoPay(@RequestBody DownloadInvPDFRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)

            int site = 0
            Map<String, Object> resp = [:]
            String ktra = traCuuService.fn_kiemtra_hoadon_tratruoc_sau(request.getHeader("Authorization"), "1", body?.token)
            if (ktra == "1") {  // site cũ
                site = 1
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.downloadInvPDFNoPay(body?.token)
            } else {
                resp = vnptInvoiceClient.downloadInvPDFNoPay(body?.token)
            }

            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data, site: site)
        } catch (Exception ex) {
            ex.printStackTrace()
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null, site: null)
        }
    }

    @Operation(summary = "PortalService", description = "Download hóa đơn định dạng Pdf")
    @PostMapping("/downloadInvPDFNoPay_PV")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse downloadInvPDFNoPay_PV(@RequestBody DownloadInvPDFRequest body) {
        try {
            if (request.getHeader("Authorization")) {
                return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: "Bạn không có quyền thực hiện chức năng này", data: null, site: null)
            }
            vnptInvoiceClient.init(body.matinh)

            int site = 0
            Map<String, Object> resp = [:]
            String ktra = traCuuService.fn_kiemtra_hoadon_tratruoc_sau(request.getHeader("Authorization"), "1", body?.token)
            if (ktra == "1") {  // site cũ
                site = 1
                vnpt63InvoiceClient.init(body.matinh)
                resp = vnpt63InvoiceClient.downloadInvPDFNoPay(body?.token)
            } else {
                resp = vnptInvoiceClient.downloadInvPDFNoPay(body?.token)
            }

            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data, site: site)
        } catch (Exception ex) {
            ex.printStackTrace()
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null, site: null)
        }
    }

    @Operation(summary = "PortalService", description = "Download hóa đơn định dạng Pdf")
    @PostMapping("/downloadInvPDFNoPay_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse downloadInvPDFNoPay_VTT(@RequestBody DownloadInvPDFRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.downloadInvPDFNoPay_VTT(provinceCode.toUpperCase(), body?.token)
            String message
            String data
            Integer errorCode
            int site = 0
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.downloadInvPDFNoPay_VTT(provinceCode.toUpperCase(), body?.token)
                if (resp?.error_code == "0") {
                    errorCode = 0
                    data = resp?.result
                    message = resp?.message
                    site = 1
                }
            }
            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data, site: site)
        } catch (Exception ex) {
            ex.printStackTrace()
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null, site: null)
        }
    }

    @Operation(summary = "PortalService", description = "Chuyển đổi hóa đơn chứng minh nguồn gốc, xuất xứ")
    @PostMapping("/convertForVerify")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse convertForVerify(@RequestBody GetInvViewNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.convertForVerify(body?.invToken)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "PortalService", description = "Chuyển đổi lưu trữ hóa đơn")
    @PostMapping("/convertForStore")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse convertForStore(@RequestBody GetInvViewNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.convertForStore(body?.invToken)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "PortalService", description = "Chuyển đổi lưu trữ hóa đơn theo Fkey")
    @PostMapping("/convertForStoreFkey")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse convertForStoreFkey(@RequestBody GetInvViewFkeyNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.convertForStoreFkey(body?.fkey)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "PortalService", description = "Chuyển đổi lưu trữ hóa đơn theo Fkey")
    @PostMapping("/convertForStoreFkey_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse convertForStoreFkey_VTT(@RequestBody GetInvViewFkeyNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.convertForStoreFkey_VTT(provinceCode.toUpperCase(), body?.fkey)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Cập nhật dữ liệu khách hàng", description = "Cập nhật dữ liệu khách hàng")
    @PostMapping("/updateCus")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse updateCus(@RequestBody UpdateCusRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.updateCus(body?.xmlCusData, body?.convert)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Cập nhật dữ liệu khách hàng VTT", description = "Cập nhật dữ liệu khách hàng VTT")
    @PostMapping("/updateCus_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse updateCus_VTT(@RequestBody UpdateCusRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.updateCus_VTT(provinceCode.toUpperCase(), body?.xmlCusData, body?.convert)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Download hóa đơn định dạng Pdf, không kiểm tra trạng thái thanh toán", description = "Download hóa đơn định dạng Pdf, không kiểm tra trạng thái thanh toán")
    @PostMapping("/downloadInvPDFFkeyNoPay")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse downloadInvPDFFkeyNoPay(@RequestBody GetInvViewFkeyNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)

            int site = 0
            Map<String, Object> resp = [:]
            String ktra = traCuuService.fn_kiemtra_hoadon_tratruoc_sau(request.getHeader("Authorization"), "0", body?.fkey)
            if (ktra == "1") {  // site cũ
                site = 1
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.downloadInvPDFFkeyNoPay(body?.fkey)
            } else {
                resp = vnptInvoiceClient.downloadInvPDFFkeyNoPay(body?.fkey)
            }

            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data, site: site)
        } catch (Exception ex) {
            ex.printStackTrace()
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null, site: null)
        }
    }

    @Operation(summary = "Download hóa đơn định dạng Pdf, không kiểm tra trạng thái thanh toán", description = "Download hóa đơn định dạng Pdf, không kiểm tra trạng thái thanh toán")
    @PostMapping("/downloadInvPDFFkeyNoPay_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse downloadInvPDFFkeyNoPay_VTT(@RequestBody GetInvViewFkeyNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.downloadInvPDFFkeyNoPay_VTT(provinceCode.toUpperCase(), body?.fkey)
            String message
            String data
            Integer errorCode
            int site = 0
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.downloadInvPDFFkeyNoPay_VTT(provinceCode.toUpperCase(), body?.fkey)
                if (resp?.error_code == "0") {
                    errorCode = 0
                    data = resp?.result
                    message = resp?.message
                    site = 1
                }
            }
            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data, site: site)
        } catch (Exception ex) {
            ex.printStackTrace()
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null, site: null)
        }
    }

    @Operation(summary = "Chuyển đổi hóa đơn chứng minh nguồn gốc, xuất xứ theo fkey", description = "Chuyển đổi hóa đơn chứng minh nguồn gốc, xuất xứ theo fkey")
    @PostMapping("/convertForVerifyFkey")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse convertForVerifyFkey(@RequestBody GetInvViewFkeyNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.convertForVerifyFkey(body?.fkey)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Chuyển đổi hóa đơn chứng minh nguồn gốc, xuất xứ theo fkey", description = "Chuyển đổi hóa đơn chứng minh nguồn gốc, xuất xứ theo fkey")
    @PostMapping("/convertForVerifyFkey_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse convertForVerifyFkey_VTT(@RequestBody GetInvViewFkeyNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.convertForVerifyFkey_VTT(provinceCode.toUpperCase(), body?.fkey)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Điều chỉnh hóa đơn", description = "Điều chỉnh hóa đơn")
    @PostMapping("/adjustInvoiceAction")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse adjustInvoiceAction(@RequestBody AdjustInvoiceActionRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.adjustInvoiceAction(provinceCode.toLowerCase(), "", "", body?.xmlInvData, body?.fkey,
                    body?.attachFile, body?.convert, body?.pattern, body?.serial)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Điều chỉnh hóa đơn", description = "Điều chỉnh hóa đơn")
    @PostMapping("/adjustInvoiceAction_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse adjustInvoiceAction_VTT(@RequestBody AdjustInvoiceActionRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.adjustInvoiceAction_VTT(provinceCode.toUpperCase(), "", "", body?.xmlInvData, body?.fkey,
                    body?.attachFile, body?.convert, body?.pattern, body?.serial)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Thay thế hóa đơn theo fkey, pattern, serial truyền vào", description = "Thay thế hóa đơn theo fkey, pattern, serial truyền vào")
    @PostMapping("/replaceInvoiceAction")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse replaceInvoiceAction(@RequestBody AdjustInvoiceActionRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.replaceInvoiceAction(provinceCode.toLowerCase(), "", "", body?.xmlInvData, body?.fkey,
                    body?.attachFile, body?.convert, body?.pattern, body?.serial)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Thay thế hóa đơn theo fkey, pattern, serial truyền vào", description = "Thay thế hóa đơn theo fkey, pattern, serial truyền vào")
    @PostMapping("/replaceInvoiceAction_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse replaceInvoiceAction_VTT(@RequestBody AdjustInvoiceActionRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.replaceInvoiceAction_VTT(provinceCode.toUpperCase(), "", "", body?.xmlInvData, body?.fkey,
                    body?.attachFile, body?.convert, body?.pattern, body?.serial)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Điều chỉnh hóa đơn", description = "Điều chỉnh hóa đơn")
    @PostMapping("/adjustInv")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse adjustInv(@RequestBody AdjustInvRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.adjustInv(provinceCode.toLowerCase(), "", "", body?.xmlInvData, body?.fkey, body?.convert)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Điều chỉnh hóa đơn v2", description = "Điều chỉnh hóa đơn v2")
    @PostMapping("/adjustInv_v2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse adjustInv_v2(@RequestBody AdjustInvV2Request body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.adjustInv_v2(provinceCode.toLowerCase(), "", "", body?.xmlInvData, body?.fkey, body?.convert, body?.pattern, body?.serial)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Điều chỉnh hóa đơn v2", description = "Điều chỉnh hóa đơn v2")
    @PostMapping("/adjustInv_v2_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse adjustInv_v2_VTT(@RequestBody AdjustInvV2Request body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.adjustInv_v2_VTT(provinceCode.toUpperCase(), "", "", body?.xmlInvData, body?.fkey, body?.convert, body?.pattern, body?.serial)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @Operation(summary = "Thay thế hóa đơn theo fkey", description = "Thay thế hóa đơn theo fkey")
    @PostMapping("/replaceInv")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse replaceInv(@RequestBody AdjustInvRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = (session.get(CSS_SCH) as String).substring(4)
            vnptInvoiceClient.init(provinceCode)
            Map<String, Object> resp = vnptInvoiceClient.replaceInv(provinceCode.toLowerCase(), "", "", body?.xmlInvData, body?.fkey, body?.convert)
            String message
            String data
            Integer errorCode
            if (resp?.error_code == "0") {
                errorCode = 0
                data = resp?.result
                message = resp?.message
            } else {
                errorCode = resp?.error_code?.toString()?.toInteger()
                message = resp?.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data)
        } catch (Exception ex) {
            ex.printStackTrace()
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }


}
