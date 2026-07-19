package com.vn.vnptit.Oneapp_Com.common;

import java.net.URI;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import com.vn.vnptit.Oneapp_Com.model.MessageToBotTele;
import com.vn.vnptit.Oneapp_Com.model.ResponseObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CallHttp {

	@Async
	public void optionsCall(String message) {
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					String url = "https://api.telegram.org/bot5685297859:AAH5ewOcX6LjrYA2QBljOMWwwL6ndvB4A7E/sendMessage";
					RestTemplate restTemplate = new RestTemplate();

					URI uri = new URI(url);

					MessageToBotTele body = new MessageToBotTele(message, true, false, null, "-763902366");

					HttpHeaders headers = new HttpHeaders();
					headers.set("Content-Type", "application/json");
					HttpEntity<Object> request = new HttpEntity<>(body, headers);

					restTemplate.postForEntity(uri, request, Object.class);
				} catch (Exception e) {
				}

			}
		};
		t.start();

	}

	public static ResponseObject httpPostBody(Map<String, String> header, Object body, String url) {
		ResponseObject modelResponse = new ResponseObject();

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();

		// set `content-type` header
		headers.setContentType(MediaType.APPLICATION_JSON);
		if (header.size() > 0) {
			for (Map.Entry<String, String> entry : header.entrySet()) {
				headers.set(entry.getKey(), entry.getValue());
			}
		}

		HttpEntity<Object> request = new HttpEntity<>(body, headers);
		try {
			ResponseEntity<Object> result = restTemplate.postForEntity(url, request, Object.class);
			if (result.getStatusCodeValue() == 200) {
				// Thành công -→ lấy body ra xử lý
				modelResponse.setData(result.getBody());
			} else {
				modelResponse.setError("" + result.getStatusCodeValue());
				modelResponse.setMessage("Lỗi khi thực hiện");
			}

		} catch (Exception e) {
			modelResponse.setError("1");
			modelResponse.setMessage(e.getMessage());
			return modelResponse;

		}
		return modelResponse;
	}

	public static ResponseObject httpPostBody_v2(Map<String, String> header, Object body, String url) {
		ResponseObject modelResponse = new ResponseObject();

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();

		// set `content-type` header
		headers.setContentType(MediaType.APPLICATION_JSON);
		if (header.size() > 0) {
			for (Map.Entry<String, String> entry : header.entrySet()) {
				headers.set(entry.getKey(), entry.getValue());
			}
		}

		HttpEntity<Object> request = new HttpEntity<>(body, headers);
		try {
			ResponseEntity<String> result = restTemplate.postForEntity(url, request, String.class);
			if (result.getStatusCodeValue() == 200) {
				modelResponse.setData(result.getBody());
			} else {
				modelResponse.setError("" + result.getStatusCodeValue());
				modelResponse.setMessage("Lỗi khi thực hiện");
			}

		} catch (Exception e) {
			modelResponse.setError("1");
			modelResponse.setMessage(e.getMessage());
			return modelResponse;

		}
		return modelResponse;
	}

}
