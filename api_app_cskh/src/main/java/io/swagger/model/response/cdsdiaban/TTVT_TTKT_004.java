package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_TTKT_004
 */
public class TTVT_TTKT_004 {
    @SerializedName("moi")
    private String moi = "0";
    @SerializedName("ton")
    private String ton = "0";
    @SerializedName("daTra")
    private String daTra = "0";
    @SerializedName("daGiao")
    private String daGiao = "0";

    public String getMoi() {
        return moi;
    }

    public void setMoi(String moi) {
        this.moi = moi;
    }

    public String getTon() {
        return ton;
    }

    public void setTon(String ton) {
        this.ton = ton;
    }

    public String getDaTra() {
        return daTra;
    }

    public void setDaTra(String daTra) {
        this.daTra = daTra;
    }

    public String getDaGiao() {
        return daGiao;
    }

    public void setDaGiao(String daGiao) {
        this.daGiao = daGiao;
    }
}
