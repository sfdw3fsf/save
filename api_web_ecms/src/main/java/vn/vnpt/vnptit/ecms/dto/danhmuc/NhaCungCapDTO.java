package vn.vnpt.vnptit.ecms.dto.danhmuc;

import java.io.Serializable;

public class NhaCungCapDTO implements Serializable {
    private Long pId;
    private String pTen;
    private String pDiaChi;
    private String pDienThoai;
    private String pEmail;
    private String pWebsite;
    private String pSoTaiKhoan;
    private String pNguoiDaiDien;
    private String pGhiChu;
    private String pMaNCC;
    private Boolean pHieuLuc;

    public String getpMaNCC() {
        return pMaNCC;
    }

    public void setpMaNCC(String pMaNCC) {
        this.pMaNCC = pMaNCC;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getpTen() {
        return pTen;
    }

    public void setpTen(String pTen) {
        this.pTen = pTen;
    }

    public String getpDiaChi() {
        return pDiaChi;
    }

    public void setpDiaChi(String pDiaChi) {
        this.pDiaChi = pDiaChi;
    }

    public String getpDienThoai() {
        return pDienThoai;
    }

    public void setpDienThoai(String pDienThoai) {
        this.pDienThoai = pDienThoai;
    }

    public String getpEmail() {
        return pEmail;
    }

    public void setpEmail(String pEmail) {
        this.pEmail = pEmail;
    }

    public String getpWebsite() {
        return pWebsite;
    }

    public void setpWebsite(String pWebsite) {
        this.pWebsite = pWebsite;
    }

    public String getpSoTaiKhoan() {
        return pSoTaiKhoan;
    }

    public void setpSoTaiKhoan(String pSoTaiKhoan) {
        this.pSoTaiKhoan = pSoTaiKhoan;
    }

    public String getpNguoiDaiDien() {
        return pNguoiDaiDien;
    }

    public void setpNguoiDaiDien(String pNguoiDaiDien) {
        this.pNguoiDaiDien = pNguoiDaiDien;
    }

    public String getpGhiChu() {
        return pGhiChu;
    }

    public void setpGhiChu(String pGhiChu) {
        this.pGhiChu = pGhiChu;
    }

    public Boolean getpHieuLuc() {
        return pHieuLuc;
    }

    public void setpHieuLuc(Boolean pHieuLuc) {
        this.pHieuLuc = pHieuLuc;
    }
}
