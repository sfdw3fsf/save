package vn.vnptit.sapi.oneapp.models.smcs.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class ThueBaoMnpPIRequest {
    @JsonProperty("city")
    public String city
    @JsonProperty("state")
    public String state
    @JsonProperty("county")
    public String county
    @JsonProperty("status")
    public String status
    @JsonProperty("stb")
    public String stb
    @JsonProperty("account")
    public String account

    String getCity() {
        return city
    }

    void setCity(String city) {
        this.city = city
    }

    String getState() {
        return state
    }

    void setState(String state) {
        this.state = state
    }

    String getCounty() {
        return county
    }

    void setCounty(String county) {
        this.county = county
    }

    String getStatus() {
        return status
    }

    void setStatus(String status) {
        this.status = status
    }

    String getStb() {
        return stb
    }

    void setStb(String stb) {
        this.stb = stb
    }

    String getAccount() {
        return account
    }

    void setAccount(String account) {
        this.account = account
    }
}