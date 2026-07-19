package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DanhMucQuyHoachThietBiDto {
    @JsonProperty(value = "trangThaiId", required = false)
    private Long trangThaiId;
    @JsonProperty(value = "ten")
    private String ten;
    @JsonProperty(value = "tenTat")
    private String tenTat;
    @JsonProperty(value = "moTa")
    private String moTa;
    @JsonProperty(value = "suDung")
    private String suDung;
    @JsonProperty(value = "ghiChu")
    private String ghiChu;

    public Long getTrangThaiId() {
        return trangThaiId;
    }

    public void setTrangThaiId(Long trangThaiId) {
        this.trangThaiId = trangThaiId;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTenTat() {
        return tenTat;
    }

    public void setTenTat(String tenTat) {
        this.tenTat = tenTat;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getSuDung() {
        return suDung;
    }

    public void setSuDung(String suDung) {
        this.suDung = suDung;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
