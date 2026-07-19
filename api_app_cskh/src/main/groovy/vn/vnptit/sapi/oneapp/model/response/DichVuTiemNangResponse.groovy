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
@JsonIgnoreProperties(value = ["metaClass"])
class DichVuTiemNangResponse {
    Long tiemnangdv_id
    Long phieutn_id
    Long khachhang_id
    Long loaitb_id
    String trangthai
    Long chungloaitv_id
    String modeltv
    String nammuatv
    Long chuquan_id
    Long chatluongdv_id
    String tocdo_up
    String tocdo_down
    String thang_ktdc
    String mota
    String ghichu
    String kieu_tt
    String loaihinh_tb
    String chungloai_tv
    String tenchuquan
    String ten_trangthai
    String ten_kieutt
    String chatluong_dv
    List<FileApp> listImage
}
