package vn.vnpt.vnptit.qlsc.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class QuanLyDieuHanhTuDongInputDto {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer mucdo_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer tinnhan_id;
    private String mucdo_sc;
    private int canhbao_dt;
    private String mota;
    private String maunen;
    private String mauchu;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer capdodh_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer chuky_dh;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer chuky_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer loaitn_id;
    private String dsnv_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer nhanvien_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private String donvi_id;

    public Integer getMucdo_id() {
        return mucdo_id;
    }

    public void setMucdo_id(Integer mucdo_id) {
        this.mucdo_id = mucdo_id;
    }

    public Integer getTinnhan_id() {
        return tinnhan_id;
    }

    public void setTinnhan_id(Integer tinnhan_id) {
        this.tinnhan_id = tinnhan_id;
    }

    public String getMucdo_sc() {
        return mucdo_sc;
    }

    public void setMucdo_sc(String mucdo_sc) {
        this.mucdo_sc = mucdo_sc;
    }

    public int getCanhbao_dt() {
        return canhbao_dt;
    }

    public void setCanhbao_dt(int canhbao_dt) {
        this.canhbao_dt = canhbao_dt;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getMaunen() {
        return maunen;
    }

    public void setMaunen(String maunen) {
        this.maunen = maunen;
    }

    public String getMauchu() {
        return mauchu;
    }

    public void setMauchu(String mauchu) {
        this.mauchu = mauchu;
    }

    public Integer getCapdodh_id() {
        return capdodh_id;
    }

    public void setCapdodh_id(Integer capdodh_id) {
        this.capdodh_id = capdodh_id;
    }

    public Integer getChuky_dh() {
        return chuky_dh;
    }

    public void setChuky_dh(Integer chuky_dh) {
        this.chuky_dh = chuky_dh;
    }

    public Integer getChuky_id() {
        return chuky_id;
    }

    public void setChuky_id(Integer chuky_id) {
        this.chuky_id = chuky_id;
    }

    public Integer getLoaitn_id() {
        return loaitn_id;
    }

    public void setLoaitn_id(Integer loaitn_id) {
        this.loaitn_id = loaitn_id;
    }

    public String getDsnv_id() {
        return dsnv_id;
    }

    public void setDsnv_id(String dsnv_id) {
        this.dsnv_id = dsnv_id;
    }

    public Integer getNhanvien_id() {
        return nhanvien_id;
    }

    public void setNhanvien_id(Integer nhanvien_id) {
        this.nhanvien_id = nhanvien_id;
    }

    public String getDonvi_id() {
        return donvi_id;
    }

    public void setDonvi_id(String donvi_id) {
        this.donvi_id = donvi_id;
    }
}
