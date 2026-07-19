package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.minio.errors.ErrorResponseException
import io.minio.errors.InsufficientDataException
import io.minio.errors.InternalException
import io.minio.errors.InvalidResponseException
import io.minio.errors.ServerException
import io.minio.errors.XmlParserException
import io.swagger.model.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import oracle.jdbc.OracleTypes
import org.apache.http.util.TextUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.convert.ResultSetToObject
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.ObjectStoragesManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.model.request.CapNhatKetQuaThuTucRequestDTO
import vn.vnptit.sapi.oneapp.model.request.CapNhatPhieuXuLyRequestDTO
import vn.vnptit.sapi.oneapp.model.request.DongPhieuXulyRequestDTO
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.sql.Array
import java.sql.Clob
import java.sql.Connection
import java.sql.ResultSet

@RestController
@RequestMapping(path = "/quy-trinh-ha-tang")
class QuyTrinhHaTangController {
    private final HttpServletRequest request;
    static OutParameter CURSOR_PARAMETER = { OracleTypes.CURSOR }
    static ThreadLocal<ObjectMapper> objectMappers = ThreadLocal.withInitial { new ObjectMapper() }
    private final ObjectMapper objectMapper;
    private static final String EXTERNAL_FILE_API_URL = "http://app-banhang/quanlyfile/get_file"

    @Autowired
    OracleManaged oracleManaged

    @Autowired
    GenericApi genericApi

    @Autowired
    ResultSetToObject resultSetToObject

    @Autowired
    ObjectStoragesManaged objectStoragesManaged

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    DataUtils dataUtils

    @Value("\${server.app-secret}")
    private String externalFileAppSecret

    @Autowired
    QuyTrinhHaTangController(HttpServletRequest request, ObjectMapper objectMapper) {
        this.request = request
        this.objectMapper = objectMapper
    }

