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
import vn.vnpt.vnptit.ecms.dto.mau.MauThietBiMangDto;

@Service
public class MauThietBiMangService {

    @Autowired
    private DbContext dbContext;

    /**
     * Tìm kiếm hoặc lấy danh sách thiết bị mạng mẫu
     */
    public List<Map<String, Object>> search(JsonNode searchJsonData) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        String jsonString = searchJsonData != null ? searchJsonData.toString() : null;
        params.add(new SqlParameter("p_search_json", jsonString, ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_MAU.SP_SEARCH_MAU_TBIMANG", params);
    }

    /**
     * Lấy chi tiết thiết bị mạng mẫu theo ID
     */
    public Map<String, Object> getById(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_mautbimang_id", id, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        List<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_MAU.SP_GET_MAU_TBIMANG_BY_ID", params);
        return result != null && !result.isEmpty() ? result.get(0) : null;
    }

    /**
     * Lấy thiết bị mạng mẫu theo mẫu thiết bị ID
     */
    public Map<String, Object> getByMauThietBiId(Long mautbiId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_mautbi_id", mautbiId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        List<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_MAU.SP_GET_MAU_TBIMANG_BY_MAUTBI_ID", params);
        return result != null && !result.isEmpty() ? result.get(0) : null;
    }

    /**
     * Tạo mới hoặc cập nhật thiết bị mạng mẫu
     */
    public Object save(MauThietBiMangDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        SqlParameter p_id = new SqlParameter("p_mautbimang_id", dto.getMautbimangId(), Types.NUMERIC);
        params.add(p_id);
        params.add(new SqlParameter("p_mautbi_id", dto.getMautbiId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_congdl", dto.getSlCongdl(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_khedl", dto.getSlKhedl(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_congnguon", dto.getSlCongnguon(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_khenguon", dto.getSlKhenguon(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_congdkh", dto.getSlCongdkh(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_khedkh", dto.getSlKhedkh(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", dto.getGhichu(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_mota", dto.getMota(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(),
                ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, ParameterDirection.OUTPUT, Types.NUMERIC));

        Map<String, Object> result = dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_THIETBI_MAU.SP_SAVE_MAU_TBIMANG", params);
		return result.get("item");
    }

    /**
     * Xóa thiết bị mạng mẫu
     */
    public Boolean delete(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_mautbimang_id", id, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THIETBI_MAU.SP_DELETE_MAU_TBIMANG", params);

        java.math.BigDecimal rs = (java.math.BigDecimal) result.getOutValue();
        return rs != null && rs.intValue() == 1;
    }
}

