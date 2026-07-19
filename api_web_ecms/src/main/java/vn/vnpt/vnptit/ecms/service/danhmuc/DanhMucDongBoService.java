package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DanhMucDongBoService {
    private final DbContext dbContext;

    public DanhMucDongBoService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public List<Map<String, Object>> GetListDMDongBo(String ten) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_DONGBO", ten == null ? "" : ten.trim(), Types.VARCHAR));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_DANHSACHDONGBO.SP_GET_LIST_DS_DONGBO", params);
    }

    public Map<String, Object> InsertDMDongBo(String ten) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_DONGBO", ten.trim(), Types.VARCHAR));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_DANHSACHDONGBO.SP_INSERT_DS_DONGBO", params);
        return rs.size() > 0 ? rs.get(0) : null;
    }

    public Map<String, Object> UpdateDMDongBo(int id, String ten) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_DONGBO_ID", id, Types.NUMERIC));
        params.add(new SqlParameter("P_DONGBO", ten.trim(), Types.VARCHAR));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_DANHSACHDONGBO.SP_UPDATE_DS_DONGBO", params);
        return rs.get(0);
    }

    public Map<String, Object> DeleteDMDongBo(int id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_DONGBO_ID", id, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_DANHSACHDONGBO.SP_DELETE_DS_DONGBO", params);
        if(Integer.parseInt(rs.get(0).get("KETQUA").toString()) == 1) {
            rs.get(0).put("KETQUA", true);
        }
        else {
            rs.get(0).put("KETQUA", false);
        }
        return rs.get(0);
    }

    public Map<String, Object> GetDMDongBo(int id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_DONGBO_ID", id, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_DANHSACHDONGBO.SP_GET_DS_DONGBO_BY_ID", params);
        return rs.size() > 0 ? rs.get(0) : null;
    }
}
