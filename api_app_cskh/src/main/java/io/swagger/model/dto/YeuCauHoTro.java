package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YeuCauHoTro {
    @JsonProperty("maSc")
    private String maSc;

    @JsonProperty("ngayYc")
    private String ngayYc;

    @JsonProperty("noiDungDx")
    private String noiDungDx;

    @JsonProperty("noiDungPh")
    private String noiDungPh;

    @JsonProperty("ngayPh")
    private String ngayPh;

    @JsonProperty("nguoiPh")
    private Long nguoiPh;

    @JsonProperty("trangThai")
    private String trangThai;

    @JsonProperty("netMaqt")
    private String netMaqt;

    @JsonProperty("maBuoc")
    private String maBuoc;

    @JsonProperty("phieuId")
    private Long phieuId;

    @JsonProperty("yeuCauId")
    private Long yeuCauId;

    @JsonProperty("tenNguoiPh")
    private String tenNguoiPh;

    // Getter & Setter

    public String getMaSc() {
        return maSc;
    }

    public void setMaSc(String maSc) {
        this.maSc = maSc;
    }

    public String getNgayYc() {
        return ngayYc;
    }

    public void setNgayYc(String ngayYc) {
        this.ngayYc = ngayYc;
    }

    public String getNoiDungDx() {
        return noiDungDx;
    }

    public void setNoiDungDx(String noiDungDx) {
        this.noiDungDx = noiDungDx;
    }

    public String getNoiDungPh() {
        return noiDungPh;
    }

    public void setNoiDungPh(String noiDungPh) {
        this.noiDungPh = noiDungPh;
    }

    public String getNgayPh() {
        return ngayPh;
    }

    public void setNgayPh(String ngayPh) {
        this.ngayPh = ngayPh;
    }

    public Long getNguoiPh() {
        return nguoiPh;
    }

    public void setNguoiPh(Long nguoiPh) {
        this.nguoiPh = nguoiPh;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getNetMaqt() {
        return netMaqt;
    }

    public void setNetMaqt(String netMaqt) {
        this.netMaqt = netMaqt;
    }

    public String getMaBuoc() {
        return maBuoc;
    }

    public void setMaBuoc(String maBuoc) {
        this.maBuoc = maBuoc;
    }

    public Long getPhieuId() {
        return phieuId;
    }

    public void setPhieuId(Long phieuId) {
        this.phieuId = phieuId;
    }

    public Long getYeuCauId() {
        return yeuCauId;
    }

    public void setYeuCauId(Long yeuCauId) {
        this.yeuCauId = yeuCauId;
    }

    public String getTenNguoiPh() {
        return tenNguoiPh;
    }

    public void setTenNguoiPh(String tenNguoiPh) {
        this.tenNguoiPh = tenNguoiPh;
    }
}
