package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.json.JsonSlurper
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.*
import vn.vnptit.sapi.oneapp.util.LoggingInOut

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.cert.X509Certificate
import java.text.SimpleDateFormat

@RestController
@RequestMapping("safemotor")
class SafeMotorService {

    @Autowired
    LoggingManaged loggingManaged

    @Value("\${spring.httpservices.safe_motor.url}")
    String host

    @Value("\${spring.httpservices.safe_motor.key}")
    String key

    Gson gson = new GsonBuilder().serializeNulls().create()

    CommonResponse execute(String url, String method, def body, Map<String, String> error) {
        try {
            def trustAllCerts = [new X509TrustManager() {
                @Override
                void checkClientTrusted(X509Certificate[] chain, String authType) {}

                @Override
                void checkServerTrusted(X509Certificate[] chain, String authType) {}

                @Override
                X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0] }
            }] as TrustManager[]

            def sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom())

            def allTrustingClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), trustAllCerts[0] as X509TrustManager)
                    .hostnameVerifier({ hostname, session -> true } as HostnameVerifier)
                    .build()

            String resp = ""
            if (method == "GET") {
                def request = new Request.Builder()
                        .url(url)
                        .get()
                        .build()
                def response = allTrustingClient.newCall(request).execute()
                resp = response.body().string()
            } else {
                def JSON = MediaType.get("application/json; charset=utf-8")
                body.partner_key = key
                body.transaction_code = new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date())
                def requestBody = okhttp3.RequestBody.create(JSON, new Gson().toJson(body))
                def request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build()
                def response = allTrustingClient.newCall(request).execute()
                resp = response.body().string()
            }

            def res = new JsonSlurper().parseText(resp)
            if (res["success"]) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res["data"])
            } else {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: res["message"] ? res["message"] : res["error"], data: res)
            }
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @PostMapping("/TaoMoi")
    @LoggingInOut
    CommonResponse TaoMoi(@RequestBody SafeMotor_TaoMoi_REQ body) {
        try {
            String url = host + "/order/new"
            return execute(url, "POST", body, null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/ThayDoiThietBi")
    @LoggingInOut
    CommonResponse ThayDoiThietBi(@RequestBody SafeMotor_ThayDoiThietBi_REQ body) {
        try {
            String url = host + "/order/exchange"
            return execute(url, "POST", body, null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/ThayDoiThongTinKhachHang")
    @LoggingInOut
    CommonResponse ThayDoiThongTinKhachHang(@RequestBody SafeMotor_ThayDoiThongTinKhachHang_REQ body) {
        try {
            String url = host + "/customer"
            return execute(url, "POST", body, null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/ThayDoiThongTinThueBao")
    @LoggingInOut
    CommonResponse ThayDoiThongTinThueBao(@RequestBody SafeMotor_ThayDoiThongTinThueBao_REQ body) {
        try {
            String url = host + "/order"
            return execute(url, "POST", body, null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/GiaHan")
    @LoggingInOut
    CommonResponse GiaHan(@RequestBody SafeMotor_GiaHan_REQ body) {
        try {
            String url = host + "/order/renewal"
            return execute(url, "POST", body, null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/TamNgung")
    @LoggingInOut
    CommonResponse TamNgung(@RequestBody SafeMotor_TamNgung_REQ body) {
        try {
            String url = host + "/order/suspend"
            return execute(url, "POST", body, null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/KhoiPhuc")
    @LoggingInOut
    CommonResponse KhoiPhuc(@RequestBody SafeMotor_KhoiPhuc_REQ body) {
        try {
            String url = host + "/order/restore"
            return execute(url, "POST", body, null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/HuyDichVu")
    @LoggingInOut
    CommonResponse HuyDichVu(@RequestBody SafeMotor_HuyDichVu_REQ body) {
        try {
            String url = host + "/order/cancel"
            return execute(url, "POST", body, null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

}