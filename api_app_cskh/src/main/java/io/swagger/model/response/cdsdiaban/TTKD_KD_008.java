package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTKD_KD_008
 */
public class TTKD_KD_008 {
    @SerializedName("da_cai")
    private String da_cai = "0";
    @SerializedName("chua_cai")
    private String chua_cai = "";

    public String getDa_cai() {
        return da_cai;
    }

    public void setDa_cai(String da_cai) {
        this.da_cai = da_cai;
    }

    public String getChua_cai() {
        return chua_cai;
    }

    public void setChua_cai(String chua_cai) {
        this.chua_cai = chua_cai;
    }
}
