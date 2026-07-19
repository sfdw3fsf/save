package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_KT_015
 */
public class TTKD_KD_015 {
    @SerializedName("daKyHD")
    private String daKyHD = "";
    @SerializedName("hen")
    private String hen = "";
    @SerializedName("khongKyHD")
    private String khongKyHD = "";
    @SerializedName("khac")
    private String khac = "";

    public String getDaKyHD() {
        return daKyHD;
    }

    public void setDaKyHD(String daKyHD) {
        this.daKyHD = daKyHD;
    }

    public String getHen() {
        return hen;
    }

    public void setHen(String hen) {
        this.hen = hen;
    }

    public String getKhongKyHD() {
        return khongKyHD;
    }

    public void setKhongKyHD(String khongKyHD) {
        this.khongKyHD = khongKyHD;
    }

    public String getKhac() {
        return khac;
    }

    public void setKhac(String khac) {
        this.khac = khac;
    }
}
