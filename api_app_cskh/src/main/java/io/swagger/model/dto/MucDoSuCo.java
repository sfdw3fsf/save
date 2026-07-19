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
public class MucDoSuCo extends BaseDTO {
    @JsonProperty("mucDoId")
    private Long mucDoId = null;
    @JsonProperty("mucDoSC")
    private String mucDoSC = null;
    @JsonProperty("canhBaoDT")
    private Long canhBaoDT = null;

    public Long getMucDoId() {
        return mucDoId;
    }

    public void setMucDoId(Long mucDoId) {
        this.mucDoId = mucDoId;
    }

    public String getMucDoSC() {
        return mucDoSC;
    }

    public void setMucDoSC(String mucDoSC) {
        this.mucDoSC = mucDoSC;
    }

    public Long getCanhBaoDT() {
        return canhBaoDT;
    }

    public void setCanhBaoDT(Long canhBaoDT) {
        this.canhBaoDT = canhBaoDT;
    }
}
