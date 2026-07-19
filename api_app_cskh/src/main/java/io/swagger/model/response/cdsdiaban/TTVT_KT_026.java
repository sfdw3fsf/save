package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_KT_026
 */
public class TTVT_KT_026 {
    @SerializedName("ngay")
    private String ngay = "";
    @SerializedName("phaiTH")
    private String phaiTH = "";
    @SerializedName("daTH")
    private String daTH = "";

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getPhaiTH() {
        return phaiTH;
    }

    public void setPhaiTH(String phaiTH) {
        this.phaiTH = phaiTH;
    }

    public String getDaTH() {
        return daTH;
    }

    public void setDaTH(String daTH) {
        this.daTH = daTH;
    }
}
