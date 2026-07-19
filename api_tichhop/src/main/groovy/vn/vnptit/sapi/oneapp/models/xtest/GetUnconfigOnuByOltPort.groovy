package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class GetUnconfigOnuByOltPort {

    @JsonProperty("deviceIp")
    public String deviceIp
    @JsonProperty("frame")
    public int frame
    @JsonProperty("slot")
    public int slot
    @JsonProperty("port")
    public int port
}