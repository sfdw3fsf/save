
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class CheckVina690MnpResponse extends SmcsBaseResponse {

    @SerializedName("apru_t1")
    @Expose
    private String apru_t1;
    @SerializedName("apru_t2")
    @Expose
    private String apru_t2;
    @SerializedName("apru_t3")
    @Expose
    private String apru_t3;

    public String getApru_t1() {
        return apru_t1;
    }

    public void setApru_t1(String apru_t1) {
        this.apru_t1 = apru_t1;
    }

    public String getApru_t2() {
        return apru_t2;
    }

    public void setApru_t2(String apru_t2) {
        this.apru_t2 = apru_t2;
    }

    public String getApru_t3() {
        return apru_t3;
    }

    public void setApru_t3(String apru_t3) {
        this.apru_t3 = apru_t3;
    }
}
