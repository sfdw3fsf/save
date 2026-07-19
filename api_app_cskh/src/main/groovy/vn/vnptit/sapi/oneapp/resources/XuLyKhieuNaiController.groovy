package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import groovy.sql.GroovyRowResult
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.api.XuLyKhieuNaiApi
import io.swagger.model.dto.*
import io.swagger.model.request.CNChenNoRequest
import io.swagger.model.request.CNDonViLoiRequest
import io.swagger.model.request.CNHoSoKhieuNaiRequest
import io.swagger.model.request.CNNhanVienLoiRequest
import io.swagger.model.request.GiaoPhieuPHKhieuNaiRequest
import io.swagger.model.request.GiaoViecXLKhieuNaiRequest
import io.swagger.model.request.HoanThienHSKhieuNaiRequest
import io.swagger.model.request.KhoaPhieuKhieuNaiRequest
import io.swagger.model.request.TraPhieuKhieuNaiRequest
import io.swagger.model.request.XoaGiaoViecNVRequest
import io.swagger.model.response.BaseResponse
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.convert.ResultSetToObject
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.model.response.PhieuPhoiHopKHDN
import vn.vnptit.sapi.oneapp.service.SmsService
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.MessageUtils
import vn.vnptit.sapi.oneapp.util.Utils

import javax.servlet.http.HttpServletRequest
import java.sql.ResultSet

import static vn.vnptit.sapi.oneapp.resources.GenericApi.*

@RestController
class XuLyKhieuNaiController implements XuLyKhieuNaiApi {

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
    SmsService smsService
    @Autowired
    ResultSetToObject resultSetToObject
    @Autowired
    DataUtils dataUtils

