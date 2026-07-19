package vn.vnpt.vnptit.ecms.service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.CardManeDto;
import vn.vnpt.vnptit.ecms.dto.CardSwitchDto;
import vn.vnpt.vnptit.ecms.dto.EwsdCardDto;
import vn.vnpt.vnptit.ecms.dto.EwsdInsDto;
import vn.vnpt.vnptit.ecms.dto.EwsdShelfIns;
import vn.vnpt.vnptit.ecms.dto.ModuleManeDto;
import vn.vnpt.vnptit.ecms.dto.TuLamDto;

@Service
public class ThietBiChuyenMachService {

	private final DbContext dbContext;

	public ThietBiChuyenMachService(DbContext dbContext) {
		this.dbContext = dbContext;
	}

	public ArrayList<Map<String, Object>> dsTrungTamVienThong(long donviDlId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_donvidl_id", donviDlId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_TRUNG_TAM_GET", params);
	}

	public ArrayList<Map<String, Object>> dsToVienThong(long donviDlId, long loaiDvId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_donvidl_id", donviDlId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loaidv_id", loaiDvId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_TO_GET", params);
	}

	public ArrayList<Map<String, Object>> dsTramVienThong(long loaiDvId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loaidv_id", loaiDvId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_TRUNG_TAM_GET", params);
	}

	public ArrayList<Map<String, Object>> dsEWSD() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_EWSD_GET", params);
	}

	public ArrayList<Map<String, Object>> dsEwsdShelf() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_SHELF_EWSD_GET", params);
	}

	public ArrayList<Map<String, Object>> dsCardEwsd() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_CARD_EWSD_GET", params);
	}

	public ArrayList<Map<String, Object>> dsBrasCard() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_BRAS_CARD_GET", params);
	}

	public ArrayList<Map<String, Object>> dsBrasPort() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_BRAS_PORT_GET", params);
	}

	public ArrayList<Map<String, Object>> dsDslamGponSwitch(long loaiTBiId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loaitbi_id", loaiTBiId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_DSLAM_GPON_SWITCH_GET",
				params);
	}

	public ArrayList<Map<String, Object>> dsGponCard() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_GPON_CARD_GET", params);
	}

	public ArrayList<Map<String, Object>> dsGponPort() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_GPON_PORT_GET", params);
	}

	public ArrayList<Map<String, Object>> dsCardMane() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_CARD_MANE_GET", params);
	}

	public ArrayList<Map<String, Object>> dsPortMane() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_PORT_MANE_GET", params);
	}

	public ArrayList<Map<String, Object>> dsDSlamMane() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_DSLAM_MANE_GET", params);
	}

	public ArrayList<Map<String, Object>> dsPortModuleMane() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_PORT_MODULE_MANE_GET", params);
	}

	public ArrayList<Map<String, Object>> dsCardSwitch() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_CARD_SWITCH_GET", params);
	}

	public ArrayList<Map<String, Object>> dsSwitchPort() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_SWITCH_PORT_GET", params);
	}

	public ArrayList<Map<String, Object>> dsSwitchCardPort() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_SWITCH_CARD_PORT_GET", params);
	}

	public ArrayList<Map<String, Object>> dsPortDslam() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_PORT_DSLAM_GET", params);
	}

	public ArrayList<Map<String, Object>> dsDslamModule() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_DSLAM_MODULE_GET", params);
	}

	public ArrayList<Map<String, Object>> dsPortModule() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_PORT_MODULE_GET", params);
	}

	public ArrayList<Map<String, Object>> dsFrameCard() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_FRAME_CARD_GET", params);
	}

	public Map<String, Object> themEWSD(EwsdInsDto ewsdInsDto) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ten_ewsd", ewsdInsDto.getTenEwsd(), SqlParameter.ParameterDirection.INPUT,
				Types.VARCHAR));
		params.add(new SqlParameter("p_loai_ewsd_id", ewsdInsDto.getLoaiEwsdId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_turack_id", ewsdInsDto.getTurackId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_host_id", ewsdInsDto.getHostId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(
				new SqlParameter("p_orig", ewsdInsDto.getOrig(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_EWSD_INS", params);
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

	public boolean capNhatEWSD(EwsdInsDto.EwsdUpdateDto ewsdInsDto) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ewsd_id", ewsdInsDto.getEwsdId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_ten_ewsd", ewsdInsDto.getTenEwsd(), SqlParameter.ParameterDirection.INPUT,
				Types.VARCHAR));
		params.add(new SqlParameter("p_loai_ewsd_id", ewsdInsDto.getLoaiEwsdId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_turack_id", ewsdInsDto.getTurackId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_host_id", ewsdInsDto.getHostId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(
				new SqlParameter("p_orig", ewsdInsDto.getOrig(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_EWSD_UPD", params);
		return "1".equals(list.get(0).get("KETQUA").toString());
	}

	public boolean checkEwsdShelf(Long ewsdId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_EWSD_ID", ewsdId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		ArrayList<Map<String, Object>> kq = dbContext
				.executeSpIdxWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_CHECK_EWSD_SHELF", params);
		boolean rs = "0".equals(kq.get(0).get("KETQUA").toString());
		return rs;
	}

	public boolean xoaEWSD(Long ewsdId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ewsd_id", ewsdId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		ArrayList<Map<String, Object>> kq = dbContext
				.executeSpIdxWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_EWSD_DEL", params);
		return "1".equals(kq.get(0).get("KETQUA").toString());
	}

	public Map<String, Object> themEWSDSHELF(EwsdShelfIns ewsdInsDto) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ten_shelf", ewsdInsDto.getTenShelf(), SqlParameter.ParameterDirection.INPUT,
				Types.VARCHAR));
		params.add(new SqlParameter("p_ewsd_id", ewsdInsDto.getEwsdId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_so_slot", ewsdInsDto.getSoSlot(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_vi_tri", ewsdInsDto.getViTri(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_EWSD_SHELF_INS", params);
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

	public boolean capNhatEWSDSHELF(EwsdShelfIns.EwsdShelfUpd ewsdInsDto) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_shelf_ewsd_id", ewsdInsDto.getShelfEwsdId(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ten_shelf", ewsdInsDto.getTenShelf(), SqlParameter.ParameterDirection.INPUT,
				Types.VARCHAR));
		params.add(new SqlParameter("p_ewsd_id", ewsdInsDto.getEwsdId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_so_slot", ewsdInsDto.getSoSlot(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_vi_tri", ewsdInsDto.getViTri(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_EWSD_SHELF_UPD", params);
		return "1".equals(list.get(0).get("KETQUA").toString());
	}

	public boolean xoaEWSDSHELF(int ewsdShelfId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(
				new SqlParameter("p_shelf_ewsd_id", ewsdShelfId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		ArrayList<Map<String, Object>> kq = dbContext
				.executeSpIdxWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_EWSD_SHELF_DEL", params);
		return "1".equals(kq.get(0).get("KETQUA").toString());
	}

	public Object xoaAllCardSHELF(int ewsdShelfId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(
				new SqlParameter("VSHELF_EWSD_ID", ewsdShelfId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		return dbContext.executeFunction("ECMS.PKG_THIETBI_CHUYENMACH.XOA_ALL_SHELWEWSD", 12, params);

	}
	public Object XoaShelfEwsd(int ewsdShelfId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(
				new SqlParameter("VCARD_EWSD_ID", ewsdShelfId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		return dbContext.executeFunction("ECMS.PKG_THIETBI_CHUYENMACH.XOA_CARD_EWSD", 12, params);

	}

	public Map<String, Object> themEwsdCard(EwsdCardDto ewsdCardDto) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vitri", ewsdCardDto.getViTri(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_shelf_ewsd_id", ewsdCardDto.getShelfEwsdId(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_card_ewsd_id", ewsdCardDto.getCard_ewsd_id(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loaicard_id", ewsdCardDto.getLoaiCardId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_ten_card", ewsdCardDto.getTenCard(), SqlParameter.ParameterDirection.INPUT,
				Types.VARCHAR));
		params.add(new SqlParameter("p_vitri_qu", ewsdCardDto.getViTriQu(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_EWSD_CARD_INS", params);
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

	public boolean capNhatEwsdCard(EwsdCardDto.EwsdCardUpd ewsdCardUpd) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_card_ewsd_id", ewsdCardUpd.getCardEwsdId(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vitri", ewsdCardUpd.getViTri(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_shelf_ewsd_id", ewsdCardUpd.getShelfEwsdId(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loaicard_id", ewsdCardUpd.getLoaiCardId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_ten_card", ewsdCardUpd.getTenCard(), SqlParameter.ParameterDirection.INPUT,
				Types.VARCHAR));
		params.add(new SqlParameter("p_vitri_qu", ewsdCardUpd.getViTriQu(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_EWSD_CARD_UPD", params);
		return "1".equals(list.get(0).get("KETQUA").toString());
	}

	public boolean xoaEwsdCard(Long cardEwsdId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(
				new SqlParameter("p_card_ewsd_id", cardEwsdId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		ArrayList<Map<String, Object>> kq = dbContext
				.executeSpIdxWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_CARD_EWSD_DEL", params);
		return "1".equals(kq.get(0).get("KETQUA").toString());
	}

	public Map<String, Object> themTuDSLam(TuLamDto tuLamDto) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_dslam_id", tuLamDto.getDslamId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_turack_id", tuLamDto.getTuRackId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_TU_DSLAM_INS", params);
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

	public boolean taoPortSW(Long switchId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_switch_id", switchId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		ArrayList<Map<String, Object>> kq = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_TAO_PORT_SW_INS", params);
		return "1".equals(kq.get(0).get("KETQUA").toString());

	}

	public Map<String, Object> themModuleMane(ModuleManeDto moduleManeDto) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_cardmane_id", moduleManeDto.getCardManeId(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vitri", moduleManeDto.getViTri(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_serial", moduleManeDto.getSerial(), SqlParameter.ParameterDirection.INPUT,
				Types.VARCHAR));
		params.add(new SqlParameter("p_partnumber", moduleManeDto.getPartNumber(),
				SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_loaimodule_id", moduleManeDto.getLoaiModuleId(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_MODULE_MANE_INS", params);
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

	public boolean capNhatModuleMane(ModuleManeDto.ModuleManeUpdDto moduleManeDto) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_cardmane_id", moduleManeDto.getCardManeId(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_cardmane_id", moduleManeDto.getModuleManeId(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vitri", moduleManeDto.getViTri(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_serial", moduleManeDto.getSerial(), SqlParameter.ParameterDirection.INPUT,
				Types.VARCHAR));
		params.add(new SqlParameter("p_partnumber", moduleManeDto.getPartNumber(),
				SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_loaimodule_id", moduleManeDto.getLoaiModuleId(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_MODULE_MANE_UPD", params);
		return "1".equals(list.get(0).get("KETQUA").toString());
	}

	public boolean xoaModuleMane(Long moduleDslId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(
				new SqlParameter("p_moduledsl_id", moduleDslId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		ArrayList<Map<String, Object>> kq = dbContext
				.executeSpIdxWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_MANE_MODULE_DEL", params);
		return "1".equals(kq.get(0).get("KETQUA").toString());
	}

	public boolean taoPortDslamModule(Long moduleManeId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_modulemane_id", moduleManeId, SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		ArrayList<Map<String, Object>> kq = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_TAO_PORT_DSLAM_MODULE_MANE_INS", params);
		return "1".equals(kq.get(0).get("KETQUA").toString());

	}

	public Map<String, Object> themCardSwitch(CardSwitchDto cardSwitchDto) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_switch_id", cardSwitchDto.getSwitchId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_vitri", cardSwitchDto.getViTri(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_slot", cardSwitchDto.getSlot(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_serial", cardSwitchDto.getSerial(), SqlParameter.ParameterDirection.INPUT,
				Types.VARCHAR));
		params.add(new SqlParameter("p_partnumber", cardSwitchDto.getPartNumber(),
				SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_loaicard_id", cardSwitchDto.getLoaiCardId(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ten_card", cardSwitchDto.getTenCard(), SqlParameter.ParameterDirection.INPUT,
				Types.VARCHAR));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_CARD_SWITCH_INS", params);
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

	public boolean capNhatCardSwitch(CardSwitchDto.CardSwitchUpDto cardSwitchDto) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_cardsw_id", cardSwitchDto.getCardswId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_switch_id", cardSwitchDto.getSwitchId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_vitri", cardSwitchDto.getViTri(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_slot", cardSwitchDto.getSlot(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_serial", cardSwitchDto.getSerial(), SqlParameter.ParameterDirection.INPUT,
				Types.VARCHAR));
		params.add(new SqlParameter("p_partnumber", cardSwitchDto.getPartNumber(),
				SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_loaicard_id", cardSwitchDto.getLoaiCardId(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ten_card", cardSwitchDto.getTenCard(), SqlParameter.ParameterDirection.INPUT,
				Types.VARCHAR));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_CARD_SWITCH_UPD", params);
		return "1".equals(list.get(0).get("KETQUA").toString());
	}

	public boolean xoaCardSwitch(Long cardswId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_moduledsl_id", cardswId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		ArrayList<Map<String, Object>> kq = dbContext
				.executeSpIdxWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_CARD_SWITCH_DEL", params);
		return "1".equals(kq.get(0).get("KETQUA").toString());
	}

	public Map<String, Object> themCardMane(CardManeDto cardManeDto) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_dslam_id", cardManeDto.getDslamId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_vitri", cardManeDto.getViTri(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_loaicard_id", cardManeDto.getLoaiCardId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_serial", cardManeDto.getSerial(), SqlParameter.ParameterDirection.INPUT,
				Types.VARCHAR));
		params.add(new SqlParameter("p_ten_card", cardManeDto.getTenCard(), SqlParameter.ParameterDirection.INPUT,
				Types.VARCHAR));
		params.add(new SqlParameter("p_partnumber", cardManeDto.getPartNumber(), SqlParameter.ParameterDirection.INPUT,
				Types.VARCHAR));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_CARD_MANE_INS", params);
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

	public boolean capNhatCardMane(CardManeDto.CardManeUpdDto cardManeDto) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_cardmane_id", cardManeDto.getCardManeId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_dslam_id", cardManeDto.getDslamId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_vitri", cardManeDto.getViTri(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_loaicard_id", cardManeDto.getLoaiCardId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("p_serial", cardManeDto.getSerial(), SqlParameter.ParameterDirection.INPUT,
				Types.VARCHAR));
		params.add(new SqlParameter("p_ten_card", cardManeDto.getTenCard(), SqlParameter.ParameterDirection.INPUT,
				Types.VARCHAR));
		params.add(new SqlParameter("p_partnumber", cardManeDto.getPartNumber(), SqlParameter.ParameterDirection.INPUT,
				Types.VARCHAR));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_CARD_MANE_UPD", params);
		return "1".equals(list.get(0).get("KETQUA").toString());
	}

	public boolean xoaCardMane(Long cardManeId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_cardmane_id", cardManeId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		ArrayList<Map<String, Object>> kq = dbContext
				.executeSpIdxWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_CARD_MANE_DEL", params);
		return "1".equals(kq.get(0).get("KETQUA").toString());
	}

	public boolean taoPortDslamMane(Long cardManeId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_cardmane_id", cardManeId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		ArrayList<Map<String, Object>> kq = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_TAO_PORT_DSLAM_MANE_INS", params);
		return "1".equals(kq.get(0).get("KETQUA").toString());
	}

	public ArrayList<Map<String, Object>> layCardEwsdTheoShelf(Long shelfEwsdId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(
				new SqlParameter("p_shelf_ewsd_id", shelfEwsdId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_LAY_CARD_EWSD_THEO_SHELF",
				params);
	}

	public ArrayList<Map<String, Object>> layPortTheoCard(Long cardEwsdId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(
				new SqlParameter("p_card_ewsd_id", cardEwsdId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_return", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_LAY_EWSD_PORT_THEO_CARD", params);
	}

	/**
	 * @author LOCNV
	 * @date 25/08/2021
	 */
	public Object layDsShelfEwsdFull() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
		params.add(new SqlParameter("P_CURSOR", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_SHELF_EWSD_FULL", params);
	}

	public Object layShelfEwsd(int shelfEwsdID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
		params.add(new SqlParameter("P_SHELF_EWSD_ID", shelfEwsdID, Types.NUMERIC));
		params.add(new SqlParameter("P_CURSOR", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_LAY_SHELF_EWSD", params);
	}

	/**
	 * END LOCNV
	 */

	/**
	 * @author CUONGLC
	 * @date 26/08/2021
	 */
	public Object layEwsdTheoID(Integer ewsd_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
		params.add(new SqlParameter("P_EWSD_ID", ewsd_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_CURSOR", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_EWSD_GET", params);
	}

	public Object layDSTuRackeTheoNhaTram(Integer donvi_id, Integer kieu) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
		params.add(new SqlParameter("P_DONVI_ID", donvi_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_KIEU", kieu, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_CURSOR", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_TU_RACK_THEO_TRAM", params);
	}

	public Object layDSLoaiEWSD() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_CURSOR", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_LOAIEWSD_GET", params);
	}

	public Object layDSHost(String loaidv_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
		params.add(new SqlParameter("P_LOAIDV_ID", loaidv_id, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("P_CURSOR", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_HOST_GET", params);
	}

	public ArrayList<Map<String, Object>> dsCardTheoShelfEwsdId(Integer shelf_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_SHELF_EWSD_ID", shelf_id, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("P_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_CARD_SHELF_EWSD_ID_GET",
				params);
	}

	public Map<String, Object> layPortInCard(Integer card_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_CARD_EWSD_ID", card_id, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("P_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_CARD_EWSD_USED_GET", params);
	}

	public Object layTrangThai() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_CURSOR", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_TRANGTHAI_PORT_GET", params);
	}

	public Object layLoaiCard(Integer nhomcard_id, Integer loaitbi_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(
				new SqlParameter("P_NHOMCARD_ID", nhomcard_id, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("P_LOAITBI_ID", loaitbi_id, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("P_CURSOR", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_LOAICARD_GET", params);
	}

	public Object layNhomCard(Integer loaitbi_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_LOAITBI_ID", loaitbi_id, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("P_CURSOR", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_NHOMCARD_GET", params);
	}

	public Object checkDeleteCard(Integer card_ewsd_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(
				new SqlParameter("P_CARD_EWSD_ID", card_ewsd_id, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("P_CURSOR", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_CHECK_LOGIC_PORT_DEL", params);
	}

	public Object updateTTPort(Integer nevl_id, Integer tt_port_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_NEVL_ID", nevl_id, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("P_TT_PORT_ID", tt_port_id, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		return dbContext.executeFunction("ECMS.PKG_THIETBI_CHUYENMACH.CAPNHAT_TT_PORT_EWSD", 12, params);
	}

	/**
	 * END CUONGLC
	 */

	/**
	 * STRT HUU MINH
	 */
	public ArrayList<Map<String, Object>> dsBras() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_BRAS_GET", params);
	}

	public ArrayList<Map<String, Object>> dsDslShelf() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_DSLAM_SHELF_GET", params);
	}

	public ArrayList<Map<String, Object>> dsOdf() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_DS_ODF_GET", params);
	}
	public ArrayList<Map<String, Object>> LayThongTin_Loai_Card(int loaicard_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_LOAI_CARD_ID", loaicard_id,SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.SP_LAYTHONGTIN_LOAI_CARD", params);
	}
	/**
	 * END HUU MINH
	 */

	public ArrayList<Map<String, Object>> TaoPortEWSD(int card_ewsd_id, int so_port) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("VCARD_EWSD_ID", card_ewsd_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("VSO_PORT", so_port, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THIETBI_CHUYENMACH.TAO_PORT_EWSD", params);
	}
}
