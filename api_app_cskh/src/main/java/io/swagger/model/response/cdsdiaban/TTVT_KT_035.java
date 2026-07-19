package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTVT_KT_035
 */
public class TTVT_KT_035 {
    @SerializedName("tongCH")
    private String tongCH = "";
    @SerializedName("slTraLoiDung")
    private String slTraLoiDung = "";

    public String getTongCH() {
        return tongCH;
    }

    public void setTongCH(String tongCH) {
        this.tongCH = tongCH;
    }

    public String getSlTraLoiDung() {
        return slTraLoiDung;
    }

    public void setSlTraLoiDung(String slTraLoiDung) {
        this.slTraLoiDung = slTraLoiDung;
    }
}
