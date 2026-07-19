package vn.vnpt.vnptit.ecms.service;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.LtbaoTestDto;

import java.sql.Types;
import java.util.ArrayList;

@Service
public class LtbaoTestService {
    private DbContext dbContext;

    public LtbaoTestService(DbContext dbContext) {this.dbContext = dbContext;}

    public Object getListDevTest() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

        params.add(new SqlParameter("list", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));
        params.add(new SqlParameter("total_element", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC, "totalElement"));

        return dbContext.executeSpWithCursorsToListMap("DEV_IT3.PKG_TEST_LTBAO.get_list", params);
    }

    public Object getDetailsById(int dtId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

        params.add(new SqlParameter("dt_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        params.add(new SqlParameter("dt_id", dtId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToEntity("DEV_IT3.PKG_TEST_LTBAO.get_details_by_id", params);
    }

    public Object createDevTest(LtbaoTestDto data) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

        params.add(new SqlParameter("dt_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        params.add(new SqlParameter("dt_hoten", data.getHoTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("dt_chieucao", data.getChieuCao(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("dt_email", data.getEmail(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("dt_sodienthoai", data.getSoDienThoai(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("dt_sothich", data.getSoThich(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("dt_ngaysinh", data.getNgaySinh(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("dt_diachi", data.getDiaChi(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("dt_xaphuong_id", data.getXaPhuongId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToEntity("DEV_IT3.PKG_TEST_LTBAO.create_dt", params);
    }

    public Object updateDevTestById(int dtId, LtbaoTestDto data) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

        params.add(new SqlParameter("dt_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        params.add(new SqlParameter("dt_id", dtId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("dt_hoten", data.getHoTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("dt_chieucao", data.getChieuCao(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("dt_email", data.getEmail(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("dt_sodienthoai", data.getSoDienThoai(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("dt_sothich", data.getSoThich(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("dt_ngaysinh", data.getNgaySinh(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("dt_diachi", data.getDiaChi(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("dt_xaphuong_id", data.getXaPhuongId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToEntity("DEV_IT3.PKG_TEST_LTBAO.update_dt_by_id", params);
    }

    public Object deleteDevTestById(int dtId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

        params.add(new SqlParameter("dt_id", dtId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToListMap("DEV_IT3.PKG_TEST_LTBAO.delete_dt_by_id", params);
    }

    public Object getDistrictByWardId(int xaPhuongId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

        params.add(new SqlParameter("d_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));
        params.add(new SqlParameter("p_phuong_id", xaPhuongId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToEntity("DEV_IT3.PKG_TEST_LTBAO.get_district_by_ward_id", params);
    }

    public Object getProviceByDistrictId(int quanHuyenId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

        params.add(new SqlParameter("pr_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));
        params.add(new SqlParameter("d_district_id", quanHuyenId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToEntity("DEV_IT3.PKG_TEST_LTBAO.get_province_by_district_id", params);
    }

    public Object getProvices() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

        params.add(new SqlParameter("pr_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));

        return dbContext.executeSpWithCursorsToListMap("DEV_IT3.PKG_TEST_LTBAO.get_provinces", params);
    }

    public Object getDistrictsByProvinceId(int tinhId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

        params.add(new SqlParameter("d_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));
        params.add(new SqlParameter("pr_id", tinhId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorsToListMap("DEV_IT3.PKG_TEST_LTBAO.get_districts_by_pronvince_id", params);
    }

    public Object getWardsByDistrictId(int quanHuyenId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));
        params.add(new SqlParameter("d_id", quanHuyenId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorsToListMap("DEV_IT3.PKG_TEST_LTBAO.get_districts_by_pronvince_id", params);
    }

}
