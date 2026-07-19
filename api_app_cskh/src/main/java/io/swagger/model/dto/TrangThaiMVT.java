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
public class TrangThaiMVT extends BaseDTO {
    @JsonProperty("suCoPtmId")
    private Long suCoPtmId = null;
    @JsonProperty("loaiPtmId")
    private Long loaiPtmId = null;
    @JsonProperty("ptmId")
    private Long ptmId = null;
    @JsonProperty("tenPtm")
    private String tenPtm = null;
    @JsonProperty("capDoId")
    private Long capDoId = null;
    @JsonProperty("ghiChu")
    private String ghiChu = null;
    @JsonProperty("loaiPtm")
    private String loaiPtm = null;
    @JsonProperty("congSoi")
    private String congSoi = null;
    @JsonProperty("dsTinhTrang")
    private String dsTinhTrang = null;

    public Long getSuCoPtmId() {
        return suCoPtmId;
    }

    public void setSuCoPtmId(Long suCoPtmId) {
        this.suCoPtmId = suCoPtmId;
    }

    public Long getLoaiPtmId() {
        return loaiPtmId;
    }

    public void setLoaiPtmId(Long loaiPtmId) {
        this.loaiPtmId = loaiPtmId;
    }

    public Long getPtmId() {
        return ptmId;
    }

    public void setPtmId(Long ptmId) {
        this.ptmId = ptmId;
    }

    public String getTenPtm() {
        return tenPtm;
    }

    public void setTenPtm(String tenPtm) {
        this.tenPtm = tenPtm;
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

    public String getLoaiPtm() {
        return loaiPtm;
    }

    public void setLoaiPtm(String loaiPtm) {
        this.loaiPtm = loaiPtm;
    }

    public String getCongSoi() {
        return congSoi;
    }

    public void setCongSoi(String congSoi) {
        this.congSoi = congSoi;
    }

    public String getDsTinhTrang() {
        return dsTinhTrang;
    }

    public void setDsTinhTrang(String dsTinhTrang) {
        this.dsTinhTrang = dsTinhTrang;
    }
}
