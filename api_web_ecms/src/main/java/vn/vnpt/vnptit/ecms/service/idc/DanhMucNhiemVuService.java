package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucNhiemVuDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DanhMucNhiemVuService {
    @Autowired
    private DbContext dbContext;

    public ArrayList<Map<String, Object>> getDanhSachNhiemVu() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
    return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_GET_ALL_NHIEMVU", params);
    }

    public Object upsertDanhMucNhiemVu(DanhMucNhiemVuDto dto) throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_ID", dto.getNhiemVuId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TEN", dto.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TENTAT",
                dto.getTenTat(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        params.add(new SqlParameter("P_MOTA",
                dto.getMoTa(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        params.add(new SqlParameter("P_GHICHU",
                dto.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        params.add(new SqlParameter("P_SUDUNG",
                dto.getSuDung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        params.add(new SqlParameter("P_NGUOI_CN",
                AppRequestContext.getCurrent().getUserName(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        params.add(new SqlParameter("P_RS", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_UPSERT_NHIEMVU", params);
    }

    public Map<String, Object> batchInsertDanhMucNhiemVu(List<Map<String, Object>> danhSachIdg) throws AppSqlException {
        try {
            String jsonData = JsonConvert.serialize(danhSachIdg);
            String nguoiCn = AppRequestContext.getCurrent().getUserName();

            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_json_data", jsonData, SqlParameter.ParameterDirection.INPUT, Types.CLOB));
            params.add(new SqlParameter("p_nguoi_cn",
                    AppRequestContext.getCurrent().getUserName(),
                    SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

            params.add(new SqlParameter("p_success_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_failed_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_DANHMUC_NHIEMVU_MULTIPLE_INSERT", params);

            Integer successCount = 0;
            Integer failedCount = 0;
            Integer result = 0;

            for (SqlParameter p : params) {
                if ("p_success_count".equalsIgnoreCase(p.getName()) && p.getOutValue() != null)
                    successCount = Integer.valueOf(String.valueOf(p.getOutValue()));

                if ("p_failed_count".equalsIgnoreCase(p.getName()) && p.getOutValue() != null)
                    failedCount = Integer.valueOf(String.valueOf(p.getOutValue()));

                if ("p_result".equalsIgnoreCase(p.getName()) && p.getOutValue() != null)
                    result = Integer.valueOf(String.valueOf(p.getOutValue()));
            }

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("successCount", successCount);
            response.put("failedCount", failedCount);
            response.put("result", result);

            return response;

        } catch (Exception e) {
            throw new AppSqlException(e);
        }
    }

    public Map<String, Object> validateBatchDanhMucNhiemVu(List<Map<String, Object>> danhSachNhiemVu) throws AppSqlException {
        try {
            String jsonData = JsonConvert.serialize(danhSachNhiemVu);

            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_json_data", jsonData, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            params.add(new SqlParameter("p_error_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_error_message", null, SqlParameter.ParameterDirection.OUTPUT, Types.NVARCHAR));

            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_DANHMUC_NHIEMVU_VALIDATE_BATCH", params);

            Integer errorCount = 0;
            String errorMessage = "";

            for (SqlParameter p : params) {
                if ("p_error_count".equalsIgnoreCase(p.getName()) && p.getOutValue() != null)
                    errorCount = Integer.valueOf(String.valueOf(p.getOutValue()));

                if ("p_error_message".equalsIgnoreCase(p.getName()) && p.getOutValue() != null)
                    errorMessage = String.valueOf(p.getOutValue());
            }

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("errorCount", errorCount);
            response.put("errorMessage", errorMessage);

            return response;

        } catch (Exception e) {
            throw new AppSqlException(e);
        }
    }

    public Map<String, Object> deleteNhiemVu(Number id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_DELETE_DANHMUC_NHIEMVU", params);
    }


}
