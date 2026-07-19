package vn.vnpt.vnptit.ecms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.SqlInjectionValidator;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.Test_ArrayContentInvalidInput;
import vn.vnpt.vnptit.ecms.dto.Test_ContentInvalidInput;
import vn.vnpt.vnptit.ecms.service.TestService;

@RestController
@RequestMapping("/test")
public class TestController {
	TestService testService = null;
	public TestController(TestService testService) {
		this.testService = testService;
	}
	
	@Operation(summary = "Kiểm tra nội dung hợp lệ")
	@RequestMapping(value = "/contentInvalid", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> contentInvalid(@Valid @RequestBody Test_ContentInvalidInput input) {
		ApiResult rs = new ApiResult();
		try {
			SqlInjectionValidator validator = new SqlInjectionValidator();
			Object result = validator.checkInvalid(input.getContent());
			rs.setData(new ResultOutput(result));
		} catch (Exception e) {
			rs.setMessageDetail(e.getMessage());
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Kiểm tra mảng nội dung hợp lệ")
	@RequestMapping(value = "/arrayContentInvalid", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> arrayContentInvalid(@Valid @RequestBody Test_ArrayContentInvalidInput input) {
		ApiResult rs = new ApiResult();
		try {
			SqlInjectionValidator validator = new SqlInjectionValidator();
			String[] cons = input.getContent();
			Map<String, Object> arrayResult = new HashMap<>();
			for (int i = 0; i < cons.length; i++) {
				if (!(cons[i] == null || cons[i].length() == 0)) {
					Object result = validator.checkInvalid(cons[i]);
					arrayResult.put(cons[i], result);
				}
			}
			rs.setData(arrayResult);
		} catch (Exception e) {
			rs.setMessageDetail(e.getMessage());
		}
		return rs.getResponseEntity();
	}
	
	@Operation(summary = "lay_ds_hopdong_theo_matb")
	@RequestMapping(value = "/lay_ds_hopdong_theo_matb", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> lay_ds_hopdong_theo_matb() {
		ApiResult rs = new ApiResult();
		try {
			Object result = this.testService.lay_ds_hopdong_theo_matb("ftth_halong_bgpto", 11, 0, 1, 268490, 0, 4, 0);
			rs.setData(result);
		} catch (Exception e) {
			rs.setMessageDetail(e.getMessage());
		}
		return rs.getResponseEntity();
	}
	
	@Operation(summary = "Kiểm tra kết nối IMS")
	@RequestMapping(value = "/test-ims", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> testIMS() {
		ApiResult rs = new ApiResult();
		try {
			Object result = this.testService.testIMS();
			rs.setData(result);
		} catch (Exception e) {
			rs.setMessageDetail(e.getMessage());
		}
		return rs.getResponseEntity();
	}
	
	@Operation(summary = "Lấy danh sách gói thầu trên IMS")
	@RequestMapping(value = "/get-ims-goithau", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getGoiThauTrenIMS() {
		ApiResult rs = new ApiResult();
		try {
			Object result = this.testService.getGoiThauTrenIMS();
			rs.setData(result);
		} catch (Exception e) {
			rs.setMessageDetail(e.getMessage());
		}
		return rs.getResponseEntity();
	}
	
	@Operation(summary = "Lấy danh sách hợp đồng trên IMS")
	@RequestMapping(value = "/get-ims-hopdong", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getHopDongTrenIMS() {
		ApiResult rs = new ApiResult();
		try {
			Object result = this.testService.getHopDongTrenIMS();
			rs.setData(result);
		} catch (Exception e) {
			rs.setMessageDetail(e.getMessage());
		}
		return rs.getResponseEntity();
	}
}