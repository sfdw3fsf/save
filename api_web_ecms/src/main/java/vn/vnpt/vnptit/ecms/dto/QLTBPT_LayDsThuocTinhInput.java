package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class QLTBPT_LayDsThuocTinhInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer vatTuID = 0;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer thietBiID = 0;
	public Integer getVatTuID() {
		return vatTuID;
	}
	public void setVatTuID(Integer vatTuID) {
		this.vatTuID = vatTuID;
	}
	public Integer getThietBiID() {
		return thietBiID;
	}
	public void setThietBiID(Integer thietBiID) {
		this.thietBiID = thietBiID;
	}
}
