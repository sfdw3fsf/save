package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_KT_054
 */
public class TTVT_KT_054 {
    @SerializedName("slQuaHan")
    private String slQuaHan = "0";
    @SerializedName("slDungHan")
    private String slDungHan = "0";

    public String getSlQuaHan() {
        return slQuaHan;
    }

    public void setSlQuaHan(String slQuaHan) {
        this.slQuaHan = slQuaHan;
    }

    public String getSlDungHan() {
        return slDungHan;
    }

    public void setSlDungHan(String slDungHan) {
        this.slDungHan = slDungHan;
    }
}
