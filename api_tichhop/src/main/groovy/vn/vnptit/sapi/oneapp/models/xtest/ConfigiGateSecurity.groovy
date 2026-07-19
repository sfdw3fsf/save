package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class ConfigiGateSecurity {

    @JsonProperty("serialNo")
    public String serialNo
    @JsonProperty("userName")
    public String userName
    @JsonProperty("passNew")
    public String passNew
}