package vn.vnpt.vnptit.qlsc.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class LayDsSuCoDPNInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer nguonSuCoID = null;

	public Integer getNguonSuCoID() {
		return nguonSuCoID;
	}

	public void setNguonSuCoID(Integer nguonSuCoID) {
		this.nguonSuCoID = nguonSuCoID;
	}
}
