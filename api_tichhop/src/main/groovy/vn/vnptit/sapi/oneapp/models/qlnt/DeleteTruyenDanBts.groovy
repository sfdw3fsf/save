package vn.vnptit.sapi.oneapp.models.qlnt

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class DeleteTruyenDanBts {
    @JsonProperty("maTinhThanh")
    String maTinhThanh
    @JsonProperty("maTruyenDan")
    String maTruyenDan
}