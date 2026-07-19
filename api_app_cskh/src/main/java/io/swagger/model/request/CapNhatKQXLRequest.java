package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Báo tồn
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class CapNhatKQXLRequest {
    @JsonProperty("phieuId")
    private Long phieuId;
    @JsonProperty("kqxlId")
    private Long kqxlId;
    @JsonProperty("lyDoId")
    private Long lyDoId;
    @JsonProperty("ghiChu")
    private String ghiChu;

    public Long getPhieuId() {
        return phieuId;
    }

    public void setPhieuId(Long phieuId) {
        this.phieuId = phieuId;
    }

    public Long getKqxlId() {
        return kqxlId;
    }

    public void setKqxlId(Long kqxlId) {
        this.kqxlId = kqxlId;
    }

    public Long getLyDoId() {
        return lyDoId;
    }

    public void setLyDoId(Long lyDoId) {
        this.lyDoId = lyDoId;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
