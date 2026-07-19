package vn.vnptit.sapi.oneapp.models.visaapp

import ws.client.visa.application.*
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class UpdateStatusVNN {
    @JsonProperty("login")
    public Login login

    @JsonProperty("accountModel")
    public AccountModel accountModel

    @JsonProperty("reason")
    public String reason

    @JsonProperty("note")
    public String note

    UpdateStatusVNN() {
        this.login = new Login()
    }
}