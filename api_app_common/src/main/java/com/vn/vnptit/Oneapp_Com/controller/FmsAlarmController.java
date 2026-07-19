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

import com.vn.vnptit.Oneapp_Com.common.JwtService;
import com.vn.vnptit.Oneapp_Com.common.Utils;
import com.vn.vnptit.Oneapp_Com.model.DataSourceProperties;
import com.vn.vnptit.Oneapp_Com.model.FrmInputModel;
import com.vn.vnptit.Oneapp_Com.model.ResponseObject;
import com.vn.vnptit.Oneapp_Com.service.AppConfigSerrvice;
import com.vn.vnptit.Oneapp_Com.service.FmsService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@Configuration
@RequestMapping("fms_alarms")
public class FmsAlarmController {
	@Autowired
	DataSourceProperties dataSourceProperties;

	@Autowired
	FmsService fmsService;

	public FmsAlarmController(DataSourceProperties dataSourceProperties, AppConfigSerrvice appConfigSerrvice) {
		super();
		this.dataSourceProperties = dataSourceProperties;
		this.fmsService = fmsService;
	}
	@Operation(summary = "Đẩy thông tin qua NET")
	@PostMapping("/post_net_dhsx_to_tts")
	public ResponseEntity<Object> post_net_dhsx_to_tts(HttpServletRequest request, @RequestBody FrmInputModel frmInputModel) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader("Authorization");

		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {

			return fmsService.post_net_dhsx_to_tts( uuid.toString(), frmInputModel, token);

		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

}
