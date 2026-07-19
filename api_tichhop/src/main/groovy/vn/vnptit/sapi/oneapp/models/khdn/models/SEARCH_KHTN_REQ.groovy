package vn.vnptit.sapi.oneapp.models.qlnt

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class SEARCH_KHTN_REQ {
    @JsonProperty("mst")
    String mst
}