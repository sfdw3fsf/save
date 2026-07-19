package vn.vnptit.sapi.oneapp.resources

import com.google.common.collect.ImmutableMap
import com.google.gson.Gson
import groovy.json.JsonSlurper
import io.swagger.model.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import org.checkerframework.checker.units.qual.C
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.ObjectStoragesManaged
import vn.vnptit.sapi.oneapp.models.DanhMuc
import vn.vnptit.sapi.oneapp.models.smcs.model.Agent
import vn.vnptit.sapi.oneapp.models.smcs.model.Area
import vn.vnptit.sapi.oneapp.models.smcs.model.ChangeGoods
import vn.vnptit.sapi.oneapp.models.smcs.model.ChangeGoodsDetail
import vn.vnptit.sapi.oneapp.models.smcs.model.CheckMnpPi
import vn.vnptit.sapi.oneapp.models.smcs.model.CheckVina690Mnp
import vn.vnptit.sapi.oneapp.models.smcs.model.DiemBan
import vn.vnptit.sapi.oneapp.models.smcs.model.DiemBanDetail
import vn.vnptit.sapi.oneapp.models.smcs.model.GoiCuocMnp
import vn.vnptit.sapi.oneapp.models.smcs.model.HangKenh
import vn.vnptit.sapi.oneapp.models.smcs.model.InfoBbbgDoiHong
import vn.vnptit.sapi.oneapp.models.smcs.model.InfoPhieuXKDoiHong
import vn.vnptit.sapi.oneapp.models.smcs.model.InfoPi
import vn.vnptit.sapi.oneapp.models.smcs.model.LoaiKenh
import vn.vnptit.sapi.oneapp.models.smcs.model.MnpPackageIn
import vn.vnptit.sapi.oneapp.models.smcs.model.MnpPackageInPre
import vn.vnptit.sapi.oneapp.models.smcs.model.MobileMoney
import vn.vnptit.sapi.oneapp.models.smcs.model.Order
import vn.vnptit.sapi.oneapp.models.smcs.model.OrderDetail
import vn.vnptit.sapi.oneapp.models.smcs.model.PackagePI
import vn.vnptit.sapi.oneapp.models.smcs.model.Product
import vn.vnptit.sapi.oneapp.models.smcs.model.ProductDetail
import vn.vnptit.sapi.oneapp.models.smcs.model.QrServiceMnp
import vn.vnptit.sapi.oneapp.models.smcs.model.Staff
import vn.vnptit.sapi.oneapp.models.smcs.model.StockGoods
import vn.vnptit.sapi.oneapp.models.smcs.model.ThueBaoMnp
import vn.vnptit.sapi.oneapp.models.smcs.model.ThueBaoMnpPI
import vn.vnptit.sapi.oneapp.models.smcs.model.TrangThai
import vn.vnptit.sapi.oneapp.models.smcs.request.AddCashBackMnpRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.AddCommitMnpRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.AddDiemBanRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.CapNhatDiemBanMMRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.CapNhatMnpPiRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.ChamDiemBanRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.CreatedNewCardRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.DKGoiMnpRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.DKVina690MnpRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.DangKyDiemBanMMRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.DiemBanRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.EditDiemBanRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.GetPiByIdRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.HuyDiemBanMMRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.ListHDChamSocUSSDRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.OpenWebMediaRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.OrderApproveRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.OtpMnpRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.RoiDiChamSocUSSDRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.ThanhToanBHRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.ThuPhiMnpPiRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.ThueBaoMnpPIRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.ThueBaoMnpRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.TuChoiMnpPiRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.UssdRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.XNTroLaiMnpRequest
import vn.vnptit.sapi.oneapp.models.smcs.request.XNTuChoiMnpRequest
import vn.vnptit.sapi.oneapp.models.smcs.response.AgentListResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.AreaListResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.ChangeStockGoodsResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.CheckMnpPiRespone
import vn.vnptit.sapi.oneapp.models.smcs.response.CheckPermissionResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.CheckVina690MnpResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.DetailChangeStockGoodsResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.DiemBanListResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.DiemBanResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.GoiCuocMnpListResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.HDChamSocUSSD
import vn.vnptit.sapi.oneapp.models.smcs.response.HangKenhResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.InfoBbbgDoiHongRespone
import vn.vnptit.sapi.oneapp.models.smcs.response.InfoPhieuXKDoiHongRespone
import vn.vnptit.sapi.oneapp.models.smcs.response.ListHDChamSocUSSDResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.LoaiKenhResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.MnpPackageInPreResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.MnpPackageInResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.MnpPiItemResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.MobileMoneyCNDiemBanResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.MobileMoneyDKDiemBanResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.MobileMoneyListResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.OpenWebMediaResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.OrderDetailResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.OrderListResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.PackagePiRespone
import vn.vnptit.sapi.oneapp.models.smcs.response.QRCommitMnpResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.QrServiceMnpResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.SearchSerialResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.SmcsBaseResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.StatusListResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.StockGoodsResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.TTNhanVienResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.ThueBaoMnpListResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.ThueBaoMnpPIListResponse
import vn.vnptit.sapi.oneapp.models.smcs.response.XNTuChoiMnpResponse
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.Constant
import vn.vnptit.sapi.oneapp.util.Constants
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.GenericApi
import vn.vnptit.sapi.oneapp.util.LoggingInOut
import vn.vnptit.sapi.oneapp.util.MessageUtils
import vn.vnptit.sapi.oneapp.util.SMCSHelper
import vn.vnptit.sapi.oneapp.util.TelegramService
import vn.vnptit.sapi.oneapp.util.Utils

import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("smcs")
class SMCSService {
    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    HttpManaged httpManaged
    @Value("\${spring.httpservices.smcs.url}")
    String smcsUrl
    @Autowired
    GenericApi genericApi
    @Autowired
    NativeWebRequest request

    @Autowired
    ObjectStoragesManaged objectStoragesManaged

    @Autowired
    TelegramService telegramService

    @Autowired
    AppSMCSService appSMCSService

