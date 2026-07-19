package vn.vnptit.sapi.oneapp.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.vnptit.sapi.oneapp.exceptions.SqlInjectionException;
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder;
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = SqlInjectionException.class)
    public ResponseEntity<Object> handleSqlInjectionException(SqlInjectionException ex, WebRequest request) {
        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, "", "Tham số không hợp lệ", ErrorCodeDefine.INTERNAL_SERVER_ERROR));
    }
}
