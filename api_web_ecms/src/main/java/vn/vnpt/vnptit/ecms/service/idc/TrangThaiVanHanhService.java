package vn.vnpt.vnptit.ecms.service.idc;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.idc.TrangThaiVanHanhDto;

@Service
public class TrangThaiVanHanhService {

    @Autowired
    private DbContext dbContext;

    /**
     * Lấy danh sách tất cả trạng thái vận hành
     */
    public ArrayList<Map<String, Object>> getListTrangThaiVanHanh() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_GET_TRANGTHAI_VH", params);
    }

    /**
     * Lưu (tạo mới hoặc cập nhật) trạng thái vận hành
     */
    public Object saveTrangThaiVanHanh(TrangThaiVanHanhDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        SqlParameter p_id = new SqlParameter("P_ID", dto.getTtvhId(), Types.NUMERIC);
        params.add(p_id);
        params.add(new SqlParameter("P_TRANGTHAI_VH", dto.getTrangThaiVh(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_SUDUNG", dto.getSuDung(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_GHI_CHU", dto.getGhiChu(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                ParameterDirection.INPUT, Types.VARCHAR));

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_SAVE_TRANGTHAI_VH", params);
        return p_id.getOutValue();
    }

    /**
     * Lưu nhiều bản ghi từ file upload
     */
    public Object saveByFile(List<Map<String, Object>> jsonDataList) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        try {
            params.add(new SqlParameter("P_JSON", JsonConvert.serialize(jsonDataList), ParameterDirection.INPUT,
                    Types.VARCHAR));
        } catch (Exception e) {
            throw new AppSqlException(e);
        }

        SqlParameter valid_rows = new SqlParameter("P_VALID_ROWS", null, SqlParameter.ParameterDirection.OUTPUT,
                Types.NUMERIC);
        SqlParameter invalid_rows = new SqlParameter("P_INVALID_ROWS", null, SqlParameter.ParameterDirection.OUTPUT,
                Types.NUMERIC);
        params.add(valid_rows);
        params.add(invalid_rows);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_SAVE_MULTI_TRANGTHAI_VH", params);

        return new HashMap<String, Object>() {
            {
                put("validRows", valid_rows.getOutValue());
                put("invalidRows", invalid_rows.getOutValue());
            }
        };
    }

    /**
     * Validate file trước khi upload
     */
    public Map<String, Object> validateByFile(List<Map<String, Object>> jsonDataList) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        try {
            params.add(new SqlParameter("P_JSON", JsonConvert.serialize(jsonDataList), ParameterDirection.INPUT,
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
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_CHECK_SAVE_MULTI_TRANGTHAI_VH", params);

        return new HashMap<String, Object>() {
            {
                put("invalidRows", invalidRows.getOutValue());
                put("businessLogicError", errors.getOutValue());
            }
        };
    }

    /**
     * Xóa trạng thái vận hành
     */
    public Boolean delete(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_ID", id, Types.NUMERIC));
        SqlParameter result = new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_DELETE_TRANGTHAI_VH", params);

        BigDecimal rs = (BigDecimal) result.getOutValue();
        return rs != null && rs.intValue() == 1;
    }

        /*
    * Version 2
     */

    public Map<String, Object> validateFileV2(List<Map<String, Object>> jsonDataList) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        try {
            String test= JsonConvert.serialize(jsonDataList);
            params.add(new SqlParameter("P_JSON", test, ParameterDirection.INPUT,
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
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_CHECK_SAVE_MULTI_TRANGTHAI_VH_V2", params);

        return new HashMap<String, Object>() {
            {
                put("numberOfInvalidRows", invalidRows.getOutValue());
                put("errors", errors.getOutValue());
            }
        };
    }

    public List<Map<String, Object>> getListTrangThaiVanHanhV2() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        List<Map<String, Object>> res = dbContext
                .executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_GET_TRANGTHAI_VH", params);
        return normalizeKeys(res);
    }

    private List<Map<String, Object>> normalizeKeys(List<Map<String, Object>> dbResult) {
        if (dbResult == null) {
            return new ArrayList<>();
        }

        return dbResult.stream().map(item -> {
            Map<String, Object> normalizedItem = new LinkedHashMap<>();
            item.forEach((key, value) -> {
                String normalizedKey = key.toLowerCase();
                normalizedItem.put(normalizedKey, value);
            });
            return normalizedItem;
        }).collect(Collectors.toList());
    }

    public Object saveFileV2(List<Map<String, Object>> jsonDataList) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        try {
            params.add(new SqlParameter("P_JSON", JsonConvert.serialize(jsonDataList), ParameterDirection.INPUT,
                    Types.VARCHAR));
        } catch (Exception e) {
            throw new AppSqlException(e);
        }

        SqlParameter valid_rows = new SqlParameter("P_OUT", null, SqlParameter.ParameterDirection.OUTPUT,
                Types.NUMERIC);
        params.add(valid_rows);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_SAVE_MULTI_TRANGTHAI_VH_V2", params);

        return valid_rows.getOutValue();
    }

}
