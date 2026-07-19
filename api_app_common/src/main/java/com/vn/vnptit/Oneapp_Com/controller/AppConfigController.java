package com.vn.vnptit.Oneapp_Com.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vn.vnptit.Oneapp_Com.common.JwtService;
import com.vn.vnptit.Oneapp_Com.common.Scheduler;
import com.vn.vnptit.Oneapp_Com.common.Utils;
import com.vn.vnptit.Oneapp_Com.model.AccountParam;
import com.vn.vnptit.Oneapp_Com.model.DataSourceProperties;
import com.vn.vnptit.Oneapp_Com.model.ResponseObject;
import com.vn.vnptit.Oneapp_Com.service.AppConfigSerrvice;

import io.swagger.v3.oas.annotations.Operation;

/**
 * @author DanhNQ
 *
 */
@RestController
@Configuration
@RequestMapping("Config")
public class AppConfigController {
	@Autowired
	DataSourceProperties dataSourceProperties;

	@Autowired
	AppConfigSerrvice appConfigSerrvice;

	public AppConfigController(DataSourceProperties dataSourceProperties, AppConfigSerrvice appConfigSerrvice) {
		super();
		this.dataSourceProperties = dataSourceProperties;
		this.appConfigSerrvice = appConfigSerrvice;
	}

	@Operation(summary = "Thông tin cấu hình app")
	@PostMapping("/app_config")
	public ResponseEntity<Object> appConfig(HttpServletRequest request) {
		UUID uuid = UUID.randomUUID();
		String token = request.getHeader("Authorization");

		ResponseObject modelResponse;

		if (JwtService.validateAuthToken(token)) {

			return appConfigSerrvice.appConfig(token, uuid.toString());

		} else {
			modelResponse = Utils.accessDenied(uuid.toString());
		}
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);

	}

	@Operation(summary = "Kiểm tra user, pass trả về domain phù hợp")
	@PostMapping("/kiemtra_user_pass")
	public ResponseEntity<Object> kiemtraUserPass(HttpServletRequest request, @RequestBody AccountParam pr) {
		UUID uuid = UUID.randomUUID();
		return appConfigSerrvice.kiemtraUserPass(uuid.toString(), pr);

	}

	@Operation(summary = "Kiểm tra user, pass trả về domain phù hợp")
	@PostMapping("/cron")
	public ResponseEntity<Object> cron(HttpServletRequest request, @RequestBody AccountParam pr) {
		UUID uuid = UUID.randomUUID();
		Scheduler scheduler = new Scheduler();
		scheduler.cronJobGetTokenekyc();
		return appConfigSerrvice.kiemtraUserPass(uuid.toString(), pr);

	}

	@Operation(summary = "Lấy token EKYC")
	@PostMapping("/token_ekyc")
	public ResponseEntity<Object> token_ekyc(HttpServletRequest request) {
		UUID uuid = UUID.randomUUID();
		return appConfigSerrvice.token_ekyc(uuid.toString());

	}
}
