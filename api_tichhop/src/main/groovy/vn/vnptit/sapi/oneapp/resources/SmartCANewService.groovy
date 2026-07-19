package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import groovy.json.JsonSlurper
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.util.LoggingInOut

@RestController
@RequestMapping("smartca")
class SmartCANewService {

    @Value("\${spring.httpservices.smartca_new.url_token}")
    String url_token

    @Value("\${spring.httpservices.smartca_new.url}")
    String url

    @Value("\${spring.httpservices.smartca_new.client_id}")
    String client_id

    @Value("\${spring.httpservices.smartca_new.client_secret}")
    String client_secret

    @Autowired
    HttpManaged httpManaged

    String post(url, Map<String, String> param) {
        def ls = param.collect { key, value -> key + "=" + value }.join("&")

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/x-www-form-urlencoded");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, ls);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response resp = client.newCall(request).execute();
        return resp.body().string()
    }

    String getToken() {
        def body = [:]
        body["grant_type"] = "client_credentials"
        body["client_id"] = client_id
        body["client_secret"] = client_secret
        String resp = post(url_token, body)
        def ret = new JsonSlurper().parseText(resp)
        return ret["access_token"]
    }

    @PostMapping("/sendMailOB")
    @LoggingInOut
    CommonResponse sendMailOB(@RequestBody Map<String, String> input) {
        try {
            String accessToken = getToken()
            if (accessToken == null || accessToken == "") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Không lấy được token", data: null)
            }

            Map<String, String> headers = [:]
            headers.put("Authorization", "Bearer " + accessToken)
            headers.put("Content-Type", "application/json; charset=UTF-8")

            httpManaged.post(["url": url + "bot/send_mail_ob"], headers, new Gson().toJson(input))
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Thành công", data: null)
        } catch (Exception e) {
            return new CommonResponse(errorCode: 1, faultCode: "", faultString: e.message, data: null)
        }
    }

}
