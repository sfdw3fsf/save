package vn.vnptit.sapi.oneapp.models.einvoice.request

import com.fasterxml.jackson.annotation.JsonProperty
import vn.vnptit.sapi.oneapp.models.einvoice.InvItem

public class ImportAndPublishInvoiceRequest {

	@JsonProperty("invItem")
	private InvItem invItem
	@JsonProperty("pattern")
	private String pattern
	@JsonProperty("serial")
	private String serial
	@JsonProperty("convert")
	private String convert = "0"

	InvItem getInvItem() {
		return invItem
	}

	void setInvItem(InvItem invItem) {
		this.invItem = invItem
	}

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

	String getConvert() {
		return convert
	}

	void setConvert(String convert) {
		this.convert = convert
	}
}
