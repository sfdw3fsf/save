package vn.vnptit.sapi.oneapp.models.email

import com.fasterxml.jackson.annotation.JsonProperty

class VerifyEmail {
	@JsonProperty("email")
	private String email
	@JsonProperty("type")
	private String type

	String getEmail() {
		return email
	}

	void setEmail(String email) {
		this.email = email
	}

	String getType() {
		return type
	}

	void setType(String type) {
		this.type = type
	}
}
