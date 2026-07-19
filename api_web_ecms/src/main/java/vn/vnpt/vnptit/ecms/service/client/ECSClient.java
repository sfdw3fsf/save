package vn.vnpt.vnptit.ecms.service.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import vn.vnpt.annotations.FeignRetry;
import vn.vnpt.exceptions.RetryableException;
import vn.vnpt.vnptit.ecms.config.MiddlewarePropeties;
import vn.vnpt.vnptit.ecms.config.RestTokenManager;
import vn.vnpt.vnptit.ecms.dto.sync.RestData;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ECSClient implements ExternalApiClient {
    private final MiddlewarePropeties config;
    private final RestTemplate restTemplate;
    private final RestTokenManager restTokenManager;

    public ECSClient(MiddlewarePropeties config,
                     @Qualifier("vcenterRestTemplate") RestTemplate restTemplate,
                     RestTokenManager restTokenManager) {
        this.config = config;
        this.restTemplate = restTemplate;
        this.restTokenManager = restTokenManager;
    }

    @Override
    public MiddlewarePropeties.SystemType getType() {
        return MiddlewarePropeties.SystemType.ECS;
    }

    @Override
    @FeignRetry(include = {RetryableException.class})
    public <T> T call(String path, HttpMethod method, Object body, Class<T> responseType) {
        T result;
        if (path.startsWith("/")) {
            path = this.config.getEcs().getBaseUrl() + path;
        } else {
            path = this.config.getEcs().getBaseUrl()+"/"+path;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-SDS-AUTH-TOKEN", restTokenManager.getToken(this.getType()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> entity = new HttpEntity<>(body, headers);
        ResponseEntity<T> response;
        try {
            response = this.restTemplate.exchange(path, method, entity, responseType);

            result = response.getBody();
        } catch (HttpClientErrorException.Unauthorized | ResourceAccessException e) {
            if (e instanceof HttpClientErrorException.Unauthorized) this.restTokenManager.forceRefresh(this.getType());
            throw new RetryableException("Retry to login: "+ e.getMessage());
        } catch (HttpClientErrorException.NotFound notFound) {
            result = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    @FeignRetry(include = {RetryableException.class})
    public <T> List<T> callList(String path, HttpMethod method, Object body, Class<T> responseType) {
        List<T> result;
        if (path.startsWith("/")) {
            path = this.config.getEcs().getBaseUrl() + path;
        } else {
            path = this.config.getEcs().getBaseUrl()+"/"+path;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-SDS-AUTH-TOKEN", restTokenManager.getToken(this.getType()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> entity = new HttpEntity<>(body, headers);
        ResponseEntity<List<T>> response;
        try {
            response = this.restTemplate.exchange(path, method, entity, ParameterizedTypeReference.forType(
                    ResolvableType
                            .forClassWithGenerics(List.class, responseType)
                            .getType()
            ));

            result = response.getBody();
        } catch (HttpClientErrorException.Unauthorized | ResourceAccessException e) {
            if (e instanceof HttpClientErrorException.Unauthorized) this.restTokenManager.forceRefresh(this.getType());
            throw new RetryableException("Retry to login: "+ e.getMessage());
        } catch (HttpClientErrorException.NotFound notFound) {
            result = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
