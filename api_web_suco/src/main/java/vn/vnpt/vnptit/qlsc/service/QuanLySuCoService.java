package vn.vnpt.vnptit.qlsc.service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import vn.vnpt.common.Converter;
import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.vnptit.qlsc.dto.*;

@Service
public class QuanLySuCoService {
	private final DbContext dbContext;

	public QuanLySuCoService(DbContext dbContext) {
		this.dbContext = dbContext;
	}

	public Object getDsNguonSuCo() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_RS", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_NGUON_SC", params);
	}

	public Object getDsNhomSuCo(int nhanVienID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NHANVIEN_ID", nhanVienID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_QUANLY_SUCO.SP_LAY_DS_NHOM_SUCO_THEO_NHANVIEN", params);
	}

	public Object getMaSuCo(int nguonSuCoID, Date ngay) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NGUONSC_ID", nguonSuCoID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NGAY_BH", ngay, java.sql.Types.DATE));
		return dbContext.executeFunction("QLSC.PKG_QUANLY_SUCO.FN_LAY_MA_SUCO_01", java.sql.Types.NVARCHAR, params);
	}

	public Object kiemTraDieuKienGiaoPhieu(int donViNguonID, int donViDichID, int nhomSuCoID, int thuTuID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_DONVI_NGUON_ID", donViNguonID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_DONVI_DICH_ID", donViDichID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NHOMSC_ID", nhomSuCoID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_THUTU_ID", thuTuID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_QUANLY_SUCO.SP_KIEMTRA_DIEUKIEN_GIAOPHIEU", params);
	}

	public Object kiemTraDieuKienTiepNhan(int kieu, String maSuCo) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_KIEU", kieu, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_MA_SC", maSuCo, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_QUANLY_SUCO.SP_KIEMTRA_DIEUKIEN_TIEPNHAN", params);
	}

	public Object layDsTinhTrangCNTT(int suCoID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_SUCO_ID", suCoID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("RETURNDS", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_TINHTRANG_CNTT", params);
	}

	public Object layDsTinhTrangMVT(int suCoID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_SUCO_ID", suCoID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("RETURNDS", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_TINHTRANG_MVT", params);
	}

	public Object layDsTinhTrangMNV(int suCoID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_SUCO_ID", suCoID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("RETURNDS", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_TINHTRANG_MNV", params);
	}

	public Object layDsSuCoTam(String maSuCo, int nguonSuCoID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_MA_SC", maSuCo, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_NGUONSC_ID", nguonSuCoID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("CUR_RETURNDS", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_SUCO_TAM", params);
	}

	public Object layDsSuCoCPN(int nguonSuCoID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NGUONSC_ID", nguonSuCoID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("RETURNDS", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_SUCO_CPN", params);
	}

	public Object layDsSuCoDPN(int nguonSuCoID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NGUONSC_ID", nguonSuCoID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("CUR_RETURNDS", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_SUCO_DPN", params);
	}

	public Object layDsSuCoDPNFilter(LayDsSuCoFilterInput item) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NGUONSC_ID", item.getNguonSuCoID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NHOMSC_ID", item.getNhomSuCoID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_MANGVT_ID", item.getMangVtID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_MA_SC", item.getMaSuCo(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_THUEBAO", item.getThueBao(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_NOIDUNG_SC", item.getNoiDungSuCo(), java.sql.Types.NVARCHAR));
		
		java.util.Date tuNgay = null;
		java.util.Date denNgay = null;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		
		try {
			if (item.getTuNgay() != null && !item.getTuNgay().trim().isEmpty()) {
				tuNgay = sdf.parse(item.getTuNgay().trim());
			}
			if (item.getDenNgay() != null && !item.getDenNgay().trim().isEmpty()) {
				denNgay = sdf.parse(item.getDenNgay().trim());
			}
		} catch (java.text.ParseException e) {
			throw new AppSqlException("Ngày tiếp nhận chỉ nhận định dạng dd/MM/yyyy");
		}
		
		if (tuNgay != null && denNgay != null && tuNgay.after(denNgay)) {
			throw new AppSqlException("Ngày tiếp nhận từ phải nhỏ hơn Ngày tiếp nhận đến");
		}
		
		params.add(new SqlParameter("P_TUNGAY", tuNgay, java.sql.Types.DATE));
		params.add(new SqlParameter("P_DENNGAY", denNgay, java.sql.Types.DATE));
		
		params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("CUR_RETURNDS", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_QUANLY_SUCO.SP_LAY_DS_SUCO_DPN_FILTER", params);
	}

	public boolean capNhatSuCo(CapNhatSuCoInput item) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_MA_SC", item.getMaSuCo(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_NGAY_SC", Converter.toDateTime(item.getNgaySuCo()), java.sql.Types.DATE));
		params.add(new SqlParameter("P_NOIDUNG_SC", item.getNoiDungSuCo(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_NGAY_BH", Converter.toDateTime(item.getNgayBH()), java.sql.Types.DATE));
		params.add(new SqlParameter("P_DONVI_BH", item.getDonViBH(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NHANVIEN_BH", item.getNhanVienBH(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_DIENTHOAI_LH", item.getDienThoaiLH(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_DONVI_TN", item.getDonViTN(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NHANVIEN_TN", item.getNhanVienTN(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_HENSUATU", Converter.toDateTime(item.getHenSuaTu()), java.sql.Types.DATE));
		params.add(new SqlParameter("P_HENSUADEN", Converter.toDateTime(item.getHenSuaDen()), java.sql.Types.DATE));
		params.add(new SqlParameter("P_NGAY_CN", new Date(), java.sql.Types.DATE));
		params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_TINHTRANG", item.getTinhTrang(), java.sql.Types.CLOB));
		params.add(new SqlParameter("P_PHIEU_ID", item.getPhieuId(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_ANHCHUP", item.getAnhChup(), java.sql.Types.NVARCHAR));
		dbContext.executeStoredProcedure("QLSC.PKG_QUANLY_SUCO.SP_CAPNHAT_SUCO", params);
		return true;
	}

	public boolean capNhatAnhChup(Number phieuID, String moTa) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_PHIEU_ID", phieuID, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_MOTA", moTa, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), java.sql.Types.NVARCHAR));
		dbContext.executeStoredProcedure("QLSC.PKG_QUANLY_SUCO.SP_CAPNHAT_ANHCHUP_TIEPNHAN_SC", params);
		return true;
	}

	public boolean capNhatSuCoTam(CapNhatSuCoTamInput item) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_MA_SC", item.getMaSuCo(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_NHOMSC_ID", item.getNhomSuCoID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NGAY_SC", Converter.toDateTime(item.getNgaySuCo()), java.sql.Types.DATE));
		params.add(new SqlParameter("P_NOIDUNG_SC", item.getNoiDungSuCo(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_NGAY_BH", Converter.toDateTime(item.getNgayBH()), java.sql.Types.DATE));
		params.add(new SqlParameter("P_DONVI_BH", item.getDonViBH(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NHANVIEN_BH", item.getNhanVienBH(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_DIENTHOAI_LH", item.getDienThoaiLH(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_DONVI_TN", item.getDonViTN(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NHANVIEN_TN", item.getNhanVienTN(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_HENSUATU", Converter.toDateTime(item.getHenSuaTu()), java.sql.Types.DATE));
		params.add(new SqlParameter("P_HENSUADEN", Converter.toDateTime(item.getHenSuaDen()), java.sql.Types.DATE));
		params.add(new SqlParameter("P_NGAY_CN", new Date(), java.sql.Types.DATE));
		params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_ANHCHUP", item.getAnhChup(), java.sql.Types.NVARCHAR));
		dbContext.executeStoredProcedure("QLSC.PKG_QUANLY_SUCO.SP_CAPNHAT_SUCO_TMP", params);
		return true;
	}

	public Object tiepNhanSuCoTam(TiepNhanSuCoTamInput item) throws AppSqlException {
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
		return dbContext.executeSpWithCursorToMap("QLSC.PKG_QUANLY_SUCO.SP_TIEPNHAN_SUCO_TAM", params);
	}

	public Object tiepNhanSuCo(TiepNhanSuCoInput item) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NGUONSC_ID", item.getNguonSuCoID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NHOMSC_ID", item.getNhomSuCoID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NGAY_BD", Converter.toDateTime(item.getNgayBD()), java.sql.Types.DATE));
		params.add(new SqlParameter("P_GHICHU_HONG", item.getGhiChuHong(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_NGAY_YC", Converter.toDateTime(item.getNgayYeuCau()), java.sql.Types.DATE));
		
		params.add(new SqlParameter("P_DONVI_YC_ID", item.getDonViYeuCauID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NHANVIEN_YC_ID", item.getNhanVienYeuCauID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_DIENTHOAI_LH", item.getDienThoaiLH(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_DONVI_ID", item.getDonViID(), java.sql.Types.INTEGER));
		
		params.add(new SqlParameter("P_NHANVIEN_ID", item.getNhanVienID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_HENSUATU", Converter.toDateTime(item.getHenSuaTu()), java.sql.Types.DATE));
		params.add(new SqlParameter("P_HENSUADEN", Converter.toDateTime(item.getHenSuaDen()), java.sql.Types.DATE));
		params.add(new SqlParameter("P_NGAY_CN", new Date(), java.sql.Types.DATE));
		params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_TINHTRANG", item.getTinhTrang(), java.sql.Types.CLOB));
		params.add(new SqlParameter("P_ANHCHUP", item.getAnhChup(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToMap("QLSC.PKG_QUANLY_SUCO.SP_TIEPNHAN_SUCO", params);
	}

	public Object layDsCongBiAnhHuong(int suCoID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_SUCO_ID", suCoID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_CONG_BI_AH", params);
	}

	public Object layDsThueBaoBiAnhHuong(int suCoID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_SUCO_ID", suCoID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_THUEBAO_BI_AH", params);
	}

	public boolean xoaPhieu(int suCoID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_SUCO_ID", suCoID, java.sql.Types.INTEGER));
		dbContext.executeStoredProcedure("QLSC.PKG_QUANLY_SUCO.SP_XOAPHIEU", params);
		return true;
	}

	public boolean huyPhieu(Integer[] suCoIDs, Integer traPhieu) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_DS_SUCO_ID", StringUtils.join(suCoIDs, ","), java.sql.Types.CLOB));
		params.add(new SqlParameter("P_TRAPHIEU", traPhieu, java.sql.Types.INTEGER));
		
		params.add(new SqlParameter("P_NGAY_CN", new Date(), java.sql.Types.DATE));
		params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), java.sql.Types.NVARCHAR));
		dbContext.executeStoredProcedure("QLSC.PKG_QUANLY_SUCO.SP_HUY_PHIEU_SUCO", params);
		return true;
	}

	public Object layDsCongSoi(int loaiThietBiID, int thietBiID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("VLOAITBI_ID", loaiThietBiID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("VTHIETBI_ID", thietBiID, java.sql.Types.INTEGER));
		Object result = dbContext.executeFunction("QLSC.PKG_DANHMUC.FN_LAY_DS_CONGSOI", java.sql.Types.CLOB, params);
		return result;
	}

	public Object layDsNhomSuCoTheoNhanVien(int nhanVienID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NHANVIEN_ID", nhanVienID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_QUANLY_SUCO.SP_LAY_DS_NHOM_SUCO_THEO_NHANVIEN", params);
	}

	public Object layDsPhieuTheoNhanVien(int nhanVienID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NHANVIEN_ID", nhanVienID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_QUANLY_SUCO.SP_LAY_DS_PHIEU_SUCO_THEO_NHANVIEN", params);
	}
	

	public boolean taoSuCoTam(TaoSuCoTamInput item) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_MA_SC", item.getMaSuCo(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_NGUONSC_ID", item.getNguonSuCoID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NHOMSC_ID", item.getNhomSuCoID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NGAY_SC", Converter.toDateTime(item.getNgaySuCo()), java.sql.Types.DATE));
		params.add(new SqlParameter("P_NOIDUNG_SC", item.getNoiDungSuCo(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_NGAY_BH", Converter.toDateTime(item.getNgayBH()), java.sql.Types.DATE));
		params.add(new SqlParameter("P_DONVI_BH", item.getDonViBH(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NHANVIEN_BH", item.getNhanVienBH(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_DIENTHOAI_LH", item.getDienThoaiLH(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_NGAY_TN", Converter.toDateTime(item.getNgayTN()), java.sql.Types.DATE));
		params.add(new SqlParameter("P_DONVI_TN", item.getDonViTN(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NHANVIEN_TN", item.getNhanVienTN(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_HENSUATU", Converter.toDateTime(item.getHenSuaTu()), java.sql.Types.DATE));
		params.add(new SqlParameter("P_HENSUADEN", Converter.toDateTime(item.getHenSuaDen()), java.sql.Types.DATE));
		params.add(new SqlParameter("P_ANHCHUP", item.getAnhChup(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_NGAY_CN", new Date(), java.sql.Types.DATE));
		params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), java.sql.Types.NVARCHAR));
		dbContext.executeStoredProcedure("QLSC.PKG_QUANLY_SUCO.SP_TAO_SUCO_TAM", params);
		return true;
	}

	public boolean xoaSuCoTam(String maSuCo) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_MA_SC", maSuCo, java.sql.Types.NVARCHAR));
		dbContext.executeStoredProcedure("QLSC.PKG_QUANLY_SUCO.SP_XOA_SUCO_TMP", params);
		return true;
	}

	public boolean ganDsSuCoChoSuCoCha(Integer[] suCoIDs, Integer suCoChaID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_DS_SUCO_ID", StringUtils.join(suCoIDs, ","), java.sql.Types.CLOB));
		params.add(new SqlParameter("P_SUCO_CHA_ID", suCoChaID, java.sql.Types.INTEGER));
		dbContext.executeStoredProcedure("QLSC.PKG_QUANLY_SUCO.SP_GAN_NHOM_SUCO", params);
		return true;
	}

	public boolean goNhomSuCo(int suCoID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_SUCO_ID", suCoID, java.sql.Types.INTEGER));
		dbContext.executeStoredProcedure("QLSC.PKG_QUANLY_SUCO.SP_GO_NHOM_SUCO", params);
		return true;
	}

	public Object layDsThueBaoTheoLoaiHinh(LayDsThueBaoTheoLoaiHinhDto input) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_SUCO_ID", input.getSuco_id(), Types.NUMERIC));
		params.add(new SqlParameter("P_LOAITB_ID", input.getLoaitb_id(), Types.NUMERIC));
		params.add(new SqlParameter("P_RETURNDS", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_TBSC_LOAIHINH_SEL", params);
	}

	public Object layDsThueBaoTrenCong(DataKeyInput input) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_RSC_ID", input.getId(), Types.NUMERIC));
		params.add(new SqlParameter("P_RETURNDS", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_TBSC_CONG_SEL", params);
	}

	public Object layDsThueBaoTheoDichVu(LayDsThueBaoTheoDichVuDto input) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_SUCO_ID", input.getSuco_id(), Types.NUMERIC));
		params.add(new SqlParameter("P_DVVT_ID", input.getDvvt_id(), Types.NUMERIC));
		params.add(new SqlParameter("P_RETURNDS", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_TBSC_DVVT_SEL", params);
	}

	public Object layDsThueBaoTheoPtm(LayDsThueBaoTheoPtmDto input) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_SUCO_ID", input.getSuco_id(), Types.NUMERIC));
		params.add(new SqlParameter("P_LOAIPTM_ID", input.getLoaiptm_id(), Types.NUMERIC));
		params.add(new SqlParameter("P_PTM_ID", input.getPtm_id(), Types.NUMERIC));
		params.add(new SqlParameter("P_RETURNDS", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_TBSC_PTM_SEL", params);
	}

	public Object layDsThamSoMd() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_RS", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_TIEPNHAN_SUCO.LAY_DS_THAMSO_MD", params);
	}

	public Object tiepNhanSuCoTamV2(TiepNhanSuCoTamInput item) throws AppSqlException {
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
		return dbContext.executeSpWithCursorToMap("QLSC.PKG_QUANLY_SUCO.SP_TIEPNHAN_SUCO_TAM_V2", params);
	}

	public boolean taoSuCoTamV2(TaoSuCoTamInput2 item) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_MA_SC", item.getMaSuCo(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_NGUONSC_ID", item.getNguonSuCoID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NHOMSC_ID", item.getNhomSuCoID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NGAY_SC", Converter.toDateTime(item.getNgaySuCo()), java.sql.Types.DATE));
		params.add(new SqlParameter("P_NOIDUNG_SC", item.getNoiDungSuCo(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_NGAY_BH", Converter.toDateTime(item.getNgayBH()), java.sql.Types.DATE));
		params.add(new SqlParameter("P_DONVI_BH", item.getDonViBH(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NHANVIEN_BH", item.getNhanVienBH(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_DIENTHOAI_LH", item.getDienThoaiLH(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_NGAY_TN", Converter.toDateTime(item.getNgayTN()), java.sql.Types.DATE));
		params.add(new SqlParameter("P_DONVI_TN", item.getDonViTN(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NHANVIEN_TN", item.getNhanVienTN(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_HENSUATU", Converter.toDateTime(item.getHenSuaTu()), java.sql.Types.DATE));
		params.add(new SqlParameter("P_HENSUADEN", Converter.toDateTime(item.getHenSuaDen()), java.sql.Types.DATE));
		params.add(new SqlParameter("P_ANHCHUP", item.getAnhChup(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_NGAY_CN", new Date(), java.sql.Types.DATE));
		params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_PHANLOAI_SC_ID", item.getPhanLoaiSuCoId(), java.sql.Types.NVARCHAR));
		dbContext.executeStoredProcedure("QLSC.PKG_QUANLY_SUCO.SP_TAO_SUCO_TAM_V2", params);
		return true;
	}

	public Object suCoMNVSendSMS(SuCoMNVSendSMSNhanVienDTO dto) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", dto.getPhanVungId(), Types.NUMERIC));
		params.add(new SqlParameter("p_suco_id", dto.getSuCoId(), Types.NUMERIC));
		try {
			params.add(new SqlParameter("p_ds", JsonConvert.serialize(dto.getNhanVienList()), Types.NVARCHAR));
		} catch (Exception e) {
			throw new AppSqlException(e.getMessage());
		}
		params.add(new SqlParameter("p_nguoidung_id", AppRequestContext.getCurrent().getNguoiDungID(), Types.NUMERIC));
		params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.NVARCHAR));
		params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), Types.NVARCHAR));
		dbContext.executeStoredProcedure("qlsc.pkg_xuly_suco_mnv.sp_suco_mnv_sendsms", params);
		return true;
	}
	public Object suCoMNVSendSMSDanhSach(SuCoMNVSendSMSNhanVienDTO dto) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", dto.getPhanVungId(), Types.NUMERIC));
		params.add(new SqlParameter("p_suco_id", dto.getSuCoId(), Types.NUMERIC));
		params.add(new SqlParameter("p_rs", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("qlsc.pkg_xuly_suco_mnv.sp_suco_mnv_sendsms_danhsach", params);
	}
}
