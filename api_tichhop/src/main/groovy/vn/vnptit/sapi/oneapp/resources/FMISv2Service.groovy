package vn.vnptit.sapi.oneapp.resources

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonSlurper
import io.swagger.model.BaseResponse
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.Constant
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.GenericApi

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("fmis_v2")
class FMISv2Service {

    @Autowired
    CacheManager cacheManager

    @Autowired
    LoggingManaged loggingManaged


    @Autowired
    HttpManaged httpManaged

    @Autowired
    GenericApi genericApi

    @Autowired
    NativeWebRequest request

    ObjectMapper json = new ObjectMapper();

    @Value("\${spring.httpservices.fmis.v2.url}")
    String fmisUrl

    @Value("\${spring.httpservices.fmis.v2.pwd}")
    String passWordDefault

    @GetMapping("/**")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> executorGet(HttpServletRequest request, @RequestParam Map<String, String> requestParams) {
        try {
            String[] arrServletPath = request.getServletPath().split("fmis/api/kydientu/", 2)
            def res = get("KyDienTu/" + arrServletPath[1], requestParams)
            def ret = new JsonSlurper().parseText(res)
            if (ret["error_code"] == "BSS-00000000") {
                List<Map<String, Object>> newList = new ArrayList<>()
                for (Map<String, Object> item : ret["data"]) {
                    newList.add(convertKeyToUpperCase(item))
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(newList))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, ret["message"], ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/**")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> executorPost(HttpServletRequest request, @RequestParam Map<String, String> requestParams, @RequestBody(required = false) Object requestBody) {
        try {
            String[] arrServletPath = request.getServletPath().split("fmis/api/kydientu/", 2)
            def res = post("KyDienTu/" + arrServletPath[1], requestParams, requestBody)
            def ret = new JsonSlurper().parseText(res)
            if (ret["error_code"] == "BSS-00000000") {
                List<Map<String, Object>> newList = new ArrayList<>()
                for (Map<String, Object> item : ret["data"]) {
                    newList.add(convertKeyToUpperCase(item))
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(newList))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, ret["message"], ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    public Map convertKeyToUpperCase(Map inputs) {
        if (inputs == null)
            return null;
        Map tmp = new HashMap(inputs.size());
        Iterator it = inputs.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            tmp.put(key.toString().toUpperCase(), inputs.get(key));
        }
        return tmp;
    }

    String get(String path, Map<String, String> param) {
        try {
            Map<String, String> headers = [:]
            def ls = ""
            // Get Token Auth của FMIS
            String tokenAuthJson = getTokenFromCache()
            def retToken = new JsonSlurper().parseText(tokenAuthJson)
            if (retToken["Status"] != "200") {
                return tokenAuthJson
            }
            String tokenAuth = retToken["Data"]
            if (tokenAuth != null && tokenAuth != "") {
                headers.put("Authorization", "Bearer " + tokenAuth)
            }
            if (param.size() > 0) {
                ls = param.collect { key, value -> key + "=" + value }.join("&")
            }
            String url = fmisUrl + path
            if (ls != "") {
                url = fmisUrl + path + "?" + ls
            }
            headers.put("Content-Type", "application/json")
            String resp = httpManaged.get(url, headers)
            return resp
        }
        catch (Exception ex) {
            return "{ \"Status\" : \"400\", \"Data\" : \"" + ex.message + "\" }"
        }
    }

    String post(String path, Map<String, String> param, Object body) {
        try {
            Map<String, String> headers = [:]
            def ls = ""
            // Get Token Auth của FMIS
            String tokenAuthJson = getTokenFromCache()
            def retToken = new JsonSlurper().parseText(tokenAuthJson)
            if (retToken["Status"] != "200") {
                return tokenAuthJson
            }
            String tokenAuth = retToken["Data"]
            if (tokenAuth != null && tokenAuth != "") {
                headers.put("Authorization", "Bearer " + tokenAuth)
            }
            if (param.size() > 0) {
                ls = param.collect { key, value -> key + "=" + value }.join("&")
            }
            String url = fmisUrl + path
            if (ls != "") {
                url = fmisUrl + path + "?" + ls
            }
            headers.put("Content-Type", "application/json")
            String resp = httpManaged.post([url: url], headers, json.writeValueAsString(body))
            return resp
        }
        catch (Exception ex) {
            return "{ \"Status\" : \"400\", \"Data\" : \"" + ex.message + "\" }"
        }

    }

    // Hàm xử lý lấy Token Fmis từ Redis -  Chưa xử lý thời gian hết hạn
    String getTokenFromCache() {
        def session = genericApi.getSessionData(request)
        if (session == null) {
            return null
        }
        String emailFull = session[GenericApi.Email].toString()
        String email = ""
        if (emailFull != null) {
            email = emailFull.split("@")[0]
        }
        String token = cacheManager.getCache(Constant.FMIS_CACHE_NAME_V2).get(email.toUpperCase() + "/serverToken", String.class)
        if (token == null) {
            return getNewToken(email)
        } else {
            DecodedJWT jwt = JWT.decode(token);
            Calendar calendar = Calendar.getInstance(); // gets a calendar using the default time zone and locale.
            calendar.add(Calendar.SECOND, 60);
            if (jwt.getExpiresAt().before(calendar.getTime())) {
                return getNewToken(email)
            }
        }
        return "{ \"Status\" : \"200\", \"Data\" : \"" + token + "\" }"
    }

    String getNewToken(String email) {
        try {
            Map<String, String> headers = [:]
            headers.put("Content-Type", "application/json")
            String url = fmisUrl + "TaiKhoan/get-token"
            String body = "{ \"Email\" : \"" + email.toLowerCase() + "\", \"PassWord\" : \"" + passWordDefault + "\" }"
            String tokenFull = httpManaged.post([url: url], headers, body)
            JSONObject json = new JSONObject(tokenFull);
            String token = ""
            if (json["error_code"] == "BSS-00000000") {
                token = json["data"]["token"]
            }
            if (token != null && token != "") {
                cacheManager.getCache(Constant.FMIS_CACHE_NAME_V2).put(email.toUpperCase() + "/serverToken", token)
            }
            return "{ \"Status\" : \"200\", \"Data\" : \"" + token + "\" }"
        }
        catch (Exception ex) {
            return "{ \"Status\" : \"400\", \"Data\" : \"" + ex.message + "\" }"
        }
    }

}
