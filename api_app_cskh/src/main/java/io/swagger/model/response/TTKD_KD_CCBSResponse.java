
package io.swagger.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.swagger.model.dto.TTThueBaoCCBS;
import io.swagger.model.response.cdsdiaban.TTKD_KD_CCBS;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class TTKD_KD_CCBSResponse {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("error_code")
    @Expose
    private String errorCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("message_detail")
    @Expose
    private Object messageDetail;
    @SerializedName("request_id")
    @Expose
    private String requestId;
    @SerializedName("data")
    @Expose
    private List<TTKD_KD_CCBS> data = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(Object messageDetail) {
        this.messageDetail = messageDetail;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<TTKD_KD_CCBS> getData() {
        return data;
    }

    public void setData(List<TTKD_KD_CCBS> data) {
        this.data = data;
    }
}
