package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.tts.Data
import vn.vnptit.sapi.oneapp.util.LoggingInOut

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("tts")
class TTSService {

    @Autowired
    LoggingManaged loggingManaged

    @Value("\${spring.httpservices.tts.url}")
    String url

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    HttpManaged httpManaged

    @PostMapping("/saveCSSAPI")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse saveCSSAPI(@Valid @RequestBody Data body) {
        try {
            String path = url + "APIDHSXKD/SaveCSSAPI"
            String resp = httpManaged.post(["url": path], ["Content-Type": "application/json"], body.data)
            try {
                def res = objectMapper.readValue(resp, Map.class)
                if (res["STATUS"].toString() != "1") {
                    return new CommonResponse(errorCode: 1, faultCode: "", faultString: res["MESSAGE"], data: null)
                }

                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
            } catch (Exception ignored) {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: resp, data: null)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

}