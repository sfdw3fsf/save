package vn.vnpt.vnptit.ecms.service.hatang;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Service
public class DanhMucLoaiThietBiService {
    private final DbContext dbcontext;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DanhMucLoaiThietBiService(DbContext dbContext, JdbcTemplate jdbcTemplate) {
        this.dbcontext = dbContext;
        this.jdbcTemplate = jdbcTemplate;
    }

    // Get all danh muc loai thiet bi
    public Object getAll(Integer validity) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_validity", validity != null ? validity : null, validity !=null ? Types.NUMERIC : Types.NULL));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbcontext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_LOAI_THIET_BI_GET_ALL", params);
    }

    //    public Object create(DanhMucLoaiThietBiDTO dto) throws AppSqlException {
    public Object create(String name, String classify, String code, String note, Number validity) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanloai", classify, Types.VARCHAR));
        params.add(new SqlParameter("p_ma", code, Types.VARCHAR));
        params.add(new SqlParameter("p_ten", name, Types.NVARCHAR));
        params.add(new SqlParameter("p_note", note, Types.NVARCHAR));
        params.add(new SqlParameter("p_hieuluc", validity, Types.NUMERIC));

        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbcontext.setConnection(ConnectionManager.Connections.CSS);
        dbcontext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_LOAI_THIET_BI_INSERT", params);

        return result.getOutValue();
    }

    public boolean update(Number id, String name, String classify, String code, String note, Number validity) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", name, Types.NVARCHAR));
        params.add(new SqlParameter("p_phanloai", classify, Types.VARCHAR));
        params.add(new SqlParameter("p_ma", code, Types.VARCHAR));
        params.add(new SqlParameter("p_note", note, Types.NVARCHAR));
        params.add(new SqlParameter("p_hieuluc", validity, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbcontext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_LOAI_THIET_BI_UPDATE", params);
        return result.getOutValue().toString().equals("1");
    }

    public boolean deleteById(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbcontext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_LOAI_THIET_BI_DELETE_SINGLE", params);
        return result.getOutValue().toString().equals("1");
    }

    public boolean deleteByIds(Number[] ids) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ids", StringUtils.join(ids, ";"), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbcontext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_LOAI_THIET_BI_DELETE_MULTI", params);
        return result.getOutValue().toString().equals("1");
    }
}
