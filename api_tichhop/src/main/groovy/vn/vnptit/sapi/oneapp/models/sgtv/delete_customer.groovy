package vn.vnptit.sapi.oneapp.models.sgtv

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class delete_customer {
    @JsonProperty("account")
    String account
    @JsonProperty("force")
    String force
}