
package vn.vnptit.sapi.oneapp.models.smcs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class ChangeGoodsDetail {

    @SerializedName("trans_id")
    @Expose
    private String trans_id;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("status_name")
    @Expose
    private String status_name;

    @SerializedName("trans_no")
    @Expose
    private String trans_no;

    @SerializedName("trans_date")
    @Expose
    private String trans_date;

    @SerializedName("stock_id")
    @Expose
    private String stock_id;

    @SerializedName("stock_name")
    @Expose
    private String stock_name;

    @SerializedName("reseller_id")
    @Expose
    private String reseller_id;

    @SerializedName("reseller_name")
    @Expose
    private String reseller_name;

    @SerializedName("trans_type")
    @Expose
    private String trans_type;

    @SerializedName("trans_type_name")
    @Expose
    private String trans_type_name;

    @SerializedName("staff_id")
    @Expose
    private String staff_id;

    @SerializedName("staff_code")
    @Expose
    private String staff_code;

    @SerializedName("staff_name")
    @Expose
    private String staff_name;

    @SerializedName("reason_id")
    @Expose
    private String reason_id;

    @SerializedName("reason_desc")
    @Expose
    private String reason_desc;

    @SerializedName("cust_info")
    @Expose
    private String cust_info;

    @SerializedName("list_detail")
    @Expose
    private List<ListDetail> list_detail;

    @SerializedName("list_old")
    @Expose
    private List<ListOld> list_old;

    public String getTrans_no() {
        return trans_no;
    }

    public void setTrans_no(String trans_no) {
        this.trans_no = trans_no;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public String getReseller_id() {
        return reseller_id;
    }

    public void setReseller_id(String reseller_id) {
        this.reseller_id = reseller_id;
    }

    public String getReseller_name() {
        return reseller_name;
    }

    public void setReseller_name(String reseller_name) {
        this.reseller_name = reseller_name;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    public String getTrans_type_name() {
        return trans_type_name;
    }

    public void setTrans_type_name(String trans_type_name) {
        this.trans_type_name = trans_type_name;
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

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getReason_id() {
        return reason_id;
    }

    public void setReason_id(String reason_id) {
        this.reason_id = reason_id;
    }

    public String getReason_desc() {
        return reason_desc;
    }

    public void setReason_desc(String reason_desc) {
        this.reason_desc = reason_desc;
    }

    public String getCust_info() {
        return cust_info;
    }

    public void setCust_info(String cust_info) {
        this.cust_info = cust_info;
    }

    public List<ListDetail> getList_detail() {
        return list_detail;
    }

    public void setList_detail(List<ListDetail> list_detail) {
        this.list_detail = list_detail;
    }

    public List<ListOld> getList_old() {
        return list_old;
    }

    public void setList_old(List<ListOld> list_old) {
        this.list_old = list_old;
    }

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }
}
