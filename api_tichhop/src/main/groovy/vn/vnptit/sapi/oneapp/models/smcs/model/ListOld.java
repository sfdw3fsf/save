package vn.vnptit.sapi.oneapp.models.smcs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class ListOld {

    @SerializedName("product_id")
    @Expose
    private String product_id;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("serial_new")
    @Expose
    private String serial_new;

    @SerializedName("product_warranty_id")
    @Expose
    private String product_warranty_id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("serial_old")
    @Expose
    private String serial_old;

    @SerializedName("detail_id")
    @Expose
    private String detail_id;

    @SerializedName("stock_serial_id")
    @Expose
    private String stock_serial_id;

    public String getStock_serial_id() {
        return stock_serial_id;
    }

    public void setStock_serial_id(String stock_serial_id) {
        this.stock_serial_id = stock_serial_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSerial_new() {
        return serial_new;
    }

    public void setSerial_new(String serial_new) {
        this.serial_new = serial_new;
    }

    public String getProduct_warranty_id() {
        return product_warranty_id;
    }

    public void setProduct_warranty_id(String product_warranty_id) {
        this.product_warranty_id = product_warranty_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerial_old() {
        return serial_old;
    }

    public void setSerial_old(String serial_old) {
        this.serial_old = serial_old;
    }

    public String getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(String detail_id) {
        this.detail_id = detail_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
