package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class GetTestHistoryByAccount {

    @JsonProperty("accountName")
    public String accountName
    @JsonProperty("serviceType")
    public int serviceType
    @JsonProperty("fromDate")
    public String fromDate
    @JsonProperty("toDate")
    public String toDate
}