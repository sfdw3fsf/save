package vn.vnpt.vnptit.qlsc.dto.cntt;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;
import vn.vnpt.validator.StringDateTimeConstraint;

public class TiepNhanSuCoCnttInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number nguonSuCoID = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number nhomSuCoID = null;
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	@StringDateTimeConstraint(message = ModelValidation.YYYYMMDDHHMMSS_INVALID)
	private String ngayBD = null;
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	private String ghiChuHong = null;
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	@StringDateTimeConstraint(message = ModelValidation.YYYYMMDDHHMMSS_INVALID)
	private String ngayYeuCau = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number donViYeuCauID = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number nhanVienYeuCauID = null;
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	private String dienThoaiLH = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number donViID = null;
	private Number nhanVienID = null;
	@StringDateTimeConstraint(message = ModelValidation.YYYYMMDDHHMMSS_INVALID)
	private String henSuaTu = null;
	@StringDateTimeConstraint(message = ModelValidation.YYYYMMDDHHMMSS_INVALID)
	private String henSuaDen = null;
	private String tinhTrang = null;
	private String anhChup = null;
	private Integer mucDoSuCoId = null;
	private Integer quyMoSuCoId;

	public Integer getQuyMoSuCoId() {
		return quyMoSuCoId;
	}

	public Number getNguonSuCoID() {
		return nguonSuCoID;
	}

	public void setNguonSuCoID(Number nguonSuCoID) {
		this.nguonSuCoID = nguonSuCoID;
	}

	public Number getNhomSuCoID() {
		return nhomSuCoID;
	}

	public void setNhomSuCoID(Number nhomSuCoID) {
		this.nhomSuCoID = nhomSuCoID;
	}

	public String getNgayBD() {
		return ngayBD;
	}

	public void setNgayBD(String ngayBD) {
		this.ngayBD = ngayBD;
	}

	public String getGhiChuHong() {
		return ghiChuHong;
	}

	public void setGhiChuHong(String ghiChuHong) {
		this.ghiChuHong = ghiChuHong;
	}

	public String getNgayYeuCau() {
		return ngayYeuCau;
	}

	public void setNgayYeuCau(String ngayYeuCau) {
		this.ngayYeuCau = ngayYeuCau;
	}

	public Number getDonViYeuCauID() {
		return donViYeuCauID;
	}

	public void setDonViYeuCauID(Number donViYeuCauID) {
		this.donViYeuCauID = donViYeuCauID;
	}

	public Number getNhanVienYeuCauID() {
		return nhanVienYeuCauID;
	}

	public void setNhanVienYeuCauID(Number nhanVienYeuCauID) {
		this.nhanVienYeuCauID = nhanVienYeuCauID;
	}

	public String getDienThoaiLH() {
		return dienThoaiLH;
	}

	public void setDienThoaiLH(String dienThoaiLH) {
		this.dienThoaiLH = dienThoaiLH;
	}

	public Number getDonViID() {
		return donViID;
	}

	public void setDonViID(Number donViID) {
		this.donViID = donViID;
	}

	public Number getNhanVienID() {
		return nhanVienID;
	}

	public void setNhanVienID(Number nhanVienID) {
		this.nhanVienID = nhanVienID;
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

	public String getTinhTrang() {
		return tinhTrang;
	}

	public void setTinhTrang(String tinhTrang) {
		this.tinhTrang = tinhTrang;
	}

	public String getAnhChup() {
		return anhChup;
	}

	public void setAnhChup(String anhChup) {
		this.anhChup = anhChup;
	}

	public Integer getMucDoSuCoId() {
		return mucDoSuCoId;
	}

	public void setMucDoSuCoId(Integer mucDoSuCoId) {
		this.mucDoSuCoId = mucDoSuCoId;
	}

}
