
package vn.vnptit.sapi.oneapp.models.smcs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class GoiCuocMnp {

    @SerializedName("PHUONGAN")
    @Expose
    private String phuongAn;
    @SerializedName("LIMIT_MINUS")
    @Expose
    private String limitMinus;
    @SerializedName("MAIN_TYPE")
    @Expose
    private String mainType;
    @SerializedName("DESCRIPTION")
    @Expose
    private String description;
    @SerializedName("DOANHTHU")
    @Expose
    private String doanhThu;
    @SerializedName("PACKAGE_FEE")
    @Expose
    private String packageFee;
    @SerializedName("PACKAGE_NAME")
    @Expose
    private String packageName;
    @SerializedName("LIMIT_DATA")
    @Expose
    private String limitData;
    @SerializedName("VOICE_ADD_LIMIT")
    @Expose
    private String voiceAddLimit;
    @SerializedName("PACKAGE_ID")
    @Expose
    private String packageId;
    @SerializedName("DISPATCH_ID")
    @Expose
    private String dispatchId;

    public String getPhuongAn() {
        return phuongAn;
    }

    public void setPhuongAn(String phuongAn) {
        this.phuongAn = phuongAn;
    }

    public String getLimitMinus() {
        return limitMinus;
    }

    public void setLimitMinus(String limitMinus) {
        this.limitMinus = limitMinus;
    }

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(String doanhThu) {
        this.doanhThu = doanhThu;
    }

    public String getPackageFee() {
        return packageFee;
    }

    public void setPackageFee(String packageFee) {
        this.packageFee = packageFee;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getLimitData() {
        return limitData;
    }

    public void setLimitData(String limitData) {
        this.limitData = limitData;
    }

    public String getVoiceAddLimit() {
        return voiceAddLimit;
    }

    public void setVoiceAddLimit(String voiceAddLimit) {
        this.voiceAddLimit = voiceAddLimit;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(String dispatchId) {
        this.dispatchId = dispatchId;
    }
}