    @Autowired
    XuLyKhieuNaiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }

    @Override
    ResponseEntity<BaseResponse<List<TBKhieuNaiXL>>> dsTBKhieuNaiXL(Integer trangThai, Integer toTruong) {
        List<TBKhieuNaiXL> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsTBKhieuNaiXL] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("trangThai", trangThai).put("toTruong", toTruong).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(ID_TINHTHANH)

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call qltn.sp_lay_ds_khieunai(?,?,?,?,?,?,?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), Long.parseLong(donViId), Long.parseLong(nhanVienId), "2,4", "2252,2256", "", 0, trangThai, toTruong, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new TBKhieuNaiXL(
                                khieuNaiId: Utils.getStringDataByKey(rs,"khieunai_id"),
                                thueBaoId: Utils.getStringDataByKey(rs,"thuebao_id"),
                                dichVuVTId: Utils.getStringDataByKey(rs,"dichvuvt_id"),
                                loaiTBId: Utils.getStringDataByKey(rs,"loaitb_id"),
                                ngayTn: Utils.getStringDataByKey(rs,"ngay_tn"),
                                nguoiKn: Utils.getStringDataByKey(rs,"nguoi_kn"),
                                dienThoaiLh: Utils.getStringDataByKey(rs,"dienthoai_lh"),
                                chuDeKNId: Utils.getStringDataByKey(rs,"chudekn_id"),
                                ndKhieuNai: Utils.getStringDataByKey(rs,"nd_khieunai"),
                                ttknId: Utils.getStringDataByKey(rs,"ttkn_id"),
                                donViId: Utils.getStringDataByKey(rs,"donvi_id"),
                                nhanVienId: Utils.getStringDataByKey(rs,"nhanvien_id"),
                                ngayGq: Utils.getStringDataByKey(rs,"ngay_gq"),
                                nhanVienGqId: Utils.getStringDataByKey(rs,"nhanvien_gq_id"),
                                noiDungGq: Utils.getStringDataByKey(rs,"noidung_gq"),
                                henGQTu: Utils.getStringDataByKey(rs,"hengq_tu"),
                                henGQDen: Utils.getStringDataByKey(rs,"hengq_den"),
                                ndTon: Utils.getStringDataByKey(rs,"nd_ton"),
                                ghiChuKn: Utils.getStringDataByKey(rs,"ghichu_kn"),
                                mayCn: Utils.getStringDataByKey(rs,"may_cn"),
                                ngayCn: Utils.getStringDataByKey(rs,"ngay_cn"),
                                nguoiCn: Utils.getStringDataByKey(rs,"nguoi_cn"),
                                congVanXl: Utils.getStringDataByKey(rs,"congvan_xl"),
                                ngayBn: Utils.getStringDataByKey(rs,"ngay_bn"),
                                congVanBn: Utils.getStringDataByKey(rs,"congvan_bn"),
                                cmt: Utils.getStringDataByKey(rs,"cmt"),
                                ngayCap: Utils.getStringDataByKey(rs,"ngaycap"),
                                noiCap: Utils.getStringDataByKey(rs,"noicap"),
                                htKNId: Utils.getStringDataByKey(rs,"htkn_id"),
                                nguyenNhanKn: Utils.getStringDataByKey(rs,"nguyennhan_kn"),
                                diaChiLh: Utils.getStringDataByKey(rs,"diachi_lh"),
                                maTb: Utils.getStringDataByKey(rs,"ma_tb"),
                                tienGc: Utils.getStringDataByKey(rs,"tien_gc"),
                                vatGc: Utils.getStringDataByKey(rs,"vat_gc"),
                                quyTrinhId: Utils.getStringDataByKey(rs,"quytrinh_id"),
                                ketQuaKNId: Utils.getStringDataByKey(rs,"ketquakn_id"),
                                thanhToanId: Utils.getStringDataByKey(rs,"thanhtoan_id"),
                                maKn: Utils.getStringDataByKey(rs,"ma_kn"),
                                giamCuocDt: Utils.getStringDataByKey(rs,"giamcuoc_dt"),
                                giamCuocCp: Utils.getStringDataByKey(rs,"giamcuoc_cp"),
                                tienTrinhknId: Utils.getStringDataByKey(rs,"tientrinhkn_id"),
                                vatDt: Utils.getStringDataByKey(rs,"vat_dt"),
                                vatCp: Utils.getStringDataByKey(rs,"vat_cp"),
                                kyKn: Utils.getStringDataByKey(rs,"ky_kn"),
                                lyDoHuygc: Utils.getStringDataByKey(rs,"lydo_huygc"),
                                ngayHuy: Utils.getStringDataByKey(rs,"ngayhuy"),
                                nguoiHuy: Utils.getStringDataByKey(rs,"nguoihuy"),
                                capDoXLId: Utils.getStringDataByKey(rs,"capdoxl_id"),
                                chiTieuTg: Utils.getStringDataByKey(rs,"chitieu_tg"),
                                thoiGianCk: Utils.getStringDataByKey(rs,"thoigian_ck"),
                                phanAnhId: Utils.getStringDataByKey(rs,"phananh_id"),
                                blacklistId: Utils.getStringDataByKey(rs,"blacklist_id"),
                                baoHongId: Utils.getStringDataByKey(rs,"baohong_id"),
                                thoiGianTn: Utils.getStringDataByKey(rs,"thoigian_tn"),
                                loaiHDId: Utils.getStringDataByKey(rs,"loaihd_id"),
                                hdkhId: Utils.getStringDataByKey(rs,"hdkh_id"),
                                email: Utils.getStringDataByKey(rs,"email"),
                                paxlId: Utils.getStringDataByKey(rs,"paxl_id"),
                                haiLong: Utils.getStringDataByKey(rs,"hailong"),
                                httlId: Utils.getStringDataByKey(rs,"httl_id"),
                                maTt: Utils.getStringDataByKey(rs,"ma_tt"),
                                maKh: Utils.getStringDataByKey(rs,"ma_kh"),
                                goiktId: Utils.getStringDataByKey(rs,"goikt_id"),
                                tenTb: Utils.getStringDataByKey(rs,"ten_tb"),
                                diaChiTb: Utils.getStringDataByKey(rs,"diachi_tb"),
                                tenChuDe: Utils.getStringDataByKey(rs,"tenchude"),
                                tenNv: Utils.getStringDataByKey(rs,"ten_nv"),
                                phieuKNId: Utils.getStringDataByKey(rs,"phieukn_id"),
                                ndGiao: Utils.getStringDataByKey(rs,"nd_giao"),
                                ngayHtGp: Utils.getStringDataByKey(rs,"ngay_ht_gp"),
                                ngayCapCMT: Utils.getStringDataByKey(rs,"ngaycapcmt"),
                                lyDoTon: Utils.getStringDataByKey(rs,"lydo_ton"),
                                nhanVienThId: Utils.getStringDataByKey(rs,"nhanvien_th_id"),
                                donViGiaoId: Utils.getStringDataByKey(rs,"donvi_giao_id"),
                                ndXacMinh: Utils.getStringDataByKey(rs,"nd_xacminh"),
                                donViNhanId: Utils.getStringDataByKey(rs,"DONVI_NHAN_ID"),
                                nhanVienGq: Utils.getStringDataByKey(rs,"nhanvien_gq"),
                                ngayGiao: Utils.getStringDataByKey(rs,"ngaygiao"),
                                ndTraCon: Utils.getStringDataByKey(rs,"nd_tra_con"),
                                nhanVienTN: Utils.getStringDataByKey(rs,"NHANVIEN_TN"),
                                tenDVVT: Utils.getStringDataByKey(rs,"TEN_DVVT"),
                                loaiHinhTB: Utils.getStringDataByKey(rs,"LOAIHINH_TB"),
                                nhanVienGiao: Utils.getStringDataByKey(rs,"NHANVIEN_GIAO"),
                                donViNhan: Utils.getStringDataByKey(rs,"DONVI_NHAN"),
                                donViGiao: Utils.getStringDataByKey(rs,"DONVI_GIAO"),
                                tuyenThu: Utils.getStringDataByKey(rs,"TUYENTHU"),
                                ketQua: Utils.getStringDataByKey(rs,"ketqua"),
                                ngayGQ: Utils.getStringDataByKey(rs,"ngay_gq"),
                                nvThucHien: Utils.getStringDataByKey(rs,"nhanvien_th"),
                                paXLKN: Utils.getStringDataByKey(rs,"phuongan_xlkn"),
                                ketQuaKN: Utils.getStringDataByKey(rs,"ketqua_kn"),
                                donViTraConId: Utils.getStringDataByKey(rs,"donvitra_con_id")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsTBKhieuNaiXL] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsTBKhieuNaiXL] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Integer>> toTruong() {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [toTruong] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nguoiDungId = session_.get(NGUOIDUNG_ID)
            Integer ret
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ ? = call css.kiemtra_quyen_nd(?,?,?) }"
                sql.call(sqlString, [Sql.INTEGER, Long.parseLong(phanVungId), Long.parseLong(nguoiDungId), 60]) { Integer rs ->
                    ret = rs
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [toTruong] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [toTruong] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsKieuHienThi() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsKieuHienThi] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
               String sqlString = "{ call CSS.webapi_ex.Post_List_v2(?,?,?,?,?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), "KIEUHIENTHI_XLKN", "", "", "", "", "", CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: Utils.getLongDataByKey(rs,"id"), NAME: Utils.getStringDataByKey(rs,"name")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsKieuHienThi] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsKieuHienThi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsKQXL() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsKQXL] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call CSS.webapi_ex.Post_List_v2(?,?,?,?,?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), "KETQUA_XLKN", "", "", "", "", "", CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: Utils.getLongDataByKey(rs,"id"), NAME: Utils.getStringDataByKey(rs,"name")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsKQXL] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsKQXL] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<NhanVienXLKN>>> dsNhanVienTH() {
        List<NhanVienXLKN> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsNhanVienTH] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call QLTN.lay_ds_nv_xuly_kn(?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), Long.parseLong(nhanVienId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new NhanVienXLKN(nhanVienId: Utils.getLongDataByKey(rs,"nhanvien_id"), maNv: Utils.getStringDataByKey(rs,"ma_nv"),
                                tenNv: Utils.getStringDataByKey(rs,"ten_nv"), tenDv: Utils.getStringDataByKey(rs,"ten_dv"),
                                tenHienThi: Utils.getStringDataByKey(rs,"ten_hienthi"), name: Utils.getStringDataByKey(rs,"name")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsNhanVienTH] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsNhanVienTH] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsPhuongAnXL() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsPhuongAnXL] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call CSS.webapi_ex.Post_List_v2(?,?,?,?,?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), "PHUONGAN_XLKN", "", "", "", "", "", CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: Utils.getLongDataByKey(rs,"PAXL_ID"), NAME: Utils.getStringDataByKey(rs,"PHUONGAN_XLKN")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsPhuongAnXL] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsPhuongAnXL] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<KetQuaLoi>>> dsKQLoi() {
        List<KetQuaLoi> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsKQLoi] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call CSS.webapi_ex.Post_List_v2(?,?,?,?,?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), "LAY_DS_KETQUA_KN", "2", "", "", "", "", CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new KetQuaLoi(id: Utils.getLongDataByKey(rs,"ID"), name: Utils.getStringDataByKey(rs,"NAME"), selected: Utils.getStringDataByKey(rs,"selected")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsKQLoi] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsKQLoi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsMucDoHL() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsMucDoHL] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call CSS.webapi_ex.Post_List_v2(?,?,?,?,?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), "MUCDO_HAILONG_XLKN", "", "", "", "", "", CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: Utils.getLongDataByKey(rs,"ID"), NAME: Utils.getStringDataByKey(rs,"NAME")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsMucDoHL] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsMucDoHL] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsKieuTra() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsKieuTra] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call qltn.sp_loai_phieutra_xlkn(?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), 1, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: Utils.getLongDataByKey(rs,"loaipt_id"), NAME: Utils.getStringDataByKey(rs,"noidung")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsKieuTra] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsKieuTra] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<NVChiuTrachNhiemXLKN>>> dsNVChiuTrachNhiem(Long khieuNaiId) {
        List<NVChiuTrachNhiemXLKN> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsNVChiuTrachNhiem] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("khieuNaiId", khieuNaiId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call QLTN.LAY_DS_NHANVIEN_DONVI_PHAT(?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), khieuNaiId, "1", CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new NVChiuTrachNhiemXLKN(stt: Utils.getStringDataByKey(rs,"STT"), donViId: Utils.getLongDataByKey(rs,"DONVI_ID"),
                                nhanVienId: Utils.getLongDataByKey(rs,"NHANVIEN_ID"), maNv: Utils.getStringDataByKey(rs,"MA_NV"),
                                tenNv: Utils.getStringDataByKey(rs,"TEN_NV"), diaChiNV: Utils.getStringDataByKey(rs,"DIACHI_NV"),
                                gioiTinh: Utils.getStringDataByKey(rs,"GIOITINH"), chucDanh: Utils.getStringDataByKey(rs,"CHUCDANH"),
                                donViDLId: Utils.getLongDataByKey(rs,"DONVI_DL_ID"), sdt: Utils.getStringDataByKey(rs,"SO_DT"),
                                ngaySn: Utils.getStringDataByKey(rs,"NGAY_SN"), chuKy: Utils.getStringDataByKey(rs,"CHUKY"),
                                flag: Utils.getStringDataByKey(rs,"FLAG"), ghiChu: Utils.getStringDataByKey(rs,"GHICHU"),
                                tienDC: Utils.getStringDataByKey(rs,"TIEN_DC"), email: Utils.getStringDataByKey(rs,"EMAIL"),
                                ngayLogin: Utils.getStringDataByKey(rs,"NGAY_LOGIN"), maOTP: Utils.getStringDataByKey(rs,"MA_OTP"),
                                emailTmp: Utils.getStringDataByKey(rs,"EMAIL_TMP"), tenTN: Utils.getStringDataByKey(rs,"TEN_TN"),
                                maNVVnp: Utils.getStringDataByKey(rs,"MANV_VNP"), viTriId: Utils.getLongDataByKey(rs,"VITRI_ID"),
                                hthdId: Utils.getLongDataByKey(rs,"HTHD_ID"), otp: Utils.getStringDataByKey(rs,"OTP"),
                                maThe: Utils.getStringDataByKey(rs,"MA_THE"), userVi: Utils.getStringDataByKey(rs,"USER_VI"),
                                maKenh: Utils.getStringDataByKey(rs,"MAKENH"), nhom: Utils.getStringDataByKey(rs,"NHOM"),
                                doiTacId: Utils.getLongDataByKey(rs,"DOITAC_ID"), maDV: Utils.getStringDataByKey(rs,"MA_DV"),
                                tenDV: Utils.getStringDataByKey(rs,"TEN_DV")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsNVChiuTrachNhiem] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsNVChiuTrachNhiem] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<NVDangChiuLoi>>> dsNVDangChiuLoi(Long khieuNaiId) {
        List<NVDangChiuLoi> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsNVDangChiuLoi] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("khieuNaiId", khieuNaiId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call QLTN.SP_LAY_DS_NHANVIEN_DONVI_LOI_KHIEUNAI(?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), khieuNaiId, "1", CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new NVDangChiuLoi(khieuNaiId: Utils.getLongDataByKey(rs,"khieunai_id"), nhanVienId: Utils.getLongDataByKey(rs,"nhanvien_id"),
                                noiDung: Utils.getStringDataByKey(rs,"noidung"), maNv: Utils.getStringDataByKey(rs,"ma_nv"),
                                tenNv: Utils.getStringDataByKey(rs,"ten_nv"), tenDv: Utils.getStringDataByKey(rs,"ten_dv")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsNVDangChiuLoi] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsNVDangChiuLoi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> cnNhanVienLoi(CNNhanVienLoiRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [cnNhanVienLoi] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("requestBody", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String mess
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ ? = call css.fn_capnhat_loi_nhanvien_xlkn(?,?,?,?,?,?) }"
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.getKhieuNaiId(), requestBody.getNhanVienId(),
                                     requestBody.getNoiDung(), 1, requestBody.getKieu()]) { String rs ->
                    mess = rs
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [cnNhanVienLoi] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", mess != null ? mess : "").build())
            if (mess != null && mess == 'OK') {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, mess, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [cnNhanVienLoi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DVChiuTrachNhiemXLKN>>> dsDVChiuTrachNhiem(Long khieuNaiId) {
        List<DVChiuTrachNhiemXLKN> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsDVChiuTrachNhiem] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("khieuNaiId", khieuNaiId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call QLTN.LAY_DS_NHANVIEN_DONVI_PHAT(?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), khieuNaiId, "2", CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DVChiuTrachNhiemXLKN(stt: Utils.getStringDataByKey(rs,"STT"), donViId: Utils.getLongDataByKey(rs,"DONVI_ID"),
                                maDV: Utils.getStringDataByKey(rs,"MA_DV"), tenDV: Utils.getStringDataByKey(rs,"TEN_DV")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsDVChiuTrachNhiem] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsDVChiuTrachNhiem] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DVDangChiuLoi>>> dsDVDangChiuLoi(Long khieuNaiId) {
        List<DVDangChiuLoi> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsDVDangChiuLoi] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("khieuNaiId", khieuNaiId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call QLTN.SP_LAY_DS_NHANVIEN_DONVI_LOI_KHIEUNAI(?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), khieuNaiId, "2", CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DVDangChiuLoi(khieuNaiId: Utils.getLongDataByKey(rs,"khieunai_id"), donViId: Utils.getLongDataByKey(rs,"donvi_id"),
                                noiDung: Utils.getStringDataByKey(rs,"noidung"), maDv: Utils.getStringDataByKey(rs,"ma_dv"), tenDv: Utils.getStringDataByKey(rs,"ten_dv")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsDVDangChiuLoi] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsDVDangChiuLoi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> cnDonViLoi(CNDonViLoiRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [cnDonViLoi] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("requestBody", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String mess
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ ? = call css.fn_capnhat_loi_nhanvien_xlkn(?,?,?,?,?,?) }"
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.getKhieuNaiId(), requestBody.getDonViId(),
                                     requestBody.getNoiDung(), 2, requestBody.getKieu()]) { String rs ->
                    mess = rs
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [cnDonViLoi] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", mess != null ? mess : "").build())
            if (mess != null && mess == 'OK') {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, mess, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [cnDonViLoi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<ThueBaoChenNo>>> dsTBChenNo(String kyHD, String maTT) {
        List<ThueBaoChenNo> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsTBChenNo] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("kyHD", kyHD).put("maTT", maTT).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call qltn.tracuu.Lay_ds_thuebao_chenno(?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), kyHD, maTT, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new ThueBaoChenNo(maTT: Utils.getStringDataByKey(rs,"ma_tt"), thangNo: Utils.getStringDataByKey(rs,"thangno"),
                                thueBao: Utils.getStringDataByKey(rs,"thuebao"), dichVuVTId: Utils.getLongDataByKey(rs,"dichvuvt_id"),
                                chuKyNo: Utils.getStringDataByKey(rs,"chukyno"), tenDVVT: Utils.getStringDataByKey(rs,"ten_dvvt"),
                                tongNo: Utils.getStringDataByKey(rs,"tongno"), noGoc: Utils.getStringDataByKey(rs,"nogoc"),
                                thue: Utils.getStringDataByKey(rs,"thue"), hoaHong: Utils.getStringDataByKey(rs,"hoahong")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsTBChenNo] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsTBChenNo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<String>>> dsChuKy(String kyHD) {
        List<String> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsChuKy] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("kyHD", kyHD).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call qltn.sp_lay_chuky_theo_kyhoadon(?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), kyHD, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(Utils.getStringDataByKey(rs,"chuky"))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsChuKy] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsChuKy] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<ThongTinGiamCuoc>>> dsGiamCuoc(Long khieuNaiId) {
        List<ThongTinGiamCuoc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsGiamCuoc] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("khieuNaiId", khieuNaiId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call qltn.sp_lay_ds_giamcuoc_xlkn(?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), khieuNaiId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new ThongTinGiamCuoc(khieuNaiId: Utils.getLongDataByKey(rs,"khieunai_id"), thangGC: Utils.getStringDataByKey(rs,"thang_gc"),
                                thangGoc: Utils.getStringDataByKey(rs,"thanggoc"), thangTD: Utils.getLongDataByKey(rs,"thang_td"),
                                thangSTD: Utils.getStringDataByKey(rs,"thang_std"), tien: Utils.getStringDataByKey(rs,"tien"),
                                tienGC: Utils.getStringDataByKey(rs,"tien_gc"), vatGC: Utils.getStringDataByKey(rs,"vat_gc"),
                                ISUPDATE: Utils.getStringDataByKey(rs,"ISUPDATE"), kyGC: Utils.getStringDataByKey(rs,"Ky_gc"), vatPT: Utils.getStringDataByKey(rs,"vat_pt")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsGiamCuoc] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsGiamCuoc] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> cnChenNo(CNChenNoRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [cnChenNo] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("requestBody", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nguoiDungId = session_.get(NGUOIDUNG_ID)
            String mess
            String dsThueBao = "["
            if (requestBody.getDsThueBao() != null && requestBody.getDsThueBao().size() > 0) {
                for (int i = 0; i < requestBody.getDsThueBao().size(); i++) {
                    if ( i < requestBody.getDsThueBao().size() - 1) {
                        dsThueBao += "{\"ma_tt\":\"" + requestBody.getDsThueBao().get(i).getMaTT() + "\",\"ma_tb\":\"" + requestBody.getDsThueBao().get(i).getMaTB() + "\",\"dichvuvt_id\":" + requestBody.getDsThueBao().get(i).getDichVuVTId() + ",\"chukyno\":\"" + requestBody.getDsThueBao().get(i).getMaTB() + "\"},"
                    } else {
                        dsThueBao += "{\"ma_tt\":\"" + requestBody.getDsThueBao().get(i).getMaTT() + "\",\"ma_tb\":\"" + requestBody.getDsThueBao().get(i).getMaTB() + "\",\"dichvuvt_id\":" + requestBody.getDsThueBao().get(i).getDichVuVTId() + ",\"chukyno\":\"" + requestBody.getDsThueBao().get(i).getMaTB() + "\"}"
                    }
                }
            }
            dsThueBao += "]"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ ? = call qltn.fn_capnhat_chenno(?,?,?,?,?) }"
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), Long.parseLong(nguoiDungId), dsThueBao,
                                     requestBody.getKyKN(), requestBody.getChuKy() + requestBody.getKyHD()]) { String rs ->
                    mess = rs
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [cnChenNo] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", mess != null ? mess : "").build())
            if (mess != null && mess == 'OK') {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, mess, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [cnChenNo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> hoanThienHSKN(HoanThienHSKhieuNaiRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [hoanThienHSKN] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("requestBody", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String donViId = session_.get(ID_DONVI)
            String mayCN = session_.get(DEVICE_ID)
            String nguoiCN = session_.get(USERNAME)
            String ipCN = session_.get(DEVICE_IP)
            String mess
            String kq
            String dsgc = "["
            if (requestBody.getDsgc() != null && requestBody.getDsgc().size() > 0) {
                for (int i = 0; i < requestBody.getDsgc().size(); i++) {
                    if ( i < requestBody.getDsgc().size() - 1) {
                        dsgc += "{\"tgc\":\"" + requestBody.getDsgc().get(i).getTgc() + "\",\"tiengc\":" + requestBody.getDsgc().get(i).getTiengc() + ",\"vat\":" + requestBody.getDsgc().get(i).getVat() + ",\"kygc\":" + requestBody.getDsgc().get(i).getKygc() + "},"
                    } else {
                        dsgc += "{\"tgc\":\"" + requestBody.getDsgc().get(i).getTgc() + "\",\"tiengc\":" + requestBody.getDsgc().get(i).getTiengc() + ",\"vat\":" + requestBody.getDsgc().get(i).getVat() + ",\"kygc\":" + requestBody.getDsgc().get(i).getKygc() + "}"
                    }
                }
            }
            dsgc += "]"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{call qltn.sp_hoanthien_hoso_khieunai(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), requestBody.getKhieuNaiId(), requestBody.getPhieuKhieuNaiId(), Long.parseLong(nhanVienId),
                        requestBody.getNhanVienGQId(), Long.parseLong(donViId), requestBody.getKqxlId(), dsgc, requestBody.getNguyenNhan(), requestBody.getTraLoi(),
                        requestBody.getXacMinh(), requestBody.getNgayGQ(), requestBody.getPaxlId(), requestBody.getHaiLong(), requestBody.getKqknId(),
                                     mayCN, nguoiCN, ipCN, Sql.VARCHAR, Sql.VARCHAR]) { String pkq, String pmess ->
                    kq = pkq
                    mess = pmess
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [hoanThienHSKN] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", mess != null ? mess : "").build())
            if (kq != null && kq == 'OK') {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, mess, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [hoanThienHSKN] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> khoaPhieu(KhoaPhieuKhieuNaiRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [khoaPhieu] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("requestBody", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String mayCN = session_.get(DEVICE_ID)
            String nguoiCN = session_.get(USERNAME)
            String ipCN = session_.get(DEVICE_IP)
            String mess
            String kq
            String dsgc = "["
            if (requestBody.getDsgc() != null && requestBody.getDsgc().size() > 0) {
                for (int i = 0; i < requestBody.getDsgc().size(); i++) {
                    if ( i < requestBody.getDsgc().size() - 1) {
                        dsgc += "{\"tgc\":\"" + requestBody.getDsgc().get(i).getTgc() + "\",\"tiengc\":" + requestBody.getDsgc().get(i).getTiengc() + ",\"vat\":" + requestBody.getDsgc().get(i).getVat() + ",\"kygc\":" + requestBody.getDsgc().get(i).getKygc() + "},"
                    } else {
                        dsgc += "{\"tgc\":\"" + requestBody.getDsgc().get(i).getTgc() + "\",\"tiengc\":" + requestBody.getDsgc().get(i).getTiengc() + ",\"vat\":" + requestBody.getDsgc().get(i).getVat() + ",\"kygc\":" + requestBody.getDsgc().get(i).getKygc() + "}"
                    }
                }
            }
            dsgc += "]"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{call qltn.sp_khoa_phieu_khieunai(?,?,?,?,?,?,?,?,?,?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), requestBody.getKhieuNaiId(), requestBody.getPhieuKhieuNaiId(),
                                     requestBody.getNhanVienGQId(), dsgc, requestBody.getNguyenNhan(), requestBody.getTraLoi(),
                                     requestBody.getNgayGQ(), mayCN, nguoiCN, ipCN, Sql.VARCHAR, Sql.VARCHAR]) { String pkq, String pmess ->
                    kq = pkq
                    mess = pmess
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [khoaPhieu] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", mess != null ? mess : "").build())
            if (kq != null && kq == 'OK') {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, mess, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [khoaPhieu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<PhieuKN>>> dsPhieuKN(String maTB) {
        List<PhieuKN> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsPhieuKN] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("maTB", maTB).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call qltn.lay_ds_phieu_yc_kn(?,?,?,?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), maTB, "0", Long.parseLong(donViId), "0", Long.parseLong(nhanVienId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new PhieuKN(phieuKNId: Utils.getLongDataByKey(rs,"PHIEUKN_ID"), stt: Utils.getStringDataByKey(rs,"STT"),
                                maKN: Utils.getStringDataByKey(rs,"MA_KN"), phieuChaId: Utils.getLongDataByKey(rs,"PHIEU_CHA_ID"),
                                loaiTBId: Utils.getLongDataByKey(rs,"LOAITB_ID"), dvGiaoId: Utils.getLongDataByKey(rs,"DONVI_GIAO_ID"),
                                maTB: Utils.getStringDataByKey(rs,"MA_TB"), tenTB: Utils.getStringDataByKey(rs,"TEN_TB"),
                                diaChiTB: Utils.getStringDataByKey(rs,"DIACHI_TB"), loaiHinhTB: Utils.getStringDataByKey(rs,"LOAIHINH_TB"),
                                ngayGiao: Utils.getStringDataByKey(rs,"NGAYGIAO"), ngayTN: Utils.getStringDataByKey(rs,"NGAY_TN"),
                                nguoiTN: Utils.getStringDataByKey(rs,"NGUOI_TN"), nguoiKN: Utils.getStringDataByKey(rs,"NGUOI_KN"),
                                henKQTu: Utils.getStringDataByKey(rs,"HENGQ_TU"), henKQDen: Utils.getStringDataByKey(rs,"HENGQ_DEN"),
                                ghiChuKN: Utils.getStringDataByKey(rs,"GHICHU_KN"), noiDungKN: Utils.getStringDataByKey(rs,"ND_KHIEUNAI"),
                                dvGiao: Utils.getStringDataByKey(rs,"DONVI_GIAO"), nvGiao: Utils.getStringDataByKey(rs,"NHANVIEN_GIAO")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsPhieuKN] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsPhieuKN] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> giaoViec(GiaoViecXLKhieuNaiRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [giaoViec] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("requestBody", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String mess
            String kq
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{call qltn.sp_giaoviec_xlkn(?,?,?,?,?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), requestBody.getKhieuNaiId(), requestBody.getPhieuKhieuNaiId(),
                                     requestBody.getGhiChu(), requestBody.getNhanVienGQId(), Long.parseLong(nhanVienId), Sql.VARCHAR, Sql.VARCHAR]) { String pkq, String pmess ->
                    kq = pkq
                    mess = pmess
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [giaoViec] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", mess != null ? mess : "").build())
            if (kq != null && kq == 'OK') {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, mess, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [giaoViec] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> traPhieu(TraPhieuKhieuNaiRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [traPhieu] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("requestBody", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String mayCN = session_.get(DEVICE_ID)
            String nguoiCN = session_.get(USERNAME)
            String mess
            String kq
            List<SMSTraPhieuKN> smsList = []
            String dsPhieu = "["
            if (requestBody.getDsPhieu() != null && requestBody.getDsPhieu().size() > 0) {
                for (int i = 0; i < requestBody.getDsPhieu().size(); i++) {
                    if ( i < requestBody.getDsPhieu().size() - 1) {
                        dsPhieu += "{\"khieunai_id\":" + requestBody.getDsPhieu().get(i).getKhieuNaiId() + ",\"phieukn_id\":" + requestBody.getDsPhieu().get(i).getPhieuKNId() + ",\"nd_tra\":\"" + requestBody.getDsPhieu().get(i).getNdTra() + "\",\"nhanvien_tra_id\":" + nhanVienId + ",\"ghichu_th\":\"" + requestBody.getDsPhieu().get(i).getGhiChuTH() + "\"},"
                    } else {
                        dsPhieu += "{\"khieunai_id\":" + requestBody.getDsPhieu().get(i).getKhieuNaiId() + ",\"phieukn_id\":" + requestBody.getDsPhieu().get(i).getPhieuKNId() + ",\"nd_tra\":\"" + requestBody.getDsPhieu().get(i).getNdTra() + "\",\"nhanvien_tra_id\":" + nhanVienId + ",\"ghichu_th\":\"" + requestBody.getDsPhieu().get(i).getGhiChuTH() + "\"}"
                    }
                }
            }
            dsPhieu += "]"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{call qltn.sp_capnhat_traphieu_xlkn(?,?,?,?,?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), 0, dsPhieu, mayCN, nguoiCN, Sql.VARCHAR, Sql.VARCHAR, CURSOR_PARAMETER]) { String pkq, String pmess, ResultSet rs ->
                    kq = pkq
                    mess = pmess
                    while (rs != null && rs.next()) {
                        smsList.add(new SMSTraPhieuKN(sdt: Utils.getStringDataByKey(rs,"sdt_nhan"),
                                phieuKNId: Utils.getLongDataByKey(rs,"phieukn_id"), noiDung: Utils.getStringDataByKey(rs,"noidung")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [traPhieu] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", mess != null ? mess : "").build())
            if (kq != null && kq == 'OK') {
                if (smsList != null && smsList.size() > 0) {
                    String token = session_.get(TOKEN)
                    smsService.init(token)
                    for (SMSTraPhieuKN item : smsList) {
                        //smsService.send(item.getNoiDung(), item.getSdt())
                    }
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, mess, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [traPhieu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<HuongGiaoPhieuKN>>> dsHuongGiao(Long quyTrinhId) {
        List<HuongGiaoPhieuKN> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsHuongGiao] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("quyTrinhId", quyTrinhId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call qltn.xuly_khieunai_common.Lay_ds_huongiao_theo_ttkn_id(?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), quyTrinhId, 3, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new HuongGiaoPhieuKN(huongGiaoId: Utils.getLongDataByKey(rs,"huonggiao_id"), sms: Utils.getStringDataByKey(rs,"sms"),
                                giaoViec: Utils.getStringDataByKey(rs,"giaoviec"), giaoViecNVKD: Utils.getStringDataByKey(rs,"giaoviec_nvkd"),
                                huongGiao: Utils.getStringDataByKey(rs,"HUONGGIAO")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsHuongGiao] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsHuongGiao] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DVGiaoPhieuKN>>> dsDonViGiao(Long huongGiaoId) {
        List<DVGiaoPhieuKN> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsDonViGiao] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("huongGiaoId", huongGiaoId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call qltn.xuly_khieunai_common.Lay_ds_donvi_theo_huonggiao_id(?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), huongGiaoId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DVGiaoPhieuKN(donViId: Utils.getLongDataByKey(rs,"donvi_id"), tenDV: Utils.getStringDataByKey(rs,"ten_dv"),
                                maDV: Utils.getStringDataByKey(rs,"ma_dv"), chon: Utils.getStringDataByKey(rs,"chon"),
                                sdt: Utils.getStringDataByKey(rs,"so_dt")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsDonViGiao] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsDonViGiao] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<PhieuDaGiaoKN>>> dsPhieuDaGiao(Long phieuKNId) {
        List<PhieuDaGiaoKN> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsPhieuDaGiao] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("phieuKNId", phieuKNId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call qltn.xuly_khieunai_common.Lay_ds_phieu_dagiao_theo_phieu_id(?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), phieuKNId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new PhieuDaGiaoKN(phieuKNId: Utils.getLongDataByKey(rs,"PHIEUKN_ID"), nguoiGiao: Utils.getStringDataByKey(rs,"NGUOIGIAO"),
                                ngayGiao: Utils.getStringDataByKey(rs,"NGAYGIAO"), trangThai: Utils.getStringDataByKey(rs,"TRANGTHAI"),
                                huongGiao: Utils.getStringDataByKey(rs,"HUONGGIAO"), ngayTH: Utils.getLongDataByKey(rs,"NGAY_TH"),
                                nguoiTH: Utils.getStringDataByKey(rs,"NGUOI_TH"), ghiChu: Utils.getStringDataByKey(rs,"GHICHU"),
                                dvNhan: Utils.getStringDataByKey(rs,"DV_NHAN"), ndGiao: Utils.getStringDataByKey(rs,"ND_GIAO"),
                                ndXacMinh: Utils.getStringDataByKey(rs,"ND_XACMINH")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsPhieuDaGiao] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsPhieuDaGiao] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> giaoPhieuPH(GiaoPhieuPHKhieuNaiRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [giaoPhieuPH] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("requestBody", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            Long donViId = Long.parseLong((String) session_.get(ID_DONVI))
            Long nhanVienId = Long.parseLong((String) session_.get(ID_NHANVIEN_DHSX))
            String mayCN = session_.get(DEVICE_ID)
            String nguoiCN = session_.get(USERNAME)
            String mess
            String kq
            List<SMSGiaoPhieuPH> smsList = []
            String dsPhieu = "["
            if (requestBody.getDsPhieu() != null && requestBody.getDsPhieu().size() > 0) {
                for (int i = 0; i < requestBody.getDsPhieu().size(); i++) {
                    if ( i < requestBody.getDsPhieu().size() - 1) {
                        dsPhieu += "{\"khieunai_id\":" + requestBody.getDsPhieu().get(i).getKhieuNaiId() + ",\"donvi_giao_id\":" + donViId + ",\"nd_giao\":\"" + requestBody.getDsPhieu().get(i).getNdGiao() + "\",\"nhanvien_giao_id\":" + nhanVienId + ",\"donvi_nhan_id\":" + requestBody.getDsPhieu().get(i).getDonViNhanId() + ",\"ttkn_id\":3,\"ttph_id\":1,\"huonggiao_id\":" + requestBody.getDsPhieu().get(i).getHuongGiaoId() + ",\"phieu_cha_id\":" + requestBody.getDsPhieu().get(i).getPhieuChaId() + ",\"socongvan\":\"" + requestBody.getDsPhieu().get(i).getSoCV() + "\",\"nguoi_cn\":\"" + nguoiCN + "\",\"may_cn\":\"" + mayCN + "\"},"
                    } else {
                        dsPhieu += "{\"khieunai_id\":" + requestBody.getDsPhieu().get(i).getKhieuNaiId() + ",\"donvi_giao_id\":" + donViId + ",\"nd_giao\":\"" + requestBody.getDsPhieu().get(i).getNdGiao() + "\",\"nhanvien_giao_id\":" + nhanVienId + ",\"donvi_nhan_id\":" + requestBody.getDsPhieu().get(i).getDonViNhanId() + ",\"ttkn_id\":3,\"ttph_id\":1,\"huonggiao_id\":" + requestBody.getDsPhieu().get(i).getHuongGiaoId() + ",\"phieu_cha_id\":" + requestBody.getDsPhieu().get(i).getPhieuChaId() + ",\"socongvan\":\"" + requestBody.getDsPhieu().get(i).getSoCV() + "\",\"nguoi_cn\":\"" + nguoiCN + "\",\"may_cn\":\"" + mayCN + "\"}"
                    }
                }
            }
            dsPhieu += "]"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{call qltn.sp_giaophieu_phoihop_xlkn(?,?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), dsPhieu, Sql.VARCHAR, Sql.VARCHAR, CURSOR_PARAMETER]) { String pkq, String pmess, ResultSet rs ->
                    kq = pkq
                    mess = pmess
                    while (rs != null && rs.next()) {
                        smsList.add(new SMSGiaoPhieuPH(sdt: Utils.getStringDataByKey(rs,"sodt_sms"),
                                khieuNaiId: Utils.getLongDataByKey(rs,"khieunai_id"), noiDung: Utils.getStringDataByKey(rs,"ndsms")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [giaoPhieuPH] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", mess != null ? mess : "").build())
            if (kq != null && kq == 'OK') {
                if (smsList != null && smsList.size() > 0) {
                    String token = session_.get(TOKEN)
                    smsService.init(token)
                    for (SMSGiaoPhieuPH item : smsList) {
                        //smsService.send(item.getNoiDung(), item.getSdt())
                    }
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, mess, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [giaoPhieuPH] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<HoSoKN>>> dsHoSoKN(Long khieuNaiId, String kyHD) {
        List<HoSoKN> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsHoSoKN] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("khieuNaiId", khieuNaiId).put("kyHD", kyHD).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call qltn.XULY_KHIEUNAI.Lay_thongtin_hs_kn(?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), khieuNaiId, kyHD, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new HoSoKN(hoSoId: Utils.getLongDataByKey(rs,"HOSO_ID"), stt: Utils.getStringDataByKey(rs,"STT"),
                                tenFile: Utils.getStringDataByKey(rs,"TENFILE"), serverUrl: Utils.getStringDataByKey(rs,"SERVER_URL"),
                                ipServer: Utils.getStringDataByKey(rs,"IPSERVER"), clientUrl: Utils.getLongDataByKey(rs,"CLIENT_URL"),
                                ngayHs: Utils.getStringDataByKey(rs,"NGAY_HS"), nguoiCn: Utils.getStringDataByKey(rs,"NGUOI_CN")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsHoSoKN] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsHoSoKN] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> cnHoSoKN(CNHoSoKhieuNaiRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [cnHoSoKN] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("requestBody", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String mayCN = session_.get(DEVICE_ID)
            String nguoiCN = session_.get(USERNAME)
            String mess
            String kq
            String dsFile = "["
            if (requestBody.getDsFile() != null && requestBody.getDsFile().size() > 0) {
                for (int i = 0; i < requestBody.getDsFile().size(); i++) {
                    if ( i < requestBody.getDsFile().size() - 1) {
                        dsFile += "{\"khieunai_id\":" + requestBody.getDsFile().get(i).getKhieuNaiId() + ",\"ten_file\":\"" + requestBody.getDsFile().get(i).getTenFile() + "\",\"server_url\":\"" + requestBody.getDsFile().get(i).getServerUrl() + "\",\"hosokn_id\":" + requestBody.getDsFile().get(i).getHoSoKNId() + ",\"ip_server\":\"" + requestBody.getDsFile().get(i).getIpServer() + "\",\"nguoi_cn\":\"" + nguoiCN + "\",\"may_cn\":\"" + mayCN + "\"},"
                    } else {
                        dsFile += "{\"khieunai_id\":" + requestBody.getDsFile().get(i).getKhieuNaiId() + ",\"ten_file\":\"" + requestBody.getDsFile().get(i).getTenFile() + "\",\"server_url\":\"" + requestBody.getDsFile().get(i).getServerUrl() + "\",\"hosokn_id\":" + requestBody.getDsFile().get(i).getHoSoKNId() + ",\"ip_server\":\"" + requestBody.getDsFile().get(i).getIpServer() + "\",\"nguoi_cn\":\"" + nguoiCN + "\",\"may_cn\":\"" + mayCN + "\"}"
                    }
                }
            }
            dsFile += "]"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{call qltn.sp_capnhat_hoso_kn(?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), dsFile, Sql.VARCHAR, Sql.VARCHAR]) { String pkq, String pmess ->
                    kq = pkq
                    mess = pmess
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [cnHoSoKN] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", mess != null ? mess : "").build())
            if (kq != null && kq == 'OK') {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, mess, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [cnHoSoKN] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> xoaHoSoKN(Long hoSoKNId) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [xoaHoSoKN] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("hoSoKNId", hoSoKNId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nguoiCn = session_.get(USERNAME)
            String kq
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{? = call qltn.fn_xoa_hoso_kn(?,?,?) }"
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), hoSoKNId, nguoiCn]) { String pkq ->
                    kq = pkq
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [xoaHoSoKN] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", kq != null ? kq : "").build())
            if (kq != null && kq == 'OK') {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, kq, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [xoaHoSoKN] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<GiaoViecNV>>> dsGiaoViecNV(Long phieuKNId) {
        List<GiaoViecNV> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsGiaoViecNV] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("phieuKNId", phieuKNId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call qltn.sp_lay_ds_giaoviec_xlkn(?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), phieuKNId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new GiaoViecNV(phieuKNId: Utils.getStringDataByKey(rs,"phieukn_id"), ngayGiao: Utils.getStringDataByKey(rs,"ngaygiao"),
                                nhiemmVu: Utils.getStringDataByKey(rs,"nhiemvu"), nhiemVuId: Utils.getStringDataByKey(rs,"nhiemvu_id"),
                                tenDV: Utils.getStringDataByKey(rs,"ten_dv"), tenNV: Utils.getStringDataByKey(rs,"ten_nv"),
                                maNV: Utils.getStringDataByKey(rs,"ma_nv"), nhanVienId: Utils.getStringDataByKey(rs,"nhanvien_id"),
                                soDT: Utils.getStringDataByKey(rs,"so_dt"), maKN: Utils.getStringDataByKey(rs,"ma_kn"),
                                nvGiao: Utils.getStringDataByKey(rs,"nhanvien_giao"), ghiChu: Utils.getStringDataByKey(rs,"ghichu")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsGiaoViecNV] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsGiaoViecNV] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> xoaGiaoViecNV(XoaGiaoViecNVRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [xoaGiaoViecNV] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("requestBody", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String kq
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{? = call qltn.fn_xoa_giaoviec_xlkn(?,?,?,?) }"
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.getPhieuKNId(), requestBody.getNhanVienId(), requestBody.getNhiemVuId()]) { String pkq ->
                    kq = pkq
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [xoaGiaoViecNV] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", kq != null ? kq : "").build())
            if (kq != null && kq == 'OK') {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, kq, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [xoaGiaoViecNV] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<ThangGC>>> dsThangGC(String maTT, String maTB) {
        List<ThangGC> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsThangGC] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("maTT", maTT).put("maTB", maTB).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call qltn.sp_lay_thang_gc_xlkn(?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), maTB, maTT, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new ThangGC(thangGC: Utils.getStringDataByKey(rs,"THANG_GC"), thangGCHT: Utils.getStringDataByKey(rs,"THANG_GCHT")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsThangGC] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsThangGC] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<KyApGC>>> dsKyApGC() {
        List<KyApGC> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [dsKyApGC] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call qltn.sp_lay_kygc_xlkn(?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new KyApGC(kyGC: Utils.getStringDataByKey(rs,"KY_GC"), thangAPHT: Utils.getStringDataByKey(rs,"THANG_APHT")))
                    }
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [dsKyApGC] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [dsKyApGC] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<?>> chuyenNetMedia(ChuyenNetMediaXLKN requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [chuyenNetMedia] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("requestBody", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String donViId = session_.get(ID_DONVI)
            String mayCN = session_.get(DEVICE_ID)
            String nguoiCN = session_.get(USERNAME)
            String kq
            String mess
            String dsPhieu = "["
            if (requestBody.getDsPhieu() != null && requestBody.getDsPhieu().size() > 0) {
                for (int i = 0; i < requestBody.getDsPhieu().size(); i++) {
                    if ( i < requestBody.getDsPhieu().size() - 1) {
                        dsPhieu += "{\"khieunai_id\":" + requestBody.getDsPhieu().get(i).getKhieuNaiId() + ",\"donvi_giao_id\":" + donViId + ",\"nd_giao\":\"" + requestBody.getDsPhieu().get(i).getNdGiao() + "\",\"nhanvien_giao_id\":" + nhanVienId + ",\"nguoi_cn\":\"" + nguoiCN + "\",\"may_cn\":\"" + mayCN + "\",\"socongvan\":\"" + requestBody.getDsPhieu().get(i).getSoCV() + "\",\"phieu_cha_id\":" + requestBody.getDsPhieu().get(i).getPhieuChaId() + ",\"ttkn_id\":3,\"ttph_id\":1,\"donvi_nhan_id\":" + requestBody.getDsPhieu().get(i).getDvNhanId() + ",\"huonggiao_id\":" + requestBody.getDsPhieu().get(i).getHuongGiaoId() + "},"
                    } else {
                        dsPhieu += "{\"khieunai_id\":" + requestBody.getDsPhieu().get(i).getKhieuNaiId() + ",\"donvi_giao_id\":" + donViId + ",\"nd_giao\":\"" + requestBody.getDsPhieu().get(i).getNdGiao() + "\",\"nhanvien_giao_id\":" + nhanVienId + ",\"nguoi_cn\":\"" + nguoiCN + "\",\"may_cn\":\"" + mayCN + "\",\"socongvan\":\"" + requestBody.getDsPhieu().get(i).getSoCV() + "\",\"phieu_cha_id\":" + requestBody.getDsPhieu().get(i).getPhieuChaId() + ",\"ttkn_id\":3,\"ttph_id\":1,\"donvi_nhan_id\":" + requestBody.getDsPhieu().get(i).getDvNhanId() + ",\"huonggiao_id\":" + requestBody.getDsPhieu().get(i).getHuongGiaoId() + "}"
                    }
                }
            }
            dsPhieu += "]"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{call qltn.sp_chuyen_net_media_xlkn(?,?,?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), requestBody.getMaTB(), requestBody.getKieu(), dsPhieu, Sql.VARCHAR, Sql.VARCHAR]) { String pkq, String pMess ->
                    kq = pkq
                    mess = pMess
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [chuyenNetMedia] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", kq != null ? kq : "").build())
            if (kq != null && kq == 'OK') {
                String str = "select tentat from css.tinh where tinh_id=?"
                GroovyRowResult dtTinhBanCheo = null
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    dtTinhBanCheo = sql.firstRow(dataUtils.replaceSchema(str), [phanVungId])
                }
                if (dtTinhBanCheo != null && !dtTinhBanCheo.isEmpty()) {
                    String matinhBanCheo = dtTinhBanCheo.get("tentat").toString().toUpperCase()
                    String maTinhBan = matinhBanCheo
                    if (matinhBanCheo == "KHDN") maTinhBan = "dbn"
                    List<PhieuPhoiHopKHDN> ret = []
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        String sqlString = "{ call qltn.lay_ds_phieu_phoihop_khdn(?,?,?,?,?) }"
                        sql.call(sqlString, [Long.parseLong(phanVungId), requestBody.getPhieuKNId(), Long.parseLong(phanVungId), maTinhBan, CURSOR_PARAMETER]) { ResultSet rs ->
                            if (rs != null) {
                                ret = resultSetToObject.getDanhSach(rs, PhieuPhoiHopKHDN.class)
                            }
                        }
                    }
                    if (ret != null && ret.size() > 0) {
                        String dsTT_KHDN = "["
                        if (ret != null && ret.size() > 0) {
                            for (int i = 0; i < ret.size(); i++) {
                                if ( i < ret.size() - 1) {
                                    if (requestBody.getKieu() == 1) {
                                        dsTT_KHDN += "{\"TINH_ID\":" + ret.get(i).getTINH_ID() + ",\"BAOHONG_TINH_ID\":" + ret.get(i).getKHIEUNAI_TINH_ID() + ",\"MA_TB\":\"" + ret.get(i).getMA_TB() + "\",\"PHIEU_TINH_ID\":" + ret.get(i).getPHIEU_TINH_ID() + ",\"HUONGGIAO_ID\":" + ret.get(i).getHUONGGIAO_ID() + ",\"DICHVUVT_ID\":" + ret.get(i).getDICHVUVT_ID() + ",\"DICHVU\":\"" + ret.get(i).getDICHVU() + "\",\"LOAITB_ID\":" + ret.get(i).getLOAITB_ID() + ",\"LOAIHINH\":\"" + ret.get(i).getLOAIHINH() + "\",\"DIACHI_TB\":\"" + ret.get(i).getDIACHI_LD() + "\",\"DIACHI_LD\":\"" + ret.get(i).getDIACHI_LD() + "\",\"DOITUONG_TB\":\"" + ret.get(i).getDOITUONG_TB() + "\",\"TINH_LD\":\"" + ret.get(i).getTINH_LD() + "\",\"NGAY_BH\":\"" + ret.get(i).getNGAY_BN() + "\",\"TEN_KH\":\"" + ret.get(i).getTEN_KH() + "\",\"DIACHI_KH\":\"" + ret.get(i).getDIACHI_KH() + "\",\"SO_DT_BH\":\"" + ret.get(i).getSO_DT_KN() + "\",\"LOAI_KH\":\"" + ret.get(i).getLOAI_KH() + "\",\"NGUOIGIAO\":\"" + ret.get(i).getNGUOIGIAO() + "\",\"NGAYGIAO\":\"" + ret.get(i).getNGAYGIAO() + "\",\"DV_GIAO\":\"" + ret.get(i).getDV_GIAO() + "\",\"SO_DT_GIAO\":\"" + ret.get(i).getSO_DT_GIAO() + "\",\"LOG_GIAO\":\"" + ret.get(i).getLOG_GIAO() + "\",\"ND_GIAO\":\"" + ret.get(i).getND_GIAO() + "\",\"NGUOI_BH\":\"" + ret.get(i).getNGUOI_KN() + "\",\"TEN_TB\":\"" + ret.get(i).getTEN_TB() + "\",\"TINHTRANG_BH\":\"" + ret.get(i).getTINHTRANG_KN() + "\",\"MA_LT\":\"" + ret.get(i).getMA_LT() + "\",\"THOIGIAN_CK\":\"" + ret.get(i).getTHOIGIAN_CK() + "\",\"HENSUATU\":\"" + ret.get(i).getHENGQ_TU() + "\",\"HENSUADEN\":\"" + ret.get(i).getHENGQ_DEN() + "\",\"NGAY_SD\":\"" + ret.get(i).getNGAY_SD() + "\",\"GHICHU_HONG\":\"" + ret.get(i).getGHICHU_KN() + "\",\"MA_BH\":\"" + ret.get(i).getMA_KN() + "\",\"TU_OMC\":\"" + "" + "\",\"THUEBAO_ID\":" + ret.get(i).getTHUEBAO_ID() + ",\"TTPH_ID\":" + ret.get(i).getTTPH_ID() + ",\"NET_ID\":" + ret.get(i).getNET_ID() +"},"
                                    } else if (requestBody.getKieu() == 2) {
                                        dsTT_KHDN += "{\"TINH_ID\":" + ret.get(i).getTINH_ID() + ",\"BAOHONG_TINH_ID\":" + ret.get(i).getKHIEUNAI_TINH_ID() + ",\"MA_TB\":\"" + ret.get(i).getMA_TB() + "\",\"PHIEU_TINH_ID\":" + ret.get(i).getPHIEU_TINH_ID() + ",\"HUONGGIAO_ID\":" + ret.get(i).getHUONGGIAO_ID() + ",\"DICHVUVT_ID\":" + ret.get(i).getDICHVUVT_ID() + ",\"DICHVU\":\"" + ret.get(i).getDICHVU() + "\",\"LOAITB_ID\":" + ret.get(i).getLOAITB_ID() + ",\"LOAIHINH\":\"" + ret.get(i).getLOAIHINH() + "\",\"DIACHI_TB\":\"" + ret.get(i).getDIACHI_LD() + "\",\"DIACHI_LD\":\"" + ret.get(i).getDIACHI_LD() + "\",\"DOITUONG_TB\":\"" + ret.get(i).getDOITUONG_TB() + "\",\"TINH_LD\":\"" + ret.get(i).getTINH_LD() + "\",\"NGAY_BH\":\"" + ret.get(i).getNGAY_BN() + "\",\"TEN_KH\":\"" + ret.get(i).getTEN_KH() + "\",\"DIACHI_KH\":\"" + ret.get(i).getDIACHI_KH() + "\",\"SO_DT_BH\":\"" + ret.get(i).getSO_DT_KN() + "\",\"LOAI_KH\":\"" + ret.get(i).getLOAI_KH() + "\",\"NGUOIGIAO\":\"" + ret.get(i).getNGUOIGIAO() + "\",\"NGAYGIAO\":\"" + ret.get(i).getNGAYGIAO() + "\",\"DV_GIAO\":\"" + ret.get(i).getDV_GIAO() + "\",\"SO_DT_GIAO\":\"" + ret.get(i).getSO_DT_GIAO() + "\",\"LOG_GIAO\":\"" + ret.get(i).getLOG_GIAO() + "\",\"ND_GIAO\":\"" + ret.get(i).getND_GIAO() + "\",\"NGUOI_BH\":\"" + ret.get(i).getNGUOI_KN() + "\",\"TEN_TB\":\"" + ret.get(i).getTEN_TB() + "\",\"TINHTRANG_BH\":\"" + ret.get(i).getTINHTRANG_KN() + "\",\"MA_LT\":\"" + ret.get(i).getMA_LT() + "\",\"THOIGIAN_CK\":\"" + ret.get(i).getTHOIGIAN_CK() + "\",\"HENSUATU\":\"" + ret.get(i).getHENGQ_TU() + "\",\"HENSUADEN\":\"" + ret.get(i).getHENGQ_DEN() + "\",\"NGAY_SD\":\"" + ret.get(i).getNGAY_SD() + "\",\"GHICHU_HONG\":\"" + ret.get(i).getGHICHU_KN() + "\",\"MA_BH\":\"" + ret.get(i).getMA_KN() + "\",\"TU_OMC\":\"" + "" + "\",\"THUEBAO_ID\":" + ret.get(i).getTHUEBAO_ID() + ",\"TTPH_ID\":" + ret.get(i).getTTPH_ID() + ",\"MEDIA_ID\":" + ret.get(i).getMEDIA_ID() +"},"
                                    }
                                } else {
                                    if (requestBody.getKieu() == 1) {
                                        dsTT_KHDN += "{\"TINH_ID\":" + ret.get(i).getTINH_ID() + ",\"BAOHONG_TINH_ID\":" + ret.get(i).getKHIEUNAI_TINH_ID() + ",\"MA_TB\":\"" + ret.get(i).getMA_TB() + "\",\"PHIEU_TINH_ID\":" + ret.get(i).getPHIEU_TINH_ID() + ",\"HUONGGIAO_ID\":" + ret.get(i).getHUONGGIAO_ID() + ",\"DICHVUVT_ID\":" + ret.get(i).getDICHVUVT_ID() + ",\"DICHVU\":\"" + ret.get(i).getDICHVU() + "\",\"LOAITB_ID\":" + ret.get(i).getLOAITB_ID() + ",\"LOAIHINH\":\"" + ret.get(i).getLOAIHINH() + "\",\"DIACHI_TB\":\"" + ret.get(i).getDIACHI_LD() + "\",\"DIACHI_LD\":\"" + ret.get(i).getDIACHI_LD() + "\",\"DOITUONG_TB\":\"" + ret.get(i).getDOITUONG_TB() + "\",\"TINH_LD\":\"" + ret.get(i).getTINH_LD() + "\",\"NGAY_BH\":\"" + ret.get(i).getNGAY_BN() + "\",\"TEN_KH\":\"" + ret.get(i).getTEN_KH() + "\",\"DIACHI_KH\":\"" + ret.get(i).getDIACHI_KH() + "\",\"SO_DT_BH\":\"" + ret.get(i).getSO_DT_KN() + "\",\"LOAI_KH\":\"" + ret.get(i).getLOAI_KH() + "\",\"NGUOIGIAO\":\"" + ret.get(i).getNGUOIGIAO() + "\",\"NGAYGIAO\":\"" + ret.get(i).getNGAYGIAO() + "\",\"DV_GIAO\":\"" + ret.get(i).getDV_GIAO() + "\",\"SO_DT_GIAO\":\"" + ret.get(i).getSO_DT_GIAO() + "\",\"LOG_GIAO\":\"" + ret.get(i).getLOG_GIAO() + "\",\"ND_GIAO\":\"" + ret.get(i).getND_GIAO() + "\",\"NGUOI_BH\":\"" + ret.get(i).getNGUOI_KN() + "\",\"TEN_TB\":\"" + ret.get(i).getTEN_TB() + "\",\"TINHTRANG_BH\":\"" + ret.get(i).getTINHTRANG_KN() + "\",\"MA_LT\":\"" + ret.get(i).getMA_LT() + "\",\"THOIGIAN_CK\":\"" + ret.get(i).getTHOIGIAN_CK() + "\",\"HENSUATU\":\"" + ret.get(i).getHENGQ_TU() + "\",\"HENSUADEN\":\"" + ret.get(i).getHENGQ_DEN() + "\",\"NGAY_SD\":\"" + ret.get(i).getNGAY_SD() + "\",\"GHICHU_HONG\":\"" + ret.get(i).getGHICHU_KN() + "\",\"MA_BH\":\"" + ret.get(i).getMA_KN() + "\",\"TU_OMC\":\"" + "" + "\",\"THUEBAO_ID\":" + ret.get(i).getTHUEBAO_ID() + ",\"TTPH_ID\":" + ret.get(i).getTTPH_ID() + ",\"NET_ID\":" + ret.get(i).getNET_ID() +"}"
                                    } else if (requestBody.getKieu() == 2) {
                                        dsTT_KHDN += "{\"TINH_ID\":" + ret.get(i).getTINH_ID() + ",\"BAOHONG_TINH_ID\":" + ret.get(i).getKHIEUNAI_TINH_ID() + ",\"MA_TB\":\"" + ret.get(i).getMA_TB() + "\",\"PHIEU_TINH_ID\":" + ret.get(i).getPHIEU_TINH_ID() + ",\"HUONGGIAO_ID\":" + ret.get(i).getHUONGGIAO_ID() + ",\"DICHVUVT_ID\":" + ret.get(i).getDICHVUVT_ID() + ",\"DICHVU\":\"" + ret.get(i).getDICHVU() + "\",\"LOAITB_ID\":" + ret.get(i).getLOAITB_ID() + ",\"LOAIHINH\":\"" + ret.get(i).getLOAIHINH() + "\",\"DIACHI_TB\":\"" + ret.get(i).getDIACHI_LD() + "\",\"DIACHI_LD\":\"" + ret.get(i).getDIACHI_LD() + "\",\"DOITUONG_TB\":\"" + ret.get(i).getDOITUONG_TB() + "\",\"TINH_LD\":\"" + ret.get(i).getTINH_LD() + "\",\"NGAY_BH\":\"" + ret.get(i).getNGAY_BN() + "\",\"TEN_KH\":\"" + ret.get(i).getTEN_KH() + "\",\"DIACHI_KH\":\"" + ret.get(i).getDIACHI_KH() + "\",\"SO_DT_BH\":\"" + ret.get(i).getSO_DT_KN() + "\",\"LOAI_KH\":\"" + ret.get(i).getLOAI_KH() + "\",\"NGUOIGIAO\":\"" + ret.get(i).getNGUOIGIAO() + "\",\"NGAYGIAO\":\"" + ret.get(i).getNGAYGIAO() + "\",\"DV_GIAO\":\"" + ret.get(i).getDV_GIAO() + "\",\"SO_DT_GIAO\":\"" + ret.get(i).getSO_DT_GIAO() + "\",\"LOG_GIAO\":\"" + ret.get(i).getLOG_GIAO() + "\",\"ND_GIAO\":\"" + ret.get(i).getND_GIAO() + "\",\"NGUOI_BH\":\"" + ret.get(i).getNGUOI_KN() + "\",\"TEN_TB\":\"" + ret.get(i).getTEN_TB() + "\",\"TINHTRANG_BH\":\"" + ret.get(i).getTINHTRANG_KN() + "\",\"MA_LT\":\"" + ret.get(i).getMA_LT() + "\",\"THOIGIAN_CK\":\"" + ret.get(i).getTHOIGIAN_CK() + "\",\"HENSUATU\":\"" + ret.get(i).getHENGQ_TU() + "\",\"HENSUADEN\":\"" + ret.get(i).getHENGQ_DEN() + "\",\"NGAY_SD\":\"" + ret.get(i).getNGAY_SD() + "\",\"GHICHU_HONG\":\"" + ret.get(i).getGHICHU_KN() + "\",\"MA_BH\":\"" + ret.get(i).getMA_KN() + "\",\"TU_OMC\":\"" + "" + "\",\"THUEBAO_ID\":" + ret.get(i).getTHUEBAO_ID() + ",\"TTPH_ID\":" + ret.get(i).getTTPH_ID() + ",\"MEDIA_ID\":" + ret.get(i).getMEDIA_ID() +"}"
                                    }
                                }
                            }
                        }
                        dsTT_KHDN += "]"
                        Sql.newInstance(oracleManaged.getDataSource("css_khdn")).withCloseable { sql ->
                            String sqlString = "{ ? = call capnhat_giaophieu_ph(?,?) }"
                            sql.call(sqlString, [Sql.VARCHAR, dsTT_KHDN, requestBody.getKieu()]) { String rs ->
                                kq = rs
                            }
                        }
                        if (kq != null && kq.toUpperCase() == "OK") {
                            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
                        } else {
                            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, kq, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                        }
                    } else {
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không tìm thấy giao phiếu phối hợp KHDN!", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                    }
                } else {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không tìm thấy thông tin tỉnh bán!", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(kq, requestId, mess, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [chuyenNetMedia] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> huyGiaoViec(Long phieuKNId) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [huyGiaoViec] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("phieuKNId", phieuKNId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String token = session_.get(TOKEN)
            String kq
            String ndSMS
            String sdtSMS
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{? = call qltn.fn_huy_giaoviec_xlkn(?,?,?,?) }"
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), phieuKNId, Sql.VARCHAR, Sql.VARCHAR]) { String pkq, String pNdSMS, pSdtSMS ->
                    kq = pkq
                    ndSMS = pNdSMS
                    sdtSMS = pSdtSMS
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [huyGiaoViec] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", kq != null ? kq : "").build())
            if (kq != null && kq == 'OK') {
                if (ndSMS != null && ndSMS.trim().length() > 0) {
                    smsService.init(token)
                    //smsService.send(ndSms, sdtSms)
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, kq, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [huyGiaoViec] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> huyGiaoPhieu(Long phieuKNId) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [huyGiaoPhieu] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("phieuKNId", phieuKNId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String kq
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{? = call qltn.fn_huy_giaophieu_xlkn(?,?,?) }"
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), phieuKNId, Long.parseLong(nhanVienId)]) { String pkq ->
                    kq = pkq
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [huyGiaoPhieu] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", kq != null ? kq : "").build())
            if (kq != null && kq == 'OK') {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, kq, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [huyGiaoPhieu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> huyGiaoPhieuPH(Long phieuKNId) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [huyGiaoPhieuPH] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("phieuKNId", phieuKNId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String tinhId = session_.get(ID_TINHTHANH)
            String kq
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{? = call qltn.fn_huy_giaophieu_ph_xlkn(?,?,?) }"
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), phieuKNId, Long.parseLong(nhanVienId)]) { String pkq ->
                    kq = pkq
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [huyGiaoPhieuPH] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", kq != null ? kq : "").build())
            if (kq != null && kq == 'OK') {
                Sql.newInstance(oracleManaged.getDataSource("css_khdn")).withCloseable { sql ->
                    String sqlString = "{ ? = call QLTN_DBN.huy_giaophieu_ph(?,?) }"
                    sql.call(sqlString, [Sql.VARCHAR, phieuKNId, Long.parseLong(tinhId)]) { String rs ->
                        kq = rs
                    }
                }
                if (kq != null && kq.toUpperCase() == "OK") {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
                } else {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, kq, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, kq, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [huyGiaoPhieuPH] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> capNhatLoiNVXLKN(CapNhatLoiNVXLKN requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[XuLyKhieuNaiController] - [capNhatLoiNVXLKN] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("requestBody", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String kq
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ ? = call qltn.fn_capnhat_loi_nhanvien_xlkn(?,?,?,?,?) }"
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.getKhieuNaiId(), requestBody.getIds(), requestBody.getNoiDung(), requestBody.getLoai()]) { String pkq ->
                    kq = pkq
                }
            }
            loggingManaged.info("[XuLyKhieuNaiController] - [capNhatLoiNVXLKN] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", kq != null ? kq : "").build())
            if (kq != null && kq == 'OK') {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, kq, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[XuLyKhieuNaiController] - [capNhatLoiNVXLKN] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

}
