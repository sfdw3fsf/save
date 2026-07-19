package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
public class SetPasswordForRoot {
    @JsonProperty("external_id")
    String external_id
    @JsonProperty("subscirber_pass")
    String subscirber_pass
}