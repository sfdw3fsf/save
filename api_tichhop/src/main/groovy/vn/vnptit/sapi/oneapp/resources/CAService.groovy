package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import oracle.ucp.proxy.annotation.Post
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.util.ResourceUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.ca.GuiLaiSMS
import vn.vnptit.sapi.oneapp.models.ca.GuiYeuCau
import vn.vnptit.sapi.oneapp.models.ca.LayThongTinCAHienHuu
import vn.vnptit.sapi.oneapp.models.edu.CheckMang
import vn.vnptit.sapi.oneapp.models.edu.SllHocSinh
import vn.vnptit.sapi.oneapp.util.GenericApi
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse

import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.security.KeyStore
import java.security.Security
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.text.SimpleDateFormat

@RestController
@RequestMapping("ca")
class CAService {
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

    @Value("\${spring.httpservices.ca.url}")
    String url

    @Value("\${spring.httpservices.ca.ssl_password}")
    String ssl_password

    @Value("\${spring.httpservices.ca.token}")
    String token

    @Value("\${spring.httpservices.ca.url_gw}")
    String url_gw

    @Value("\${spring.httpservices.ca.token_gw}")
    String token_gw

    @Value("\${spring.httpservices.ca.appsecret_gw}")
    String appsecret_gw

    @PostMapping(["/TaoYeuCau", "/GuiYeuCau"])
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
   // @FakeSuccessCommonResponse
    CommonResponse TaoYeuCau(@Valid @RequestBody Map<String, Object> bodyRef) {
        try {
            def session = genericApi.getSessionData(request)
            String matinh = session[GenericApi.MA_TINH]
            def body = [:]
            bodyRef.each {
                body[it.key.toString().toLowerCase()] = it.value
            }
            body.ma_tinh = matinh
            byte[] decoded = body.dulieu.toString().decodeBase64()
            body.id = 0
            body.dulieu = new String(decoded)
            body.ngay_cn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date())
            def res = postgw("api/CA/taoyeucau", body)
            def ret = new JsonSlurper().parseText(res)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

//    @PostMapping("/GuiYeuCau")
//    @Consumes([MediaType.APPLICATION_JSON])
//    @Produces([MediaType.APPLICATION_JSON])
//    CommonResponse GuiYeuCau(@Valid @RequestBody GuiYeuCau body) {
//        try {
//            def res = get(searchParams)
//            if (res != null && res != ""){
//                def ret = new JsonSlurper().parseText(res)
//                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret)
//            }
//            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: body)
//        } catch (Exception e) {
//            return CommonResponse.resolveException(e, loggingManaged)
//        }
//    }

    @PostMapping(["/GuiLaiSMS/{username}", "/GuiLaiEmail/{username}"])
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse GuiLaiSMS(@PathVariable("username") String username) {
        try {
            def res = getgw("api/CA/guilaiemail/$username")
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/LayThongTinCAHienHuu")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse LayThongTinCAHienHuu(@Valid @RequestBody LayThongTinCAHienHuu body) {
        try { 
            def res = postgw("http://10.70.52.244:6061/api/CA/LayThongTinCAHienHuu", body)
            def ret = new JsonSlurper().parseText(res)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Test1")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse Test1(@Valid @RequestBody LayThongTinCAHienHuu body) {
        return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
    }

    def extractProperties(obj) {
        obj.getClass()
                .declaredFields
                .findAll { !it.synthetic }
                .collectEntries { field ->
                    [field.name, obj."$field.name"]
                }
    }

    def getgw(String path) {
        Map<String, String> headers = [:]
        String url = url_gw + path
        headers.put("Authorization", token_gw)
        headers.put("Appsecret", appsecret_gw)
        headers.put("Content-Type", "application/json")
        headers.put("Content-Encoding", "UTF-8")
        String resp = httpManaged.get(url)
        return resp
    }

    def postgw(String path, Object data) {
        Map<String, String> headers = [:]
        String url = path
        if (!url.contains("http")) url = url_gw + url
        headers.put("Authorization", token_gw)
        headers.put("Appsecret", appsecret_gw)
        headers.put("Content-Type", "application/json")
        headers.put("Content-Encoding", "UTF-8")
        String resp = httpManaged.post(["url": url], headers, JsonOutput.toJson(data))
        return resp
    }

    String get(String path, Map<Object, Object> param) {
        def paramMap = param.collectEntries { key, value -> [key.toString(), value == null ? "" : value.toString()] }
        def ls = paramMap.collect { key, value -> key.toString() + "=" + value }.join("&")
        String url = url + path + "?" + ls
        Security.addProvider(new BouncyCastleProvider());


        KeyManagerFactory keyManagerFactory;
        final char[] PASSWORD = "1".toCharArray();
        TrustManagerFactory trustManagerFactory;
        SSLSocketFactory sslSocketFactory;
        X509TrustManager trustManager;

        KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
        keyStore.load(ResourceUtils.getFile("classpath:cert.p12").newInputStream(), PASSWORD);
        trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

        trustManager = (X509TrustManager) trustManagers[0];
        keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, PASSWORD);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);
        sslSocketFactory = sslContext.getSocketFactory();

        final OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .build();


        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Host", "test-cms.vnpt-ca.vn")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();

        return response.body().string()
    }

}
