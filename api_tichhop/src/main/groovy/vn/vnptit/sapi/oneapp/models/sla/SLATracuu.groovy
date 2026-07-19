package vn.vnptit.sapi.oneapp.models.sla

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Canonical
@JsonIgnoreProperties(["metaClass"])
class SLATracuu {
    @JsonProperty("Key")
    String Key
    @JsonProperty("Condition")
    String Condition
}