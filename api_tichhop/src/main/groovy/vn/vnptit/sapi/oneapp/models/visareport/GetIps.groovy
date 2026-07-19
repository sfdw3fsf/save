package vn.vnptit.sapi.oneapp.models.visareport
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import client.ws.visa.report.*

@JsonIgnoreProperties([ "metaClass" ])
public class GetIps {
    @JsonProperty("login")
    public Login login
    @JsonProperty("brasId")
    public long brasId
    @JsonProperty("serviceId")
    public long serviceId

    GetIps() {
        this.login = new Login()
    }
}