package vn.vnpt.vnptit.qlsc.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;
import vn.vnpt.validator.StringDateTimeConstraint;

public class GetMaSuCoInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer nguonSuCoID = null;

	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	@StringDateTimeConstraint(isShortDate = true, message = ModelValidation.YYYYMMDD_INVALID)
	private String ngay = null;

	public Integer getNguonSuCoID() {
		return nguonSuCoID;
	}

	public void setNguonSuCoID(Integer nguonSuCoID) {
		this.nguonSuCoID = nguonSuCoID;
	}

	public String getNgay() {
		return ngay;
	}

	public void setNgay(String ngay) {
		this.ngay = ngay;
	}
}
