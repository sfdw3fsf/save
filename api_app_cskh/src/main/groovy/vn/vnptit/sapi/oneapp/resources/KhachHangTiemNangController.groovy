package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.api.KhachHangTiemNangApi
import io.swagger.model.dto.CTKhachHangTiemNang
import io.swagger.model.dto.DanhMuc
import io.swagger.model.dto.PhieuKhachHangTiemNang
import io.swagger.model.dto.TTKhachHang
import io.swagger.model.dto.TTKhachHangTiemNang
import io.swagger.model.dto.TapKH
import io.swagger.model.request.CNKhachHangTiemNangRequest
import io.swagger.model.request.CapNhatKQXLRequest
import io.swagger.model.request.HenKhachHangRequest
import io.swagger.model.request.KhachHangTiemNangRequest
import io.swagger.model.response.BaseResponse
import io.swagger.model.response.KhachHangTiemNangResponse
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.MessageUtils
import vn.vnptit.sapi.oneapp.util.Utils

import javax.servlet.http.HttpServletRequest
import java.sql.ResultSet
import java.text.SimpleDateFormat

import static vn.vnptit.sapi.oneapp.resources.GenericApi.*

@RestController
class KhachHangTiemNangController implements KhachHangTiemNangApi {

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
    KhachHangTiemNangController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsLoaiGiayTo() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [dsLoaiGiayTo] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.WEBAPI_EX.POST_LIST_V2(?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), "MOBI_LAYDS_LOAI_GIAYTO", "", "", "", "", "", CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: Utils.getLongDataByKey(rs, "ID"), NAME: Utils.getStringDataByKey(rs, "NAME")))
                    }
                }
            }
            loggingManaged.info("[KhachHangTiemNangController] - [dsLoaiGiayTo] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            if (ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[KhachHangTiemNangController] - [dsLoaiGiayTo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsDichVuKHTiemNang() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [dsDichVuKHTiemNang] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.sp_lay_thongtin_dvvt_loaitb(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), 0, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: Utils.getLongDataByKey(rs, "dichvuvt_id"), NAME: Utils.getStringDataByKey(rs, "ten_dvvt")))
                    }
                }
            }
            loggingManaged.info("[KhachHangTiemNangController] - [dsDichVuKHTiemNang] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            if (ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[KhachHangTiemNangController] - [dsDichVuKHTiemNang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsLoaiHinhThueBaoKHTiemNang(Long dichVuVTId) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [dsLoaiHinhThueBaoKHTiemNang] - Request: ", new ImmutableMap.Builder<String, Object>().
                    put("request_id", requestId).put("dichVuVTId", dichVuVTId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.sp_lay_thongtin_dvvt_loaitb(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), dichVuVTId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: Utils.getLongDataByKey(rs, "loaitb_id"), NAME: Utils.getStringDataByKey(rs, "loaihinh_tb")))
                    }
                }
            }
            loggingManaged.info("[KhachHangTiemNangController] - [dsLoaiHinhThueBaoKHTiemNang] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            if (ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[KhachHangTiemNangController] - [dsLoaiHinhThueBaoKHTiemNang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsLoaiYC() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [dsLoaiYC] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.WEBAPI_EX.POST_LIST_V2(?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), "LAY_DS_LOAIYC_KHTN", "", "", "", "", "", CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: Utils.getLongDataByKey(rs, "kieuld_id"), NAME: Utils.getStringDataByKey(rs, "ten_kieuld")))
                    }
                }
            }
            loggingManaged.info("[KhachHangTiemNangController] - [dsLoaiYC] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            if (ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[KhachHangTiemNangController] - [dsLoaiYC] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsDichVu() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [dsDichVu] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request);
            String dataSrc = session_.get(CSS_SCH);
            String sqlString = "{ call CSS.DANHMUC_LOC_KHACHHANG_TIEMNANG(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [0, 0, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: Utils.getLongDataByKey(rs, "dichvuvt_id"), NAME: Utils.getStringDataByKey(rs, "ten_dvvt")))
                    }
                }
            }
            loggingManaged.info("[KhachHangTiemNangController] - [dsDichVu] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            if (ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[KhachHangTiemNangController] - [dsDichVu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsKetQuaXuLy() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [dsKetQuaXuLy] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.WEBAPI_EX.POST_LIST_V2(?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), "LAY_KQXL_KHTN", "", "", "", "", "", CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: Utils.getLongDataByKey(rs, "ID"), NAME: Utils.getStringDataByKey(rs, "NAME")))
                    }
                }
            }
            loggingManaged.info("[KhachHangTiemNangController] - [dsKetQuaXuLy] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            if (ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[KhachHangTiemNangController] - [dsKetQuaXuLy] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsKetQuaXuLyV2() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [dsKetQuaXuLyV2] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.danhmuc_khachhang_tn(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), 6, 0, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: Utils.getLongDataByKey(rs, "ID"), NAME: Utils.getStringDataByKey(rs, "NAME")))
                    }
                }
            }
            loggingManaged.info("[KhachHangTiemNangController] - [dsKetQuaXuLyV2] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            if (ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[KhachHangTiemNangController] - [dsKetQuaXuLyV2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsNhanVien() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [dsNhanVien] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call CSS.danhmuc_khachhang_tn(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), 5, Long.parseLong(nhanVienId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: Utils.getLongDataByKey(rs, "ID"), NAME: Utils.getStringDataByKey(rs, "NAME")))
                    }
                }
            }
            loggingManaged.info("[KhachHangTiemNangController] - [dsNhanVien] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            if (ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[KhachHangTiemNangController] - [dsNhanVien] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsHinhThucTiepCan() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [dsHinhThucTiepCan] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.danhmuc_khachhang_tn(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), 1, 0, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: Utils.getLongDataByKey(rs, "ID"), NAME: Utils.getStringDataByKey(rs, "NAME")))
                    }
                }
            }
            loggingManaged.info("[KhachHangTiemNangController] - [dsHinhThucTiepCan] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            if (ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[KhachHangTiemNangController] - [dsHinhThucTiepCan] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsNhaMang() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [dsNhaMang] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.danhmuc_khachhang_tn(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), 3, 0, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: Utils.getLongDataByKey(rs, "chuquan_id"), NAME: Utils.getStringDataByKey(rs, "tenchuquan")))
                    }
                }
            }
            loggingManaged.info("[KhachHangTiemNangController] - [dsNhaMang] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            if (ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[KhachHangTiemNangController] - [dsNhaMang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsNhomLyDo() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [dsNhomLyDo] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.WEBAPI_EX.POST_LIST_V2(?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), "LAY_NHOM_LYDOTON_KHTN", "", "", "", "", "", CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: Utils.getLongDataByKey(rs, "NHOMTON_ID"), NAME: Utils.getStringDataByKey(rs, "NHOM_TON")))
                    }
                }
            }
            loggingManaged.info("[KhachHangTiemNangController] - [dsNhomLyDo] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            if (ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[KhachHangTiemNangController] - [dsNhomLyDo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsLydo(Long nhomLyDoId) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [dsLydo] - Request: ", new ImmutableMap.Builder<String, Object>().
                    put("request_id", requestId).put("nhomLyDoId", nhomLyDoId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.WEBAPI_EX.POST_LIST_V2(?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), "LAY_LYDOTONTC_THEONHOM", String.valueOf(nhomLyDoId), "", "", "", "", CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: Utils.getLongDataByKey(rs, "lydotontc_id"), NAME: Utils.getStringDataByKey(rs, "lydoton_tc")))
                    }
                }
            }
            loggingManaged.info("[KhachHangTiemNangController] - [dsLydo] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            if (ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[KhachHangTiemNangController] - [dsLydo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsLoaiHinhThueBao(Long dichVuVTId) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [dsLoaiHinhThueBao] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request);
            String dataSrc = session_.get(CSS_SCH);
            String sqlString = "{ call CSS.DANHMUC_LOC_KHACHHANG_TIEMNANG(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [1, dichVuVTId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: Utils.getLongDataByKey(rs, "loaitb_id"), NAME: Utils.getStringDataByKey(rs, "loaihinh_tb")))
                    }
                }
            }
            loggingManaged.info("[KhachHangTiemNangController] - [dsLoaiHinhThueBao] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[KhachHangTiemNangController] - [dsLoaiHinhThueBao] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTKhachHang>> layTTKhachHang(Long khachHangId) {
        TTKhachHang ret
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [layTTKhachHang] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.lay_thongtin_kh_theo_kh_id(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), khachHangId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null && rs.next()) {
                        ret = new TTKhachHang(khachHangId: Utils.getLongDataByKey(rs, "khachhang_id"), soDienThoai: Utils.getStringDataByKey(rs, "so_dt"),
                                maKH: Utils.getStringDataByKey(rs, "ma_kh"), tenKH: Utils.getStringDataByKey(rs, "ten_kh"),
                                soGT: Utils.getStringDataByKey(rs, "so_gt"), noiCap: Utils.getStringDataByKey(rs, "noicap"),
                                ngayCap: Utils.getStringDataByKey(rs, "ngaycap"), loaiGTId: Utils.getLongDataByKey(rs, "loaigt_id"),
                                ghiChu: Utils.getStringDataByKey(rs, "ghichu"), diaChi: Utils.getStringDataByKey(rs, "diachi"),
                                tinhId: Utils.getLongDataByKey(rs, "tinh_id"), quanId: Utils.getLongDataByKey(rs, "quan_id"),
                                phuongId: Utils.getLongDataByKey(rs, "phuong_id"), phoId: Utils.getLongDataByKey(rs, "pho_id"),
                                apId: Utils.getLongDataByKey(rs, "ap_id"), khuId: Utils.getLongDataByKey(rs, "khu_id"),
                                soNha: Utils.getStringDataByKey(rs, "sonha"), kinhDo: Utils.getStringDataByKey(rs, "kinhdo"),
                                viDo: Utils.getStringDataByKey(rs, "vido"), tinh: Utils.getStringDataByKey(rs, "ten_tinh"),
                                quan: Utils.getStringDataByKey(rs, "ten_quan"), phuong: Utils.getStringDataByKey(rs, "ten_phuong"),
                                pho: Utils.getStringDataByKey(rs, "ten_pho"), ap: Utils.getStringDataByKey(rs, "ten_ap"),
                                khu: Utils.getStringDataByKey(rs, "ten_khu"), dacDiemId: Utils.getLongDataByKey(rs, "dacdiem_id"),
                                tenDacDiem: Utils.getStringDataByKey(rs, "ten_dacdiem"), loaiGT: Utils.getStringDataByKey(rs, "loai_gt"))
                    }
                }
            }
            loggingManaged.info("[KhachHangTiemNangController] - [layTTKhachHang] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", ret != null ? ret.toString() : "").build())
            if (ret != null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[KhachHangTiemNangController] - [layTTKhachHang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<KhachHangTiemNangResponse>> dsKHTiemNang(KhachHangTiemNangRequest requestBody) {
        List<TTKhachHangTiemNang> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [dsKHTiemNang] - Request: ", new ImmutableMap.Builder<String, Object>().
                    put("request_id", requestId).put("body", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request);
            String dataSrc = session_.get(CSS_SCH);
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            if (requestBody.getNhanVienId() != null && requestBody.getNhanVienId() != -1) {
                nhanVienId = requestBody.getNhanVienId()
            }
            List<String> dsThangHetHan = new ArrayList<>()
            boolean check = false
            String sqlString = "{ call css.sp_theodoi_kh_tiemnang(?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), Long.parseLong(nhanVienId), requestBody.getTuNgay(), requestBody.getDenNgay(),
                                     requestBody.getLoaiTBId(), requestBody.getThangHetHan(), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        check = true
                        String thangHH = Utils.getStringDataByKey(rs, "thang_kt")
                        ret.add(new TTKhachHangTiemNang(
                                loaiTBId: Utils.getLongDataByKey(rs, "loaitb_id"),
                                dichVuVTId: Utils.getLongDataByKey(rs, "dichvuvt_id"),
                                hdkhId: Utils.getLongDataByKey(rs, "hdkh_id"),
                                thangHetHan: Utils.getStringDataByKey(rs, "thang_kt"),
                                tenKH: Utils.getStringDataByKey(rs, "ten_kh"),
                                loaiHinhTB: Utils.getStringDataByKey(rs, "loaihinh_tb"),
                                soDT: Utils.getStringDataByKey(rs, "so_dt"),
                                diaChiKH: Utils.getStringDataByKey(rs, "diachi_kh"),
                                kqxl: Utils.getStringDataByKey(rs, "ketqua_xl"),
                                kqxlId: Utils.getLongDataByKey(rs, "kqxlId")
                        ))
                        if (thangHH != null && thangHH.trim() != '' && thangHH.trim().toLowerCase() != 'null' && !dsThangHetHan.contains(thangHH)) {
                            dsThangHetHan.add(thangHH)
                        }
                    }
                }
            }
            loggingManaged.info("[KhachHangTiemNangController] - [dsKHTiemNang] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            if (check) {
                KhachHangTiemNangResponse response = new KhachHangTiemNangResponse()
                response.setDsKhachHangTiemNang(ret)
                response.setDsThangHetHan(dsThangHetHan)
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[KhachHangTiemNangController] - [dsKHTiemNang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<KhachHangTiemNangResponse>> dsKHTiemNangV2(KhachHangTiemNangRequest requestBody) {
        List<TTKhachHangTiemNang> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [dsKHTiemNangV2] - Request: ", new ImmutableMap.Builder<String, Object>().
                    put("request_id", requestId).put("body", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request);
            String dataSrc = session_.get(CSS_SCH);
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            List<String> dsThangHetHan = new ArrayList<>()
            boolean check = false
            String sqlString = "{ call css.lay_ds_khachhang_tn_smp_V2(?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), Long.parseLong(nhanVienId), requestBody.getTuNgay(), requestBody.getDenNgay(),
                                     requestBody.getLoaiTBId(), requestBody.getThangHetHan(), requestBody.getKqxlId(), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        check = true
                        String thangHH = Utils.getStringDataByKey(rs, "thang_kt")
                        ret.add(new TTKhachHangTiemNang(
                                loaiTBId: Utils.getLongDataByKey(rs, "loaitb_id"),
                                dichVuVTId: Utils.getLongDataByKey(rs, "dichvuvt_id"),
                                hdkhId: Utils.getLongDataByKey(rs, "hdkh_id"),
                                thangHetHan: Utils.getStringDataByKey(rs, "thang_kt"),
                                tenKH: Utils.getStringDataByKey(rs, "ten_kh"),
                                loaiHinhTB: Utils.getStringDataByKey(rs, "loaihinh_tb"),
                                soDT: Utils.getStringDataByKey(rs, "so_dt"),
                                diaChiKH: Utils.getStringDataByKey(rs, "diachi_kh"),
                                kqxl: Utils.getStringDataByKey(rs, "ketqua_xl"),
                                kqxlId: Utils.getLongDataByKey(rs, "kqxlId")
                        ))
                        if (thangHH != null && thangHH.trim() != '' && thangHH.trim().toLowerCase() != 'null' && !dsThangHetHan.contains(thangHH)) {
                            dsThangHetHan.add(thangHH)
                        }
                    }
                }
            }
            loggingManaged.info("[KhachHangTiemNangController] - [dsKHTiemNangV2] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            if (check) {
                KhachHangTiemNangResponse response = new KhachHangTiemNangResponse()
                response.setDsKhachHangTiemNang(ret)
                response.setDsThangHetHan(dsThangHetHan)
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[KhachHangTiemNangController] - [dsKHTiemNangV2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TapKH>>> dsTapKH() {
        List<TapKH> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [dsTapKH] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call CSS.LAY_DS_TAP_KHTN(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), Long.parseLong(nhanVienId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new TapKH().convertDataFromDatabase(rs))
                    }
                }
            }
            loggingManaged.info("[KhachHangTiemNangController] - [dsTapKH] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            if (ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[KhachHangTiemNangController] - [dsTapKH] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<PhieuKhachHangTiemNang>>> dsPhieuKHTiemNang() {
        List<PhieuKhachHangTiemNang> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [dsPhieuKHTiemNang] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request);
            String dataSrc = session_.get(CSS_SCH);
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String donViId = session_.get(ID_DONVI)
            boolean check = false
            String sqlString = "{ call css.sp_tuvan_ds_phieu_khtn(?,?,?,?,?,?,?) }"
            //p_tthd_id number, – (fix: 1) 1 la danh cho NVKT, 3 – danh cho KDV va CTV TC, 5 --Danh cho GDV
            // p_ttph_id number, – (fix: 1) 1 la phieu moi duoc giao, -3 Phieu da tra lai, – 2 Phieu da giao, 4- Phieu da hoan thanh, 7 – Phieu ton
            // p_kieu number, – (fix: 2) 1 lay theo don vi, 2 lay theo nhan vien
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), Long.parseLong(nhanVienId), Long.parseLong(donViId), 1, 1, 2, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        check = true
                        ret.add(new PhieuKhachHangTiemNang(
                                phieuId: Utils.getLongDataByKey(rs, "PHIEU_ID"), phieuChaId: Utils.getLongDataByKey(rs, "PHIEU_CHA_ID"),
                                hdkhId: Utils.getLongDataByKey(rs, "HDKH_ID"), hdtbId: Utils.getLongDataByKey(rs, "HDTB_ID"),
                                khachHangId: Utils.getLongDataByKey(rs, "KHACHHANG_ID"), kieuLDId: Utils.getLongDataByKey(rs, "KIEULD_ID"),
                                donViNhanId: Utils.getLongDataByKey(rs, "DONVI_NHAN_ID"), dichVuVTId: Utils.getLongDataByKey(rs, "DICHVUVT_ID"),
                                loaiNCId: Utils.getLongDataByKey(rs, "LOAINC_ID"), mucDoId: Utils.getLongDataByKey(rs, "MUCDO_ID"),
                                nhanVienTBId: Utils.getLongDataByKey(rs, "NHANVIEN_TN_ID"), isCheck: Utils.getStringDataByKey(rs, "ISCHECK"),
                                tenKH: Utils.getStringDataByKey(rs, "TEN_KH"), maGD: Utils.getStringDataByKey(rs, "MA_GD"), soDT: Utils.getStringDataByKey(rs, "SO_DT"),
                                diaChiKH: Utils.getStringDataByKey(rs, "DIACHI_KH"), ngayYC: Utils.getStringDataByKey(rs, "NGAY_YC") != null ? Utils.convertToDay(Utils.getStringDataByKey(rs, "NGAY_YC")) : "",
                                trangThaiHD: Utils.getStringDataByKey(rs, "TRANGTHAI_HD"), ghiChu: Utils.getStringDataByKey(rs, "GHICHU"), ttgv: Utils.getStringDataByKey(rs, "TTGV"),
                                tenNV: Utils.getStringDataByKey(rs, "TEN_NV"), ngayGiao: Utils.getStringDataByKey(rs, "NGAYGIAO") != null ? Utils.convertToDayAndHour(Utils.getStringDataByKey(rs, "NGAYGIAO")) : "",
                                maKH: Utils.getStringDataByKey(rs, "MA_KH"), tenDVVT: Utils.getStringDataByKey(rs, "TEN_DVVT"), dvGiao: Utils.getStringDataByKey(rs, "DV_GIAO"),
                                lyDoTonTC: Utils.getStringDataByKey(rs, "LYDOTON_TC"), tenKieuLD: Utils.getStringDataByKey(rs, "TEN_KIEULD"),
                                moTa: Utils.getStringDataByKey(rs, "MOTA"), gioHenTu: Utils.getStringDataByKey(rs, "GIOHEN_TU") != null ? Utils.convertToDayAndHour(Utils.getStringDataByKey(rs, "GIOHEN_TU")) : "",
                                gioHenDen: Utils.getStringDataByKey(rs, "GIOHEN_DEN") != null ? Utils.convertToDayAndHour(Utils.getStringDataByKey(rs, "GIOHEN_DEN")) : "", tenHoDan: Utils.getStringDataByKey(rs, "TEN_HODAN"),
                                diaChiHoDan: Utils.getStringDataByKey(rs, "DIACHI_HODAN"), moTaHoDan: Utils.getStringDataByKey(rs, "MOTA_HODAN"),
                                toaDo: Utils.getStringDataByKey(rs, "TOADO"), tinhTrang: Utils.getStringDataByKey(rs, "TINHTRANG"),
                                chiTieuTG: Utils.getStringDataByKey(rs, "CHITIEU_TG"), thoiGianCK: Utils.getStringDataByKey(rs, "THOIGIAN_CK"),
                                gioCL: Utils.getStringDataByKey(rs, "GIO_CL"), nhanVienTN: Utils.getStringDataByKey(rs, "NHANVIEN_TN"),
                                donViTN: Utils.getStringDataByKey(rs, "DONVI_TN"), loaiNC: Utils.getStringDataByKey(rs, "LOAI_NC"),
                                mucDo: Utils.getStringDataByKey(rs, "MUCDO"), loaiHinhTB: Utils.getStringDataByKey(rs, "LOAIHINH_TB"),
                                soThangCK: Utils.getStringDataByKey(rs, "SOTHANG_CK"), thangCK: Utils.getStringDataByKey(rs, "THANGCK")
                        ))
                    }
                }
            }
            loggingManaged.info("[HopDongDungThuController] - [dsPhieuKHTiemNang] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            if (check) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[HopDongDungThuController] - [dsPhieuKHTiemNang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<CTKhachHangTiemNang>> layChiTietKHTiemNang(Long phieuId, Long hdkhId) {
        List<CTKhachHangTiemNang> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [layChiTietKHTiemNang] - Request: ", new ImmutableMap.Builder<String, Object>().
                    put("request_id", requestId).put("phieuId", phieuId).put("hdkhId", hdkhId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request);
            String dataSrc = session_.get(CSS_SCH);
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.lay_thongtin_kh_tiemnang(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), hdkhId, phieuId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs.next()) {
                        ret.add(new CTKhachHangTiemNang(
                                loaiGTId: Utils.getLongDataByKey(rs, "LOAIGT_ID"), tinhId: Utils.getLongDataByKey(rs, "TINH_ID"),
                                hinhThucId2: Utils.getLongDataByKey(rs, "hinhthuc_id2"), nhaMangId: Utils.getLongDataByKey(rs, "nhacc_cu_id"),
                                hdkhId: Utils.getLongDataByKey(rs, "HDKH_ID"), quanId: Utils.getLongDataByKey(rs, "QUAN_ID"),
                                khachHangId: Utils.getLongDataByKey(rs, "KHACHHANG_ID"), kieuLDId: Utils.getLongDataByKey(rs, "KIEULD_ID"),
                                phuongId: Utils.getLongDataByKey(rs, "PHUONG_ID"), dichVuVTId: Utils.getLongDataByKey(rs, "DICHVUVT_ID"),
                                phoId: Utils.getLongDataByKey(rs, "PHO_ID"), apId: Utils.getLongDataByKey(rs, "AP_ID"), khuId: Utils.getLongDataByKey(rs, "KHU_ID"),
                                soGT: Utils.getStringDataByKey(rs, "SO_GT"), tenKH: Utils.getStringDataByKey(rs, "TEN_KH"), dichVuVT: Utils.getStringDataByKey(rs, "TEN_DVVT"),
                                ngayCap: Utils.getStringDataByKey(rs, "NGAYCAP") != null ? Utils.convertToDayAndHour(Utils.getStringDataByKey(rs, "NGAYCAP")) : "", soDT: Utils.getStringDataByKey(rs, "SO_DT"),
                                noiCap: Utils.getStringDataByKey(rs, "NOICAP"), diaChi: Utils.getStringDataByKey(rs, "DIACHI"),
                                soNha: Utils.getStringDataByKey(rs, "SONHA"), ghiChu: Utils.getStringDataByKey(rs, "GHICHU"),
                                dacDiemId: Utils.getLongDataByKey(rs, "DACDIEM_ID"), kinhDo: Utils.getStringDataByKey(rs, "KINHDO"),
                                viDo: Utils.getStringDataByKey(rs, "VIDO"), loaiTBId: Utils.getLongDataByKey(rs, "LOAITB_ID"),
                                lyDoTonTCId: Utils.getLongDataByKey(rs, "LYDOTONTC_ID"), nhomTonId: Utils.getLongDataByKey(rs, "NHOMTON_ID"), lyDoTonTC: Utils.getStringDataByKey(rs, "LYDOTON_TC"),
                                ngayKTCK: Utils.getStringDataByKey(rs, "NGAY_KTCK") != null ? Utils.convertToDayAndHour(Utils.getStringDataByKey(rs, "NGAY_KTCK")) : "", hinhThucId: Utils.getLongDataByKey(rs, "HINHTHUC_ID"),
                                thueBaoId: Utils.getLongDataByKey(rs, "THUEBAO_ID"), userSD: Utils.getStringDataByKey(rs, "USERSD"),
                                soDTCaiApp: Utils.getStringDataByKey(rs, "SODT_CAIAPP"), tinh: Utils.getStringDataByKey(rs, "TENTINH"),
                                quan: Utils.getStringDataByKey(rs, "TEN_QUAN"), phuong: Utils.getStringDataByKey(rs, "TEN_PHUONG"),
                                pho: Utils.getStringDataByKey(rs, "TEN_PHO"), ap: Utils.getStringDataByKey(rs, "TEN_AP"), tenChuQuan: Utils.getStringDataByKey(rs, "tenchuquan"),
                                khu: Utils.getStringDataByKey(rs, "TEN_KHU"), dacDiem: Utils.getStringDataByKey(rs, "DACDIEM"),
                                loaiGT: Utils.getStringDataByKey(rs, "LOAI_GT"), kieuLD: Utils.getStringDataByKey(rs, "TEN_KIEULD"), loaiHinhTB: Utils.getStringDataByKey(rs, "LOAIHINH_TB"),
                                hinhthuc2: Utils.getStringDataByKey(rs, "hinhthuc2")
                        ))
                    }
                }
            }
            loggingManaged.info("[HopDongDungThuController] - [layChiTietKHTiemNang] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", ret != null && ret.size() > 0 ? ret.get(0).toString() : "").build())
            if (ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret.get(0)))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[HopDongDungThuController] - [layChiTietKHTiemNang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> xoaPhieuKHTiemNang(Long hdkhId) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [xoaPhieuKHTiemNang] - Request: ", new ImmutableMap.Builder<String, Object>().
                    put("request_id", requestId).put("phieuId", hdkhId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request);
            String dataSrc = session_.get(CSS_SCH);
            String phanVungId = session_.get(ID_TINHTHANH)
            String message = ""
            String sqlString = "{ call CSS.LAP_HOP_DONG.xoa_hdkh_v2(?,?,?) }"
            Long loaiHDId = 38 //FIXME DB báo fix 38 ở hàm này
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), hdkhId, loaiHDId]) { Object rs ->
                    if (rs != null) message = (String) rs
                }
            }
            loggingManaged.info("[HopDongDungThuController] - [xoaPhieuKHTiemNang] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", message).build())
            if (message == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[HopDongDungThuController] - [xoaPhieuKHTiemNang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> henKhachHang(HenKhachHangRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [henKhachHang] - Request: ", new ImmutableMap.Builder<String, Object>().
                    put("request_id", requestId).put("body", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request);
            String dataSrc = session_.get(CSS_SCH);
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String donViId = session_.get(ID_DONVI)
            String maNV = session_.get(MA_NV)
            String mayCN = session_.get(DEVICE_ID)
            String message = ""
            String sqlString = "{ ? = call css.fn_hen_kh_tiemnang_smp(?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.getHdtbId(), requestBody.getTuNgay(), requestBody.getDenNgay(),
                                     mayCN, maNV, Long.parseLong(nhanVienId), Long.parseLong(donViId), requestBody.getNoiDung()]) { String rs ->
                    message = rs
                }
            }
            loggingManaged.info("[HopDongDungThuController] - [henKhachHang] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", message).build())
            if (message == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[HopDongDungThuController] - [henKhachHang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> capNhatKQXL(CapNhatKQXLRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [capNhatKQXL] - Request: ", new ImmutableMap.Builder<String, Object>().
                    put("request_id", requestId).put("body", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request);
            String dataSrc = session_.get(CSS_SCH);
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String donViId = session_.get(ID_DONVI)
            String maNV = session_.get(MA_NV)
            String mayCN = session_.get(DEVICE_ID)
            String message = ""
            String sqlString = "{ ? = call css.fn_capnhat_kqxl_khtn_smp(?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.getPhieuId(), requestBody.getKqxlId(), requestBody.getLyDoId(),
                                     Long.parseLong(nhanVienId), Long.parseLong(donViId), requestBody.getGhiChu(), maNV, mayCN]) { String rs ->
                    message = rs
                }
            }
            loggingManaged.info("[HopDongDungThuController] - [capNhatKQXL] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", message).build())
            if (message == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[HopDongDungThuController] - [capNhatKQXL] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> capNhatKHTiemNang(CNKhachHangTiemNangRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [capNhatKHTiemNang] - Request: ", new ImmutableMap.Builder<String, Object>().
                    put("request_id", requestId).put("body", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request);
            String dataSrc = session_.get(CSS_SCH);
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String donViId = session_.get(ID_DONVI)
            String maNV = session_.get(MA_NV)
            String message = ""
            String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())
            requestBody.setLyDoTonId(108)//FIXME api yêu cầu fix 108 ở hàm này
            String sqlString = "{ ? = call css.tuvan_khachhang_tiemnang_v2(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.getHdkhId(), requestBody.getKhachHangId(), requestBody.getKieuLdId(),
                                     date, requestBody.getTenKH(), requestBody.getDiaChi(), requestBody.getSoDt(), requestBody.getQuanId(),
                                    requestBody.getPhuongId(), requestBody.getPhoId(), requestBody.getKhuId(), requestBody.getApId(), requestBody.getDacDiemId(),
                                    requestBody.getSoNha(), requestBody.getKinhDo(), requestBody.getViDo(), requestBody.getLoaiGtId(), requestBody.getSoGt(),
                                    requestBody.getNgayCap(), requestBody.getNoiCap(), requestBody.getGhiChu(), Long.parseLong(nhanVienId), Long.parseLong(donViId),
                                    maNV, requestBody.getDichVuVTId(), requestBody.getLoaiTBId(), requestBody.getLyDoTonId(), requestBody.getThangHH(),
                                    requestBody.hinhThucId, requestBody.nhacc_cu_id]) { String rs ->
                    message = rs
                }
            }
            loggingManaged.info("[KhachHangTiemNangController] - [capNhatKHTiemNang] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", message).build())
            if (message == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[KhachHangTiemNangController] - [capNhatKHTiemNang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> capNhatKHTiemNangV2(CNKhachHangTiemNangRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhachHangTiemNangController] - [capNhatKHTiemNangV2] - Request: ", new ImmutableMap.Builder<String, Object>().
                    put("request_id", requestId).put("body", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request);
            String dataSrc = session_.get(CSS_SCH);
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String donViId = session_.get(ID_DONVI)
            String maNV = session_.get(MA_NV)
            String message = ""
            String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())
            requestBody.setLyDoTonId(108)//FIXME api yêu cầu fix 108 ở hàm này
            String sqlString = "{ ? = call css.tuvan_khachhang_tiemnang_v2(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.getHdkhId(), requestBody.getKhachHangId(), requestBody.getKieuLdId(),
                                     date, requestBody.getTenKH(), requestBody.getDiaChi(), requestBody.getSoDt(), requestBody.getQuanId(), requestBody.getPhuongId(),
                                     requestBody.getPhoId(), requestBody.getKhuId(), requestBody.getApId(), requestBody.getDacDiemId(), requestBody.getSoNha(), requestBody.getKinhDo(),
                                     requestBody.getViDo(), requestBody.getLoaiGtId(), requestBody.getSoGt(), requestBody.getNgayCap(), requestBody.getNoiCap(),
                                     requestBody.getGhiChu(), Long.parseLong(nhanVienId), Long.parseLong(donViId), maNV, requestBody.getDichVuVTId(), requestBody.getLoaiTBId(),
                                     requestBody.getLyDoTonId(), requestBody.getThangHH(), requestBody.getHinhThucId(), requestBody.getNhaMangId()]) { String rs ->
                    message = rs
                }
            }
            loggingManaged.info("[KhachHangTiemNangController] - [capNhatKHTiemNangV2] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", message).build())
            if (message == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[KhachHangTiemNangController] - [capNhatKHTiemNangV2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
}
