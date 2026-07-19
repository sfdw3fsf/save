package vn.vnptit.sapi.oneapp.models.sps


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class DELETE_OFFER {
    @JsonProperty("msisdn")
    public String msisdn
    @JsonProperty("offer_id")
    public String offer_id
}