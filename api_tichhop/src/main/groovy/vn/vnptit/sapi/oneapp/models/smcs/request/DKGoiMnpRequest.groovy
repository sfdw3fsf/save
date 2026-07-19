package vn.vnptit.sapi.oneapp.models.smcs.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class DKGoiMnpRequest {
    @JsonProperty("account")
    public String account
    @JsonProperty("soTB")
    public String soTB
    @JsonProperty("otp")
    public String otp
    @JsonProperty("goiCuoc")
    public String goiCuoc

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

    String getGoiCuoc() {
        return goiCuoc
    }

    void setGoiCuoc(String goiCuoc) {
        this.goiCuoc = goiCuoc
    }
}