package vn.vnptit.sapi.oneapp.models.einvoice

import com.fasterxml.jackson.annotation.JsonProperty

public class InvProduct {
    @JsonProperty("prodName")
    private String prodName
    @JsonProperty("prodUnit")
    private String prodUnit
    @JsonProperty("prodQuantity")
    private String prodQuantity
    @JsonProperty("prodPrice")
    private String prodPrice
    @JsonProperty("amount")
    private Long amount
    @JsonProperty("tien")
    private String tien
    @JsonProperty("vat")
    private String vat
    @JsonProperty("tongTien")
    private String tongTien
    @JsonProperty("tyLeVat")
    private String tyLeVat
    @JsonProperty("vatAmount")
    private String vatAmount
    @JsonProperty("total")
    private String total

    String getVatAmount() {
        return vatAmount
    }

    void setVatAmount(String vatAmount) {
        this.vatAmount = vatAmount
    }

    String getTotal() {
        return total
    }

    void setTotal(String total) {
        this.total = total
    }

    String getProdName() {
        return prodName
    }

    void setProdName(String prodName) {
        this.prodName = prodName
    }

    String getProdUnit() {
        return prodUnit
    }

    void setProdUnit(String prodUnit) {
        this.prodUnit = prodUnit
    }

    String getProdQuantity() {
        return prodQuantity
    }

    void setProdQuantity(String prodQuantity) {
        this.prodQuantity = prodQuantity
    }

    String getProdPrice() {
        return prodPrice
    }

    void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice
    }

    Long getAmount() {
        return amount
    }

    void setAmount(Long amount) {
        this.amount = amount
    }

    String getTien() {
        return tien
    }

    void setTien(String tien) {
        this.tien = tien
    }

    String getVat() {
        return vat
    }

    void setVat(String vat) {
        this.vat = vat
    }

    String getTongTien() {
        return tongTien
    }

    void setTongTien(String tongTien) {
        this.tongTien = tongTien
    }

    String getTyLeVat() {
        return tyLeVat
    }

    void setTyLeVat(String tyLeVat) {
        this.tyLeVat = tyLeVat
    }

    public String toXml78() {
        StringBuilder str = new StringBuilder()
        str.append("<Product>")
        str.append("<ProdName>$prodName</ProdName>")
        str.append("<ProdUnit>$prodUnit</ProdUnit>")
        str.append("<ProdQuantity>$prodQuantity</ProdQuantity>")
        str.append("<ProdPrice>$prodPrice</ProdPrice>")
        str.append("<Total>$tien</Total>")
        str.append("<VATRate>$tyLeVat</VATRate>")
        str.append("<VATAmount>$vat</VATAmount>")
        str.append("<IsSum>1</IsSum>");
        str.append("<Amount>$tongTien</Amount>")
        str.append("</Product>")
        return str.toString()
    }

    public String toXml32() {
        StringBuilder str = new StringBuilder()
        str.append("<Product>")
        str.append("<ProdName>$prodName</ProdName>")
        str.append("<ProdUnit>$prodUnit</ProdUnit>")
        str.append("<ProdQuantity>$prodQuantity</ProdQuantity>")
        str.append("<ProdPrice>$prodPrice</ProdPrice>")
        str.append("<Amount>$amount</Amount>")
        str.append("</Product>")
        return str.toString()
    }
}
