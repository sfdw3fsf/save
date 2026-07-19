package vn.vnpt.vnptit.ecms.service.idc;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.idc.ChungLoaiTuDiaDto;
import vn.vnpt.vnptit.ecms.dto.idc.DeleteChungLoaiTuDiaDto;

@Service
public class ChungLoaiTuDiaService {

    private final DbContext dbContext;

    public ChungLoaiTuDiaService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    /**
     * Danh sách hoặc tìm kiếm chủng loại tủ đĩa
     */
    public Object getListChungLoaiTuDia(ChungLoaiTuDiaDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto != null ? dto.getChungloaitdId() : null, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_model", dto != null ? dto.getModelThietbi() : null, ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_hangsx_id", dto != null ? dto.getHangsxId() : null, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaitudia_id", dto != null ? dto.getLoaitudiaId() : null, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sudung", dto != null ? dto.getSudung() : null, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_GET_DS_CHUNGLOAI_TUDIA", params);
    }

    /**
     * Upsert chủng loại tủ đĩa
     */
    public Object upsertChungLoaiTuDia(ChungLoaiTuDiaDto dto) throws AppSqlException {
        dto.setNguoiCn(AppRequestContext.getCurrent().getUserName());
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_ID", dto.getChungloaitdId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_MA_VT", dto.getMaVt(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_LOAITUDIA_ID", dto.getLoaitudiaId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_MODEL_THIETBI", dto.getModelThietbi(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_HANGSX_ID", dto.getHangsxId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CONGSUAT", dto.getCongsuat(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAIVAITRO_ID", dto.getLoaivaitroId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_FULL_DEPTH", dto.getFullDepth(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_BODK_SL", dto.getBodkSl(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_BODK_CPU", dto.getBodkCpu(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_BODK_RAM", dto.getBodkRam(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_BODK_MA_VT", dto.getBodkMaVt(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_DC_SOCONG", dto.getDcSocong(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DC_TENCONG", dto.getDcTencong(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_DC_LOAICONG", dto.getDcLoaicong(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_DC_BANGTHONG", dto.getDcBangthong(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DC_TTMODULE_ID", dto.getDcTtmoduleId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DC_MUCDICHSD_ID", dto.getDcMucdichsdId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_KD_SL", dto.getKdSl(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_KD_MA_TUDIA", dto.getKdMaTudia(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_KD_STT", dto.getKdStt(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_KD_SL_SSD", dto.getKdSlSsd(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_KD_SL_HDD", dto.getKdSlHdd(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_KD_SL_NVME", dto.getKdSlNvme(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PP_SOCONG", dto.getPpSocong(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PP_TENCONG", dto.getPpTencong(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_PP_DONGDIEN", dto.getPpDongdien(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PP_DIENAP", dto.getPpDienap(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PP_LOAIND", dto.getPpLoaind(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PP_CHUANDN_ID", dto.getPpChuandnId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_SOLUONG_U_LD", dto.getSoluongULd(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_SUDUNG", dto.getSudung(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_GHICHU", dto.getGhichu(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NGUOI_CN", dto.getNguoiCn(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_RESULT", null, ParameterDirection.OUTPUT, Types.NUMERIC));

        Map<String, Object> result = dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_UPSERT_CHUNGLOAI_TUDIA", params);
        return result.get("item");
    }

    /**
     * Xóa nhiều chủng loại tủ đĩa
     */
    public Boolean deleteChungLoaiTuDiaMulti(DeleteChungLoaiTuDiaDto dto) throws AppSqlException {
        String idsString = "";
        if (dto.getIds() != null && dto.getIds().length > 0) {
            idsString = java.util.Arrays.toString(dto.getIds()).replaceAll("[\\[\\]]", "");
        }
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ids", idsString, ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, ParameterDirection.OUTPUT, Types.NUMERIC));

        Map<String, Object> result = dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_DELETE_CHUNGLOAI_TUDIA_MULTI", params);
        return "1".equals(result.get("item").toString());
    }

    /**
     * Validate một chủng loại tủ đĩa
     */
    public Boolean validateChungLoaiTuDia(ChungLoaiTuDiaDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getChungloaitdId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaitudia_id", dto.getLoaitudiaId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hangsx_id", dto.getHangsxId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_model_thietbi", dto.getModelThietbi(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_dc_socong", dto.getDcSocong(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_pp_socong", dto.getPpSocong(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, ParameterDirection.OUTPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_error", null, ParameterDirection.OUTPUT, Types.VARCHAR));

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_VALIDATE_CHUNGLOAI_TUDIA", params);

        Number p_result = (Number) params.get(6).getOutValue();
        String errorMsg = (String) params.get(7).getOutValue();
        int result = p_result != null ? p_result.intValue() : 0;
        if (result == 0) {
            throw new AppSqlException("Validation failed: " + (errorMsg != null ? errorMsg : "Validation failed"));
        }
        return true;
    }

    /**
     * Validate nhiều chủng loại tủ đĩa
     */
    public Boolean validateChungLoaiTuDiaMulti(List<ChungLoaiTuDiaDto> dto) throws AppSqlException {
        try {
            String jsonListData = JsonConvert.serialize(dto);
            String nguoiCn = AppRequestContext.getCurrent().getUserName();

            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_list_data", jsonListData, ParameterDirection.INPUT, Types.CLOB));
            params.add(new SqlParameter("p_nguoi_cn", nguoiCn, ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_result", null, ParameterDirection.OUTPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_error", null, ParameterDirection.OUTPUT, Types.CLOB));

            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_VALIDATE_CHUNGLOAI_TUDIA_MULTI", params);

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

    /**
     * Upsert nhiều chủng loại tủ đĩa
     */
    public Map<String, Object> upsertChungLoaiTuDiaMulti(List<ChungLoaiTuDiaDto> dto) throws AppSqlException {
        try {
            String jsonListData = JsonConvert.serialize(dto);
            String nguoiCn = AppRequestContext.getCurrent().getUserName();

            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_list_data", jsonListData, ParameterDirection.INPUT, Types.CLOB));
            params.add(new SqlParameter("p_nguoi_cn", nguoiCn, ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_pass", null, ParameterDirection.OUTPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_fail", null, ParameterDirection.OUTPUT, Types.NUMERIC));

            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_UPSERT_CHUNGLOAI_TUDIA_MULTI", params);

            Number passNum = (Number) params.get(2).getOutValue();
            Number failNum = (Number) params.get(3).getOutValue();

            int pass = passNum != null ? passNum.intValue() : 0;
            int fail = failNum != null ? failNum.intValue() : 0;

            java.util.HashMap<String, Object> result = new java.util.HashMap<>();
            result.put("successCount", pass);
            result.put("failCount", fail);
            return result;
        } catch (IOException e) {
            throw new AppSqlException(e);
        }
    }
}


