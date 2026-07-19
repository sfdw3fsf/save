
package com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Product {

    @SerializedName("VATRate")
    @Expose
    private String vATRate;
    @SerializedName("ProdQuantity")
    @Expose
    private String prodQuantity;
    @SerializedName("Discount")
    @Expose
    private String discount;
    @SerializedName("VATAmount")
    @Expose
    private String vATAmount;
    @SerializedName("ProdName")
    @Expose
    private String prodName;
    @SerializedName("Total")
    @Expose
    private String total;
    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("ProdUnit")
    @Expose
    private String prodUnit;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("DiscountAmount")
    @Expose
    private String discountAmount;
    @SerializedName("Remark")
    @Expose
    private String remark;
    @SerializedName("ProdPrice")
    @Expose
    private String prodPrice;

    public String getVATRate() {
        return vATRate;
    }

    public void setVATRate(String vATRate) {
        this.vATRate = vATRate;
    }

    public String getProdQuantity() {
        return prodQuantity;
    }

    public void setProdQuantity(String prodQuantity) {
        this.prodQuantity = prodQuantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getVATAmount() {
        return vATAmount;
    }

    public void setVATAmount(String vATAmount) {
        this.vATAmount = vATAmount;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProdUnit() {
        return prodUnit;
    }

    public void setProdUnit(String prodUnit) {
        this.prodUnit = prodUnit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

}
