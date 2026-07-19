package vn.vnpt.vnptit.ecms.service;

import org.springframework.stereotype.Service;

import vn.vnpt.database.DbContext;
import vn.vnpt.vnptit.ecms.dto.DanhMucPhanLoaiHaTangDtoIn;
import vn.vnpt.vnptit.ecms.dto.IPStoreDtoIn;
import vn.vnpt.vnptit.ecms.dto.KhayDiaTrongTuDia;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.SqlParameter;

import java.sql.Types;
import java.util.ArrayList;

@Service
public class DanhMucPhanLoaiHaTangService {
    private DbContext dbContext;

    public DanhMucPhanLoaiHaTangService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object insertPhanLoaiHaTang(DanhMucPhanLoaiHaTangDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_TEN", obj.getTen(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(
                new SqlParameter("P_GHICHU", obj.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(
                new SqlParameter("P_HIEULUC", obj.getHieuluc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_INSERT_DANHMUC_PHANLOAI_HATANG",
                params);
    }

    public Object updatePhanLoaiHaTang(DanhMucPhanLoaiHaTangDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_ID", obj.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TEN", obj.getTen(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(
                new SqlParameter("P_GHICHU", obj.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(
                new SqlParameter("P_HIEULUC", obj.getHieuluc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_UPDATE_DANHMUC_PHANLOAI_HATANG",
                params);
    }

    public Object deletePhanLoaiHaTang(DanhMucPhanLoaiHaTangDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_ID", obj.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_DELETE_DANHMUC_PHANLOAI_HATANG",
                params);
    }

    public Object getPhanLoaiHaTang(DanhMucPhanLoaiHaTangDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_ID", obj.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR,
                "data"));
        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_GET_DANHMUC_PHANLOAI_HATANG_BY_ID",
                params);
    }

    public Object getAllPhanLoaiHaTang() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_REF_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR,
                "data"));
        return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_HATANG_DANHMUC.SP_GET_DANHMUC_PHANLOAI_HATANG",
                params);
    }

    public Object layDsIPStore() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.SP_GET_ALL_IP_STORE", params);
    }

    public Object layDsIPStoreTheoId(IPStoreDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", obj.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ref_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.SP_GET_IP_STORE_BY_ID", params);
    }

    public Object themMoiIPStore(IPStoreDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_ip", obj.getIp(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_ip_loai_id", obj.getIp_loai_id(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_ip_phanloai_id", obj.getIp_phanloai_id(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_idc_id", obj.getIdc_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vrf_id", obj.getVrf_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("p_zone_id", obj.getZone_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("p_vlan_id", obj.getVlan_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_subnet_id", obj.getSubnet_id(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_subnet_quyhoach_id", obj.getSubnet_quyhoach_id(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hatangmang_id", obj.getHatangmang_id(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_thietbimang_id", obj.getThietbimang_id(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(
                new SqlParameter("p_ghi_chu", obj.getGhi_chu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_hieu_luc", obj.getHieu_luc(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_INSERT_IP_STORE", params);
    }

    public Object capNhatIPStore(IPStoreDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", obj.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ip", obj.getIp(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_ip_loai_id", obj.getIp_loai_id(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_ip_phanloai_id", obj.getIp_phanloai_id(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_idc_id", obj.getIdc_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vrf_id", obj.getVrf_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("p_zone_id", obj.getZone_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("p_vlan_id", obj.getVlan_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_subnet_id", obj.getSubnet_id(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_subnet_quyhoach_id", obj.getSubnet_quyhoach_id(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hatangmang_id", obj.getHatangmang_id(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_thietbimang_id", obj.getThietbimang_id(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(
                new SqlParameter("p_ghi_chu", obj.getGhi_chu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_hieu_luc", obj.getHieu_luc(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_UPDATE_IP_STORE", params);
    }

    public Object xoaIPStore(IPStoreDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", obj.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_DELETE_IP_STORE", params);
    }

    public Object getLichSuCapPhatByIPStore(IPStoreDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_IPSTORE_ID", obj.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.GET_LICHSU_CAPPHAT_IP_BY_IPSTORE",
                params);
    }

    public Object layDsKhayDiaTrongTuDia() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.SP_GET_ALL_KHAY_DIA", params);
    }

    public Object layDsKhayDiaTrongTuDiaTheoId(KhayDiaTrongTuDia obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", obj.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ref_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.SP_GET_KHAY_DIA_BY_ID", params);
    }

    public Object themMoiKhayDiaTrongTuDia(KhayDiaTrongTuDia obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_thietbi_id", obj.getThietbi_id(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_ma_thietbi", obj.getMa_thietbi(), SqlParameter.ParameterDirection.INPUT,
                Types.NVARCHAR));
        params.add(new SqlParameter("p_so_thutu", obj.getSo_thutu(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_ten", obj.getTen(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(
                new SqlParameter("p_ky_hieu", obj.getKy_hieu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_serial_number", obj.getSerial_number(), SqlParameter.ParameterDirection.INPUT,
                Types.NVARCHAR));
        params.add(new SqlParameter("p_ip_mgmt_id", obj.getIp_mgmt_id(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_ip_controller_id", obj.getIp_controller_id(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("p_ghi_chu", obj.getGhi_chu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_hieu_luc", obj.getHieu_luc(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_hdd_tong", obj.getHdd_tong(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_hdd_capphat", obj.getHdd_capphat(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_ssd_tong", obj.getSsd_tong(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_ssd_capphat", obj.getSsd_capphat(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_trangthaisd", obj.getTrangthai_sd(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(
                new SqlParameter("p_maduan", obj.getMa_duan(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_tenduan", obj.getTen_duan(), SqlParameter.ParameterDirection.INPUT,
                Types.NVARCHAR));
        params.add(new SqlParameter("p_sothe", obj.getSo_the(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_donviqly_id", obj.getDonvi_qly_id(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_phongbanqly_id", obj.getPhongban_qly_id(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_nguoiqly_id", obj.getNguoi_qly_id(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_INSERT_KHAY_DIA", params);
    }

    public Object capNhatKhayDiaTrongTuDia(KhayDiaTrongTuDia obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", obj.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_thietbi_id", obj.getThietbi_id(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_ma_thietbi", obj.getMa_thietbi(), SqlParameter.ParameterDirection.INPUT,
                Types.NVARCHAR));
        params.add(new SqlParameter("p_so_thutu", obj.getSo_thutu(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_ten", obj.getTen(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(
                new SqlParameter("p_ky_hieu", obj.getKy_hieu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_serial_number", obj.getSerial_number(), SqlParameter.ParameterDirection.INPUT,
                Types.NVARCHAR));
        params.add(new SqlParameter("p_ip_mgmt_id", obj.getIp_mgmt_id(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_ip_controller_id", obj.getIp_controller_id(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("p_ghi_chu", obj.getGhi_chu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_hieu_luc", obj.getHieu_luc(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_hdd_tong", obj.getHdd_tong(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_hdd_capphat", obj.getHdd_capphat(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_ssd_tong", obj.getSsd_tong(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_ssd_capphat", obj.getSsd_capphat(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_trangthaisd", obj.getTrangthai_sd(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(
                new SqlParameter("p_maduan", obj.getMa_duan(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_tenduan", obj.getTen_duan(), SqlParameter.ParameterDirection.INPUT,
                Types.NVARCHAR));
        params.add(new SqlParameter("p_sothe", obj.getSo_the(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_donviqly_id", obj.getDonvi_qly_id(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_phongbanqly_id", obj.getPhongban_qly_id(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_nguoiqly_id", obj.getNguoi_qly_id(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_UPDATE_KHAY_DIA", params);
    }

    public Object xoaKhayDiaTrongTuDia(KhayDiaTrongTuDia obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", obj.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_HATANG_DANHMUC.SP_DELETE_KHAY_DIA", params);
    }
}
