package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_KT_053
 */
public class TTVT_KT_053 {
    @SerializedName("ngay")
    private String ngay = "";
    @SerializedName("slPhieuHoanThanh")
    private String slPhieuHoanThanh = "0";
    @SerializedName("slPhieuDuocGiao")
    private String slPhieuDuocGiao = "0";

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getSlPhieuHoanThanh() {
        return slPhieuHoanThanh;
    }

    public void setSlPhieuHoanThanh(String slPhieuHoanThanh) {
        this.slPhieuHoanThanh = slPhieuHoanThanh;
    }

    public String getSlPhieuDuocGiao() {
        return slPhieuDuocGiao;
    }

    public void setSlPhieuDuocGiao(String slPhieuDuocGiao) {
        this.slPhieuDuocGiao = slPhieuDuocGiao;
    }
}
