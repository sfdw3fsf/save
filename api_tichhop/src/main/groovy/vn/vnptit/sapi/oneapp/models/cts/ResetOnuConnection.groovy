package vn.vnptit.sapi.oneapp.models.cts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class ResetOnuConnection {
    @JsonProperty("deviceIp")
    String deviceIp

    @JsonProperty("frame")
    int frame

    @JsonProperty("slot")
    int slot

    @JsonProperty("port")
    int port

    @JsonProperty("onuIndex")
    int onuIndex
}
