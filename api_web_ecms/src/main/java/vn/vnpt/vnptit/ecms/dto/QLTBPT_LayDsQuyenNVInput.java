package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class QLTBPT_LayDsQuyenNVInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer nhanVienID = 0;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer donViID = 0;
	public Integer getNhanVienID() {
		return nhanVienID;
	}
	public void setNhanVienID(Integer nhanVienID) {
		this.nhanVienID = nhanVienID;
	}
	public Integer getDonViID() {
		return donViID;
	}
	public void setDonViID(Integer donViID) {
		this.donViID = donViID;
	}
}
