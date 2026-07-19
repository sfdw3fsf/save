package vn.vnptit.sapi.oneapp.models.visaapp
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.visa.application.*

@JsonIgnoreProperties([ "metaClass" ])
public class VerifyPasswordRASAccount {
    @JsonProperty("accountName")
    public String accountName
    @JsonProperty("password")
    public String password
}