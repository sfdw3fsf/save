package vn.vnptit.sapi.oneapp.models.vtn

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class GetPassword {
    @JsonProperty("external_id")
    String external_id
}