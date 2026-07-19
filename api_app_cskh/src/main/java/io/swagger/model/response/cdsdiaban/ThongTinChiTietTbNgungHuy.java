package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * khieunai_brcd
 */
@Generated("jsonschema2pojo")
public class ThongTinChiTietTbNgungHuy {
    @SerializedName("maTb")
    private String maTb = "";
    @SerializedName("tenTb")
    private String tenTb = "";
    @SerializedName("soDt")
    private String soDt = "";
    @SerializedName("diaChiLd")
    private String diaChiLd = "";
    @SerializedName("loai")
    private String loai = "";
    @SerializedName("ngay")
    private String ngay = "";

    public String getMaTb() {
        return maTb;
    }

    public void setMaTb(String maTb) {
        this.maTb = maTb;
    }

    public String getTenTb() {
        return tenTb;
    }

    public void setTenTb(String tenTb) {
        this.tenTb = tenTb;
    }

    public String getSoDt() {
        return soDt;
    }

    public void setSoDt(String soDt) {
        this.soDt = soDt;
    }

    public String getDiaChiLd() {
        return diaChiLd;
    }

    public void setDiaChiLd(String diaChiLd) {
        this.diaChiLd = diaChiLd;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
