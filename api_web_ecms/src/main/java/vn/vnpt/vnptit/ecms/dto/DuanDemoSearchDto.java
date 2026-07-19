package vn.vnpt.vnptit.ecms.dto;

import java.util.Date;

public class DuanDemoSearchDto {
    private String hoTen;
    private String email;
    private String soDienThoai;
    private String soThich;
    private int chieuCaoMin;
    private int chieuCaoMax;
    private Date ngaySinhMin;
    private Date ngaySinhMax;
    private int xaPhuongId;
    private int quanHuyenId;
    private int tinhId;

    public int getQuanHuyenId() {
        return quanHuyenId;
    }

    public void setQuanHuyenId(int quanHuyenId) {
        this.quanHuyenId = quanHuyenId;
    }

    public int getTinhId() {
        return tinhId;
    }

    public void setTinhId(int tinhId) {
        this.tinhId = tinhId;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getSoThich() {
        return soThich;
    }

    public void setSoThich(String soThich) {
        this.soThich = soThich;
    }

    public int getChieuCaoMin() {
        return chieuCaoMin;
    }

    public void setChieuCaoMin(int chieuCaoMin) {
        this.chieuCaoMin = chieuCaoMin;
    }

    public int getChieuCaoMax() {
        return chieuCaoMax;
    }

    public void setChieuCaoMax(int chieuCaoMax) {
        this.chieuCaoMax = chieuCaoMax;
    }

    public Date getNgaySinhMin() {
        return ngaySinhMin;
    }

    public void setNgaySinhMin(Date ngaySinhMin) {
        this.ngaySinhMin = ngaySinhMin;
    }

    public Date getNgaySinhMax() {
        return ngaySinhMax;
    }

    public void setNgaySinhMax(Date ngaySinhMax) {
        this.ngaySinhMax = ngaySinhMax;
    }

    public Integer getXaPhuongId() {
        return xaPhuongId;
    }

    public void setXaPhuongId(Integer xaPhuongId) {
        this.xaPhuongId = xaPhuongId;
    }
}
