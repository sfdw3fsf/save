package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.sql.Sql
import groovy.util.slurpersupport.GPathResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.fmsalarms.UPDATE_IMS_PASSWORD_DHSX_REQ
import vn.vnptit.sapi.oneapp.models.fmsalarms.POST_NET_DHSX_TO_TTS_REQ
import vn.vnptit.sapi.oneapp.models.fmsalarms.PRECHECK_ACCESS_HW_KPI_REQ
import vn.vnptit.sapi.oneapp.models.fmsalarms.PRECHECK_XLSC_KPI_REQ
import vn.vnptit.sapi.oneapp.models.fmsalarms.FMSBB_CLOSE_TICKET_REQ
import vn.vnptit.sapi.oneapp.models.fmsalarms.SYNC_VIA_GW_OSS_REQ
import vn.vnptit.sapi.oneapp.redis.RedisRepository
import vn.vnptit.sapi.oneapp.util.GenericApi
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.service.tts.SyncViaGwOssRequestDTO

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.text.SimpleDateFormat

@RestController
@RequestMapping("fms_alarms")
class FMSAlarmsService {

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    HttpManaged httpManaged

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    RedisRepository redisRepository

    @Autowired
    OracleManaged oracleManaged

    @Autowired
    GenericApi genericApi

    @Autowired
    NativeWebRequest request

    @Value("\${spring.httpservices.fms_alarms.url}")
    String fmsUrl

    @Value("\${external.net.get_token_url}")
    String netGetTokenUrl

    @Value("\${external.net.save_url}")
    String netSaveUrl

    @Value("\${external.net.username}")
    String netUsername

    @Value("\${external.net.password}")
    String netPassword

    @Value("\${external.net.grant_type}")
    String netGrantType

    @Value("\${external.tts.save_url}")
    String ttsSaveUrl

    @Value("\${external.tts.cp_code}")
    String ttsCpCode

    @Value("\${external.tts.secure_key}")
    String ttsSecureKey

    @Value("\${external.tts.user_name}")
    String ttsUserName

    @Value("\${external.fmsbb.precheck_url}")
    String fmsbbPrecheckUrl

    @Value("\${external.fmsbb.precheck_kpi_url}")
    String fmsbbPrecheckKpiUrl

    @Value("\${external.fmsbb.close_ticket_url}")
    String fmsbbCloseTicketUrl

    @Value("\${external.fmsbb.api_key:}") // TODO: Bổ sung sau khi được cấp x-api-key
    String fmsbbApiKey

