
package vn.vnptit.sapi.oneapp.models.smcs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class OtherPackage {

    @SerializedName("servicecode")
    @Expose
    private String servicecode;
    @SerializedName("des")
    @Expose
    private String des;

    public String getServicecode() {
        return servicecode;
    }

    public void setServicecode(String servicecode) {
        this.servicecode = servicecode;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
