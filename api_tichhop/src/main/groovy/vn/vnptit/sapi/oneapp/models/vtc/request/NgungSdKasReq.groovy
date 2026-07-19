package vn.vnptit.sapi.oneapp.models.vtc.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class NgungSdKasReq {

    @JsonProperty("custCode")
    public String custCode = ""
    @JsonProperty("reason")
    public String reason = ""

    String getCustCode() {
        return custCode
    }

    void setCustCode(String custCode) {
        this.custCode = custCode
    }

    String getReason() {
        return reason
    }

    void setReason(String reason) {
        this.reason = reason
    }
}
