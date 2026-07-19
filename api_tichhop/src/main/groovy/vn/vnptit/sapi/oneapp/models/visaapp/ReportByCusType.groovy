package vn.vnptit.sapi.oneapp.models.visaapp
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.visa.application.*

@JsonIgnoreProperties([ "metaClass" ])
public class ReportByCusType {

    @JsonProperty("arg0")
    public Login arg0
    @JsonProperty("arg1")
    public String arg1
    @JsonProperty("arg2")
    public String arg2
    @JsonProperty("arg3")
    public String arg3
    @JsonProperty("arg4")
    public String arg4
    @JsonProperty("arg5")
    public String arg5
    @JsonProperty("arg6")
    public int arg6
}