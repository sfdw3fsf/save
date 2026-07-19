package vn.vnptit.sapi.oneapp.models.vtn

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.vtn.AccountObject
import ws.client.vtn.SubscriberObject

@JsonIgnoreProperties(["metaClass"])
class TransferSubscriber {
    @JsonProperty("subscriberObject")
    SubscriberObject subscriberObject
    @JsonProperty("accountObject")
    AccountObject accountObject
    @JsonProperty("strReasonId")
    String strReasonId
}