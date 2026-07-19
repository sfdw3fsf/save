package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.LoaiEWSDtoIn;
import vn.vnpt.vnptit.ecms.dto.LoaiEWSUpdDtoIn;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class LoaiEWSDService {
    private final DbContext dbContext;

    public LoaiEWSDService(DbContext dbContext){
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> DsLoaiEWSD() throws AppSqlException {
        SqlParameter param = new SqlParameter("p_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAIEWSD.SP_DS_LOAI_EWSD_SEL", param);
    }

    public Map<String, Object> DsLoaiEWSDTheoID(Long p_loai_ewsd_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_loai_ewsd_id", String.valueOf(p_loai_ewsd_id), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAIEWSD.SP_LOAI_EWSD_SEL_ID", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public Map<String, Object> themLoaiEWSD(LoaiEWSDtoIn loaiEWSDtoIn) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_loai_ewsd", loaiEWSDtoIn.getLoai_ewsd().toString(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAIEWSD.SP_LOAI_EWSD_INS", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public Map<String, Object> capnhatLoaiEWSD(LoaiEWSUpdDtoIn loaiEWSDtoIn) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_loai_ewsd_id", loaiEWSDtoIn.getLoai_ewsd_id().toString(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loai_ewsd", loaiEWSDtoIn.getLoai_ewsd().toString(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAIEWSD.SP_LOAI_EWSD_UPD", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public ArrayList<Map<String, Object>> xoaLoaiEWSD(Long p_loai_ewsd_id) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_loai_ewsd_id", String.valueOf(p_loai_ewsd_id), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAIEWSD.SP_LOAI_EWSD_DEL", params);
    }

    public boolean xoa_loai_ewsd(String id) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loai_ewsd_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR,int.class));
        ArrayList<?> result = dbContext.executeSpWithCursorToEntities("ECMS.PKG_LOAIEWSD.SP_LOAI_EWSD_DEL", params);
        if (!(result == null || result.size() == 0)) {
            return Integer.parseInt(result.get(0).toString()) > 0;
        }
        return false;
    }
}
