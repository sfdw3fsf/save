package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTKD_KD_010
 */
public class TTKD_KD_010 {
    @SerializedName("giayBaoCuoc")
    private String giayBaoCuoc = "0";
    @SerializedName("bienNhanThanhToan")
    private String bienNhanThanhToan = "";
    @SerializedName("phuLucCuocVienThong")
    private String phuLucCuocVienThong = "0";
    @SerializedName("bienNhanTraTruoc")
    private String bienNhanTraTruoc = "";

    public String getGiayBaoCuoc() {
        return giayBaoCuoc;
    }

    public void setGiayBaoCuoc(String giayBaoCuoc) {
        this.giayBaoCuoc = giayBaoCuoc;
    }

    public String getBienNhanThanhToan() {
        return bienNhanThanhToan;
    }

    public void setBienNhanThanhToan(String bienNhanThanhToan) {
        this.bienNhanThanhToan = bienNhanThanhToan;
    }

    public String getPhuLucCuocVienThong() {
        return phuLucCuocVienThong;
    }

    public void setPhuLucCuocVienThong(String phuLucCuocVienThong) {
        this.phuLucCuocVienThong = phuLucCuocVienThong;
    }

    public String getBienNhanTraTruoc() {
        return bienNhanTraTruoc;
    }

    public void setBienNhanTraTruoc(String bienNhanTraTruoc) {
        this.bienNhanTraTruoc = bienNhanTraTruoc;
    }
}
