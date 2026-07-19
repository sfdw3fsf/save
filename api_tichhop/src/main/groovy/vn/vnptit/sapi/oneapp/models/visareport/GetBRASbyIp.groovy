package vn.vnptit.sapi.oneapp.models.visareport
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import client.ws.visa.report.*

@JsonIgnoreProperties([ "metaClass" ])
public class GetBRASbyIp {

    @JsonProperty("arg0")
    public Login arg0
    @JsonProperty("arg1")
    public String arg1
}