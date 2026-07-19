package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import groovy.json.JsonSlurper
import groovy.util.slurpersupport.GPathResult
import io.swagger.model.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import org.json.XML
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.ims.DoiPass
import vn.vnptit.sapi.oneapp.models.sps.GET_PROJECTBYINVESTMENTFIELD_REQ
import vn.vnptit.sapi.oneapp.models.sps.IMS_HUY_THUEBAO_REQ
import vn.vnptit.sapi.oneapp.models.sps.IMS_HUY_THUEBAO_RESP
import vn.vnptit.sapi.oneapp.models.sps.TraCuuThongTin
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("ims")
class IMSService {

    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    HttpManaged httpManaged
    @Value("\${spring.defaultconfigs.ims-url}")
    String imsUrl

    @Value("\${spring.httpservices.qlts.userid}")
    String qltsUserid

    @Value("\${spring.httpservices.qlts.pass}")
    String qltsPass

    @PostMapping("/doiPass")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse traCuuThongTinMTAS(@Valid @RequestBody DoiPass requestBody) {
        try {
            String body = """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
				  <soap:Body>
					<DOI_PASS xmlns="http://tempuri.org/">
					  <msisdn>${requestBody.msisdn}</msisdn>
					  <newpass>${requestBody.password}</newpass>
					  <type>${requestBody.type}</type>
					</DOI_PASS>
				  </soap:Body>
				</soap:Envelope>"""
            def env = post(imsUrl, ["Content-Type": "text/xml;charset=UTF-8", "SOAPAction": "http://tempuri.org/DOI_PASS"], body)
            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                return new CommonResponse(errorCode: 1, faultCode: fault.faultstring.text(), faultString: "", data: "")
            }
            String xml = env.Body.DOI_PASSResponse.DOI_PASSResult.text()
            String status = ""
            String statusMessage = ""
            try {
                status = env.Body.DOI_PASSResponse.DOI_PASSResult.Envelope.Body.Response.ResponseHeader.Status.text()
                statusMessage = env.Body.DOI_PASSResponse.DOI_PASSResult.Envelope.Body.Response.ResponseHeader.StatusMessage.text()
            }catch(Exception ex){}
            if (xml != null && status == "9") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: statusMessage, data: xml)
            } else {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: statusMessage, data: xml)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    String main_svr_url = "http://10.193.69.74:8081/MainSVR.asmx"
    String ims_service_url = "http://10.70.52.215:8086/IMSService.asmx"

    ObjectMapper objectMapper = new ObjectMapper()
    private List<Map<String, String>> convert_ims_data(def Response) {
        List<Map<String, String>> listObj = []
        try {
            def RequestParameters = Response["NewDataSet"]
            if (RequestParameters != null) {
                Map<String, String> mapData = new HashMap<>()
                def Parameter = RequestParameters["NewDataSet"]
                for (Object item in Parameter) {
                    mapData.put(item["name"]?.toString(), item["value"]?.toString())
                }
                listObj.add(mapData)
            }
        } catch (Exception ex) {

        }
        return listObj
    }

    @PostMapping("/get_projectbyinvestmentfield")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_projectbyinvestmentfield(@Valid @RequestBody GET_PROJECTBYINVESTMENTFIELD_REQ body) {
        return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Chuyển sang api: https://api-oneapp-dev.vnptit3.vn/tichhop/swagger-ui/#/qldt-service/get_ProjectbyInvestmentFieldUsingGET", data: "")
    }

    @PostMapping("/ims_huy_thuebao")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse ims_huy_thuebao(@Valid @RequestBody IMS_HUY_THUEBAO_REQ body) {
        try {
            def env = post1(ims_service_url, null,
                    """<?xml version="1.0" encoding="utf-8"?>
                            <soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
                              <soap12:Body>
                                <HUY_THUEBAO xmlns="http://tempuri.org/">
                                  <msisdn>${body.msisdn}</msisdn>
                                </HUY_THUEBAO>
                              </soap12:Body>
                            </soap12:Envelope>""")
            IMS_HUY_THUEBAO_RESP data = new IMS_HUY_THUEBAO_RESP()
            data.status = true
            data.result = env
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/get_acceptancelist_warranty")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "get_acceptancelist_warranty", description = "get_acceptancelist_warranty", tags = ["get_acceptancelist_warranty"])
    ResponseEntity<BaseResponse> get_acceptancelist_warranty(
            @RequestHeader(value = "token-key", required = false) String token_key,
            @RequestHeader(value = "token-id", required = false) String token_id,
            @RequestBody Map<?,?> body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        Map<String, Object> inputLogData = new HashMap<>()
        try {
            String url = "http://10.165.7.65:8081/MainSVR.asmx"
            def env = post1(url,
                    """<?xml version="1.0" encoding="utf-8"?>
                            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                              <soap:Body>
                                <Get_AcceptanceList_Warranty xmlns="http://tempuri.org/">
                                  <sUserID>${qltsUserid}</sUserID>
                                  <sPass>${qltsPass}</sPass>
                                  <sProjectCode>${body["maduan"]}</sProjectCode>
                                  <iPO>${body["so_po"]}</iPO>
                                  <sBBBG>${body["so_bb"]}</sBBBG>
                                </Get_AcceptanceList_Warranty>
                              </soap:Body>
                            </soap:Envelope>""")
            loggingManaged.info("[IMSController] - [get_acceptancelist_warranty] - 1", new ImmutableMap.Builder<String, Object>().put("requestId", uuidAsString).put("env", env).build())
            if (!env.toString().contains("NewDataSet")) {
                def data = new XmlSlurper().parseText(env)
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, data.toString(), HttpStatus.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[IMSController] - [get_acceptancelist_warranty] - 2", new ImmutableMap.Builder<String, Object>().put("requestId", uuidAsString).put("env", env).build())
            def data = new XmlSlurper().parseText(env)
            String result = data.Body.Get_AcceptanceList_WarrantyResponse.Get_AcceptanceList_WarrantyResult.text()
            def data2 = new XmlSlurper().parseText(result)
            def result2 = data2.Table.collectEntries { node ->
                [ "ProjectCode": (node.ProjectCode.text()),
                  "ContractNumber": (node.ContractNumber.text()),
                  "AcceptanceGeneralName": (node.AcceptanceGeneralName.text()),
                  "WarrantyTime": (node.WarrantyTime.text())]
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(result2))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    String post1(String url, Map<String, String> headers, String body) {
        if (headers == null) headers = [:]
        headers.put("Content-Type", "application/soap+xml;charset=UTF-8")
        String resp = httpManaged.post(["url": url], headers, body)
        return resp
    }

    GPathResult post(String url, Map<String, String> headers, String body) {
        httpManaged = new HttpManaged()
        headers.put("Content-Type", "text/xml;charset=UTF-8")
        String resp = httpManaged.post(["url":url], headers, body)
        return new XmlSlurper().parseText(resp)
    }

    String post1(String url, String body) {
        Map<String, String> headers = [:]
        headers.put("Content-Type", "text/xml; charset=utf-8")
        return httpManaged.post(["url": url], headers, body)
    }

}
