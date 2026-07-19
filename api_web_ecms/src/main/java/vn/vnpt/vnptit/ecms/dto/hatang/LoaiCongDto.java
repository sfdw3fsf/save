package vn.vnpt.vnptit.ecms.dto.hatang;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import vn.vnpt.message.ModelValidation;

public class LoaiCongDto {

	private Number id;
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	@Size(max = 100, message = "Thông tin tên loại cổng có độ dài không hợp lệ")
	private String ten;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number phanLoai;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number thuocNhom;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number camModule;
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	@Size(max = 50, message = "Thông tin tốc độ PORT có độ dài không hợp lệ")
	private String tocDoPort;
	@Size(max = 500, message = "Thông tin ghi chú có độ dài không hợp lệ")
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
	public Number getPhanLoai() {
		return phanLoai;
	}
	public void setPhanLoai(Number phanLoai) {
		this.phanLoai = phanLoai;
	}
	public Number getThuocNhom() {
		return thuocNhom;
	}
	public void setThuocNhom(Number thuocNhom) {
		this.thuocNhom = thuocNhom;
	}
	public Number getCamModule() {
		return camModule;
	}
	public void setCamModule(Number camModule) {
		this.camModule = camModule;
	}
	public String getTocDoPort() {
		return tocDoPort;
	}
	public void setTocDoPort(String tocDoPort) {
		this.tocDoPort = tocDoPort;
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
