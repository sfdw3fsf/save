package vn.vnptit.sapi.oneapp.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TelegramConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public String getBaseUrl() {
        return "https://api.telegram.org/bot6309451397:AAGnZVK1pT1DmQqFgl1MQXQuXTqyqixRQ1I/";
    }
}

