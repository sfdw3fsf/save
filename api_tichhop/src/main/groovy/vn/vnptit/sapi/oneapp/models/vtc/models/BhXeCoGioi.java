package vn.vnptit.sapi.oneapp.models.vtc.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class BhXeCoGioi {

    @SerializedName("cerPlatenumber")
    @Expose
    private String cerPlatenumber;

    @SerializedName("cerChassisNumber")
    @Expose
    private String cerChassisNumber;

    @SerializedName("cerEngineNumber")
    @Expose
    private String cerEngineNumber;

    @SerializedName("cerFullname")
    @Expose
    private String cerFullname;

    @SerializedName("cerAddress")
    @Expose
    private String cerAddress;

    @SerializedName("ownerPhone")
    @Expose
    private String ownerPhone;

    public String getCerPlatenumber() {
        return cerPlatenumber;
    }

    public void setCerPlatenumber(String cerPlatenumber) {
        this.cerPlatenumber = cerPlatenumber;
    }

    public String getCerChassisNumber() {
        return cerChassisNumber;
    }

    public void setCerChassisNumber(String cerChassisNumber) {
        this.cerChassisNumber = cerChassisNumber;
    }

    public String getCerEngineNumber() {
        return cerEngineNumber;
    }

    public void setCerEngineNumber(String cerEngineNumber) {
        this.cerEngineNumber = cerEngineNumber;
    }

    public String getCerFullname() {
        return cerFullname;
    }

    public void setCerFullname(String cerFullname) {
        this.cerFullname = cerFullname;
    }

    public String getCerAddress() {
        return cerAddress;
    }

    public void setCerAddress(String cerAddress) {
        this.cerAddress = cerAddress;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }
}
