package io.swagger.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Báo tồn
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class PhieuDieuNghiemThu extends BaseDTO {
    @JsonProperty("phieuXlId")
    private Long phieuXlId = null;
    @JsonProperty("ttphXlId")
    private Long ttphXlId = null;
    @JsonProperty("ttphXl")
    private String ttphXl = null;
    @JsonProperty("donViNhanId")
    private Long donViNhanId = null;
    @JsonProperty("donViNhan")
    private String donViNhan = null;
    @JsonProperty("ngayGiao")
    private String ngayGiao = null;
    @JsonProperty("ndGiao")
    private String ndGiao = null;
    @JsonProperty("khan")
    private String khan = null;
    @JsonProperty("chiTieuTg")
    private String chiTieuTg = null;
    @JsonProperty("conLai")
    private String conLai = null;
    @JsonProperty("ghiChuXl")
    private String ghiChuXl = null;
    @JsonProperty("nhanVienXlId")
    private Long nhanVienXlId = null;
    @JsonProperty("nhanVienXl")
    private String nhanVienXl = null;
    @JsonProperty("ngayXl")
    private String ngayXl = null;
    @JsonProperty("phieuNtId")
    private Long phieuNtId = null;
    @JsonProperty("ttphNtId")
    private Long ttphNtId = null;
    @JsonProperty("ghiChuNt")
    private String ghiChuNt = null;
    @JsonProperty("nhanVienNtId")
    private Long nhanVienNtId = null;
    @JsonProperty("nhanVienNt")
    private String nhanVienNt = null;
    @JsonProperty("ngayNt")
    private String ngayNt = null;

    public Long getPhieuXlId() {
        return phieuXlId;
    }

    public void setPhieuXlId(Long phieuXlId) {
        this.phieuXlId = phieuXlId;
    }

    public Long getTtphXlId() {
        return ttphXlId;
    }

    public void setTtphXlId(Long ttphXlId) {
        this.ttphXlId = ttphXlId;
    }

    public String getTtphXl() {
        return ttphXl;
    }

    public void setTtphXl(String ttphXl) {
        this.ttphXl = ttphXl;
    }

    public Long getDonViNhanId() {
        return donViNhanId;
    }

    public void setDonViNhanId(Long donViNhanId) {
        this.donViNhanId = donViNhanId;
    }

    public String getDonViNhan() {
        return donViNhan;
    }

    public void setDonViNhan(String donViNhan) {
        this.donViNhan = donViNhan;
    }

    public String getNgayGiao() {
        return ngayGiao;
    }

    public void setNgayGiao(String ngayGiao) {
        this.ngayGiao = ngayGiao;
    }

    public String getNdGiao() {
        return ndGiao;
    }

    public void setNdGiao(String ndGiao) {
        this.ndGiao = ndGiao;
    }

    public String getKhan() {
        return khan;
    }

    public void setKhan(String khan) {
        this.khan = khan;
    }

    public String getChiTieuTg() {
        return chiTieuTg;
    }

    public void setChiTieuTg(String chiTieuTg) {
        this.chiTieuTg = chiTieuTg;
    }

    public String getConLai() {
        return conLai;
    }

    public void setConLai(String conLai) {
        this.conLai = conLai;
    }

    public String getGhiChuXl() {
        return ghiChuXl;
    }

    public void setGhiChuXl(String ghiChuXl) {
        this.ghiChuXl = ghiChuXl;
    }

    public Long getNhanVienXlId() {
        return nhanVienXlId;
    }

    public void setNhanVienXlId(Long nhanVienXlId) {
        this.nhanVienXlId = nhanVienXlId;
    }

    public String getNhanVienXl() {
        return nhanVienXl;
    }

    public void setNhanVienXl(String nhanVienXl) {
        this.nhanVienXl = nhanVienXl;
    }

    public String getNgayXl() {
        return ngayXl;
    }

    public void setNgayXl(String ngayXl) {
        this.ngayXl = ngayXl;
    }

    public Long getPhieuNtId() {
        return phieuNtId;
    }

    public void setPhieuNtId(Long phieuNtId) {
        this.phieuNtId = phieuNtId;
    }

    public Long getTtphNtId() {
        return ttphNtId;
    }

    public void setTtphNtId(Long ttphNtId) {
        this.ttphNtId = ttphNtId;
    }

    public String getGhiChuNt() {
        return ghiChuNt;
    }

    public void setGhiChuNt(String ghiChuNt) {
        this.ghiChuNt = ghiChuNt;
    }

    public Long getNhanVienNtId() {
        return nhanVienNtId;
    }

    public void setNhanVienNtId(Long nhanVienNtId) {
        this.nhanVienNtId = nhanVienNtId;
    }

    public String getNhanVienNt() {
        return nhanVienNt;
    }

    public void setNhanVienNt(String nhanVienNt) {
        this.nhanVienNt = nhanVienNt;
    }

    public String getNgayNt() {
        return ngayNt;
    }

    public void setNgayNt(String ngayNt) {
        this.ngayNt = ngayNt;
    }
}
