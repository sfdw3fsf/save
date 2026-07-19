package vn.vnptit.sapi.oneapp.resources


import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.InputStreamResource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.sla.SLAData
import vn.vnptit.sapi.oneapp.models.sla.SLATracuu
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse
import vn.vnptit.sapi.oneapp.util.GenericApi

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("econtract")
class EContractService {
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

    @Value("\${spring.httpservices.econtract.url}")
    String url

    @Value("\${spring.httpservices.econtract.username}")
    String username

    @Value("\${spring.httpservices.econtract.password}")
    String password

    @Value("\${spring.httpservices.econtract.domain}")
    String domain

    @Value("\${spring.httpservices.econtract.client_id}")
    String client_id

    @Value("\${spring.httpservices.econtract.client_secret}")
    String client_secret

    @Value("\${spring.httpservices.econtract.grant_type}")
    String grant_type

    class Token{
        String token
        Date expires

        boolean isExpired(){
            expires < new Date()
        }
    }

    static Token cachedToken = null

    Token renewToken() {
        String url = url + "/oauth/token"
        Map<String, String> headers = [:]
        headers.put("Content-Type", "application/json")
        headers.put("Content-Encoding", "UTF-8")
        def body = [
                client_id: client_id,
                client_secret: client_secret,
                domain: domain,
                grant_type: grant_type,
                username: username,
                password: password
        ]
        String resp = httpManaged.post(["url": url], headers, JsonOutput.toJson(body))
        def ret = new JsonSlurper().parseText(resp)
        Token t = new Token()
        t.token = ret.access_token
        Calendar date = Calendar.getInstance();
        long timeInSecs = date.getTimeInMillis();
        t.expires = new Date(timeInSecs + ((ret.expires_in - 30) * 1000));
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
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: t)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/download/{id_contract}")
    ResponseEntity<InputStreamResource> download(@PathVariable("id_contract") String id_contract) {
        try {
            String requestId = UUID.randomUUID().toString()
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url("$url/management-service/contracts/$id_contract/ccbs-download-documents?fileKey=$id_contract&documentType=DRAFT&view=attachment")
                    .method("GET", null)
                    .addHeader("Authorization", "Bearer ${getToken().token}")
                    .addHeader("Content-Disposition", "inline|attachment; filename=${id_contract}.pdf")
                    .build();
            Response response = client.newCall(request).execute();
            def rs = new InputStreamResource(response.body().byteStream());

            return ResponseEntity.ok()
            // Content-Disposition
                    .header("Content-Disposition", "attachment;filename=${id_contract}.pdf")
            // Content-Type
//                    .contentType(org.springframework.http.MediaType.parseMediaType("application/pdf"))
            // Contet-Length
//                    .contentLength(response.body().byteStream().bytes.length) //
                    .body(rs);
        } catch (Exception e) {
            return null
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
        String resp = httpManaged.post(["url": url], headers, JsonOutput.toJson(body))
        return new JsonSlurper().parseText(resp)
    }

}
