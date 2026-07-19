package vn.vnptit.sapi.oneapp.models.vtc

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class Resend {
    @JsonProperty("fiberId")
    String fiberId
    @JsonProperty("subscriptionId")
    String subscriptionId
    @JsonProperty("email")
    String email
    @JsonProperty("phone")
    String phone
}