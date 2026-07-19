package vn.vnptit.sapi.oneapp.resources.visa

import client.ws.visa.application.ApplicationBusinessServicesSoapBindingStub
import client.ws.visa.report.ReportBusinessServicesSoapBindingStub
import client.ws.visa.report.WSDeviceImplServiceLocator
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.util.slurpersupport.GPathResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.visacom.*
import vn.vnptit.sapi.oneapp.models.visareport.GetIps
import vn.vnptit.sapi.oneapp.models.visareport.GetIpsV6
import vn.vnptit.sapi.oneapp.models.visareport.getIpsV6byGroupCustomer
import vn.vnptit.sapi.oneapp.util.GenericApi

import javax.net.ssl.KeyManager
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate

@RestController
@RequestMapping("visa-report")
class VisaReportService {
    static String attrType = "@xsi:type"
    static String attrItemType = "@soapenc:arrayType"
    static String attrHref = "@href"
    static String attrNull = "@xsi:nil"

    static ThreadLocal<Gson> gson = ThreadLocal.withInitial { new GsonBuilder().serializeNulls().create() }

    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    OracleManaged oracleManaged
    @Autowired
    HttpManaged httpManaged

    @Autowired
    GenericApi genericApi
    @Autowired
    NativeWebRequest request

    @Value("\${spring.httpservices.visa.accounts}")
    String visaAccounts

    @Value("\${spring.httpservices.visa.url}")
    String visaUrl


    static Map<String, Map<String, String>> cachedAcc = null

    void loadVisaAccountsMap() {
        cachedAcc = [:]
        visaAccounts.split(',').each {
            String[] accRec = it.split(":", 3)
            cachedAcc.put(accRec[0], ["username": accRec[1], "password": accRec[2]])
        }
    }

    String getUrlVisa(){
        return "http://10.156.10.49/visa/services/" + "ReportBusinessServices"
    }

    String getUsername(Map<String, Object> session) {

        String phanvungId = session[GenericApi.ID_TINHTHANH]

        if (cachedAcc == null) {
            synchronized (visaAccounts) {
                if (cachedAcc == null) loadVisaAccountsMap()
            }
        }

        if (cachedAcc) {
            if (cachedAcc.containsKey(phanvungId)) {
                return cachedAcc.get(phanvungId).get("username")
            } else {
                return cachedAcc.get("0").get("username")
            }
        }
    }

    String getPassword(Map<String, Object> session) {

        String phanvungId = session[GenericApi.ID_TINHTHANH]

        if (cachedAcc == null) {
            synchronized (visaAccounts) {
                if (cachedAcc == null) loadVisaAccountsMap()
            }
        }

        if (cachedAcc) {
            if (cachedAcc.containsKey(phanvungId)) {
                return cachedAcc.get(phanvungId).get("password")
            } else {
                return cachedAcc.get("0").get("password")
            }
        }
    }

    @Autowired
    @Qualifier("visaReportServicesStubThreadLocal")
    ThreadLocal<ReportBusinessServicesSoapBindingStub> servicesStubThreadLocal

    @PostMapping("/getIps")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getIps(@Valid @RequestBody GetIps body) {
        ReportBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)
            def data = service.getIps(body.login, body.brasId, body.serviceId)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/getIpsV6")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getIpsV6(@Valid @RequestBody GetIpsV6 body) {
        ReportBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)
            def data = service.getIpsV6(body.login, body.brasId, body.serviceId)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/getIpsV6byGroupCustomer")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getIpsV6byGroupCustomer(@Valid @RequestBody getIpsV6byGroupCustomer body) {
        try {
            def session = genericApi.getSessionData(request)
            String reqBody =
                    """
                    <soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:dev="http://device.ws.hyframe.hyperlogy" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/">
                        <soapenv:Header/>
                        <soapenv:Body>
                            <dev:getIpsV6byCustomerType soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                                <login xsi:type="urn:login" xmlns:urn="urn:security.model.hyframe.hyperlogy">
                                    <password xsi:type="xsd:string">${getPassword(session)}</password>
                                    <userId xsi:type="xsd:string">${getUsername(session)}</userId>
                                </login>
                                <brasId xsi:type="xsd:long">${body.brasId}</brasId>
                                <serviceId xsi:type="xsd:long">${body.serviceId}</serviceId>
                                <cusType xsi:type="xsd:string">${body.groupCustomerCode}</cusType>
                            </dev:getIpsV6byCustomerType>
                        </soapenv:Body>
                    </soapenv:Envelope>
                    """
            def env = post1(getUrlVisa(),reqBody)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: env)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/getIpsbyGroupCustomer")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getIpsbyGroupCustomer(@Valid @RequestBody getIpsV6byGroupCustomer body) {
        try {
            def session = genericApi.getSessionData(request)
            String reqBody =
                    """
                    <soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:dev="http://device.ws.hyframe.hyperlogy" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/">
                        <soapenv:Header/>
                        <soapenv:Body>
                            <dev:getIpsbyCustomerType soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                                <login xsi:type="urn:login" xmlns:urn="urn:security.model.hyframe.hyperlogy">
                                    <password xsi:type="xsd:string">${getPassword(session)}</password>
                                    <userId xsi:type="xsd:string">${getUsername(session)}</userId>
                                </login>
                                <brasId xsi:type="xsd:long">${body.brasId}</brasId>
                                <serviceId xsi:type="xsd:long">${body.serviceId}</serviceId>
                                <cusType xsi:type="xsd:string">${body.groupCustomerCode}</cusType>
                            </dev:getIpsbyCustomerType>
                        </soapenv:Body>
                    </soapenv:Envelope>
                    """
            def env = post1(getUrlVisa(),reqBody)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: env)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    Object parseNode(GPathResult root, GPathResult node) {
        String type = node[attrType], refId = node[attrHref], isNull = node[attrNull]

        if (refId) return parseNode(root, root.'**'.find { it.@id == refId.drop(1) })
        if (isNull == 'true') return null
        if ([':dateTime', ':long', ':int', ':boolean'].any { type.endsWith(it) }) {
            String text = node.text()
            if (text) {
                if (type.endsWith(':long')) return Long.parseLong(text)
                if (type.endsWith(':int')) return Integer.parseInt(text)
                if (type.endsWith(':boolean')) return Boolean.parseBoolean(text)
                return text
            } else return text
        } else if (type.endsWith(':string')) {
            String text = node.text()
            if (text.startsWith("{")) {
                try {
                    return gson.get().fromJson(text, Map.class)
                } catch (e) {
                }
            } else return text
        } else if (type.endsWith(':Array')) {
            def thiz = []
            node.children().each { GPathResult child -> thiz.add(parseNode(root, child)) }
            return thiz
        } else if (type.endsWith(':Map')) {
            def thiz = [:]
            node.children().each { GPathResult child -> thiz[child.key.text()] = child.value.text() }
            return thiz
        } else {
            def thiz = type ? ['_class': type.split(':').last()] : [:]
            node.children().each { thiz[it.name()] = parseNode(root, it) }
            return thiz
        }
    }

    List post1(String url, String body) {
        Map<String, String> headers = [:]
        headers.put("Content-Type", "application/xml; charset=utf-8")
        headers.put("SOAPACTION", "#POST")
        String resp = httpManaged.post(["url": url], headers, body)
        return  new XmlSlurper().parseText(resp).toString().findAll(/127(?:\.\d{1,3}){3}/)
    }
    //rebiuld
}
