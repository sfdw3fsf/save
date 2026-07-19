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
public class DVITCareLayThongtinBaotriRequest {

    public Integer getVgr_id() {
        return vgr_id;
    }

    public void setVgr_id(Integer vgr_id) {
        this.vgr_id = vgr_id;
    }

    public Integer getVkieu_id() {
        return vkieu_id;
    }

    public void setVkieu_id(Integer vkieu_id) {
        this.vkieu_id = vkieu_id;
    }

    public Integer getVloai_id() {
        return vloai_id;
    }

    public void setVloai_id(Integer vloai_id) {
        this.vloai_id = vloai_id;
    }

    public Integer getVbieumau_id() {
        return vbieumau_id;
    }

    public void setVbieumau_id(Integer vbieumau_id) {
        this.vbieumau_id = vbieumau_id;
    }

    @JsonProperty("vgr_id")
    private Integer vgr_id;
    @JsonProperty("vkieu_id")
    private Integer vkieu_id;
    @JsonProperty("vloai_id")
    private Integer vloai_id;
    @JsonProperty("vbieumau_id")
    private Integer vbieumau_id;
}
