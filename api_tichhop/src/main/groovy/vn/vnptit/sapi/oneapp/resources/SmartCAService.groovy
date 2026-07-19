package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
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
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.properties.DataSourceProperties
import vn.vnptit.sapi.oneapp.util.BSSCrypto
import vn.vnptit.sapi.oneapp.util.DUtils
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse
import groovy.json.JsonSlurper
import org.apache.commons.text.StringEscapeUtils;

import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("smartca")
class SmartCAService {
    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    HttpManaged httpManaged

    @Autowired
    DataSourceProperties dataSourceProperties

    @Value("\${spring.httpservices.smart_ca.service_url}")
    String service_url

    //@Value("\${spring.httpservices.smart_ca.host_smartca}")
    String host_smartca = "https://rmgateway.vnptit.vn/"

    //@Value("\${spring.httpservices.smart_ca.client_id_smartca}")
    String client_id_smartca = "4ea7-638932698889944613.apps.smartcaapi.com"

    //@Value("\${spring.httpservices.smart_ca.client_secret_smartca}")
    String client_secret_smartca = "NjEzNTU1NDE-NWQxNy00ZWE3"

    @Value("\${spring.httpservices.smart_ca.secret_key}")
    String secret_key

    private String baseUrl
    private String clientId
    private String clientSecret

    String post(String path, Map<String, String> param) {
        String url = host_smartca + path
        def ls = param.collect {key, value -> key + "=" + value}.join("&")

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

    @PostMapping("/GetAccessToken")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    //@FakeSuccessCommonResponse
    CommonResponse GetAccessToken(@RequestBody Map<String, String> input) {
        Map<String, String> result = null
        try {
            baseUrl = host_smartca
            clientId = client_id_smartca
            clientSecret = client_secret_smartca

            Map<String, String> body = [:]
            body.grant_type = "password"
            body.username = input.email
            body.password = input.pass.replaceAll("&", "%26")
            body.client_id = clientId
            body.client_secret = clientSecret
            
            String resp = post("auth/token", body)
            def ret = new JsonSlurper().parseText(resp)
            result = [:]
            result.refresh_token = ret.refresh_token
            result.access_token = ret.access_token
            result.error_description = ret.error_description ? ret.error_description : ""
        } catch(Exception e) {

        }

        return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result)
    }

    @PostMapping("/GetCredentialSmartCA")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    //@FakeSuccessCommonResponse
    CommonResponse GetCredentialSmartCA(@RequestBody Map<String, String> input) {
        try {
            String url = "csc/credentials/list"
            Map<String, Object> req = [:]
            def resp = Query(req, url, input.accessToken)
            def credentials = new JsonSlurper().parseText(resp)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: credentials.content[0])
        } catch(Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/GetListCredentialSmartCA")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    //@FakeSuccessCommonResponse
    CommonResponse GetListCredentialSmartCA(@RequestBody Map<String, String> input) {
        try {
            String url = "csc/credentials/list"
            Map<String, Object> req = [:]
            def resp = Query(req, url, input.accessToken)
            def credentials = new JsonSlurper().parseText(resp)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: credentials.content)
        } catch(Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/GetAccoutSmartCACert")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    //@FakeSuccessCommonResponse
    CommonResponse GetAccoutSmartCACert(@RequestBody Map<String, String> input) {
        try {
            String url = "csc/credentials/info"
            Map<String, Object> req = [:]
            req.credentialId = input.credentialId
            req.certificates = "chain"
            req.certInfo = true
            req.authInfo = true
            def resp = Query(req, url, input.accessToken)
            def resp2 = new JsonSlurper().parseText(resp)
            String certBase64 = resp2.cert.certificates[0]
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: certBase64.replace("\r\n", ""))
        } catch(Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/GetInfoAccoutSmartCACert")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    //@FakeSuccessCommonResponse
    CommonResponse GetInfoAccoutSmartCACert(@RequestBody Map<String, String> input) {
        try {
            String url = "csc/credentials/info"
            Map<String, Object> req = [:]
            req.credentialId = input.credentialId
            req.certificates = "chain"
            req.certInfo = true
            req.authInfo = true
            def resp = Query(req, url, input.accessToken)
            def resp2 = new JsonSlurper().parseText(resp)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: resp2)
        } catch(Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/GetTranInfo")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    //@FakeSuccessCommonResponse
    CommonResponse GetTranInfo(@RequestBody Map<String, String> input) {
        def result = ""
        try {
            String url = "csc/credentials/gettraninfo"
            Map<String, Object> req = [:]
            req.tranId = input.tranId
            def resp = Query(req, url, input.accessToken)
            def resp2 = new JsonSlurper().parseText(resp)
            if (resp2.code == 0) {
                result = resp2.content            
            }
            
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result)
        } catch(Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/signhash")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    //@FakeSuccessCommonResponse
    CommonResponse signhash(@RequestBody Map<String, Object> input) {
        String requestId = UUID.randomUUID().toString()
        try {
            loggingManaged.info(["request_id": requestId, "action": "signhash", "request": new ObjectMapper().writeValueAsString(input)])
            String url = "csc/signature/signhash"
            def response = Query(input.req, url, input.accessToken)
            loggingManaged.info(["request_id": requestId, "response": response])
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: response)
        } catch(Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    String Query(Map<String, Object> req, String serviceUri, String accessToken) {
        try{
            String bToken = "Bearer " + accessToken
            Map<String, String> headers = [:]
            headers.put("Authorization", bToken)
            headers.put("Content-Type", "application/json; charset=UTF-8")

            String url = host_smartca + serviceUri
            String response = httpManaged.post(["url": url], headers, StringEscapeUtils.unescapeJava(new Gson().toJson(req)))
            return response
        } catch (Exception ex) {
            return ex.message
        }
    }
    
}
