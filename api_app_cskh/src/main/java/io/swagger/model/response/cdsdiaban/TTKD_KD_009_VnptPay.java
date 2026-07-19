package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTKD_KD_008_009_VnptPay
 */
public class TTKD_KD_009_VnptPay {
    @SerializedName("da_dinh_danh")
    private String da_dinh_danh = "0";
    @SerializedName("chua_dinh_danh")
    private String chua_dinh_danh = "0";
    @SerializedName("da_lien_ket")
    private String da_lien_ket = "0";
    @SerializedName("da_thanh_toan")
    private String da_thanh_toan = "0";
    @SerializedName("chua_lien_ket")
    private String chua_lien_ket = "0";
    @SerializedName("chua_thanh_toan")
    private String chua_thanh_toan = "0";

    public String getDa_dinh_danh() {
        return da_dinh_danh;
    }

    public void setDa_dinh_danh(String da_dinh_danh) {
        this.da_dinh_danh = da_dinh_danh;
    }

    public String getChua_dinh_danh() {
        return chua_dinh_danh;
    }

    public void setChua_dinh_danh(String chua_dinh_danh) {
        this.chua_dinh_danh = chua_dinh_danh;
    }

    public String getDa_lien_ket() {
        return da_lien_ket;
    }

    public void setDa_lien_ket(String da_lien_ket) {
        this.da_lien_ket = da_lien_ket;
    }

    public String getDa_thanh_toan() {
        return da_thanh_toan;
    }

    public void setDa_thanh_toan(String da_thanh_toan) {
        this.da_thanh_toan = da_thanh_toan;
    }

    public String getChua_lien_ket() {
        return chua_lien_ket;
    }

    public void setChua_lien_ket(String chua_lien_ket) {
        this.chua_lien_ket = chua_lien_ket;
    }

    public String getChua_thanh_toan() {
        return chua_thanh_toan;
    }

    public void setChua_thanh_toan(String chua_thanh_toan) {
        this.chua_thanh_toan = chua_thanh_toan;
    }
}
