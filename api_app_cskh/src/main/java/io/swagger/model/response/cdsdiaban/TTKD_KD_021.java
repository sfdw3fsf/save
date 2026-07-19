package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTKD_KD_021
 */
public class TTKD_KD_021 {
    @SerializedName("slGoi")
    private String slGoi = "0";
    @SerializedName("tongTien")
    private String tongTien = "0";
    @SerializedName("ngay")
    private String ngay = "";

    public String getSlGoi() {
        return slGoi;
    }

    public void setSlGoi(String slGoi) {
        this.slGoi = slGoi;
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
