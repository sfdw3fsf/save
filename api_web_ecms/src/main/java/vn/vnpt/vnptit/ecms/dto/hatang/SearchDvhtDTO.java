package vn.vnpt.vnptit.ecms.dto.hatang;

import java.util.Date;

public class SearchDvhtDTO {
    private String maDichVu;
    private String tenDichVu;
    private Long hieuLuc;
    private Long tinhPhi;
    private Long trangThaiId;
    private Date ngayCapTu;
    private Date ngayCapDen;
    private Date ngayHetHanTu;
    private Date ngayHetHanDen;
    private Long idcId;
    private Long cumHtId;
    private Long loaiDvId;
    private Long doiTuongId;
    private Long tenSpdvId;
    private Long cumSpdvId;
    private Long donViChuQuanId;
    private Long donViQuanLyId;
    private String caNhanQuanLy;
    private Long loaiHtId;
    private Integer checkQuyen = 0; // 1: áp dụng phân quyền, 0: bỏ qua phân quyền (xem tất cả)

    public String getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public Long getHieuLuc() {
        return hieuLuc;
    }

    public void setHieuLuc(Long hieuLuc) {
        this.hieuLuc = hieuLuc;
    }

    public Long getTinhPhi() {
        return tinhPhi;
    }

    public void setTinhPhi(Long tinhPhi) {
        this.tinhPhi = tinhPhi;
    }

    public Long getIdcId() {
        return idcId;
    }

    public void setIdcId(Long idcId) {
        this.idcId = idcId;
    }

    public Long getCumHtId() {
        return cumHtId;
    }

    public void setCumHtId(Long cumHtId) {
        this.cumHtId = cumHtId;
    }

    public Long getLoaiDvId() {
        return loaiDvId;
    }

    public void setLoaiDvId(Long loaiDvId) {
        this.loaiDvId = loaiDvId;
    }

    public Long getDoiTuongId() {
        return doiTuongId;
    }

    public void setDoiTuongId(Long doiTuongId) {
        this.doiTuongId = doiTuongId;
    }

    public Long getDonViChuQuanId() {
        return donViChuQuanId;
    }

    public void setDonViChuQuanId(Long donViChuQuanId) {
        this.donViChuQuanId = donViChuQuanId;
    }

    public Long getDonViQuanLyId() {
        return donViQuanLyId;
    }

    public void setDonViQuanLyId(Long donViQuanLyId) {
        this.donViQuanLyId = donViQuanLyId;
    }

    public String getCaNhanQuanLy() {
        return caNhanQuanLy;
    }

    public void setCaNhanQuanLy(String caNhanQuanLy) {
        this.caNhanQuanLy = caNhanQuanLy;
    }

    public Date getNgayCapDen() { return ngayCapDen; }

    public void setNgayCapDen(Date ngayCapDen) { this.ngayCapDen = ngayCapDen;}

    public Date getNgayCapTu() { return ngayCapTu; }

    public void setNgayCapTu(Date ngayCapTu) { this.ngayCapTu = ngayCapTu; }

    public Date getNgayHetHanTu() { return ngayHetHanTu; }

    public void setNgayHetHanTu(Date ngayHetHanTu) { this.ngayHetHanTu = ngayHetHanTu; }

    public Date getNgayHetHanDen() { return ngayHetHanDen; }

    public void setNgayHetHanDen(Date ngayHetHanDen) { this.ngayHetHanDen = ngayHetHanDen; }

    public Long getTrangThaiId() {
        return trangThaiId;
    }

    public void setTrangThaiId(Long trangThaiId) {
        this.trangThaiId = trangThaiId;
    }

    public Long getTenSpdvId() {
        return tenSpdvId;
    }

    public void setTenSpdvId(Long tenSpdvId) {
        this.tenSpdvId = tenSpdvId;
    }

    public Long getCumSpdvId() {
        return cumSpdvId;
    }

    public void setCumSpdvId(Long cumSpdvId) {
        this.cumSpdvId = cumSpdvId;
    }

    public Long getLoaiHtId() {
        return loaiHtId;
    }

    public void setLoaiHtId(Long loaiHtId) {
        this.loaiHtId = loaiHtId;
    }

    public Integer getCheckQuyen() {
        return checkQuyen;
    }

    public void setCheckQuyen(Integer checkQuyen) {
        this.checkQuyen = checkQuyen;
    }
}
