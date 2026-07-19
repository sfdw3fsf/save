package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_KT_032
 */
public class TTVT_KT_032 {
    @SerializedName("khongThiCong")
    private String khongThiCong = "";
    @SerializedName("thiCong")
    private String thiCong = "";

    public String getKhongThiCong() {
        return khongThiCong;
    }

    public void setKhongThiCong(String khongThiCong) {
        this.khongThiCong = khongThiCong;
    }

    public String getThiCong() {
        return thiCong;
    }

    public void setThiCong(String thiCong) {
        this.thiCong = thiCong;
    }
}
