package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class ChangeDslPortProfileByPortVisa {

    @JsonProperty("visaString")
    public String visaString
    @JsonProperty("profileName")
    public String profileName
}