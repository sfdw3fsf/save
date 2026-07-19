package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class StatisticOLT {

    @JsonProperty("unitID")
    public String unitID
    @JsonProperty("statisticDate")
    public String statisticDate
}