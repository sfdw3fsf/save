package vn.vnpt.vnptit.ecms.service.idc;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.idc.ToaDoMatsanDto;
import vn.vnpt.context.AppRequestContext;

@Service
public class ToadoMatsanService {

    private final DbContext dbContext;

    public ToadoMatsanService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> DsIDC() throws AppSqlException {
        SqlParameter param = new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_DS_IDC", param);
    }

    public ArrayList<Map<String, Object>> DsToaNha() throws AppSqlException {
        SqlParameter param = new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_DS_TOA_NHA", param);
    }

    public ArrayList<Map<String, Object>> DsMatsan() throws AppSqlException {
        SqlParameter param = new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_DS_MATSAN", param);
    }

    public ArrayList<Map<String, Object>> DsToadoMatsan(String name) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_NAME", name, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_DS_TOADO_MATSAN", params);
    }

    public Map<String, Object> ThemToadoMatsan(ToaDoMatsanDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_MATSAN_ID", input.getMatsanId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_MATSAN_TEN", input.getMatsanTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TRUC_NGANG", input.getTrucNgang(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TRUC_DOC", input.getTrucDoc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_GHI_CHU", input.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_HIEULUC", input.getHieuLuc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_TOADO_MATSAN_INS", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public Map<String, Object> ChiTietToadoMatsan(Integer id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_ID", id, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_TOADO_MATSAN", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public Map<String, Object> CapNhatToadoMatsan(ToaDoMatsanDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_ID", input.getToadoMatsanId().toString(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NAME", input.getToadoMatsanTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_MATSAN_ID", input.getMatsanId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TRUC_NGANG", input.getTrucNgang(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TRUC_DOC", input.getTrucDoc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_GHI_CHU", input.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_HIEULUC", input.getHieuLuc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_RS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_TOADO_MATSAN_UPD", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public Integer XoaToadoMatsan(Integer id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
         ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_TOADO_MATSAN_DEL", params);
        if (result != null && !result.isEmpty()) {
            Object val = result.get(0).get("KQ");
            return val != null ? Integer.parseInt(val.toString()) : 0;
        }
        return 0;
    }

    public Map<String, Object> importToadoMatsan(List<ToaDoMatsanDto> inputs) {
        int ok = 0, fail = 0;
        List<Map<String, Object>> errorList = new ArrayList<>();

        for (ToaDoMatsanDto input : inputs) {
            try {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("P_MATSAN_ID",  input.getMatsanId(),       SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_MATSAN_TEN",  input.getMatsanTen(),       SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_TRUC_NGANG", input.getTrucNgang(),      SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_TRUC_DOC",   input.getTrucDoc(),        SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_GHI_CHU",    input.getGhiChu(),         SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_HIEULUC",    input.getHieuLuc(),        SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
                dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_TOADO_MATSAN_INS", params);

                ok++;
            } catch (Exception ex) {
                fail++;
                Map<String, Object> err = new HashMap<>();
                err.put("input", input);
                err.put("errorMessage", extractErrorMessage(ex));
                errorList.add(err);
            }
        }

        Map<String, Object> summary = new HashMap<>();
        summary.put("total", inputs.size());
        summary.put("success", ok);
        summary.put("failed", fail);
        summary.put("errors", errorList);
        return summary;
    }

    @java.lang.SuppressWarnings("squid:S4784")
    private String extractErrorMessage(Exception ex) {
        String msg = ex.getMessage();
        if (msg == null) return "Lỗi không xác định";

        Pattern p = Pattern.compile("ORA-\\d+: (.+)");
        Matcher m = p.matcher(msg);
        if (m.find()) {
            String shortMsg = m.group(1).trim();
            int nl = shortMsg.indexOf("\n");
            if (nl > 0) {
                shortMsg = shortMsg.substring(0, nl).trim();
            }
            return shortMsg;
        }

        return msg.split("\n")[0].trim();
    }

}
