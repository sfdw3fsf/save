package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.annotation.Validated

import javax.annotation.Generated

/**
 *  Dịch vụ IT Care
 *  4. Xem trước mẫu file
 */

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-13")
@Schema
public class DVITCareHiethiThongtinDVCNTTBDDVRequest {


    public Integer getIn_thuebao_id() {
        return in_thuebao_id;
    }

    public void setIn_thuebao_id(Integer in_thuebao_id) {
        this.in_thuebao_id = in_thuebao_id;
    }

    public Integer getVloaitb_id() {
        return vloaitb_id;
    }

    public void setVloaitb_id(Integer vloaitb_id) {
        this.vloaitb_id = vloaitb_id;
    }

    @JsonProperty("in_thuebao_id")
    private Integer in_thuebao_id;
    @JsonProperty("vloaitb_id")
    private Integer vloaitb_id;
}
