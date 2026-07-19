package vn.vnpt.vnptit.qlsc.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class KiemTraDieuKienGiaoPhieuInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer donViNguonID = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer donViDichID = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer nhomSuCoID = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer thuTuID = null;

	public Integer getDonViNguonID() {
		return donViNguonID;
	}

	public void setDonViNguonID(Integer donViNguonID) {
		this.donViNguonID = donViNguonID;
	}

	public Integer getDonViDichID() {
		return donViDichID;
	}

	public void setDonViDichID(Integer donViDichID) {
		this.donViDichID = donViDichID;
	}

	public Integer getNhomSuCoID() {
		return nhomSuCoID;
	}

	public void setNhomSuCoID(Integer nhomSuCoID) {
		this.nhomSuCoID = nhomSuCoID;
	}

	public Integer getThuTuID() {
		return thuTuID;
	}

	public void setThuTuID(Integer thuTuID) {
		this.thuTuID = thuTuID;
	}
}
