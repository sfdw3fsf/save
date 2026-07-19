package vn.vnptit.sapi.oneapp.models.green_online

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class DangKyV2Req {
    @JsonProperty("id")
    String id

    @JsonProperty("flag")
    String flag

    @JsonProperty("operator")
    String operator

    @JsonProperty("province")
    String province

    @JsonProperty("acc_id")
    String acc_id

    @JsonProperty("promotioncode")
    String promotioncode

    @JsonProperty("unit")
    String unit

    @JsonProperty("name")
    String name

    @JsonProperty("address")
    String address

    @JsonProperty("phone")
    String phone

    @JsonProperty("email")
    String email
}

@JsonIgnoreProperties(["metaClass"])
class DangKyReq {
    @JsonProperty("account")
    String account

    @JsonProperty("flag")
    String flag

    @JsonProperty("user")
    String user

    @JsonProperty("province")
    String province

    @JsonProperty("acc_id")
    String acc_id

    @JsonProperty("promotioncode")
    String promotioncode

    @JsonProperty("unit")
    String unit
}
