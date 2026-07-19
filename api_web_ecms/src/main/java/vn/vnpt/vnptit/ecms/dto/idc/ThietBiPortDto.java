package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ThietBiPortDto implements Serializable {
    
    @JsonProperty("ID")
    private Long id;
    
    @JsonProperty("LOAICONG_ID")
    private String loaiCong;
    
    @JsonProperty("TEN")
    private String tenCong;
    
    @JsonProperty("GIATRI_CONG")
    private String tocDoGiaTriCong;
    
    @JsonProperty("THONGSO_KYTHUAT")
    private String thongSoKyThuat;
    
    @JsonProperty("TRANGTHAI_SD")
    private String trangThaiPortVanHanh;
    
    @JsonProperty("TRANGTHAI_DIEU_NOI")
    private String trangThaiDieuNoiPort;
    
    @JsonProperty("GHI_CHU")
    private String ghiChu;
    
    @JsonProperty("PHANLOAI")
    private String phanLoai;
    
    // Constructors
    public ThietBiPortDto() {}
    
    public ThietBiPortDto(Long id, String loaiCong, String tenCong, String tocDoGiaTriCong, 
                   String thongSoKyThuat, String trangThaiPortVanHanh, 
                   String trangThaiDieuNoiPort, String ghiChu, String phanLoai) {
        this.id = id;
        this.loaiCong = loaiCong;
        this.tenCong = tenCong;
        this.tocDoGiaTriCong = tocDoGiaTriCong;
        this.thongSoKyThuat = thongSoKyThuat;
        this.trangThaiPortVanHanh = trangThaiPortVanHanh;
        this.trangThaiDieuNoiPort = trangThaiDieuNoiPort;
        this.ghiChu = ghiChu;
        this.phanLoai = phanLoai;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getLoaiCong() {
        return loaiCong;
    }
    
    public void setLoaiCong(String loaiCong) {
        this.loaiCong = loaiCong;
    }
    
    public String getTenCong() {
        return tenCong;
    }
    
    public void setTenCong(String tenCong) {
        this.tenCong = tenCong;
    }
    
    public String getTocDoGiaTriCong() {
        return tocDoGiaTriCong;
    }
    
    public void setTocDoGiaTriCong(String tocDoGiaTriCong) {
        this.tocDoGiaTriCong = tocDoGiaTriCong;
    }
    
    public String getThongSoKyThuat() {
        return thongSoKyThuat;
    }
    
    public void setThongSoKyThuat(String thongSoKyThuat) {
        this.thongSoKyThuat = thongSoKyThuat;
    }
    
    public String getTrangThaiPortVanHanh() {
        return trangThaiPortVanHanh;
    }
    
    public void setTrangThaiPortVanHanh(String trangThaiPortVanHanh) {
        this.trangThaiPortVanHanh = trangThaiPortVanHanh;
    }
    
    public String getTrangThaiDieuNoiPort() {
        return trangThaiDieuNoiPort;
    }
    
    public void setTrangThaiDieuNoiPort(String trangThaiDieuNoiPort) {
        this.trangThaiDieuNoiPort = trangThaiDieuNoiPort;
    }
    
    public String getGhiChu() {
        return ghiChu;
    }
    
    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
    public String getPhanLoai() {
        return phanLoai;
    }
    
    public void setPhanLoai(String phanLoai) {
        this.phanLoai = phanLoai;
    }
}
