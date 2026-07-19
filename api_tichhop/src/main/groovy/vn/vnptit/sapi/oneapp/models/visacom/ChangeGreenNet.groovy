package vn.vnptit.sapi.oneapp.models.visacom

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class ChangeGreenNet {
    @JsonProperty("accountName")
    public String accountName
    @JsonProperty("status")
    public int status
}