    @PostMapping("/update_ims_password_dhsx")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse update_ims_password_dhsx(@Valid @RequestBody UPDATE_IMS_PASSWORD_DHSX_REQ body) {
        try {
            def env = post1(fmsUrl,
                    """<?xml version="1.0" encoding="utf-8"?>
                        <soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                            <soap12:Body>
                                <update_ims_password_dhsx xmlns="http://tempuri.org/">
                                    <_ma_tinh>${body.ma_tinh}</_ma_tinh>
                                    <p_ma_tb>${body.ma_tb}</p_ma_tb>
                                    <p_pass_ims>${body.pass_ims}</p_pass_ims>
                                </update_ims_password_dhsx>
                            </soap12:Body>
                        </soap12:Envelope>""")

            String kq = env.Body.update_ims_password_dhsxResponse.update_ims_password_dhsxResult.text()
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: kq)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/post_net_dhsx_to_tts")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse post_net_dhsx_to_tts(@Valid @RequestBody POST_NET_DHSX_TO_TTS_REQ body) {
        try {
            // Parse jsonData to check MA_QUYTRINH
            def parsedData = new JsonSlurper().parseText(body.jsonData)

            loggingManaged.info(new ImmutableMap.Builder<String, Object>()
                    .put("action", "[FMSAlarmsService] - [Start] - Request")
                    .put("requestBody", new JsonBuilder(parsedData).toString())
                    .build())

            String maQuytrinh = parsedData?.MA_QUYTRINH?.toString()

            if (maQuytrinh in ["QT1.6.1", "QTXLSC_MANE", "QTXLSC_ACCESS", "QT1.4"]) {
                return handleSaveCssApi(parsedData)
            } else if (maQuytrinh == "C3_CLM_03") {
                return handleSaveCssApiCellClk(parsedData)
            }

            // Existing SOAP call for other cases
            def env = post1(fmsUrl,
                    """<?xml version="1.0" encoding="utf-8"?>
                        <soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                            <soap12:Body>
                                <post_net_dhsx_to_tts xmlns="http://tempuri.org/">
                                    <jsonData>${body.jsonData}</jsonData>
                                </post_net_dhsx_to_tts>
                            </soap12:Body>
                        </soap12:Envelope>""")

            String kq = env.Body.post_net_dhsx_to_ttsResponse.post_net_dhsx_to_ttsResult.text()
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: kq)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/sync_via_gw_oss")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse sync_via_gw_oss(@Valid @RequestBody SYNC_VIA_GW_OSS_REQ body) {
        Map<String, Object> logMap = new LinkedHashMap<>()
        logMap.put("request_id", UUID.randomUUID().toString())
        logMap.put("action", "/fms_alarms/sync_via_gw_oss")
        logMap.put("requestBody", new JsonBuilder(body).toString())
        boolean isError = false
        try {
            Map<String, Object> sessionData = genericApi.getSessionData(request)
            logMap.put("username", sessionData.get(GenericApi.USERNAME) ?: "")
            logMap.put("email", sessionData.get(GenericApi.EMAIL) ?: "")

            // --- Build security headers ---
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss")
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"))
            String requestDate = sdf.format(new Date())

            String combinedStr = logMap.get("request_id") + "|" + requestDate + "|" + ttsCpCode + "|" + ttsSecureKey
            String secureCode = getSHA512Hash(combinedStr)
            String accessKey = getSHA256Hash(ttsUserName + "|" + ttsSecureKey)

            Map<String, String> apiHeaders = new HashMap<>()
            apiHeaders.put("Content-Type", "application/json")
            apiHeaders.put("X-Cp-Code", ttsCpCode)
            apiHeaders.put("X-Secure-Code", secureCode)
            apiHeaders.put("X-Request-Id", logMap.get("request_id"))
            apiHeaders.put("X-Request-Date", requestDate)
            apiHeaders.put("X-User-Name", ttsUserName)
            apiHeaders.put("X-Access-Key", accessKey)

            // --- Build payload ---
            SyncViaGwOssRequestDTO requestDTO = new SyncViaGwOssRequestDTO()
            requestDTO.loaiphieu_id = body.loaiphieu_id
            requestDTO.parent_id = body.tts_parent_id
            requestDTO.id_tts = body.id_tts
            requestDTO.thongtin_chitiet = body.noi_dung_xu_ly
            requestDTO.ma_nhanvien_xuly = body.ma_nhanvien_xuly
            requestDTO.ma_donvi_xuly_hrm = body.ma_donvi_xuly_hrm
            requestDTO.sla_date = body.sla_date
            requestDTO.sla_process_time = body.sla_process_time
            requestDTO.sla = body.sla
            requestDTO.quahan_taibuoc = body.quahan_taibuoc
            requestDTO.quahan_toantrinh = body.quahan_toantrinh

            String apiBody = objectMapper.writeValueAsString(requestDTO)
            logMap.put("input", apiBody)

            String apiResp = httpManaged.post(["url": ttsSaveUrl], apiHeaders, apiBody)

            def apiResult
            try {
                apiResult = new JsonSlurper().parseText(apiResp)
            } catch (Exception ignore) {
                apiResult = apiResp
            }
            logMap.put("output", apiResult != null ? new JsonBuilder(apiResult).toString() : "")

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: apiResult)
        } catch (Exception e) {
            isError = true
            logMap.put("error", e.message)
            return CommonResponse.resolveException(e, loggingManaged)
        } finally {
            logMap.put("type", isError ? "error" : "success")
            loggingManaged.info(logMap)
        }
    }

    /**
     * Handle MA_QUYTRINH = QT1.6.1 case:
     * Convert input body to lowercase-key format and POST to SaveCSSAPI REST endpoint.
     */
    private CommonResponse handleSaveCssApi(def parsedData) {
        Map<String, Object> logMap = new LinkedHashMap<>()
        logMap.put("request_id", UUID.randomUUID().toString())
        logMap.put("action", "handleSaveCssApi")
        logMap.put("requestBody", new JsonBuilder(parsedData).toString())
        boolean isError = false
        try {
            Map<String, Object> sessionData = genericApi.getSessionData(request)
            logMap.put("username", sessionData.get(GenericApi.USERNAME) ?: "")
            logMap.put("email", sessionData.get(GenericApi.EMAIL) ?: "")

        // Get Bearer token
        // String accessToken = getOrFetchNetToken(requestId, "handleSaveCssApi")

        // Parse ma_buoc
        String maBuoc = parsedData.MA_BUOC?.toString() ?: ""
        String maTinh = parsedData.MA_TINH?.toString() ?: ""
        if (!maBuoc.isEmpty() && !maTinh.isEmpty() && (parsedData.MA_QUYTRINH?.toString() == "QT1.6.1" || parsedData.MA_QUYTRINH?.toString() == "QTXLSC_MANE")) {
            String khuvuc = null;
            Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
                def row = sql.firstRow("SELECT KHU_VUC FROM qlsc.PHANCAP_NET WHERE MA_TINH = ?", [maTinh])
                khuvuc = row?.KHU_VUC
            }
            if (khuvuc != null) {
                switch (maBuoc) {
                    case "1.3":
                        if (khuvuc == "1") maBuoc = "1.3.1"
                        else if (khuvuc == "2") maBuoc = "1.3.2"
                        else if (khuvuc == "3") maBuoc = "1.3.3"
                        break
                    case "1.4":
                        if (khuvuc == "1") maBuoc = "1.4.1"
                        else if (khuvuc == "2") maBuoc = "1.4.2"
                        else if (khuvuc == "3") maBuoc = "1.4.3"
                        break
                    case "1.5":
                        if (khuvuc == "1") maBuoc = "1.5.1"
                        else if (khuvuc == "2") maBuoc = "1.5.2"
                        else if (khuvuc == "3") maBuoc = "1.5.3"
                        break
                    case "4.1":
                        if (khuvuc == "1") maBuoc = "4.1.1"
                        else if (khuvuc == "2") maBuoc = "4.1.2"
                        else if (khuvuc == "3") maBuoc = "4.1.3"
                        break
                    default:
                        break
                }
            }
        }

        if (!maBuoc.isEmpty() && !maTinh.isEmpty() &&
                parsedData.MA_QUYTRINH?.toString() == "QTXLSC_ACCESS") {

            String khuvuc = null
            Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
                def row = sql.firstRow("SELECT KHU_VUC FROM qlsc.PHANCAP_NET WHERE MA_TINH = ?", [maTinh])
                khuvuc = row?.KHU_VUC
            }

            if (khuvuc != null) {
                switch (maBuoc) {
                    case "4.1":
                        if (khuvuc == "1") maBuoc = "4.1.1"
                        else if (khuvuc == "2") maBuoc = "4.1.2"
                        else if (khuvuc == "3") maBuoc = "4.1.3"
                        break
                    case "6.1":
                        if (khuvuc == "1") maBuoc = "6.1.1"
                        else if (khuvuc == "2") maBuoc = "6.1.2"
                        else if (khuvuc == "3") maBuoc = "6.1.3"
                        break

                }
            }
        }

        // Build headers
        Map<String, String> apiHeaders = new HashMap<>()
        apiHeaders.put("Content-Type", "application/json")
        // apiHeaders.put("Authorization", "Bearer " + accessToken)

        // Convert UPPER_CASE input fields to lowercase format matching SaveCSSAPI
        Map<String, Object> apiPayload = [
                "id_tts"             : parsedData.ID_TTS?.toString() ?: "0",
                "id_css"             : null,
                "loaiphieu_id"       : "3",
                "parent_id"          : parsedData.PARENT_ID?.toString() ?: "0",
                "ma_tinh"            : maTinh,
                "mo_ta"              : parsedData.MO_TA?.toString() ?: "",
                "tg_batdau"          : parsedData.TG_BATDAU?.toString() ?: "",
                "tg_dukien_xyly"     : parsedData.TG_DUKIEN_XYLY?.toString() ?: "",
                "tg_ketthuc"         : parsedData.TG_KETTHUC?.toString() ?: "",
                "anhhuong_dvu"       : parsedData.ANHHUONG_DVU?.toString() ?: "0",
                "chitiet_anhhuong"   : parsedData.CHITIET_ANHHUONG?.toString() ?: "",
                "donvi_chuquan"      : parsedData.DONVI_CHUQUAN?.toString() ?: "",
                "dai_vt"             : parsedData.DAI_VT?.toString() ?: "",
                "mucdo_id"           : parsedData.MUCDO_ID?.toString() ?: "0",
                "loai_suco"          : parsedData.LOAI_SUCO?.toString() ?: "0",
                "hethong"            : parsedData.HETHONG?.toString() ?: "0",
                "thietbi_id"         : parsedData.THIETBI_ID?.toString() ?: "0",
                "ma_thietbi"         : parsedData.MA_THIETBI?.toString() ?: "",
                "nguon_sc"           : parsedData.NGUON_SC?.toString() ?: "",
                "thongtin_chitiet"   : parsedData.THONGTIN_CHITIET?.toString() ?: "",
                "nguyen_nhan"        : parsedData.NGUYEN_NHAN?.toString() ?: "",
                "giai_phap"          : parsedData.GIAI_PHAP?.toString() ?: "",
                "bienphap_phongngua" : parsedData.BIENPHAP_PHONGNGUA?.toString() ?: "",
                "thamso_thaydoi"     : parsedData.THAMSO_THAYDOI?.toString() ?: "",
                "trangthai_xly"      : parsedData.TRANGTHAI_XLY?.toString() ?: "0",
                "ma_quytrinh"        : parsedData.MA_QUYTRINH?.toString() ?: "",
                "ma_buoc"            : maBuoc,
                "ma_nhanvien_xuly"   : parsedData.MA_NHANVIEN_XULY?.toString() ?: "",
                "donvi_nhan_id"      : parsedData.DONVI_NHAN_ID?.toString() ?: "0",
                "ma_donvi_nhan_id"   : parsedData.MA_DONVI_NHAN_ID?.toString() ?: "",
                "donvi_giao_id"      : parsedData.DONVI_GIAO_ID?.toString() ?: "0",
                "css_parent_id"      : "0",
                "css_id"             : parsedData.CSS_ID?.toString() ?: "0",
                "donvi_xuly_id"      : "0",
                "loaidoituong"       : parsedData.LOAIDOITUONG?.toString() ?: "",
                "ma_donvi_xuly_hrm"  : parsedData.MA_DONVI_XULY_HRM?.toString() ?: "",
                "sla_date"           : parsedData.SLA_DATE?.toString() ?: "",
                "sla_process_time"   : parsedData.SLA_PROCESS_TIME?.toString() ?: "",
                "sla"                 : parsedData.SLA?.toString() ?: "",
                "quahan_taibuoc"      : parsedData.QUAHAN_TAIBUOC?.toString() ?: "",
                "quahan_toantrinh"    : parsedData.QUAHAN_TOANTRINH?.toString() ?: "",
                "phuongan_dexuat"    : []
        ]

        String apiBody = objectMapper.writeValueAsString(apiPayload)
        logMap.put("input", apiBody)

        String apiResp = httpManaged.post(["url": netSaveUrl], apiHeaders, apiBody)

        def apiResult
        try {
            apiResult = new JsonSlurper().parseText(apiResp)
        } catch (Exception ignore) {
            apiResult = apiResp
        }
        logMap.put("output", apiResult != null ? new JsonBuilder(apiResult).toString() : "")

        return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: apiResult)
        } catch (Exception e) {
            isError = true
            logMap.put("error", e.message)
            return CommonResponse.resolveException(e, loggingManaged)
        } finally {
            logMap.put("type", isError ? "error" : "success")
            loggingManaged.info(logMap)
        }
    }
    /**
     * Handle MA_QUYTRINH = C3_CLM_03 (QT CELL CLK) case:
     * Convert input body to lowercase-key format and POST to TTS SaveCSSAPI REST endpoint
     * with X-Secure-Code, X-Access-Key authentication headers.
     */
    private CommonResponse handleSaveCssApiCellClk(def parsedData) {
        Map<String, Object> logMap = new LinkedHashMap<>()
        logMap.put("request_id", UUID.randomUUID().toString())
        logMap.put("action", "handleSaveCssApiCellClk")
        logMap.put("requestBody", new JsonBuilder(parsedData).toString())
        boolean isError = false
        try {
            Map<String, Object> sessionData = genericApi.getSessionData(request)
            logMap.put("username", sessionData.get(GenericApi.USERNAME) ?: "")
            logMap.put("email", sessionData.get(GenericApi.EMAIL) ?: "")

        // Helper: lấy giá trị từ parsedData (tất cả dữ liệu lấy từ input body)
        def val = { String parsedKey ->
            def v = parsedData[parsedKey]
            return (v != null && v.toString() != "null") ? v.toString() : null
        }

        // --- Build security headers ---
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss")
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"))
        String requestDate = sdf.format(new Date())

        String combinedStr = logMap.get("request_id") + "|" + requestDate + "|" + ttsCpCode + "|" + ttsSecureKey
        String secureCode = getSHA512Hash(combinedStr)
        String accessKey = getSHA256Hash(ttsUserName + "|" + ttsSecureKey)

        Map<String, String> apiHeaders = new HashMap<>()
        apiHeaders.put("Content-Type", "application/json")
        apiHeaders.put("X-Cp-Code", ttsCpCode)
        apiHeaders.put("X-Secure-Code", secureCode)
        apiHeaders.put("X-Request-Id", logMap.get("request_id"))
        apiHeaders.put("X-Request-Date", requestDate)
        apiHeaders.put("X-User-Name", ttsUserName)
        apiHeaders.put("X-Access-Key", accessKey)

        // Normalize loaiphieu_id: business rule requires mapping 2 -> 3.
        String loaiPhieuId = val("LOAIPHIEU_ID")
        if (loaiPhieuId == "2") {
            loaiPhieuId = "3"
        }

        // --- Build payload: tất cả dữ liệu từ input body ---
        Map<String, Object> apiPayload = [
                "id_tts"             : val("ID_TTS"),
                "id_css"             : val("ID_CSS"),
                "loaiphieu_id"       : loaiPhieuId,
                "parent_id"          : val("PARENT_ID"),
                "ma_tinh"            : val("MA_TINH"),
                "mo_ta"              : val("MO_TA"),
                "tg_batdau"          : val("TG_BATDAU"),
                "tg_dukien_xyly"     : val("TG_DUKIEN_XYLY"),
                "tg_ketthuc"         : val("TG_KETTHUC"),
                "tg_chuyenphieu"     : val("TG_CHUYENPHIEU"), 
                "anhhuong_dvu"       : val("ANHHUONG_DVU"),
                "chitiet_anhhuong"   : val("CHITIET_ANHHUONG"),
                "donvi_chuquan"      : val("DONVI_CHUQUAN"),
                "dai_vt"             : val("DAI_VT"),
                "mucdo_id"           : val("MUCDO_ID"),
                "loai_suco"          : val("LOAI_SUCO"),
                "hethong"            : val("HETHONG"),
                "thietbi_id"         : val("THIETBI_ID"),
                "ma_thietbi"         : val("MA_THIETBI"),
                "nguon_sc"           : val("NGUON_SC"),
                "thongtin_chitiet"   : val("THONGTIN_CHITIET"),
                "nguyen_nhan"        : val("NGUYEN_NHAN"),
                "giai_phap"          : val("GIAI_PHAP"),
                "bienphap_phongngua" : val("BIENPHAP_PHONGNGUA"),
                "thamso_thaydoi"     : val("THAMSO_THAYDOI"),
                "trangthai_xly"      : val("TRANGTHAI_XLY"),
                "ma_quytrinh"        : val("MA_QUYTRINH"),
                "ma_buoc_hien_tai"   : val("MA_BUOC_HIEN_TAI"),                 
                "ma_buoc_tiep_theo"  : val("MA_BUOC"),                           
                "ma_nhanvien_xuly"   : val("MA_NHANVIEN_XULY"),
                "donvi_xuly_id"      : val("DONVI_XULY_ID"),                     
                "ma_donvi_xuly_hrm"  : val("MA_DONVI_XULY_HRM"),               
                "donvi_nhan_id"      : val("DONVI_NHAN_ID"),
                "ma_donvi_nhan_id"   : val("MA_DONVI_NHAN_ID"),
                "donvi_giao_id"      : val("DONVI_GIAO_ID"),
                "ma_donvi_giao_hrm"  : val("MA_DONVI_GIAO_HRM"),                
                "css_parent_id"      : val("CSS_PARENT_ID"),                    
                "css_id"             : val("CSS_ID"),
                "loaidoituong"       : val("LOAIDOITUONG"),
                "id_hethong"         : val("ID_HETHONG"),
                "diachine"           : val("DIACHINE"),
                "tenhang"            : val("TENHANG"),
                "phanloaitram"       : val("PHANLOAITRAM"),
                "ma_csht"            : val("MA_CSHT"),
                "loaicanhbao"        : val("LOAICANHBAO"),
                "muc_donvi_nhan"     : val("MUC_DONVI_NHAN"),
                "ten_donvi_giao"     : val("TEN_DONVI_GIAO"),                   // *** APP CẦN BỔ SUNG ***
                "ten_donvi_nhan"     : val("TEN_DONVI_NHAN"),                   // *** APP CẦN BỔ SUNG ***
                "ma_donvi_nhan_hrm"  : val("MA_DONVI_NHAN_HRM"),               // *** APP CẦN BỔ SUNG ***
                "id_fms"             : val("ID_FMS"),
                "nhomnn_id"          : val("NHOMNN_ID"),
                "nhom_nn"            : val("NHOM_NN"),
                "nguyennhan_id"      : val("NGUYENNHAN_ID"),
                "Ma_quytrinh_cha"    : val("MA_QUYTRINH_CHA"),
                "qos"                : val("QOS"),
                "qoe"                : val("QOE"),
                "voice"              : val("VOICE"),
                "cei"                : val("CEI"),
                "ma_nguyen_nhan_fms" : val("MA_NGUYEN_NHAN_FMS"),
                "VSAT"               : val("VSAT"),
                "MAY_NO"             : val("MAY_NO"),
                "KV_BIENBAO"         : val("KV_BIENBAO"),
                "MA_NHOM_TTP"        : val("MA_NHOM_TTP"),
                "sla_date"           : val("SLA_DATE"),
                "sla_process_time"   : val("SLA_PROCESS_TIME"),
                "sla"                 : val("SLA"),
                "quahan_taibuoc"      : val("QUAHAN_TAIBUOC"),
                "quahan_toantrinh"    : val("QUAHAN_TOANTRINH"),
                "phuongan_dexuat"    : (parsedData.PHUONGAN_DEXUAT ?: []).collect { item -> [
                        "idYeuCau"           : item.ID_YEU_CAU?.toString() ?: "",
                        "idBuoc"             : item.ID_BUOC?.toString() ?: "",
                        "phieu_id"           : item.PHIEU_ID != null ? item.PHIEU_ID as Integer : 0,
                        "phanloai_padx_id"   : item.PHANLOAI_PADX_ID != null ? item.PHANLOAI_PADX_ID as Integer : 0,
                        "padx_id"            : item.PADX_ID != null ? item.PADX_ID as Integer : 0,
                        "file_dokiem"        : item.FILE_DOKIEM?.toString() ?: "",
                        "file_dinhkem"       : item.FILE_DINHKEM?.toString() ?: "",
                        "ghichu"             : item.GHICHU?.toString() ?: "",
                        "loai_crs_hientruong": item.LOAI_CRS_HIENTRUONG?.toString() ?: "",
                        "phuongan_dexuat"    : item.PHUONGAN_DEXUAT?.toString() ?: "",
                        "docao_hientai"      : item.DOCAO_HIENTAI?.toString() ?: "",
                        "docao_dexuat"       : item.DOCAO_DEXUAT?.toString() ?: "",
                        "huong_hientai"      : item.HUONG_HIENTAI?.toString() ?: "",
                        "huong_dexuat"       : item.HUONG_DEXUAT?.toString() ?: "",
                        "mtilt_hientai"      : item.MTILT_HIENTAI?.toString() ?: "",
                        "mtilt_dexuat"       : item.MTILT_DEXUAT?.toString() ?: "",
                        "etilt_hientai"      : item.ETILT_HIENTAI?.toString() ?: "",
                        "etilt_dexuat"       : item.ETILT_DEXUAT?.toString() ?: "",
                        "ten_thamso"         : item.TEN_THAMSO?.toString() ?: "",
                        "ynghia_thamso"      : item.YNGHIA_THAMSO?.toString() ?: "",
                        "giatri_macdinh"     : item.GIATRI_MACDINH?.toString() ?: "",
                        "giatri_hientai"     : item.GIATRI_HIENTAI?.toString() ?: "",
                        "giatri_dexuat"      : item.GIATRI_DEXUAT?.toString() ?: "",
                        "loai_phuongan"      : item.LOAI_PHUONGAN?.toString() ?: "",
                        "muc_dich"           : item.MUC_DICH?.toString() ?: "",
                        "loai_tram"          : item.LOAI_TRAM?.toString() ?: "",
                        "quy_hoach"          : item.QUY_HOACH?.toString() ?: "",
                        "ma_csht_qh"         : item.MA_CSHT_QH?.toString() ?: "",
                        "csht_long"          : item.CSHT_LONG?.toString() ?: "",
                        "csht_lat"           : item.CSHT_LAT?.toString() ?: "",
                        "bang_tan"           : item.BANG_TAN?.toString() ?: "",
                        "cauhinh_dexuat"     : item.CAUHINH_DEXUAT?.toString() ?: "",
                        "goc_huong"          : item.GOC_HUONG?.toString() ?: "",
                        "antena_high"        : item.ANTENA_HIGH?.toString() ?: "",
                        "kc_tram_gannhat"    : item.KC_TRAM_GANNHAT?.toString() ?: "",
                        "congnghe"           : item.CONGNGHE?.toString() ?: "",
                        "vendor"             : item.VENDOR?.toString() ?: "",
                        "ten_vattu"          : item.TEN_VATTU?.toString() ?: "",
                        "so_luong"           : item.SO_LUONG?.toString() ?: "",
                        "dvt"                : item.DVT?.toString() ?: "",
                        "nguyennhan"         : item.NGUYENNHAN?.toString() ?: "",
                        "mucdich_bosung"     : item.MUCDICH_BOSUNG?.toString() ?: "",
                        "ten_license"        : item.TEN_LICENSE?.toString() ?: "",
                        "cauhinh"            : item.CAUHINH?.toString() ?: "",
                        "mucdich"            : item.MUCDICH?.toString() ?: "",
                        "loaitram"           : item.LOAITRAM?.toString() ?: "",
                        "chitiet_phuongan"   : item.CHITIET_PHUONGAN?.toString() ?: "",
                        "quyhoach"           : item.QUYHOACH?.toString() ?: "",
                        "ma_csht_quyhoach"   : item.MA_CSHT_QUYHOACH?.toString() ?: "",
                        "long_tbi"           : item.LONG_TBI?.toString() ?: "",
                        "lat_tbi"            : item.LAT_TBI?.toString() ?: "",
                        "bangtan"            : item.BANGTAN?.toString() ?: "",
                        "goc_tbi"            : item.GOC_TBI?.toString() ?: "",
                        "antena_tbi"         : item.ANTENA_TBI?.toString() ?: "",
                        "trafic_hientai"     : item.TRAFIC_HIENTAI?.toString() ?: "",
                        "tsd"                : item.TSD?.toString() ?: ""
                ]}
        ]

        String apiBody = objectMapper.writeValueAsString(apiPayload)
        logMap.put("input", apiBody)

        String apiResp = httpManaged.post(["url": ttsSaveUrl], apiHeaders, apiBody)

        def apiResult
        try {
            apiResult = new JsonSlurper().parseText(apiResp)
        } catch (Exception ignore) {
            apiResult = apiResp
        }
        logMap.put("output", apiResult != null ? new JsonBuilder(apiResult).toString() : "")

        return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: apiResult)
        } catch (Exception e) {
            isError = true
            logMap.put("error", e.message)
            return CommonResponse.resolveException(e, loggingManaged)
        } finally {
            logMap.put("type", isError ? "error" : "success")
            loggingManaged.info(logMap)
        }
    }

    /**
     * Helper: Get or fetch NET external API token with Redis caching
     */
    private String getOrFetchNetToken(String requestId, String callerName) {
        if (!netUsername || !netPassword) {
            throw new IllegalArgumentException("Missing external.net.username or external.net.password")
        }

        String redisKey = "OneBSS_esb_net_fms_token"
        String accessToken = null
        try {
            Object cachedToken = redisRepository.getKeyValue(redisKey)
            if (cachedToken) {
                accessToken = cachedToken.toString()
                loggingManaged.info(new ImmutableMap.Builder<String, Object>()
                        .put("request_id", requestId)
                        .put("action", "[FMSAlarmsService] - [" + callerName + "] - Token Retrieved From Cache")
                        .build())
            }
        } catch (Exception ex) {
            loggingManaged.warn(["request_id": requestId, "action": "[FMSAlarmsService] - [" + callerName + "] - Token Cache Read Failed", "message": ex.message])
        }

        if (!accessToken) {
            String tokenBody = "username=" + URLEncoder.encode(netUsername, "UTF-8") +
                    "&password=" + URLEncoder.encode(netPassword, "UTF-8") +
                    "&grant_type=" + URLEncoder.encode(netGrantType ?: "password", "UTF-8")
            Map<String, String> tokenHeaders = new HashMap<>()
            tokenHeaders.put("Content-Type", "application/x-www-form-urlencoded")

            String tokenResp = httpManaged.post(["url": netGetTokenUrl], tokenHeaders, tokenBody)
            def tokenJson = new JsonSlurper().parseText(tokenResp)
            accessToken = tokenJson?.access_token ?: tokenJson?.accessToken ?: tokenJson?.token
            if (!accessToken) {
                throw new IllegalStateException("Token response missing access_token")
            }

            try {
                Long tokenTtlMinutes = 23L * 60L
                redisRepository.setKeyValueExpire(redisKey, accessToken, tokenTtlMinutes)
                loggingManaged.info(new ImmutableMap.Builder<String, Object>()
                        .put("request_id", requestId)
                        .put("action", "[FMSAlarmsService] - [" + callerName + "] - Token Cached")
                        .build())
            } catch (Exception ex) {
                loggingManaged.warn(["request_id": requestId, "action": "[FMSAlarmsService] - [" + callerName + "] - Token Cache Write Failed", "message": ex.message])
            }
        }

        return accessToken
    }
    private String getSHA512Hash(String input) {
        MessageDigest md = MessageDigest.getInstance("SHA-512")
        byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8))
        StringBuilder hexString = new StringBuilder()
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b)
            if (hex.length() == 1) hexString.append('0')
            hexString.append(hex)
        }
        return hexString.toString()
    }

    private String getSHA256Hash(String input) {
        MessageDigest md = MessageDigest.getInstance("SHA-256")
        byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8))
        StringBuilder hexString = new StringBuilder()
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b)
            if (hex.length() == 1) hexString.append('0')
            hexString.append(hex)
        }
        return hexString.toString()
    }
    @PostMapping("/precheck_access_hw_kpi")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse precheck_access_hw_kpi(@Valid @RequestBody PRECHECK_ACCESS_HW_KPI_REQ body) {
        String requestId = UUID.randomUUID().toString()
        try {
            // Build request payload
            Map<String, Object> requestPayload = [
                    "device_ip": body.device_ip,
                    "alarm_id" : body.alarm_id,
                    "frame_id" : body.frame_id,
                    "slot_id"  : body.slot_id
            ]
            // port_id không bắt buộc: chỉ truyền nếu có giá trị (check lỗi port) - theo tài liệu
            if (body.port_id != null) {
                requestPayload.put("port_id", body.port_id)
            }

            String apiBody = objectMapper.writeValueAsString(requestPayload)

            Map<String, String> apiHeaders = new HashMap<>()
            apiHeaders.put("Content-Type", "application/json")
            apiHeaders.put("x-api-key", fmsbbApiKey) // TODO: Bổ sung x-api-key sau khi được cấp

            loggingManaged.info(new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("action", "[FMSAlarmsService] - [precheck_access_hw_kpi] - Request")
                    .put("requestBody", apiBody)
                    .build())

            String apiResp = httpManaged.post(["url": fmsbbPrecheckUrl], apiHeaders, apiBody)

            def apiResult
            try {
                apiResult = new JsonSlurper().parseText(apiResp)
            } catch (Exception ignore) {
                apiResult = apiResp
            }

            loggingManaged.info(new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("action", "[FMSAlarmsService] - [precheck_access_hw_kpi] - Response")
                    .put("result", apiResult != null ? new JsonBuilder(apiResult).toString() : "")
                    .build())

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: apiResult)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    /**
     * API Check KPI phiếu XLSC thiết bị mạng BRCD
     * Forward request đến FMS BRCD /api/v1/xlsc/precheck/kpi rồi trả response về
     */
    @PostMapping("/precheck_xlsc_kpi")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse precheck_xlsc_kpi(@Valid @RequestBody PRECHECK_XLSC_KPI_REQ body) {
        String requestId = UUID.randomUUID().toString()
        try {
            // Build request payload
            Map<String, Object> requestPayload = [
                    "device_ip"  : body.device_ip,
                    "alarm_code" : body.alarm_code
            ]
            // target không bắt buộc: chỉ truyền nếu có giá trị
            if (body.target != null && !body.target.isEmpty()) {
                requestPayload.put("target", body.target)
            }

            String apiBody = objectMapper.writeValueAsString(requestPayload)

            Map<String, String> apiHeaders = new HashMap<>()
            apiHeaders.put("Content-Type", "application/json")
            apiHeaders.put("x-api-key", fmsbbApiKey)

            loggingManaged.info(new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("action", "[FMSAlarmsService] - [precheck_xlsc_kpi] - Request")
                    .put("requestBody", apiBody)
                    .build())

            String apiResp = httpManaged.post(["url": fmsbbPrecheckKpiUrl], apiHeaders, apiBody)

            def apiResult
            try {
                apiResult = new JsonSlurper().parseText(apiResp)
            } catch (Exception ignore) {
                apiResult = apiResp
            }

            loggingManaged.info(new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("action", "[FMSAlarmsService] - [precheck_xlsc_kpi] - Response")
                    .put("result", apiResult != null ? new JsonBuilder(apiResult).toString() : "")
                    .build())

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: apiResult)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/fmsbb-close-ticket")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse fmsbbCloseTicket(@Valid @RequestBody FMSBB_CLOSE_TICKET_REQ body) {
        String requestId = UUID.randomUUID().toString()
        try {
            // Build request payload
            Map<String, Object> requestPayload = [
                    "alarm_id"      : body.alarm_id,
                    "clear_time"    : body.clear_time,
                    "clear_username": body.clear_username,
                    "clear_note"    : body.clear_note
            ]

            String apiBody = objectMapper.writeValueAsString(requestPayload)

            Map<String, String> apiHeaders = new HashMap<>()
            apiHeaders.put("Content-Type", "application/json")
            apiHeaders.put("x-api-key", fmsbbApiKey)

            loggingManaged.info(new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("action", "[FMSAlarmsService] - [fmsbb-close-ticket] - Request")
                    .put("requestBody", apiBody)
                    .build())

            String apiResp = httpManaged.post(["url": fmsbbCloseTicketUrl], apiHeaders, apiBody)

            def apiResult
            try {
                apiResult = new JsonSlurper().parseText(apiResp)
            } catch (Exception ignore) {
                apiResult = apiResp
            }

            loggingManaged.info(new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("action", "[FMSAlarmsService] - [fmsbb-close-ticket] - Response")
                    .put("result", apiResult != null ? new JsonBuilder(apiResult).toString() : "")
                    .build())

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: apiResult)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    GPathResult post1(String url, String body) {
        Map<String, String> headers = [:]
        headers.put("Host", "10.165.11.179")
        headers.put("Content-Type", "application/soap+xml; charset=utf-8")
        String resp = httpManaged.post(["url": url], headers, body)
        return new XmlSlurper().parseText(resp)
    }

}
