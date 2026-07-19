package vn.vnpt.context;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.UnexpectedTypeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.exc.InputCoercionException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.Message;
import vn.vnpt.message.ModelValidation;

@ControllerAdvice
public class AppResponseExceptionHandler extends ResponseEntityExceptionHandler {
	static final String THAM_SO = "Tham số";
//    @ExceptionHandler(value = SqlInjectionException.class)
//    public ResponseEntity<Object> handleSqlInjectionException(SqlInjectionException ex, WebRequest request) {
//		ApiResult apiResult = new ApiResult(Message.Codes.BSS_00009490);
//		apiResult.setMessageDetail("Tham số đầu vào không đúng định dạng.");
//		apiResult.setError(String.valueOf(HttpStatus.BAD_REQUEST.value()));
//		return new ResponseEntity<Object>(apiResult, new HttpHeaders(), HttpStatus.BAD_REQUEST);
//    }
	private static final Logger logger = LoggerFactory.getLogger(AppResponseExceptionHandler.class);
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		ApiResult apiResult = new ApiResult(Message.Codes.BSS_00000500);
		apiResult.setMessageDetail(ex.getMessage());
		apiResult.setError(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		return new ResponseEntity<Object>(apiResult, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, String> errors = new HashMap<>();
		Class<?> parameterClazz = ex.getParameter().getParameterType();

		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			String fieldName = fieldError.getField();
			try {
				Field field = parameterClazz.getDeclaredField(fieldName);
				fieldName = Optional.ofNullable(field.getAnnotation(JsonProperty.class))
						.map(JsonProperty::value)
						.orElseGet(field::getName);
			} catch (NoSuchFieldException e) {
				logger.error("NoSuchFieldException occurred while processing field error.", e);
			}
			errors.put(fieldName, fieldError.getDefaultMessage());
		}
		ApiResult apiResult = new ApiResult(Message.Codes.BSS_00009490);
		apiResult.setData(errors);
		apiResult.setError(String.valueOf(HttpStatus.BAD_REQUEST.value()));
		logger.error("Method argument validation failed.", ex);
		return new ResponseEntity<Object>(apiResult, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ApiResult apiResult = new ApiResult(Message.Codes.BSS_00009490);
		
		String msg = "Giá trị " + ex.getValue() + " không đúng định dạng (" + ex.getRequiredType() + ").";
		if (ex instanceof MethodArgumentTypeMismatchException) {
			MethodArgumentTypeMismatchException e = (MethodArgumentTypeMismatchException) ex;
			msg = THAM_SO + " " + e.getName() + " (" + e.getValue() + ") không đúng định dạng (" + e.getRequiredType() + ").";
		};
		apiResult.setMessageDetail(msg);
		apiResult.setError(String.valueOf(HttpStatus.BAD_REQUEST.value()));
		return new ResponseEntity<Object>(apiResult, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiResult apiResult = new ApiResult(Message.Codes.BSS_00000400);
		apiResult.setMessageDetail("Không tìm thấy tham số " + ex.getParameterName());
		apiResult.setError(String.valueOf(HttpStatus.BAD_REQUEST.value()));
		return new ResponseEntity<Object>(apiResult, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ApiResult apiResult = new ApiResult(Message.Codes.BSS_00000404);
		apiResult.setMessageDetail(ex.getMessage());
		apiResult.setError(String.valueOf(HttpStatus.NOT_FOUND.value()));
		return new ResponseEntity<Object>(apiResult, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiResult apiResult = new ApiResult(Message.Codes.BSS_00009490);
		Throwable mostSpecificCause = ex.getMostSpecificCause();
		
		if (mostSpecificCause instanceof InvalidFormatException)
		{
			InvalidFormatException invalidFormatException = (InvalidFormatException) mostSpecificCause;
			String msg = "";
			List<Reference> paths = invalidFormatException.getPath();
			for (Reference path : paths) {
				if(path.getFieldName() != null)
					msg += THAM_SO + " " + path.getFieldName() + ": " + ModelValidation.INVALID + ".";
			}
			apiResult.setMessageDetail(msg);
		}
		else if(mostSpecificCause instanceof InputCoercionException)
		{
			InputCoercionException inputCoercionException = (InputCoercionException) mostSpecificCause;
			try {
				apiResult.setMessageDetail(THAM_SO + " " + inputCoercionException.getProcessor().getCurrentName() + " (" + inputCoercionException.getProcessor().getText() + ") không đúng định dạng (" + inputCoercionException.getTargetType().toString()
						+ ").");
			} catch (IOException e) {
				apiResult.setMessageDetail(inputCoercionException.getOriginalMessage());
			}
		}
		else if(mostSpecificCause instanceof JsonParseException)
		{
			JsonParseException jsonParseException = (JsonParseException) mostSpecificCause;
			try {
				apiResult.setMessageDetail("Định dạng JSON lỗi tham số " + jsonParseException.getProcessor().getCurrentName() + ".");
			} catch (IOException e) {
				apiResult.setMessageDetail(jsonParseException.getOriginalMessage());
			}
		}
		else
		{
			String message = ex.getMessage();
			if(message.startsWith("Required request body is missing"))
				message = "Nội dung yêu cầu bắt buộc trong body bị thiếu.";
			apiResult.setMessageDetail(message);
		}
		apiResult.setError(String.valueOf(HttpStatus.BAD_REQUEST.value()));
		return new ResponseEntity<Object>(apiResult, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		ApiResult apiResult = new ApiResult(Message.Codes.BSS_00000409);
		apiResult.setMessageDetail(ex.getMessage());
		apiResult.setError(String.valueOf(HttpStatus.CONFLICT.value()));
		return new ResponseEntity<Object>(apiResult, new HttpHeaders(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler({ UnexpectedTypeException.class })
	public ResponseEntity<Object> handleUnexpectedTypeException(Exception ex, WebRequest request) {
		UnexpectedTypeException e = (UnexpectedTypeException) ex;
		ApiResult apiResult = new ApiResult(Message.Codes.BSS_00009490);
		apiResult.setMessageDetail(e.getMessage());
		apiResult.setError(String.valueOf(HttpStatus.BAD_REQUEST.value()));
		return new ResponseEntity<Object>(apiResult, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ AppSqlException.class })
	public ResponseEntity<Object> appSQLExceptionHandler(AppSqlException ex, HttpServletRequest request) {
		ex.printStackTrace();
		ApiResult apiResult = new ApiResult(Message.Codes.BSS_00009490);
		apiResult.setMessageDetail(ex.getAppFullMessage());
		apiResult.setMessage(ex.getAppMessage());
		apiResult.setRequest_id(request.getRequestURI());
		apiResult.setError(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		return new ResponseEntity<>(apiResult, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
