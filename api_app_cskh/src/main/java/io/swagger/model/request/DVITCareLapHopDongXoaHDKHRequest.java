package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 *  Dịch vụ IT Care
 */

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-13")
@Schema
public class DVITCareLapHopDongXoaHDKHRequest {

    @JsonProperty("vhdkh_id")
    private Integer vhdkh_id;

    @JsonProperty("vloaihd_id")
    private Integer vloaihd_id;

    public Integer getVhdkh_id() {
        return vhdkh_id;
    }

    public void setVhdkh_id(Integer vhdkh_id) {
        this.vhdkh_id = vhdkh_id;
    }

    public Integer getVloaihd_id() {
        return vloaihd_id;
    }

    public void setVloaihd_id(Integer vloaihd_id) {
        this.vloaihd_id = vloaihd_id;
    }

}
