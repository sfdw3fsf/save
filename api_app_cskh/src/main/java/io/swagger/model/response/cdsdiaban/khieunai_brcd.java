package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * khieunai_brcd
 */
@Generated("jsonschema2pojo")
public class khieunai_brcd {
    @SerializedName("ten_loai")
    private String ten_loai = "";
    @SerializedName("soluong_kn")
    private String soluong_kn = "0";

    public String getTen_loai() {
        return ten_loai;
    }

    public void setTen_loai(String ten_loai) {
        this.ten_loai = ten_loai;
    }

    public String getSoluong_kn() {
        return soluong_kn;
    }

    public void setSoluong_kn(String soluong_kn) {
        this.soluong_kn = soluong_kn;
    }
}
