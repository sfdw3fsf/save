package vn.vnptit.sapi.oneapp.models.visaapp
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.visa.application.*

@JsonIgnoreProperties([ "metaClass" ])
public class ChangePlanId {
    @JsonProperty("login")
    public Login login
    @JsonProperty("accountModel")
    public AccountModel accountModel
    @JsonProperty("planId")
    public String planId
    @JsonProperty("note")
    public String note

    ChangePlanId() {
        this.login = new Login()
    }
}