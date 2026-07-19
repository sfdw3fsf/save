package vn.vnpt.vnptit.ecms.service.idc;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.danhmuc.IDCDto;

@Service
public class IDCService {
    @Autowired
    private DbContext dbContext;

    public ArrayList<Map<String, Object>> getListIDC() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_STATISTIC.SP_GET_ALL_IDC", params);
    }

    public Object getById(Long id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_STATISTIC.SP_GET_IDC_BY_ID", params);
    }


    public ArrayList<Map<String, Object>> getListIDCStatistic() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_STATISTIC.SP_GET_IDC_STATISTIC", params);
    }

    public Object getIDCStatisticDetail(Long idcId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_IDC_ID", idcId, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_STATISTIC.SP_GET_IDC_STATISTIC_DETAIL", params);
    }

    public ArrayList<Map<String, Object>> getIDCRackStatistic(Long idcId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_IDC_ID", idcId, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_STATISTIC.SP_GET_IDC_RACK_STATISTIC", params);
    }

    public Object saveIDC(IDCDto idc) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        SqlParameter p_id = new SqlParameter("P_ID", idc.getId(), Types.NUMERIC);
        params.add(p_id);
        params.add(new SqlParameter("P_DONVI_ID", idc.getIdDonVi(), Types.NUMERIC));
        params.add(new SqlParameter("P_TEN", idc.getTen(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TONG_DIENTICH", idc.getDienTichTongThe(), ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("P_ANHDAIDIEN", idc.getAnhDaiDien(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TRANGTHAI_VH", idc.getTrangThaiVanHanh(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TOADO_X", idc.getToaDoX(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TOADO_Y", idc.getToaDoY(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DIA_CHI", idc.getDiaChi(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_THOIDIEM_VANHANH", idc.getThoiDiemVanHanh(), ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("P_GHI_CHU", idc.getGhiChu(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TENTAT", idc.getTenTat(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_MOTA", idc.getMoTa(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_SUDUNG", idc.isSuDung(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                ParameterDirection.INPUT, Types.VARCHAR));

        params.add(new SqlParameter("P_OUT", null, ParameterDirection.OUTPUT, Types.NUMERIC));
        Map<String, Object> result = dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_STATISTIC.SP_SAVE_IDC", params);
        return result.get("item");
    }

    public Object saveByFile(List<Map<String, Object>> jsonDataList) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();
        try {
            String jsonString = JsonConvert.serialize(jsonDataList);
            params.add(new SqlParameter("P_JSON", jsonString, ParameterDirection.INPUT,
                    Types.VARCHAR));
        } catch (Exception e) {
            throw new AppSqlException(e);
        }

        SqlParameter valid_rows = new SqlParameter("P_VALID_ROWS", null, SqlParameter.ParameterDirection.OUTPUT,
                Types.NUMERIC);
        SqlParameter invalid_rows = new SqlParameter("P_INVALID_ROWS", null, SqlParameter.ParameterDirection.OUTPUT,
                Types.NUMERIC);
        params.add(valid_rows);
        params.add(invalid_rows);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_STATISTIC.SP_SAVE_MULTI_IDC", params);

        return new HashMap<String, Object>() {
            {
                put("validRows", valid_rows.getOutValue());
                put("invalidRows", invalid_rows.getOutValue());
            }
        };
    }

    public Map<String, Object> validateByFile(List<Map<String, Object>> jsonDataList) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();
        try {
            params.add(new SqlParameter("P_JSON", JsonConvert.serialize(jsonDataList), ParameterDirection.INPUT,
                    Types.VARCHAR));
        } catch (Exception e) {
            throw new AppSqlException(e);
        }

        SqlParameter invalidRows = new SqlParameter("P_INVALID_ROWS", null, SqlParameter.ParameterDirection.OUTPUT,
                Types.NUMERIC);
        SqlParameter errors = new SqlParameter("P_ERROR_MESSAGE", null, SqlParameter.ParameterDirection.OUTPUT,
                Types.VARCHAR);

        params.add(invalidRows);
        params.add(errors);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_STATISTIC.SP_CHECK_SAVE_MULTI_INSERT", params);

        return new HashMap<String, Object>() {
            {
                put("invalidRows", invalidRows.getOutValue());
                put("businessLogicError", errors.getOutValue());
            }
        };
    }

    public Boolean delete(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_ID", id, Types.NUMERIC));
        SqlParameter result = new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_STATISTIC.SP_DELETE_IDC", params);

        BigDecimal rs = (BigDecimal) result.getOutValue();
        return rs != null && rs.intValue() == 1;
    }

    // ===== Không gian
    public Object getMatSan(Long id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_STATISTIC.SP_MAT_SAN_BY_IDC", params);
    }

    public Object getToaNha(Long id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_STATISTIC.SP_GET_TOANHA_BY_IDC", params);
    }

    public Object getKhachHang(Long id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_STATISTIC.SP_KHACHHANG_BY_IDC", params);
    }

}
