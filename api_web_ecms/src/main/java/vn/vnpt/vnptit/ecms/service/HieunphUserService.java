package vn.vnpt.vnptit.ecms.service;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.HieunphUserCreateDTO;
import vn.vnpt.vnptit.ecms.dto.HieunphUserFilterDTO;

import java.sql.Types;
import java.util.ArrayList;

@Service
public class HieunphUserService {
    private DbContext dbContext;

    public HieunphUserService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getAllUser() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("list_user", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));
        params.add(new SqlParameter("total_element", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC, "totalElement"));

        return dbContext.executeSpWithCursorsToListMap("DEV_IT3.PKG_TEST_HIEUNPH.hieunph_get_list_user", params);
    }

    public Object getUserById(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToEntity("DEV_IT3.PKG_TEST_HIEUNPH.hieunph_get_user_by_id", params);
    }

    public Object createUser(HieunphUserCreateDTO user) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_ho_ten", user.getHoTen(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_chieu_cao", user.getChieuCao(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_email", user.getEmail(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_so_dien_thoai", user.getSoDienThoai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_so_thich", user.getSoThich(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ngay_sinh", user.getNgaySinh(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_diachi", user.getDiaChi(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_xaphuong_id", user.getXaPhuongId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToEntity("DEV_IT3.PKG_TEST_HIEUNPH.hieunph_create_user", params);
    }

    public Object updateUser(HieunphUserCreateDTO user, int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ho_ten", user.getHoTen(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_chieu_cao", user.getChieuCao(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_email", user.getEmail(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_so_dien_thoai", user.getSoDienThoai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_so_thich", user.getSoThich(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ngay_sinh", user.getNgaySinh(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_diachi", user.getDiaChi(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_xaphuong_id", user.getXaPhuongId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToEntity("DEV_IT3.PKG_TEST_HIEUNPH.hieunph_update_user", params);
    }

    public Object deleteUser(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToEntity("DEV_IT3.PKG_TEST_HIEUNPH.hieunph_delete_user", params);
    }

    public Object getAllProvince() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("rs_list_provicne", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));

        return dbContext.executeSpWithCursorsToListMap("DEV_IT3.PKG_TEST_HIEUNPH.hieunph_get_all_province", params);
    }

    public Object getAllDistrictByProvinceId(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("rs_list_district", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));

        return dbContext.executeSpWithCursorsToListMap("DEV_IT3.PKG_TEST_HIEUNPH.hieunph_get_district_by_province_id", params);
    }

    public Object getAllWardByDistrictId(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("rs_list_ward", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));

        return dbContext.executeSpWithCursorsToListMap("DEV_IT3.PKG_TEST_HIEUNPH.hieunph_get_ward_by_district_id", params);
    }

    public Object getProvinceAndDistrictByWardId(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));

        return dbContext.executeSpWithCursorToEntity("DEV_IT3.PKG_TEST_HIEUNPH.hieunph_get_province_district_by_ward_id", params);
    }


    public Object filterUser(HieunphUserFilterDTO filter) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_ho_ten", filter.getHoTen(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_email", filter.getEmail(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_so_dien_thoai", filter.getSoDienThoai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_so_thich", filter.getSoThich(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_chieu_cao_from", filter.getChieuCaoFrom(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_chieu_cao_to", filter.getChieuCaoTo(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_sinh_from", filter.getNgaySinhFrom(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_ngay_sinh_to", filter.getNgaySinhTo(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_tinh", filter.getTinhId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_quanhuyen", filter.getQuanHuyenId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_xaphuong", filter.getXaPhuongId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));
        System.out.println(filter.getXaPhuongId());
        return dbContext.executeSpWithCursorsToListMap("DEV_IT3.PKG_TEST_HIEUNPH.hieunph_filter_user", params);
    }
}
