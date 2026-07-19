package vn.vnptit.sapi.oneapp.models.qlts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import javax.validation.constraints.NotNull

@JsonIgnoreProperties(["metaClass"])
class ASSET_SCAN_INFO {
    @JsonProperty("type")
    @NotNull
    public String type
    @JsonProperty("value")
    @NotNull
    public String value
}
