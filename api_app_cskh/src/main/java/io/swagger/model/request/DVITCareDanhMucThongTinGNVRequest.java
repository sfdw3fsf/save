package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 *  Dịch vụ IT Care
 *  Danh sách phiếu yêu cầu khảo sát
 */

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-13")
@Schema
public class DVITCareDanhMucThongTinGNVRequest {


    public Integer getVkieu() {
        return vkieu;
    }

    public void setVkieu(Integer vkieu) {
        this.vkieu = vkieu;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getVid2() {
        return vid2;
    }

    public void setVid2(String vid2) {
        this.vid2 = vid2;
    }

    @JsonProperty("vkieu")
    private Integer vkieu;

    @JsonProperty("vid")
    private String vid;

    @JsonProperty("vid2")
    private String vid2;


}
