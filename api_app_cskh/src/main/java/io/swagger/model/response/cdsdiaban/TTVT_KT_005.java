package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_KT_005
 */
public class TTVT_KT_005 {
    @SerializedName("chuaQuet")
    private String chuaQuet = "";
    @SerializedName("daQuet")
    private String daQuet = "";

    public String getChuaQuet() {
        return chuaQuet;
    }

    public void setChuaQuet(String chuaQuet) {
        this.chuaQuet = chuaQuet;
    }

    public String getDaQuet() {
        return daQuet;
    }

    public void setDaQuet(String daQuet) {
        this.daQuet = daQuet;
    }
}
