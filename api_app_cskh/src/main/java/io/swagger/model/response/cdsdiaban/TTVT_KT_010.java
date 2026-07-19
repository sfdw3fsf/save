package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_KT_010
 */
public class TTVT_KT_010 {
    @SerializedName("denHan")
    private String denHan = "";
    @SerializedName("quaHan")
    private String quaHan = "";
    @SerializedName("chuaToiHan")
    private String chuaToiHan = "";

    public String getDenHan() {
        return denHan;
    }

    public void setDenHan(String denHan) {
        this.denHan = denHan;
    }

    public String getQuaHan() {
        return quaHan;
    }

    public void setQuaHan(String quaHan) {
        this.quaHan = quaHan;
    }

    public String getChuaToiHan() {
        return chuaToiHan;
    }

    public void setChuaToiHan(String chuaToiHan) {
        this.chuaToiHan = chuaToiHan;
    }
}
