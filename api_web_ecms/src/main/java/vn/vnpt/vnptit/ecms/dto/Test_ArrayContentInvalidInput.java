package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotEmpty;

import vn.vnpt.message.ModelValidation;

public class Test_ArrayContentInvalidInput {
    @NotEmpty(message = ModelValidation.NOT_NULL)
    private String[] content = null;

	public String[] getContent() {
		return content;
	}

	public void setContent(String[] content) {
		this.content = content;
	}
}
