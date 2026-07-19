package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class GetNonTrafficFiberVNN {

    @JsonProperty("UnitID")
    public int UnitID
    @JsonProperty("fromDate")
    public String fromDate
    @JsonProperty("toDate")
    public String toDate
    @JsonProperty("backgroundTraffic")
    public int backgroundTraffic
}