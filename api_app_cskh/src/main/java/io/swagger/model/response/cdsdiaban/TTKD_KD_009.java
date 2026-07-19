package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

/**
 * TTKD_KD_009
 */
public class TTKD_KD_009 {
    @SerializedName("mobile_money")
    private TTKD_KD_009_MobileMoney mobile_money;
    @SerializedName("vnpt_pay")
    private TTKD_KD_009_VnptPay vnpt_pay;

    public TTKD_KD_009_MobileMoney getMobile_money() {
        return mobile_money;
    }

    public void setMobile_money(TTKD_KD_009_MobileMoney mobile_money) {
        this.mobile_money = mobile_money;
    }

    public TTKD_KD_009_VnptPay getVnpt_pay() {
        return vnpt_pay;
    }

    public void setVnpt_pay(TTKD_KD_009_VnptPay vnpt_pay) {
        this.vnpt_pay = vnpt_pay;
    }
}
