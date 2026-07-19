package vn.vnptit.sapi.oneapp.models.vtc.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class GiaHanDichVuKasReq {
    @JsonProperty("productCode")
    public String productCode
    @JsonProperty("custCode")
    public String custCode
    @JsonProperty("phone")
    public String phone
    @JsonProperty("email")
    public String email

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
}
