package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * khieunai_brcd
 */
@Generated("jsonschema2pojo")
public class BRCD_KHONG_PSC {
    @SerializedName("ma_nv")
    private String ma_nv = "";
    @SerializedName("ten_nv")
    private String ten_nv = "";
    @SerializedName("tong_khong_psc")
    private String tong_khong_psc = "0";
    @SerializedName("sl_fiber_khong_psc")
    private String sl_fiber_khong_psc = "0";
    @SerializedName("sl_mytv_khong_psc")
    private String sl_mytv_khong_psc = "0";

    public String getMa_nv() {
        return ma_nv;
    }

    public void setMa_nv(String ma_nv) {
        this.ma_nv = ma_nv;
    }

    public String getTen_nv() {
        return ten_nv;
    }

    public void setTen_nv(String ten_nv) {
        this.ten_nv = ten_nv;
    }

    public String getTong_khong_psc() {
        return tong_khong_psc;
    }

    public void setTong_khong_psc(String tong_khong_psc) {
        this.tong_khong_psc = tong_khong_psc;
    }

    public String getSl_fiber_khong_psc() {
        return sl_fiber_khong_psc;
    }

    public void setSl_fiber_khong_psc(String sl_fiber_khong_psc) {
        this.sl_fiber_khong_psc = sl_fiber_khong_psc;
    }

    public String getSl_mytv_khong_psc() {
        return sl_mytv_khong_psc;
    }

    public void setSl_mytv_khong_psc(String sl_mytv_khong_psc) {
        this.sl_mytv_khong_psc = sl_mytv_khong_psc;
    }
}
