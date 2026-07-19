package vn.vnptit.sapi.oneapp.models.visaapp

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.visa.application.*

@JsonIgnoreProperties(["metaClass"])
class ChangeBillingInfo {
    @JsonProperty("login")
    public Login login
    @JsonProperty("accountModel")
    public AccountModel accountModel
    @JsonProperty("billingInfo")
    public String billingInfo
    @JsonProperty("note")
    public String note

    ChangeBillingInfo() {
        this.login = new Login()
    }
}