package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_KT_027
 */
public class TTVT_KT_027 {
    @SerializedName("tbHong")
    private String tbHong = "";
    @SerializedName("tbSdDuoc")
    private String tbSdDuoc = "";

    public String getTbHong() {
        return tbHong;
    }

    public void setTbHong(String tbHong) {
        this.tbHong = tbHong;
    }

    public String getTbSdDuoc() {
        return tbSdDuoc;
    }

    public void setTbSdDuoc(String tbSdDuoc) {
        this.tbSdDuoc = tbSdDuoc;
    }
}
