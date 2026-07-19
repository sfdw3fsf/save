package vn.vnptit.sapi.oneapp.models.ump

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class UMP_REQ {
    @JsonProperty("serialNumber")
    String serialNumber

    @JsonProperty("mytvUsername")
    String mytvUsername

    @JsonProperty("mytvPassword")
    String mytvPassword
}
