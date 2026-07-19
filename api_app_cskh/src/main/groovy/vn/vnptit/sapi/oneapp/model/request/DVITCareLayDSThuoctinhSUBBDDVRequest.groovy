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
public class DVITCareLayDSThuoctinhSUBBDDVRequest {

    public Long getVthuebao_id() {
        return vthuebao_id;
    }

    public void setVthuebao_id(Long vthuebao_id) {
        this.vthuebao_id = vthuebao_id;
    }

    public Integer getVloaitb_id() {
        return vloaitb_id;
    }

    public void setVloaitb_id(Integer vloaitb_id) {
        this.vloaitb_id = vloaitb_id;
    }

    public String getVappend_name() {
        return vappend_name;
    }

    public void setVappend_name(String vappend_name) {
        this.vappend_name = vappend_name;
    }

    @JsonProperty("vthuebao_id")
    private Long vthuebao_id;

    @JsonProperty("vloaitb_id")
    private Integer vloaitb_id;

    @JsonProperty("vappend_name")
    private String vappend_name;
}