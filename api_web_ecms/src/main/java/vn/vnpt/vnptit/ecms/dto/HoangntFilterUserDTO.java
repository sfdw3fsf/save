package vn.vnpt.vnptit.ecms.dto;

import java.util.Date;

public class HoangntFilterUserDTO {
    private String hoTen;
    private String email;
    private String soDienThoai;
    private String soThich;
    private int chieuCaoFrom;
    private int chieuCaoTo;
    private Date ngaySinhFrom;
    private Date ngaySinhTo;
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

    public int getChieuCaoFrom() {
        return chieuCaoFrom;
    }

    public void setChieuCaoFrom(int chieuCaoFrom) {
        this.chieuCaoFrom = chieuCaoFrom;
    }

    public int getChieuCaoTo() {
        return chieuCaoTo;
    }

    public void setChieuCaoTo(int chieuCaoTo) {
        this.chieuCaoTo = chieuCaoTo;
    }

    public Date getNgaySinhFrom() {
        return ngaySinhFrom;
    }

    public void setNgaySinhFrom(Date ngaySinhFrom) {
        this.ngaySinhFrom = ngaySinhFrom;
    }

    public Date getNgaySinhTo() {
        return ngaySinhTo;
    }

    public void setNgaySinhTo(Date ngaySinhTo) {
        this.ngaySinhTo = ngaySinhTo;
    }

    public int getXaPhuongId() {
        return xaPhuongId;
    }

    public void setXaPhuongId(int xaPhuongId) {
        this.xaPhuongId = xaPhuongId;
    }
}
