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
public class DanhMucLopKNService {
    private final DbContext dbContext;

    public DanhMucLopKNService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Map<String, Object> upsertLopKN(DanhMucLopKNDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_lopkn_id", dto.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_doituong", dto.getDoiTuong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_thutu", dto.getThuTu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hieuluc", dto.getSuDung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ghichu", dto.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_UPSERT_LOPKN", params);
    }

    public Object getDanhmucLopKN(String loaiDanhmuc, Number thamSo1, Number thamSo2) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(
                new SqlParameter("p_loai_danhmuc", loaiDanhmuc, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tham_so1", thamSo1, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tham_so2", thamSo2, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_GET_DANHMUC_LOPKN", params);
    }


    public Map<String, Object> deleteLopKN(Number id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_DELETE_LOPKN", params);
    }


    public DanhMucChungBatchResponseDto batchInsertLopKN(java.util.List<DanhMucLopKNDto> danhSachLopKN) throws AppSqlException {
        try {
            String jsonData = JsonConvert.serialize(danhSachLopKN);
            String nguoiCn = AppRequestContext.getCurrent().getUserName();

            ArrayList<SqlParameter> parameters = new ArrayList<>();
            parameters.add(new SqlParameter("p_json_data", jsonData, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            parameters.add(new SqlParameter("p_nguoi_cn", nguoiCn, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            parameters.add(new SqlParameter("p_success_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("p_failed_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_LOP_KN_BATCH_INSERT", parameters);

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

            return new DanhMucChungBatchResponseDto(successCount, failedCount, result);

        } catch (IOException e) {
            throw new AppSqlException(e);
        }
    }
    public DanhMucChungValidateResponseDTO validateBatchLopKN(java.util.List<DanhMucLopKNDto> danhSachLopKN) throws AppSqlException {
        try {
            String jsonData = JsonConvert.serialize(danhSachLopKN);

            ArrayList<SqlParameter> parameters = new ArrayList<>();
            parameters.add(new SqlParameter("p_json_data", jsonData, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            parameters.add(new SqlParameter("p_error_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("p_error_message", null, SqlParameter.ParameterDirection.OUTPUT, Types.NVARCHAR));

            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_LOP_KN_VALIDATE_BATCH", parameters);

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

            return new DanhMucChungValidateResponseDTO(errorCount, errorMessage);

        } catch (IOException e) {
            throw new AppSqlException(e);
        }
    }

}
