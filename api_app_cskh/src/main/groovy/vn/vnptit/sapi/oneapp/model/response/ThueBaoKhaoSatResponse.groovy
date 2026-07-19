package vn.vnptit.sapi.oneapp.model.response
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString
import io.swagger.model.dto.FileApp

/**
 * Thuê bao khảo sát
 */
@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class ThueBaoKhaoSatResponse {
    Long thuebao_id
    String ma_tb
    Long loaitb_id
    Long dichvuvt_id
    String loaihinh_tb
    Long phieutn_id
    Long camnhankh_id
    Long cauhoi_id
    Long traloi_id
    String tocdo_up
    String tocdo_down
    String suyhao_up
    String suyhao_down
    String congsuat_wifi
    String mota
    String tocdo3g_up
    String tocdo3g_down
    String mota3g
    String tocdo4g_up
    String tocdo4g_down
    String mota4g
    String ghichu
    String nd_cauhoi
    String nd_traloi
    String camnhankh
    List<FileApp> listImage
    List<FileApp> listImage3g
    List<FileApp> listImage4g
}
