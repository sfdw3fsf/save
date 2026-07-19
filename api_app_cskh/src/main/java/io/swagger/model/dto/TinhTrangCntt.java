package io.swagger.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Tình trạng công nghệ thông tin
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class TinhTrangCntt {

    @JsonProperty("suCoPtmId")
    private Long suCoPtmId;

    @JsonProperty("ptmId")
    private Long ptmId;

    @JsonProperty("tenHt")
    private String tenHt;

    @JsonProperty("capDoId")
    private Long capDoId;

    @JsonProperty("ghiChu")
    private String ghiChu;

    @JsonProperty("dsTinhTrang")
    private String dsTinhTrang;

    public Long getSuCoPtmId() {
        return suCoPtmId;
    }

    public void setSuCoPtmId(Long suCoPtmId) {
        this.suCoPtmId = suCoPtmId;
    }

    public Long getPtmId() {
        return ptmId;
    }

    public void setPtmId(Long ptmId) {
        this.ptmId = ptmId;
    }

    public String getTenHt() {
        return tenHt;
    }

    public void setTenHt(String tenHt) {
        this.tenHt = tenHt;
    }

    public Long getCapDoId() {
        return capDoId;
    }

    public void setCapDoId(Long capDoId) {
        this.capDoId = capDoId;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getDsTinhTrang() {
        return dsTinhTrang;
    }

    public void setDsTinhTrang(String dsTinhTrang) {
        this.dsTinhTrang = dsTinhTrang;
    }
}
