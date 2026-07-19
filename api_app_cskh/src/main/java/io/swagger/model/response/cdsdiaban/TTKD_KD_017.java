package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTKD_KD_017
 */
public class TTKD_KD_017 {
    @SerializedName("tuVan")
    private TTKD_KD_017_TuVan tuVan;
    @SerializedName("suDung")
    private TTKD_KD_017_SuDung suDung;

    public TTKD_KD_017_TuVan getTuVan() {
        return tuVan;
    }

    public void setTuVan(TTKD_KD_017_TuVan tuVan) {
        this.tuVan = tuVan;
    }

    public TTKD_KD_017_SuDung getSuDung() {
        return suDung;
    }

    public void setSuDung(TTKD_KD_017_SuDung suDung) {
        this.suDung = suDung;
    }
}
