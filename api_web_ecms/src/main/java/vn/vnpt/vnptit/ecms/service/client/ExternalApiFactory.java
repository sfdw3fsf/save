package vn.vnpt.vnptit.ecms.service.client;

import org.springframework.stereotype.Component;
import vn.vnpt.vnptit.ecms.config.MiddlewarePropeties;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ExternalApiFactory {
    private final Map<MiddlewarePropeties.SystemType, ExternalApiClient> clientMap;

    public ExternalApiFactory(List<ExternalApiClient> clients) {
        this.clientMap = clients.stream()
                .collect(Collectors.toMap(ExternalApiClient::getType, c -> c));
    }

    public ExternalApiClient getClient(MiddlewarePropeties.SystemType type) {
        return clientMap.get(type);
    }
}
