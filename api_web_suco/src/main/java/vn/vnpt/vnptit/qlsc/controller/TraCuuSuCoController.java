package vn.vnpt.vnptit.qlsc.controller;

import io.swagger.v3.oas.annotations.Operation;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.qlsc.dto.LayDsNhanVienXlTheoNvDto;
import vn.vnpt.vnptit.qlsc.service.TraCuuSuCoService;

@RestController
@RequestMapping("/tracuu/suco")
public class TraCuuSuCoController {
	private final TraCuuSuCoService traCuuSuCoService;

	public TraCuuSuCoController(TraCuuSuCoService traCuuSuCoService) {
		this.traCuuSuCoService = traCuuSuCoService;
	}

	@Operation(summary = "Thông tin chi tiết sự cố", description = "Thông tin chi tiết sự cố")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> thongtin_suco(@RequestParam("suco_id") int suco_id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(traCuuSuCoService.thongtin_suco(suco_id));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
	
	@Operation(summary = "Danh sách cổng bị ảnh hưởng sự cố", description = "Danh sách cổng bị ảnh hưởng sự cố")
	@RequestMapping(value = "/ds-cong-bi-anhhuong", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> ds_cong_bi_anhhuong(@RequestParam("suco_id") int suco_id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(traCuuSuCoService.ds_cong_bi_anhhuong(suco_id));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
	
	@Operation(summary = "Danh sách nhân viên xử lý", description = "Danh sách nhân viên xử lý")
	@RequestMapping(value = "/ds-nhanvien-xuly", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> ds_nhanvien_xl(@RequestParam("phieu_id") int phieu_id,@RequestParam("suco_ptm_id") int suco_ptm_id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(traCuuSuCoService.ds_nhanvien_xuly(phieu_id,suco_ptm_id));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Danh sách báo tồn", description = "Danh sách báo tồn")
	@RequestMapping(value = "/ds-bao-ton", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> ds_baoton(@RequestParam("phieu_id") int phieu_id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(traCuuSuCoService.ds_baoton(phieu_id));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Danh sách nhân viên xử lý theo nhân viên")
	@RequestMapping(value = "/ds-nhanvien-xl-theo-nv", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> lay_ds_nhanvien_xl_theo_nv(@RequestBody LayDsNhanVienXlTheoNvDto input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(traCuuSuCoService.lay_ds_nhanvien_xl_theo_nv(input));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Danh sách nhân viên xử lý theo đơn vị")
	@RequestMapping(value = "/ds-nhanvien-xl-theo-dv", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> lay_ds_nhanvien_xl_theo_dv(@RequestParam("donvi_id") Integer donViId) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(traCuuSuCoService.lay_ds_nhanvien_xl_theo_dv(donViId));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
}
