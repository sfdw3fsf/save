package vn.vnptit.sapi.oneapp.models.vtc.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class GoiCuocBh {

    @SerializedName("productName")
    @Expose
    private String productName;

    @SerializedName("productCode")
    @Expose
    private String productCode;

    @SerializedName("priceNoVat")
    @Expose
    private String priceNoVat;

    @SerializedName("priceVat")
    @Expose
    private String priceVat;

    @SerializedName("discount")
    @Expose
    private String discount;

    @SerializedName("bic")
    @Expose
    private String bic;

    @SerializedName("pit")
    @Expose
    private String pit;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getPriceNoVat() {
        return priceNoVat;
    }

    public void setPriceNoVat(String priceNoVat) {
        this.priceNoVat = priceNoVat;
    }

    public String getPriceVat() {
        return priceVat;
    }

    public void setPriceVat(String priceVat) {
        this.priceVat = priceVat;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getPit() {
        return pit;
    }

    public void setPit(String pit) {
        this.pit = pit;
    }
}
