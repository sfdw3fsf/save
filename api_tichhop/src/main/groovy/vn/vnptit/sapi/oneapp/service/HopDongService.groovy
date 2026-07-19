package vn.vnptit.sapi.oneapp.service

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.stereotype.Component
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.util.Constant

@Component
class HopDongService {
    @Autowired
    private DiscoveryClient discoveryClient

    ObjectMapper json = new ObjectMapper();

    public static final String TRACUU_PATH = "/sms/"

    private String token = ""
    private String base_url = ""

    def init(String token) {
        this.token = token
        List<ServiceInstance> nodes = discoveryClient.getInstances("web-hopdong")
        if (nodes == null || nodes.size() == 0){
            base_url = "https://api-onebss.vnpt.vn/web-hopdong/hopdong/"
        }else{
            ServiceInstance node = nodes[0]
            base_url = "http${node.secure ? 's' : ''}://${node.host}:${node.port}" + "/hopdong/"
        }
    }

    public boolean get_app_config() {
        try {
            String url = base_url + "get_app_config"
            def req = [:]
            req.keyname = "DONGBO_TEST"
            String body = json.writeValueAsString(req)
            String resp = HttpManaged.post(["url": url], ["Content-Type" : "application/json", "Authorization": token], body)
            def ret = new JsonSlurper().parseText(resp)
            // if (ret.data.dongbo != "1" || ret.data.keyvalue != "FALSE") {
            //     try {
            //         telegramService.sendMessage(url + "|" + token + "|" + json.writeValueAsString(ret), "-997442492")
            //     } catch (Exception e) {}
            // }
            return ret.data.dongbo == "1" && ret.data.keyvalue == "FALSE"
        } catch(Exception ex){
            // try {
            //     telegramService.sendMessage(token + "|" + ex.message, "-997442492")
            // } catch (Exception e) {}
            return false
        }
    }

    public boolean checkCallVisaOtherProvince(String accountName) {
        boolean result = false
        try {
            String url = base_url + "checkCallVisaOtherProvince"
            def req = [:]
            req.accountName = accountName
            String body = json.writeValueAsString(req)
            String resp = HttpManaged.post(["url": url], ["Content-Type" : "application/json", "Authorization": token], body)
            def ret = new JsonSlurper().parseText(resp)
            if (ret.error_code == "BSS-00000000" && ret.data == "1") {
                result = true
            }
        } catch(Exception ex){
            
        }
        return result
    }

    public void logAction(String actionName, String keyParameter, String note) {
        try {
            String url = base_url + "log_action"
            def req = [:]
            req.action_name = actionName
            req.key_parameter = keyParameter
            req.note = note
            HttpManaged.post(["url": url], ["Content-Type" : "application/json", "Authorization": token], json.writeValueAsString(req))
        } catch(Exception ex){
            
        }
    }

    public void sendSMS(String so_dt, String noidung, String ghichu) {
        try {
            String url = "http://web-hopdong/xac_nhan_giahan_datcoc_tt/send_sms"
            def req = [:]
            req.so_dt = so_dt
            req.noidung = noidung
            req.ghichu = ghichu
            HttpManaged.post(["url": url], ["Content-Type" : "application/json", "Authorization": token], json.writeValueAsString(req))
        } catch(Exception ex){
            
        }
    }

    public String lay_dulieu_promotion(String hdtb_id) {
        String result = "1"
        try {
            String url = "http://web-hopdong/hopdong/lay_dulieu_promotion?hdtb_id=" + hdtb_id
            String resp = HttpManaged.get(url, ["Content-Type" : "application/json", "Authorization": token])
            def ret = new JsonSlurper().parseText(resp)
            if (ret["error_code"] == "BSS-00000000" && ret["data"]) {
                result = ret["data"]
            }
        } catch(Exception ex){
            
        }
        return result
    }

    public boolean ktra_hdong_daxuat(String cusCode) {
        boolean ktra = false
        try {
            String url = "http://web-hopdong/hopdong/ktra_hdong_daxuat"
            def req = [:]
            req.cusCode = cusCode
            String resp = HttpManaged.post(["url": url], ["Content-Type" : "application/json", "Authorization": token], json.writeValueAsString(req))
            def ret = new JsonSlurper().parseText(resp)
            if (ret["error_code"] != "BSS-00000000" || ret["data"].toString() == "1") {
                ktra = true
            }
        } catch(Exception ex){
            ktra = true
        }
        return ktra
    }

    public String lay_thongtin_diachi_ims(String ma_tb, String type) {
        String diachi = ""
        try {
            String url = "http://web-hopdong/khaibao-ims/lay_thongtin_diachi_ims_theo_matb?ma_tb="+ma_tb+"&type="+type
            String resp = HttpManaged.get(url, ["Content-Type" : "application/json", "Authorization": token])
            def ret = new JsonSlurper().parseText(resp)
            if (ret["error_code"] == "BSS-00000000" && ret["data"]) {
                diachi = ret["data"]
            }
        } catch(Exception ex){
        }
        diachi
    }
    
}
