package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import io.swagger.model.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.DanhMuc
import vn.vnptit.sapi.oneapp.models.vtc.GetShippingData
import vn.vnptit.sapi.oneapp.models.vtc.Resend
import vn.vnptit.sapi.oneapp.models.vtc.SubscriptionActivate
import vn.vnptit.sapi.oneapp.models.vtc.models.GoiCuocBh
import vn.vnptit.sapi.oneapp.models.vtc.request.ChuyenDoiGoiCuocKasReq
import vn.vnptit.sapi.oneapp.models.vtc.request.GiaHanDichVuKasReq
import vn.vnptit.sapi.oneapp.models.vtc.request.KichHoatDichVuKasReq
import vn.vnptit.sapi.oneapp.models.vtc.request.KichHoatDichVuReq
import vn.vnptit.sapi.oneapp.models.vtc.request.NgungDichVuReq
import vn.vnptit.sapi.oneapp.models.vtc.request.NgungSdKasReq
import vn.vnptit.sapi.oneapp.models.vtc.respones.KichHoatDichVuRes
import vn.vnptit.sapi.oneapp.models.vtc.respones.NgungDichVuRes
import vn.vnptit.sapi.oneapp.models.vtc.respones.ThongTinSpDetailsKasRes
import vn.vnptit.sapi.oneapp.models.vtc.respones.ThongTinSpKasKeyRes
import vn.vnptit.sapi.oneapp.models.vtc.respones.ThongTinSpKasRes
import vn.vnptit.sapi.oneapp.models.vtc.respones.ThongTinSpRes
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.Constant
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.GenericApi
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse

import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.nio.charset.StandardCharsets

@RestController
@RequestMapping("vtc")
class VTCService {
    static String attrType = "@xsi:type"
    static String attrItemType = "@soapenc:arrayType"
    static String attrHref = "@href"
    static String attrNull = "@xsi:nil"

    static ThreadLocal<Gson> gson = ThreadLocal.withInitial { new GsonBuilder().serializeNulls().create() }

    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    OracleManaged oracleManaged
    @Autowired
    HttpManaged httpManaged

    @Autowired
    GenericApi genericApi
    @Autowired
    NativeWebRequest request

    @Value("\${spring.httpservices.vtc.url}")
    String url

    @Value("\${spring.httpservices.vtc.token}")
    String token

    @Value("\${spring.httpservices.vtc.partnerid}")
    String partnerid

    @Value("\${spring.httpservices.vtc.tokenkas}")
    String tokenKas

    @Value("\${spring.httpservices.vtc.partneridkas}")
    String partneridKas

