package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_KT_030
 */
public class TTVT_KT_030 {
    @SerializedName("phieuGiao")
    private String phieuGiao = "";
    @SerializedName("phieuXL")
    private String phieuXL = "";
    @SerializedName("ngay")
    private String ngay = "";

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getPhieuGiao() {
        return phieuGiao;
    }

    public void setPhieuGiao(String phieuGiao) {
        this.phieuGiao = phieuGiao;
    }

    public String getPhieuXL() {
        return phieuXL;
    }

    public void setPhieuXL(String phieuXL) {
        this.phieuXL = phieuXL;
    }
}
