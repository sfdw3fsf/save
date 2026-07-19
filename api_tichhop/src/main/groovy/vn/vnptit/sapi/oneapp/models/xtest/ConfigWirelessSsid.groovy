package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class ConfigWirelessSsid {

    @JsonProperty("serialNo")
    public String serialNo
    @JsonProperty("passNew")
    public String passNew
    @JsonProperty("ssid")
    public String ssid
    @JsonProperty("wLanInterface")
    public String wLanInterface
    @JsonProperty("securityStatus")
    public String securityStatus
    @JsonProperty("authentication")
    public String authentication
    @JsonProperty("encryption")
    public String encryption
}