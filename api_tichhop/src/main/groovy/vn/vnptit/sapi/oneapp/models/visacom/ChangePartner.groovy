package vn.vnptit.sapi.oneapp.models.visacom

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.visa.common.Login

@JsonIgnoreProperties(["metaClass"])
class ChangePartner {
    @JsonProperty("login")
    public Login login
    @JsonProperty("accountName")
    public String accountName
    @JsonProperty("partnerCode")
    public String partnerCode
    @JsonProperty("note")
    public String note

    ChangePartner() {
        this.login = new Login()
    }
}