package vn.vnptit.sapi.oneapp.models.cts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class GetONTInfoByAccount {
    @JsonProperty("provinceCode")
    String provinceCode

    @JsonProperty("accountName")
    String accountName
}
