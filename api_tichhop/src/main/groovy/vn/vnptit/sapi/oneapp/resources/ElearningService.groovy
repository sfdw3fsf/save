package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.properties.DataSourceProperties
import vn.vnptit.sapi.oneapp.properties.HttpServiceConfig
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse

import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

@RestController
@RequestMapping("elearning")
class ElearningService {
    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    HttpManaged httpManaged

    @Autowired
    DataSourceProperties dataSourceProperties

    @PostMapping("/khoiTaoElearning")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse khoiTaoElearning(@RequestBody Map<String, String> input) {
        try {
            HttpServiceConfig elearningConfig = dataSourceProperties.httpservices.get('elearning')
        
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", elearningConfig.headers.get('Content-Type'))
            headers.put("Authorization", elearningConfig.headers.get('Authorization'))

            String secret_key = elearningConfig.secretKey
            String abc = input.vnedu_site_id.toString()+'|'+input.info.toString()+'|'+input.order.toString()+'|'+input.attribute.toString()+'|'+secret_key
            String secure_code = toHexString(getSHA(abc))
            input.secure_code = secure_code.toLowerCase()

            String url = elearningConfig.url + "ActiveProduct"
            String resp = httpManaged.post(["url": url], headers, new Gson().toJson(input))

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: resp)
        } catch(Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256")
        return md.digest(input.getBytes(StandardCharsets.UTF_8))
    }

    String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash)
        StringBuilder hexString = new StringBuilder(number.toString(16))

        while (hexString.length() < 64) {
            hexString.insert(0, '0')
        }

        return hexString.toString()
    }

}
