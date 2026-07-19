package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * TTKD_KD_030_032_034_035 
 */
public class TTKD_KD_030_032_034_035 {
    @SerializedName("ngay")
    private String ngay;
    @SerializedName("soLuong")
    private String soLuong;
    @SerializedName("cuocDc")
    private String cuocDc;

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

    public String getCuocDc() {
        return cuocDc;
    }

    public void setCuocDc(String cuocDc) {
        this.cuocDc = cuocDc;
    }
}
