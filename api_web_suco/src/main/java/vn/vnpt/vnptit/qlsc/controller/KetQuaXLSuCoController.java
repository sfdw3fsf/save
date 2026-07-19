package vn.vnpt.vnptit.qlsc.controller;

import io.swagger.v3.oas.annotations.Operation;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.qlsc.dto.*;
import vn.vnpt.vnptit.qlsc.service.KetQuaXLSuCoService;

@RestController
@RequestMapping("/ketqua-xuly-suco")
public class KetQuaXLSuCoController {
	private final KetQuaXLSuCoService service;

	public KetQuaXLSuCoController(KetQuaXLSuCoService service) {
		this.service = service;
	}

	@Operation(summary = "Danh sách phiếu", description = "Danh sách phiếu")
	@RequestMapping(value = "/danhsach-phieu", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> ds_phieu_dieuto(@RequestBody @Valid PhieuDieuToInput input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.ds_phieu_dieuto(input));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
	
	@Operation(summary = "Danh sách nhóm sự cố", description = "Danh sách nhóm sự cố")
	@RequestMapping(value = "/danhsach-nhom-suco", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<ApiResult> ds_nhom_suco() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.ds_nhom_suco());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
	
	@Operation(summary = "Danh sách nguồn sự cố", description = "Danh sách nguồn sự cố")
	@RequestMapping(value = "/danhsach-nguon-suco", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<ApiResult> ds_nguon_suco() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.ds_nguon_suco());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
	
	@Operation(summary = "Danh sách phần tử mạng", description = "Danh sách phần tử mạng")
	@RequestMapping(value = "/danhsach-phantu-mang", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<ApiResult> ds_phantu_mang(@RequestParam("phieu_id") int phieu_id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.ds_phantu_mang(phieu_id));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
	
	@Operation(summary = "Danh sách giao việc nhân viên", description = "Danh sách giao việc nhân viên")
	@RequestMapping(value = "/danhsach-giaoviec-nv", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<ApiResult> ds_giaoviec_nv(@RequestParam("phieu_id") int phieu_id,@RequestParam("suco_ptm_id") int suco_ptm_id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.ds_giaoviec(phieu_id,suco_ptm_id));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật ghi chú phiếu xử lý", description = "Cập nhật ghi chú phiếu xử lý")
	@RequestMapping(value = "/capnhat-ghichu", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capnhat_ghichu(@RequestBody @Valid GhiChuPXLInput input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.capnhat_ghichu(input.getphieu_id(),input.getghi_chu())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
	
	@Operation(summary = "Hoàn công phiếu xử lý", description = "Hoàn công phiếu xử lý")
	@RequestMapping(value = "/hoan-cong", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> hoan_cong(@RequestBody @Valid HoanCongPXLInput input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.hoan_cong(input)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật ghi chú phần từ mạng")
	@RequestMapping(value = "/capnhat-ghichu-ptm", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capnhat_ghichu_ptm(@RequestBody @Valid GhiChuPtmInput input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.capnhat_ghichu_ptm(input)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật ghi chú xử lý nhân viên")
	@RequestMapping(value = "/capnhat-ghichu-xuly-nv", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capnhat_ghichu_xuly_nv(@RequestBody @Valid GhiChuXuLyNvInput input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.capnhat_ghichu_xuly_nv(input)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Hủy giao việc nhân viên")
	@RequestMapping(value = "/huy-giaoviec-nv", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> huy_giaoviec_nv(@RequestParam("giaoviec_id") Long giaoViecId) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.xuLySMSHuyGiaoViec(giaoViecId));
			//rs.setData(service.huy_giaoviec_nv(giaoViecId));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Sms giao việc nhân viên")
	@RequestMapping(value = "/sms-giaoviec-nv", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> sms_giaoviec_nv(@RequestParam("giaoviec_id") Long giaoViecId) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.sms_giaoviec_nv(giaoViecId)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Trả phiếu sự cố")
	@RequestMapping(value = "/traphieu-suco", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> traphieu_suco(@RequestBody @Valid TraPhieuSuCoInput input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.traphieu_suco(input)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật báo tồn")
	@RequestMapping(value = "/capnhat-baoton", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capnhat_baoton(@RequestBody @Valid CapNhatBaoTonInput input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.capnhat_baoton(input)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Hủy báo tồn")
	@RequestMapping(value = "/huy-baoton", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> huy_baoton(@RequestBody @Valid HuyBaoTonInput input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.huy_baoton(input)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách in phiếu sự cố")
	@RequestMapping(value = "/lay-ds-inphieu-suco", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> lay_ds_inphieu_suco(@RequestBody LayDsInPhieuSuCoInput input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.lay_ds_inphieu_suco(input));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Giao việc nhân viên")
	@RequestMapping(value = "/giaoviec-nv", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> giaoviec_nv(@RequestBody GiaoViecNvInput input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.xuLySMSGiaoViecNV(input)));
			//rs.setData(service.giaoviec_nv(input));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật trạng thái gửi sms")
	@RequestMapping(value = "/capnhat-trangthai-gui-sms", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capnhat_trangthai_gui_sms(@RequestBody CapNhatTrangThaiGuiSms input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.capnhat_trangthai_gui_sms(input)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy nội dung gửi sms giao việc nhân viên")
	@RequestMapping(value = "/lay-nd-gui-sms", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<ApiResult> lay_nd_gui_sms(@RequestParam("giaoviec_id") Long giaoViecId) {
		ApiResult rs = new ApiResult();
		try {
			//rs.setData(service.lay_nd_gui_sms(giaoViecId));
			rs.setData(service.xuLySMSGiaoViec(giaoViecId));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật ảnh chụp")
	@RequestMapping(value = "/capnhat-anhchup", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capnhat_anhchup(@RequestBody CapNhatAnhChupDto input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.capnhat_anhchup(input)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
}
