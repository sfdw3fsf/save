package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.model.BaseResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.service.ConsentWaitService
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.MessageUtils

import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RestController
@RequestMapping("vneid")
class VneIDService {

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    HttpManaged httpManaged

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    ConsentWaitService consentWaitService

    @Value("\${spring.httpservices.vneid.url}")
    String host

    @Value("\${spring.httpservices.vneid.key}")
    String key

    @PostMapping("/share_info")
    ResponseEntity<BaseResponse> share_info(@RequestBody Map body) {
        try {
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")
            headers.put("api_key", key)

            String url = host + "/api/vneid/shareInfo"
            String resp = httpManaged.post([url: url], headers, objectMapper.writeValueAsString(body))
            def res = objectMapper.readValue(resp, Map.class)
            if (res["errorCode"].toString() == "0") {
                if (res["data"]["txnId"]) {
                    // Có txnId => đợi KH xác nhận trên app vneid
                    String requestId = res["data"]["requestId"].toString()
                    CompletableFuture<Map> future = consentWaitService.register(requestId)
                    try {
                        Map trans = future.get(300, TimeUnit.SECONDS)
                        // Lấy được xác nhận từ KH
                        String resultCode = trans["resultCode"]
                        if (resultCode == "1") {
                            return ResponseEntity.ok().body(BaseResponseBuilder.of(trans))
                        } else if (resultCode == "2") {
                            throw new Exception("KH không đồng ý chia sẻ")
                        } else {
                            throw new Exception("Trạng thái không hợp lệ: " + resultCode)
                        }
                    } catch (TimeoutException ignored) {
                        consentWaitService.timeout(requestId)
                        throw new Exception("Quá thời gian xử lý")
                    } catch (Exception ex) {
                        throw new Exception("Có lỗi xảy ra khi gửi yêu cầu chia sẻ: " + ex.message)
                    } finally {
                        consentWaitService.remove(requestId)
                    }
                } else {
                    // Không có txnId trả về là case bypass
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(res["data"]))
                }
            } else {
                // Lỗi khi gọi apigw
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, res["errorMessage"].toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/face_matching")
    ResponseEntity<BaseResponse> face_matching(@RequestBody Map body) {
        try {
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")
            headers.put("api_key", key)

            String url = host + "/api/vneid/faceMatching"
            String resp = httpManaged.post([url: url], headers, objectMapper.writeValueAsString(body))
            def res = objectMapper.readValue(resp, Map.class)
            if (res["errorCode"].toString() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(res["data"], null, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, res["errorMessage"].toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/consent")
    ResponseEntity<BaseResponse> consent(@RequestBody Map body) {
        try {
            // callback từ apigw gửi về thông tin xác nhận KH
            consentWaitService.complete(body.requestId.toString(), body)
            return ResponseEntity.ok().body(BaseResponseBuilder.of("ok"))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

}
