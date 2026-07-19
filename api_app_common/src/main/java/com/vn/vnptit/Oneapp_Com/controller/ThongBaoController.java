
package com.vn.vnptit.Oneapp_Com.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vn.vnptit.Oneapp_Com.common.JwtService;
import com.vn.vnptit.Oneapp_Com.common.Utils;
import com.vn.vnptit.Oneapp_Com.model.NotificationRequest;
import com.vn.vnptit.Oneapp_Com.model.ResponseObject;
import com.vn.vnptit.Oneapp_Com.model.ThongBaoModel;
import com.vn.vnptit.Oneapp_Com.service.FCMService;
import com.vn.vnptit.Oneapp_Com.service.ThongBaoService;

import io.swagger.v3.oas.annotations.Operation;

/**
 * @author DanhNQ
 *
 */
@RestController
@Configuration
@RequestMapping("thongbao")
public class ThongBaoController {
	@Autowired
	ThongBaoService thongBaoService;
	@Autowired
	private FCMService fcmService;

	@Operation(summary = "Danh sách thông báo")
	@PostMapping("/ds_thongbao")
	public ResponseEntity<Object> dsThongbao(HttpServletRequest request, @RequestBody ThongBaoModel thongBaoModel) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader("Authorization");
		String appSecret = request.getHeader("App-secret");
		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {
			if (!TextUtils.isEmpty(appSecret)) {
				return thongBaoService.danhsachThongbao(token, thongBaoModel.getNoidung(), uuid.toString());
			} else {
				modelResponse = Utils.badRequestAppSecret(uuid.toString());
			}
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

	@Operation(summary = "Chi tiết thông báo")
	@PostMapping("/chitiet_thongbao")
	public ResponseEntity<Object> chitietThongbao(HttpServletRequest request,
			@RequestBody ThongBaoModel thongBaoModel) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader("Authorization");
		String appSecret = request.getHeader("App-secret");
		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {
			if (!TextUtils.isEmpty(appSecret)) {
				return thongBaoService.chitietThongbao(token, thongBaoModel.getP_id_thongbao(), uuid.toString());
			} else {
				modelResponse = Utils.badRequestAppSecret(uuid.toString());
			}
		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

	@Operation(summary = "Push notification to firebase")
	@PostMapping("/send_to_device")
	public String sendPnsToDevice(@RequestBody NotificationRequest notificationRequestDto) {
		return fcmService.sendPnsToDevice(notificationRequestDto);
	}

}
