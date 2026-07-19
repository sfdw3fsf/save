package vn.vnptit.sapi.oneapp.models.vtc.respones

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class NgungDichVuRes {
    @JsonProperty("subscriptionId")
    public String subscriptionId

    @JsonProperty("status")
    public String status

    String getSubscriptionId() {
        return subscriptionId
    }

    void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId
    }

    String getStatus() {
        return status
    }

    void setStatus(String status) {
        this.status = status
    }
}
