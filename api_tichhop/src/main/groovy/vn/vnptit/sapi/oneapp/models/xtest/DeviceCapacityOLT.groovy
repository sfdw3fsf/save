package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class DeviceCapacityOLT {

    @JsonProperty("ProvinceCode")
    public String ProvinceCode
    @JsonProperty("reportType")
    public int reportType
    @JsonProperty("beginDate")
    public String beginDate
    @JsonProperty("endDate")
    public String endDate
}