package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.LoaiRackDTO;
import vn.vnpt.vnptit.ecms.dto.idc.LoaiRackValidateResponseDTO;
import vn.vnpt.vnptit.ecms.dto.idc.LoaiRackBatchResponseDTO;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class LoaiRackService {
    @Autowired
    DbContext dbContext;

    public ArrayList<Map<String, Object>> layDsLoaiRack() throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_DANHSACH_LOAI_RACK", parameters);
    }

    public Boolean updateInsertLoaiRack(LoaiRackDTO input) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id", input.getpId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ten", input.getpTen(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        parameters.add(new SqlParameter("p_so_u", input.getpSoU(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_dai", input.getpDai(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_rong", input.getpRong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cao", input.getpCao(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ghi_chu", input.getpGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        parameters.add(new SqlParameter("p_ten_tat", input.getpTenTat(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        parameters.add(new SqlParameter("p_mo_ta", input.getpMoTa(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        parameters.add(new SqlParameter("p_su_dung", input.getpSuDung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        parameters.add(new SqlParameter("p_mamau", input.getpMaMau(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_LOAI_RACK_UPDATE_INSERT", parameters);
        if (rs != null && !rs.isEmpty()) {
            if ("1".equals(String.valueOf(rs.get(0).get("item"))))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean xoaLoaiRack(Integer input) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id", input, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_LOAI_RACK_DELETE", parameters);
        if (rs != null && !rs.isEmpty()) {
            if ("1".equals(String.valueOf(rs.get(0).get("item"))))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public LoaiRackBatchResponseDTO batchInsertLoaiRack(java.util.List<LoaiRackDTO> danhSachLoaiRack) throws AppSqlException {
        try {
            String jsonData = JsonConvert.serialize(danhSachLoaiRack);
            String nguoiCn = AppRequestContext.getCurrent().getUserName();

            ArrayList<SqlParameter> parameters = new ArrayList<>();
            parameters.add(new SqlParameter("p_json_data", jsonData, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            parameters.add(new SqlParameter("p_nguoi_cn", nguoiCn, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            parameters.add(new SqlParameter("p_success_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("p_failed_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_LOAI_RACK_BATCH_INSERT", parameters);

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

            return new LoaiRackBatchResponseDTO(successCount, failedCount, result);

        } catch (IOException e) {
            throw new AppSqlException(e);
        }
    }

    public LoaiRackValidateResponseDTO validateBatchLoaiRack(java.util.List<LoaiRackDTO> danhSachLoaiRack) throws AppSqlException {
        try {
            String jsonData = JsonConvert.serialize(danhSachLoaiRack);

            ArrayList<SqlParameter> parameters = new ArrayList<>();
            parameters.add(new SqlParameter("p_json_data", jsonData, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            parameters.add(new SqlParameter("p_error_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("p_error_message", null, SqlParameter.ParameterDirection.OUTPUT, Types.NVARCHAR));

            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_LOAI_RACK_VALIDATE_BATCH", parameters);

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

            return new LoaiRackValidateResponseDTO(errorCount, errorMessage);

        } catch (IOException e) {
            throw new AppSqlException(e);
        }
    }
}