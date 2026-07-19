package vn.vnpt.vnptit.ecms.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.ChuyenDSKhoaMoMaySangHopDongKhoaMoInput;
import vn.vnpt.vnptit.ecms.service.ThuNoService;

@RestController
@RequestMapping("/thuno")
public class ThuNoController {
	ThuNoService thuNoService = null;
	public ThuNoController(ThuNoService thuNoService) {
		this.thuNoService = thuNoService;
	}
	
	@Operation(summary = "Chuyển danh sách khóa mở máy sang hợp đồng khóa mở")
	@RequestMapping(value = "chuyen-hd-khoamo-may", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> chuyenDSKhoaMoMaySangHopDongKhoaMo(@Valid @RequestBody ChuyenDSKhoaMoMaySangHopDongKhoaMoInput item) {
		ApiResult rs = new ApiResult();
		try {
			Object result = this.thuNoService.chuyenDSKhoaMoMaySangHopDongKhoaMo(item);
			rs.setData(result);
		} catch (Exception e) {
			rs.setMessageDetail(e.getMessage());
		}
		return rs.getResponseEntity();
	}
	
}