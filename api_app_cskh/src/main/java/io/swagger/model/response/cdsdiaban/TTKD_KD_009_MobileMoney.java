package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTKD_KD_008_009_MobileMoney
 */
public class TTKD_KD_009_MobileMoney {
    @SerializedName("da_dk")
    private String da_dk = "0";
    @SerializedName("chua_dk")
    private String chua_dk = "0";

    public String getDa_dk() {
        return da_dk;
    }

    public void setDa_dk(String da_dk) {
        this.da_dk = da_dk;
    }

    public String getChua_dk() {
        return chua_dk;
    }

    public void setChua_dk(String chua_dk) {
        this.chua_dk = chua_dk;
    }
}
