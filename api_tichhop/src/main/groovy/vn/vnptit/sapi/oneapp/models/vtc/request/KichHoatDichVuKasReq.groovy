package vn.vnptit.sapi.oneapp.models.vtc.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import vn.vnptit.sapi.oneapp.models.vtc.models.*

@JsonIgnoreProperties(["metaClass"])
class KichHoatDichVuKasReq {
    @JsonProperty("productCode")
    public String productCode
    @JsonProperty("custCode")
    public String custCode
    @JsonProperty("phone")
    public String phone
    @JsonProperty("email")
    public String email
    @JsonProperty("fullname")
    public String fullname
    @JsonProperty("address")
    public String address

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

    String getFullname() {
        return fullname
    }

    void setFullname(String fullname) {
        this.fullname = fullname
    }

    String getAddress() {
        return address
    }

    void setAddress(String address) {
        this.address = address
    }
}
