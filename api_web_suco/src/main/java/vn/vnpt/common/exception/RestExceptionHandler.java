//package vn.vnpt.common.exception;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//import javax.validation.ConstraintViolation;
//import javax.validation.ConstraintViolationException;
//
//import org.springframework.beans.TypeMismatchException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.validation.BindException;
//import org.springframework.validation.FieldError;
//import org.springframework.validation.ObjectError;
//import org.springframework.web.HttpMediaTypeNotSupportedException;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.MissingServletRequestParameterException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//import org.springframework.web.multipart.support.MissingServletRequestPartException;
//import org.springframework.web.servlet.NoHandlerFoundException;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import vn.vnpt.common.errorcode.ErrorCode;
//import vn.vnpt.common.exception.model.ApiError;
//import vn.vnpt.common.exception.model.MessageField;
//import vn.vnpt.common.exception.model.messageObject;
//
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@ControllerAdvice
//public class RestExceptionHandler extends ResponseEntityExceptionHandler {
//
//	@Autowired
//	private MessageSource msgSource;
//
//	@Override
//	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
//			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
//
//		ApiError apiError = null;
//		List<MessageField> messageFields = new ArrayList<>();
//		List<messageObject> msgObjecs = new ArrayList<>();
//		Locale currentLocale = LocaleContextHolder.getLocale();
//
//		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
//			String fieldName = error.getField();
//			String msgError = "";
//			try {
//				msgError = msgSource.getMessage(error.getDefaultMessage(), null, currentLocale);
//			} catch (Exception e) {
//				msgError = error.getDefaultMessage();
//			}
//			MessageField msg = new MessageField(fieldName, msgError);
//			messageFields.add(msg);
//
//		}
//		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
//
//			String fieldName = error.getObjectName();
//			String msgError = "";
//			try {
//				msgError = msgSource.getMessage(error.getDefaultMessage(), null, currentLocale);
//			} catch (Exception e) {
//				msgError = error.getDefaultMessage();
//			}
//			messageObject msg = new messageObject(fieldName, msgError);
//			msgObjecs.add(msg);
//		}
////		message = new ApiError(MessageType.ERROR, msgs, msgObjecs, HttpStatus.BAD_REQUEST.toString());
//		apiError = new ApiError(messageFields, msgObjecs, HttpStatus.BAD_REQUEST);
//
//		return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);
//	}
//
//	@Override
//	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
//			final HttpStatus status, final WebRequest request) {
//		logger.info(ex.getClass().getName());
//		ApiError apiError = null;
//		List<MessageField> messageFields = new ArrayList<>();
//		List<messageObject> msgObjecs = new ArrayList<>();
//		Locale currentLocale = LocaleContextHolder.getLocale();
//		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
//			String fieldName = error.getField();
//			String msgError = "";
//			try {
//				msgError = msgSource.getMessage(error.getDefaultMessage(), null, currentLocale);
//			} catch (Exception e) {
//				msgError = error.getDefaultMessage();
//			}
//			MessageField msg = new MessageField(fieldName, msgError);
//			messageFields.add(msg);
//
//		}
//		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
//			String fieldName = error.getObjectName();
//			String msgError = "";
//			try {
//				msgError = msgSource.getMessage(error.getDefaultMessage(), null, currentLocale);
//			} catch (Exception e) {
//				msgError = error.getDefaultMessage();
//			}
//			messageObject msg = new messageObject(fieldName, msgError);
//			msgObjecs.add(msg);
//		}
//		apiError = new ApiError(messageFields, msgObjecs, HttpStatus.BAD_REQUEST);
//
//		return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);
//	}
//
//	@Override
//	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
//			final HttpStatus status, final WebRequest request) {
//		logger.info(ex.getClass().getName());
//		//
//		final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type "
//				+ ex.getRequiredType();
//
//		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ErrorCode.IDG_00000400, error);
//		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//	}
//
//	@Override
//	protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
//			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
//
//		final String error = ex.getClass().getName() + " :" + ex.getRequestPartName() + " part is missing";
//		logger.error(error);
//		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ErrorCode.IDG_00000400, error);
//		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//	}
//
//	@Override
//	protected ResponseEntity<Object> handleMissingServletRequestParameter(
//			final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status,
//			final WebRequest request) {
//		final String error = ex.getClass().getName() + " :" + ex.getParameterName() + " parameter is missing";
//		logger.error(error);
//		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ErrorCode.IDG_00000400, error);
//		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//	}
//
//	//
//
//	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
//	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex,
//			final WebRequest request) {
//		final String error = ex.getClass().getName() + " :" + ex.getName() + " should be of type "
//				+ ex.getRequiredType().getName();
//		logger.error(error);
//		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ErrorCode.IDG_00000400, error);
//		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//	}
//
//	@ExceptionHandler({ ConstraintViolationException.class })
//	public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex,
//			final WebRequest request) {
//		logger.info(ex.getClass().getName());
//		//
//		final List<String> errors = new ArrayList<String>();
//		for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
//			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
//					+ violation.getMessage());
//		}
//		logger.info(ex.getClass().getName() + errors.toString());
//		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ErrorCode.IDG_00000600);
//		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//	}
//
//	// 404
//
//	@Override
//	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
//			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
//		logger.info(ex.getClass().getName());
//		//
//		final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
//
//		final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ErrorCode.IDG_00000404, error);
//		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//	}
//
//	// 405
//
//	@Override
//	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
//			final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status,
//			final WebRequest request) {
//		logger.info(ex.getClass().getName());
//		//
//		final StringBuilder builder = new StringBuilder();
//		builder.append(ex.getMethod());
//		builder.append(" method is not supported for this request. Supported methods are ");
//		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
//
//		final ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, ErrorCode.IDG_00000405,
//				builder.toString());
//		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//	}
//
//	// 415
//
//	@Override
//	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
//			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
//		logger.info(ex.getClass().getName());
//		//
//		final StringBuilder builder = new StringBuilder();
//		builder.append(ex.getContentType());
//		builder.append(" media type is not supported. Supported media types are ");
//		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));
//
//		final ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ErrorCode.IDG_00000415,
//				builder.toString());
//		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//	}
//
//	@ExceptionHandler({ AccessDeniedException.class })
//	public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
//		final ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, ErrorCode.IDG_00000403, ex.getLocalizedMessage());
//		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//	}
//
//	// 500
//	@ExceptionHandler({ Exception.class })
//	public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
//		logger.info(ex.getClass().getName());
//		logger.error("error", ex);
//		//
//		final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.IDG_00000500,
//				"error occurred");
////		final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.IDG_00000500,
////				ex.getLocalizedMessage().substring(0, 100));
//		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
//	}
//
////	@ExceptionHandler(NotFoundException.class)
////	void handleIllegalArgumentException(NotFoundException e, HttpServletResponse response) throws IOException {
////		response.sendError(HttpStatus.NOT_FOUND.value());
////	}
//}
