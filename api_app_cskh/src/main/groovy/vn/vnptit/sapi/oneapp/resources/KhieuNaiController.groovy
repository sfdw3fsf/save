package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import groovy.sql.GroovyRowResult
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.api.KhieuNaiApi
import io.swagger.model.dto.*
import io.swagger.model.request.*
import io.swagger.model.response.BasePageResponse
import io.swagger.model.response.BaseResponse
import io.swagger.model.response.PageInfo
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
class KhieuNaiController implements KhieuNaiApi {

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
    DataUtils dataUtils

    @Autowired
    KhieuNaiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }

    @Override
    ResponseEntity<BaseResponse<List<KhieuNai>>> dsKhieuNai(String trangThai) {
        List<KhieuNai> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [dsKhieuNai] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("trangThai", trangThai)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(ID_TINHTHANH)

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String totruongkhieunai = "0"
                String sqlString = "{ call CSS.sp_smp_lay_thongtin_nd(?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), nhanVienId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs.next()) {
                        totruongkhieunai = Utils.getStringDataByKey(rs,"totruongkhieunai")
                    }
                }

                SimpleDateFormat df = new SimpleDateFormat("MMyyyy")
                String kyhoadon = df.format(new Date())

                sqlString = "{ call QLTN.lay_ds_khieunai_theo_trangthai_pt(?,?,?,?,?,?,?,?,?,?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), donViId, nhanVienId, 3, "2254,20135", kyhoadon, trangThai, totruongkhieunai, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new KhieuNai(
                                phanVungId: Utils.getStringDataByKey(rs,"phanvung_id"),
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
                                dvn: Utils.getStringDataByKey(rs,"dvn"),
                                nhanVienGq: Utils.getStringDataByKey(rs,"nhanvien_gq"),
                                ngayGiao: Utils.getStringDataByKey(rs,"ngaygiao"),
                                ndTraCon: Utils.getStringDataByKey(rs,"nd_tra_con"),
                                donViTraConId: Utils.getStringDataByKey(rs,"donvitra_con_id"),
                                tenLoai: Utils.getStringDataByKey(rs,"ten_loai"),
                                isHoanThanh: Utils.getStringDataByKey(rs,"ishoanthanh")
                        ))
                    }
                }
            }
            loggingManaged.info("[KhieuNaiController] - [dsKhieuNai] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [dsKhieuNai] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BasePageResponse<List<KhieuNai>, PageInfo>> dsKhieuNaiPT(String trangThai, Integer pageNum, Integer pageRec, Long totalRow) {
        List<KhieuNai> ret = []
        PageInfo pageInfo = new PageInfo()
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [dsKhieuNai] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("trangThai", trangThai)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(ID_TINHTHANH)

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String totruongkhieunai = "0"
                String sqlString = "{ call CSS.sp_smp_lay_thongtin_nd(?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), nhanVienId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs.next()) {
                        totruongkhieunai = Utils.getStringDataByKey(rs,"totruongkhieunai")
                    }
                }

                SimpleDateFormat df = new SimpleDateFormat("MMyyyy")
                String kyhoadon = df.format(new Date())

                sqlString = "{ call QLTN.lay_ds_khieunai_theo_trangthai_pt(?,?,?,?,?,?,?,?,?,?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), donViId, nhanVienId, 3, "2254,20135", kyhoadon, trangThai, totruongkhieunai, pageNum, pageRec, totalRow, CURSOR_PARAMETER, CURSOR_PARAMETER]) { ResultSet rs, ResultSet rsPage ->
                    while (rs.next()) {
                        ret.add(new KhieuNai(
                                phanVungId: Utils.getStringDataByKey(rs,"phanvung_id"),
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
                                dvn: Utils.getStringDataByKey(rs,"dvn"),
                                nhanVienGq: Utils.getStringDataByKey(rs,"nhanvien_gq"),
                                ngayGiao: Utils.getStringDataByKey(rs,"ngaygiao"),
                                ndTraCon: Utils.getStringDataByKey(rs,"nd_tra_con"),
                                donViTraConId: Utils.getStringDataByKey(rs,"donvitra_con_id"),
                                tenLoai: Utils.getStringDataByKey(rs,"ten_loai"),
                                isHoanThanh: Utils.getStringDataByKey(rs,"ishoanthanh")
                        ))
                    }
                    if (rsPage != null && rsPage.next()) {
                        pageInfo.setPageNum(rsPage.getInt("page_num"))
                        pageInfo.setPageSize(rsPage.getInt("page_size"))
                        pageInfo.setRecordCount(rsPage.getLong("record_count"))
                    }
                }
            }
            loggingManaged.info("[KhieuNaiController] - [dsKhieuNai] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret, pageInfo))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [dsKhieuNai] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsKetQuaXL() {
        List<DanhMuc> ret
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [dsKetQuaXL] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .build())

            ret = [
                    new DanhMuc(
                            ID: 1,
                            NAME: "Xử lý được"
                    ),
                    new DanhMuc(
                            ID: 2,
                            NAME: "Không xử lý được"
                    )
            ]

            loggingManaged.info("[KhieuNaiController] - [dsKetQuaXL] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [dsKetQuaXL] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsNhanVien() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [dsNhanVien] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(ID_TINHTHANH)

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
               String sqlString = "{ call QLTN.lay_ds_nv_xuly_kn(?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), nhanVienId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(
                                ID: Utils.getLongDataByKey(rs,"nhanvien_id"),
                                NAME: Utils.getStringDataByKey(rs,"name")
                        ))
                    }
                }
            }
            loggingManaged.info("[KhieuNaiController] - [dsNhanVien] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [dsNhanVien] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsPhuongAnXL() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [dsPhuongAnXL] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "Select PAXL_ID, PHUONGAN_XLKN from QLTN.phuongan_xlkn WHERE PHANVUNG_ID = ?"
                sqlString = dataUtils.replaceSchema(dataSrc, sqlString)
                def rs = sql.eachRow(sqlString, [Long.parseLong(phanVungId)]){rs ->
                    ret.add(new DanhMuc(
                            ID: Utils.getLongDataByKey(rs,"PAXL_ID"),
                            NAME: Utils.getStringDataByKey(rs,"PHUONGAN_XLKN")
                    ))
                }
            }
            loggingManaged.info("[KhieuNaiController] - [dsPhuongAnXL] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [dsPhuongAnXL] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsKetQuaLoi() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [dsKetQuaLoi] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "Select distinct * From QLTN.ketqua_kn Where loai = 2 Order by ketquakn_id"
                sqlString = dataUtils.replaceSchema(dataSrc, sqlString)
                def rs = sql.eachRow(sqlString){rs ->
                    ret.add(new DanhMuc(
                            ID: Utils.getLongDataByKey(rs,"KETQUAKN_ID"),
                            NAME: Utils.getStringDataByKey(rs,"NOIDUNG")
                    ))
                }
            }
            loggingManaged.info("[KhieuNaiController] - [dsKetQuaLoi] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [dsKetQuaLoi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsHTTraLoi() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [dsHTTraLoi] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "Select * from QLTN.HINHTHUC_TL"
                sqlString = dataUtils.replaceSchema(dataSrc, sqlString)
                def rs = sql.eachRow(sqlString){rs ->
                    ret.add(new DanhMuc(
                            ID: Utils.getLongDataByKey(rs,"HTTL_ID"),
                            NAME: Utils.getStringDataByKey(rs,"HINHTHUC_TL")
                    ))
                }
            }
            loggingManaged.info("[KhieuNaiController] - [dsHTTraLoi] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [dsHTTraLoi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DonViTree>>> dsDonViChuyenTo() {
        List<DonViTree> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [dsDonViChuyenTo] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(ID_TINHTHANH)

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ CALL CSS.WEBAPI_EX.POST_LIST_V2(?,?,?,?,?,?,?,?) }"
                def rs = sql.call(sqlString, [Long.parseLong(phanVungId), 'TO_KYTHUAT_TREE', null, null, null, null, null, CURSOR_PARAMETER]){ResultSet rs ->
                    while (rs.next()){
                        ret.add(new DonViTree(
                                donViId: Utils.getLongDataByKey(rs,"ID"),
                                tenDv: Utils.getStringDataByKey(rs,"NAME"),
                                donViChaId: Utils.getLongDataByKey(rs,"PARENTID"),
                                unitLevel: rs.getInt("UNITLEVEL")
                        ))
                    }
                }
            }
            loggingManaged.info("[KhieuNaiController] - [dsDonViChuyenTo] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [dsDonViChuyenTo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> chuyenTo(ChuyenToKNRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [chuyenTo] - Request: ", new ImmutableMap.Builder<String, Object>().
                    put("request_id", requestId).put("body", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String donViId = session_.get(ID_DONVI)
            String maNV = session_.get(MA_NV)
            String mayCN = session_.get(DEVICE_ID)
            String nguoiCn = session_.get(USERNAME)
            GroovyRowResult row = null
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "SELECT * FROM ADMIN.DONVI_LDV WHERE LOAIDV_ID = 31 AND DONVI_ID = ?"
                sqlString = dataUtils.replaceSchema(dataSrc, sqlString)
                row = sql.firstRow(sqlString, [requestBody.donViNhanId])
            }
            if (row == null || row.isEmpty())
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Đơn vị nhận không có quyền xác minh khiếu nại", ErrorCodeDefine.INTERNAL_SERVER_ERROR))

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ CALL QLTN.Mobile_chuyento_xacminh_khieunai(?,?,?,?,?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), requestBody.khieuNaiId, requestBody.phieuKNId, requestBody.donViNhanId,
                                     nhanVienId, requestBody.noiDung, mayCN, nguoiCn]) {}
            }

            loggingManaged.info("[KhieuNaiController] - [chuyenTo] - Response: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [chuyenTo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> khoaPhieu(XacMinhKNRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [khoaPhieu] - Request: ", new ImmutableMap.Builder<String, Object>().
                    put("request_id", requestId).put("body", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String donViId = session_.get(ID_DONVI)
            String maNV = session_.get(MA_NV)
            String mayCN = session_.get(DEVICE_ID)
            String nguoiCn = session_.get(MA_NV)
            GroovyRowResult row = null
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "select count(*) count from QLTN.giaophieu_kn a where a.phanvung_id = ? and a.phieu_cha_id = ? and a.ttph_id in (1,2)"
                sqlString = dataUtils.replaceSchema(dataSrc, sqlString)
                row = sql.firstRow(sqlString, [Long.parseLong(phanVungId), requestBody.phieuKNId])
            }
            if (row != null && !row.isEmpty() && Long.parseLong(row.get("count").toString()) > 0)
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Vẫn còn phiếu con chưa thực hiện nên Bạn chưa được phép Hoàn thành!", ErrorCodeDefine.INTERNAL_SERVER_ERROR))

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ CALL QLTN.XULY_KHIEUNAI.Capnhat_hths_khieunai(?,?,?,?,sysdate,?,?,?,?,?,sysdate) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), requestBody.khieuNaiId, requestBody.phieuKNId, requestBody.nvTHId,
                                     mayCN, nguoiCn, 6, requestBody.nguyenNhan, requestBody.noiDungTL]) {}
            }

            loggingManaged.info("[KhieuNaiController] - [khoaPhieu] - Response: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [khoaPhieu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> hoanThanh(XacMinhKNRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [hoanThanh] - Request: ", new ImmutableMap.Builder<String, Object>().
                    put("request_id", requestId).put("body", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String donViId = session_.get(ID_DONVI)
            String maNV = session_.get(MA_NV)
            String mayCN = session_.get(DEVICE_ID)
            String nguoiCn = session_.get(MA_NV)
            String ipCn = session_.get(DEVICE_IP)
            String ma_tinh = session_.get(MA_TINH)
            String kq = ""
            String msg = ""
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ CALL qltn.sp_hoanthien_xacminhkn(?,?,?,?,?,?,?,?,?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.khieuNaiId,
                                     requestBody.phieuKNId,
                                     nhanVienId,
                                     requestBody.nvTHId,
                                     requestBody.hinhThuctlId,
                                     requestBody.nguyenNhan,
                                     requestBody.noiDungTL,
                                     requestBody.noiDungXM,
                                     requestBody.maCongVan,
                                     Sql.VARCHAR, Sql.VARCHAR]) {String k, String m -> kq = k; msg = m}
            }

            loggingManaged.info("[KhieuNaiController] - [hoanThanh] - Response: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            if (kq != null && kq == 'OK') {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, msg, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [hoanThanh] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    private boolean PhaiLam(int luong_id, String code, String dataSrc)
    {
        try
        {
            String sqlString = "select a.thaotac_id, b.thaotac, a.enable, b.code " +
                    "from css.luong_thaotac a, css.thaotac b " +
                    "where a.thaotac_id = b.thaotac_id and a.luong_id = ? " +
                    "and code='?' and enable=1"
            sqlString = dataUtils.replaceSchema(dataSrc, sqlString)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                def ret = sql.firstRow(sqlString, [luong_id, code])
                return !ret.isEmpty()
            }
        }
        catch(Exception ignored){
            return false
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsLoaiPhieu() {
        List<DanhMuc> ret
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [dsLoaiPhieu] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .build())

            ret = [
                    new DanhMuc(
                            ID: 1,
                            NAME: "Đã tiếp nhận"
                    ),
                    new DanhMuc(
                            ID: 2,
                            NAME: "Đã giao đi"
                    ),
                    new DanhMuc(
                            ID: 4,
                            NAME: "Xác minh xong"
                    )
            ]

            loggingManaged.info("[KhieuNaiController] - [dsLoaiPhieu] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [dsLoaiPhieu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<PhieuKhieuNai>>> dsPhieu(String trangThai) {
        List<PhieuKhieuNai> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [dsPhieu] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(ID_TINHTHANH)
            SimpleDateFormat df = new SimpleDateFormat("MMyyyy")
            String kyhoadon = df.format(new Date())
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ CALL QLTN.lay_ds_tb_phkh_v2(?,?,?,?,?,?,?,?) }"
                def rs = sql.call(sqlString, [Long.parseLong(phanVungId), donViId, nhanVienId, 1, 0, kyhoadon, trangThai, CURSOR_PARAMETER]){ResultSet rs ->
                    while (rs.next()){
                        ret.add(new PhieuKhieuNai(
                                stt: Utils.getStringDataByKey(rs,"stt"),
                                phanVungId: Utils.getStringDataByKey(rs,"phanvung_id"),
                                khieuNaiId: Utils.getStringDataByKey(rs,"khieunai_id"),
                                thueBaoId: Utils.getStringDataByKey(rs,"thuebao_id"),
                                dichVuVTId: Utils.getStringDataByKey(rs,"dichvuvt_id"),
                                loaiTBId: Utils.getStringDataByKey(rs,"loaitb_id"),
                                ngayTn: Utils.getStringDataByKey(rs,"ngay_tn"),
                                nguoiKn: Utils.getStringDataByKey(rs,"nguoi_kn"),
                                dienThoaiLh: Utils.getStringDataByKey(rs,"dienthoai_lh"),
                                chuDeknId: Utils.getStringDataByKey(rs,"chudekn_id"),
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
                                htknId: Utils.getStringDataByKey(rs,"htkn_id"),
                                nguyenNhanKn: Utils.getStringDataByKey(rs,"nguyennhan_kn"),
                                diaChiLh: Utils.getStringDataByKey(rs,"diachi_lh"),
                                maTb: Utils.getStringDataByKey(rs,"ma_tb"),
                                tienGc: Utils.getStringDataByKey(rs,"tien_gc"),
                                vatGc: Utils.getStringDataByKey(rs,"vat_gc"),
                                quyTrinhId: Utils.getStringDataByKey(rs,"quytrinh_id"),
                                ketQuaknId: Utils.getStringDataByKey(rs,"ketquakn_id"),
                                thanhToanId: Utils.getStringDataByKey(rs,"thanhtoan_id"),
                                maKn: Utils.getStringDataByKey(rs,"ma_kn"),
                                giamCuocDt: Utils.getStringDataByKey(rs,"giamcuoc_dt"),
                                giamCuocCp: Utils.getStringDataByKey(rs,"giamcuoc_cp"),
                                tienTrinhknId: Utils.getStringDataByKey(rs,"tientrinhkn_id"),
                                vatDt: Utils.getStringDataByKey(rs,"vat_dt"),
                                vatCp: Utils.getStringDataByKey(rs,"vat_cp"),
                                kyKn: Utils.getStringDataByKey(rs,"ky_kn"),
                                lydoHuygc: Utils.getStringDataByKey(rs,"lydo_huygc"),
                                ngayHuy: Utils.getStringDataByKey(rs,"ngayhuy"),
                                nguoiHuy: Utils.getStringDataByKey(rs,"nguoihuy"),
                                capDoxlId: Utils.getStringDataByKey(rs,"capdoxl_id"),
                                chiTieuTg: Utils.getStringDataByKey(rs,"chitieu_tg"),
                                thoiGianCk: Utils.getStringDataByKey(rs,"thoigian_ck"),
                                phanAnhId: Utils.getStringDataByKey(rs,"phananh_id"),
                                blacklistId: Utils.getStringDataByKey(rs,"blacklist_id"),
                                baoHongId: Utils.getStringDataByKey(rs,"baohong_id"),
                                thoiGianTn: Utils.getStringDataByKey(rs,"thoigian_tn"),
                                loaihdId: Utils.getStringDataByKey(rs,"loaihd_id"),
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
                                phieuknId: Utils.getStringDataByKey(rs,"phieukn_id"),
                                ndGiao: Utils.getStringDataByKey(rs,"nd_giao"),
                                ngayHtGp: Utils.getStringDataByKey(rs,"ngay_ht_gp"),
                                ngayCapcmt: Utils.getStringDataByKey(rs,"ngaycapcmt"),
                                lyDoTon: Utils.getStringDataByKey(rs,"lydo_ton"),
                                nhanVienThId: Utils.getStringDataByKey(rs,"nhanvien_th_id"),
                                donViGiaoId: Utils.getStringDataByKey(rs,"donvi_giao_id"),
                                ndXacMinh: Utils.getStringDataByKey(rs,"nd_xacminh"),
                                dvn: Utils.getStringDataByKey(rs,"dvn"),
                                nhanVienGq: Utils.getStringDataByKey(rs,"nhanvien_gq"),
                                ngayGiao: Utils.getStringDataByKey(rs,"ngaygiao"),
                                trangThaiKn: Utils.getStringDataByKey(rs,"trangthai_kn")
                        ))
                    }
                }
            }
            loggingManaged.info("[KhieuNaiController] - [dsPhieu] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [dsPhieu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BasePageResponse<List<PhieuKhieuNai>, PageInfo>> dsPhieuPT(String trangThai, Integer pageNum, Integer pageRec, Long totalRow) {
        List<PhieuKhieuNai> ret = []
        PageInfo pageInfo = new PageInfo()
        String requestId = genericApi.generateRequestId()
        try {
//            loggingManaged.info("[KhieuNaiController] - [dsPhieuPT] - Request: ", new ImmutableMap.Builder<String, Object>()
//                    .put("request_id", requestId)
//                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(ID_TINHTHANH)
            SimpleDateFormat df = new SimpleDateFormat("MMyyyy")
            String kyhoadon = df.format(new Date())
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ CALL QLTN.lay_ds_tb_phkh_v2_pt(?,?,?,?,?,?,?,?,?,?,?,?) }"
                def rs = sql.call(sqlString, [Long.parseLong(phanVungId), donViId, nhanVienId, 1, 0, kyhoadon, trangThai, pageNum, pageRec, totalRow, CURSOR_PARAMETER, CURSOR_PARAMETER]){ResultSet rs, ResultSet rsPage ->
                    while (rs.next()){
                        ret.add(new PhieuKhieuNai(
                                stt: Utils.getStringDataByKey(rs,"stt"),
                                phanVungId: Utils.getStringDataByKey(rs,"phanvung_id"),
                                khieuNaiId: Utils.getStringDataByKey(rs,"khieunai_id"),
                                thueBaoId: Utils.getStringDataByKey(rs,"thuebao_id"),
                                dichVuVTId: Utils.getStringDataByKey(rs,"dichvuvt_id"),
                                loaiTBId: Utils.getStringDataByKey(rs,"loaitb_id"),
                                ngayTn: Utils.getStringDataByKey(rs,"ngay_tn"),
                                nguoiKn: Utils.getStringDataByKey(rs,"nguoi_kn"),
                                dienThoaiLh: Utils.getStringDataByKey(rs,"dienthoai_lh"),
                                chuDeknId: Utils.getStringDataByKey(rs,"chudekn_id"),
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
                                htknId: Utils.getStringDataByKey(rs,"htkn_id"),
                                nguyenNhanKn: Utils.getStringDataByKey(rs,"nguyennhan_kn"),
                                diaChiLh: Utils.getStringDataByKey(rs,"diachi_lh"),
                                maTb: Utils.getStringDataByKey(rs,"ma_tb"),
                                tienGc: Utils.getStringDataByKey(rs,"tien_gc"),
                                vatGc: Utils.getStringDataByKey(rs,"vat_gc"),
                                quyTrinhId: Utils.getStringDataByKey(rs,"quytrinh_id"),
                                ketQuaknId: Utils.getStringDataByKey(rs,"ketquakn_id"),
                                thanhToanId: Utils.getStringDataByKey(rs,"thanhtoan_id"),
                                maKn: Utils.getStringDataByKey(rs,"ma_kn"),
                                giamCuocDt: Utils.getStringDataByKey(rs,"giamcuoc_dt"),
                                giamCuocCp: Utils.getStringDataByKey(rs,"giamcuoc_cp"),
                                tienTrinhknId: Utils.getStringDataByKey(rs,"tientrinhkn_id"),
                                vatDt: Utils.getStringDataByKey(rs,"vat_dt"),
                                vatCp: Utils.getStringDataByKey(rs,"vat_cp"),
                                kyKn: Utils.getStringDataByKey(rs,"ky_kn"),
                                lydoHuygc: Utils.getStringDataByKey(rs,"lydo_huygc"),
                                ngayHuy: Utils.getStringDataByKey(rs,"ngayhuy"),
                                nguoiHuy: Utils.getStringDataByKey(rs,"nguoihuy"),
                                capDoxlId: Utils.getStringDataByKey(rs,"capdoxl_id"),
                                chiTieuTg: Utils.getStringDataByKey(rs,"chitieu_tg"),
                                thoiGianCk: Utils.getStringDataByKey(rs,"thoigian_ck"),
                                phanAnhId: Utils.getStringDataByKey(rs,"phananh_id"),
                                blacklistId: Utils.getStringDataByKey(rs,"blacklist_id"),
                                baoHongId: Utils.getStringDataByKey(rs,"baohong_id"),
                                thoiGianTn: Utils.getStringDataByKey(rs,"thoigian_tn"),
                                loaihdId: Utils.getStringDataByKey(rs,"loaihd_id"),
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
                                phieuknId: Utils.getStringDataByKey(rs,"phieukn_id"),
                                ndGiao: Utils.getStringDataByKey(rs,"nd_giao"),
                                ngayHtGp: Utils.getStringDataByKey(rs,"ngay_ht_gp"),
                                ngayCapcmt: Utils.getStringDataByKey(rs,"ngaycapcmt"),
                                lyDoTon: Utils.getStringDataByKey(rs,"lydo_ton"),
                                nhanVienThId: Utils.getStringDataByKey(rs,"nhanvien_th_id"),
                                donViGiaoId: Utils.getStringDataByKey(rs,"donvi_giao_id"),
                                ndXacMinh: Utils.getStringDataByKey(rs,"nd_xacminh"),
                                dvn: Utils.getStringDataByKey(rs,"dvn"),
                                nhanVienGq: Utils.getStringDataByKey(rs,"nhanvien_gq"),
                                ngayGiao: Utils.getStringDataByKey(rs,"ngaygiao"),
                                trangThaiKn: Utils.getStringDataByKey(rs,"trangthai_kn")
                        ))
                    }
                    if (rsPage != null && rsPage.next()) {
                        pageInfo.setPageNum(rsPage.getInt("page_num"))
                        pageInfo.setPageSize(rsPage.getInt("page_size"))
                        pageInfo.setRecordCount(rsPage.getLong("record_count"))
                    }
                }
            }
//            loggingManaged.info("[KhieuNaiController] - [dsPhieuPT] - Response: ",
//                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret, pageInfo))
        } catch (Exception e) {
            // loggingManaged.error("[KhieuNaiController] - [dsPhieuPT] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> xoaPhieu(String khieuNaiId) {
        List<KhieuNai> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [xoaPhieu] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("khieuNaiId", khieuNaiId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(ID_TINHTHANH)

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call CSS.WEBAPI.INSERT_KHIEUNAI_SMP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), 3, khieuNaiId, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null , CURSOR_PARAMETER]) { }
            }
            loggingManaged.info("[KhieuNaiController] - [xoaPhieu] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [xoaPhieu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<ChiTietKhieuNai>> ctPhieu(String khieuNaiId, String phieuKNId) {
        ChiTietKhieuNai ret = null
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [ctPhieu] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("khieuNaiId", khieuNaiId)
                    .put("phieuKNId", phieuKNId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(ID_TINHTHANH)

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call CSS.webapi_ex.LAY_CT_KHIEUNAI_SMP(?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), khieuNaiId , phieuKNId, CURSOR_PARAMETER]) {ResultSet rs ->
                    if(rs.next()){
                        ret = new ChiTietKhieuNai(
                                thuebaoId: Utils.getStringDataByKey(rs,"thuebao_id"),
                                thanhtoanId: Utils.getStringDataByKey(rs,"thanhtoan_id"),
                                maTb: Utils.getStringDataByKey(rs,"ma_tb"),
                                dichvuvtId: Utils.getStringDataByKey(rs,"dichvuvt_id"),
                                tenDvvt: Utils.getStringDataByKey(rs,"ten_dvvt"),
                                loaitbId: Utils.getStringDataByKey(rs,"loaitb_id"),
                                loaihinhTb: Utils.getStringDataByKey(rs,"loaihinh_tb"),
                                tenLoaikh: Utils.getStringDataByKey(rs,"ten_loaikh"),
                                trangthaiTb: Utils.getStringDataByKey(rs,"trangthai_tb"),
                                diachiTb: Utils.getStringDataByKey(rs,"diachi_tb"),
                                diachiTt: Utils.getStringDataByKey(rs,"diachi_tt"),
                                phanvungId: Utils.getStringDataByKey(rs,"phanvung_id"),
                                khieunaiId: Utils.getStringDataByKey(rs,"khieunai_id"),
                                ngayTn: Utils.getStringDataByKey(rs,"ngay_tn"),
                                nguoiKn: Utils.getStringDataByKey(rs,"nguoi_kn"),
                                dienthoaiLh: Utils.getStringDataByKey(rs,"dienthoai_lh"),
                                chudeknId: Utils.getStringDataByKey(rs,"chudekn_id"),
                                ndKhieunai: Utils.getStringDataByKey(rs,"nd_khieunai"),
                                ttknId: Utils.getStringDataByKey(rs,"ttkn_id"),
                                donviId: Utils.getStringDataByKey(rs,"donvi_id"),
                                nhanvienId: Utils.getStringDataByKey(rs,"nhanvien_id"),
                                ngayGq: Utils.getStringDataByKey(rs,"ngay_gq"),
                                nhanvienGqId: Utils.getStringDataByKey(rs,"nhanvien_gq_id"),
                                noidungGq: Utils.getStringDataByKey(rs,"noidung_gq"),
                                hengqTu: Utils.getStringDataByKey(rs,"hengq_tu"),
                                hengqDen: Utils.getStringDataByKey(rs,"hengq_den"),
                                ndTon: Utils.getStringDataByKey(rs,"nd_ton"),
                                ghichuKn: Utils.getStringDataByKey(rs,"ghichu_kn"),
                                mayCn: Utils.getStringDataByKey(rs,"may_cn"),
                                ngayCn: Utils.getStringDataByKey(rs,"ngay_cn"),
                                nguoiCn: Utils.getStringDataByKey(rs,"nguoi_cn"),
                                congvanXl: Utils.getStringDataByKey(rs,"congvan_xl"),
                                ngayBn: Utils.getStringDataByKey(rs,"ngay_bn"),
                                congvanBn: Utils.getStringDataByKey(rs,"congvan_bn"),
                                cmt: Utils.getStringDataByKey(rs,"cmt"),
                                ngaycap: Utils.getStringDataByKey(rs,"ngaycap"),
                                noicap: Utils.getStringDataByKey(rs,"noicap"),
                                htknId: Utils.getStringDataByKey(rs,"htkn_id"),
                                nguyennhanKn: Utils.getStringDataByKey(rs,"nguyennhan_kn"),
                                diachiLh: Utils.getStringDataByKey(rs,"diachi_lh"),
                                tienGc: Utils.getStringDataByKey(rs,"tien_gc"),
                                vatGc: Utils.getStringDataByKey(rs,"vat_gc"),
                                quytrinhId: Utils.getStringDataByKey(rs,"quytrinh_id"),
                                ketquaknId: Utils.getStringDataByKey(rs,"ketquakn_id"),
                                maKn: Utils.getStringDataByKey(rs,"ma_kn"),
                                giamcuocDt: Utils.getStringDataByKey(rs,"giamcuoc_dt"),
                                giamcuocCp: Utils.getStringDataByKey(rs,"giamcuoc_cp"),
                                tientrinhknId: Utils.getStringDataByKey(rs,"tientrinhkn_id"),
                                vatDt: Utils.getStringDataByKey(rs,"vat_dt"),
                                vatCp: Utils.getStringDataByKey(rs,"vat_cp"),
                                kyKn: Utils.getStringDataByKey(rs,"ky_kn"),
                                lydoHuygc: Utils.getStringDataByKey(rs,"lydo_huygc"),
                                ngayhuy: Utils.getStringDataByKey(rs,"ngayhuy"),
                                nguoihuy: Utils.getStringDataByKey(rs,"nguoihuy"),
                                capdoxlId: Utils.getStringDataByKey(rs,"capdoxl_id"),
                                chitieuTg: Utils.getStringDataByKey(rs,"chitieu_tg"),
                                thoigianCk: Utils.getStringDataByKey(rs,"thoigian_ck"),
                                phananhId: Utils.getStringDataByKey(rs,"phananh_id"),
                                blacklistId: Utils.getStringDataByKey(rs,"blacklist_id"),
                                baohongId: Utils.getStringDataByKey(rs,"baohong_id"),
                                thoigianTn: Utils.getStringDataByKey(rs,"thoigian_tn"),
                                loaihdId: Utils.getStringDataByKey(rs,"loaihd_id"),
                                hdkhId: Utils.getStringDataByKey(rs,"hdkh_id"),
                                email: Utils.getStringDataByKey(rs,"email"),
                                paxlId: Utils.getStringDataByKey(rs,"paxl_id"),
                                hailong: Utils.getStringDataByKey(rs,"hailong"),
                                httlId: Utils.getStringDataByKey(rs,"httl_id"),
                                maTt: Utils.getStringDataByKey(rs,"ma_tt"),
                                maKh: Utils.getStringDataByKey(rs,"ma_kh"),
                                goiktId: Utils.getStringDataByKey(rs,"goikt_id"),
                                loaiknId: Utils.getStringDataByKey(rs,"loaikn_id"),
                                lydoTon: Utils.getStringDataByKey(rs,"lydo_ton"),
                                huonggiaoTieptheoId: Utils.getStringDataByKey(rs,"huonggiao_tieptheo_id"),
                                huonggiaoTieptheo: Utils.getStringDataByKey(rs,"huonggiao_tieptheo"),
                                loaidvDichId: Utils.getStringDataByKey(rs,"loaidv_dich_id"),
                                huonggiaoXulyxongId: Utils.getStringDataByKey(rs,"huonggiao_xulyxong_id"),
                                phieuknId: Utils.getStringDataByKey(rs,"phieukn_id"),
                                ngaygiao: Utils.getStringDataByKey(rs,"ngaygiao"),
                                ndGiao: Utils.getStringDataByKey(rs,"nd_giao"),
                                donviGiaoId: Utils.getStringDataByKey(rs,"donvi_giao_id"),
                                nhanvienGiaoId: Utils.getStringDataByKey(rs,"nhanvien_giao_id"),
                                donviNhanId: Utils.getStringDataByKey(rs,"donvi_nhan_id"),
                                nhanvienThId: Utils.getStringDataByKey(rs,"nhanvien_th_id"),
                                phieuChaId: Utils.getStringDataByKey(rs,"phieu_cha_id"),
                                huonggiaoId: Utils.getStringDataByKey(rs,"huonggiao_id"),
                                ghichuTh: Utils.getStringDataByKey(rs,"ghichu_th"),
                                ttphId: Utils.getStringDataByKey(rs,"ttph_id"),
                                nhanvienTraId: Utils.getStringDataByKey(rs,"nhanvien_tra_id"),
                                ngaytra: Utils.getStringDataByKey(rs,"ngaytra"),
                                ngayTh: Utils.getStringDataByKey(rs,"ngay_th"),
                                ndTra: Utils.getStringDataByKey(rs,"nd_tra"),
                                socongvan: Utils.getStringDataByKey(rs,"socongvan"),
                                ndXacminh: Utils.getStringDataByKey(rs,"nd_xacminh"),
                                mota: Utils.getStringDataByKey(rs,"mota"),
                                songayTn: Utils.getStringDataByKey(rs,"songay_tn"),
                                lydotraConId: Utils.getStringDataByKey(rs,"lydotra_con_id"),
                                ndTraCon: Utils.getStringDataByKey(rs,"nd_tra_con"),
                                ngaytraCon: Utils.getStringDataByKey(rs,"ngaytra_con"),
                                donvitraConId: Utils.getStringDataByKey(rs,"donvitra_con_id"),
                                nhanvientraConId: Utils.getStringDataByKey(rs,"nhanvientra_con_id"),
                                linkfile: Utils.getStringDataByKey(rs,"linkfile"),
                                tenLoaiKN: Utils.getStringDataByKey(rs,"ten_loaikn"),
                                tenHTKN: Utils.getStringDataByKey(rs,"ten_htph"),
                                tenKQLoi: Utils.getStringDataByKey(rs,"ten_htph")
                        )
                    }
                }
            }

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ CALL CSS.webapi_ex.Post_List_v2(?,?,?,?,?,?,?,?) }"
                def rs = sql.call(sqlString, [phanVungId, "LAY_DS_CHUDE_KN", ret.loaiknId, null, null, null, null, CURSOR_PARAMETER]){ResultSet rs ->
                    while (rs.next()){
                        if (Utils.getLongDataByKey(rs,"id") + "" == ret.chudeknId){
                            ret.chuDeKN = new ChuDeKN(
                                    id: Utils.getLongDataByKey(rs,"id"),
                                    name: Utils.getStringDataByKey(rs,"name"),
                                    group2: Utils.getStringDataByKey(rs,"group2"),
                                    group2id: Utils.getStringDataByKey(rs,"group2id"),
                                    group1id: Utils.getStringDataByKey(rs,"group1id"),
                                    group1: Utils.getStringDataByKey(rs,"group1")
                            )
                            break
                        }
                    }
                }
            }

            loggingManaged.info("[KhieuNaiController] - [ctPhieu] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [ctPhieu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsHTPhanHoi() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [dsHTPhanHoi] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(ID_TINHTHANH)

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ CALL CSS.webapi_ex.Post_List_v2(?,?,?,?,?,?,?,?) }"
                def rs = sql.call(sqlString, [phanVungId, "LAY_DS_HINHTHUC_KN", null, null, null, null, null, CURSOR_PARAMETER]){ResultSet rs ->
                    while (rs.next()){
                        ret.add(new DanhMuc(
                                ID: Utils.getLongDataByKey(rs,"id"),
                                NAME: Utils.getStringDataByKey(rs,"name")
                        ))
                    }
                }
            }
            loggingManaged.info("[KhieuNaiController] - [dsHTPhanHoi] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [dsHTPhanHoi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsLoaiChuDe() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [dsLoaiChuDe] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(ID_TINHTHANH)

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ CALL CSS.webapi_ex.Post_List_v2(?,?,?,?,?,?,?,?) }"
                def rs = sql.call(sqlString, [phanVungId, "LAY_DS_LOAI_CHUDE_KN", null, null, null, null, null, CURSOR_PARAMETER]){ResultSet rs ->
                    while (rs.next()){
                        ret.add(new DanhMuc(
                                ID: Utils.getLongDataByKey(rs,"id"),
                                NAME: Utils.getStringDataByKey(rs,"name")
                        ))
                    }
                }
            }
            loggingManaged.info("[KhieuNaiController] - [dsLoaiChuDe] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [dsLoaiChuDe] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<ChuDeKN>>> dsChuDe(String loaiChuDeId) {
        List<ChuDeKN> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [dsChuDe] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("loaiChuDeId", loaiChuDeId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(ID_TINHTHANH)

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ CALL CSS.webapi_ex.Post_List_v2(?,?,?,?,?,?,?,?) }"
                def rs = sql.call(sqlString, [phanVungId, "LAY_DS_CHUDE_KN", loaiChuDeId, null, null, null, null, CURSOR_PARAMETER]){ResultSet rs ->
                    while (rs.next()){
                        ret.add(new ChuDeKN(
                                id: Utils.getLongDataByKey(rs,"id"),
                                name: Utils.getStringDataByKey(rs,"name"),
                                group2: Utils.getStringDataByKey(rs,"group2"),
                                group2id: Utils.getStringDataByKey(rs,"group2id"),
                                group1id: Utils.getStringDataByKey(rs,"group1id"),
                                group1: Utils.getStringDataByKey(rs,"group1")
                        ))
                    }
                }
            }
            loggingManaged.info("[KhieuNaiController] - [dsChuDe] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [dsChuDe] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsNVXuLy() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [dsNVXuLy] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(ID_TINHTHANH)

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ CALL CSS.webapi_ex.Post_List_v2(?,?,?,?,?,?,?,?) }"
                def rs = sql.call(sqlString, [phanVungId, "LAY_DS_NHANVIEN_XL_KN", nhanVienId, null, null, null, null, CURSOR_PARAMETER]){ResultSet rs ->
                    while (rs.next()){
                        ret.add(new DanhMuc(
                                ID: Utils.getLongDataByKey(rs,"id"),
                                NAME: Utils.getStringDataByKey(rs,"name"),
                                CODE: Utils.getStringDataByKey(rs,"selected")
                        ))
                    }
                }
            }
            loggingManaged.info("[KhieuNaiController] - [dsNVXuLy] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [dsNVXuLy] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsKQKhieuNai() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [dsKQKhieuNai] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(ID_TINHTHANH)

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ CALL CSS.webapi_ex.Post_List_v2(?,?,?,?,?,?,?,?) }"
                def rs = sql.call(sqlString, [phanVungId, "LAY_DS_KETQUA_KN", 2, null, null, null, null, CURSOR_PARAMETER]){ResultSet rs ->
                    while (rs.next()){
                        ret.add(new DanhMuc(
                                ID: Utils.getLongDataByKey(rs,"id"),
                                NAME: Utils.getStringDataByKey(rs,"name"),
                                CODE: Utils.getStringDataByKey(rs,"selected")
                        ))
                    }
                }
            }
            loggingManaged.info("[KhieuNaiController] - [dsKQKhieuNai] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [dsKQKhieuNai] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<String>> capNhat(PhieuKNRequest requestBody) {
        String out
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [capNhat] - Request: ", new ImmutableMap.Builder<String, Object>().
                    put("request_id", requestId).put("body", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String donViId = session_.get(ID_DONVI)
            String maNV = session_.get(MA_NV)
            String mayCN = session_.get(DEVICE_ID)
            String nguoiCn = session_.get(MA_NV)
            GroovyRowResult row = null
            String huonggiaoid, quytrinhid, makn

            String sqlString
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sqlString = "{ CALL CSS.webapi_ex.Post_List_v2(?,?,?,?,?,?,?,?) }"
                def rs = sql.call(sqlString, [phanVungId, "LAY_TT_HUONGGIAO_TIEPNHAN_KHIEUNAI", requestBody.chudeknId, donViId, requestBody.loaitbId, null, null, CURSOR_PARAMETER]){ResultSet rs ->
                    if (rs.next()){
                        huonggiaoid = Utils.getStringDataByKey(rs,"huonggiaoid")
                        quytrinhid = Utils.getStringDataByKey(rs,"quytrinhid")
                        makn = Utils.getStringDataByKey(rs,"makn")
                    }
                }
            }
            if (huonggiaoid == null || huonggiaoid == "")
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Quy trình chưa được thiết lập cho chủ đề phản hồi", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            requestBody.quytrinhId = Long.parseLong(quytrinhid)
            requestBody.huonggiaoId = Long.parseLong(huonggiaoid)

            if (requestBody.id == 1){
                requestBody.maKn = makn
                sqlString = "{ CALL CSS.WEBAPI_EX.INSERT_KHIEUNAI_SMP(?, ?, ?, ?, ?, " +
                        "?, to_date(?,'dd/mm/yyyy hh24:mi:ss'), ?, ?, ?, " +
                        "?, ?, ?, ?, to_date(?,'dd/mm/yyyy hh24:mi:ss'), " +
                        "?, ?, to_date(?,'dd/mm/yyyy hh24:mi:ss'), to_date(?,'dd/mm/yyyy hh24:mi:ss'), ?, " +
                        "?, ?, sysdate, ?, ?, " +
                        "to_date(?,'dd/mm/yyyy hh24:mi:ss'), ?, ?, to_date(?,'dd/mm/yyyy hh24:mi:ss'), ?, " +
                        "?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, " +
                        "?, ?) }"
            }else if (requestBody.id == 2 || requestBody.id == 4){
                sqlString = "{ CALL CSS.WEBAPI.INSERT_KHIEUNAI_SMP(?, ?, ?, ?, ?, " +
                        "?, to_date(?,'dd/mm/yyyy hh24:mi:ss'), ?, ?, ?, " +
                        "?, ?, ?, ?, to_date(?,'dd/mm/yyyy hh24:mi:ss'), "  +
                        "?, ?, to_date(?,'dd/mm/yyyy hh24:mi:ss'), to_date(?,'dd/mm/yyyy hh24:mi:ss'), ?, " +
                        "?, ?, sysdate, ?, ?, " +
                        "to_date(?,'dd/mm/yyyy hh24:mi:ss'), ?, ?, to_date(?,'dd/mm/yyyy hh24:mi:ss'), ?, " +
                        "?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, " +
                        "?, ?) }"
            }

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), requestBody.id, requestBody.khieunaiId, requestBody.thuebaoId, requestBody.dichvuvtId,
                                     requestBody.loaitbId, requestBody.ngayTn, requestBody.nguoiKn, requestBody.dienthoaiLh, requestBody.chudeknId,
                                     requestBody.ndKhieunai, requestBody.ttknId, donViId, nhanVienId, requestBody.ngayGq,
                                     requestBody.nhanvienGqId, requestBody.noidungGq, requestBody.hengqTu, requestBody.hengqDen, requestBody.ndTon,
                                     requestBody.ghichuKn, mayCN, nguoiCn, requestBody.congvanXl,
                                     requestBody.ngayBn, requestBody.congvanBn, requestBody.cmt, requestBody.ngaycap, requestBody.noicap,
                                     requestBody.htknId, requestBody.nguyennhanKn, requestBody.diachiLh, requestBody.maTb, requestBody.tienGc,
                                     requestBody.vatGc, requestBody.quytrinhId, requestBody.huonggiaoId, requestBody.ketquaknId, requestBody.thanhtoanId,
                                     requestBody.maKn, requestBody.giamcuocDt, requestBody.giamcuocCp, requestBody.tientrinhknId, requestBody.vatDt,
                                     requestBody.vatCp, requestBody.xulyDuoc, requestBody.lydoKoXl, requestBody.nguoixlId, requestBody.phieuknId,
                                     requestBody.nguyennhanXl, CURSOR_PARAMETER
                ]) {ResultSet rs ->
                    if (rs.next()){
                        out = Utils.getStringDataByKey(rs,"KHIEUNAIID")
                    }
                }
            }

            loggingManaged.info("[KhieuNaiController] - [capNhat] - Response: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(out, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [capNhat] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DonViTree>>> dsDVNhan(String khieuNaiId, String loaiDVDichId) {
        List<DonViTree> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [dsDVNhan] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("khieuNaiId", khieuNaiId)
                    .put("loaiDVDichId", loaiDVDichId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(ID_TINHTHANH)

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ CALL CSS.webapi_ex.Post_List_v2(?,?,?,?,?,?,?,?) }"
                def rs = sql.call(sqlString, [phanVungId, "LAY_DS_DONVI_KN", khieuNaiId, loaiDVDichId, null, null, null, CURSOR_PARAMETER]){ResultSet rs ->
                    while (rs.next()){
                        ret.add(new DonViTree(
                                donViId: Utils.getLongDataByKey(rs,"id"),
                                tenDv: Utils.getStringDataByKey(rs,"ten_dv"),
                                donViChaId: Utils.getLongDataByKey(rs,"donvi_cha_id")
                        ))
                    }
                }
            }
            loggingManaged.info("[KhieuNaiController] - [dsDVNhan] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [dsDVNhan] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<String>> giaoPhieu(GiaoPhieuKhieuNaiRequest requestBody) {
        String out
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[KhieuNaiController] - [giaoPhieu] - Request: ", new ImmutableMap.Builder<String, Object>().
                    put("request_id", requestId).put("body", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String donViId = session_.get(ID_DONVI)
            String maNV = session_.get(MA_NV)
            String mayCN = session_.get(DEVICE_ID)
            String nguoiCn = session_.get(MA_NV)

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ CALL CSS.WEBAPI_EX.INSERT_GIAOPHIEU_KHIEUNAI_SMP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), 1, -1, requestBody.khieuNaiId, null,
                                     requestBody.noiDung, donViId, nhanVienId, requestBody.donViNhanId, null,
                                     requestBody.phieuKNId, requestBody.huongGiaoId, requestBody.ttKNId, null, 1,
                                     null, null, null, mayCN, nguoiCn,
                                     null, null, null, requestBody.congVan, null,
                                     CURSOR_PARAMETER
                ]) {ResultSet rs ->
                    if (rs.next()){
                        out = Utils.getStringDataByKey(rs,"PHIEUKNID")
                    }
                }
            }

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ CALL ? := QLTN.FN_GUITINNHAN_GIAOPHIEU_KHIEUNAI_SMP (?,?,?,?,?) }"
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.khieuNaiId, requestBody.donViNhanId, requestBody.huongGiaoId, nguoiCn]) {}
            }

            loggingManaged.info("[KhieuNaiController] - [giaoPhieu] - Response: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(out, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[KhieuNaiController] - [giaoPhieu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
}
