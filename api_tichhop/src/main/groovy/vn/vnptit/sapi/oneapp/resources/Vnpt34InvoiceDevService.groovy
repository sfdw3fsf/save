package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.microsoft.playwright.Browser
import com.microsoft.playwright.Playwright
import groovy.xml.StreamingMarkupBuilder
import io.swagger.v3.oas.annotations.Operation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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

@RestController
@RequestMapping("vnptinvoice/dev")
class Vnpt34InvoiceDevService {

    private static final Logger logger = LoggerFactory.getLogger(Vnpt34InvoiceService.class)

    @Autowired
    LoggingManaged loggingManaged
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

    private Playwright playwright
    private Browser browser

    @Autowired
    Vnpt34InvoiceDevService(ObjectMapper objectMapper, HttpServletRequest request) {
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
            String provinceCode = "dev"
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

    static boolean hasItemWithStatusAndPattern(String xmlText, String patternToCheck) {
        def xml = new XmlSlurper().parseText(xmlText)
        def items = (xml.name() == 'Data') ? (xml.Item?.toList() ?: []) : (xml.Data?.Item?.toList() ?: xml.'**'.findAll { it.name()?.toString().endsWith('Item') } ?: [])

        return items.any { item ->
            item.status.text()?.trim() == "1" &&
                    item.pattern.text()?.trim() == patternToCheck
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
            String provinceCode = "dev"
            vnptInvoiceClient.init(provinceCode)
            vnpt63InvoiceClient.init(provinceCode)

            try {
                if (body.cusCode && body.cusCode != null && body.cusCode != "") {
                    // hopDongService.init(request.getHeader("Authorization"))
                    // boolean ktra = hopDongService.ktra_hdong_daxuat(body?.cusCode)
                    // if (ktra) {
                    //     return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Hóa đơn này đã được xuất!!!", data: null)
                    // }

                    Map<String, Object> respC = vnptInvoiceClient.listInvByCus(provinceCode, body?.cusCode, "", "")
                    if (respC["error_code"].toString() == "0" && respC["result"].toString() != "<Data></Data>"
                            && (hasItemWithStatusAndPattern(respC["result"].toString(), "1/023")
                            || hasItemWithStatusAndPattern(respC["result"].toString(), "1/024"))
                    ) {
                        return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Hóa đơn này đã được xuất!!!", data: null)
                    }
                }
            } catch (Exception ignored) {
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
            String provinceCode = "dev"
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
            String provinceCode = (session.get(GenericApi.CSS_SCH) as String).substring(4)
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
            String provinceCode = (session.get(GenericApi.CSS_SCH) as String).substring(4)
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
            String provinceCode = (session.get(GenericApi.CSS_SCH) as String).substring(4)
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

    @Operation(summary = "Tìm kiếm hóa đơn theo fkey", description = "Tìm kiếm hóa đơn theo fkey")
    @PostMapping("/listInvByCusFkeyNew")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse listInvByCusFkeyNew(@RequestBody ListInvByCusFkeyRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = "dev"
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
    @PostMapping("/listInvByCusFkeyNew_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse listInvByCusFkeyNew_VTT(@RequestBody ListInvByCusFkeyRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = "dev"
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
            String provinceCode = "dev"
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

    @Operation(summary = "Tìm kiếm hóa đơn theo khách hàng", description = "Tìm kiếm hóa đơn theo khách hàng")
    @PostMapping("/listInvByCusNew")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse listInvByCusNew(@RequestBody ListInvByCusRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = "dev"
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
            String provinceCode = "dev"
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
            String provinceCode = "dev"
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

    @Operation(summary = "PortalService", description = "Lấy thông tin chi tiết hóa đơn, không kiểm tra trạng thái thanh toán")
    @PostMapping("/getInvViewNoPay")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getInvViewNoPay(@RequestBody GetInvViewNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = "dev"
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
            String provinceCode = "dev"
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

    /* private BrowserType.LaunchOptions launchOptions() {
         return new BrowserType.LaunchOptions()
                 .setHeadless(true)
                 .setArgs(List.of(
                         "--no-sandbox",
                         "--disable-setuid-sandbox",
                         "--disable-dev-shm-usage",
                         "--disable-gpu",
                         "--no-zygote"
                 ));
     }

     private Page.PdfOptions pdfOptions() {
         return new Page.PdfOptions()
                 .setFormat("A4")
                 .setPrintBackground(true)
                 .setMargin(new Margin()
                         .setTop("8mm")
                         .setBottom("8mm")
                         .setLeft("8mm")
                         .setRight("8mm")
                 )
     }

     @PostConstruct
     void initPlaywright() {
         try {
             System.setProperty("playwright.browsers.path", "/opt/pw-browsers")
             playwright = Playwright.create()
             browser = playwright.chromium().launch(launchOptions())
             logger.info("Playwright initialized, Chromium version: {}", browser.version())
         } catch (Exception ex) {
             logger.error("Playwright init failed: {}", ex.message, ex)
             throw ex
         }
     }

     @PreDestroy
     void destroyPlaywright() {
         try { browser?.close() } catch (Exception ex) { logger.error("Browser close error: {}", ex.message) }
         try { playwright?.close() } catch (Exception ex) { logger.error("Playwright close error: {}", ex.message) }
     }

     private byte[] renderPdf(String htmlContent) {
         BrowserContext context = browser.newContext()
         try {
             Page page = context.newPage()
             try {
                 page.setContent(htmlContent, new Page.SetContentOptions()
                         .setWaitUntil(WaitUntilState.NETWORKIDLE)
                 )
                 return page.pdf(pdfOptions())
             } finally {
                 page.close()
             }
         } finally {
             context.close()
         }
     }*/

    @Operation(summary = "PortalService", description = "Download hóa đơn theo fkey, không kiểm tra trạng thái thanh toán")
    @PostMapping("/downloadInvFkeyNoPay")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse downloadInvFkeyNoPay(@RequestBody GetInvViewFkeyNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = "dev"
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

    @Operation(summary = "PortalService", description = "Download hóa đơn với cả các hóa đơn chưa thanh toán")
    @PostMapping("/downloadInvNoPay")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse downloadInvNoPay(@RequestBody GetInvViewNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = "dev"
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

    @Operation(summary = "Cập nhật dữ liệu khách hàng", description = "Cập nhật dữ liệu khách hàng")
    @PostMapping("/updateCus")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse updateCus(@RequestBody UpdateCusRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = "dev"
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
            String provinceCode = "dev"
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
    @LoggingInOut
    CommonResponse downloadInvPDFFkeyNoPay(@RequestBody GetInvViewFkeyNoPayRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        long tStart = System.currentTimeMillis()
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = "dev"
            vnptInvoiceClient.init(provinceCode)

            String userid = (session.get(GenericApi.USERNAME) as String)

            int site = 0
            Map<String, Object> resp = [:]

            long t1 = System.currentTimeMillis()
            String ktra = traCuuService.fn_kiemtra_hoadon_tratruoc_sau(request.getHeader("Authorization"), "0", body?.fkey)
            long tSiteCheck = System.currentTimeMillis() - t1
            logger.warn("[{}] downloadInvPDFFkeyNoPay step=siteCheck fkey={}, user={}, result={} elapsed={}ms", uuidAsString, userid, body?.fkey, ktra, tSiteCheck)

            long t2 = System.currentTimeMillis()
            if (ktra == "1") {  // site cũ
                site = 1
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.downloadInvPDFFkeyNoPay(body?.fkey)
            } else {
                resp = vnptInvoiceClient.downloadInvPDFFkeyNoPay(body?.fkey)
            }
            long tDownload = System.currentTimeMillis() - t2
            logger.warn("[{}] downloadInvPDFFkeyNoPay step=download fkey={} site={} error_code={} elapsed={}ms", uuidAsString, body?.fkey, site, resp?.error_code, tDownload)

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

            long tTotal = System.currentTimeMillis() - tStart
            logger.warn("[{}] downloadInvPDFFkeyNoPay step=done fkey={} province={} site={} siteCheck={}ms download={}ms total={}ms errorCode={}", uuidAsString, body?.fkey, provinceCode, site, tSiteCheck, tDownload, tTotal, errorCode)

            return new InvoiceCommonResponse(errorCode: errorCode, faultCode: "", faultString: message, data: data, site: site)
        } catch (Exception ex) {
            long tTotal = System.currentTimeMillis() - tStart
            logger.warn("[{}] downloadInvPDFFkeyNoPay step=error fkey={} total={}ms error={}", uuidAsString, body?.fkey, tTotal, ex.message, ex)
            return new InvoiceCommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null, site: null)
        }
    }

    @Operation(summary = "Download hóa đơn định dạng Pdf, không kiểm tra trạng thái thanh toán", description = "Download hóa đơn định dạng Pdf, không kiểm tra trạng thái thanh toán")
    @PostMapping("/downloadInvPDFFkeyNoPay_VTT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse downloadInvPDFFkeyNoPay_VTT(@RequestBody GetInvViewFkeyNoPayRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = "dev"
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

    @Operation(summary = "Điều chỉnh hóa đơn", description = "Điều chỉnh hóa đơn")
    @PostMapping("/adjustInvoiceAction")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse adjustInvoiceAction(@RequestBody AdjustInvoiceActionRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = "dev"
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

    @Operation(summary = "Thay thế hóa đơn theo fkey, pattern, serial truyền vào", description = "Thay thế hóa đơn theo fkey, pattern, serial truyền vào")
    @PostMapping("/replaceInvoiceAction")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse replaceInvoiceAction(@RequestBody AdjustInvoiceActionRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = "dev"
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

    @Operation(summary = "Điều chỉnh hóa đơn", description = "Điều chỉnh hóa đơn")
    @PostMapping("/adjustInv")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse adjustInv(@RequestBody AdjustInvRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = "dev"
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

    @Operation(summary = "Bỏ gạch nợ hóa đơn theo fkey", description = "Bỏ gạch nợ hóa đơn theo fkey")
    @PostMapping("/unConfirmPaymentFkey")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse unConfirmPaymentFkey(@RequestBody UnConfirmPaymentFkeyRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = "dev"

            Map<String, Object> resp = [:]
            String ktra = traCuuService.fn_kiemtra_hoadon_tratruoc_sau(request.getHeader("Authorization"), "0", body?.lstFkey)
            if (ktra == "1") {
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.unConfirmPaymentFkey(body?.lstFkey)
            } else {
                vnptInvoiceClient.init(provinceCode)
                resp = vnptInvoiceClient.unConfirmPaymentFkey(body?.lstFkey)
            }
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

    @Operation(summary = "Xác nhận thanh toán", description = "Xác nhận thanh toán")
    @PostMapping("/confirmPaymentFkey")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse confirmPaymentFkey(@RequestBody ConfirmPaymentFKeyRequest body) {
        try {
            Map<String, Object> session = genericApi.getSessionData(request)
            String provinceCode = "dev"

            Map<String, Object> resp = [:]
            String ktra = traCuuService.fn_kiemtra_hoadon_tratruoc_sau(request.getHeader("Authorization"), "0", body.getfKey())
            if (ktra == "1") {  // site cũ
                vnpt63InvoiceClient.init(provinceCode)
                resp = vnpt63InvoiceClient.hddt_confirmPaymentFkey(body.getfKey(), body.getPattern(), body.getSerial())
            } else {
                vnptInvoiceClient.init(provinceCode)
                resp = vnptInvoiceClient.hddt_confirmPaymentFkey(body.getfKey(), body.getPattern(), body.getSerial())
            }

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
            String provinceCode = "dev"

            Map<String, Object> resp = [:]
            String ktra = traCuuService.fn_kiemtra_hoadon_tratruoc_sau(request.getHeader("Authorization"), "0", body.getfKey())
            if (ktra == "1") {  // site cũ
                vnpt63InvoiceClient.init(provinceCode)
                vnpt63InvoiceClient.hddt_confirmPaymentFkey_VTT(provinceCode.toUpperCase(), body.getfKey(), body.getPattern(), body.getSerial())
            } else {
                vnptInvoiceClient.init(provinceCode)
                vnptInvoiceClient.hddt_confirmPaymentFkey_VTT(provinceCode.toUpperCase(), body.getfKey(), body.getPattern(), body.getSerial())
            }

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
}
