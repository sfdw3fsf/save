package io.swagger.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * BaseResponse
 */
@Validated
//@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")

public class BaseResponse<T> {
    @JsonProperty("error")
    protected String error = "0";
    @JsonProperty("error_code")
    protected String errorCode = "0";
    /*@JsonProperty("code")
    protected String code = "0";*/
    @JsonProperty("message")
    protected String message = "";
    @JsonProperty("message_detail")
    protected String messageDetail = null;
    @JsonProperty("request_id")
    protected String requestId = null;

    /*@JsonProperty("retryable")
    private Boolean retryable = null;

    @JsonProperty("object_ref_type")
    private String objectRefType = null;

    @JsonProperty("object_ref_id")
    private Long objectRefId = null;*/

    /*public BaseResponse code(String code) {
        this.code = code;
        return this;
    }*/

    /**
     * Get code
     *
     * @return code
     **/
    @Schema(description = "")

    /*public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }*/

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

    /*public BaseResponse retryable(Boolean retryable) {
        this.retryable = retryable;
        return this;
    }

    *//**
     * Get retryable
     *
     * @return retryable
     **//*
    @Schema(description = "")

    public Boolean isRetryable() {
        return retryable;
    }

    public void setRetryable(Boolean retryable) {
        this.retryable = retryable;
    }

    public BaseResponse objectRefType(String objectRefType) {
        this.objectRefType = objectRefType;
        return this;
    }

    *//**
     * Get objectRefType
     *
     * @return objectRefType
     **//*
    @Schema(description = "")

    public String getObjectRefType() {
        return objectRefType;
    }

    public void setObjectRefType(String objectRefType) {
        this.objectRefType = objectRefType;
    }

    public BaseResponse objectRefId(Long objectRefId) {
        this.objectRefId = objectRefId;
        return this;
    }

    *//**
     * Get objectRefId
     *
     * @return objectRefId
     **//*
    @Schema(description = "")

    public Long getObjectRefId() {
        return objectRefId;
    }

    public void setObjectRefId(Long objectRefId) {
        this.objectRefId = objectRefId;
    }*/


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
                Objects.equals(this.requestId, baseResponse.requestId) /*&&
                Objects.equals(this.code, baseResponse.code) &&
                Objects.equals(this.retryable, baseResponse.retryable) &&
                Objects.equals(this.objectRefType, baseResponse.objectRefType) &&
                Objects.equals(this.objectRefId, baseResponse.objectRefId)*/;
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, messageDetail, requestId);//code, retryable, objectRefType, objectRefId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BaseResponse {\n");

        //sb.append("    code: ").append(toIndentedString(code)).append("\n");
        sb.append("    message: ").append(toIndentedString(message)).append("\n");
        sb.append("    messageDetail: ").append(toIndentedString(messageDetail)).append("\n");
        sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
        /*sb.append("    retryable: ").append(toIndentedString(retryable)).append("\n");
        sb.append("    objectRefType: ").append(toIndentedString(objectRefType)).append("\n");
        sb.append("    objectRefId: ").append(toIndentedString(objectRefId)).append("\n");*/
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
