package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.*;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class CapPhatThuHoiUService {
    private final DbContext dbContext;

    public CapPhatThuHoiUService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> dsRackSanSangCapPhatU(Long idPhieuThiCong) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", idPhieuThiCong, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_CAPPHATTHUHOI_U_TUANLN.DS_RACK_SANSANG_CAPPHAT_U", params);
    }

    public ArrayList<Map<String, Object>> dsRackSanSangThuHoiU(Long idPhieuThiCong) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", idPhieuThiCong, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_CAPPHATTHUHOI_U_TUANLN.DS_RACK_SANSANG_THUHOI_U", params);
    }

    public ArrayList<Map<String, Object>> dsRackTrongPhieuCapPhatThuHoiU(Long idPhieuThiCong) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", idPhieuThiCong, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_CAPPHATTHUHOI_U_TUANLN.DS_RACK_TRONGPHIEU_CAPPHAT_THUHOI_U", params);
    }

    public ArrayList<Map<String, Object>> dsUSanSangCapPhat(Long idPhieuThiCong, Long rackId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", idPhieuThiCong, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_id_rack", rackId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_CAPPHATTHUHOI_U_TUANLN.DS_U_SANGSANG_CAPPHAT", params);
    }

    public ArrayList<Map<String, Object>> capPhat(CapPhatURequestV2DTO request) throws AppSqlException {
        AppRequestContext context = AppRequestContext.getCurrent();

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", request.getIdPhieuThiCong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_id_rack", request.getIdRack(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_id_u", request.getListOfIdUnit(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nhanvien_id", context.getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_CAPPHATTHUHOI_U_TUANLN.CAPPHAT_U", params);
    }

    public ArrayList<Map<String, Object>> xoaCapPhat(XoaCapPhatThuHoiURequestDTO request) throws AppSqlException {
        AppRequestContext context = AppRequestContext.getCurrent();

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", request.getIdPhieuThiCong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_id_u", request.getListOfIdUnit(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nhanvien_id", context.getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_CAPPHATTHUHOI_U_TUANLN.XOA_CAPPHAT_U", params);
    }

    public ArrayList<Map<String, Object>> dsUTrongPhieuCapPhat(Long idPhieu) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", idPhieu, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_CAPPHATTHUHOI_U_TUANLN.DS_U_TRONG_PHIEUTC_CAPPHAT", params);
    }

    public ArrayList<Map<String, Object>> traPhieu(Long idPhieu, TraPhieuCapPhatThuHoiRequestDTO request) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", idPhieu, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_lydo_traphieu", request.getReason(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_CAPPHATTHUHOI_U_TUANLN.TRAPHIEU", params);
    }

    public ArrayList<Map<String, Object>> hoanThanhCapPhat(Long idPhieu) throws AppSqlException {
        AppRequestContext context = AppRequestContext.getCurrent();
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", idPhieu, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", context.getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_CAPPHATTHUHOI_U_TUANLN.HOANTHANH_CAPPHAT_U", params);
    }

    public ArrayList<Map<String, Object>> dsKetQua(KetQuaThiCongUDTO dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_loaicv_id", dto.getLoaiCvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_donvi_id", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_trangthai_id", dto.getTrangthaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_nhanvien_th_id", dto.getNhanvienThId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ngay_yc_start", dto.getNgayYcStart(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        parameters.add(new SqlParameter("p_ngay_yc_end", dto.getNgayYcEnd(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        parameters.add(new SqlParameter("p_ngay_ycht_start", dto.getNgayYchtStart(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        parameters.add(new SqlParameter("p_ngay_ycht_end", dto.getNgayYchtEnd(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        parameters.add(new SqlParameter("p_phanloai", dto.getPhanLoai(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_khachhang", dto.getKhachHang(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_congviec", dto.getCongViec(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_idc_id", dto.getIdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ten_u", dto.getTenU(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_CAPPHATTHUHOI_U_TUANLN.DS_KETQUA_U", parameters);
    }

    public ArrayList<Map<String, Object>> dsSanSangThuHoi(Long idPhieu, Long idRack) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", idPhieu, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_id_rack", idRack, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_CAPPHATTHUHOI_U_TUANLN.DS_U_SANGSANG_THUHOI", params);
    }

    public ArrayList<Map<String, Object>> thuHoi(CapPhatURequestV2DTO request) throws AppSqlException {
        AppRequestContext context = AppRequestContext.getCurrent();

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", request.getIdPhieuThiCong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_id_rack", request.getIdRack(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_id_u", request.getListOfIdUnit(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nhanvien_id", context.getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_CAPPHATTHUHOI_U_TUANLN.THUHOI_U", params);
    }

    public ArrayList<Map<String, Object>> xoaThuHoi(XoaCapPhatThuHoiURequestDTO request) throws AppSqlException {
        AppRequestContext context = AppRequestContext.getCurrent();

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", request.getIdPhieuThiCong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_id_u", request.getListOfIdUnit(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nhanvien_id", context.getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_CAPPHATTHUHOI_U_TUANLN.XOA_THUHOI_U", params);
    }

    public ArrayList<Map<String, Object>> dsUTrongPhieuThuHoi(Long idPhieu) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", idPhieu, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_CAPPHATTHUHOI_U_TUANLN.DS_U_TRONG_PHIEUTC_THUHOI", params);
    }

    public ArrayList<Map<String, Object>> hoanThanhThuHoi(Long idPhieu) throws AppSqlException {
        AppRequestContext context = AppRequestContext.getCurrent();
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", idPhieu, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", context.getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_CAPPHATTHUHOI_U_TUANLN.HOANTHANH_THUHOI_U", params);
    }
}
