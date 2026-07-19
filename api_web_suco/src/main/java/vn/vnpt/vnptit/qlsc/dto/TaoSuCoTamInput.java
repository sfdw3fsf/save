package vn.vnpt.vnptit.qlsc.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;
import vn.vnpt.validator.StringDateTimeConstraint;

public class TaoSuCoTamInput extends CapNhatSuCoTamInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number nguonSuCoID = null;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
	@StringDateTimeConstraint(message = ModelValidation.YYYYMMDDHHMMSS_INVALID)
    private String ngayTN = null;

	public Number getNguonSuCoID() {
		return nguonSuCoID;
	}

	public void setNguonSuCoID(Number nguonSuCoID) {
		this.nguonSuCoID = nguonSuCoID;
	}

	public String getNgayTN() {
		return ngayTN;
	}

	public void setNgayTN(String ngayTN) {
		this.ngayTN = ngayTN;
	}

}
