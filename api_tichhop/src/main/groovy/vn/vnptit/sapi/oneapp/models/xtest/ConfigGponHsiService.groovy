package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class ConfigGponHsiService {

    @JsonProperty("deviceIp")
    public String deviceIp
    @JsonProperty("frame")
    public int frame
    @JsonProperty("slot")
    public int slot
    @JsonProperty("port")
    public int port
    @JsonProperty("onuIndex")
    public int onuIndex
    @JsonProperty("tcontProfile")
    public String tcontProfile
    @JsonProperty("trafficProfileUS")
    public String trafficProfileUS
    @JsonProperty("trafficProfileDS")
    public String trafficProfileDS
    @JsonProperty("svlan")
    public int svlan
    @JsonProperty("pppUser")
    public String pppUser
    @JsonProperty("pppPassword")
    public String pppPassword
}