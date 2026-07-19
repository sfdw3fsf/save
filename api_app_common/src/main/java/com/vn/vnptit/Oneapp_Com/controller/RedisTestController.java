package com.vn.vnptit.Oneapp_Com.controller;

import com.vn.vnptit.Oneapp_Com.common.ConstantString;
import com.vn.vnptit.Oneapp_Com.common.JwtService;
import com.vn.vnptit.Oneapp_Com.managed.RedisManaged;
import com.vn.vnptit.Oneapp_Com.model.ResponseObject;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("redis-test")
public class RedisTestController {

    @Autowired
    @Qualifier("redisManaged")
    private RedisManaged redisManaged;

    @Operation(summary = "Mock save with partition from token")
    @PostMapping("/save")
    public ResponseEntity<Object> testSave(HttpServletRequest request, @RequestBody Map<String, Object> body) {
        UUID uuid = UUID.randomUUID();
        String token = request.getHeader(ConstantString.AUTHORIZATION);
        
        String phanVung = "UNKNOWN";
        if (token != null && token.contains(" ")) {
            phanVung = JwtService.getPhanVungFromToken(token.split(" ")[1]);
        }

        String id_danhmucs = (String) body.get("id_danhmucs");
        String param_customize = (String) body.get("param_customize");
        String value = (String) body.get("value");
        
        String prefix = "get_danhmuc";
        String key = "|" + phanVung + "|" + (id_danhmucs != null ? id_danhmucs : "") + "|" + (param_customize != null ? param_customize : "");
        
        redisManaged.saveToCache(prefix, key, value, 10);
        
        ResponseObject modelResponse = new ResponseObject();
        modelResponse.setError("0");
        modelResponse.setMessage("Saved with key format: App_common/get_danhmuc|tinh|id|param");
        modelResponse.setRequest_id(uuid.toString());
        
        return ResponseEntity.ok(modelResponse);
    }

    @Operation(summary = "Mock get with partition from token")
    @PostMapping("/get")
    public ResponseEntity<Object> testGet(HttpServletRequest request, @RequestBody Map<String, Object> body) {
        UUID uuid = UUID.randomUUID();
        String token = request.getHeader(ConstantString.AUTHORIZATION);
        
        String phanVung = "UNKNOWN";
        if (token != null && token.contains(" ")) {
            phanVung = JwtService.getPhanVungFromToken(token.split(" ")[1]);
        }
        
        String id_danhmucs = (String) body.get("id_danhmucs");
        String param_customize = (String) body.get("param_customize");
        
        String prefix = "get_danhmuc";
        String key = "|" + phanVung + "|" + (id_danhmucs != null ? id_danhmucs : "") + "|" + (param_customize != null ? param_customize : "");
        
        Object cachedData = redisManaged.getFromCache(prefix, key);
        
        ResponseObject modelResponse = new ResponseObject();
        modelResponse.setData(cachedData);
        modelResponse.setError("0");
        modelResponse.setMessage("Retrieved with key format: App_common/get_danhmuc|tinh|id|param");
        modelResponse.setRequest_id(uuid.toString());
        
        return ResponseEntity.ok(modelResponse);
    }
}
