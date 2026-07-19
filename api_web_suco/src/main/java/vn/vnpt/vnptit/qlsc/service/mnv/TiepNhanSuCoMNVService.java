package vn.vnpt.vnptit.qlsc.service.mnv;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.qlsc.dto.mnv.CapNhatTinhTrangSuCoMNVInput;
import vn.vnpt.vnptit.qlsc.dto.mnv.CapNhatTinhTrangSuCoTempMNVInput;
import vn.vnpt.vnptit.qlsc.dto.mnv.GiaoViecKiemTraSuCoInput;
import vn.vnpt.vnptit.qlsc.dto.mnv.TaoSuCoTamMNVInput;
import vn.vnpt.vnptit.qlsc.dto.mnv.TaoYeuCauHoTroMNVInput;

import java.sql.Types;
import java.util.ArrayList;

@Service
public class TiepNhanSuCoMNVService {
    private final DbContext dbContext;
    private static final String PKG_NAME = "QLSC.PKG_NDTHANH";

    public TiepNhanSuCoMNVService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public boolean giaoviec_kiemtrasuco(GiaoViecKiemTraSuCoInput input, Integer phanvungId, Integer nhanvienGiaoId)
            throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvungId, SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_GIAO_ID", nhanvienGiaoId, SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_TH_ID", input.getNhanvien_id(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("P_MA_SUCO", input.getMa_suco(), SqlParameter.ParameterDirection.INPUT,
                Types.VARCHAR));
        params.add(new SqlParameter("P_NHOM_SUCO", input.getNhom_suco(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("P_NOIDUNG_GIAO", input.getNoidung_giao(), SqlParameter.ParameterDirection.INPUT,
                Types.VARCHAR));
        params.add(
                new SqlParameter("P_RS_OUT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        dbContext.executeSpWithCursorToListMap("QLSC.PKG_XULY_SUCO_MNV.GIAOVIEC_KIEMTRA_SUCO", params);
        return true;
    }

    public boolean taoSuCoTamMNV(TaoSuCoTamMNVInput item) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                Types.INTEGER));
        params.add(new SqlParameter("P_MA_SC", item.getMaSuCo(), Types.NVARCHAR));
        params.add(new SqlParameter("P_NGUONSC_ID", item.getNguonSuCoID(), Types.INTEGER));
        params.add(new SqlParameter("P_NHOMSC_ID", item.getNhomSuCoID(), Types.INTEGER));
        params.add(new SqlParameter("P_NGAY_SC", item.getNgaySuCo(), Types.NVARCHAR));
        params.add(new SqlParameter("P_NOIDUNG_SC", item.getNoiDungSuCo(), Types.NVARCHAR));
        params.add(new SqlParameter("P_NGAY_BH", item.getNgayBH(), Types.NVARCHAR));
        params.add(new SqlParameter("P_DONVI_BH", item.getDonViBH(), Types.INTEGER));
        params.add(new SqlParameter("P_NHANVIEN_BH", item.getNhanVienBH(), Types.INTEGER));
        params.add(new SqlParameter("P_DIENTHOAI_LH", item.getDienThoaiLH(), Types.NVARCHAR));
        // params.add(new SqlParameter("P_NGAY_TN", item.getNgay, java.sql.Types.DATE));
        params.add(new SqlParameter("P_DONVI_TN", AppRequestContext.getCurrent().getDonViID(), Types.INTEGER));
        params.add(new SqlParameter("P_NHANVIEN_TN", AppRequestContext.getCurrent().getNhanVienID(),
                Types.INTEGER));
        params.add(new SqlParameter("P_NGAYBD_DUKIEN", item.getNgayBDDuKien(), Types.NVARCHAR));
        params.add(new SqlParameter("P_NGAYKT_DUKIEN", item.getNgayKTDuKien(), Types.NVARCHAR));
        params.add(new SqlParameter("P_ANHCHUP", item.getAnhChup(), Types.NVARCHAR));
        params.add(new SqlParameter("P_PHANLOAI_SC_ID", item.getPhanLoaiSuCoId(), Types.NVARCHAR));
        params.add(new SqlParameter("P_PHANTULOI", item.getPhanTuLoiId(), Types.NVARCHAR));
        // params.add(new SqlParameter("P_NGAY_CN", new Date(), java.sql.Types.DATE));
        params.add(
                new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), Types.NVARCHAR));
        params.add(
                new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), Types.NVARCHAR));
        params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(),
                Types.NVARCHAR));

        dbContext.executeStoredProcedure("QLSC.PKG_XULY_SUCO_MNV.SP_TAO_SUCO_TAM_MNV", params);
        return true;
    }

    public boolean capNhatTinhTrangSuCoMNV(CapNhatTinhTrangSuCoMNVInput item) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                Types.INTEGER));
        params.add(new SqlParameter("P_SUCO_ID", item.getSuCoId(), Types.INTEGER));
        params.add(new SqlParameter("P_NHOMSC_ID", item.getNhomSuCoId(), Types.INTEGER));
        params.add(new SqlParameter("P_TINHTRANG", item.getTinhTrang(), Types.NVARCHAR));
        dbContext.executeStoredProcedure("QLSC.PKG_QUANLY_SUCO.SP_CAPNHAT_TINHTRANG_SUCO_MNV", params);
        return true;
    }

    public boolean capNhatTinhTrangSuCoTempMNV(CapNhatTinhTrangSuCoTempMNVInput item) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                Types.INTEGER));
        params.add(new SqlParameter("P_MA_SC", item.getMaSuCo(), Types.NVARCHAR));
        params.add(new SqlParameter("P_TINHTRANG", item.getTinhTrang(), Types.NVARCHAR));
        dbContext.executeStoredProcedure("QLSC.PKG_QUANLY_SUCO.SP_CAPNHAT_TINHTRANG_SUCO_TEMP_MNV", params);
        return true;
    }

    public boolean taoYeuCauHoTroMNV(TaoYeuCauHoTroMNVInput item) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                Types.INTEGER));
        params.add(new SqlParameter("P_SUCO_ID", item.getSuCoId(), Types.INTEGER));
        params.add(new SqlParameter("P_NHANVIEN_YC", AppRequestContext.getCurrent().getNhanVienID(),
                Types.INTEGER));
        params.add(new SqlParameter("P_BUOCXULY", item.getBuocXuLy(), Types.NVARCHAR));
        params.add(new SqlParameter("P_DONVIXULY", item.getDonViXuLy(), Types.INTEGER));
        params.add(new SqlParameter("P_TINHTRANG", item.getTinhTrang(), Types.NVARCHAR));
        params.add(new SqlParameter("P_NOIDUNG_DE_XUAT", item.getNoiDungDeXuat(), Types.NVARCHAR));
        dbContext.executeStoredProcedure("QLSC.PKG_QUANLY_SUCO.SP_TAO_YEUCAU_HOTRO_MNV", params);
        return true;
    }
}
