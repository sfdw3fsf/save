package vn.vnpt.message;

import com.fasterxml.jackson.annotation.JsonIgnore;

import vn.vnpt.common.errorcode.ErrorCode;

/**
 * @author Danh
 *
 */
public class Response {
	public String error;
	public String error_code = Message.Codes.BSS_00000000.name();
	public String message;
	public String message_detail;
	public Object data;
	public String request_id;
	
	@JsonIgnore
	public static Response accessDenied(String uuid) {
		Response modelResponse = new Response();
		modelResponse.error = "401";
		modelResponse.message = "User chưa đăng nhập";
		modelResponse.error_code = ErrorCode.BSS_00004001;
		modelResponse.request_id = uuid;
		return modelResponse;
	}

	@JsonIgnore
	public static Response NotFound(String uuid) {
		Response modelResponse = new Response();
		modelResponse.error = "404";
		modelResponse.message = "Không có thông tin";
		modelResponse.error_code = ErrorCode.BSS_00004000;
		modelResponse.request_id = uuid;
		return modelResponse;
	}

	@JsonIgnore
	public static Response BadRequest(String uuid) {
		Response modelResponse = new Response();
		modelResponse.error = "400";
		modelResponse.message = "Lỗi";
		modelResponse.error_code = ErrorCode.BSS_00004002;
		modelResponse.request_id = uuid;
		return modelResponse;
	}	
}
