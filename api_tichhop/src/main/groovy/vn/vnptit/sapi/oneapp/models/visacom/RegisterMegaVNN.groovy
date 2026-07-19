package vn.vnptit.sapi.oneapp.models.visacom

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import vn.vnptit.sapi.oneapp.models.Attribute
import ws.client.visa.common.AccountModel
import ws.client.visa.common.AccountServiceModel
import ws.client.visa.common.CustomerModel
import ws.client.visa.common.Login

@JsonIgnoreProperties(["metaClass"])
class RegisterMegaVNN {
    @JsonProperty("login")
    public Login login
    @JsonProperty("customerModel")
    public CustomerModel customerModel
    @JsonProperty("accountModel")
    public AccountModel accountModel
    @JsonProperty("accountServiceModel")
    public AccountServiceModel accountServiceModel
    @JsonProperty("childServices")
    public Object[] childServices
    @JsonProperty("attributes")
    public List<Attribute> attributes

    RegisterMegaVNN() {
        this.login = new Login()
    }
}