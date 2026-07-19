package vn.vnpt.vnptit.ecms.service.idc;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.DanhSachCongViecDto;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.vnptit.ecms.dto.idc.DanhSachCongViecV2Dto;

@Service
public class DanhSachCongViecService {
    private final DbContext dbContext;

    public DanhSachCongViecService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> getDanhSachCongViec(DanhSachCongViecDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_loaicv_id", dto.getLoaicvId(), Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id", dto.getDonviId(), Types.NUMERIC));
        params.add(new SqlParameter("p_idc_id", dto.getIdcId(), Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
        params.add(new SqlParameter("p_phanloai", dto.getPhanloai(), Types.NUMERIC));
        params.add(new SqlParameter("p_yc_batdau", dto.getYcBatdau(), Types.DATE));
        params.add(new SqlParameter("p_yc_ketthuc", dto.getYcKetthuc(), Types.DATE));
        params.add(new SqlParameter("p_ycht_batdau", dto.getYchtBatdau(), Types.DATE));
        params.add(new SqlParameter("p_ycht_ketthuc", dto.getYchtKetthuc(), Types.DATE));
        params.add(new SqlParameter("p_khachhang", dto.getKhachhang(), Types.VARCHAR));
        params.add(new SqlParameter("p_congviec", dto.getCongviec(), Types.VARCHAR));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_CONGVIEC.sp_get_ds_congviec_v2", params);
    }

    public ArrayList<Map<String, Object>> getDanhSachCongViecTheoHeThongThietBi(DanhSachCongViecDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_LOAICV_ID", dto.getLoaicvId(), Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", dto.getDonviId(), Types.NUMERIC));
        params.add(new SqlParameter("P_IDC_ID", dto.getIdcId(), Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_ID", dto.getNhanvienId(), Types.NUMERIC));
        params.add(new SqlParameter("P_PHANLOAI", dto.getPhanloai(), Types.NUMERIC));
        params.add(new SqlParameter("P_YC_BATDAU", dto.getYcBatdau(), Types.DATE));
        params.add(new SqlParameter("P_YC_KETTHUC", dto.getYcKetthuc(), Types.DATE));
        params.add(new SqlParameter("P_YCHT_BATDAU", dto.getYchtBatdau(), Types.DATE));
        params.add(new SqlParameter("P_YCHT_KETTHUC", dto.getYchtKetthuc(), Types.DATE));
        params.add(new SqlParameter("P_KHACHHANG", dto.getKhachhang(), Types.VARCHAR));
        params.add(new SqlParameter("P_CONGVIEC", dto.getCongviec(), Types.VARCHAR));
        params.add(new SqlParameter("P_TRANGTHAI_ID", dto.getTrangthaiId(), Types.NUMERIC));
        params.add(new SqlParameter("P_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_CONGVIEC.SP_GET_DS_CONGVIEC_THEO_HETHONG_THIETBI", params);
    }

    public ArrayList<Map<String, Object>> getDanhSachCongViecTheoThiCongRack(DanhSachCongViecDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_LOAICV_ID", dto.getLoaicvId(), Types.NUMERIC));
        params.add(new SqlParameter("P_TRANGTHAI_ID", dto.getTrangthaiId(), Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", dto.getDonviId(), Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_TH_ID", dto.getNhanvienThId(), Types.NUMERIC));
        params.add(new SqlParameter("P_NGAY_BATDAU", dto.getNgayBatdau(), Types.DATE));
        params.add(new SqlParameter("P_NGAY_KETTHUC", dto.getNgayKetthuc(), Types.DATE));
        params.add(new SqlParameter("P_MA_CV", dto.getMaCv(), Types.VARCHAR));
        params.add(new SqlParameter("P_TEN_CV", dto.getTenCv(), Types.VARCHAR));
        params.add(new SqlParameter("P_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_CONGVIEC.SP_GET_DS_CONGVIEC_THEO_THICONG_RACK", params);
    }

    public ArrayList<Map<String, Object>> getChiTietCongViec(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHIEUTC_ID", id, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_CONGVIEC.SP_GET_DETAIL_JOB", params);
    }

    public ArrayList<Map<String, Object>> getDanhSachCongViecV2(DanhSachCongViecV2Dto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_LOAICV_ID", dto.getLoaicvId(), Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", dto.getDonviId(), Types.NUMERIC));
        params.add(new SqlParameter("P_IDC_ID", dto.getIdcId(), Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_ID", dto.getNhanvienId(), Types.NUMERIC));
        params.add(new SqlParameter("P_PHANLOAI", dto.getPhanloai(), Types.NUMERIC));
        params.add(new SqlParameter("P_YC_BATDAU", dto.getYcBatdau(), Types.DATE));
        params.add(new SqlParameter("P_YC_KETTHUC", dto.getYcKetthuc(), Types.DATE));
        params.add(new SqlParameter("P_YCHT_BATDAU", dto.getYchtBatdau(), Types.DATE));
        params.add(new SqlParameter("P_YCHT_KETTHUC", dto.getYchtKetthuc(), Types.DATE));
        params.add(new SqlParameter("P_KHACHHANG", dto.getKhachhang(), Types.VARCHAR));
        params.add(new SqlParameter("P_CONGVIEC", dto.getCongviec(), Types.VARCHAR));
        params.add(new SqlParameter("P_TRANGTHAI_ID", dto.getTrangthaiId(), Types.NUMERIC));
        params.add(new SqlParameter("P_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_THICONG_CONGVIEC.SP_GET_DS_CONGVIEC", params);
    }
}
