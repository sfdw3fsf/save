package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.stereotype.Service;
import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.DanhMucDonViSHBatchResponseDto;
import vn.vnpt.vnptit.ecms.dto.hatang.DanhMucDonViSHDto;
import vn.vnpt.vnptit.ecms.dto.hatang.DanhMucDonViSHValidateResponseDto;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DanhMucDonViSHService {

    private final DbContext dbContext;

    public DanhMucDonViSHService(DbContext dbContext) {this.dbContext= dbContext;}

    public ArrayList<Map<String,Object>> getDonViSH() throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result",null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TEST_HUY.SP_GET_DON_VI",params);
    }

    public Map<String, Object> addDonVi(DanhMucDonViSHDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ten", dto.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tentat", dto.getTenTat(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_mota", dto.getMoTa(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sudung", dto.getSuDung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ghichu", dto.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToMap("ECMS.PKG_TEST_HUY.SP_ADD_DONVI_SH", params);
    }

    public Map<String, Object> updateDonVi(DanhMucDonViSHDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getDonViSH_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tentat", dto.getTenTat(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_mota", dto.getMoTa(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sudung", dto.getSuDung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ghichu", dto.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToMap("ECMS.PKG_TEST_HUY.SP_UPDATE_DON_VI_SH", params);
    }

    public Map<String, Object> deleteDonVi(Number id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));


        return dbContext.executeSpWithCursorToMap("ECMS.PKG_TEST_HUY.SP_DELETE_DON_VI_SH", params);
    }

    public DanhMucDonViSHBatchResponseDto batchInsertDonVi(List<DanhMucDonViSHDto> danhSachDonVi) throws AppSqlException {
        try {
            String jsonData = JsonConvert.serialize(danhSachDonVi);
            String nguoiCn = AppRequestContext.getCurrent().getUserName();

            ArrayList<SqlParameter> parameters = new ArrayList<>();
            parameters.add(new SqlParameter("p_json_data", jsonData, SqlParameter.ParameterDirection.INPUT, Types.CLOB));
            parameters.add(new SqlParameter("p_nguoi_cn", nguoiCn, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            parameters.add(new SqlParameter("p_success_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("p_failed_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

            dbContext.executeStoredProcedure("ECMS.PKG_TEST_HUY.SP_DONVI_SH_MULTIPLE_INSERT", parameters);

            Integer successCount = null;
            Integer failedCount = null;
            Integer result = null;

            for (SqlParameter param : parameters) {
                if ("p_success_count".equals(param.getName()) && param.getOutValue() != null)
                    successCount = Integer.valueOf(String.valueOf(param.getOutValue()));
                if ("p_failed_count".equals(param.getName()) && param.getOutValue() != null)
                    failedCount = Integer.valueOf(String.valueOf(param.getOutValue()));
                if ("p_result".equals(param.getName()) && param.getOutValue() != null)
                    result = Integer.valueOf(String.valueOf(param.getOutValue()));
            }

            return new DanhMucDonViSHBatchResponseDto(successCount, failedCount, result);

        } catch (IOException e) {
            throw new AppSqlException(e);
        }
    }

    public DanhMucDonViSHValidateResponseDto validateBatchDonVi(List<DanhMucDonViSHDto> danhSachDonVi) throws AppSqlException {
        try {
            String jsonData = JsonConvert.serialize(danhSachDonVi);

            ArrayList<SqlParameter> parameters = new ArrayList<>();
            parameters.add(new SqlParameter("p_json_data", jsonData, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            parameters.add(new SqlParameter("p_error_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("p_error_message", null, SqlParameter.ParameterDirection.OUTPUT, Types.NVARCHAR));

            dbContext.executeStoredProcedure("ECMS.PKG_TEST_HUY.SP_DONVI_SH_VALIDATE_BATCH", parameters);

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

            return new DanhMucDonViSHValidateResponseDto(errorCount, errorMessage);

        } catch (IOException e) {
            throw new AppSqlException(e);
        }
    }
}
