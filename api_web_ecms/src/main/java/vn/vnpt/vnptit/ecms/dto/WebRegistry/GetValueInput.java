package vn.vnpt.vnptit.ecms.dto.WebRegistry;

import javax.validation.constraints.NotEmpty;

import vn.vnpt.message.ModelValidation;

public class GetValueInput {
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String keyName;

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

}
