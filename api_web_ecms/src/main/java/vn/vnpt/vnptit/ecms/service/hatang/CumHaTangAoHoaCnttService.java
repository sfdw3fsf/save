package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.CumHaTangAoHoaCnttDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class CumHaTangAoHoaCnttService {
    private final DbContext dbcontext;
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public CumHaTangAoHoaCnttService(DbContext dbContext, JdbcTemplate jdbcTemplate) {
        this.dbcontext = dbContext;
        this.jdbcTemplate = jdbcTemplate;
    }
    public ArrayList<Map<String, Object>> dmphanloaihatang() throws AppSqlException {
        SqlParameter param = new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        dbcontext.setConnection(ConnectionManager.Connections.CSS);
        return dbcontext.executeSpWithCursorToListMap("ECMS.pkg_hatang_cumaohoa_cntt.SP_GET_DS_dmphanloaihatang", param);
    }
    public ArrayList<Map<String, Object>> dmloaihatang() throws AppSqlException {
        SqlParameter param = new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        dbcontext.setConnection(ConnectionManager.Connections.CSS);
        return dbcontext.executeSpWithCursorToListMap("ECMS.pkg_hatang_cumaohoa_cntt.SP_GET_DS_dmloaihatang", param);
    }
    public ArrayList<Map<String, Object>> dmhinhthucdautu() throws AppSqlException {
        SqlParameter param = new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        dbcontext.setConnection(ConnectionManager.Connections.CSS);
        return dbcontext.executeSpWithCursorToListMap("ECMS.pkg_hatang_cumaohoa_cntt.SP_GET_DS_dmhinhthucdautu", param);
    }
    public ArrayList<Map<String, Object>> dmidc() throws AppSqlException {
        SqlParameter param = new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        dbcontext.setConnection(ConnectionManager.Connections.CSS);
        return dbcontext.executeSpWithCursorToListMap("ECMS.pkg_hatang_cumaohoa_cntt.SP_GET_DS_dmidc", param);
    }
    public ArrayList<Map<String, Object>> dmhatangmang() throws AppSqlException {
        SqlParameter param = new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        dbcontext.setConnection(ConnectionManager.Connections.CSS);
        return dbcontext.executeSpWithCursorToListMap("ECMS.pkg_hatang_cumaohoa_cntt.SP_GET_DS_dmhatangmang", param);
    }
    public ArrayList<Map<String, Object>> dmcapdohatang() throws AppSqlException {
        SqlParameter param = new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        dbcontext.setConnection(ConnectionManager.Connections.CSS);
        return dbcontext.executeSpWithCursorToListMap("ECMS.pkg_hatang_cumaohoa_cntt.SP_GET_DS_dmcapdohatang", param);
    }
    public ArrayList<Map<String, Object>> dmcongnghehatang() throws AppSqlException {
        SqlParameter param = new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        dbcontext.setConnection(ConnectionManager.Connections.CSS);
        return dbcontext.executeSpWithCursorToListMap("ECMS.pkg_hatang_cumaohoa_cntt.SP_GET_DS_dmcongnghehatang", param);
    }
    public ArrayList<Map<String, Object>> dmtrangthaiccdv() throws AppSqlException {
        SqlParameter param = new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        dbcontext.setConnection(ConnectionManager.Connections.CSS);
        return dbcontext.executeSpWithCursorToListMap("ECMS.pkg_hatang_cumaohoa_cntt.SP_GET_DS_dmtrangthaiccdv", param);
    }

    public Object getCumaohoa(Integer loai ) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_loaicum", loai, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbcontext.executeSpWithCursorToListMap("ecms.pkg_hatang_cumaohoa_cntt.SP_GET_DS_cumaohoa", params);
    }
    public Boolean delCumaohoa(Integer cumhtId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("cumht_id", cumhtId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        ArrayList<Map<String, Object>> rs = dbcontext.executeSpWithCursorToListMap("ECMS.pkg_hatang_cumaohoa_cntt.SP_cumaohoa_DELETE", parameters);
        if (rs != null && !rs.isEmpty()) {
            if ("1".equals(String.valueOf(rs.get(0).get("item"))))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    public Object upCumaohoa(CumHaTangAoHoaCnttDto item) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_cumht_id", item.getCumht_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_cumht_aohoa_id", item.getCumht_aohoa_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_ma_cumht", item.getMa_cumht(), Types.VARCHAR));
        params.add(new SqlParameter("p_ten_cumht", item.getTen_cumht(), Types.VARCHAR));
        params.add(new SqlParameter("p_ten_hienthi", item.getTen_hienthi(), Types.VARCHAR));
        params.add(new SqlParameter("p_phanloaiht_id", item.getPhanloaiht_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_loaiht_id", item.getLoaiht_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_sudung", item.getSudung(), Types.NUMERIC));
        params.add(new SqlParameter("p_hinhthucdt_id", item.getHinhthucdt_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_idc_id", item.getIdc_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_datacenter", item.getDatacenter(), Types.VARCHAR));
        params.add(new SqlParameter("p_nhomht_id", item.getNhomht_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_htm_id", item.getHtm_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_capdoht_id", item.getCapdoht_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_congnghe_id", item.getCongnghe_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_link_quantri", item.getLink_quantri(), Types.VARCHAR));
        params.add(new SqlParameter("p_trangthaiccdv_id", item.getTrangthaiccdv_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", item.getGhichu(), Types.VARCHAR));
        params.add(new SqlParameter("p_donvi_ql_id", item.getDonvi_ql_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_phong_ql_id", item.getPhong_ql_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_ql_id", item.getNhanvien_ql_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn",
                AppRequestContext.getCurrent().getUserName() != null ? AppRequestContext.getCurrent().getUserName()
                        : "",
                Types.VARCHAR));
        SqlParameter result = new SqlParameter(
                "p_result",
                null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.NUMERIC
        );
        params.add(result);
        dbcontext.executeStoredProcedure("ECMS.pkg_hatang_cumaohoa_cntt.SP_cumaohoa_UPDATE", params);
        return result.getOutValue();
    }
}