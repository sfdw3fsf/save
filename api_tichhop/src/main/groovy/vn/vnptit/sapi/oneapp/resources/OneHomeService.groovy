package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.*
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse
import vn.vnptit.sapi.oneapp.util.LoggingInOut

@RestController
@RequestMapping("onehome")
class OneHomeService {

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    HttpManaged httpManaged

    @Value("\${spring.httpservices.onehome.url}")
    String host

    @Value("\${spring.httpservices.onehome.key}")
    String key

    Gson gson = new GsonBuilder().serializeNulls().create()

    CommonResponse execute(String url, String method, def body, Map<String, String> error) {
        try {
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")
            headers.put("apikey", key)

            String resp = ""
            if (method == "GET") {
                resp = httpManaged.get(url, headers)
            } else {
                resp = httpManaged.post([url: url], headers, body != null ? gson.toJson(body) : "")
            }

            def res = new JsonSlurper().parseText(resp)
            if (res["status"].toString() == "0" || res["status"].toString() == "200") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res["data"])
            } else {
                String err = error && error[res["message"]] ? error[res["message"]] : res["message"]
                return new CommonResponse(errorCode: 1, faultCode: res["status"], faultString: err, data: res)
            }
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @GetMapping("/KiemTraSDT")
    CommonResponse KiemTraSDT(@RequestParam String phone) {
        try {
            String url = host + "/api/check-phone-existed?phone=" + phone
            return execute(url, "GET", null, null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/KiemTraMaXacThuc")
    @FakeSuccessCommonResponse
    CommonResponse KiemTraMaXacThuc(@RequestBody Map body) {
        try {
            body["userName"] = body["username"]
            String url = host + "/api/auth-smart-otp"
            return execute(url, "POST", body, OneHomeConstant.KiemTraMaXacThuc)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/TaoTaiKhoan")
    @LoggingInOut
    @FakeSuccessCommonResponse
    CommonResponse TaoTaiKhoan(@RequestBody TaoTaiKhoan_REQ body) {
        try {
            String url = host + "/api/dang-ky-tai-khoan"
            return execute(url, "POST", body, OneHomeConstant.TaoTaiKhoan)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/HoanCong")
    @LoggingInOut
    @FakeSuccessCommonResponse
    CommonResponse HoanCong(@RequestBody HoanCongNhieuPhieu_REQ body) {
        try {
            String url = host + "/api/hoan-cong"
            CommonResponse resp = execute(url, "POST", body.dsPhieu, OneHomeConstant.HoanCong)
            if (resp.errorCode == 0 && resp.data && ((List) resp.data).size() > 0) {
                if (((List) resp.data).size() != body.dsPhieu.size()) {
                    return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Số phiếu kích hoạt onehome không khớp", data: resp.data)
                }

                List respSuccess = resp.data.findAll({ it["acceptCompleted"] && it["activationStatus"] })
                if (respSuccess.size() != body.dsPhieu.size()) {
                    List respFailAccept = resp.data.findAll({ !it["acceptCompleted"] })
                    List respFailActive = resp.data.findAll({ !it["activationStatus"] })
                    String error = ""
                    if (respFailAccept.size() > 0) {
                        error += "Thiết bị chưa đủ điều kiện hoàn công: " + respFailAccept.collect { "${it["serialNumber"]}-${it["phoneNumber"]}" }.join(',')
                    }
                    if (respFailActive.size() > 0) {
                        if (error.length() > 0) error += "\n"
                        error += "Thiết bị với serial đầu vào không tồn tại: " + respFailActive.collect { "${it["serialNumber"]}-${it["phoneNumber"]}" }.join(',')
                    }
                    return new CommonResponse(errorCode: 1, faultCode: "", faultString: error, data: resp.data)
                }

                return resp
            } else {
                return new CommonResponse(errorCode: 1, faultCode: resp.faultCode, faultString: resp.faultString, data: resp.data)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/TraCuuThongTinThietBi")
    CommonResponse TraCuuThongTinThietBi(@RequestParam String serialCamera) {
        try {
            String url = host + "/api/get-list-device-not-owner?serialCamera=" + serialCamera
            return execute(url, "GET", null, null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/ThayDoiThongTinTaiKhoan")
    @LoggingInOut
    @FakeSuccessCommonResponse
    CommonResponse ThayDoiThongTinTaiKhoan(@RequestBody ThayDoiThongTinTaiKhoan_REQ body) {
        try {
            String url = host + "/api/cap-nhat-tai-khoan"
            return execute(url, "POST", body, OneHomeConstant.ThayDoiThongTinTaiKhoan)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/KhoaThietBi")
    @LoggingInOut
    @FakeSuccessCommonResponse
    CommonResponse KhoaThietBi(@RequestBody KhoaThietBi_REQ body) {
        try {
            String url = host + "/api/khoa-thiet-bi"
            return execute(url, "POST", body.serialNumber, null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/MoKhoaThietBi")
    @LoggingInOut
    @FakeSuccessCommonResponse
    CommonResponse MoKhoaThietBi(@RequestBody KhoaThietBi_REQ body) {
        try {
            String url = host + "/api/mo-khoa-thiet-bi"
            return execute(url, "POST", body.serialNumber, null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/ImportWhitelist")
    @LoggingInOut
    CommonResponse ImportWhitelist(@RequestBody KhoaThietBi_REQ body) {
        try {
            String url = host + "/api/danh-sach-mien-khoa"
            return execute(url, "POST", body.serialNumber, null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

}