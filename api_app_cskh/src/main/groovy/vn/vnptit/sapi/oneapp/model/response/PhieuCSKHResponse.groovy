package vn.vnptit.sapi.oneapp.model.response
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

/**
 * Phiếu chăm sóc khách hàng
 */
@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class PhieuCSKHResponse {
    Long phieutn_id
    Long khachhang_id
    Long hdkh_id
    String ten_kh
    String diachi_kh
    String so_dt
    Date ngaygiao
    String qt_zalo
    String ma_kh
    Long ttlh_id
    Float kinhdo
    Float vido
    String ghichu
    String mota
    String nguon
    Date ngay_cn
    String loai_nc
    String loainc_id
    String ngay_cskh
    String nguon_tt
    String thongbao
}
