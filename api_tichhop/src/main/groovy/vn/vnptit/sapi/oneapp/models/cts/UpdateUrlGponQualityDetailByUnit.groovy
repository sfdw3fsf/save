package vn.vnptit.sapi.oneapp.models.cts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class UpdateUrlGponQualityDetailByUnit {
    @JsonProperty("unitID")
    int unitID

    @JsonProperty("statisticDate")
    String statisticDate
}
