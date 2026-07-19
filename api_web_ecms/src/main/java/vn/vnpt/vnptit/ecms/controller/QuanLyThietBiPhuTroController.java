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
import vn.vnpt.vnptit.ecms.dto.QLTBPT_CapNhatDiaChiThietBiInput;
import vn.vnpt.vnptit.ecms.dto.QLTBPT_CapNhatThietBiNoiDungInput;
import vn.vnpt.vnptit.ecms.dto.QLTBPT_CapNhatVatTuThuocTinhInput;
import vn.vnpt.vnptit.ecms.dto.QLTBPT_KiemTraQuyenTBPTInput;
import vn.vnpt.vnptit.ecms.dto.QLTBPT_LayDsQuyenNVInput;
import vn.vnpt.vnptit.ecms.dto.QLTBPT_LayDsThietBiInput;
import vn.vnpt.vnptit.ecms.dto.QLTBPT_LayDsThuocTinhInput;
import vn.vnpt.vnptit.ecms.dto.QLTBPT_LayDsTiepNhanTBIV3Input;
import vn.vnpt.vnptit.ecms.service.QuanLyThietBiPhuTroService;

@RestController
@RequestMapping("quanlythietbiphutro")
public class QuanLyThietBiPhuTroController {
	private final QuanLyThietBiPhuTroService service;

	public QuanLyThietBiPhuTroController(QuanLyThietBiPhuTroService service) {
		this.service = service;
	}

	@Operation(summary = "Lấy danh sách tham số")
	@RequestMapping(value = "/getDsThamSo", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDsThamSo() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.getDsThamSo());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách thiết bị")
	@RequestMapping(value = "/getDsThietBi", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDsThietBi(@Valid @RequestBody QLTBPT_LayDsThietBiInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.getDsThietBi(item.getDonViID(), item.getKieu()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách lịch sử vận hành")
	@RequestMapping(value = "/getDsLichSuVanHanh", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDsLichSuVanHanh(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.getDsLichSuVanHanh(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách thuộc tính")
	@RequestMapping(value = "/getDsThuocTinh", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDsThuocTinh(@Valid @RequestBody QLTBPT_LayDsThuocTinhInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.getDsThuocTinh(item.getVatTuID(), item.getThietBiID()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách thuộc tính theo VT TB")
	@RequestMapping(value = "/getDsThuocTinhTheoVTTB", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDsThuocTinhTheoVTTB(@Valid @RequestBody QLTBPT_LayDsThuocTinhInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.getDsThuocTinhTheoVTTB(item.getVatTuID(), item.getThietBiID()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Phụ trợ lấy dánh sách theo thiết bị")
	@RequestMapping(value = "/phutro_lay_ds_theo_tbi", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> phutro_lay_ds_theo_tbi(@Valid @RequestParam String ma_vt, @RequestParam int kieu) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.phutro_lay_ds_theo_tbi(ma_vt, kieu));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách quyền nhân viên")
	@RequestMapping(value = "/getDsQuyenNhanVien", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDsQuyenNhanVien(@Valid @RequestBody QLTBPT_LayDsQuyenNVInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.getDsQuyenNhanVien(item.getNhanVienID(), item.getDonViID()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách thiết bị con")
	@RequestMapping(value = "/getDsThietBiCon", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDsThietBiCon(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.getDsThietBiCon(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Phụ trợ lấy danh sách tiếp nhận v3")
	@RequestMapping(value = "/phutro_lay_ds_tnhan_tbi_v3", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> phutro_lay_ds_tnhan_tbi_v3(
			@Valid @RequestBody QLTBPT_LayDsTiepNhanTBIV3Input item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.phutro_lay_ds_tnhan_tbi_v3(item));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lay_ThongTin_ThuocTinh_Theo_VatTu_ThietBi")
	@RequestMapping(value = "/Lay_ThongTin_ThuocTinh_Theo_VatTu_ThietBi", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> Lay_ThongTin_ThuocTinh_Theo_VatTu_ThietBi(@Valid @RequestParam int vattu_id,
			@RequestParam int thietbi_id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.Lay_ThongTin_ThuocTinh_Theo_VatTu_ThietBi(vattu_id, thietbi_id));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Phụ trợ lấy danh sách tiếp nhận thiết bị")
	@RequestMapping(value = "/phutro_lay_ds_tnhan_tbi", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> phutro_lay_ds_tnhan_tbi(@Valid @RequestParam int ttvh_id,
			@RequestParam int thietbi_id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.phutro_lay_ds_tnhan_tbi(ttvh_id, thietbi_id));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Kiểm tra quyền TBPT")
	@RequestMapping(value = "/kiemTraQuyenTBPT", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> kiemTraQuyenTBPT(@Valid @RequestBody QLTBPT_KiemTraQuyenTBPTInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.kiemTraQuyenTBPT(item.getNguoiDungID(), item.getQuyenID())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa thiết bị phụ trợ")
	@RequestMapping(value = "/xoaThietBiPhuTro", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> xoaThietBiPhuTro(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.xoaThietBiPhuTro(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật thuộc tính vật tư")
	@RequestMapping(value = "/capNhatVatTuThuocTinh", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capNhatVatTuThuocTinh(@Valid @RequestBody QLTBPT_CapNhatVatTuThuocTinhInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.capNhatVatTuThuocTinh(item.getThietBiID(), item.getTtvtID(),
					item.getGtttID(), item.getGiaTri())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật địa chỉ thiết bị")
	@RequestMapping(value = "/capNhatDiaChiThietBi", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capNhatDiaChiThietBi(@Valid @RequestBody QLTBPT_CapNhatDiaChiThietBiInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(
					service.capNhatDiaChiThietBi(item.getThietBiID(), item.getDiaChiID(), item.getTinhID(),
							item.getQuanID(), item.getPhuongID(), item.getPhoID(), item.getKhuID(), item.getApID(),
							item.getDacDiemID(), item.getSoNha(), item.getDiaChi(), item.getKinhDo(), item.getViDo())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật mã thiết bị")
	@RequestMapping(value = "/capNhatThietBiMaTBI", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capNhatThietBiMaTBI(@Valid @RequestBody QLTBPT_CapNhatThietBiNoiDungInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.capNhatThietBiMaTBI(item.getThietBiID(), item.getNoiDung())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật ghi chú thiết bị")
	@RequestMapping(value = "/capNhatThietBiGhiChu", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capNhatThietBiGhiChu(@Valid @RequestBody QLTBPT_CapNhatThietBiNoiDungInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.capNhatThietBiGhiChu(item.getThietBiID(), item.getNoiDung())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
}
