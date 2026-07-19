package vn.vnptit.sapi.oneapp.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class taophieubaohanh_req {
    @JsonProperty
    List ListBHChiTietBaoHanh
    @JsonProperty
    String SoPhieuDHSXKD
    @JsonProperty
    String MaDonVi
    @JsonProperty
    Integer DoiTacId
}
