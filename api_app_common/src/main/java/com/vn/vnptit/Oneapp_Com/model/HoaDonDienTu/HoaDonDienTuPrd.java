
package com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu;

/**
 * @author DanhNQ
 *
 */
public class HoaDonDienTuPrd {

	private String	code;
	private String	remark;
	private Double	total;
	private String	prodName;
	private String	prodUnit;
	private Integer	prodQuantity;
	private Integer	prodPrice;
	private Double	discount;
	private Double	discountAmount;
	private Double	vat_rate;
	private Double	vat_amount;
	private Integer	amount;

	public Double getVat_rate() {
		return vat_rate;
	}

	public void setVat_rate(Double vat_rate) {
		this.vat_rate = vat_rate;
	}

	public Double getVat_amount() {
		return vat_amount;
	}

	public void setVat_amount(Double vat_amount) {
		this.vat_amount = vat_amount;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProdUnit() {
		return prodUnit;
	}

	public void setProdUnit(String prodUnit) {
		this.prodUnit = prodUnit;
	}

	public Integer getProdQuantity() {
		return prodQuantity;
	}

	public void setProdQuantity(Integer prodQuantity) {
		this.prodQuantity = prodQuantity;
	}

	public Integer getProdPrice() {
		return prodPrice;
	}

	public void setProdPrice(Integer prodPrice) {
		this.prodPrice = prodPrice;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}


}
