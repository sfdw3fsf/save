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
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.QuyHoachVCIDInput;
import vn.vnpt.vnptit.ecms.service.QuyHoachVCIDService;

@RestController
@RequestMapping("/quyhoach-vcid")
public class QuyHoachVCIDController {
	private final QuyHoachVCIDService service;

	public QuyHoachVCIDController(QuyHoachVCIDService service) {
		this.service = service;
	}

	@Operation(summary = "Lấy danh sách trạm thiết bị", description = "Lấy danh sách trạm thiết bị")
	@RequestMapping(value = "/ds-tram-tbi", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> danhsach_tram_tbi() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.danhsach_tram_tbi());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách thiết bị theo trạm", description = "Lấy danh sách thiết bị theo trạm")
	@RequestMapping(value = "/ds-tbi-theo-tram", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> danhsach_tbi_theo_tram(@RequestParam("donvi_id") int donvi_id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.danhsach_tbi_theo_tram(donvi_id));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách quy hoạch VCID", description = "Lấy danh sách quy hoạch VCID")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> danhsach_quyhoach(@RequestParam("vlan") int vlan) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.danhsach_quyhoach(vlan));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Thêm quy hoạch VCID", description = "Thêm quy hoạch VCID")
	@RequestMapping(value = "/them-quyhoach-vcid", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> them_quyhoach_vcid(@RequestBody @Valid QuyHoachVCIDInput input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.them_quyhoach_vcid(input));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật quy hoạch VCID", description = "Cập nhật quy hoạch VCID")
	@RequestMapping(value = "/capnhat-quyhoach-vcid", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capnhat_quyhoach_vcid(@RequestParam("quyhoach_id") int quyhoach_id,
			@RequestBody @Valid QuyHoachVCIDInput input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.capnhat_quyhoach_vcid(quyhoach_id, input));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa quy hoạch VCID", description = "Xóa quy hoạch VCID")
	@RequestMapping(value = "/xoa-quyhoach-vcid", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> xoa_turack(@RequestParam("quyhoach_id") int quyhoach_id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.xoa_quyhoach_vcid(quyhoach_id)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

}
