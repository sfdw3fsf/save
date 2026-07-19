package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonSlurper
import io.swagger.model.BaseResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.GenericApi

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("brandname")
class BrandnameService {

    @Autowired
    HttpManaged httpManaged

    @Autowired
    NativeWebRequest request

    ObjectMapper json = new ObjectMapper();

    @Value("\${spring.httpservices.brandname.url}")
    String url_brandname

    @GetMapping("/**")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> executorGet(HttpServletRequest request, @RequestParam Map <String, String> requestParams) {
        try {
            String[] arrServletPath = request.getServletPath().split("brandname/", 2)
            String res = get(arrServletPath[1], requestParams)
            try {
                def ret = new JsonSlurper().parseText(res)
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } catch(Exception ex){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(res, null, res, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/**")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> executorPost(HttpServletRequest request, @RequestParam Map <String, String> requestParams, @RequestBody(required = false) Object requestBody) {
        try {
            String[] arrServletPath = request.getServletPath().split("brandname/", 2)
            String res = post(arrServletPath[1], requestParams, requestBody)
            try {
                def ret = new JsonSlurper().parseText(res)
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } catch(Exception ex){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(res, null, res, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }


    String get(String path, Map<String, String> param) {
        def ls = param.collect {key, value -> key + "=" + value}.join("&")
        Map<String, String> headers = [:]
        String url = url_brandname + path + "?" + ls
        headers.put("Content-Type", "application/json")
        headers.put("Content-Encoding", "UTF-8")
        String resp = httpManaged.get(url, headers)
        return resp
    }

    String post(String path, Map<String, String> param, Object body) {
        def ls = param.collect {key, value -> key + "=" + value}.join("&")
        Map<String, String> headers = [:]
        String url = url_brandname + path + "?" + ls
        headers.put("Content-Type", "application/json")
        headers.put("Content-Encoding", "UTF-8")
        String resp = httpManaged.post([url: url], headers, json.writeValueAsString(body))
        return resp
    }

}
