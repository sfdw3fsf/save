package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.annotation.Validated

import javax.annotation.Generated

/**
 * Cập nhật dịch kết quả tư vấn
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class DVITCareDSDonvXulyRequest {

    @JsonProperty("vloaidv_id")
    private Integer vloaidv_id;

    public Integer getVloaidv_id() {
        return vloaidv_id;
    }

    public void setVloaidv_id(Integer vloaidv_id) {
        this.vloaidv_id = vloaidv_id;
    }
}