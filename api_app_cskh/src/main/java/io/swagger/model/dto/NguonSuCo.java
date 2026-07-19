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
public class NguonSuCo extends BaseDTO {
    @JsonProperty("nguonSuCoId")
    private Long nguonSuCoId = null;
    @JsonProperty("moTa")
    private String moTa = null;

    public Long getNguonSuCoId() {
        return nguonSuCoId;
    }

    public void setNguonSuCoId(Long nguonSuCoId) {
        this.nguonSuCoId = nguonSuCoId;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
