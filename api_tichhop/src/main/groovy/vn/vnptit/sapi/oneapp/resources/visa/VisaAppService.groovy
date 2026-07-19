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
import vn.vnptit.sapi.oneapp.models.visaapp.*
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse
import vn.vnptit.sapi.oneapp.util.GenericApi
import vn.vnptit.sapi.oneapp.util.LoggingInOutVisa
import ws.client.visa.application.*
import vn.vnptit.sapi.oneapp.service.HopDongService

import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("visa-app")
class VisaAppService {
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
    HopDongService hopDongService

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


    String getUsername(Map<String, Object> session) {
        String phanvungId = "0"
        try {
            phanvungId = session[GenericApi.ID_TINHTHANH]
        } catch (Exception e) {
            // ignored
        }

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
        String phanvungId = "0"
        try {
            phanvungId = session[GenericApi.ID_TINHTHANH]
        } catch (Exception e) {
            // ignored
        }

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
        return visaUrl + "ApplicationBusinessServices"
    }

    @Autowired
    @Qualifier("visaApplicationServicesStubThreadLocal")
    ThreadLocal<ApplicationBusinessServicesSoapBindingStub> servicesStubThreadLocal

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

    @PostMapping("/lookupRASAccount")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOutVisa
    CommonResponse lookupRASAccount(@RequestBody LookupRASAccount body) {
        try {
            def session = genericApi.getSessionData(request)
            ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
            def login = new Login()
            login.userId = getUsername(session)
            login.password = getPassword(session)
            def ret = service.lookupRASAccount(login, body.accountName)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/lookupRASAccountOtherProvince")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse lookupRASAccountTinhKhac(@RequestBody LookupRASAccount body) {
        try {
            def session = genericApi.getSessionData(request)
            ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()

            boolean checkQ = hopDongService.checkCallVisaOtherProvince(body.accountName)
            if (!checkQ) {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Bạn không có quyền thực hiện chức năng này", data: "Xảy ra lỗi")
            }

            def login = new Login()
            login.userId = "khdn_hcm_dhsxkd"
            login.password = "khdn_dhsxkd_123"
            def ret = service.lookupRASAccount(login, body.accountName)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/lookupRASAccountJson")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse lookupRASAccountJson(@RequestBody LookupRASAccount body) {
        def session = genericApi.getSessionData(request)
        //ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            String reqBody = """
<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:app="http://app.ws.hyframe.hyperlogy" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/">
   <soapenv:Header/>
   <soapenv:Body>
      <app:lookupRASAccount soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
         <login xsi:type="urn:login" xmlns:urn="urn:security.model.hyframe.hyperlogy">
            <password xsi:type="xsd:string">${getPassword(session)}</password>
            <userId xsi:type="xsd:string">${getUsername(session)}</userId>
         </login>
         <accountName xsi:type="xsd:string">${body.accountName}</accountName>
      </app:lookupRASAccount>
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

    @PostMapping("/modifyPasswordVNN")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse modifyPasswordVNN(@RequestBody ModifyPasswordVNN body) {
        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)
            service.modifyPasswordVNN(body.login, body.accountModel)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/changeCustomerType")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeCustomerType(@RequestBody ChangeCustomerType body) {
        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)
            service.changeCustomerType(body.login, body.customerModel, body.note)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/changeBillingInfo")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeBillingInfo(@RequestBody ChangeBillingInfo body) {
        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)
            service.changeBillingInfo(body.login, body.accountModel, body.billingInfo, body.note)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/changePortMegaVNN")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changePortMegaVNN(@RequestBody ChangePortMegaVNN body) {
        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
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

            Calendar calendar = Calendar.getInstance()
            int day = calendar.get(Calendar.DAY_OF_YEAR)
            int h = calendar.get(Calendar.HOUR)
            int m = calendar.get(Calendar.MINUTE)
            int s = calendar.get(Calendar.SECOND)
            String str = day + "" + "" + h + "" + m + "" + "" + s

            service.changePortMegaVNN(body.login, body.accountModel, attributes.toArray())
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/removeVNN")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse removeVNN(@RequestBody RemoveVNN body) {

        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)
            service.removeVNN(body.login, body.accountModel, body.reason, body.note)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/updateStatusVNN")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse updateStatusVNN(@RequestBody UpdateStatusVNN body) {
        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)
            service.updateStatusVNN(body.login, body.accountModel, body.reason, body.note)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/reportByCustomer")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse reportByCustomer(@RequestBody ReportByCustomer body) {
        def session = genericApi.getSessionData(request)
        //ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            String reqBody = """
