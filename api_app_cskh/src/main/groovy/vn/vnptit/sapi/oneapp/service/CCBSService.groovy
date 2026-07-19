package vn.vnptit.sapi.oneapp.service

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.stereotype.Component
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.util.Constant

@Component
class CCBSService {
    @Autowired
    private DiscoveryClient discoveryClient

    ObjectMapper json = new ObjectMapper();

    public static final String TRACUU_PATH = "/"

    private String token = ""
    private String base_url = ""

    def init(String token) {
        this.token = token
        List<ServiceInstance> nodes = discoveryClient.getInstances(Constant.CCBS)
        if (nodes == null || nodes.size() == 0) {
            base_url = "https://api-onebss.vnpt.vn/ccbs/"
        } else {
            ServiceInstance node = nodes[0]
            base_url = "http${node.secure ? 's' : ''}://${node.host}:${node.port}" + TRACUU_PATH
        }
    }

    public String ts_tracuu_laytt_tb(String so_tb) {
        String url = base_url + "tracuu/ts_tracuu_laytt_tb"
        String body = """{
                      "so_tb": "$so_tb"
                    }"""
        String resp = HttpManaged.post(["url": url], ["Content-Type" : "application/json", "Authorization": token], body)
        return resp
    }

    public String get_number_of_changesim(String fdate, tdate) {
        String url = base_url + "tracuu/get_number_of_changesim"
        String body = """{
                      "from_date": "$fdate",
                        "to_date": "$tdate"
                    }"""
        String resp = HttpManaged.post(["url": url], ["Content-Type" : "application/json", "Authorization": token], body)
        return resp
    }

    public String get_number_of_reginfo(String fdate, tdate) {
        String url = base_url + "tracuu/get_number_of_reginfo"
        String body = """{
                      "from_date": "$fdate",
                        "to_date": "$tdate"
                    }"""
        String resp = HttpManaged.post(["url": url], ["Content-Type" : "application/json", "Authorization": token], body)
        return resp
    }

    public String get_number_of_regpackage(String fdate, tdate) {
        String url = base_url + "tracuu/get_number_of_regpackage"
        String body = """{
                      "from_date": "$fdate",
                        "to_date": "$tdate"
                    }"""
        String resp = HttpManaged.post(["url": url], ["Content-Type" : "application/json", "Authorization": token], body)
        return resp
    }

    def qltn_ts_tt_no_tonghop(String kyhoadon, String ma_kh, String so_tb) {
        String url = base_url + "qltn/ts_tt_no_tonghop"
        def body = [
                kyhoadon: kyhoadon,
                ma_kh: ma_kh,
                so_tb: so_tb
        ]
        String resp = HttpManaged.post(["url": url], ["Content-Type" : "application/json", "Authorization": token], json.writeValueAsString(body))
        try {
            def retObject = new JsonSlurper().parseText(resp)
            return retObject.data
        }catch(Exception ex){
            return null
        }
    }

    def qltn_ts_tracuu_ls_tra(String tu_kyhoadon, String den_kyhoadon, String ma_kh, String so_tb) {
        String url = base_url + "qltn/ts_tracuu_ls_tra"
        def body = [
                tu_kyhoadon: tu_kyhoadon,
                den_kyhoadon: den_kyhoadon,
                ma_kh: ma_kh,
                so_tb: so_tb
        ]
        String resp = HttpManaged.post(["url": url], ["Content-Type" : "application/json", "Authorization": token], json.writeValueAsString(body))
        try {
            def retObject = new JsonSlurper().parseText(resp)
            return retObject.data
        }catch(Exception ex){
            return null
        }
    }

    def qltn_ts_tracuu_ls_no(String tu_kyhoadon, String den_kyhoadon, String ma_kh, String so_tb) {
        String url = base_url + "qltn/ts_tracuu_ls_no"
        def body = [
                tu_kyhoadon: tu_kyhoadon,
                den_kyhoadon: den_kyhoadon,
                ma_kh: ma_kh,
                so_tb: so_tb
        ]
        String resp = HttpManaged.post(["url": url], ["Content-Type" : "application/json", "Authorization": token], json.writeValueAsString(body))
        try {
            def retObject = new JsonSlurper().parseText(resp)
            return retObject.data
        }catch(Exception ex){
            return null
        }
    }
}
