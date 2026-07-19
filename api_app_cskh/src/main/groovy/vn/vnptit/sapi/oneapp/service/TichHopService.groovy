package vn.vnptit.sapi.oneapp.service

import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import org.springframework.web.context.WebApplicationContext
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.util.Constant

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TichHopService {

    @Autowired
    private DiscoveryClient discoveryClient

    public static final String PATH_SPACE = "/vnptpay/"
    private String token = ""
    private String base_url = ""

    def init(String token) {
        this.token = token
        try {
            List<ServiceInstance> nodes = discoveryClient.getInstances(Constant.SERVICE)
            if (nodes == null || nodes.size() == 0) {
                base_url = "https://api-onebss.vnpt.vn/tichhop/vnptpay/"
            } else {
                ServiceInstance node = nodes[0]
                base_url = "http${node.secure ? 's' : ''}://${node.host}:${node.port}" + PATH_SPACE
            }
        } catch (Exception e) {
            base_url = "https://api-onebss.vnpt.vn/tichhop/vnptpay/"
        }
    }

    Map<String, Object> checkUserPayByPhone(String phoneNumber) {
        String url = base_url + "checkUserPayByPhone" + "?" + "phoneNumber=" + phoneNumber
        String resp = HttpManaged.get(url, ["Content-Type" : "application/json",
                                            "Authorization": token])
        return new JsonSlurper().parseText(resp) as Map<String, Object>
    }
}
