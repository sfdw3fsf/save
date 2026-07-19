package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonProperty;


public class GoiDichVuIOPSDto {

	@JsonProperty("iops_id")
	private Long iopsId;

	@JsonProperty("ten")
	private String ten;

	@JsonProperty("tentat")
	private String tenTat;

	@JsonProperty("mota")
	private String moTa;

	@JsonProperty("ghichu")
	private String ghiChu;

	@JsonProperty("sudung")
	private Integer suDung;
	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public Long getIopsId() {
		return iopsId;
	}

	public void setIopsId(Long iopsId) {
		this.iopsId = iopsId;
	}

	public String getTenTat() {
		return tenTat;
	}

	public void setTenTat(String tenTat) {
		this.tenTat = tenTat;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public Integer getSuDung() {
		return suDung;
	}

	public void setSuDung(Integer suDung) {
		this.suDung = suDung;
	}
}
