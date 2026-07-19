package vn.vnptit.sapi.oneapp.models.vtc

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class GetShippingData {
    @JsonProperty("fiberId")
    String fiberId
}