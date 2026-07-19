package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OcamTrenThanhNguonDto {
    
    @JsonProperty("ocamId")
    private Long ocamId;
    
    @JsonProperty("thanhNguonId")
    private Long thanhNguonId;
    
    @JsonProperty("ten")
    private String ten;
    
    @JsonProperty("moTa")
    private String moTa;
    
    @JsonProperty("trangThaiId")
    private Long trangThaiId;
    
    @JsonProperty("trangThaiHdId")
    private Long trangThaiHdId;
    
    @JsonProperty("thietBiId")
    private Long thietBiId;
    
    @JsonProperty("ghiChu")
    private String ghiChu;
    
    @JsonProperty("suDung")
    private Long suDung;
    
    @JsonProperty("loaiOcamId")
    private Long loaiOcamId;
    
    @JsonProperty("nguoiCn")
    private String nguoiCn;

    public OcamTrenThanhNguonDto(Long ocamId, Long thanhNguonId, String ten, String moTa, 
                                Long trangThaiId, Long trangThaiHdId, Long thietBiId, 
                                String ghiChu, Long suDung, Long loaiOcamId, String nguoiCn) {
        this.ocamId = ocamId;
        this.thanhNguonId = thanhNguonId;
        this.ten = ten;
        this.moTa = moTa;
        this.trangThaiId = trangThaiId;
        this.trangThaiHdId = trangThaiHdId;
        this.thietBiId = thietBiId;
        this.ghiChu = ghiChu;
        this.suDung = suDung;
        this.loaiOcamId = loaiOcamId;
        this.nguoiCn = nguoiCn;
    }

    public Long getOcamId() {
        return ocamId;
    }

    public void setOcamId(Long ocamId) {
        this.ocamId = ocamId;
    }

    public Long getThanhNguonId() {
        return thanhNguonId;
    }

    public void setThanhNguonId(Long thanhNguonId) {
        this.thanhNguonId = thanhNguonId;
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

    public Long getTrangThaiHdId() {
        return trangThaiHdId;
    }

    public void setTrangThaiHdId(Long trangThaiHdId) {
        this.trangThaiHdId = trangThaiHdId;
    }

    public Long getThietBiId() {
        return thietBiId;
    }

    public void setThietBiId(Long thietBiId) {
        this.thietBiId = thietBiId;
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

    public Long getLoaiOcamId() {
        return loaiOcamId;
    }

    public void setLoaiOcamId(Long loaiOcamId) {
        this.loaiOcamId = loaiOcamId;
    }

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }
}
