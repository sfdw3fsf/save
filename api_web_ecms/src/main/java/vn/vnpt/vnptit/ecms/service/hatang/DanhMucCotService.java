package vn.vnpt.vnptit.ecms.service.hatang;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.message.DataKeyInput;

import vn.vnpt.vnptit.ecms.dto.hatang.DanhMucCotDto;
@Service
public class DanhMucCotService {
    private final DbContext dbcontext;
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public DanhMucCotService(DbContext dbContext, JdbcTemplate jdbcTemplate) {
        this.dbcontext = dbContext;
        this.jdbcTemplate = jdbcTemplate;
    }
    public Object getPhong(String loaiPhong ) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_loai", loaiPhong, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbcontext.executeSpWithCursorToListMap("ecms.pkg_hatang_danhmuc_cot.SP_GET_DS_PHONG", params);
    }

    public ArrayList<Map<String, Object>> dscots() throws AppSqlException {
        SqlParameter param = new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        dbcontext.setConnection(ConnectionManager.Connections.CSS);
        return dbcontext.executeSpWithCursorToListMap("ECMS.pkg_hatang_danhmuc_cot.SP_GET_DS_ALLCOT", param);
    }
    public Object upCot(DanhMucCotDto item) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("id", item.getId(), Types.NUMERIC));
        params.add(new SqlParameter("phong", item.getPhong(), Types.NUMERIC));
        params.add(new SqlParameter("ten", item.getTen(), Types.VARCHAR));
        params.add(new SqlParameter("chieuDai", item.getChieuDai(), Types.NUMERIC));
        params.add(new SqlParameter("chieuRong", item.getChieuRong(), Types.NUMERIC));
        params.add(new SqlParameter("moTa", item.getMoTa(), Types.VARCHAR));
        params.add(new SqlParameter("toaDoX", item.getToaDoX(), Types.NUMERIC));
        params.add(new SqlParameter("toaDoY", item.getToaDoY(), Types.NUMERIC));
        params.add(new SqlParameter("ghiChu", item.getGhiChu(), Types.VARCHAR));
        params.add(new SqlParameter("nguoi_cn",
                AppRequestContext.getCurrent().getUserName() != null ? AppRequestContext.getCurrent().getUserName()
                        : "",
                Types.VARCHAR));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbcontext.executeStoredProcedure("ECMS.pkg_hatang_danhmuc_cot.SP_COT_INSERT", params);
        return result.getOutValue();
    }
    public Boolean delCot(Integer input) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("id", input, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        ArrayList<Map<String, Object>> rs = dbcontext.executeSpWithCursorToListMap("ECMS.pkg_hatang_danhmuc_cot.SP_COT_DELETE", parameters);
        if (rs != null && !rs.isEmpty()) {
            if ("1".equals(String.valueOf(rs.get(0).get("item"))))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    public ArrayList<Map<String, Object>> searchCot(DanhMucCotDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("phong", dto.getPhong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("ten", dto.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbcontext.executeSpWithCursorToListMap("ECMS.pkg_hatang_danhmuc_cot.SP_COT_SEARCH", params);
    }
}
