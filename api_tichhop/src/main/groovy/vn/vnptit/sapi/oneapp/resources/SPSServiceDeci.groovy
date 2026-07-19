package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
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
import vn.vnptit.sapi.oneapp.models.sps.SPS_AARANET_ACCOUNT
import vn.vnptit.sapi.oneapp.models.sps.SPS_AARANET_ACCOUNT_DELETE
import vn.vnptit.sapi.oneapp.models.sps.SPS_AARANET_ADDRESS
import vn.vnptit.sapi.oneapp.models.sps.SPS_AARANET_ADDRESS_DELETE
import vn.vnptit.sapi.oneapp.models.sps.SPS_AARANET_CHAN_GOIDEN_KHONGMONGMUON
import vn.vnptit.sapi.oneapp.models.sps.SPS_AARANET_DANGKY_HANMUCCUOC
import vn.vnptit.sapi.oneapp.models.sps.SPS_AARANET_DIEUCHINH_HANMUCCUOC
import vn.vnptit.sapi.oneapp.models.sps.SPS_AARANET_KHOA_GOIDI_QUOCTE
import vn.vnptit.sapi.oneapp.models.sps.SPS_AARANET_KHOA_GOI_ACC_RULESETS
import vn.vnptit.sapi.oneapp.models.sps.SPS_AARANET_MO_CHAN_GOIDEN_KHONGMONGMUON_THEO_TB
import vn.vnptit.sapi.oneapp.models.sps.SPS_AARANET_PROFILE
import vn.vnptit.sapi.oneapp.models.sps.SPS_AARANET_PROFILE_ACCOUNT
import vn.vnptit.sapi.oneapp.models.sps.SPS_AARANET_PROFILE_DELETE
import vn.vnptit.sapi.oneapp.models.sps.SPS_AARANET_TAMNGUNG
import vn.vnptit.sapi.oneapp.models.sps.SPS_AARANET_THAYDOI_SOKENH_CUOCGOI_DONGTHOI
import vn.vnptit.sapi.oneapp.models.sps.SPS_DELETE_SIPTRUNK_ISM
import vn.vnptit.sapi.oneapp.models.sps.SPS_PRODUCT_MTA
import vn.vnptit.sapi.oneapp.models.sps.SPS_PRODUCT_NAME_ONLY
import vn.vnptit.sapi.oneapp.models.sps.SPS_PRODUCT_ROUTING_TABLE
import vn.vnptit.sapi.oneapp.models.sps.SPS_PRODUCT_RULE_SETS
import vn.vnptit.sapi.oneapp.models.sps.SPS_PRODUCT_SERVICE_ACTION
import vn.vnptit.sapi.oneapp.models.sps.SPS_PRODUCT_SUBTYPE
import vn.vnptit.sapi.oneapp.models.sps.TraCuuThongTin
import vn.vnptit.sapi.oneapp.util.GenericApi

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import org.json.JSONObject;
import org.json.JSONException;
import org.json.XML;

@RestController
@RequestMapping("sps")
class SPSServiceDeci {
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

    @Value("\${spring.defaultconfigs.sps-accounts}")
    String spsAccounts

    @Value("\${spring.defaultconfigs.sps-url-new}")
    String spsUrl

    static Map<String, Map<String, String>> cachedAcc = null

