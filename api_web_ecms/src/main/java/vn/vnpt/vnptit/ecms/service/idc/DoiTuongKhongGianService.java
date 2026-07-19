package vn.vnpt.vnptit.ecms.service.idc;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.DoiTuongKhongGianDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ToaDoDoiTuongDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ToaDoDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ToaDoDoiTuongDto;

@Service
public class DoiTuongKhongGianService {
    private final DbContext dbContext;

    public DoiTuongKhongGianService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object upsertDoiTuongKhongGian(DoiTuongKhongGianDto item) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_loai_doituong", item.getLoaiDoiTuong(), Types.VARCHAR));
        params.add(new SqlParameter("p_doituong_id", item.getDoiTuongId(), Types.NUMERIC));
        params.add(new SqlParameter("p_doituong_cha_id", item.getDoiTuongChaId(), Types.NUMERIC));
        params.add(new SqlParameter("p_ten", item.getTen(), Types.VARCHAR));
        params.add(new SqlParameter("p_tentat", item.getTenTat(), Types.VARCHAR));
        params.add(new SqlParameter("p_chieudai", item.getChieuDai(), Types.NUMERIC));
        params.add(new SqlParameter("p_chieurong", item.getChieuRong(), Types.NUMERIC));
        params.add(new SqlParameter("p_toado_x", item.getToaDoX(), Types.NUMERIC));
        params.add(new SqlParameter("p_toado_y", item.getToaDoY(), Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", item.getGhiChu(), Types.VARCHAR));
        params.add(new SqlParameter("p_mota", item.getMoTa(), Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn",
                AppRequestContext.getCurrent().getUserName() != null ? AppRequestContext.getCurrent().getUserName()
                        : "",
                Types.VARCHAR));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_KHONGGIAN.SP_UPSERT_DOITUONG_KHONGGIAN", params);
        return result.getOutValue();
    }

    public Object deleteDoiTuongKhongGian(DoiTuongKhongGianDto item) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_loai_doituong", item.getLoaiDoiTuong(), Types.VARCHAR));
        params.add(new SqlParameter("p_doituong_id", item.getDoiTuongId(), Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_KHONGGIAN.SP_DELETE_DOITUONG_KHONGGIAN", params);
        return result.getOutValue();
    }

    public ArrayList<Map<String, Object>> getDsDoiTuongKhongGian(DoiTuongKhongGianDto item) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_loai_doituong", item.getLoaiDoiTuong(), Types.VARCHAR));
        params.add(new SqlParameter("p_doituong_cha_id", item.getDoiTuongChaId(), Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_KHONGGIAN.SP_GET_ALL_DOITUONG_KHONGGIAN",
                params);
    }

    public ArrayList<Map<String, Object>> getDsDoiTuongChuaLapDat(DoiTuongKhongGianDto item) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_loai_doituong", item.getLoaiDoiTuong(), Types.VARCHAR));
        params.add(new SqlParameter("p_doituong_cha_id", item.getDoiTuongChaId(), Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_KHONGGIAN.SP_GET_DS_DOITUONG_CHUA_LAPDAT",
                params);
    }

    public ArrayList<Map<String, Object>> getDsDoiTuongDaLapDat(DoiTuongKhongGianDto item) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_loai_doituong", item.getLoaiDoiTuong(), Types.VARCHAR));
        params.add(new SqlParameter("p_doituong_cha_id", item.getDoiTuongChaId(), Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_KHONGGIAN.SP_GET_DS_DOITUONG_DA_LAPDAT", params);
    }

    public Object updateVitriDoiTuongKhongGian(DoiTuongKhongGianDto item) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_loai_doituong", item.getLoaiDoiTuong(), Types.VARCHAR));
        params.add(new SqlParameter("p_doituong_id", item.getDoiTuongId(), Types.NUMERIC));
        params.add(new SqlParameter("p_toado_x", item.getToaDoX(), Types.NUMERIC));
        params.add(new SqlParameter("p_toado_y", item.getToaDoY(), Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn",
                AppRequestContext.getCurrent().getUserName() != null ? AppRequestContext.getCurrent().getUserName()
                        : "",
                Types.VARCHAR));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_KHONGGIAN.SP_UPDATE_VITRI_DOITUONG", params);
        return result.getOutValue();
    }

    public Object getToaDoDoiTuong(DoiTuongKhongGianDto item) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_loai_doituong", item.getLoaiDoiTuong(), Types.VARCHAR));
        params.add(new SqlParameter("p_doituong_id", item.getDoiTuongId(), Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_KHONGGIAN.SP_TOADO_KHONGGIAN_GET", params);
    }

    public Object insertToaDoDoiTuong(ToaDoDoiTuongDto toaDoDoiTuong) throws AppSqlException {
        return this.insertToaDoDoiTuong(toaDoDoiTuong.getLoaiDoiTuong(), toaDoDoiTuong.getDoiTuongID(),
                toaDoDoiTuong.getDsToaDo());
    }

    public Object insertToaDoDoiTuong(String loaiDoiTuong, Number DoiTuongID, ToaDoDto[] dsToaDo)
            throws AppSqlException {
        ArrayList<Object> result = new ArrayList<Object>();
        for (int i = 0; i < dsToaDo.length; i++) {
            result.add(
                    this.insertToaDoDoiTuong(loaiDoiTuong, DoiTuongID, dsToaDo[i].getToaDoX(), dsToaDo[i].getToaDoY(),
                            i + 1, null));
        }
        return result;
    }

    public Object insertToaDoDoiTuong(String loaiDoiTuong, Number DoiTuongID, ToaDoDto toaDo) throws AppSqlException {
        return this.insertToaDoDoiTuong(loaiDoiTuong, DoiTuongID, toaDo, null);
    }

    public Object insertToaDoDoiTuong(String loaiDoiTuong, Number DoiTuongID, ToaDoDto toaDo, Number thuTu)
            throws AppSqlException {
        return this.insertToaDoDoiTuong(loaiDoiTuong, DoiTuongID, toaDo.getToaDoX(), toaDo.getToaDoY(), thuTu, null);
    }

    public Object insertToaDoDoiTuong(String loaiDoiTuong, Number DoiTuongID, Number toaDoX, Number toaDoY,
            Number thuTu, String moTa)
            throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_loai_doituong", loaiDoiTuong, Types.VARCHAR));
        params.add(new SqlParameter("p_doituong_id", DoiTuongID, Types.NUMERIC));
        params.add(new SqlParameter("p_toado_x", toaDoX, Types.NUMERIC));
        params.add(new SqlParameter("p_toado_y", toaDoY, Types.NUMERIC));
        params.add(new SqlParameter("p_thutu", thuTu, Types.NUMERIC));
        params.add(new SqlParameter("p_mota", moTa, Types.NVARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.NVARCHAR));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_KHONGGIAN.SP_TOADO_DOITUONG_INSERT", params);
        return result.getOutValue();
    }

    public Object deleteToaDoDoiTuong(String loaiDoiTuong, Number DoiTuongID) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_loai_doituong", loaiDoiTuong, Types.VARCHAR));
        params.add(new SqlParameter("p_doituong_id", DoiTuongID, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_KHONGGIAN.SP_TOADO_DOITUONG_DELETE", params);
        return result.getOutValue();
    }

    public Object updateToaDoDoiTuong(ToaDoDoiTuongDto toaDoDoiTuong) throws AppSqlException {
        this.deleteToaDoDoiTuong(toaDoDoiTuong.getLoaiDoiTuong(), toaDoDoiTuong.getDoiTuongID());
        return this.insertToaDoDoiTuong(toaDoDoiTuong.getLoaiDoiTuong(), toaDoDoiTuong.getDoiTuongID(),
                toaDoDoiTuong.getDsToaDo());
    }

    public ArrayList<Map<String, Object>> getDsThietBiChuaLapDat(DoiTuongKhongGianDto item) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", item.getDoiTuongId(), Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_KHONGGIAN.SP_GET_THIETBI_CLD", params);
    }
}
