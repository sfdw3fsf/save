package vn.vnptit.sapi.oneapp.models.visaapp
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.visa.application.*

@JsonIgnoreProperties([ "metaClass" ])
public class ModifyTariffType {
    @JsonProperty("login")
    public Login login
    @JsonProperty("accountModel")
    public AccountModel accountModel
    @JsonProperty("accountServiceModel")
    public AccountServiceModel accountServiceModel
    @JsonProperty("effectType")
    public String effectType

    ModifyTariffType() {
        this.login = new Login()
    }
}