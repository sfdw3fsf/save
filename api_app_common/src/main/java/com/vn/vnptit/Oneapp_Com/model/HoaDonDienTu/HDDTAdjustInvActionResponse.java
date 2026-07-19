
package com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author DanhNQ
 *
 */
public class HDDTAdjustInvActionResponse {
	@JsonProperty("AdjustInvoiceActionResult")
	private String AdjustInvoiceActionResult;

	public String getAdjustInvoiceActionResult() {
		return AdjustInvoiceActionResult;
	}

	public void setAdjustInvoiceActionResult(
			String adjustInvoiceActionResult) {
		AdjustInvoiceActionResult = adjustInvoiceActionResult;
	}


}
