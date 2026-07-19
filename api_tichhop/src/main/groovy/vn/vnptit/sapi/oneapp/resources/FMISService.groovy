package vn.vnptit.sapi.oneapp.resources

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import com.google.gson.Gson
import groovy.json.JsonSlurper
import groovy.util.slurpersupport.GPathResult
import io.swagger.model.BaseResponse
import org.apache.commons.lang3.StringEscapeUtils
import org.json.JSONObject
import org.msgpack.util.json.JSON
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
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
import vn.vnptit.sapi.oneapp.models.DonVi
import vn.vnptit.sapi.oneapp.models.fmis.FS_BSS_KTDH_DHSXKD_REQ
import vn.vnptit.sapi.oneapp.models.fmis.FXML_CTVT_NH1_REQ
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.GenericApi
import vn.vnptit.sapi.oneapp.util.Constant

import vn.vnptit.sapi.oneapp.models.fmis.FXML_CTVT_NH_REQ
import vn.vnptit.sapi.oneapp.models.fmis.FXML_CTVT_XOA_REQ

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("fmis")
class FMISService {
    @Autowired
    CacheManager cacheManager
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
    @Autowired
    DonVi donVi

    ObjectMapper json = new ObjectMapper();

    @Value("\${spring.httpservices.fmis.url}")
    String fmisUrl

    @Value("\${spring.httpservices.fmis.token}")
    String fmisToken

    // Mật khẩu default của FMIS
    String passWordDefault = "bc6d20742e9766580a4d1d42a03f0f4a0613f079"

    String fmisSoapUrl = "http://10.168.3.92/quannm/Service/ktvt/sktvthp_ct.asmx"

    String fmisKtdh = "http://10.168.3.92/quannm/Service/ktdh/sktdh_lct.asmx"

    String fmisSoapUrl_2025 = "https://fmis2025.vnpt.vn/Service/ktvt/sktvthp_ct.asmx"

    String fmisKtdh_2025 = "https://fmis2025.vnpt.vn/Service/ktdh/sktdh_lct.asmx"


