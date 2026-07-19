package vn.vnptit.sapi.oneapp.resources.qldt

import groovy.json.JsonSlurper
import groovy.util.slurpersupport.GPathResult
import org.apache.axis.message.MessageElement
import org.json.XML
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.cts.*
import ws.client.qldt.MainSVRSoap_BindingStub

import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.text.SimpleDateFormat

@RestController
@RequestMapping("qldt")
class QLDTService {
    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    OracleManaged oracleManaged

    @Value("\${spring.httpservices.qldt.username}")
    String username

    @Value("\${spring.httpservices.qldt.password}")
    String password

    @Autowired
    @Qualifier("qldtServicesStubThreadLocal")
    ThreadLocal<MainSVRSoap_BindingStub> qldtServicesStubThreadLocal

    @GetMapping("/get_ProjectbyInvestmentField")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_ProjectbyInvestmentField() {
        def service = qldtServicesStubThreadLocal.get()
        try {
            def xml = service.get_ProjectbyInvestmentField(username, password)
            def result = new JsonSlurper().parseText(XML.toJSONObject(xml).toString())
            try {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result.NewDataSet.Table)
            } catch (Exception ex) {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: result)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/get_ListProject")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_ListProject(@RequestParam String sDepartmentCode) {
        def service = qldtServicesStubThreadLocal.get()
        try {
            def xml = service.get_ListProject(username, password, sDepartmentCode)
            def result = new JsonSlurper().parseText(XML.toJSONObject(xml).toString())
            try {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result.NewDataSet.Table)
            } catch (Exception ex) {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: result)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

}
