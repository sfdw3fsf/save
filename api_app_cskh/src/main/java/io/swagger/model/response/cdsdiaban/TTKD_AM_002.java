package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTKD_AM_002
 */
public class TTKD_AM_002 {
    @SerializedName("maNV")
    private String maNV = "";
    @SerializedName("tenNV")
    private String tenNV = "";
    @SerializedName("chuaThu")
    private String chuaThu = "0";
    @SerializedName("daThu")
    private String daThu = "0";

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getChuaThu() {
        return chuaThu;
    }

    public void setChuaThu(String chuaThu) {
        this.chuaThu = chuaThu;
    }

    public String getDaThu() {
        return daThu;
    }

    public void setDaThu(String daThu) {
        this.daThu = daThu;
    }
}
