package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTKD_KD_019_BD_CoCau
 */
public class TTKD_KD_019_BD_CoCau {
    @SerializedName("slMyVnpt")
    private String slMyVnpt = "";
    @SerializedName("slKhac")
    private String slKhac = "";
    @SerializedName("slChuaThu")
    private String slChuaThu = "";
    @SerializedName("tienMyVnpt")
    private String tienMyVnpt = "";
    @SerializedName("tienKhac")
    private String tienKhac = "";
    @SerializedName("tienChuaThu")
    private String tienChuaThu = "";

    public String getSlMyVnpt() {
        return slMyVnpt;
    }

    public void setSlMyVnpt(String slMyVnpt) {
        this.slMyVnpt = slMyVnpt;
    }

    public String getSlKhac() {
        return slKhac;
    }

    public void setSlKhac(String slKhac) {
        this.slKhac = slKhac;
    }

    public String getSlChuaThu() {
        return slChuaThu;
    }

    public void setSlChuaThu(String slChuaThu) {
        this.slChuaThu = slChuaThu;
    }

    public String getTienMyVnpt() {
        return tienMyVnpt;
    }

    public void setTienMyVnpt(String tienMyVnpt) {
        this.tienMyVnpt = tienMyVnpt;
    }

    public String getTienKhac() {
        return tienKhac;
    }

    public void setTienKhac(String tienKhac) {
        this.tienKhac = tienKhac;
    }

    public String getTienChuaThu() {
        return tienChuaThu;
    }

    public void setTienChuaThu(String tienChuaThu) {
        this.tienChuaThu = tienChuaThu;
    }
}
