package vn.vnptit.sapi.oneapp.model.request
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Chuyển đổi MyTV - VMP
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
class ChuyenDoiMytvVMPRequest {
    String maTb
    String kieuVmp; // 0 - MyTV thường; 1 - VMP
}
