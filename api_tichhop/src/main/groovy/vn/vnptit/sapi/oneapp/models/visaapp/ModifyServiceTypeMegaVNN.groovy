package vn.vnptit.sapi.oneapp.models.visaapp
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.visa.application.*

@JsonIgnoreProperties([ "metaClass" ])
public class ModifyServiceTypeMegaVNN {
    @JsonProperty("login")
    public Login login
    @JsonProperty("accountModel")
    public AccountModel accountModel
    @JsonProperty("accountServiceModel")
    public AccountServiceModel accountServiceModel
    @JsonProperty("ip")
    public String ip
    @JsonProperty("autoIpFlag")
    public boolean autoIpFlag
    @JsonProperty("oldIp")
    public boolean oldIp

    ModifyServiceTypeMegaVNN() {
        login = new Login()
    }
}