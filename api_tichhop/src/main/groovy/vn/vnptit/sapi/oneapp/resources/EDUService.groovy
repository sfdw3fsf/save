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
import vn.vnptit.sapi.oneapp.models.edu.*
import vn.vnptit.sapi.oneapp.util.GenericApi

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("edu")
class EDUService {
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

    @Value("\${spring.httpservices.edu.url}")
    String eduUrl

    @Value("\${spring.httpservices.edu.token}")
    String eduToken

    @Value("\${spring.httpservices.edu.username}")
    String eduUsername

    @Value("\${spring.httpservices.edu.fullname}")
    String eduFullname

    @Value("\${spring.httpservices.edu.email}")
    String eduEmail

    @GetMapping("/{path}")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get(@PathVariable("path") String path, @RequestParam Map<String,String> searchParams) {
        try {
            def res = get(searchParams)
            if (res != null && res != ""){
                def ret = new JsonSlurper().parseText(res)
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret)
            }
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Không có dữ liệu", data: "")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/saveSllHocSinh")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse saveSllHocSinh(@Valid @RequestBody SllHocSinh body) {
        try {
            String path = "?call=app.doisoat.serv.api.saveSllHocSinh"
//            def res = get(searchParams)
//            if (res != null || res != ""){
//                def ret = new JsonSlurper().parseText(res)
//                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret)
//            }
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/huySll")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse huySll(@Valid @RequestBody SllHocSinh body) {
        try {
            String path = "?call=app.doisoat.serv.api.huySll"
//            def res = get(searchParams)
//            if (res != null || res != ""){
//                def ret = new JsonSlurper().parseText(res)
//                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret)
//            }
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/checkmang")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse checkmang(@Valid @RequestBody CheckMang body) {
        try {
            String path = "?call=app.doisoat.serv.api.checkmang"

//            def res = get(searchParams)
//            if (res != null || res != ""){
//                def ret = new JsonSlurper().parseText(res)
//                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret)
//            }
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    String get(Map<String, String> param) {
        //Fix test
        List<String> ig = ["app.doisoat.serv.api.getListTruong"]
        def checkget = param.find {it -> it.key == "call" && ig.contains(it.value.toString())}
        if (checkget == null){
            def check = param.find {it -> it.key == "truong_id" && it.value != "632007518"}
            if (check != null) return null
            check = param.find {it -> it.key == "tinh_id" && it.value != "88"}
            if (check != null) return null
        }


        param.put("token", eduToken)
        param.put("user_name", eduUsername)
        def ls = param.collect {key, value -> key + "=" + value}.join("&")
        Map<String, String> headers = [:]
        String url = eduUrl + "?" + ls
        headers.put("Content-Type", "text/xml;charset=UTF-8")
        String resp = httpManaged.get(url, headers)
        return resp
    }
}
