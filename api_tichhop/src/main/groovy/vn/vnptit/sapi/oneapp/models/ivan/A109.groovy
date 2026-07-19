package vn.vnptit.sapi.oneapp.models.ivan

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical

@Canonical
@JsonIgnoreProperties(["metaClass"])
class A109 {
    String ten_dang_nhap
    String mat_khau
}
