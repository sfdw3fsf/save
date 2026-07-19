package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class DoiTuongKhongGianDto implements Serializable {
    private String loaiDoiTuong;
    private Long doiTuongId;
    private Long doiTuongChaId;
    private String ten;
    private String tenTat;
    private Long chieuDai;
    private Long chieuRong;
    private Long toaDoX;
    private Long toaDoY;
    private String ghiChu;
    private String moTa;
    private String nguoiCn;

    public DoiTuongKhongGianDto() {
    }

    public DoiTuongKhongGianDto(String loaiDoiTuong, Long doiTuongId, Long doiTuongChaId, String ten, String tenTat,
            Long chieuDai, Long chieuRong, Long toaDoX, Long toaDoY,
            String ghiChu, String moTa, String nguoiCn) {
        this.loaiDoiTuong = loaiDoiTuong;
        this.doiTuongId = doiTuongId;
        this.doiTuongChaId = doiTuongChaId;
        this.ten = ten;
        this.tenTat = tenTat;
        this.chieuDai = chieuDai;
        this.chieuRong = chieuRong;
        this.toaDoX = toaDoX;
        this.toaDoY = toaDoY;
        this.ghiChu = ghiChu;
        this.moTa = moTa;
        this.nguoiCn = nguoiCn;
    }

    public String getLoaiDoiTuong() {
        return loaiDoiTuong;
    }

    public void setLoaiDoiTuong(String loaiDoiTuong) {
        this.loaiDoiTuong = loaiDoiTuong;
    }

    public Long getDoiTuongId() {
        return doiTuongId;
    }

    public void setDoiTuongId(Long doiTuongId) {
        this.doiTuongId = doiTuongId;
    }

    public Long getDoiTuongChaId() {
        return doiTuongChaId;
    }

    public void setDoiTuongChaId(Long doiTuongChaId) {
        this.doiTuongChaId = doiTuongChaId;
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

    public Long getChieuDai() {
        return chieuDai;
    }

    public void setChieuDai(Long chieuDai) {
        this.chieuDai = chieuDai;
    }

    public Long getChieuRong() {
        return chieuRong;
    }

    public void setChieuRong(Long chieuRong) {
        this.chieuRong = chieuRong;
    }

    public Long getToaDoX() {
        return toaDoX;
    }

    public void setToaDoX(Long toaDoX) {
        this.toaDoX = toaDoX;
    }

    public Long getToaDoY() {
        return toaDoY;
    }

    public void setToaDoY(Long toaDoY) {
        this.toaDoY = toaDoY;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }
}