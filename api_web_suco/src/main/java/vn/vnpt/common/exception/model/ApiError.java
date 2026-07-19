package vn.vnpt.common.exception.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ApiError {
	private List<messageObject> messageObjects;
	private List<MessageField> messageFields;
	private String statusCode;
	private String message;
	private HttpStatus status;
	private String error;

	public List<messageObject> getMessageObjects() {
		return messageObjects;
	}

	public void setMessageObjects(List<messageObject> messageObjects) {
		this.messageObjects = messageObjects;
	}

	public List<MessageField> getMessageFields() {
		return messageFields;
	}

	public void setMessageFields(List<MessageField> messageFields) {
		this.messageFields = messageFields;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public ApiError() {
		super();
	}

	public ApiError(List<MessageField> messageFields, List<messageObject> messageObjects, HttpStatus status) {
		super();
		this.messageObjects = messageObjects;
		this.messageFields = messageFields;
		this.message = "";
		this.status = status;
		this.statusCode = status.toString();
		this.error = "";
	}

	public ApiError(HttpStatus status, String message, List<MessageField> messageFields,
			List<messageObject> messageObjects) {
		super();
		this.messageObjects = messageObjects;
		this.messageFields = messageFields;
		this.message = message;
		this.statusCode = status.toString();
		this.status = status;
		this.error = "";
	}

	public ApiError(HttpStatus status, String message) {
		super();
		this.messageObjects = new ArrayList<>();
		this.messageFields = new ArrayList<>();
		this.message = message;
		this.statusCode = status.toString();
		this.status = status;
		this.error = "";
	}

	public ApiError(HttpStatus status, String message, String error) {
		super();
		this.messageObjects = new ArrayList<>();
		this.messageFields = new ArrayList<>();
		this.message = message;
		this.statusCode = status.toString();
		this.status = status;
		this.error = error;
	}
}
