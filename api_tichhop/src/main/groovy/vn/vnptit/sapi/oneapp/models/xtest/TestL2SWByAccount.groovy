package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class TestL2SWByAccount {

    @JsonProperty("accountName")
    public String accountName
    @JsonProperty("serviceType")
    public int serviceType
}