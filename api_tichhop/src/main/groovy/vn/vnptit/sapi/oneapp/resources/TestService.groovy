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
import vn.vnptit.sapi.oneapp.util.Constant
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.GenericApi

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("test")
class TestService {
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

    @Value("\${spring.httpservices.hrm.url}")
    String hrmUrl

    @Value("\${spring.httpservices.hrm.token}")
    String hrmToken

    @GetMapping("/tokenHrm")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> tokenHrm() {
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String email = session_.get(genericApi.EMAIL)
            String userToken = getToken(email)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(userToken))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/**")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> executorPost(HttpServletRequest request, @RequestParam Map <String, String> requestParams, @RequestBody(required = false) Object requestBody) {
        try {
            String[] arrServletPath = request.getServletPath().split("hrm/", 2)
            def res = post(arrServletPath[1], requestParams, requestBody)
            def ret = new JsonSlurper().parseText(res)
            if (ret?.success || ret?.result == "True"){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            }else{
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret, null, ret?.message ?: ret?.messageReturn, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    String renewToken(String email){
        Map<String, String> headers = [:]
        Map<String, String> params = [:]
        params.put("tokenCode", hrmToken)
        params.put("email", email)
        def ls = params.collect {key, value -> key + "=" + value}.join("&")
        String url = hrmUrl + "hrm/get_token_by_email?$ls"
        headers.put("Content-Type", "text/xml;charset=UTF-8")
        String resp = httpManaged.post([url: url], headers, "")
        def ret = new JsonSlurper().parseText(resp)
        String token = ret.access_token
        cacheManager.getCache(Constant.HRM_CACHE_NAME).put(email.toUpperCase() + "/serverToken", token)
        return token
    }

    String getToken(String email){
        String token = cacheManager.getCache(Constant.HRM_CACHE_NAME).get(email.toUpperCase() + "/serverToken", String.class)
        if (token == null){
            return renewToken(email)
        }
        return token
    }

    String get(String path, Map<String, String> param) {
        Map<String, Object> session_ = genericApi.getSessionData(request)
        String email = session_.get(genericApi.EMAIL)
        String userToken = getToken(email)
        param.put("tokenCode", hrmToken)
        param.put("access_token", userToken)
        def ls = param.collect {key, value -> key + "=" + value}.join("&")
        Map<String, String> headers = [:]
        String url = hrmUrl + path + "?" + ls
        headers.put("Content-Type", "text/xml;charset=UTF-8")
        String resp = httpManaged.get(url, headers)
        return resp
    }

    String post(String path, Map<String, String> param, Object body) {
        Map<String, Object> session_ = genericApi.getSessionData(request)
        String email = session_.get(genericApi.EMAIL)
        String userToken = getToken(email)
        param.put("tokenCode", hrmToken)
        param.put("access_token", userToken)
        if (!param.containsKey("access_token")){
            param.put("access_token", hrmToken)
        }
        def ls = param.collect {key, value -> key + "=" + value}.join("&")
        Map<String, String> headers = [:]
        String url = hrmUrl + path + "?" + ls
        headers.put("Content-Type", "text/xml;charset=UTF-8")
        String resp = httpManaged.post([url: url], headers, json.writeValueAsString(body))
        return resp
    }
}
