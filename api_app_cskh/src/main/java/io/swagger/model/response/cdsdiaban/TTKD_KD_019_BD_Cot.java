package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTKD_KD_019_BD_Cot
 */
public class TTKD_KD_019_BD_Cot {
    @SerializedName("ngayTT")
    private String ngayTT = "";
    @SerializedName("slHDDaThu")
    private String slHDDaThu = "";
    @SerializedName("slHDDaThuLuyKe")
    private String slHDDaThuLuyKe = "";
    @SerializedName("tienDaThu")
    private String tienDaThu = "";
    @SerializedName("tienDaThuLuyKe")
    private String tienDaThuLuyKe = "";

    public String getNgayTT() {
        return ngayTT;
    }

    public void setNgayTT(String ngayTT) {
        this.ngayTT = ngayTT;
    }

    public String getSlHDDaThu() {
        return slHDDaThu;
    }

    public void setSlHDDaThu(String slHDDaThu) {
        this.slHDDaThu = slHDDaThu;
    }

    public String getSlHDDaThuLuyKe() {
        return slHDDaThuLuyKe;
    }

    public void setSlHDDaThuLuyKe(String slHDDaThuLuyKe) {
        this.slHDDaThuLuyKe = slHDDaThuLuyKe;
    }

    public String getTienDaThu() {
        return tienDaThu;
    }

    public void setTienDaThu(String tienDaThu) {
        this.tienDaThu = tienDaThu;
    }

    public String getTienDaThuLuyKe() {
        return tienDaThuLuyKe;
    }

    public void setTienDaThuLuyKe(String tienDaThuLuyKe) {
        this.tienDaThuLuyKe = tienDaThuLuyKe;
    }
}
