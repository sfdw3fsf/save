package vn.vnptit.sapi.oneapp.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.http.HttpStatus;
import io.swagger.model.BaseResponse;

public class BaseResponseBuilder {
    public static final String CODE_OK = "BSS-00000000";
    public static final String CODE_NO_CONTENT = "BSS-00000204";
    static PropertiesConfiguration props = null;

    static {
        props = new PropertiesConfiguration();
        props.setDelimiterParsingDisabled(true);
        props.setEncoding("UTF-8");
        try {
            props.load(BaseResponseBuilder.class.getClassLoader().getResourceAsStream("error_codes.properties"));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static <T> BaseResponse<T> of(T data, String request_id, String message, String status) {
        BaseResponse<T> obj = new BaseResponse<T>();
        //obj.setCode(status);
        obj.setErrorCode(status);
        obj.setError(status);
        obj.setRequestId(request_id);
        if (message == null) {
            obj.setMessage(props.getString(status, "unset"));
        } else
            obj.setMessage(message);
        obj.setData(data);

        return obj;
    }

    @Deprecated
    public static <T> BaseResponse<T> of(T data, String request_id, String message, HttpStatus status) {
        if (status == HttpStatus.OK) {
            return of(data, request_id, message, CODE_OK);
        } else {
            return of(data, request_id, message, "BSS-00000" + status.value());
        }
    }

    public static <T> BaseResponse<T> of(T data, String request_id, String message) {
        if (data instanceof Iterable<?>) {
            if (!((Iterable) data).iterator().hasNext())
                return of(data, request_id, message, CODE_NO_CONTENT);
            else
                return of(data, request_id, message, CODE_OK);
        } else
            return of(data, request_id, message, CODE_OK);
    }

    public static <T> BaseResponse<T> of(T data, String request_id) {
        return of(data, request_id, null);
    }

    public static <T> BaseResponse<T> of(T data) {
        return of(data, null, null);
    }
}
