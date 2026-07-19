package vn.vnptit.sapi.oneapp.service

import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.stereotype.Component
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.util.Constant

@Component
public class SmsService {

    @Autowired
    private DiscoveryClient discoveryClient

    public static final String SMS_SPACE = "/sms/"

    private String token = ""
    private String url = ""

    def init(String token) {
        this.token = token
        List<ServiceInstance> nodes = discoveryClient.getInstances(Constant.SERVICE)
        if (nodes == null || nodes.size() == 0){
            url = "https://api-onebss.vnpt.vn/tichhop/sms/"
        }else{
            ServiceInstance node = nodes[0]
            url = "http${node.secure ? 's' : ''}://${node.host}:${node.port}" + SMS_SPACE
        }
    }

    public String send(String message, String phone) {
        String url = url + "send"
        String body = """{
                          "msisdn": "$phone",
                          "content": "$message",
                          "type": ""
                        }
                        """
        try {
            String resp = HttpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": token], body)
            def result = new JsonSlurper().parseText(resp)
            if (result.errorCode == 0) {
                return "OK"
            } else {
                return result.faultString == null ? result.message : result.faultString
            }
        } catch(Exception ex) {
            return ex.message
        }
    }
}
