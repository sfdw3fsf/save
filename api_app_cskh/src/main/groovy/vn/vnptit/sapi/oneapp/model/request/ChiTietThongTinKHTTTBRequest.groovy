package vn.vnptit.sapi.oneapp.model.request
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.annotation.Validated

import javax.annotation.Generated

/**
 *  Hàm lấy chi thiết thông tin KH/TT/TB
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
class ChiTietThongTinKHTTTBRequest {
    Integer p_db_id
    Integer p_loai
    Integer p_kieu 
}
