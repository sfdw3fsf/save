package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class LineQualityByDevice {

    @JsonProperty("unitID")
    public String unitID
    @JsonProperty("deviceID")
    public String deviceID
    @JsonProperty("isGetAll")
    public String isGetAll
    @JsonProperty("beginDate")
    public String beginDate
    @JsonProperty("endDate")
    public String endDate
}