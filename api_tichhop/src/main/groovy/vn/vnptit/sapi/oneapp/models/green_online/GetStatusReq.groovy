package vn.vnptit.sapi.oneapp.models.green_online

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class GetStatusReq {
    @JsonProperty("id")
    String id
}
