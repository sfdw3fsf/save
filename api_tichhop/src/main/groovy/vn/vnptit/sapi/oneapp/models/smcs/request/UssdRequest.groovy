package vn.vnptit.sapi.oneapp.models.smcs.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class UssdRequest {
    @JsonProperty("account")
    public String account
    @JsonProperty("agentCode")
    public String agentCode

    String getAccount() {
        return account
    }

    void setAccount(String account) {
        this.account = account
    }

    String getAgentCode() {
        return agentCode
    }

    void setAgentCode(String agentCode) {
        this.agentCode = agentCode
    }
}