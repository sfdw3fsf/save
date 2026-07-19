package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import groovy.json.JsonSlurper
import org.apache.commons.codec.binary.Base64
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.validation.Valid
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.time.ZoneOffset

@RestController
@RequestMapping("ipcc")
class OBIPCCService {

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    HttpManaged httpManaged

    @Value("\${spring.httpservices.ob_ipcc.url}")
    String urlIPCC

    @Value("\${spring.httpservices.ob_ipcc.secret_key}")
    String secret_key

    @Value("\${spring.httpservices.ob_ipcc.signature}")
    String ob_signature

    String getBearerToken(def payload) {
        List<String> segments = new ArrayList<>()

        // Part 1
        segments.add("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")

        // Part 2
		payload["iat"] = Instant.now().atOffset(ZoneOffset.ofHours(7)).toInstant().plus(Duration.ofSeconds(-60)).getEpochSecond().toString()
		payload["exp"] = Instant.now().atOffset(ZoneOffset.ofHours(7)).toInstant().plus(Duration.ofSeconds(230)).getEpochSecond().toString()
        payload["parameters"]["secret_key"] = secret_key
        byte[] payloadBytes = new Gson().toJson(payload).getBytes(StandardCharsets.UTF_8)
        segments.add(Base64.encodeBase64URLSafeString(payloadBytes))
        String stringToSign = String.join(".", segments)
        byte[] bytesToSign = stringToSign.getBytes(StandardCharsets.UTF_8)

        // Part 3
        byte[] signature
        byte[] keyBytes = ob_signature.getBytes(StandardCharsets.UTF_8)
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256")
        sha256_HMAC.init(new SecretKeySpec(keyBytes, "HmacSHA256"))
        signature = sha256_HMAC.doFinal(bytesToSign)
        segments.add(Base64.encodeBase64URLSafeString(signature))
        String bearer = String.join(".", segments)

        return bearer
    }

    @PostMapping("downloadFileAutocall")
    CommonResponse downloadFileAutocall(@Valid @RequestBody Map input) {
        try {
            Map<String, String> headers = [:]

            def payload = [:]
            payload["parameters"] = [:]
            payload["parameters"]["province"] = input.province
            payload["parameters"]["date"] = input.date

            headers.put("Authorization", "Bearer " + getBearerToken(payload))
            String resp = httpManaged.get(urlIPCC + "outbound/download-file-autocall", headers)
            try {
                def ret = new JsonSlurper().parseText(resp.substring(1))
                if (ret["success"].toString() == "false") {
                    return new CommonResponse(errorCode: 1, faultCode: ret["error_code"], faultString: ret["message"], data: resp)
                }
            } catch (Exception e2) {
                // success
            }

            String[] lst = resp.substring(1).split("\n")
            def result = []
            for (String item : lst) {
                String[] params = item.split(",")
                if (params && params[0] != "" && params.size() == 16) {
                    def row = [:]
                    row["id"] = getValue(params[0])
                    row["ttkd"] = getValue(params[1])
                    row["donvi"] = getValue(params[2])
                    row["username"] = getValue(params[3])
                    row["ktv"] = getValue(params[4])
                    row["chuongtrinh_ob"] = getValue(params[5])
                    row["thuebao"] = getValue(params[6])
                    row["trangthai"] = getValue(params[7])
                    row["thoidiem_thuc_hien"] = getValue(params[8])
                    row["thoidiem_bat_dau"] = getValue(params[9])
                    row["thoidiem_traloi"] = getValue(params[10])
                    row["thoidiem_ketthuc"] = getValue(params[11])
                    row["thoigian_dochuong"] = getValue(params[12])
                    row["thoigian_hang_doi"] = getValue(params[13])
                    row["thoigian_giumay"] = getValue(params[14])
                    row["thoigian_dam_thoai"] = getValue(params[15])
                    result.add(row)
                }
            }
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result)
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

	String getValue(String value) {
		if (value.startsWith("\"")) value = value.substring(1)
		if (value.endsWith("\"")) value = value.substring(0, value.length() - 1)
		return value
	}

    @PostMapping("updateUserState")
    CommonResponse updateUserState(@Valid @RequestBody Map input) {
        try {
            Map<String, String> headers = [:]

            def payload = [:]
            payload["parameters"] = [:]
            payload["parameters"]["userAuto"] = input.userAuto
            // payload["parameters"]["config_active_time"] = input.config_active_time ? input.config_active_time : 10

            headers.put("Authorization", "Bearer " + getBearerToken(payload))
            String resp = httpManaged.post(["url": urlIPCC + "extension/update-user-state"], headers, "")
            def ret
            try {
                ret = new JsonSlurper().parseText(resp)
            } catch (Exception e) {
                ret = new JsonSlurper().parseText(resp.substring(1))
            }
            if (ret["success"].toString() == "true") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret["data"])
            } else {
                return new CommonResponse(errorCode: 1, faultCode: ret["error_code"], faultString: ret["message"], data: ret["data"])
            }
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

}
