package vn.vnptit.sapi.oneapp.models.qlts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import javax.validation.constraints.NotNull

@JsonIgnoreProperties(["metaClass"])
class ASSET_SUPPLIES_CATEGORY_ITEM {
    @JsonProperty("code")
    @NotNull
    public String code
}
