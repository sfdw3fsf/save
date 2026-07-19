package vn.vnptit.sapi.oneapp.model.request
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.annotation.Validated

import javax.annotation.Generated

/**
 * Cập nhật dịch vụ tiềm năng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
class CapNhatDichVuTiemNangRequest {
    Long tiemnangdv_id // /* -1 => Them moi  */
    Long phieutn_id
    Long khachhang_id
    Long loaitb_id
    Long trangthai_id
    Long chungloaitv_id
    String modeltv
    Long nammuatv
    Long chuquan_id
    Long chatluongdv_id
    Float tocdo_up
    Float tocdo_down
    Long kieu_tt
    String thang_ktdc
    String mota
    String ghichu
    List<DiDong> didongs
}

class DiDong {
    String so_dt
    Long chuquan_id
    Long kieu_tt

//    @Override
//    public String toString() {
//        return "{\"so_dt\":\"${so_dt}\",\"chuquan_id\":${chuquan_id},\"kieu_tt\":${kieu_tt}}"
//    }
}
