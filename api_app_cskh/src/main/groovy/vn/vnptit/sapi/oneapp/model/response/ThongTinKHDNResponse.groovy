package vn.vnptit.sapi.oneapp.model.response
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

/**
 * Thông tin khách hàng doanh nghiệp
 */
@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class ThongTinKHDNResponse {
    Long phanvung_id
    Long thongtin_id
    String ma_kh
    Long doituongcs_id
    String tendoituong
    String diachi
    String dienthoai
    String email
    String fax
    String ngaysinh
    Long gioitinh_id
    Long quoctich_id
    Long dantoc_id
    Long nganhnghe_id
    String trinhdo
    String bophancongtac
    String kethon
    Long quan_id
    Long phuong_id
    Long pho_id
    String so_nha
    Long donvi_id
    String may_cn
    Date ngay_cn
    String nguoi_cn
    String ghichu
    String ma_kh_cs
}
