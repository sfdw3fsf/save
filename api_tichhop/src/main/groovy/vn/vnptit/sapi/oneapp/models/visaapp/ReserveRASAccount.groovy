package vn.vnptit.sapi.oneapp.models.visaapp
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.visa.application.*

@JsonIgnoreProperties([ "metaClass" ])
public class ReserveRASAccount {
    @JsonProperty("login")
    public Login login
    @JsonProperty("accountModel")
    public AccountModel accountModel
    @JsonProperty("reserveModel")
    public ReserveModel reserveModel

    ReserveRASAccount() {
        login = new Login()
    }
}