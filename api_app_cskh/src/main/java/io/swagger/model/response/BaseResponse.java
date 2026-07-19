package io.swagger.model.response;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * BaseResponse
 */
@Validated
public class BaseResponse<T> {
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

    public BaseResponse message(String message) {
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

    public BaseResponse messageDetail(String messageDetail) {
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

    public BaseResponse requestId(String requestId) {
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
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseResponse baseResponse = (BaseResponse) o;
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
    private String toIndentedString(java.lang.Object o) {
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
