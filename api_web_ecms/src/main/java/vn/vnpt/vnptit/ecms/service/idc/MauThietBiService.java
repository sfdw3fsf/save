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
import vn.vnpt.vnptit.ecms.dto.mau.MauThietBiDto;

@Service
public class MauThietBiService {

    @Autowired
    private DbContext dbContext;

    /**
     * Tìm kiếm hoặc lấy danh sách mẫu thiết bị
     */
    public List<Map<String, Object>> search(JsonNode searchJsonData) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        String jsonString = searchJsonData != null ? searchJsonData.toString() : null;
        params.add(new SqlParameter("p_search_json", jsonString, ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_MAU.SP_SEARCH_MAU_TBI", params);
    }

    /**
     * Lấy chi tiết mẫu thiết bị theo ID
     */
    public Map<String, Object> getById(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_mautbi_id", id, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        List<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_MAU.SP_GET_MAU_TBI_BY_ID", params);
        // return result != null && !result.isEmpty() ? result.get(0) : null;
        // normalize keys to lowercase
        return !result.isEmpty() ? result.get(0) : null;
    }
    
    /**
     * Lấy chi tiết mẫu thiết bị dùng để merge data theo ID
     */
    public Map<String, Object> getMauThietBiMergeById(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_mautbi_id", id, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        List<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_MAU.SP_GET_MAU_TBI_MERGE_BY_ID", params);
        // return result != null && !result.isEmpty() ? result.get(0) : null;
        // normalize keys to lowercase
        return !result.isEmpty() ? result.get(0) : null;
    }

    /**
     * Tạo mới hoặc cập nhật mẫu thiết bị
     */
    public Object save(MauThietBiDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        SqlParameter p_id = new SqlParameter("p_mautbi_id", dto.getMautbiId(), ParameterDirection.INPUT, Types.NUMERIC);
        params.add(p_id);
        params.add(new SqlParameter("p_mangtbi_id", dto.getMangtbiId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaitbi_id", dto.getLoaitbiId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("p_phanloaitbi_id", dto.getPhanloaitbiId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ma_mautbi", dto.getMaMautbi(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_mautbi", dto.getTenMautbi(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sl_rackunit", dto.getSlRackunit(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_full_depth", dto.getFullDepth(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hangsx_id", dto.getHangsxId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cs_dinhdanh", dto.getCsDinhdanh(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ma_vtu", dto.getMaVtu(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_hinhanh", dto.getHinhanh(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_da_dinhdanh", dto.getDaDinhdanh(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dd_dinhdanh", dto.getDdDinhdanh(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_heso_cosphi", dto.getHsCosphi(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loai_ndien", dto.getLoaiNdien(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaimay", dto.getLoaiMay(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_chieudai", dto.getChieudai(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_chieurong", dto.getChieurong(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_chieucao", dto.getChieucao(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cauhinh", dto.getCauhinh(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sudung", dto.getSudung(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", dto.getGhichu(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_mota", dto.getMota(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(),
                ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, ParameterDirection.OUTPUT, Types.NUMERIC));

        Map<String, Object> result = dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_THIETBI_MAU.SP_SAVE_MAU_TBI", params);
		return result.get("item");
    }

    /**
     * Xóa mẫu thiết bị
     */
    public Boolean delete(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_mautbi_id", id, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THIETBI_MAU.SP_DELETE_MAU_TBI", params);

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
