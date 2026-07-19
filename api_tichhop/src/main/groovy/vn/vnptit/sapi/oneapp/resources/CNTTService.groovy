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
@RequestMapping("cntt")
class CNTTService {
    @Autowired
    CacheManager cacheManager
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

    ObjectMapper json = new ObjectMapper();

    @Value("\${spring.httpservices.ca.url_gw0}")
    String url_gw

    @Value("\${spring.httpservices.ca.token_gw}")
    String token_gw

    @Value("\${spring.httpservices.ca.appsecret_gw}")
    String appsecret_gw

    @GetMapping("/**")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> executorGet(HttpServletRequest request, @RequestParam Map <String, String> requestParams) {
        try {
            String[] arrServletPath = request.getServletPath().split("cntt/", 2)
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
            String[] arrServletPath = request.getServletPath().split("cntt/", 2)
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

    @PutMapping("/**")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> executorPut(HttpServletRequest request, @RequestParam Map <String, String> requestParams, @RequestBody(required = false) Object requestBody) {
        try {
            String[] arrServletPath = request.getServletPath().split("cntt/", 2)
            String res = put(arrServletPath[1], requestParams, requestBody)
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

    @DeleteMapping("/**")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> executorDelete(HttpServletRequest request, @RequestParam Map <String, String> requestParams, @RequestBody(required = false) Object requestBody) {
        try {
            String[] arrServletPath = request.getServletPath().split("cntt/", 2)
            String res = delete(arrServletPath[1], requestParams)
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
        String url = url_gw + "api/" + path + "?" + ls
        headers.put("Authorization", token_gw)
        headers.put("Appsecret", appsecret_gw)
        headers.put("Content-Type", "application/json")
        headers.put("Content-Encoding", "UTF-8")
        String resp = httpManaged.get(url, headers)
        return resp
    }

    String post(String path, Map<String, String> param, Object body) {
        return 'ok' //test
        def ls = param.collect {key, value -> key + "=" + value}.join("&")
        Map<String, String> headers = [:]
        String url = url_gw + "api/" + path + "?" + ls
        headers.put("Authorization", token_gw)
        headers.put("Appsecret", appsecret_gw)
        headers.put("Content-Type", "application/json")
        headers.put("Content-Encoding", "UTF-8")
        String resp = httpManaged.post([url: url], headers, json.writeValueAsString(body))
        return resp
    }

    String put(String path, Map<String, String> param, Object body) {
        def ls = param.collect {key, value -> key + "=" + value}.join("&")
        Map<String, String> headers = [:]
        String url = url_gw + "api/" + path + "?" + ls
        headers.put("Authorization", token_gw)
        headers.put("Appsecret", appsecret_gw)
        headers.put("Content-Type", "application/json")
        headers.put("Content-Encoding", "UTF-8")
        String resp = httpManaged.put([url: url], headers, json.writeValueAsString(body))
        return resp
    }

    String delete(String path, Map<String, String> param) {
        def ls = param.collect {key, value -> key + "=" + value}.join("&")
        Map<String, String> headers = [:]
        String url = url_gw + "api/" + path + "?" + ls
        headers.put("Authorization", token_gw)
        headers.put("Appsecret", appsecret_gw)
        headers.put("Content-Type", "application/json")
        headers.put("Content-Encoding", "UTF-8")
        String resp = httpManaged.delete(url, headers)
        return resp
    }
}
