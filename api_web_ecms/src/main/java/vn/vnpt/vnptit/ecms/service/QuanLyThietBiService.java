package vn.vnpt.vnptit.ecms.service;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.QLTB_BrasInput;
import vn.vnpt.vnptit.ecms.dto.QLTB_BrasInsertInput;
import vn.vnpt.vnptit.ecms.model.CssBrasModel;

@Service
public class QuanLyThietBiService {
	private final DbContext dbContext;

	public QuanLyThietBiService(DbContext dbContext) {
		this.dbContext = dbContext;
	}

	public CssBrasModel getBras(int id) throws AppSqlException {
		CssBrasModel entity = new CssBrasModel();
		entity.setBras_id(id);
		entity.setPhanvung_id(AppRequestContext.getCurrent().getPhanVungID());
		return entity.Load(dbContext) ? entity : null;
	}

	public CssBrasModel themBras(QLTB_BrasInsertInput item) throws AppSqlException {
		CssBrasModel entity = new CssBrasModel();
		entity.Load(item);
		return entity.Insert(dbContext) ? entity : null;
	}

	public CssBrasModel themBrasNew(QLTB_BrasInput item) throws AppSqlException {
		CssBrasModel entity = new CssBrasModel();
		entity.Load(item);
		return entity.InsertNew(dbContext) ? entity : null;
	}

	public boolean capNhatBras(QLTB_BrasInput item) throws AppSqlException {
		CssBrasModel entity = new CssBrasModel();
		entity.Load(item);
		return entity.Update(dbContext);
	}

	public boolean xoaBras(int bras_id) throws AppSqlException {
		CssBrasModel entity = new CssBrasModel();
		entity.setBras_id(bras_id);
		entity.setPhanvung_id(AppRequestContext.getCurrent().getPhanVungID());
		return entity.Delete(dbContext);
	}

