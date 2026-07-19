package vn.vnptit.sapi.oneapp.models.smcs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class ListInfoPhieuXKDoiHongDetail {

    @SerializedName("amount_public")
    @Expose
    private String amount_public;

    @SerializedName("unit")
    @Expose
    private String unit;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("unit_price")
    @Expose
    private String unit_price;

    public String getAmount_public() {
        return amount_public;
    }

    public void setAmount_public(String amount_public) {
        this.amount_public = amount_public;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }
}
