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
import vn.vnpt.vnptit.ecms.dto.idc.DeleteMucDichSuDungDto;
import vn.vnpt.vnptit.ecms.dto.idc.MucDichSuDungDto;

@Service
public class MucDichSuDungService {

    private final DbContext dbContext;

    public MucDichSuDungService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    /**
     * Lấy danh sách hoặc tìm kiếm mục đích sử dụng
     * Sử dụng SP_GET_DS_MUCDICH_SUDUNG với các tham số tùy chọn
     */
    public Object getListMucDichSuDung(MucDichSuDungDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto != null ? dto.getId() : null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto != null ? dto.getTen() : null, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_loai_mucdich_id", dto != null ? dto.getNhomMucDichId() : null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hieu_luc", dto != null ? dto.getHieuLuc() : null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_GET_DS_MUCDICH_SUDUNG", params);
    }

    /**
     * Lấy chi tiết mục đích sử dụng theo ID
     */
    public Object getMucDichSuDungById(Integer id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_MUCDICH_SUDUNG_BY_ID", params);
    }

    /**
     * Upsert mục đích sử dụng (thêm mới hoặc cập nhật)
     */
    public Object upsertMucDichSuDung(MucDichSuDungDto dto) throws AppSqlException {
        dto.setNguoiCn(AppRequestContext.getCurrent().getUserName());
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_loai_mucdich_id", dto.getNhomMucDichId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hieu_luc", dto.getHieuLuc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghi_chu", dto.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", dto.getNguoiCn(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

        Map<String, Object> result = dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_UPSERT_MUCDICH_SUDUNG", params);
        return result.get("item");
    }

    /**
     * Xóa nhiều mục đích sử dụng
     */
    public Boolean deleteMucDichSuDungMulti(DeleteMucDichSuDungDto dto) throws AppSqlException {
        String idsString = "";
        if (dto.getIds() != null && dto.getIds().length > 0) {
            // xóa ký tự [ ]
            idsString = java.util.Arrays.toString(dto.getIds()).replaceAll("[\\[\\]]", "");
        }

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ids", idsString, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

        Map<String, Object> result = dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_DELETE_MUCDICH_SUDUNG_MULTI", params);
        return "1".equals(result.get("item").toString());
    }

    /**
     * Validate một mục đích sử dụng
     */
    public Boolean validateMucDichSuDung(MucDichSuDungDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loai_mucdich_id", dto.getNhomMucDichId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_error", null, SqlParameter.ParameterDirection.OUTPUT, Types.VARCHAR));

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_VALIDATE_MUCDICH_SUDUNG", params);

        Number p_result = (Number) params.get(3).getOutValue();
        String errorMsg = (String) params.get(4).getOutValue();

        int result = p_result != null ? p_result.intValue() : 0;

        if (result == 0) {
            throw new AppSqlException("Validation failed: " + (errorMsg != null ? errorMsg : "Validation failed"));
        }

        return true;
    }

    /**
     * Validate nhiều mục đích sử dụng
     */
    public Boolean validateMucDichSuDungMulti(List<MucDichSuDungDto> dto) throws AppSqlException {
        try {
            String jsonListData = JsonConvert.serialize(dto);
            String nguoiCn = AppRequestContext.getCurrent().getUserName();

            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_list_data", jsonListData, SqlParameter.ParameterDirection.INPUT, Types.CLOB));
            params.add(new SqlParameter("p_nguoi_cn", nguoiCn, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_error", null, SqlParameter.ParameterDirection.OUTPUT, Types.CLOB));

            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_VALIDATE_MUCDICH_SUDUNG_MULTI", params);

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
}

