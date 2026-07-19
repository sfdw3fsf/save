package vn.vnptit.sapi.oneapp.models.tts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class Data {
    @JsonProperty("data")
    String data
}