package vn.vnptit.sapi.oneapp.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class TelegramService {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.url}")
    private String botUrl;

    private final TelegramConfig telegramConfig;
    private final RestTemplate restTemplate;

    public TelegramService(TelegramConfig telegramConfig, RestTemplate restTemplate) {
        this.telegramConfig = telegramConfig;
        this.restTemplate = restTemplate;
    }

    @Async
    public CompletableFuture<Object> sendMessage(String message, String groupID) {
        CompletableFuture<Object> future = new CompletableFuture<>();
        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
//            formData.add("chat_id", groupID);
//            formData.add("text", message);
//            formData.add("parse_mode", "HTML");
//            HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(formData, headers);
//            String url = telegramConfig.getBaseUrl() + "sendMessage";
//            URI uri = UriComponentsBuilder.fromHttpUrl(url).build().toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("chat_id", groupID);
            jsonMap.put("text", message);
            jsonMap.put("parse_mode", "HTML");
            jsonMap.put("token", botToken);

            HttpEntity<Map<String, Object>> body = new HttpEntity<>(jsonMap, headers);
            URI uri = UriComponentsBuilder.fromHttpUrl(botUrl).build().toUri();
            ResponseEntity<Object> responseEntity = restTemplate.postForEntity(uri, body, Object.class);

            if (responseEntity.getStatusCodeValue() == 200) {
                future.complete(responseEntity.getBody());
            } else {
                future.completeExceptionally(new RuntimeException("Failed to send message. Status code: " + responseEntity.getStatusCodeValue()));
            }
        } catch (Exception e) {
            future.completeExceptionally(e);
        }
        return future;
    }

}
