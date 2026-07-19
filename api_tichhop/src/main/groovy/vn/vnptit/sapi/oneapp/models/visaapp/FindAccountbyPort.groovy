package vn.vnptit.sapi.oneapp.models.visaapp
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.visa.application.*

@JsonIgnoreProperties([ "metaClass" ])
public class FindAccountbyPort {

    @JsonProperty("arg0")
    public Login arg0
    @JsonProperty("arg1")
    public long arg1
    @JsonProperty("arg2")
    public long arg2
    @JsonProperty("arg3")
    public String arg3
    @JsonProperty("arg4")
    public String arg4
    @JsonProperty("arg5")
    public String arg5
    @JsonProperty("arg6")
    public String arg6
    @JsonProperty("arg7")
    public String arg7
    @JsonProperty("arg8")
    public String arg8
    @JsonProperty("arg9")
    public String arg9
}