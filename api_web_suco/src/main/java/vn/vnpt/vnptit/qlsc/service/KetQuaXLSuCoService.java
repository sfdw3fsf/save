package vn.vnpt.vnptit.qlsc.service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.DbHelper;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.qlsc.dto.*;

@Service
public class KetQuaXLSuCoService {
	private final DbContext dbContext;

	@Autowired
	DbHelper dbHelper;

	public KetQuaXLSuCoService(DbContext dbContext) {
		this.dbContext = dbContext;
	}

	public Object ds_phieu_dieuto(PhieuDieuToInput input) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_nhomsc_id", input.getnhomsc_id(), ParameterDirection.INPUT,java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_ma_sc", input.getma_sc(), ParameterDirection.INPUT, java.sql.Types.VARCHAR));
		params.add(new SqlParameter("p_nguoibao_id", input.getnguoibao_id(), ParameterDirection.INPUT,java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_mucdokc_id", input.getmucdokc_id(), ParameterDirection.INPUT,java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_nguonsc_id", input.getnguonsc_id(), ParameterDirection.INPUT,java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_tinhtrang", input.gettinhtrang(), ParameterDirection.INPUT,java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_noidung_sc", input.getnoidung_sc(), ParameterDirection.INPUT,java.sql.Types.VARCHAR));
		params.add(new SqlParameter("p_noidung_giao", input.getnoidung_giao(), ParameterDirection.INPUT,java.sql.Types.VARCHAR));
		params.add(new SqlParameter("p_ngaysc_tu", input.getngaysc_tu(), ParameterDirection.INPUT,java.sql.Types.VARCHAR));
		params.add(new SqlParameter("p_ngaysc_den", input.getngaysc_den(), ParameterDirection.INPUT,java.sql.Types.VARCHAR));
		params.add(new SqlParameter("p_ngaybao_tu", input.getngaybao_tu(), ParameterDirection.INPUT,java.sql.Types.VARCHAR));
		params.add(new SqlParameter("p_ngaybao_den", input.getngaybao_den(), ParameterDirection.INPUT,java.sql.Types.VARCHAR));
		params.add(new SqlParameter("p_ngaygiao_tu", input.getngaygiao_tu(), ParameterDirection.INPUT,java.sql.Types.VARCHAR));
		params.add(new SqlParameter("p_ngaygiao_den", input.getngaygiao_den(), ParameterDirection.INPUT,java.sql.Types.VARCHAR));
		params.add(new SqlParameter("p_mucdosc_id", input.getmucdosc_id(), ParameterDirection.INPUT,java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_keodai_tu_op", input.getkeodai_tu_op(), ParameterDirection.INPUT,java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_keodai_tu", input.getkeodai_tu(), ParameterDirection.INPUT,java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_keodai_den_op", input.getkeodai_den_op(), ParameterDirection.INPUT,java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_keodai_den", input.getkeodai_den(), ParameterDirection.INPUT,java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_donvi_id",  AppRequestContext.getCurrent().getDonViID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_nhanvien_id",  AppRequestContext.getCurrent().getNhanVienID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("returnds", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_PHIEU_DIEU_TO_01", params);
	}
	
	public Object ds_nhom_suco() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();		
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));			
		params.add(new SqlParameter("P_RETURNS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_XULY_SUCO.SP_DANHSACH_NHOM_SC", params);
	}
	
	public boolean capnhat_ghichu(int P_PHIEU_ID,String P_GHICHU) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();		
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_PHIEU_ID", P_PHIEU_ID,ParameterDirection.INPUT, java.sql.Types.NUMERIC));				
		params.add(new SqlParameter("P_GHICHU", P_GHICHU,ParameterDirection.INPUT, java.sql.Types.VARCHAR));
		params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getMaNhanVien(),ParameterDirection.INPUT, java.sql.Types.VARCHAR));
		params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(),ParameterDirection.INPUT, java.sql.Types.VARCHAR));
		params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(),ParameterDirection.INPUT, java.sql.Types.VARCHAR));		
		params.add(new SqlParameter("P_RETURNS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("QLSC.PKG_XULY_SUCO.SP_CAPNHAT_GHICHU_TH_PXL", params);
		Map<String, Object> map = list.get(0);
		return "1".equals(map.get("RESULT").toString());
	}

	public boolean capnhat_ghichu_ptm(GhiChuPtmInput input) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_PHIEU_ID", input.getPhieu_id(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_SUCO_PTM_ID", input.getSuco_ptm_id(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_GHICHU", input.getGhi_chu(),ParameterDirection.INPUT, java.sql.Types.VARCHAR));
		params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getMaNhanVien(),ParameterDirection.INPUT, java.sql.Types.VARCHAR));
		params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(),ParameterDirection.INPUT, java.sql.Types.VARCHAR));
		params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(),ParameterDirection.INPUT, java.sql.Types.VARCHAR));
		params.add(new SqlParameter("P_RS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		dbContext.executeSpWithCursorToListMap("QLSC.PKG_XULY_SUCO.SP_CAPNHAT_GHICHU_XULY_PTM_UPD", params);
		return true;
	}

	public boolean capnhat_ghichu_xuly_nv(GhiChuXuLyNvInput input) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_GIAOVIEC_ID", input.getGiaoviec_id(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_GHICHU", input.getGhichu(),ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("P_RS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		dbContext.executeSpWithCursorToListMap("QLSC.PKG_XULY_SUCO.SP_CAPNHAT_GHICHU_XULY_NV_UPD", params);
		return true;
	}

	public boolean hoan_cong(HoanCongPXLInput input) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();						
		params.add(new SqlParameter("P_PHIEU_ID", input.getphieu_id(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));	
		params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));		
		params.add(new SqlParameter("P_GHICHU", input.getghi_chu(),ParameterDirection.INPUT, java.sql.Types.VARCHAR));
		params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getMaNhanVien(),ParameterDirection.INPUT, java.sql.Types.VARCHAR));
		params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(),ParameterDirection.INPUT, java.sql.Types.VARCHAR));
		params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(),ParameterDirection.INPUT, java.sql.Types.VARCHAR));	
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_RETURNS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("QLSC.PKG_XULY_SUCO.SP_HOANCONG_XULY_SUCO", params);
		Map<String, Object> map = list.get(0);
		return "1".equals(map.get("RESULT").toString());
	}
	
	public Object ds_nguon_suco() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();								
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_RETURNS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_XULY_SUCO.SP_DANHSACH_NGUON_SC", params);
	}
	
	public Object ds_phantu_mang(int phieu_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();								
		params.add(new SqlParameter("p_phieu_id", phieu_id,ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_RETURNS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		Object ds=dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_PTM_PDT", params);
		return ds;
	}
	
	public Object ds_giaoviec(int phieu_id, int SUCO_PTM_ID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();								
		params.add(new SqlParameter("p_phieu_id", phieu_id,ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_SUCO_PTM_ID", SUCO_PTM_ID,ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_RETURNS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_GV_PDT", params);
	}

	public Object huy_giaoviec_nv(Long idGiaoViec) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_GIAOVIEC_ID", idGiaoViec,ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_ND_GUI_SMS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		ArrayList<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("QLSC.pkg_nghiepvu.SP_HUY_GIAOVIEC_NV_01_DEL", params);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	public boolean sms_giaoviec_nv(Long idGiaoViec) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_GIAOVIEC_ID", idGiaoViec,ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		dbContext.executeSpWithCursorToListMap("QLSC.pkg_nghiepvu.sp_sms_giaoviec_nv_upd", params);
		return true;
	}

	public boolean traphieu_suco(TraPhieuSuCoInput input) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phieu_id", input.getPhieu_id(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_nd_tra", input.getNoi_dung(),ParameterDirection.INPUT, java.sql.Types.VARCHAR));
		params.add(new SqlParameter("p_lydotra_id", input.getLydotra_id(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_nhanvien_tra_id", AppRequestContext.getCurrent().getNhanVienID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getMaNhanVien(),ParameterDirection.INPUT, java.sql.Types.VARCHAR));
		params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(),ParameterDirection.INPUT, java.sql.Types.VARCHAR));
		params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(),ParameterDirection.INPUT, java.sql.Types.VARCHAR));
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		dbContext.executeSpWithCursorToListMap("QLSC.pkg_nghiepvu.SP_TRAPHIEU_XULY_UPD", params);
		return true;
	}

	public boolean capnhat_baoton(CapNhatBaoTonInput input) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phieu_id", input.getPhieu_id(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_nnton_id", input.getNnton_id(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_ngay_bt", input.getNgay_bt(),ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_nhanvien_bt_id", input.getNhanvien_bt_id(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_donvi_bt_id", AppRequestContext.getCurrent().getDonViID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_ghichu", input.getGhichu(),ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getMaNhanVien(),ParameterDirection.INPUT, java.sql.Types.VARCHAR));
		params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(),ParameterDirection.INPUT, java.sql.Types.VARCHAR));
		params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(),ParameterDirection.INPUT, java.sql.Types.VARCHAR));
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		dbContext.executeSpWithCursorToListMap("QLSC.pkg_nghiepvu.SP_BAOTON_XULY_SUCO", params);
		return true;
	}

	public boolean huy_baoton(HuyBaoTonInput input) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_phieu_id", input.getPhieu_id(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_nnton_id", input.getNnton_id(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		dbContext.executeSpWithCursorToListMap("QLSC.pkg_nghiepvu.sp_huyphieu_bt_xuly_suco_del", params);
		return true;
	}

	public Object lay_ds_inphieu_suco(LayDsInPhieuSuCoInput input) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_SUCO_ID", input.getSuco_id(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_GIAOVIEC_ID", input.getGiaoviec_id(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_RETURNDS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.pkg_baocao.SP_LAY_BC_PHIEUDIEU_XL_SEL", params);
	}

	public Object giaoviec_nv(GiaoViecNvInput input) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_PHIEU_ID", input.getPhieu_id(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_SUCO_PTM_ID", input.getSuco_ptm_id(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_DS_GIAOVIEC", input.getDs_giaoviec(),ParameterDirection.INPUT, Types.CLOB));
		params.add(new SqlParameter("P_DS_ND_SMS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.pkg_nghiepvu.SP_GIAOVIEC_NV_01_UPD", params);
	}

	public Object giaoViecNVXaHoiHoa(GiaoViecNvInput input) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_phieu_id", input.getPhieu_id(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_ten_nhanvien_th", input.getTenNvXHH(),ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_so_dt", input.getSdtNvXHH(),ParameterDirection.INPUT, java.sql.Types.VARCHAR));
		params.add(new SqlParameter("p_nhanvien_giao_id", AppRequestContext.getCurrent().getNhanVienID(),ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_nhiemvu", input.getNhiemVuNvXHH(),ParameterDirection.INPUT, java.sql.Types.VARCHAR));
		params.add(new SqlParameter("p_ghichu", input.getGhiChuNvXHH(),ParameterDirection.INPUT, java.sql.Types.VARCHAR));

		params.add(new SqlParameter("vkq", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.SP_GIAOVIEC_NHANVIEN_XHH", params);
	}

	public Object capnhat_trangthai_gui_sms(CapNhatTrangThaiGuiSms input) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_DS_GIAOVIEC_ID", input.getDs_giaoviec_id(),ParameterDirection.INPUT, Types.CLOB));
		dbContext.executeSpWithCursorToListMap("QLSC.pkg_nghiepvu.SP_CAPNHAT_TRANGTHAI_GUI_SMS", params);
		return true;
	}

	public Object lay_nd_gui_sms(Long giaoviec_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_GIAOVIEC_ID", giaoviec_id,ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_ND_GUI_SMS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		ArrayList<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("QLSC.pkg_nghiepvu.SP_LAY_ND_GUI_SMS", params);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	public Object xuLySMSGiaoViecNV(GiaoViecNvInput input) throws AppSqlException {
//		ArrayList<Object> result = new ArrayList<Object>();
//		ArrayList<Map<String, Object>> items = (ArrayList<Map<String, Object>>) this.giaoviec_nv(input);
//		if(!(items == null || items.size() == 0)) {
//			for (int i = 0; i < items.size(); i++) {
//				Map<String, Object> item = items.get(i);
//				Object soDT = item.get("SO_DT");
//				Object noiDung = item.get("ND_SMS");
//				Object giaoViecID = item.get("GIAOVIEC_ID");
//				result.add(giaoViecID);
//				this.smsSend(soDT.toString(), noiDung.toString());
//			}
//		}
//		return result;
		
		ArrayList<Object> result = new ArrayList<Object>();
		if (input.getType() != null && input.getType().equals(1)) { // NV ky thuat dia ban
			ArrayList<Map<String, Object>> items = (ArrayList<Map<String, Object>>) this.giaoviec_nv(input);
			if(!(items == null || items.size() == 0)) {
				for (int i = 0; i < items.size(); i++) {
					Map<String, Object> item = items.get(i);
					Object soDT = item.get("SO_DT");
					Object noiDung = item.get("ND_SMS");
					Object giaoViecID = item.get("GIAOVIEC_ID");
					result.add(giaoViecID);
					this.smsSend(soDT.toString(), noiDung.toString());
				}
			}
		}
		else if (input.getType() != null && input.getType().equals(2)) { // NV xa hoi hoa
			ArrayList<Map<String, Object>> items = (ArrayList<Map<String, Object>>) this.giaoViecNVXaHoiHoa(input);
			if(!(items == null || items.size() == 0)) {
				for (int i = 0; i < items.size(); i++) {
					Map<String, Object> item = items.get(i);
					Object soDT = item.get("SO_DT");
					Object noiDung = item.get("ND_SMS");
					Object giaoViecID = item.get("GIAOVIEC_ID");
					result.add(giaoViecID);
					boolean sent = this.smsSend(soDT.toString(), noiDung.toString());

					// Cap nhat trang thai gui sms
					if (sent) {
						CapNhatTrangThaiGuiSms capNhatTrangThaiGuiSms = new CapNhatTrangThaiGuiSms();
						capNhatTrangThaiGuiSms.setDs_giaoviec_id(giaoViecID.toString());
						this.capnhat_trangthai_gui_sms(capNhatTrangThaiGuiSms);
					}
				}
			}
		}

		return result;

	}

	public Object xuLySMSHuyGiaoViec(Long idGiaoViec) throws AppSqlException {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Result", false);
		result.put("Msg", "Không gửi được tin nhắn (hãy kiểm tra lại số điện thoại hoặc hệ thống nhắn tin)");
		Map<String, Object> item = (Map<String, Object>) this.huy_giaoviec_nv(idGiaoViec);
		if(item != null) {
			Object soDT = item.get("SO_DT");
			Object noiDung = item.get("ND_SMS");
			if(!(soDT == null || noiDung == null)) {
				boolean sms = this.smsSend(soDT.toString(), noiDung.toString());
				if(sms) {
					result.replace("Result", true);
					result.replace("Msg", "Gửi tin nhắn thành công");
				}
			}
			else {
				result.replace("Msg", "Không gửi được tin nhắn (hãy kiểm tra lại số điện thoại hoặc hệ thống nhắn tin)");
			}
		}
		return result;
	}

	public Object xuLySMSGiaoViec(Long giaoviec_id) throws AppSqlException {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Result", false);
		result.put("Msg", "Phiếu không tồn tại hoặc đã hoàn công không thể nhắn tin");
		Map<String, Object> item = (Map<String, Object>) this.lay_nd_gui_sms(giaoviec_id);
		if(item != null) {
			Object soDT = item.get("SO_DT");
			if(soDT != null) {
				boolean sms = this.smsSend(soDT.toString(), item.get("ND_SMS").toString());
				if(sms) {
					result.replace("Result", true);
					result.replace("Msg", "Gửi tin nhắn thành công");
				}
				else {
					result.replace("Msg", "Không gửi được tin nhắn (hãy kiểm tra lại số điện thoại hoặc hệ thống nhắn tin)");
				}
			}
			else {
				result.replace("Msg", "Không gửi được tin nhắn (hãy kiểm tra lại số điện thoại hoặc hệ thống nhắn tin)");
			}
		}
		return result;
	}

    public boolean smsSend(String soDT, String noiDung) {
    	SendSMSDtoIn item = new SendSMSDtoIn();
    	item.setMsisdn(soDT);
    	item.setContent(noiDung);
    	return this.smsSend(item);
	}

    public boolean smsSend(SendSMSDtoIn obj) {
		AppRequestContext current = AppRequestContext.getCurrent();
		RestTemplate restTemplate = new RestTemplate();
		String soDT = obj.getMsisdn();
		if (!(soDT == null || soDT.equals("") || soDT.startsWith("84"))) {
			if (soDT.startsWith("0"))
				soDT = soDT.substring(1);
			soDT = "84" + soDT;
		}
		// soDT = "84915445554";
		obj.setMsisdn(soDT);
		obj.setMt_type("reply");
		String urlquantri = "http://quantri/internal/sendSms";
		HttpHeaders headers = new HttpHeaders();
		// set content-type header
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", current.getToken().getValue());
		HttpEntity<Object> request = new HttpEntity<>(obj, headers);
		try {
			ResponseEntity<Object> result = restTemplate.postForEntity(urlquantri, request, Object.class);
			Map<String, Object> resultBody = (Map<String, Object>) result.getBody();
			Map<String, Object> resultBodyData = (Map<String, Object>) resultBody.get("data");
			int ecode = Integer.parseInt(resultBodyData.get("error_code").toString());
			return ecode == 0;
		} catch (Exception e) {

		}
		return false;
	}

	public boolean capnhat_anhchup(CapNhatAnhChupDto input) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_PHIEU_ID", input.getPhieu_id(),ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_MOTA", input.getMota(),ParameterDirection.INPUT, Types.VARCHAR));
		dbContext.executeSpWithCursorToListMap("QLSC.pkg_nghiepvu.SP_CAPNHAT_ANHCHUP_TIEPNHAN_SC", params);
		return true;
	}
}
