
package vn.vnptit.sapi.oneapp.models.smcs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class ChangeGoods {

    @SerializedName("staff_name")
    @Expose
    private String staff_name;

    @SerializedName("stock_trans_id")
    @Expose
    private String stock_trans_id;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("stock_name")
    @Expose
    private String stock_name;

    @SerializedName("status_name")
    @Expose
    private String status_name;

    @SerializedName("staff_id")
    @Expose
    private String staff_id;

    @SerializedName("staff_code")
    @Expose
    private String staff_code;

    @SerializedName("exp_date")
    @Expose
    private String exp_date;

    @SerializedName("stock_id")
    @Expose
    private String stock_id;

    @SerializedName("cus_info")
    @Expose
    private String cus_info;

    @SerializedName("status")
    @Expose
    private String status;

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getStock_trans_id() {
        return stock_trans_id;
    }

    public void setStock_trans_id(String stock_trans_id) {
        this.stock_trans_id = stock_trans_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getStaff_code() {
        return staff_code;
    }

    public void setStaff_code(String staff_code) {
        this.staff_code = staff_code;
    }

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    public String getCus_info() {
        return cus_info;
    }

    public void setCus_info(String cus_info) {
        this.cus_info = cus_info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
