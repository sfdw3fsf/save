package vn.vnptit.sapi.oneapp.resources.vasc

import client.ws.vasc.*
import groovy.json.JsonSlurper
import groovy.util.slurpersupport.GPathResult
import org.apache.axis.message.MessageElement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import org.tempuri.GetUserParent
import org.tempuri.SubscriberManagementStub
import org.w3c.dom.Document
import org.w3c.dom.Element
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.vasc.ErrorSolutionInput
import vn.vnptit.sapi.oneapp.models.vasc.GetSubscriberInfoBC
import vn.vnptit.sapi.oneapp.models.vasc.ResetPasswordLockChildrenInput
import vn.vnptit.sapi.oneapp.models.vasc.UnbindKplusInput
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse
import vn.vnptit.sapi.oneapp.util.GenericApi

import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

@RestController
@RequestMapping("vasc")
class VascService {

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

    @Value("\${spring.httpservices.vasc.url}")
    String url

    @Value("\${spring.httpservices.vasc.accounts}")
    String accounts

    static Map<String, Map<String, String>> cachedAcc = null

    void loadVisaAccountsMap() {
        cachedAcc = [:]
        accounts.split(',').each {
            String[] accRec = it.split(":", 3)
            cachedAcc.put(accRec[0], ["username": accRec[1], "password": accRec[2]])
        }
    }


