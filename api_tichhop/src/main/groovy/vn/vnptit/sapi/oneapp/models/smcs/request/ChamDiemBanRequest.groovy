package vn.vnptit.sapi.oneapp.models.smcs.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class ChamDiemBanRequest {
    @JsonProperty("account")
    public String account
    @JsonProperty("agentCode")
    public String agentCode
    @JsonProperty("description")
    public String description

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

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }
}