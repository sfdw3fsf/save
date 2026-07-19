package vn.vnpt.vnptit.ecms.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vn.vnpt.vnptit.ecms.config.MiddlewarePropeties;
import vn.vnpt.vnptit.ecms.dto.sync.RestData;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VCenterClient implements ExternalApiClient {

    private final MiddlewarePropeties config;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public VCenterClient(MiddlewarePropeties config,
                         @Qualifier("vcenterRestTemplate") RestTemplate restTemplate) {
        this.config = config;
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public MiddlewarePropeties.SystemType getType() {
        return MiddlewarePropeties.SystemType.VCENTER;
    }

    @Override
    public <T> T call(String path, HttpMethod method, Object body, Class<T> responseType) {
        if (path.startsWith("/")) {
            path = this.config.getVcenter().getBaseUrl() + path;
        } else {
            path = this.config.getVcenter().getBaseUrl()+"/"+path;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-private-key", this.config.getVcenter().getPrivateKey());
        headers.set("x-app-name", this.config.getVcenter().getAppName());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> entity = new HttpEntity<>(body, headers);

        ResponseEntity<RestData> response = this.restTemplate.exchange(path, method, entity, RestData.class);
        if (response.getBody()==null) return null;
        return this.objectMapper.convertValue(response.getBody().getData(), responseType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> callList(String path, HttpMethod method, Object body, Class<T> responseType) {
        if (path.startsWith("/")) {
            path = this.config.getVcenter().getBaseUrl() + path;
        } else {
            path = this.config.getVcenter().getBaseUrl()+"/"+path;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-private-key", this.config.getVcenter().getPrivateKey());
        headers.set("x-app-name", this.config.getVcenter().getAppName());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> entity = new HttpEntity<>(body, headers);
        ResponseEntity<RestData> response = this.restTemplate.exchange(path, method, entity, RestData.class);
        if (response.getBody()==null) return null;
        List<Object> dataList = (List<Object>) response.getBody().getData();
        List<T> result = dataList.stream()
                .map(i->this.objectMapper.convertValue(i, responseType))
                .collect(Collectors.toList());
        return result;
    }
}
