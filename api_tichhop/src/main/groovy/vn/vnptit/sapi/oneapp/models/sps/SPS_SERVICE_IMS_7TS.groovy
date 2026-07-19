package vn.vnptit.sapi.oneapp.models.sps

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class SPS_SERVICE_IMS_7TS {
    @JsonProperty("line")
    public String line
    @JsonProperty("subtype")
    public String subtype
    @JsonProperty("msisdn")
    public String msisdn
    @JsonProperty("ism_res_3")
    public String ism_res_3
    @JsonProperty("imsi")
    public String imsi
}