	public ArrayList<Map<String, Object>> getMaKetNoiThietBi(int loaitbi_id_n, int loaitbi_id_d)
			throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_loaitbi_id_n", loaitbi_id_n, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_loaitbi_id_d", loaitbi_id_d, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QL_THIETBI.SP_TBI_GET_MAKETNOI", params);
	}

	public boolean ketNoiThietBi(int porvl_id_n, int loaitbi_id_n, int porvl_id_d, int loaitbi_id_d, int thietbi_id_n,
			int thietbi_id_d, int link_code) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_porvl_id_n", porvl_id_n, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_loaitbi_id_n", loaitbi_id_n, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_porvl_id_d", porvl_id_d, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_loaitbi_id_d", loaitbi_id_d, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_thietbi_id_n", thietbi_id_n, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_thietbi_id_d", thietbi_id_d, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_link_code", link_code, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor", java.sql.Types.REF_CURSOR, int.class));
		ArrayList<?> result = dbContext.executeSpWithCursorToEntities("ECMS.PKG_QL_THIETBI.SP_TBI_KETNOI", params);
		if (!(result == null || result.size() == 0)) {
			return Integer.parseInt(result.get(0).toString()) > 0;
		}
		return false;
	}

	public boolean goKetNoiThietBi(int porvl_id_n, int loaitbi_id_n, int porvl_id_d, int loaitbi_id_d)
			throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_porvl_id_n", porvl_id_n, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_loaitbi_id_n", loaitbi_id_n, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_porvl_id_d", porvl_id_d, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_loaitbi_id_d", loaitbi_id_d, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor", java.sql.Types.REF_CURSOR, int.class));
		ArrayList<?> result = dbContext.executeSpWithCursorToEntities("ECMS.PKG_QL_THIETBI.SP_TBI_GOKETNOI", params);
		if (!(result == null || result.size() == 0)) {
			return Integer.parseInt(result.get(0).toString()) > 0;
		}
		return false;
	}

	public boolean capNhatRacksDslam(int dslam_id, Integer[] racks_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_dslam_id", dslam_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_racks_id", StringUtils.join(racks_id, ","), java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_cursor", java.sql.Types.REF_CURSOR, int.class));
		ArrayList<?> result = dbContext.executeSpWithCursorToEntities("ECMS.PKG_QL_THIETBI.SP_RACKS_DSLAM_UPDATE",
				params);
		if (!(result == null || result.size() == 0)) {
			return Integer.parseInt(result.get(0).toString()) > 0;
		}
		return false;
	}

	public ArrayList<Map<String, Object>> getDsRackChuaGan(int dslam_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_dslam_id", dslam_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QL_THIETBI.SP_RACK_GET_DSCHUAGAN", params);
	}

	public ArrayList<Map<String, Object>> getDsRackDaGan(int dslam_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_dslam_id", dslam_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QL_THIETBI.SP_RACK_GET_DSDAGAN", params);
	}

	public Object getThongTinKetNoi(int portvl_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_portvl_id", portvl_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToMap("ECMS.PKG_QL_THIETBI.SP_GET_THONGTIN_UPLINK", params);
	}

	public Object getCayDanhMucThietBi() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor_port_bras", java.sql.Types.REF_CURSOR, "PortBras"));
		params.add(new SqlParameter("p_cursor_card_bras", java.sql.Types.REF_CURSOR, "CardBras"));
		params.add(new SqlParameter("p_cursor_bras", java.sql.Types.REF_CURSOR, "Bras"));
		params.add(new SqlParameter("p_cursor_port_gpon", java.sql.Types.REF_CURSOR, "PortGpon"));
		params.add(new SqlParameter("p_cursor_card_gpon", java.sql.Types.REF_CURSOR, "CardGpon"));
		params.add(new SqlParameter("p_cursor_gpon", java.sql.Types.REF_CURSOR, "Gpon"));
		params.add(new SqlParameter("p_cursor_port_mane", java.sql.Types.REF_CURSOR, "PortMane"));
		params.add(new SqlParameter("p_cursor_card_mane", java.sql.Types.REF_CURSOR, "CardMane"));
		params.add(new SqlParameter("p_cursor_mane", java.sql.Types.REF_CURSOR, "Mane"));
		params.add(new SqlParameter("p_cursor_port_modulemane", java.sql.Types.REF_CURSOR, "PortModuleMane"));
		params.add(new SqlParameter("p_cursor_module_mane", java.sql.Types.REF_CURSOR, "ModuleMane"));
		params.add(new SqlParameter("p_cursor_port_card_sw", java.sql.Types.REF_CURSOR, "PortCardSw"));
		params.add(new SqlParameter("p_cursor_card_switch", java.sql.Types.REF_CURSOR, "CardSwitch"));
		params.add(new SqlParameter("p_cursor_switch", java.sql.Types.REF_CURSOR, "Switch"));
		params.add(new SqlParameter("p_cursor_port_switch", java.sql.Types.REF_CURSOR, "PortSwitch"));
		params.add(new SqlParameter("p_cursor_port_carddsl", java.sql.Types.REF_CURSOR, "PortCardDsl"));
		params.add(new SqlParameter("p_cursor_card_dsl", java.sql.Types.REF_CURSOR, "CardDsl"));
		params.add(new SqlParameter("p_cursor_shelf_dsl", java.sql.Types.REF_CURSOR, "ShelfDsl"));
		params.add(new SqlParameter("p_cursor_frame_dsl", java.sql.Types.REF_CURSOR, "FrameDsl"));
		params.add(new SqlParameter("p_cursor_dslam", java.sql.Types.REF_CURSOR, "Dslam"));
		params.add(new SqlParameter("p_cursor_port_moduledsl", java.sql.Types.REF_CURSOR, "PortModuleDsl"));
		params.add(new SqlParameter("p_cursor_module_dsl", java.sql.Types.REF_CURSOR, "ModuleDsl"));
		params.add(new SqlParameter("p_cursor_card_td", java.sql.Types.REF_CURSOR, "CardTD"));
		params.add(new SqlParameter("p_cursor_frame_td", java.sql.Types.REF_CURSOR, "FrameTD"));
		params.add(new SqlParameter("p_cursor_odf", java.sql.Types.REF_CURSOR, "Odf"));
		params.add(new SqlParameter("p_cursor_ewsd", java.sql.Types.REF_CURSOR, "Ewsd"));
		params.add(new SqlParameter("p_cursor_shelf_ewsd", java.sql.Types.REF_CURSOR, "ShelfEwsd"));
		params.add(new SqlParameter("p_cursor_tu_rack", java.sql.Types.REF_CURSOR, "TuRack"));
		return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_QL_THIETBI.SP_GET_CAY_DM_TBI", params);
	}

	public Object getDanhMucTuRackTheoKieuTu(int kieuTu) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_kieutu", kieuTu, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor", java.sql.Types.REF_CURSOR, "TuRack"));
		return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_QL_THIETBI.SP_GET_DS_TU_RACK_THEO_KIEUTU", params);
	}

	public Object getDanhMucTuRack() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor", java.sql.Types.REF_CURSOR, "TuRack"));
		return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_QL_THIETBI.SP_GET_DS_TU_RACK", params);
	}

	public Object getDanhMucOdf() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor", java.sql.Types.REF_CURSOR, "Odf"));
		return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_QL_THIETBI.SP_GET_DS_ODF_FULL", params);
	}

	public Object getDanhMucBras() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor_port_bras", java.sql.Types.REF_CURSOR, "PortBras"));
		params.add(new SqlParameter("p_cursor_card_bras", java.sql.Types.REF_CURSOR, "CardBras"));
		params.add(new SqlParameter("p_cursor_bras", java.sql.Types.REF_CURSOR, "Bras"));
		return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_QL_THIETBI.SP_GET_DM_BRAS", params);
	}

	public Object getDanhMucGpon() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor_port_gpon", java.sql.Types.REF_CURSOR, "PortGpon"));
		params.add(new SqlParameter("p_cursor_card_gpon", java.sql.Types.REF_CURSOR, "CardGpon"));
		params.add(new SqlParameter("p_cursor_gpon", java.sql.Types.REF_CURSOR, "Gpon"));
		return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_QL_THIETBI.SP_GET_DM_GPON", params);
	}

	public Object getDanhMucMane() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor_port_mane", java.sql.Types.REF_CURSOR, "PortMane"));
		params.add(new SqlParameter("p_cursor_card_mane", java.sql.Types.REF_CURSOR, "CardMane"));
		params.add(new SqlParameter("p_cursor_mane", java.sql.Types.REF_CURSOR, "Mane"));
		params.add(new SqlParameter("p_cursor_port_modulemane", java.sql.Types.REF_CURSOR, "PortModuleMane"));
		params.add(new SqlParameter("p_cursor_module_mane", java.sql.Types.REF_CURSOR, "ModuleMane"));
		return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_QL_THIETBI.SP_GET_DM_MANE", params);
	}

	public Object getDanhMucSwitch() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor_port_card_sw", java.sql.Types.REF_CURSOR, "PortCardSw"));
		params.add(new SqlParameter("p_cursor_card_switch", java.sql.Types.REF_CURSOR, "CardSwitch"));
		params.add(new SqlParameter("p_cursor_switch", java.sql.Types.REF_CURSOR, "Switch"));
		params.add(new SqlParameter("p_cursor_port_switch", java.sql.Types.REF_CURSOR, "PortSwitch"));
		return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_QL_THIETBI.SP_GET_DM_SWITCH", params);
	}

	public Object getDanhMucDslam() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor_port_carddsl", java.sql.Types.REF_CURSOR, "PortCardDsl"));
		params.add(new SqlParameter("p_cursor_card_dsl", java.sql.Types.REF_CURSOR, "CardDsl"));
		params.add(new SqlParameter("p_cursor_shelf_dsl", java.sql.Types.REF_CURSOR, "ShelfDsl"));
		params.add(new SqlParameter("p_cursor_frame_dsl", java.sql.Types.REF_CURSOR, "FrameDsl"));
		params.add(new SqlParameter("p_cursor_dslam", java.sql.Types.REF_CURSOR, "Dslam"));
		params.add(new SqlParameter("p_cursor_port_moduledsl", java.sql.Types.REF_CURSOR, "PortModuleDsl"));
		params.add(new SqlParameter("p_cursor_module_dsl", java.sql.Types.REF_CURSOR, "ModuleDsl"));
		return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_QL_THIETBI.SP_GET_DM_DSLAM", params);
	}

	public Object getDanhMucFrame() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor_card_td", java.sql.Types.REF_CURSOR, "CardTD"));
		params.add(new SqlParameter("p_cursor_frame_td", java.sql.Types.REF_CURSOR, "FrameTD"));
		return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_QL_THIETBI.SP_GET_DM_FRAME", params);
	}

	public Object getDanhMucEwsd() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor_ewsd", java.sql.Types.REF_CURSOR, "Ewsd"));
		params.add(new SqlParameter("p_cursor_shelf_ewsd", java.sql.Types.REF_CURSOR, "ShelfEwsd"));
		return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_QL_THIETBI.SP_GET_DM_EWSD", params);
	}

	public Object getDsCardEwsd() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QL_THIETBI.SP_GET_DS_CARD_EWSD_FULL", params);
	}

	public Object getCayDanhMucThietBiTD() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_rs", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QL_THIETBI.SP_GET_CAY_DM_TBI_TD_FULL", params);
	}

	public Object getThongTinCardGpon(int id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cardgp_id", id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_rs", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToMap("ECMS.PKG_QL_THIETBI.SP_GET_CARD_GPON", params);
	}

	public Object themThongTinCardGpon(Integer gpon_id, Integer loaicard_id, Integer vitri, String ten_card,
			String serial, String partnumber, Integer istaoportlg) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_gpon_id", gpon_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_loaicard_id", loaicard_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_vitri", vitri, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_ten_card", ten_card, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_serial", serial, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_partnumber", partnumber, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_istaoportlg", istaoportlg, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_rs", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToMap("ECMS.PKG_QL_THIETBI.SP_INSERT_CARD_GPON", params);
	}

	public boolean capNhatThongTinCardGpon(Integer cardgp_id, Integer gpon_id, Integer loaicard_id, Integer vitri,
			String ten_card, String serial, String partnumber, Integer istaoportlg) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cardgp_id", cardgp_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_gpon_id", gpon_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_loaicard_id", loaicard_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_vitri", vitri, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_ten_card", ten_card, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_serial", serial, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_partnumber", partnumber, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_istaoportlg", istaoportlg, java.sql.Types.INTEGER));
		dbContext.executeStoredProcedure("ECMS.PKG_QL_THIETBI.SP_UPDATE_CARD_GPON", params);
		return true;
	}

	public boolean xoaThongTinCardGpon(Integer cardgp_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cardgp_id", cardgp_id, java.sql.Types.INTEGER));
		dbContext.executeStoredProcedure("ECMS.PKG_QL_THIETBI.SP_DELETE_CARD_GPON", params);
		return true;
	}

	public boolean kiemTraVitriCardGpon(Integer gpon_id, Integer vitri) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_gpon_id", gpon_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_vitri", vitri, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_rs", java.sql.Types.REF_CURSOR, int.class));
		ArrayList<?> result = dbContext.executeSpWithCursorToEntities("ECMS.PKG_QL_THIETBI.SP_CHECK_CARD_GPON_VITRI",
				params);
		if (!(result == null || result.size() == 0)) {
			return Integer.parseInt(result.get(0).toString()) > 0;
		}
		return false;
	}

	public boolean kiemTraSerialThietBi(String serial) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_serial", serial, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_rs", java.sql.Types.REF_CURSOR, int.class));
		ArrayList<?> result = dbContext.executeSpWithCursorToEntities("ECMS.PKG_QL_THIETBI.SP_CHECK_SERIAL",
				params);
		if (!(result == null || result.size() == 0)) {
			return Integer.parseInt(result.get(0).toString()) > 0;
		}
		return false;
	}

	public boolean kiemTraThayCardGpon(Integer cardgp_id, Integer soport_moi) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cardgp_id", cardgp_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_soport_moi", soport_moi, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_rs", java.sql.Types.REF_CURSOR, int.class));
		ArrayList<?> result = dbContext.executeSpWithCursorToEntities("ECMS.PKG_QL_THIETBI.SP_CHECK_CARD_GPON_THAY",
				params);
		if (!(result == null || result.size() == 0)) {
			return Integer.parseInt(result.get(0).toString()) > 0;
		}
		return false;
	}

	public Object getDsPortCardGpon(Integer cardgp_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cardgp_id", cardgp_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_rs", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QL_THIETBI.SP_GET_DS_PORT_BY_CARD_GPON", params);
	}

	public boolean kiemTraXoaCardGpon(Integer cardgp_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cardgp_id", cardgp_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_rs", java.sql.Types.REF_CURSOR, int.class));
		ArrayList<?> result = dbContext.executeSpWithCursorToEntities(
				"ECMS.PKG_QL_THIETBI.SP_COUNT_DS_PORT_BY_CARD_GPON",
				params);
		if (!(result == null || result.size() == 0)) {
			return Integer.parseInt(result.get(0).toString()) > 0;
		}
		return false;
	}

	public int kiemTraPortCardGponDangSuDung(Integer cardgp_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cardgp_id", cardgp_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_rs", java.sql.Types.REF_CURSOR, int.class));
		ArrayList<?> result = dbContext.executeSpWithCursorToEntities(
				"ECMS.PKG_QL_THIETBI.SP_COUNT_DS_PORT_SUDUNG_BY_CARD_GPON",
				params);
		return Integer.parseInt(result.get(0).toString());
	}

	// Mane

	public Object getThongTinCardMane(int id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cardmane_id", id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_rs", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToMap("ECMS.PKG_QL_THIETBI.SP_GET_CARD_MANE", params);
	}

	public Object themThongTinCardMane(Integer dslam_id, Integer loaicard_id, Integer vitri, String ten_card,
			String serial, String partnumber) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_dslam_id", dslam_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_loaicard_id", loaicard_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_vitri", vitri, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_serial", serial, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_ten_card", ten_card, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_partnumber", partnumber, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_rs", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToMap("ECMS.PKG_QL_THIETBI.SP_INSERT_CARD_MANE", params);
	}

	public boolean capNhatThongTinCardMane(Integer cardmane_id, Integer dslam_id, Integer loaicard_id, Integer vitri,
			String ten_card, String serial, String partnumber) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cardmane_id", cardmane_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_dslam_id", dslam_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_loaicard_id", loaicard_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_vitri", vitri, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_serial", serial, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_ten_card", ten_card, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_partnumber", partnumber, java.sql.Types.NVARCHAR));
		dbContext.executeStoredProcedure("ECMS.PKG_QL_THIETBI.SP_UPDATE_CARD_MANE", params);
		return true;
	}

	public boolean xoaThongTinCardMane(Integer cardmane_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cardmane_id", cardmane_id, java.sql.Types.INTEGER));
		dbContext.executeStoredProcedure("ECMS.PKG_QL_THIETBI.SP_DELETE_CARD_MANE", params);
		return true;
	}

	public Object getDsPortCardMane(Integer cardmane_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cardmane_id", cardmane_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_rs", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QL_THIETBI.SP_GET_DS_PORT_BY_CARD_MANE", params);
	}

	public int kiemTraPortCardManeDangSuDung(Integer cardmane_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cardmane_id", cardmane_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_rs", java.sql.Types.REF_CURSOR, int.class));
		ArrayList<?> result = dbContext.executeSpWithCursorToEntities(
				"ECMS.PKG_QL_THIETBI.SP_COUNT_DS_PORT_SUDUNG_BY_CARD_MANE",
				params);
		return Integer.parseInt(result.get(0).toString());
	}

	public Object getCayDanhMucThietBiV2(Integer donViID, Integer kieuTu) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_donvi_id", donViID, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_kieu_tu", kieuTu, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QL_THIETBI.SP_GET_CAY_DM_TBI_V2", params);
	}

	public Object getCayDanhMucDonViV2(Integer kieuTu) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_kieu_tu", kieuTu, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QL_THIETBI.SP_GET_CAY_DONVI_V2", params);
	}

	public Object timKiemCayDanhMucThietBiV2(Integer kieuTu, String text) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_text", text, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_kieu_tu", kieuTu, java.sql.Types.INTEGER));
		SqlParameter p_out_thietbi_id = new SqlParameter("p_thietbi_id", java.sql.Types.NVARCHAR);
		params.add(p_out_thietbi_id);
		SqlParameter p_out_turack_id = new SqlParameter("p_turack_id", java.sql.Types.INTEGER);
		params.add(p_out_turack_id);
		SqlParameter p_out_donvi_id = new SqlParameter("p_donvi_id", java.sql.Types.INTEGER);
		params.add(p_out_donvi_id);
		SqlParameter p_out_type = new SqlParameter("p_type", java.sql.Types.NVARCHAR);
		params.add(p_out_type);
		SqlParameter p_out_message = new SqlParameter("p_message", java.sql.Types.NVARCHAR);
		params.add(p_out_message);
		dbContext.executeStoredProcedure("ECMS.PKG_QL_THIETBI.SP_TIMKIEM_CAY_TBI_V2", params);
		String msg = (String) p_out_message.getOutValue();
		Map<String, Object> result = new java.util.HashMap<>();
		result.put("result", (msg == null || msg.equals("")));
		result.put("message", msg);
		if (msg == null || msg.equals("")) {
			Map<String, Object> resultDetail = new java.util.HashMap<>();
			resultDetail.put("thietBiID", p_out_thietbi_id.getOutValue());
			resultDetail.put("tuRackID", p_out_turack_id.getOutValue());
			resultDetail.put("donViID", p_out_donvi_id.getOutValue());
			resultDetail.put("type", p_out_type.getOutValue());
			result.put("detail", resultDetail);
		}
		return result;
	}

	public Object kiemTraTinhNangThietBiL2SW() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_rs", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QL_THIETBI.SP_KT_TINHNANG_KT_TBI_L2SW", params);
	}
}
