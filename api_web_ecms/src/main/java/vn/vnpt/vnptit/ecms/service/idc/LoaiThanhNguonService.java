package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.stereotype.Service;
import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.LoaiThanhNguonDto;
import vn.vnpt.vnptit.ecms.dto.idc.DeleteLoaiThanhNguonDto;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoaiThanhNguonService {

    private final DbContext dbContext;

    public LoaiThanhNguonService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getListLoaiThanhNguon() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_LAY_DS_LOAI_THANHNGUON", params);
    }

    public Object searchLoaiThanhNguon(LoaiThanhNguonDto searchDto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", searchDto.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", searchDto.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_hang_sx_id", searchDto.getHangSxId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loai_dongdien", searchDto.getLoaiDongDien(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_lap_dat", searchDto.getLapDat(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cs_thietke", searchDto.getCsThietKe(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_thongso_dc", searchDto.getThongSoDongDien(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dienap_dc", searchDto.getDienApDongDien(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dai", searchDto.getDai(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rong", searchDto.getRong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cao", searchDto.getCao(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_iec_c13", searchDto.getSlIecC13(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_iec_c19", searchDto.getSlIecC19(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_khac", searchDto.getSlKhac(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_SEARCH_LOAI_THANHNGUON", params);
    }

    public Object upsertLoaiThanhNguon(LoaiThanhNguonDto dto) throws AppSqlException {
        dto.setNguoiCn(AppRequestContext.getCurrent().getUserName());
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_hang_sx_id", dto.getHangSxId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loai_dongdien", dto.getLoaiDongDien(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_thongso_dongdien", dto.getThongSoDongDien(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dienap_dongdien", dto.getDienApDongDien(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cs_thietke", dto.getCsThietKe(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_lap_dat", dto.getLapDat(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dai", dto.getDai(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rong", dto.getRong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cao", dto.getCao(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_iec_c13", dto.getSlIecC13(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_iec_c19", dto.getSlIecC19(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_khac", dto.getSlKhac(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", dto.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sudung", dto.getSuDung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", dto.getNguoiCn(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

        Map<String, Object> result = dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN.SP_UPSERT_LOAI_THANHNGUON", params);
        return "1".equals(result.get("item").toString());
    }

    public Boolean deleteLoaiThanhNguonMulti(DeleteLoaiThanhNguonDto dto) throws AppSqlException {
        String idsString = "";
        if (dto.getIds() != null && dto.getIds().length > 0) {
            // xóa ký tự [ ]
            idsString = java.util.Arrays.toString(dto.getIds()).replaceAll("[\\[\\]]", "");
        }

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ids", idsString, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

        Map<String, Object> result = dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN.SP_DELETE_LOAI_THANHNGUON_MULTI", params);
        return "1".equals(result.get("item").toString());
    }

    public Map<String, Object> upsertLoaiThanhNguonMulti(List<LoaiThanhNguonDto> dto) throws AppSqlException {
        try {
            ArrayList<SqlParameter> params = buildLoaiThanhNguonMultiParams(dto);
            params.add(new SqlParameter("p_pass", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_fail", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_UPSERT_LOAI_THANHNGUON_MULTI", params);

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

    public Boolean checkValidLoaiThanhNguonMulti(List<LoaiThanhNguonDto> dto) throws AppSqlException {
        try {
            ArrayList<SqlParameter> params = buildLoaiThanhNguonMultiParams(dto);
            params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_error", null, SqlParameter.ParameterDirection.OUTPUT, Types.CLOB));

            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_VALIDATE_LOAI_THANHNGUON_MULTI", params);

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

    //    ============ PRIVATE ============   //
    private ArrayList<SqlParameter> buildLoaiThanhNguonMultiParams(List<LoaiThanhNguonDto> dto) throws IOException {
        String jsonListData = JsonConvert.serialize(dto);
        String nguoiCn = AppRequestContext.getCurrent().getUserName();

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_list_data", jsonListData, SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_nguoi_cn", nguoiCn, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        return params;
    }
}
