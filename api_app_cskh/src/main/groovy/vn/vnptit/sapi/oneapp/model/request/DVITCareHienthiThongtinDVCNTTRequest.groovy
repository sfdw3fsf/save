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
public class DVITCareHienthiThongtinDVCNTTRequest {


    public Long getHdtb_id() {
        return hdtb_id;
    }

    public void setHdtb_id(Long hdtb_id) {
        this.hdtb_id = hdtb_id;
    }

    public Integer getVloaitb_id() {
        return vloaitb_id;
    }

    public void setVloaitb_id(Integer vloaitb_id) {
        this.vloaitb_id = vloaitb_id;
    }

    public Integer getVluong_id() {
        return vluong_id;
    }

    public void setVluong_id(Integer vluong_id) {
        this.vluong_id = vluong_id;
    }

    public Integer getVtthd_id() {
        return vtthd_id;
    }

    public void setVtthd_id(Integer vtthd_id) {
        this.vtthd_id = vtthd_id;
    }

    @JsonProperty("hdtb_id")
    private Long hdtb_id;
    @JsonProperty("vloaitb_id")
    private Integer vloaitb_id;
    @JsonProperty("vluong_id")
    private Integer vluong_id;
    @JsonProperty("vtthd_id")
    private Integer vtthd_id;
}