    @GetMapping("/**")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> executorGet(HttpServletRequest request, @RequestParam Map <String, String> requestParams) {
        Map<String, Object> inputLogData = new HashMap<>()
        try {
            String[] arrServletPath = request.getServletPath().split("fmis/", 2)
            inputLogData.put("class", "FMISService")
            inputLogData.put("action", arrServletPath[1])
            inputLogData.put("onebss_username", genericApi.getSessionData(request).get(GenericApi.USERNAME))
            inputLogData.put("params", new Gson().toJson(requestParams))

            if (arrServletPath[1].contains("danh-sach-nguoi-ky-bc")) {
                requestParams["p_ngayc"] = ""
            }

            def res = get(arrServletPath[1], requestParams)
            inputLogData.put("response", res)
            def ret = new JsonSlurper().parseText(res)
            // Nếu Status = 200 thì trả ra data
            if (ret["Status"] == 200){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret['Data']))
            }
            else
            {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, ret["Data"], ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            inputLogData.put("error", e.message)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        } finally {
            loggingManaged.info(inputLogData)
        }
    }

    @PostMapping("/**")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> executorPost(HttpServletRequest request, @RequestParam Map <String, String> requestParams, @RequestBody(required = false) Object requestBody) {
        Map<String, Object> inputLogData = new HashMap<>()
        try {
            String[] arrServletPath = request.getServletPath().split("fmis/", 2)
            inputLogData.put("class", "FMISService")
            inputLogData.put("action", arrServletPath[1])
            inputLogData.put("onebss_username", genericApi.getSessionData(request).get(GenericApi.USERNAME))
            inputLogData.put("params", new Gson().toJson(requestParams))
            inputLogData.put("body", new Gson().toJson(requestBody))
            def res = post(arrServletPath[1], requestParams, requestBody)
            inputLogData.put("response", res)
            def ret = new JsonSlurper().parseText(res)
            // Nếu Status = 200 thì trả ra data
            if (ret["Status"] == 200){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret['Data']))
            }
            else
            {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, ret["Data"], ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            inputLogData.put("error", e.message)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        } finally {
            loggingManaged.info(inputLogData)
        }
    }

    String get(String path, Map<String, String> param) {
        try {
            Map<String, String> headers = [:]
            def ls = ""
            // Get Token Auth của FMIS
            String tokenAuthJson = getTokenFromCache()
            def retToken = new JsonSlurper().parseText(tokenAuthJson)
            if (retToken["Status"] != "200") {
                return tokenAuthJson
            }
            String tokenAuth = retToken["Data"]
            if (tokenAuth != null && tokenAuth != ""){
                headers.put("Authorization", "bearer " + tokenAuth)
            }
            if (param.size() > 0){
                ls = param.collect {key, value -> key + "=" + value}.join("&")
            }
            String url = fmisUrl + path
            if (ls != "")
            {
                url = fmisUrl + path + "?" + ls
            }
            headers.put("Content-Type", "application/json")
            String resp = httpManaged.get(url, headers)
            return resp
        }
        catch (Exception ex)
        {
            return "{ \"Status\" : \"400\", \"Data\" : \""+ ex.message +"\" }"
        }
    }

    String post(String path, Map<String, String> param, Object body) {
        try
        {
            Map<String, String> headers = [:]
            def ls = ""
            // Get Token Auth của FMIS
            String tokenAuthJson = getTokenFromCache()
            def retToken = new JsonSlurper().parseText(tokenAuthJson)
            if (retToken["Status"] != "200") {
                return tokenAuthJson
            }
            String tokenAuth = retToken["Data"]
            if (tokenAuth != null && tokenAuth != ""){
                headers.put("Authorization", "bearer " + tokenAuth)
            }
            if (param.size() > 0){
                ls = param.collect {key, value -> key + "=" + value}.join("&")
            }
            String url = fmisUrl + path
            if (ls != "")
            {
                url = fmisUrl + path + "?" + ls
            }
            headers.put("Content-Type", "application/json")
            String resp = httpManaged.post([url: url], headers, json.writeValueAsString(body))
            return resp
        }
        catch (Exception ex)
        {
            return "{ \"Status\" : \"400\", \"Data\" : \""+ ex.message +"\" }"
        }

    }

    // Hàm xử lý lấy Token Fmis từ Redis -  Chưa xử lý thời gian hết hạn
    String getTokenFromCache(){
        def session = genericApi.getSessionData(request)
        if (session == null) { return null }
        String emailFull = session[GenericApi.Email].toString()
        String email = ""
        if (emailFull != null) {
            email = emailFull.split("@")[0]
        }
        // email = 'bacnv.hgg';
        String token = cacheManager.getCache(Constant.FMIS_CACHE_NAME).get(email.toUpperCase() + "/serverToken", String.class)
        if (token == null){
            return getNewToken(email)
        } else {
            DecodedJWT jwt = JWT.decode(token);
            Calendar calendar = Calendar.getInstance(); // gets a calendar using the default time zone and locale.
            calendar.add(Calendar.SECOND, 60);
            if(jwt.getExpiresAt().before(calendar.getTime())) {
                return getNewToken(email)
            }
        }
        return "{ \"Status\" : \"200\", \"Data\" : \""+ token +"\" }"
    }

    String getNewToken(String email){
        try
        {
            Map<String, String> headers = [:]
            headers.put("Content-Type", "application/json")
            // Get Token Auth của FMIS
            String url = fmisUrl + "api/get-token"
            String body = "{ \"Email\" : \""+ email.toLowerCase() +"\", \"PassWord\" : \"" + passWordDefault + "\" }"
            String tokenFull = httpManaged.post([url: url], headers, body)
            JSONObject json = new JSONObject(tokenFull);
            String  token = json["Token"]
            if (token != null && token != ""){
                cacheManager.getCache(Constant.FMIS_CACHE_NAME).put(email.toUpperCase() + "/serverToken", token)
            }
            return "{ \"Status\" : \"200\", \"Data\" : \""+ token +"\" }"
        }
        catch (Exception ex) {
            return "{ \"Status\" : \"400\", \"Data\" : \""+ ex.message +"\" }"  
        }
    }

    String nvl(Object object) {
        String result = ""
        if (object != null)
            result = object.toString().trim()
        return result
    }

    @PostMapping("/fxml_ctvt_nh")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse fxml_ctvt_nh(@Valid @RequestBody FXML_CTVT_NH_REQ body) {
        String thongTinChung = ""
        String thongtinChiTiet = ""
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        Map<String, Object> inputLogData = new HashMap<>()
        inputLogData.put("class", "FMISService")
        inputLogData.put("action", "fxml_ctvt_nh")
        inputLogData.put("requestId", uuidAsString)
        inputLogData.put("onebss_username", genericApi.getSessionData(request).get(GenericApi.USERNAME))
        inputLogData.put("body", new Gson().toJson(body))
        try {
            if (body.dataChungTu != null && body.dataChungTu.size() > 0) {
                thongTinChung = """<so_id_ct>${body.dataChungTu[0].so_id_ct}</so_id_ct>   
                                    <l_ct>${body.dataChungTu[0].l_ct}</l_ct>
                                    <ngay_ht>${body.dataChungTu[0].ngay_ht}</ngay_ht>
                                    <so_ct>${body.dataChungTu[0].so_ct}</so_ct>
                                    <ngay_ct>${body.dataChungTu[0].ngay_ct}</ngay_ct>
                                    <k_ma_kh>${nvl(body.dataChungTu[0].k_ma_kh)}</k_ma_kh>
                                    <ma_kh>${nvl(body.dataChungTu[0].ma_kh)}</ma_kh>
                                    <ten>${nvl(body.dataChungTu[0].ten)}</ten>
                                    <ma_thue>${nvl(body.dataChungTu[0].ma_thue)}</ma_thue>
                                    <dchi>${nvl(body.dataChungTu[0].dchi)}</dchi>
                                    <hdong>${nvl(body.dataChungTu[0].hdong)}</hdong>
                                    <nd><![CDATA[${body.dataChungTu[0].nd}]]></nd>
                                    <ma_mdsd>${nvl(body.dataChungTu[0].ma_mdsd)}</ma_mdsd>
                                    <tien_tt>${body.dataChungTu[0].tien_tt}</tien_tt>"""
            }

            if (body.dataCT_ChungTu != null && body.dataCT_ChungTu.size() > 0) {
                for (Map<String, String> item : body.dataCT_ChungTu) {
                    thongtinChiTiet += """<ThongTinChiTiet>
                                                <kho>${nvl(item.kho)}</kho>
                                                <kho_m>${nvl(item.kho_m)}</kho_m>
                                                <nhom>${item.nhom}</nhom>
                                                <ma_vt>${nvl(item.ma_vt)}</ma_vt>
                                                <dv>${item.dv}</dv>
                                                <luong>${item.luong}</luong>
                                                <gia>${item.gia}</gia>
                                                <tien>${item.tien}</tien>
                                                <ct_nd>${nvl(item.ct_nd)}</ct_nd>       
                                            </ThongTinChiTiet>"""
                }
            }

            def env = post1(fmisSoapUrl,
                    """<?xml version="1.0" encoding="utf-8"?>
                        <soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                            <soap12:Body>
                                <Fs_CTVT_NH xmlns="http://tempuri.org/">
                                    <dongBoVatTu>
                                        <b_ma_dvi>${body.b_ma_dvi}</b_ma_dvi>
                                        <b_ma_nsd>${body.b_ma_nsd}</b_ma_nsd>
                                        <id_ct_nh>${body.id_ct_nh}</id_ct_nh>
                                        <ThongTinChung>$thongTinChung</ThongTinChung>
                                        <ChiTiet>$thongtinChiTiet</ChiTiet>
                                    </dongBoVatTu>
                                </Fs_CTVT_NH>
                            </soap12:Body>
                        </soap12:Envelope>""")
            
            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                inputLogData.put("faultCode", fault.Code.text())
                inputLogData.put("faultString", fault.Reason.text())
                loggingManaged.info(inputLogData)
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            inputLogData.put("data", env.Body.Fs_CTVT_NHResponse.Fs_CTVT_NHResult.text())
            loggingManaged.info(inputLogData)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: env.Body.Fs_CTVT_NHResponse.Fs_CTVT_NHResult.text())
        } catch (Exception e) {
            inputLogData.put("error", e.message)
            loggingManaged.info(inputLogData)
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/fxml_ctvt_nh_2025")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse fxml_ctvt_nh_2025(@Valid @RequestBody FXML_CTVT_NH_REQ body) {
        String thongTinChung = ""
        String thongtinChiTiet = ""
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        Map<String, Object> inputLogData = new HashMap<>()
        inputLogData.put("class", "FMISService")
        inputLogData.put("action", "fxml_ctvt_nh")
        inputLogData.put("requestId", uuidAsString)
        inputLogData.put("onebss_username", genericApi.getSessionData(request).get(GenericApi.USERNAME))
        inputLogData.put("body", new Gson().toJson(body))
        try {
            if (body.dataChungTu != null && body.dataChungTu.size() > 0) {
                thongTinChung = """<so_id_ct>${body.dataChungTu[0].so_id_ct}</so_id_ct>   
                                    <l_ct>${body.dataChungTu[0].l_ct}</l_ct>
                                    <ngay_ht>${body.dataChungTu[0].ngay_ht}</ngay_ht>
                                    <so_ct>${body.dataChungTu[0].so_ct}</so_ct>
                                    <ngay_ct>${body.dataChungTu[0].ngay_ct}</ngay_ct>
                                    <k_ma_kh>${nvl(body.dataChungTu[0].k_ma_kh)}</k_ma_kh>
                                    <ma_kh>${nvl(body.dataChungTu[0].ma_kh)}</ma_kh>
                                    <ten>${nvl(body.dataChungTu[0].ten)}</ten>
                                    <ma_thue>${nvl(body.dataChungTu[0].ma_thue)}</ma_thue>
                                    <dchi>${nvl(body.dataChungTu[0].dchi)}</dchi>
                                    <hdong>${nvl(body.dataChungTu[0].hdong)}</hdong>
                                    <nd><![CDATA[${body.dataChungTu[0].nd}]]></nd>
                                    <ma_mdsd>${nvl(body.dataChungTu[0].ma_mdsd)}</ma_mdsd>
                                    <tien_tt>${body.dataChungTu[0].tien_tt}</tien_tt>"""
            }

            if (body.dataCT_ChungTu != null && body.dataCT_ChungTu.size() > 0) {
                for (Map<String, String> item : body.dataCT_ChungTu) {
                    thongtinChiTiet += """<ThongTinChiTiet>
                                                <kho>${nvl(item.kho)}</kho>
                                                <kho_m>${nvl(item.kho_m)}</kho_m>
                                                <nhom>${item.nhom}</nhom>
                                                <ma_vt>${nvl(item.ma_vt)}</ma_vt>
                                                <dv>${item.dv}</dv>
                                                <luong>${item.luong}</luong>
                                                <gia>${item.gia}</gia>
                                                <tien>${item.tien}</tien>
                                                <ct_nd>${nvl(item.ct_nd)}</ct_nd>       
                                            </ThongTinChiTiet>"""
                }
            }

            def env = post1(fmisSoapUrl_2025,
                    """<?xml version="1.0" encoding="utf-8"?>
                        <soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                            <soap12:Body>
                                <Fs_CTVT_NH xmlns="http://tempuri.org/">
                                    <dongBoVatTu>
                                        <b_ma_dvi>${body.b_ma_dvi}</b_ma_dvi>
                                        <b_ma_nsd>${body.b_ma_nsd}</b_ma_nsd>
                                        <id_ct_nh>${body.id_ct_nh}</id_ct_nh>
                                        <ThongTinChung>$thongTinChung</ThongTinChung>
                                        <ChiTiet>$thongtinChiTiet</ChiTiet>
                                    </dongBoVatTu>
                                </Fs_CTVT_NH>
                            </soap12:Body>
                        </soap12:Envelope>""")

            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                inputLogData.put("faultCode", fault.Code.text())
                inputLogData.put("faultString", fault.Reason.text())
                loggingManaged.info(inputLogData)
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            inputLogData.put("data", env.Body.Fs_CTVT_NHResponse.Fs_CTVT_NHResult.text())
            loggingManaged.info(inputLogData)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: env.Body.Fs_CTVT_NHResponse.Fs_CTVT_NHResult.text())
        } catch (Exception e) {
            inputLogData.put("error", e.message)
            loggingManaged.info(inputLogData)
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/fxml_ctvt_nh1")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse fxml_ctvt_nh1(@Valid @RequestBody FXML_CTVT_NH1_REQ body) {
        String bodys = ""
        String thongTinChung = ""
        String thongtinChiTiet = ""
        StringBuilder thongtinFile = new StringBuilder()
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        Map<String, Object> inputLogData = new HashMap<>()
        inputLogData.put("class", "FMISService")
        inputLogData.put("action", "fxml_ctvt_nh")
        inputLogData.put("requestId", uuidAsString)
        inputLogData.put("onebss_username", genericApi.getSessionData(request).get(GenericApi.USERNAME))
        inputLogData.put("body", new Gson().toJson(body))
        try {
            loggingManaged.info("[FMISService] - [fxml_ctvt_nh] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", uuidAsString).put("body", new Gson().toJson(body)).build())
            if (body.dataChungTu != null && body.dataChungTu.size() > 0) {
                thongTinChung = """<so_id_ct>${body.dataChungTu[0].so_id_ct}</so_id_ct>   
                                    <l_ct>${body.dataChungTu[0].l_ct}</l_ct>
                                    <ngay_ht>${body.dataChungTu[0].ngay_ht}</ngay_ht>
                                    <so_ct>${body.dataChungTu[0].so_ct}</so_ct>
                                    <ngay_ct>${body.dataChungTu[0].ngay_ct}</ngay_ct>
                                    <k_ma_kh>${nvl(body.dataChungTu[0].k_ma_kh)}</k_ma_kh>
                                    <ma_kh>${nvl(body.dataChungTu[0].ma_kh)}</ma_kh>
                                    <ten>${nvl(body.dataChungTu[0].ten)}</ten>
                                    <ma_thue>${nvl(body.dataChungTu[0].ma_thue)}</ma_thue>
                                    <dchi>${nvl(body.dataChungTu[0].dchi)}</dchi>
                                    <hdong>${nvl(body.dataChungTu[0].hdong)}</hdong>
                                    <nd><![CDATA[${body.dataChungTu[0].nd}]]></nd>
                                    <ma_mdsd>${nvl(body.dataChungTu[0].ma_mdsd)}</ma_mdsd>
                                    <tien_tt>${body.dataChungTu[0].tien_tt}</tien_tt>"""
            }

            if (body.dataCT_ChungTu != null && body.dataCT_ChungTu.size() > 0) {
                for (Map<String, String> item : body.dataCT_ChungTu) {
                    thongtinChiTiet += """<ThongTinChiTiet>
                                                <kho>${nvl(item.kho)}</kho>
                                                <kho_m>${nvl(item.kho_m)}</kho_m>
                                                <nhom>${item.nhom}</nhom>
                                                <ma_vt>${nvl(item.ma_vt)}</ma_vt>
                                                <dv>${item.dv}</dv>
                                                <luong>${item.luong}</luong>
                                                <gia>${new BigDecimal(item.gia.toString())}</gia>
                                                <tien>${item.tien}</tien>
                                                <ct_nd>${nvl(item.ct_nd)}</ct_nd>       
                                            </ThongTinChiTiet>"""
                }
            }

            if(body.dataFlie != null && body.dataFlie.size() > 0){
                for(item in body.dataFlie){
                    for(dt in item["urls"]) {
                        thongtinFile.append(
                                """
                                    <FileUpLoad>
                                        <protocol>https</protocol>
                                        <path>${StringEscapeUtils.escapeHtml4(dt["url"].toString())}</path>
                                        <noi_dung>${dt["title"]}</noi_dung>
                                    </FileUpLoad>
                                """)
                    }
                }
                bodys =
                        """<?xml version="1.0" encoding="utf-8"?>
                        <soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                            <soap12:Body>
                                <Fs_CTVT_NH xmlns="http://tempuri.org/">
                                    <dongBoVatTu>
                                        <b_ma_dvi>${body.b_ma_dvi}</b_ma_dvi>
                                        <b_ma_nsd>${body.b_ma_nsd}</b_ma_nsd>
                                        <id_ct_nh>${body.id_ct_nh}</id_ct_nh>
                                        <ThongTinChung>$thongTinChung</ThongTinChung>
                                        <ChiTiet>$thongtinChiTiet</ChiTiet>
                                        <FileUpLoads>${thongtinFile}</FileUpLoads>
                                    </dongBoVatTu>
                                </Fs_CTVT_NH>
                            </soap12:Body>
                        </soap12:Envelope>"""
            }else {
                bodys = """<?xml version="1.0" encoding="utf-8"?>
                        <soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                            <soap12:Body>
                                <Fs_CTVT_NH xmlns="http://tempuri.org/">
                                    <dongBoVatTu>
                                        <b_ma_dvi>${body.b_ma_dvi}</b_ma_dvi>
                                        <b_ma_nsd>${body.b_ma_nsd}</b_ma_nsd>
                                        <id_ct_nh>${body.id_ct_nh}</id_ct_nh>
                                        <ThongTinChung>$thongTinChung</ThongTinChung>
                                        <ChiTiet>$thongtinChiTiet</ChiTiet>
                                    </dongBoVatTu>
                                </Fs_CTVT_NH>
                            </soap12:Body>
                        </soap12:Envelope>"""
            }

            def env = post1(fmisSoapUrl,bodys)

            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                inputLogData.put("faultCode", fault.Code.text())
                inputLogData.put("faultString", fault.Reason.text())
                loggingManaged.info(inputLogData)
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            inputLogData.put("data", env.Body.Fs_CTVT_NHResponse.Fs_CTVT_NHResult.text())
            loggingManaged.info(inputLogData)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: env.Body.Fs_CTVT_NHResponse.Fs_CTVT_NHResult.text())
        } catch (Exception e) {
            inputLogData.put("error", e.message)
            loggingManaged.info(inputLogData)
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/fxml_ctvt_nh1_2025")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse fxml_ctvt_nh1_2025(@Valid @RequestBody FXML_CTVT_NH1_REQ body) {
        String bodys = ""
        String thongTinChung = ""
        String thongtinChiTiet = ""
        StringBuilder thongtinFile = new StringBuilder()
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        Map<String, Object> inputLogData = new HashMap<>()
        inputLogData.put("class", "FMISService")
        inputLogData.put("action", "fxml_ctvt_nh")
        inputLogData.put("requestId", uuidAsString)
        inputLogData.put("onebss_username", genericApi.getSessionData(request).get(GenericApi.USERNAME))
        inputLogData.put("body", new Gson().toJson(body))
        try {
            loggingManaged.info("[FMISService] - [fxml_ctvt_nh] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", uuidAsString).put("body", new Gson().toJson(body)).build())
            if (body.dataChungTu != null && body.dataChungTu.size() > 0) {
                thongTinChung = """<so_id_ct>${body.dataChungTu[0].so_id_ct}</so_id_ct>   
                                    <l_ct>${body.dataChungTu[0].l_ct}</l_ct>
                                    <ngay_ht>${body.dataChungTu[0].ngay_ht}</ngay_ht>
                                    <so_ct>${body.dataChungTu[0].so_ct}</so_ct>
                                    <ngay_ct>${body.dataChungTu[0].ngay_ct}</ngay_ct>
                                    <k_ma_kh>${nvl(body.dataChungTu[0].k_ma_kh)}</k_ma_kh>
                                    <ma_kh>${nvl(body.dataChungTu[0].ma_kh)}</ma_kh>
                                    <ten>${nvl(body.dataChungTu[0].ten)}</ten>
                                    <ma_thue>${nvl(body.dataChungTu[0].ma_thue)}</ma_thue>
                                    <dchi>${nvl(body.dataChungTu[0].dchi)}</dchi>
                                    <hdong>${nvl(body.dataChungTu[0].hdong)}</hdong>
                                    <nd><![CDATA[${body.dataChungTu[0].nd}]]></nd>
                                    <ma_mdsd>${nvl(body.dataChungTu[0].ma_mdsd)}</ma_mdsd>
                                    <tien_tt>${body.dataChungTu[0].tien_tt}</tien_tt>"""
            }

            if (body.dataCT_ChungTu != null && body.dataCT_ChungTu.size() > 0) {
                for (Map<String, String> item : body.dataCT_ChungTu) {
                    thongtinChiTiet += """<ThongTinChiTiet>
                                                <kho>${nvl(item.kho)}</kho>
                                                <kho_m>${nvl(item.kho_m)}</kho_m>
                                                <nhom>${item.nhom}</nhom>
                                                <ma_vt>${nvl(item.ma_vt)}</ma_vt>
                                                <dv>${item.dv}</dv>
                                                <luong>${item.luong}</luong>
                                                <gia>${new BigDecimal(item.gia.toString())}</gia>
                                                <tien>${item.tien}</tien>
                                                <ct_nd>${nvl(item.ct_nd)}</ct_nd>       
                                            </ThongTinChiTiet>"""
                }
            }

            if(body.dataFlie != null && body.dataFlie.size() > 0){
                for(item in body.dataFlie){
                    for(dt in item["urls"]) {
                        thongtinFile.append(
                                """
                                    <FileUpLoad>
                                        <protocol>https</protocol>
                                        <path>${StringEscapeUtils.escapeHtml4(dt["url"].toString())}</path>
                                        <noi_dung>${dt["title"]}</noi_dung>
                                    </FileUpLoad>
                                """)
                    }
                }
                bodys =
                        """<?xml version="1.0" encoding="utf-8"?>
                        <soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                            <soap12:Body>
                                <Fs_CTVT_NH xmlns="http://tempuri.org/">
                                    <dongBoVatTu>
                                        <b_ma_dvi>${body.b_ma_dvi}</b_ma_dvi>
                                        <b_ma_nsd>${body.b_ma_nsd}</b_ma_nsd>
                                        <id_ct_nh>${body.id_ct_nh}</id_ct_nh>
                                        <ThongTinChung>$thongTinChung</ThongTinChung>
                                        <ChiTiet>$thongtinChiTiet</ChiTiet>
                                        <FileUpLoads>${thongtinFile}</FileUpLoads>
                                    </dongBoVatTu>
                                </Fs_CTVT_NH>
                            </soap12:Body>
                        </soap12:Envelope>"""
            }else {
                bodys = """<?xml version="1.0" encoding="utf-8"?>
                        <soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                            <soap12:Body>
                                <Fs_CTVT_NH xmlns="http://tempuri.org/">
                                    <dongBoVatTu>
                                        <b_ma_dvi>${body.b_ma_dvi}</b_ma_dvi>
                                        <b_ma_nsd>${body.b_ma_nsd}</b_ma_nsd>
                                        <id_ct_nh>${body.id_ct_nh}</id_ct_nh>
                                        <ThongTinChung>$thongTinChung</ThongTinChung>
                                        <ChiTiet>$thongtinChiTiet</ChiTiet>
                                    </dongBoVatTu>
                                </Fs_CTVT_NH>
                            </soap12:Body>
                        </soap12:Envelope>"""
            }

            def env = post1(fmisSoapUrl_2025,bodys)

            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                inputLogData.put("faultCode", fault.Code.text())
                inputLogData.put("faultString", fault.Reason.text())
                loggingManaged.info(inputLogData)
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            inputLogData.put("data", env.Body.Fs_CTVT_NHResponse.Fs_CTVT_NHResult.text())
            loggingManaged.info(inputLogData)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: env.Body.Fs_CTVT_NHResponse.Fs_CTVT_NHResult.text())
        } catch (Exception e) {
            inputLogData.put("error", e.message)
            loggingManaged.info(inputLogData)
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/fxml_ctvt_xoa")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse fxml_ctvt_xoa(@Valid @RequestBody FXML_CTVT_XOA_REQ body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        Map<String, Object> inputLogData = new HashMap<>()
        inputLogData.put("class", "FMISService")
        inputLogData.put("action", "fxml_ctvt_xoa")
        inputLogData.put("requestId", uuidAsString)
        inputLogData.put("onebss_username", genericApi.getSessionData(request).get(GenericApi.USERNAME))
        inputLogData.put("body", new Gson().toJson(body))
        try {
            String bb_pass = body.b_pass.replaceAll("&", "&amp;")
            def env = post1(fmisSoapUrl,
                    """<?xml version="1.0" encoding="utf-8"?>
                        <soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                            <soap12:Body>
                                <Fxml_CTVT_XOA xmlns="http://tempuri.org/">
                                    <b_ma_dvi>${body.b_ma_dvi}</b_ma_dvi>
                                    <b_ma_nsd>${body.b_ma_nsd}</b_ma_nsd>
                                    <b_pass><![CDATA[${bb_pass}]]></b_pass>
                                    <b_so_id>${body.b_so_id}</b_so_id>
                                </Fxml_CTVT_XOA>
                            </soap12:Body>
                        </soap12:Envelope>""")
            
            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                inputLogData.put("faultCode", fault.Code.text())
                inputLogData.put("faultString", fault.Reason.text())
                loggingManaged.info(inputLogData)
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            inputLogData.put("data", env.Body.Fxml_CTVT_XOAResponse.Fxml_CTVT_XOAResult.text())
            loggingManaged.info(inputLogData)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: env.Body.Fxml_CTVT_XOAResponse.Fxml_CTVT_XOAResult.text())
        } catch (Exception e) {
            inputLogData.put("error", e.message)
            loggingManaged.info(inputLogData)
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/fxml_ctvt_xoa_2025")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse fxml_ctvt_xoa_2025(@Valid @RequestBody FXML_CTVT_XOA_REQ body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        Map<String, Object> inputLogData = new HashMap<>()
        inputLogData.put("class", "FMISService")
        inputLogData.put("action", "fxml_ctvt_xoa")
        inputLogData.put("requestId", uuidAsString)
        inputLogData.put("onebss_username", genericApi.getSessionData(request).get(GenericApi.USERNAME))
        inputLogData.put("body", new Gson().toJson(body))
        try {
            String bb_pass = body.b_pass.replaceAll("&", "&amp;")
            def env = post1(fmisSoapUrl_2025,
                    """<?xml version="1.0" encoding="utf-8"?>
                        <soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                            <soap12:Body>
                                <Fxml_CTVT_XOA xmlns="http://tempuri.org/">
                                    <b_ma_dvi>${body.b_ma_dvi}</b_ma_dvi>
                                    <b_ma_nsd>${body.b_ma_nsd}</b_ma_nsd>
                                    <b_pass><![CDATA[${bb_pass}]]></b_pass>
                                    <b_so_id>${body.b_so_id}</b_so_id>
                                </Fxml_CTVT_XOA>
                            </soap12:Body>
                        </soap12:Envelope>""")

            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                inputLogData.put("faultCode", fault.Code.text())
                inputLogData.put("faultString", fault.Reason.text())
                loggingManaged.info(inputLogData)
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            inputLogData.put("data", env.Body.Fxml_CTVT_XOAResponse.Fxml_CTVT_XOAResult.text())
            loggingManaged.info(inputLogData)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: env.Body.Fxml_CTVT_XOAResponse.Fxml_CTVT_XOAResult.text())
        } catch (Exception e) {
            inputLogData.put("error", e.message)
            loggingManaged.info(inputLogData)
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/fs_bss_ktdh_dhsxkd")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse fs_bss_ktdh_dhsxkd(@Valid @RequestBody FS_BSS_KTDH_DHSXKD_REQ body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        Map<String, Object> inputLogData = new HashMap<>()
        inputLogData.put("class", "FMISService")
        inputLogData.put("action", "fs_bss_ktdh_dhsxkd")
        inputLogData.put("requestId", uuidAsString)
        inputLogData.put("onebss_username", genericApi.getSessionData(request).get(GenericApi.USERNAME))
        inputLogData.put("body", new ObjectMapper().writeValueAsString(body))
        try {
            String bb_pas = body.pas.replaceAll("&", "&amp;")
            String bb_mat_khau = body.mat_khau.replaceAll("&", "&amp;")
            StringBuilder data_chitiet = new StringBuilder()
            for (item in body.data_chitiet){
                data_chitiet.append("""
                                    <ktdh_dhsxkd_chitiet>
                                        <id_chitiet>${item.id_chitiet}</id_chitiet>
                                        <ma_dvi_ps>${item.ma_dvi_ps}</ma_dvi_ps>
                                        <ma_dvi_nh>${item.ma_dvi_nh}</ma_dvi_nh>
                                        <ma_tk_kho>${item.ma_tk_kho}</ma_tk_kho>
                                        <ma_tk_du>${item.ma_tk_du}</ma_tk_du>
                                        <tien>${item.tien}</tien>
                                    </ktdh_dhsxkd_chitiet>
                                    """)
            }
            String bodys = ""
            if(body.data_file > 0) {
                StringBuilder data_file = new StringBuilder()
                String
                for (it in body.data_file){
                    for(dt in it?.urls){
                        data_file.append("""
                                     <FileUpLoad>
                                        <protocol>
                                            https
                                        </protocol>
                                        <path>
                                            ${StringEscapeUtils.escapeHtml4(dt?.url?.toString())}
                                        </path>
                                        <noi_dung>
                                            ${dt?.title}
                                        </noi_dung>
                                     </FileUpLoad>
                                     """)
                    }
                }
                bodys = """<?xml version="1.0" encoding="utf-8"?>
                            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                              <soap:Body>
                                <FS_BSS_ktdh_dhsxkd xmlns="http://tempuri.org/">
                                  <dhsxkd>
                                    <ma_dvi>${body.ma_dvi}</ma_dvi>
                                    <user>${body.user}</user>
                                    <pas>${bb_pas}</pas>
                                    <nsd>${body.nsd}</nsd>
                                    <mat_khau>${bb_mat_khau}</mat_khau>
                                    <id_phieu>${body.id_phieu}</id_phieu>
                                    <so_phieu>${body.so_phieu}</so_phieu>
                                    <ngay_ht>${body.ngay_ht}</ngay_ht>
                                    <loai>${body.loai}</loai>
                                    <noi_dung>${body.noi_dung}</noi_dung>
                                    <nguoi_lap>${body.nguoi_lap}</nguoi_lap>
                                    <email>${body.email}</email>
                                    <so_phieu_dh>${body.so_phieu_dh}</so_phieu_dh>
                                    <data_chitiet>
                                      ${data_chitiet}
                                    </data_chitiet>
                                    <FileUpLoads>${data_file}</FileUpLoads>
                                  </dhsxkd>
                                </FS_BSS_ktdh_dhsxkd>
                              </soap:Body>
                            </soap:Envelope>"""
            }else {
                bodys = """<?xml version="1.0" encoding="utf-8"?>
                            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                              <soap:Body>
                                <FS_BSS_ktdh_dhsxkd xmlns="http://tempuri.org/">
                                  <dhsxkd>
                                    <ma_dvi>${body.ma_dvi}</ma_dvi>
                                    <user>${body.user}</user>
                                    <pas>${bb_pas}</pas>
                                    <nsd>${body.nsd}</nsd>
                                    <mat_khau>${bb_mat_khau}</mat_khau>
                                    <id_phieu>${body.id_phieu}</id_phieu>
                                    <so_phieu>${body.so_phieu}</so_phieu>
                                    <ngay_ht>${body.ngay_ht}</ngay_ht>
                                    <loai>${body.loai}</loai>
                                    <noi_dung>${body.noi_dung}</noi_dung>
                                    <nguoi_lap>${body.nguoi_lap}</nguoi_lap>
                                    <email>${body.email}</email>
                                    <so_phieu_dh>${body.so_phieu_dh}</so_phieu_dh>
                                    <data_chitiet>
                                      ${data_chitiet}
                                    </data_chitiet>
                                  </dhsxkd>
                                </FS_BSS_ktdh_dhsxkd>
                              </soap:Body>
                            </soap:Envelope>"""
            }
            def env = post1(fmisKtdh, bodys)
            def fault = new JsonSlurper().parseText(env.toString())
            inputLogData.response_fmis = fault
            loggingManaged.info(new ObjectMapper().writeValueAsString(inputLogData))
            if (fault?.status?.toString() == "200") {
                return new CommonResponse(errorCode: 0, faultCode: fault?.id_phieu, faultString: fault?.thong_bao, data: fault?.data)
            }
            return new CommonResponse(errorCode: 1, faultCode: fault?.id_phieu, faultString: fault?.thong_bao, data: fault?.data)
        } catch (Exception e) {
            inputLogData.put("error", e.message)
            loggingManaged.info(inputLogData)
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/fs_bss_ktdh_dhsxkd_2025")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse fs_bss_ktdh_dhsxkd_2025(@Valid @RequestBody FS_BSS_KTDH_DHSXKD_REQ body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        Map<String, Object> inputLogData = new HashMap<>()
        inputLogData.put("class", "FMISService")
        inputLogData.put("action", "fs_bss_ktdh_dhsxkd")
        inputLogData.put("requestId", uuidAsString)
        inputLogData.put("onebss_username", genericApi.getSessionData(request).get(GenericApi.USERNAME))
        inputLogData.put("body", new ObjectMapper().writeValueAsString(body))
        try {
            String bb_pas = body.pas.replaceAll("&", "&amp;")
            String bb_mat_khau = body.mat_khau.replaceAll("&", "&amp;")
            StringBuilder data_chitiet = new StringBuilder()
            for (item in body.data_chitiet){
                data_chitiet.append("""
                                    <ktdh_dhsxkd_chitiet>
                                        <id_chitiet>${item.id_chitiet}</id_chitiet>
                                        <ma_dvi_ps>${item.ma_dvi_ps}</ma_dvi_ps>
                                        <ma_dvi_nh>${item.ma_dvi_nh}</ma_dvi_nh>
                                        <ma_tk_kho>${item.ma_tk_kho}</ma_tk_kho>
                                        <ma_tk_du>${item.ma_tk_du}</ma_tk_du>
                                        <tien>${item.tien}</tien>
                                    </ktdh_dhsxkd_chitiet>
                                    """)
            }
            String bodys = ""
            if(body.data_file > 0) {
                StringBuilder data_file = new StringBuilder()
                String
                for (it in body.data_file){
                    for(dt in it?.urls){
                        data_file.append("""
                                     <FileUpLoad>
                                        <protocol>
                                            https
                                        </protocol>
                                        <path>
                                            ${StringEscapeUtils.escapeHtml4(dt?.url?.toString())}
                                        </path>
                                        <noi_dung>
                                            ${dt?.title}
                                        </noi_dung>
                                     </FileUpLoad>
                                     """)
                    }
                }
                bodys = """<?xml version="1.0" encoding="utf-8"?>
                            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                              <soap:Body>
                                <FS_BSS_ktdh_dhsxkd xmlns="http://tempuri.org/">
                                  <dhsxkd>
                                    <ma_dvi>${body.ma_dvi}</ma_dvi>
                                    <user>${body.user}</user>
                                    <pas>${bb_pas}</pas>
                                    <nsd>${body.nsd}</nsd>
                                    <mat_khau>${bb_mat_khau}</mat_khau>
                                    <id_phieu>${body.id_phieu}</id_phieu>
                                    <so_phieu>${body.so_phieu}</so_phieu>
                                    <ngay_ht>${body.ngay_ht}</ngay_ht>
                                    <loai>${body.loai}</loai>
                                    <noi_dung>${body.noi_dung}</noi_dung>
                                    <nguoi_lap>${body.nguoi_lap}</nguoi_lap>
                                    <email>${body.email}</email>
                                    <so_phieu_dh>${body.so_phieu_dh}</so_phieu_dh>
                                    <data_chitiet>
                                      ${data_chitiet}
                                    </data_chitiet>
                                    <FileUpLoads>${data_file}</FileUpLoads>
                                  </dhsxkd>
                                </FS_BSS_ktdh_dhsxkd>
                              </soap:Body>
                            </soap:Envelope>"""
            }else {
                bodys = """<?xml version="1.0" encoding="utf-8"?>
                            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                              <soap:Body>
                                <FS_BSS_ktdh_dhsxkd xmlns="http://tempuri.org/">
                                  <dhsxkd>
                                    <ma_dvi>${body.ma_dvi}</ma_dvi>
                                    <user>${body.user}</user>
                                    <pas>${bb_pas}</pas>
                                    <nsd>${body.nsd}</nsd>
                                    <mat_khau>${bb_mat_khau}</mat_khau>
                                    <id_phieu>${body.id_phieu}</id_phieu>
                                    <so_phieu>${body.so_phieu}</so_phieu>
                                    <ngay_ht>${body.ngay_ht}</ngay_ht>
                                    <loai>${body.loai}</loai>
                                    <noi_dung>${body.noi_dung}</noi_dung>
                                    <nguoi_lap>${body.nguoi_lap}</nguoi_lap>
                                    <email>${body.email}</email>
                                    <so_phieu_dh>${body.so_phieu_dh}</so_phieu_dh>
                                    <data_chitiet>
                                      ${data_chitiet}
                                    </data_chitiet>
                                  </dhsxkd>
                                </FS_BSS_ktdh_dhsxkd>
                              </soap:Body>
                            </soap:Envelope>"""
            }
            def env = post1(fmisKtdh_2025, bodys)
            def fault = new JsonSlurper().parseText(env.toString())
            inputLogData.response_fmis = fault
            loggingManaged.info(new ObjectMapper().writeValueAsString(inputLogData))
            if (fault?.status?.toString() == "200") {
                return new CommonResponse(errorCode: 0, faultCode: fault?.id_phieu, faultString: fault?.thong_bao, data: fault?.data)
            }
            return new CommonResponse(errorCode: 1, faultCode: fault?.id_phieu, faultString: fault?.thong_bao, data: fault?.data)
        } catch (Exception e) {
            inputLogData.put("error", e.message)
            loggingManaged.info(inputLogData)
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/fs_ctvt_nh_trang_thai")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse fs_ctvt_nh_trang_thai(@Valid @RequestBody Map body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            loggingManaged.info("[FMISService] - [fs_ctvt_nh_trang_thai] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", uuidAsString).put("body", new Gson().toJson(body)).build())
            Map<String, Object> session = genericApi.getSessionData(request)
            String ma_tinh = (session[GenericApi.MA_TINH]).toString()
            if(ma_tinh == "DNO"){
                ma_tinh = "DAN"
            }
            String donvi = donVi.get_dv(ma_tinh)
            def env = post1(fmisSoapUrl,
                    """<?xml version="1.0" encoding="utf-8"?>
                        <soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                            <soap12:Body>
                                <Fs_CTVT_NH_TRANG_THAI xmlns="http://tempuri.org/">
                                    <b_ma_dvi>${donvi}</b_ma_dvi>
                                    <b_ma_nsd>${"admin"}</b_ma_nsd>
                                    <b_pass><![CDATA[${"bkjcq74xZDl2YtVzQaEHjQ=="}]]></b_pass>
                                    <b_so_phieu>${body["so_phieu"]}</b_so_phieu>
                                </Fs_CTVT_NH_TRANG_THAI>
                            </soap12:Body>
                        </soap12:Envelope>""")

            def data = new JsonSlurper().parseText(env.toString())
            if(data?.code.toString() == "200"){
                loggingManaged.info("[FMISService] - [fs_ctvt_nh_trang_thai] - Request: ", new ImmutableMap.Builder<String, Object>()
                        .put("request_id", uuidAsString).put("body", env.toString()).build())
                return new CommonResponse( errorCode : 0, faultCode : "", faultString : data?.NoiDung.toString(), data :data?.data)
            }else {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: data?.NoiDung.toString(), data: "Lỗi")
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/fs_dh_ct_trang_thai")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse fs_dh_ct_trang_thai(@Valid @RequestBody Map body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            loggingManaged.info("[FMISService] - [fs_ctvt_nh_trang_thai] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", uuidAsString).put("body", new Gson().toJson(body)).build())
            Map<String, Object> session = genericApi.getSessionData(request)
            def env = post1(fmisKtdh,
                    """<soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                              <soap12:Body>
                                <Fs_DH_CT_TRANG_THAI xmlns="http://tempuri.org/">
                                  <b_ma_dvi>${body.b_ma_dvi}</b_ma_dvi>
                                  <b_ma_nsd>${body.b_ma_nsd}</b_ma_nsd>
                                  <b_pass>${body.b_pass}</b_pass>
                                  <b_so_phieu>${body.b_so_phieu}</b_so_phieu>
                                </Fs_DH_CT_TRANG_THAI>
                              </soap12:Body>
                            </soap12:Envelope>""")

            def data = new JsonSlurper().parseText(env.toString())
            if(data?.code.toString() == "200"){
                loggingManaged.info("[FMISService] - [fs_ctvt_nh_trang_thai] - Request: ", new ImmutableMap.Builder<String, Object>()
                        .put("request_id", uuidAsString).put("body", env.toString()).build())
                return new CommonResponse( errorCode : 0, faultCode : "", faultString : data?.NoiDung.toString(), data :data?.DuLieu)
            }else {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: data?.NoiDung.toString(), data: "Lỗi")
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/fs_dh_ct_trang_thai_2025")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse fs_dh_ct_trang_thai_2025(@Valid @RequestBody Map body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            loggingManaged.info("[FMISService] - [fs_ctvt_nh_trang_thai] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", uuidAsString).put("body", new Gson().toJson(body)).build())
            Map<String, Object> session = genericApi.getSessionData(request)
            def env = post1(fmisKtdh_2025,
                    """<soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                              <soap12:Body>
                                <Fs_DH_CT_TRANG_THAI xmlns="http://tempuri.org/">
                                  <b_ma_dvi>${body.b_ma_dvi}</b_ma_dvi>
                                  <b_ma_nsd>${body.b_ma_nsd}</b_ma_nsd>
                                  <b_pass>${body.b_pass}</b_pass>
                                  <b_so_phieu>${body.b_so_phieu}</b_so_phieu>
                                </Fs_DH_CT_TRANG_THAI>
                              </soap12:Body>
                            </soap12:Envelope>""")

            def data = new JsonSlurper().parseText(env.toString())
            if(data?.code.toString() == "200"){
                loggingManaged.info("[FMISService] - [fs_ctvt_nh_trang_thai] - Request: ", new ImmutableMap.Builder<String, Object>()
                        .put("request_id", uuidAsString).put("body", env.toString()).build())
                return new CommonResponse( errorCode : 0, faultCode : "", faultString : data?.NoiDung.toString(), data :data?.DuLieu)
            }else {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: data?.NoiDung.toString(), data: "Lỗi")
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    GPathResult post1(String url, String body) {
        Map<String, String> headers = [:]
        headers.put("Host", "fmis.vnpt.vn")
        headers.put("Content-Type", "text/xml; charset=utf-8")
        String resp = httpManaged.postWithTimeout(["url": url], headers, body, 180000)
        return new XmlSlurper().parseText(resp)
    }
}
