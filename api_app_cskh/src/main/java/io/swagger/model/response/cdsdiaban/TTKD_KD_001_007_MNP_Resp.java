package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * KR001_007_MNP_Resp
 */
public class TTKD_KD_001_007_MNP_Resp {
    @SerializedName("error_code")
    private String errorCode = "0";
    @SerializedName("result")
    private List<TTKD_KD_001_007_MNP> result;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public List<TTKD_KD_001_007_MNP> getResult() {
        return result;
    }

    public void setResult(List<TTKD_KD_001_007_MNP> result) {
        this.result = result;
    }
}
