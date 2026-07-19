package vn.vnpt.common.success.model;

public class ApiResult<T> {

	private String message;

	private T object;

	public ApiResult(String message, T object) {
		super();
		this.message = message;
		this.object = object;
	}

	public ApiResult(String message) {
		super();
		this.message = message;
	}

	public ApiResult() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

}
