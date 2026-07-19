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
public class QuyTrinh extends BaseDTO {
    @JsonProperty("quyTrinhId")
    private Long quyTrinhId = null;
    @JsonProperty("quyTrinh")
    private String quyTrinh = null;

    public Long getQuyTrinhId() {
        return quyTrinhId;
    }

    public void setQuyTrinhId(Long quyTrinhId) {
        this.quyTrinhId = quyTrinhId;
    }

    public String getQuyTrinh() {
        return quyTrinh;
    }

    public void setQuyTrinh(String quyTrinh) {
        this.quyTrinh = quyTrinh;
    }
}
