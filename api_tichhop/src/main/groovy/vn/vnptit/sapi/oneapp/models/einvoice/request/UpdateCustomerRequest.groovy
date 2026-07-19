package vn.vnptit.sapi.oneapp.models.einvoice.request

import com.fasterxml.jackson.annotation.JsonProperty
import vn.vnptit.sapi.oneapp.models.einvoice.InvCustomer

public class UpdateCustomerRequest {

	@JsonProperty("customer")
	private InvCustomer customer
	@JsonProperty("convert")
	private String convert = "0"

	InvCustomer getCustomer() {
		return customer
	}

	void setCustomer(InvCustomer customer) {
		this.customer = customer
	}

	String getConvert() {
		return convert
	}

	void setConvert(String convert) {
		this.convert = convert
	}
}
