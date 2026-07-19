package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class SetPortStatus {

    @JsonProperty("systemName")
    public String systemName
    @JsonProperty("communityString")
    public String communityString
    @JsonProperty("port")
    public String port
    @JsonProperty("status")
    public int status
}