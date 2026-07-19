package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * khieunai_brcd
 */
@Generated("jsonschema2pojo")
public class BRCD_KHONG_PSC_CT {
    @SerializedName("maTB")
    private String maTB = "";
    @SerializedName("soDT")
    private String soDT = "";
    @SerializedName("loaiTB")
    private String loaiTB = "";
    @SerializedName("tenTB")
    private String tenTB = "";
    @SerializedName("diaChiLD")
    private String diaChiLD = "";

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getLoaiTB() {
        return loaiTB;
    }

    public void setLoaiTB(String loaiTB) {
        this.loaiTB = loaiTB;
    }

    public String getTenTB() {
        return tenTB;
    }

    public void setTenTB(String tenTB) {
        this.tenTB = tenTB;
    }

    public String getDiaChiLD() {
        return diaChiLD;
    }

    public void setDiaChiLD(String diaChiLD) {
        this.diaChiLD = diaChiLD;
    }
}
