package vn.vnptit.sapi.oneapp.service

import com.google.gson.Gson
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.stereotype.Component
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.util.Constant

@Component
public class CTSService {

    public static final String PATH_SPACE = "/ctslinetest/"
    private String base_url = ""
    private String token = ""

    @Autowired
    private DiscoveryClient discoveryClient

    def init(String token) {
        this.token = token
        List<ServiceInstance> nodes = discoveryClient.getInstances(Constant.SERVICE)
        if (nodes == null || nodes.size() == 0) {
            base_url = "https://api-onebss-rb-dev.vnptit3.vn/tichhop-ex/ctslinetest/"
        } else {
            ServiceInstance node = nodes[0]
            base_url = "http${node.secure ? 's' : ''}://${node.host}:${node.port}" + PATH_SPACE
        }
    }

    public def testOnuPort(String deviceip, String rack, String sefl, String slot, String port, String vpi) {
        String url = "http://tichhop-ex/ctsnewpost/testOnuPorts?deviceip=$deviceip&rack=$rack&sefl=$sefl&slot=$slot&port=$port&vpi=$vpi"
        String body = ""
        String resp = HttpManaged.post(["url": url],
                ["Content-Type" : "application/json",
                 "Authorization": token], body)
        def res = new JsonSlurper().parseText(resp)
        return res
    }

    public def testOnuPort_vb8362(String deviceip, String systemName, String frame, String slot, String port, String vpi) {
        String url = "http://tichhop-ex/ctsnewpost/" + "testOnuPortsvb8362?deviceip=$deviceip&systemName=$systemName&frame=$frame&slot=$slot&port=$port&vpi=$vpi"
        String body = ""
        String resp = HttpManaged.get(url, ["Content-Type": "application/json", "Authorization": token])
        def res = new JsonSlurper().parseText(resp)
        return res
    }

    public def getUserInforByAccount(String accountName, int serviceType) {
        String url = "http://app-ccdv/ctsnewpost/getUserInfoByAccount"
        Map<String , Object> body = new HashMap<>()
        body.accountName = accountName
        body.serviceType = serviceType
        String resp = HttpManaged.post(["url": url],
                ["Content-Type" : "application/json",
                 "Authorization": token], new Gson().toJson(body))
        def res
        if(!resp.equals("") && resp != null) {
            res = new JsonSlurper().parseText(resp)
        }
        return res
    }

    public def getDeviceInformationByOptical(String serialNo) {
        String url = "http://tichhop-ex/cts/getDeviceInformationByOptical"
        Map<String , Object> body = new HashMap<>()
        body.serialNo = serialNo
        String resp = HttpManaged.post(["url": url],
                ["Content-Type" : "application/json",
                 "Authorization": token], new Gson().toJson(body))
        def res
        if(!resp.equals("") && resp != null) {
            res = new JsonSlurper().parseText(resp)
        }
        return res
    }
    //
}
