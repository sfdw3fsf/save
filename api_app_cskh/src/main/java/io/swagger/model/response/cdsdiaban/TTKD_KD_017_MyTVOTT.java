package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTKD_KD_017_MyTVOTT
 */
public class TTKD_KD_017_MyTVOTT {
    @SerializedName("phatSinhLL")
    private String phatSinhLL = "0";
    @SerializedName("khongPhatSinhLL")
    private String khongPhatSinhLL = "0";

    public String getPhatSinhLL() {
        return phatSinhLL;
    }

    public void setPhatSinhLL(String phatSinhLL) {
        this.phatSinhLL = phatSinhLL;
    }

    public String getKhongPhatSinhLL() {
        return khongPhatSinhLL;
    }

    public void setKhongPhatSinhLL(String khongPhatSinhLL) {
        this.khongPhatSinhLL = khongPhatSinhLL;
    }
}
