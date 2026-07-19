package vn.vnptit.sapi.oneapp.resources.cts

import client.ws.visa.application.ApplicationBusinessServicesSoapBindingStub
import groovy.util.slurpersupport.GPathResult
import org.apache.axis.AxisFault
import org.apache.axis.message.MessageElement
import org.apache.axis.message.SOAPHeaderElement
import org.apache.commons.lang.StringEscapeUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.cts.*
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse
import ws.client.cts.*

import javax.net.ssl.KeyManager
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.xml.soap.SOAPElement
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.text.SimpleDateFormat

@RestController
@RequestMapping("ctslinetest")
class CtsLineService {
    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    OracleManaged oracleManaged

    @Autowired
    HttpManaged httpManaged

    @Autowired
    @Qualifier("ctsServicesStubThreadLocal")
    ThreadLocal<LineTestWSSoap_BindingStub> servicesStubThreadLocal

    @Value("\${spring.httpservices.cts.url}")
    String url

    @Value("\${spring.httpservices.cts.username}")
    String username

    @Value("\${spring.httpservices.cts.password}")
    String password

    private List<Object> processMessageElement(MessageElement[] me) {
        def ret = []
        if (me.length >= 2) {
            def Envelope = new XmlSlurper().parseText(me[1].getAsString())

            def Items = (Envelope.children().first() as GPathResult)

            Items.children().each { GPathResult item ->
                def row = [:]
                item.children().each { GPathResult f ->
                    row.put(f.name(), f.text())
                }

                ret.add(row)
            }
        }

        return ret
    }

