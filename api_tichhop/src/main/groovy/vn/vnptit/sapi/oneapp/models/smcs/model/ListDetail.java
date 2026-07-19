package vn.vnptit.sapi.oneapp.models.smcs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class ListDetail {

    @SerializedName("product_id")
    @Expose
    private String product_id;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    @SerializedName("serial_fist")
    @Expose
    private String serial_fist;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("date_public")
    @Expose
    private String date_public;

    @SerializedName("detail_id")
    @Expose
    private String detail_id;

    @SerializedName("serial_last")
    @Expose
    private String serial_last;

    @SerializedName("stock_serial_id")
    @Expose
    private String stock_serial_id;

    public String getStock_serial_id() {
        return stock_serial_id;
    }

    public void setStock_serial_id(String stock_serial_id) {
        this.stock_serial_id = stock_serial_id;
    }

    public String getSerial_fist() {
        return serial_fist;
    }

    public void setSerial_fist(String serial_fist) {
        this.serial_fist = serial_fist;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_public() {
        return date_public;
    }

    public void setDate_public(String date_public) {
        this.date_public = date_public;
    }

    public String getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(String detail_id) {
        this.detail_id = detail_id;
    }

    public String getSerial_last() {
        return serial_last;
    }

    public void setSerial_last(String serial_last) {
        this.serial_last = serial_last;
    }
}
