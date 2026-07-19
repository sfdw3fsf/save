package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThanhPhanThietBiDto {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("ten")
    private String ten;
    
    @JsonProperty("ghiChu")
    private String ghiChu;
    
    @JsonProperty("suDung")
    private Long suDung;
    
    @JsonProperty("nguoiCn")
    private String nguoiCn;

    // Default constructor
    public ThanhPhanThietBiDto() {}

    // Constructor for creating
    public ThanhPhanThietBiDto(String ten, String ghiChu, Long suDung) {
        this.ten = ten;
        this.ghiChu = ghiChu;
        this.suDung = suDung;
    }

    // Constructor for updating
    public ThanhPhanThietBiDto(Long id, String ten, String ghiChu, Long suDung) {
        this.id = id;
        this.ten = ten;
        this.ghiChu = ghiChu;
        this.suDung = suDung;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
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

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }
}
