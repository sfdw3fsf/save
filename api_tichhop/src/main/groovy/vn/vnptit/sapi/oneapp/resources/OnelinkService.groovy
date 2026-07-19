package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.json.JsonSlurper
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.SecureRandom
import java.security.cert.X509Certificate

@RestController
@RequestMapping("onelink")
class OnelinkService {
    @Autowired
    LoggingManaged loggingManaged

    @Value("\${spring.httpservices.onelink.url}")
    String host

    @Value("\${spring.httpservices.onelink.key}")
    String key

    String accessToken = ""
    String refreshToken = ""
    Long expiredTime = 0L

    static Map<String, String> mErrors = ["400": "App ID không tồn tại",
                                          "401": "Version không được hỗ trợ",
                                          "402": "Device ID không tồn tại",
                                          "403": "Access Key bị hết hạn",
                                          "404": "Token bị hết hạn",
                                          "405": "Giá trị không hợp lệ",
                                          "406": "Cấu trúc không hợp lệ",
                                          "407": "Không tìm thấy thiết bị",
                                          "408": "Yêu cầu quá thời hạn xử lý",
                                          "409": "Device mất kết nối",
                                          "410": "Token không hợp lệ",
                                          "411": "Model Name không được hỗ trợ",
                                          "412": "Sai định dạng địa chỉ IP",
                                          "413": "Thiết bị đang bận",
                                          "414": "User không tồn tại",
                                          "415": "Nhập sai mật khẩu",
                                          "416": "Phiên đăng nhập bị hết hạn, login lại phiên mới",
                                          "417": "Người dùng không được tác động do phân quyền area",
                                          "418": "Người dùng không được tác động do phân quyền theo loại thiết bị",
                                          "419": "App không có quyền sử dụng command",
                                          "420": "Sai email khi quên mật khẩu",
                                          "421": "Thiết bị không hỗ trợ online token",
                                          "422": "Thiết bị chặn login do đăng nhập sai nhiều lần",
                                          "423": "Email quên mật khẩu đã được gửi",
                                          "500": "Lỗi hệ thống"]

    CommonResponse execute(String url, String method, def body) {
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
            sslContext.init(null, trustAllCerts, new SecureRandom())

            def allTrustingClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), trustAllCerts[0] as X509TrustManager)
                    .hostnameVerifier({ hostname, session -> true } as HostnameVerifier)
                    .build()

            Gson gson = new GsonBuilder().disableHtmlEscaping().create()
            Response response = null
            if (method == "GET") {
                def request = new Request.Builder().url(url).header("Authorization", "Bearer " + accessToken).get().build()
                response = allTrustingClient.newCall(request).execute()
            } else {
                def JSON = MediaType.get("application/json; charset=utf-8")
                def requestBody = okhttp3.RequestBody.create(JSON, gson.toJson(body))
                def request = new Request.Builder().url(url).post(requestBody).build()
                if (!url.contains("register") && !url.contains("refresh")) {
                    request = new Request.Builder().url(url).header("Authorization", "Bearer " + accessToken).post(requestBody).build()
                }
                response = allTrustingClient.newCall(request).execute()
            }

            if (response.code() == 200) {
                String resp = response.body().string()
                def res = new JsonSlurper().parseText(resp)
                if (res["errorCode"].toString() == "200") {
                    if (url.contains("register") || url.contains("refresh")) {
                        accessToken = res["body"]["accessToken"]
                        refreshToken = res["body"]["refreshToken"]
                        expiredTime = Long.parseLong(res["body"]["expiredTime"].toString())
                    }

                    return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
                } else {
                    String error = url.contains("deviceConfig") ? mErrors[res["errorCode"].toString()] : res["errorMessage"]
                    return new CommonResponse(errorCode: 1, faultCode: res["errorCode"], faultString: error, data: res)
                }
            } else if (response.code() == 401) {
                return new CommonResponse(errorCode: 1, faultCode: response.code(), faultString: "Bạn không có quyền thực hiện chức năng này", data: null)
            } else if (response.code() == 404) {
                return new CommonResponse(errorCode: 1, faultCode: response.code(), faultString: "Not found", data: null)
            } else {
                return new CommonResponse(errorCode: 1, faultCode: response.code(), faultString: "Lỗi không xác định", data: null)
            }
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    void register() {
        try {
            String url = host + "register"

            def body = [:]
            body["appId"] = "one_bss"
            body["version"] = "1.0"
            body["appToken"] = key

            execute(url, "POST", body)
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    void refresh(@RequestParam String deviceId) {
        try {
            String url = host + "refresh"

            def body = [:]
            body["appId"] = "one_bss"
            body["version"] = "1.0"
            body["refreshToken"] = refreshToken
            body["deviceId"] = deviceId

            execute(url, "POST", body)
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    @GetMapping("/getDeviceLockInfo")
    CommonResponse getDeviceLockInfo(@RequestParam String serialNumber, @RequestParam(required = false) String modelName, @RequestParam(required = false) String username) {
        if (accessToken == null || accessToken == "" ||
                refreshToken == null || refreshToken == "" || expiredTime < System.currentTimeMillis()) {
            register()
        }

        try {
            String url = host + "getDeviceLockInfo?serialNumber=" + serialNumber
            if (modelName) {
                url += "&modelName=" + modelName
            }
            if (username) {
                url += "&username=" + username
            }

            return execute(url, "GET", "")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/lockDevice")
    CommonResponse lockDevice(@RequestBody Map input) {
        if (accessToken == null || accessToken == "" ||
                refreshToken == null || refreshToken == "" || expiredTime < System.currentTimeMillis()) {
            register()
        }

        try {
            String url = host + "deviceConfig"

            def body = [:]
            body["serialNumber"] = input["serialNumber"]
            body["modelName"] = input["modelName"]
            body["Reason"] = input["Reason"]
            body["Actor"] = input["Actor"]
            body["command"] = "lockDevice"

            return execute(url, "POST", input)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/unlockDevice")
    CommonResponse unlockDevice(@RequestBody Map input) {
        if (accessToken == null || accessToken == "" ||
                refreshToken == null || refreshToken == "" || expiredTime < System.currentTimeMillis()) {
            register()
        }

        try {
            String url = host + "deviceConfig"

            def body = [:]
            body["serialNumber"] = input["serialNumber"]
            body["modelName"] = input["modelName"]
            body["Reason"] = input["Reason"]
            body["Actor"] = input["Actor"]
            body["command"] = "unlockDevice"

            return execute(url, "POST", input)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/zeroTouch")
    CommonResponse zeroTouch(@RequestBody Map input) {
        if (accessToken == null || accessToken == "" ||
                refreshToken == null || refreshToken == "" || expiredTime < System.currentTimeMillis()) {
            register()
        }

        try {
            String url = host + "zeroTouch"
            return execute(url, "POST", input)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

}