package vn.vnptit.sapi.oneapp.models.visacom


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class ChangeMacAddress {
    @JsonProperty("accountName")
    public String accountName
    @JsonProperty("macAddress")
    public String macAddress
    @JsonProperty("note")
    public String note
}