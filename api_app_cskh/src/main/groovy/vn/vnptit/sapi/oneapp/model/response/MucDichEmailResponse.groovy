package vn.vnptit.sapi.oneapp.model.response
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

/**
 * Mục đích email
 */
@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class MucDichEmailResponse {
    Long phanvung_id
    Long rlh_id
    Long hdlh_id
    String email
    String kieu
    String kieu_yc
    String ghichu
    Long mucdich_id
}
