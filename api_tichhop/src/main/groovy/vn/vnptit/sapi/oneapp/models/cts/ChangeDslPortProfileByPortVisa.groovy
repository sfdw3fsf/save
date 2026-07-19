package vn.vnptit.sapi.oneapp.models.cts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class ChangeDslPortProfileByPortVisa {
    @JsonProperty("visaString")
    String visaString

    @JsonProperty("profileName")
    String profileName
}
