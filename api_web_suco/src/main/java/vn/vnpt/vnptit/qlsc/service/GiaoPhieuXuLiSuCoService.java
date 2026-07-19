package vn.vnpt.vnptit.qlsc.service;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.qlsc.dto.*;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

@Service
public class GiaoPhieuXuLiSuCoService {

    private final DbContext dbContext;

    public GiaoPhieuXuLiSuCoService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object layDsPhieuXuLy(LayDsPhieuXuLyDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_NGUONSC_ID", input.getNguonScId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_MA_SC", input.getMaSc(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_NGAYGIAO", input.getNgayGiao(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_TRANGTHAI", input.getTrangThai(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_MUCDOSC_ID", input.getMucDoScId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_KEODAI", input.getKeoDai(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_HUONGGIAO_ID", input.getHuongGiaoId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("CUR_RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_PHIEU_XULY", params);
    }

    public Object layDsTinhTrangXuLy(LayDsTinhTrangXuLyDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_SUCO_ID", input.getSuco_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_TRANGTHAI", input.getTrangthai_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("CUR_RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_TINHTRANG_XULY", params);
    }

    public boolean giaoPhieuXlSuCo(GiaoPhieuXlSuCoDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phieu_cha_id", input.getPhieu_cha_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_huonggiao_id", input.getHuonggiao_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nd_giao", input.getNd_giao(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_suco_id", input.getSuco_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_giao_id", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_nhan_id", input.getDonvi_nhan_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_nhanvien_giao_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_suco_ptm", input.getSuco_ptm(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_khan", input.getKhan(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_chitieu_tg", input.getChitieu_tg(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getMaNhanVien(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        dbContext.executeSpWithCursorToListMap("QLSC.pkg_nghiepvu.SP_GIAOPHIEU_XULY", params);
        return true;
    }

    public boolean capNhatPhieuXlSuCo(CapNhatPhieuXlSuCoDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_phieu_id", input.getPhieu_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_giao_id", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_giao_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_nhan_id", input.getDonvi_nhan_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nd_giao", input.getNd_giao(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_khan", input.getKhan(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_chitieu_tg", input.getChitieu_tg(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getMaNhanVien(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        dbContext.executeSpWithCursorToListMap("QLSC.pkg_nghiepvu.sp_capnhat_xuly_upd", params);
        return true;
    }

    public boolean huyPhieuXlSuCo(HuyPhieuXlSuCoDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_suco_id", input.getSuco_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_suco_ptm", input.getSuco_ptm(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        dbContext.executeSpWithCursorToListMap("QLSC.pkg_nghiepvu.huygiao_xuly", params);
        return true;
    }

    public boolean giaoPhieuXlMNVSuCo(GiaoPhieuXlMNVSuCoDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phieu_cha_id", input.getPhieu_cha_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_huonggiao_id", input.getHuonggiao_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nd_giao", input.getNd_giao(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_suco_id", input.getSuco_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_giao_id", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_giao_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_nhan_id", input.getDonvi_nhan_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ds_nhanvien_nhan", input.getDs_nhanvien_nhan(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_suco_ptm", input.getSuco_ptm(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_khan", input.getKhan(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_chitieu_tg", input.getChitieu_tg(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getMaNhanVien(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        dbContext.executeSpWithCursorToListMap("QLSC.PKG_QUANLY_SUCO_MNV.sp_giaophieu_xuly_mnv", params);
        return true;
    }

    public Object layDsNhanVien(GiaoPhieuXlMNVSuCoDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id", input.getDonvi_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_QUANLY_SUCO_MNV.sp_ds_nhanvien_donvi", params);
    }

    public Object layDsTinhTrangThietBi(GiaoPhieuXlMNVSuCoDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_suco_id", input.getSuco_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ma_sc", input.getMa_sc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_QUANLY_SUCO_MNV.sp_ds_tinhtrang_thietbi", params);
    }

    public Object layDsNghiepVu() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.pkg_danhmuc.sp_ds_nghiepvu_sc_mnv", params);
    }

    public Object layDsNhomNN() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.pkg_quanly_suco_mnv.sp_ds_nhom_nn", params);
    }

    public Object layDsVatTuNghiepVu(DanhSachVatTuMNVDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nghiepvu_id", input.getNghiepVuID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.pkg_danhmuc.sp_ds_vattu_theo_nghiepvu_sc_mnv", params);
    }

    public Object layDsVatTuPhieuMNV(DanhSachVatTuMNVDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_phieu_id", input.getPhieuId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_suco_id", input.getSuCoId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_kieutbi", input.getKieuTBi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_status", input.getStatus(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_QUANLY_SUCO_MNV.lay_ds_vattu_theo_phieu_mnv", params);
    }

    public Object layDsVatTuSuDungMNV(DanhSachVatTuMNVDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_suco_id", input.getSuCoId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_QUANLY_SUCO_MNV.lay_ds_vattu_sd_mnv", params);
    }

    public boolean capNhatThuHoiVTMNV(DanhSachVatTuMNVDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_suco_id", input.getSuCoId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_dsphieu_vt", input.getDsPhieu(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        dbContext.executeSpWithCursorToListMap("QLSC.PKG_QUANLY_SUCO_MNV.sp_capnhat_ds_phieuvt_mnv", params);
        return true;
    }

    public boolean hoanThanhThuHoiVTMNV(DanhSachVatTuMNVDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_suco_id", input.getSuCoId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_phieu_id", input.getPhieuId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_thuhoi_vt", input.getThuHoiVT(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_lydo_id", input.getLyDoId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", input.getGhiChu(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        dbContext.executeSpWithCursorToListMap("QLSC.PKG_QUANLY_SUCO_MNV.sp_hoanthanh_thuhoi_vt_mnv", params);
        return true;
    }

    public boolean capNhatPhieuThuHoiVTMNV(CapNhatPhieuThuHoiVatTu input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_suco_id", input.getSuCoId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_sdvt_id", input.getSdvtId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_insert", input.getAction(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_phieuvt_id", input.getPhieuVtId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_hdtb_id", input.getHdtbId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_baohong_id", input.getBaoHongId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_phieu_id", input.getPhieuId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_thuebao_id", input.getThueBaoId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tinhtrangtbi_id", input.getTinhTrangTbId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_serial", input.getSerial(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ngay_th", input.getNgayTh(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_vattu_id", input.getVatTuId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_thietbi_id", input.getThietBiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_soluong", input.getSoLuong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_lydocap_id", input.getLyDoCapId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_lydocaptbi_id", input.getLyDoCapTbId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_mucdich_id", input.getMucDichId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_trangbi_id", input.getTrangBiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_kieu", 2, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", input.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        dbContext.executeSpWithCursorToListMap("QLSC.PKG_QUANLY_SUCO_MNV.sp_capnhat_phieuvt_thuhoi_mnv", params);
        return true;
    }

    public Object tiepNhanSuCoTamMNV(TiepNhanSuCoTamInput item) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_MA_SC", item.getMaSuCo(), java.sql.Types.NVARCHAR));
        params.add(new SqlParameter("P_NGAY_CN", new Date(), java.sql.Types.DATE));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), java.sql.Types.NVARCHAR));
        params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), java.sql.Types.NVARCHAR));
        params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), java.sql.Types.NVARCHAR));
        params.add(new SqlParameter("P_TINHTRANG", item.getTinhTrang(), java.sql.Types.CLOB));
        params.add(new SqlParameter("P_ANHCHUP", item.getAnhChup(), java.sql.Types.NVARCHAR));
        params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("QLSC.PKG_XULY_SUCO_MNV.SP_TIEPNHAN_SUCO_TAM_MNV", params);
    }
}
