package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class B2A_TONGHOP_NV {
    String tungay
    String denngay
    String donvi_id
    String ma_tap
    String tt_tb
    String loaikenh
    String ma_chiendich
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class LAY_B2A_CHITIET_PHANGIAO {
    String tungay
    String denngay
    String loaikenh
    String ma_chiendich
    String tt_tb
    String ma_kh
    String sdt
}

