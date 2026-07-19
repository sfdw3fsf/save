package vn.vnptit.sapi.oneapp.models.smcs.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import vn.vnptit.sapi.oneapp.models.smcs.model.AgentInfo

@JsonIgnoreProperties(["metaClass"])
class OpenWebMediaRequest {
    @JsonProperty("agentInfo")
    public AgentInfo agentInfo

    AgentInfo getAgentInfo() {
        return agentInfo
    }

    void setAgentInfo(AgentInfo agentInfo) {
        this.agentInfo = agentInfo
    }
}