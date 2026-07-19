package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.HaTangCnttThongTinChungDTO;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class HaTangCNTTService {
    private final DbContext dbContext;

    public HaTangCNTTService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public boolean checkIsToTruong() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), java.sql.Types.NUMERIC));
        Object result = dbContext.executeFunction("ECMS.PKG_IDC_TAINGUYEN_AOHOA.FN_IS_TO_TRUONG", java.sql.Types.NUMERIC, params);
        return result != null && result.toString().equals("1");
    }

    public List<Map<String, Object>> DanhSachMangThietBi()  throws AppSqlException {
       // ArrayList<SqlParameter> parameters = new ArrayList<>();
       // parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeQueryToListMap("select * from ECMS.NHOM_TBI where phanvung_id = 68 and sudung =1");
    }
    public List<Map<String, Object>> DanhSachHeThongThietBi()  throws AppSqlException {
        // ArrayList<SqlParameter> parameters = new ArrayList<>();
        // parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeQueryToListMap("select hethongtbi_id,ten from ECMS.HETHONG_THIETBI");
    }
    public List<Map<String, Object>> DanhSachLoaiThietBi(long mangThietbiId)  throws AppSqlException {
        // ArrayList<SqlParameter> parameters = new ArrayList<>();
        // parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeQueryToListMap("select loaitbi_id,loai_tbi,ma_loaitbi,nhomtbi_id from ECMS.LOAI_TBI where phanvung_id = 68 and nhomtbi_id = "+mangThietbiId);
    }

    public List<Map<String, Object>> DanhSachPhanLoaiThietBi(long loaitbi_id)  throws AppSqlException {
        // ArrayList<SqlParameter> parameters = new ArrayList<>();
        // parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeQueryToListMap("select phanloaitbi_id, ten from ECMS.PHANLOAI_TBI where loaitbi_id =  "+ loaitbi_id);
    }

    public List<Map<String, Object>> DanhSachChungLoaiThietBi()  throws AppSqlException {
        // ArrayList<SqlParameter> parameters = new ArrayList<>();
        // parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeQueryToListMap("select chungloaitbi_id,ten from ECMS.CHUNGLOAI_TBI");
    }
    public List<Map<String, Object>> DanhSachChuQuanThietBi()  throws AppSqlException {
        // ArrayList<SqlParameter> parameters = new ArrayList<>();
        // parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeQueryToListMap("select chuquan_id,ten  from ECMS.CHUQUAN where sudung =1");
    }
    public List<Map<String, Object>> DanhSachLoaiKhachHang()  throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_RESULT", java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_HATANG_CNTT.DANHMUC_LOAI_KHACHHANG", params);
    }
    public List<Map<String, Object>> DanhSachTrangThaiVanHanh()  throws AppSqlException {
        // ArrayList<SqlParameter> parameters = new ArrayList<>();
        // parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeQueryToListMap("select ttvh_id,trangthai_vh from ECMS.trangthai_vh where phanvung_id = 68 and sudung =1");
    }
    public List<Map<String, Object>> DanhSachHangSanXuat()  throws AppSqlException {
        // ArrayList<SqlParameter> parameters = new ArrayList<>();
        // parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeQueryToListMap("select hangsx_id,hang_sx,ten from ecms.HANG_SX where su_dung =1");
    }
    public List<Map<String, Object>> DanhSachNhaCungCap()  throws AppSqlException {
        // ArrayList<SqlParameter> parameters = new ArrayList<>();
        // parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeQueryToListMap("select id,ten from ecms.NHACUNGCAP\n");
    }
    public List<Map<String, Object>> DanhSachHeDieuHanh()  throws AppSqlException {
        // ArrayList<SqlParameter> parameters = new ArrayList<>();
        // parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeQueryToListMap("select id,ten from ecms.HEDIEUHANH");
    }
    public List<Map<String, Object>> TimKiemThietBi(HaTangCnttThongTinChungDTO obj)  throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_USER_ID", AppRequestContext.getCurrent().getPhanVungID(),
                java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_IDC_ID", obj.getIdcId(),
                java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_MATSAN_ID", obj.getMatSanId(),
                java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_PHONG_ZONE_ID", obj.getPhongId(),
                java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_RACK_ID", obj.getIdcRackId(),
                java.sql.Types.INTEGER));

        params.add(new SqlParameter("P_HANGSX_ID", obj.getHangSxId(),
                java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_LOAI_THIETBI_ID", obj.getLoaiThietBiId(),
                java.sql.Types.INTEGER));

        params.add(new SqlParameter("P_TEN_THIETBI", obj.getTen(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_SERIAL_NUMBER", obj.getSerialNumber(), java.sql.Types.VARCHAR));

        params.add(new SqlParameter("P_THE_TAI_SAN", obj.getTaiSanId(), java.sql.Types.VARCHAR));

        params.add(new SqlParameter("P_MODEL", obj.getModel(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_DONVI_QUANLY_ID", obj.getDonViQLyId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_NGUOI_QUAN_LY", obj.getNguoiQLyId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_TRANG_THAI_VAN_HANH_ID", obj.getTrangThaiVhId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_HE_THONG_ID", obj.getHeThongTbiId(), java.sql.Types.INTEGER));

        params.add(new SqlParameter("P_RESULT", java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_HATANG_CNTT.SP_TIMKIEM_THIETBI", params);
    }

    public List<Map<String, Object>> UpdateThietBi()  throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PORT_ID", 3114, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_THIETBI_ID", 929, java.sql.Types.INTEGER));

        params.add(new SqlParameter("P_TEN", "x", java.sql.Types.NVARCHAR));

        params.add(new SqlParameter("P_LOAI_NDIEN_ID", 1, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_KETNOI", 1, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_TRANGTHAI_SD", 1, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_LOAICONG_ID", 1, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_DD_DINHDANH", 1, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_DA_DINHDANH", 1, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_NGUOI_CN", "trung", java.sql.Types.VARCHAR));
        SqlParameter result = new SqlParameter("P_RETURN_ID", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(result);

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_HATANG_CNTT.SP_UPDATE_THIETBI_PORT", params);



    }

    public List<Map<String, Object>> ThemMoiThietBi(HaTangCnttThongTinChungDTO obj)  throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

        params.add(new SqlParameter("P_MANGTBI_ID", obj.getMangTbiId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_HETHONGTBI_ID", obj.getHeThongTbiId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_LOAITBI_ID", obj.getLoaiTbiId(), java.sql.Types.NVARCHAR));
        params.add(new SqlParameter("P_PHANLOAI_TBI_ID", obj.getPhanLoaiTbiId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CHUNGLOAITBI_ID", obj.getChungLoaiTbiId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CHUQUAN_ID", obj.getChuQuanId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_MA_TBI", obj.getMaTbi(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_TEN", obj.getTen(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_HIEU_LUC", obj.getHieuLuc(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_SERIAL_NUMBER", obj.getSerialNumber(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_KYHIEU", obj.getKyHieu(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_TRANGTHAIVH_ID", obj.getTrangThaiVhId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_MODEL", obj.getModel(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_DOITUONGKT_ID", obj.getDoiTuongKt(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_ICON_PATH", obj.getIconPath(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_HANGSX_ID", obj.getHangSxId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_NHACC_ID", obj.getNhaccId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_GHI_CHU", obj.getGhiChu(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_DAI", obj.getDai(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_RONG", obj.getRong(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CAO", obj.getCao(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CS_DINHDANH", obj.getCsDinhDanh(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CS_THUCTE", obj.getCsThucTe(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_DA_DINHDANH", obj.getDaDinhDanh(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_DD_THUCTE", obj.getDdThucTe(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_LOAI_NDIEN", obj.getLoaiNdien(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_COS_PHI", obj.getCosPhi(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_TYLE_SUDUNG", obj.getTyLeSuDung(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CAUHINH_HETHONG", obj.getCauHinhHeThong(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_SL_RACK_UNIT", obj.getSlRackUnit(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_KIEU_LAPDAT", obj.getKieuLapDat(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_LOAI_MAY", obj.getLoaiMay(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_FULL_DEPTH", obj.getFullDepth(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_LOAI_KHACHHANG", obj.getLoaiKhachHang(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_DONGDIEN_DINHDANH", obj.getDdDinhDanh(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), java.sql.Types.VARCHAR));
        SqlParameter result = new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR);
        params.add(result);

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_HATANG_CNTT.THEMMOI_THIETBI", params);



    }


    public List<Map<String, Object>> CapNhatThietBi(HaTangCnttThongTinChungDTO obj)  throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_THIETBI_ID", obj.getId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_MANGTBI_ID", obj.getMangTbiId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_HETHONGTBI_ID", obj.getHeThongTbiId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_LOAITBI_ID", obj.getLoaiTbiId(), java.sql.Types.NVARCHAR));
        params.add(new SqlParameter("P_PHANLOAI_TBI_ID", obj.getPhanLoaiTbiId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CHUNGLOAITBI_ID", obj.getChungLoaiTbiId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CHUQUAN_ID", obj.getChuQuanId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_MA_TBI", obj.getMaTbi(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_TEN", obj.getTen(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_HIEU_LUC", obj.getHieuLuc(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_SERIAL_NUMBER", obj.getSerialNumber(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_KYHIEU", obj.getKyHieu(), java.sql.Types.VARCHAR)); 
        params.add(new SqlParameter("P_TRANGTHAIVH_ID", obj.getTrangThaiVhId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_MODEL", obj.getModel(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_DOITUONGKT_ID", obj.getDoiTuongKt(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_ICON_PATH", obj.getIconPath(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_HANGSX_ID", obj.getHangSxId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_NHACC_ID", obj.getNhaccId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_GHI_CHU", obj.getGhiChu(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_DAI", obj.getDai(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_RONG", obj.getRong(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CAO", obj.getCao(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CS_DINHDANH", obj.getCsDinhDanh(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CS_THUCTE", obj.getCsThucTe(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_DA_DINHDANH", obj.getDaDinhDanh(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_DD_THUCTE", obj.getDdThucTe(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_LOAI_NDIEN", obj.getLoaiNdien(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_COS_PHI", obj.getCosPhi(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_TYLE_SUDUNG", obj.getTyLeSuDung(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CAUHINH_HETHONG", obj.getCauHinhHeThong(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_SL_RACK_UNIT", obj.getSlRackUnit(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_KIEU_LAPDAT", obj.getKieuLapDat(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_LOAI_MAY", obj.getLoaiMay(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_FULL_DEPTH", obj.getFullDepth(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_LOAI_KHACHHANG", obj.getLoaiKhachHang(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_DONGDIEN_DINHDANH", obj.getDdDinhDanh(), java.sql.Types.INTEGER));


        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), java.sql.Types.VARCHAR));
        SqlParameter result = new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR);
        params.add(result);

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_HATANG_CNTT.CAPNHAT_THIETBI", params);



    }

    public List<Map<String, Object>> XoaThietBi(HaTangCnttThongTinChungDTO obj)  throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_THIETBI_ID", obj.getId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), java.sql.Types.VARCHAR));
        SqlParameter result = new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR);
        params.add(result);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_HATANG_CNTT.XOA_THIETBI", params);
    }
    public List<Map<String, Object>> DANHSACH_KHACHHANG(HaTangCnttThongTinChungDTO obj)  throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_ID", obj.getId(), java.sql.Types.INTEGER));
        SqlParameter result = new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR);
        params.add(result);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_HATANG_CNTT.DANHSACH_KHACHHANG", params);
    }
    public List<Map<String, Object>> CapNhatTaiSan(HaTangCnttThongTinChungDTO obj)  throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_THIETBI_ID", obj.getId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_MA_DU_AN", obj.getMaDa(), Types.VARCHAR));
        params.add(new SqlParameter("P_TEN_DU_AN", obj.getTenDa(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_THE_TAI_SAN", obj.getMaTs(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_NGAY_DUKIEN_KHAU_HAO", obj.getNgayKh(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_TAISAN_ID", obj.getTaiSanId(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_HAN_BAO_HANH", obj.getHanBaoHanh(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_MA_VAT_TU", obj.getMaVtu(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_NGAY_LAP_DAT", obj.getNgayLapDat(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_NGAY_SU_DUNG", obj.getNgaySuDung(), java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_DONVI_QUANLY_ID", obj.getDonViQLyId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_PHONGBAN_QUANLY_ID", obj.getPhongBanQLyId(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_NHANVIEN_ID", obj.getNguoiQLyId(), java.sql.Types.INTEGER));

        SqlParameter result = new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR);
        params.add(result);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_HATANG_CNTT.THEMMOI_CAPNHAT_TAISAN", params);
    }


    public List<Map<String, Object>> CapNhatThemMoiOCam(Long thietBiId,Long loaiOCamId, String ten, int trangThaiOCam, int trangThaiKetNoi, Long thietBiKetNoiId   )  throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_THIETBI_ID", thietBiId, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_LOAI_OCAM_ID", loaiOCamId, Types.INTEGER));
        params.add(new SqlParameter("P_TEN", ten , java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_TRANGTHAI_OCAM", trangThaiOCam, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_TRANGTHAI_KETNOI", trangThaiKetNoi, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_THIETBI_KETNOI_ID",thietBiKetNoiId, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_NGUOI_CN",  AppRequestContext.getCurrent().getUserName(), java.sql.Types.VARCHAR));

        SqlParameter result = new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR);
        params.add(result);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_HATANG_CNTT.THEMMOI_CAPNHAT_OCAM", params);
    }
    public List<Map<String, Object>> ChiTietTaiSan(Long thietBiId)  throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_THIETBI_ID", thietBiId, java.sql.Types.INTEGER));

        SqlParameter result = new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR);
        params.add(result);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_HATANG_CNTT.CHITIET_TAISAN", params);
    }
    public List<Map<String, Object>> ChiTietThietBi(Long thietBiId)  throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_THIETBI_ID", thietBiId, java.sql.Types.INTEGER));

        SqlParameter result = new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR);
        params.add(result);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_HATANG_CNTT.CHITIET_THIETBI", params);
    }

	public List<Map<String, Object>> DanhSachThietBiKetNoi()  throws AppSqlException {
        return dbContext.executeQueryToListMap("select id,ten from ecms.thietbi");
    }
    public List<Map<String, Object>> DanhSachLoaiOCam()  throws AppSqlException {

        return dbContext.executeQueryToListMap("select loaiocam_id as id,ten from ecms.loai_ocam where sudung =1");
    }
    public List<Map<String, Object>> DanhMucIDC(String loaiDanhMuc,Long thamSo1)  throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_NHANVIEN_ID",  AppRequestContext.getCurrent().getNhanVienID(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_LOAI_DANHMUC", loaiDanhMuc , java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_THAM_SO1", thamSo1 , java.sql.Types.INTEGER));

        SqlParameter result = new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR);
        params.add(result);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_HATANG_CNTT.SP_DANHMUC_DANHSACH", params);
    }

}
