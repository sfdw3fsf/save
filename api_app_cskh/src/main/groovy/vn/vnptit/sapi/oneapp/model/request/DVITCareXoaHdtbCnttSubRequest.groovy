package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.annotation.Validated

import javax.annotation.Generated
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-13")
@Schema
public class DVITCareXoaHdtbCnttSubRequest {


    public Integer getVhdtb_sub_id() {
        return vhdtb_sub_id;
    }

    public void setVhdtb_sub_id(Integer vhdtb_sub_id) {
        this.vhdtb_sub_id = vhdtb_sub_id;
    }

    @JsonProperty("vhdtb_sub_id")
    private Integer vhdtb_sub_id;
}
