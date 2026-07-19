package vn.vnptit.sapi.oneapp.models.visaapp
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.visa.application.*

@JsonIgnoreProperties([ "metaClass" ])
public class RetrieveAccountsbyName {

    @JsonProperty("arg0")
    public Login arg0
    @JsonProperty("arg1")
    public AccountModel arg1
}