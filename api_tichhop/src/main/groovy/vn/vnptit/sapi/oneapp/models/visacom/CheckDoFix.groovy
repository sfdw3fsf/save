package vn.vnptit.sapi.oneapp.models.visacom

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.visa.common.AccountModel
import ws.client.visa.common.Login

@JsonIgnoreProperties(["metaClass"])
class CheckDoFix {
    @JsonProperty("login")
    public Login login
    @JsonProperty("accountModel")
    public AccountModel accountModel

    CheckDoFix() {
        this.login = new Login()
    }
}