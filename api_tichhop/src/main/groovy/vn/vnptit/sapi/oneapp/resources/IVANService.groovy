package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.ivan.*
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse
import vn.vnptit.sapi.oneapp.util.GenericApi
import ws.client.ivan.BasicHttpBinding_IServicesStub
import ws.client.ivan.ServicesLocator

import javax.net.ssl.KeyManager
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate

@RestController
@RequestMapping("ivan")
class IVANService {
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

    @Value("\${spring.httpservices.ivan.url}")
    String url

    @Value("\${spring.httpservices.ivan.client_id}")
    String client_id

    @Value("\${spring.httpservices.ivan.key}")
    String key

    ThreadLocal<BasicHttpBinding_IServicesStub> ivanBindingStub = ThreadLocal.withInitial {
        SSLContext ctx = SSLContext.getInstance("TLS")
        ctx.init(new KeyManager[0], [new DefaultTrustManager()] as TrustManager[], new SecureRandom())
        SSLContext.setDefault(ctx)
        ServicesLocator locator = new ServicesLocator()
        locator.setBasicHttpBinding_IServicesEndpointAddress(url)

        BasicHttpBinding_IServicesStub service = (BasicHttpBinding_IServicesStub) locator.getBasicHttpBinding_IServices()
        return service
    }

    private static class DefaultTrustManager implements X509TrustManager {
        @Override
        void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        X509Certificate[] getAcceptedIssuers() {
            return null
        }
    }

    String createSecureCode(List<String> arr) {
        arr.join(";").sha256()
    }

    CommonResponse excuteRequest(String function_code, Object input) {
        try {
            String request_id = UUID.randomUUID().toString()
            def service = ivanBindingStub.get()
            String json = JsonOutput.toJson(input)
            String parameters = json.getBytes().encodeBase64()
            String secure_code = createSecureCode([request_id, client_id, function_code, parameters, key])
            def data = service.excuteRequest(request_id, client_id, function_code, parameters, secure_code)
            return new CommonResponse(errorCode: data.status == 200 ? 0 : data.status, faultCode: data.requestId, faultString: data.message, data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/a101")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse a101(@Valid @RequestBody A101 body) {
        excuteRequest("A101", body)
    }

    @PostMapping("/a102")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse a102(@Valid @RequestBody A102 body) {
        excuteRequest("A102", body)
    }

    @PostMapping("/a103")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse a103(@Valid @RequestBody A103 body) {
        excuteRequest("A103", body)
    }

    @PostMapping("/a105")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse a105(@Valid @RequestBody A105 body) {
        excuteRequest("A105", body)
    }

    @PostMapping("/a108")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse a108(@Valid @RequestBody A108 body) {
        excuteRequest("A108", body)
    }

    @PostMapping("/a109")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse a109(@Valid @RequestBody A109 body) {
       excuteRequest("A109", body)
    }

    @PostMapping("/a113")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse a113(@Valid @RequestBody A113 body) {
        excuteRequest("A113", body)
    }
}
