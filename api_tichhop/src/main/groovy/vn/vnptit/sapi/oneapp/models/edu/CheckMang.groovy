package vn.vnptit.sapi.oneapp.models.edu

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class CheckMang {
    @JsonProperty("data")
    List<String> sdts
}