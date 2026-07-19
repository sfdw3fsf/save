package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class GponQualityByUnit {

    @JsonProperty("unitID")
    public int unitID
    @JsonProperty("fromDate")
    public String fromDate
    @JsonProperty("toDate")
    public String toDate
}