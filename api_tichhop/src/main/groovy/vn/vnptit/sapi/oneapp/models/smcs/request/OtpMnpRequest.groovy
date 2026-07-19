package vn.vnptit.sapi.oneapp.models.smcs.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class OtpMnpRequest {
    @JsonProperty("account")
    public String account
    @JsonProperty("msisdn")
    public String msisdn

    String getAccount() {
        return account
    }

    void setAccount(String account) {
        this.account = account
    }

    String getMsisdn() {
        return msisdn
    }

    void setMsisdn(String msisdn) {
        this.msisdn = msisdn
    }
}