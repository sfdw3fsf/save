package vn.vnpt.vnptit.ecms.service.idc;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.mau.MauKhayDiaDto;

@Service
public class MauKhayDiaService {

    @Autowired
    private DbContext dbContext;

    /**
     * Tìm kiếm hoặc lấy danh sách khay đĩa mẫu
     */
    public List<Map<String, Object>> search(JsonNode searchJsonData) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        String jsonString = searchJsonData != null ? searchJsonData.toString() : null;
        params.add(new SqlParameter("p_search_json", jsonString, ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_MAU.SP_SEARCH_MAU_KHD", params);
    }

    /**
     * Lấy chi tiết khay đĩa mẫu theo ID
     */
    public Map<String, Object> getById(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_maukhd_id", id, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        ArrayList<Map<String, Object>> response = dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_MAU.SP_GET_MAU_KHD_BY_ID", params);
        // normalize keys to lowercase
        Map<String, Object> result = response != null && !response.isEmpty() ? response.get(0) : null;
        return result != null ? normalizeKeys(result) : null;
    }

    /**
     * Lấy khay đĩa mẫu theo mẫu thiết bị ID
     */
    public Map<String, Object> getByMauThietBiId(Long mautbiId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_mautbi_id", mautbiId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        ArrayList<Map<String, Object>> response = dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_MAU.SP_GET_MAU_KHD_BY_MAUTBI_ID", params);
        // normalize keys to lowercase
        Map<String, Object> result = response != null && !response.isEmpty() ? response.get(0) : null;
        return result != null ? normalizeKeys(result) : null;
    }

    /**
     * Tạo mới hoặc cập nhật khay đĩa mẫu
     */
    public Object save(MauKhayDiaDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        SqlParameter p_id = new SqlParameter("p_maukhd_id", dto.getMaukhdId(), Types.NUMERIC);
        params.add(p_id);
        params.add(new SqlParameter("p_mautbi_id", dto.getMautbiId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_hdd", dto.getSlHdd(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_ssd", dto.getSlSsd(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_nvme", dto.getSlNvme(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", dto.getGhichu(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_mota", dto.getMota(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(),
                ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, ParameterDirection.OUTPUT, Types.NUMERIC));

        Map<String, Object> result = dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_THIETBI_MAU.SP_SAVE_MAU_KHD",
                params);
        return result.get("item");
    }

    /**
     * Xóa khay đĩa mẫu
     */
    public Boolean delete(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_maukhd_id", id, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THIETBI_MAU.SP_DELETE_MAU_KHD", params);

        java.math.BigDecimal rs = (java.math.BigDecimal) result.getOutValue();
        return rs != null && rs.intValue() == 1;
    }

    // lowercase
    Map<String, Object> normalizeKeys(Map<String, Object> original) {
        Map<String, Object> normalized = new java.util.HashMap<>();
        for (Map.Entry<String, Object> entry : original.entrySet()) {
            normalized.put(entry.getKey().toLowerCase(), entry.getValue());
        }
        return normalized;
    }
}
