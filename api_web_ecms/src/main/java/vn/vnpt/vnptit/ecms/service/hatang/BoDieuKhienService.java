package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoDieuKhienService {
    private final DbContext dbContext;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BoDieuKhienService(DbContext dbContext, JdbcTemplate jdbcTemplate) {
        this.dbContext = dbContext;
        this.jdbcTemplate = jdbcTemplate;
    }

    // Get danh sach bo dieu khien
    public Object GetBoDieuKhien(Integer tb_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_tb_id", tb_id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ecms.PKG_HATANG_DANHMUC_THIETBI.SP_GET_CONTROLLER", params);
    }
}
