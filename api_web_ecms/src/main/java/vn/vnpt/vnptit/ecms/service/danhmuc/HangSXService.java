package vn.vnpt.vnptit.ecms.service.danhmuc;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.danhmuc.ThemDanhMuc;
import vn.vnpt.vnptit.ecms.dto.danhmuc.CapNhatDanhMuc;

@Service
public class HangSXService {

    private final DbContext dbContext;

    public HangSXService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> DsHangSX() throws AppSqlException {
        SqlParameter param = new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_GET_DS_HANG_SX", param);
    }

    public ArrayList<Map<String, Object>> DsHangSX(String name) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_name", name, ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_GET_DS_HANG_SX", params);
    }

    public Map<String, Object> ChiTietHangSX(Integer id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_GET_HANG_SX", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public Map<String, Object> ThemHangSX(ThemDanhMuc input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_name", input.getName(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_HANG_SX_INS", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public Map<String, Object> CapNhatHangSX(CapNhatDanhMuc input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", input.getId().toString(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_name", input.getName(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_HANG_SX_UPD", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public Boolean XoaHangSX(Integer id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_HANG_SX_DEL", params);
        return true;
    }
}
