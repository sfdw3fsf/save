package vn.vnpt.common.response;

import vn.vnpt.common.exception.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseEntites<T> {

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	private int status;

	private String message;

	private T data;

	public ResponseEntites() {
		super();
	}

	public ResponseEntites(HttpStatus status, String message, T data) {
		this.status = status.value();
		this.message = message;
		this.data = data;
	}

	public ResponseEntity<?> createSuccessResponse(HttpStatus status) {
		return new ResponseEntity<>(status);
	}

	public ResponseEntity<?> createSuccessResponse(HttpStatus status, T data) {
		return new ResponseEntity<>(data, status);
	}

	public ResponseEntity<?> createErrorResponse(HttpStatus status, T data) {
		return new ResponseEntity<>(data, status);
	}

	public ResponseEntity<?> createErrorResponse(HttpStatus status, final String errorMessage) {
		final ApiError apiError = new ApiError(status, errorMessage);
		return new ResponseEntity<>(apiError, status);
	}

	public ResponseEntity<?> createErrorResponse(final HttpStatus status, final String errorMessage,
			final String responseData) {
		final ApiError apiError = new ApiError(status, errorMessage, responseData);
		return new ResponseEntity<>(apiError, status);
	}

}