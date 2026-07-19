package vn.vnptit.sapi.oneapp.models.visaapp
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.visa.application.AccountModel
import ws.client.visa.application.CustomerModel
import ws.client.visa.application.Login

@JsonIgnoreProperties([ "metaClass" ])
public class UpdateCustomer {
    @JsonProperty("login")
    public Login login
    @JsonProperty("customerModel")
    public CustomerModel customerModel
    @JsonProperty("accountModel")
    public AccountModel accountModel
    @JsonProperty("contract")
    public String contract

    UpdateCustomer() {
        this.login = new Login()
    }
}