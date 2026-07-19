package vn.vnptit.sapi.oneapp.models.visaapp
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.visa.application.*

@JsonIgnoreProperties([ "metaClass" ])
public class ModifyStatusDomain {

    @JsonProperty("arg0")
    public Login arg0
    @JsonProperty("arg1")
    public String arg1
    @JsonProperty("arg2")
    public String arg2
    @JsonProperty("arg3")
    public String arg3
}