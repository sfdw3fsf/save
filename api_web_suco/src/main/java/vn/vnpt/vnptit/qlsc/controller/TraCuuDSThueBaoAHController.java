package vn.vnpt.vnptit.qlsc.controller;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.qlsc.dto.DstbDto;
import vn.vnpt.vnptit.qlsc.dto.LayDsNhanVienXlTheoNvDto;
import vn.vnpt.vnptit.qlsc.dto.TraCuuDSTB;
import vn.vnpt.vnptit.qlsc.service.TraCuuDSThueBaoAHService;

@RestController
@RequestMapping("/tracuu/thue-bao-anh-huong")
public class TraCuuDSThueBaoAHController {
	private final TraCuuDSThueBaoAHService service;

	public TraCuuDSThueBaoAHController(TraCuuDSThueBaoAHService service) {
		this.service = service;
	}

//	@Operation(summary = "Thông tin chi tiết sự cố", description = "Thông tin chi tiết sự cố")
//	@RequestMapping(value = "", method = RequestMethod.GET)
//	public ResponseEntity<ApiResult> thongtin_suco(@RequestParam("suco_id") int suco_id) {
//		ApiResult rs = new ApiResult();
//		try {
//			rs.setData(service.thongtin_suco(suco_id));
//		} catch (AppSqlException e) {
//			rs.setException(e);
//		}
//		return rs.getResponseEntity();
//	}
//	
//	@Operation(summary = "Danh sách cổng bị ảnh hưởng sự cố", description = "Danh sách cổng bị ảnh hưởng sự cố")
//	@RequestMapping(value = "/ds-cong-bi-anhhuong", method = RequestMethod.GET)
//	public ResponseEntity<ApiResult> ds_cong_bi_anhhuong(@RequestParam("suco_id") int suco_id) {
//		ApiResult rs = new ApiResult();
//		try {
//			rs.setData(service.ds_cong_bi_anhhuong(suco_id));
//		} catch (AppSqlException e) {
//			rs.setException(e);
//		}
//		return rs.getResponseEntity();
//	}
//	
//	@Operation(summary = "Danh sách nhân viên xử lý", description = "Danh sách nhân viên xử lý")
//	@RequestMapping(value = "/ds-nhanvien-xuly", method = RequestMethod.GET)
//	public ResponseEntity<ApiResult> ds_nhanvien_xl(@RequestParam("phieu_id") int phieu_id,@RequestParam("suco_ptm_id") int suco_ptm_id) {
//		ApiResult rs = new ApiResult();
//		try {
//			rs.setData(service.ds_nhanvien_xuly(phieu_id,suco_ptm_id));
//		} catch (AppSqlException e) {
//			rs.setException(e);
//		}
//		return rs.getResponseEntity();
//	}
//
//	@Operation(summary = "Danh sách báo tồn", description = "Danh sách báo tồn")
//	@RequestMapping(value = "/ds-bao-ton", method = RequestMethod.GET)
//	public ResponseEntity<ApiResult> ds_baoton(@RequestParam("phieu_id") int phieu_id) {
//		ApiResult rs = new ApiResult();
//		try {
//			rs.setData(service.ds_baoton(phieu_id));
//		} catch (AppSqlException e) {
//			rs.setException(e);
//		}
//		return rs.getResponseEntity();
//	}
//
//	@Operation(summary = "Danh sách nhân viên xử lý theo nhân viên")
//	@RequestMapping(value = "/ds-nhanvien-xl-theo-nv", method = RequestMethod.POST)
//	public ResponseEntity<ApiResult> lay_ds_nhanvien_xl_theo_nv(@RequestBody LayDsNhanVienXlTheoNvDto input) {
//		ApiResult rs = new ApiResult();
//		try {
//			rs.setData(service.lay_ds_nhanvien_xl_theo_nv(input));
//		} catch (AppSqlException e) {
//			rs.setException(e);
//		}
//		return rs.getResponseEntity();
//	}
//
//	@Operation(summary = "Danh sách nhân viên xử lý theo đơn vị")
//	@RequestMapping(value = "/ds-nhanvien-xl-theo-dv", method = RequestMethod.GET)
//	public ResponseEntity<ApiResult> lay_ds_nhanvien_xl_theo_dv(@RequestParam("donvi_id") Integer donViId) {
//		ApiResult rs = new ApiResult();
//		try {
//			rs.setData(service.lay_ds_nhanvien_xl_theo_dv(donViId));
//		} catch (AppSqlException e) {
//			rs.setException(e);
//		}
//		return rs.getResponseEntity();
//	}
	
	@Operation(summary = "HT_DichVuVT_Combobox")
	@RequestMapping(value = "/ht-dichvuvt-combobox", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> HT_DichVuVT_Combobox(@RequestParam("dichvuvt_id") String dichvuvt_id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.HT_DichVuVT_Combobox(dichvuvt_id));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
	@Operation(summary = "NAP_DS_TRANGTHAI_SC")
	@RequestMapping(value = "/nap-ds-trangthai-sc", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> NAP_DS_TRANGTHAI_SC() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.NAP_DS_TRANGTHAI_SC());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	} 
	@Operation(summary = "SP_LAY_DSTB_THEO_JSON")
	@RequestMapping(value = "/sp-lay-dstb-theo-json", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> SP_LAY_DSTB_THEO_JSON(@RequestBody List<DstbDto> input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.SP_LAY_DSTB_THEO_JSON(input));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		catch (JsonProcessingException e) {			
		}
		return rs.getResponseEntity();
	}
	@Operation(summary = "Post_TraCuuDSTB_CNTT_AnhHuong")
	@RequestMapping(value = "/post-tracuudstb-cntt-anhhuong", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> Post_TraCuuDSTB_CNTT_AnhHuong(@RequestBody TraCuuDSTB input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.Post_TraCuuDSTB_CNTT_AnhHuong(input));
		} catch (AppSqlException e) {
			rs.setException(e);
		}		
		return rs.getResponseEntity();
	}
}
