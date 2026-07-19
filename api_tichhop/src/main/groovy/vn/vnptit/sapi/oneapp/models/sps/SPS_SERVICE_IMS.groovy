package vn.vnptit.sapi.oneapp.models.sps


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class SPS_SERVICE_IMS {
    @JsonProperty("msisdn")
    public String msisdn
    @JsonProperty("imsi")
    public String imsi
    @JsonProperty("subtype")
    public String subtype
    @JsonProperty("ism_res_3")
    public String ism_res_3
    @JsonProperty("user_password")
    public String user_password
    @JsonProperty("profile")
    public String profile
}