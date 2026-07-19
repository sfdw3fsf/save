package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.redis.RedisRepository

@RestController
@RequestMapping("vnptai")
class VNPTAIService {

    static Gson gson = new GsonBuilder().serializeNulls().create()

    static final String VNPTAI_TOKEN_KEY = "VNPTAI:ACCESS_TOKEN"

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    HttpManaged httpManaged

    @Autowired
    RedisRepository redisRepository

    @Value("\${spring.httpservices.vnptai.url}")
    String url

    @Value("\${spring.httpservices.vnptai.client_id}")
    String client_id

    @Value("\${spring.httpservices.vnptai.client_secret}")
    String client_secret

    @Value("\${spring.httpservices.vnptai.grant_type}")
    String grant_type

    String postToVNPTAI(String path, String jsonBody) {
        String fullUrl = "${url}${path}"
        String token = getVNPTAIToken()
        Map<String, String> headers = ["Content-Type" : "application/json",
                                       "Authorization": "Bearer " + token,
                                       "mac-address"  : "onebss"]
        return httpManaged.post(["url": fullUrl], headers, jsonBody)
    }

    String getVNPTAIToken() {
        Object cachedToken = redisRepository.getKeyValue(VNPTAI_TOKEN_KEY)
        if (cachedToken) {
            return cachedToken.toString()
        }
        try {
            String fullUrl = "${url}/auth/oauth/token"
            Map<String, String> headers = ["Content-Type": "application/json",
                                           "Accept"      : "application/json"]
            Map body = [:]
            body["client_id"] = client_id
            body["client_secret"] = client_secret
            body["grant_type"] = grant_type
            String jsonBody = objectMapper.writeValueAsString(body)
            String response = httpManaged.post(["url": fullUrl], headers, jsonBody)
            def data = objectMapper.readValue(response, Map.class)
            String token = data.access_token
            Integer expiresIn = data.expires_in - 300 ?: 3600
            Long expireMinute = ((expiresIn - 60) / 60) as Long
            redisRepository.setKeyValueExpire(VNPTAI_TOKEN_KEY, token, expireMinute)
            return token
        } catch (Exception ex) {
            throw new RuntimeException("Lỗi khi lấy VNPT AI token: ${ex.message}", ex)
        }
    }

    @PostMapping("/lay_thongtin_tuvan")
    CommonResponse crossSellVNPTAI(@RequestBody Map body) {
        try {
            String jsonBody = gson.toJson(body)
            String path = "/iq-service/v1/cross-sell/content"
            String response = postToVNPTAI(path, jsonBody)
            def res = objectMapper.readValue(response, Map.class)
            if (res["message"] != "IDG-00000000") {
                return new CommonResponse(errorCode: 1, faultCode: res["message"], faultString: res["error"], data: res)
            }
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Thành công", data: res["object"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

}
