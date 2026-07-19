package vn.vnptit.sapi.oneapp.resources

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
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.iveda.IVEDA_ADD_REQ
import vn.vnptit.sapi.oneapp.util.GenericApi
import vn.vnptit.sapi.oneapp.util.LoggingInOut

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("iveda")
class IvedaService {

    static ThreadLocal<Gson> gson = ThreadLocal.withInitial { new GsonBuilder().serializeNulls().create() }

    @Autowired
    LoggingManaged loggingManaged
    //@Autowired
    //OracleManaged oracleManaged
    //@Autowired
    //HttpManaged httpManaged

    @Autowired
    GenericApi genericApi

    @Autowired
    NativeWebRequest request

    @Value("\${spring.httpservices.iveda.url}")
    String url

    @PostMapping("/add")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOut
    CommonResponse add(@Valid @RequestBody IVEDA_ADD_REQ body) {
        try {
            def res = post('add', body)
            String faultString = ""
            int errorCode = 0
            if (res.resultid != '0') {
                errorCode = Integer.parseInt(res.resultid)
                faultString = res.message
            }
            return new CommonResponse(errorCode: errorCode, faultCode: res.resultid, faultString: faultString, data: res)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    Map post(String apiName, Object params) {
        def res = HttpManaged.post([url: url + apiName], ['Content-Type': 'application/json;charset=utf-8'], gson.get().toJson(params))
        (Map<String, Object>) new JsonSlurper().parseText(res)
    }
}
