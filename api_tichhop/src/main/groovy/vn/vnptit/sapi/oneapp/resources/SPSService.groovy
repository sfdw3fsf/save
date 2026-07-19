package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.json.JsonSlurper
import groovy.sql.Sql
import org.json.XML
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.sps.SPS_SERVICE_IMS
import vn.vnptit.sapi.oneapp.models.sps.SPS_SERVICE_IMS_7TS
import vn.vnptit.sapi.oneapp.models.sps.TS_ISM_MODIFY_SUBSCRIBER
import vn.vnptit.sapi.oneapp.models.sps.TS_MTAS_MODIFY_SUBSCRIBER
import vn.vnptit.sapi.oneapp.models.sps.TS_MTAS_MODIFY_SUBSCRIBER_HOTLINE
import vn.vnptit.sapi.oneapp.models.sps.TraCuuThongTin
import vn.vnptit.sapi.oneapp.models.sps.CHANGE_LOCATION
import vn.vnptit.sapi.oneapp.models.sps.TRACUU_THONGTIN_THUEBAO
import vn.vnptit.sapi.oneapp.models.sps.SET_OFFER
import vn.vnptit.sapi.oneapp.models.sps.DELETE_OFFER
import vn.vnptit.sapi.oneapp.service.HopDongService
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse
import vn.vnptit.sapi.oneapp.util.GenericApi
import vn.vnptit.sapi.oneapp.util.LoggingInOut

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("sps")
class SPSService {
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

    @Value("\${spring.defaultconfigs.sps-accounts}")
    String spsAccounts

    @Value("\${spring.defaultconfigs.sps-url-new}")
    String spsUrl

    @Value("\${spring.defaultconfigs.sps-url-ucip}")
    String spsUrlUcip


    static Map<String, Map<String, String>> cachedAcc = null

