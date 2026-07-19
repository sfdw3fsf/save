package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class ComposeRuntimeByProvince {

    @JsonProperty("provinceCode")
    public String provinceCode
    @JsonProperty("statisticDate")
    public String statisticDate
    @JsonProperty("Service")
    public int Service
}