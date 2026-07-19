package vn.vnptit.sapi.oneapp.models.visaapp

import ws.client.visa.application.Login
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class UnReservedAccount {
    @JsonProperty("accountName")
    public String accountName
}