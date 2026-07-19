package vn.vnptit.sapi.oneapp.resources.vasc

import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.util.GenericApi
import ws.client.vasc.CheckCCU
import ws.client.vasc.GetDeviceKplus
import ws.client.vasc.LoginHistory
import ws.client.vasc.SubscriberManagementSoap_BindingStub

import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("vasc")
class VascNewService {

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

    @Autowired
    VASCNewStubFactory vascStubFactory

    @PostMapping("/getDeviceKplus")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getDeviceKplus(@RequestBody GetDeviceKplus body) {
        try {
            def session = genericApi.getSessionData(request)
            String phanvungId = session[GenericApi.ID_TINHTHANH]
            SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

            def str = service.getDeviceKplus(body.getUsername())
            def res = new JsonSlurper().parseText(str)
            if (res.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: res.message, data: res)
            } else {
                return new CommonResponse(errorCode: res.result, faultCode: "", faultString: res.message, data: res)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/loginHistory")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse loginHistory(@RequestBody LoginHistory body) {
        try {
            def session = genericApi.getSessionData(request)
            String phanvungId = session[GenericApi.ID_TINHTHANH]
            SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()

            def str = service.loginHistory(body.getUsername())
            def res = new JsonSlurper().parseText(str)
            if (res.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: res.message, data: res)
            } else {
                return new CommonResponse(errorCode: res.result, faultCode: "", faultString: res.message, data: res)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/checkCCU")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse checkCCU(@RequestBody CheckCCU body) {
        try {
            def session = genericApi.getSessionData(request)
            String phanvungId = session[GenericApi.ID_TINHTHANH]
            SubscriberManagementSoap_BindingStub service = vascStubFactory.get(phanvungId).get()
            def str = service.checkCCU(body.getUsername())
            def res = new JsonSlurper().parseText(str)
            if (res.result == 0) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: res.message, data: res)
            } else {
                return new CommonResponse(errorCode: res.result, faultCode: "", faultString: res.message, data: res)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


}