package io.swagger.model.dto;

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
public class TrangThaiSuCo extends BaseDTO {
    @JsonProperty("ttscId")
    private Long ttscId = null;
    @JsonProperty("trangThaiSc")
    private String trangThaiSc = null;

    public Long getTtscId() {
        return ttscId;
    }

    public void setTtscId(Long ttscId) {
        this.ttscId = ttscId;
    }

    public String getTrangThaiSc() {
        return trangThaiSc;
    }

    public void setTrangThaiSc(String trangThaiSc) {
        this.trangThaiSc = trangThaiSc;
    }
}
