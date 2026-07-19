package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Giao phiếu báo hỏng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class PhieuDaGiaoKN {
    @JsonProperty("nguoiGiao")
    private String nguoiGiao = null;
    @JsonProperty("ngayGiao")
    private String ngayGiao = null;
    @JsonProperty("trangThai")
    private String trangThai = null;
    @JsonProperty("huongGiao")
    private String huongGiao = null;
    @JsonProperty("ngayTH")
    private String ngayTH = null;
    @JsonProperty("phieuKNId")
    private String phieuKNId = null;
    @JsonProperty("nguoiTH")
    private String nguoiTH = null;
    @JsonProperty("ghiChu")
    private String ghiChu = null;
    @JsonProperty("dvNhan")
    private String dvNhan = null;
    @JsonProperty("ndGiao")
    private String ndGiao = null;
    @JsonProperty("ndXacMinh")
    private String ndXacMinh = null;

    public String getNguoiGiao() {
        return nguoiGiao;
    }

    public void setNguoiGiao(String nguoiGiao) {
        this.nguoiGiao = nguoiGiao;
    }

    public String getNgayGiao() {
        return ngayGiao;
    }

    public void setNgayGiao(String ngayGiao) {
        this.ngayGiao = ngayGiao;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getHuongGiao() {
        return huongGiao;
    }

    public void setHuongGiao(String huongGiao) {
        this.huongGiao = huongGiao;
    }

    public String getNgayTH() {
        return ngayTH;
    }

    public void setNgayTH(String ngayTH) {
        this.ngayTH = ngayTH;
    }

    public String getPhieuKNId() {
        return phieuKNId;
    }

    public void setPhieuKNId(String phieuKNId) {
        this.phieuKNId = phieuKNId;
    }

    public String getNguoiTH() {
        return nguoiTH;
    }

    public void setNguoiTH(String nguoiTH) {
        this.nguoiTH = nguoiTH;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getDvNhan() {
        return dvNhan;
    }

    public void setDvNhan(String dvNhan) {
        this.dvNhan = dvNhan;
    }

    public String getNdGiao() {
        return ndGiao;
    }

    public void setNdGiao(String ndGiao) {
        this.ndGiao = ndGiao;
    }

    public String getNdXacMinh() {
        return ndXacMinh;
    }

    public void setNdXacMinh(String ndXacMinh) {
        this.ndXacMinh = ndXacMinh;
    }
}
