package vn.vnpt.vnptit.ecms.controller.danhmuc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.*;
import vn.vnpt.vnptit.ecms.service.danhmuc.DsLamService;

import javax.validation.Valid;

@RestController
@RequestMapping("danhmuc/dongthietbi")
public class DsLamController {
	private final DsLamService dsLamService;

	public DsLamController(DsLamService dsLamService) {
		this.dsLamService = dsLamService;
	}

	@Operation(summary = "Liệt kê danh sách nhóm DSLAM")
	@RequestMapping(value = "/lietKeDs", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> lietKeDs() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamService.DsNhomDsLam());
		} catch (AppSqlException e) {
			// TODO Auto-generated catch block
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Liệt kê danh sách nhóm DSLAM có filter")
	@RequestMapping(value = "/lietKeDsFilter", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> lietKeDsFilter(@Valid @RequestBody LoaiDSLamObjectDtoIn loaiDSlamDtoIn) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamService.DsNhomDsLamTheoObject(loaiDSlamDtoIn));
		} catch (AppSqlException e) {
			// TODO Auto-generated catch block
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Liệt kê danh sách nhóm DSLAM theo ID")
	@RequestMapping(value = "/lietKeDsTheoID", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> lietKeDsTheoID( @RequestParam("p_loai_dslam_id") Long p_loai_dslam_id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamService.DsNhomDsLamTheoID(p_loai_dslam_id));
		} catch (AppSqlException e) {
			// TODO Auto-generated catch block
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Thêm mới nhóm DSLAM")
	@RequestMapping(value = "/them", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> add(@Valid @RequestBody LoaiDSlamDtoIn loaiDSlamDtoIn) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamService.themNhomDsLam(loaiDSlamDtoIn));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật nhóm DSLAM")
	@RequestMapping(value = "/capnhat", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capnhat(@Valid @RequestBody LoaiDSlamUpdDtoIn loaiDSlamDtoIn) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamService.capNhomDsLam(loaiDSlamDtoIn));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa nhóm DSLAM")
	@RequestMapping(value = "/xoa", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> xoa(@RequestParam("loai_dslam_id") Long loai_dslam_id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(dsLamService.xoaNhomDsLam(loai_dslam_id)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Liệt kê danh sách Dslam, Gpon, Switch", description = "Liệt kê danh sách Dslam, Gpon, Switch")
	@RequestMapping(value = "/lietke_danhsach_dslam/{loaitbi_id}", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> lietKeDanhSachDslam(@PathVariable("loaitbi_id") int loaiTbiId) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamService.lietKeDanhSachDslam(loaiTbiId));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Thêm mới thông tin Dslam, Gpon, Switch", description = "Thêm mới thông tin Dslam, Gpon, Switch")
	@RequestMapping(value = "/them_dslam", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> themDslam(@Valid @RequestBody ThemDslamDtoIn themDslamDtoIn) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamService.themDslam(themDslamDtoIn));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật thông tin Dslam, Gpon, Switch", description = "Cập nhật thông tin Dslam, Gpon, Switch")
	@RequestMapping(value = "/capnhat_dslam", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capNhatDslam(@Valid @RequestBody CapNhatDslamDtoIn capNhatDslamDtoIn) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(dsLamService.capNhatDslam(capNhatDslamDtoIn)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa thông tin Dslam, Gpon, Switch", description = "Xóa thông tin Dslam, Gpon, Switch")
	@RequestMapping(value = "/xoa_dslam", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> xoaDslam(@RequestBody XoaDslamDtoIn xoaDslamDtoIn) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(dsLamService.xoaDslam(xoaDslamDtoIn)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy thông tin Dslam, Gpon, Switch", description = "Lấy thông tin Dslam, Gpon, Switch")
	@RequestMapping(value = "/thongtin_dslam/{dslam_id}", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> layThongTinDslam(@PathVariable("dslam_id") int dslamId) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamService.layThongTinDslam(dslamId));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy port theo Switch", description = "Lấy port theo Switch")
	@RequestMapping(value = "/lay_port_theo_switch/{switch_id}", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> layPortTheoSwitch(@PathVariable("switch_id") int switchId) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamService.layPortTheoSwitch(switchId));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy tham số md", description = "Lấy port theo Switch")
	@RequestMapping(value = "/lay_tham_so_md", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> layThamSoMd(@Valid @RequestBody ThamSoMD obj) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamService.layThamSoMd(obj));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy dslam cha", description = "Lấy dslam cha")
	@RequestMapping(value = "/lay_ds_dslam_cha/{bras_id}", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> layDsDslamCha(@PathVariable("bras_id") int bras_id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamService.layDsDslamCha(bras_id));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy ds đối tác", description = "Lấy ds đối tác")
	@RequestMapping(value = "/lay_ds_doitac", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> layDsDoiTac() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamService.layDsDoiTac());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy ds tòa nhà", description = "Lấy ds tòa nhà")
	@RequestMapping(value = "/lay_ds_toanha", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> layDsToaNha() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamService.layDsToaNha());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Kiểm tra trùng ip/tên", description = "Kiểm tra trùng ip/tên")
	@RequestMapping(value = "/kiemtra-trung-ip", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> kiemTraTrungIP(@Valid @RequestBody DslamDtoInp obj) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamService.kiemTraTrungIP(obj));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy ds hãng sản xuất", description = "Lấy ds hãng sản xuất")
	@RequestMapping(value = "/ds-hangsx", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> layDsHangSX() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamService.layDsHangSX());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Version 2 Thêm mới thông tin Dslam, Gpon, Switch", description = "Thêm mới thông tin Dslam, Gpon, Switch")
	@RequestMapping(value = "/them_dslam_v2", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> themDslamV2(@Valid @RequestBody ThemDslamDtoIn themDslamDtoIn) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamService.themDslamV2(themDslamDtoIn));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Version 2 Cập nhật thông tin Dslam, Gpon, Switch", description = "Cập nhật thông tin Dslam, Gpon, Switch")
	@RequestMapping(value = "/capnhat_dslam_v2", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capNhatDslamV2(@Valid @RequestBody CapNhatDslamDtoIn capNhatDslamDtoIn) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(dsLamService.capNhatDslamV2(capNhatDslamDtoIn)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
}
