package vn.vnptit.sapi.oneapp.models.ca

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class GuiYeuCau {
    @JsonProperty("input")
    String input
}