    void loadSPSAccountsMap() {
        cachedAcc = [:]
        spsAccounts.split(',').each {
            String[] accRec = it.split(":", 3)
            cachedAcc.put(accRec[0], ["username": accRec[1], "password": accRec[2]])
        }
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

    String post(String url, Map<String, String> headers, String body) {
        if (headers == null) headers = [:]
        headers.put("Content-Type", "text/xml;charset=UTF-8")
        String resp = httpManaged.post(["url": url], headers, body)
        return resp
    }

    String xml_add_param(String soap_header, String request_header, String request_param, String option) {
        if (request_param == null) request_param = ""
        if (soap_header == null) soap_header = ""
        if (request_header == null) request_header = """<ins:NeType>ONLINE</ins:NeType>
                                                            <ins:OrderNo>9999</ins:OrderNo>
                                                            <ins:Priority>3</ins:Priority>
                                                            <ins:ReqUser>aha</ins:ReqUser>
                                                            """
        String soap_template = ""
        if (option == 'create_request' && option != null) {
            soap_template = """
                <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
                    <soap:Header>
                        ${soap_header}
                    </soap:Header>
                    <soap:Body>
                        <ins:CreateRequest>
                            <ins:RequestHeader>
                                ${request_header}
                            </ins:RequestHeader>
                            <ins:RequestParameters>
                                ${request_param}
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
                                """
            return soap_template
        }
        soap_template = """
            <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
               <soap:Header>
                ${soap_header}
               </soap:Header>
               <soap:Body>
                  <ins:DisplayRequest>
                     <ins:RequestHeader>
                        ${request_header}
                     </ins:RequestHeader>
                     <ins:RequestParameters>
                        ${request_param}
                     </ins:RequestParameters>
                  </ins:DisplayRequest>
               </soap:Body>
            </soap:Envelope>
        """
        return soap_template
    }

    String get_soap_header() {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            String soap_header = """ <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                               <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
                                  <wsse:Username>${username}</wsse:Username>
                                  <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
                               </wsse:UsernameToken>
                            </wsse:Security>"""
            return soap_header
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/traCuuThongTinMTAS_test")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse traCuuThongTinMTAS_test(@Valid @RequestBody TraCuuThongTin body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>1235</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """
                                  <ins:Parameter name="SUBTYPE" value="SIP"/>
                                  <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_DISPLAY_SUBSCRIBER"/>
                                  <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                  <ins:Parameter name="OPERATION" value="Display"/>"""
            if (body.msisdn) {
                request_param += """<ins:Parameter name="MSISDN" value="${body.msisdn}"/>"""
            }
//            print(request_param)
//            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "")
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: env)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/get_by_prn_only")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_by_prn_only(@Valid @RequestBody SPS_PRODUCT_NAME_ONLY body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="MSISDN" value="${body.msisdn}"/>
                                  <ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                  <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                  <ins:Parameter name="OPERATION" value="${body.operation}"/>"""
            if (body.msisdn) {
                request_param += """<ins:Parameter name="SUBTYPE" value="${body.subtype}"/>"""
            } else {
                request_param += """<ins:Parameter name="SUBTYPE" value="SIP"/>"""
            }
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/get_by_prn_sva")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_by_prn_sva(@Valid @RequestBody SPS_PRODUCT_SERVICE_ACTION body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="MSISDN" value="${body.msisdn}"/>
                                        <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                        <ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="${body.operation}"/>"""
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/get_by_prn_sva_res")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_by_prn_sva_res(@Valid @RequestBody SPS_PRODUCT_SERVICE_ACTION body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="MSISDN" value="${body.msisdn}"/>
                                    <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                    <ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                    <ins:Parameter name="ISM_RES_3" value="${body.ism_res_3}"/>
                                    <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                    <ins:Parameter name="OPERATION" value="${body.operation}"/>
                                    """
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/get_by_prn_sva_abbr")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_by_prn_sva_abbr(@Valid @RequestBody SPS_PRODUCT_SERVICE_ACTION body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="MSISDN" value="${body.msisdn}"/>
                                    <ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                    <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                    <ins:Parameter name="ABBR_NUMBER" value="${body.abbr_number}"/>
                                    <ins:Parameter name="STORED_NUMBER" value="${body.stored_number}"/>
                                    <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                    <ins:Parameter name="OPERATION" value="${body.operation}"/>"""
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/get_by_prn_sva_cfu")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_by_prn_sva_cfu(@Valid @RequestBody SPS_PRODUCT_SERVICE_ACTION body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="MSISDN" value="${body.msisdn}"/>
                                    <ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                    <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                    <ins:Parameter name="CFU_NUMBER" value="${body.cfu_number}"/>
                                    <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                    <ins:Parameter name="OPERATION" value="${body.operation}"/>"""
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/get_by_prn_sva_cfb")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_by_prn_sva_cfb(@Valid @RequestBody SPS_PRODUCT_SERVICE_ACTION body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="MSISDN" value="${body.msisdn}"/>
                                    <ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                    <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                    <ins:Parameter name="CFB_NUMBER" value="${body.cfb_number}"/>
                                    <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                    <ins:Parameter name="OPERATION" value="${body.operation}"/>"""
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/get_by_prn_sva_cfnry")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_by_prn_sva_cfnry(@Valid @RequestBody SPS_PRODUCT_SERVICE_ACTION body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="MSISDN" value="${body.msisdn}"/>
                                    <ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                    <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                    <ins:Parameter name="CFNRY_NUMBER" value="${body.cfnry_number}"/>
                                    <ins:Parameter name="NOREPLY_TIMER" value="${body.noreply_timer}"/>
                                    <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                    <ins:Parameter name="OPERATION" value="${body.operation}"/>"""
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/get_by_prn_sva_sub")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_by_prn_sva_sub(@Valid @RequestBody SPS_PRODUCT_SERVICE_ACTION body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                        <ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="${body.operation}"/>"""
            if (body.msisdn) {
                request_param += """<ins:Parameter name="MSISDN" value="${body.msisdn}"/>"""
            }
            if (body.cfnry_number) {
                request_param += """<ins:Parameter name="CFNRY_NUMBER" value="${body.cfnry_number}"/>"""
            }
            if (body.noreply_timer) {
                request_param += """<ins:Parameter name="NOREPLY_TIMER" value="${body.noreply_timer}"/>"""
            }
            if (body.cfb_number) {
                request_param += """<ins:Parameter name="CFB_NUMBER" value="${body.cfb_number}"/>"""
            }
            if (body.number_1x00) {
                request_param += """<ins:Parameter name="NUMBER_1X00" value="${body.number_1x00}"/>"""
            }
            if (body.scf_address) {
                request_param += """<ins:Parameter name="SCF_ADDRESS" value="${body.scf_address}"/>"""
            }
            if (body.orig_service_key) {
                request_param += """<ins:Parameter name="ORIG_SERVICE_KEY" value="${body.orig_service_key}"/>"""
            }
            if (body.term_service_key) {
                request_param += """<ins:Parameter name="TERM_SERVICE_KEY" value="${body.term_service_key}"/>"""
            }
            if (body.call_handling) {
                request_param += """<ins:Parameter name="CALL_HANDLING" value="${body.call_handling}"/>"""
            }
            if (body.cfc10_number) {
                request_param += """<ins:Parameter name="CFC10_NUMBER" value="${body.cfc10_number}"/>"""
            }
            if (body.block_list) {
                request_param += """<ins:Parameter name="BLOCK_LIST" value="${body.block_list}"/>"""
            }
            if (body.snd1) {
                request_param += """<ins:Parameter name="SND1" value="${body.snd1}"/>"""
            }
            if (body.groupname1) {
                request_param += """<ins:Parameter name="GROUPNAME1" value="${body.groupname1}"/>"""
            }
            if (body.snd2) {
                request_param += """<ins:Parameter name="SND2" value="${body.snd2}"/>"""
            }
            if (body.groupname2) {
                request_param += """<ins:Parameter name="GROUPNAME2" value="${body.groupname2}"/>"""
            }
            if (body.seq_ring_list) {
                request_param += """<ins:Parameter name="SEQ_RING_LIST" value="${body.seq_ring_list}"/>"""
            }
            if (body.ring_period) {
                request_param += """<ins:Parameter name="RING_PERIOD" value="${body.ring_period}"/>"""
            }
            if (body.sim_ring_list) {
                request_param += """<ins:Parameter name="SIM_RING_LIST" value="${body.sim_ring_list}"/>"""
            }
            if (body.mtas_res_1) {
                request_param += """<ins:Parameter name="MTAS_RES_1" value="${body.mtas_res_1}"/>"""
            }
            if (body.mtas_res_5) {
                request_param += """<ins:Parameter name="MTAS_RES_5" value="${body.mtas_res_5}"/>"""
            }
            if (body.ism_res_1) {
                request_param += """<ins:Parameter name="ISM_RES_1" value="${body.ism_res_1}"/>"""
            }
            if (body.mtas_res_3) {
                request_param += """<ins:Parameter name="MTAS_RES_3" value="${body?.mtas_res_3}"/>"""
            }
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/create_siptrunk_mtas")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_by_prn_subtype(@Valid @RequestBody SPS_PRODUCT_SUBTYPE body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="SUBTYPE" value="${body.sub_type}"/>
                                        <ins:Parameter name="MTAS_RES_1" value="${body.mtas_res_1}"/>
                                        <ins:Parameter name="MTAS_RES_2" value="${body.mtas_res_2}"/>
                                        <ins:Parameter name="MTAS_RES_3" value="${body.mtas_res_3}"/>
                                        <ins:Parameter name="MTAS_RES_4" value="${body.mtas_res_4}"/>
                                        <ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="${body.operation}"/>"""
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/get_by_prn_subtype_extend")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_by_prn_subtype_sub(@Valid @RequestBody SPS_PRODUCT_SUBTYPE body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                      <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                      <ins:Priority>3</ins:Priority>
                                      <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="SUBTYPE" value="${body.sub_type}"/>
                                        <ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="${body.operation}"/>"""
            if (body.msisdn) {
                request_param += """<ins:Parameter name="MSISDN" value="${body.msisdn}"/>"""
            }
            if (body.ism_res_1) {
                request_param += """<ins:Parameter name="ISM_RES_1" value="${body.ism_res_1}"/>"""
            }
            if (body.ism_res_2) {
                request_param += """<ins:Parameter name="ISM_RES_2" value="${body.ism_res_2}"/>"""
            }
            if (body.imsi) {
                request_param += """<ins:Parameter name="IMSI" value="${body.imsi}"/>"""
            }
            if (body.charging_prof_id) {
                request_param += """<ins:Parameter name="CHARGING_PROF_ID" value="${body.charging_prof_id}"/>"""
            }
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/cancel_siptrunk_mtas")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_by_prn_mta(@Valid @RequestBody SPS_PRODUCT_MTA body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="MTAS_RES_1" value="${body.mtas_res_1}"/>
                                        <ins:Parameter name="MTAS_RES_2" value="${body.mtas_res_2}"/>
                                        <ins:Parameter name="MSISDN" value="${body?.msisdn}"/>
                                        <ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="${body.operation}"/>
                                        """
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/get_by_prn_routing_table")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_by_prn_routing_table(@Valid @RequestBody SPS_PRODUCT_ROUTING_TABLE body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                        <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                        <ins:Parameter name="ACC_NAME" value="${body.acc_name}"/>
                                        <ins:Parameter name="ACC_ROUTING_TABLE" value="${body.acc_routing_table}"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="${body.operation}"/>
                                                                                """
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/get_by_prn_rule_sets")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_by_prn_rule_sets(@Valid @RequestBody SPS_PRODUCT_RULE_SETS body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                        <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                        <ins:Parameter name="ACC_NAME" value="${body.acc_name}"/>
                                        <ins:Parameter name="ACC_RULESETS" value="${body.acc_rulesets}"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="${body.operation}"/>"""
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/aaranet_create_profile")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse aaranet_create_profile(@Valid @RequestBody SPS_AARANET_PROFILE body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                        <ins:Parameter name="PROFILE_NAME" value="${body.profile_name}"/>
                                        <ins:Parameter name="PROFILE_INFO" value="${body.profile_info}"/>
                                        <ins:Parameter name="PROFILE_SIP_CONTACT" value="${body.profile_sip_contact}"/>
                                        <ins:Parameter name="PROFILE_ENDPOINT" value="${body.profile_endpoint}"/>
                                        <ins:Parameter name="PROFILE_USER_AGENT" value="${body.profile_user_agent}"/>
                                        <ins:Parameter name="PROFILE_Q" value="${body.profile_q}"/>
                                        <ins:Parameter name="PROFILE_GROUP" value="${body.profile_group}"/>
                                        <ins:Parameter name="PROFILE_AUTH" value="${body.profile_auth}"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="Create"/>"""
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/aaranet_delete_profile")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse aaranet_delete_profile(@Valid @RequestBody SPS_AARANET_PROFILE_DELETE body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                        <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                        <ins:Parameter name="PROFILE_NAME" value="${body.profile_name}"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="Delete"/>"""
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/aaranet_create_account")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse aaranet_create_account(@Valid @RequestBody SPS_AARANET_ACCOUNT body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                        <ins:Parameter name="ACC_NAME" value="${body.acc_name}"/>
                                        <ins:Parameter name="ACC_INFO" value="${body.acc_info}"/>
                                        <ins:Parameter name="ACC_MAX_CHANNELS" value="${body.acc_max_channels}"/>
                                        <ins:Parameter name="ACC_ROUTING_TABLE" value="${body.acc_routing_table}"/>
                                        <ins:Parameter name="ACC_USERNAME" value="${body.acc_username}"/>
                                        <ins:Parameter name="ACC_PASSWORD" value="${body.acc_password}"/>
                                        <ins:Parameter name="ACC_TENANT" value="${body.acc_tenant}"/>
                                        <ins:Parameter name="ACC_RULESETS" value="${body.acc_rulesets}"/>
                                        <ins:Parameter name="ACC_EMERGENCY_LOCATION" value="${body.acc_emergency_location}"/>
                                        <ins:Parameter name="ACC_USE_MEDIA_SERVER" value="${body.acc_use_media_server}"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="Create"/>"""
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/aaranet_delete_account")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse aaranet_delete_account(@Valid @RequestBody SPS_AARANET_ACCOUNT_DELETE body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                        <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                        <ins:Parameter name="ACC_NAME" value="${body.acc_name}"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="Delete"/>"""
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/aaranet_mapping_profile_account")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse aaranet_mapping_profile_account(@Valid @RequestBody SPS_AARANET_PROFILE_ACCOUNT body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                        <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                        <ins:Parameter name="ACC_NAME" value="${body.acc_name}"/>
                                        <ins:Parameter name="PROFILE_NAME" value="${body.profile_name}"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="Modify"/>"""
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/aaranet_add_address")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse aaranet_add_address(@Valid @RequestBody SPS_AARANET_ADDRESS body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                        <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                        <ins:Parameter name="ADDR_DOMAIN" value="${body.addr_domain}"/>
                                        <ins:Parameter name="ADDR_NUMBER" value="${body.addr_number}"/>
                                        <ins:Parameter name="ADDR_LANGUAGE" value="${body.addr_language}"/>
                                        <ins:Parameter name="ACC_NAME" value="${body.acc_name}"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="Modify"/>"""
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/aaranet_remove_address")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse AARANET_REMOVE_ADDRESS(@Valid @RequestBody SPS_AARANET_ADDRESS_DELETE body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                        <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                        <ins:Parameter name="ADDR_NUMBER" value="${body.addr_number}"/>
                                        <ins:Parameter name="ACC_NAME" value="${body.acc_name}"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="Delete"/>"""
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/aaranet_tamngung")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse AARANET_REMOVE_ADDRESS(@Valid @RequestBody SPS_AARANET_TAMNGUNG body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                        <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                        <ins:Parameter name="ACC_NAME" value="${body.acc_name}"/>
                                        <ins:Parameter name="ACC_VALID_AFTER" value="${body.acc_valid_after}"/>
                                        <ins:Parameter name="ACC_VALID_UNTIL" value="${body.acc_valid_until}"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="${body.operation}"/>"""
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/aaranet_khoa_goidi_quocte")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse AARANET_REMOVE_ADDRESS(@Valid @RequestBody SPS_AARANET_KHOA_GOIDI_QUOCTE body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                    <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                    <ins:Parameter name="ACC_NAME" value="${body.acc_name}"/>
                                    <ins:Parameter name="ACC_ROUTING_TABLE" value="${body.acc_routing_table}"/>
                                    <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                    <ins:Parameter name="OPERATION" value="${body.operation}"/>
                                    """
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/aaranet_khoa_goi_acc_rulesets")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse AARANET_REMOVE_ADDRESS(@Valid @RequestBody SPS_AARANET_KHOA_GOI_ACC_RULESETS body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                        <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                        <ins:Parameter name="ACC_NAME" value="${body.acc_name}"/>
                                        <ins:Parameter name="ACC_RULESETS" value="${body.acc_rulesets}"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="${body.operation}"/>"""
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/aaranet_chan_goiden_khongmongmuon")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse AARANET_REMOVE_ADDRESS(@Valid @RequestBody SPS_AARANET_CHAN_GOIDEN_KHONGMONGMUON body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                        <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                        <ins:Parameter name="ACC_NAME" value="${body.acc_name}"/>
                                        <ins:Parameter name="ACC_BLOCK_LIST" value="${body.acc_block_list}"/>
                                        <ins:Parameter name="ACC_BLOCK_DEST_PATTERN" value="${body.acc_block_dest_pattern}"/>
                                        <ins:Parameter name="ACC_BLOCK_TIME_PATTERN" value="${body.acc_block_time_pattern}"/>
                                        <ins:Parameter name="ACC_BLOCK_DEST_REPLACE" value="${body.acc_block_dest_replace}"/>
                                        <ins:Parameter name="ACC_BLOCK_PRIORITY" value="${body.acc_block_priority}"/>
                                        <ins:Parameter name="ACC_BLOCK_TYPE" value="${body.acc_block_type}"/>
                                        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                        <ins:Parameter name="OPERATION" value="${body.operation}"/>"""
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/aaranet_mo_chan_goiden_khongmongmuon_theo_tb")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse AARANET_REMOVE_ADDRESS(@Valid @RequestBody SPS_AARANET_MO_CHAN_GOIDEN_KHONGMONGMUON_THEO_TB body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                    <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                    <ins:Parameter name="ACC_NAME" value="${body.acc_name}"/>
                                    <ins:Parameter name="ACC_BLOCK_NAME" value="${body.acc_block_name}"/>
                                    <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                    <ins:Parameter name="OPERATION" value="${body.operation}"/>
                                    """
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/aaranet_thaydoi_sokenh_cuocgoi_dongthoi")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse AARANET_REMOVE_ADDRESS(@Valid @RequestBody SPS_AARANET_THAYDOI_SOKENH_CUOCGOI_DONGTHOI body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                    <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                    <ins:Parameter name="ACC_NAME" value="${body.acc_name}"/>
                                    <ins:Parameter name="ACC_MAX_CHANNELS" value="${body.acc_max_channels}"/>
                                    <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                    <ins:Parameter name="OPERATION" value="${body.operation}"/>"""
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/aaranet_dangky_hanmuccuoc")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse AARANET_REMOVE_ADDRESS(@Valid @RequestBody SPS_AARANET_DANGKY_HANMUCCUOC body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                    <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                    <ins:Parameter name="ACC_NAME" value="${body.acc_name}"/>
                                    <ins:Parameter name="ACC_TOPSTOP_TYPE" value="${body.acc_topstop_type}"/>
                                    <ins:Parameter name="ACC_TOPSTOP_MAX" value="${body.acc_topstop_max}"/>
                                    <ins:Parameter name="ACC_TOPSTOP_ALARM_LEVEL" value="${body.acc_topstop_alarm_level}"/>
                                    <ins:Parameter name="ACC_TOPSTOP_ALARM_EMAIL" value="${body.acc_topstop_alarm_email}"/>
                                    <ins:Parameter name="ACC_TOPSTOP_MONTHLY_RESET" value="${body.acc_topstop_monthly_reset}"/>
                                    <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                    <ins:Parameter name="OPERATION" value="${body.operation}"/>"""
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/aaranet_dieuchinh_hanmuccuoc")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse AARANET_REMOVE_ADDRESS(@Valid @RequestBody SPS_AARANET_DIEUCHINH_HANMUCCUOC body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                  <ins:OrderNo>${body.order_no}</ins:OrderNo>
                                  <ins:Priority>3</ins:Priority>
                                  <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="LL_PRODUCT_NAME" value="${body.ll_product_name}"/>
                                    <ins:Parameter name="SERVICE_ACTION" value="${body.service_action}"/>
                                    <ins:Parameter name="ACC_NAME" value="${body.acc_name}"/>
                                    <ins:Parameter name="ACC_TOPSTOP_TYPE" value="${body.acc_topstop_type}"/>
                                    <ins:Parameter name="ACC_TOPSTOP_MAX" value="${body.acc_topstop_max}"/>
                                    <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                    <ins:Parameter name="OPERATION" value="${body.operation}"/>"""
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/delete_siptrunk_ism")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse delete_siptrunk_ism(@Valid @RequestBody SPS_DELETE_SIPTRUNK_ISM body) {
        try {
            String soap_header = get_soap_header()
            String request_header = """<ins:NeType>ONLINE</ins:NeType>
                                <ins:OrderNo>1235</ins:OrderNo>
                                <ins:Priority>3</ins:Priority>
                                <ins:ReqUser>aha</ins:ReqUser>"""
            String request_param = """<ins:Parameter name="MSISDN" value="${body.msisdn}"/>
                                <ins:Parameter name="LL_PRODUCT_NAME" value="TS_ISM_DELETE_SUBSCRIBER"/>
                                <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                                <ins:Parameter name="OPERATION" value="Delete"/>
                                </ins:RequestParameters>
                                </ins:CreateRequest>
                                <ins:DisplayRequest>"""
            def env = post(spsUrl, null, xml_add_param(soap_header, request_header, request_param, 'create_request'))
            def result = new JsonSlurper().parseText(XML.toJSONObject(env).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: _return)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }
}
