package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class DeviceCapacityGpon {

    @JsonProperty("unitID")
    public String unitID
    @JsonProperty("beginDate")
    public String beginDate
    @JsonProperty("endDate")
    public String endDate
    @JsonProperty("month")
    public int month
    @JsonProperty("quater")
    public int quater
    @JsonProperty("year")
    public int year
    @JsonProperty("reportType")
    public int reportType
}