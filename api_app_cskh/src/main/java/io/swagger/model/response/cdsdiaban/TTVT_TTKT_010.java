package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * TTVT_TTKT_006
 */
@Generated("jsonschema2pojo")
public class TTVT_TTKT_010 {
    @SerializedName("ma_nv")
    private String ma_nv = "";
    @SerializedName("ten_nv")
    private String ten_nv = "";
    @SerializedName("dat")
    private String dat = "0";
    @SerializedName("khong_dat")
    private String khong_dat = "0";

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

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getKhong_dat() {
        return khong_dat;
    }

    public void setKhong_dat(String khong_dat) {
        this.khong_dat = khong_dat;
    }
}
