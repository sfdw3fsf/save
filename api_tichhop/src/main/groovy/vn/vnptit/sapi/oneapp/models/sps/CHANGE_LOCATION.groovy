package vn.vnptit.sapi.oneapp.models.sps


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class CHANGE_LOCATION {
    @JsonProperty("msisdn")
    public String msisdn
    @JsonProperty("area")
    public String area
}