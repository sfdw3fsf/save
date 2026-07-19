package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

public class ImportrPDUDto {
    @NotNull(message = ModelValidation.NOT_NULL)
    @JsonProperty("RPDU_TEN")
    private String rpduTen;

    @NotNull(message = ModelValidation.NOT_NULL)
    @JsonProperty("CONG_SUAT_DINH_DANH_W")
    private Integer congSuatDinhDanhW;

    @NotNull(message = ModelValidation.NOT_NULL)
    @JsonProperty("DIEN_AP_DINH_DANH_V")
    private Integer dienApDinhDanhV;

    @NotNull(message = ModelValidation.NOT_NULL)
    @JsonProperty("LOAI_NGUON_DIEN")
    private String loaiNguonDien;

    @JsonProperty("HANG_SX")
    private String hangSx;

    @JsonProperty("LOAI_RPDU")
    private String loaiRpdu;

    @NotNull(message = ModelValidation.NOT_NULL)
    @JsonProperty("KHONG_GIAN_LAP_DAT_U")
    private Integer khongGianLapDatU;

    @NotNull(message = ModelValidation.NOT_NULL)
    @JsonProperty("KIEU_LAP_DAT")
    private String kieuLapDat;

    @NotNull(message = ModelValidation.NOT_NULL)
    @JsonProperty("SO_CONG_RA")
    private Integer soCongRa;

    @JsonProperty("SO_CONG_VAO")
    private Integer soCongVao;

    @NotNull(message = ModelValidation.NOT_NULL)
    @JsonProperty("IDC_CODE")
    private String idcCode;

    @NotNull(message = ModelValidation.NOT_NULL)
    @JsonProperty("MAT_SAN_TANG")
    private String matSanTang;

    @JsonProperty("IDC_TEN")
    private String idcTen;

    @JsonProperty("TOA_NHA")
    private String toaNha;

    @JsonProperty("MAT_SAN")
    private String matSan;

    @JsonProperty("ZONE")
    private String zone;

    @NotNull(message = ModelValidation.NOT_NULL)
    @JsonProperty("RACK_CHUA_RPDU")
    private String rackChuaRpdu;

    @JsonProperty("VI_TRI_TREN_RACK")
    private String viTriTrenRack;

    @JsonProperty("VI_TRI_LAP_DAT")
    private String viTriLapDat;

    @JsonProperty("DON_VI_QUAN_LY")
    private String donViQuanLy;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("LY_DO_KHONG_HOP_LE")
    private String lyDoKhongHopLe;

    @JsonProperty("MOTA")
    private String moTa;

    public String getLyDoKhongHopLe() {
        return lyDoKhongHopLe;
    }

    public void setLyDoKhongHopLe(String lyDoKhongHopLe) {
        this.lyDoKhongHopLe = lyDoKhongHopLe;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getRpduTen() {
        return rpduTen;
    }

    public void setRpduTen(String rpduTen) {
        this.rpduTen = rpduTen;
    }

    public Integer getCongSuatDinhDanhW() {
        return congSuatDinhDanhW;
    }

    public void setCongSuatDinhDanhW(Integer congSuatDinhDanhW) {
        this.congSuatDinhDanhW = congSuatDinhDanhW;
    }

    public Integer getDienApDinhDanhV() {
        return dienApDinhDanhV;
    }

    public void setDienApDinhDanhV(Integer dienApDinhDanhV) {
        this.dienApDinhDanhV = dienApDinhDanhV;
    }

    public String getLoaiNguonDien() {
        return loaiNguonDien;
    }

    public void setLoaiNguonDien(String loaiNguonDien) {
        this.loaiNguonDien = loaiNguonDien;
    }

    public String getHangSx() {
        return hangSx;
    }

    public void setHangSx(String hangSx) {
        this.hangSx = hangSx;
    }

    public String getLoaiRpdu() {
        return loaiRpdu;
    }

    public void setLoaiRpdu(String loaiRpdu) {
        this.loaiRpdu = loaiRpdu;
    }

    public Integer getKhongGianLapDatU() {
        return khongGianLapDatU;
    }

    public void setKhongGianLapDatU(Integer khongGianLapDatU) {
        this.khongGianLapDatU = khongGianLapDatU;
    }

    public String getKieuLapDat() {
        return kieuLapDat;
    }

    public void setKieuLapDat(String kieuLapDat) {
        this.kieuLapDat = kieuLapDat;
    }

    public Integer getSoCongRa() {
        return soCongRa;
    }

    public void setSoCongRa(Integer soCongRa) {
        this.soCongRa = soCongRa;
    }

    public Integer getSoCongVao() {
        return soCongVao;
    }

    public void setSoCongVao(Integer soCongVao) {
        this.soCongVao = soCongVao;
    }

    public String getIdcCode() {
        return idcCode;
    }

    public void setIdcCode(String idcCode) {
        this.idcCode = idcCode;
    }

    public String getIdcTen() {
        return idcTen;
    }

    public void setIdcTen(String idcTen) {
        this.idcTen = idcTen;
    }

    public String getMatSanTang() {
        return matSanTang;
    }

    public void setMatSanTang(String matSanTang) {
        this.matSanTang = matSanTang;
    }

    public String getToaNha() {
        return toaNha;
    }

    public void setToaNha(String toaNha) {
        this.toaNha = toaNha;
    }

    public String getMatSan() {
        return matSan;
    }

    public void setMatSan(String matSan) {
        this.matSan = matSan;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getRackChuaRpdu() {
        return rackChuaRpdu;
    }

    public void setRackChuaRpdu(String rackChuaRpdu) {
        this.rackChuaRpdu = rackChuaRpdu;
    }

    public String getViTriTrenRack() {
        return viTriTrenRack;
    }

    public void setViTriTrenRack(String viTriTrenRack) {
        this.viTriTrenRack = viTriTrenRack;
    }

    public String getViTriLapDat() {
        return viTriLapDat;
    }

    public void setViTriLapDat(String viTriLapDat) {
        this.viTriLapDat = viTriLapDat;
    }

    public String getDonViQuanLy() {
        return donViQuanLy;
    }

    public void setDonViQuanLy(String donViQuanLy) {
        this.donViQuanLy = donViQuanLy;
    }
}