    @PostMapping("/subscriptionActivate")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse subscriptionActivate(@Valid @RequestBody SubscriptionActivate body) {
        try {
            String path = Constant.VTC.activate
            def res = get(path, extractProperties(body))
            if (res != null && res != "") {
                def ret = new JsonSlurper().parseText(res)
                if (ret.result == 1)
                    return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret)
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: ret.message, data: ret)
            }
            return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: "")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/getShippingData")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse subscriptionActivate(@Valid @RequestBody GetShippingData body) {
        try {
            String path = Constant.VTC.getShippingData
            def res = get(path, extractProperties(body))
            if (res != null && res != "") {
                def ret = new JsonSlurper().parseText(res)
                if (ret.result == 1)
                    return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret)
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: ret.message, data: ret)
            }
            return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: "")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/resend")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse resend(@Valid @RequestBody Resend body) {
        try {
            String path = Constant.VTC.resend
            def res = get(path, extractProperties(body))
            if (res != null && res != "") {
                def ret = new JsonSlurper().parseText(res)
                if (ret.result == 1)
                    return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret)
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: ret.message, data: ret)
            }
            return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: "")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    def extractProperties(obj) {
        obj.getClass()
                .declaredFields
                .findAll { !it.synthetic }
                .collectEntries { field ->
                    [field.name, obj."$field.name"]
                }
    }

    String get(String path, Map<Object, Object> param) {
        def paramMap = param.collectEntries { key, value -> [key.toString(), value == null ? "" : value.toString()] }
        paramMap.put("partnerId", partnerid)
        def maps = paramMap.sort { i1, i2 -> i1.key <=> i2.key }
        maps.put("zskey", token)
//        String hash = maps.collect { it.value }.join(",")
        String sign = maps.collect { it.value }.join(",").getBytes(StandardCharsets.UTF_8).md5()
        maps.remove("zskey")
        maps.put("sign", sign)
        def ls = maps.collect { key, value -> key.toString() + "=" + URLEncoder.encode(value, "UTF-8") }.join("&")
        String url = url + path + "?" + ls
        Map<String, String> headers = [:]
        headers.put("Content-Type", "text/xml;charset=UTF-8")
        String resp = httpManaged.get(url, headers)
        return resp
    }

    String getkas(String path, Map<Object, Object> param) {
        def paramMap = param.collectEntries { key, value -> [key.toString(), value == null ? "" : value.toString()] }
        paramMap.put("partnerId", partneridKas)
        def maps = paramMap.sort { i1, i2 -> i1.key <=> i2.key }
        maps.put("zskey", tokenKas)
//        String hash = maps.collect { it.value }.join(",")
        String sign = maps.collect { it.value }.join(",").getBytes(StandardCharsets.UTF_8).md5()
        maps.remove("zskey")
        maps.put("sign", sign)
        def ls = maps.collect { key, value -> key.toString() + "=" + URLEncoder.encode(value, "UTF-8") }.join("&")
        String url = url + path + "?" + ls
        Map<String, String> headers = [:]
        headers.put("Content-Type", "text/xml;charset=UTF-8")
        String resp = httpManaged.get(url, headers)
        return resp
    }

    //#region bao hiem an ninh mang ca nhan, an sinh
    @PostMapping("/vinaphone/KichhoatDangKyBaoHiemSo_V3")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @Operation(summary = "Api kích hoạt dịch vụ", description = "Mô tả: api dùng để mua bảo hiểm, kết quả trả về thành công gửi email thông báo cho khách hàng", tags = ["Mua bảo hiểm"])
    ResponseEntity<BaseResponse<KichHoatDichVuRes>> KichhoatDangKyBaoHiemSo_V3(@RequestBody Map<String, Object> paramVTC) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            String url = paramVTC.url
            paramVTC.remove('url')
            def result = getV3(url,
//                    "24b44b5b60b647749b7043a187547aac",
                    "1dcc24d8a18a4620bb025b084d609ea4",
                    paramVTC)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(result, null, "Thành công", ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[VTCService] - KichhoatDangKyBaoHiemSo_V1: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }


    def getV3(String path, String keys, Map<String, Object> param) {
        String url = path
        Map<String, String> headers = [:]
        headers.put("Content-Type", "text/xml;charset=UTF-8")
        def response_str = httpManaged.post(["url": url], headers, JsonOutput.toJson(param))
        def response = new JsonSlurper().parseText(response_str)
        if(response.result == 200 || response.result == 1 ) {
            def result =  new JsonSlurper().parseText(response.body)
            return result
        }
        else {
            throw new Exception("Error (${response.result}): ${response.message.toString()}")
            return [:]
        }
    }

    //#endregion

    @PostMapping("/vinaphone/kiemtra_thongtin")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @Operation(summary = "Api kiểm tra thông tin", description = "Mô tả api kiểm tra thông tin trước khi gọi api kích hoạt sản phẩm", tags = ["Mua bảo hiểm"])
    ResponseEntity<BaseResponse> KiemTraThongTin(@RequestBody KichHoatDichVuReq param) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            if (param.phone == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Số điện thoại không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            if (param.fullname == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Họ tên khách hàng không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            if (param.address == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Địa chỉ không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            if (param.productCode == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Mã sản phẩm không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            if (param.supplyCode == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Đơn vị cung cấp không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            def session = genericApi.getSessionData(request)
            def ma_tinh = session[genericApi.MA_TINH]
            Map<String, String> paramVTC = new HashMap<>([
                    "productCode" : param.productCode,
                    "address"     : param.address,
                    "email"       : param.email,
                    "fullname"    : param.fullname,
                    "phone"       : param.phone,
                    "refId"       : uuidAsString,
                    "supplyCode"  : param.supplyCode,
                    "locationCode": ma_tinh,
                    "custCode"    : param.custCode
            ])
            paramVTC.put("fromDate", new Date().format("yyyy-MM-dd HH:mm:ss").toString())
            paramVTC.putAll(extractProperties(param.thongTinQuanLyBh))
            if (param.bhHocSinhSinhVien != null) {
                paramVTC.putAll(extractProperties(param.bhHocSinhSinhVien))
            }
            if (param.bhHoGiaDinh != null) {
                paramVTC.putAll(extractProperties(param.bhHoGiaDinh))
            }
            if (param.bhTaiNanHoGiaDinh != null) {
                paramVTC.putAll(extractProperties(param.bhTaiNanHoGiaDinh))
            }
            print(paramVTC.toString())
            def res = get(Constant.VTC.vinaphone.xacnhan_thongtin, paramVTC)
            def json = new JsonSlurper().parseText(res)
            if (json["result"] != 1) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, json["message"].toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Thành công", ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[VTCService] - kichHoatDichVu: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/vinaphone/kichhoat_dichvu")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @Operation(summary = "Api kích hoạt dịch vụ", description = "Mô tả: api dùng để mua bảo hiểm, kết quả trả về thành công gửi email thông báo cho khách hàng", tags = ["Mua bảo hiểm"])
    @FakeSuccessCommonResponse
    ResponseEntity<BaseResponse<KichHoatDichVuRes>> kichHoatDichVu(@RequestBody KichHoatDichVuReq param) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            if (param.phone == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Số điện thoại không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            if (param.fullname == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Họ tên khách hàng không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            if (param.address == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Địa chỉ không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            if (param.productCode == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Mã sản phẩm không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            if (param.supplyCode == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Đơn vị cung cấp không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            def session = genericApi.getSessionData(request)
            def ma_tinh = session[genericApi.MA_TINH]
            Map<String, String> paramVTC = new HashMap<>([
                    "productCode" : param.productCode,
                    "address"     : param.address,
                    "email"       : param.email,
                    "fullname"    : param.fullname,
                    "phone"       : param.phone,
                    "refId"       : uuidAsString,
                    "supplyCode"  : param.supplyCode,
                    "locationCode": ma_tinh,
                    "custCode"    : param.custCode
            ])
            paramVTC.put("fromDate", new Date().format("yyyy-MM-dd HH:mm:ss").toString())
            paramVTC.putAll(extractProperties(param.thongTinQuanLyBh))
            if (param.bhHocSinhSinhVien != null) {
                paramVTC.putAll(extractProperties(param.bhHocSinhSinhVien))
            }
            if (param.bhHoGiaDinh != null) {
                paramVTC.putAll(extractProperties(param.bhHoGiaDinh))
            }
            if (param.bhTaiNanHoGiaDinh != null) {
                paramVTC.putAll(extractProperties(param.bhTaiNanHoGiaDinh))
            }
            print(paramVTC.toString())
            def res = get(Constant.VTC.vinaphone.kichoat_dichvu, paramVTC)
            def json = new JsonSlurper().parseText(res)
            if (json["result"] != 1) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, json["message"].toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            KichHoatDichVuRes output = new KichHoatDichVuRes()
            output.subscriptionId = json["data"]["subscriptionId"]
            output.refId = uuidAsString
            return ResponseEntity.ok().body(BaseResponseBuilder.of(output, uuidAsString, "Thành công", ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[VTCService] - kichHoatDichVu: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/vinaphone/thongtin_sp")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @Operation(summary = "Lấy thông tin sản phẩm", description = "Mô tả: api chi tiết sản phẩm: link tải, số giấy phép… nếu không có mã subscriptionId, sẽ trả về danh sách các bảo hiểm khách hàng mua.", tags = ["Mua bảo hiểm"])
    ResponseEntity<BaseResponse<ThongTinSpRes>> thongTinSp(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Mã subscription", required = true, schema = @Schema()) @Valid @RequestParam(value = "subscriptionId", required = true) String subscriptionId,
                                                           @NotNull @Parameter(in = ParameterIn.QUERY, description = "Mã khách hàng", required = true, schema = @Schema()) @Valid @RequestParam(value = "custCode", required = true) String custCode,
                                                           @NotNull @Parameter(in = ParameterIn.QUERY, description = "Mã tham chiếu ", required = true, schema = @Schema()) @Valid @RequestParam(value = "refId", required = true) String refId) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            if (refId == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Mã tham chiếu không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            if (custCode == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Mã khách hàng không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            def session = genericApi.getSessionData(request)
            def ma_tinh = session[genericApi.MA_TINH]
            Map<String, String> param = new HashMap<>([
                    "subscriptionId": subscriptionId,
                    "refId"         : refId,
                    "custCode"      : custCode,
                    "locationCode"  : ma_tinh
            ])
            def res = get(Constant.VTC.vinaphone.thongtin_sp, param)
            def json = new JsonSlurper().parseText(res)
            if (json.result == 1) {
                String data = new Gson().toJson(json["data"][0])
                ThongTinSpRes sp = new Gson().fromJson(data, ThongTinSpRes.class)
                return ResponseEntity.ok().body(BaseResponseBuilder.of(sp, uuidAsString, "Thành công", ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, json["message"].toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[VTCService] - thongTinSp: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/donvi_cungcap")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @Operation(summary = "Lấy danh sách nhà cung cấp dịch vụ bảo hiểm", description = "Lấy danh sách nhà cung cấp dịch vụ bảo hiểm", tags = ["Mua bảo hiểm"])
    ResponseEntity<BaseResponse<List<DanhMuc>>> donViCungCapDv() {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            List<DanhMuc> lsData = []
            DanhMuc dmPti = new DanhMuc(NAME: "PIT", CODE: "20210015", ID: 20210015)
            DanhMuc dmBic = new DanhMuc(NAME: "BIC", CODE: "20210005", ID: 20210005)
            lsData.add(dmPti)
            lsData.add(dmBic)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(lsData, uuidAsString, "Thành công", ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[VTCService] - donViCungCapDv: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/goicuoc_bh")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @Operation(summary = "Lấy danh sách gói cước bảo hiểm", description = "Lấy danh sách gói cước bảo hiểm", tags = ["Mua bảo hiểm"])
    ResponseEntity<BaseResponse<List<GoiCuocBh>>> dmGoiCuocBh(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Mã supplyCode", required = true, schema = @Schema()) @Valid @RequestParam(value = "supplyCode", required = true) String supplyCode) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            if (supplyCode == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Đơn vị cung cấp không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            String data = "[{\"productName\":\"GÓI7THÁNG-BảohiểmtainạnHộgiađình\",\"productionCode\":\"2022020202\",\"priceNoVat\":\"100,000\",\"discount\":\"30\",\"note\":\"Khôngthuế\",\"bic\":\"\",\"pit\":\"20210015\"},{\"productName\":\"GÓI7THÁNG-BảohiểmhỗtrợviệnphíHộgiađình\",\"productionCode\":\"2022020602\",\"priceNoVat\":\"165,600\",\"discount\":\"30\",\"note\":\"Khôngthuế\",\"bic\":\"\",\"pit\":\"20210015\"},{\"productName\":\"GÓI7THÁNG-BHtainạncánhân(GPA)\",\"productionCode\":\"2022020203\",\"priceNoVat\":\"280,000\",\"discount\":\"30\",\"note\":\"Khôngthuế\",\"bic\":\"\",\"pit\":\"\"},{\"productName\":\"GÓI7THÁNG-BHtoàndiệnnhà(TDN)\",\"productionCode\":\"2022020501\",\"priceNoVat\":\"189,581\",\"discount\":\"30\",\"note\":\"Khôngthuế\",\"bic\":\"\",\"pit\":\"\"},{\"productName\":\"GÓI7THÁNG-Bảohiểmhọcsinhsinhviên(CSI)\",\"productionCode\":\"2022020301\",\"priceNoVat\":\"175,000\",\"discount\":\"30\",\"note\":\"Khôngthuế\",\"bic\":\"\",\"pit\":\"\"},{\"productName\":\"GÓI15THÁNG-BảohiểmtainạnHộgiađình\",\"productionCode\":\"2022020204\",\"priceNoVat\":\"190,000\",\"discount\":\"30\",\"note\":\"Khôngthuế\",\"bic\":\"\",\"pit\":\"20210015\"},{\"productName\":\"GÓI15THÁNG-BảohiểmhỗtrợviệnphíHộgiađình\",\"productionCode\":\"2022020603\",\"priceNoVat\":\"334,800\",\"discount\":\"30\",\"note\":\"Khôngthuế\",\"bic\":\"\",\"pit\":\"20210015\"},{\"productName\":\"GÓI15THÁNG-BHtainạncánhân(GPA)\",\"productionCode\":\"2022020205\",\"priceNoVat\":\"600,000\",\"discount\":\"30\",\"note\":\"Khôngthuế\",\"bic\":\"\",\"pit\":\"\"},{\"productName\":\"GÓI15THÁNG-BHtoàndiệnnhà(TDN)\",\"productionCode\":\"2022020502\",\"priceNoVat\":\"406,245\",\"priceVat\":\"446,870\",\"discount\":\"30\",\"bic\":\"\",\"pit\":\"\"},{\"productName\":\"GÓI15THÁNG-Bảohiểmhọcsinhsinhviên(CSI)\",\"productionCode\":\"2022020302\",\"priceNoVat\":\"375,000\",\"discount\":\"30\",\"note\":\"Khôngthuế\",\"bic\":\"\",\"pit\":\"\"},{\"productName\":\"GÓI12THÁNG-BảohiểmTNDSxemáytrên50cc\",\"productionCode\":\"2022020102\",\"priceNoVat\":\"60,000\",\"priceVat\":\"66,000\",\"discount\":\"55\",\"bic\":\"20210005\",\"pit\":\"\"},{\"productName\":\"GÓI12THÁNG-BảohiểmTNDSxemáydưới50cc\",\"productionCode\":\"2022020103\",\"priceNoVat\":\"55,000\",\"priceVat\":\"60,500\",\"discount\":\"55\",\"bic\":\"20210005\",\"pit\":\"\"},{\"productName\":\"GÓI12THÁNG-BảohiểmTNDSxemáyvàngườingồitrênxe\",\"productionCode\":\"2022020104\",\"priceNoVat\":\"80,000\",\"priceVat\":\"86,000\",\"discount\":\"55\",\"note\":\"TínhthuếchophíBHTNDS60.000*10%=6.000+20.000phíBHngườingồitrênxekhôngchịuthuế=86.000\",\"bic\":\"20210005\",\"pit\":\"\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkhôngkinhdoanhvậntải-Xedưới6chỗngồi\",\"productionCode\":\"2022020405\",\"priceNoVat\":\"437,000\",\"priceVat\":\"480,700\",\"discount\":\"30\",\"pit\":\"\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkhôngkinhdoanhvậntải-Xetừ6đến11chỗngồi\",\"productionCode\":\"2022020406\",\"priceNoVat\":\"794,000\",\"priceVat\":\"873,400\",\"discount\":\"30\",\"pit\":\"\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkhôngkinhdoanhvậntải-Xetừ12đến24chỗngồi\",\"productionCode\":\"2022020407\",\"priceNoVat\":\"1,270,000\",\"priceVat\":\"1,397,000\",\"discount\":\"30\",\"pit\":\"\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkhôngkinhdoanhvậntải-Xetrên24chỗngồi\",\"productionCode\":\"2022020408\",\"priceNoVat\":\"1,825,000\",\"priceVat\":\"2,007,500\",\"discount\":\"30\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkhôngkinhdoanhvậntải-Xevừachởngườivừachởhàng(Pickup,minivan)\",\"productionCode\":\"2022020409\",\"priceNoVat\":\"437,000\",\"priceVat\":\"480,700\",\"discount\":\"30\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xedưới6chỗngồitheođăngký\",\"productionCode\":\"2022020410\",\"priceNoVat\":\"756,000\",\"priceVat\":\"831,600\",\"discount\":\"30\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe6chỗngồitheođăngký\",\"productionCode\":\"2022020417\",\"priceNoVat\":\"929,000\",\"priceVat\":\"1,021,900\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe7chỗngồitheođăngký\",\"productionCode\":\"2022020418\",\"priceNoVat\":\"1,080,000\",\"priceVat\":\"1,188,000\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe8chỗngồitheođăngký\",\"productionCode\":\"2022020419\",\"priceNoVat\":\"1,253,000\",\"priceVat\":\"1,378,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe9chỗngồitheođăngký\",\"productionCode\":\"2022020460\",\"priceNoVat\":\"1,404,000\",\"priceVat\":\"1,544,400\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe10chỗngồitheođăngký\",\"productionCode\":\"2022020461\",\"priceNoVat\":\"1,512,000\",\"priceVat\":\"1,663,200\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe11chỗngồitheođăngký\",\"productionCode\":\"2022020420\",\"priceNoVat\":\"1,656,000\",\"priceVat\":\"1,821,600\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe12chỗngồitheođăngký\",\"productionCode\":\"2022020421\",\"priceNoVat\":\"1,822,000\",\"priceVat\":\"2,004,200\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe13chỗngồitheođăngký\",\"productionCode\":\"2022020422\",\"priceNoVat\":\"2,049,000\",\"priceVat\":\"2,253,900\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe14chỗngồitheođăngký\",\"productionCode\":\"2022020423\",\"priceNoVat\":\"2,221,000\",\"priceVat\":\"2,443,100\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe15chỗngồitheođăngký\",\"productionCode\":\"2022020424\",\"priceNoVat\":\"2,394,000\",\"priceVat\":\"2,633,400\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe16chỗngồitheođăngký\",\"productionCode\":\"2022020425\",\"priceNoVat\":\"3,054,000\",\"priceVat\":\"3,359,400\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe17chỗngồitheođăngký\",\"productionCode\":\"2022020426\",\"priceNoVat\":\"2,718,000\",\"priceVat\":\"2,989,800\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe18chỗngồitheođăngký\",\"productionCode\":\"2022020427\",\"priceNoVat\":\"2,869,000\",\"priceVat\":\"3,155,900\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe19chỗngồitheođăngký\",\"productionCode\":\"2022020428\",\"priceNoVat\":\"3,041,000\",\"priceVat\":\"3,345,100\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe20chỗngồitheođăngký\",\"productionCode\":\"2022020429\",\"priceNoVat\":\"3,191,000\",\"priceVat\":\"3,510,100\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe21chỗngồitheođăngký\",\"productionCode\":\"2022020430\",\"priceNoVat\":\"3,364,000\",\"priceVat\":\"3,700,400\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe22chỗngồitheođăngký\",\"productionCode\":\"2022020431\",\"priceNoVat\":\"3,515,000\",\"priceVat\":\"3,866,500\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe23chỗngồitheođăngký\",\"productionCode\":\"2022020432\",\"priceNoVat\":\"3,688,000\",\"priceVat\":\"4,056,800\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe24chỗngồitheođăngký\",\"productionCode\":\"2022020433\",\"priceNoVat\":\"4,632,000\",\"priceVat\":\"5,095,200\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe25chỗngồitheođăngký\",\"productionCode\":\"2022020434\",\"priceNoVat\":\"4,813,000\",\"priceVat\":\"5,294,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe26chỗngồitheođăngký\",\"productionCode\":\"2022020435\",\"priceNoVat\":\"4,843,000\",\"priceVat\":\"5,327,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe27chỗngồitheođăngký\",\"productionCode\":\"2022020436\",\"priceNoVat\":\"4,873,000\",\"priceVat\":\"5,360,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe28chỗngồitheođăngký\",\"productionCode\":\"2022020437\",\"priceNoVat\":\"4,903,000\",\"priceVat\":\"5,393,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe29chỗngồitheođăngký\",\"productionCode\":\"2022020438\",\"priceNoVat\":\"4,933,000\",\"priceVat\":\"5,426,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe30chỗngồitheođăngký\",\"productionCode\":\"2022020439\",\"priceNoVat\":\"4,963,000\",\"priceVat\":\"5,459,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe31chỗngồitheođăngký\",\"productionCode\":\"2022020440\",\"priceNoVat\":\"4,993,000\",\"priceVat\":\"5,492,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe32chỗngồitheođăngký\",\"productionCode\":\"2022020441\",\"priceNoVat\":\"5,023,000\",\"priceVat\":\"5,525,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe33chỗngồitheođăngký\",\"productionCode\":\"2022020442\",\"priceNoVat\":\"5,053,000\",\"priceVat\":\"5,558,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe34chỗngồitheođăngký\",\"productionCode\":\"2022020443\",\"priceNoVat\":\"5,083,000\",\"priceVat\":\"5,591,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe35chỗngồitheođăngký\",\"productionCode\":\"2022020444\",\"priceNoVat\":\"5,113,000\",\"priceVat\":\"5,624,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe36chỗngồitheođăngký\",\"productionCode\":\"2022020445\",\"priceNoVat\":\"5,143,000\",\"priceVat\":\"5,657,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe37chỗngồitheođăngký\",\"productionCode\":\"2022020446\",\"priceNoVat\":\"5,173,000\",\"priceVat\":\"5,690,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe38chỗngồitheođăngký\",\"productionCode\":\"2022020447\",\"priceNoVat\":\"5,203,000\",\"priceVat\":\"5,723,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe39chỗngồitheođăngký\",\"productionCode\":\"2022020448\",\"priceNoVat\":\"5,233,000\",\"priceVat\":\"5,756,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe40chỗngồitheođăngký\",\"productionCode\":\"2022020449\",\"priceNoVat\":\"5,263,000\",\"priceVat\":\"5,789,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe41chỗngồitheođăngký\",\"productionCode\":\"2022020450\",\"priceNoVat\":\"5,293,000\",\"priceVat\":\"5,822,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe42chỗngồitheođăngký\",\"productionCode\":\"2022020451\",\"priceNoVat\":\"5,323,000\",\"priceVat\":\"5,855,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe43chỗngồitheođăngký\",\"productionCode\":\"2022020452\",\"priceNoVat\":\"5,353,000\",\"priceVat\":\"5,888,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe44chỗngồitheođăngký\",\"productionCode\":\"2022020453\",\"priceNoVat\":\"5,383,000\",\"priceVat\":\"5,921,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe45chỗngồitheođăngký\",\"productionCode\":\"2022020454\",\"priceNoVat\":\"5,413,000\",\"priceVat\":\"5,954,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe46chỗngồitheođăngký\",\"productionCode\":\"2022020455\",\"priceNoVat\":\"5,443,000\",\"priceVat\":\"5,987,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe47chỗngồitheođăngký\",\"productionCode\":\"2022020456\",\"priceNoVat\":\"5,473,000\",\"priceVat\":\"6,020,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe48chỗngồitheođăngký\",\"productionCode\":\"2022020457\",\"priceNoVat\":\"5,503,000\",\"priceVat\":\"6,053,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe49chỗngồitheođăngký\",\"productionCode\":\"2022020458\",\"priceNoVat\":\"5,533,000\",\"priceVat\":\"6,086,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xe50chỗngồitheođăngký\",\"productionCode\":\"2022020459\",\"priceNoVat\":\"5,563,000\",\"priceVat\":\"6,119,300\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôkinhdoanhvậntải-Xevừachởngườivừachởhàng(Pickup,minivan)\",\"productionCode\":\"2022020411\",\"priceNoVat\":\"933,000\",\"priceVat\":\"1,026,300\",\"discount\":\"30\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôchởhàng(xetải)-Dưới3tấn\",\"productionCode\":\"2022020401\",\"priceNoVat\":\"853,000\",\"priceVat\":\"938,300\",\"discount\":\"30\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôchởhàng(xetải)-Từ3đến8tấn\",\"productionCode\":\"2022020402\",\"priceNoVat\":\"1,660,000\",\"priceVat\":\"1,826,000\",\"discount\":\"30\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôchởhàng(xetải)-Trên8đến15tấn\",\"productionCode\":\"2022020403\",\"priceNoVat\":\"2,746,000\",\"priceVat\":\"3,020,600\",\"discount\":\"30\"},{\"productName\":\"GÓI12THÁNG-BảohiểmXeôtôchởhàng(xetải)-Trên15tấn\",\"productionCode\":\"2022020404\",\"priceNoVat\":\"3,200,000\",\"priceVat\":\"3,520,000\",\"discount\":\"30\"},{\"productName\":\"GÓI12THÁNG-BảohiểmxeĐầukéorơ-moóc\",\"productionCode\":\"2022020415\",\"priceNoVat\":\"4,800,000\",\"priceVat\":\"5,280,000\",\"discount\":\"30\",\"bic\":\"20210005\"},{\"productName\":\"GÓI12THÁNG-BảohiểmxeMáykéo,xemáychuyêndùng\",\"productionCode\":\"2022020416\",\"priceNoVat\":\"1,023,600\",\"priceVat\":\"1,125,960\",\"discount\":\"30\",\"bic\":\"20210005\"}]"
            List<Map<String, String>> ls = new JsonSlurper().parseText(data)
            List<GoiCuocBh> lsData = ls.findAll { it -> it["pit"] == supplyCode || it["bic"] == supplyCode }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(lsData, uuidAsString, "Thành công", ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[VTCService] - dmGoiCuocBh: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/goicuoc_kas")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @Operation(summary = "Lấy danh sách gói cước Kaspersky", description = "Lấy danh sách gói cước Kaspersky", tags = ["Mua Kaspersky"])
    ResponseEntity<BaseResponse<List<DanhMuc>>> GoiCuocKas() {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            List<DanhMuc> lsData = []
            DanhMuc _2022010701 = new DanhMuc(NAME: "Kaspersky gói tháng - Phần mềm Kaspersky Internet Security - Multi-Device (1DVC)", CODE: "2022010701", ID: 2022010701)
            DanhMuc _2022010702 = new DanhMuc(NAME: "Kaspersky gói tháng - Phần mềm Kaspersky Internet Security - Multi-Device (3DVC)", CODE: "2022010702", ID: 2022010702)
            DanhMuc _2022010703 = new DanhMuc(NAME: "Kaspersky gói tháng - Phần mềm Kaspersky Internet Security - Multi-Device (5DVC)", CODE: "2022010703", ID: 2022010703)
            DanhMuc _2022010704 = new DanhMuc(NAME: "Kaspersky gói tháng - Phần mềm Kaspersky Safe Kid", CODE: "2022010704", ID: 2022010704)
            DanhMuc _2022010705 = new DanhMuc(NAME: "Kaspersky gói tháng - Phần mềm Kaspersky Multidevice 1 + Kaspersky Safe Kid", CODE: "2022010705", ID: 2022010705)
            DanhMuc _2022010801 = new DanhMuc(NAME: "Kaspersky gói 6 tháng - Phần mềm Kaspersky Internet Security - Multi-Device (1DVC)", CODE: "2022010801", ID: 2022010801)
            DanhMuc _2022010802 = new DanhMuc(NAME: "Kaspersky gói 6 tháng - Phần mềm Kaspersky Internet Security - Multi-Device (3DVC)", CODE: "2022010802", ID: 2022010802)
            DanhMuc _2022010803 = new DanhMuc(NAME: "Kaspersky gói 6 tháng - Phần mềm Kaspersky Internet Security - Multi-Device (5DVC)", CODE: "2022010803", ID: 2022010803)
            DanhMuc _2022010804 = new DanhMuc(NAME: "Kaspersky gói 6 tháng - Phần mềm Kaspersky Safe Kid", CODE: "2022010804", ID: 2022010804)
            DanhMuc _2022010805 = new DanhMuc(NAME: "Kaspersky gói 6 tháng - Phần mềm Kaspersky Multidevice 1 + Kaspersky Safe Kid", CODE: "2022010805", ID: 2022010805)
            DanhMuc _2022010901 = new DanhMuc(NAME: "Kaspersky gói 12 tháng - Phần mềm Kaspersky Internet Security - Multi-Device (1DVC)", CODE: "2022010901", ID: 2022010901)
            DanhMuc _2022010902 = new DanhMuc(NAME: "Kaspersky gói 12 tháng - Phần mềm Kaspersky Internet Security - Multi-Device (3DVC)", CODE: "2022010902", ID: 2022010902)
            DanhMuc _2022010903 = new DanhMuc(NAME: "Kaspersky gói 12 tháng - Phần mềm Kaspersky Internet Security - Multi-Device (5DVC)", CODE: "2022010903", ID: 2022010903)
            DanhMuc _2022010904 = new DanhMuc(NAME: "Kaspersky gói 12 tháng - Phần mềm Kaspersky Safe Kid", CODE: "2022010904", ID: 2022010904)
            DanhMuc _2022010905 = new DanhMuc(NAME: "Kaspersky gói 12 tháng - Phần mềm Kaspersky Multidevice 1 + Kaspersky Safe Kid", CODE: "2022010905", ID: 2022010905)
            lsData.add(_2022010701)
            lsData.add(_2022010702)
            lsData.add(_2022010703)
            lsData.add(_2022010704)
            lsData.add(_2022010705)
            lsData.add(_2022010801)
            lsData.add(_2022010802)
            lsData.add(_2022010803)
            lsData.add(_2022010804)
            lsData.add(_2022010805)
            lsData.add(_2022010901)
            lsData.add(_2022010902)
            lsData.add(_2022010903)
            lsData.add(_2022010904)
            lsData.add(_2022010905)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(lsData, uuidAsString, "Thành công", ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[VTCService] - donViCungCapDv: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/vnptfiber/kichhoat_dichvu")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @Operation(summary = "Api kích hoạt dịch vụ", description = "Mô tả: api dùng để mua bảo hiểm, kết quả trả về thành công gửi email thông báo cho khách hàng", tags = ["Mua Kaspersky"])
    @FakeSuccessCommonResponse
    ResponseEntity<BaseResponse<KichHoatDichVuRes>> kichHoatDichVuKasperky(@RequestBody KichHoatDichVuKasReq param) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            if (param.phone == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Số điện thoại không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            if (param.fullname == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Họ tên khách hàng không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            if (param.address == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Địa chỉ không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            if (param.productCode == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Mã sản phẩm không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
//            def session = genericApi.getSessionData(request)
//            def ma_tinh = session[genericApi.MA_TINH]
            Map<String, String> paramVTC = new HashMap<>([
                    "productCode": param.productCode,
                    "address"    : param.address,
                    "email"      : param.email,
                    "fullname"   : param.fullname,
                    "phone"      : param.phone,
                    "refId"      : uuidAsString,
                    "custCode"   : param.custCode
            ])
            print(paramVTC.toString())
            def res = getkas(Constant.VTC.vnptfiber.kichoat_dichvu, paramVTC)
            def json = new JsonSlurper().parseText(res)
            if (json["result"] != 1) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, json["message"].toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            KichHoatDichVuRes output = new KichHoatDichVuRes()
            output.subscriptionId = json["data"]["subscription"][0]["subscriptionId"]
            output.refId = uuidAsString
            return ResponseEntity.ok().body(BaseResponseBuilder.of(output, uuidAsString, "Thành công", ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[VTCService] - kichHoatDichVu: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/vnptfiber/giahan_dichvu")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @Operation(summary = "Api gia hạn dịch vụ", description = "Mô tả: api dùng để mua bảo hiểm, kết quả trả về thành công gửi email thông báo cho khách hàng", tags = ["Mua Kaspersky"])
    @FakeSuccessCommonResponse
    ResponseEntity<BaseResponse<KichHoatDichVuRes>> giaHanDv(@RequestBody GiaHanDichVuKasReq param) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            if (param.phone == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Số điện thoại không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            if (param.email == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Email khách hàng không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            if (param.productCode == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Mã sản phẩm không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            Map<String, String> paramVTC = new HashMap<>([
                    "productCode": param.productCode,
                    "email"      : param.email,
                    "phone"      : param.phone,
                    "refId"      : uuidAsString,
                    "custCode"   : param.custCode
            ])
            print(paramVTC.toString())
            def res = getkas(Constant.VTC.vnptfiber.giahan_dichvu, paramVTC)
            def json = new JsonSlurper().parseText(res)
            if (json["result"] != 1) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, json["message"].toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            KichHoatDichVuRes output = new KichHoatDichVuRes()
            output.subscriptionId = json["data"]["subscription"][0]["subscriptionId"]
            output.refId = uuidAsString
            return ResponseEntity.ok().body(BaseResponseBuilder.of(output, uuidAsString, "Thành công", ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[VTCService] - kichHoatDichVu: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/vnptfiber/chuyendoi_goicuoc")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @Operation(summary = "Api chuyển đổi gói", description = "Mô tả: Dùng để chuyển đổi gói cước", tags = ["Mua bảo hiểm"])
    @FakeSuccessCommonResponse
    ResponseEntity<BaseResponse<KichHoatDichVuRes>> ChuyenDoiGoiCuoc(@RequestBody ChuyenDoiGoiCuocKasReq param) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            if (param.phone == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Số điện thoại không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            if (param.email == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Email khách hàng không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            if (param.productCode == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Mã sản phẩm không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            if (param.reason == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Lý do không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            Map<String, String> paramVTC = new HashMap<>([
                    "productCode": param.productCode,
                    "email"      : param.email,
                    "phone"      : param.phone,
                    "refId"      : uuidAsString,
                    "custCode"   : param.custCode,
                    "reason"     : param.reason,
                    "reset_date" : param.resetDate
            ])
            print(paramVTC.toString())
            def res = getkas(Constant.VTC.vnptfiber.doi_goicuoc, paramVTC)
            def json = new JsonSlurper().parseText(res)
            if (json["result"] != 1) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, json["message"].toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            KichHoatDichVuRes output = new KichHoatDichVuRes()
            output.subscriptionId = json["data"]["subscription"][0]["subscriptionId"]
            output.refId = uuidAsString
            return ResponseEntity.ok().body(BaseResponseBuilder.of(output, uuidAsString, "Thành công", ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[VTCService] - ChuyenDoiGoiCuoc: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/vnptfiber/ngung_dichvu")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @Operation(summary = "Api ngừng dịch vụ", description = "Mô tả: api dùng để ngưng dịch vụ sản phẩm để không tự động gia hạn dịch vụ. Mỗi mã thuê bao chỉ có 1 gói sản phẩm kích hoạt tại thời điểm nên dùng mã thuê bao để suspend sản phẩm. Với các gói 1 tháng, 6 tháng, 12 tháng thanh lý dịch vụ thời hạn key giữ nguyên, không tự động gia hạn gói cước.", tags = ["Mua Kaspersky"])
    @FakeSuccessCommonResponse
    ResponseEntity<BaseResponse<List<NgungDichVuRes>>> NgungDichVu(@RequestBody NgungSdKasReq param) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            if (param.custCode == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Số điện thoại không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            if (param.reason == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Lý do không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            Map<String, String> paramVTC = new HashMap<>([
                    "custCode": param.custCode,
                    "reason"  : param.reason,
            ])
            print(paramVTC.toString())
            def res = getkas(Constant.VTC.vnptfiber.ngung_dichvu, paramVTC)
            def json = new JsonSlurper().parseText(res)
            if (json["result"] != 1) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, json["message"].toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            List<NgungDichVuRes> ls = json["data"]
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ls, uuidAsString, "Thành công", ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[VTCService] - NgungDichVu: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/vnptfiber/thongtin_sp")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @Operation(summary = "Lấy thông tin sản phẩm", description = "Mô tả: api chi tiết sản phẩm: link tải, số giấy phép… nếu không có mã subscriptionId, sẽ trả về danh sách các bảo hiểm khách hàng mua.", tags = ["Mua Kaspersky"])
    @FakeSuccessCommonResponse
    ResponseEntity<BaseResponse<ThongTinSpKasRes>> thongTinSpKas(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Mã khách hàng", required = true, schema = @Schema()) @Valid @RequestParam(value = "custCode", required = true) String custCode) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            if (custCode == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Mã khách hàng không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            Map<String, String> param = new HashMap<>([
                    "custCode": custCode
            ])
            def res = getkas(Constant.VTC.vnptfiber.thongtin_sp, param)
            def json = new JsonSlurper().parseText(res)
            if (json.result == 1) {
                String data = new Gson().toJson(json["data"][0])
                ThongTinSpKasRes sp = new Gson().fromJson(data, ThongTinSpKasRes.class)
                return ResponseEntity.ok().body(BaseResponseBuilder.of(sp, uuidAsString, "Thành công", ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, json["message"].toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[VTCService] - thongTinSp: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/vnptfiber/kiemtra_key")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @Operation(summary = "Api kiểm tra thông tin key", description = "Mô tả: : kiểm tra key có tồn tại trong hệ thống hay không. Nếu key sản phẩm combo thì trả về các sản phẩm trong gói combo.", tags = ["Mua Kaspersky"])
    @FakeSuccessCommonResponse
    ResponseEntity<BaseResponse<ThongTinSpKasKeyRes>> ThongTinkey(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Mã key", required = true, schema = @Schema()) @Valid @RequestParam(value = "key", required = true) String key) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            if (key == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Mã key không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            Map<String, String> param = new HashMap<>([
                    "key": key
            ])
            def res = getkas(Constant.VTC.vnptfiber.kiemtra_key, param)
            def json = new JsonSlurper().parseText(res)
            if (json.result == 1) {
                String data = new Gson().toJson(json["data"][0])
                ThongTinSpKasKeyRes sp = new Gson().fromJson(data, ThongTinSpKasKeyRes.class)
                return ResponseEntity.ok().body(BaseResponseBuilder.of(sp, uuidAsString, "Thành công", ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, json["message"].toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[VTCService] - ThongTinkey: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/vnptfiber/guilai_key")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @Operation(summary = "Api gửi lại key cho khách hàng", description = "Mô tả: api gửi lại key cho khách hàng khi có phản ánh không nhận được thông tin sản phẩm. Ghi chú: lần 1 gửi sms + email, từ lần 2 trở đi gửi email.", tags = ["Mua Kaspersky"])
    @FakeSuccessCommonResponse
    ResponseEntity<BaseResponse> guiLaiKey(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Số điện thoại", required = true, schema = @Schema()) @Valid @RequestParam(value = "phone", required = true) String phone,
                                           @NotNull @Parameter(in = ParameterIn.QUERY, description = "Email", required = true, schema = @Schema()) @Valid @RequestParam(value = "email", required = true) String email,
                                           @NotNull @Parameter(in = ParameterIn.QUERY, description = "Mã khách hàng", required = true, schema = @Schema()) @Valid @RequestParam(value = "custCode", required = true) String custCode) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            if (custCode == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Mã khách hàng không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            if (phone == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Số điện thoại không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            if (email == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Email không được để trống", ErrorCodeDefine.INVALID_PARAM))
            }
            Map<String, String> param = new HashMap<>([
                    "custCode": custCode,
                    "phone"   : phone,
                    "email"   : email
            ])
            def res = getkas(Constant.VTC.vnptfiber.guilai_sms_email, param)
            def json = new JsonSlurper().parseText(res)
            if (json.result == 1) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, "Thành công", ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, json["message"].toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[VTCService] - guiLaiKey: ", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, uuidAsString, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
}
