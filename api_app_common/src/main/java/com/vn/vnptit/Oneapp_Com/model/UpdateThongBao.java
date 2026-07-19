package com.vn.vnptit.Oneapp_Com.model;

public class UpdateThongBao {
	private Integer tinh;
	private Number id_nv;
	private Number id_tb;
	private String tg;
	private Number loi;
	
	public Integer getTinh() {
		return tinh;
	}
	public void setTinh(Integer tinh) {
		this.tinh = tinh;
	}
	public Number getId_nv() {
		return id_nv;
	}
	public void setId_nv(Number id_nv) {
		this.id_nv = id_nv;
	}
	public Number getId_tb() {
		return id_tb;
	}
	public void setId_tb(Number id_tb) {
		this.id_tb = id_tb;
	}
	public String getTg() {
		return tg;
	}
	public void setTg(String tg) {
		this.tg = tg;
	}
	
	public Number getLoi() {
		return loi;
	}
	public void setLoi(Number loi) {
		this.loi = loi;
	}
	public UpdateThongBao(Integer tinh, Number id_nv, Number id_tb, String tg, Number loi) {
		super();
		this.tinh = tinh;
		this.id_nv = id_nv;
		this.id_tb = id_tb;
		this.tg = tg;
		this.loi = loi;
	}	
	
}
