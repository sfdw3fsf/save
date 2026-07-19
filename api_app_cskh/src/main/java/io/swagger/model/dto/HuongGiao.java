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
public class HuongGiao extends BaseDTO {
    @JsonProperty("huongGiaoId")
    private Long huongGiaoId = null;
    @JsonProperty("huongGiao")
    private String huongGiao = null;

    public Long getHuongGiaoId() {
        return huongGiaoId;
    }

    public void setHuongGiaoId(Long huongGiaoId) {
        this.huongGiaoId = huongGiaoId;
    }

    public String getHuongGiao() {
        return huongGiao;
    }

    public void setHuongGiao(String huongGiao) {
        this.huongGiao = huongGiao;
    }
}
