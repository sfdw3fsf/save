package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.kafka.services.KafkaProducerService
import vn.vnptit.sapi.oneapp.models.CommonResponse

@RestController
@RequestMapping("/kafka")
public class KafkaService {

    @Value("\${spring.kafka.topic}")
    private String defaultTopic

    @Autowired
    KafkaProducerService kafkaProducerService

    @PostMapping("/sendMessage")
    public CommonResponse sendMessage(@RequestBody Map<String, Object> message) throws JsonProcessingException {
        try {
            String mess = new ObjectMapper().writeValueAsString(message)
            kafkaProducerService.sendMessage(defaultTopic, mess)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: null)
        } catch (Exception ex) {
            return new CommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null)
        }
    }

}
