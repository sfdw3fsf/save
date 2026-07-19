package vn.vnptit.sapi.oneapp.models.edu

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class SllHocSinh {
    @JsonProperty("data")
    List<SLL> data
}

@JsonIgnoreProperties(["metaClass"])
class SLL{
    @JsonProperty("lop_hoc_id")
    String lop_hoc_id
    @JsonProperty("truong_id")
    String truong_id
    @JsonProperty("hoc_sinh_id")
    String hoc_sinh_id
    @JsonProperty("sll_mobile")
    String sll_mobile
    @JsonProperty("goi_cuoc_id")
    String goi_cuoc_id
}