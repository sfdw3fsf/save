
package com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author DanhNQ
 *
 */
public class HoaDonDienTuInvNoPayModel {
	@JsonProperty("phanvung_id")
	private Integer phanvung_id;
	@JsonProperty("invToken")
	private String invToken;
	
	public Integer getPhanvung_id() {
		return phanvung_id;
	}
	public void setPhanvung_id(Integer phanvung_id) {
		this.phanvung_id = phanvung_id;
	}
	public String getInvToken() {
		return invToken;
	}
	public void setInvToken(String invToken) {
		this.invToken = invToken;
	}
	
}
