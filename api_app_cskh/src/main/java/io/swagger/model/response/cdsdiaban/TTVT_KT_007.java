package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_KT_007
 */
public class TTVT_KT_007 {
    @SerializedName("ton")
    private String ton = "";
    @SerializedName("giao")
    private String giao = "";

    public String getTon() {
        return ton;
    }

    public void setTon(String ton) {
        this.ton = ton;
    }

    public String getGiao() {
        return giao;
    }

    public void setGiao(String giao) {
        this.giao = giao;
    }
}
