package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Nhân viên
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class    GiaoViecNV extends BaseDTO {
    @JsonProperty("phieuKNId")
    private String phieuKNId = null;
    @JsonProperty("ngayGiao")
    private String ngayGiao = null;
    @JsonProperty("nhiemmVu")
    private String nhiemmVu = null;
    @JsonProperty("nhiemVuId")
    private String nhiemVuId = null;
    @JsonProperty("tenNV")
    private String tenNV = null;
    @JsonProperty("maNV")
    private String maNV = null;
    @JsonProperty("nhanVienId")
    private String nhanVienId = null;
    @JsonProperty("soDT")
    private String soDT = null;
    @JsonProperty("tenDV")
    private String tenDV = null;
    @JsonProperty("maKN")
    private String maKN = null;
    @JsonProperty("nvGiao")
    private String nvGiao = null;
    @JsonProperty("ghiChu")
    private String ghiChu = null;

    public String getMaKN() {
        return maKN;
    }

    public void setMaKN(String maKN) {
        this.maKN = maKN;
    }

    public String getNvGiao() {
        return nvGiao;
    }

    public void setNvGiao(String nvGiao) {
        this.nvGiao = nvGiao;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getPhieuKNId() {
        return phieuKNId;
    }

    public void setPhieuKNId(String phieuKNId) {
        this.phieuKNId = phieuKNId;
    }

    public String getNgayGiao() {
        return ngayGiao;
    }

    public void setNgayGiao(String ngayGiao) {
        this.ngayGiao = ngayGiao;
    }

    public String getNhiemmVu() {
        return nhiemmVu;
    }

    public void setNhiemmVu(String nhiemmVu) {
        this.nhiemmVu = nhiemmVu;
    }

    public String getNhiemVuId() {
        return nhiemVuId;
    }

    public void setNhiemVuId(String nhiemVuId) {
        this.nhiemVuId = nhiemVuId;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(String nhanVienId) {
        this.nhanVienId = nhanVienId;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getTenDV() {
        return tenDV;
    }

    public void setTenDV(String tenDV) {
        this.tenDV = tenDV;
    }
}
