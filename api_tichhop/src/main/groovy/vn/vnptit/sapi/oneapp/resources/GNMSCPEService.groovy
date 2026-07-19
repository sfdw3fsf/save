package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder

import javax.servlet.http.HttpServletRequest
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat

@RestController
@RequestMapping("gnms_cpe")
class GNMSCPEService {

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    HttpManaged httpManaged

    @Value("\${spring.httpservices.gnms_cpe.url}")
    String gnmsUrl

    @Value("\${spring.httpservices.gnms_cpe.timeout}")
    int timeout

    @Value("\${spring.httpservices.ctsnewport.cpCode}")
    String cpCode

    @Value("\${spring.httpservices.ctsnewport.secureKey}")
    String secureKey

    static Map<String, String> gnmsNewChange = ["optical"              : "serialNumber",
                                                //"getByOptical"         : "fetchOpticalInfo",
                                                "getUptime"            : "getByOptical",
                                                "getResultFactoryReset": "resetFactory",
                                                "getInfoDeviceConnect" : "fetchConnectedUserInfor",
                                                "onOffWifi"            : "updateWifi",
                                                "delAll"               : "deleteAll",
                                                "delxml"               : "delete",
                                                "addBridgeWan"         : "addWan",
                                                "configWebGuiPass"     : "configWebPassword",
                                                "reset"                : "resetFactory",
                                                "updateWifiType"       : "updateWifi",
                                                "configNewPassWord"    : "configWebPassword",
                                                "authMode"             : "authenMode"]

    @GetMapping("/**")
    ResponseEntity<?> executorGet(HttpServletRequest request, @RequestParam Map<String, String> requestParams) {
        try {
            String[] arrServletPath = request.getServletPath().split("gnms_cpe/", 2)
            return execute(arrServletPath[1], "GET", requestParams, null)
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/**")
    ResponseEntity<?> executorPost(HttpServletRequest request, @RequestParam Map<String, String> requestParams, @RequestBody(required = false) Object requestBody) {
        try {
            String[] arrServletPath = request.getServletPath().split("gnms_cpe/", 2)
            return execute(arrServletPath[1], "POST", requestParams, requestBody)
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    ResponseEntity<?> execute(String path, String method, Map<String, String> param, Object body) {
        String requestId = UUID.randomUUID().toString()
        Map<String, Object> inputLogData = new HashMap<>()
        inputLogData.put("class", "GNMSCPEService")
        long start = System.currentTimeMillis()
        try {
            Map<String, String> headers = getHeader()

            def ls = param.collect { key, value -> key + "=" + URLEncoder.encode(value, StandardCharsets.UTF_8.toString()) }.join("&")
            String url = gnmsUrl + path + "?" + ls

            gnmsNewChange.each { key, value ->
                url = url.replace(key, value)
            }

            inputLogData.put("requestId", requestId)
            inputLogData.put("requestDate", headers.get("X-Request-Date"))
            inputLogData.put("url", url)
            inputLogData.put("body", new Gson().toJson(body))

            String resp = ""
            if (method == "GET") {
                resp = httpManaged.getWithTimeout(url, headers, timeout)
            } else {
                resp = httpManaged.post(["url": url], headers, body != null ? new Gson().toJson(body) : "")
            }

            inputLogData.put("resp", resp)
            inputLogData.put("duration", System.currentTimeMillis() - start)
            loggingManaged.info(inputLogData)

            return xlResp(resp)
        } catch (Exception ex) {
            inputLogData.put("error", ex.message)
            inputLogData.put("duration", System.currentTimeMillis() - start)
            loggingManaged.info(inputLogData)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, ex.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    ResponseEntity<?> xlResp(String resp) {
        Map result = [:]
        try {
            def ret = new JsonSlurper().parseText(resp)
            result["error_code"] = ret["errorCode"]
            result["message"] = ret["errorMessage"]
            result.putAll(ret)
        } catch (Exception ex) {
            result["error_code"] = "-999"
            result["message"] = resp
        }
        return ResponseEntity.ok().body(result)
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

    Map<String, String> getHeader() {
        String requestId = UUID.randomUUID().toString()
        String requestDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
        String combinedStr = requestId + "|" + requestDate + "|" + cpCode + "|" + secureKey
        String secure = getSHA512Hash(combinedStr)
        return [
                "Content-Type"  : "application/json",
                "X-Secure-Code" : secure,
                "X-Request-Id"  : requestId,
                "X-Request-Date": requestDate,
                "X-Cp-Code"     : cpCode
        ]
    }

}