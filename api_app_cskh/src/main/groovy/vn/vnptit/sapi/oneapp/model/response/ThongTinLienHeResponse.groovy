package vn.vnptit.sapi.oneapp.model.response
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

/**
 * Thông tin liên hệ
 */
@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class ThongTinLienHeResponse {
    Long phanvung_id
    Long hdlh_id
    String ten_lh
    String diachi
    String dienthoai
    String fax
    String email
    String donvi
    Long ttlh_id
    Long hdkh_id
    String kieu_yc
    Long doituongcs_id
}
