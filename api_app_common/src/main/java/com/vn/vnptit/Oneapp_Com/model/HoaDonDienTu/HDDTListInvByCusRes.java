
package com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author DanhNQ
 *
 */
public class HDDTListInvByCusRes {

	@JsonProperty("listInvByCusResult")
	private String listInvByCusResult;

	public String getListInvByCusResult() {
		return listInvByCusResult;
	}

	public void setListInvByCusResult(String listInvByCusResult) {
		this.listInvByCusResult = listInvByCusResult;
	}
	
	

}
