package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * khieunai_brcd
 */
@Generated("jsonschema2pojo")
public class ThongTinChiTietThuCuocGachNo {
    @SerializedName("maTt")
    private String maTt = "";
    @SerializedName("tenTT")
    private String tenTt = "";
    @SerializedName("soDt")
    private String soDt = "";
    @SerializedName("diaChiTt")
    private String diaChiTt = "";
    @SerializedName("tongNo")
    private String tongNo = "";

    public String getMaTt() {
        return maTt;
    }

    public void setMaTt(String maTt) {
        this.maTt = maTt;
    }

    public String getTenTt() {
        return tenTt;
    }

    public void setTenTt(String tenTt) {
        this.tenTt = tenTt;
    }

    public String getSoDt() {
        return soDt;
    }

    public void setSoDt(String soDt) {
        this.soDt = soDt;
    }

    public String getDiaChiTt() {
        return diaChiTt;
    }

    public void setDiaChiTt(String diaChiTt) {
        this.diaChiTt = diaChiTt;
    }

    public String getTongNo() {
        return tongNo;
    }

    public void setTongNo(String tongNo) {
        this.tongNo = tongNo;
    }
}
