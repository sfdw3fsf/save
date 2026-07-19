package vn.vnptit.sapi.oneapp.models.vims

import com.fasterxml.jackson.annotation.JsonProperty

class CreateHssReq {
    @JsonProperty("msisdn")
    String msisdn

    @JsonProperty("imsi")
    String imsi

    @JsonProperty("reqUser")
    String reqUser

    @JsonProperty("ismRes1")
    String ismRes1

    @JsonProperty("ismRes2")
    String ismRes2

    @JsonProperty("subtype")
    String subtype

    @JsonProperty("chargingProfId")
    String chargingProfId
}
