package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoaiThanhNguonDto {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("ten")
    private String ten;
    
    @JsonProperty("hangSxId")
    private Long hangSxId;
    
    @JsonProperty("loaiDongDien")
    private Long loaiDongDien;

    @JsonProperty("thongSoDongDien")
    @JsonAlias({"thongSoDc"})
    private Double thongSoDongDien;

    @JsonProperty("dienApDongDien")
    @JsonAlias({"dienApDc"})
    private Double dienApDongDien;
    
    @JsonProperty("csThietKe")
    private Double csThietKe;
    
    @JsonProperty("lapDat")
    private Long lapDat;
    
    @JsonProperty("dai")
    private Double dai;
    
    @JsonProperty("rong")
    private Double rong;
    
    @JsonProperty("cao")
    private Double cao;
    
    @JsonProperty("slIecC13")
    private Long slIecC13;
    
    @JsonProperty("slIecC19")
    private Long slIecC19;
    
    @JsonProperty("slKhac")
    private Long slKhac;
    
    @JsonProperty("ghiChu")
    private String ghiChu;
    
    @JsonProperty("suDung")
    private Long suDung;
    
    @JsonProperty("nguoiCn")
    private String nguoiCn;

    // Default constructor
    public LoaiThanhNguonDto() {}

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

    public Long getHangSxId() {
        return hangSxId;
    }

    public void setHangSxId(Long hangSxId) {
        this.hangSxId = hangSxId;
    }

    public Long getLoaiDongDien() {
        return loaiDongDien;
    }

    public void setLoaiDongDien(Long loaiDongDien) {
        this.loaiDongDien = loaiDongDien;
    }

    public Double getThongSoDongDien() {
        return thongSoDongDien;
    }

    public void setThongSoDongDien(Double thongSoDongDien) {
        this.thongSoDongDien = thongSoDongDien;
    }

    public Double getDienApDongDien() {
        return dienApDongDien;
    }

    public void setDienApDongDien(Double dienApDongDien) {
        this.dienApDongDien = dienApDongDien;
    }

    public Double getCsThietKe() {
        return csThietKe;
    }

    public void setCsThietKe(Double csThietKe) {
        this.csThietKe = csThietKe;
    }

    public Long getLapDat() {
        return lapDat;
    }

    public void setLapDat(Long lapDat) {
        this.lapDat = lapDat;
    }

    public Double getDai() {
        return dai;
    }

    public void setDai(Double dai) {
        this.dai = dai;
    }

    public Double getRong() {
        return rong;
    }

    public void setRong(Double rong) {
        this.rong = rong;
    }

    public Double getCao() {
        return cao;
    }

    public void setCao(Double cao) {
        this.cao = cao;
    }

    public Long getSlIecC13() {
        return slIecC13;
    }

    public void setSlIecC13(Long slIecC13) {
        this.slIecC13 = slIecC13;
    }

    public Long getSlIecC19() {
        return slIecC19;
    }

    public void setSlIecC19(Long slIecC19) {
        this.slIecC19 = slIecC19;
    }

    public Long getSlKhac() {
        return slKhac;
    }

    public void setSlKhac(Long slKhac) {
        this.slKhac = slKhac;
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
