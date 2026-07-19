package io.swagger.model.request;

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
public class CNKhachHangTiemNangRequest extends BaseDTO {
    @JsonProperty("hdkhId")
    private Long hdkhId;
    @JsonProperty("khachHangId")
    private Long khachHangId;
    @JsonProperty("kieuLdId")
    private Long kieuLdId;
    @JsonProperty("tenKH")
    private String tenKH;
    @JsonProperty("diaChi")
    private String diaChi;
    @JsonProperty("soDt")
    private String soDt;
    @JsonProperty("quanId")
    private Long quanId;
    @JsonProperty("phuongId")
    private Long phuongId;
    @JsonProperty("phoId")
    private Long phoId;
    @JsonProperty("khuId")
    private Long khuId;
    @JsonProperty("apId")
    private Long apId;
    @JsonProperty("dacDiemId")
    private Long dacDiemId;
    @JsonProperty("soNha")
    private String soNha;
    @JsonProperty("kinhDo")
    private String kinhDo;
    @JsonProperty("viDo")
    private String viDo;
    @JsonProperty("noiCap")
    private String noiCap;
    @JsonProperty("ngayCap")
    private String ngayCap;
    @JsonProperty("loaiGtId")
    private Long loaiGtId;
    @JsonProperty("soGt")
    private String soGt;
    @JsonProperty("ghiChu")
    private String ghiChu;
    @JsonProperty("dichVuVTId")
    private Long dichVuVTId;
    @JsonProperty("loaiTBId")
    private Long loaiTBId;
    @JsonProperty("thangHH")
    private String thangHH;
    @JsonProperty("lyDoTonId")
    private Long lyDoTonId;
    @JsonProperty("hinhThucId")
    private Long hinhThucId;
    @JsonProperty("nhacc_cu_id")
    private Long nhacc_cu_id;

    public Long getHinhThucId() {
        return hinhThucId;
    }

    public void setHinhThucId(Long hinhThucId) {
        this.hinhThucId = hinhThucId;
    }

    public Long getHdkhId() {
        return hdkhId;
    }

    public void setHdkhId(Long hdkhId) {
        this.hdkhId = hdkhId;
    }

    public Long getKhachHangId() {
        return khachHangId;
    }

    public void setKhachHangId(Long khachHangId) {
        this.khachHangId = khachHangId;
    }

    public Long getKieuLdId() {
        return kieuLdId;
    }

    public void setKieuLdId(Long kieuLdId) {
        this.kieuLdId = kieuLdId;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDt() {
        return soDt;
    }

    public void setSoDt(String soDt) {
        this.soDt = soDt;
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

    public Long getKhuId() {
        return khuId;
    }

    public void setKhuId(Long khuId) {
        this.khuId = khuId;
    }

    public Long getApId() {
        return apId;
    }

    public void setApId(Long apId) {
        this.apId = apId;
    }

    public Long getDacDiemId() {
        return dacDiemId;
    }

    public void setDacDiemId(Long dacDiemId) {
        this.dacDiemId = dacDiemId;
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

    public Long getLoaiGtId() {
        return loaiGtId;
    }

    public void setLoaiGtId(Long loaiGtId) {
        this.loaiGtId = loaiGtId;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Long getDichVuVTId() {
        return dichVuVTId;
    }

    public void setDichVuVTId(Long dichVuVTId) {
        this.dichVuVTId = dichVuVTId;
    }

    public Long getLoaiTBId() {
        return loaiTBId;
    }

    public void setLoaiTBId(Long loaiTBId) {
        this.loaiTBId = loaiTBId;
    }

    public String getThangHH() {
        return thangHH;
    }

    public void setThangHH(String thangHH) {
        this.thangHH = thangHH;
    }

    public Long getLyDoTonId() {
        return lyDoTonId;
    }

    public void setLyDoTonId(Long lyDoTonId) {
        this.lyDoTonId = lyDoTonId;
    }

    public String getSoGt() {
        return soGt;
    }

    public void setSoGt(String soGt) {
        this.soGt = soGt;
    }

    public Long getNhacc_cu_id() {
        return nhacc_cu_id;
    }

    public void setNhacc_cu_id(Long nhacc_cu_id) {
        this.nhacc_cu_id = nhacc_cu_id;
    }
}
