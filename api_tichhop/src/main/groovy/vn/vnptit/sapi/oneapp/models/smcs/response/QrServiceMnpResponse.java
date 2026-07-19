
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.OtherPackage;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class QrServiceMnpResponse extends SmcsBaseResponse {

    @SerializedName("package")
    @Expose
    private String pack;
    @SerializedName("packageinf")
    @Expose
    private String packageinf;
    @SerializedName("arpu_2")
    @Expose
    private String arpu_2;
    @SerializedName("arpu_1")
    @Expose
    private String arpu_1;
    @SerializedName("cashbackinf")
    @Expose
    private String cashbackinf;
    @SerializedName("otherpackage")
    @Expose
    private List<OtherPackage> otherpackage;
    @SerializedName("case_")
    @Expose
    private String case_;

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getPackageinf() {
        return packageinf;
    }

    public void setPackageinf(String packageinf) {
        this.packageinf = packageinf;
    }

    public String getArpu_2() {
        return arpu_2;
    }

    public void setArpu_2(String arpu_2) {
        this.arpu_2 = arpu_2;
    }

    public String getArpu_1() {
        return arpu_1;
    }

    public void setArpu_1(String arpu_1) {
        this.arpu_1 = arpu_1;
    }

    public String getCashbackinf() {
        return cashbackinf;
    }

    public void setCashbackinf(String cashbackinf) {
        this.cashbackinf = cashbackinf;
    }

    public List<OtherPackage> getOtherpackage() {
        return otherpackage;
    }

    public void setOtherpackage(List<OtherPackage> otherpackage) {
        this.otherpackage = otherpackage;
    }

    public String getCase_() {
        return case_;
    }

    public void setCase_(String case_) {
        this.case_ = case_;
    }
}
