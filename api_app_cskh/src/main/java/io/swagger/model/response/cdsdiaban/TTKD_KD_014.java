package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTKD_KD_014
 */
public class TTKD_KD_014 {
    @SerializedName("daKS")
    private String daKS;
    @SerializedName("chuaKS")
    private String chuaKS;

    public String getDaKS() {
        return daKS;
    }

    public void setDaKS(String daKS) {
        this.daKS = daKS;
    }

    public String getChuaKS() {
        return chuaKS;
    }

    public void setChuaKS(String chuaKS) {
        this.chuaKS = chuaKS;
    }
}
