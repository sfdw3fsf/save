package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTKD_KD_017
 */
public class TTKD_KD_017_TuVan {
    @SerializedName("daSuDungDaTuVan")
    private String daSuDungDaTuVan;
    @SerializedName("daSuDungChuaTuVan")
    private String daSuDungChuaTuVan;
    @SerializedName("chuaSuDungDaTuVan")
    private String chuaSuDungDaTuVan;

    public String getDaSuDungDaTuVan() {
        return daSuDungDaTuVan;
    }

    public void setDaSuDungDaTuVan(String daSuDungDaTuVan) {
        this.daSuDungDaTuVan = daSuDungDaTuVan;
    }

    public String getDaSuDungChuaTuVan() {
        return daSuDungChuaTuVan;
    }

    public void setDaSuDungChuaTuVan(String daSuDungChuaTuVan) {
        this.daSuDungChuaTuVan = daSuDungChuaTuVan;
    }

    public String getChuaSuDungDaTuVan() {
        return chuaSuDungDaTuVan;
    }

    public void setChuaSuDungDaTuVan(String chuaSuDungDaTuVan) {
        this.chuaSuDungDaTuVan = chuaSuDungDaTuVan;
    }
}
