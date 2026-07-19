package vn.vnptit.sapi.oneapp.models.smcs.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import vn.vnptit.sapi.oneapp.models.smcs.model.AgentInfo

@JsonIgnoreProperties(["metaClass"])
class CapNhatDiemBanMMRequest {
    @JsonProperty("agentInfo")
    public AgentInfo agentInfo
    @JsonProperty("type")
    public String type
    @JsonProperty("account")
    public String account

    AgentInfo getAgentInfo() {
        return agentInfo
    }

    void setAgentInfo(AgentInfo agentInfo) {
        this.agentInfo = agentInfo
    }

    String getType() {
        return type
    }

    void setType(String type) {
        this.type = type
    }

    String getAccount() {
        return account
    }

    void setAccount(String account) {
        this.account = account
    }
}