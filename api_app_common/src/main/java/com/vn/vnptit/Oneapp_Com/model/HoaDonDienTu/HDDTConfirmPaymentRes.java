
package com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author DanhNQ
 *
 */
public class HDDTConfirmPaymentRes {

	@JsonProperty("confirmPaymentResult")
	private String confirmPaymentResult;

	public String getConfirmPaymentResult() {
		return confirmPaymentResult;
	}

	public void setConfirmPaymentResult(String confirmPaymentResult) {
		this.confirmPaymentResult = confirmPaymentResult;
	}

}
