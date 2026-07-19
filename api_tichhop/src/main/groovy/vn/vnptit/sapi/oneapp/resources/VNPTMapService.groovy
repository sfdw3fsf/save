package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonSlurper
import io.swagger.model.BaseResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.MessageUtils

import java.nio.charset.StandardCharsets

@RestController
@RequestMapping("vnpt_map")
class VNPTMapService {
    @Autowired
    HttpManaged httpManaged

    @Value("\${spring.httpservices.vnpt_map.url}")
    String domain

    @Value("\${spring.httpservices.vnpt_map.key}")
    String key

    @PostMapping("/address_by_latlong")
    ResponseEntity<BaseResponse> address_by_latlong(@RequestBody ADDRESS_BY_LATLONG_REQ body) {
        UUID uuid = UUID.randomUUID()
        String requestId = uuid.toString()
        try {
            String url = domain + "/places/v1/geocoding/address_by_latlong?Lat=" + body.lat + "&Lon=" + body.lon
            return execute(url, "GET", body, requestId)
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/latlong_by_address")
    ResponseEntity<BaseResponse> latlong_by_address(@RequestBody LATLONG_BY_ADDRESS_REQ body) {
        UUID uuid = UUID.randomUUID()
        String requestId = uuid.toString()
        try {
            String url = domain + "/places/v1/geocoding/latlong_by_address?q=address:" + URLEncoder.encode(body.address, StandardCharsets.UTF_8.toString())
            return execute(url, "POST", body, requestId)
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/directions")
    ResponseEntity<BaseResponse> directions(@RequestBody DIRECTIONS body) {
        UUID uuid = UUID.randomUUID()
        String requestId = uuid.toString()
        try {
            body.algorithm = "alternative_route"
            body.weighting = "fastest"
            body.turn_cost = true
            String url = domain + "/directions/v1/path"
            return execute(url, "POST", body, requestId)
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    ResponseEntity<BaseResponse> execute(String url, String method, def body, String requestId) {
        Map<String, String> headers = [:]
        headers.put("Content-Type", "application/json")
        headers.put("Authorization", key)
        if (body["location"]) {
            headers.put("Location", body["location"])
        }
        String resp = ""
        if (method == "GET") {
            resp = httpManaged.get(url, headers)
        } else {
            resp = httpManaged.post([url: url], headers, new ObjectMapper().writeValueAsString(body))
        }
        return xlResp(resp, requestId)
    }

    ResponseEntity<BaseResponse> xlResp(String resp, String requestId) {
        def res = new JsonSlurper().parseText(resp)
        if (res["error"].toString() == "200" && res["status"].toString() == "true") {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(res["result"], requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } else {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, res["msg"], ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
}

class LATLONG_BY_ADDRESS_REQ {
    String address
    String location
}

class ADDRESS_BY_LATLONG_REQ {
    String lat
    String lon
    String location
}

class DIRECTIONS {
    String vehical
    String points
    String algorithm
    boolean turn_cost
    String weighting
    String location
}
