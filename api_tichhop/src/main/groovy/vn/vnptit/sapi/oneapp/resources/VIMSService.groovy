package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
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
import vn.vnptit.sapi.oneapp.models.vims.ThemDaiSoReq
import vn.vnptit.sapi.oneapp.models.vims.KhoaMoGoi1900Req
import vn.vnptit.sapi.oneapp.models.vims.DeleteSubReq
import vn.vnptit.sapi.oneapp.models.vims.TangKenhCuocGoiReq
import vn.vnptit.sapi.oneapp.models.vims.KhoaMoChieuDiReq
import vn.vnptit.sapi.oneapp.models.vims.ShowHssReq
import vn.vnptit.sapi.oneapp.models.vims.KhoaMoGoiDiQuocTeReq
import vn.vnptit.sapi.oneapp.models.vims.ShowMtasServiceReq
import vn.vnptit.sapi.oneapp.models.vims.ShowMtasSipReq
import vn.vnptit.sapi.oneapp.models.vims.ShowMtasTelReq
import vn.vnptit.sapi.oneapp.models.vims.ThemServerBackupReq
import vn.vnptit.sapi.oneapp.models.vims.TamNgungReq
import vn.vnptit.sapi.oneapp.models.vims.ChanBoChanReq
import vn.vnptit.sapi.oneapp.models.vims.ThemReferralReq
import vn.vnptit.sapi.oneapp.models.vims.XoaDaiSoHssReq
import vn.vnptit.sapi.oneapp.models.vims.XoaMtasServiceReq
import vn.vnptit.sapi.oneapp.models.vims.XoaReferralMtas2tbReq
import vn.vnptit.sapi.oneapp.models.vims.XoaDaiSoMtasReq
import vn.vnptit.sapi.oneapp.models.vims.ThayDoiIpReq
import vn.vnptit.sapi.oneapp.models.vims.CreateHssReq
import vn.vnptit.sapi.oneapp.models.vims.CreateMtasReq
import vn.vnptit.sapi.oneapp.models.vims.KhoaMo1080Req
import vn.vnptit.sapi.oneapp.models.vims.XoaHssReq
import vn.vnptit.sapi.oneapp.service.HopDongService
import vn.vnptit.sapi.oneapp.util.GenericApi
import vn.vnptit.sapi.oneapp.util.LoggingInOut
import org.json.XML
import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("/vims")
class VIMSService {

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
            // Thêm kiểm tra để đảm bảo có đủ 3 phần tử trước khi sử dụng
            if (accRec.length == 3) {
                cachedAcc.put(accRec[0], ["username": accRec[1], "password": accRec[2]])
            } else {
                // Có thể log cảnh báo nếu cần để thông báo về dữ liệu không hợp lệ
                println "Invalid sps account entry: $it"
            }
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