    void loadSPSAccountsMap() {
        cachedAcc = [:]
        spsAccounts.split(',').each {
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
            synchronized (spsAccounts) {
                if (cachedAcc == null) loadSPSAccountsMap()
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
            synchronized (spsAccounts) {
                if (cachedAcc == null) loadSPSAccountsMap()
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


    @PostMapping("/test_bbll")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse test_bbll(@RequestBody Map<Object, Object> body) {
        try {
            def session = genericApi.getSessionData(request)
            String body_str =  body["body_str"].toString();
            Long phanvung_id = Long.parseLong(session[GenericApi.ID_TINHTHANH]);
            def env = post(spsUrlUcip, null,body_str);
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/traCuuThongTinMTAS")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse traCuuThongTinMTAS(@Valid @RequestBody TraCuuThongTin body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            def env = post(spsUrl, null,
                    """<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                           <soap:Header>
                            <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                               <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                  <wsse:Username>${username}</wsse:Username>
                                  <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                               </wsse:UsernameToken>
                            </wsse:Security>
                           </soap:Header>
                           <soap:Body>
                            <ins:CreateRequest>
                               <ins:RequestHeader>
                                  <ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>1235</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>
                               </ins:RequestHeader>
                               <ins:RequestParameters>
                                  <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
                                  <ins:Parameter name="SUBTYPE" value="SIP"/>
                                  <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_DISPLAY_SUBSCRIBER"/>
                                  <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                  <ins:Parameter name="OPERATION" value="Display"/>
                               </ins:RequestParameters>
                            </ins:CreateRequest>
                            <ins:DisplayRequest>
                               <ins:RequestParameters/>
                               <ins:RequestHeader>
                                  <ins:NeType/>
                               </ins:RequestHeader>
                            </ins:DisplayRequest>
                           </soap:Body>
                        </soap:Envelope>""")
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/sps_service_ims")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse sps_service_ims(@Valid @RequestBody SPS_SERVICE_IMS body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            def env = post(spsUrl, null,
                    """<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                           <soap:Header>
                            <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                               <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                  <wsse:Username>${username}</wsse:Username>
                                  <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                               </wsse:UsernameToken>
                            </wsse:Security>
                           </soap:Header>
                           <soap:Body>
                              <ins:DisplayRequest>
                                 <ins:RequestHeader>
                                    <ins:NeType>ONLINE</ins:NeType>
                                    <ins:OrderNo>9999</ins:OrderNo>
                                    <ins:Priority>3</ins:Priority>
                                    <ins:ReqUser>ccbs_vnp</ins:ReqUser>
                                 </ins:RequestHeader>
                                 <ins:RequestParameters>
                                    <ins:Parameter name="MSISDN1" value="${body.msisdn}"/>
                                    <ins:Parameter name="IMSI" value="${body.imsi}"/>
                                    <ins:Parameter name="SUBTYPE" value="${body.subtype}"/>
                                    <ins:Parameter name="USER_PASSWORD" value="${body.user_password}"/>
                                    <ins:Parameter name="ISM_RES_3" value="${body.ism_res_3}"/>
                                    <ins:Parameter name="CHARGING_PROFILE" value="${body?.profile}"/>
                                    <ins:Parameter name="LL_PRODUCT_NAME" value="SPS_SERVICE_IMS"/>
                                    <ins:Parameter name="ITEM_TYPE" value="Service"/>
                                    <ins:Parameter name="OPERATION" value="Create"/>
                                 </ins:RequestParameters>
                              </ins:DisplayRequest>
                           </soap:Body>
                        </soap:Envelope>""")
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/sps_service_ims_deactive")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse sps_service_ims_deactive(@Valid @RequestBody TraCuuThongTin body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            def env = post(spsUrl, null,
                    """<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                           <soap:Header>
                            <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                               <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                  <wsse:Username>${username}</wsse:Username>
                                  <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                               </wsse:UsernameToken>
                            </wsse:Security>
                           </soap:Header>
                           <soap:Body>
                              <ins:DisplayRequest>
                                 <ins:RequestHeader>
                                    <ins:NeType>ONLINE</ins:NeType>
                                    <ins:OrderNo>9999</ins:OrderNo>
                                    <ins:Priority>3</ins:Priority>
                                 </ins:RequestHeader>
                                 <ins:RequestParameters>
                                    <ins:Parameter name="MSISDN1" value="${body.msisdn}"/>
                                    <ins:Parameter name="LL_PRODUCT_NAME" value="SPS_SERVICE_IMS_DEACTIVE"/>
                                    <ins:Parameter name="ITEM_TYPE" value="Service"/>
                                    <ins:Parameter name="OPERATION" value="Create"/>
                                 </ins:RequestParameters>
                              </ins:DisplayRequest>
                           </soap:Body>
                        </soap:Envelope>""")
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/ts_mtas_delete_subscriber")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse ts_mtas_delete_subscriber(@Valid @RequestBody TraCuuThongTin body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            def env = post(spsUrl, null,
                    """<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                           <soap:Header>
                            <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                               <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                  <wsse:Username>${username}</wsse:Username>
                                  <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                               </wsse:UsernameToken>
                            </wsse:Security>
                           </soap:Header>
                           <soap:Body>
                              <ins:DisplayRequest>
                                 <ins:RequestHeader>
                                    <ins:NeType>ONLINE</ins:NeType>
                                    <ins:OrderNo>9999</ins:OrderNo>
                                    <ins:Priority>3</ins:Priority>
                                 </ins:RequestHeader>
                                 <ins:RequestParameters>
                                    <ins:Parameter name="MSISDN1" value="${body.msisdn}"/>
                                    <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_DELETE_SUBSCRIBER"/>
                                    <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                    <ins:Parameter name="OPERATION" value="Delete"/>
                                 </ins:RequestParameters>
                              </ins:DisplayRequest>
                           </soap:Body>
                        </soap:Envelope>""")
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/ts_ism_delete_subscriber")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse ts_ism_delete_subscriber(@Valid @RequestBody TraCuuThongTin body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            def env = post(spsUrl, null,
                    """<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                           <soap:Header>
                            <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                               <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                  <wsse:Username>${username}</wsse:Username>
                                  <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                               </wsse:UsernameToken>
                            </wsse:Security>
                           </soap:Header>
                           <soap:Body>
                              <ins:DisplayRequest>
                                 <ins:RequestHeader>
                                    <ins:NeType>ONLINE</ins:NeType>
                                    <ins:OrderNo>9999</ins:OrderNo>
                                    <ins:Priority>3</ins:Priority>
                                    <ins:ReqUser>aha</ins:ReqUser>
                                 </ins:RequestHeader>
                                 <ins:RequestParameters>
                                    <ins:Parameter name="MSISDN1" value="${body.msisdn}"/>
                                    <ins:Parameter name="LL_PRODUCT_NAME" value="TS_ISM_DELETE_SUBSCRIBER"/>
                                    <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                    <ins:Parameter name="OPERATION" value="Delete"/>
                                 </ins:RequestParameters>
                              </ins:DisplayRequest>
                           </soap:Body>
                        </soap:Envelope>""")
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/ts_ipw_delete_subscriber")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse ts_ipw_delete_subscriber(@Valid @RequestBody TraCuuThongTin body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            def env = post(spsUrl, null,
                    """<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                           <soap:Header>
                            <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                               <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                  <wsse:Username>${username}</wsse:Username>
                                  <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                               </wsse:UsernameToken>
                            </wsse:Security>
                           </soap:Header>
                           <soap:Body>
                              <ins:DisplayRequest>
                                 <ins:RequestHeader>
                                    <ins:NeType>ONLINE</ins:NeType>
                                    <ins:OrderNo>9999</ins:OrderNo>
                                    <ins:Priority>3</ins:Priority>
                                    <ins:ReqUser>aha</ins:ReqUser>
                                 </ins:RequestHeader>
                                 <ins:RequestParameters>
                                    <ins:Parameter name="MSISDN1" value="${body.msisdn}"/>
                                    <ins:Parameter name="LL_PRODUCT_NAME" value="TS_IPW_DELETE_SUBSCRIBER"/>
                                    <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                    <ins:Parameter name="OPERATION" value="Delete"/>
                                 </ins:RequestParameters>
                              </ins:DisplayRequest>
                           </soap:Body>
                        </soap:Envelope>""")
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/ts_mtas_modify_subscriber")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse ts_mtas_modify_subscriber(@Valid @RequestBody TS_MTAS_MODIFY_SUBSCRIBER body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            def env = post(spsUrl, null,
                    """<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                           <soap:Header>
                            <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                               <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                  <wsse:Username>${username}</wsse:Username>
                                  <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                               </wsse:UsernameToken>
                            </wsse:Security>
                           </soap:Header>
                           <soap:Body>
                              <ins:DisplayRequest>
                                 <ins:RequestHeader>
                                    <ins:NeType>ONLINE</ins:NeType>
                                    <ins:OrderNo>9999</ins:OrderNo>
                                    <ins:Priority>3</ins:Priority>
                                    <ins:ReqUser>aha</ins:ReqUser>
                                 </ins:RequestHeader>
                                 <ins:RequestParameters>
                                    <ins:Parameter name="MSISDN1" value="${body.msisdn}"/>
                                    <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                    <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_MODIFY_SUBSCRIBER"/>
                                    <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                    <ins:Parameter name="OPERATION" value="Modify"/>
                                 </ins:RequestParameters>
                              </ins:DisplayRequest>
                           </soap:Body>
                        </soap:Envelope>""")
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/ts_mtas_modify_subscriber_v2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse ts_mtas_modify_subscriber_v2(@Valid @RequestBody TS_MTAS_MODIFY_SUBSCRIBER body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            def env = post(spsUrl, null,
                    """<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                           <soap:Header>
                            <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                               <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                  <wsse:Username>${username}</wsse:Username>
                                  <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                               </wsse:UsernameToken>
                            </wsse:Security>
                           </soap:Header>
                           <soap:Body>
                              <ins:DisplayRequest>
                                 <ins:RequestHeader>
                                    <ins:NeType>ONLINE</ins:NeType>
                                    <ins:OrderNo>9999</ins:OrderNo>
                                    <ins:Priority>3</ins:Priority>
                                    <ins:ReqUser>aha</ins:ReqUser>
                                 </ins:RequestHeader>
                                 <ins:RequestParameters>
                                    <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
                                    <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                    <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_MODIFY_SUBSCRIBER"/>
                                    <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                    <ins:Parameter name="OPERATION" value="Modify"/>
                                 </ins:RequestParameters>
                              </ins:DisplayRequest>
                           </soap:Body>
                        </soap:Envelope>""")
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/ts_ism_modify_subscriber")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse ts_ism_modify_subscriber(@Valid @RequestBody TS_ISM_MODIFY_SUBSCRIBER body) {
        try {
            // try {
            //    hopDongService.init(request.getHeader("Authorization"))
            //    hopDongService.logAction("ts_ism_modify_subscriber", new Gson().toJson(body))
            // } catch (Exception exe) {
               
            // }
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            def env = post(spsUrl, null,
                    """<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                           <soap:Header>
                            <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                               <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                  <wsse:Username>${username}</wsse:Username>
                                  <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                               </wsse:UsernameToken>
                            </wsse:Security>
                           </soap:Header>
                           <soap:Body>
                              <ins:DisplayRequest>
                                 <ins:RequestHeader>
                                    <ins:NeType>ONLINE</ins:NeType>
                                    <ins:OrderNo>9999</ins:OrderNo>
                                    <ins:Priority>3</ins:Priority>
                                    <ins:ReqUser>aha</ins:ReqUser>
                                 </ins:RequestHeader>
                                 <ins:RequestParameters>
                                    <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
                                    <ins:Parameter name="MSISDN1" value="${body.msisdn}"/>
                                    <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                    <ins:Parameter name="LL_PRODUCT_NAME" value="TS_ISM_MODIFY_SUBSCRIBER"/>
                                    <ins:Parameter name="USER_PASSWORD" value="${body.user_password}" />
                                    <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                    <ins:Parameter name="OPERATION" value="Modify"/>
                                 </ins:RequestParameters>
                              </ins:DisplayRequest>
                           </soap:Body>
                        </soap:Envelope>""")
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/ts_ism_modify_subscriber_v2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse ts_ism_modify_subscriber_v2(@Valid @RequestBody TS_ISM_MODIFY_SUBSCRIBER body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            def env = post(spsUrl, null,
                    """<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                           <soap:Header>
                            <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                               <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                  <wsse:Username>${username}</wsse:Username>
                                  <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                               </wsse:UsernameToken>
                            </wsse:Security>
                           </soap:Header>
                           <soap:Body>
                              <ins:DisplayRequest>
                                 <ins:RequestHeader>
                                    <ins:NeType>ONLINE</ins:NeType>
                                    <ins:OrderNo>1235</ins:OrderNo>
                                    <ins:Priority>3</ins:Priority>
                                    <ins:ReqUser>aha</ins:ReqUser>
                                 </ins:RequestHeader>
                                 <ins:RequestParameters>
                                    <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
                                    <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                    <ins:Parameter name="LL_PRODUCT_NAME" value="TS_ISM_MODIFY_SUBSCRIBER"/>
                                    <ins:Parameter name="USER_PASSWORD" value="${body.user_password}"/>
                                    <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                    <ins:Parameter name="OPERATION" value="Modify"/>
                                 </ins:RequestParameters>
                              </ins:DisplayRequest>
                           </soap:Body>
                        </soap:Envelope>""")
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/change_location")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse change_location(@Valid @RequestBody CHANGE_LOCATION body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            def env = post(spsUrl, null,
                    """<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                           <soap:Header>
                            <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                               <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                  <wsse:Username>${username}</wsse:Username>
                                  <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                               </wsse:UsernameToken>
                            </wsse:Security>
                           </soap:Header>
                           <soap:Body>
                              <ins:DisplayRequest>
                                 <ins:RequestHeader>
                                    <ins:NeType>ONLINE</ins:NeType>
                                    <ins:OrderNo>ism-mod-access-location</ins:OrderNo>
                                    <ins:Priority>3</ins:Priority>
                                    <ins:ReqUser>aha</ins:ReqUser>
                                 </ins:RequestHeader>
                                 <ins:RequestParameters>
                                    <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
                                    <ins:Parameter name="SERVICE_ACTION" value="ism-mod-access-location"/>
                                    <ins:Parameter name="LL_PRODUCT_NAME" value="TS_ISM_MODIFY_SUBSCRIBER"/>
                                    <ins:Parameter name="ISM_RES_3" value="${body.area}"/>
                                    <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                    <ins:Parameter name="OPERATION" value="Modify"/>
                                 </ins:RequestParameters>
                              </ins:DisplayRequest>
                           </soap:Body>
                        </soap:Envelope>""")
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/tracuu_thongtin_thuebao")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse tracuu_thongtin_thuebao(@Valid @RequestBody TRACUU_THONGTIN_THUEBAO body) {
        try {
            String sNode = "";
            if (body.node == "MTAS")
                sNode = "TS_MTAS_DISPLAY_SUBSCRIBER";
            else if (body.node == "HSS")
                sNode = "TS_ISM_DISPLAY_SUBSCRIBER";
            else
                sNode = "TS_IPW_DISPLAY_SUBSCRIBER";

            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            def env = post(spsUrl, null,
                    """<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                           <soap:Header>
                            <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                               <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                  <wsse:Username>${username}</wsse:Username>
                                  <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                               </wsse:UsernameToken>
                            </wsse:Security>
                           </soap:Header>
                           <soap:Body>
                              <ins:DisplayRequest>
                                 <ins:RequestHeader>
                                    <ins:NeType>ONLINE</ins:NeType>
                                    <ins:OrderNo>1235</ins:OrderNo>
                                    <ins:Priority>3</ins:Priority>
                                    <ins:ReqUser>aha</ins:ReqUser>
                                 </ins:RequestHeader>
                                 <ins:RequestParameters>
                                    <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
                                    <ins:Parameter name="LL_PRODUCT_NAME" value="${sNode}"/>
                                    <ins:Parameter name="SUBTYPE" value="${body.type}"/>
                                    <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                    <ins:Parameter name="OPERATION" value="Display"/>
                                 </ins:RequestParameters>
                              </ins:DisplayRequest>
                           </soap:Body>
                        </soap:Envelope>""")
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

      @LoggingInOut
      @PostMapping("/ts_mtas_modify_subscriber_hotline")
      @Consumes([MediaType.APPLICATION_JSON])
      @Produces([MediaType.APPLICATION_JSON])
      CommonResponse ts_mtas_modify_subscriber_hotline(@Valid @RequestBody TS_MTAS_MODIFY_SUBSCRIBER_HOTLINE body) {
         try {
            def session = genericApi.getSessionData(request) 
            String username = getUsername(session)
            String password = getPassword(session)
            String bodySoap = """<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                                    <soap:Header>
                                       <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                                          <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                             <wsse:Username>${username}</wsse:Username>
                                             <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                                          </wsse:UsernameToken>
                                       </wsse:Security>
                                    </soap:Header>
                                    <soap:Body>
                                       <ins:CreateRequest>
                                          <ins:RequestHeader>
                                             <ins:NeType>ONLINE</ins:NeType>
                                             <ins:OrderNo>1235</ins:OrderNo>
                                             <ins:Priority>3</ins:Priority>
                                             <ins:ReqUser>aha</ins:ReqUser>
                                          </ins:RequestHeader>
                                          <ins:RequestParameters>
                                             <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
                                             <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>"""
                           if (body.hotline && body.hotline != null && body.hotline != "") {
                              if (body.hotline.startsWith("0")) {
                                  body.hotline = "84" + body.hotline.substring(1)
                              }
                              bodySoap += """<ins:Parameter name="HOTLINE" value="${body.hotline}"/>"""
                           }
                              bodySoap += """<ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_MODIFY_SUBSCRIBER"/>
                                             <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                             <ins:Parameter name="OPERATION" value="Modify"/>
                                          </ins:RequestParameters>
                                       </ins:CreateRequest>
                                       <ins:DisplayRequest>
                                          <ins:RequestParameters />
                                          <ins:RequestHeader>
                                             <ins:NeType />
                                          </ins:RequestHeader>
                                       </ins:DisplayRequest>
                                    </soap:Body>
                                 </soap:Envelope>"""
            def env = post(spsUrl, null, bodySoap)
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
         } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
         }
      }

      @LoggingInOut
      @PostMapping("/set_offer")
      @Consumes([MediaType.APPLICATION_JSON])
      @Produces([MediaType.APPLICATION_JSON])
      CommonResponse set_offer(@Valid @RequestBody SET_OFFER body) {
         try {
            def session = genericApi.getSessionData(request) 
            String username = getUsername(session)
            String password = getPassword(session)
            String bodySoap = """<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                                    <soap:Header>
                                       <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                                          <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                             <wsse:Username>${username}</wsse:Username>
                                             <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                                          </wsse:UsernameToken>
                                       </wsse:Security>
                                    </soap:Header>
                                    <soap:Body>
                                       <ins:CreateRequest>
                                          <ins:RequestHeader>
                                             <ins:NeType>ONLINE</ins:NeType>
                                             <ins:OrderNo>1235</ins:OrderNo>
                                             <ins:Priority>3</ins:Priority>
                                             <ins:ReqUser>aha</ins:ReqUser>
                                          </ins:RequestHeader>
                                          <ins:RequestParameters>
                                             <ins:Parameter name="MSISDN1" value="${body.msisdn}"/>
                                             <ins:Parameter name="ACTION" value="UpdateOffer"/>"""
                           if (body.msisdn1 && body.msisdn1 != null && body.msisdn1 != "") {
                              bodySoap += """<ins:Parameter name="UOFR_OFFERPROVIDERID" value="${body.msisdn1}"/>"""
                           }
                              bodySoap += """<ins:Parameter name="NEGOTIATEDCAPABILITIES_1" value="805642820"/>
                                             <ins:Parameter name="NEGOTIATEDCAPABILITIES_2" value="163816"/>
                                             <ins:Parameter name="UOFR_OFFERID" value="${body.offer_id}"/>"""
                           if (body.groupname && body.groupname != null && body.groupname != "") {
                              bodySoap += """<ins:Parameter name="ATTUIL_1_ATTRIBUTENAME" value="GroupName"/>"""
                              bodySoap += """<ins:Parameter name="ATTUIL_1_ATTRIBUTEUPDATEACTION" value="SET"/>"""
                              bodySoap += """<ins:Parameter name="ATTUIL_1_ATTRIBUTEVALUESTRING" value="${body.groupname}"/>"""
                           }
                           if (body.membertype && body.membertype != null && body.membertype != "") {
                              bodySoap += """<ins:Parameter name="ATTUIL_2_ATTRIBUTENAME" value="MemberType"/>"""
                              bodySoap += """<ins:Parameter name="ATTUIL_2_ATTRIBUTEUPDATEACTION" value="SET"/>"""
                              bodySoap += """<ins:Parameter name="ATTUIL_2_ATTRIBUTEVALUESTRING" value="${body.membertype}"/>"""
                           }
                           if (body.start_date && body.start_date != null && body.start_date != "") {
                              bodySoap += """<ins:Parameter name="UOFR_STARTDATE" value="${body.start_date}"/>"""
                           }
                           if (body.expiry_date && body.expiry_date != null && body.expiry_date != "") {
                              bodySoap += """<ins:Parameter name="UOFR_EXPIRYDATE" value="${body.expiry_date}"/>"""
                           }
                              bodySoap += """<ins:Parameter name="LL_PRODUCT_NAME" value="TS_OCSAIR_UPDOFR"/>
                                             <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                             <ins:Parameter name="OPERATION" value="Modify"/>
                                          </ins:RequestParameters>
                                       </ins:CreateRequest>
                                       <ins:DisplayRequest>
                                          <ins:RequestParameters />
                                          <ins:RequestHeader>
                                             <ins:NeType />
                                          </ins:RequestHeader>
                                       </ins:DisplayRequest>
                                    </soap:Body>
                                 </soap:Envelope>"""
            def env = post(spsUrl, null, bodySoap)
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
         } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
         }
      }

    @LoggingInOut
    @PostMapping("/delete_offer")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse delete_offer(@Valid @RequestBody DELETE_OFFER body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            def env = post(spsUrl, null,
                    """<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                           <soap:Header>
                            <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                               <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                  <wsse:Username>${username}</wsse:Username>
                                  <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                               </wsse:UsernameToken>
                            </wsse:Security>
                           </soap:Header>
                           <soap:Body>
                              <ins:DisplayRequest>
                                 <ins:RequestHeader>
                                    <ins:NeType>ONLINE</ins:NeType>
                                    <ins:OrderNo>1235</ins:OrderNo>
                                    <ins:Priority>3</ins:Priority>
                                    <ins:ReqUser>aha</ins:ReqUser>
                                 </ins:RequestHeader>
                                 <ins:RequestParameters>
                                    <ins:Parameter name="MSISDN1" value="${body.msisdn}"/>
                                    <ins:Parameter name="ACTION" value="DeleteOffer"/>
                                    <ins:Parameter name="NEGOTIATEDCAPABILITIES_1" value="805642820"/>
                                    <ins:Parameter name="NEGOTIATEDCAPABILITIES_2" value="8104"/>
                                    <ins:Parameter name="DOFR_OFFERID" value="${body.offer_id}"/>
                                    <ins:Parameter name="LL_PRODUCT_NAME" value="TS_OCSAIR_DELOFR"/>                             
                                    <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                    <ins:Parameter name="OPERATION" value="Delete"/>
                                 </ins:RequestParameters>
                              </ins:DisplayRequest>
                           </soap:Body>
                        </soap:Envelope>""")
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/exec")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse exec(@RequestBody Map<String, String> body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            def env = post(spsUrl, null,
                    """<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                           <soap:Header>
                            <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                               <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                  <wsse:Username>${username}</wsse:Username>
                                  <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                               </wsse:UsernameToken>
                            </wsse:Security>
                           </soap:Header>
                           <soap:Body>
                              <ins:DisplayRequest>
                                 <ins:RequestHeader>
                                    <ins:NeType>ONLINE</ins:NeType>
                                    <ins:OrderNo>1235</ins:OrderNo>
                                    <ins:Priority>3</ins:Priority>
                                    <ins:ReqUser>aha</ins:ReqUser>
                                 </ins:RequestHeader>
                                 ${body.request}
                              </ins:DisplayRequest>
                           </soap:Body>
                        </soap:Envelope>""")
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    String post(String url, Map<String, String> headers, String body) {
        if(headers == null) headers = [:]
        headers.put("Content-Type", "text/xml;charset=UTF-8")
        String resp = httpManaged.post(["url": url], headers, body)
        return resp
    }

    ObjectMapper objectMapper = new ObjectMapper()
    private List<Map<String, String>> convertXmlData(def Response) {
        List<Map<String, String>> listObj = []
        try {
            def ResponseHeader = Response["ResponseHeader"]
            if (ResponseHeader != null) {
                Map<String, String> responseHeader = objectMapper.convertValue(ResponseHeader, Map.class)
                listObj.add(responseHeader)
            }
            def ResponseParameters = Response["ResponseParameters"]
            if (ResponseParameters != null) {
                Map<String, String> mapData = new HashMap<>()
                def Parameter = ResponseParameters["Parameter"]
                for (Object item in Parameter) {
                    mapData.put(item["name"]?.toString(), item["value"]?.toString())
                }
                listObj.add(mapData)
            }
            def RequestParameters = Response["RequestParameters"]
            if (RequestParameters != null) {
                Map<String, String> mapData = new HashMap<>()
                def Parameter = RequestParameters["Parameter"]
                for (Object item in Parameter) {
                    mapData.put(item["name"]?.toString(), item["value"]?.toString())
                }
                listObj.add(mapData)
            }
        } catch (Exception ex) {

        }
        return listObj
    }

    @LoggingInOut
    @PostMapping("/run-pam")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse RunPAM(@RequestBody Map<String, Object> body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            def env = post(spsUrl, null,
                    """<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                           <soap:Header>
                            <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                               <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                  <wsse:Username>${username}</wsse:Username>
                                  <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                               </wsse:UsernameToken>
                            </wsse:Security>
                           </soap:Header>
                       <soap:Body>
                          <ins:CreateRequest>
                             <ins:RequestHeader>
                                <ins:NeType>ONLINE</ins:NeType>
                                <ins:OrderNo>1235</ins:OrderNo>
                                <ins:Priority>3</ins:Priority>
                                <ins:ReqUser>aha</ins:ReqUser>
                             </ins:RequestHeader>
                             <ins:RequestParameters>
                                <ins:Parameter name="MSISDN1" value="${body.msisdn}"/>
                                <ins:Parameter name="ACTION" value="RunPeriodicAccountManagement"/>
                                <ins:Parameter name="NEGOTIATEDCAPABILITIES_1" value="268763136"/>
                                <ins:Parameter name="PAM_SERVICE_ID" value="1"/>
                                <ins:Parameter name="PAM_INDICATOR" value="${body.pam}"/>
                                <ins:Parameter name="LL_PRODUCT_NAME" value="TS_OCSAIR_RUNPAM"/>
                                <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                <ins:Parameter name="OPERATION" value="Modify"/>
                             </ins:RequestParameters>
                          </ins:CreateRequest>
                          <ins:DisplayRequest>
                             <ins:RequestParameters/>
                             <ins:RequestHeader>
                                <ins:NeType/>
                             </ins:RequestHeader>
                          </ins:DisplayRequest>
                       </soap:Body>
                        </soap:Envelope>""")
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }



    @LoggingInOut
    @PostMapping("/sps_product_hlr_cf")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse sps_product_hlr_cf(@RequestBody Map<String, Object> body) {
        try {
            def session = genericApi.getSessionData(request);
            String username = getUsername(session);
            String password = getPassword(session);

            String uniqueID = UUID.randomUUID().toString();

            def env = post(spsUrl, null,
                    """
                        <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                <soap:Header>
                      <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                         <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                            <wsse:Username>${username}</wsse:Username>
                            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                         </wsse:UsernameToken>
                      </wsse:Security>
                   </soap:Header>
                   <soap:Body>
                      <ins:CreateRequest>
                         <ins:RequestHeader>
                            <ins:NeType>BST_COMMON</ins:NeType>
                            <ins:OrderNo>${uniqueID}</ins:OrderNo>
                            <ins:Priority>3</ins:Priority>
                            <ins:ReqUser>aha</ins:ReqUser>
                         </ins:RequestHeader>
                         <ins:RequestParameters>
                             <ins:Parameter name="LL_PRODUCT_NAME" value="SPS_PRODUCT_HLR_CF"/>
                             <ins:Parameter name="ITEM_TYPE" value="Product"/>
                             <ins:Parameter name="MSISDN1" value="${body.i_msisdn}"/>
                             <ins:Parameter name="FWDNUM" value="${body.i_fwnum}"/>
                             <ins:Parameter name="NOTE" value="${body.i_note}"/>
                             <ins:Parameter name="SPR_USER" value="${body.i_userid}"/>
                             <ins:Parameter name="SPR_IP" value="${body.i_userip}"/>
                         </ins:RequestParameters>
                      </ins:CreateRequest>
                   </soap:Body>
                </soap:Envelope>
                        """)
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @LoggingInOut
    @PostMapping("/sps_product_hlr_cfnum")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse sps_product_hlr_cfnum(@RequestBody Map<String, Object> body) {
        try {
            def session = genericApi.getSessionData(request);
            String username = getUsername(session);
            String password = getPassword(session);

            String uniqueID = UUID.randomUUID().toString();

            def env = post(spsUrl, null,
                    """
                       <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                <soap:Header>
                      <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                         <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                            <wsse:Username>${username}</wsse:Username>
                            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                         </wsse:UsernameToken>
                      </wsse:Security>
                   </soap:Header>
                   <soap:Body>
                      <ins:CreateRequest>
                         <ins:RequestHeader>
                            <ins:NeType>BST_COMMON</ins:NeType>
                            <ins:OrderNo>${uniqueID}</ins:OrderNo>
                            <ins:Priority>3</ins:Priority>
                            <ins:ReqUser>aha</ins:ReqUser>
                         </ins:RequestHeader>
                         <ins:RequestParameters>
                             <ins:Parameter name="LL_PRODUCT_NAME" value="SPS_PRODUCT_HLR_CFNUM"/>
                             <ins:Parameter name="ITEM_TYPE" value="Product"/>
                             <ins:Parameter name="MSISDN1" value="${body.i_msisdn}"/>
                             <ins:Parameter name="FWDNUM" value="${body.i_fwnum}"/>
                             <ins:Parameter name="NOTE" value="${body.i_note}"/>
                             <ins:Parameter name="SPR_USER" value="${body.i_userid}"/>
                             <ins:Parameter name="SPR_IP" value="${body.i_userip}"/>
                         </ins:RequestParameters>
                      </ins:CreateRequest>
                   </soap:Body>
                </soap:Envelope>
                        """)
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/queryOcsDedicateAccount")
    CommonResponse queryOcsDedicateAccount(@RequestParam String msisdn) {
        try {
            def session = genericApi.getSessionData(request);
            String username = getUsername(session);
            String password = getPassword(session);

            String trace = System.currentTimeMillis().toString()
            def env = post(spsUrl, null,
                    """ <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                           <soap:Header>
                              <wsse:Security soap:mustUnderstand="true"
                                 xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                                    <wsse:UsernameToken wsu:Id="SOAI_req_SOAI"
                                          xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                       <wsse:Username>$username</wsse:Username>
                                       <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">$password</wsse:Password>
                                    </wsse:UsernameToken>
                              </wsse:Security>
                           </soap:Header>
                           <soap:Body>
                              <ins:CreateRequest>
                                    <ins:RequestHeader>
                                       <ins:NeType>ONLINE</ins:NeType>
                                       <ins:OrderNo>$trace</ins:OrderNo>
                                       <ins:Priority>3</ins:Priority>
                                       <ins:ReqUser>onebss-ccbs</ins:ReqUser>
                                    </ins:RequestHeader>
                                    <ins:RequestParameters>
                                       <ins:Parameter name="MSISDN1" value="$msisdn"/>
                                       <ins:Parameter name="ACTION" value="GetBalanceAndDate"/>
                                       <ins:Parameter name="NEGOTIATEDCAPABILITIES_1" value="805642820"/>
                                       <ins:Parameter name="NEGOTIATEDCAPABILITIES_2" value="8104"/>
                                       <ins:Parameter name="LL_PRODUCT_NAME" value="TS_OCSAIR_GETBAL"/>
                                       <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                       <ins:Parameter name="OPERATION" value="Display"/>
                                    </ins:RequestParameters>
                              </ins:CreateRequest>
                              <ins:DisplayRequest>
                                    <ins:RequestParameters/>
                                    <ins:RequestHeader>
                                       <ins:NeType/>
                                    </ins:RequestHeader>
                              </ins:DisplayRequest>
                           </soap:Body>
                        </soap:Envelope>
                     """)
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    //start : kích hoạt dịch vụ
    @PostMapping("/thietlap_luong_active_call_dongthoi")
    CommonResponse thietlap_luong_active_call_dongthoi(@RequestBody Map body) {
        try {
            def session = genericApi.getSessionData(request);
            String username = getUsername(session);
            String password = getPassword(session);

            String trace = System.currentTimeMillis().toString()
            def env = post(spsUrl, null,
                    """ <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                               <soap:Header>
                                  <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                                     <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                        <wsse:Username>${username}</wsse:Username>
                                        <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                                     </wsse:UsernameToken>
                                  </wsse:Security>
                               </soap:Header>
                               <soap:Body>
                                  <ins:CreateRequest>
                                     <ins:RequestHeader>
                                        <ins:NeType>ONLINE</ins:NeType>
                                        <ins:OrderNo>mtas-fix-random-ring-set-total</ins:OrderNo>
                                        <ins:Priority>3</ins:Priority>
                                     </ins:RequestHeader>
                                     <ins:RequestParameters>
                                        <ins:Parameter name="MSISDN" value="${body["msisdn"]}"/>
                                        <ins:Parameter name="MTAS_RES_1" value="${body["mtas_res_1"]}"/>
                                        <ins:Parameter name="SERVICE_ACTION" value="mtas-fix-random-ring-set-total"/>
                                        <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_MODIFY_SUBSCRIBER"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="Modify"/>
                                     </ins:RequestParameters>
                                  </ins:CreateRequest>
                               </soap:Body>
                            </soap:Envelope>
                     """)
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/bat_call_wait_cho_tb_chu")
    CommonResponse bat_call_wait_cho_tb_chu(@RequestBody Map body) {
        try {
            def session = genericApi.getSessionData(request);
            String username = getUsername(session);
            String password = getPassword(session);

            String trace = System.currentTimeMillis().toString()
            def env = post(spsUrl, null,
                    """ <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                               <soap:Header>
                                  <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                                     <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                        <wsse:Username>${username}</wsse:Username>
                                        <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                                     </wsse:UsernameToken>
                                  </wsse:Security>
                               </soap:Header>
                               <soap:Body>
                                  <ins:CreateRequest>
                                     <ins:RequestHeader>
                                        <ins:NeType>ONLINE</ins:NeType>
                                        <ins:OrderNo>mtas-fix-random-ring-cw-active</ins:OrderNo>
                                        <ins:Priority>3</ins:Priority>
                                     </ins:RequestHeader>
                                     <ins:RequestParameters>
                                        <ins:Parameter name="MSISDN" value="${body["msisdn"]}"/>
                                        <ins:Parameter name="SERVICE_ACTION" value="mtas-fix-random-ring-cw-active"/>
                                        <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_MODIFY_SUBSCRIBER"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="Modify"/>
                                     </ins:RequestParameters>
                                  </ins:CreateRequest>
                               </soap:Body>
                            </soap:Envelope>
                     """)
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/cauhinh_dv_fip_cho_tb_con")
    CommonResponse cauhinh_dv_fip_cho_tb_con(@RequestBody Map body) {
        try {
            def session = genericApi.getSessionData(request);
            String username = getUsername(session);
            String password = getPassword(session);

            String trace = System.currentTimeMillis().toString()
            def env = post(spsUrl, null,
                    """ <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                               <soap:Header>
                                  <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                                     <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                        <wsse:Username>${username}</wsse:Username>
                                        <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                                     </wsse:UsernameToken>
                                  </wsse:Security>
                               </soap:Header>
                               <soap:Body>
                                  <ins:CreateRequest>
                                     <ins:RequestHeader>
                                        <ins:NeType>ONLINE</ins:NeType>
                                        <ins:OrderNo>mtas-fix-fip-active</ins:OrderNo>
                                        <ins:Priority>3</ins:Priority>
                                     </ins:RequestHeader>
                                     <ins:RequestParameters>
                                        <ins:Parameter name="MSISDN" value="${body["msisdn"]}"/>
                                        <ins:Parameter name="MTAS_RES_1" value="${body["mtas_res_1"]}"/>
                                        <ins:Parameter name="MTAS_RES_2" value="${body["mtas_res_2"]}"/>
                                        <ins:Parameter name="SERVICE_ACTION" value="mtas-fix-fip-active"/>
                                        <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_MODIFY_SUBSCRIBER"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="Modify"/>
                                     </ins:RequestParameters>
                                  </ins:CreateRequest>
                               </soap:Body>
                            </soap:Envelope>
                     """)
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }
    // end : kích hoạt dịch vụ

    // start : huỷ dịch vụ nhóm
    @PostMapping("/gan_actiive_call_default_cho_chunhom")
    CommonResponse gan_actiive_call_default_cho_chunhom(@RequestBody Map body) {
        try {
            def session = genericApi.getSessionData(request);
            String username = getUsername(session);
            String password = getPassword(session);

            String trace = System.currentTimeMillis().toString()
            def env = post(spsUrl, null,
                    """ <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                               <soap:Header>
                                  <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                                     <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                        <wsse:Username>${username}</wsse:Username>
                                        <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                                     </wsse:UsernameToken>
                                  </wsse:Security>
                               </soap:Header>
                               <soap:Body>
                                  <ins:CreateRequest>
                                     <ins:RequestHeader>
                                        <ins:NeType>ONLINE</ins:NeType>
                                        <ins:OrderNo>mtas-fix-active-call-default</ins:OrderNo>
                                        <ins:Priority>3</ins:Priority>
                                     </ins:RequestHeader>
                                     <ins:RequestParameters>
                                        <ins:Parameter name="MSISDN" value="${body["msisdn"]}"/>
                                        <ins:Parameter name="SERVICE_ACTION" value="mtas-fix-active-call-default"/>
                                        <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_MODIFY_SUBSCRIBER"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="Modify"/>
                                     </ins:RequestParameters>
                                  </ins:CreateRequest>
                               </soap:Body>
                            </soap:Envelope>
                     """)
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/tat_tinhnang_call_wait_cho_tb_chu")
    CommonResponse tat_tinhnang_call_wait_cho_tb_chu(@RequestBody Map body) {
        try {
            def session = genericApi.getSessionData(request);
            String username = getUsername(session);
            String password = getPassword(session);

            String trace = System.currentTimeMillis().toString()
            def env = post(spsUrl, null,
                    """ <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                               <soap:Header>
                                  <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                                     <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                        <wsse:Username>${username}</wsse:Username>
                                        <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                                     </wsse:UsernameToken>
                                  </wsse:Security>
                               </soap:Header>
                               <soap:Body>
                                  <ins:CreateRequest>
                                     <ins:RequestHeader>
                                        <ins:NeType>ONLINE</ins:NeType>
                                        <ins:OrderNo>mtas-fix-random-ring-cw-deactive</ins:OrderNo>
                                        <ins:Priority>3</ins:Priority>
                                     </ins:RequestHeader>
                                     <ins:RequestParameters>
                                        <ins:Parameter name="MSISDN" value="${body["msisdn"]}"/>
                                        <ins:Parameter name="SERVICE_ACTION" value="mtas-fix-random-ring-cw-deactive"/>
                                        <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_MODIFY_SUBSCRIBER"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="Modify"/>
                                     </ins:RequestParameters>
                                  </ins:CreateRequest>
                               </soap:Body>
                            </soap:Envelope>
                     """)
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/huy_cauhinh_fip_tren_cac_tb_con")
    CommonResponse huy_cauhinh_fip_tren_cac_tb_con(@RequestBody Map body) {
        try {
            def session = genericApi.getSessionData(request);
            String username = getUsername(session);
            String password = getPassword(session);

            String trace = System.currentTimeMillis().toString()
            def env = post(spsUrl, null,
                    """ <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                               <soap:Header>
                                  <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                                     <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                        <wsse:Username>${username}</wsse:Username>
                                        <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                                     </wsse:UsernameToken>
                                  </wsse:Security>
                               </soap:Header>
                               <soap:Body>
                                  <ins:CreateRequest>
                                     <ins:RequestHeader>
                                        <ins:NeType>ONLINE</ins:NeType>
                                        <ins:OrderNo>mtas-fix-fip-deactive</ins:OrderNo>
                                        <ins:Priority>3</ins:Priority>
                                     </ins:RequestHeader>
                                     <ins:RequestParameters>
                                        <ins:Parameter name="MSISDN" value="${body["msisdn"]}"/>
                                        <ins:Parameter name="SERVICE_ACTION" value="mtas-fix-fip-deactive"/>
                                        <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_MODIFY_SUBSCRIBER"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="Modify"/>
                                     </ins:RequestParameters>
                                  </ins:CreateRequest>
                               </soap:Body>
                            </soap:Envelope>
                     """)
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }
    // end : huỷ dịch vụ nhóm

    @LoggingInOut
    @PostMapping("/sps_service_ims_7ts")
    CommonResponse sps_service_ims_7ts(@RequestBody SPS_SERVICE_IMS_7TS body) {
        try {
            def session = genericApi.getSessionData(request);
            String username = getUsername(session);
            String password = getPassword(session);
            String phanVungId = session[GenericApi.ID_TINHTHANH]
            String ism_res_3 = lay_thongtin_diachi_ims(phanVungId, body.msisdn, body.ism_res_3, "2")

            String subType = body.subtype == "SIP" ? "SIP-NBA" : "POTS-NBA"
            def env = post(spsUrl, null,
                    """ <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                           <soap:Header>
                            <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                               <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                  <wsse:Username>${username}</wsse:Username>
                                  <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                               </wsse:UsernameToken>
                            </wsse:Security>
                           </soap:Header>
                           <soap:Body>
                              <ins:DisplayRequest>
                                 <ins:RequestHeader>
                                    <ins:NeType>ONLINE</ins:NeType>
                                    <ins:OrderNo>9999</ins:OrderNo>
                                    <ins:Priority>3</ins:Priority>
                                    <ins:ReqUser>ccbs_vnp</ins:ReqUser>
                                 </ins:RequestHeader>
                                 <ins:RequestParameters>
                                    <ins:Parameter name="MSISDN1" value="${body.msisdn}"/>
                                    <ins:Parameter name="IMSI" value="${body.imsi}"/>
                                    <ins:Parameter name="ISM_SUBTYPE" value="$subType"/>
                                    <ins:Parameter name="IPW_SUBTYPE" value="${body.subtype}"/>
                                    <ins:Parameter name="MTAS_SUBTYPE" value="${body.subtype}"/>
                                    <ins:Parameter name="ISM_RES_3" value="$ism_res_3"/>
                                    <ins:Parameter name="ISM_RES_4" value="${body.line}"/>
                                    <ins:Parameter name="LL_PRODUCT_NAME" value="SPS_SERVICE_IMS_NBA"/>
                                    <ins:Parameter name="CHARGING_PROFILE" value="FixOfflineCharging"/>
                                    <ins:Parameter name="ITEM_TYPE" value="Service"/>
                                    <ins:Parameter name="OPERATION" value="Create"/>
                                 </ins:RequestParameters>
                              </ins:DisplayRequest>
                           </soap:Body>
                        </soap:Envelope>
                     """)
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/ims_change_line_nba")
    CommonResponse ims_change_line_nba(@RequestBody SPS_SERVICE_IMS_7TS body) {
        try {
            def session = genericApi.getSessionData(request);
            String username = getUsername(session);
            String password = getPassword(session);

            String serviceAction = body.subtype == "SIP" ? "ism-sip-change-lineid" : "ism-pots-change-lineid"
            def env = post(spsUrl, null,
                    """ <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                               <soap:Header>
                                  <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                                     <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                        <wsse:Username>${username}</wsse:Username>
                                        <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                                     </wsse:UsernameToken>
                                  </wsse:Security>
                               </soap:Header>
                               <soap:Body>
                                  <ins:CreateRequest>
                                     <ins:RequestHeader>
                                        <ins:NeType>ONLINE</ins:NeType>
                                        <ins:OrderNo>1235</ins:OrderNo>
                                        <ins:Priority>3</ins:Priority>
                                        <ins:ReqUser>aha</ins:ReqUser>
                                     </ins:RequestHeader>
                                     <ins:RequestParameters>
                                        <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
                                        <ins:Parameter name="SERVICE_ACTION" value="$serviceAction"/>
                                        <ins:Parameter name="ISM_RES_4" value="${body.line}"/>
                                        <ins:Parameter name="LL_PRODUCT_NAME" value="TS_ISM_MODIFY_SUBSCRIBER"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="Modify"/>
                                     </ins:RequestParameters>
                                  </ins:CreateRequest>
                               </soap:Body>
                            </soap:Envelope>
                     """)
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    String lay_thongtin_diachi_ims(String phanVungId, String msisdn, String ism_res_3, String type) {
        String result = ism_res_3
        if (ism_res_3 == null || ism_res_3.trim() == "") {
            try {
                hopDongService.init(request.getHeader("Authorization"))
                String maTb = ""
                if (phanVungId == "21" || phanVungId == "28") {
                    maTb = msisdn.substring(4)
                } else {
                    maTb = msisdn.substring(5)
                }
                result = hopDongService.lay_thongtin_diachi_ims(maTb, type)
            } catch (Exception ex) {
            }
        }
        result
    }

    @LoggingInOut
    @PostMapping("/ims_change_to_nba")
    CommonResponse ims_change_to_nba(@RequestBody SPS_SERVICE_IMS_7TS body) {
        try {
            def session = genericApi.getSessionData(request);
            String username = getUsername(session);
            String password = getPassword(session);
            String phanVungId = session[GenericApi.ID_TINHTHANH]
            String ism_res_3 = lay_thongtin_diachi_ims(phanVungId, body.msisdn, body.ism_res_3, "1")

            String serviceAction = body.subtype == "SIP" ? "ism-sip-convert-digest-nba" : "ism-pots-convert-digest-nba"
            def env = post(spsUrl, null,
                    """ <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                               <soap:Header>
                                  <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                                     <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                        <wsse:Username>${username}</wsse:Username>
                                        <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                                     </wsse:UsernameToken>
                                  </wsse:Security>
                               </soap:Header>
                               <soap:Body>
                                  <ins:CreateRequest>
                                     <ins:RequestHeader>
                                        <ins:NeType>ONLINE</ins:NeType>
                                        <ins:OrderNo>1235</ins:OrderNo>
                                        <ins:Priority>3</ins:Priority>
                                        <ins:ReqUser>aha</ins:ReqUser>
                                     </ins:RequestHeader>
                                     <ins:RequestParameters>
                                        <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
                                        <ins:Parameter name="SERVICE_ACTION" value="$serviceAction"/>
                                        <ins:Parameter name="ISM_RES_3" value="$ism_res_3"/>
                                        <ins:Parameter name="ISM_RES_4" value="${body.line}"/>
                                        <ins:Parameter name="LL_PRODUCT_NAME" value="TS_ISM_MODIFY_SUBSCRIBER"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="Modify"/>
                                     </ins:RequestParameters>
                                  </ins:CreateRequest>
                               </soap:Body>
                            </soap:Envelope>
                     """)
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }



    @LoggingInOut
    @PostMapping("/ims_change_to_digest")
    CommonResponse ims_change_to_digest(@RequestBody SPS_SERVICE_IMS body) {
        try {
            def session = genericApi.getSessionData(request);
            String username = getUsername(session);
            String password = getPassword(session);

            String serviceAction = body.subtype == "SIP" ? "ism-sip-convert-nba-digest" : "ism-pots-convert-nba-digest"
            def env = post(spsUrl, null,
                    """ <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                               <soap:Header>
                                  <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                                     <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                        <wsse:Username>${username}</wsse:Username>
                                        <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                                     </wsse:UsernameToken>
                                  </wsse:Security>
                               </soap:Header>
                               <soap:Body>
                                  <ins:CreateRequest>
                                     <ins:RequestHeader>
                                        <ins:NeType>ONLINE</ins:NeType>
                                        <ins:OrderNo>1235</ins:OrderNo>
                                        <ins:Priority>3</ins:Priority>
                                        <ins:ReqUser>aha</ins:ReqUser>
                                     </ins:RequestHeader>
                                     <ins:RequestParameters>
                                        <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
                                        <ins:Parameter name="SERVICE_ACTION" value="$serviceAction"/>
                                        <ins:Parameter name="USER_PASSWORD" value="${body.user_password}"/>
                                        <ins:Parameter name="LL_PRODUCT_NAME" value="TS_ISM_MODIFY_SUBSCRIBER"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="Modify"/>
                                     </ins:RequestParameters>
                                  </ins:CreateRequest>
                               </soap:Body>
                            </soap:Envelope>
                     """)
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

}
