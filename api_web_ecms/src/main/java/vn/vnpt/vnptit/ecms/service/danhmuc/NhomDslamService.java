package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.danhmuc.CapNhatDanhMuc;
import vn.vnpt.vnptit.ecms.dto.danhmuc.ThemDanhMuc;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class NhomDslamService {
    private final DbContext dbContext;

    public NhomDslamService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> DsNhomDslam() throws AppSqlException {
        SqlParameter param = new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_GET_DS_NHOM_DSLAM", param);
    }

    public ArrayList<Map<String, Object>> DsNhomDslam(String name) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_name", name, ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_GET_DS_NHOM_DSLAM", params);
    }

    public Map<String, Object> ChiTietNhomDslam(String id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_GET_NHOM_DSLAM", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public Map<String, Object> ThemNhomDslam(ThemDanhMuc input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_name", input.getName(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_NHOM_DSLAM_INS", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public Map<String, Object> CapNhatNhomDslam(CapNhatDanhMuc input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", input.getId().toString(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_name", input.getName(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_NHOM_DSLAM_UPD", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public Boolean XoaNhomDslam(String id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_NHOM_DSLAM_DEL", params);
        return  true;
    }
}
