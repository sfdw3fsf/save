package vn.vnptit.sapi.oneapp.model.response
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.swagger.model.dto.FileApp
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.annotation.Validated

import javax.annotation.Generated
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
@JsonIgnoreProperties(value = ["metaClass"])
class DsAnhNhomBHResponse {
    Long nhombh_id
    String mota
    List<FileApp> listImage
}
