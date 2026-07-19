package vn.vnptit.sapi.oneapp.model.response
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

/**
 * Thông tin liên hệ hợp đồng
 */
@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class ThongTinLienHeHopDongResponse {
    Long rlh_id
    String giatri
    Long loai_id
    Long ttkh_id
    Long hd_id
    Long dv_id
    String nhom
    String mucdich
    String ten_loai
}
