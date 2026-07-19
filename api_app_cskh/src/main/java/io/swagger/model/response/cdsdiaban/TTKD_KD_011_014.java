package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * TTKD_KD_011_014
 */
public class TTKD_KD_011_014 {
    @SerializedName("TTKD_KD_011")
    private TTKD_KD_011 ttkd_kd_011;
    @SerializedName("TTKD_KD_012")
    private List<TTKD_KD_012> ttkd_kd_012;
    @SerializedName("TTKD_KD_013")
    private TTKD_KD_013 ttkd_kd_013;
    @SerializedName("TTKD_KD_014")
    private TTKD_KD_014 ttkd_kd_014;

    public List<TTKD_KD_012> getTtkd_kd_012() {
        return ttkd_kd_012;
    }

    public void setTtkd_kd_012(List<TTKD_KD_012> ttkd_kd_012) {
        this.ttkd_kd_012 = ttkd_kd_012;
    }

    public TTKD_KD_011 getTtkd_kd_011() {
        return ttkd_kd_011;
    }

    public void setTtkd_kd_011(TTKD_KD_011 ttkd_kd_011) {
        this.ttkd_kd_011 = ttkd_kd_011;
    }

    public TTKD_KD_013 getTtkd_kd_013() {
        return ttkd_kd_013;
    }

    public void setTtkd_kd_013(TTKD_KD_013 ttkd_kd_013) {
        this.ttkd_kd_013 = ttkd_kd_013;
    }

    public TTKD_KD_014 getTtkd_kd_014() {
        return ttkd_kd_014;
    }

    public void setTtkd_kd_014(TTKD_KD_014 ttkd_kd_014) {
        this.ttkd_kd_014 = ttkd_kd_014;
    }
}
