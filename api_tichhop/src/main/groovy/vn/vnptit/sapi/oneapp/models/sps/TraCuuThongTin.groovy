package vn.vnptit.sapi.oneapp.models.sps


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class TraCuuThongTin {
    @JsonProperty("msisdn")
    public String msisdn
    @JsonProperty("cfnry_number")
    public String cfnry_number
}
@JsonIgnoreProperties(["metaClass"])
class SPS_DELETE_SIPTRUNK_ISM {
    @JsonProperty("msisdn")
    public String msisdn
}
@JsonIgnoreProperties(["metaClass"])
class GET_PROJECTBYINVESTMENTFIELD_REQ {
    @JsonProperty("suserid")
    public String suserid
    @JsonProperty("spass")
    public String spass
}
@JsonIgnoreProperties(["metaClass"])
class IMS_HUY_THUEBAO_REQ {
    @JsonProperty("msisdn")
    public String msisdn
}
class IMS_HUY_THUEBAO_RESP implements Serializable{
    Boolean status
    String result
}