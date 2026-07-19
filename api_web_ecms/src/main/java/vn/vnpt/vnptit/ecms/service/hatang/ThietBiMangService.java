package vn.vnpt.vnptit.ecms.service.hatang;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

@Service
public class ThietBiMangService {
    private final DbContext dbContext;

    public ThietBiMangService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getThietBiMang() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_cursor", Types.REF_CURSOR));
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_VATLY.SP_GET_DANHSACH_THIETBI_MANG", params);
    }

    public Object getThietBiMangById(Long id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_cursor", Types.REF_CURSOR));
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_VATLY.SP_GET_DANHSACH_THIETBI_MANG_THEOID", params);
    }
 }
