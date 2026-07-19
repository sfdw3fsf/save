package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTKD_KD_011
 */
public class TTKD_KD_011 {
    @SerializedName("chuaKS")
    private String chuaKS = "0";
    @SerializedName("khongHL")
    private String khongHL = "0";
    @SerializedName("haiLong")
    private String haiLong = "0";

    public String getChuaKS() {
        return chuaKS;
    }

    public void setChuaKS(String chuaKS) {
        this.chuaKS = chuaKS;
    }

    public String getKhongHL() {
        return khongHL;
    }

    public void setKhongHL(String khongHL) {
        this.khongHL = khongHL;
    }

    public String getHaiLong() {
        return haiLong;
    }

    public void setHaiLong(String haiLong) {
        this.haiLong = haiLong;
    }
}
