package vn.vnptit.sapi.oneapp.model.request
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.annotation.Validated

import javax.annotation.Generated

/**
 * Cập nhật thông tin địa chỉ
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
class CapNhatThongTinDiaChiRequest {
    Long phieutn_id
    Long thuebao_id
    String diachi
    String sonha
    Long quan_id
    Long phuong_id
    Long pho_id
    Long khu_id
    Long ap_id
    Long dacdiem_id
    Float kinhdo
    Float vido
    String ghichu
//    kieuld_id in number, -- 75 : thay doi thong tin thue bao, 18 thay doi thong tin thanh toan
}
