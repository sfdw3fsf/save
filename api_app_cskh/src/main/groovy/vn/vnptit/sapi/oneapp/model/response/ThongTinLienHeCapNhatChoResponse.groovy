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
class ThongTinLienHeCapNhatChoResponse {
    String ma_dv
    String loai_dv
    Long ttkh_id
    Long db_id
    Long soluong
    Boolean checked
}
