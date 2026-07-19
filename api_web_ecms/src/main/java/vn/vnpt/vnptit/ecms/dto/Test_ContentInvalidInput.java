package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotEmpty;

import vn.vnpt.message.ModelValidation;

public class Test_ContentInvalidInput {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String content = null;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
