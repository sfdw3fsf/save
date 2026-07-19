package vn.vnpt.vnptit.ecms.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.ThemTuRackInput;
import vn.vnpt.vnptit.ecms.dto.TuRackInput;
import vn.vnpt.vnptit.ecms.service.TuRackService;

@RestController
@RequestMapping("/danhmuc/turack")
public class TuRackController {
	private final TuRackService tuRackService;

	public TuRackController(TuRackService tuRackService) {
		this.tuRackService = tuRackService;
	}

	@Operation(summary = "Lấy danh sách tủ rack theo kiểu tủ", description = "Lấy danh sách tủ rack theo kiểu tủ")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> danhsach_turack(@RequestParam("kieutu") int kieutu) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(tuRackService.danhsach_turack(kieutu));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy thông tin chi tiết một tủ rack", description = "Lấy thông tin chi tiết một tủ rack")
	@RequestMapping(value = "/chitiet", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<ApiResult> thongtin_turack_id(@RequestParam("turack_id") int turack_id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(tuRackService.thongtin_turack_id(turack_id));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Thêm một tủ rack", description = "Thêm một tủ rack")
	@RequestMapping(value = "/them", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> them_turack(@RequestBody @Valid ThemTuRackInput turack) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(tuRackService.them_turack(turack));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật một tủ rack", description = "Cập nhật một tủ rack")
	@RequestMapping(value = "/capnhat", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capnhat_turack(@RequestBody @Valid TuRackInput turack) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(tuRackService.sua_turack(turack)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa một tủ rack", description = "Xóa một tủ rack")
	@RequestMapping(value = "/xoa", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> xoa_turack(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(tuRackService.xoa_turack(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách tủ rack theo đơn vị và kiểu tủ")
	@RequestMapping(value = "/ds-turack-theo-donvi-va-kieutu", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> lay_ds_turack_theo_donvi_va_kieutu(@RequestBody TuRackInput obj) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(tuRackService.lay_ds_turack_theo_donvi_va_kieutu(obj));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách tủ rack", description = "Lấy danh sách tủ rack")
	@RequestMapping(value = "/get-all", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> ds_turack() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(tuRackService.ds_turack());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách tủ rack theo đơn vị")
	@RequestMapping(value = "/ds-turack-theo-donvi", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> lay_ds_turack_theo_donvi(@RequestBody TuRackInput obj) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(tuRackService.lay_ds_turack_theo_donvi(obj));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
}
