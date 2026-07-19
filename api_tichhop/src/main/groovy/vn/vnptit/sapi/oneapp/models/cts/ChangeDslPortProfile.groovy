package vn.vnptit.sapi.oneapp.models.cts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class ChangeDslPortProfile {
    @JsonProperty("deviceIp")
    String deviceIp

    @JsonProperty("frame")
    int frame

    @JsonProperty("slot")
    int slot

    @JsonProperty("port")
    int port

    @JsonProperty("profileName")
    String profileName
}
