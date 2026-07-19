package vn.vnptit.sapi.oneapp.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import vn.vnptit.sapi.oneapp.managed.HttpManaged

@Component
class TraCuuService {

    @Autowired
    HttpManaged httpManaged

    @Autowired
    ObjectMapper objectMapper

    String host = "http://web-tracuu"

    String fn_kiemtra_hoadon_tratruoc_sau(String token, String kieu, String giatri) {
        try {
            String url = host + "/tracuu/fn_kiemtra_hoadon_tratruoc_sau?kieu=$kieu&giatri=$giatri"
            String resp = httpManaged.get(url, ["Content-Type": "application/json", "Authorization": token])
            def res = objectMapper.readValue(resp, Map.class)
            if (res["error_code"].toString() != "BSS-00000000") {
                return "Lỗi: " + res["message"]
            }

            return res["data"]
        } catch (Exception ex) {
            return "Lỗi: " + ex.message
        }
    }

    @Async
    void capnhat_log_tracuu_sms(String token, String vInput, String vOutput) {
        try {
            String url = host + "/tracuu/capnhat_log_tracuu_sms"
            def body = [:]
            body["vInput"] = vInput
            body["vOutput"] = vOutput
            httpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": token],
                    objectMapper.writeValueAsString(body))
        } catch (Exception ignored) {
        }
    }

    @Async
    String fn_tracuu_serial_ma_vt(String token, String serial) {
        try {
            String url = host + "/tracuu/fn_tracuu_serial_ma_vt?serial=$serial"
            String resp = httpManaged.get(url, ["Content-Type": "application/json", "Authorization": token])
            def res = objectMapper.readValue(resp, Map.class)
            if (res["error_code"].toString() != "BSS-00000000") {
                return "Lỗi: " + res["message"]
            }
            return res["data"].toString()
        } catch (Exception ex) {
            return "Lỗi: " + ex.message
        }
    }

}

