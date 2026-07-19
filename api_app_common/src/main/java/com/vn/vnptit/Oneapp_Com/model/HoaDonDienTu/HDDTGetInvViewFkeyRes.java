
package com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author DanhNQ
 *
 */
public class HDDTGetInvViewFkeyRes {
	
	@JsonProperty("getInvViewFkeyResult")
	private String getInvViewFkeyResult;

	public String getGetInvViewFkeyResult() {
		return getInvViewFkeyResult;
	}

	public void setGetInvViewFkeyResult(String getInvViewFkeyResult) {
		this.getInvViewFkeyResult = getInvViewFkeyResult;
	}
	
	
}
