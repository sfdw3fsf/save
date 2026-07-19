package vn.vnpt.vnptit.ecms.service.idc;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.ThanhNguonTrenRackBatchDto;
import vn.vnpt.vnptit.ecms.dto.idc.ThanhNguonTrenRackBatchResponseDto;
import vn.vnpt.vnptit.ecms.dto.idc.ThanhNguonTrenRackDto;
import vn.vnpt.vnptit.ecms.dto.idc.ThanhNguonTrenRackValidateResponseDto;

@Service
public class ThanhNguonTrenRackService {
    private final DbContext dbContext;

    public ThanhNguonTrenRackService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    /**
     * Lấy danh sách thanh nguồn trên rack
     */
    public ArrayList<Map<String, Object>> getListThanhNguonTrenRack() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_THANHNGUON_TRENRACK_GETLIST", params);
    }

    /**
     * Cập nhật hoặc thêm mới thanh nguồn trên rack
     */
    public Map<String, Object> upsertThanhNguonTrenRack(ThanhNguonTrenRackDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thanhnguon_id", dto.getThanhNguonId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_loaitn_id", dto.getLoaiTnId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(
                new SqlParameter("p_rack_id", dto.getRackId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_mota", dto.getMoTa(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_trangthai_id", dto.getTrangThaiId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(
                new SqlParameter("p_vitri_ld", dto.getViTriLd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("p_kieu_ld", dto.getKieuLd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("p_ghichu", dto.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_sudung", dto.getSuDung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN.SP_THANHNGUON_TRENRACK_UPDATE_INSERT",
                params);
    }

    /**
     * Xóa thanh nguồn trên rack
     */
    public Map<String, Object> deleteThanhNguonTrenRack(Long thanhNguonId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thanhnguon_id", thanhNguonId, SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN.SP_THANHNGUON_TRENRACK_DELETE", params);
    }

    /**
     * Insert nhiều thanh nguồn trên rack từ JSON
     */
    public ThanhNguonTrenRackBatchResponseDto batchInsertThanhNguonTrenRack(ThanhNguonTrenRackBatchDto dto)
            throws AppSqlException {
        try {
            String jsonData = JsonConvert.serialize(dto.getDanhSachThanhNguon());
            ArrayList<SqlParameter> parameters = new ArrayList<>();
            parameters.add(
                    new SqlParameter("p_json_data", jsonData, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            parameters.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(),
                    SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            parameters.add(
                    new SqlParameter("p_success_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            parameters.add(
                    new SqlParameter("p_failed_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_THANHNGUON_TRENRACK_BATCH_INSERT", parameters);

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

            return new ThanhNguonTrenRackBatchResponseDto(
                    successCount != null ? successCount.longValue() : 0L,
                    failedCount != null ? failedCount.longValue() : 0L,
                    result != null ? result.longValue() : 0L);

        } catch (IOException e) {
            throw new AppSqlException(e);
        }
    }

    /**
     * Kiểm tra có cho phép chỉnh sửa loại thanh nguồn thuộc thanh nguồn đã tồn tại
     * không
     */
    public Map<String, Object> checkEditLoaiTn(Long thanhNguonId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thanhnguon_id", thanhNguonId, SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN.SP_THANHNGUON_TRENRACK_CHECK_EDIT_LOAITN",
                params);
    }

    /**
     * Lấy danh sách ổ cắm theo thanh nguồn ID cho Popup info
     */
    public ArrayList<Map<String, Object>> getOcamInfoByThanhNguonId(Long thanhNguonId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thanhnguon_id", thanhNguonId, SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_OCAM_GET_INFO_BY_THANHNGUON_ID",
                params);
    }

    /**
     * Validate dữ liệu trước khi thêm mới nhiều thanh nguồn trên rack
     */
    public ThanhNguonTrenRackValidateResponseDto validateBatchThanhNguonTrenRack(
            java.util.List<ThanhNguonTrenRackDto> danhSachThanhNguon) throws AppSqlException {
        try {
            String jsonData = JsonConvert.serialize(danhSachThanhNguon);

            ArrayList<SqlParameter> parameters = new ArrayList<>();
            parameters.add(
                    new SqlParameter("p_json_data", jsonData, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            parameters.add(
                    new SqlParameter("p_error_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            parameters.add(
                    new SqlParameter("p_error_message", null, SqlParameter.ParameterDirection.OUTPUT, Types.NVARCHAR));
            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_THANHNGUON_TRENRACK_VALIDATE_BATCH",
                    parameters);
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

            return new ThanhNguonTrenRackValidateResponseDto(errorCount, errorMessage);

        } catch (IOException e) {
            throw new AppSqlException(e);
        }
    }

    public ArrayList<Map<String, Object>> getListThanhNguonTrenRackByRackId(Integer rackId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_rack_id", rackId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_THANHNGUON_TRENRACK_GETLIST_BY_RACKID",
                params);
    }
}
