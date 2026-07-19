package vn.vnptit.sapi.oneapp.models.visaapp
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.visa.application.*

@JsonIgnoreProperties([ "metaClass" ])
public class RegisterMailPlus {

    @JsonProperty("arg0")
    public Login arg0
    @JsonProperty("arg1")
    public CustomerModel arg1
    @JsonProperty("arg2")
    public AccountModel[] arg2
    @JsonProperty("arg3")
    public AccountServiceModel[] arg3
    @JsonProperty("arg4")
    public Object[] arg4
    @JsonProperty("arg5")
    public long arg5
    @JsonProperty("arg6")
    public String arg6
}