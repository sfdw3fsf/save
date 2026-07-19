
package io.swagger.model.response.cdsdiaban;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CheckHRMCodeResponse {

    @SerializedName("errorCode")
    @Expose
    private String errorCode;
    @SerializedName("errorDescription")
    @Expose
    private String errorDescription;
    @SerializedName("info")
    @Expose
    private HRMInfo HRMInfo;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public HRMInfo getInfo() {
        return HRMInfo;
    }

    public void setInfo(HRMInfo HRMInfo) {
        this.HRMInfo = HRMInfo;
    }

}
