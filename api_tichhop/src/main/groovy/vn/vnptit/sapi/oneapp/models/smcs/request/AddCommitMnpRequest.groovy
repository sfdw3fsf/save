package vn.vnptit.sapi.oneapp.models.smcs.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class AddCommitMnpRequest {
    @JsonProperty("account")
    public String account
    @JsonProperty("msisdn")
    public String msisdn
    @JsonProperty("type")
    public String type
    @JsonProperty("otp")
    public String otp

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

    String getType() {
        return type
    }

    void setType(String type) {
        this.type = type
    }

    String getOtp() {
        return otp
    }

    void setOtp(String otp) {
        this.otp = otp
    }
}