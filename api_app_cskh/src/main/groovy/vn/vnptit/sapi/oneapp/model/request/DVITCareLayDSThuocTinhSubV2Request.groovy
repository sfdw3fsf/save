package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.annotation.Validated

import javax.annotation.Generated

/**
 *  Dịch vụ IT Care
 *  Danh sách phiếu yêu cầu khảo sát
 */

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-13")
@Schema
public class DVITCareLayDSThuocTinhSubV2Request {

    public Long getVhdtb_id() {
        return vhdtb_id;
    }

    public void setVhdtb_id(Long vhdtb_id) {
        this.vhdtb_id = vhdtb_id;
    }

    public Integer getVloaitb_id() {
        return vloaitb_id;
    }

    public void setVloaitb_id(Integer vloaitb_id) {
        this.vloaitb_id = vloaitb_id;
    }

    public String getvFieldNames() {
        return vFieldNames;
    }

    public void setvFieldNames(String vFieldNames) {
        this.vFieldNames = vFieldNames;
    }

    @JsonProperty("vhdtb_id")
    private Long vhdtb_id;



    @JsonProperty("vloaitb_id")
    private Integer vloaitb_id;

    @JsonProperty("vFieldNames")
    private String vFieldNames;
}
