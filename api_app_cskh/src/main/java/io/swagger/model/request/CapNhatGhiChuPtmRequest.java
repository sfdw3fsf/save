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
public class CapNhatGhiChuPtmRequest extends BaseDTO {
    @JsonProperty("phieuId")
    private Long phieuId = null;
    @JsonProperty("suCoPtmId")
    private Long suCoPtmId = null;
    @JsonProperty("ghiChu")
    private String ghiChu = null;

    public Long getPhieuId() {
        return phieuId;
    }

    public void setPhieuId(Long phieuId) {
        this.phieuId = phieuId;
    }

    public Long getSuCoPtmId() {
        return suCoPtmId;
    }

    public void setSuCoPtmId(Long suCoPtmId) {
        this.suCoPtmId = suCoPtmId;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

}
