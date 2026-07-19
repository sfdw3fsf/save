package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_KT_040
 */
public class TTVT_KT_040 {
    @SerializedName("ngay")
    private String ngay = "";
    @SerializedName("moi")
    private String moi = "";
    @SerializedName("lai")
    private String lai = "";

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getMoi() {
        return moi;
    }

    public void setMoi(String moi) {
        this.moi = moi;
    }

    public String getLai() {
        return lai;
    }

    public void setLai(String lai) {
        this.lai = lai;
    }
}
