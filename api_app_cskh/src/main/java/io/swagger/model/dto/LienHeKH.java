package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Giao phiếu báo hỏng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class LienHeKH extends BaseDTO {
    @JsonProperty("ttlhId")
    private String ttlhId = null;
    @JsonProperty("tenLh")
    private String tenLh = null;
    @JsonProperty("diaChi")
    private String diaChi = null;
    @JsonProperty("dienThoai")
    private String dienThoai = null;
    @JsonProperty("vaiTroId")
    private String vaiTroId = null;
    @JsonProperty("tenVaiTro")
    private String tenVaiTro = null;
    @JsonProperty("khachHangId")
    private String khachHangId = null;
    @JsonProperty("maKh")
    private String maKh = null;
    @JsonProperty("maTt")
    private String maTt = null;
    @JsonProperty("maTb")
    private String maTb = null;
    @JsonProperty("email")
    private String email = null;
    @JsonProperty("mangXh")
    private String mangXh = null;
    @JsonProperty("ngayCn")
    private String ngayCn = null;
    @JsonProperty("nguoiCn")
    private String nguoiCn = null;

    public String getTtlhId() {
        return ttlhId;
    }

    public void setTtlhId(String ttlhId) {
        this.ttlhId = ttlhId;
    }

    public String getTenLh() {
        return tenLh;
    }

    public void setTenLh(String tenLh) {
        this.tenLh = tenLh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getVaiTroId() {
        return vaiTroId;
    }

    public void setVaiTroId(String vaiTroId) {
        this.vaiTroId = vaiTroId;
    }

    public String getTenVaiTro() {
        return tenVaiTro;
    }

    public void setTenVaiTro(String tenVaiTro) {
        this.tenVaiTro = tenVaiTro;
    }

    public String getKhachHangId() {
        return khachHangId;
    }

    public void setKhachHangId(String khachHangId) {
        this.khachHangId = khachHangId;
    }

    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public String getMaTt() {
        return maTt;
    }

    public void setMaTt(String maTt) {
        this.maTt = maTt;
    }

    public String getMaTb() {
        return maTb;
    }

    public void setMaTb(String maTb) {
        this.maTb = maTb;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMangXh() {
        return mangXh;
    }

    public void setMangXh(String mangXh) {
        this.mangXh = mangXh;
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
}
