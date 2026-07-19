package vn.vnpt.vnptit.ecms.model;

import java.util.ArrayList;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.QLTB_BrasInput;
import vn.vnpt.vnptit.ecms.dto.QLTB_BrasInsertInput;

public class CssBrasModel {
	private int phanvung_id = 0;
	private int bras_id = 0;
	private String tenbras = null;
	private Integer caidat_md = null;
	private String ip = null;
	private Integer bras_vdc_id = null;
	private String so_slot = null;
	private String so_subslot = null;
	private Integer used = null;
	private Integer slot_bd = null;
	private Integer subslot_bd = null;
	private Integer turack_id = null;
	private Integer slot_visa = null;
	private Integer port_visa = null;
	private Integer slotbd = null;
	private Integer so_port = null;
	private Integer portbd = null;
	private Integer donvi_id = null;

	public int getPhanvung_id() {
		return phanvung_id;
	}

	public void setPhanvung_id(int phanvung_id) {
		this.phanvung_id = phanvung_id;
	}

	public int getBras_id() {
		return bras_id;
	}

	public void setBras_id(int bras_id) {
		this.bras_id = bras_id;
	}

	public String getTenbras() {
		return tenbras;
	}

	public void setTenbras(String tenbras) {
		this.tenbras = tenbras;
	}

	public Integer getCaidat_md() {
		return caidat_md;
	}

