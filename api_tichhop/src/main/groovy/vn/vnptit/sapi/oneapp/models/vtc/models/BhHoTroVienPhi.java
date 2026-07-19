package vn.vnptit.sapi.oneapp.models.vtc.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class BhHoTroVienPhi {

    @SerializedName("ownerFullname")
    @Expose
    private String ownerFullname;

    @SerializedName("ownerGender")
    @Expose
    private String ownerGender;

    @SerializedName("ownerDateOfBirth")
    @Expose
    private String ownerDateOfBirth;

    @SerializedName("ownerCardId")
    @Expose
    private String ownerCardId;

    @SerializedName("ownerPhone")
    @Expose
    private String ownerPhone;

    public String getOwnerFullname() {
        return ownerFullname;
    }

    public void setOwnerFullname(String ownerFullname) {
        this.ownerFullname = ownerFullname;
    }

    public String getOwnerGender() {
        return ownerGender;
    }

    public void setOwnerGender(String ownerGender) {
        this.ownerGender = ownerGender;
    }

    public String getOwnerDateOfBirth() {
        return ownerDateOfBirth;
    }

    public void setOwnerDateOfBirth(String ownerDateOfBirth) {
        this.ownerDateOfBirth = ownerDateOfBirth;
    }

    public String getOwnerCardId() {
        return ownerCardId;
    }

    public void setOwnerCardId(String ownerCardId) {
        this.ownerCardId = ownerCardId;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }
}
