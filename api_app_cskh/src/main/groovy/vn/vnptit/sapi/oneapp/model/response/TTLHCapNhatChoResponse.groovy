package vn.vnptit.sapi.oneapp.model.response
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

/**
 * Thông tin liên hệ hợp đồng cập nhật cho
 */
@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class TTLHCapNhatChoResponse {
    String loai_dv
    String ma_dv
    Long ttkh_id
    Long dv_id
}
