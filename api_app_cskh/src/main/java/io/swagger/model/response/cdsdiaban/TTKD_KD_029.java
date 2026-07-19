package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTKD_KD_029
 */
public class TTKD_KD_029 {
    @SerializedName("ptmThangTruoc")
    private String ptmThangTruoc = "0";
    @SerializedName("ptmThang")
    private String ptmThang = "0";

    public String getPtmThangTruoc() {
        return ptmThangTruoc;
    }

    public void setPtmThangTruoc(String ptmThangTruoc) {
        this.ptmThangTruoc = ptmThangTruoc;
    }

    public String getPtmThang() {
        return ptmThang;
    }

    public void setPtmThang(String ptmThang) {
        this.ptmThang = ptmThang;
    }
}
