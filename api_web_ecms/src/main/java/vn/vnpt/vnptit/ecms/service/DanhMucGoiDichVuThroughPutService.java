package vn.vnpt.vnptit.ecms.service;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import java.sql.Types;
import java.util.ArrayList;
import vn.vnpt.vnptit.ecms.dto.DanhMucGoiDichVuThroughPutDtoIn;

@Service
public class DanhMucGoiDichVuThroughPutService {
    private DbContext dbContext;

    public DanhMucGoiDichVuThroughPutService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object insertThroughput(DanhMucGoiDichVuThroughPutDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_TEN", obj.getTen(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_GIATRI", obj.getGiatri(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_GHICHU", obj.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_HIEULUC", obj.getHieuluc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_INSERT_DANHMUC_GOIDICHVU_THROUGHPUT", params);
    }

    public Object updateThroughput(DanhMucGoiDichVuThroughPutDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_ID", obj.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TEN", obj.getTen(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_GIATRI", obj.getGiatri(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_GHICHU", obj.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_HIEULUC", obj.getHieuluc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_UPDATE_DANHMUC_GOIDICHVU_THROUGHPUT", params);
    }

    public Object deleteThroughput(DanhMucGoiDichVuThroughPutDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_ID", obj.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_DELETE_DANHMUC_GOIDICHVU_THROUGHPUT", params);
    }

    public Object getThroughput(DanhMucGoiDichVuThroughPutDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_ID", obj.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));
        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_GET_DANHMUC_GOIDICHVU_THROUGHPUT_BY_ID", params);
    }

    public Object getAllThroughput() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_REF_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));
        return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_HATANG_DANHMUC.SP_GET_DANHMUC_GOIDICHVU_THROUGHPUT", params);
    }
}