    @GetMapping("/banHang/searchSerial")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<Product>> searchSerial(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Serial", required = true, schema = @Schema()) @Valid @RequestParam(value = "serial", required = true) String serial) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Sale.SEARCH_SERIAL
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "serial": "$serial","key":"$Constant.Smcs.API_KEY"}""")
            SearchSerialResponse response = new Gson().fromJson(resp, SearchSerialResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - searchSerial: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/banHang/thanhToan")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<Void>> thanhToanBH(@RequestBody ThanhToanBHRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            if (body.geteInvoice() != null && body.geteInvoice() == 1) {
                if (body.getTaxCode() == null || body.getTaxCode().trim().length() == 0) {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Chưa có Mã số thuế", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
            }

            if (!Utils.checkMST(body.getTaxCode())) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Định dạng mã số thuế không hợp lệ, vui lòng kiểm tra lại", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            String service = Constant.Smcs.Sale.SALE
            String bodyRequest = """{"service": "$service", "ContactPhone": "$body.phone", "SaleType": "$body.type", "StockId": "$body.stockId", "PaymentType": "$body.paymentType",
                                    "XuatHDDT": "$body.eInvoice", "Email": "$body.email", "AgentCode": "$body.agentCode", "ProductDetail": "$body.productDetail", 
                                    "MaDVQHNS": "${body.maDvqhns != null ? body.maDvqhns : ""}",
                                    "CCCDan": "${body.soCccd != null ? body.soCccd : ""}",
                                    "CusAddress": "$body.address", "MaSoThue": "$body.taxCode", "CusName": "$body.name", "account": "$body.account","key":"$Constant.Smcs.API_KEY"}"""
            if (body.getIsPromotion() != null && body.getIsPromotion() == "1") {
                service = Constant.Smcs.Sale.SALE_PROMOTION
                if (body.getReasonCode() == null || body.getReasonCode().trim().length() == 0) {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Chưa có lý do khuyến mại", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
                bodyRequest = """{"service": "$service", "ContactPhone": "$body.phone", "SaleType": "$body.type", "StockId": "$body.stockId", "XuatHDDT": "$body.eInvoice", "PaymentType": "$body.paymentType",
                              "Email": "$body.email", "AgentCode": "$body.agentCode", "ProductDetail": "$body.productDetail", "CusAddress": "$body.address", 
                              "MaDVQHNS": "${body.maDvqhns != null ? body.maDvqhns : ""}",
                              "CCCDan": "${body.soCccd != null ? body.soCccd : ""}",
                              "MaSoThue": "$body.taxCode", "CusName": "$body.name", "account": "$body.account", "LyDoKM": "$body.reasonCode","key":"$Constant.Smcs.API_KEY"}"""
            }
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], bodyRequest)
            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - thanhToanBH: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/kenhBan/mobileMoney/dsDaiLy")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<List<Agent>>> dsDaiLy(@NotNull @Parameter(in = ParameterIn.QUERY, description = "account", required = true, schema = @Schema()) @Valid @RequestParam(value = "account", required = true) String account) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.MobileMoney.AGENT_LIST
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "account": "$account","key":"$Constant.Smcs.API_KEY"}""")
            AgentListResponse response = new Gson().fromJson(resp, AgentListResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dsDaiLy: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/kenhBan/mobileMoney/dsDiemBan")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<List<MobileMoney>>> dsDiemBanMM(@NotNull @Parameter(in = ParameterIn.QUERY, description = "account", required = true, schema = @Schema()) @Valid @RequestParam(value = "account", required = true) String account) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.MobileMoney.MOBILE_MONEY_LIST
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "account": "$account","key":"$Constant.Smcs.API_KEY"}""")
            MobileMoneyListResponse response = new Gson().fromJson(resp, MobileMoneyListResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dsDiemBanMM: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/kenhBan/mobileMoney/dsLoaiKenh")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<List<LoaiKenh>>> dsLoaiKenhMM() {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.MobileMoney.CHANNEL_TYPE_LIST
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], """{"service": "$service","key":"$Constant.Smcs.API_KEY"}""")
            LoaiKenhResponse response = new Gson().fromJson(resp, LoaiKenhResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dsLoaiKenhMM: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/kenhBan/mobileMoney/dsHangKenh")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<List<HangKenh>>> dsHangKenhMM(@NotNull @Parameter(in = ParameterIn.QUERY, description = "type", required = true, schema = @Schema()) @Valid @RequestParam(value = "type", required = true) String type) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.MobileMoney.CHANNEL_LIST
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "type": "$type","key":"$Constant.Smcs.API_KEY"}""")
            HangKenhResponse response = new Gson().fromJson(resp, HangKenhResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dsDiemBanMM: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/kenhBan/mobileMoney/checkDK")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<String>> checkDK(@NotNull @Parameter(in = ParameterIn.QUERY, description = "account", required = true, schema = @Schema()) @Valid @RequestParam(value = "account", required = true) String account) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.MobileMoney.CHECK_PERMISSION
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "account": "$account","key":"$Constant.Smcs.API_KEY"}""")
            CheckPermissionResponse response = new Gson().fromJson(resp, CheckPermissionResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - checkDK: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/checkHrm")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<String>> checkHrm(@NotNull @Parameter(in = ParameterIn.QUERY, description = "hrmCode", required = true, schema = @Schema()) @Valid @RequestParam(value = "hrmCode", required = true) String hrmCode) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.CHECK_HRM

            def session = genericApi.getSessionData(request)
            String EMAIL = session[genericApi.EMAIL]
            String account = EMAIL
            if (EMAIL.contains("@")) {
                account = EMAIL.split("@").first()
            }
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "hrmCode": "$hrmCode","account": "$account","key":"$Constant.Smcs.API_KEY"}""")
            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - checkHrm: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/kenhBan/mobileMoney/openWebMedia")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<String>> openWebMedia(@RequestBody OpenWebMediaRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.MobileMoney.MEDIA
            String agentInfo = """{\\"AGENT_ID\\":\\"$body.agentInfo.agentId\\",\\"TAX\\":\\"$body.agentInfo.tax\\",\\"IS_MOBILE_MONEY\\":\\"$body.agentInfo.isMobileMoney\\",\\"IS_MERCHANT\\":\\"$body.agentInfo.isMerchant\\",\\"RESELLER_CODE\\":\\"$body.agentInfo.resellerCode\\",
                                    \\"AGENT_TYPE_NAME\\":\\"$body.agentInfo.agentTypeName\\",\\"AGENT_CITY_NAME\\":\\"$body.agentInfo.agentCityName\\",\\"AGENT_COUNTY_NAME\\":\\"$body.agentInfo.agentCountyName\\",
                                    \\"LONGITUDE_ID\\":\\"$body.agentInfo.longitudeId\\",\\"TYPE\\":\\"$body.agentInfo.type\\",\\"CONTACT_NAME\\":\\"$body.agentInfo.contactName\\",\\"ELOAD_NUMBER\\":\\"$body.agentInfo.eloadNumber\\",\\"AGENT_TYPE\\":\\"$body.agentInfo.agentType\\",\\"LATITUDE_ID\\":\\"$body.agentInfo.latitudeId\\",
                                    \\"AGENT_COUNTY_ID\\":\\"$body.agentInfo.agentCountyId\\",\\"AGENT_STATE_NAME\\":\\"$body.agentInfo.agentStateName\\",\\"CONTACT_PHONE\\":\\"$body.agentInfo.contactPhone\\",\\"AGENT_CODE\\":\\"$body.agentInfo.agentCode\\",
                                    \\"AGENT_STATE_ID\\":\\"$body.agentInfo.agentStateId\\",\\"parentId\\":\\"$body.agentInfo.parentId\\",\\"IS_APPROVE\\":\\"$body.agentInfo.isApprove\\",\\"CONTACT_EMAIL\\":\\"$body.agentInfo.contactEmail\\",\\"ADDRESS\\":\\"$body.agentInfo.address\\",\\"DISPLAY_VALUE\\":\\"$body.agentInfo.displayValue\\",\\"AGENT_CITY_ID\\":\\"$body.agentInfo.agentCityId\\",
                                    \\"HRM_CODE\\":\\"$body.agentInfo.hrmCode\\",\\"TYPE_NAME\\":\\"$body.agentInfo.typeName\\"}"""
            String bodyRequest = """{"service": "$service", "agent_info": "$agentInfo","key":"$Constant.Smcs.API_KEY"}"""
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], bodyRequest)
            OpenWebMediaResponse response = new Gson().fromJson(resp, OpenWebMediaResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getLink(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - huyDiemBanMM: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/kenhBan/mobileMoney/cnDiemBan")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<String>> cnDiemBanMM(@RequestBody CapNhatDiemBanMMRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.MobileMoney.EDIT
            String agentInfo = """{\\"AGENT_ID\\":\\"$body.agentInfo.agentId\\",\\"TAX\\":\\"$body.agentInfo.tax\\",\\"IS_MOBILE_MONEY\\":\\"$body.agentInfo.isMobileMoney\\",\\"IS_MERCHANT\\":\\"$body.agentInfo.isMerchant\\",\\"RESELLER_CODE\\":\\"$body.agentInfo.resellerCode\\",
                                    \\"AGENT_TYPE_NAME\\":\\"$body.agentInfo.agentTypeName\\",\\"AGENT_CITY_NAME\\":\\"$body.agentInfo.agentCityName\\",\\"AGENT_COUNTY_NAME\\":\\"$body.agentInfo.agentCountyName\\",
                                    \\"LONGITUDE_ID\\":\\"$body.agentInfo.longitudeId\\",\\"TYPE\\":\\"$body.agentInfo.type\\",\\"CONTACT_NAME\\":\\"$body.agentInfo.contactName\\",\\"ELOAD_NUMBER\\":\\"$body.agentInfo.eloadNumber\\",\\"AGENT_TYPE\\":\\"$body.agentInfo.agentType\\",\\"LATITUDE_ID\\":\\"$body.agentInfo.latitudeId\\",
                                    \\"AGENT_COUNTY_ID\\":\\"$body.agentInfo.agentCountyId\\",\\"AGENT_STATE_NAME\\":\\"$body.agentInfo.agentStateName\\",\\"CONTACT_PHONE\\":\\"$body.agentInfo.contactPhone\\",\\"AGENT_CODE\\":\\"$body.agentInfo.agentCode\\",
                                    \\"AGENT_STATE_ID\\":\\"$body.agentInfo.agentStateId\\",\\"parentId\\":\\"$body.agentInfo.parentId\\",\\"IS_APPROVE\\":\\"$body.agentInfo.isApprove\\",\\"CONTACT_EMAIL\\":\\"$body.agentInfo.contactEmail\\",\\"ADDRESS\\":\\"$body.agentInfo.address\\",\\"DISPLAY_VALUE\\":\\"$body.agentInfo.displayValue\\",\\"AGENT_CITY_ID\\":\\"$body.agentInfo.agentCityId\\",
                                    \\"HRM_CODE\\":\\"$body.agentInfo.hrmCode\\",\\"TYPE_NAME\\":\\"$body.agentInfo.typeName\\"}"""
            String bodyRequest = """{"service": "$service", "type": "$body.type", "agent_info": "$agentInfo", "account": "$body.account","key":"$Constant.Smcs.API_KEY"}"""
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], bodyRequest)
            MobileMoneyCNDiemBanResponse response = new Gson().fromJson(resp, MobileMoneyCNDiemBanResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getLink(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - cnDiemBanMM: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/kenhBan/mobileMoney/huyDiemBan")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<Void>> huyDiemBanMM(@RequestBody HuyDiemBanMMRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.MobileMoney.CANCEL
            String agentInfo = """{\\"AGENT_ID\\":\\"$body.agentInfo.agentId\\",\\"TAX\\":\\"$body.agentInfo.tax\\",\\"IS_MOBILE_MONEY\\":\\"$body.agentInfo.isMobileMoney\\",\\"IS_MERCHANT\\":\\"$body.agentInfo.isMerchant\\",\\"RESELLER_CODE\\":\\"$body.agentInfo.resellerCode\\",
                                    \\"AGENT_TYPE_NAME\\":\\"$body.agentInfo.agentTypeName\\",\\"AGENT_CITY_NAME\\":\\"$body.agentInfo.agentCityName\\",\\"AGENT_COUNTY_NAME\\":\\"$body.agentInfo.agentCountyName\\",
                                    \\"LONGITUDE_ID\\":\\"$body.agentInfo.longitudeId\\",\\"TYPE\\":\\"$body.agentInfo.type\\",\\"CONTACT_NAME\\":\\"$body.agentInfo.contactName\\",\\"ELOAD_NUMBER\\":\\"$body.agentInfo.eloadNumber\\",\\"AGENT_TYPE\\":\\"$body.agentInfo.agentType\\",\\"LATITUDE_ID\\":\\"$body.agentInfo.latitudeId\\",
                                    \\"AGENT_COUNTY_ID\\":\\"$body.agentInfo.agentCountyId\\",\\"AGENT_STATE_NAME\\":\\"$body.agentInfo.agentStateName\\",\\"CONTACT_PHONE\\":\\"$body.agentInfo.contactPhone\\",\\"AGENT_CODE\\":\\"$body.agentInfo.agentCode\\",
                                    \\"AGENT_STATE_ID\\":\\"$body.agentInfo.agentStateId\\",\\"parentId\\":\\"$body.agentInfo.parentId\\",\\"IS_APPROVE\\":\\"$body.agentInfo.isApprove\\",\\"CONTACT_EMAIL\\":\\"$body.agentInfo.contactEmail\\",\\"ADDRESS\\":\\"$body.agentInfo.address\\",\\"DISPLAY_VALUE\\":\\"$body.agentInfo.displayValue\\",\\"AGENT_CITY_ID\\":\\"$body.agentInfo.agentCityId\\",
                                    \\"HRM_CODE\\":\\"$body.agentInfo.hrmCode\\",\\"TYPE_NAME\\":\\"$body.agentInfo.typeName\\"}"""
            String bodyRequest = """{"service": "$service", "type": "$body.type", "agent_info": "$agentInfo", "account": "$body.account","key":"$Constant.Smcs.API_KEY"}"""
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], bodyRequest)
            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - huyDiemBanMM: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/kenhBan/mobileMoney/dkDiemBan")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<String>> dkDiemBanMM(@RequestBody DangKyDiemBanMMRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.MobileMoney.REGISTER
            String bodyRequest = """{"service": "$service", "contact_name": "$body.contactName", "address": "$body.address", "longitude": "$body.longitude", "country_code": "$body.countryCode",
                                    "latitude": "$body.latitude", "contact_phone": "$body.contactPhone", "state_code": "${body.stateCode != null ? body.stateCode : ""}", "county_code": "$body.countryCode", 
                                    "type": "$body.type", "agent_type": "$body.agentType", "hrmCode": "$body.hrmCode", "parentId": "$body.parentId", "tax": "$body.tax",
                                    "maDvqhns": "${body.maDvqhns != null ? body.maDvqhns : ""}",
                                    "soCccd": "${body.soCccd != null ? body.soCccd : ""}",
                                    "province2025": "${body.province2025 != null ? body.province2025 : ""}",
                                    "county2025": "${body.county2025  != null ? body.county2025 : ""}",
                                    "is_mc": "$body.isMc", "is_mm": "$body.isMm", "email": "$body.email", "eload_number": "$body.eloadNumber", "account": "$body.account","key":"$Constant.Smcs.API_KEY"}"""
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], bodyRequest)
            MobileMoneyDKDiemBanResponse response = new Gson().fromJson(resp, MobileMoneyDKDiemBanResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getLink(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dkDiemBanMM: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/kenhBan/donHang/dsDonHang")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<List<Order>>> dsDonHang(@NotNull @Parameter(in = ParameterIn.QUERY, description = "account", required = true, schema = @Schema()) @Valid @RequestParam(value = "account", required = true) String account,
                                                        @NotNull @Parameter(in = ParameterIn.QUERY, description = "status", required = true, schema = @Schema()) @Valid @RequestParam(value = "status", required = true) String status) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Order.LIST
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "account": "$account", "status": "$status","key":"$Constant.Smcs.API_KEY"}""")
            OrderListResponse response = new Gson().fromJson(resp, OrderListResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - checkDK: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/kenhBan/donHang/ctDonHang")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<OrderDetail>> ctDonHang(@NotNull @Parameter(in = ParameterIn.QUERY, description = "orderId", required = true, schema = @Schema()) @Valid @RequestParam(value = "orderId", required = true) String orderId,
                                                        @NotNull @Parameter(in = ParameterIn.QUERY, description = "orderDate", required = true, schema = @Schema()) @Valid @RequestParam(value = "orderDate", required = true) String orderDate) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Order.DETAIL
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "order_id": "$orderId", "order_date": "$orderDate","key":"$Constant.Smcs.API_KEY"}""")
            OrderDetailResponse response = new Gson().fromJson(resp, OrderDetailResponse.class)
            if (response.getError_code() == "0") {
                OrderDetail data = new OrderDetail()
                data.setAmountInTax(response.getAmountInTax())
                data.setDeliveryDate(response.getDeliveryDate())
                data.setDiscount(response.getDiscount())
                data.setOrderNo(response.getOrderNo())
                data.setPayAmount(response.getPayAmount())
                data.setPayMethod(response.getPayMethod())
                data.setReason(response.getReason())
                data.setResult(response.getResult())
                data.setStatusOrder(response.getStatusOrder())
                data.setStatusTrans(response.getStatusTrans())
                return ResponseEntity.ok().body(BaseResponseBuilder.of(data, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - checkDK: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/kenhBan/donHang/cnDonHang")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<Void>> cnDonHang(@RequestBody OrderApproveRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Order.APPROVE
            String productDetail = "["
            if (body.getProductDetails() != null && body.getProductDetails().size() > 0) {
                for (int i = 0; i < body.getProductDetails().size(); i++) {
                    ProductDetail item = body.getProductDetails().get(i)
                    if (i == body.getProductDetails().size() - 1) {
                        productDetail += """{\\"product_code\\":\\"$item.productCode\\",\\"quantity\\":\\"$item.quantity\\"}"""
                    } else {
                        productDetail += """{\\"product_code\\":\\"$item.productCode\\",\\"quantity\\":\\"$item.quantity\\"},"""
                    }
                }
            }
            productDetail += "]"
            String bodyRequest = """{"service": "$service", "order_id": "$body.orderId", "order_date": "$body.date", "order_status": "$body.status", 
                                    "eload": "$body.eload", "product_detail": "$productDetail", "change": "$body.change", "sms_900": "$body.sms900","key":"$Constant.Smcs.API_KEY"}"""
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], bodyRequest)
            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dkDiemBanMM: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/dsTinh")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<List<Area>>> dsTinh(@NotNull @Parameter(in = ParameterIn.QUERY, description = "account", required = true, schema = @Schema()) @Valid @RequestParam(value = "account", required = true) String account) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.PROVINCE_LIST
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "account": "$account","key":"$Constant.Smcs.API_KEY"}""")
            AreaListResponse response = new Gson().fromJson(resp, AreaListResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dsTinh: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/dsQuan")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<List<Area>>> dsQuan(@NotNull @Parameter(in = ParameterIn.QUERY, description = "account", required = true, schema = @Schema()) @Valid @RequestParam(value = "account", required = true) String account,
                                                    @NotNull @Parameter(in = ParameterIn.QUERY, description = "provinceId", required = true, schema = @Schema()) @Valid @RequestParam(value = "provinceId", required = true) String provinceId) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.DISTRICT_LIST
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "account": "$account", "province_id": "$provinceId","key":"$Constant.Smcs.API_KEY"}""")
            AreaListResponse response = new Gson().fromJson(resp, AreaListResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dsQuan: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/dsPhuong")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<List<Area>>> dsPhuong(@NotNull @Parameter(in = ParameterIn.QUERY, description = "stateId", required = true, schema = @Schema()) @Valid @RequestParam(value = "stateId", required = true) String stateId) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.WARD_LIST
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "state_id": "$stateId","key":"$Constant.Smcs.API_KEY"}""")
            AreaListResponse response = new Gson().fromJson(resp, AreaListResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dsPhuong: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/thongTinNV")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<Staff>> thongTinNV(@NotNull @Parameter(in = ParameterIn.QUERY, description = "account", required = true, schema = @Schema()) @Valid @RequestParam(value = "account", required = true) String account) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.STAFF_INFO
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "account": "$account","key":"$Constant.Smcs.API_KEY"}""")
            TTNhanVienResponse response = new Gson().fromJson(resp, TTNhanVienResponse.class)
            if (response.getError_code() == "0") {
                Staff staff = response.getResult()
                Area newArea = new Area()
                newArea.setId(staff.getNewProvinceId())
                newArea.setCode(staff.getNewProvinceCode())
                newArea.setName(staff.getNewProvinceName())
                staff.setNewProvince(newArea)
                staff.setTinhId(Constants.OneBSSProvince[staff.getProvinceId()])
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/kenhBan/chamSocKB/dsDiemBan")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<List<DiemBan>>> dsDiemBan(@RequestBody DiemBanRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        def session = genericApi.getSessionData(request)
        String ma_nd = session[genericApi.USERNAME]
        long start = System.currentTimeMillis()
        try {
            String service
            String bodyRequest
            if (body.getType() == "1") {
                service = Constant.Smcs.Chanel.LIST
                bodyRequest = """{"service": "$service", "account": "$body.account", "Longitude": "$body.longitude", "Latitude": "$body.latitude","key":"$Constant.Smcs.API_KEY"}"""
            } else {
                service = Constant.Smcs.Chanel.LIST_FULL
                bodyRequest = """{"service": "$service", "radius": "$body.radius", "Longitude": "$body.longitude", "Latitude": "$body.latitude","key":"$Constant.Smcs.API_KEY"}"""
            }
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], bodyRequest)
            DiemBanListResponse response = new Gson().fromJson(resp, DiemBanListResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/kenhBan/chamSocKB/chamDiemBan")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<Void>> chamDiemBan(@RequestBody ChamDiemBanRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Chanel.CARE
            String bodyRequest = """{"service": "$service", "account": "$body.account", "desc": "$body.description", "agentCode": "$body.agentCode","key":"$Constant.Smcs.API_KEY"}"""
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], bodyRequest)
            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dkDiemBanMM: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/kenhBan/chamSocKB/ussd")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<Void>> ussd(@RequestBody UssdRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Chanel.CARE_USSD
            String bodyRequest = """{"service": "$service", "account": "$body.account", "agentCode": "$body.agentCode","key":"$Constant.Smcs.API_KEY"}"""
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], bodyRequest)
            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - ussd: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/kenhBan/chamSocKB/listHDChamSocUSSD")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<List<ListHDChamSocUSSDResponse>>> listHDChamSocUSSD(@RequestBody ListHDChamSocUSSDRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Chanel.LIST_HD_CHAMSOC_USSD
            String bodyRequest = """{"service": "$service", "account": "$body.account", "agentCode": "$body.agentCode","key":"$Constant.Smcs.API_KEY", "trangThai": "$body.trangThai", "dateFrom": "$body.dateFrom", "dateTo": "$body.dateTo", "ma_tinh": "$body.ma_tinh"}"""
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], bodyRequest)
            ListHDChamSocUSSDResponse response = new Gson().fromJson(resp, ListHDChamSocUSSDResponse.class)
            if (response.getError_code() == "0") {
                List<HDChamSocUSSD> lst = response.getResult()
                if (lst.size() > 0) {
                    for (HDChamSocUSSD item : lst) {
                        if (item.getImg_path_minio() != null && item.getImg_path_minio() != "" && !item.getImg_path_minio().contains("không tồn tại")
                                && !item.getImg_path_minio().contains("Không có file")) {
                            item.setImg_path_minio(objectStoragesManaged.getUrl(item.getImg_path_minio()))
                        }
                    }
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(lst, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - listHDChamSocUSSD: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    String resize(String picture) {
        try {
            String serverName = "onebss-develop"
            String newPicture = picture.substring(0, picture.lastIndexOf(".")) + "_new." + picture.substring(picture.lastIndexOf(".") + 1, picture.length())
            InputStream ins = objectStoragesManaged.getObject(serverName, picture)
            boolean kq = objectStoragesManaged.uploadObject(serverName, newPicture, Utils.reduceImageQuality(ins, 1, 1, 800), null)
            if (kq) {
                return newPicture
            } else {
                return picture
            }
        } catch (Exception ex) {
            return picture
        }
    }

    @PostMapping("/kenhBan/chamSocKB/roiDiChamSocUSSD")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<Void>> roiDiChamSocUSSD(@RequestBody RoiDiChamSocUSSDRequest body) {
//        UUID uuid = UUID.randomUUID()
//        String uuidAsString = uuid.toString()
//        def session = genericApi.getSessionData(request)
//        String ma_nd = session[genericApi.USERNAME]
//        Map<String, Object> inputLogData = new HashMap<>()
//        inputLogData.put("class", "SMCSSService")
//        inputLogData.put("action", "roiDiChamSocUSSD")
//        inputLogData.put("onebss_username", ma_nd)
//        inputLogData.put("body", new Gson().toJson(body))
//        try {
//            String service = Constant.Smcs.Chanel.ROIDI_CHAMSOC_USSD
//            body.picture = resize(body.picture)
//            String bodyRequest = """{"service": "$service", "account": "$body.account", "agentCode": "$body.agentCode","key":"$Constant.Smcs.API_KEY", "longitude": "$body.longitude", "latitude": "$body.latitude", "picture": "$body.picture", "ma_tinh": "$body.ma_tinh"}"""
//            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], bodyRequest)
//            inputLogData.put("resp", resp)
//            loggingManaged.info(inputLogData)
//            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)
//            if (response.getError_code() == "0") {
//                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
//            } else {
//                if (response.getMessage() != "Lần ghé thăm gần nhất đã ghi nhận thông tin rời đi!") {
//                    if (response.getMessage() == null || response.getMessage().isEmpty()) {
//                        response.setMessage("Có lỗi xảy ra trong quá trình xử lý. Xin vui lòng thử lại")
//                    }
//                }
//                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.SUCCESS))
//            }
//        } catch (Exception e) {
//            inputLogData.put("error", e.message)
//            loggingManaged.info(inputLogData)
//            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.SUCCESS))
//        }
        return appSMCSService.roiDiChamSocUSSD(body)
    }

    @GetMapping("/kenhBan/chamSocKB/ctDiemBan")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<DiemBanDetail>> ctDiemBan(@NotNull @Parameter(in = ParameterIn.QUERY, description = "account", required = true, schema = @Schema()) @Valid @RequestParam(value = "account", required = true) String account,
                                                          @NotNull @Parameter(in = ParameterIn.QUERY, description = "agentCode", required = true, schema = @Schema()) @Valid @RequestParam(value = "agentCode", required = true) String agentCode) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Chanel.DETAIL
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "AgentCode": "$agentCode", "account": "$account","key":"$Constant.Smcs.API_KEY"}""")
            DiemBanResponse response = new Gson().fromJson(resp, DiemBanResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - checkDK: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/kenhBan/chamSocKB/themDiemBan")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<Void>> themDiemBan(@RequestBody AddDiemBanRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Chanel.ADD
            String bodyRequest = """{"service": "$service", "account": "$body.account", "AgentName": "$body.agentName", "AgentCode": "$body.agentCode",
                                    "ParAgentCode": "$body.parAgentCode", "ContactName": "$body.contactName", "Address": "$body.address", "Longitude": "$body.longitude",
                                    "Latitude": "$body.latitude", "ContactPhone": "$body.contactPhone", "ContactEmail": "$body.contactEmail", "EloadNumber": "$body.eloadNumber",
                                    "MaDVQHNS": "${body.maDvqhns != null ? body.maDvqhns : ""}",
                                    "SoCCCD": "${body.soCccd != null ? body.soCccd : ""}",
                                    "province2025": "${body.province2025 != null ? body.province2025 : ""}",
                                    "county2025": "${body.county2025 != null ? body.county2025 : ""}",
                                    "MSIN": "$body.msin", "StateCode": "$body.stateCode", "CountyCode": "$body.countryCode", "CountryCode": "$body.countryCode","key":"$Constant.Smcs.API_KEY"}"""
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], bodyRequest)
            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dkDiemBanMM: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/kenhBan/chamSocKB/cnDiemBan")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<Void>> cnDiemBan(@RequestBody EditDiemBanRequest body) {
//        UUID uuid = UUID.randomUUID()
//        String uuidAsString = uuid.toString()
//        try {
//            String service = Constant.Smcs.Chanel.EDIT
//            String bodyRequest = """{"service": "$service", "account": "$body.account", "AgentName": "$body.agentName", "AgentCodeOld": "$body.agentCodeOld", "CountryCode": "$body.countryCode",
//                                    "ParAgentCode": "$body.parAgentCode", "ContactName": "$body.contactName", "Address": "$body.address", "Longitude": "$body.longitude",
//                                    "Latitude": "$body.latitude", "ContactPhone": "$body.contactPhone", "ContactEmail": "$body.contactEmail", "EloadNumber": "$body.eloadNumber",
//                                    "MSIN": "$body.msin", "StateCode": "$body.stateCode", "CountyCode": "$body.countryCode", "AgentCodeNew": "$body.agentCodeNew","key":"$Constant.Smcs.API_KEY"}"""
//            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], bodyRequest)
//            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)
//            if (response.getError_code() == "0") {
//                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
//            } else {
//                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
//            }
//        } catch (Exception e) {
//            loggingManaged.error("[SMCSService] - dkDiemBanMM: ", e)
//            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
//        }
        return appSMCSService.modifyAgent(body)
    }

    @GetMapping("/mnp/dsPhuongXa")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<List<Area>>> dsPhuongXa(@NotNull @Parameter(in = ParameterIn.QUERY, description = "account", required = true, schema = @Schema()) @Valid @RequestParam(value = "account", required = true) String account) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Mnp.WARD_LIST
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "account": "$account","key":"$Constant.Smcs.API_KEY"}""")
            AreaListResponse response = new Gson().fromJson(resp, AreaListResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dsPhuongXa: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/mnp/dsTrangThai")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<List<TrangThai>>> dsTrangThai(@NotNull @Parameter(in = ParameterIn.QUERY, description = "type", required = true, schema = @Schema()) @Valid @RequestParam(value = "type", required = true) String type) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Mnp.STATUS_LIST
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "type": "$type","key":"$Constant.Smcs.API_KEY"}""")
            StatusListResponse response = new Gson().fromJson(resp, StatusListResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dsTrangThai: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/mnp/dsNguyenNhanTC")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<List<TrangThai>>> dsNguyenNhanTC(@NotNull @Parameter(in = ParameterIn.QUERY, description = "type", required = true, schema = @Schema()) @Valid @RequestParam(value = "type", required = true) String type) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Mnp.REASON_LIST
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "type": "$type","key":"$Constant.Smcs.API_KEY"}""")
            StatusListResponse response = new Gson().fromJson(resp, StatusListResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dsNguyenNhanTC: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/mnp/dsGoiThoai")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<List<TrangThai>>> dsGoiThoai(@NotNull @Parameter(in = ParameterIn.QUERY, description = "type", required = true, schema = @Schema()) @Valid @RequestParam(value = "type", required = true) String type) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Mnp.PKG_VOICE_LIST
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "type": "$type","key":"$Constant.Smcs.API_KEY"}""")
            StatusListResponse response = new Gson().fromJson(resp, StatusListResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dsTrangThai: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/mnp/dsThueBaoMnp")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<List<ThueBaoMnp>>> dsThueBaoMnp(@RequestBody ThueBaoMnpRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Mnp.MNP_LIST
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "account": "$body.account", "area": "$body.area", "status": "$body.status", "subtype": "$body.subtype","key":"$Constant.Smcs.API_KEY"}""")
            ThueBaoMnpListResponse response = new Gson().fromJson(resp, ThueBaoMnpListResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dsThueBaoMnp: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/mnp/xnTuChoiMnp")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<Void>> xnTuChoiMnp(@RequestBody XNTuChoiMnpRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Mnp.APPROVE
            String bodyRequest = """{"service": "$service", "account": "$body.account", "poid": "$body.poid", "so_tb": "$body.soTB", 
                                        "status": "$body.status", "subtype": "$body.subtype", "note": "$body.note","key":"$Constant.Smcs.API_KEY"}"""
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], bodyRequest)
            XNTuChoiMnpResponse response = new Gson().fromJson(resp, XNTuChoiMnpResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - xnTuChoiMnp: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/mnp/xnTroLaiMnp")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<Void>> xnTroLaiMnp(@RequestBody XNTroLaiMnpRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Mnp.BACK
            String bodyRequest = """{"service": "$service", "account": "$body.account", "poid": "$body.poid", "so_tb": "$body.soTB", 
                                        "status": "$body.status", "subtype": "$body.subtype", "note": "$body.note", "packageId": "$body.packageId", "otp": "$body.otp","key":"$Constant.Smcs.API_KEY"}"""
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], bodyRequest)
            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - xnTroLaiMnp: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/mnp/dsGoiCuoc")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<List<GoiCuocMnp>>> dsGoiCuoc(@NotNull @Parameter(in = ParameterIn.QUERY, description = "soTb", required = true, schema = @Schema()) @Valid @RequestParam(value = "soTb", required = true) String soTb,
                                                             @NotNull @Parameter(in = ParameterIn.QUERY, description = "planType", required = true, schema = @Schema()) @Valid @RequestParam(value = "planType", required = true) String planType) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Mnp.PKG_LIST
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "soTb": "$soTb", "planType": "$planType","key":"$Constant.Smcs.API_KEY"}""")
            GoiCuocMnpListResponse response = new Gson().fromJson(resp, GoiCuocMnpListResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dsGoiCuoc: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/mnp/otp")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<Void>> otp(@RequestBody OtpMnpRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Mnp.OTP
            String bodyRequest = """{"service": "$service", "account": "$body.account", "customer_msisdn": "$body.msisdn","key":"$Constant.Smcs.API_KEY"}"""
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], bodyRequest)
            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - otp: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/mnp/regPackage")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<Void>> regPackage(@RequestBody DKGoiMnpRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Mnp.REG_PACKAGE
            String bodyRequest = """{"service": "$service", "account": "$body.account", "soTb": "$body.soTB", "otp": "$body.otp", "goiCuoc": "$body.goiCuoc","key":"$Constant.Smcs.API_KEY"}"""
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], bodyRequest)
            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - regPackage: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/mnp/discount")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<Void>> discount(@RequestBody DKGoiMnpRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Mnp.DISCOUNT
            String bodyRequest = """{"service": "$service", "account": "$body.account", "soTb": "$body.soTB", "otp": "$body.otp", "goiCuoc": "$body.goiCuoc","key":"$Constant.Smcs.API_KEY"}"""
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], bodyRequest)
            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - discount: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/mnp/addCashBack")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<Void>> addCashBack(@RequestBody AddCashBackMnpRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Mnp.ADD_CASH_BACK
            String bodyRequest = """{"service": "$service", "account": "$body.account", "soTb": "$body.soTB", "otp": "$body.otp","key":"$Constant.Smcs.API_KEY"}"""
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], bodyRequest)
            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - addCashBack: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/mnp/addMnpCommit")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<Void>> addMnpCommit(@RequestBody AddCommitMnpRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Mnp.ADD_COMMIT
            String bodyRequest = """{"service": "$service", "account": "$body.account", "customer_msisdn": "$body.msisdn", "otp": "$body.otp", "type": "$body.type","key":"$Constant.Smcs.API_KEY"}"""
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], bodyRequest)
            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - addMnpCommit: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/mnp/dkVina690")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<Void>> dkVina690(@RequestBody DKVina690MnpRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Mnp.DK_VINA_690
            String bodyRequest = """{"service": "$service", "account": "$body.account", "customer_msisdn": "$body.msisdn", "otp": "$body.otp","key":"$Constant.Smcs.API_KEY"}"""
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], bodyRequest)
            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dkVina690: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/mnp/qrCommit")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<String>> qrCommit(@NotNull @Parameter(in = ParameterIn.QUERY, description = "soTB", required = true, schema = @Schema()) @Valid @RequestParam(value = "soTB", required = true) String soTB) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Mnp.QR_COMMIT
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "soTb": "$soTB","key":"$Constant.Smcs.API_KEY"}""")
            QRCommitMnpResponse response = new Gson().fromJson(resp, QRCommitMnpResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - qrCommit: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/mnp/checkVina690")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<CheckVina690Mnp>> checkVina690(@NotNull @Parameter(in = ParameterIn.QUERY, description = "soTB", required = true, schema = @Schema()) @Valid @RequestParam(value = "soTB", required = true) String soTB) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Mnp.CHECK_VINA_690
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "so_tb": "$soTB","key":"$Constant.Smcs.API_KEY"}""")
            CheckVina690MnpResponse response = new Gson().fromJson(resp, CheckVina690MnpResponse.class)
            if (response.getError_code() == "0") {
                CheckVina690Mnp checkVina690Mnp = new CheckVina690Mnp()
                checkVina690Mnp.setApru_t1(response.getApru_t1())
                checkVina690Mnp.setApru_t2(response.getApru_t2())
                checkVina690Mnp.setApru_t3(response.getApru_t3())
                return ResponseEntity.ok().body(BaseResponseBuilder.of(checkVina690Mnp, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - checkVina690: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/mnp/qrService")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<QrServiceMnp>> qrService(@NotNull @Parameter(in = ParameterIn.QUERY, description = "soTB", required = true, schema = @Schema()) @Valid @RequestParam(value = "soTB", required = true) String soTB) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String service = Constant.Smcs.Mnp.QR_SERVICE
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"],
                    """{"service": "$service", "soTb": "$soTB","key":"$Constant.Smcs.API_KEY"}""")
            QrServiceMnpResponse response = new Gson().fromJson(resp, QrServiceMnpResponse.class)
            if (response.getError_code() == "0") {
                QrServiceMnp qrServiceMnp = new QrServiceMnp()
                qrServiceMnp.setPack(response.getPack())
                qrServiceMnp.setPackageinf(response.getPackageinf())
                qrServiceMnp.setArpu_1(response.getArpu_1())
                qrServiceMnp.setArpu_2(response.getArpu_2())
                qrServiceMnp.setCashbackinf(response.getCashbackinf())
                qrServiceMnp.setOtherpackage(response.getOtherpackage())
                qrServiceMnp.setCase_(response.getCase_())
                return ResponseEntity.ok().body(BaseResponseBuilder.of(qrServiceMnp, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - qrService: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/mnp/dsThueBaoMnpPI")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<List<ThueBaoMnpPI>>> dsThueBaoMnpPI(@NotNull @Parameter(in = ParameterIn.QUERY, description = "city", required = true, schema = @Schema()) @Valid @RequestParam(value = "city", required = true) String city,
                                                                    @NotNull @Parameter(in = ParameterIn.QUERY, description = "state", required = true, schema = @Schema()) @Valid @RequestParam(value = "state", required = true) String state,
                                                                    @NotNull @Parameter(in = ParameterIn.QUERY, description = "county", required = true, schema = @Schema()) @Valid @RequestParam(value = "county", required = true) String county,
                                                                    @NotNull @Parameter(in = ParameterIn.QUERY, description = "status", required = true, schema = @Schema()) @Valid @RequestParam(value = "status", required = true) String status,
                                                                    @NotNull @Parameter(in = ParameterIn.QUERY, description = "stb", required = true, schema = @Schema()) @Valid @RequestParam(value = "stb", required = true) String stb,
                                                                    @NotNull @Parameter(in = ParameterIn.QUERY, description = "account", required = true, schema = @Schema()) @Valid @RequestParam(value = "account", required = true) String account) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            Map<String, String> param = new HashMap<>([
                    "service": Constant.Smcs.Mnp.ONEBSS_MNP_PORT_IN_REQUEST,
                    "city"   : city,
                    "state"  : state,
                    "county" : county,
                    "status" : status,
                    "stb"    : stb,
                    "account": account,
                    "key"    : Constant.Smcs.API_KEY
            ])
            print(new Gson().toJson(param))
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
            ThueBaoMnpPIListResponse response = new Gson().fromJson(resp, ThueBaoMnpPIListResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dsThueBaoMnpPI: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/mnp/getPiById")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<InfoPi>> getPiById(@NotNull @Parameter(in = ParameterIn.QUERY, description = "pi_id", required = true, schema = @Schema()) @Valid @RequestParam(value = "pi_id", required = true) String pi_id) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            Map<String, String> param = new HashMap<>([
                    "service": Constant.Smcs.Mnp.ONEBSS_MNP_GET_PI_BY_ID,
                    "pi_id"  : pi_id,
                    "key"    : Constant.Smcs.API_KEY
            ])
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
            MnpPiItemResponse response = new Gson().fromJson(resp, MnpPiItemResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - getPiById: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/mnp/checkPackagePi")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<PackagePI>> checkPackagePi(@NotNull @Parameter(in = ParameterIn.QUERY, description = "so_tb", required = true, schema = @Schema()) @Valid @RequestParam(value = "so_tb", required = true) String so_tb) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            Map<String, String> param = new HashMap<>([
                    "service": Constant.Smcs.Mnp.ONEBSS_MNP_CHECK_GOICUOC,
                    "so_tb"  : so_tb,
                    "key"    : Constant.Smcs.API_KEY
            ])
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
            PackagePiRespone response = new Gson().fromJson(resp, PackagePiRespone.class)
            if (response.getError_code() == "0") {
                PackagePI item = new PackagePI()
                item.result = response.getResult()
                item.price = response.getPrice()
                return ResponseEntity.ok().body(BaseResponseBuilder.of(item, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - getPiById: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/mnp/checkMnpPi")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<CheckMnpPi>> checkMnpPi(@NotNull @Parameter(in = ParameterIn.QUERY, description = "so_tb", required = true, schema = @Schema()) @Valid @RequestParam(value = "so_tb", required = true) String so_tb) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            Map<String, String> param = new HashMap<>([
                    "service": Constant.Smcs.Mnp.ONEBSS_MNP_CHECK_YCCM,
                    "so_tb"  : so_tb,
                    "key"    : Constant.Smcs.API_KEY
            ])
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
            CheckMnpPiRespone response = new Gson().fromJson(resp, CheckMnpPiRespone.class)

            if (response.getError_code() == "0") {
                CheckMnpPi item = new CheckMnpPi()
                item.result = response.getResult()
                return ResponseEntity.ok().body(BaseResponseBuilder.of(item, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - getPiById: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/mnp/thuPhiMnpPi")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse> thuPhiMnp(@RequestBody ThuPhiMnpPiRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            Map<String, String> param = new HashMap<>([
                    "service" : Constant.Smcs.Mnp.ONEBSS_MNP_THU_PHI,
                    "sotb"    : body.getSotb(),
                    "matinh"  : body.getMatinh(),
                    "tienphi" : body.getTienphi(),
                    "ngaytt"  : body.getNgaytt(),
                    "loaitb"  : body.getLoaitb(),
                    "nguoithu": body.getNguoithu(),
                    "schema"  : body.getSchema(),
                    "tentt"   : body.getTentt(),
                    "diachitt": body.getDiachitt(),
                    "key"     : Constant.Smcs.API_KEY
            ])
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - getPiById: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/huysp/dmPhieuDoiHong")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    @Operation(summary = "Danh sách trạng thái phiếu đổi hỏng", description = "Danh sách trạng thái phiếu đổi hỏng", tags = ["Đổi, Hủy thẻ"])
    ResponseEntity<BaseResponse<List<DanhMuc>>> dmPhieuDoiHong() {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        List<DanhMuc> lsDm = []
        try {
            Map<String, String> param = new HashMap<>([
                    "service": Constant.Smcs.DoiTraHang.ONEBSS_GET_APP_DOMAIN_LIST,
                    "key"    : Constant.Smcs.API_KEY
            ])
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
            def json = new JsonSlurper().parseText(resp)
            if (json["error_code"] == "0") {
                List<Map<String, String>> lsData = json["result"]
                lsData.each { Map<String, String> item ->
                    DanhMuc dm = new DanhMuc()
                    dm.setID(item["id"].toString().toLong())
                    dm.setCODE(item["id"])
                    dm.setNAME(item["name"])
                    lsDm.add(dm)
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(lsDm, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dmPhieuDoiHong: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/huysp/dmLoaiXuatKho")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    @Operation(summary = "List loại xuất kho", description = "List loại xuất kho", tags = ["Đổi, Hủy thẻ"])
    ResponseEntity<BaseResponse<List<DanhMuc>>> dmLoaiXuatKho() {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        List<DanhMuc> lsDm = []
        try {
            Map<String, String> param = new HashMap<>([
                    "service": Constant.Smcs.DoiTraHang.ONEBSS_GET_APP_DOMAIN_GROUP_TYPE,
                    "key"    : Constant.Smcs.API_KEY
            ])
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
            def json = new JsonSlurper().parseText(resp)
            if (json["error_code"] == "0") {
                List<Map<String, String>> lsData = json["result"]
                lsData.each { Map<String, String> item ->
                    DanhMuc dm = new DanhMuc()
                    dm.setID(item["id"].toString().toLong())
                    dm.setCODE(item["id"])
                    dm.setNAME(item["name"])
                    lsDm.add(dm)
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(lsDm, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dmLoaiXuatKho: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/huysp/dmLyDoDoi")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    @Operation(summary = "List lý do đổi hỏng", description = "List lý do đổi hỏng", tags = ["Đổi, Hủy thẻ"])
    ResponseEntity<BaseResponse<List<DanhMuc>>> dmLyDoDoi() {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        List<DanhMuc> lsDm = []
        try {
            Map<String, String> param = new HashMap<>([
                    "service": Constant.Smcs.DoiTraHang.ONEBSS_GET_REASON_LIST,
                    "key"    : Constant.Smcs.API_KEY
            ])
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
            def json = new JsonSlurper().parseText(resp)
            if (json["error_code"] == "0") {
                List<Map<String, String>> lsData = json["result"]
                lsData.each { Map<String, String> item ->
                    DanhMuc dm = new DanhMuc()
                    dm.setID(item["id"].toString().toLong())
                    dm.setCODE(item["id"])
                    dm.setNAME(item["name"])
                    lsDm.add(dm)
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(lsDm, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dmLyDoDoi: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/huysp/dsKhoNhanVien")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    @Operation(summary = "List danh sách kho theo nhân viên", description = "List danh sách kho theo nhân viên", tags = ["Đổi, Hủy thẻ"])
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsKhoNhanVien(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Mã nhân viên", required = true, schema = @Schema()) @Valid @RequestParam(value = "account", required = true) String account) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        List<DanhMuc> lsDm = []
        try {
            Map<String, String> param = new HashMap<>([
                    "service": Constant.Smcs.DoiTraHang.ONEBSS_GET_STOCK_LIST,
                    "account": account.toLowerCase(),
                    "key"    : Constant.Smcs.API_KEY
            ])
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
            def json = new JsonSlurper().parseText(resp)
            if (json["error_code"] == "0") {
                List<Map<String, String>> lsData = json["result"]
                lsData.each { Map<String, String> item ->
                    DanhMuc dm = new DanhMuc()
                    dm.setID(item["id"].toString().toLong())
                    dm.setCODE(item["id"])
                    dm.setNAME(item["name"])
                    lsDm.add(dm)
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(lsDm, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dmLyDoDoi: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/huysp/dsTheCaoTrongKho")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    @Operation(summary = "List thẻ cào trong kho", description = "List thẻ cào trong kho", tags = ["Đổi, Hủy thẻ"])
    ResponseEntity<BaseResponse<List<StockGoods>>> dsTheCaoTrongKho(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Mã nhân viên", required = true, schema = @Schema()) @Valid @RequestParam(value = "account", required = true) String account,
                                                                    @NotNull @Parameter(in = ParameterIn.QUERY, description = "ID kho", required = true, schema = @Schema()) @Valid @RequestParam(value = "kho_id", required = true) String kho_id) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            Map<String, String> param = new HashMap<>([
                    "service" : Constant.Smcs.DoiTraHang.ONEBSS_GET_STOCK_GOODS,
                    "account" : account.toLowerCase(),
                    "stock_id": kho_id,
                    "key"     : Constant.Smcs.API_KEY
            ])
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
            StockGoodsResponse response = new Gson().fromJson(resp, StockGoodsResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dsTheCaoTrongKho: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/huysp/dsHangDoiTra")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    @Operation(summary = "List đơn hàng đổi hỏng hàng hóa", description = "List đơn hàng đổi hỏng hàng hóa", tags = ["Đổi, Hủy thẻ"])
    ResponseEntity<BaseResponse<List<ChangeGoods>>> dsHangDoiTra(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Mã nhân viên", required = true, schema = @Schema()) @Valid @RequestParam(value = "account", required = true) String account,
                                                                 @NotNull @Parameter(in = ParameterIn.QUERY, description = "Ngày bắt đầu tìm kiếm", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = true) String fdate,
                                                                 @NotNull @Parameter(in = ParameterIn.QUERY, description = "Ngày kết thúc tìm kiếm", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = true) String tdate,
                                                                 @NotNull @Parameter(in = ParameterIn.QUERY, description = "Trạng thái đơn hàng", required = true, schema = @Schema()) @Valid @RequestParam(value = "status", required = true) String status) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            Map<String, String> param = new HashMap<>([
                    "service": Constant.Smcs.DoiTraHang.ONEBSS_GET_LIST_PRODUCT_CHANGE,
                    "account": account.toLowerCase(),
                    "fdate"  : fdate,
                    "tdate"  : tdate,
                    "status" : status,
                    "key"    : Constant.Smcs.API_KEY
            ])
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
            ChangeStockGoodsResponse response = new Gson().fromJson(resp, ChangeStockGoodsResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dsHangDoiTra: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/huysp/detailDonHangDoiTra")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    @Operation(summary = "Lấy chi tiết đơn hàng đổi hỏng hàng hóa", description = "Lấy chi tiết đơn hàng đổi hỏng hàng hóa", tags = ["Đổi, Hủy thẻ"])
    ResponseEntity<BaseResponse<ChangeGoodsDetail>> detailDonHangDoiTra(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Mã nhân viên", required = true, schema = @Schema()) @Valid @RequestParam(value = "account", required = true) String account,
                                                                        @NotNull @Parameter(in = ParameterIn.QUERY, description = "ID đơn hàng lấy từ danh sách dơn", required = true, schema = @Schema()) @Valid @RequestParam(value = "stock_trans_id", required = true) String stock_trans_id,
                                                                        @NotNull @Parameter(in = ParameterIn.QUERY, description = "Ngày đổi trả lấy từ danh sách đơn", required = true, schema = @Schema()) @Valid @RequestParam(value = "export_date", required = true) String export_date) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            Map<String, String> param = new HashMap<>([
                    "service"       : Constant.Smcs.DoiTraHang.ONEBSS_GET_PRODUCT_CHANGE_DETAIL,
                    "account"       : account.toLowerCase(),
                    "stock_trans_id": stock_trans_id,
                    "export_date"   : export_date,
                    "key"           : Constant.Smcs.API_KEY
            ])
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
            DetailChangeStockGoodsResponse response = new Gson().fromJson(resp, DetailChangeStockGoodsResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - dsHangDoiTra: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @DeleteMapping("/huysp/cancelCard")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    @Operation(summary = "Hủy thẻ theo serial", description = "Hủy thẻ theo serial", tags = ["Đổi, Hủy thẻ"])
    ResponseEntity<BaseResponse> cancelCard(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Mã nhân viên", required = true, schema = @Schema()) @Valid @RequestParam(value = "account", required = true) String account,
                                            @NotNull @Parameter(in = ParameterIn.QUERY, description = "ID đơn hàng lấy từ danh sách dơn", required = true, schema = @Schema()) @Valid @RequestParam(value = "serial", required = true) String serial) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            Map<String, String> param = new HashMap<>([
                    "service": Constant.Smcs.DoiTraHang.ONEBSS_CANCEL_CARD_SERIAL,
                    "account": account.toLowerCase(),
                    "serial" : serial,
                    "key"    : Constant.Smcs.API_KEY
            ])
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)

            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - cancelCard: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/huysp/createdNewCard")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    @Operation(summary = "Ghi dữ liệu khi thêm mới hoặc sửa đơn đổi hỏng", description = "Ghi dữ liệu khi thêm mới hoặc sửa đơn đổi hỏng", tags = ["Đổi, Hủy thẻ"])
    ResponseEntity<BaseResponse> createdNewCard(@RequestBody CreatedNewCardRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            Map<String, String> param = new HashMap<>([
                    "service"      : Constant.Smcs.DoiTraHang.ONEBSS_PRODUCT_SAVE_CHANGE,
                    "account"      : body.getAccount().toLowerCase(),
                    "action"       : body.getAction(),
                    "trans_id"     : body.getTrans_id(),
                    "trans_date"   : body.getTrans_date(),
                    "trans_no"     : body.getTrans_no(),
                    "stock_id"     : body.getStock_id(),
                    "reason_id"    : body.getReason_id(),
                    "cus_info"     : body.getCus_info(),
                    "detail_list"  : body.getDetail_list(),
                    "detail_serial": body.getDetail_serial(),
                    "key"          : Constant.Smcs.API_KEY
            ])
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - createdNewCard: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/huysp/changeSuccess")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    @Operation(summary = "Thực hiện hủy hoặc hoàn tất đơn đổi hỏng", description = "Thực hiện hủy hoặc hoàn tất đơn đổi hỏng", tags = ["Đổi, Hủy thẻ"])
    ResponseEntity<BaseResponse> changeSuccess(@RequestBody CreatedNewCardRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            Map<String, String> param = new HashMap<>([
                    "service"   : Constant.Smcs.DoiTraHang.ONEBSS_PRODUCT_CHANGE_SUCCESS,
                    "account"   : body.getAccount().toLowerCase(),
                    "action"    : body.getAction(),
                    "trans_id"  : body.getTrans_id(),
                    "trans_date": body.getTrans_date(),
                    "key"       : Constant.Smcs.API_KEY
            ])
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - changeSuccess: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/huysp/infoBbbgDoiHong")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    @Operation(summary = "Lấy thông tin Biên bản bàn giao đơn hàng đổi hỏng hàng hóa", description = "Lấy thông tin Biên bản bàn giao đơn hàng đổi hỏng hàng hóa", tags = ["Đổi, Hủy thẻ"])
    ResponseEntity<BaseResponse<InfoBbbgDoiHong>> infoBbbgDoiHong(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Mã giao dịch", required = true, schema = @Schema()) @Valid @RequestParam(value = "trans_id", required = true) String trans_id,
                                                                  @NotNull @Parameter(in = ParameterIn.QUERY, description = "Ngày giao dịch", required = true, schema = @Schema()) @Valid @RequestParam(value = "trans_date", required = true) String trans_date) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            Map<String, String> param = new HashMap<>([
                    "service"   : Constant.Smcs.DoiTraHang.ONEBSS_GET_BBBG_DOIHONG,
                    "trans_id"  : trans_id,
                    "trans_date": trans_date,
                    "key"       : Constant.Smcs.API_KEY
            ])
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
            InfoBbbgDoiHongRespone response = new Gson().fromJson(resp, InfoBbbgDoiHongRespone.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - infoBbbgDoiHong: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/huysp/infoPhieuXKDoiHong")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    @Operation(summary = "Lấy thông tin Phiếu xuất đơn hàng đổi hỏng hàng hóa", description = "Lấy thông tin Phiếu xuất đơn hàng đổi hỏng hàng hóa", tags = ["Đổi, Hủy thẻ"])
    ResponseEntity<BaseResponse<InfoPhieuXKDoiHong>> infoPhieuXKDoiHong(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Mã giao dịch", required = true, schema = @Schema()) @Valid @RequestParam(value = "trans_id", required = true) String trans_id,
                                                                        @NotNull @Parameter(in = ParameterIn.QUERY, description = "Ngày giao dịch", required = true, schema = @Schema()) @Valid @RequestParam(value = "trans_date", required = true) String trans_date) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            Map<String, String> param = new HashMap<>([
                    "service"   : Constant.Smcs.DoiTraHang.ONEBSS_GET_PHIEUXK_DOIHONG,
                    "trans_id"  : trans_id,
                    "trans_date": trans_date,
                    "key"       : Constant.Smcs.API_KEY
            ])
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
            InfoPhieuXKDoiHongRespone response = new Gson().fromJson(resp, InfoPhieuXKDoiHongRespone.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getResult(), uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - infoPhieuXKDoiHong: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/mnp/tuChoiMnp")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse> tuChoiMnp(@RequestBody TuChoiMnpPiRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            Map<String, String> param = new HashMap<>([
                    "service": Constant.Smcs.Mnp.ONEBSS_MNP_PI_UPDATE,
                    "status" : body.getStatus(),
                    "note"   : body.getNote(),
                    "so_tb"  : body.getSo_tb(),
                    "piid"   : body.getPiid(),
                    "account": body.getAccount(),
                    "key"    : Constant.Smcs.API_KEY
            ])
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - getPiById: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/mnp/capNhatMnpPi")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse> capNhatMnpPi(@RequestBody CapNhatMnpPiRequest body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            Map<String, String> param = new HashMap<>([
                    "service"       : Constant.Smcs.Mnp.ONEBSS_MNP_SEND_PIN_REQUEST,
                    "address"       : body.getAddress(),
                    "bday"          : body.getBday(),
                    "city"          : body.getCity(),
                    "comment"       : body.getComment(),
                    "contact_no"    : body.getContact_no(),
                    "county"        : body.getCounty(),
                    "curr_service"  : body.getCurr_service(),
                    "cus_name"      : body.getCus_name(),
                    "cus_type"      : body.getCus_type(),
                    "disPatchId"    : body.getDisPatchId(),
                    "dno_offer"     : body.getDno_offer(),
                    "email"         : body.getEmail(),
                    "expect"        : body.getExpect(),
                    "file1"         : body.getFile1(),
                    "file10"        : body.getFile10(),
                    "file2"         : body.getFile2(),
                    "file3"         : body.getFile3(),
                    "file4"         : body.getFile4(),
                    "file5"         : body.getFile5(),
                    "file6 "        : body.getFile6(),
                    "file7"         : body.getFile7(),
                    "file8"         : body.getFile8(),
                    "file9"         : body.getFile9(),
                    "file_name1"    : body.getFile_name1(),
                    "file_name10"   : body.getFile_name10(),
                    "file_name2"    : body.getFile_name2(),
                    "file_name3 "   : body.getFile_name3(),
                    "file_name4"    : body.getFile_name4(),
                    "file_name5"    : body.getFile_name5(),
                    "file_name6"    : body.getFile_name6(),
                    "file_name7"    : body.getFile_name7(),
                    "file_name8"    : body.getFile_name8(),
                    "file_name9"    : body.getFile_name9(),
                    "from_carrer"   : body.getFrom_carrer(),
                    "id_number"     : body.getId_number(),
                    "id_number_type": body.getId_number_type(),
                    "issue_date"    : body.getIssue_date(),
                    "issue_place"   : body.getIssue_place(),
                    "leave_reason"  : body.getLeave_reason(),
                    "loaiGoi"       : body.getLoaiGoi(),
                    "nguoiGT"       : body.getNguoiGT(),
                    "offer"         : body.getOffer(),
                    "p_PhuongAn"    : body.getP_PhuongAn(),
                    "packageCode"   : body.getPackageCode(),
                    "packageId"     : body.getPackageId(),
                    "package_new"   : body.getPackage_new(),
                    "piid"          : body.getPiid(),
                    "refferer"      : body.getRefferer(),
                    "sdtNguoiGT"    : body.getSdtNguoiGT(),
                    "serviceCode"   : body.getServiceCode(),
                    "sex"           : body.getSex(),
                    "so_tb"         : body.getSo_tb(),
                    "state"         : body.getState(),
                    "sub_type"      : body.getSub_type(),
                    "subdata"       : body.getSubdata(),
                    "account"       : body.getAccount(),
                    "key"           : Constant.Smcs.API_KEY
            ])
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
            SmcsBaseResponse response = new Gson().fromJson(resp, SmcsBaseResponse.class)
            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - capNhatMnpPi: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    String Convert_File_To_Base64(String URLminio) {
        try {
            URL imageUrl = new URL(URLminio)
            URLConnection ucon = imageUrl.openConnection();
            InputStream is = ucon.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, read);
            }
            baos.flush();
            return Base64.getEncoder().encodeToString(baos.toByteArray())
        } catch (Exception e) {

        }
        return null
    }

    @GetMapping("/mnp/getPackageIn")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<List<MnpPackageIn>>> getPackageIn(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Số thuê bao", required = true, schema = @Schema()) @Valid @RequestParam(value = "soTb", required = true) String soTb,
                                                                  @NotNull @Parameter(in = ParameterIn.QUERY, description = "Phương án", required = true, schema = @Schema()) @Valid @RequestParam(value = "planType", required = true) String planType) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            Map<String, String> param = new HashMap<>([
                    "service" : Constant.Smcs.Mnp.ONEBSS_MNP_PKG_IN,
                    "so_tb"   : soTb,
                    "planType": planType,
                    "key"     : Constant.Smcs.API_KEY
            ])
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
            MnpPackageInResponse response = new Gson().fromJson(resp, MnpPackageInResponse.class)

            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.result, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - getPackageIn: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/mnp/getPackageInPre")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    ResponseEntity<BaseResponse<List<MnpPackageInPre>>> getPackageInPre() {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            Map<String, String> param = new HashMap<>([
                    "service": Constant.Smcs.Mnp.ONEBSS_MNP_PKG_IN_PRE,
                    "key"    : Constant.Smcs.API_KEY
            ])
            String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
            MnpPackageInPreResponse response = new Gson().fromJson(resp, MnpPackageInPreResponse.class)

            if (response.getError_code() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.result, uuidAsString, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, response.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[SMCSService] - getPiById: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/lay_tongtien_khachle")
    ResponseEntity<BaseResponse> lay_tongtien_khachle(@RequestParam("msisdn") String msisdn) {
        try {
            def result = [:]
            String[] lMsisdn = msisdn.split("\\;")
            if (lMsisdn) {
                for (String ms : lMsisdn) {
                    Map<String, String> param = new HashMap<>([
                            "service" : "lay_tongtien_khachle",
                            "key"     : Constant.Smcs.API_KEY,
                            "p_msisdn": (ms.startsWith("0") && ms.length() == 10 ? "84" + ms.substring(1) : ms)
                    ])
                    String resp = httpManaged.post(["url": smcsUrl], ["Content-Type": "application/json"], new Gson().toJson(param))
                    Map response = new Gson().fromJson(resp, Map.class)
                    if (response.error_code.toString() == "0") {
                        result[ms] = response.data
                    } else {
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, response.message.toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(result, null, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

}