    String post(String url, Map<String, String> headers, String body) {
        if (headers == null) headers = [:]
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

    //tạo thuê bao trên HSS/ISM với IP cố định.
    @LoggingInOut
    @PostMapping("/create_hss")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse createHSS(@Valid @RequestBody CreateHssReq body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def soapBody = """
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
                <ins:NeType>ONLINE</ins:NeType>
                <ins:OrderNo>TS_ISM_CREATE_SUBSCRIBER</ins:OrderNo>
                <ins:Priority>3</ins:Priority>
                <ins:ReqUser>${body.reqUser ?: 'testing'}</ins:ReqUser>
            </ins:RequestHeader>
            <ins:RequestParameters>
                <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
                <ins:Parameter name="IMSI" value="${body.imsi ?: body.msisdn}"/>
                <ins:Parameter name="ISM_RES_1" value="${body.ismRes1 ?: 'hnitest'}"/>
                <ins:Parameter name="ISM_RES_2" value="${body.ismRes2 ?: 'HNI'}"/>
                <ins:Parameter name="SUBTYPE" value="${body.subtype ?: 'SIPTRUNK'}"/>
                <ins:Parameter name="CHARGING_PROF_ID" value="${body.chargingProfId ?: 'FixOfflineCharging'}"/>
                <ins:Parameter name="LL_PRODUCT_NAME" value="TS_ISM_CREATE_SUBSCRIBER"/>
                <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
                <ins:Parameter name="OPERATION" value="Create"/>
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
            def result = new JsonSlurper().parseText(XML.toJSONObject(post(spsUrl, null, soapBody)).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)[0]

            // Kiểm tra StatusMessage để xác định thành công hay thất bại
            if (_return?.StatusMessage?.toString()?.contains('00000|') && _return?.Status?.toString() == '9') {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Tạo HSS thành công.", data: _return)
            } else {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Tạo HSS thất bại.", data: _return)
            }

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/create_mtas_fix_ip")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse createMtasFixIp(@Valid @RequestBody CreateMtasReq body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def subtypes = ["SIPTRUNK11", "SIPTRUNK12", "SIPTRUNK13"]
            def results = []

            for (String subtype : subtypes) {
                def soapBody = """
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
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>${body.orderNo ?: '1235'}</ins:OrderNo>
            <ins:Priority>${body.priority ?: '3'}</ins:Priority>
            <ins:ReqUser>${body.reqUser ?: 'testing'}</ins:ReqUser>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
            <ins:Parameter name="MTAS_RES_1" value="${body.mtasRes1 ?: 'hnitest'}"/>
            ${subtype == "SIPTRUNK11" ? """
              <ins:Parameter name="MTAS_RES_2" value="${body.mtasRes2 ?: '10.201.12.6:5060'}"/>
              <ins:Parameter name="MTAS_RES_3" value="${body.mtasRes3 ?: '10.163.22.3:5060'}"/>
              <ins:Parameter name="MTAS_RES_4" value="${body.mtasRes4 ?: '20'}"/>
            """ : ""}
            <ins:Parameter name="SUBTYPE" value="${subtype}"/>
            <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_CREATE_SUBSCRIBER"/>
            <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
            <ins:Parameter name="OPERATION" value="Create"/>
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

                def result = new JsonSlurper().parseText(XML.toJSONObject(post(spsUrl, null, soapBody)).toString())
                def Response = result["S:Envelope"]["S:Body"]["Response"]
                def _return = convertXmlData(Response)[0]

                results << [subtype: subtype, response: _return]

                if (!(_return?.StatusMessage?.toString()?.contains('00000|') && _return?.Status?.toString() == '9')) {
                    return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Tạo MTAS (${subtype}) thất bại.", data: _return)
                }
            }

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Tạo MTAS FIX IP thành công.", data: results)

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/create_mtas_share")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse createMtasShare(@Valid @RequestBody CreateMtasReq body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def subtypes = ["SIPTRUNK21", "SIPTRUNK22", "SIPTRUNK23"]
            def results = []

            for (String subtype : subtypes) {
                def soapBody = """
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
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>${body.orderNo ?: '1235'}</ins:OrderNo>
            <ins:Priority>${body.priority ?: '3'}</ins:Priority>
            <ins:ReqUser>${body.reqUser ?: 'testing'}</ins:ReqUser>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
            <ins:Parameter name="MTAS_RES_1" value="${body.mtasRes1 ?: 'hnitest'}"/>
            ${subtype == "SIPTRUNK21" ? """
              <ins:Parameter name="MTAS_RES_2" value="${body.mtasRes2 ?: '10.201.12.6:5060'}"/>
              <ins:Parameter name="MTAS_RES_3" value="${body.mtasRes3 ?: 'hnitest.ims.vnpt.vn'}"/>
              <ins:Parameter name="MTAS_RES_4" value="${body.mtasRes4 ?: '20'}"/>
            """ : ""}
            <ins:Parameter name="SUBTYPE" value="${subtype}"/>
            <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_CREATE_SUBSCRIBER"/>
            <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
            <ins:Parameter name="OPERATION" value="Create"/>
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

                def result = new JsonSlurper().parseText(XML.toJSONObject(post(spsUrl, null, soapBody)).toString())
                def Response = result["S:Envelope"]["S:Body"]["Response"]
                def _return = convertXmlData(Response)[0]

                results << [subtype: subtype, response: _return]
                if (!(_return?.StatusMessage?.toString()?.contains('00000|') && _return?.Status?.toString() == '9')) {
                    return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Tạo MTAS (${subtype}) thất bại.", data: _return)
                }
            }

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Tạo MTAS Share thành công.", data: results)

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/create_mtas_backup")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse createMtasBackup(@Valid @RequestBody CreateMtasReq body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def subtypes = ["SIPTRUNK31", "SIPTRUNK32", "SIPTRUNK33"]
            def results = []

            for (String subtype : subtypes) {
                def soapBody = """
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
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>${body.orderNo ?: '1235'}</ins:OrderNo>
            <ins:Priority>${body.priority ?: '3'}</ins:Priority>
            <ins:ReqUser>${body.reqUser ?: 'testing'}</ins:ReqUser>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
            <ins:Parameter name="MTAS_RES_1" value="${body.mtasRes1 ?: 'hnitest'}"/>
            ${subtype == "SIPTRUNK31" ? """
              <ins:Parameter name="MTAS_RES_2" value="${body.mtasRes2 ?: '10.201.12.6:5060'}"/>
              <ins:Parameter name="MTAS_RES_3" value="${body.mtasRes3 ?: 'hnitest.ims.vnpt.vn'}"/>
              <ins:Parameter name="MTAS_RES_4" value="${body.mtasRes4 ?: '20'}"/>
            """ : ""}
            <ins:Parameter name="SUBTYPE" value="${subtype}"/>
            <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_CREATE_SUBSCRIBER"/>
            <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
            <ins:Parameter name="OPERATION" value="Create"/>
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

                def result = new JsonSlurper().parseText(XML.toJSONObject(post(spsUrl, null, soapBody)).toString())
                def Response = result["S:Envelope"]["S:Body"]["Response"]
                def _return = convertXmlData(Response)[0]

                results << [subtype: subtype, response: _return]

                if (!(_return?.StatusMessage?.toString()?.contains('00000|') && _return?.Status?.toString() == '9')) {
                    return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Tạo MTAS (${subtype}) thất bại.", data: _return)
                }
            }

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Tạo MTAS Backup thành công.", data: results)

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/delete_sub")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse deleteSub(@Valid @RequestBody DeleteSubReq body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def buildSoap = { Map params ->
                """
<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope"
               xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
  <soap:Header>
    <wsse:Security soap:mustUnderstand="true"
                   xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
      <wsse:UsernameToken wsu:Id="SOAI_req_SOAI"
                   xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
        <wsse:Username>${username}</wsse:Username>
        <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
      </wsse:UsernameToken>
    </wsse:Security>
  </soap:Header>
  <soap:Body>
    <ins:CreateRequest>
      <ins:RequestHeader>
        <ins:NeType>ONLINE</ins:NeType>
        <ins:OrderNo>${body.orderNo ?: '1235'}</ins:OrderNo>
        <ins:Priority>3</ins:Priority>
        <ins:ReqUser>${body.reqUser ?: 'testing'}</ins:ReqUser>
      </ins:RequestHeader>
      <ins:RequestParameters>
        ${params.collect { k, v -> """<ins:Parameter name="${k}" value="${v}"/>""" }.join("\n")}
        <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
        <ins:Parameter name="OPERATION" value="Delete"/>
      </ins:RequestParameters>
    </ins:CreateRequest>
  </soap:Body>
</soap:Envelope>
"""
            }
            def steps = [
                    [ MSISDN: body.msisdn, MTAS_RES_1: body.mtasRes1 ?: "hnitest", MTAS_RES_2: body.mtasRes2 ?: "mtas-delete-siptrunk1", LL_PRODUCT_NAME: "TS_MTAS_DELETE_SUBSCRIBER" ],
                    [ MSISDN: body.msisdn, MTAS_RES_2: body.mtasRes2 ?: "mtas-delete-siptrunk2", LL_PRODUCT_NAME: "TS_MTAS_DELETE_SUBSCRIBER" ],
                    [ MSISDN: body.msisdn, MTAS_RES_2: body.mtasRes2 ?: "mtas-delete-siptrunk3", LL_PRODUCT_NAME: "TS_MTAS_DELETE_SUBSCRIBER" ],
                    [ MSISDN: body.msisdn, ISM_RES_1: body.ismRes1 ?: "hnitest", ISM_RES_2: "ism-delete-siptrunk", LL_PRODUCT_NAME: "TS_ISM_DELETE_SUBSCRIBER" ]
            ]

