package vn.vnptit.sapi.oneapp.models.vtc.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class BhHoGiaDinh {

    @SerializedName("houseAddress")
    @Expose
    private String houseAddress;

    @SerializedName("owner")
    @Expose
    private String owner;

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
