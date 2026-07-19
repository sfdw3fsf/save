package vn.vnpt.vnptit.qlsc.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class LayDsSuCoTamInput {
	private String maSuCo = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer nguonSuCoID = null;

	public String getMaSuCo() {
		return maSuCo;
	}

	public void setMaSuCo(String maSuCo) {
		this.maSuCo = maSuCo;
	}

	public Integer getNguonSuCoID() {
		return nguonSuCoID;
	}

	public void setNguonSuCoID(Integer nguonSuCoID) {
		this.nguonSuCoID = nguonSuCoID;
	}
}
