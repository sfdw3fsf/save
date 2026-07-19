package vn.vnptit.sapi.oneapp.model.response
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

/**
 * Nguyên nhân không hài lòng
 */
@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class NguyenNhanKHLResponse {
    Long cauhoi_id
    Long traloi_id
    String thutu
    Long rch_id
    Long loaidv_id
    String dieulai
    String ma_hl
    Long tndv_id
    String hienthi
    String dieu_xn
    String dieu_cs
    Long phanvung_id
    String noidung
    String loai
    String kieu
    String ma_tl
}
