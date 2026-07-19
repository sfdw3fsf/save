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
public class DVITCareLayThongtinBaotriLienheRequest {


    public Integer getVkieu_id() {
        return vkieu_id;
    }

    public void setVkieu_id(Integer vkieu_id) {
        this.vkieu_id = vkieu_id;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public Integer getVloai_id() {
        return vloai_id;
    }

    public void setVloai_id(Integer vloai_id) {
        this.vloai_id = vloai_id;
    }

    @JsonProperty("vkieu_id")
    private Integer vkieu_id;
    @JsonProperty("vid")
    private Integer vid;
    @JsonProperty("vloai_id")
    private Integer vloai_id;
}
