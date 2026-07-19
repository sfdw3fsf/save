package vn.vnpt.vnptit.ecms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.Message;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.QuyHoachVlanUpeInput;
import vn.vnpt.vnptit.ecms.service.QuyHoachVlanUpeService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/quyhoach-vlan-upe")
public class QuyHoachVlanUpeController {

	private final QuyHoachVlanUpeService service;

	public QuyHoachVlanUpeController(QuyHoachVlanUpeService service) {
		this.service = service;
	}

	@Operation(summary = "Lấy vlan upe", description = "Lấy vlan upe")
	@RequestMapping(value = "/lay", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> danhsach_quyhoach(@RequestParam(value = "vlan", required = false) Long vlan) {
		if (vlan == null || vlan == 0) {
			ApiResult apiResult = new ApiResult(Message.Codes.BSS_00009490);
			apiResult.setMessageDetail("Không tìm thấy tham số vlan");
			apiResult.setError(String.valueOf(HttpStatus.BAD_REQUEST.value()));
			return new ResponseEntity<ApiResult>(apiResult, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.lay(vlan));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy vlan upe", description = "Lấy vlan upe")
	@RequestMapping(value = "/lay-all-upe", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> layUpe() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.layUpe());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy all vlan upe", description = "Lấy all vlan upe")
	@RequestMapping(value = "/lay-all", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> danhsach_quyhoach_all() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.lay_all());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Thêm quy hoạch Vlan Upe", description = "Thêm quy hoạch Vlan Upe")
	@RequestMapping(value = "/them", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> them_quyhoach_vcid(@RequestBody @Valid QuyHoachVlanUpeInput input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.them(input));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật quy hoạch vlan upe", description = "Cập nhật quy hoạch vlan upe")
	@RequestMapping(value = "/capnhat", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capnhat(@RequestParam("quyhoachId") Long quyhoachId,
			@RequestBody @Valid QuyHoachVlanUpeInput input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.capnhat(quyhoachId, input));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa quy hoạch vlan upe", description = "Xóa quy hoạch vlan upe")
	@RequestMapping(value = "/xoa", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> xoa(@RequestParam("quyhoachId") Long quyhoachId) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.xoa(quyhoachId)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

}
