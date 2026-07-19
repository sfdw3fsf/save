package com.vn.vnptit.Oneapp_Com.controller;

import com.vn.vnptit.Oneapp_Com.common.JwtService;
import com.vn.vnptit.Oneapp_Com.common.Utils;
import com.vn.vnptit.Oneapp_Com.model.ResponseObject;
import com.vn.vnptit.Oneapp_Com.model.ThongBaoModel;
import com.vn.vnptit.Oneapp_Com.service.DonHangSimKitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@Configuration
@RequestMapping("donhangsimkit")
public class DonHangSimKitController {
    @Autowired
    DonHangSimKitService donHangSimKitService;

//    @PostMapping("/ds_donhang_huy")
//    public ResponseEntity<Object> dsDonHangHuy(HttpServletRequest request) {
//        UUID uuid = UUID.randomUUID();
//        String token = request.getHeader("Authorization");
//        String appSecret = request.getHeader("App-secret");
//        ResponseObject modelResponse = null;
//
//        if (JwtService.validateAuthToken(token)) {
//            donHangSimKitService.layDanhSachDonHangCanHuy();
//                return ResponseEntity.status(Integer.parseInt("200")).body(modelResponse);
//        } else {
//            modelResponse = Utils.accessDenied(uuid.toString());
//        }
//        return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
//
//    }
}
