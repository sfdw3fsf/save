package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class GetNonTrafficMegaVNN {

    @JsonProperty("provinceCode")
    public String provinceCode
    @JsonProperty("fromDate")
    public String fromDate
    @JsonProperty("toDate")
    public String toDate
    @JsonProperty("backgroundTraffic")
    public int backgroundTraffic
}