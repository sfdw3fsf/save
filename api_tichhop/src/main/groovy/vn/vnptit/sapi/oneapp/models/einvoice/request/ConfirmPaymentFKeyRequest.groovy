package vn.vnptit.sapi.oneapp.models.einvoice.request

import com.fasterxml.jackson.annotation.JsonProperty

public class ConfirmPaymentFKeyRequest {

	@JsonProperty("pattern")
	private String pattern
	@JsonProperty("serial")
	private String serial
	@JsonProperty("fKey")
	private String fKey

	String getPattern() {
		return pattern
	}

	void setPattern(String pattern) {
		this.pattern = pattern
	}

	String getSerial() {
		return serial
	}

	void setSerial(String serial) {
		this.serial = serial
	}

	String getfKey() {
		return fKey
	}

	void setfKey(String fKey) {
		this.fKey = fKey
	}
}
