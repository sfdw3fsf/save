package vn.vnptit.sapi.oneapp.advices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.vnptit.sapi.oneapp.managed.LoggingManaged;
import vn.vnptit.sapi.oneapp.models.CommonResponse;
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder;

import vn.vnptit.sapi.oneapp.interceptor.SqlInjectionException;
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
    static String THAMSO_KODUNG = "Tham số đầu vào không đúng định dạng.";
    static String DUONGDAN_KODUNG = "Đường dẫn không đúng";
    @Autowired
    LoggingManaged loggingManaged;

    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }
        return ResponseEntity.badRequest().body(new CommonResponse(400, String.join(", ", errors)));
    }

    @ExceptionHandler(value = SqlInjectionException.class)
    public ResponseEntity<Object> handleSqlInjectionException(SqlInjectionException ex, WebRequest request) {
        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, "", THAMSO_KODUNG, ErrorCodeDefine.INTERNAL_SERVER_ERROR));
    }

//    @ExceptionHandler({OneBssException.class, Exception.class})
//    protected ResponseEntity<Object> handleOneBssException(
//            Exception ex, WebRequest request) {
//        loggingManaged.error(ex.getMessage(), ex);
//        if (ex instanceof OneBssException) {
//            OneBssException obe = (OneBssException) ex;
//            if (obe.getMessage().equals("Token/App-secret is not valid"))
//                return ResponseEntity.badRequest().body(BaseResponseBuilder.ex(null, null, obe.getMessage(), HttpStatus.BAD_REQUEST));
//            else
//                return ResponseEntity.badRequest().body(BaseResponseBuilder.ex(null, null, ex.getMessage(), HttpStatus.BAD_REQUEST));
//        } else if (ex instanceof UndeclaredThrowableException) {
//            //Main ex = SqlException
//            UndeclaredThrowableException ute = (UndeclaredThrowableException) ex;
//
//            Throwable innerEx = ute.getUndeclaredThrowable();
//
//            return ResponseEntity.badRequest().body(BaseResponseBuilder.ex(null, null, innerEx.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
//
//        } else {
//            return ResponseEntity.badRequest().body(BaseResponseBuilder.ex(null, null, ex.getMessage(), HttpStatus.BAD_REQUEST));
//        }
//    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (FieldError violation : ex.getFieldErrors()) {
            errors.add(violation.getDefaultMessage() + " (" + violation.getField() + ": " + violation.getRejectedValue() + ")");
        }
        return ResponseEntity.badRequest().body(new CommonResponse(400, String.join(", ", errors)));
    }

    /*@ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<Object> handleError404(HttpServletRequest request, Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponseBuilder.ex(null, null, DUONGDAN_KODUNG, HttpStatus.NOT_FOUND));
    }*/

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponse(404, ex.getMessage()));
    }
}
