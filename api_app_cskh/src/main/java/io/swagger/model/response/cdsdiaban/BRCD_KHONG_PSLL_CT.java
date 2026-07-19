package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * khieunai_brcd
 */
@Generated("jsonschema2pojo")
public class BRCD_KHONG_PSLL_CT {
    @SerializedName("maTB")
    private String maTB = "";
    @SerializedName("dienThoaiLH")
    private String dienThoaiLH = "";
    @SerializedName("ngaySL")
    private String ngaySL = "";
    @SerializedName("kieu")
    private String kieu = "";
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

    public String getDienThoaiLH() {
        return dienThoaiLH;
    }

    public void setDienThoaiLH(String dienThoaiLH) {
        this.dienThoaiLH = dienThoaiLH;
    }

    public String getNgaySL() {
        return ngaySL;
    }

    public void setNgaySL(String ngaySL) {
        this.ngaySL = ngaySL;
    }

    public String getKieu() {
        return kieu;
    }

    public void setKieu(String kieu) {
        this.kieu = kieu;
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
