package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTKD_KD_018_DS
 */
public class TTKD_KD_018_DS {
    @SerializedName("maTB")
    private String maTB = "";
    @SerializedName("tinhTrang")
    private String tinhTrang = "";
    @SerializedName("ngayTT")
    private String ngayTT = "";

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getNgayTT() {
        return ngayTT;
    }

    public void setNgayTT(String ngayTT) {
        this.ngayTT = ngayTT;
    }
}
