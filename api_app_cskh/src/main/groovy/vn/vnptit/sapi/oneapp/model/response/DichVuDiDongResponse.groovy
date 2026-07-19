package vn.vnptit.sapi.oneapp.model.response
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString
import io.swagger.model.dto.FileApp

/**
 * Dịch vụ tiềm năng
 */
@Canonical
@ToString(includeFields = true, includeNames = true)
class DichVuDiDongResponse {
    Long phanvung_id
    Long tiemnangdv_id
    String so_dt
    Long chuquan_id
    String kieu_tt
    String tenchuquan
    String ten_kieutt
}
