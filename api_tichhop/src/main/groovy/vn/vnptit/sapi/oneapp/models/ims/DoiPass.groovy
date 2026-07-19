package vn.vnptit.sapi.oneapp.models.ims


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class DoiPass {
    @JsonProperty("msisdn")
    public String msisdn
    @JsonProperty("type")
    public String type
    @JsonProperty("password")
    public String password
}