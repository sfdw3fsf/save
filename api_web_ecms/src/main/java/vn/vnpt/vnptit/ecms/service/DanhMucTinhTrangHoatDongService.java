package vn.vnpt.vnptit.ecms.service;

import org.springframework.stereotype.Service;

import vn.vnpt.database.DbContext;
import vn.vnpt.vnptit.ecms.dto.DanhMucTinhTrangHoatDongDtoIn;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.SqlParameter;

import java.sql.Types;
import java.util.ArrayList;

@Service
public class DanhMucTinhTrangHoatDongService {
    private DbContext dbContext;

    public DanhMucTinhTrangHoatDongService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object insertTinhTrangHoatDong(DanhMucTinhTrangHoatDongDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_TEN", obj.getTen(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_GHICHU", obj.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_HIEULUC", obj.getHieuluc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_INSERT_DANHMUC_TINH_TRANG_HOAT_DONG", params);
    }

    public Object updateTinhTrangHoatDong(DanhMucTinhTrangHoatDongDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_ID", obj.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TEN", obj.getTen(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_GHICHU", obj.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_HIEULUC", obj.getHieuluc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_UPDATE_DANHMUC_TINH_TRANG_HOAT_DONG", params);
    }

    public Object deleteTinhTrangHoatDong(DanhMucTinhTrangHoatDongDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_ID", obj.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_DELETE_DANHMUC_TINH_TRANG_HOAT_DONG", params);
    }

    public Object getTinhTrangHoatDong(DanhMucTinhTrangHoatDongDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_ID", obj.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));
        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_GET_DANHMUC_TINH_TRANG_HOAT_DONG_BY_ID", params);
    }

    public Object getAllTinhTrangHoatDong() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_REF_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));
        return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_HATANG_DANHMUC.SP_GET_DANHMUC_TINH_TRANG_HOAT_DONG", params);
    }
}
