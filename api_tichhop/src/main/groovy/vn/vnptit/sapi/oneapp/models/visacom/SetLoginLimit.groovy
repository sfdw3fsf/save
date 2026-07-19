package vn.vnptit.sapi.oneapp.models.visacom

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class SetLoginLimit {
    @JsonProperty("accountName")
    public String accountName
    @JsonProperty("loginLimit")
    public int loginLimit
}