package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.stereotype.Service;
import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.*;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class DanhMucLoaiOCungService {
    private final DbContext dbContext;

    public DanhMucLoaiOCungService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Map<String, Object> upsertLoaiOCung(DanhMucLoaiOCungDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tentat", dto.getTenTat(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_mota", dto.getMoTa(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sudung", dto.getSuDung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", dto.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN.SP_UPSERT_LOAI_OCUNG", params);
    }

    public ArrayList<Map<String, Object>> getLoaiOCung() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_LOAI_OCUNG", params);
    }


    public Map<String, Object> deleteLoaiOCung(Number id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN.SP_DELETE_LOAI_OCUNG", params);
    }


    public DanhMucLoaiOCungBatchResponseDto batchInsertLoaiOCung(java.util.List<DanhMucLoaiOCungDto> dto) throws AppSqlException {
        try {
            String jsonData = JsonConvert.serialize(dto);
            String nguoiCn = AppRequestContext.getCurrent().getUserName();

            ArrayList<SqlParameter> parameters = new ArrayList<>();
            parameters.add(new SqlParameter("p_json_data", jsonData, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            parameters.add(new SqlParameter("p_nguoi_cn", nguoiCn, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            parameters.add(new SqlParameter("p_success_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("p_failed_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_LOAI_OCUNG_BATCH_INSERT", parameters);

            // Lấy giá trị từ output parameters
            Integer successCount = null;
            Integer failedCount = null;
            Integer result = null;

            for (SqlParameter param : parameters) {
                if ("p_success_count".equals(param.getName()) && param.getOutValue() != null) {
                    successCount = Integer.valueOf(String.valueOf(param.getOutValue()));
                }
                if ("p_failed_count".equals(param.getName()) && param.getOutValue() != null) {
                    failedCount = Integer.valueOf(String.valueOf(param.getOutValue()));
                }
                if ("p_result".equals(param.getName()) && param.getOutValue() != null) {
                    result = Integer.valueOf(String.valueOf(param.getOutValue()));
                }
            }

            return new DanhMucLoaiOCungBatchResponseDto(successCount, failedCount, result);

        } catch (IOException e) {
            throw new AppSqlException(e);
        }
    }
    public DanhMucLoaiOCungValidateResponseDTO validateBatchLoaiOCung(java.util.List<DanhMucLoaiOCungDto> dto) throws AppSqlException {
        try {
            String jsonData = JsonConvert.serialize(dto);

            ArrayList<SqlParameter> parameters = new ArrayList<>();
            parameters.add(new SqlParameter("p_json_data", jsonData, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            parameters.add(new SqlParameter("p_error_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("p_error_message", null, SqlParameter.ParameterDirection.OUTPUT, Types.NVARCHAR));

            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_LOAI_OCUNG_VALIDATE_BATCH", parameters);

            // Lấy giá trị từ output parameters
            Integer errorCount = null;
            String errorMessage = null;

            for (SqlParameter param : parameters) {
                if ("p_error_count".equals(param.getName()) && param.getOutValue() != null) {
                    errorCount = Integer.valueOf(String.valueOf(param.getOutValue()));
                }
                if ("p_error_message".equals(param.getName()) && param.getOutValue() != null) {
                    errorMessage = String.valueOf(param.getOutValue());
                }
            }

            return new DanhMucLoaiOCungValidateResponseDTO(errorCount, errorMessage);

        } catch (IOException e) {
            throw new AppSqlException(e);
        }
    }

}
