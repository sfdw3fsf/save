package vn.vnptit.sapi.oneapp.models.vtc.respones

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import vn.vnptit.sapi.oneapp.models.vtc.models.*

@JsonIgnoreProperties(["metaClass"])
class KichHoatDichVuRes {
    @JsonProperty("subscriptionId")
    public String subscriptionId

    @JsonProperty("refId")
    public String refId

    String getRefId() {
        return refId
    }

    void setRefId(String refId) {
        this.refId = refId
    }

    String getSubscriptionId() {
        return subscriptionId
    }

    void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId
    }
}
