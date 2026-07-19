package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * TTKhanhHang
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class TTKhachHang extends BaseDTO {
    @JsonProperty("khachHangId")
    private Long khachHangId = null;
    @JsonProperty("soDienThoai")
    private String soDienThoai = null;
    @JsonProperty("maKH")
    private String maKH = null;
    @JsonProperty("tenKH")
    private String tenKH = null;
    @JsonProperty("noiCap")
    private String noiCap = null;
    @JsonProperty("ngayCap")
    private String ngayCap = null;
    @JsonProperty("soGT")
    private String soGT = null;
    @JsonProperty("loaiGT")
    private String loaiGT = null;
    @JsonProperty("loaiGTId")
    private Long loaiGTId = null;
    @JsonProperty("ghiChu")
    private String ghiChu = null;
    @JsonProperty("diaChi")
    private String diaChi = null;
    @JsonProperty("tinhId")
    private Long tinhId = null;
    @JsonProperty("quanId")
    private Long quanId = null;
    @JsonProperty("phuongId")
    private Long phuongId = null;
    @JsonProperty("phoId")
    private Long phoId = null;
    @JsonProperty("apId")
    private Long apId = null;
    @JsonProperty("khuId")
    private Long khuId = null;
    @JsonProperty("soNha")
    private String soNha = null;
    @JsonProperty("kinhDo")
    private String kinhDo = null;
    @JsonProperty("viDo")
    private String viDo = null;
    @JsonProperty("dacDiemId")
    private Long dacDiemId = null;
    @JsonProperty("tenDacDiem")
    private String tenDacDiem = null;
    @JsonProperty("tinh")
    private String tinh = null;
    @JsonProperty("quan")
    private String quan = null;
    @JsonProperty("phuong")
    private String phuong = null;
    @JsonProperty("pho")
    private String pho = null;
    @JsonProperty("ap")
    private String ap = null;
    @JsonProperty("khu")
    private String khu = null;

    public String getLoaiGT() {
        return loaiGT;
    }

    public void setLoaiGT(String loaiGT) {
        this.loaiGT = loaiGT;
    }

    public Long getKhachHangId() {
        return khachHangId;
    }

    public void setKhachHangId(Long khachHangId) {
        this.khachHangId = khachHangId;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getNoiCap() {
        return noiCap;
    }

    public void setNoiCap(String noiCap) {
        this.noiCap = noiCap;
    }

    public String getNgayCap() {
        return ngayCap;
    }

    public void setNgayCap(String ngayCap) {
        this.ngayCap = ngayCap;
    }

    public String getSoGT() {
        return soGT;
    }

    public void setSoGT(String soGT) {
        this.soGT = soGT;
    }

    public Long getLoaiGTId() {
        return loaiGTId;
    }

    public void setLoaiGTId(Long loaiGTId) {
        this.loaiGTId = loaiGTId;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
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

    public String getSoNha() {
        return soNha;
    }

    public void setSoNha(String soNha) {
        this.soNha = soNha;
    }

    public String getKinhDo() {
        return kinhDo;
    }

    public void setKinhDo(String kinhDo) {
        this.kinhDo = kinhDo;
    }

    public String getViDo() {
        return viDo;
    }

    public void setViDo(String viDo) {
        this.viDo = viDo;
    }

    public Long getDacDiemId() {
        return dacDiemId;
    }

    public void setDacDiemId(Long dacDiemId) {
        this.dacDiemId = dacDiemId;
    }

    public String getTenDacDiem() {
        return tenDacDiem;
    }

    public void setTenDacDiem(String tenDacDiem) {
        this.tenDacDiem = tenDacDiem;
    }

    public String getTinh() {
        return tinh;
    }

    public void setTinh(String tinh) {
        this.tinh = tinh;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }

    public String getPhuong() {
        return phuong;
    }

    public void setPhuong(String phuong) {
        this.phuong = phuong;
    }

    public String getPho() {
        return pho;
    }

    public void setPho(String pho) {
        this.pho = pho;
    }

    public String getAp() {
        return ap;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }

    public String getKhu() {
        return khu;
    }

    public void setKhu(String khu) {
        this.khu = khu;
    }
}
