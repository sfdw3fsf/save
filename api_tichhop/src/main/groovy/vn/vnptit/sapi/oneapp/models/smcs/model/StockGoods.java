
package vn.vnptit.sapi.oneapp.models.smcs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class StockGoods {

    @SerializedName("serial_fist")
    @Expose
    private String serial_fist;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("stock_serial_id")
    @Expose
    private String stock_serial_id;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("stock_id")
    @Expose
    private String stock_id;

    @SerializedName("serial_last")
    @Expose
    private String serial_last;

    @SerializedName("product_id")
    @Expose
    private String product_id;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStock_serial_id() {
        return stock_serial_id;
    }

    public void setStock_serial_id(String stock_serial_id) {
        this.stock_serial_id = stock_serial_id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    public String getSerial_last() {
        return serial_last;
    }

    public void setSerial_last(String serial_last) {
        this.serial_last = serial_last;
    }
}
