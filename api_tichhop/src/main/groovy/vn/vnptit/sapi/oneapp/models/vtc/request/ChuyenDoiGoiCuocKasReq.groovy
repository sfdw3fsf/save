package vn.vnptit.sapi.oneapp.models.vtc.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class ChuyenDoiGoiCuocKasReq {
    @JsonProperty("productCode")
    public String productCode
    @JsonProperty("custCode")
    public String custCode
    @JsonProperty("phone")
    public String phone
    @JsonProperty("email")
    public String email
    @JsonProperty("reason")
    public String reason
    @JsonProperty("resetDate")
    public boolean resetDate

    String getProductCode() {
        return productCode
    }

    void setProductCode(String productCode) {
        this.productCode = productCode
    }

    String getCustCode() {
        return custCode
    }

    void setCustCode(String custCode) {
        this.custCode = custCode
    }

    String getPhone() {
        return phone
    }

    void setPhone(String phone) {
        this.phone = phone
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }

    String getReason() {
        return reason
    }

    void setReason(String reason) {
        this.reason = reason
    }

    boolean getResetDate() {
        return resetDate
    }

    void setResetDate(boolean resetDate) {
        this.resetDate = resetDate
    }
}
