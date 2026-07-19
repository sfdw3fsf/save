package vn.vnptit.sapi.oneapp.resources.vasc

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.json.JsonSlurper
import groovy.util.slurpersupport.GPathResult
import org.json.XML
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.util.GenericApi

import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("mytv")
class MyTVService {
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

    @Value("\${spring.httpservices.vasc.mytvnotlogin}")
    String mytvUrl

    @Value("\${spring.httpservices.vasc.mytvnotlogin_token}")
    String mytvToken

    @GetMapping("/mytvUserNotLoginGetV2/{NummOff}/{PageSize}/{PageNumber}")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse mytvUserNotLoginGetV2(@PathVariable("NummOff") String NummOff,
                                         @PathVariable("PageSize") String PageSize,
                                         @PathVariable("PageNumber") String PageNumber) {
        try {
            String requestId = UUID.randomUUID().toString()
            Map<String, Object> session = genericApi.getSessionData(request)
            String maTinh = session[GenericApi.MA_TINH].toString()
            if (maTinh.toUpperCase() == "DNO")
                maTinh = "DKG";
            if (maTinh.toUpperCase() == "BKN")
                maTinh = "BCN";
            if (maTinh.toUpperCase() == "DLC")
                maTinh = "DLK";
            String body = """
                    <RQST>
                        <Name>MytvUserNotLoginGetV2</Name>
                        <ChannelCode>API</ChannelCode>
                        <OrgCode>MYTV</OrgCode>
                        <RequestId>$requestId</RequestId>
                        <BeginDayNumber>$NummOff</BeginDayNumber>
                        <EndDayNumber>$NummOff</EndDayNumber>
                        <ZipCode>$maTinh</ZipCode>
                        <PageSize>$PageSize</PageSize>
                        <PageNumber>$PageNumber</PageNumber>
                    </RQST>
                """
            Map<String, String> headers = [:]
            headers.put("Content-Type", "application/xml;charset=UTF-8")
            headers.put("Authorization", mytvToken)
            String resp = httpManaged.post(["url": mytvUrl], headers, body)
            def res = new JsonSlurper().parseText(XML.toJSONObject(resp).toString())
            if (res.RPLY.ErrorId == 0)
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res.RPLY)
            return new CommonResponse(errorCode: res.RPLY.ErrorId, faultCode: "", faultString: res.RPLY.ErrorDesc, data: res.RPLY)
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

}
