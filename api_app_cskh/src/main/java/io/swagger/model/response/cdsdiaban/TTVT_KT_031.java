package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_KT_031
 */
public class TTVT_KT_031 {
    @SerializedName("duocGiao")
    private String duocGiao = "";
    @SerializedName("hoanThanh")
    private String hoanThanh = "";
    @SerializedName("ngay")
    private String ngay = "";

    public String getDuocGiao() {
        return duocGiao;
    }

    public void setDuocGiao(String duocGiao) {
        this.duocGiao = duocGiao;
    }

    public String getHoanThanh() {
        return hoanThanh;
    }

    public void setHoanThanh(String hoanThanh) {
        this.hoanThanh = hoanThanh;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
