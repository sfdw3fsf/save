package vn.vnptit.sapi.oneapp.models.vtc

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class SubscriptionActivate {
    @JsonProperty("ma_tb")
    String ma_tb
    @JsonProperty("phone")
    String phone
    @JsonProperty("productCode")
    String productCode
    @JsonProperty("email")
    String email
    @JsonProperty("fullname")
    String fullname
    @JsonProperty("address")
    String address
    @JsonProperty("ngaysinh")
    String ngaysinh
    @JsonProperty("so_gt")
    String so_gt
    @JsonProperty("gioitinh")
    String gioitinh
}