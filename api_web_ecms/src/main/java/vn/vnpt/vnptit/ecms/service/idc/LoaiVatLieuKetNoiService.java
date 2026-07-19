package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.stereotype.Service;
import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.LoaiVatLieuKetNoiDto;
import vn.vnpt.vnptit.ecms.dto.idc.DeleteLoaiVatLieuKetNoiDto;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoaiVatLieuKetNoiService {

    private final DbContext dbContext;

    public LoaiVatLieuKetNoiService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getListLoaiVatLieuKetNoi() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_LAY_DS_LOAI_VATLIEU_KETNOI", params);
    }

    public Object upsertLoaiVatLieuKetNoi(LoaiVatLieuKetNoiDto dto) throws AppSqlException {
        dto.setNguoiCn(AppRequestContext.getCurrent().getUserName());
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaivl_id", dto.getLoaivlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_thongso", dto.getThongSo(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ghichu", dto.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_icon_path", dto.getIconPath(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sudung", dto.getSuDung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", dto.getNguoiCn(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

        Map<String, Object> result = dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN.SP_UPSERT_LOAI_VATLIEU_KETNOI", params);
        return "1".equals(result.get("item").toString());
    }

    public Boolean deleteLoaiVatLieuKetNoiMulti(DeleteLoaiVatLieuKetNoiDto dto) throws AppSqlException {
        String idsString = "";
        if (dto.getIds() != null && dto.getIds().length > 0) {
            // xóa ký tự [ ]
            idsString = java.util.Arrays.toString(dto.getIds()).replaceAll("[\\[\\]]", "");
        }

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ids", idsString, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

        Map<String, Object> result = dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN.SP_DELETE_LOAI_VATLIEU_KETNOI_MULTI", params);
        return "1".equals(result.get("item").toString());
    }

    public Map<String, Object> upsertLoaiVatLieuKetNoiMulti(List<LoaiVatLieuKetNoiDto> dto) throws AppSqlException {
        try {
            ArrayList<SqlParameter> params = buildLoaiVatLieuKetNoiMultiParams(dto);
            params.add(new SqlParameter("p_pass", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_fail", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

            dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN.SP_UPSERT_LOAI_VATLIEU_KETNOI_MULTI", params);
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

    public Boolean checkValidVatLieuKetNoiMulti(List<LoaiVatLieuKetNoiDto> dto) throws AppSqlException {
        try {
            ArrayList<SqlParameter> params = buildLoaiVatLieuKetNoiMultiParams(dto);
            params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_error", null, SqlParameter.ParameterDirection.OUTPUT, Types.CLOB));

            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_VALIDATE_LOAI_VATLIEU_KETNOI_MULTI", params);

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

    private ArrayList<SqlParameter> buildLoaiVatLieuKetNoiMultiParams(List<LoaiVatLieuKetNoiDto> dto) throws IOException {
        String jsonListData = JsonConvert.serialize(dto);
        String nguoiCn = AppRequestContext.getCurrent().getUserName();

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_list_data", jsonListData, SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_nguoi_cn", nguoiCn, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        return params;
    }
}
