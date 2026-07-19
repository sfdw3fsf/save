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
public class DVITCareLayThongtinBaotriTonghopRequest {


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

    @JsonProperty("vkieu_id")
    private Integer vkieu_id;
    @JsonProperty("vid")
    private Integer vid;
}
