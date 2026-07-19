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
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SanPhamDichVuService {
    private final DbContext dbContext;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SanPhamDichVuService(DbContext dbContext, JdbcTemplate jdbcTemplate) {
        this.dbContext = dbContext;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Object dsSPDV() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ecms.PKG_PHIEU_CAPPHAT.SP_GET_SPDV", params);
    }
}
