package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.annotation.Validated

import javax.annotation.Generated

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-13")
@Schema
public class DVITCareHuyGiaoPhieuPhoihopRequest {

    @JsonProperty("vphieu_id")
    private Integer vphieu_id;

    public Integer getVphieu_id() {
        return vphieu_id;
    }

    public void setVphieu_id(Integer vphieu_id) {
        this.vphieu_id = vphieu_id;
    }
}
