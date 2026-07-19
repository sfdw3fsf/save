package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.sla.SLAAccount
import vn.vnptit.sapi.oneapp.models.sla.SLAData
import vn.vnptit.sapi.oneapp.models.sla.SLATracuu
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse
import vn.vnptit.sapi.oneapp.util.GenericApi
import vn.vnptit.sapi.oneapp.util.DUtils
import vn.vnptit.sapi.oneapp.util.LoggingInOut

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("sla")
class SLAService {
    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    OracleManaged oracleManaged
    @Autowired
    HttpManaged httpManaged

    @Autowired
    GenericApi genericApi
    @Autowired
    NativeWebRequest request

    @Value("\${spring.httpservices.sla.url}")
    String url

    @Value("\${spring.httpservices.sla.username}")
    String username

    @Value("\${spring.httpservices.sla.password}")
    String password

    @Value("\${spring.httpservices.sla.secret_key}")
    String secret_key

    class Token{
        String token
        Date expires

        boolean isExpired(){
            expires < new Date()
        }
    }

    static Token cachedToken = null

    Token renewToken() {
        String action = "token"
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/x-www-form-urlencoded");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "grant_type=password&username=$username&password=$password");
        Request request = new Request.Builder()
                .url(url + action)
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response resp = client.newCall(request).execute();
        def ret = new JsonSlurper().parseText(resp.body().string())
        Token t = new Token()
        t.token = ret.access_token
        t.expires = new Date(Date.parse(ret[".expires"].toString()) - 100000)
        return t
    }

    Token getToken() {
        if (cachedToken == null || cachedToken.isExpired()) {
            cachedToken = renewToken()
        }
        cachedToken
    }

