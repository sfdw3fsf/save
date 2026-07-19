package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class RecentGponOfflineByUnit {

    @JsonProperty("unitID")
    public int unitID
    @JsonProperty("BeginDate")
    public String BeginDate
    @JsonProperty("EndDate")
    public String EndDate
}