package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.model.response.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.convert.ResultSetToObject
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.ObjectStoragesManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.model.request.CAPNHAT_SAU_KYSO_V2
import vn.vnptit.sapi.oneapp.model.request.FN_CAPNHAT_MOTA_PHIEU
import vn.vnptit.sapi.oneapp.model.request.FN_HUYPHIEU
import vn.vnptit.sapi.oneapp.model.request.FN_TRAPHIEU_V2
import vn.vnptit.sapi.oneapp.model.request.GUI_OTP_V2
import vn.vnptit.sapi.oneapp.model.request.KT_OTP
import vn.vnptit.sapi.oneapp.model.request.KT_THAOTAC_PHIEU_VT
import vn.vnptit.sapi.oneapp.model.request.LAY_DANHMUC_QLDN
import vn.vnptit.sapi.oneapp.model.request.LAY_DS_GIAOPHIEU_NV
import vn.vnptit.sapi.oneapp.model.request.LAY_DS_NHANVIEN_GIAOPHIEU_THAOTAC
import vn.vnptit.sapi.oneapp.model.request.LAY_MA_PHIEU
import vn.vnptit.sapi.oneapp.model.request.LAY_THONGTIN_NGUOI_KYSO
import vn.vnptit.sapi.oneapp.model.request.LAY_THONGTIN_PHIEU_KYSO
import vn.vnptit.sapi.oneapp.model.request.LAY_THONGTIN_THUKHO_KYSO
import vn.vnptit.sapi.oneapp.model.request.SP_LAY_DANHMUC_QLDN
import vn.vnptit.sapi.oneapp.model.request.SP_LAY_DS_DONVI_GIAOPHIEU
import vn.vnptit.sapi.oneapp.model.request.SP_LAY_HUONGGIAO_TIEPTHEO
import vn.vnptit.sapi.oneapp.model.request.SP_LAY_THONGTIN_VITRI_KYSO
import vn.vnptit.sapi.oneapp.service.VisaService
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.sql.ResultSet

@RestController
@RequestMapping("smartca")
class SmartCAController {
    private final HttpServletRequest request
    static OutParameter CURSOR_PARAMETER = { OracleTypes.CURSOR }
    private final ObjectMapper objectMapper

    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    GenericApi genericApi
    @Autowired
    OracleManaged oracleManaged

    @Autowired
    ObjectStoragesManaged objectStoragesManaged

    @Autowired
    DataUtils dataUtils

    @Autowired
    ResultSetToObject resultSetToObject

    @Autowired
    VisaService visaService

    ObjectMapper json = new ObjectMapper();

