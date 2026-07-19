package io.swagger.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Danh bạ base
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class DanhBaBase {
    @JsonProperty("stt")
    protected Long stt;

    @JsonProperty("stk")
    protected String stk;

    @JsonProperty("sdt")
    protected String sdt;

    @JsonProperty("email")
    protected String email;

    @JsonProperty("tinhId")
    protected Long tinhId;

    @JsonProperty("quanId")
    protected Long quanId;

    @JsonProperty("phuongId")
    protected Long phuongId;

    @JsonProperty("phoId")
    protected Long phoId;

    @JsonProperty("apId")
    protected Long apId;

    @JsonProperty("khuId")
    protected Long khuId;

    @JsonProperty("dacDiemId")
    protected Long dacDiemId;

    @JsonProperty("diaChi")
    protected String diaChi;

    @JsonProperty("soNha")
    protected String soNha;

    @JsonProperty("datt")
    protected Long datt;

    @JsonProperty("ghiChu")
    protected String ghiChu;

    @JsonProperty("ketQuaCh")
    protected String ketQuaCh;

    @JsonProperty("mayCn")
    protected String mayCn;

    @JsonProperty("ngayCn")
    protected String ngayCn;

    @JsonProperty("nguoiCn")
    protected String nguoiCn;

    public Long getStt() {
        return stt;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }

    public String getStk() {
        return stk;
    }

    public void setStk(String stk) {
        this.stk = stk;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTinhId() {
        return tinhId;
    }

    public void setTinhId(Long tinhId) {
        this.tinhId = tinhId;
    }

    public Long getQuanId() {
        return quanId;
    }

    public void setQuanId(Long quanId) {
        this.quanId = quanId;
    }

    public Long getPhuongId() {
        return phuongId;
    }

    public void setPhuongId(Long phuongId) {
        this.phuongId = phuongId;
    }

    public Long getPhoId() {
        return phoId;
    }

    public void setPhoId(Long phoId) {
        this.phoId = phoId;
    }

    public Long getApId() {
        return apId;
    }

    public void setApId(Long apId) {
        this.apId = apId;
    }

    public Long getKhuId() {
        return khuId;
    }

    public void setKhuId(Long khuId) {
        this.khuId = khuId;
    }

    public Long getDacDiemId() {
        return dacDiemId;
    }

    public void setDacDiemId(Long dacDiemId) {
        this.dacDiemId = dacDiemId;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoNha() {
        return soNha;
    }

    public void setSoNha(String soNha) {
        this.soNha = soNha;
    }

    public Long getDatt() {
        return datt;
    }

    public void setDatt(Long datt) {
        this.datt = datt;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getKetQuaCh() {
        return ketQuaCh;
    }

    public void setKetQuaCh(String ketQuaCh) {
        this.ketQuaCh = ketQuaCh;
    }

    public String getMayCn() {
        return mayCn;
    }

    public void setMayCn(String mayCn) {
        this.mayCn = mayCn;
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
