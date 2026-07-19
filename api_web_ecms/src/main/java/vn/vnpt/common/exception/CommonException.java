//package vn.vnpt.common.exception;
//
//import org.springframework.http.HttpStatus;
//
//import vn.vnpt.common.Common;
//import vn.vnpt.common.errorcode.ErrorCode;
//import vn.vnpt.common.exception.model.ApiError;
//
//public class CommonException extends RuntimeException {
//
//	private ApiError apiError;
//
//	public ApiError getApiError() {
//		return apiError;
//	}
//
//	public void setApiError(ApiError apiError) {
//		this.apiError = apiError;
//	}
//
//	public static long getSerialversionuid() {
//		return serialVersionUID;
//	}
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 2407102742288585067L;
//
//	private CommonException(ApiError apiError) {
//		super(apiError.getMessage());
//		this.apiError = apiError;
//	}
//
//	public static CommonException of() {
//		return ofAbnormal(HttpStatus.BAD_REQUEST, ErrorCode.IDG_00000499, ErrorCode.IDG_00000499);
//	}
//
//	static CommonException of(HttpStatus httpStatus, String errorCode, String message) {
//		switch (httpStatus) {
//		case NOT_FOUND:
//			return ofNotFound(errorCode, message);
//		case BAD_REQUEST:
//			return ofBadRequest(errorCode, message);
//		case FORBIDDEN:
//			return ofForbidden(errorCode, message);
//		case INTERNAL_SERVER_ERROR:
//			return ofIntenalServerError(errorCode, message);
//		default:
//			return ofAbnormal(httpStatus, errorCode, message);
//		}
//	}
//
//	static CommonException ofNotFound(String errorCode, String message) {
//		ApiError apiError = new ApiError();
//		apiError.setStatus(HttpStatus.NOT_FOUND);
//		apiError.setError(Common.isNullOrEmpty(errorCode) ? ErrorCode.IDG_00000404 : errorCode);
//		apiError.setError(Common.isNullOrEmpty(message) ? ErrorCode.IDG_00000404 : message);
//		return new CommonException(apiError);
//	}
//
//	static CommonException ofBadRequest(String errorCode, String message) {
//		ApiError apiError = new ApiError();
//		apiError.setStatus(HttpStatus.BAD_REQUEST);
//		apiError.setError(Common.isNullOrEmpty(errorCode) ? ErrorCode.IDG_00000400 : errorCode);
//		apiError.setError(Common.isNullOrEmpty(message) ? ErrorCode.IDG_00000400 : message);
//		return new CommonException(apiError);
//	}
//
//	static CommonException ofIntenalServerError(String errorCode, String message) {
//		ApiError apiError = new ApiError();
//		apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//		apiError.setError(Common.isNullOrEmpty(errorCode) ? ErrorCode.IDG_00000500 : errorCode);
//		apiError.setError(Common.isNullOrEmpty(message) ? ErrorCode.IDG_00000500 : message);
//		return new CommonException(apiError);
//	}
//
//	static CommonException ofForbidden(String errorCode, String message) {
//		ApiError apiError = new ApiError();
//		apiError.setStatus(HttpStatus.FORBIDDEN);
//		apiError.setError(Common.isNullOrEmpty(errorCode) ? ErrorCode.IDG_00000403 : errorCode);
//		apiError.setError(Common.isNullOrEmpty(message) ? ErrorCode.IDG_00000403 : message);
//		return new CommonException(apiError);
//	}
//
//	static CommonException ofAbnormal(HttpStatus httpStatus, String errorCode, String message) {
//		ApiError apiError = new ApiError();
//		apiError.setStatus(httpStatus != null ? httpStatus : HttpStatus.BAD_REQUEST);
//		apiError.setError(Common.isNullOrEmpty(errorCode) ? ErrorCode.IDG_00000499 : errorCode);
//		apiError.setError(Common.isNullOrEmpty(message) ? ErrorCode.IDG_00000499 : message);
//		return new CommonException(apiError);
//	}
//
//}
