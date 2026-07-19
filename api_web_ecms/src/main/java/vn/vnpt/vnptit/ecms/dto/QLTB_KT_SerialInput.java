package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotEmpty;

import vn.vnpt.message.ModelValidation;

public class QLTB_KT_SerialInput {
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	private String serial = null;

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
}
