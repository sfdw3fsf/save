package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import groovy.json.JsonSlurper
import io.swagger.model.BaseResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat

@RestController
@RequestMapping("rims")
class RIMService {

    @Autowired
    HttpManaged httpManaged

    @Value("\${spring.httpservices.rims.url}")
    String url

    @Value("\${spring.httpservices.rims.token}")
    String token

    @Value("\${spring.httpservices.rims.new_url}")
    String urlNew

    @Value("\${spring.httpservices.ctsnewport.cpCode}")
    String cpCode

    @Value("\${spring.httpservices.ctsnewport.secureKey}")
    String secureKey

    @PostMapping("/**")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> executorPost(HttpServletRequest request, @RequestParam Map<String, String> requestParams, @RequestBody(required = false) Object requestBody) {
        try {
            String[] arrServletPath = request.getServletPath().split("rims/", 2)
            def res = post(arrServletPath[1], requestParams, requestBody)
            def ret = new JsonSlurper().parseText(res)
            if (ret["success"].toString() == "true") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret, null, ret["errorMsg"], ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    String post(String path, Map<String, String> param, Object body) {
        def ls = param.collect { key, value -> key + "=" + value }.join("&")
        Map<String, String> headers = [:]
        String url = url + path + "?" + ls
        headers.put("Authorization", "Bearer " + token)
        String resp = httpManaged.post([url: url], headers, new Gson().toJson(body))
        return resp
    }

    @PostMapping("/checkExists")
    ResponseEntity<BaseResponse<?>> checkExists(@RequestBody Map requestBody) {
        try {
            String url = urlNew + "/common-api/device/IsExisted"
            def allTrustingClient = new OkHttpClient.Builder().build()
            def JSON = okhttp3.MediaType.get("application/json; charset=utf-8")
            def reqBody = okhttp3.RequestBody.create(JSON, new Gson().toJson(requestBody))
            String requestId = UUID.randomUUID().toString()
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss")
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"))
            String requestDate = sdf.format(new Date())
            String combinedStr = requestId + "|" + requestDate + "|" + cpCode + "|" + secureKey
            String secure = getSHA512Hash(combinedStr)
            def request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("X-Secure-Code", secure)
                    .addHeader("X-Request-Id", requestId)
                    .addHeader("X-Request-Date", requestDate)
                    .addHeader("X-Cp-Code", cpCode)
                    .post(reqBody)
                    .build()
            def response = allTrustingClient.newCall(request).execute()
            def res = response.body().string()
            def ret = new JsonSlurper().parseText(res)
            if (ret["code"].toString() == "0" && ret["isError"].toString() == "false") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret["data"]))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret, null, ret["message"], ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    String getSHA512Hash(String input) {
        try {
            // Tạo instance của MessageDigest với thuật toán SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // Chuyển chuỗi đầu vào thành mảng byte
            byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);

            // Tính toán băm và lưu kết quả trong mảng byte
            byte[] hashBytes = md.digest(inputBytes);

            // Chuyển đổi mảng byte thành chuỗi dạng hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
