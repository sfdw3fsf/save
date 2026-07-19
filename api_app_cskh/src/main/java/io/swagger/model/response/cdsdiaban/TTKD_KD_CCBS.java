package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTKD_KD_CCBS
 */
public class TTKD_KD_CCBS {
    @SerializedName("SL")
    private Integer soLuong = 0;
    @SerializedName("NGAY")
    private String ngay = "";
    @SerializedName("SOTIEN")
    private Long soTien = 0L;

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public Long getSoTien() {
        return soTien;
    }

    public void setSoTien(Long soTien) {
        this.soTien = soTien;
    }
}
