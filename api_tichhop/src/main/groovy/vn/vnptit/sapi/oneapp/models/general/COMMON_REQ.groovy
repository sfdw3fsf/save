package vn.vnptit.sapi.oneapp.models.general

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class COMMON_REQ {
    @JsonProperty("apipath")
    String apipath

    @JsonProperty("apibody")
    String apibody

    @JsonProperty("apimethod")
    String apimethod
}
