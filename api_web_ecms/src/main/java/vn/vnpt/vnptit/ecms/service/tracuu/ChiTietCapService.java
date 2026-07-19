package vn.vnpt.vnptit.ecms.service.tracuu;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.danhmuc.CapNhatDslamModule;
import vn.vnpt.vnptit.ecms.dto.danhmuc.ThemDslamModule;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ChiTietCapService {
    private final DbContext dbContext;

    public ChiTietCapService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> ChiTietCapTheoToQuanLy(Integer phanVungId, String dsToQuanLyId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_ds_toql_id", dsToQuanLyId, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TRACUU.SP_CHITIET_CAP_THEO_TOQL", params);
    }
    public Map<String, Object> ChiTietCapTheoToQuanLyPaging(Integer phanVungId, String dsToQuanLyId, Integer pageIndex, Integer pageSize) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_ds_toql_id", dsToQuanLyId, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_page_index", pageIndex, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_page_size", pageSize, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        SqlParameter parameterCursor = new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        params.add(parameterCursor);
        SqlParameter parameterTotalRow = new SqlParameter("p_total_row", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.NUMERIC);
        params.add(parameterTotalRow);

        dbContext.executeStoredProcedure("ECMS.PKG_TRACUU.SP_CHITIET_CAP_THEO_TOQL_PAGING", params);
        Map<String, Object> result = new HashMap<>();
        result.put("pageItems", parameterCursor.getOutValue());
        result.put("allItems", parameterTotalRow.getOutValue());
        return result;
    }
}
