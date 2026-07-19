package vn.vnpt.vnptit.ecms.service;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.TuanDdDTO;
import vn.vnpt.vnptit.ecms.dto.TuanDdSearchDTO;

import javax.validation.Valid;
import java.sql.Types;
import java.util.ArrayList;


@Service
public class TuanDdService {
	private final DbContext dbContext;
    
    public TuanDdService(DbContext dbContext) {
        this.dbContext = dbContext;
    }
    

    public Object createNewUser(TuanDdDTO user) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_ho_ten",user.getHoten() , SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_chieu_cao", user.getChieucao(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_email", user.getEmail(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_so_dien_thoai", user.getSodienthoai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_so_thich", user.getSothich(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ngay_sinh", user.getNgaysinh(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_dia_chi", user.getDiachi(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_xaphuong_id", user.getXaphuongid(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToEntity("DEV_IT3.PKG_TEST_TUANDD.tuandd_create_user", params);
    }
    public Object getUserById(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT,  java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToEntity("DEV_IT3.PKG_TEST_TUANDD.tuandd_get_user_by_id", params);
    }
    public Object getAllUser() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("list_user", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));
        params.add(new SqlParameter("total_user", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC, "totalUser"));
        return dbContext.executeSpWithCursorsToListMap("DEV_IT3.PKG_TEST_TUANDD.tuandd_getuser", params);
    }
    public Object updateUser(TuanDdDTO user, int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id",id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ho_ten",user.getHoten() , SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_chieu_cao", user.getChieucao(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_email", user.getEmail(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_so_dien_thoai", user.getSodienthoai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_so_thich", user.getSothich(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ngay_sinh", user.getNgaysinh(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_dia_chi", user.getDiachi(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_xaphuong_id", user.getXaphuongid(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToEntity("DEV_IT3.PKG_TEST_TUANDD.tuandd_update_user", params);
    }
    public Object deleteUser(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToEntity("DEV_IT3.PKG_TEST_TUANDD.tuandd_delete", params);
    }
    public Object getAllProvince() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("list_provicne", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));
        return dbContext.executeSpWithCursorsToListMap("DEV_IT3.PKG_TEST_TUANDD.tuandd_get_all_province", params);
    }
    public Object getAllDistrictByProvinceId(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("list_district", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));
        return dbContext.executeSpWithCursorsToListMap("DEV_IT3.PKG_TEST_TUANDD.tuandd_get_information_district_by_province_id", params);
    }
    public Object getAllWardByDistrictId(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("list_ward", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));

        return dbContext.executeSpWithCursorsToListMap("DEV_IT3.PKG_TEST_TUANDD.tuandd_get_information_ward_by_district_id", params);
    }
    public Object getProvinceAndDistrictByWardId(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));
        return dbContext.executeSpWithCursorToEntity("DEV_IT3.PKG_TEST_TUANDD.tuandd_get_information_district_province", params);
    }

    public Object filterUsers(@Valid TuanDdSearchDTO filter) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_ho_ten", filter.getHoten(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_email", filter.getEmail(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_so_dien_thoai", filter.getSodienthoai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_so_thich", filter.getSothich(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_chieu_cao_from", filter.getChieucaofrom(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_chieu_cao_to", filter.getChieucaoto(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_sinh_from", filter.getNgaysinhfrom(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_ngay_sinh_to", filter.getNgaysinhto(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_tinh_id", filter.getTinhid(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_quanhuyen_id", filter.getQuanhuyenid(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_xaphuong_id", filter.getXaphuongid(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));
        return dbContext.executeSpWithCursorsToListMap("DEV_IT3.PKG_TEST_TUANDD.tuandd_filter_user", params);
    }
}
