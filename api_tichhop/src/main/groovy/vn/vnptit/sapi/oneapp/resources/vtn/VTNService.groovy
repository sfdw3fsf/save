package vn.vnptit.sapi.oneapp.resources.vtn


import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
import vn.vnptit.sapi.oneapp.models.vtn.*
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse
import vn.vnptit.sapi.oneapp.util.GenericApi
import ws.client.subman.SubInfo
import ws.client.vtn.SubscriberObject
import ws.client.vtn.VTNAPIPortBindingStub
import ws.client.vtn.AccountObject

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("vtn")
class VTNService {
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

    @Value("\${spring.httpservices.vtn.accounts}")
    String vtnAccounts

    static Map<String, Map<String, String>> cachedAcc = null

    void loadAccountsMap() {
        cachedAcc = [:]
        vtnAccounts.split(',').each {
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
            synchronized (vtnAccounts) {
                if (cachedAcc == null) loadAccountsMap()
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
            synchronized (vtnAccounts) {
                if (cachedAcc == null) loadAccountsMap()
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
    @Qualifier("vtnServicesStubThreadLocal")
    ThreadLocal<VTNAPIPortBindingStub> vtnAPIPortBindingStub

    @PostMapping("/disconnectSubscriber")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse disconnectSubscriber(@Valid @RequestBody DisconnectSubscriber body) {
        VTNAPIPortBindingStub service = vtnAPIPortBindingStub.get()
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            service.disconnectSubscriber(body.subscriberObject, body.strReasonId, username, password)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/queryAccount")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse queryAccount(@Valid @RequestBody QueryAccount body) {
        VTNAPIPortBindingStub service = vtnAPIPortBindingStub.get()
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            AccountObject data = service.queryAccount(body.account_code, body.account_no, username, password)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/createAccount")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse createAccount(@Valid @RequestBody AccountObject body) {
        VTNAPIPortBindingStub service = vtnAPIPortBindingStub.get()
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            String data = service.createAccount(body, username, password)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/updateAccount")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse updateAccount(@Valid @RequestBody UpdateAccount body) {
        VTNAPIPortBindingStub service = vtnAPIPortBindingStub.get()
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            service.updateAccount(body.accountObject, body.strReasonId, username, password)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/transferSubscriber")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse transferSubscriber(@Valid @RequestBody TransferSubscriber body) {
        VTNAPIPortBindingStub service = vtnAPIPortBindingStub.get()
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            service.transferSubscriber(body.subscriberObject, body.accountObject, body.strReasonId, username, password)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/querySubscriber")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse querySubscriber(@Valid @RequestBody QuerySubscriber body) {
        VTNAPIPortBindingStub service = vtnAPIPortBindingStub.get()
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            SubscriberObject[] data = service.querySubscriber(body.external_id, username, password)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/createSubscriber")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse createSubscriber(@Valid @RequestBody UpdateSubscriber body) {
        VTNAPIPortBindingStub service = vtnAPIPortBindingStub.get()
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            String data = service.createSubscriber(body.subscriberObject, body.strReasonId, username, password)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/updateSubscriber")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse updateSubscriber(@Valid @RequestBody UpdateSubscriber body) {
        VTNAPIPortBindingStub service = vtnAPIPortBindingStub.get()
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            service.updateSubscriber(body.subscriberObject, body.strReasonId, username, password)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/setPasswordForRoot")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse setPasswordForRoot(@Valid @RequestBody SetPasswordForRoot body) {
        VTNAPIPortBindingStub service = vtnAPIPortBindingStub.get()
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            String data = service.setPasswordForRoot(body.external_id, body.subscirber_pass, username, password)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/getPassword")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse getPassword(@Valid @RequestBody GetPassword body) {
        VTNAPIPortBindingStub service = vtnAPIPortBindingStub.get()
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            String data = service.getPassword(body.external_id, username, password)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/changeServiceActStatus")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeServiceActStatus(@Valid @RequestBody ChangeServiceActStatus body) {
        VTNAPIPortBindingStub service = vtnAPIPortBindingStub.get()
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            service.changeServiceActStatus(body.subscriberObject, body.strBareUnbare, body.strBareType, body.strBareReasonType, 
                                                                body.strReasonId, username, password)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/registerSupplementOffer")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse registerSupplementOffer(@Valid @RequestBody SupplementOffer body) {
        VTNAPIPortBindingStub service = vtnAPIPortBindingStub.get()
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            service.registerSupplementOffer(body.subscriberObject, body.supplementOffer, body.strReasonId, username, password)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/removeSupplementOffer")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse removeSupplementOffer(@Valid @RequestBody SupplementOffer body) {
        VTNAPIPortBindingStub service = vtnAPIPortBindingStub.get()
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            service.removeSupplementOffer(body.subscriberObject, body.supplementOffer, body.strReasonId, username, password)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

}
