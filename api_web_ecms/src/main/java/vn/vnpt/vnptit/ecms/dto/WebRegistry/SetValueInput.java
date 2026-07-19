package vn.vnpt.vnptit.ecms.dto.WebRegistry;

import javax.validation.constraints.NotEmpty;

import vn.vnpt.message.ModelValidation;

public class SetValueInput extends GetValueInput {
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
