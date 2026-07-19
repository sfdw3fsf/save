package vn.vnpt.vnptit.ecms.service.idc;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.DeletePhongZoneDto;
import vn.vnpt.vnptit.ecms.dto.idc.PhongZoneDto;

@Service
public class ThongTinPhongZoneService {

    private final DbContext dbContext;

    public ThongTinPhongZoneService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getListPhongZone() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_LAY_THONGTIN_PHONG_ZONE", params);
    }

    public Object upsertPhongZone(PhongZoneDto dto) throws AppSqlException {
        dto.setNguoiCn(AppRequestContext.getCurrent().getUserName());
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_matsan_id", dto.getMatsanId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(
                new SqlParameter("p_ghi_chu", dto.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten", dto.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_tat", dto.getTenTat(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sudung", dto.getSuDung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dientich", dto.getDientich(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_anh_daidien", dto.getAnhDaidien(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_trangthai_vh", dto.getTrangthaiVh(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_sd", dto.getNgaySd(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_mucdichsd_id", dto.getMucdichsdId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_rack_thietke", dto.getSlRackThietke(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_toado_x", dto.getToadoX(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_toado_y", dto.getToadoY(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_huong_x", dto.getHuongX(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_huong_y", dto.getHuongY(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("p_nguoi_cn", dto.getNguoiCn(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

        Map<String, Object> result = dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN.SP_UPSERT_PHONG_ZONE",
                params);
        return "1".equals(result.get("item").toString());
    }

    public Boolean deletePhongZoneMulti(DeletePhongZoneDto dto) throws AppSqlException {
        String idsString = "";
        if (dto.getIds() != null && dto.getIds().length > 0) {
            // xóa ký tự [ ]
            idsString = java.util.Arrays.toString(dto.getIds()).replaceAll("[\\[\\]]", "");
        }

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ids", idsString, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

        Map<String, Object> result = dbContext
                .executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN.SP_DELETE_PHONG_ZONE_MULTI", params);
        return "1".equals(result.get("item").toString());
    }

    public Map<String, Object> upsertPhongZoneMulti(List<PhongZoneDto> dto) throws AppSqlException {
        try {
            ArrayList<SqlParameter> params = buildPhongZoneMultiParams(dto);
            params.add(new SqlParameter("p_pass", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_fail", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_UPSERT_PHONG_ZONE_MULTI", params);

            Number passNum = (Number) params.get(2).getOutValue();
            Number failNum = (Number) params.get(3).getOutValue();

            int pass = passNum != null ? passNum.intValue() : 0;
            int fail = failNum != null ? failNum.intValue() : 0;

            Map<String, Object> result = new HashMap<>();
            result.put("failCount", fail);
            result.put("successCount", pass);

            return result;
        } catch (IOException e) {
            throw new AppSqlException(e);
        }
    }

    public Boolean checkValidPhongZoneMulti(List<PhongZoneDto> dto) throws AppSqlException {
        try {
            ArrayList<SqlParameter> params = buildPhongZoneMultiParams(dto);
            params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_error", null, SqlParameter.ParameterDirection.OUTPUT, Types.CLOB));

            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_VALIDATE_PHONG_ZONE_MULTI", params);

            Number p_result = (Number) params.get(2).getOutValue();
            String errorMsg = (String) params.get(3).getOutValue();

            int result = p_result != null ? p_result.intValue() : 0;

            if (result == 0) {
                throw new AppSqlException("Validation failed: " + (errorMsg != null ? errorMsg : "Validation failed"));
            }

            return true;
        } catch (IOException e) {
            throw new AppSqlException(e);
        }
    }

    public Object getById(Integer id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_PHONG_ZONE_BY_ID", params);
    }

    public List<Map<String, Object>> getThietBiByPhongZoneId(Integer id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phong_zone_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_THIETBI_BY_PHONG_ZONE_ID", params);
    }

    // ============ PRIVATE ============ //

    private ArrayList<SqlParameter> buildPhongZoneMultiParams(List<PhongZoneDto> dto) throws IOException {
        String jsonListData = JsonConvert.serialize(dto);
        String nguoiCn = AppRequestContext.getCurrent().getUserName();

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_list_data", jsonListData, SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_nguoi_cn", nguoiCn, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        return params;
    }
}