    @Autowired
    SmartCAController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }

    // 1
    @PostMapping("/sp_lay_danhmuc_qldn")
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu gửi yêu cầu (request)
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu trả về (response).
    @Operation(summary = "sp_lay_danhmuc_qldn", tags = ["SmartCA"]) // swagger-ui
    ResponseEntity<BaseResponse<?>> lay_danhmuc_qldn(@RequestBody SP_LAY_DANHMUC_QLDN body) {
        List data
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[SmartCAController] - [SP_LAY_DANHMUC_QLDN] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("body", body)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String nhanVienId = session_.get(GenericApi.ID_NHANVIEN_DHSX)
            String maNhanVien = session_.get(GenericApi.MA_NV)
            String sqlString = "{ call QLVT.PKG_QUANLY_DENGHI.SP_LAY_DANHMUC_QLDN(?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, nhanVienId, body.kieu, body.param1, body.param2, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[SmartCAController] - [SP_LAY_DANHMUC_QLDN] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[SmartCAController] - [SP_LAY_DANHMUC_QLDN] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // 2.1
    @PostMapping("/lay_ds_giaophieu_nv")
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu gửi yêu cầu (request)
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu trả về (response).
    @Operation(summary = "lay_ds_giaophieu_nv", tags = ["SmartCA"]) // swagger-ui
    ResponseEntity<BaseResponse<?>> lay_ds_giaophieu_nv(@RequestBody LAY_DS_GIAOPHIEU_NV body) {
        List data
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[SmartCAController] - [lay_ds_giaophieu_nv] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("body", body)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String nhanVienId = session_.get(GenericApi.ID_NHANVIEN_DHSX)
            String sqlString = "{ call  QLVT.PKG_QUANLY_DENGHI.lay_ds_giaophieu_nv(?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, nhanVienId, body.vloaiphieu_id, body.vnhiemvu_id, body.vttph_id,body.vtungay,body.vdenngay, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[SmartCAController] - [lay_ds_giaophieu_nv] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[SmartCAController] - [lay_ds_giaophieu_nv] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // 2.5
    @PostMapping("/sp_lay_huonggiao_tieptheo")
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu gửi yêu cầu (request)
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu trả về (response).
    @Operation(summary = "sp_lay_huonggiao_tieptheo", tags = ["SmartCA"]) // swagger-ui
    ResponseEntity<BaseResponse<?>> sp_lay_huonggiao_tieptheo(@RequestBody SP_LAY_HUONGGIAO_TIEPTHEO body) {
        List data
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[SmartCAController] - [SP_LAY_HUONGGIAO_TIEPTHEO] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("body", body)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            //String maNhanVien = session_.get(GenericApi.MA_NV)
            String sqlString = "{ call  QLVT.PKG_QUANLY_DENGHI.SP_LAY_HUONGGIAO_TIEPTHEO(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, body.p_huonggiao_id, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[SmartCAController] - [SP_LAY_HUONGGIAO_TIEPTHEO] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[SmartCAController] - [SP_LAY_HUONGGIAO_TIEPTHEO] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // 2.5.2
    @PostMapping("/sp_lay_ds_donvi_giaophieu")
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu gửi yêu cầu (request)
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu trả về (response).
    @Operation(summary = "sp_lay_ds_donvi_giaophieu", tags = ["SmartCA"]) // swagger-ui
    ResponseEntity<BaseResponse<?>> sp_lay_ds_donvi_giaophieu(@RequestBody SP_LAY_DS_DONVI_GIAOPHIEU body) {
        List data1
        List data2
        List data3
        Integer huonggiao_tieptheo_id
        Integer donvi_id

        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[SmartCAController] - [sp_lay_ds_donvi_giaophieu] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("body", body)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String nhanVienId = session_.get(GenericApi.ID_NHANVIEN_DHSX)
            String donViId = session_.get(GenericApi.ID_DONVI)

            // bước 1 : lấy hướng giao id
            String sqlString = "{ call  QLVT.PKG_QUANLY_DENGHI.SP_LAY_HUONGGIAO_TIEPTHEO(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, body.p_huonggiao_id, CURSOR_PARAMETER]) { ResultSet rs ->
                    data1 = resultSetToObject.getDynamicData(rs)
                }

                if (data1 != null && !data1.isEmpty()) {
                    if (data1.huonggiao_id != null && !data1.huonggiao_id.isEmpty()) {

                        huonggiao_tieptheo_id = data1[0].huonggiao_id;

                        String sqlString2 = "{ call  QLVT.PKG_QUANLY_DENGHI.sp_lay_donvi_nhanvien_giaophieu(?,?,?,?,?) }"
                        sql.call(sqlString2, [phanVungId, body.phieu_id, nhanVienId, huonggiao_tieptheo_id, CURSOR_PARAMETER]) { ResultSet rs ->
                            data2 = resultSetToObject.getDynamicData(rs)
                        }

                        if (data2 != null && !data2.isEmpty()) {
                            donvi_id = data2[0].donvi_id;

                            String sqlString3 = "{ call  QLVT.PKG_QUANLY_DENGHI.sp_lay_ds_donvi_giaophieu(?,?,?,?,?) }"
                            sql.call(sqlString3, [phanVungId, huonggiao_tieptheo_id, nhanVienId, donvi_id, CURSOR_PARAMETER]) { ResultSet rs ->
                                data3 = resultSetToObject.getDynamicData(rs)
                            }
                        } else {
                            String msgError = "Không xác định được đơn vị giao phiếu tiếp theo!"
                            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, msgError, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                        }
                    }
                }
            }

            loggingManaged.info("[SmartCAController] - [sp_lay_ds_donvi_giaophieu] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data3 ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data3))

        } catch (Exception e) {
            loggingManaged.error("[SmartCAController] - [sp_lay_ds_donvi_giaophieu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // 2.6 Danh sách  nhân viên xử lý cho combobox nhân viên xử lý
    @PostMapping("/lay_ds_nhanvien_giaophieu_thaotac")
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu gửi yêu cầu (request)
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu trả về (response).
    @Operation(summary = "lay_ds_nhanvien_giaophieu_thaotac", tags = ["SmartCA"]) // swagger-ui
    ResponseEntity<BaseResponse<?>> lay_ds_nhanvien_giaophieu_thaotac(@RequestBody LAY_DS_NHANVIEN_GIAOPHIEU_THAOTAC body) {
        List data
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[SmartCAController] - [lay_ds_nhanvien_giaophieu_thaotac] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("body", body)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String maNhanVien = session_.get(GenericApi.MA_NV)
            String donViId =    session_.get(GenericApi.ID_DONVI)
            String sqlString = "{ call  QLVT.PKG_QUANLY_DENGHI.lay_ds_nhanvien_giaophieu_thaotac(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, body.p_donvi_id, body.p_huonggiao_id, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[SmartCAController] - [lay_ds_nhanvien_giaophieu_thaotac] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[SmartCAController] - [lay_ds_nhanvien_giaophieu_thaotac] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // 2.7
    @PostMapping("/gui_otp_v2")
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu gửi yêu cầu (request)
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu trả về (response).
    @Operation(summary = "gui_otp_v2", tags = ["SmartCA"]) // swagger-ui
    ResponseEntity<BaseResponse<?>> gui_otp_v2(@RequestBody GUI_OTP_V2 body) {
        List data
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[SmartCAController] - [gui_otp_v2] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("body", body)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String nhanVienId = session_.get(GenericApi.ID_NHANVIEN_DHSX)
            String sqlString = "{ call  QLVT.PKG_QUANLY_DENGHI.GUI_OTP_V2(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [body.vphieu_ids, nhanVienId, body.vso_dt_sms,body.vnoidung_sms, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[SmartCAController] - [gui_otp_v2] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[SmartCAController] - [gui_otp_v2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // 2.8.1
    @PostMapping("/kt_otp")
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu gửi yêu cầu (request)
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu trả về (response).
    @Operation(summary = "kt_otp", tags = ["SmartCA"]) // swagger-ui
    ResponseEntity<BaseResponse<?>> kt_otp(@RequestBody KT_OTP body) {
        List data
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[SmartCAController] - [gui_otp_v2] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("body", body)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String maNhanVien = session_.get(GenericApi.MA_NV)
            //String donViId =    session_.get(GenericApi.ID_DONVI)
            String sqlString = "{ call  QLVT.PKG_QUANLY_DENGHI.kt_otp(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, body.vphieu_ids, body.votp, maNhanVien, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[SmartCAController] - [kt_otp] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[SmartCAController] - [kt_otp] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // 2.8.2
    @PostMapping("/lay_thongtin_phieu_kyso")
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu gửi yêu cầu (request)
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu trả về (response).
    @Operation(summary = "lay_thongtin_phieu_kyso", tags = ["SmartCA"]) // swagger-ui
    ResponseEntity<BaseResponse<?>> lay_thongtin_phieu_kyso(@RequestBody LAY_THONGTIN_PHIEU_KYSO body) {
        List data
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[SmartCAController] - [lay_thongtin_phieu_kyso] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("body", body)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String maNhanVien = session_.get(GenericApi.MA_NV)
            //String donViId =    session_.get(GenericApi.ID_DONVI)
            String sqlString = "{ call  QLVT.PKG_QUANLY_DENGHI.lay_thongtin_phieu_kyso(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, body.vphieu_id, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[SmartCAController] - [lay_thongtin_phieu_kyso] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[SmartCAController] - [lay_thongtin_phieu_kyso] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }


    // 2.8.2 Hàm lấy thông tin người ký
    @PostMapping("/lay_thongtin_nguoi_kyso")
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu gửi yêu cầu (request)
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu trả về (response).
    @Operation(summary = "lay_thongtin_nguoi_kyso", tags = ["SmartCA"]) // swagger-ui
    ResponseEntity<BaseResponse<?>> lay_thongtin_nguoi_kyso(@RequestBody LAY_THONGTIN_NGUOI_KYSO body) {
        List data
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[SmartCAController] - [lay_thongtin_nguoi_kyso] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("body", body)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String maNhanVien = session_.get(GenericApi.MA_NV)
            //String donViId =    session_.get(GenericApi.ID_DONVI)
            String sqlString = "{ call  QLVT.PKG_QUANLY_DENGHI.lay_thongtin_nguoi_kyso(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, maNhanVien, body.vloaiphieu_id, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[SmartCAController] - [lay_thongtin_nguoi_kyso] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[SmartCAController] - [lay_thongtin_nguoi_kyso] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // 2.8.3
    @PostMapping("/sp_lay_thongtin_vitri_kyso")
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu gửi yêu cầu (request)
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu trả về (response).
    @Operation(summary = "sp_lay_thongtin_vitri_kyso", tags = ["SmartCA"]) // swagger-ui
    ResponseEntity<BaseResponse<?>> lay_thongtin_vitri_kyso(@RequestBody SP_LAY_THONGTIN_VITRI_KYSO body) {
        List data
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[SmartCAController] - [sp_lay_thongtin_vitri_kyso] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("body", body)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String maNhanVien = session_.get(GenericApi.MA_NV)
            //String donViId =    session_.get(GenericApi.ID_DONVI)
            String sqlString = "{ call  QLVT.PKG_QUANLY_DENGHI.sp_lay_thongtin_vitri_kyso(?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, body.p_phieu_cha_id, body.p_loaiphieu_id, body.p_huonggiao_id,body.p_baocao_id, body.p_kieu, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[SmartCAController] - [sp_lay_thongtin_vitri_kyso] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[SmartCAController] - [sp_lay_thongtin_vitri_kyso] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // 2.8.4
    @PostMapping("/kt_thaotac_phieu_vt")
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu gửi yêu cầu (request)
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu trả về (response).
    @Operation(summary = "kt_thaotac_phieu_vt", tags = ["SmartCA"]) // swagger-ui
    ResponseEntity<BaseResponse<?>> kt_thaotac_phieu_vt(@RequestBody KT_THAOTAC_PHIEU_VT body) {
        List data
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[SmartCAController] - [kt_thaotac_phieu_vt] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("body", body)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String maNhanVien = session_.get(GenericApi.MA_NV)
            //String donViId =    session_.get(GenericApi.ID_DONVI)
            String sqlString = "{ call  QLVT.PKG_QUANLY_DENGHI.KT_THAOTAC_PHIEU_VT(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, body.vphieu_id, body.vthaotac, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[SmartCAController] - [kt_thaotac_phieu_vt] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[SmartCAController] - [kt_thaotac_phieu_vt] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // 2.8.5
    @PostMapping("/lay_ma_phieu")
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu gửi yêu cầu (request)
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu trả về (response).
    @Operation(summary = "lay_ma_phieu", tags = ["SmartCA"]) // swagger-ui
    ResponseEntity<BaseResponse<?>> lay_ma_phieu(@RequestBody LAY_MA_PHIEU body) {
        List data
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[SmartCAController] - [lay_ma_phieu] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("body", body)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)

            String sqlString = "{ call  QLVT.PKG_QUANLY_DENGHI.LAY_MA_PHIEU(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, body.vphieu_id, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[SmartCAController] - [lay_ma_phieu] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[SmartCAController] - [lay_ma_phieu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // 2.8.6
    @PostMapping("/lay_thongtin_thukho_kyso")
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu gửi yêu cầu (request)
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu trả về (response).
    @Operation(summary = "lay_thongtin_thukho_kyso", tags = ["SmartCA"]) // swagger-ui
    ResponseEntity<BaseResponse<?>> lay_thongtin_thukho_kyso(@RequestBody LAY_THONGTIN_THUKHO_KYSO body) {
        List data
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[SmartCAController] - [lay_thongtin_thukho_kyso] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("body", body)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)

            String sqlString = "{ call  qlvt.pkg_quanly_denghi.lay_thongtin_thukho_kyso(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, body.vphieu_id, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[SmartCAController] - [lay_thongtin_thukho_kyso] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[SmartCAController] - [lay_thongtin_thukho_kyso] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // 2.8.7
    @PostMapping("/fn_capnhat_mota_phieu")
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu gửi yêu cầu (request)
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu trả về (response).
    @Operation(summary = "fn_capnhat_mota_phieu", tags = ["SmartCA"]) // swagger-ui
    ResponseEntity<BaseResponse<?>> fn_capnhat_mota_phieu(@RequestBody FN_CAPNHAT_MOTA_PHIEU body) {
        List data
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[SmartCAController] - [fn_capnhat_mota_phieu] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("body", body)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String maNhanVien = session_.get(GenericApi.MA_NV)
            //String donViId =    session_.get(GenericApi.ID_DONVI)
            String sqlString = "{ call  QLVT.PKG_QUANLY_DENGHI.FN_CAPNHAT_MOTA_PHIEU(?,?,?,?,? }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, body.vphieu_id, maNhanVien, body.vmota, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[SmartCAController] - [fn_capnhat_mota_phieu] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[SmartCAController] - [fn_capnhat_mota_phieu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // 2.8.8
    @PostMapping("/capnhat_sau_kyso_v2")
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu gửi yêu cầu (request)
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu trả về (response).
    @Operation(summary = "capnhat_sau_kyso_v2", tags = ["SmartCA"]) // swagger-ui
    ResponseEntity<BaseResponse<?>> capnhat_sau_kyso_v2(@RequestBody CAPNHAT_SAU_KYSO_V2 body) {
        List data
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[SmartCAController] - [capnhat_sau_kyso_v2] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("body", body)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String maNhanVien = session_.get(GenericApi.MA_NV)
            String donViId =    session_.get(GenericApi.ID_DONVI)
            String sqlString = "{ call  QLVT.PKG_QUANLY_DENGHI.CAPNHAT_SAU_KYSO_v2(?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, body.vphieu_id, maNhanVien, donViId, body.vdonvi_nhan_id, body.vnhanvien_nhan_id,body.vnd_giao,body.vmota,body.vnguoi_cn,body.vmay_cn,body.vip_cn, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[SmartCAController] - [capnhat_sau_kyso_v2] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[SmartCAController] - [capnhat_sau_kyso_v2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // 2.10 Hàm lấy lý do trả
    @PostMapping("/lay_danhmuc_qldn")
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu gửi yêu cầu (request)
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu trả về (response).
    @Operation(summary = "lay_danhmuc_qldn", tags = ["SmartCA"]) // swagger-ui
    ResponseEntity<BaseResponse<?>> lay_danhmuc_qldn(@RequestBody LAY_DANHMUC_QLDN body) {
        List data
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[SmartCAController] - [lay_danhmuc_qldn] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("body", body)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String maNhanVien = session_.get(GenericApi.MA_NV)
            String donViId =    session_.get(GenericApi.ID_DONVI)
            String sqlString = "{ call  QLVT.PKG_QUANLY_DENGHI.LAY_DANHMUC_QLDN(?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, maNhanVien, body.vkieu, body.vparam1, body.vparam2, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[SmartCAController] - [lay_danhmuc_qldn] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[SmartCAController] - [lay_danhmuc_qldn] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // 17 Trả phiếu
    @PostMapping("/fn_traphieu_v2")
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu gửi yêu cầu (request)
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu trả về (response).
    @Operation(summary = "fn_traphieu_v2", tags = ["SmartCA"]) // swagger-ui
    ResponseEntity<BaseResponse<?>> traphieu_v2(@RequestBody FN_TRAPHIEU_V2 body) {
        List data
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[SmartCAController] - [fn_traphieu_v2] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("body", body)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String maNhanVien = session_.get(GenericApi.MA_NV)
            String donViId =    session_.get(GenericApi.ID_DONVI)
            String username = session_.get(GenericApi.USERNAME)
            String p_may_cn = 'VNPT EMPLOYEE'
            String p_ip_cn = 'VNPT EMPLOYEE'

            String sqlString = "{ call  QLVT.PKG_QUANLY_DENGHI.FN_TRAPHIEU_V2(?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, body.p_phieu_id, body.p_lydotra_id, body.p_nd_tra, body.p_ghichu_tra, maNhanVien, donViId, username, p_may_cn, p_ip_cn, body.p_otp, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[SmartCAController] - [fn_traphieu_v2] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[SmartCAController] - [fn_traphieu_v2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // 18 Hủy giao
    @PostMapping("/fn_huyphieu")
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu gửi yêu cầu (request)
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN]) // xác định kiểu dữ liệu trả về (response).
    @Operation(summary = "fn_huyphieu", tags = ["SmartCA"]) // swagger-ui
    ResponseEntity<BaseResponse<?>> fn_huyphieu(@RequestBody FN_HUYPHIEU body) {
        List data
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[SmartCAController] - [fn_huyphieu] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("body", body)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String maNhanVien = session_.get(GenericApi.MA_NV)
            String donViId =    session_.get(GenericApi.ID_DONVI)
            String username = session_.get(GenericApi.USERNAME)
            String vmay_cn = 'VNPT EMPLOYEE'
            String vip_cn = 'VNPT EMPLOYEE'
            String sqlString = "{ call  QLVT.PKG_QUANLY_DENGHI.FN_HUYPHIEU(?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, body.p_phieu_id, maNhanVien, donViId, username, vmay_cn, vip_cn, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[SmartCAController] - [fn_huyphieu] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[SmartCAController] - [fn_huyphieu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
}
