package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.ThroughputDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vn.vnpt.common.converter.JsonConvert;

@Service
public class ThroughputService {
    private final DbContext dbContext;

    public ThroughputService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object upsertThroughput(ThroughputDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_throughput_id", dto.getThroughputId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tentat", dto.getTenTat(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_mota", dto.getMoTa(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sudung", dto.getSuDung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", dto.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_UPSERT_THROUGHPUT", params);
        for (SqlParameter param : params) {
            if ("p_result".equals(param.getName())) {
                return param.getOutValue();
            }
        }
        return null;
    }

    public List<Map<String, Object>> getDanhSachThroughput() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_GET_THROUGHPUT", params);
    }

    public Object deleteThroughput(Number id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_throughput_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_DELETE_THROUGHPUT", params);
        for (SqlParameter param : params) {
            if ("p_result".equals(param.getName())) {
                return param.getOutValue();
            }
        }
        return null;
    }

    /**
     * Validate dữ liệu từ file trước khi upload
     */
    public Map<String, Object> validateFile(List<Map<String, Object>> jsonDataList) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        try {
            params.add(new SqlParameter("P_JSON", JsonConvert.serialize(jsonDataList), SqlParameter.ParameterDirection.INPUT,
                    Types.VARCHAR));
        } catch (Exception e) {
            throw new AppSqlException(e);
        }

        SqlParameter invalidRows = new SqlParameter("P_INVALID_ROWS", null, SqlParameter.ParameterDirection.OUTPUT,
                Types.NUMERIC);
        SqlParameter errors = new SqlParameter("P_ERROR_MESSAGE", null, SqlParameter.ParameterDirection.OUTPUT,
                Types.VARCHAR);

        params.add(invalidRows);
        params.add(errors);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_CHECK_SAVE_MULTI_THROUGHPUT", params);

        return new HashMap<String, Object>() {
            {
                put("invalidRows", invalidRows.getOutValue());
                put("businessLogicError", errors.getOutValue());
            }
        };
    }

    /**
     * Lưu nhiều bản ghi từ file upload
     */
    public Map<String, Object> saveFile(List<Map<String, Object>> jsonDataList) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        try {
            params.add(new SqlParameter("P_JSON", JsonConvert.serialize(jsonDataList), SqlParameter.ParameterDirection.INPUT,
                    Types.VARCHAR));
        } catch (Exception e) {
            throw new AppSqlException(e);
        }
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        SqlParameter valid_rows = new SqlParameter("P_VALID_ROWS", null, SqlParameter.ParameterDirection.OUTPUT,
                Types.NUMERIC);
        SqlParameter invalid_rows = new SqlParameter("P_INVALID_ROWS", null, SqlParameter.ParameterDirection.OUTPUT,
                Types.NUMERIC);
        SqlParameter errors = new SqlParameter("P_ERROR_MESSAGE", null, SqlParameter.ParameterDirection.OUTPUT,
                Types.VARCHAR);
        params.add(valid_rows);
        params.add(invalid_rows);
        params.add(errors);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_SAVE_MULTI_THROUGHPUT", params);

        Number passNum = (Number) valid_rows.getOutValue();
        Number failNum = (Number) invalid_rows.getOutValue();

        int pass = passNum != null ? passNum.intValue() : 0;
        int fail = failNum != null ? failNum.intValue() : 0;

        Map<String, Object> result = new HashMap<>();
        result.put("successCount", pass);
        result.put("failCount", fail);
        result.put("errorMessage", errors.getOutValue());

        return result;
    }
}