	public void setCaidat_md(Integer caidat_md) {
		this.caidat_md = caidat_md;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getBras_vdc_id() {
		return bras_vdc_id;
	}

	public void setBras_vdc_id(Integer bras_vdc_id) {
		this.bras_vdc_id = bras_vdc_id;
	}

	public String getSo_slot() {
		return so_slot;
	}

	public void setSo_slot(String so_slot) {
		this.so_slot = so_slot;
	}

	public String getSo_subslot() {
		return so_subslot;
	}

	public void setSo_subslot(String so_subslot) {
		this.so_subslot = so_subslot;
	}

	public Integer getUsed() {
		return used;
	}

	public void setUsed(Integer used) {
		this.used = used;
	}

	public Integer getSlot_bd() {
		return slot_bd;
	}

	public void setSlot_bd(Integer slot_bd) {
		this.slot_bd = slot_bd;
	}

	public Integer getSubslot_bd() {
		return subslot_bd;
	}

	public void setSubslot_bd(Integer subslot_bd) {
		this.subslot_bd = subslot_bd;
	}

	public Integer getTurack_id() {
		return turack_id;
	}

	public void setTurack_id(Integer turack_id) {
		this.turack_id = turack_id;
	}

	public Integer getSlot_visa() {
		return slot_visa;
	}

	public void setSlot_visa(Integer slot_visa) {
		this.slot_visa = slot_visa;
	}

	public Integer getPort_visa() {
		return port_visa;
	}

	public void setPort_visa(Integer port_visa) {
		this.port_visa = port_visa;
	}

	public Integer getSlotbd() {
		return slotbd;
	}

	public void setSlotbd(Integer slotbd) {
		this.slotbd = slotbd;
	}

	public Integer getSo_port() {
		return so_port;
	}

	public void setSo_port(Integer so_port) {
		this.so_port = so_port;
	}

	public Integer getPortbd() {
		return portbd;
	}

	public void setPortbd(Integer portbd) {
		this.portbd = portbd;
	}

	public Integer getDonvi_id() {
		return donvi_id;
	}

	public void setDonvi_id(Integer donvi_id) {
		this.donvi_id = donvi_id;
	}

	public boolean Load(QLTB_BrasInsertInput item) {
		if (item != null) {
			this.setTenbras(item.getTenbras());
			this.setCaidat_md(item.getCaidat_md());
			this.setIp(item.getIp());
			this.setBras_vdc_id(item.getBras_vdc_id());
			this.setSo_slot(item.getSo_slot());
			this.setSo_subslot(item.getSo_subslot());
			this.setUsed(item.getUsed());
			this.setSlot_bd(item.getSlot_bd());
			this.setSubslot_bd(item.getSubslot_bd());
			this.setTurack_id(item.getTurack_id());
			this.setSlot_visa(item.getSlot_visa());
			this.setPort_visa(item.getPort_visa());
			this.setSlotbd(item.getSlotbd());
			this.setSo_port(item.getSo_port());
			this.setPortbd(item.getPortbd());
			
			this.setPhanvung_id(AppRequestContext.getCurrent().getPhanVungID());
			return true;
		}
		return false;
	}

	public boolean Load(QLTB_BrasInput item) {
		if (item != null) {
			this.Load((QLTB_BrasInsertInput)item);
			this.setPhanvung_id(item.getPhanvung_id());
			this.setBras_id(item.getBras_id());
			return true;
		}
		return false;
	}

	public boolean Load(DbContext dbContext) throws AppSqlException {
		CssBrasModel item = this.Get(dbContext);
		if (item != null) {
			this.setPhanvung_id(item.getPhanvung_id());
			this.setBras_id(item.getBras_id());
			this.setTenbras(item.getTenbras());
			this.setCaidat_md(item.getCaidat_md());
			this.setIp(item.getIp());
			this.setBras_vdc_id(item.getBras_vdc_id());
			this.setSo_slot(item.getSo_slot());
			this.setSo_subslot(item.getSo_subslot());
			this.setUsed(item.getUsed());
			this.setSlot_bd(item.getSlot_bd());
			this.setSubslot_bd(item.getSubslot_bd());
			this.setTurack_id(item.getTurack_id());
			this.setSlot_visa(item.getSlot_visa());
			this.setPort_visa(item.getPort_visa());
			this.setSlotbd(item.getSlotbd());
			this.setSo_port(item.getSo_port());
			this.setPortbd(item.getPortbd());
			this.setDonvi_id(item.getDonvi_id());
			return true;
		}
		return false;
	}

	public boolean Insert(DbContext dbContext) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", this.getPhanvung_id(), ParameterDirection.INPUT,
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_tenbras", this.getTenbras(), ParameterDirection.INPUT, java.sql.Types.NVARCHAR));
		params.add(
				new SqlParameter("p_caidat_md", this.getCaidat_md(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_ip", this.getIp(), ParameterDirection.INPUT, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_bras_vdc_id", this.getBras_vdc_id(), ParameterDirection.INPUT,
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_so_slot", this.getSo_slot(), ParameterDirection.INPUT, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_so_subslot", this.getSo_subslot(), ParameterDirection.INPUT,
				java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_used", this.getUsed(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_slot_bd", this.getSlot_bd(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_subslot_bd", this.getSubslot_bd(), ParameterDirection.INPUT,
				java.sql.Types.INTEGER));
		params.add(
				new SqlParameter("p_turack_id", this.getTurack_id(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(
				new SqlParameter("p_slot_visa", this.getSlot_visa(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(
				new SqlParameter("p_port_visa", this.getPort_visa(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_slotbd", this.getSlotbd(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_so_port", this.getSo_port(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_portbd", this.getPortbd(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR, int.class));
		ArrayList<?> result = dbContext.executeSpWithCursorToEntities("ECMS.PKG_QL_THIETBI.SP_BRAS_INSERT",
				params);
		if (!(result == null || result.size() == 0)) {
			this.setBras_id(Integer.parseInt(result.get(0).toString()));
			return true;
		}
		return false;
	}
	public boolean InsertNew(DbContext dbContext) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", this.getPhanvung_id(), ParameterDirection.INPUT,
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_bras_id", this.getBras_id(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_tenbras", this.getTenbras(), ParameterDirection.INPUT, java.sql.Types.NVARCHAR));
		params.add(
				new SqlParameter("p_caidat_md", this.getCaidat_md(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_ip", this.getIp(), ParameterDirection.INPUT, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_bras_vdc_id", this.getBras_vdc_id(), ParameterDirection.INPUT,
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_so_slot", this.getSo_slot(), ParameterDirection.INPUT, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_so_subslot", this.getSo_subslot(), ParameterDirection.INPUT,
				java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_used", this.getUsed(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_slot_bd", this.getSlot_bd(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_subslot_bd", this.getSubslot_bd(), ParameterDirection.INPUT,
				java.sql.Types.INTEGER));
		params.add(
				new SqlParameter("p_turack_id", this.getTurack_id(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(
				new SqlParameter("p_slot_visa", this.getSlot_visa(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(
				new SqlParameter("p_port_visa", this.getPort_visa(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_slotbd", this.getSlotbd(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_so_port", this.getSo_port(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_portbd", this.getPortbd(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR, int.class));
		ArrayList<?> result = dbContext.executeSpWithCursorToEntities("ECMS.PKG_QL_THIETBI.SP_BRAS_NEW_INSERT",
				params);
		if (!(result == null || result.size() == 0)) {
			this.setBras_id(Integer.parseInt(result.get(0).toString()));
			return true;
		}
		return false;
	}
	public boolean Update(DbContext dbContext) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", this.getPhanvung_id(), ParameterDirection.INPUT,
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_bras_id", this.getBras_id(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_tenbras", this.getTenbras(), ParameterDirection.INPUT, java.sql.Types.NVARCHAR));
		params.add(
				new SqlParameter("p_caidat_md", this.getCaidat_md(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_ip", this.getIp(), ParameterDirection.INPUT, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_bras_vdc_id", this.getBras_vdc_id(), ParameterDirection.INPUT,
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_so_slot", this.getSo_slot(), ParameterDirection.INPUT, java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_so_subslot", this.getSo_subslot(), ParameterDirection.INPUT,
				java.sql.Types.NVARCHAR));
		params.add(new SqlParameter("p_used", this.getUsed(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_slot_bd", this.getSlot_bd(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_subslot_bd", this.getSubslot_bd(), ParameterDirection.INPUT,
				java.sql.Types.INTEGER));
		params.add(
				new SqlParameter("p_turack_id", this.getTurack_id(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(
				new SqlParameter("p_slot_visa", this.getSlot_visa(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(
				new SqlParameter("p_port_visa", this.getPort_visa(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_slotbd", this.getSlotbd(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_so_port", this.getSo_port(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_portbd", this.getPortbd(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR, int.class));
		ArrayList<?> result = dbContext.executeSpWithCursorToEntities("ECMS.PKG_QL_THIETBI.SP_BRAS_UPDATE",
				params);
		if (!(result == null || result.size() == 0)) {
			return Integer.parseInt(result.get(0).toString()) > 0;
		}
		return false;
	}

	public boolean Delete(DbContext dbContext) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", this.getPhanvung_id(), ParameterDirection.INPUT,
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_bras_id", this.getBras_id(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR, int.class));
		ArrayList<?> result = dbContext.executeSpWithCursorToEntities("ECMS.PKG_QL_THIETBI.SP_BRAS_DELETE",
				params);
		if (!(result == null || result.size() == 0)) {
			return Integer.parseInt(result.get(0).toString()) > 0;
		}
		return false;
	}

	public CssBrasModel Get(DbContext dbContext) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", this.getPhanvung_id(), ParameterDirection.INPUT,
				java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_bras_id", this.getBras_id(), ParameterDirection.INPUT, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_cursor", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR, new CssBrasMapper()));
		ArrayList<?> result = dbContext.executeSpWithCursorToEntities("ECMS.PKG_QL_THIETBI.SP_BRAS_GET", params);
		if(!(result == null || result.size() == 0))
			return (CssBrasModel) result.get(0);
		return null;
	}

}
