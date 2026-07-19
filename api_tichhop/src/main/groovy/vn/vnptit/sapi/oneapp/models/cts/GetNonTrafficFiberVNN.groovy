package vn.vnptit.sapi.oneapp.models.cts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class GetNonTrafficFiberVNN {
    @JsonProperty("unitID")
    int unitID

    @JsonProperty("fromDate")
    String fromDate

    @JsonProperty("toDate")
    String toDate

    @JsonProperty("backgroundTraffic")
    int backgroundTraffic
}
