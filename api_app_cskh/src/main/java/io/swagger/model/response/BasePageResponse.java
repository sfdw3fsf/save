package io.swagger.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * BaseResponse
 */
@Validated
public class BasePageResponse<T, P> {
    @JsonProperty("error")
    protected String error = "0";
    @JsonProperty("error_code")
    protected String errorCode = "0";
    @JsonProperty("message")
    protected String message = "";
    @JsonProperty("message_detail")
    protected String messageDetail = null;
    @JsonProperty("request_id")
    protected String requestId = null;

    public BasePageResponse message(String message) {
        this.message = message;
        return this;
    }

    /**
     * Get message
     *
     * @return message
     **/
    @Schema(description = "")

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BasePageResponse messageDetail(String messageDetail) {
        this.messageDetail = messageDetail;
        return this;
    }

    /**
     * Get messageDetail
     *
     * @return messageDetail
     **/
    @Schema(description = "")

    public String getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail;
    }

    public BasePageResponse requestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    /**
     * Get requestId
     *
     * @return requestId
     **/
    @Schema(description = "")

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BasePageResponse baseResponse = (BasePageResponse) o;
        return Objects.equals(this.message, baseResponse.message) &&
                Objects.equals(this.messageDetail, baseResponse.messageDetail) &&
                Objects.equals(this.error, baseResponse.error) &&
                Objects.equals(this.errorCode, baseResponse.errorCode) &&
                Objects.equals(this.requestId, baseResponse.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error, errorCode, message, messageDetail, requestId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BaseResponse {\n");
        sb.append("    error: ").append(toIndentedString(error)).append("\n");
        sb.append("    message: ").append(toIndentedString(message)).append("\n");
        sb.append("    messageDetail: ").append(toIndentedString(messageDetail)).append("\n");
        sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
        sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    @JsonProperty("data")
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @JsonProperty("page_info")
    private P pageInfo;

    public P getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(P pageInfo) {
        this.pageInfo = pageInfo;
    }

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
}
