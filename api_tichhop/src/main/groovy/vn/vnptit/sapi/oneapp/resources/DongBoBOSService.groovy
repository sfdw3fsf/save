package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate;
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import java.time.OffsetDateTime

@RestController
@RequestMapping("/dongbo_bos")
class DongBoBOSService {

    static Gson gson = new GsonBuilder().serializeNulls().create()

    @Autowired
    HttpManaged httpManaged

    @Autowired
    LoggingManaged loggingManaged

    @Value("\${spring.httpservices.iot.api-url}")
    String apiUrl

    @Value("\${spring.httpservices.iot.oauth.client-id}")
    String clientId

    @Value("\${spring.httpservices.iot.oauth.client-secret}")
    String clientSecret

    @Value("\${spring.httpservices.iot.oauth.token-url}")
    String tokenUrl

    @Value("\${spring.httpservices.iot.oauth.grant_type}")
    String grantType

    String postToBos(String path, String jsonBody) {
        String token = getBosToken()
        Map<String, String> headers = [
                "Content-Type" : "application/json",
                "Accept"       : "application/json",
                "Authorization": "Bearer ${token}".toString()
        ]
        String fullUrl = apiUrl + path
        return httpManaged.post(
                ["url": fullUrl],
                headers,
                jsonBody
        )
    }

    String putToBos(String path, String jsonBody) {
        String token = getBosToken()
        Map<String, String> headers = [
                "Content-Type" : "application/json",
                "Accept"       : "application/json",
                "Authorization": "Bearer ${token}".toString()
        ]
        String fullUrl = apiUrl + path
        return httpManaged.put(
                ["url": fullUrl],
                headers,
                jsonBody
        )
    }

    String getBosToken() {
        try {
            String fullUrl = tokenUrl
            Map<String, String> headers = [
                    "Content-Type": "application/json",
                    "Accept"      : "application/json"
            ]
            String jsonBody = """
        {
            "grant_type": "${grantType}",
            "client_id": "${clientId}",
            "client_secret": "${clientSecret}"
        }
        """
            String response = httpManaged.post(["url": fullUrl], headers, jsonBody)
            def data = new JsonSlurper().parseText(response)
            return data.access_token
        } catch (Exception ex) {
            throw new RuntimeException("Lỗi khi lấy token Bos: " + ex.message, ex)
        }
    }


    @PostMapping("/taomoi_donhang_dongbo_bos")
    CommonResponse taoMoiDonHang(@RequestBody Map body) {
        try {
            String jsonBody = gson.toJson(body)
            String response = postToBos("/orders", jsonBody)
            return new CommonResponse(
                    errorCode: 200,
                    faultCode: "",
                    faultString: "Thành công",
                    data: response
            )
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/capnhat_donhang_dongbo_bos")
    CommonResponse capNhatDonHang(@RequestBody Map body) {
        try {
            String jsonBody = gson.toJson(body)
            String path = "/orders/${body.orderCode}"
            String response = postToBos(path, jsonBody)
            return new CommonResponse(
                    errorCode: 200,
                    faultCode: "",
                    faultString: "Thành công",
                    data: response
            )
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/thoaitra_dongbo_bos")
    CommonResponse capNhatDonHangThoaiTra(@RequestBody Map body) {
        try {
            String jsonBody = gson.toJson(body)
            String path = "/orders/${body.orderCode}/cancel"
            String response = postToBos(path, jsonBody)
            return new CommonResponse(errorCode: 200, faultCode: "", faultString: "Thành công", data: response)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/kichhoat_hoancong_dongbo_bos")
    CommonResponse kichHoatTrangThai(@RequestBody Map body) {
        try {
            String jsonBody = gson.toJson(body)
            String path = "/subscriptions/${body.subCode}/activate"
            String response = postToBos(path, jsonBody)
            return new CommonResponse(errorCode: 200, faultCode: "", faultString: "Thành công", data: response)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/thaythe_thietbi_dongbo_bos")
    CommonResponse thayTheThietBi(@RequestBody Map body) {
        try {
            String jsonBody = gson.toJson(body)
            String path = "/subscriptions/${body.subCode}/devices"
            String response = putToBos(path, jsonBody)
            return new CommonResponse(errorCode: 200, faultCode: "", faultString: "Thành công", data: response)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/chuyendungthu_sang_dungthat_dongbo_bos")
    CommonResponse chuyenDungThuSangDungThat(@RequestBody Map body) {
        try {
            String jsonBody = gson.toJson(body)
            String path = "/subscriptions/${body.subCode}/convert"
            String response = postToBos(path, jsonBody)
            return new CommonResponse(errorCode: 200, faultCode: "", faultString: "Thành công", data: response)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/muathem_bot_dvgtgt_dongbo_bos")
    CommonResponse muaThemBotDvGtgt(@RequestBody Map body) {
        try {
            String jsonBody = gson.toJson(body)
            String path = "/subscriptions/${body.subCode}/addons"
            String response = postToBos(path, jsonBody)
            return new CommonResponse(errorCode: 200, faultCode: "", faultString: "Thành công", data: response)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/tam_dung_thuebao_dongbo_bos")
    CommonResponse tamDungThueBao(@RequestBody Map body) {
        try {
            String jsonBody = gson.toJson(body)
            String path = "/subscriptions/${body.subCode}/suspend"
            String response = postToBos(path, jsonBody)
            return new CommonResponse(errorCode: 200, faultCode: "", faultString: "Thành công", data: response)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/mo_tam_dung_thuebao_dongbo_bos")
    CommonResponse moTamDungThueBao(@RequestBody Map body) {
        try {
            String jsonBody = gson.toJson(body)
            String path = "/subscriptions/${body.subCode}/resume"
            String response = postToBos(path, jsonBody)
            return new CommonResponse(errorCode: 200, faultCode: "", faultString: "Thành công", data: response)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/thanh_ly_thuebao_dongbo_bos")
    CommonResponse thanhLyThueBao(@RequestBody Map body) {
        try {
            String jsonBody = gson.toJson(body)
            String path = "/subscriptions/${body.subCode}/cancel"
            String response = postToBos(path, jsonBody)
            if (!response) {
                return new CommonResponse(errorCode: 200, faultCode: "", faultString: "", data: "Thành công")
            }
            return new CommonResponse(errorCode: 200, faultCode: "", faultString: "", data: response)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/tao_phieu_hotro_dongbo_bos")
    CommonResponse taoPhieuHoTro(@RequestBody Map body) {
        try {
            String jsonBody = gson.toJson(body)
            String path = "/api/integration/onebss/customer-tickets"
            String response = postToBos(path, jsonBody)
            return new CommonResponse(errorCode: 200, faultCode: "", faultString: "Thành công", data: response)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/dongbo_trangthai_donhang_dongbo_bos")
    CommonResponse dongBoTrangThaiDonHang(@RequestBody Map body) {
        try {
            String jsonBody = gson.toJson(body)
            String path = "/subscriptions/${body.subCode}/status"
            String response = postToBos(path, jsonBody)
            return new CommonResponse(errorCode: 200, faultCode: "", faultString: "Thành công", data: response)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/giahan_dongbo_bos")
    CommonResponse giaHanTrangThaiDonHang(@RequestBody Map body) {
        try {
            String jsonBody = gson.toJson(body)
            String path = "/subscriptions/${body.subCode}/renew"
            String response = postToBos(path, jsonBody)
            return new CommonResponse(errorCode: 200, faultCode: "", faultString: "Thành công", data: response)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }
}
