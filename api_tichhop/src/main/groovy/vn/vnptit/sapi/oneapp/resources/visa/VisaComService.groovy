package vn.vnptit.sapi.oneapp.resources.visa

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
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse
import vn.vnptit.sapi.oneapp.util.GenericApi
import ws.client.visa.common.*

import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("visa-com")
class VisaComService {
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

    @Value("\${spring.httpservices.visa.chuyentinh.username}")
    String visaChuyenTinhUsername

    @Value("\${spring.httpservices.visa.chuyentinh.password}")
    String visaChuyenTinhPassword

    static Map<String, Map<String, String>> cachedAcc = null

    void loadVisaAccountsMap() {
        cachedAcc = [:]
        visaAccounts.split(',').each {
            String[] accRec = it.split(":", 3)
            cachedAcc.put(accRec[0], ["username": accRec[1], "password": accRec[2]])
        }
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

    String getUrl(){
        return visaUrl + "CommonBusinessServices"
    }

    @Autowired
    @Qualifier("visaCommonServicesStubThreadLocal")
    ThreadLocal<CommonBusinessServicesSoapBindingStub> servicesStubThreadLocal

    @PostMapping("/changeMacAddress")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse changeMacAddress(@RequestBody ChangeMacAddress body) {
        CommonBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            def login = new Login()
            login.userId = getUsername(session)
            login.password = getPassword(session)
            service.changeMacAddress(login, body.accountName, body.macAddress, body.note)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/changePartner")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse changePartner(@RequestBody ChangePartner body) {
        CommonBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)
            service.changePartner(body.login, body.accountName, body.partnerCode, body.note)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/modifyServiceTypeMegaVNN1")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse modifyServiceTypeMegaVNN1(@RequestBody ModifyServiceTypeMegaVNN1 body) {
        CommonBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)
            service.modifyServiceTypeMegaVNN1(body.login, body.accountModel, body.accountServiceModel, body.ip, body.autoIpFlag, body.oldIp, body.ipv6, body.autoIpV6Flag)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/checkDoFix")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse checkDoFix(@RequestBody CheckDoFix body) {
        CommonBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)
            service.checkDoFix(body.login, body.accountModel)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/registerMegaVNN")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse registerMegaVNN(@RequestBody RegisterMegaVNN body) {
        CommonBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)
            List attributes = []
            if (body.attributes != null && body.attributes.size() > 0){
                body.attributes.each {
                    Properties prop = new Properties()
                    prop.put("type", it.type)
                    prop.put("value", it.value ?: "")
                    attributes.push(prop)
                }
            }
            service.registerMegaVNN(body.login, body.customerModel, body.accountModel, body.accountServiceModel, body.childServices, attributes.toArray())
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            // return new CommonResponse(errorCode: 1, faultCode: body.login.userId + "|" + body.login.password, faultString: "", data: "Lỗi")
           return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/modifyServiceIPAccessMegaIPv6")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse modifyServiceIPAccessMegaIPv6(@RequestBody ModifyServiceIPAccessMegaIPv6 body) {
        CommonBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)

            service.modifyServiceIPAccessMegaIPv6(body.login, body.accountModel, body.ip, body.ipv6Lan, body.ipv4Wan, body.ipv6Wan)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/changeCGNAT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse changeCGNAT(@RequestBody ChangeCGNAT body) {
        CommonBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            def login = new Login()
            login.userId = getUsername(session)
            login.password = getPassword(session)

            service.changeCGNAT(login, body.accountName, body.status)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/checkGreenNet")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse checkGreenNet(@RequestBody CheckGreenNet body) {
        CommonBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            def login = new Login()
            login.userId = getUsername(session)
            login.password = getPassword(session)

            service.checkGreenNet(login, body.serviceInfoId)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/changeGreenNet")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse changeGreenNet(@RequestBody ChangeGreenNet body) {
        CommonBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            def login = new Login()
            login.userId = getUsername(session)
            login.password = getPassword(session)

            service.changeGreenNet(login, body.accountName, body.status)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/lookupRASAccountNoLog")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse lookupRASAccountNoLog(@RequestBody LookupRASAccountNoLog body) {
        def session = genericApi.getSessionData(request)
        try {
            String reqBody = """
                    <soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:app="http://app.ws.hyframe.hyperlogy" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/">
                       <soapenv:Header/>
                       <soapenv:Body>
                          <app:lookupRASAccountNoLog soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                             <login xsi:type="urn:login" xmlns:urn="urn:security.model.hyframe.hyperlogy">
                                <password xsi:type="xsd:string">${getPassword(session)}</password>
                                <userId xsi:type="xsd:string">${getUsername(session)}</userId>
                             </login>
                             <accountName xsi:type="xsd:string">${body.accountName}</accountName>
                          </app:lookupRASAccountNoLog>
                       </soapenv:Body>
                    </soapenv:Envelope>
                    """
            String xml = httpManaged.post(["url": getUrl()],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.156.10.49:8080",
                     "SOAPAction"  : ""],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def faultcode = Envelope.Body.Fault?.faultcode?.text() ?: ""
            def faultstring = Envelope.Body.Fault?.faultstring?.text() ?: ""

            if (faultcode != "" || faultstring != "") {
                return new CommonResponse(errorCode: 1, faultCode: faultcode, faultString: faultstring, data: null)
            } else {
                def result = parseNode(Envelope.Body, Envelope.Body.'*'.first())
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/setLoginLimit")
    CommonResponse setLoginLimit(@RequestBody SetLoginLimit body) {
        def session = genericApi.getSessionData(request)
        try {
            String reqBody = """<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:app="http://app.ws.hyframe.hyperlogy" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/"><soapenv:Header/><soapenv:Body><app:changeLoginLimit soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"><login xsi:type="urn:login" xmlns:urn="urn:security.model.hyframe.hyperlogy"><password xsi:type="xsd:string"><![CDATA[${getPassword(session)}]]></password><userId xsi:type="xsd:string">${getUsername(session)}</userId></login><accountName xsi:type="xsd:string">${body.accountName}</accountName><loginLimit xsi:type="xsd:int">${body.loginLimit}</loginLimit></app:changeLoginLimit></soapenv:Body></soapenv:Envelope>"""
            String xml = httpManaged.post(["url": getUrl()],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "SOAPAction"  : ""],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def faultcode = Envelope.Body.Fault?.faultcode?.text() ?: ""
            def faultstring = Envelope.Body.Fault?.faultstring?.text() ?: ""

            if (faultcode && faultcode.contains("error.package.customer.not.change")) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
            }

            if (faultcode != "" || faultstring != "") {
                return new CommonResponse(errorCode: 1, faultCode: faultcode, faultString: faultstring, data: null)
            }
                
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            if (e.message.contains("Không có sự thay đổi")) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
            }
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

    @PostMapping("/changeDonViTB")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse changeDonViTB(@RequestBody Map body) {
        try {
            def session = genericApi.getSessionData(request)
            String reqBody = """<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:app="http://app.ws.hyframe.hyperlogy" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/">
                                    <soapenv:Header/>
                                    <soapenv:Body>
                                        <app:changeDonViTB soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                                            <login xsi:type="urn:login" xmlns:urn="urn:security.model.hyframe.hyperlogy">
                                                <password xsi:type="xsd:string">$visaChuyenTinhPassword</password>
                                                <userId xsi:type="xsd:string">$visaChuyenTinhUsername</userId>
                                            </login>
                                            <accountName xsi:type="xsd:string">${body.accountName}</accountName>
                                            <matinhmoi xsi:type="xsd:string">${body.matinhmoi}</matinhmoi>
                                        </app:changeDonViTB>
                                    </soapenv:Body>
                                </soapenv:Envelope>"""

            Map<String, String> headers = [:]
            headers.put("Content-Type", "text/xml; charset=UTF-8")
            headers.put("SOAPAction", "")
            String resp = httpManaged.post(["url": getUrl()], headers, reqBody)
            def env = new XmlSlurper().parseText(resp)

            def faultcode = env.Body.Fault?.faultcode?.text() ?: ""
            def faultstring = env.Body.Fault?.faultstring?.text() ?: ""
            if (faultcode != "" || faultstring != "") {
                return new CommonResponse(errorCode: 1, faultCode: faultcode, faultString: faultstring, data: null)
            } else {
                def result = parseNode(env.Body, env.Body.'*'.first())
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }
}
