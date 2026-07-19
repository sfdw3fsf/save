package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_KT_018
 */
public class TTVT_KT_018 {
    @SerializedName("ton")
    private String ton = "";
    @SerializedName("hoanThanh")
    private String hoanThanh = "";

    public String getTon() {
        return ton;
    }

    public void setTon(String ton) {
        this.ton = ton;
    }

    public String getHoanThanh() {
        return hoanThanh;
    }

    public void setHoanThanh(String hoanThanh) {
        this.hoanThanh = hoanThanh;
    }
}
