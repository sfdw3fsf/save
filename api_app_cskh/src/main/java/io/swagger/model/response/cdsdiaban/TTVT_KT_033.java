package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_KT_033
 */
public class TTVT_KT_033 {
    @SerializedName("ngay")
    private String ngay = "";
    @SerializedName("ton")
    private String ton = "";

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getTon() {
        return ton;
    }

    public void setTon(String ton) {
        this.ton = ton;
    }
}
