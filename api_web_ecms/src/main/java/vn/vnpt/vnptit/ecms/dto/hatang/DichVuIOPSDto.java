package vn.vnpt.vnptit.ecms.dto.hatang;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class DichVuIOPSDto {

	private Number id;
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	private String ten;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number giaTri;
	private String ghiChu;
	private Number hieuLuc;
	
	public Number getId() {
		return id;
	}
	public void setId(Number id) {
		this.id = id;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public Number getGiaTri() {
		return giaTri;
	}
	public void setGiaTri(Number giaTri) {
		this.giaTri = giaTri;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	public Number getHieuLuc() {
		return hieuLuc;
	}
	public void setHieuLuc(Number hieuLuc) {
		this.hieuLuc = hieuLuc;
	}
	
}
