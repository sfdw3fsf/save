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
import vn.vnpt.vnptit.ecms.dto.mau.MauServerBladeDto;

@Service
public class MauServerBladeService {

    @Autowired
    private DbContext dbContext;

    /**
     * Tìm kiếm hoặc lấy danh sách server/blade mẫu
     */
    public List<Map<String, Object>> search(JsonNode searchJsonData) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        String jsonString = searchJsonData != null ? searchJsonData.toString() : null;
        params.add(new SqlParameter("p_search_json", jsonString, ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_MAU.SP_SEARCH_MAU_SB", params);
    }

    /**
     * Lấy chi tiết server/blade mẫu theo ID
     */
    public Map<String, Object> getById(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_mausb_id", id, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        List<Map<String, Object>> response = dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_MAU.SP_GET_MAU_SB_BY_ID", params);
        // normalize keys to lowercase
        Map<String, Object> result = response != null && !response.isEmpty() ? response.get(0) : null;
        return result != null ? normalizeKeys(result) : null;
    }

    /**
     * Lấy server/blade mẫu theo mẫu thiết bị ID
     */
    public Map<String, Object> getByMauThietBiId(Long mautbiId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_mautbi_id", mautbiId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        ArrayList<Map<String, Object>> response = dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_MAU.SP_GET_MAU_SB_BY_MAUTBI_ID", params);
        // normalize keys to lowercase
        Map<String, Object> result = response != null && !response.isEmpty() ? response.get(0) : null;
        return result != null ? normalizeKeys(result) : null;
    }

    /**
     * Tạo mới hoặc cập nhật server/blade mẫu
     */
    public Object save(MauServerBladeDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_mausb_id", dto.getMausbId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_mautbi_id", dto.getMautbiId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_socketcpu", dto.getSlSocketcpu(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_corecpu", dto.getSlCorecpu(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_model_cpu", dto.getModelCpu(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sl_threadcpu", dto.getSlThreadcpu(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_kheram", dto.getSlKheram(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dl_ram", dto.getDlRam(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_khessd", dto.getSlKhessd(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_khenvme", dto.getSlKhenvme(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_khehdd", dto.getSlKhehdd(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_gpu", dto.getSlGpu(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_khegpu", dto.getSlKhegpu(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_fan", dto.getSlFan(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_khepci", dto.getSlKhepci(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_pci", dto.getSlPci(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_kheblade", dto.getSlKheblade(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_congdl", dto.getSlCongdl(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_khedl", dto.getSlKhedl(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_congdkh", dto.getSlCongdkh(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_khedkh", dto.getSlKhedkh(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_congnguon", dto.getSlCongnguon(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_khenguon", dto.getSlKhenguon(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", dto.getGhichu(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_mota", dto.getMota(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(),
                ParameterDirection.INPUT, Types.VARCHAR));
        SqlParameter p_result = new SqlParameter("p_result", ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(p_result);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THIETBI_MAU.SP_SAVE_MAU_SB", params);
        return p_result.getOutValue();
    }

    /**
     * Xóa server/blade mẫu
     */
    public Boolean delete(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_mausb_id", id, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THIETBI_MAU.SP_DELETE_MAU_SB", params);

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
