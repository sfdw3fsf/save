package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_TTKT_006
 */
public class TTVT_TTKT_006 {
    @SerializedName("ngay")
    private String ngay = "";
    @SerializedName("soLuong")
    private String soLuong = "0";

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }
}
