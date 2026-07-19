package vn.vnpt.message;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import vn.vnpt.database.AppSqlException;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult extends Response {

	public ApiResult() {
		this(Message.Codes.BSS_00000000, null);
	}

	public ApiResult(AppSqlException appSqlException) {
		this.setException(appSqlException);
	}

	public ApiResult(Object data) {
		this(Message.Codes.BSS_00000000, data);
	}

	public ApiResult(Message.Codes messageCode) {
		this(messageCode, null);
	}

	public ApiResult(Message message) {
		this(message, null);
	}

	public ApiResult(Message.Codes messageCode, Object data) {
		this(Message.getMessageItem(messageCode), data);
	}

	public ApiResult(Message message, Object data) {
		this.setMessage(message);
		this.setData(data);
		this.request_id = java.util.UUID.randomUUID().toString();
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@JsonIgnore
	public String getErrorCode() {
		return error_code;
	}

	public void setErrorCode(String errorCode) {
		this.error_code = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@JsonIgnore
	public String getMessageDetail() {
		return message_detail;
	}

	public void setMessageDetail(String messageDetail) {
		this.message_detail = messageDetail;
	}

	@JsonIgnore
	public String getRequestID() {
		return request_id;
	}

	//@SuppressWarnings("unused")
	public void setRequestID(String requestID) {
		this.request_id = requestID;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setException(AppSqlException appSqlException) {
		this.setMessageDetail(appSqlException.getAppMessage());
		this.setMessage(Message.getMessageItem(Message.Codes.BSS_00009491));
	}

	public void setMessage(Message message) {
		if(message != null)
		{
			this.setErrorCode(message.getCode());
			this.setMessage(message.getContent());
		}
		else
		{
			this.setErrorCode(null);
			this.setMessage((String)null);
		}
	}

	public void setMessage(Message message, String detail) {
		this.setMessage(message);
		this.setMessageDetail(detail);
	}

	public void setMessage(Message.Codes messageCode) {
		this.setMessage(Message.getMessageItem(messageCode));
	}

	public void setMessage(Message.Codes messageCode, String detail) {
		this.setMessage(Message.getMessageItem(messageCode), detail);
	}

	public void setDataInvalidMessage(String message) {
		this.setMessage(Message.getMessageItem(Message.Codes.BSS_00009491), message);
	}

	@JsonIgnore
	public Boolean isDataEmpty() {
		return data == null;
	}

	@JsonIgnore
	public Boolean isResultError() {
		return !this.getErrorCode().equals("BSS-00000000");
	}

	@JsonIgnore
	public ResponseEntity<ApiResult> getResponseEntity() {
		if (!this.isResultError())
		{
			this.setError(String.valueOf(HttpStatus.OK.value()));
			return ResponseEntity.ok().body(this);
		}
		else {
			this.setError(String.valueOf(HttpStatus.BAD_REQUEST.value()));
			return ResponseEntity.badRequest().body(this);
		}
	}

	@JsonIgnore
	public ResponseEntity<ApiResult> getResponseEntity(Object data) {
		this.setData(data);
		return getResponseEntity();
	}

	@JsonIgnore
	public ResponseEntity<ApiResult> getResponseEntity(Message.Codes messageCode) {
		return getResponseEntity(Message.getMessageItem(messageCode));
	}

	@JsonIgnore
	public ResponseEntity<ApiResult> getResponseEntity(Message message) {
		this.setMessage(message);
		return getResponseEntity();
	}

	@JsonIgnore
	public ResponseEntity<ApiResult> getResponseEntity(AppSqlException appSqlException) {
		this.setException(appSqlException);
		return getResponseEntity();
	}

	public static ResponseEntity<ApiResult> createResponseEntity(Object data) {
		ApiResult apiResult = new ApiResult(data);
		return apiResult.getResponseEntity();
	}

	public static ResponseEntity<ApiResult> createResponseEntity(Message.Codes messageCode) {
		ApiResult apiResult = new ApiResult(messageCode);
		return apiResult.getResponseEntity();
	}

	public static ResponseEntity<ApiResult> createResponseEntity(Message message) {
		ApiResult apiResult = new ApiResult(message);
		return apiResult.getResponseEntity();
	}

	public static ResponseEntity<ApiResult> createResponseEntity(AppSqlException appSqlException) {
		ApiResult apiResult = new ApiResult(appSqlException);
		return apiResult.getResponseEntity();
	}
}
