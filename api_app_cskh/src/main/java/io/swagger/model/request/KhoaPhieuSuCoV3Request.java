package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class KhoaPhieuSuCoV3Request {
    private Long suCoId;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date ngayTh;

    private Long nhanVienThId;

    private String dsNguyenNhan; // vẫn giữ XML cũ

    private String ghiChuTh;

    private Long nhanVienId;

    private Long khoaPhieu = 1L;

    //  NEW
    private Long nhomNguyenNhanId;
    private Long nguyenNhanId;

    public Long getSuCoId() {
        return suCoId;
    }

    public void setSuCoId(Long suCoId) {
        this.suCoId = suCoId;
    }

    public Date getNgayTh() {
        return ngayTh;
    }

    public void setNgayTh(Date ngayTh) {
        this.ngayTh = ngayTh;
    }

    public Long getNhanVienThId() {
        return nhanVienThId;
    }

    public void setNhanVienThId(Long nhanVienThId) {
        this.nhanVienThId = nhanVienThId;
    }

    public String getDsNguyenNhan() {
        return dsNguyenNhan;
    }

    public void setDsNguyenNhan(String dsNguyenNhan) {
        this.dsNguyenNhan = dsNguyenNhan;
    }

    public String getGhiChuTh() {
        return ghiChuTh;
    }

    public void setGhiChuTh(String ghiChuTh) {
        this.ghiChuTh = ghiChuTh;
    }

    public Long getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(Long nhanVienId) {
        this.nhanVienId = nhanVienId;
    }

    public Long getKhoaPhieu() {
        return khoaPhieu;
    }

    public void setKhoaPhieu(Long khoaPhieu) {
        this.khoaPhieu = khoaPhieu;
    }

    public Long getNhomNguyenNhanId() {
        return nhomNguyenNhanId;
    }

    public void setNhomNguyenNhanId(Long nhomNguyenNhanId) {
        this.nhomNguyenNhanId = nhomNguyenNhanId;
    }

    public Long getNguyenNhanId() {
        return nguyenNhanId;
    }
    public void setNguyenNhanId(Long nguyenNhanId) {
        this.nguyenNhanId = nguyenNhanId;
    }
}