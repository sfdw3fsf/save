package vn.vnptit.sapi.oneapp.models.vtn

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class QueryAccount {
    @JsonProperty("account_code")
    String account_code
    @JsonProperty("account_no")
    String account_no
}