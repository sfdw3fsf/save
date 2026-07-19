package vn.vnpt.vnptit.ecms.dto.danhmuc;

import java.io.Serializable;

public class CapNhatLoaiVatLieuKNMDTO implements Serializable {
    private Long Id;
    private String loaiVatLieuId;
    private String tenVatLieuKetNoi;
    private String ghiChu;
    private String thongSoKyThuat;

    public Long getId() {
        return Id;
    }

    public String getLoaiVatLieuId() {
        return loaiVatLieuId;
    }

    public String getTenVatLieuKetNoi() {
        return tenVatLieuKetNoi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public String getThongSoKyThuat() {
        return thongSoKyThuat;
    }

    public void  setId(Long id) {
        this.Id = id;
    }

    public void setLoaiVatLieuId(String loaiVatLieuId) {
        this.loaiVatLieuId = loaiVatLieuId;
    }

    public void setTenVatLieuKetNoi(String tenVatLieuKetNoi) {
        this.tenVatLieuKetNoi = tenVatLieuKetNoi;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public void setThongSoKyThuat(String thongSoKyThuat) {
        this.thongSoKyThuat = thongSoKyThuat;
    }
}
