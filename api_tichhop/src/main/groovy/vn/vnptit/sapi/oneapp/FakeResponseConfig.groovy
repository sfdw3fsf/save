package vn.vnptit.sapi.oneapp

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

import java.util.concurrent.atomic.AtomicBoolean

@Configuration
class FakeResponseConfig {
    @Bean("fakeSuccessCommonResponseValue")
    @Scope("singleton")
    AtomicBoolean getFakeSuccessCommonResponseValue() {
        AtomicBoolean
        new AtomicBoolean(true)
    }
}
