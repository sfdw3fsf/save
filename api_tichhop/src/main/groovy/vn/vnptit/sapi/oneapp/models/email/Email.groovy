package vn.vnptit.sapi.oneapp.models.email

import com.fasterxml.jackson.annotation.JsonProperty

class Email {
	@JsonProperty("subject")
	private String subject
	@JsonProperty("message")
	private String message
	@JsonProperty("mailTo")
	private String mailTo
	@JsonProperty("mailCc")
	private String mailCc
	@JsonProperty("mailBcc")
	private String mailBcc
	@JsonProperty("file")
	private String file
	@JsonProperty("fileName")
	private String fileName
	@JsonProperty("contentType")
	private String contentType

	String getContentType() {
		return contentType
	}

	void setContentType(String contentType) {
		this.contentType = contentType
	}

	String getSubject() {
		return subject
	}

	void setSubject(String subject) {
		this.subject = subject
	}

	String getMessage() {
		return message
	}

	void setMessage(String message) {
		this.message = message
	}

	String getMailTo() {
		return mailTo
	}

	void setMailTo(String mailTo) {
		this.mailTo = mailTo
	}

	String getMailCc() {
		return mailCc
	}

	void setMailCc(String mailCc) {
		this.mailCc = mailCc
	}

	String getMailBcc() {
		return mailBcc
	}

	void setMailBcc(String mailBcc) {
		this.mailBcc = mailBcc
	}

	String getFile() {
		return file
	}

	void setFile(String file) {
		this.file = file
	}

	String getFileName() {
		return fileName
	}

	void setFileName(String fileName) {
		this.fileName = fileName
	}
}
