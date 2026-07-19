package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * TTVT_TTKT_006
 */
@Generated("jsonschema2pojo")
public class TTVT_TTKT_011 {
    @SerializedName("ma_nv")
    private String ma_nv = "";
    @SerializedName("ten_nv")
    private String ten_nv = "";

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

    public String getTong_thietbi() {
        return tong_thietbi;
    }

    public void setTong_thietbi(String tong_thietbi) {
        this.tong_thietbi = tong_thietbi;
    }

    public String getSl_thietbi_daquet() {
        return sl_thietbi_daquet;
    }

    public void setSl_thietbi_daquet(String sl_thietbi_daquet) {
        this.sl_thietbi_daquet = sl_thietbi_daquet;
    }

    public String getSl_thietbi_chuaquet() {
        return sl_thietbi_chuaquet;
    }

    public void setSl_thietbi_chuaquet(String sl_thietbi_chuaquet) {
        this.sl_thietbi_chuaquet = sl_thietbi_chuaquet;
    }

    @SerializedName("tong_thietbi")
    private String tong_thietbi = "0";
    @SerializedName("sl_thietbi_daquet")
    private String sl_thietbi_daquet = "0";
    @SerializedName("sl_thietbi_chuaquet")
    private String sl_thietbi_chuaquet = "0";


}
