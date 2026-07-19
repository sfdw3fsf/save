package vn.vnptit.sapi.oneapp.model.request
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.annotation.Validated

import javax.annotation.Generated

/**
 * Cập nhật Zalo thanh toán
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
class CapNhatZaloThanhToanRequest {
    Long phieutn_id
    Long khong_smp // => truyền 1 nếu check không sd smp; else 0
    String so_dt //=> số zalo
    Long quantam //=> dựa theo check quan tâm trên form --0: chua quan tam, 1 da quan tam Zalo VNPT
}
