package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * TTKD_KD_019
 */
public class TTKD_KD_019 {
    @SerializedName("bdCoCau")
    private List<TTKD_KD_019_BD_CoCau> bdCoCau;
    @SerializedName("bdCot")
    private List<TTKD_KD_019_BD_Cot> bdCot;
    @SerializedName("danhSach")
    private List<TTKD_KD_019_DS> danhSach;

    public List<TTKD_KD_019_BD_CoCau> getBdCoCau() {
        return bdCoCau;
    }

    public void setBdCoCau(List<TTKD_KD_019_BD_CoCau> bdCoCau) {
        this.bdCoCau = bdCoCau;
    }

    public List<TTKD_KD_019_BD_Cot> getBdCot() {
        return bdCot;
    }

    public void setBdCot(List<TTKD_KD_019_BD_Cot> bdCot) {
        this.bdCot = bdCot;
    }

    public List<TTKD_KD_019_DS> getDanhSach() {
        return danhSach;
    }

    public void setDanhSach(List<TTKD_KD_019_DS> danhSach) {
        this.danhSach = danhSach;
    }
}
