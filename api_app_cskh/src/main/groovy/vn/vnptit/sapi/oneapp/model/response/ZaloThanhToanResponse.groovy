package vn.vnptit.sapi.oneapp.model.response
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString
import io.swagger.model.dto.FileApp

/**
 * Zalo thanh toán
 */
@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class ZaloThanhToanResponse {
    Long phanvung_id
    Long rlh_id
    Long id
    String nguon
    Date ngay_cn
    String nguoi_cn
    String khong_smp
}