    @GetMapping("/ds-thutuc-cua-phieu")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "Lấy danh sách thủ tục của phiếu")
    ResponseEntity<BaseResponse<List>> dsThuTucCuaPhieu(@RequestParam(name = "phieuId") Long phieuId,
                                                        @RequestParam(name = "maProc") String maProc) {
        String requestId = genericApi.generateRequestId();
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String identifyCode = session_.get("identify")
            List list = []
            Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
                sql.call("{ call QLSC.PKG_XULY_SUCO_NET.LAY_DS_THUTUC_CUA_PHIEU(?, ?, ?, ?) }",
                        [
                                identifyCode,
                                phieuId,
                                maProc,
                                CURSOR_PARAMETER
                        ]) { ResultSet rs ->
                    list = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[QuyTrinhHaTangController] - [dsThuTucCuaPhieu] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", list != null && list.size() > 0 ? new JsonBuilder(list).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(list, requestId))
        } catch (Exception ex) {
            loggingManaged.error("[QuyTrinhHaTangController] - [dsThuTucCuaPhieu] - Error:", ex)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ex.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/ds-cacbuoc-theo-mathutuc")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "Lấy danh sách các bước của thủ tục theo mã thủ tục")
    ResponseEntity<BaseResponse<List>> dsCacBuocTheoMaThuTuc(@RequestParam(name = "maThuTuc") String maThuTuc) {
        String requestId = genericApi.generateRequestId();
        try {
            List list = []
            Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
                sql.call("{ ? = call QLSC.FN_CACBUOC_THUTUC_THEO_MATHUTUC(?) }",
                        [
                        CURSOR_PARAMETER,
                                maThuTuc
                        ]) { ResultSet rs ->
                    list = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[QuyTrinhHaTangController] - [dsCacBuocTheoMaThuTuc] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", list != null && list.size() > 0 ? new JsonBuilder(list).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(list, requestId))
        } catch (Exception ex) {
            loggingManaged.error("[QuyTrinhHaTangController] - [dsCacBuocTheoMaThuTuc] - Error:", ex)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ex.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/chitiet-giaophieu-thutuc")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "Lấy thông tin thủ tục bao gồm danh sách ảnh và danh sách các bước")
    ResponseEntity<BaseResponse<Object>> chiTietGiaoPhieuThuTuc(@RequestParam(name = "phieuId") Long phieuId) {
        String requestId = genericApi.generateRequestId();
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String identifyCode = session_.get("identify")
            String token = session_.get(GenericApi.TOKEN)
            Object res = null;
            Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
                sql.call("{ call QLSC.PKG_XULY_SUCO_NET.GET_GIAOPHIEU_THUTUC_DETAIL(?, ?, ?) }",
                        [
                                identifyCode,
                                phieuId,
                                Sql.CLOB
                        ]) { Clob rs ->
                    if (rs != null) {
                        String jsonText = rs.getSubString(1, (int) rs.length())
                        res = new JsonSlurper().parseText(jsonText)
                        enrichAnhUrlsExternal(res, token)
                    }
                }
            }
            loggingManaged.info("[QuyTrinhHaTangController] - [chiTietGiaoPhieuThuTuc] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", res != null && res.size() > 0 ? new JsonBuilder(res).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(res, requestId))
        } catch (Exception ex) {
            loggingManaged.error("[QuyTrinhHaTangController] - [chiTietGiaoPhieuThuTuc] - Error:", ex)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ex.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/capnhat-phieuxuly")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "Cập nhật phiếu xử lý (button Xác nhận)")
    ResponseEntity<BaseResponse<Object>> capNhatPhieuXuLy(@RequestParam(name = "gpttId", required = false) Long gpttId,
                                                            @RequestBody CapNhatPhieuXuLyRequestDTO requestDTO) {
        String requestId = genericApi.generateRequestId();
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String identifyCode = session_.get("identify")
            String token = session_.get(GenericApi.TOKEN)
            Object res = null;
            Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->

                sql.call("{ call QLSC.PKG_XULY_SUCO_NET.UPDATE_GIAOPHIEU_THUTUC(?, ?, ?, ?, ?, ?, ?, ?, ?) }",
                        [
                                identifyCode,
                                requestDTO.phieuId,
                                gpttId,
                                requestDTO.maProc,
                                requestDTO.isHienTruong,
                                requestDTO.kinhDo,
                                requestDTO.viDo,
                                requestDTO.getDsIdAnh() != null && requestDTO.getDsIdAnh().size() > 0 ? requestDTO.getDsIdAnh().join(",") : "",
                                Sql.CLOB
                        ]) { Clob rs ->
                    if (rs != null) {
                        String jsonText = rs.getSubString(1, (int) rs.length())
                        res = new JsonSlurper().parseText(jsonText)
                        enrichAnhUrlsExternal(res, token)
                    }
                }
            }
            loggingManaged.info("[QuyTrinhHaTangController] - [capNhatPhieuXuLy] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", res != null && res.size() > 0 ? new JsonBuilder(res).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(res, requestId))
        } catch (Exception ex) {
            loggingManaged.error("[QuyTrinhHaTangController] - [capNhatPhieuXuLy] - Error:", ex)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ex.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/dong-phieuxuly")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "Đóng phiếu xử lý")
    ResponseEntity<BaseResponse<Object>> dongPhieuXuLy(@RequestParam(name = "gpttId", required = false) Long gpttId,
                                                          @RequestBody DongPhieuXulyRequestDTO requestDTO) {
        String requestId = genericApi.generateRequestId();
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String identifyCode = session_.get("identify")
            String token = session_.get(GenericApi.TOKEN)
            Object res = null;
            Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->

                sql.call("{ call QLSC.PKG_XULY_SUCO_NET.CLOSE_GIAOPHIEU_THUTUC(?, ?, ?, ?, ?) }",
                        [
                                identifyCode,
                                gpttId,
                                requestDTO.ghiChu,
                                requestDTO.getDsIdAnh() != null && requestDTO.getDsIdAnh().size() > 0 ? requestDTO.getDsIdAnh().join(",") : "",
                                Sql.CLOB
                        ]) { Clob rs ->
                    if (rs != null) {
                        String jsonText = rs.getSubString(1, (int) rs.length())
                        res = new JsonSlurper().parseText(jsonText)
                        enrichAnhUrlsExternal(res, token)
                    }
                }
            }
            loggingManaged.info("[QuyTrinhHaTangController] - [dongPhieuXuLy] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", res != null && res.size() > 0 ? new JsonBuilder(res).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(res, requestId))
        } catch (Exception ex) {
            loggingManaged.error("[QuyTrinhHaTangController] - [dongPhieuXuLy] - Error:", ex)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ex.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/capnhat-ketqua-thutuc")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "Cập nhật kết quả thủ tục")
    ResponseEntity<BaseResponse<Object>> capNhatKetQuaThuTuc(@RequestParam(name = "gpttId", required = false) Long gpttId,
                                                       @RequestBody CapNhatKetQuaThuTucRequestDTO requestDTO) {
        String requestId = genericApi.generateRequestId();
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String identifyCode = session_.get("identify")
            String token = session_.get(GenericApi.TOKEN)
            Object res = null;
            Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->

                sql.call("{ call QLSC.PKG_XULY_SUCO_NET.CAPNHAT_KETQUA_THUTUC(?, ?, ?, ?, ?, ?, ?) }",
                        [
                                identifyCode,
                                gpttId,
                                requestDTO.buocId,
                                requestDTO.idLyDo,
                                requestDTO.ghiChu,
                                requestDTO.isThanhCong,
                                Sql.CLOB
                        ]) { Clob rs ->
                    if (rs != null) {
                        String jsonText = rs.getSubString(1, (int) rs.length())
                        res = new JsonSlurper().parseText(jsonText)
                        enrichAnhUrlsExternal(res, token)
                    }
                }
            }
            loggingManaged.info("[QuyTrinhHaTangController] - [capNhatKetQuaThuTuc] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", res != null && res.size() > 0 ? new JsonBuilder(res).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(res, requestId))
        } catch (Exception ex) {
            loggingManaged.error("[QuyTrinhHaTangController] - [capNhatKetQuaThuTuc] - Error:", ex)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ex.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/chitiet-giaophieu-thutuc-theo-gpttid")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "Lấy thông tin thủ tục bao gồm danh sách ảnh và danh sách các bước theo GPTT ID")
    ResponseEntity<BaseResponse<Object>> chiTietGiaoPhieuThuTucTheoGPTTID(@RequestParam(name = "gpttId") Long gpttId) {
        String requestId = genericApi.generateRequestId();
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String identifyCode = session_.get("identify")
            String token = session_.get(GenericApi.TOKEN)
            Object res = null;
            Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
                sql.call("{ call QLSC.PKG_XULY_SUCO_NET.CHITIET_GPTHUTUC_THEO_ID_GPTT(?, ?, ?) }",
                        [
                                identifyCode,
                                gpttId,
                                Sql.CLOB
                        ]) { Clob rs ->
                    if (rs != null) {
                        String jsonText = rs.getSubString(1, (int) rs.length())
                        res = new JsonSlurper().parseText(jsonText)
                        enrichAnhUrlsExternal(res, token)
                    }
                }
            }
            loggingManaged.info("[QuyTrinhHaTangController] - [chiTietGiaoPhieuThuTucTheoGPTTID] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", res != null && res.size() > 0 ? new JsonBuilder(res).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(res, requestId))
        } catch (Exception ex) {
            loggingManaged.error("[QuyTrinhHaTangController] - [chiTietGiaoPhieuThuTucTheoGPTTID] - Error:", ex)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ex.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/tao-giaophieu-thutuc")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "Tạo giao phiếu thủ tục")
    ResponseEntity<BaseResponse<Object>> taoGiaoPhieuThuTuc(@RequestParam(name = "phieuId") Long phieuId,
                                                            @RequestParam(name = "maProc") String maProc) {
        String requestId = genericApi.generateRequestId();
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String identifyCode = session_.get("identify")
            String token = session_.get(GenericApi.TOKEN)
            Object res = null;
            Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->

                sql.call("{ call QLSC.PKG_XULY_SUCO_NET.TAO_GPTHUTUC_CHO_PHIEU(?, ?, ?, ?) }",
                        [
                                identifyCode,
                                phieuId,
                                maProc,
                                Sql.CLOB
                        ]) { Clob rs ->
                    if (rs != null) {
                        String jsonText = rs.getSubString(1, (int) rs.length())
                        res = new JsonSlurper().parseText(jsonText)
                        enrichAnhUrlsExternal(res, token)
                    }
                }
            }
            loggingManaged.info("[QuyTrinhHaTangController] - [taoGiaoPhieuThuTuc] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", res != null && res.size() > 0 ? new JsonBuilder(res).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(res, requestId))
        } catch (Exception ex) {
            loggingManaged.error("[QuyTrinhHaTangController] - [taoGiaoPhieuThuTuc] - Error:", ex)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ex.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR));
        }
    }

    private String getFileUrlFromExternalApi(String token, String appSecret, String path) {
        if (TextUtils.isEmpty(token) || TextUtils.isEmpty(appSecret) || TextUtils.isEmpty(path)) {
            return null
        }
        try {
            Map<String, Object> params = [
                    url: EXTERNAL_FILE_API_URL,
                    service: "get_file"
            ]
            Map<String, String> headers = [
                    "App-secret": appSecret,
                    "Content-Type": "application/json",
                    "Authorization": token
            ]
            String body = new JsonBuilder([p_path: path]).toString()
            String response = HttpManaged.post(params, headers, body)
            if (response == null || response.trim().isEmpty()) {
                return null
            }
            def json = new JsonSlurper().parseText(response)
            return json?.data?.url_taptin
        } catch (Exception ex) {
            loggingManaged.error("[QuyTrinhHaTangController] - [getFileUrlFromExternalApi] - Error:", ex)
            return null
        }
    }

    private void enrichAnhUrlsExternal(Object res, String token) {
        if (!(res instanceof Map)) {
            return
        }
        def anhList = res.get("anh")
        if (!(anhList instanceof List)) {
            return
        }
        String appSecret = request.getHeader("App-secret")
        if (TextUtils.isEmpty(appSecret)) {
            appSecret = externalFileAppSecret
        }
        anhList.each { item ->
            if (item instanceof Map) {
                def path = item.get("PATH")
                if (path == null) {
                    path = item.get("path")
                }
                if (path != null && path.toString().trim()) {
                    item.put("URL", getFileUrlFromExternalApi(token, appSecret, path.toString()))
                } else {
                    item.put("URL", null)
                }
                item.remove("PATH")
                item.remove("path")
            }
        }
    }

}
