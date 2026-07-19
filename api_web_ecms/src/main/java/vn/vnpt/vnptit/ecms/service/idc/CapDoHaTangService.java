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
import vn.vnpt.vnptit.ecms.dto.idc.CapDoHaTangDto;

@Service
public class CapDoHaTangService {

    @Autowired
    private DbContext dbContext;

    /**
     * Lấy danh sách tất cả cấp độ hạ tầng
     */
    public List<Map<String, Object>> getListCapDoHaTangV2() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        List<Map<String, Object>> res = dbContext
                .executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_GET_CAPDO_HT", params);
        return normalizeKeys(res);
    }

    /**
     * Lưu (tạo mới hoặc cập nhật) cấp độ hạ tầng
     */
    public Object saveCapDoHaTang(CapDoHaTangDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_ID", dto.getCapDoHaTangId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TEN", dto.getTen(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TENTAT", dto.getTenTat(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_MOTA", dto.getMoTa(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_GHI_CHU", dto.getGhiChu(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_SUDUNG", dto.getSudung(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                ParameterDirection.INPUT, Types.VARCHAR));
        SqlParameter p_rs = new SqlParameter("P_RS", null, ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(p_rs);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_UPSERT_CAPDO_HT", params);
        return p_rs.getOutValue();
    }

    /**
     * Lưu nhiều bản ghi từ file upload
     */

    public Object saveFile(List<Map<String, Object>> jsonDataList) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        try {
            params.add(new SqlParameter("P_JSON", JsonConvert.serialize(jsonDataList), ParameterDirection.INPUT,
                    Types.VARCHAR));
        } catch (Exception e) {
            throw new AppSqlException(e);
        }
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), ParameterDirection.INPUT, Types.VARCHAR));

        SqlParameter valid_rows = new SqlParameter("P_OUT", null, SqlParameter.ParameterDirection.OUTPUT,
                Types.NUMERIC);
        params.add(valid_rows);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_SAVE_MULTI_CAPDO_HT", params);

        return valid_rows.getOutValue();
    }

    public Map<String, Object> validateFile(List<Map<String, Object>> jsonDataList) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        try {
            String test = JsonConvert.serialize(jsonDataList);
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
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_CHECK_SAVE_MULTI_CAPDO_HT", params);

        return new HashMap<String, Object>() {
            {
                put("numberOfInvalidRows", invalidRows.getOutValue());
                put("errors", errors.getOutValue());
            }
        };
    }

    /**
     * Xóa cấp độ hạ tầng
     */
    public Boolean delete(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_ID", id, Types.NUMERIC));
        SqlParameter result = new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_DELETE_CAPDO_HT", params);

        BigDecimal rs = (BigDecimal) result.getOutValue();
        return rs != null && rs.intValue() == 1;
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

}
