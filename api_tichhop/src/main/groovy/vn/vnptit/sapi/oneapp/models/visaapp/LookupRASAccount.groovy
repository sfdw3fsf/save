package vn.vnptit.sapi.oneapp.models.visaapp

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.visa.application.*

@JsonIgnoreProperties(["metaClass"])
class LookupRASAccount {
    @JsonProperty("login")
    public Login login
    @JsonProperty("accountName")
    public String accountName

    LookupRASAccount() {
        this.login = new Login()
    }
}