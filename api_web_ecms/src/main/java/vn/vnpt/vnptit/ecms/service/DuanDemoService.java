package vn.vnpt.vnptit.ecms.service;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.DuanDemoDto;
import vn.vnpt.vnptit.ecms.dto.DuanDemoSearchDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

@Service
public class DuanDemoService {

    private DbContext dbContext;
    
    public DuanDemoService(DbContext dbContext) {
        this.dbContext = dbContext;
    }
    //-- Lấy Quận Huyện theo ID Tỉnh
    public Object getDistrictsByProvinceId(int provinceId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_tinh_id", provinceId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("rs_list_district", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));

        return dbContext.executeSpWithCursorsToListMap("DEV_IT3.PKG_TEST_DUANNV.get_districts_by_province_id", params);
    }
    //--Lấy Xã Phường theo ID Quận Huyện
    public Object getWardsByDistrictId(int districtId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_quanhuyen_id", districtId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("rs_list_ward", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));

        return dbContext.executeSpWithCursorsToListMap("DEV_IT3.PKG_TEST_DUANNV.get_wards_by_district_id", params);
    }
    //-- Lấy Thông Tin Tỉnh, Quận Huyện, Xã Phường bằng ID Xã Phường
    public Object getLocationInfoByWardId(int wardId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", wardId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));

        return dbContext.executeSpWithCursorToEntity("DEV_IT3.PKG_TEST_DUANNV.get_location_info_by_ward_id", params);
    }
    //-- Lấy danh sách tất cả các tỉnh
    public Object getAllProvinces() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("rs_list_province", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));

        return dbContext.executeSpWithCursorsToListMap("DEV_IT3.PKG_TEST_DUANNV.get_all_provinces", params);
    }
    // Tìm kiếm
    public Object searchUsers(DuanDemoSearchDto duanDemoSearchDto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_ho_ten", duanDemoSearchDto.getHoTen(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_email", duanDemoSearchDto.getEmail(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_so_dien_thoai", duanDemoSearchDto.getSoDienThoai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_so_thich", duanDemoSearchDto.getSoThich(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_chieucao_min", duanDemoSearchDto.getChieuCaoMin(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_chieucao_max", duanDemoSearchDto.getChieuCaoMax(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ngaysinh_min", duanDemoSearchDto.getNgaySinhMin(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_ngaysinh_max", duanDemoSearchDto.getNgaySinhMax(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_tinh", duanDemoSearchDto.getTinhId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_quanhuyen", duanDemoSearchDto.getQuanHuyenId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_xaphuong", duanDemoSearchDto.getXaPhuongId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        params.add(new SqlParameter("p_thoigian_capnhat", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "data"));

        return dbContext.executeSpWithCursorsToListMap("DEV_IT3.PKG_TEST_DUANNV.search_users", params);
    }
    // Lấy toàn bộ user
    public Object getAllUser() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_thoigian_capnhat", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "result_data"));
        params.add(new SqlParameter("total_element", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC, "totalElement"));

        return dbContext.executeSpWithCursorsToListMap("DEV_IT3.PKG_TEST_DUANNV.get_all_users", params);
    }

    // Lấy user theo id
    public Object getUserById(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_thoigian_capnhat", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToEntity("DEV_IT3.PKG_TEST_DUANNV.get_user_by_id", params);
    }

    //Thêm mới
public Object createUser(DuanDemoDto user) throws AppSqlException {
    ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
    params.add(new SqlParameter("p_hoten", user.getHoten(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
    params.add(new SqlParameter("p_chieucao", user.getChieucao(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_email", user.getEmail(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
    params.add(new SqlParameter("p_sodienthoai", user.getSodienthoai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
    params.add(new SqlParameter("p_sothich", user.getSothich(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
    params.add(new SqlParameter("p_ngaysinh", user.getNgaysinh(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
    params.add(new SqlParameter("p_diachi", user.getDiachi(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR)); // Thêm trường diachi
    params.add(new SqlParameter("p_xaphuong_id", user.getXaphuongId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC)); // Thêm trường xaphuongId
    params.add(new SqlParameter("p_thoigian_capnhat", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

    return dbContext.executeSpWithCursorToEntity("DEV_IT3.PKG_TEST_DUANNV.create_user", params);
}

//Cập nhật
public Object updateUser(DuanDemoDto user, int id) throws AppSqlException {
    ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
    params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_ho_ten", user.getHoten(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
    params.add(new SqlParameter("p_chieucao", user.getChieucao(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_email", user.getEmail(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
    params.add(new SqlParameter("p_so_dien_thoai", user.getSodienthoai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
    params.add(new SqlParameter("p_so_thich", user.getSothich(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
    params.add(new SqlParameter("p_ngay_sinh", user.getNgaysinh(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
    params.add(new SqlParameter("p_diachi", user.getDiachi(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR)); // Thêm trường diachi
    params.add(new SqlParameter("p_xaphuong_id", user.getXaphuongId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC)); // Thêm trường xaphuongId
    params.add(new SqlParameter("p_thoigian_capnhat", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

    return dbContext.executeSpWithCursorToEntity("DEV_IT3.PKG_TEST_DUANNV.update_user", params);
}

    //Xóa
    public Object deleteUser(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToEntity("DEV_IT3.PKG_TEST_DUANNV.delete_user", params);
    }

    


}
