package com.vn.vnptit.Oneapp_Com.model;

public class DonHangSimKitHuy {
    private Integer phanvung_id;
    private Number id_donhang;
    private Integer id_trangthai;
    private Integer loai_tb;
    private String so_dt;
    private String ngaytao;

    public Integer getPhanvung_id() {
        return phanvung_id;
    }

    public void setPhanvung_id(Integer phanvung_id) {
        this.phanvung_id = phanvung_id;
    }

    public Number getId_donhang() {
        return id_donhang;
    }

    public void setId_donhang(Number id_donhang) {
        this.id_donhang = id_donhang;
    }

    public Integer getId_trangthai() {
        return id_trangthai;
    }

    public void setId_trangthai(Integer id_trangthai) {
        this.id_trangthai = id_trangthai;
    }

    public Integer getLoai_tb() {
        return loai_tb;
    }

    public void setLoai_tb(Integer loai_tb) {
        this.loai_tb = loai_tb;
    }

    public String getSo_dt() {
        return so_dt;
    }

    public void setSo_dt(String so_dt) {
        this.so_dt = so_dt;
    }

    public String getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(String ngaytao) {
        this.ngaytao = ngaytao;
    }
}
