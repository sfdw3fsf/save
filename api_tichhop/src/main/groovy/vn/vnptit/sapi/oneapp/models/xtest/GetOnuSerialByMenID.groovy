package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class GetOnuSerialByMenID {

    @JsonProperty("ProvinceCode")
    public String ProvinceCode
    @JsonProperty("MenID")
    public String MenID
}