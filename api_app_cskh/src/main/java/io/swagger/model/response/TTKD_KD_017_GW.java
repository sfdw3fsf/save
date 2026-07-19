
package io.swagger.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class TTKD_KD_017_GW {

    @SerializedName("ACCOUNT")
    @Expose
    private String account;
    @SerializedName("PS_LL")
    @Expose
    private Integer psLL;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getPsLL() {
        return psLL;
    }

    public void setPsLL(Integer psLL) {
        this.psLL = psLL;
    }
}
