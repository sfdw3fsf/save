package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * DanhMuc
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class LayDsTbscLoaiHinhSelRequest extends BaseDTO {
    public Long getP_phanvung_id() {
        return p_phanvung_id;
    }

    public void setP_phanvung_id(Long p_phanvung_id) {
        this.p_phanvung_id = p_phanvung_id;
    }

    public Long getP_suco_id() {
        return p_suco_id;
    }

    public void setP_suco_id(Long p_suco_id) {
        this.p_suco_id = p_suco_id;
    }

    public Long getP_loaitb_id() {
        return p_loaitb_id;
    }

    public void setP_loaitb_id(Long p_loaitb_id) {
        this.p_loaitb_id = p_loaitb_id;
    }

    @JsonProperty("p_phanvung_id")
    private Long p_phanvung_id = null;
    @JsonProperty("p_suco_id")
    private Long p_suco_id = null;
    @JsonProperty("p_loaitb_id")
    private Long p_loaitb_id = null;
}
