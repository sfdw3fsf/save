package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.dto.AnhChupSuCoSMP;
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
public class DVITCareDanhmucTracuuKHDonhangSMPRequest {

    @JsonProperty("vkieu")
    private Integer vkieu;

    @JsonProperty("vinput_id")
    private String vinput_id;

    public Integer getVkieu() {
        return vkieu;
    }

    public void setVkieu(Integer vkieu) {
        this.vkieu = vkieu;
    }

    public String getVinput_id() {
        return vinput_id;
    }

    public void setVinput_id(String vinput_id) {
        this.vinput_id = vinput_id;
    }
}