    String getUsername(Map<String, Object> session) {
        String phanvungId = "0"
        try {
            phanvungId = session[GenericApi.ID_TINHTHANH]
        } catch (Exception ignored) {
            // ignored
        }

        if (cachedAcc == null) {
            synchronized (accounts) {
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
        } catch (Exception ignored) {
            // ignored
        }

        if (cachedAcc == null) {
            synchronized (accounts) {
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


    static ThreadLocal<SubscriberManagementStub> servicesStubThreadLocal = ThreadLocal.withInitial { new SubscriberManagementStub() }

    @Autowired
    VASCStubFactory vascStubFactory

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

    @PostMapping("/getPackageName")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getPackageName(@RequestBody GetPackageName body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.getPackageName(body.getIPTVAccount())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changeDistrictSubscriber")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeDistrictSubscriber(@RequestBody ChangeDistrictSubscriber body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changeDistrictSubscriber(body.getIPTVACCOUNT(), body.getDistrictNo())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changeStatusHotel")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeStatusHotel(@RequestBody ChangeStatusHotel body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changeStatusHotel(body.getStrUsername(), body.getStrStatus())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/registerSubscriberV2_1KGG")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse registerSubscriberV2_1KGG(@RequestBody RegisterSubscriberV2_1KGG body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.registerSubscriberV2_1KGG(body.getName(), body.getBirthday(), body.getSex(), body.getFirstAdd(), body.getSecondAdd(), body.getIDNo(), body.getTelNo(), body.getCellNo(), body.getAreaCode(), body.getDistrictID(), body.getEmail(), body.getYM(), body.getWebsite(), body.getFaxNo(), body.getContractCode(), body.getIPTVAccount(), body.getIPTVPW(), body.getPackageCode(), body.getServiceCode(), body.getServiceVASCode(), body.getSTBID(), body.getStatus(), body.getCateCode(), body.getFixDate(), body.getNotFixReason(), body.getContractSignDate(), body.getPayment(), body.getNo_Month(), body.getLoginAccount(), body.getLoginAcount_Password(), body.getBillAccount(), body.getMegaMyTV(), body.getSPortDSL(), body.getSPromo_id(), body.getSID_STB_Discount(), body.getSNDTV_CD(), body.getSMultiCD())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/b2B_Room_RegistAddOn")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse b2B_Room_RegistAddOn(@RequestBody B2B_Room_RegistAddOn body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.b2B_Room_RegistAddOn(body.getRAddOnDTO())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changeID_STBDiscount")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeID_STBDiscount(@RequestBody ChangeID_STBDiscount body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changeID_STBDiscount(body.getIPTVACCOUNT(), body.getSID_STB_Discount())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/cust_UserMulti_UpdateInfo")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse cust_UserMulti_UpdateInfo(@RequestBody Cust_UserMulti_UpdateInfo body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.cust_UserMulti_UpdateInfo(body.getCustDTO())
            if (obj.result == 1) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/suspendSubscriberV2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse suspendSubscriberV2(@RequestBody SuspendSubscriberV2 body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.suspendSubscriberV2(body.getIPTVAccount(), body.reasonID)
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/sTB_Rent_UpdateInfo")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse sTB_Rent_UpdateInfo(@RequestBody org.tempuri.STB_Rent_UpdateInfo body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        def service = servicesStubThreadLocal.get()
        try {
            def obj = service.sTB_Rent_UpdateInfo(body, vascStubFactory.getAuthHeader(phanvungId))
            def res = obj.getSTB_Rent_UpdateInfoResult()
            if (res.result == 1) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: res.result, faultCode: "", faultString: res.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/getListObjPromoCD")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getListObjPromoCD() {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.getListObjPromoCD()
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/b2B_Global_AddAddOn")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse b2B_Global_AddAddOn(@RequestBody B2B_Global_AddAddOn body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.b2B_Global_AddAddOn(body.packDTO)
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/registerSubscriberFull")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse registerSubscriberFull(@RequestBody RegisterSubscriberFull body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.registerSubscriberFull(body.getName(), body.getBirthday(), body.getSex(), body.getFirstAdd(), body.getSecondAdd(), body.getIDNo(), body.getTelNo(), body.getCellNo(), body.getAreaCode(), body.getDistrictID(), body.getEmail(), body.getYM(), body.getWebsite(), body.getFaxNo(), body.getContractCode(), body.getIPTVAccount(), body.getIPTVPW(), body.getPackageCode(), body.getServiceCode(), body.getServiceVASCode(), body.getSTBID(), body.getStatus(), body.getCateCode(), body.getFixDate(), body.getNotFixReason(), body.getContractSignDate(), body.getPayment(), body.getNo_Month(), body.getLoginAccount(), body.getLoginAcount_Password(), body.getBillAccount(), body.getMegaMyTV())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/getSubscriberInfoV3_List")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getSubscriberInfoV3_List(@RequestBody GetSubscriberInfoV3_List body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.getSubscriberInfoV3_List(body.getUsernames())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/smart_OTTGetMemberPending")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse smart_OTTGetMemberPending(@RequestBody Smart_OTTGetMemberPending body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.smart_OTTGetMemberPending(body.getReq())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/b2B_Global_AddPackage")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse b2B_Global_AddPackage(@RequestBody B2B_Global_AddPackage body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.b2B_Global_AddPackage(body.packDTO)
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changeUserPassword")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeUserPassword(@RequestBody ChangeUserPassword body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changeUserPassword(body.getStrUserName(), body.getStrOldPassword(), body.getStrNewPassword())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/getListSubscriber")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getListSubscriber() {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.getListSubscriber()
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/payTVFull_Register")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse payTVFull_Register(@RequestBody PayTVFull_Register body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.payTVFull_Register(body.getMyTVAccount(), body.getMonth())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/suspendSubscriber")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse suspendSubscriber(@RequestBody SuspendSubscriber body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.suspendSubscriber(body.getIPTVAccount(), body.getReason(), body.getOperator())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changePackageAndMegaVNNV3")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changePackageAndMegaVNNV3(@RequestBody ChangePackageAndMegaVNN_TitanSportHome body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changePackageAndMegaVNNV3(body.getObjRequest())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/registerSubscriberPay")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse registerSubscriberPay(@RequestBody RegisterSubscriberPay body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.registerSubscriberPay(body.getName(), body.getBirthday(), body.getSex(), body.getFirstAdd(), body.getSecondAdd(), body.getIDNo(), body.getTelNo(), body.getCellNo(), body.getAreaCode(), body.getEmail(), body.getYM(), body.getWebsite(), body.getFaxNo(), body.getContractCode(), body.getIPTVAccount(), body.getIPTVPW(), body.getPackageCode(), body.getServiceCode(), body.getServiceVASCode(), body.getSTBID(), body.getStatus(), body.getCateCode(), body.getFixDate(), body.getNotFixReason(), body.getContractSignDate(), body.getPayment(), body.getNo_Month(), body.getLoginAccount(), body.getLoginAcount_Password(), body.getBillAccount())
            if (obj.result == 1) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/registerSubscriberNew")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse registerSubscriberNew(@RequestBody RegisterSubscriberNew body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.registerSubscriberNew(body.getName(), body.getBirthday(), body.getSex(), body.getFirstAdd(), body.getSecondAdd(), body.getIDNo(), body.getTelNo(), body.getCellNo(), body.getAreaCode(), body.getDistrictID(), body.getEmail(), body.getYM(), body.getWebsite(), body.getFaxNo(), body.getContractCode(), body.getIPTVAccount(), body.getIPTVPW(), body.getPackageCode(), body.getServiceCode(), body.getServiceVASCode(), body.getSTBID(), body.getStatus(), body.getCateCode(), body.getFixDate(), body.getNotFixReason(), body.getContractSignDate(), body.getLoginAccount(), body.getLoginAcount_Password(), body.getBillAccount())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/b2B_Room_CancelAddOn")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse b2B_Room_CancelAddOn(@RequestBody B2B_Room_CancelAddOn body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.b2B_Room_CancelAddOn(body.getRAddOnDTO())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/updateUserConference")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse updateUserConference(@RequestBody UpdateUserConference body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.updateUserConference(body.getIPTVAccount(), body.getOperator())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/getStatusBlockPayTV")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getStatusBlockPayTV(@RequestBody GetStatusBlockPayTV body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.getStatusBlockPayTV(body.getIptvaccount())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/cancelSubscriberV2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse cancelSubscriberV2(@RequestBody CancelSubscriberV2 body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.cancelSubscriberV2(body.getIPTVAccount(), body.getReasonID())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/sTB_PROM_Register")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse sTB_PROM_Register(@RequestBody org.tempuri.STB_PROM_Register body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        def service = servicesStubThreadLocal.get()

        try {
            def obj = service.sTB_PROM_Register(body, vascStubFactory.getAuthHeader(phanvungId))
            def res = obj.getSTB_PROM_RegisterResult()
            if (res.result == 1) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: res.result, faultCode: "", faultString: res.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/registerSubscriberHST")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse registerSubscriberHST(@RequestBody RegisterSubscriberHST body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.registerSubscriberHST(body.getHstDTO())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/sTB_PROM_Summary3855")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse sTB_PROM_Summary3855(@RequestBody org.tempuri.STB_PROM_Summary3855 body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        def service = servicesStubThreadLocal.get()

        try {
            def obj = service.sTB_PROM_Summary3855(body, vascStubFactory.getAuthHeader(phanvungId))
            def res = obj.getSTB_PROM_Summary3855Result()
            if (res.result == 1) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: res.result, faultCode: "", faultString: res.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/registerSubscriberV2_1HNI")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse registerSubscriberV2_1HNI(@RequestBody RegisterSubscriberV2_1HNI body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.registerSubscriberV2_1HNI(body.getName(), body.getBirthday(), body.getSex(), body.getFirstAdd(), body.getSecondAdd(), body.getIDNo(), body.getTelNo(), body.getCellNo(), body.getAreaCode(), body.getDistrictID(), body.getEmail(), body.getYM(), body.getWebsite(), body.getFaxNo(), body.getContractCode(), body.getIPTVAccount(), body.getIPTVPW(), body.getPackageCode(), body.getServiceCode(), body.getServiceVASCode(), body.getSTBID(), body.getStatus(), body.getCateCode(), body.getFixDate(), body.getNotFixReason(), body.getContractSignDate(), body.getPayment(), body.getNo_Month(), body.getLoginAccount(), body.getLoginAcount_Password(), body.getBillAccount(), body.getMegaMyTV(), body.getSPortDSL(), body.getSPromo_id(), body.getSID_STB_Discount(), body.getSNDTV_CD(), body.getSFiberB2BCD())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/registerSubscriberV2HNI")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse registerSubscriberV2HNI(@RequestBody RegisterSubscriberV2HNI body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.registerSubscriberV2HNI(body.getName(), body.getBirthday(), body.getSex(), body.getFirstAdd(), body.getSecondAdd(), body.getIDNo(), body.getTelNo(), body.getCellNo(), body.getAreaCode(), body.getDistrictID(), body.getEmail(), body.getYM(), body.getWebsite(), body.getFaxNo(), body.getContractCode(), body.getIPTVAccount(), body.getIPTVPW(), body.getPackageCode(), body.getServiceCode(), body.getServiceVASCode(), body.getSTBID(), body.getStatus(), body.getCateCode(), body.getFixDate(), body.getNotFixReason(), body.getContractSignDate(), body.getPayment(), body.getNo_Month(), body.getLoginAccount(), body.getLoginAcount_Password(), body.getBillAccount(), body.getMegaMyTV(), body.getSPortDSL(), body.getSPromo_id(), body.getSID_STB_Discount())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/getSubscriberInfoV3")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getSubscriberInfoV3(@RequestBody GetSubscriberInfoV3 body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.getSubscriberInfoV3(body.username)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changePackageAndMegaVNN")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changePackageAndMegaVNN(@RequestBody ChangePackageAndMegaVNN body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changePackageAndMegaVNN(body.getIPTVAccount(), body.getPackageNo(), body.getServiceCD(), body.getServiceVAS(), body.getReason(), body.getMegaMyTV())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/getSubscriberInfoV2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getSubscriberInfoV2(@RequestBody GetSubscriberInfoV2 body) {
        try {
            def session = genericApi.getSessionData(request)
            String phanvungId = session[GenericApi.ID_TINHTHANH]
            SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

            GetSubscriberRequest obj = new GetSubscriberRequest()
            obj.setInforDevice(body.objRequest.inforDevice)
            obj.setIPTVAccount(body.objRequest.IPTVAccount)
            def vo = service.getSubscriberInfoV2(obj)
            List<Object> data = processMessageElement(vo.data._any)
            //GetSubscriberInfoV2Response obj = service.getSubscriberInfoV2(body)
            return new CommonResponse(errorCode: vo.result, faultCode: "", faultString: vo.errordesc, data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/getHisSubscriberChangeSTB")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getHisSubscriberChangeSTB(@RequestBody GetHisSubscriberChangeSTB body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.getHisSubscriberChangeSTB(body.getStrMyTV())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/cancelMegaForSuspendAccount")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse cancelMegaForSuspendAccount(@RequestBody CancelMegaForSuspendAccount body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.cancelMegaForSuspendAccount(body.getUsername())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @FakeSuccessCommonResponse
    @PostMapping("/getSubscriberInfo")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getSubscriberInfo(@RequestBody GetSubscriberInfo body) {
        try {
            def session = genericApi.getSessionData(request)
            String phanvungId = session[GenericApi.ID_TINHTHANH]
            SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

            def obj = service.getSubscriberInfo(body.getIPTVAccount())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/getSubscriberInfoBC")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse getSubscriberInfoBC(@RequestBody GetSubscriberInfoBC body) {
        try {
            def session = genericApi.getSessionData(request)
            String phanvungId = session[GenericApi.ID_TINHTHANH]

            if (body.tinhthicong_id != null && !body.tinhthicong_id.toString().isEmpty()) {
                phanvungId = body.tinhthicong_id
            }

            SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

            def obj = service.getSubscriberInfo(body.iptvaccount)
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/registerKplusPromotion2021")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse registerKplusPromotion2021(@RequestBody RegisterKplusPromotion2021 body) {
        try {
            def session = genericApi.getSessionData(request)
            String phanvungId = session[GenericApi.ID_TINHTHANH]
            SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()
            def obj = service.registerKplusPromotion2021(body.getUsername(), body.getMobile(), body.getArea_code(), body.getPack_cd(), body.getPack_name(), body.isRenew())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/b2B_Hotel_AddPackage")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse b2B_Hotel_AddPackage(@RequestBody B2B_Hotel_AddPackage body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.b2B_Hotel_AddPackage(body.getPackDTO())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/cust_Trial_UpdateInfo")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse cust_Trial_UpdateInfo(@RequestBody Cust_Trial_UpdateInfo body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.cust_Trial_UpdateInfo(body.getCustDTO())
            if (obj.result == 1 || obj.result == -7) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/getSubscriberInfoV3_String")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getSubscriberInfoV3_String(@RequestBody GetSubscriberInfoV3_String body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.getSubscriberInfoV3_String(body.getUsernames())
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/gponCab_RegistWithoutMyTV")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse gponCab_RegistWithoutMyTV(@RequestBody GponCab_RegistWithoutMyTV body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.gponCab_RegistWithoutMyTV(body.getObjRequest())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/registerSubscriberFull_Update")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse registerSubscriberFull_Update(@RequestBody RegisterSubscriberFull_Update body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.registerSubscriberFull_Update(body.getName(), body.getBirthday(), body.getSex(), body.getFirstAdd(), body.getSecondAdd(), body.getIDNo(), body.getTelNo(), body.getCellNo(), body.getAreaCode(), body.getDistrictID(), body.getEmail(), body.getYM(), body.getWebsite(), body.getFaxNo(), body.getContractCode(), body.getIPTVAccount(), body.getIPTVPW(), body.getPackageCode(), body.getServiceCode(), body.getServiceVASCode(), body.getSTBID(), body.getStatus(), body.getCateCode(), body.getFixDate(), body.getNotFixReason(), body.getContractSignDate(), body.getPayment(), body.getNo_Month(), body.getLoginAccount(), body.getLoginAcount_Password(), body.getBillAccount(), body.getMegaMyTV(), body.getSPortDSL())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changePromotionsV2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changePromotionsV2(@RequestBody ChangePromotionsV2 body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changePromotionsV2(body.getIPTVACCOUNT(), body.getSPromo_ID(), body.getOption())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/registerSubscriber")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse registerSubscriber(@RequestBody RegisterSubscriber body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.registerSubscriber(body.getName(), body.getBirthday(), body.getSex(), body.getFirstAdd(), body.getSecondAdd(), body.getIDNo(), body.getTelNo(), body.getCellNo(), body.getAreaCode(), body.getEmail(), body.getYM(), body.getWebsite(), body.getFaxNo(), body.getContractCode(), body.getIPTVAccount(), body.getIPTVPW(), body.getPackageCode(), body.getServiceCode(), body.getServiceVASCode(), body.getSTBID(), body.getStatus(), body.getCateCode(), body.getFixDate(), body.getNotFixReason(), body.getContractSignDate(), body.getLoginAccount(), body.getLoginAcount_Password(), body.getBillAccount())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/getSubscriberBySTB_SN")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getSubscriberBySTB_SN(@RequestBody GetSubscriberBySTB_SN body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.getSubscriberBySTB_SN(body.getSerialNumber())
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/registerSubscriberV2_1")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse registerSubscriberV2_1(@RequestBody RegisterSubscriberV2_1 body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.registerSubscriberV2_1(body.getName(), body.getBirthday(), body.getSex(), body.getFirstAdd(), body.getSecondAdd(), body.getIDNo(), body.getTelNo(), body.getCellNo(), body.getAreaCode(), body.getDistrictID(), body.getEmail(), body.getYM(), body.getWebsite(), body.getFaxNo(), body.getContractCode(), body.getIPTVAccount(), body.getIPTVPW(), body.getPackageCode(), body.getServiceCode(), body.getServiceVASCode(), body.getSTBID(), body.getStatus(), body.getCateCode(), body.getFixDate(), body.getNotFixReason(), body.getContractSignDate(), body.getPayment(), body.getNo_Month(), body.getLoginAccount(), body.getLoginAcount_Password(), body.getBillAccount(), body.getMegaMyTV(), body.getSPortDSL(), body.getSPromo_id(), body.getSNDTV_CD())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/b2B_EditHotel")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse b2B_EditHotel(@RequestBody B2B_EditHotel body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.b2B_EditHotel(body.hotelDTO)
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changeSTB")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeSTB(@RequestBody ChangeSTB body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changeSTB(body.getIPTVAccount(), body.getReason())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/sTB_PROM_Summary")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse sTB_PROM_Summary(@RequestBody org.tempuri.STB_PROM_Summary body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        def service = servicesStubThreadLocal.get()

        try {
            def obj = service.sTB_PROM_Summary(body, vascStubFactory.getAuthHeader(phanvungId)).getSTB_PROM_SummaryResult()
            if (obj.result == 1) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/totalMyTVPlus")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse totalMyTVPlus(@RequestBody TotalMyTVPlus body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.totalMyTVPlus(body.getObjRequest())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/blockPayTV")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse blockPayTV(@RequestBody BlockPayTV body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.blockPayTV(body.getObjRequest())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errorDesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/b2B_DeleteHotel")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse b2B_DeleteHotel(@RequestBody B2B_DeleteHotel body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.b2B_DeleteHotel(body.getCode())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/deleteDistrict")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse deleteDistrict(@RequestBody DeleteDistrict body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.deleteDistrict(body.districtID)
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/getPackageEPG")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getPackageEPG(@RequestBody GetPackageEPG body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.getPackageEPG(body.getStarttime(), body.getEndtime(), body.getIPTVAccount(), body.getStrAll())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/registAddOn")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse registAddOn(@RequestBody RegistAddOn body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.registAddOn(body.addonDTO)
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changePassword")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changePassword(@RequestBody ChangePassword body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changePassword(body.getIPTVAccount(), body.getOldPassword(), body.getNewPassword())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/registKplus")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse registKplus(@RequestBody RegistKplus body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            //body.kplusDTO.SUBPACK = "KP01"
            def obj = service.registKplus(body.kplusDTO)
            if (obj.result == 1) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/resumeSubscriber")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse resumeSubscriber(@RequestBody ResumeSubscriber body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.resumeSubscriber(body.getIPTVAccount(), body.getReason())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/cancelSubscriber")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse cancelSubscriber(@RequestBody CancelSubscriber body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.cancelSubscriber(body.getIPTVAccount(), body.getReason())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/updateKplusInfo")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse updateKplusInfo(@RequestBody UpdateKplusInfo body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.updateKplusInfo(body.getMyTVAccount(), body.getStrOperator())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/updateDeviceInfo")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse updateDeviceInfo(@RequestBody UpdateDeviceInfo body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.updateDeviceInfo(body.getMyTVAccount(), body.getStrDeviceName(), body.getStrPortID())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changePackageV3")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changePackageV3(@RequestBody ChangePackageV3 body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changePackageV3(body.getObjRequest())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/editHotel")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse editHotel(@RequestBody EditHotel body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.editHotel(body.getStrUsername(), body.getStrName(), body.getStrOwner(), body.getStrCard(), body.getStrAddress(), body.getStrSigndate(), body.getStrContractID(), body.getStrBillDate(), body.getStrPayMode(), body.getStrPackageID(), body.getIAccNumber())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changeSubscriber")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeSubscriber(@RequestBody ChangeSubscriber body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changeSubscriber(body.getName(), body.getBirthday(), body.getSex(), body.getFirstAdd(), body.getSecondAdd(), body.getIDNo(), body.getTelNo(), body.getCellNo(), body.getAreaCode(), body.getEmail(), body.getYM(), body.getWebsite(), body.getFaxNo(), body.getIPTVAccount())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/updateSubTrial")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse updateSubTrial(@RequestBody UpdateSubTrial body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.updateSubTrial(body.getObjRequest())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changePromotions")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changePromotions(@RequestBody ChangePromotions body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changePromotions(body.getIPTVACCOUNT(), body.getSPromo_ID())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changeContract")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeContract(@RequestBody ChangeContract body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changeContract(body.getIPTVACCOUNT(), body.getContractNo())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/addHotel")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse addHotel(@RequestBody AddHotel body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.addHotel(body.getStrUserName(), body.getStrPassword(), body.getStrName(), body.getStrOwner(), body.getStrCard(), body.getStrAddress(), body.getStrSigndate(), body.getStrContractID(), body.getStrBillDate(), body.getStrMethod(), body.getStrPayMode(), body.getStrPackageID(), body.getStrProviceID(), body.getIAccNumber())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/updatePromotions")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse updatePromotions(@RequestBody UpdatePromotions body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.updatePromotions(body.getIPTVACCOUNT(), body.getPromoCD())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changePackageHST")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changePackageHST(@RequestBody ChangePackageHST body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changePackageHST(body.getHstDTO())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changePackage")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changePackage(@RequestBody ChangePackage body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changePackage(body.getIPTVAccount(), body.getPackageNo(), body.getServiceCD(), body.getServiceVAS(), body.getReason())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/registerSelfCare")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse registerSelfCare(@RequestBody RegisterSelfCare body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.registerSelfCare(body.getScDTO())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changeMegaVNNV3")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeMegaVNNV3(@RequestBody ChangeMegaVNNV3 body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changeMegaVNNV3(body.getObjReq())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changeFiberCD")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeFiberCD(@RequestBody ChangeFiberCD body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changeFiberCD(body.getIPTVACCOUNT(), body.getSFiberCD())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changeFiberVNN")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeFiberVNN(@RequestBody ChangeFiberVNN body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changeFiberVNN(body.getIPTVACCOUNT(), body.getFiberVNN())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/b2B_AddRoom")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse b2B_AddRoom(@RequestBody B2B_AddRoom body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.b2B_AddRoom(body.getRoomDTO())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/sTB_PROM_Back")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse sTB_PROM_Back(@RequestBody org.tempuri.STB_PROM_Back body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        def service = servicesStubThreadLocal.get()

        try {
            def obj = service.sTB_PROM_Back(body, vascStubFactory.getAuthHeader(phanvungId))
            def res = obj.getSTB_PROM_BackResult()
            if (res.result == 1) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: res.result, faultCode: "", faultString: res.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/getSTBInfo")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getSTBInfo(@RequestBody GetSTBInfo body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.getSTBInfo(body.getIPTVAccount())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/deleteMyTV")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse deleteMyTV(@RequestBody DeleteMyTV body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.deleteMyTV(body.getIPTVAccount())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/editDistrict")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse editDistrict(@RequestBody EditDistrict body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.editDistrict(body.getDistrictCD(), body.getDistrictName(), body.getDistrictNote())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changeSystem")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeSystem(@RequestBody ChangeSystem body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changeSystem(body.getObjRequest())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/getDeviceInfo")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getDeviceInfo(@RequestBody GetDeviceInfo body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.getDeviceInfo(body.getUsernames())
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/addNewDistrict")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse addNewDistrict(@RequestBody AddNewDistrict body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.addNewDistrict(body.getDistrictName(), body.getProvinceCD(), body.getDistrictCD(), body.getDistrictNote())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changeMegaVNN")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeMegaVNN(@RequestBody ChangeMegaVNN body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changeMegaVNN(body.getIPTVAccount(), body.getMegaMyTV(), body.getCombo_FiberCD())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/getUserParent")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getUserParent(@RequestBody GetUserParent body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.getUserParent(body.iptvAccount)
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/updatePrepaid")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse updatePrepaid(@RequestBody UpdatePrepaid body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.updatePrepaid(body.getMyTVAccount(), body.getINo_Month())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/getBillByMonth")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getBillByMonth(@RequestBody GetBillByMonth body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.getBillByMonth(body.getIPTVAccount(), body.getMonth())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/validPassword")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse validPassword(@RequestBody ValidPassword body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.validPassword(body.getIPTVAccount(), body.getOldPassword())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/updateSubGponCab")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse updateSubGponCab(@RequestBody UpdateSubGponCab body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.updateSubGponCab(body.getObjRequest())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changePWHotel")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changePWHotel(@RequestBody ChangePWHotel body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changePWHotel(body.getStrUsername(), body.getOldPassword(), body.getNewPassword())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/updatePackDaily")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse updatePackDaily(@RequestBody UpdatePackDaily body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.updatePackDaily(body.getMyTVAccount(), body.getOperator())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changeDateBill")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeDateBill(@RequestBody ChangeDateBill body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changeDateBill(body.getMyTVAccount(), body.getStrDateBillNew())
            if (obj.result == 0 || obj.result == -3) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/gponCab_Update")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse gponCab_Update(@RequestBody GponCab_Update body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.gponCab_Update(body.getObjRequest())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/getHistorySTB")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getHistorySTB(@RequestBody GetHistorySTB body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.getHistorySTB(body.getStbid())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/getSTBInfor")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getSTBInfor(@RequestBody GetSTBInfor body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.getSTBInfor(body.getStbid())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/b2B_AddHotel")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse b2B_AddHotel(@RequestBody B2B_AddHotel body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.b2B_AddHotel(body.hotelDTO)
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/resetPassword")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse resetPassword(@RequestBody ResetPassword body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.resetPassword(body.getIPTVAccount())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/activeSelfCare")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse activeSelfCare(@RequestBody ActiveSelfCare body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.activeSelfCare(body.getScDTO())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changeBillCode")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeBillCode(@RequestBody ChangeBillCode body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changeBillCode(body.getMyTVAccount(), body.getStrBillcodeNew())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/comboFAF_Update")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse comboFAF_Update(@RequestBody ComboFAF_Update body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.comboFAF_Update(body.getMyTVAccount(), body.getMonthFree(), body.getOperator())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/updateUserParent")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse updateUserParent(@RequestBody UpdateUserParent body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.updateUserParent(body.getObjRequest())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/methodChangePay")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse methodChangePay(@RequestBody MethodChangePay body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.methodChangePay(body.getIPTVACCOUNT(), body.getMethod(), body.getNo_Month())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/updateObjPromoCD")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse updateObjPromoCD(@RequestBody UpdateObjPromoCD body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.updateObjPromoCD(body.getObjRequest())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/sTB_PROM_Active")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse sTB_PROM_Active(@RequestBody org.tempuri.STB_PROM_Active body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        def service = servicesStubThreadLocal.get()

        try {
            def obj = service.sTB_PROM_Active(body, vascStubFactory.getAuthHeader(phanvungId))
            def res = obj.getSTB_PROM_ActiveResult()
            if (res.result == 1) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: res.result, faultCode: "", faultString: res.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/getHisPayTV")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getHisPayTV(@RequestBody GetHisPayTV body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.getHisPayTV(body.getStrMyTV())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/cancelAddOn")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse cancelAddOn(@RequestBody CancelAddOn body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.cancelAddOn(body.getAddonDTO())
            if (obj.result == 1) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/cancelAddOn_v2")
    CommonResponse cancelAddOn_v2(@RequestParam(name = "phanvungId") String phanvungId, @RequestBody CancelAddOn body) {
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()
        return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
    }

    @PostMapping("/sTB_PROM_Buy")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse sTB_PROM_Buy(@RequestBody org.tempuri.STB_PROM_Buy body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        def service = servicesStubThreadLocal.get()

        try {
            def obj = service.sTB_PROM_Buy(body, vascStubFactory.getAuthHeader(phanvungId))
            def res = obj.getSTB_PROM_BuyResult()
            if (res.result == 1) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: res.result, faultCode: "", faultString: res.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/b2B_DeleteRoom")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse b2B_DeleteRoom(@RequestBody B2B_DeleteRoom body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.b2B_DeleteRoom(body.getRoomDTO())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/getBillByContent")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getBillByContent(@RequestBody GetBillByContent body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.getBillByContent(body.getIPTVAccount(), body.getFromDate(), body.getEndDate())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/getBillPackage")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getBillPackage() {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.getBillPackage()
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/deleteHotel")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse deleteHotel(@RequestBody DeleteHotel body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.deleteHotel(body.getStrUsername())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/registerSubscriberV2_1Fiber")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse registerSubscriberV2_1Fiber(@RequestBody RegisterSubscriberV2_1Fiber body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.registerSubscriberV2_1Fiber(body.getName(), body.getBirthday(), body.getSex(), body.getFirstAdd(), body.getSecondAdd(), body.getIDNo(), body.getTelNo(), body.getCellNo(), body.getAreaCode(), body.getDistrictID(), body.getEmail(), body.getYM(), body.getWebsite(), body.getFaxNo(), body.getContractCode(), body.getIPTVAccount(), body.getIPTVPW(), body.getPackageCode(), body.getServiceCode(), body.getServiceVASCode(), body.getSTBID(), body.getStatus(), body.getCateCode(), body.getFixDate(), body.getNotFixReason(), body.getContractSignDate(), body.getPayment(), body.getNo_Month(), body.getLoginAccount(), body.getLoginAcount_Password(), body.getBillAccount(), body.getMegaMyTV(), body.getSPortDSL(), body.getSPromo_id(), body.getSFiberCD(), body.getSNDTV_CD())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/registerSubscriberV2Fiber")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse registerSubscriberV2Fiber(@RequestBody RegisterSubscriberV2Fiber body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.registerSubscriberV2Fiber(body.getName(), body.getBirthday(), body.getSex(), body.getFirstAdd(), body.getSecondAdd(), body.getIDNo(), body.getTelNo(), body.getCellNo(), body.getAreaCode(), body.getDistrictID(), body.getEmail(), body.getYM(), body.getWebsite(), body.getFaxNo(), body.getContractCode(), body.getIPTVAccount(), body.getIPTVPW(), body.getPackageCode(), body.getServiceCode(), body.getServiceVASCode(), body.getSTBID(), body.getStatus(), body.getCateCode(), body.getFixDate(), body.getNotFixReason(), body.getContractSignDate(), body.getPayment(), body.getNo_Month(), body.getLoginAccount(), body.getLoginAcount_Password(), body.getBillAccount(), body.getMegaMyTV(), body.getSPortDSL(), body.getSPromo_id(), body.getSFiberCD())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/methodChangePayV2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse methodChangePayV2(@RequestBody MethodChangePayV2 body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.methodChangePayV2(body.getIPTVACCOUNT(), body.getMethod(), body.getNo_Month(), body.getSStartDate())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changeSubscriberV3")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeSubscriberV3(@RequestBody ChangeSubscriberV3 body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changeSubscriberV3(body.getObjRequset())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/b2B_Hotel_AddAddOn")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse b2B_Hotel_AddAddOn(@RequestBody B2B_Hotel_AddAddOn body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.b2B_Hotel_AddAddOn(body.getPackDTO())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/methodChangePayV3")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse methodChangePayV3(@RequestBody MethodChangePayV3 body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.methodChangePayV3(body.getObjRequest())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/changeSubscriberV2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changeSubscriberV2(@RequestBody ChangeSubscriberV2 body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.changeSubscriberV2(body.portDSL, body.getIPTVAccount())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/registerSubscriberV3")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse registerSubscriberV3(@RequestBody RegisterSubscriberV3 body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            body.getObjRequest().extSys = CustExtSysEnum.NORMAR
            body.getObjRequest().loginAccount = getUsername(session)
            body.getObjRequest().loginAcount_Password = getPassword(session)
            def obj = service.registerSubscriberV3(body.getObjRequest())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @PostMapping("/registerSubscriberV2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse registerSubscriberV2(@RequestBody RegisterSubscriberV2 body) {
        def session = genericApi.getSessionData(request)
        String phanvungId = session[GenericApi.ID_TINHTHANH]
        SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

        try {
            def obj = service.registerSubscriberV2(body.getName(), body.getBirthday(), body.getSex(), body.getFirstAdd(), body.getSecondAdd(), body.getIDNo(), body.getTelNo(), body.getCellNo(), body.getAreaCode(), body.getDistrictID(), body.getEmail(), body.getYM(), body.getWebsite(), body.getFaxNo(), body.getContractCode(), body.getIPTVAccount(), body.getIPTVPW(), body.getPackageCode(), body.getServiceCode(), body.getServiceVASCode(), body.getSTBID(), body.getStatus(), body.getCateCode(), body.getFixDate(), body.getNotFixReason(), body.getContractSignDate(), body.getPayment(), body.getNo_Month(), body.getLoginAccount(), body.getLoginAcount_Password(), body.getBillAccount(), body.getMegaMyTV(), body.getSPortDSL(), body.getSPromo_id())
            if (obj.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: obj)
            } else {
                return new CommonResponse(errorCode: obj.result, faultCode: "", faultString: obj.errordesc, data: obj)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/errorSolution")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse errorSolution(@RequestBody ErrorSolutionInput body) {
        def session = genericApi.getSessionData(request)
        try {
            String reqBody = """
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Header>
    <AuthHeader xmlns="http://tempuri.org/">
      <strUserName>${getUsername(session)}</strUserName>
      <strPassword>${getPassword(session)}</strPassword>
    </AuthHeader>
  </soap:Header>
  <soap:Body>
    <ErrorSolution xmlns="http://tempuri.org/">
      <error_code>${body?.error_code}</error_code>
      <error_keyword>${body?.error_keyword}</error_keyword>
    </ErrorSolution>
  </soap:Body>
</soap:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "portal.mytv.vn",
                     "SOAPAction"  : "http://tempuri.org/ErrorSolution"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def result = Envelope.Body.ErrorSolutionResponse.ErrorSolutionResult.text()
            def jObj = new JsonSlurper().parseText(result?.toString())
            int errorCode = jObj["result"] ?: 0
            String message = jObj["message"] ?: ""
            def data = jObj["data"] ?: []
            return new CommonResponse(errorCode: errorCode, faultCode: errorCode, faultString: message, data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/resetPasswordLockChildren")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse resetPasswordLockChildren(@RequestBody ResetPasswordLockChildrenInput body) {
        def session = genericApi.getSessionData(request)
        try {
            String reqBody = """
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Header>
    <AuthHeader xmlns="http://tempuri.org/">
      <strUserName>${getUsername(session)}</strUserName>
      <strPassword>${getPassword(session)}</strPassword>
    </AuthHeader>
  </soap:Header>
  <soap:Body>
    <ResetPasswordLockChildren xmlns="http://tempuri.org/">
      <username>${body?.username}</username>
      <lock_password>${body?.lock_password}</lock_password>
    </ResetPasswordLockChildren>
  </soap:Body>
</soap:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "portal.mytv.vn",
                     "SOAPAction"  : "http://tempuri.org/ResetPasswordLockChildren"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def result = Envelope.Body.ResetPasswordLockChildrenResponse.ResetPasswordLockChildrenResult.text()
            def jObj = new JsonSlurper().parseText(result?.toString())
            int errorCode = jObj["result"] ?: 0
            String message = jObj["message"] ?: ""
            def data = jObj["data"] ?: null
            return new CommonResponse(errorCode: errorCode, faultCode: errorCode, faultString: message, data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/resetCCU")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse resetCCU(@RequestBody ResetPasswordLockChildrenInput body) {
        def session = genericApi.getSessionData(request)
        try {
            String reqBody = """
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Header>
    <AuthHeader xmlns="http://tempuri.org/">
      <strUserName>${getUsername(session)}</strUserName>
      <strPassword>${getPassword(session)}</strPassword>
    </AuthHeader>
  </soap:Header>
  <soap:Body>
    <ResetCCU xmlns="http://tempuri.org/">
      <username>${body?.username}</username>
    </ResetCCU>
  </soap:Body>
</soap:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "portal.mytv.vn",
                     "SOAPAction"  : "http://tempuri.org/ResetCCU"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def result = Envelope.Body.ResetCCUResponse.ResetCCUResult.text()
            def jObj = new JsonSlurper().parseText(result?.toString())
            int errorCode = jObj["result"] ?: 0
            String message = jObj["message"] ?: ""
            def data = jObj["data"] ?: null
            return new CommonResponse(errorCode: errorCode, faultCode: errorCode, faultString: message, data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/unbindKplus")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse unbindKplus(@RequestBody UnbindKplusInput body) {
        def session = genericApi.getSessionData(request)
        try {
            String reqBody = """
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Header>
    <AuthHeader xmlns="http://tempuri.org/">
      <strUserName>${getUsername(session)}</strUserName>
      <strPassword>${getPassword(session)}</strPassword>
    </AuthHeader>
  </soap:Header>
  <soap:Body>
    <UnbindKplus xmlns="http://tempuri.org/">
      <username>${body?.username}</username>
    </UnbindKplus>
  </soap:Body>
</soap:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "portal.mytv.vn",
                     "SOAPAction"  : "http://tempuri.org/UnbindKplus"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def result = Envelope.Body.UnbindKplusResponse.UnbindKplusResult.text()
            def jObj = new JsonSlurper().parseText(result?.toString())
            int errorCode = jObj["result"] ?: 0
            String message = jObj["message"] ?: ""
            def data = jObj["data"] ?: null
            return new CommonResponse(errorCode: errorCode, faultCode: errorCode, faultString: message, data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/getPackageEPG_v2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getPackageEPG_v2(@RequestBody GetPackageEPG body) {
        return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: [])
    }

    @PostMapping("/getPackageEPG_v3")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getPackageEPG_v2(@RequestParam(name = "phanvungId") String phanvungId, @RequestBody GetPackageEPG body) {
        return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: [])
    }

    @PostMapping("/changePromotions_v2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changePromotions_v2(@RequestParam(name = "phanvungId") String phanvungId, @RequestBody ChangePromotions body) {
        return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
    }

    @PostMapping("/changePackage_v2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse changePackage_v2(@RequestParam(name = "phanvungId") String phanvungId, @RequestBody ChangePromotions body) {
        return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
    }

    private String getTagValue(String tag, Element element) {
        org.w3c.dom.NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }
}