package vn.vnpt.vnptit.ecms.service.hatang;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.DanhMucIOPSBatchResponseDto;
import vn.vnpt.vnptit.ecms.dto.hatang.DanhMucIOPSValidateResponseDto;
import vn.vnpt.vnptit.ecms.dto.hatang.GoiDichVuIOPSDto;

@Service
public class DichVuIOPSService {
    private final DbContext dbContext;

    public DichVuIOPSService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getItems() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.SP_DICHVU_IOPS_GET_ALL", params);
    }

    public Object getItem(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_HATANG_DANHMUC.SP_DICHVU_IOPS_GET_ITEM", params);
    }

    public Object insert(String ten, Number giaTri, String ghiChu, Number hieuLuc) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ten", ten, Types.NVARCHAR));
        params.add(new SqlParameter("p_giatri", giaTri, Types.NUMERIC));
        params.add(new SqlParameter("p_ghi_chu", ghiChu, Types.NVARCHAR));
        params.add(new SqlParameter("p_hieu_luc", hieuLuc, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC.SP_DICHVU_IOPS_INSERT", params);
        return result.getOutValue();
    }

    public boolean update(Number id, String ten, Number giaTri, String ghiChu, Number hieuLuc) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", ten, Types.NVARCHAR));
        params.add(new SqlParameter("p_giatri", giaTri, Types.NUMERIC));
        params.add(new SqlParameter("p_ghi_chu", ghiChu, Types.NVARCHAR));
        params.add(new SqlParameter("p_hieu_luc", hieuLuc, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC.SP_DICHVU_IOPS_UPDATE", params);
        return result.getOutValue().toString().equals("1");
    }

    public boolean delete(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC.SP_DICHVU_IOPS_DELETE", params);
        return result.getOutValue().toString().equals("1");
    }

    public boolean deleteItems(Number[] ids) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ids",  StringUtils.join(ids, ";"), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC.SP_DICHVU_IOPS_DELETE_ITEMS", params);
        return result.getOutValue().toString().equals("1");
    }

    public Object getListIOPS() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.SP_DS_DICHVU_IOPS", params);
    }

    public Object upsert(GoiDichVuIOPSDto input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_IOPS_ID", input.getIopsId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TEN", input.getTen(), SqlParameter.ParameterDirection.INPUT,Types.NVARCHAR));
        params.add(new SqlParameter("P_TENTAT", input.getTenTat(),SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_MOTA", input.getMoTa(),SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_SUDUNG", input.getSuDung(),SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_GHICHU", input.getGhiChu(),SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));

        SqlParameter result = new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);

        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC.SP_DICHVU_IOPS_UPSERT", params);
        return result.getOutValue();
    }

    public boolean deleteIOPS(Number[] ids) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ids",  StringUtils.join(ids, ";"), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC.SP_DELETE_DICHVU_IOPS", params);
        return result.getOutValue().toString().equals("1");
    }

    public DanhMucIOPSBatchResponseDto batchInsert(List<GoiDichVuIOPSDto> danhSach)
            throws AppSqlException {
        try {
            String jsonData = JsonConvert.serialize(danhSach);
            String nguoiCn = AppRequestContext.getCurrent().getUserName();

            ArrayList<SqlParameter> parameters = new ArrayList<>();
            parameters.add(
                    new SqlParameter("p_json_data", jsonData, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            parameters.add(
                    new SqlParameter("p_nguoi_cn", nguoiCn, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            parameters.add(
                    new SqlParameter("p_success_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            parameters.add(
                    new SqlParameter("p_failed_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

            dbContext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC.SP_IOPS_BATCH_INSERT", parameters);

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

            return new DanhMucIOPSBatchResponseDto(successCount, failedCount, result);

        } catch (IOException e) {
            throw new AppSqlException(e);
        }
    }

    public DanhMucIOPSValidateResponseDto validateBatch(
            List<GoiDichVuIOPSDto> danhSach) throws AppSqlException {
        try {
            String jsonData = JsonConvert.serialize(danhSach);

            ArrayList<SqlParameter> parameters = new ArrayList<>();
            parameters.add(
                    new SqlParameter("p_json_data", jsonData, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            parameters.add(
                    new SqlParameter("p_error_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            parameters.add(
                    new SqlParameter("p_error_message", null, SqlParameter.ParameterDirection.OUTPUT, Types.NVARCHAR));

            dbContext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC.SP_IOPS_VALIDATE_BATCH", parameters);

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

            return new DanhMucIOPSValidateResponseDto(errorCount, errorMessage);

        } catch (IOException e) {
            throw new AppSqlException(e);
        }
    }
}