    @PostMapping("/changeDslPortProfile")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeDslPortProfile(@RequestBody ChangeDslPortProfile body) {
        LineTestWSSoap_BindingStub service = servicesStubThreadLocal.get()
        String result
        try {
            result = service.changeDslPortProfile(body.deviceIp, body.frame, body.slot, body.port, body.profileName)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/testOnuByPortVisa")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse testOnuByPortVisa(@RequestBody TestOnuByPortVisaReq body) {
        LineTestWSSoap_BindingStub service = servicesStubThreadLocal.get()
        TestOnuByPortVisaResponseTestOnuByPortVisaResult ret
        try {
            String reqBody = """
            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:vnpt="http://vnpt.vn/">
               <soapenv:Header>
                  <vnpt:AuthHeaderWS>
                     <!--Optional:-->
                     <vnpt:Username>$username</vnpt:Username>
                     <!--Optional:-->
                     <vnpt:Password>$password</vnpt:Password>
                  </vnpt:AuthHeaderWS>
               </soapenv:Header>
               <soapenv:Body>
                  <vnpt:TestOnuByPortVisa>
                     <!--Optional:-->
                     <vnpt:PortInfo>${body.portInfo}</vnpt:PortInfo>
                  </vnpt:TestOnuByPortVisa>
               </soapenv:Body>
            </soapenv:Envelope>
            """
            String xml = httpManaged.post(["url": url],
                    [
                            "Content-Type": "text/xml;charset=UTF-8"
                    ],
                    reqBody)

            List data = processXML(xml)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    private List<Object> processXML(String xml) {
        xml = xml.replaceAll(/&#x([0-9A-F]+?);/, "")

        def ret = []
        def Envelope = new XmlSlurper().parseText(xml)

        def Items = (Envelope.Body.TestOnuByPortVisaResponse.TestOnuByPortVisaResult.diffgram.NewDataSet as GPathResult)

        Items.children().each { GPathResult item ->
            def row = [:]
            item.children().each { GPathResult f ->
                row.put(f.name(), f.text())
            }

            ret.add(row)
        }

        return ret
    }

    @PostMapping("/resetOnuConnectionByPortVisa")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse resetOnuConnectionByPortVisa(@RequestBody ResetOnuConnectionByPortVisa body) {
        LineTestWSSoap_BindingStub service = servicesStubThreadLocal.get()
        String result
        try {
            result = service.resetOnuConnectionByPortVisa(body.visaString)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/changeDslPortProfileByPortVisa")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeDslPortProfileByPortVisa(@RequestBody ChangeDslPortProfileByPortVisa body) {
        LineTestWSSoap_BindingStub service = servicesStubThreadLocal.get()
        String result
        try {
            result = service.changeDslPortProfileByPortVisa(body.visaString, body.profileName)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/getNonTrafficFiberVNN")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getNonTrafficFiberVNN(@RequestBody GetNonTrafficFiberVNN body) {
        LineTestWSSoap_BindingStub service = servicesStubThreadLocal.get()

        Calendar fromDate = Calendar.getInstance()
        Calendar toDate = Calendar.getInstance()
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)

        try {
            fromDate.setTime(sdf.parse(body.fromDate))
            toDate.setTime(sdf.parse(body.toDate))
        } catch (Exception e) {
            loggingManaged.error(e.message, e)
        }

        GetNonTrafficFiberVNNResponseGetNonTrafficFiberVNNResult xml
        try {
            xml = service.getNonTrafficFiberVNN(body.unitID, fromDate, toDate, body.backgroundTraffic)
            List<Object> data = processMessageElement(xml._any)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/getONTInfoByAccount")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getONTInfoByAccount(@RequestBody GetONTInfoByAccount body) {
        LineTestWSSoap_BindingStub service = servicesStubThreadLocal.get()
        GetONTInfoByAccountResponseGetONTInfoByAccountResult xml
        try {
            xml = service.getONTInfoByAccount(body.provinceCode, body.accountName)
            List<Object> data = processMessageElement(xml._any)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/getUrlQualityFile")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getUrlQualityFile(@RequestBody GetUrlQualityFile body) {
        LineTestWSSoap_BindingStub service = servicesStubThreadLocal.get()
        String result
        try {
            result = service.getUrlQualityFile(body.unitID)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/resetByPortVisa")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse resetByPortVisa(@RequestBody ResetByPortVisa body) {
        LineTestWSSoap_BindingStub service = servicesStubThreadLocal.get()
        try {
            TaskOfString tos = service.resetByPortVisa(body.visaString)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: tos)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/resetDslPort")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse resetDslPort(@RequestBody ResetDslPort body) {
        LineTestWSSoap_BindingStub service = servicesStubThreadLocal.get()
        try {
            TaskOfString tos = service.resetDslPort(body.deviceIp, body.frame, body.slot, body.port)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: tos)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/resetOnuConnection")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse resetOnuConnection(@RequestBody ResetOnuConnection body) {
        LineTestWSSoap_BindingStub service = servicesStubThreadLocal.get()
        String result
        try {
            result = service.resetOnuConnection(body.deviceIp, body.frame, body.slot, body.port, body.onuIndex)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/testByPort")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse testByPort(@RequestBody ResetDslPort body) {
        LineTestWSSoap_BindingStub service = servicesStubThreadLocal.get()
        TestByPortResponseTestByPortResult xml
        try {
            xml = service.testByPort(body.deviceIp, body.frame, body.slot, body.port)
            List<Object> data = processMessageElement(xml._any)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/testByPortVisa")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse testByPortVisa(@RequestBody TestByPortVisaReq body) {
        LineTestWSSoap_BindingStub service = servicesStubThreadLocal.get()
        TestByPortVisaResponseTestByPortVisaResult xml
        try {
            xml = service.testByPortVisa(body.portInfo)
            List<Object> data = processMessageElement(xml._any)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/testOnuByAccount")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse testOnuByAccount(@RequestBody TestOnuByAccount body) {
        LineTestWSSoap_BindingStub service = servicesStubThreadLocal.get()
        TestOnuByAccountResponseTestOnuByAccountResult xml
        try {
            xml = service.testOnuByAccount(body.accountName)
            List<Object> data = processMessageElement(xml._any)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/testOnuPort")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse testOnuPort(@RequestBody ResetOnuConnection body) {
        LineTestWSSoap_BindingStub service = servicesStubThreadLocal.get()
        TestOnuPortResponseTestOnuPortResult xml
        try {
            xml = service.testOnuPort(body.deviceIp, body.frame, body.slot, body.port, body.onuIndex)
            List<Object> data = processMessageElement(xml._any)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/updateUrlGponQualityDetailByUnit")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse updateUrlGponQualityDetailByUnit(@RequestBody UpdateUrlGponQualityDetailByUnit body) {
        LineTestWSSoap_BindingStub service = servicesStubThreadLocal.get()

        Calendar statisticDate = Calendar.getInstance()
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)

        try {
            statisticDate.setTime(sdf.parse(body.statisticDate))
        } catch (Exception e) {
            loggingManaged.error(e.getMessage(), e)
        }

        try {
            service.updateUrlGponQualityDetailByUnit(body.unitID, statisticDate)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }
}
