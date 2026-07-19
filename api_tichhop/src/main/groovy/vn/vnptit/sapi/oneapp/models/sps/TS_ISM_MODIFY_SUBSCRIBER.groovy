package vn.vnptit.sapi.oneapp.models.sps


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class TS_ISM_MODIFY_SUBSCRIBER {
    @JsonProperty("msisdn")
    public String msisdn
    @JsonProperty("service_action")
    public String service_action
    @JsonProperty("user_password")
    public String user_password
}