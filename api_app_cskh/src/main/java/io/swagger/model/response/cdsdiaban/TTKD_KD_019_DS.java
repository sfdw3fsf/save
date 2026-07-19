package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTKD_KD_019_DS
 */
public class TTKD_KD_019_DS {
    @SerializedName("maTT")
    private String maTT = "";
    @SerializedName("tinhTrang")
    private String tinhTrang = "";
    @SerializedName("ngayTT")
    private String ngayTT = "";

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
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
