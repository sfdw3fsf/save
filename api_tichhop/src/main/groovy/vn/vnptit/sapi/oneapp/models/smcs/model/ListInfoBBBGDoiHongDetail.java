package vn.vnptit.sapi.oneapp.models.smcs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class ListInfoBBBGDoiHongDetail {
    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    @SerializedName("total_price")
    @Expose
    private String total_price;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("unit")
    @Expose
    private String unit;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("serial_first")
    @Expose
    private String serial_first;

    @SerializedName("serial_last")
    @Expose
    private String serial_last;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public String getSerial_first() {
        return serial_first;
    }

    public void setSerial_first(String serial_first) {
        this.serial_first = serial_first;
    }

    public String getSerial_last() {
        return serial_last;
    }

    public void setSerial_last(String serial_last) {
        this.serial_last = serial_last;
    }
}
