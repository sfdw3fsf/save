package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotEmpty;

import vn.vnpt.message.ModelValidation;

public class EncryptionDtoInput {
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String content;
	
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
