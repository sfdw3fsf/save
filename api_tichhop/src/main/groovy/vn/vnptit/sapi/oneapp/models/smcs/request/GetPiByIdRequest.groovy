package vn.vnptit.sapi.oneapp.models.smcs.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class GetPiByIdRequest {
    @JsonProperty("pi_id")
    public String pi_id

    String getPi_id() {
        return pi_id
    }

    void setPi_id(String pi_id) {
        this.pi_id = pi_id
    }
}