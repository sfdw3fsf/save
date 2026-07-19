package vn.vnpt.vnptit.qlsc.dto;

import javax.validation.constraints.NotEmpty;

import vn.vnpt.message.ModelValidation;

public class StorageInput {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String fileSource = null;

	public String getFileSource() {
		return fileSource;
	}

	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}
}
