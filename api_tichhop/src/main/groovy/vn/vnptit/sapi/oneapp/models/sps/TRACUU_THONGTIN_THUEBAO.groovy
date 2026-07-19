package vn.vnptit.sapi.oneapp.models.sps

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class TRACUU_THONGTIN_THUEBAO {
    @JsonProperty("msisdn")
    public String msisdn
    @JsonProperty("node")
    public String node
    @JsonProperty("type")
    public String type
}