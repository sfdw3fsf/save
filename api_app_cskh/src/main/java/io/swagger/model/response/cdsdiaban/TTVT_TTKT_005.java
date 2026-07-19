package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_TTKT_005
 */
public class TTVT_TTKT_005 {
    @SerializedName("moi")
    private String moi = "";
    @SerializedName("daGiao")
    private String daGiao = "";
    @SerializedName("daTraLai")
    private String daTraLai = "";

    public String getMoi() {
        return moi;
    }

    public void setMoi(String moi) {
        this.moi = moi;
    }

    public String getDaGiao() {
        return daGiao;
    }

    public void setDaGiao(String daGiao) {
        this.daGiao = daGiao;
    }

    public String getDaTraLai() {
        return daTraLai;
    }

    public void setDaTraLai(String daTraLai) {
        this.daTraLai = daTraLai;
    }
}
