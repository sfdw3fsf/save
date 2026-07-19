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
public class DVITCareLayDsGVTheoPhieuIDRequest {


    public Integer getVkieu() {
        return vkieu;
    }

    public void setVkieu(Integer vkieu) {
        this.vkieu = vkieu;
    }

    public Integer getVphieu_id() {
        return vphieu_id;
    }

    public void setVphieu_id(Integer vphieu_id) {
        this.vphieu_id = vphieu_id;
    }

    @JsonProperty("vkieu")
    private Integer vkieu;

    @JsonProperty("vphieu_id")
    private Integer vphieu_id;



}
