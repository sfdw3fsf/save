package vn.vnptit.sapi.oneapp.resources

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.google.gson.Gson
import com.netflix.client.http.HttpRequest
import groovy.json.JsonSlurper
import io.swagger.model.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import org.apache.commons.httpclient.HttpClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.SP_CHANGE_AGENT_STATE_REQ
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.GenericApi

import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType


@RestController
@RequestMapping("obtelesale")
class OBTeleSaleService {

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

    @Value("\${spring.httpservices.ob_telesale.url}")
    String obtelesale_url

    @Value("\${spring.httpservices.ob_telesale.iss}")
    String obtelesale_iss

    @Value("\${spring.httpservices.ob_telesale.key}")
    String obtelesale_key

    @PostMapping("/sp_change_agent_state")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "UR2.11.009_100 sp_change_agent_state", description = "thuanln.hcm", tags = ["UR2.11.100"])
    CommonResponse sp_change_agent_state(@RequestBody(required = true)  SP_CHANGE_AGENT_STATE_REQ body){
        try{
            long iat = System.currentTimeMillis()/1000L  // Thời gian hiện tại (millisecond)
            long exp = iat + 300  // Thêm 5 phút (300 giây)

            Map<?,?> parameters = [:]
            parameters.put("iat", iat.toString())
            parameters.put("exp", exp.toString())
            parameters.put("iss", obtelesale_iss)
            parameters.put("parameters", body)

            //lấy thông tin token
            String token = generateTokenChangeAgent(body, iat.toString(), exp.toString(), obtelesale_iss, obtelesale_key)
            String resp = HttpManaged.post(["url": obtelesale_url + "extension/change-agent-state"], ["Authorization": "Bearer " + token], new Gson().toJson(parameters))
            def res = new JsonSlurper().parseText(resp.substring(1))
            if(res?.success == true){
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: res?.message?.toString(), data: res?.data)
            }else {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: res?.message?.toString(), data: res?.data)
            }
        }catch (Exception ex){
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @PostMapping("/call-outbound")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "UR2.11.009_100 sp_change_agent_state", description = "thuanln.hcm", tags = ["UR2.11.100"])
    CommonResponse call_outbound(@RequestBody(required = true)  Map<?,?> body){
        try{
            long iat = System.currentTimeMillis()/1000L  // Thời gian hiện tại (millisecond)
            long exp = iat + 300  // Thêm 5 phút (300 giây)

            Map<String, Object> session = genericApi.getSessionData(request)
            Long phanvung_id = Long.parseLong(session.get("id_tinhthanh").toString())
            String ma_tinh = session.get("ma_tinh")

            Map<?,?> parameters = [:]
            parameters.put("iat", iat.toString())
            parameters.put("exp", exp.toString())
            parameters.put("iss", obtelesale_iss)
            parameters.put("parameters", body)

            //lấy thông tin token
            String token = generateTokenCallOutbound(body, phanvung_id, ma_tinh, iat.toString(), exp.toString(), obtelesale_iss, obtelesale_key)
            String resp = HttpManaged.post(["url": obtelesale_url + "call/call-outbound"], ["Authorization": "Bearer " + token], new Gson().toJson(parameters))
            def res = new JsonSlurper().parseText(resp.substring(1))
            if(res?.success == true){
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: res?.message?.toString(), data: res?.data)
            }else {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: res?.message?.toString(), data: res?.data)
            }
        }catch (Exception ex){
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @PostMapping("/get-cdr")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "UR2.11.009_100 sp_change_agent_state", description = "thuanln.hcm", tags = ["UR2.11.100"])
    CommonResponse get_cdr(@RequestBody(required = true)  Map<?,?> body){
        try{
            long iat = System.currentTimeMillis()/1000L  // Thời gian hiện tại (millisecond)
            long exp = iat + 300  // Thêm 5 phút (300 giây)

            Map<?,?> parameters = [:]
            parameters.put("iat", iat.toString())
            parameters.put("exp", exp.toString())
            parameters.put("iss", obtelesale_iss)
            parameters.put("parameters", body)

            //lấy thông tin token
            String token = generateTokenGetCdr(body, iat.toString(), exp.toString(), obtelesale_iss, obtelesale_key)
            String resp = HttpManaged.post(["url": obtelesale_url + "outbound/get-cdr"], ["Authorization": "Bearer " + token], new Gson().toJson(parameters))
            def res = new JsonSlurper().parseText(resp.substring(1))
            if(res?.success == true){
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: res?.message?.toString(), data: res?.data)
            }else {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: res?.message?.toString(), data: res?.data)
            }
        }catch (Exception ex){
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    String generateTokenChangeAgent(SP_CHANGE_AGENT_STATE_REQ params,String iat, String exp, String iss, String key){

        //tạo parameters
        def parameters = [:]
        parameters.put("username", params.username)
        parameters.put("extension", params.extension)
        parameters.put("status", params.status)

        // tạo header
        def header = [:];
        header.put("alg", "HS256")
        header.put("typ", "JWT")

        try{
            String token = JWT.create()
                    .withHeader(header)
                    .withClaim("iat", iat)
                    .withClaim("exp", exp)
                    .withClaim("iss", iss)
                    .withClaim("parameters", parameters)
                    .sign(Algorithm.HMAC256(key))
            return token
        }catch (Exception err){
            return ""
        }
    }

    String generateTokenCallOutbound(Map params, Long phanvung_id, String ma_tinh, String iat, String exp, String iss, String key){

        //tạo parameters
        def parameters = [:]
        parameters.put("phanvung_id", phanvung_id)
        parameters.put("ob_id", params.ob_id)
        parameters.put("dauso_ob", "0888000666")
        parameters.put("sdt_ob", params.sdt_ob)
        parameters.put("ten_ct", params.ten_ct)
        parameters.put("ma_ct", params.ma_ct)
        parameters.put("ma_tinh", ma_tinh)
        parameters.put("user_ipcc", params.user_ipcc)
        parameters.put("line", params.line)
        parameters.put("source", "onebss_ob")

        // tạo header
        def header = [:];
        header.put("alg", "HS256")
        header.put("typ", "JWT")

        try{
            String token = JWT.create()
                    .withHeader(header)
                    .withClaim("iat", iat)
                    .withClaim("exp", exp)
                    .withClaim("iss", iss)
                    .withClaim("parameters", parameters)
                    .sign(Algorithm.HMAC256(key))
            return token
        }catch (Exception err){
            return ""
        }
    }

    String generateTokenGetCdr(Map params,String iat, String exp, String iss, String key){

        //tạo parameters
        def parameters = [:]
        parameters.put("date", params.date)

        // tạo header
        def header = [:];
        header.put("alg", "HS256")
        header.put("typ", "JWT")

        try{
            String token = JWT.create()
                    .withHeader(header)
                    .withClaim("iat", iat)
                    .withClaim("exp", exp)
                    .withClaim("iss", iss)
                    .withClaim("parameters", parameters)
                    .sign(Algorithm.HMAC256(key))
            return token
        }catch (Exception err){
            return ""
        }
    }

}
