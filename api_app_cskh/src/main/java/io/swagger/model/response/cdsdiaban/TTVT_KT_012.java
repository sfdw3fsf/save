package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_KT_012
 */
public class TTVT_KT_012 {
    @SerializedName("hoanThanh")
    private String hoanThanh = "";
    @SerializedName("giao")
    private String giao = "";
    @SerializedName("ngay")
    private String ngay = "";

    public String getHoanThanh() {
        return hoanThanh;
    }

    public void setHoanThanh(String hoanThanh) {
        this.hoanThanh = hoanThanh;
    }

    public String getGiao() {
        return giao;
    }

    public void setGiao(String giao) {
        this.giao = giao;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
