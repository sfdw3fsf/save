package vn.vnptit.sapi.oneapp.resources.cts


import groovy.json.JsonSlurper
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("cts")
class CTSNewService {
    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    HttpManaged httpManaged

    @Value("\${spring.httpservices.cts.newurl}")
    String url


    @GetMapping("/**")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse g(HttpServletRequest request, @RequestParam Map<String, String> searchParams) {
        try {
            String[] arrServletPath = request.getServletPath().split("cts/", 2)
            def res = get(arrServletPath[1], searchParams)
            def ret = new JsonSlurper().parseText(res)
            return new CommonResponse(errorCode: ret.err.code, faultCode: "", faultString: ret.err.msg, data: ret.data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/**")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse p(HttpServletRequest request, @RequestParam Map<String, String> searchParams) {
        try {
            String[] arrServletPath = request.getServletPath().split("cts/", 2)
            def res = post(arrServletPath[1], searchParams)
            if (res.code == 200){
                def ret = new JsonSlurper().parseText(res.body().string())
                return new CommonResponse(errorCode: ret.err.code, faultCode: "", faultString: ret.err.msg, data: ret.data)
            }else{
                return new CommonResponse(errorCode: res.code, faultCode: "", faultString: res.message, data: res)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    def post(String path, Map<String, String> param) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "")
        def ls = param.collect { key, value -> key + "=" + value }.join("&")
        Request request = new Request.Builder()
                .url(url + path + "?" + ls)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        return response
    }


    String get(String path, Map<String, String> param) {
        def ls = param.collect { key, value -> key + "=" + value }.join("&")
        Map<String, String> headers = [:]
        String full_url = url + path + "?" + ls
        headers.put("Content-Type", "text/xml;charset=UTF-8")
        String resp = httpManaged.get(full_url, headers)
        return resp
    }
}
