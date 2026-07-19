package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * KR001_007_DCRS_Resp
 */
public class TTKD_KD_001_007_DCRS_Resp {
    @SerializedName("error_code")
    private String errorCode = "0";
    @SerializedName("message")
    private String message = "";
    @SerializedName("result")
    private List<TTKD_KD_001_007_DCRS> result;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public List<TTKD_KD_001_007_DCRS> getResult() {
        return result;
    }

    public void setResult(List<TTKD_KD_001_007_DCRS> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
