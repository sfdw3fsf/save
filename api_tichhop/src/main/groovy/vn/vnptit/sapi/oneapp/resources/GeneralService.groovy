package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.general.COMMON_REQ
import vn.vnptit.sapi.oneapp.util.GenericApi

import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("general")
class GeneralService {
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

//    @Value("\${spring.httpservices.hrm.url}")
//    String hrmUrl
//
//    @Value("\${spring.httpservices.hrm.token}")
//    String hrmToken

    @PostMapping("/callapi")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse callapi(@RequestBody COMMON_REQ reqbody) {
        try {
            if (!reqbody.apipath.startsWith("https://api-dev-onebss.vnpt.vn/tichhop/") &&
                    !reqbody.apipath.startsWith("https://api-onebss.vnpt.vn/tichhop/") &&
                    !reqbody.apipath.startsWith("http://10.70.52.244:6061/api/")
            ) {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Thông tin url không hợp lệ", data: reqbody.apipath)
            }

            String res
            if (reqbody.apimethod == null || reqbody.apimethod == "GET")
                res = get(reqbody.apipath, reqbody.apibody)
            else
                res = post(reqbody.apipath, reqbody.apibody)
            def ret = new JsonSlurper().parseText(res)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    String get(String path, String param) {
//        param.put("tokenCode", hrmToken)
//        def ls = param.collect {key, value -> key + "=" + value}.join("&")
        Map<String, String> headers = [:]
        String url = path + "?" + param
        headers.put("Content-Type", "application/json; charset=UTF-8")
        headers.put("Authorization", request.getHeader("Authorization"))
        String resp = httpManaged.get(url, headers)
        return resp
    }

    String post(String path, String params) {
        Map<String, String> headers = [:]
        headers.put("Content-Type", "application/json; charset=UTF-8")
        headers.put("Authorization", request.getHeader("Authorization"))
        return httpManaged.post(["url": path], headers, params)
    }
}