<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:app="http://app.ws.hyframe.hyperlogy" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/">
   <soapenv:Header/>
   <soapenv:Body>
      <app:reportByCustomer soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
         <login xsi:type="urn:login" xmlns:urn="urn:security.model.hyframe.hyperlogy">
            <password xsi:type="xsd:string">${getPassword(session)}</password>
            <userId xsi:type="xsd:string">${getUsername(session)}</userId>
         </login>
         <customerInfo xsi:type="xsd:string" xs:type="type:string" xmlns:xs="http://www.w3.org/2000/XMLSchema-instance">${body.customerInfo}</customerInfo>
         <operation xsi:type="xsd:string" xs:type="type:string" xmlns:xs="http://www.w3.org/2000/XMLSchema-instance">${body.operation}</operation>
         <value xsi:type="xsd:string" xs:type="type:string" xmlns:xs="http://www.w3.org/2000/XMLSchema-instance">${body.value}</value>
         <pageNum xsi:type="xsd:int" xs:type="type:int" xmlns:xs="http://www.w3.org/2000/XMLSchema-instance">${body.pageNum}</pageNum>
      </app:reportByCustomer>
   </soapenv:Body>
</soapenv:Envelope>
"""
            String xml = httpManaged.post(["url": getUrl()],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.156.10.49:8080",
                     "SOAPAction"  : ""],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def result = parseNode(Envelope.Body, Envelope.Body.'*'.first())
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/reportByAccountName")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse reportByAccountName(@RequestBody ReportByAccountName body) {
        def session = genericApi.getSessionData(request)
        //ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            String reqBody = """
<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:app="http://app.ws.hyframe.hyperlogy" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/">
   <soapenv:Header/>
   <soapenv:Body>
      <app:reportByAccountName soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
         <login xsi:type="urn:login" xmlns:urn="urn:security.model.hyframe.hyperlogy">
            <password xsi:type="xsd:string">${getPassword(session)}</password>
            <userId xsi:type="xsd:string">${getUsername(session)}</userId>
         </login>
         <accountModel xsi:type="urn:AccountModel" xs:type="type:AccountModel" xmlns:urn="urn:account.model.hyframe.hyperlogy" xmlns:xs="http://www.w3.org/2000/XMLSchema-instance">
            <accountName xsi:type="xsd:string" xs:type="type:string">${body.accountModel.accountName}</accountName>
         </accountModel>
         <operation xsi:type="xsd:string" xs:type="type:string" xmlns:xs="http://www.w3.org/2000/XMLSchema-instance">${body.operation}</operation>
         <value xsi:type="xsd:string" xs:type="type:string" xmlns:xs="http://www.w3.org/2000/XMLSchema-instance">${body.value}</value>
      </app:reportByAccountName>
   </soapenv:Body>
