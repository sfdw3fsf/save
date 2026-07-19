package vn.vnptit.sapi.oneapp.models.subman

import client.ws.visa.common.AccountModel
import client.ws.visa.common.Login
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class KiemTraThongTin {
    @JsonProperty("tongDai")
    public int tongDai
    @JsonProperty("soMay")
    public String soMay
}