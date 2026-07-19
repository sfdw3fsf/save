package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThanhNguonTrenRackDto {
    
    @JsonProperty("thanhNguonId")
    private Long thanhNguonId;
    
    @JsonProperty("loaiTnId")
    private Long loaiTnId;
    
    @JsonProperty("rackId")
    private Long rackId;
    
    @JsonProperty("ten")
    private String ten;
    
    @JsonProperty("moTa")
    private String moTa;
    
    @JsonProperty("trangThaiId")
    private Long trangThaiId;
    
    @JsonProperty("viTriLd")
    private Long viTriLd;
    
    @JsonProperty("kieuLd")
    private Long kieuLd;
    
    @JsonProperty("ghiChu")
    private String ghiChu;
    
    @JsonProperty("suDung")
    private Long suDung;

    // Default constructor
    public ThanhNguonTrenRackDto() {}

    // Constructor with all parameters
    public ThanhNguonTrenRackDto(Long thanhNguonId, Long loaiTnId, Long rackId, 
                                String ten, String moTa, Long trangThaiId, 
                                Long viTriLd, Long kieuLd, String ghiChu, 
                                Long suDung) {
        this.thanhNguonId = thanhNguonId;
        this.loaiTnId = loaiTnId;
        this.rackId = rackId;
        this.ten = ten;
        this.moTa = moTa;
        this.trangThaiId = trangThaiId;
        this.viTriLd = viTriLd;
        this.kieuLd = kieuLd;
        this.ghiChu = ghiChu;
        this.suDung = suDung;
    }

    // Getters and Setters
    public Long getThanhNguonId() {
        return thanhNguonId;
    }

    public void setThanhNguonId(Long thanhNguonId) {
        this.thanhNguonId = thanhNguonId;
    }

    public Long getLoaiTnId() {
        return loaiTnId;
    }

    public void setLoaiTnId(Long loaiTnId) {
        this.loaiTnId = loaiTnId;
    }

    public Long getRackId() {
        return rackId;
    }

    public void setRackId(Long rackId) {
        this.rackId = rackId;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Long getTrangThaiId() {
        return trangThaiId;
    }

    public void setTrangThaiId(Long trangThaiId) {
        this.trangThaiId = trangThaiId;
    }

    public Long getViTriLd() {
        return viTriLd;
    }

    public void setViTriLd(Long viTriLd) {
        this.viTriLd = viTriLd;
    }

    public Long getKieuLd() {
        return kieuLd;
    }

    public void setKieuLd(Long kieuLd) {
        this.kieuLd = kieuLd;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Long getSuDung() {
        return suDung;
    }

    public void setSuDung(Long suDung) {
        this.suDung = suDung;
    }
}
