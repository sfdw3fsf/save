package vn.vnptit.sapi.oneapp.kafka.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import vn.vnptit.sapi.oneapp.managed.LoggingManaged;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class KafkaProducerService {

    @Autowired
    LoggingManaged log;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        String requestId = UUID.randomUUID().toString();
        Map<String, Object> mlog = new HashMap<>();
        mlog.put("request_id", requestId);
        mlog.put("action", "kafka_sendMessage");
        mlog.put("message", message);
        log.info(mlog);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                mlog.put("response", "success");
                log.info(mlog);
            }

            @Override
            public void onFailure(Throwable ex) {
                mlog.put("response", "error: " + ex.getMessage());
                log.info(mlog);
                ex.printStackTrace();
            }
        });
    }

}
