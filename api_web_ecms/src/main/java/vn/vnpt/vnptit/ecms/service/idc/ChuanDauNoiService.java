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
import vn.vnpt.vnptit.ecms.dto.idc.ChuanDauNoiDto;
import vn.vnpt.vnptit.ecms.dto.idc.DeleteChuanDauNoiDto;

@Service
public class ChuanDauNoiService {

    private final DbContext dbContext;

    public ChuanDauNoiService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    /**
     * Lấy danh sách hoặc tìm kiếm chuẩn đấu nối
     * Sử dụng SP_GET_DS_CHUAN_DAUNOI với các tham số tùy chọn
     */
    public Object getListChuanDauNoi(ChuanDauNoiDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto != null ? dto.getId() : null, SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_chuan_dau_noi", dto != null ? dto.getChuanDauNoi() : null,
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_chuan_id", dto != null ? dto.getChuanId() : null,
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hieu_luc", dto != null ? dto.getHieuLuc() : null,
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_GET_DS_CHUAN_DAUNOI", params);
    }

    /**
     * Upsert chuẩn đấu nối (thêm mới hoặc cập nhật)
     */
    public Object upsertChuanDauNoi(ChuanDauNoiDto dto) throws AppSqlException {
        dto.setNguoiCn(AppRequestContext.getCurrent().getUserName());
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_chuan_dau_noi", dto.getChuanDauNoi(), SqlParameter.ParameterDirection.INPUT,
                Types.VARCHAR));
        params.add(new SqlParameter("p_chuan_id", dto.getChuanId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("p_hieu_luc", dto.getHieuLuc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("p_nguoi_cn", dto.getNguoiCn(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

        Map<String, Object> result = dbContext
                .executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_UPSERT_CHUAN_DAUNOI", params);
        return result.get("item");
    }

    /**
     * Xóa nhiều chuẩn đấu nối
     */
    public Boolean deleteChuanDauNoiMulti(DeleteChuanDauNoiDto dto) throws AppSqlException {
        String idsString = "";
        if (dto.getIds() != null && dto.getIds().length > 0) {
            // xóa ký tự [ ]
            idsString = java.util.Arrays.toString(dto.getIds()).replaceAll("[\\[\\]]", "");
        }

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ids", idsString, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

        Map<String, Object> result = dbContext
                .executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_DELETE_CHUAN_DAUNOI_MULTI", params);
        return "1".equals(result.get("item").toString());
    }

    /**
     * Validate một chuẩn đấu nối
     */
    public Boolean validateChuanDauNoi(ChuanDauNoiDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_chuan_dau_noi", dto.getChuanDauNoi(), SqlParameter.ParameterDirection.INPUT,
                Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_error", null, SqlParameter.ParameterDirection.OUTPUT, Types.VARCHAR));

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_VALIDATE_CHUAN_DAUNOI", params);

        Number p_result = (Number) params.get(2).getOutValue();
        String errorMsg = (String) params.get(3).getOutValue();

        int result = p_result != null ? p_result.intValue() : 0;

        if (result == 0) {
            throw new AppSqlException("Validation failed: " + (errorMsg != null ? errorMsg : "Validation failed"));
        }

        return true;
    }

    /**
     * Validate nhiều chuẩn đấu nối
     */
    public Boolean validateChuanDauNoiMulti(List<ChuanDauNoiDto> dto) throws AppSqlException {
        try {
            String jsonListData = JsonConvert.serialize(dto);
            String nguoiCn = AppRequestContext.getCurrent().getUserName();

            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(
                    new SqlParameter("p_list_data", jsonListData, SqlParameter.ParameterDirection.INPUT, Types.CLOB));
            params.add(new SqlParameter("p_nguoi_cn", nguoiCn, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_error", null, SqlParameter.ParameterDirection.OUTPUT, Types.CLOB));

            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_VALIDATE_CHUAN_DAUNOI_MULTI", params);

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
