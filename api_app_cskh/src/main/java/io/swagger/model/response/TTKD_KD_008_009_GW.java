
package io.swagger.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class TTKD_KD_008_009_GW {

    @SerializedName("DINH_DANH")
    @Expose
    private Integer dinhDanh;
    @SerializedName("IS_MOBILE_MONEY")
    @Expose
    private Integer isMobileMoney;
    @SerializedName("PHONE_NUMBER")
    @Expose
    private String phoneNumber;
    @SerializedName("LIENKET_NGANHANG")
    @Expose
    private Integer lienKetNH;
    @SerializedName("GIAO_DICH")
    @Expose
    private Integer giaoDich;

    public Integer getDinhDanh() {
        return dinhDanh;
    }

    public void setDinhDanh(Integer dinhDanh) {
        this.dinhDanh = dinhDanh;
    }

    public Integer getIsMobileMoney() {
        return isMobileMoney;
    }

    public void setIsMobileMoney(Integer isMobileMoney) {
        this.isMobileMoney = isMobileMoney;
    }

    public Integer getLienKetNH() {
        return lienKetNH;
    }

    public void setLienKetNH(Integer lienKetNH) {
        this.lienKetNH = lienKetNH;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getGiaoDich() {
        return giaoDich;
    }

    public void setGiaoDich(Integer giaoDich) {
        this.giaoDich = giaoDich;
    }
}
