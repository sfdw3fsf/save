package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class GetOnuSerialByAccount {

    @JsonProperty("ProvinceCode")
    public String ProvinceCode
    @JsonProperty("Account")
    public String Account
}