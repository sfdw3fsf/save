package vn.vnpt.vnptit.qlsc.dto;

import javax.validation.constraints.NotEmpty;

import vn.vnpt.message.ModelValidation;

public class XoaSuCoTamInput {
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	private String maSuCo = null;

	public String getMaSuCo() {
		return maSuCo;
	}

	public void setMaSuCo(String maSuCo) {
		this.maSuCo = maSuCo;
	}

}
