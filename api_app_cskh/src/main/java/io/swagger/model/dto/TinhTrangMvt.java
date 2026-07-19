package io.swagger.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Tình trạng mạng viễn thông
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class TinhTrangMvt {

    @JsonProperty("suCoPtmId")
    private Long suCoPtmId;

    @JsonProperty("loaiPtmId")
    private Long loaiPtmId;

    @JsonProperty("ptmId")
    private Long ptmId;

    @JsonProperty("tenPtm")
    private String tenPtm;

    @JsonProperty("capDoId")
    private Long capDoId;

    @JsonProperty("ghiChu")
    private String ghiChu;

    @JsonProperty("loaiPtm")
    private String loaiPtm;

    @JsonProperty("congSoi")
    private String congSoi;

    @JsonProperty("dsTinhTrang")
    private String dsTinhTrang;

    @JsonProperty("dsTenTinhTrang")
    private String dsTenTinhTrang;

    public String getDsTenTinhTrang() {
        return dsTenTinhTrang;
    }

    public void setDsTenTinhTrang(String dsTenTinhTrang) {
        this.dsTenTinhTrang = dsTenTinhTrang;
    }

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
