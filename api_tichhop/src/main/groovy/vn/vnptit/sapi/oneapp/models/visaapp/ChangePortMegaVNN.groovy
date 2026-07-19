package vn.vnptit.sapi.oneapp.models.visaapp

import ws.client.visa.application.*
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import vn.vnptit.sapi.oneapp.models.Attribute

@JsonIgnoreProperties(["metaClass"])
class ChangePortMegaVNN {
    @JsonProperty("login")
    public Login login
    @JsonProperty("accountModel")
    public AccountModel accountModel
    @JsonProperty("attributes")
    public List<Attribute> attributes

    ChangePortMegaVNN() {
        this.login = new Login()
    }
}