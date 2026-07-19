package io.swagger.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-23")
@Schema
public class BaoCaoChiTietKSRequest {

    private Long p_tbtl_id;

    private Long p_nhomch_id;

    public Long getP_tbtl_id() {
        return p_tbtl_id;
    }

    public void setP_tbtl_id(Long p_tbtl_id) {
        this.p_tbtl_id = p_tbtl_id;
    }

    public Long getP_nhomch_id() {
        return p_nhomch_id;
    }

    public void setP_nhomch_id(Long p_nhomch_id) {
        this.p_nhomch_id = p_nhomch_id;
    }
}
