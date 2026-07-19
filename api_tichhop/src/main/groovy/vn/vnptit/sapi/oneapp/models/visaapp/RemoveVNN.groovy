package vn.vnptit.sapi.oneapp.models.visaapp

import ws.client.visa.application.AccountModel
import ws.client.visa.application.Login
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class RemoveVNN {
    @JsonProperty("login")
    public Login login
    @JsonProperty("accountModel")
    public AccountModel accountModel
    @JsonProperty("reason")
    public String reason
    @JsonProperty("note")
    public String note

    RemoveVNN() {
        this.login = new Login()
    }
}