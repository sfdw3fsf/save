package vn.vnptit.sapi.oneapp.models.vtn


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.vtn.SubscriberObject

@JsonIgnoreProperties(["metaClass"])
class DisconnectSubscriber {
    @JsonProperty("subscriberObject")
    SubscriberObject subscriberObject
    @JsonProperty("strReasonId")
    String strReasonId
}