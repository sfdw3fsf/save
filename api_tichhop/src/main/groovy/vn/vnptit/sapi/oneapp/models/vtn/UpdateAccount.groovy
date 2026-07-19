package vn.vnptit.sapi.oneapp.models.vtn

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.vtn.AccountObject

@JsonIgnoreProperties(["metaClass"])
class UpdateAccount {
    @JsonProperty("accountObject")
    AccountObject accountObject
    @JsonProperty("strReasonId")
    String strReasonId
}