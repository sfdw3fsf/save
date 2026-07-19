package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class QLTB_BrasInsertInput {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String tenbras = null;
    private Integer caidat_md = null;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String ip = "";
    private Integer bras_vdc_id = null;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String so_slot = "";
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String so_subslot = "";
    private Integer used = null;
    private Integer slot_bd = null;
    private Integer subslot_bd = null;
    private Integer turack_id = null;
	@NotNull(message = ModelValidation.NOT_NULL) 
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer slot_visa = null;
	@NotNull(message = ModelValidation.NOT_NULL) 
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer port_visa = null;
    private Integer slotbd = null;
    private Integer so_port = null;
    private Integer portbd = null;
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
}
