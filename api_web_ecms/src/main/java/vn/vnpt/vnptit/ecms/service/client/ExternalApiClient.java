package vn.vnpt.vnptit.ecms.service.client;

import org.springframework.http.HttpMethod;
import vn.vnpt.vnptit.ecms.config.MiddlewarePropeties;

import java.util.List;

public interface ExternalApiClient {
    MiddlewarePropeties.SystemType getType();
    <T> T call(String path, HttpMethod method, Object body, Class<T> responseType);
    <T> List<T>  callList(String path, HttpMethod method, Object body, Class<T> responseType);
}
