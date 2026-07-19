package vn.vnpt.vnptit.qlsc.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;
import vn.vnpt.validator.StringDateTimeConstraint;

public class CapNhatThongTinSuCoInput {
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	private String maSuCo = null;
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	@StringDateTimeConstraint(message = ModelValidation.YYYYMMDDHHMMSS_INVALID)
    private String ngaySuCo = null;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String noiDungSuCo = null;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
	@StringDateTimeConstraint(message = ModelValidation.YYYYMMDDHHMMSS_INVALID)
    private String ngayBH = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Number donViBH = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Number nhanVienBH = null;
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String dienThoaiLH = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Number donViTN = null;
    private Number nhanVienTN = null;
	@StringDateTimeConstraint(message = ModelValidation.YYYYMMDDHHMMSS_INVALID)
    private String henSuaTu = null;
	@StringDateTimeConstraint(message = ModelValidation.YYYYMMDDHHMMSS_INVALID)
    private String henSuaDen = null;
	public String getMaSuCo() {
		return maSuCo;
	}
	public void setMaSuCo(String maSuCo) {
		this.maSuCo = maSuCo;
	}
	public String getNgaySuCo() {
		return ngaySuCo;
	}
	public void setNgaySuCo(String ngaySuCo) {
		this.ngaySuCo = ngaySuCo;
	}
	public String getNoiDungSuCo() {
		return noiDungSuCo;
	}
	public void setNoiDungSuCo(String noiDungSuCo) {
		this.noiDungSuCo = noiDungSuCo;
	}
	public String getNgayBH() {
		return ngayBH;
	}
	public void setNgayBH(String ngayBH) {
		this.ngayBH = ngayBH;
	}
	public Number getDonViBH() {
		return donViBH;
	}
	public void setDonViBH(Number donViBH) {
		this.donViBH = donViBH;
	}
	public Number getNhanVienBH() {
		return nhanVienBH;
	}
	public void setNhanVienBH(Number nhanVienBH) {
		this.nhanVienBH = nhanVienBH;
	}
	public String getDienThoaiLH() {
		return dienThoaiLH;
	}
	public void setDienThoaiLH(String dienThoaiLH) {
		this.dienThoaiLH = dienThoaiLH;
	}
	public Number getDonViTN() {
		return donViTN;
	}
	public void setDonViTN(Number donViTN) {
		this.donViTN = donViTN;
	}
	public Number getNhanVienTN() {
		return nhanVienTN;
	}
	public void setNhanVienTN(Number nhanVienTN) {
		this.nhanVienTN = nhanVienTN;
	}
	public String getHenSuaTu() {
		return henSuaTu;
	}
	public void setHenSuaTu(String henSuaTu) {
		this.henSuaTu = henSuaTu;
	}
	public String getHenSuaDen() {
		return henSuaDen;
	}
	public void setHenSuaDen(String henSuaDen) {
		this.henSuaDen = henSuaDen;
	}
}
