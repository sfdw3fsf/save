package vn.vnptit.sapi.oneapp.service

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import org.springframework.web.context.WebApplicationContext
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.model.request.DoiPass
import vn.vnptit.sapi.oneapp.util.Constant

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SPSService {

    ObjectMapper json = new ObjectMapper();

    @Autowired
    private DiscoveryClient discoveryClient

    public static final String PATH_SPACE = "/sps/"
    private String token = ""
    private String base_url = ""

    def init(String token) {
        this.token = token
        List<ServiceInstance> nodes = discoveryClient.getInstances(Constant.SERVICE)
        if (nodes == null || nodes.size() == 0) {
            base_url = "https://api-onebss.vnpt.vn/tichhop/sps/"
//            base_url = "http://localhost:8888/sps/"
        } else {
            ServiceInstance node = nodes[0]
            base_url = "http${node.secure ? 's' : ''}://${node.host}:${node.port}" + PATH_SPACE
        }
    }

    String ts_ism_modify_subscriber(String msisdn, String service_action, String user_password) {
        try {
            String url = base_url + "ts_ism_modify_subscriber"
            def req = [:]
            req.msisdn = "84" + msisdn
            req.service_action = service_action
            req.user_password = user_password
            String body = json.writeValueAsString(req)
            String resp = HttpManaged.post(["url": url],
                    ["Content-Type" : "application/json",
                     "Authorization": token], body)
            def res = new JsonSlurper().parseText(resp)
            if (res["errorCode"] == 0) {
                return "OK"
            } else {
                return res["faultString"]
            }
        } catch (Exception ex){
            println ex.getMessage()
        }
    }
}
