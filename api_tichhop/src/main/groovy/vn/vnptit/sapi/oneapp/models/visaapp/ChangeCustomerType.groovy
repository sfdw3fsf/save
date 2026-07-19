package vn.vnptit.sapi.oneapp.models.visaapp

import ws.client.visa.application.*
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class ChangeCustomerType {
    @JsonProperty("login")
    public Login login
    @JsonProperty("customerModel")
    public CustomerModel customerModel
    @JsonProperty("note")
    public String note

    ChangeCustomerType() {
        this.login = new Login()
    }
}