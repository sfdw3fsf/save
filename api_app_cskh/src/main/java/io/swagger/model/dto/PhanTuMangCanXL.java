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
public class PhanTuMangCanXL extends BaseDTO {
    @JsonProperty("loaiPtm")
    private String loaiPtm = null;
    @JsonProperty("tenPtm")
    private String tenPtm = null;
    @JsonProperty("tinhTrang")
    private String tinhTrang = null;
    @JsonProperty("ghiChuTt")
    private String ghiChuTt = null;
    @JsonProperty("ghiChuHc")
    private String ghiChuHc = null;
    @JsonProperty("ngayCn")
    private String ngayCn = null;
    @JsonProperty("nguoiCn")
    private String nguoiCn = null;
    @JsonProperty("suCoPtmId")
    private Long suCoPtmId = null;
    @JsonProperty("loaiPtmId")
    private Long loaiPtmId = null;
    @JsonProperty("ptmId")
    private Long ptmId = null;
    @JsonProperty("congSoi")
    private String congSoi = null;
    @JsonProperty("moTa")
    private String moTa = null;

    public String getLoaiPtm() {
        return loaiPtm;
    }

    public void setLoaiPtm(String loaiPtm) {
        this.loaiPtm = loaiPtm;
    }

    public String getTenPtm() {
        return tenPtm;
    }

    public void setTenPtm(String tenPtm) {
        this.tenPtm = tenPtm;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getGhiChuTt() {
        return ghiChuTt;
    }

    public void setGhiChuTt(String ghiChuTt) {
        this.ghiChuTt = ghiChuTt;
    }

    public String getGhiChuHc() {
        return ghiChuHc;
    }

    public void setGhiChuHc(String ghiChuHc) {
        this.ghiChuHc = ghiChuHc;
    }

    public String getNgayCn() {
        return ngayCn;
    }

    public void setNgayCn(String ngayCn) {
        this.ngayCn = ngayCn;
    }

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
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

    public String getCongSoi() {
        return congSoi;
    }

    public void setCongSoi(String congSoi) {
        this.congSoi = congSoi;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
