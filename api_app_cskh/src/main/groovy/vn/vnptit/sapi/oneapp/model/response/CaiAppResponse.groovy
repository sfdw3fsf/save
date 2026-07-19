package vn.vnptit.sapi.oneapp.model.response
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

/**
 * Chăm sóc khách hàng 36 tháng
 */
@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class CaiAppResponse {
    Long phanvung_id
    Long rid
    String so_dt
    String loai_app
    String ten_loaiapp
}
