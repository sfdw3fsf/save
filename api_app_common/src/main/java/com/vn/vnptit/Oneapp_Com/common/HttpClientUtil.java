package com.vn.vnptit.Oneapp_Com.common;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public class HttpClientUtil {

	private final RestTemplate restTemplate;

	public HttpClientUtil() {
		this.restTemplate = new RestTemplate();

		this.restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			// Danhnq 07/12/2025:
			// Ghi đè ErrorHandler để bỏ qua việc ném lỗi khi gặp HTTP status error
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}

		});
	}

	public <T> T doGet(String url, Map<String, String> headers, Class<T> responseType) {
		HttpHeaders httpHeaders = new HttpHeaders();
		if (headers != null) {
			headers.forEach(httpHeaders::add);
		}
		httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
		return response.getBody();
	}

	public <T> T doPost(String url, Map<String, String> headers, Object body, Class<T> responseType) {
		HttpHeaders httpHeaders = new HttpHeaders();
		if (headers != null) {
			headers.forEach(httpHeaders::add);
		}
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Object> entity = new HttpEntity<>(body, httpHeaders);

		// Lúc này, dù API trả về 500 hay 400, RestTemplate vẫn sẽ map JSON trả về vào
		// responseType
		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, entity, responseType);

		return response.getBody();
	}
}
