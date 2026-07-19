package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * KR013_MNP
 */
public class TTVT_KT_013 {
    @SerializedName("maTB")
    private String maTB = "";
    @SerializedName("thoiGian")
    private String thoiGian = "";
    @SerializedName("trangThai")
    private String trangThai = "";

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
