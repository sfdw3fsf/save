package vn.vnptit.sapi.oneapp.models.vtn

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.vtn.SubscriberObject

@JsonIgnoreProperties(["metaClass"])
class ChangeServiceActStatus {
    @JsonProperty("subscriberObject")
    SubscriberObject subscriberObject
    @JsonProperty("strBareUnbare")
    String strBareUnbare
    @JsonProperty("strBareType")
    String strBareType
    @JsonProperty("strBareReasonType")
    String strBareReasonType
    @JsonProperty("strReasonId")
    String strReasonId
}