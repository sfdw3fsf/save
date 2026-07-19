package vn.vnptit.sapi.oneapp.models.smcs.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class CreatedNewCardRequest {
    @JsonProperty("account")
    public String account
    @JsonProperty("action")
    public String action
    @JsonProperty("trans_id")
    public String trans_id
    @JsonProperty("trans_date")
    public String trans_date
    @JsonProperty("trans_no")
    public String trans_no
    @JsonProperty("stock_id")
    public String stock_id
    @JsonProperty("reason_id")
    public String reason_id
    @JsonProperty("cus_info")
    public String cus_info
    @JsonProperty("detail_list")
    public String detail_list
    @JsonProperty("detail_serial")
    public String detail_serial

    String getAccount() {
        return account
    }

    void setAccount(String account) {
        this.account = account
    }

    String getAction() {
        return action
    }

    void setAction(String action) {
        this.action = action
    }

    String getTrans_id() {
        return trans_id
    }

    void setTrans_id(String trans_id) {
        this.trans_id = trans_id
    }

    String getTrans_date() {
        return trans_date
    }

    void setTrans_date(String trans_date) {
        this.trans_date = trans_date
    }

    String getTrans_no() {
        return trans_no
    }

    void setTrans_no(String trans_no) {
        this.trans_no = trans_no
    }

    String getStock_id() {
        return stock_id
    }

    void setStock_id(String stock_id) {
        this.stock_id = stock_id
    }

    String getReason_id() {
        return reason_id
    }

    void setReason_id(String reason_id) {
        this.reason_id = reason_id
    }

    String getCus_info() {
        return cus_info
    }

    void setCus_info(String cus_info) {
        this.cus_info = cus_info
    }

    String getDetail_list() {
        return detail_list
    }

    void setDetail_list(String detail_list) {
        this.detail_list = detail_list
    }

    String getDetail_serial() {
        return detail_serial
    }

    void setDetail_serial(String detail_serial) {
        this.detail_serial = detail_serial
    }
}