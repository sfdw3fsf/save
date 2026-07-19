package vn.vnptit.sapi.oneapp.model.response
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

/**
 * Thông tin liên hệ
 */
@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class ThongTinPhieuCSKH36Response {
    Long phanvung_id
    Long rid
    Long thuebao_id
    Long baohong_id
    Long phieu_id
    String sticker_bh
    Long hinhthuc_id
    Long thongtin_id
    String chatluong_dv
    String ketqua_mytv
    String ketqua_stb
    Long hd_diachi_id
    Long hd_lienhe_id
    Long hdtb_id
    String dieu_bd
    String dieu_bh
    Long ttks_id
    Long tapkh_id
    Long thongtinkt_id
    Long thongtinit_id
    Long tbtl_id
    String ten_chatluongdv
    String hinhthuc
}