            def results = []
            steps.eachWithIndex { params, idx ->
                def soapBody = buildSoap(params)
                def result = new JsonSlurper().parseText(XML.toJSONObject(post(spsUrl, null, soapBody)).toString())
                def Response = result["S:Envelope"]["S:Body"]["Response"]
                def _return = convertXmlData(Response)[0]
                results << _return

                if (!(_return?.StatusMessage?.toString()?.contains('00000|') && _return?.Status?.toString() == '9')) {
                    throw new RuntimeException("Thất bại tại bước ${idx+1}: ${_return?.StatusMessage}")
                }
            }
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Xóa thành công tất cả các bước.", data: results)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    //Tăng số kênh cuộc gọi đồng thời (Nghiệp vụ ST1)
    @LoggingInOut
    @PostMapping("/tang_kenh_cuocgoi")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse tangKenhCuocGoi(@Valid @RequestBody TangKenhCuocGoiReq body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def soapBody = """
<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
   <soap:Header>
      <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0.xsd">
            <wsse:Username>${username}</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soap:Header>
   <soap:Body>
      <ins:CreateRequest>
         <ins:RequestHeader>
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>${body.orderNo ?: 'mtas-siptrunk-channel'}</ins:OrderNo>
            <ins:Priority>${body.priority ?: '3'}</ins:Priority>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
            <ins:Parameter name="MTAS_RES_3" value="${body.mtasRes3}"/>
            <ins:Parameter name="MTAS_RES_4" value="${body.mtasRes4}"/>
            <ins:Parameter name="SERVICE_ACTION" value="mtas-siptrunk-channel"/>
            <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_MODIFY_SUBSCRIBER"/>
            <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
            <ins:Parameter name="OPERATION" value="Modify"/>
         </ins:RequestParameters>
      </ins:CreateRequest>
   </soap:Body>
</soap:Envelope>
"""
            def responseXml = post(spsUrl, null, soapBody)
            def responseJson = new JsonSlurper().parseText(XML.toJSONObject(responseXml).toString())
            def Response = responseJson["S:Envelope"]["S:Body"]["Response"]
            def result = convertXmlData(Response)[0]

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Tăng số kênh cuộc gọi đồng thời thành công", data: result)

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    // Khóa/Mở chiều gọi đi quốc tế (Nghiệp vụ ST2)
    @LoggingInOut
    @PostMapping("/khoa_mo_goi_di_quocte")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse khoaMoGoiDiQuocTe(@Valid @RequestBody KhoaMoGoiDiQuocTeReq body) {
        try {
            if (!["lock", "unlock"].contains(body.action?.toLowerCase())) {
                return new CommonResponse(
                        errorCode: 1,
                        faultCode: "",
                        faultString: "Action phải là 'lock' hoặc 'unlock'.",
                        data: null
                )
            }

            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def actionMap = [
                    lock  : [orderNo: "mtas-siptrunk-idd-lock",   serviceAction: "mtas-siptrunk-idd-lock"],
                    unlock: [orderNo: "mtas-siptrunk-idd-unlock", serviceAction: "mtas-siptrunk-idd-unlock"]
            ]
            def params = actionMap[body.action.toLowerCase()]

            def soapBody = """
<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
   <soap:Header>
      <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0.xsd">
            <wsse:Username>${username}</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soap:Header>
   <soap:Body>
      <ins:CreateRequest>
         <ins:RequestHeader>
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>${params.orderNo}</ins:OrderNo>
            <ins:Priority>3</ins:Priority>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
            <ins:Parameter name="MTAS_RES_3" value="${body.mtasRes3 ?: 'hnitest'}"/>
            <ins:Parameter name="SERVICE_ACTION" value="${params.serviceAction}"/>
            <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_MODIFY_SUBSCRIBER"/>
            <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
            <ins:Parameter name="OPERATION" value="Modify"/>
         </ins:RequestParameters>
      </ins:CreateRequest>
   </soap:Body>
</soap:Envelope>
"""

            def responseXml = post(spsUrl, null, soapBody)
            def responseJson = new JsonSlurper().parseText(XML.toJSONObject(responseXml).toString())
            def Response = responseJson["S:Envelope"]["S:Body"]["Response"]
            def result = convertXmlData(Response)[0]

            return new CommonResponse(
                    errorCode: 0,
                    faultCode: "",
                    faultString: "Thực hiện ${body.action} thành công.",
                    data: result
            )

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/khoa_mo_1080")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse khoaMo1080(@Valid @RequestBody KhoaMo1080Req body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def soapMap = [
                    'lock1080'   : [orderNo: "mtas-siptrunk-1080-lock",    serviceAction: "mtas-siptrunk-1080-lock"],
                    'unlock1080' : [orderNo: "mtas-siptrunk-1080-unlock",  serviceAction: "mtas-siptrunk-1080-unlock"],
                    'lockIdd'    : [orderNo: "mtas-siptrunk-idd-lock",     serviceAction: "mtas-siptrunk-idd-lock"],
                    'unlockIdd'  : [orderNo: "mtas-siptrunk-idd-unlock",   serviceAction: "mtas-siptrunk-idd-unlock"]
            ]

            def buildSoapBody = { params ->
                return """
<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
   <soap:Header>
      <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0.xsd">
            <wsse:Username>${username}</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soap:Header>
   <soap:Body>
      <ins:CreateRequest>
         <ins:RequestHeader>
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>${params.orderNo}</ins:OrderNo>
            <ins:Priority>3</ins:Priority>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
            <ins:Parameter name="MTAS_RES_3" value="${body.mtasRes3}"/>
            <ins:Parameter name="SERVICE_ACTION" value="${params.serviceAction}"/>
            <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_MODIFY_SUBSCRIBER"/>
            <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
            <ins:Parameter name="OPERATION" value="Modify"/>
         </ins:RequestParameters>
      </ins:CreateRequest>
   </soap:Body>
</soap:Envelope>
"""
            }

            def results = []
            if (body.action.toLowerCase() == 'lock') {
                def soapBody = buildSoapBody(soapMap['lock1080'])
                def responseXml = post(spsUrl, null, soapBody)
                def responseJson = new JsonSlurper().parseText(XML.toJSONObject(responseXml).toString())
                results << convertXmlData(responseJson["S:Envelope"]["S:Body"]["Response"])[0]

                if (body.hasInternationalLock) {
                    def soapBody2 = buildSoapBody(soapMap['lockIdd'])
                    def responseXml2 = post(spsUrl, null, soapBody2)
                    def responseJson2 = new JsonSlurper().parseText(XML.toJSONObject(responseXml2).toString())
                    results << convertXmlData(responseJson2["S:Envelope"]["S:Body"]["Response"])[0]
                }

            } else if (body.action.toLowerCase() == 'unlock') {
                def soapBody = buildSoapBody(soapMap['unlock1080'])
                def responseXml = post(spsUrl, null, soapBody)
                def responseJson = new JsonSlurper().parseText(XML.toJSONObject(responseXml).toString())
                results << convertXmlData(responseJson["S:Envelope"]["S:Body"]["Response"])[0]

                if (body.hasInternationalLock) {
                    def soapBody2 = buildSoapBody(soapMap['lockIdd'])
                    def responseXml2 = post(spsUrl, null, soapBody2)
                    def responseJson2 = new JsonSlurper().parseText(XML.toJSONObject(responseXml2).toString())
                    results << convertXmlData(responseJson2["S:Envelope"]["S:Body"]["Response"])[0]
                }

            } else {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Action phải là 'lock' hoặc 'unlock'.", data: null)
            }

            return new CommonResponse(
                    errorCode: 0,
                    faultCode: "",
                    faultString: "Thực hiện ${body.action} ${body.hasInternationalLock ? '1080 + quốc tế' : '1080'} thành công.",
                    data: results
            )

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    // Khóa/mở gọi 1900 (Nghiệp vụ ST4)
    @LoggingInOut
    @PostMapping("/khoa_mo_goi_1900")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse khoaMoGoi1900(@Valid @RequestBody KhoaMoGoi1900Req body) {
        try {
            def action = body.action?.toLowerCase()
            if (!(action in ["lock", "unlock"])) {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Action phải là 'lock' hoặc 'unlock'.", data: null)
            }

            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def actionMap = [
                    lock  : [orderNo: "mtas-siptrunk-1900-lock",   serviceAction: "mtas-siptrunk-1900-lock"],
                    unlock: [orderNo: "mtas-siptrunk-1900-unlock", serviceAction: "mtas-siptrunk-1900-unlock"]
            ]
            def params = actionMap[action]

            def soapBody = """
<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
   <soap:Header>
      <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0.xsd">
            <wsse:Username>${username}</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soap:Header>
   <soap:Body>
      <ins:CreateRequest>
         <ins:RequestHeader>
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>${params.orderNo}</ins:OrderNo>
            <ins:Priority>3</ins:Priority>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
            <ins:Parameter name="MTAS_RES_3" value="${body.mtasRes3 ?: 'hnitest'}"/>
            <ins:Parameter name="SERVICE_ACTION" value="${params.serviceAction}"/>
            <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_MODIFY_SUBSCRIBER"/>
            <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
            <ins:Parameter name="OPERATION" value="Modify"/>
         </ins:RequestParameters>
      </ins:CreateRequest>
   </soap:Body>
</soap:Envelope>
"""

            def responseXml = post(spsUrl, null, soapBody)
            def responseJson = new JsonSlurper().parseText(XML.toJSONObject(responseXml).toString())
            def response = responseJson["S:Envelope"]["S:Body"]["Response"]
            def result = convertXmlData(response)

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Thực hiện ${action} 1900 thành công.", data: result)

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

// Chặn/bỏ chặn các cuộc gọi đến không mong muốn (Nghiệp vụ ST5)
    @LoggingInOut
    @PostMapping("/chan_bo_chan_so")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse chanBoChanSo(@Valid @RequestBody ChanBoChanReq body) {
        try {
            def action = body.action?.toLowerCase()
            if (!(action in ["block", "unblock"])) {
                return new CommonResponse(
                        errorCode: 1,
                        faultCode: "",
                        faultString: "Action phải là 'block' hoặc 'unblock'.",
                        data: null
                )
            }

            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def actionMap = [
                    block  : [orderNo: "mtas-siptrunk-icb-active",   serviceAction: "mtas-siptrunk-icb-active"],
                    unblock: [orderNo: "mtas-siptrunk-icb-deactive", serviceAction: "mtas-siptrunk-icb-deactive"]
            ]
            def params = actionMap[action]

            def blockListParam = action == "block" && body.mtasRes5 ?
                    """<ins:Parameter name="MTAS_RES_5" value="${body.mtasRes5}"/>""" : ""

            def soapBody = """
<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
   <soap:Header>
      <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0.xsd">
            <wsse:Username>${username}</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soap:Header>
   <soap:Body>
      <ins:CreateRequest>
         <ins:RequestHeader>
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>${params.orderNo}</ins:OrderNo>
            <ins:Priority>3</ins:Priority>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
            <ins:Parameter name="MTAS_RES_3" value="${body.mtasRes3 ?: 'hnitest'}"/>
            ${blockListParam}
            <ins:Parameter name="SERVICE_ACTION" value="${params.serviceAction}"/>
            <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_MODIFY_SUBSCRIBER"/>
            <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
            <ins:Parameter name="OPERATION" value="Modify"/>
         </ins:RequestParameters>
      </ins:CreateRequest>
   </soap:Body>
</soap:Envelope>
"""

            def responseXml = post(spsUrl, null, soapBody)
            def responseJson = new JsonSlurper().parseText(XML.toJSONObject(responseXml).toString())
            def response = responseJson["S:Envelope"]["S:Body"]["Response"]
            def result = convertXmlData(response)[0]

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Thực hiện ${action} thành công.", data: result)

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    //Tạm ngưng/bỏ tạm ngưng sử dụng điện thoại (Nghiệp vụ ST6)
    @LoggingInOut
    @PostMapping("/tam_ngung")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse tamNgung(@Valid @RequestBody TamNgungReq body) {
        try {
            if (!["active", "deactive"].contains(body.action?.toLowerCase())) {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Action phải là 'active' hoặc 'deactive'.", data: null)
            }

            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def actionMap = [
                    active: [orderNo: "mtas-siptrunk-suspend-active",   serviceAction: "mtas-siptrunk-suspend-active"],
                    deactive : [orderNo: "mtas-siptrunk-suspend-deactive", serviceAction: "mtas-siptrunk-suspend-deactive"]
            ]
            def params = actionMap[body.action.toLowerCase()]

            def soapBody = """
<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
   <soap:Header>
      <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0.xsd">
            <wsse:Username>${username}</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soap:Header>
   <soap:Body>
      <ins:CreateRequest>
         <ins:RequestHeader>
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>${params.orderNo}</ins:OrderNo>
            <ins:Priority>3</ins:Priority>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
            <ins:Parameter name="MTAS_RES_3" value="${body.mtasRes3}"/>
            <ins:Parameter name="SERVICE_ACTION" value="${params.serviceAction}"/>
            <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_MODIFY_SUBSCRIBER"/>
            <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
            <ins:Parameter name="OPERATION" value="Modify"/>
         </ins:RequestParameters>
      </ins:CreateRequest>
   </soap:Body>
</soap:Envelope>
"""

            def responseXml = post(spsUrl, null, soapBody)
            def responseJson = new JsonSlurper().parseText(XML.toJSONObject(responseXml).toString())
            def response = responseJson["S:Envelope"]["S:Body"]["Response"]
            def result = convertXmlData(response)[0]

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Thực hiện ${params.serviceAction} thành công.", data: result)

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    //Khóa/mở khóa chiều đi (Nghiệp vụ ST7)
    @LoggingInOut
    @PostMapping("/khoa_mo_chieu_di")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse khoaMoChieuDi(@Valid @RequestBody KhoaMoChieuDiReq body) {
        try {
            if (!["lock", "unlock"].contains(body.action?.toLowerCase())) {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Action phải là 'lock' hoặc 'unlock'.", data: null)
            }

            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def actionMap = [
                    lock  : [orderNo: "mtas-siptrunk-oc-lock",   serviceAction: "mtas-siptrunk-oc-lock"],
                    unlock: [orderNo: "mtas-siptrunk-oc-unlock", serviceAction: "mtas-siptrunk-oc-unlock"]
            ]
            def params = actionMap[body.action.toLowerCase()]

            def soapBody = """
<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
   <soap:Header>
      <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0.xsd">
            <wsse:Username>${username}</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soap:Header>
   <soap:Body>
      <ins:CreateRequest>
         <ins:RequestHeader>
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>${params.orderNo}</ins:OrderNo>
            <ins:Priority>3</ins:Priority>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
            <ins:Parameter name="MTAS_RES_3" value="${body.mtasRes3 ?: 'hnitest'}"/>
            <ins:Parameter name="SERVICE_ACTION" value="${params.serviceAction}"/>
            <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_MODIFY_SUBSCRIBER"/>
            <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
            <ins:Parameter name="OPERATION" value="Modify"/>
         </ins:RequestParameters>
      </ins:CreateRequest>
   </soap:Body>
</soap:Envelope>
"""

            def responseXml = post(spsUrl, null, soapBody)
            def responseJson = new JsonSlurper().parseText(XML.toJSONObject(responseXml).toString())
            def Response = responseJson["S:Envelope"]["S:Body"]["Response"]
            def result = convertXmlData(Response)[0]

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Thực hiện ${body.action} chiều đi thành công.", data: result)

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    //8. Thêm thuê bao vào ST đã tồn tại (Nghiệp vụ ST8)
    //a.Bước 1: Set HSS thêm dải số 84243840005
    @LoggingInOut
    @PostMapping("/them_dai_so")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse themDaiSo(@Valid @RequestBody ThemDaiSoReq body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def soapBody = """
<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
   <soap:Header>
      <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
         <wsse:UsernameToken wsu:Id="SOAI_req_SOAI">
            <wsse:Username>${username}</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soap:Header>
   <soap:Body>
      <ins:CreateRequest>
         <ins:RequestHeader>
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>${body.orderNo ?: "ism-siptrunk-add-sub"}</ins:OrderNo>
            <ins:Priority>3</ins:Priority>
            <ins:ReqUser>${body.reqUser ?: "testing"}</ins:ReqUser>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="ISM_RES_3" value="${body.ismRes3 ?: "hnitest"}"/>
            <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
            <ins:Parameter name="SERVICE_ACTION" value="${body.serviceAction ?: "ism-siptrunk-add-sub"}"/>
            <ins:Parameter name="LL_PRODUCT_NAME" value="TS_ISM_MODIFY_SUBSCRIBER"/>
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
</soap:Envelope>
"""

            def responseXml = post(spsUrl, null, soapBody)
            def responseJson = new JsonSlurper().parseText(XML.toJSONObject(responseXml).toString())

            def response = responseJson["S:Envelope"]["S:Body"]["Response"]
            def result = convertXmlData(response)[0]

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Thực hiện thêm dải số thành công.", data: result)

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    //Thêm thuê bao vào ST đã tồn tại (Nghiệp vụ ST8)
    //a.Bước 3: Gọi 2 lệnh MTAS tạo thêm các referral từ dải số mới
    @LoggingInOut
    @PostMapping("/them_referral")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse themReferral(@Valid @RequestBody ThemReferralReq body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def subtypes = ["SIPTRUNK12", "SIPTRUNK13"]
            def results = []

            for (String subtype : subtypes) {
                def soapBody = """
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
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>${body.orderNo ?: '1235'}</ins:OrderNo>
            <ins:Priority>${body.priority ?: '3'}</ins:Priority>
            <ins:ReqUser>${body.reqUser ?: 'testing'}</ins:ReqUser>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
            <ins:Parameter name="MTAS_RES_3" value="${body.mtasRes3 ?: 'hnitest'}"/>
            <ins:Parameter name="SUBTYPE" value="${subtype}"/>
            <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_CREATE_SUBSCRIBER"/>
            <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
            <ins:Parameter name="OPERATION" value="Create"/>
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

                def result = new JsonSlurper().parseText(XML.toJSONObject(post(spsUrl, null, soapBody)).toString())
                def Response = result["S:Envelope"]["S:Body"]["Response"]
                def _return = convertXmlData(Response)[0]

                results << [subtype: subtype, response: _return]

                if (!(_return?.StatusMessage?.toString()?.contains('00000|')
                        && _return?.Status?.toString() == '9')) {
                    return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Tạo MTAS (${subtype}) thất bại.", data: _return)
                }
            }

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Tạo MTAS SIPTRUNK12 & SIPTRUNK13 thành công.", data: results
            )

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    //
    //b.Bước 1: Set HSS xóa dải số 84243840005
    @LoggingInOut
    @PostMapping("/xoa_dai_so_hss")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse xoaDaiSoHss(@Valid @RequestBody XoaDaiSoHssReq body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def soapBody = """
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
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>${body.orderNo ?: 'ism-siptrunk-del-sub'}</ins:OrderNo>
            <ins:Priority>${body.priority ?: '3'}</ins:Priority>
            <ins:ReqUser>${body.reqUser ?: 'testing'}</ins:ReqUser>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="ISM_RES_3" value="${body.ismRes3 ?: 'hnitest'}"/>
            <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
            <ins:Parameter name="SERVICE_ACTION" value="ism-siptrunk-del-sub"/>
            <ins:Parameter name="LL_PRODUCT_NAME" value="TS_ISM_MODIFY_SUBSCRIBER"/>
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
</soap:Envelope>
"""

            def result = new JsonSlurper().parseText(XML.toJSONObject(post(spsUrl, null, soapBody)).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)[0]

            if (!(_return?.StatusMessage?.toString()?.contains('00000|') && _return?.Status?.toString() == '9')) {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Xóa dải số thất bại.", data: _return
                )
            }

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Xóa dải số thành công.", data: _return
            )

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    //b.Bước 3: Gọi 2 lệnh MTAS xóa các referral từ dải số cần xóa
    @LoggingInOut
    @PostMapping("/xoa_dai_so_mtas")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse xoaDaiSoMtas(@Valid @RequestBody XoaDaiSoMtasReq body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)
            def subtypes = ["mtas-delete-siptrunk2", "mtas-delete-siptrunk3"]
            def results = []

            for (String subtype : subtypes) {
                def soapBody = """
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
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>${body.orderNo ?: '1235'}</ins:OrderNo>
            <ins:Priority>${body.priority ?: '3'}</ins:Priority>
            <ins:ReqUser>${body.reqUser ?: 'testing'}</ins:ReqUser>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
            <ins:Parameter name="MTAS_RES_2" value="${subtype}"/>
            <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_DELETE_SUBSCRIBER"/>
            <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
            <ins:Parameter name="OPERATION" value="Delete"/>
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

                def result = new JsonSlurper().parseText(XML.toJSONObject(post(spsUrl, null, soapBody)).toString())
                def Response = result["S:Envelope"]["S:Body"]["Response"]
                def _return = convertXmlData(Response)[0]

                results << [subtype: subtype, response: _return]

                if (!(_return?.StatusMessage?.toString()?.contains('00000|')
                        && _return?.Status?.toString() == '9')) {
                    return new CommonResponse(
                            errorCode: 1,
                            faultCode: "",
                            faultString: "Xóa MTAS (${subtype}) thất bại.",
                            data: _return
                    )
                }
            }

            return new CommonResponse(
                    errorCode: 0,
                    faultCode: "",
                    faultString: "Xóa MTAS delete-siptrunk2 & delete-siptrunk3 thành công.",
                    data: results
            )

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    //c.Bước 1: Gọi 4 lệnh MTAS xóa referral cho 2 dải 84243840004 và 84243840005
    @LoggingInOut
    @PostMapping("/xoa_referral_mtas_2tb")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse xoaReferralMtas2tb(@Valid @RequestBody XoaReferralMtas2tbReq body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def commands = [
                    [msisdn: body.msisdn1, subtype: "mtas-delete-siptrunk2"],
                    [msisdn: body.msisdn1, subtype: "mtas-delete-siptrunk3"],
                    [msisdn: body.msisdn2, subtype: "mtas-delete-siptrunk2"],
                    [msisdn: body.msisdn2, subtype: "mtas-delete-siptrunk3"]
            ]

            def results = []

            for (cmd in commands) {
                def soapBody = """
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
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>${body.orderNo ?: '1235'}</ins:OrderNo>
            <ins:Priority>${body.priority ?: '3'}</ins:Priority>
            <ins:ReqUser>${body.reqUser ?: 'testing'}</ins:ReqUser>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="MSISDN" value="${cmd.msisdn}"/>
            <ins:Parameter name="MTAS_RES_2" value="${cmd.subtype}"/>
            <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_DELETE_SUBSCRIBER"/>
            <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
            <ins:Parameter name="OPERATION" value="Delete"/>
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

                def result = new JsonSlurper().parseText(XML.toJSONObject(post(spsUrl, null, soapBody)).toString())
                def Response = result["S:Envelope"]["S:Body"]["Response"]
                def _return = convertXmlData(Response)[0]

                results << [msisdn: cmd.msisdn, subtype: cmd.subtype, response: _return]

                if (!(_return?.StatusMessage?.toString()?.contains('00000|') && _return?.Status?.toString() == '9')) {
                    return new CommonResponse(
                            errorCode: 1,
                            faultCode: "",
                            faultString: "Xóa MTAS (${cmd.msisdn}, ${cmd.subtype}) thất bại.",
                            data: _return
                    )
                }
            }

            return new CommonResponse(
                    errorCode: 0,
                    faultCode: "",
                    faultString: "Xóa MTAS 2 thuê bao thành công.",
                    data: results
            )

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    //c.Bước 2: Xóa MTAS service của thuê bao SIP Trunking
    @LoggingInOut
    @PostMapping("/xoa_mtas_service")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse xoaMtasService(@Valid @RequestBody XoaMtasServiceReq body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def soapBody = """
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
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>${body.orderNo ?: '1235'}</ins:OrderNo>
            <ins:Priority>${body.priority ?: '3'}</ins:Priority>
            <ins:ReqUser>${body.reqUser ?: 'testing'}</ins:ReqUser>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="MTAS_RES_1" value="${body.mtasRes1 ?: 'hnitest'}"/>
            <ins:Parameter name="MTAS_RES_2" value="${body.mtasRes2 ?: 'mtas-delete-siptrunk1'}"/>
            <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_DELETE_SUBSCRIBER"/>
            <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
            <ins:Parameter name="OPERATION" value="Delete"/>
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

            def resultXml = post(spsUrl, null, soapBody)
            def resultJson = new JsonSlurper().parseText(XML.toJSONObject(resultXml).toString())
            def Response = resultJson["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)[0]

            if (!(_return?.StatusMessage?.toString()?.contains('00000|') && _return?.Status?.toString() == '9')) {
                return new CommonResponse(
                        errorCode: 1,
                        faultCode: "",
                        faultString: "Xóa MTAS (${body.mtasRes2}) thất bại.",
                        data: _return
                )
            }

            return new CommonResponse(
                    errorCode: 0,
                    faultCode: "",
                    faultString: "Xóa MTAS SIPTRUNK1 thành công.",
                    data: _return
            )

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    //c.Bước 4: Xóa HSS thuê bao SIP Trunking
    @LoggingInOut
    @PostMapping("/xoa_hss")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse xoaHss(@Valid @RequestBody XoaHssReq body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def soapBody = """
<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
   <soap:Header>
      <wsse:Security soap:mustUnderstand="true" 
                     xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" 
                     xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
         <wsse:UsernameToken wsu:Id="SOAI_req_SOAI">
            <wsse:Username>${username}</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soap:Header>
   <soap:Body>
      <ins:CreateRequest>
         <ins:RequestHeader>
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>${body.orderNo ?: '1235'}</ins:OrderNo>
            <ins:Priority>${body.priority ?: '3'}</ins:Priority>
            <ins:ReqUser>${body.reqUser ?: 'testing'}</ins:ReqUser>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
            <ins:Parameter name="ISM_RES_1" value="${body.ismRes1 ?: 'hnitest'}"/>
            <ins:Parameter name="ISM_RES_2" value="ism-delete-siptrunk"/>
            <ins:Parameter name="LL_PRODUCT_NAME" value="TS_ISM_DELETE_SUBSCRIBER"/>
            <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
            <ins:Parameter name="OPERATION" value="Delete"/>
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

            def resultXml = post(spsUrl, null, soapBody)
            def resultJson = new JsonSlurper().parseText(XML.toJSONObject(resultXml).toString())
            def Response = resultJson["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)[0]

            if (!(_return?.StatusMessage?.toString()?.contains('00000|') && _return?.Status?.toString() == '9')) {
                return new CommonResponse(
                        errorCode: 1,
                        faultCode: "",
                        faultString: "Xóa ISM Subscriber thất bại.",
                        data: _return
                )
            }

            return new CommonResponse(
                    errorCode: 0,
                    faultCode: "",
                    faultString: "Xóa ISM Subscriber thành công.",
                    data: _return
            )

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    // 9.API thay đổi IP tổng đài SIP Trunking (ST9)
    @LoggingInOut
    @PostMapping("/thay_doi_ip")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse thayDoiIp(@Valid @RequestBody ThayDoiIpReq body) {
        try {
            if (!body.msisdn || !body.mtasRes6) {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "MSISDN và mtasRes6 bắt buộc.", data: null);
            }

            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def soapBody = """
<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
   <soap:Header>
      <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0.xsd">
            <wsse:Username>${username}</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soap:Header>
   <soap:Body>
      <ins:CreateRequest>
         <ins:RequestHeader>
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>mtas-siptrunk-change-ip</ins:OrderNo>
            <ins:Priority>${body.priority ?: '3'}</ins:Priority>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
            <ins:Parameter name="MTAS_RES_3" value="${body.mtasRes3 ?: body.msisdn}"/>
            <ins:Parameter name="MTAS_RES_6" value="${body.mtasRes6}"/>
            <ins:Parameter name="SERVICE_ACTION" value="mtas-siptrunk-change-ip"/>
            <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_MODIFY_SUBSCRIBER"/>
            <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
            <ins:Parameter name="OPERATION" value="Modify"/>
         </ins:RequestParameters>
      </ins:CreateRequest>
   </soap:Body>
</soap:Envelope>
//"""
            def responseXml = post(spsUrl, null, soapBody)
            def responseJson = new JsonSlurper().parseText(XML.toJSONObject(responseXml).toString())
            def Response = responseJson["S:Envelope"]["S:Body"]["Response"]
            def result = convertXmlData(Response)[0]

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Thay đổi IP MTAS thành công.", data: result)

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


// API ST10: Gán thêm server backup (IP2) cho khách hàng SIP Trunking
    @LoggingInOut
    @PostMapping("/them_server_backup")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse themServerBackup(@Valid @RequestBody ThemServerBackupReq body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def soapBody = """
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
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>mtas-siptrunk-change-ip</ins:OrderNo>
            <ins:Priority>3</ins:Priority>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
            <ins:Parameter name="MTAS_RES_3" value="${body.mtasRes3 ?: 'hnitest'}"/>
            <ins:Parameter name="MTAS_RES_6" value="${body.mtasRes6}"/>
            <ins:Parameter name="SERVICE_ACTION" value="mtas-siptrunk-change-ip"/>
            <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_MODIFY_SUBSCRIBER"/>
            <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
            <ins:Parameter name="OPERATION" value="Modify"/>
         </ins:RequestParameters>
      </ins:CreateRequest>
   </soap:Body>
</soap:Envelope>
"""

            def responseXml = post(spsUrl, null, soapBody)
            def responseJson = new JsonSlurper().parseText(XML.toJSONObject(responseXml).toString())
            def Response = responseJson["S:Envelope"]["S:Body"]["Response"]
            def result = convertXmlData(Response)[0]

            return new CommonResponse(
                    errorCode: 0,
                    faultCode: "",
                    faultString: "Thêm server backup thành công.",
                    data: result
            )

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    // API để hiển thị thông tin thuê bao trên HSS
    @LoggingInOut
    @PostMapping("/show_hss")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse showHss(@Valid @RequestBody ShowHssReq body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def soapBody = """
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
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>${body.orderNo ?: '1235'}</ins:OrderNo>
            <ins:Priority>${body.priority ?: '3'}</ins:Priority>
            <ins:ReqUser>${body.reqUser ?: 'aha'}</ins:ReqUser>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
            <ins:Parameter name="SUBTYPE" value="${body.subtype ?: 'SIP'}"/>
            <ins:Parameter name="LL_PRODUCT_NAME" value="TS_ISM_DISPLAY_SUBSCRIBER"/>
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
"""

            def result = new JsonSlurper().parseText(XML.toJSONObject(post(spsUrl, null, soapBody)).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)

            def data = _return ? _return[0] : null

            if (!(data?.StatusMessage?.toString()?.contains('00000|') && data?.Status?.toString() == '9')) {
                return new CommonResponse(
                        errorCode: 1,
                        faultCode: "",
                        faultString: "Hiển thị subscriber Hss thất bại.",
                        data: _return
                )
            }

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Hiển thị subscriber Hss thành công.", data: _return)

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    // API để hiển thị dịch vụ MTAS (Show MTAS Service)
    @LoggingInOut
    @PostMapping("/show_mtas_service")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse showMtasService(@Valid @RequestBody ShowMtasServiceReq body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def soapBody = """
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
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>${body.orderNo ?: '1235'}</ins:OrderNo>
            <ins:Priority>${body.priority ?: '3'}</ins:Priority>
            <ins:ReqUser>${body.reqUser ?: 'aha'}</ins:ReqUser>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
            <ins:Parameter name="SUBTYPE" value="${body.subtype ?: 'SIPTRUNK'}"/>
            <ins:Parameter name="MTAS_RES_1" value="${body.mtasRes1 ?: 'mtas-siptrunk-show-service'}"/>
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
</soap:Envelope>
"""

            def result = new JsonSlurper().parseText(XML.toJSONObject(post(spsUrl, null, soapBody)).toString())
            def Response = result["S:Envelope"]["S:Body"]["Response"]
            def _return = convertXmlData(Response)

            def data = _return ? _return[0] : null

            if (!(data?.StatusMessage?.toString()?.contains('00000|') && data?.Status?.toString() == '9')) {
                return new CommonResponse(
                        errorCode: 1,
                        faultCode: "",
                        faultString: "Hiển thị MTAS service thất bại.",
                        data: _return
                )
            }

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Hiển thị MTAS service thành công.", data: _return)

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    @LoggingInOut
    @PostMapping("/show_mtas_sip")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse showMtasSip(@Valid @RequestBody ShowMtasSipReq body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def soapBody = """
<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
   <soap:Header>
      <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0.xsd">
            <wsse:Username>${username}</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soap:Header>
   <soap:Body>
      <ins:CreateRequest>
         <ins:RequestHeader>
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>${body.orderNo ?: '1235'}</ins:OrderNo>
            <ins:Priority>${body.priority ?: '3'}</ins:Priority>
            <ins:ReqUser>${body.reqUser ?: 'aha'}</ins:ReqUser>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
            <ins:Parameter name="SUBTYPE" value="${body.subtype ?: 'SIPTRUNK'}"/>
            <ins:Parameter name="MTAS_RES_1" value="${body.mtasRes1 ?: 'mtas-siptrunk-show-sip'}"/>
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
</soap:Envelope>
"""

            def responseXml = post(spsUrl, null, soapBody)
            def responseJson = new JsonSlurper().parseText(XML.toJSONObject(responseXml).toString())

            def responseBody = responseJson["S:Envelope"]["S:Body"]
            def Response = responseBody["Response"] ?: responseBody["CreateResponse"] ?: responseBody
            def data = convertXmlData(Response)

            def statusCheck = data?.find { it.StatusMessage?.toString()?.contains('00000|') && it.Status?.toString() == '9' }

            if (!statusCheck) {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Hiển thị MTAS Sip thất bại.", data: data)
            }

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Hiển thị MTAS Sip thành công.", data: data
            )

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

//Show MTAS tel
    @LoggingInOut
    @PostMapping("/show_mtas_tel")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse showMtasTel(@Valid @RequestBody ShowMtasTelReq body) {
        try {
            def session = genericApi.getSessionData(request)
            String username = getUsername(session)
            String password = getPassword(session)

            def soapBody = """
<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ins="http://soa.comptel.com/2011/02/instantlink">
   <soap:Header>
      <wsse:Security soap:mustUnderstand="true" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken wsu:Id="SOAI_req_SOAI" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0.xsd">
            <wsse:Username>${username}</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">${password}</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soap:Header>
   <soap:Body>
      <ins:CreateRequest>
         <ins:RequestHeader>
            <ins:NeType>ONLINE</ins:NeType>
            <ins:OrderNo>${body.orderNo ?: '1235'}</ins:OrderNo>
            <ins:Priority>${body.priority ?: '3'}</ins:Priority>
            <ins:ReqUser>${body.reqUser ?: 'aha'}</ins:ReqUser>
         </ins:RequestHeader>
         <ins:RequestParameters>
            <ins:Parameter name="MSISDN" value="${body.msisdn}"/>
            <ins:Parameter name="SUBTYPE" value="${body.subtype ?: 'SIPTRUNK'}"/>
            <ins:Parameter name="MTAS_RES_1" value="mtas-siptrunk-show-tel"/>
            <ins:Parameter name="LL_PRODUCT_NAME" value="TS_MTAS_DISPLAY_SUBSCRIBER"/>
            <ins:Parameter name="ITEM_TYPE" value="Technical Service"/>
            <ins:Parameter name="OPERATION" value="Display"/>
         </ins:RequestParameters>
      </ins:CreateRequest>
   </soap:Body>
</soap:Envelope>
"""

            def responseXml = post(spsUrl, null, soapBody)
            def responseJson = new JsonSlurper().parseText(XML.toJSONObject(responseXml).toString())

            def responseBody = responseJson["S:Envelope"]["S:Body"]
            def Response = responseBody["Response"] ?: responseBody["CreateResponse"] ?: responseBody
            def data = convertXmlData(Response)

            def statusCheck = data?.find { it.StatusMessage?.toString()?.contains('00000|') && it.Status?.toString() == '9' }

            if (!statusCheck) {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Hiển thị MTAS TEL thất bại.", data: data)
            }

            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Hiển thị MTAS TEL thành công.", data: data
            )

        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


}