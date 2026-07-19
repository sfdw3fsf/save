package vn.vnpt.vnptit.ecms.service.idc;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.danhmuc.ToaNhaDto;

@Service
public class ToaNhaService {
    @Autowired
    private DbContext dbContext;

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
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_STATISTIC.SP_SAVE_MULTI_TOANHA", params);

        return new HashMap<String, Object>() {
            {
                put("validRows", valid_rows.getOutValue());
                put("invalidRows", invalid_rows.getOutValue());
            }
        };
    }

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
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_STATISTIC.SP_CHECK_SAVE_MULTI_TOANHA", params);

        return new HashMap<String, Object>() {
            {
                put("invalidRows", invalidRows.getOutValue());
                put("businessLogicError", errors.getOutValue());
            }
        };
    }

    public Boolean delete(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_ID", id, Types.NUMERIC));
        SqlParameter result = new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_STATISTIC.SP_DELETE_TOANHA", params);

        BigDecimal rs = (BigDecimal) result.getOutValue();
        return rs != null && rs.intValue() == 1;
    }

    // ======= Không gian ======

    public Map<String, Object> getToaNhaById(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_ID", id, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        ArrayList<Map<String, Object>> result = dbContext.executeSpIdxWithCursorToListMap(
                "ECMS.PKG_IDC_STATISTIC.SP_GET_TOANHA_BY_ID", params);
        if (result.size() > 0) {
            return result.get(0);
        }
        return null;
    }

    public Object getMatSan(Long id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_STATISTIC.SP_MAT_SAN_BY_TOANHA", params);
    }

    public ArrayList<Map<String, Object>> searchToaNha(JsonNode searchJsonData) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        String jsonString = searchJsonData != null ? searchJsonData.toString() : null;
        params.add(new SqlParameter("p_search_json", jsonString, Types.CLOB));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap(
                "ECMS.PKG_IDC_STATISTIC.SP_SEARCH_TOANHA", params);
    }

    public Object saveToaNha(ToaNhaDto toaNha) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        SqlParameter p_id = new SqlParameter("P_ID", toaNha.getId(), Types.NUMERIC);
        params.add(p_id);
        params.add(new SqlParameter("P_IDC_ID", toaNha.getIdIDC(), Types.NUMERIC));
        params.add(new SqlParameter("P_TEN", toaNha.getTenToaNha(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_GHI_CHU", toaNha.getGhiChu(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TENTAT", toaNha.getTenTat(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_MOTA", toaNha.getMoTa(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_SUDUNG", toaNha.isSuDung(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NGAY_SD", toaNha.getThoiDiemVanHanh(), ParameterDirection.INPUT, Types.DATE));
        params.add(
                new SqlParameter("P_MUCDICH_SD_ID", toaNha.getMucDichSdId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DIENTICH", toaNha.getDienTich(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TOADO_X", toaNha.getToaDoX(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TOADO_Y", toaNha.getToaDoY(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HINHANH", toaNha.getAnhDaiDien(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(
                new SqlParameter("P_TRANGTHAI_VH", toaNha.getTrangThaiVh(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                ParameterDirection.INPUT, Types.VARCHAR));

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_STATISTIC.SP_SAVE_TOANHA", params);
        return p_id.getOutValue();
    }

    public ArrayList<Map<String, Object>> getListToaNha() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_STATISTIC.SP_GET_ALL_TOANHA", params);
    }
}
