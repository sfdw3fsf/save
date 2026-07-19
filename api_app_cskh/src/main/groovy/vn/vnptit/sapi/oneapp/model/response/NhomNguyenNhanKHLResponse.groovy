package vn.vnptit.sapi.oneapp.model.response
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

/**
 * Nhóm Nguyên nhân không hài lòng
 */
@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class NhomNguyenNhanKHLResponse {
    Long cauhoi_id
    String noidung
    String hinhthuc
    Long tieuchuan_id
    Long loaich_id
    String kieu
    String ma_ch
    Long phanvung_id
}
