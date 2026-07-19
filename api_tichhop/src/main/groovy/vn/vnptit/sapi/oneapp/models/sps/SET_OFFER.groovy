package vn.vnptit.sapi.oneapp.models.sps


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class SET_OFFER {
    @JsonProperty("msisdn")
    public String msisdn
    @JsonProperty("msisdn1")
    public String msisdn1
    @JsonProperty("offer_id")
    public String offer_id
    @JsonProperty("start_date")
    public String start_date
    @JsonProperty("expiry_date")
    public String expiry_date
    @JsonProperty("groupname")
    public String groupname
    @JsonProperty("membertype")
    public String membertype
}