package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * KR001_007_DCRS
 */
public class TTKD_KD_001_007_DCRS {
    @SerializedName("SO_LAN")
    private Integer soLuong = 0;
    @SerializedName("DATE_")
    private String ngay = "";
    @SerializedName("TONG_TIEN")
    private Long soTien = 0L;

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Long getSoTien() {
        return soTien;
    }

    public void setSoTien(Long soTien) {
        this.soTien = soTien;
    }
}
