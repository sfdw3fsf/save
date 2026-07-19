package vn.vnpt.vnptit.ecms.dto.hatang;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;


public class TrangThaiMayAoInDto {
    private Number id;
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	private String ten;
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

	public TrangThaiMayAoInDto() {
	}

	public TrangThaiMayAoInDto(Number id, String ten, String ghiChu, Number hieuLuc) {
		this.id = id;
		this.ten = ten;
		this.ghiChu = ghiChu;
		this.hieuLuc = hieuLuc;
	}
}
