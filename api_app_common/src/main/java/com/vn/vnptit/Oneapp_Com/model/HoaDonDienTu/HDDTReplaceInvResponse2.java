
package com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author DanhNQ
 *
 */
public class HDDTReplaceInvResponse2 {
	@JsonProperty("replaceInvResult")
	private String replaceInvResult;

	public String getReplaceInvResult() {
		return replaceInvResult;
	}

	public void setReplaceInvResult(String replaceInvResult) {
		this.replaceInvResult = replaceInvResult;
	}

}
