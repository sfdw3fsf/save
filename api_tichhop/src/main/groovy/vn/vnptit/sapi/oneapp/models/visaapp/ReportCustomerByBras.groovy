package vn.vnptit.sapi.oneapp.models.visaapp
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.visa.application.*

@JsonIgnoreProperties([ "metaClass" ])
public class ReportCustomerByBras {

    @JsonProperty("arg0")
    public Login arg0
    @JsonProperty("arg1")
    public String arg1
    @JsonProperty("arg2")
    public long arg2
    @JsonProperty("arg3")
    public long arg3
    @JsonProperty("arg4")
    public String arg4
    @JsonProperty("arg5")
    public int arg5
    @JsonProperty("arg6")
    public int arg6
    @JsonProperty("arg7")
    public int arg7
    @JsonProperty("arg8")
    public int arg8
    @JsonProperty("arg9")
    public int arg9
    @JsonProperty("arg10")
    public int arg10
    @JsonProperty("arg11")
    public String arg11
    @JsonProperty("arg12")
    public int arg12
}