package io.swagger.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-23")
@Schema
public class XoaCauHoiRequest {
    private Long p_cauhoi_id;

    public Long getP_cauhoi_id() {
        return p_cauhoi_id;
    }

    public void setP_cauhoi_id(Long p_cauhoi_id) {
        this.p_cauhoi_id = p_cauhoi_id;
    }
}
