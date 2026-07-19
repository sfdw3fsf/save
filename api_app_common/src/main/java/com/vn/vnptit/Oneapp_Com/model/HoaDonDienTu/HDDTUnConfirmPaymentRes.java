
package com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author DanhNQ
 *
 */
public class HDDTUnConfirmPaymentRes {

	@JsonProperty("unConfirmPaymentResult")
	private String unConfirmPaymentResult;

	public String getUnConfirmPaymentResult() {
		return unConfirmPaymentResult;
	}

	public void setUnConfirmPaymentResult(String unConfirmPaymentResult) {
		this.unConfirmPaymentResult = unConfirmPaymentResult;
	}

	

}
