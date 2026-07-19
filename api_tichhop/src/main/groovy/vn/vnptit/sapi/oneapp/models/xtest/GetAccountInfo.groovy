package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class GetAccountInfo {

    @JsonProperty("SearchText")
    public String SearchText
    @JsonProperty("SearchBy")
    public String SearchBy
}