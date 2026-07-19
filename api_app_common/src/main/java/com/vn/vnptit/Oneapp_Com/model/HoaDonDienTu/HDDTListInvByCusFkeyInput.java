
package com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu;

/**
 * @author DanhNQ
 *
 */
public class HDDTListInvByCusFkeyInput {
	private Integer phanvung_id;
	private String key;
	private String fromDate;
	private String toDate;
	public Integer getPhanvung_id() {
		return phanvung_id;
	}
	public void setPhanvung_id(Integer phanvung_id) {
		this.phanvung_id = phanvung_id;
	}
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
