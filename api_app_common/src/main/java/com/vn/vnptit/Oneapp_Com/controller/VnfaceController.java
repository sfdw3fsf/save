package com.vn.vnptit.Oneapp_Com.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vn.vnptit.Oneapp_Com.common.ConstantString;
import com.vn.vnptit.Oneapp_Com.common.JwtService;
import com.vn.vnptit.Oneapp_Com.common.Utils;
import com.vn.vnptit.Oneapp_Com.model.ResponseObject;
import com.vn.vnptit.Oneapp_Com.model.VnFaceHisCheckinRequest;
import com.vn.vnptit.Oneapp_Com.service.VnfaceService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@Configuration
@RequestMapping("vnface")
public class VnfaceController {

    @Autowired
    VnfaceService vnfaceService;

    @Operation(summary = "Lay access token VNFace")
    @PostMapping("/user-token")
    public ResponseEntity<Object> getAccountAccessToken(HttpServletRequest request) {
        UUID uuid = UUID.randomUUID();
        String token = request.getHeader(ConstantString.AUTHORIZATION);

        if (token == null || token.trim().isEmpty() || !JwtService.validateAuthToken(token)) {
            ResponseObject unauthorized = Utils.accessDenied(uuid.toString());
            return ResponseEntity.status(Integer.parseInt(unauthorized.getError())).body(unauthorized);
        }

        return vnfaceService.getAccountAccessToken(uuid.toString(), token);
    }

    @Operation(summary = "Lay lich su checkin theo uuid")
    @PostMapping("/his-checkin-by-uuid")
    public ResponseEntity<Object> getCheckinHistory(HttpServletRequest request,
                                                    @RequestBody VnFaceHisCheckinRequest body) {
        UUID uuid = UUID.randomUUID();
        String token = request.getHeader(ConstantString.AUTHORIZATION);

        if (token == null || token.trim().isEmpty() || !JwtService.validateAuthToken(token)) {
            ResponseObject unauthorized = Utils.accessDenied(uuid.toString());
            return ResponseEntity.status(Integer.parseInt(unauthorized.getError())).body(unauthorized);
        }

        String uuidHisCheckin = body != null ? body.getUuidHisCheckin() : null;
        return vnfaceService.getCheckinHistory(uuid.toString(), token, uuidHisCheckin);
    }
}