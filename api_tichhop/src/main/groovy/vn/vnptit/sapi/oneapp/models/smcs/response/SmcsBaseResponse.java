
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class SmcsBaseResponse {

    @SerializedName("error_code")
    @Expose
    private String error_code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("qlsp_msg")
    @Expose
    private String qlsp_msg;
    @SerializedName("errCode_qlsp")
    @Expose
    private String errCodeQlsp;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getQlsp_msg() {
        return qlsp_msg;
    }

    public void setQlsp_msg(String qlsp_msg) {
        this.qlsp_msg = qlsp_msg;
    }

    public String getErrCodeQlsp() {
        return errCodeQlsp;
    }

    public void setErrCodeQlsp(String errCodeQlsp) {
        this.errCodeQlsp = errCodeQlsp;
    }
}
