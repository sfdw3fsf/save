package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_KT_019
 */
public class TTVT_KT_019 {
    @SerializedName("tong")
    private String tong = "";
    @SerializedName("toiHan")
    private String toiHan = "";
    @SerializedName("chuaToiHan")
    private String chuaToiHan = "";
    @SerializedName("quaHan")
    private String quaHan = "";

    public String getTong() {
        return tong;
    }

    public void setTong(String tong) {
        this.tong = tong;
    }

    public String getToiHan() {
        return toiHan;
    }

    public void setToiHan(String toiHan) {
        this.toiHan = toiHan;
    }

    public String getChuaToiHan() {
        return chuaToiHan;
    }

    public void setChuaToiHan(String chuaToiHan) {
        this.chuaToiHan = chuaToiHan;
    }

    public String getQuaHan() {
        return quaHan;
    }

    public void setQuaHan(String quaHan) {
        this.quaHan = quaHan;
    }
}
