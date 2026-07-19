package vn.vnpt.vnptit.ecms.service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.QLTBPT_LayDsTiepNhanTBIV3Input;

@Service
public class QuanLyThietBiPhuTroService {
	private final DbContext dbContext;

	public QuanLyThietBiPhuTroService(DbContext dbContext) {
		this.dbContext = dbContext;
	}

	public Object getDsThamSo() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QL_THIETBI_PHUTRO.LAY_DS_THAMSO_MD", params);
	}

	public Object getDsThietBi(int donViID, int kieu) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_DONVI_ID", donViID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_KIEU", kieu, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QL_THIETBI_PHUTRO.LAY_DS_TBI_V2", params);
	}

	public Object getDsLichSuVanHanh(int thietBiID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_THIETBI_ID", thietBiID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QL_THIETBI_PHUTRO.LAY_DS_LICHSU_VANHANH_THIETBI",
				params);
	}

	public Object getDsThuocTinh(int vatTuID, int thietBiID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		int phanVungID = AppRequestContext.getCurrent().getPhanVungID();
		params.add(new SqlParameter("P_PHANVUNG_ID", phanVungID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_VATTU_ID", vatTuID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_THIETBI_ID", thietBiID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
		Map<String, Object> result = new HashMap<>();
		ArrayList<Map<String, Object>> dsThuocTinh = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_QL_THIETBI_PHUTRO.LAY_DS_THUOCTINH", params);
		result.put("DS_THUOCTINH", dsThuocTinh);
		ArrayList<Map<String, Object>> listSQL = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < dsThuocTinh.size(); i++) {
			Map<String, Object> thuocTinh = dsThuocTinh.get(i);
			String tenLoai = (String) thuocTinh.get("TEN_LOAI");
			if (tenLoai.equals("Combobox") || tenLoai.equals("Combobox Seach")) {
				String cauLenh = (String) thuocTinh.get("CAULENH");
				if (cauLenh != null) {
					String tenTT = (String) thuocTinh.get("THUOCTINH");
					boolean ck = false;
					for (int j = 0; j < listSQL.size(); j++) {
						if (((String) listSQL.get(j).get("THUOCTINH")).equals(tenTT)) {
							ck = true;
							break;
						}
					}
					if (!ck) {
						Map<String, Object> item = new HashMap<>();
						item.put("THUOCTINH", tenTT);
						item.put("CAULENH",
								cauLenh.replace("_hpg.", ".").replace(":vphanvung_id", Integer.toString(phanVungID)));
						listSQL.add(item);
					}
				}
			}
		}
		for (int i = 0; i < listSQL.size(); i++) {
			Object items = dbContext.executeQueryToListMap((String) listSQL.get(i).get("CAULENH"));
			result.put((String) listSQL.get(i).get("THUOCTINH"), items);
		}
		return result;
	}

	public Object getDsThuocTinhTheoVTTB(int vatTuID, int thietBiID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		int phanVungID = AppRequestContext.getCurrent().getPhanVungID();
		params.add(new SqlParameter("P_PHANVUNG_ID", phanVungID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_VATTU_ID", vatTuID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_THIETBI_ID", thietBiID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
		Map<String, Object> result = new HashMap<>();
		ArrayList<Map<String, Object>> dsThuocTinh = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_QL_THIETBI_PHUTRO.LAY_TT_THUOCTINH_THEO_VATTU_TBI", params);
		result.put("DS_THUOCTINH", dsThuocTinh);
		ArrayList<Map<String, Object>> listSQL = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < dsThuocTinh.size(); i++) {
			Map<String, Object> thuocTinh = dsThuocTinh.get(i);
			String tenLoai = (String) thuocTinh.get("TEN_LOAI");
			if (tenLoai.equals("Combobox") || tenLoai.equals("Combobox Seach")) {
				String cauLenh = (String) thuocTinh.get("CAULENH");
				if (cauLenh != null) {
					String tenTT = (String) thuocTinh.get("THUOCTINH");
					boolean ck = false;
					for (int j = 0; j < listSQL.size(); j++) {
						if (((String) listSQL.get(j).get("THUOCTINH")).equals(tenTT)) {
							ck = true;
							break;
						}
					}
					if (!ck) {
						Map<String, Object> item = new HashMap<>();
						item.put("THUOCTINH", tenTT);
						item.put("CAULENH",
								cauLenh.replace("_hpg.", ".").replace(":vphanvung_id", Integer.toString(phanVungID)));
						listSQL.add(item);
					}
				}
			}
		}
		for (int i = 0; i < listSQL.size(); i++) {
			Object items = dbContext.executeQueryToListMap((String) listSQL.get(i).get("CAULENH"));
			result.put((String) listSQL.get(i).get("THUOCTINH"), items);
		}
		return result;
	}

	public Object phutro_lay_ds_theo_tbi(String ma_vt, int kieu) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("vma_vt", ma_vt, Types.VARCHAR));
		params.add(new SqlParameter("vkieu", kieu, java.sql.Types.INTEGER));
		params.add(new SqlParameter("vnhanvien_id", AppRequestContext.getCurrent().getNhanVienID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.phutro_lay_ds_theo_tbi", params);
	}

	public Object getDsQuyenNhanVien(int nhanVienID, int donViID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_NHANVIEN_ID", nhanVienID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_DONVI_ID", donViID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QL_THIETBI_PHUTRO.LAY_DS_QUYEN_NHANVIEN", params);
	}

	public Object phutro_lay_ds_tnhan_tbi_v3(QLTBPT_LayDsTiepNhanTBIV3Input item) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("vttvh_id", item.getTtvh_id(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("vthietbi_id", item.getThietbi_id(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("vnhanvien_id", AppRequestContext.getCurrent().getNhanVienID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("vloaivh_id", item.getLoaivh_id(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.phutro_lay_ds_tnhan_tbi_v3", params);
	}

	public Object Lay_ThongTin_ThuocTinh_Theo_VatTu_ThietBi(int vattu_id, int thietbi_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("vma_vt", vattu_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("vkieu", thietbi_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QL_THIETBI_PHUTRO.LAY_TT_THUOCTINH_THEO_VATTU_TBI",
				params);
	}

	public Object phutro_lay_ds_tnhan_tbi(int ttvh_id, int thietbi_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("vttvh_id", ttvh_id, Types.VARCHAR));
		params.add(new SqlParameter("vthietbi_id", thietbi_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("vnhanvien_id", AppRequestContext.getCurrent().getNhanVienID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.phutro_lay_ds_tnhan_tbi", params);
	}

	public Object getDsThietBiCon(int donViID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_DONVI_ID", donViID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QL_THIETBI_PHUTRO.LAY_DS_TBI_CON", params);
	}

	public Object kiemTraQuyenTBPT(int nguoiDungID, int quyenID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_NGUOIDUNG_ID", nguoiDungID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_QUYEN_ID", quyenID, java.sql.Types.INTEGER));
		SqlParameter count = new SqlParameter("P_COUNT", java.sql.Types.NUMERIC);
		params.add(count);
		dbContext.executeStoredProcedure("ECMS.PKG_QL_THIETBI_PHUTRO.KIEMTRAQUYEN_TBPT", params);
		return count.getOutValue();
	}

	public Boolean xoaThietBiPhuTro(int thietBiID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_THIETBI_ID", thietBiID, java.sql.Types.INTEGER));
		dbContext.executeStoredProcedure("ECMS.PKG_QL_THIETBI_PHUTRO.XOA_THIETBI_PHUTRO", params);
		return true;
	}

	public Object capNhatVatTuThuocTinh(int thietBiID, int ttvtID, int gtttID, String giaTri) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		AppRequestContext current = AppRequestContext.getCurrent();
		params.add(new SqlParameter("P_PHANVUNG_ID", current.getPhanVungID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_THIETBI_ID", thietBiID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_TTVT_ID", ttvtID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_GTTT_ID", gtttID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_GIATRI", giaTri, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_NGUOI_CN", current.getUserName(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_MAY_CN", current.getClientIpAddress(), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_IP_CN", current.getClientIpAddress(), java.sql.Types.NVARCHAR));
		SqlParameter result = new SqlParameter("P_MESSAGE", java.sql.Types.NVARCHAR);
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_QL_THIETBI_PHUTRO.UPDATE_VATTU_THUOCTINH", params);
		return result.getOutValue();
	}

	public Boolean capNhatDiaChiThietBi(int thietBiID, Integer diaChiID, int tinhID, int quanID, Integer phuongID, Integer phoID,
			Integer khuID, Integer apID, Integer dacDiemID, String soNha, String diaChi, float kinhDo, float viDo)
			throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_THIETBI_ID", thietBiID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_DIACHI_ID", diaChiID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_TINH_ID", tinhID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_QUAN_ID", quanID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_PHUONG_ID", phuongID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_PHO_ID", phoID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_KHU_ID", khuID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_AP_ID", apID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_DACDIEM_ID", dacDiemID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_SONHA", soNha, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_DIACHI", diaChi, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("P_KINHDO", kinhDo, java.sql.Types.FLOAT));
		params.add(new SqlParameter("P_VIDO", viDo, java.sql.Types.FLOAT));
		dbContext.executeStoredProcedure("ECMS.PKG_QL_THIETBI_PHUTRO.CAPNHAT_DIACHI_THIETBI", params);
		return true;
	}

	public Boolean capNhatThietBiMaTBI(int thietBiID, String maTBI) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_THIETBI_ID", thietBiID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_MA_TBI", maTBI, java.sql.Types.NVARCHAR));
		dbContext.executeStoredProcedure("ECMS.PKG_QL_THIETBI_PHUTRO.UPDATE_THIETBI_MATBI", params);
		return true;
	}

	public Boolean capNhatThietBiGhiChu(int thietBiID, String ghiChu) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_THIETBI_ID", thietBiID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("P_GHICHU", ghiChu, java.sql.Types.NVARCHAR));
		dbContext.executeStoredProcedure("ECMS.PKG_QL_THIETBI_PHUTRO.UPDATE_THIETBI_GHICHU", params);
		return true;
	}
	
}