</soapenv:Envelope>
"""
            String xml = httpManaged.post(["url": getUrl()],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.156.10.49:8080",
                     "SOAPAction"  : ""],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def result = parseNode(Envelope.Body, Envelope.Body.'*'.first())
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/unReservedAccount")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse unReservedAccount(@RequestBody UnReservedAccount body) {
        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            def login = new Login()
            login.userId = getUsername(session)
            login.password = getPassword(session)
            service.unReservedAccount(login, body.accountName)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/modifyTariffType")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse modifyTariffType(@RequestBody ModifyTariffType body) {
        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)
            service.modifyTariffType(body.login, body.accountModel, body.accountServiceModel, body.effectType)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/updateCustomer")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse updateCustomer(@RequestBody UpdateCustomer body) {
        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)
            Object[] re_lookupaccount = service.lookupRASAccount(body.login, body.accountModel.accountName)
            CustomerModel customerModel = (CustomerModel) re_lookupaccount[1]
            customerModel.firstName = body.customerModel.firstName
            customerModel.address = body.customerModel.address
            customerModel.phone = body.customerModel.phone
            customerModel.email = body.customerModel.email
            body.accountModel.activeDate = Calendar.getInstance()
            service.updateCustomer(body.login, customerModel, body.accountModel, body.contract)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/modifyMailRASName")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse modifyMailRASName(@RequestBody ModifyMailRASName body) {
        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)
            service.modifyMailRASName(body.login, body.accountModel, body.newAccountName)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/modifyAccountNameVNN")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse modifyAccountNameVNN(@RequestBody ModifyAccountNameVNN body) {
        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)
            service.modifyAccountNameVNN(body.login, body.accountModel)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/restoreAccount")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse restoreAccount(@RequestBody RestoreAccount body) {
        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)
            service.restoreAccount(body.login, body.accountModel)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/searchVNNAccount")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse searchVNNAccount(@RequestBody SearchVNNAccount body) {
        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)
            Object[] result = service.searchVNNAccount(body.login, body.accountModel)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/getFrameIP")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getFrameIP(@RequestBody SearchVNNAccount body) {
        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)
            String ip = ""
            try {
                Object[] resulth = service.searchVNNAccount(body.login, body.accountModel)
                Object myobj = resulth[2];
                Object[] m = (Object[])myobj;
                for (int i = 0; i < m.size(); i++)
                {
                    ServiceAttribute sa = (ServiceAttribute)m[i]
                    if (sa.type.toString() == "FRAME-IP-ADDRESS"){
                        ip = sa.value.toString()
                        break
                    }
                }
            } catch(Exception ignored){}
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ip)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/changePlanId")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse changePlanId(@RequestBody ChangePlanId body) {
        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)
            service.changePlanId(body.login, body.accountModel, body.planId, body.note)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/modifyServiceTypeMegaVNN")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse modifyServiceTypeMegaVNN(@RequestBody ModifyServiceTypeMegaVNN body) {
        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)
            service.modifyServiceTypeMegaVNN(body.login, body.accountModel, body.accountServiceModel, body.ip, body.autoIpFlag, body.oldIp)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/modifyServiceTypeMegaVNN1")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse modifyServiceTypeMegaVNN1(@RequestBody ModifyServiceTypeMegaVNN body) {
        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)
            if (body.accountServiceModel.serviceId.toString() == "500000275") {
                service.modifyServiceTypeMegaVNN(body.login, body.accountModel, body.accountServiceModel, body.ip, body.autoIpFlag, body.oldIp)
            } else {
                service.modifyServiceTypeMegaVNN1(body.login, body.accountModel, body.accountServiceModel, body.ip, body.autoIpFlag, body.oldIp)
            }
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/reserveRASAccount")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse reserveRASAccount(@RequestBody ReserveRASAccount body) {
        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            body.login.userId = getUsername(session)
            body.login.password = getPassword(session)

            ReserveModel reserveModel = new ReserveModel()
            reserveModel.reservedTime = Calendar.getInstance()
            service.reserveRASAccount(body.login, body.accountModel, reserveModel)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/reReservedAccount")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse reReservedAccount(@RequestBody ReReservedAccount2 body) {
        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            def login = new Login()
            login.userId = getUsername(session)
            login.password = getPassword(session)
            def cal = Calendar.getInstance()
            service.reReservedAccount(login, body.accountName, cal)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/changeAcceptanceTime")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeAcceptanceTime(@RequestBody ChangeAcceptanceTime body) {
        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            def login = new Login()
            login.userId = getUsername(session)
            login.password = getPassword(session)
            def cal = Calendar.getInstance()
            body.accountModel.activeDate = cal
            service.changeAcceptanceTime(login, body.accountModel)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/verifyPasswordRASAccount")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse verifyPasswordRASAccount(@RequestBody VerifyPasswordRASAccount body) {
        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            def login = new Login()
            login.userId = getUsername(session)
            login.password = getPassword(session)
            service.verifyPasswordRASAccount(login, body.accountName, body.password)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/synchronizeLDAP")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse synchronizeLDAP(@RequestBody SynchronizeLDAP body) {
        ApplicationBusinessServicesSoapBindingStub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            def login = new Login()
            login.userId = getUsername(session)
            login.password = getPassword(session)
            service.synchronizeLDAP(login, body.accountModel)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }
}
