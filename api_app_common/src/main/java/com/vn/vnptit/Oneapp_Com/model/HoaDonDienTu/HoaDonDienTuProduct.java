package com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HoaDonDienTuProduct {

	@JsonProperty("stt")
	private Number	stt;

	@JsonProperty("prodname")
	private String	prodName;

	@JsonProperty("produnit")
	private String	prodUnit;

	@JsonProperty("prodquantity")
	private Number	prodQuantity;

	@JsonProperty("prodprice")
	private Double	prodPrice;

	@JsonProperty("tien")
	private Double	tien;

	@JsonProperty("vat")
	private Double	vat;

	@JsonProperty("amount")
	private Double	amount;

	@JsonProperty("thanhtien")
	private Double	thanhTien;

	@JsonProperty("total")
	private Double	total;

	@JsonProperty("tongtien")
	private Double	tongTien;

	@JsonProperty("tyle_vat")
	private Double	tyLeVat;

	@JsonProperty("vatamount")
	private Double	vatamount;

	@JsonProperty("vatrate")
	private Double	vatrate;

	public HoaDonDienTuProduct() {
		super();
	}

	public HoaDonDienTuProduct(Number stt, String prodName, String prodUnit,
			Number prodQuantity, Double prodPrice, Double tien, Double vat,
			Double amount, Double thanhTien, Double tongTien, Double tyLeVat,
			Double vatAm, Double total, Double vatrate) {
		super();
		this.stt = stt;
		this.prodName = prodName;
		this.prodUnit = prodUnit;
		this.prodQuantity = prodQuantity;
		this.prodPrice = prodPrice;
		this.tien = tien;
		this.vat = vat;
		this.amount = amount;
		this.vatamount = vatAm;
		this.thanhTien = thanhTien;
		this.tongTien = tongTien;
		this.tyLeVat = tyLeVat;
		this.vatrate = vatrate;
	}

	
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getTongTien() {
		return tongTien;
	}

	public void setTongTien(Double tongTien) {
		this.tongTien = tongTien;
	}

	public Double getTyLeVat() {
		return tyLeVat;
	}

	public void setTyLeVat(Double tyLeVat) {
		this.tyLeVat = tyLeVat;
	}

	public Double getVatamount() {
		return vatamount;
	}

	public void setVatamount(Double vatamount) {
		this.vatamount = vatamount;
	}

	public Double getVatrate() {
		return vatrate;
	}

	public void setVatrate(Double vatrate) {
		this.vatrate = vatrate;
	}

	public Number getStt() {
		return stt;
	}

	public void setStt(Number stt) {
		this.stt = stt;
	}

	public Double getTien() {
		return tien;
	}

	public void setTien(Double tien) {
		this.tien = tien;
	}

	public Double getVat() {
		return vat;
	}

	public void setVat(Double vat) {
		this.vat = vat;
	}

	public Double getThanhTien() {
		return thanhTien;
	}

	public void setThanhTien(Double thanhTien) {
		this.thanhTien = thanhTien;
	}

	public void setProdQuantity(Number prodQuantity) {
		this.prodQuantity = prodQuantity;
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

	public Number getProdQuantity() {
		return prodQuantity;
	}

	public Double getProdPrice() {
		return prodPrice;
	}

	public void setProdPrice(Double prodPrice) {
		this.prodPrice = prodPrice;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		HoaDonDienTuProduct invItem = (HoaDonDienTuProduct) o;
		return Objects.equals(this.prodName, invItem.prodName)
				&& Objects.equals(this.prodUnit, invItem.prodUnit)
				&& Objects.equals(this.prodQuantity, invItem.prodQuantity)
				&& Objects.equals(this.prodPrice, invItem.prodPrice)
				&& Objects.equals(this.amount, invItem.amount);
	}

	@Override
	public int hashCode() {
		return Objects.hash(prodName, prodUnit, prodQuantity, prodPrice,
				amount);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class InvProduct {\n");
		sb.append("    ProdName: ").append(toIndentedString(prodName))
				.append("\n");
		sb.append("    ProdUnit: ").append(toIndentedString(prodUnit))
				.append("\n");
		sb.append("    ProdQuantity: ").append(toIndentedString(prodQuantity))
				.append("\n");
		sb.append("    ProdPrice: ").append(toIndentedString(prodPrice))
				.append("\n");
		sb.append("    Amount: ").append(toIndentedString(amount))
				.append("\n");
		sb.append("}");
		return sb.toString();
	}

	public String toXml() {
		StringBuilder str = new StringBuilder();
		str.append("<Product>");
		str.append("<ProdName>" + prodName + "</ProdName>");
		str.append("<ProdUnit>" + prodUnit + "</ProdUnit>");
		str.append("<ProdQuantity>" + prodQuantity + "</ProdQuantity>");
		str.append("<ProdPrice>" + prodPrice + "</ProdPrice>");
		str.append("<Amount>" + amount + "</Amount>");
		str.append("</Product>");

		return str.toString();
	}


	public String toXml78() {
		StringBuilder str = new StringBuilder();
		str.append("<Product>");
		str.append("<ProdName>" + prodName + "</ProdName>");
		str.append("<ProdUnit>" + prodUnit + "</ProdUnit>");
		str.append("<ProdQuantity>" + prodQuantity + "</ProdQuantity>");
		str.append("<ProdPrice>" + prodPrice + "</ProdPrice>");
		str.append("<Total>" + tien + "</Total>");
		str.append("<VATRate>" + tyLeVat + "</VATRate>");
		str.append("<VATAmount>" + vat + "</VATAmount>");
		str.append("<IsSum>1</IsSum>");
		str.append("<Amount>" + tongTien + "</Amount>");
		str.append("</Product>");
		return str.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return null;
		}
		return o.toString().replace("\n", "\n    ");
	}
}
