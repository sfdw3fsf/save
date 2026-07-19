package vn.vnpt.vnptit.qlsc.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class CapNhatSuCoTamInput extends CapNhatThongTinSuCoInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number nhomSuCoID = null;
	private String anhChup = null;
	public Number getNhomSuCoID() {
		return nhomSuCoID;
	}
	public void setNhomSuCoID(Number nhomSuCoID) {
		this.nhomSuCoID = nhomSuCoID;
	}
	public String getAnhChup() {
		return anhChup;
	}
	public void setAnhChup(String anhChup) {
		this.anhChup = anhChup;
	}
}
