package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTKD_KD_011
 */
public class TTVT_KT_052 {
    @SerializedName("tong")
    private String tong = "0";
    @SerializedName("denHan")
    private String denHan = "0";
    @SerializedName("chuaDenHan")
    private String chuaDenHan = "0";

    public String getTong() {
        return tong;
    }

    public void setTong(String tong) {
        this.tong = tong;
    }

    public String getDenHan() {
        return denHan;
    }

    public void setDenHan(String denHan) {
        this.denHan = denHan;
    }

    public String getChuaDenHan() {
        return chuaDenHan;
    }

    public void setChuaDenHan(String chuaDenHan) {
        this.chuaDenHan = chuaDenHan;
    }
}
