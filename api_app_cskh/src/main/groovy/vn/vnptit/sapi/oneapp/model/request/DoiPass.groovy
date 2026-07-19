package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
public class DoiPass {
    @JsonProperty("msisdn")
    public String msisdn
    @JsonProperty("type")
    public String type
    @JsonProperty("password")
    public String password

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

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }
}