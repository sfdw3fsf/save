package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_KT_004
 */
public class TTVT_KT_004 {
    @SerializedName("slDungThat")
    private String slDungThat = "";
    @SerializedName("slDungThu")
    private String slDungThu = "";
    @SerializedName("slHuyDungThu")
    private String slHuyDungThu = "";

    public String getSlDungThat() {
        return slDungThat;
    }

    public void setSlDungThat(String slDungThat) {
        this.slDungThat = slDungThat;
    }

    public String getSlDungThu() {
        return slDungThu;
    }

    public void setSlDungThu(String slDungThu) {
        this.slDungThu = slDungThu;
    }

    public String getSlHuyDungThu() {
        return slHuyDungThu;
    }

    public void setSlHuyDungThu(String slHuyDungThu) {
        this.slHuyDungThu = slHuyDungThu;
    }
}
