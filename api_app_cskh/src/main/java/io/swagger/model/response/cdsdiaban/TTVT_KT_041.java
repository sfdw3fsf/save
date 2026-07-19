package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_KT_041
 */
public class TTVT_KT_041 {
    @SerializedName("khac")
    private String khac = "";
    @SerializedName("ngay")
    private String ngay = "";
    @SerializedName("mobifone")
    private String mobifone = "";
    @SerializedName("viettel")
    private String viettel = "";
    @SerializedName("fpt")
    private String fpt = "";
    @SerializedName("vietnammobile")
    private String vietnammobile = "";

    public String getVietnammobile() {
        return vietnammobile;
    }

    public void setVietnammobile(String vietnammobile) {
        this.vietnammobile = vietnammobile;
    }

    public String getKhac() {
        return khac;
    }

    public void setKhac(String khac) {
        this.khac = khac;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getMobifone() {
        return mobifone;
    }

    public void setMobifone(String mobifone) {
        this.mobifone = mobifone;
    }

    public String getViettel() {
        return viettel;
    }

    public void setViettel(String viettel) {
        this.viettel = viettel;
    }

    public String getFpt() {
        return fpt;
    }

    public void setFpt(String fpt) {
        this.fpt = fpt;
    }
}
