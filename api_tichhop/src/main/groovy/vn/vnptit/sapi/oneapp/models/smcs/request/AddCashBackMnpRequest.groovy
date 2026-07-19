package vn.vnptit.sapi.oneapp.models.smcs.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class AddCashBackMnpRequest {
    @JsonProperty("account")
    public String account
    @JsonProperty("soTB")
    public String soTB
    @JsonProperty("otp")
    public String otp

    String getAccount() {
        return account
    }

    void setAccount(String account) {
        this.account = account
    }

    String getSoTB() {
        return soTB
    }

    void setSoTB(String soTB) {
        this.soTB = soTB
    }

    String getOtp() {
        return otp
    }

    void setOtp(String otp) {
        this.otp = otp
    }
}