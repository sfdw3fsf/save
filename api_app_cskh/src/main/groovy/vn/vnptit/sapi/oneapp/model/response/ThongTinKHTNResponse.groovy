package vn.vnptit.sapi.oneapp.model.response
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

/**
 * Thông tin khách hàng tiềm năng
 */
@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class ThongTinKHTNResponse {
    Long khachhang_id
    String ma_kh
    String ten_kh
    String diachi_kh
    String so_dt
    String so_gt
    Date ngaycap
    String noicap
    Long loaigt_id
    Long diachi_id
    Long tinh_id
    Long quan_id
    Long phuong_id
    Long pho_id
    Long ap_id
    Long khu_id
    String sonha
    Long dacdiem_id
    String tentinh
    String ten_quan
    String ten_pho
    String ten_ap
    String ten_khu
    String dacdiem
}
