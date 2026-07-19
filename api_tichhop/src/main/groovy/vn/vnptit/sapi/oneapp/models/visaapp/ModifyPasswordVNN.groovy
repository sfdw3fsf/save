package vn.vnptit.sapi.oneapp.models.visaapp

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.visa.application.*

@JsonIgnoreProperties(["metaClass"])
class ModifyPasswordVNN {
    @JsonProperty("login")
    public Login login

    @JsonProperty("accountModel")
    public AccountModel accountModel

    ModifyPasswordVNN() {
        this.login = new Login()
    }
}