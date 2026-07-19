package vn.vnpt.vnptit.qlsc.service.cntt;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.qlsc.dto.cntt.TraCuuSuCoCNTTRequestDTO;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class TraCuuSuCoCNTTService {
    private final DbContext dbContext;

    public TraCuuSuCoCNTTService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object danhMucDichVu() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_CNTT.SP_DANHMUC_DICHVU_CNTT", params);
    }

    public Object danhSachSuCo(TraCuuSuCoCNTTRequestDTO input, Integer phanVungId, Integer nhanVienId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_ma_sc", input.getMaSuCo(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_donvi_id", input.getDonViId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", nhanVienId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ttsc_id", input.getTrangThaiSuCoID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguonsc_id", input.getNguonSuCoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dichvu_id", input.getDichVuId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaihinh_id", input.getLoaiHinhId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tungay_yc", input.getTuNgayYC(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_denngay_yc", input.getDenNgayYC(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_tungay_ht", input.getTuNgayHT(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_denngay_ht", input.getDenNgayHT(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_CNTT.SP_LAY_DS_TIENTRINH_SC_CNTT", params);
    }

    public Object chiTietSuCo(int VSUCO_ID, int phanvungId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("VPHANVUNG_ID", phanvungId, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VSUCO_ID", VSUCO_ID, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> list= (ArrayList<Map<String, Object>>)dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_CNTT.SP_LAY_CHITIET_SC", params);
        return list;
    }

    public Object dsCongBiAnhHuong(int VSUCO_ID) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VSUCO_ID", VSUCO_ID, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_CNTT.SP_LAY_DS_CONG_BI_AH", params);
    }

    public Object dsNhanVienXuLy(int VPHIEU_ID,int VSUCO_PTM_ID) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VPHIEU_ID", VPHIEU_ID, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VSUCO_PTM_ID", VSUCO_PTM_ID, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_CNTT.SP_LAY_DS_NHANVIEN_XL", params);
    }

    public Object dsBaoTon(int VPHIEU_ID) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHIEU_ID", VPHIEU_ID, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("CUR_RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_CNTT.SP_LAY_DS_BAOTON", params);
    }
}
