package vn.vnpt.vnptit.ecms.service;

import org.springframework.stereotype.Service;

import vn.vnpt.database.DbContext;
import vn.vnpt.vnptit.ecms.dto.DanhMucLoaiBackupDtoIn;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.SqlParameter;

import java.sql.Types;
import java.util.ArrayList;

@Service
public class DanhMucLoaiBackupService {
    private DbContext dbContext;

    public DanhMucLoaiBackupService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object insertLoaiBackup(DanhMucLoaiBackupDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_TEN", obj.getTen(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_GHICHU", obj.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_HIEULUC", obj.getHieuluc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_INSERT_DANHMUC_LOAI_BACKUP", params);
    }

    public Object updateLoaiBackup(DanhMucLoaiBackupDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_ID", obj.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TEN", obj.getTen(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_GHICHU", obj.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_HIEULUC", obj.getHieuluc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_UPDATE_DANHMUC_LOAI_BACKUP", params);
    }

    public Object deleteLoaiBackup(DanhMucLoaiBackupDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_ID", obj.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_DELETE_DANHMUC_LOAI_BACKUP", params);
    }

    public Object getLoaiBackup(DanhMucLoaiBackupDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_ID", obj.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));
        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_GET_DANHMUC_LOAI_BACKUP_BY_ID", params);
    }

    public Object getAllLoaiBackup() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_REF_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));
        return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_HATANG_DANHMUC.SP_GET_DANHMUC_LOAI_BACKUP", params);
    }
}
