package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.*;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DsLamService {
	private final DbContext dbContext;

	public DsLamService(DbContext dbContext) {
		this.dbContext = dbContext;
	}

	public ArrayList<Map<String, Object>> DsNhomDsLam() throws AppSqlException {
		SqlParameter param = new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAIDSLAM.SP_LOAIDSLAM_SEL", param);
	}

	public Map<String, Object> DsNhomDsLamTheoID(Long p_loai_dslam_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_loai_dslam_id", String.valueOf(p_loai_dslam_id), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAIDSLAM.SP_LOAIDSLAM_SEL_BY_ID", params);
		return result != null && result.size() > 0 ? result.get(0) : null;
	}

	public ArrayList<Map<String, Object>> DsNhomDsLamTheoObject(LoaiDSLamObjectDtoIn loaiDSlamDtoIn) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_nhom_dslam", loaiDSlamDtoIn.getP_nhom_dslam(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_loai_dslam", loaiDSlamDtoIn.getP_loai_dslam(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_thamso", loaiDSlamDtoIn.getP_thamso() != null ? loaiDSlamDtoIn.getP_thamso().toString() : null, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_pagenumber", loaiDSlamDtoIn.getPageNumber().toString(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_pagesize", loaiDSlamDtoIn.getPageSize().toString(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAIDSLAM.SP_LOAIDSLAM_FILTER_SEL", params);
	}

	public Map<String, Object> themNhomDsLam(LoaiDSlamDtoIn loaiDSlamDtoIn) throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_nhom_dslam_id", loaiDSlamDtoIn.getP_nhom_dslam_id().toString(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loai_dslam", loaiDSlamDtoIn.getP_loai_dslam(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_thamso", loaiDSlamDtoIn.getP_thamso().toString(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ghichu", loaiDSlamDtoIn.getP_ghichu(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_port_td", loaiDSlamDtoIn.getP_port_td().toString(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_bras_td", loaiDSlamDtoIn.getP_bras_td().toString(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAIDSLAM.SP_LOAIDSLAM_INS", params);
		return result != null && result.size() > 0 ? result.get(0) : null;
	}

	public Map<String, Object> capNhomDsLam(LoaiDSlamUpdDtoIn loaiDSlamDtoIn) throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_nhom_dslam_id", loaiDSlamDtoIn.getP_nhom_dslam_id().toString(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loai_dslam_id", loaiDSlamDtoIn.getP_loai_dslam_id().toString(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loai_dslam", loaiDSlamDtoIn.getP_loai_dslam(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_thamso", loaiDSlamDtoIn.getP_thamso().toString(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ghichu", loaiDSlamDtoIn.getP_ghichu(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_port_td", loaiDSlamDtoIn.getP_port_td().toString(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_bras_td", loaiDSlamDtoIn.getP_bras_td().toString(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAIDSLAM.SP_LOAIDSLAM_UPD", params);
		return result != null && result.size() > 0 ? result.get(0) : null;
	}

	public boolean xoaNhomDsLam(Long p_loai_dslam_id) throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loai_dslam_id", String.valueOf(p_loai_dslam_id), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", java.sql.Types.REF_CURSOR, int.class));
		ArrayList<?> result =  dbContext.executeSpWithCursorToEntities("ECMS.PKG_LOAIDSLAM.SP_LOAIDSLAM_DEL", params);
		if (!(result == null || result.size() == 0)) {
			return Integer.parseInt(result.get(0).toString()) > 0;
		}
		return false;
	}

	public ArrayList<Map<String, Object>> lietKeDanhSachDslam(int loaiTbiId) throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loaitbi_id", loaiTbiId, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_thongtin_dslam", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ecms.pkg_dslam.sp_ds_dslam_sel", params);
	}

	public Map<String, ArrayList<Map<String, Object>>> themDslam(ThemDslamDtoIn themDslamDtoIn) throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_tendslam", themDslamDtoIn.getTenDslam(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_bras_id", themDslamDtoIn.getBrasId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_thamso", themDslamDtoIn.getThamSo(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_donvi_id", themDslamDtoIn.getDonViId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_dslam_cha_id", themDslamDtoIn.getDslamChaId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ip", themDslamDtoIn.getIp(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_system", themDslamDtoIn.getSystem(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_svlan", themDslamDtoIn.getSvlan(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_loai_dslam_id", themDslamDtoIn.getLoaiDslamId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_card_bd", themDslamDtoIn.getCardBd(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vci_bd", themDslamDtoIn.getVciBd(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getMaNhanVien(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_ghichu", themDslamDtoIn.getGhiChu(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_dslam_vdc_id", themDslamDtoIn.getDslamVdcId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_kieudslam_id", themDslamDtoIn.getKieuDslamId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loaitbi_id", themDslamDtoIn.getLoaiTbiId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_hangsx_id", themDslamDtoIn.getHangSxId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_so_port", themDslamDtoIn.getSoPort(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_so_slot", themDslamDtoIn.getSoSlot(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_port_bd", themDslamDtoIn.getPortBd(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vlanmytv", themDslamDtoIn.getVlanMytv(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_vlan_int", themDslamDtoIn.getVlanInt(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vlan_gnms", themDslamDtoIn.getVlanGnms(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vlan_ims", themDslamDtoIn.getVlanIms(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_slot_bras", themDslamDtoIn.getSlotBras(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_port_bras", themDslamDtoIn.getPortBras(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_dothu", themDslamDtoIn.getDoThu(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_toanha_id", themDslamDtoIn.getToaNhaId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_doitac_id", themDslamDtoIn.getDoiTacId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_sub_slot", themDslamDtoIn.getSubSlot(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_mdf_bd", themDslamDtoIn.getMdfBd(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_thongtin_ae", themDslamDtoIn.getThongTinAe(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_tu_dslam", themDslamDtoIn.getTuDslam(), ParameterDirection.INPUT, Types.VARCHAR));
		//change position of username and password
		params.add(new SqlParameter("p_username", themDslamDtoIn.getUsername(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_password", themDslamDtoIn.getPassword(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_thongtin_dslam", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR, "dslam"));
		params.add(new SqlParameter("p_thongtin_port", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR, "port"));
		return dbContext.executeSpWithCursorsToListMap("ecms.pkg_dslam.sp_dslam_ins", params);
	}

	public boolean capNhatDslam(CapNhatDslamDtoIn capNhatDslamDtoIn) throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_dslam_id", capNhatDslamDtoIn.getDslamId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_tendslam", capNhatDslamDtoIn.getTenDslam(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_bras_id", capNhatDslamDtoIn.getBrasId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_thamso", capNhatDslamDtoIn.getThamSo(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_donvi_id", capNhatDslamDtoIn.getDonViId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_dslam_cha_id", capNhatDslamDtoIn.getDslamChaId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ip", capNhatDslamDtoIn.getIp(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_system", capNhatDslamDtoIn.getSystem(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_svlan", capNhatDslamDtoIn.getSvlan(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_loai_dslam_id", capNhatDslamDtoIn.getLoaiDslamId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_card_bd", capNhatDslamDtoIn.getCardBd(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vci_bd", capNhatDslamDtoIn.getVciBd(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getMaNhanVien(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_ghichu", capNhatDslamDtoIn.getGhiChu(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_dslam_vdc_id", capNhatDslamDtoIn.getDslamVdcId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_stt_olt", capNhatDslamDtoIn.getSttOlt(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_kieudslam_id", capNhatDslamDtoIn.getKieuDslamId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loaitbi_id", capNhatDslamDtoIn.getLoaiTbiId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_hangsx_id", capNhatDslamDtoIn.getHangSxId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_so_port", capNhatDslamDtoIn.getSoPort(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_so_slot", capNhatDslamDtoIn.getSoSlot(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_port_bd", capNhatDslamDtoIn.getPortBd(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vlanmytv", capNhatDslamDtoIn.getVlanMytv(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_vlan_int", capNhatDslamDtoIn.getVlanInt(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vlan_gnms", capNhatDslamDtoIn.getVlanGnms(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vlan_ims", capNhatDslamDtoIn.getVlanIms(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_slot_bras", capNhatDslamDtoIn.getSlotBras(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_port_bras", capNhatDslamDtoIn.getPortBras(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_dothu", capNhatDslamDtoIn.getDoThu(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_toanha_id", capNhatDslamDtoIn.getToaNhaId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_doitac_id", capNhatDslamDtoIn.getDoiTacId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_sub_slot", capNhatDslamDtoIn.getSubSlot(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_mdf_bd", capNhatDslamDtoIn.getMdfBd(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_thongtin_ae", capNhatDslamDtoIn.getThongTinAe(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_tu_dslam", capNhatDslamDtoIn.getTuDslam(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_username", capNhatDslamDtoIn.getUsername(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_password", capNhatDslamDtoIn.getPassword(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_ketqua", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		ArrayList<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("ecms.pkg_dslam.sp_dslam_upd", params);
		return "1".equals(list.get(0).get("KQ").toString());
	}

	public boolean xoaDslam(XoaDslamDtoIn xoaDslamDtoIn) throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_dslam_id", xoaDslamDtoIn.getDslamId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loaitbi_id", xoaDslamDtoIn.getLoaiTbiId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ketqua", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		ArrayList<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("ecms.pkg_dslam.sp_dslam_del", params);
		return "1".equals(list.get(0).get("KQ").toString());
	}

	public ArrayList<Map<String, Object>> layThongTinDslam(int dslamId) throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_dslam_id", dslamId, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_thongtin_dslam", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ecms.pkg_dslam.sp_dslam_sel", params);
	}

	public ArrayList<Map<String, Object>> layPortTheoSwitch(int switchId) throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_switch_id", switchId, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_thongtin_port", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ecms.pkg_dslam.sp_lay_port_theo_switch_sel", params);
	}

	public ArrayList<Map<String, Object>> layThamSoMd(ThamSoMD obj) throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_kieu_id", obj.getKieu_id(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ma_ts", obj.getMa_ts(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ref_cursor", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ecms.pkg_dslam.sp_lay_ds_thamso_md", params);
	}

	public ArrayList<Map<String, Object>> layDsDslamCha(int bras_id) throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_bras_id", bras_id, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ref_cursor", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ecms.pkg_dslam.sp_lay_ds_dslam_cha", params);
	}

	public ArrayList<Map<String, Object>> layDsDoiTac() throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ref_cursor", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ecms.pkg_dslam.sp_lay_ds_doitac", params);
	}

	public ArrayList<Map<String, Object>> layDsToaNha() throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ref_cursor", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ecms.pkg_dslam.sp_lay_ds_toanha", params);
	}

	public Map<String, Object> kiemTraTrungIP(DslamDtoInp obj) throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_dslam_id", obj.getDslam_id(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ip", obj.getIp(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_ten_dslam", obj.getTen_dslam(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_ref_cursor", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ecms.pkg_dslam.sp_kiemtra_trung_ip", params);
		Map<String, Object> kq = new HashMap<>();
		if(rs.size() > 0) {
			kq.put("RESULT", true);
		}
		else {
			kq.put("RESULT", false);
		}
		return kq;
	}

	public ArrayList<Map<String, Object>> layDsHangSX() throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_ref_cursor", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ecms.pkg_dslam.sp_lay_ds_hang_sx", params);
	}

	public Map<String, ArrayList<Map<String, Object>>> themDslamV2(ThemDslamDtoIn themDslamDtoIn) throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_tendslam", themDslamDtoIn.getTenDslam(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_bras_id", themDslamDtoIn.getBrasId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_thamso", themDslamDtoIn.getThamSo(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_donvi_id", themDslamDtoIn.getDonViId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_dslam_cha_id", themDslamDtoIn.getDslamChaId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ip", themDslamDtoIn.getIp(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_system", themDslamDtoIn.getSystem(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_svlan", themDslamDtoIn.getSvlan(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_loai_dslam_id", themDslamDtoIn.getLoaiDslamId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_card_bd", themDslamDtoIn.getCardBd(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vci_bd", themDslamDtoIn.getVciBd(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getMaNhanVien(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_ghichu", themDslamDtoIn.getGhiChu(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_dslam_vdc_id", themDslamDtoIn.getDslamVdcId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_kieudslam_id", themDslamDtoIn.getKieuDslamId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loaitbi_id", themDslamDtoIn.getLoaiTbiId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_hangsx_id", themDslamDtoIn.getHangSxId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_so_port", themDslamDtoIn.getSoPort(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_so_slot", themDslamDtoIn.getSoSlot(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_port_bd", themDslamDtoIn.getPortBd(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vlanmytv", themDslamDtoIn.getVlanMytv(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_vlan_int", themDslamDtoIn.getVlanInt(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vlan_gnms", themDslamDtoIn.getVlanGnms(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vlan_ims", themDslamDtoIn.getVlanIms(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_slot_bras", themDslamDtoIn.getSlotBras(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_port_bras", themDslamDtoIn.getPortBras(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_dothu", themDslamDtoIn.getDoThu(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_toanha_id", themDslamDtoIn.getToaNhaId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_doitac_id", themDslamDtoIn.getDoiTacId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_sub_slot", themDslamDtoIn.getSubSlot(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_mdf_bd", themDslamDtoIn.getMdfBd(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_thongtin_ae", themDslamDtoIn.getThongTinAe(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_tu_dslam", themDslamDtoIn.getTuDslam(), ParameterDirection.INPUT, Types.VARCHAR));
		//change position of username and password
		params.add(new SqlParameter("p_username", themDslamDtoIn.getUsername(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_password", themDslamDtoIn.getPassword(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_thongtin_dslam", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR, "dslam"));
		params.add(new SqlParameter("p_thongtin_port", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR, "port"));
		params.add(new SqlParameter("P_GIAOTHUC_KN", themDslamDtoIn.getGiaoThucKN(), ParameterDirection.INPUT, Types.NUMERIC));
		return dbContext.executeSpWithCursorsToListMap("ecms.pkg_dslam.sp_dslam_ins_v2", params);
	}

	public boolean capNhatDslamV2(CapNhatDslamDtoIn capNhatDslamDtoIn) throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_dslam_id", capNhatDslamDtoIn.getDslamId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_tendslam", capNhatDslamDtoIn.getTenDslam(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_bras_id", capNhatDslamDtoIn.getBrasId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_thamso", capNhatDslamDtoIn.getThamSo(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_donvi_id", capNhatDslamDtoIn.getDonViId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_dslam_cha_id", capNhatDslamDtoIn.getDslamChaId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ip", capNhatDslamDtoIn.getIp(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_system", capNhatDslamDtoIn.getSystem(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_svlan", capNhatDslamDtoIn.getSvlan(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_loai_dslam_id", capNhatDslamDtoIn.getLoaiDslamId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_card_bd", capNhatDslamDtoIn.getCardBd(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vci_bd", capNhatDslamDtoIn.getVciBd(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getMaNhanVien(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_ghichu", capNhatDslamDtoIn.getGhiChu(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_dslam_vdc_id", capNhatDslamDtoIn.getDslamVdcId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_stt_olt", capNhatDslamDtoIn.getSttOlt(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_kieudslam_id", capNhatDslamDtoIn.getKieuDslamId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loaitbi_id", capNhatDslamDtoIn.getLoaiTbiId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_hangsx_id", capNhatDslamDtoIn.getHangSxId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_so_port", capNhatDslamDtoIn.getSoPort(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_so_slot", capNhatDslamDtoIn.getSoSlot(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_port_bd", capNhatDslamDtoIn.getPortBd(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vlanmytv", capNhatDslamDtoIn.getVlanMytv(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_vlan_int", capNhatDslamDtoIn.getVlanInt(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vlan_gnms", capNhatDslamDtoIn.getVlanGnms(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vlan_ims", capNhatDslamDtoIn.getVlanIms(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_slot_bras", capNhatDslamDtoIn.getSlotBras(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_port_bras", capNhatDslamDtoIn.getPortBras(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_dothu", capNhatDslamDtoIn.getDoThu(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_toanha_id", capNhatDslamDtoIn.getToaNhaId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_doitac_id", capNhatDslamDtoIn.getDoiTacId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_sub_slot", capNhatDslamDtoIn.getSubSlot(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_mdf_bd", capNhatDslamDtoIn.getMdfBd(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_thongtin_ae", capNhatDslamDtoIn.getThongTinAe(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_tu_dslam", capNhatDslamDtoIn.getTuDslam(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_username", capNhatDslamDtoIn.getUsername(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_password", capNhatDslamDtoIn.getPassword(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_ketqua", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		params.add(new SqlParameter("P_GIAOTHUC_KN", capNhatDslamDtoIn.getGiaoThucKN(), ParameterDirection.INPUT, Types.NUMERIC));
		ArrayList<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("ecms.pkg_dslam.sp_dslam_upd_v2", params);
		return "1".equals(list.get(0).get("KQ").toString());
	}
}
