package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.CapPhatRackRequestDTO;
import vn.vnpt.vnptit.ecms.dto.idc.KetQuaDanhSachTacNghiepDto;
import vn.vnpt.vnptit.ecms.dto.idc.KetQuaThiCongRackDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class CapPhatRackService {
    private final DbContext dbContext;

    public CapPhatRackService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> thiCongCapPhatRack(CapPhatRackRequestDTO request) throws AppSqlException {
        AppRequestContext context = AppRequestContext.getCurrent();

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", request.getIdPhieuThiCong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_idc_rack_id", request.getIdRack(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", context.getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TUANLN.THICONG_CAPPHAT_RACK", params);
    }

    public ArrayList<Map<String, Object>> xoaThiCongCapPhatRack(CapPhatRackRequestDTO request) throws AppSqlException {
        AppRequestContext context = AppRequestContext.getCurrent();

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", request.getIdPhieuThiCong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_idc_rack_id", request.getIdRack(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", context.getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TUANLN.XOA_THICONG_CAPPHAT_RACK", params);
    }

    public ArrayList<Map<String, Object>> dsRacksDaCapPhat(Long idPhieuThiCong) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", idPhieuThiCong, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TUANLN.DS_RACK_TRONG_PHIEUTC_CAPPHAT", params);
    }

    public ArrayList<Map<String, Object>> traPhieu(Long idPhieuThiCong) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", idPhieuThiCong, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TUANLN.TRAPHIEU_CAPPHAT_RACK", params);
    }

    public ArrayList<Map<String, Object>> hoanThanh(Long idPhieuThiCong) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", idPhieuThiCong, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TUANLN.HOANTHANH_CAPPHAT_RACK", params);
    }

    public ArrayList<Map<String, Object>> dsKetQuaThiCongCapPhatRack(KetQuaThiCongRackDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_loaicv_id", 57, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
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
        parameters.add(new SqlParameter("p_ten_rack", dto.getTenRack(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_loai_rack", dto.getLoaiRack(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TUANLN.DS_KETQUA_RACK", parameters);
    }
}
