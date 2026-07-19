package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class QLTBPT_CapNhatDiaChiThietBiInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer thietBiID = 0;
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer diaChiID = 0;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer tinhID = 0;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer quanID = 0;
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer phuongID = 0;
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer phoID = 0;
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer khuID = 0;
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer apID = 0;
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer dacDiemID = 0;
	private String soNha = null;
	private String diaChi = null;
	private float kinhDo = 0;
	private float viDo = 0;
	public Integer getThietBiID() {
		return thietBiID;
	}
	public void setThietBiID(Integer thietBiID) {
		this.thietBiID = thietBiID;
	}
	public Integer getDiaChiID() {
		return diaChiID;
	}
	public void setDiaChiID(Integer diaChiID) {
		this.diaChiID = diaChiID;
	}
	public Integer getTinhID() {
		return tinhID;
	}
	public void setTinhID(Integer tinhID) {
		this.tinhID = tinhID;
	}
	public Integer getQuanID() {
		return quanID;
	}
	public void setQuanID(Integer quanID) {
		this.quanID = quanID;
	}
	public Integer getPhuongID() {
		return phuongID;
	}
	public void setPhuongID(Integer phuongID) {
		this.phuongID = phuongID;
	}
	public Integer getPhoID() {
		return phoID;
	}
	public void setPhoID(Integer phoID) {
		this.phoID = phoID;
	}
	public Integer getKhuID() {
		return khuID;
	}
	public void setKhuID(Integer khuID) {
		this.khuID = khuID;
	}
	public Integer getApID() {
		return apID;
	}
	public void setApID(Integer apID) {
		this.apID = apID;
	}
	public Integer getDacDiemID() {
		return dacDiemID;
	}
	public void setDacDiemID(Integer dacDiemID) {
		this.dacDiemID = dacDiemID;
	}
	public String getSoNha() {
		return soNha;
	}
	public void setSoNha(String soNha) {
		this.soNha = soNha;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public float getKinhDo() {
		return kinhDo;
	}
	public void setKinhDo(float kinhDo) {
		this.kinhDo = kinhDo;
	}
	public float getViDo() {
		return viDo;
	}
	public void setViDo(float viDo) {
		this.viDo = viDo;
	}
}
