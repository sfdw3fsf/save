package vn.vnptit.sapi.oneapp.resources.fmsalarms

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.netflix.discovery.converters.Auto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.properties.DataSourceProperties
import vn.vnptit.sapi.oneapp.properties.HttpServiceConfig

import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("/fms-alarm")
class FmsAlarmService {

    @Autowired
    ThreadLocal<Gson> gsons;

    @Autowired
    DataSourceProperties dataSourceProperties

    @PostMapping("/{action}")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse action(@PathVariable("action") String action, @RequestBody Map<String, Object> body) {
        StringBuilder sb = new StringBuilder()

        HttpServiceConfig fmsConfig = dataSourceProperties.httpservices.get('fms-alarms')

        sb.append("""<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:tem="http://tempuri.org/"><soap:Header><tem:AuthHeader><tem:Username>""")
        sb.append(fmsConfig.usr)
        sb.append("""</tem:Username><tem:Password>""")
        sb.append(fmsConfig.pwd)
        sb.append("""</tem:Password></tem:AuthHeader></soap:Header><soap:Body><tem:$action>""")

        Gson gson = gsons.get()
        body.each { k, v ->
            sb.append("""<tem:$k>""")
            if (v instanceof String || v instanceof Integer || v instanceof Long) {
                sb.append(v)
            } else
                sb.append(gson.toJson(v))
            sb.append("""</tem:$k>""")
        }

        sb.append("""</tem:$action></soap:Body></soap:Envelope>""")

        def Envelope = new XmlSlurper().parseText(HttpManaged.post(
                [url: fmsConfig.url],
                ['Content-Type': fmsConfig.headers.get('Content-Type') ?: 'application/soap+xml;charset=UTF-8'],
                sb.toString()
        ))

        String data = Envelope.text()

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readTree(data);
            new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: actualObj)
        } catch (e) {
            new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        }
    }
}
