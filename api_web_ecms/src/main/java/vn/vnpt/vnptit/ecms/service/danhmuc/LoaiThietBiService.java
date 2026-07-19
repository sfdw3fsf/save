package vn.vnpt.vnptit.ecms.service.danhmuc;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.SqlParameter;
import org.springframework.stereotype.Service;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.vnptit.ecms.dto.LoaiThietBiInput;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class LoaiThietBiService {
    private final DbContext dbContext;
    public LoaiThietBiService(DbContext dbContext)
    {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> lietke_loai_thieti() throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_LOAITHIETBI.SP_LOAI_THIET_BI_SEL", params);
    }
    public Map<String, Object> lay_loai_thietbi_id(int id) throws AppSqlException
    {
        ArrayList<Map<String, Object>> result  = new ArrayList<>();
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_ID", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        result=  dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_LOAITHIETBI.SP_LOAI_THIET_BI_BY_ID_SEL", params);
        return result != null && result.size()>0 ? result.get(0):null;
    }
    public Map<String, Object> them_loai_thietbi(LoaiThietBiInput.LoaiTbiIns loaiThietBiInput) throws AppSqlException
    {
        ArrayList<Map<String, Object>> result  = new ArrayList<>();
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAI_TBI", loaiThietBiInput.getLoaiTbi(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_GHI_CHU", loaiThietBiInput.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_CUR", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        result = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_LOAITHIETBI.SP_LOAI_THIET_BI_INS", params);
        return result != null && result.size()>0 ? result.get(0):null;
    }
    public Map<String, Object> sua_loai_thietbi(LoaiThietBiInput.LoaiTbiUpd loaiThietBiInput) throws AppSqlException
    {
        ArrayList<Map<String, Object>> result  = new ArrayList<>();
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAI_TBI_ID", loaiThietBiInput.getLoaiTbiId().toString(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAI_TBI", loaiThietBiInput.getLoaiTbi(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_GHI_CHU", loaiThietBiInput.getGhiChu(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_CUR", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        result =  dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_LOAITHIETBI.SP_LOAI_THIET_BI_UPD", params);
        return result != null && result.size()>0 ? result.get(0):null;
    }
    public boolean xoa_loai_tbi(int id, int phanvung_id) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_ID", id, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR,int.class));
        ArrayList<?> result = dbContext.executeSpWithCursorToEntities("ECMS.PKG_LOAITHIETBI.SP_LOAI_THIET_BI_DEL", params);
        if (!(result == null || result.size() == 0)) {
            return Integer.parseInt(result.get(0).toString()) > 0;
        }
        return false;
    }
}