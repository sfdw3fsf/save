package vn.vnpt.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import vn.vnpt.common.errorcode.ErrorCode;

/**
 * @author Danh
 *
 */
public class Response {
	private String error;
	private String error_code = Message.Codes.BSS_00000000.name();
	private String message;
	private String message_detail;
	protected Object data;
	private String request_id;

	// Constructors, getters, and setters (if needed)

	public static Response accessDenied(String uuid) {
		Response response = new Response();
		response.setError("401");
		response.setMessage("User chưa đăng nhập");
		response.setErrorCode(ErrorCode.BSS_00004001);
		response.setRequest_id(uuid);
		return response;
	}

	public static Response notFound(String uuid) {
		Response response = new Response();
		response.setError("404");
		response.setMessage("Không có thông tin");
		response.setErrorCode(ErrorCode.BSS_00004000);
		response.setRequest_id(uuid);
		return response;
	}

	public static Response badRequest(String uuid) {
		Response response = new Response();
		response.setError("400");
		response.setMessage("Lỗi");
		response.setErrorCode(ErrorCode.BSS_00004002);
		response.setRequest_id(uuid);
		return response;
	}

	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@JsonProperty("message_detail")
	public String getMessageDetail() {
		return message_detail;
	}

	public void setMessageDetail(String message_detail) {
		this.message_detail = message_detail;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@JsonProperty("error_code")
	public String getErrorCode() {
		return error_code;
	}

	public void setErrorCode(String error_code) {
		this.error_code = error_code;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
