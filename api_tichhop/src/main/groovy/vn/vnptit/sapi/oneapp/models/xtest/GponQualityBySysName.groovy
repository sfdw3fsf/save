package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class GponQualityBySysName {

    @JsonProperty("systemName")
    public String systemName
    @JsonProperty("beginDate")
    public String beginDate
    @JsonProperty("endDate")
    public String endDate
    @JsonProperty("attenDsThresHold")
    public String attenDsThresHold
}