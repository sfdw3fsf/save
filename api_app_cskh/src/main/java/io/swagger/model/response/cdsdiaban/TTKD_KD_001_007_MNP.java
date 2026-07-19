package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * KR001_007_MNP
 */
public class TTKD_KD_001_007_MNP {
    @SerializedName("SL")
    private String soLuong = "0";
    @SerializedName("REGISTERDATE")
    private String ngay = "";
    @SerializedName("SOTIEN")
    private String soTien = "0";

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getSoTien() {
        return soTien;
    }

    public void setSoTien(String soTien) {
        this.soTien = soTien;
    }
}
