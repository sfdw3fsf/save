package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_KT_011
 */
public class TTVT_KT_011 {
    @SerializedName("phieu12")
    private String phieu12 = "";
    @SerializedName("phieu12_16")
    private String phieu12_16 = "";
    @SerializedName("phieu16")
    private String phieu16 = "";

    public String getPhieu12() {
        return phieu12;
    }

    public void setPhieu12(String phieu12) {
        this.phieu12 = phieu12;
    }

    public String getPhieu12_16() {
        return phieu12_16;
    }

    public void setPhieu12_16(String phieu12_16) {
        this.phieu12_16 = phieu12_16;
    }

    public String getPhieu16() {
        return phieu16;
    }

    public void setPhieu16(String phieu16) {
        this.phieu16 = phieu16;
    }
}