    @GetMapping("/token")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse token() {
        try {
            String requestId = UUID.randomUUID().toString()
            Token t = getToken()
            return new CommonResponse(errorCode: resp.code == 200 ? 0 : 1, faultCode: "", faultString: "", data: t)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/CreateAccountSLA")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse CreateAccountSLA(@Valid @RequestBody SLAData data) {
        try {
            String requestId = UUID.randomUUID().toString()
//            String pwd = data.PASSWORD
//            data.PASSWORD = DUtils.decrypt(data.PASSWORD, secret_key)
//            if (data.PASSWORD == null || data.PASSWORD == "") {
//                data.PASSWORD = pwd
//            }
            def resp = post("CreateAccountSLA", data)
            if (resp["message"].toString().contains("đã tồn tại")) {
                resp["code"] = 200
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: resp.message, data: resp)
            }
            return new CommonResponse(errorCode: resp.code == 200 ? 0 : 1, faultCode: "", faultString: resp.message, data: resp)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/UpdateAccountInfo")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse UpdateAccountInfo(@Valid @RequestBody SLAData data) {
        try {
            String requestId = UUID.randomUUID().toString()
//            String pwd = data.PASSWORD
//            data.PASSWORD = DUtils.decrypt(data.PASSWORD, secret_key)
//            if (data.PASSWORD == null || data.PASSWORD == "") {
//                data.PASSWORD = pwd
//            }
            def resp = post("UpdateAccountInfo", data)
            return new CommonResponse(errorCode: resp.code == 200 ? 0 : 1, faultCode: "", faultString: resp.message, data: resp)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/AddThueBao")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse AddThueBao(@Valid @RequestBody SLAData data) {
        try {
            String requestId = UUID.randomUUID().toString()
//            data.PASSWORD = DUtils.decrypt(data.PASSWORD, secret_key)
            def resp = post("AddThueBao", data)
            if (resp["message"].toString().contains("Mã kênh (Account) đã tồn tại")) {
                resp["code"] = 200
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: resp.message, data: resp)
            }
            return new CommonResponse(errorCode: resp.code == 200 ? 0 : 1, faultCode: "", faultString: resp.message, data: resp)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/AddOrUpdateThueBao")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse AddOrUpdateThueBao(@Valid @RequestBody SLAData data) {
        try {
            String requestId = UUID.randomUUID().toString()
//            data.PASSWORD = DUtils.decrypt(data.PASSWORD, secret_key)
            def resp = post("AddOrUpdateThueBao", data)
            return new CommonResponse(errorCode: resp.code == 200 ? 0 : 1, faultCode: "", faultString: resp.message, data: resp)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/UpdateThueBao")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse UpdateThueBao(@Valid @RequestBody SLAData data) {
        try {
            String requestId = UUID.randomUUID().toString()
//            data.PASSWORD = DUtils.decrypt(data.PASSWORD, secret_key)
            def resp = post("UpdateThueBao", data)
            return new CommonResponse(errorCode: resp.code == 200 ? 0 : 1, faultCode: "", faultString: resp.message, data: resp)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/RemoveThueBao")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse RemoveThueBao(@Valid @RequestBody SLAData data) {
        try {
            String requestId = UUID.randomUUID().toString()
//            data.PASSWORD = DUtils.decrypt(data.PASSWORD, secret_key)
            def resp = post("RemoveThueBao", data)
            return new CommonResponse(errorCode: resp.code == 200 ? 0 : 1, faultCode: "", faultString: resp.message, data: resp)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/TraCuu")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse TraCuu(@Valid @RequestBody SLATracuu data) {
        try {
            String requestId = UUID.randomUUID().toString()
            def resp = post("TraCuu", data)
            return new CommonResponse(errorCode: resp.code == 200 ? 0 : 1, faultCode: "", faultString: "", data: resp)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/ChangeStatusAccount")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse ChangeStatusAccount(@Valid @RequestBody SLAData data) {
        try {
            def resp = post("ChangeStatusAccount", data)
            return new CommonResponse(errorCode: resp.code == 200 ? 0 : 1, faultCode: "", faultString: resp.message, data: resp)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    def post(String path, Object data) {
        Map<String, String> headers = [:]
        String url = url + "api/dhsxkd/post"
        headers.put("Authorization", "Bearer " + getToken().token)
        headers.put("Content-Type", "application/json")
        headers.put("Content-Encoding", "UTF-8")
        def body = [
                Command: path,
                Data: data
        ]
        String resp = httpManaged.post(["url": url], headers, new Gson().toJson(body))
        return new JsonSlurper().parseText(resp)
    }

    // Bo sung cho loai hinh IP Transit
    // https://cntt.vnpt.vn/browse/BSS-110044
    def postIT(String path, Object data) {
        Map<String, String> headers = [:]
        String url = url + "api/onebss/iptransit/post"
        headers.put("Authorization", "Bearer " + getToken().token)
        headers.put("Content-Type", "application/json")
        headers.put("Content-Encoding", "UTF-8")
        def body = [
                Command: path,
                Data: data
        ]
        String resp = httpManaged.post(["url": url], headers, new Gson().toJson(body))
        return new JsonSlurper().parseText(resp)
    }

    @LoggingInOut
    @PostMapping("/IPTransit/AddThueBao")
    CommonResponse AddThueBaoIT(@Valid @RequestBody SLAAccount data) {
        try {
            def resp = postIT("AddThueBao", data)
            return new CommonResponse(errorCode: resp.code == 200 ? 0 : 1, faultCode: "", faultString: resp.message, data: resp)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/IPTransit/AddOrUpdateThueBao")
    CommonResponse AddOrUpdateThueBaoIT(@Valid @RequestBody SLAAccount data) {
        try {
            def resp = postIT("AddOrUpdateThueBao", data)
            return new CommonResponse(errorCode: resp.code == 200 ? 0 : 1, faultCode: "", faultString: resp.message, data: resp)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/IPTransit/UpdateThueBao")
    CommonResponse UpdateThueBaoIT(@Valid @RequestBody SLAAccount data) {
        try {
            def resp = postIT("UpdateThueBao", data)
            return new CommonResponse(errorCode: resp.code == 200 ? 0 : 1, faultCode: "", faultString: resp.message, data: resp)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/IPTransit/RemoveThueBao")
    CommonResponse RemoveThueBaoIT(@Valid @RequestBody SLAAccount data) {
        try {
            def resp = postIT("RemoveThueBao", data)
            return new CommonResponse(errorCode: resp.code == 200 ? 0 : 1, faultCode: "", faultString: resp.message, data: resp)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/IPTransit/TraCuu")
    CommonResponse TraCuuIT(@Valid @RequestBody SLATracuu data) {
        try {
            def resp = postIT("TraCuu", data)
            return new CommonResponse(errorCode: resp.code == 200 ? 0 : 1, faultCode: "", faultString: resp.message, data: resp)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

}
