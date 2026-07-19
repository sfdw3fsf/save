package vn.vnpt.vnptit.ecms.service;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.DevTestPhLanDto;
import vn.vnpt.vnptit.ecms.dto.DevTestPhLanFilterDto;

import java.sql.Date;
import java.sql.Types;
import java.util.ArrayList;

@Service
public class DevTestPhLanService {
    private DbContext dbContext;


    public DevTestPhLanService(DbContext dbContext) { this.dbContext = dbContext;}


    public Object getListUserWithFilterAndPagination() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

        params.add(new SqlParameter("list_user", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));
        params.add((new SqlParameter("total_element", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC, "totalElement")));

        return dbContext.executeSpWithCursorsToListMap("DEV_IT3.PKG_TEST_LANPH.get_list_user", params);
    }

    public  Object getUserById(Long userId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

        params.add(new SqlParameter("p_user_id", userId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("DEV_IT3.PKG_TEST_LANPH.get_user_by_id", params);
    }

    public Object createUser(DevTestPhLanDto devTestPhLanDto) throws  AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

        params.add(new SqlParameter("p_hoten", devTestPhLanDto.getHoten(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_chieucao", devTestPhLanDto.getChieucao(), SqlParameter.ParameterDirection.INPUT, Types.DOUBLE));
        params.add(new SqlParameter("p_email", devTestPhLanDto.getEmail(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sodienthoai", devTestPhLanDto.getSodienthoai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sothich", devTestPhLanDto.getSothich(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ngaysinh", devTestPhLanDto.getNgaysinh(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_diachi", devTestPhLanDto.getDiachi(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_xaphuong_id", devTestPhLanDto.getXaphuong_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("DEV_IT3.PKG_TEST_LANPH.create_user", params);
    }

    public Object deleteUser(int userId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

        params.add(new SqlParameter("p_user_id", userId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap("DEV_IT3.PKG_TEST_LANPH.delete_user", params);
    }

    public Object updateUser(int userId, DevTestPhLanDto devTestPhLanDto) throws  AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

        params.add(new SqlParameter("p_user_id", userId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hoten", devTestPhLanDto.getHoten(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_chieucao", devTestPhLanDto.getChieucao(), SqlParameter.ParameterDirection.INPUT, Types.DOUBLE));
        params.add(new SqlParameter("p_email", devTestPhLanDto.getEmail(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sodienthoai", devTestPhLanDto.getSodienthoai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sothich", devTestPhLanDto.getSothich(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ngaysinh", devTestPhLanDto.getNgaysinh(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_diachi", devTestPhLanDto.getDiachi(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_xaphuong_id", devTestPhLanDto.getXaphuong_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("DEV_IT3.PKG_TEST_LANPH.update_user", params);
    }

    public Object getAllProvince() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_rs",null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("DEV_IT3.PKG_TEST_LANPH.get_all_province", params);
    }
    public Object getAllDistricByProvinceId(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id",id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs",null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("DEV_IT3.PKG_TEST_LANPH.get_district_by_province_id", params);
    }

    public Object getAllWardByDistrictId(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id",id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs",null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("DEV_IT3.PKG_TEST_LANPH.get_ward_by_district_id", params);
    }

    public Object getProvinceAndDistrictByWardId(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id",id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs",null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("DEV_IT3.PKG_TEST_LANPH.get_province_district_by_ward_id", params);
    }

    public Object filterUsers(DevTestPhLanFilterDto devTestPhLanFilterDto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_hoten", devTestPhLanFilterDto.getHoten(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_email", devTestPhLanFilterDto.getEmail(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sodienthoai", devTestPhLanFilterDto.getSodienthoai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sothich", devTestPhLanFilterDto.getSothich(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_chieucao_from",devTestPhLanFilterDto.getChieucaoFrom(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_chieucao_to", devTestPhLanFilterDto.getChieucaoTo(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ngaysinh_from", devTestPhLanFilterDto.getNgaysinhFrom(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_ngaysinh_to", devTestPhLanFilterDto.getNgaysinhTo(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_tinh_id", devTestPhLanFilterDto.getTinh_id(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_quanhuyen_id", devTestPhLanFilterDto.getQuanhuyen_id(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_xaphuong_id", devTestPhLanFilterDto.getXaphuong_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("DEV_IT3.PKG_TEST_LANPH.filter_users", params);

    }


}