package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_KT_039
 */
public class TTVT_KT_039 {
    @SerializedName("ngay")
    private String ngay = "";
    @SerializedName("hopLe")
    private String hopLe = "";
    @SerializedName("khongHopLe")
    private String khongHopLe = "";

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getHopLe() {
        return hopLe;
    }

    public void setHopLe(String hopLe) {
        this.hopLe = hopLe;
    }

    public String getKhongHopLe() {
        return khongHopLe;
    }

    public void setKhongHopLe(String khongHopLe) {
        this.khongHopLe = khongHopLe;
    }
}
