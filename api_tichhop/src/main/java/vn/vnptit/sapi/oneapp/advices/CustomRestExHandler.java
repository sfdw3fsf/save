package vn.vnptit.sapi.oneapp.advices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import vn.vnptit.sapi.oneapp.managed.LoggingManaged;
import vn.vnptit.sapi.oneapp.models.CommonResponse;
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder;

@ControllerAdvice
public class CustomRestExHandler {
    static String DUONGDAN_KODUNG = "Đường dẫn không đúng";

    @Autowired
    LoggingManaged loggingManaged;

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadableException(
            Exception ex, WebRequest request) {
        String errMsg = ex.getMessage();
        loggingManaged.error(ex.getMessage(), ex);
        try {
            if (errMsg.startsWith("JSON parse error")) {
                errMsg = errMsg.split(";")[0];
            }
        } catch (Exception ee) {
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponse(404, errMsg));
    }

    /*@ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<Object> handleError404(HttpServletRequest request, Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponseBuilder.ex(null, null, DUONGDAN_KODUNG, HttpStatus.NOT_FOUND));
    }*/
}
