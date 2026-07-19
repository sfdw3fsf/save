package vn.vnpt.vnptit.qlsc.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.common.Converter;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.qlsc.dto.*;
import vn.vnpt.vnptit.qlsc.service.QuanLySuCoService;

@Controller
@RequestMapping("quanlysuco")
public class QuanLySuCoController {
	private QuanLySuCoService quanLySuCoService;

	public QuanLySuCoController(QuanLySuCoService quanLySuCoService) {
		this.quanLySuCoService = quanLySuCoService;
	}

	@Operation(summary = "Lấy danh sách nguồn sự cố")
	@RequestMapping(value = "/getDsNguonSuCo", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDsNguonSuCo() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.getDsNguonSuCo());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách nhóm sự cố theo nhân viên")
	@RequestMapping(value = "/getDsNhomSuCoTheoNhanVien", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDsNhomSuCoTheoNhanVien() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.getDsNhomSuCo(AppRequestContext.getCurrent().getNhanVienID()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy mã sự cố mới")
	@RequestMapping(value = "/getMaSuCo", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getMaSuCo(@Valid @RequestBody GetMaSuCoInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLySuCoService.getMaSuCo(item.getNguonSuCoID(), Converter.toDateTime(item.getNgay(), true))));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Kiểm tra điều kiện giao phiếu")
	@RequestMapping(value = "/kiemTraDieuKienGiaoPhieu", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> kiemTraDieuKienGiaoPhieu(@Valid @RequestBody KiemTraDieuKienGiaoPhieuInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.kiemTraDieuKienGiaoPhieu(item.getDonViNguonID(), item.getDonViDichID(), item.getNhomSuCoID(), item.getThuTuID()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Kiểm tra điều kiện tiếp nhận")
	@RequestMapping(value = "/kiemTraDieuKienTiepNhan", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> kiemTraDieuKienTiepNhan(@Valid @RequestBody KiemTraDieuKienTiepNhanInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.kiemTraDieuKienTiepNhan(item.getKieu(), item.getMaSuCo()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách tình trạng CNTT")
	@RequestMapping(value = "/layDsTinhTrangCNTT", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> layDsTinhTrangCNTT(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.layDsTinhTrangCNTT(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách tình trạng MVT")
	@RequestMapping(value = "/layDsTinhTrangMVT", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> layDsTinhTrangMVT(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.layDsTinhTrangMVT(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách tình trạng MNV")
	@RequestMapping(value = "/layDsTinhTrangMNV", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> layDsTinhTrangMNV(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.layDsTinhTrangMNV(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách sự cố tạm")
	@RequestMapping(value = "/layDsSuCoTam", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> layDsSuCoTam(@Valid @RequestBody LayDsSuCoTamInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.layDsSuCoTam(item.getMaSuCo(), item.getNguonSuCoID()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách sự cố chưa phân nhóm")
	@RequestMapping(value = "/layDsSuCoCPN", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> layDsSuCoCPN(@Valid @RequestBody LayDsSuCoCPNInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.layDsSuCoCPN(item.getNguonSuCoID()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách sự cố đã phân nhóm")
	@RequestMapping(value = "/layDsSuCoDPN", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> layDsSuCoDPN(@Valid @RequestBody LayDsSuCoDPNInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.layDsSuCoDPN(item.getNguonSuCoID()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách sự cố đã phân nhóm (có filter)")
	@RequestMapping(value = "/layDsSuCoDPNFilter", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> layDsSuCoDPNFilter(@Valid @RequestBody LayDsSuCoFilterInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.layDsSuCoDPNFilter(item));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật thông tin sự cố")
	@RequestMapping(value = "/capNhatSuCo", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capNhatSuCo(@Valid @RequestBody CapNhatSuCoInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLySuCoService.capNhatSuCo(item)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật ảnh chụp phiếu sự cố")
	@RequestMapping(value = "/capNhatAnhChup", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capNhatAnhChup(@Valid @RequestBody CapNhatAnhChupInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLySuCoService.capNhatAnhChup(item.getPhieuID(), item.getMoTa())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật thông tin sự cố tạm")
	@RequestMapping(value = "/capNhatSuCoTam", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capNhatSuCoTam(@Valid @RequestBody CapNhatSuCoTamInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLySuCoService.capNhatSuCoTam(item)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Tiếp nhận sự cố tạm")
	@RequestMapping(value = "/tiepNhanSuCoTam", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> tiepNhanSuCoTam(@Valid @RequestBody TiepNhanSuCoTamInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.tiepNhanSuCoTam(item));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Tạo sự cố tạm")
	@RequestMapping(value = "/taoSuCoTam", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> taoSuCoTam(@Valid @RequestBody TaoSuCoTamInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLySuCoService.taoSuCoTam(item)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}	

	@Operation(summary = "Tiếp nhận sự cố")
	@RequestMapping(value = "/tiepNhanSuCo", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> tiepNhanSuCo(@Valid @RequestBody TiepNhanSuCoInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.tiepNhanSuCo(item));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách cổng bị ảnh hưởng bởi sự cố")
	@RequestMapping(value = "/layDsCongBiAnhHuong", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> layDsCongBiAnhHuong(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.layDsCongBiAnhHuong(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách thuê bao bị ảnh hưởng bởi sự cố")
	@RequestMapping(value = "/layDsThueBaoBiAnhHuong", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> layDsThueBaoBiAnhHuong(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.layDsThueBaoBiAnhHuong(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa phiếu sự cố")
	@RequestMapping(value = "/xoaPhieu", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> xoaPhieu(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLySuCoService.xoaPhieu(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa sự cố tạm")
	@RequestMapping(value = "/xoaSuCoTam", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> xoaSuCoTam(@Valid @RequestBody XoaSuCoTamInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLySuCoService.xoaSuCoTam(item.getMaSuCo())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Hủy phiếu sự cố")
	@RequestMapping(value = "/huyPhieu", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> huyPhieu(@Valid @RequestBody HuyPhieuSuCoInput item) {
		ApiResult rs = new ApiResult();
		try {
        	Integer[] suCoIDs = item.getSuCoIDs();
        	String msg = "";
        	if(suCoIDs.length == 0) 
        		msg = "Danh sách phiếu không được để trống";
        	else
        	{
        		for (Integer suCoID : suCoIDs) {
					if(suCoID == null || suCoID < 1)
					{
						msg = "Danh sách phiếu có thông không hợp lệ";
						break;
					}
				}
        	}
        	if(msg.equals(""))
        		rs.setData(new ResultOutput(quanLySuCoService.huyPhieu(item.getSuCoIDs(), item.getTraPhieu())));
        	else
        		rs.setDataInvalidMessage(msg);
			
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách cổng sợi")
	@RequestMapping(value = "/layDsCongSoi", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> layDsCongSoi(@Valid @RequestBody LayDsCongSoiInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLySuCoService.layDsCongSoi(item.getLoaiThietBiID(), item.getThietBiID())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách nhóm sự cố theo nhân viên")
	@RequestMapping(value = "/layDsNhomSuCoTheoNhanVien", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> layDsNhomSuCoTheoNhanVien(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.layDsNhomSuCoTheoNhanVien(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách phiếu theo nhân viên")
	@RequestMapping(value = "/layDsPhieuTheoNhanVien", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> layDsPhieuTheoNhanVien(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.layDsPhieuTheoNhanVien(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}	

	@Operation(summary = "Gán danh sách sự cố cho sự cố cha")
	@RequestMapping(value = "/ganDsSuCoChoSuCoCha", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> ganDsSuCoChoSuCoCha(@Valid @RequestBody GanDsSuCoChoSuCoChaInput item) {
		ApiResult rs = new ApiResult();
		try {
        	Integer[] suCoIDs = item.getSuCoIDs();
        	String msg = "";
        	if(suCoIDs.length == 0) 
        		msg = "Danh sách phiếu không được để trống";
        	else
        	{
        		for (Integer suCoID : suCoIDs) {
					if(suCoID == null || suCoID < 1)
					{
						msg = "Danh sách phiếu có thông không hợp lệ";
						break;
					}
				}
        	}
        	if(msg.equals(""))
        		rs.setData(new ResultOutput(quanLySuCoService.ganDsSuCoChoSuCoCha(item.getSuCoIDs(), item.getSuCoChaID())));
        	else
        		rs.setDataInvalidMessage(msg);
			
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}	
	

	@Operation(summary = "Gỡ sự cố ra khỏi nhóm sự cố cha")
	@RequestMapping(value = "/goNhomSuCo", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> goNhomSuCo(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLySuCoService.goNhomSuCo(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách thuê bao theo loại hình")
	@RequestMapping(value = "/layDsThueBaoTheoLoaiHinh", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> layDsThueBaoTheoLoaiHinh(@RequestBody LayDsThueBaoTheoLoaiHinhDto input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.layDsThueBaoTheoLoaiHinh(input));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách thuê bao trên cổng")
	@RequestMapping(value = "/layDsThueBaoTrenCong", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> layDsThueBaoTrenCong(@RequestBody DataKeyInput input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.layDsThueBaoTrenCong(input));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách thuê bao theo dịch vụ")
	@RequestMapping(value = "/layDsThueBaoTheoDichVu", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> layDsThueBaoTheoDichVu(@RequestBody LayDsThueBaoTheoDichVuDto input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.layDsThueBaoTheoDichVu(input));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách thuê bao theo phần tử mạng")
	@RequestMapping(value = "/layDsThueBaoTheoPtm", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> layDsThueBaoTheoPtm(@RequestBody LayDsThueBaoTheoPtmDto input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.layDsThueBaoTheoPtm(input));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách tham số mặc định")
	@RequestMapping(value = "/layDsThamSoMd", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<ApiResult> layDsThamSoMd() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.layDsThamSoMd());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Tiếp nhận sự cố tạm v2")
	@RequestMapping(value = "/tiepNhanSuCoTam_v2", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> tiepNhanSuCoTamV2(@Valid @RequestBody TiepNhanSuCoTamInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLySuCoService.tiepNhanSuCoTamV2(item));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Tạo sự cố tạm V2")
	@RequestMapping(value = "/taoSuCoTam_v2", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> taoSuCoTamV2(@Valid @RequestBody TaoSuCoTamInput2 item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLySuCoService.taoSuCoTamV2(item)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@PostMapping(value = "/suCoMNVSendSMS", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ApiResult> suCoMNVSendSMS(@RequestBody SuCoMNVSendSMSNhanVienDTO input) {
		ApiResult rs = new ApiResult();
		String err = input.validSendSMS();
		if (!err.isEmpty()) {
			rs.setException(new AppSqlException(err));
			return rs.getResponseEntity();
		}
		try {
			rs.setData(quanLySuCoService.suCoMNVSendSMS(input));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@PostMapping(value = "/suCoMNVSendSMSDanhSach", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ApiResult> suCoMNVSendSMSDanhSach(@RequestBody SuCoMNVSendSMSNhanVienDTO input) {
		ApiResult rs = new ApiResult();
		String err = input.valid();
		if (!err.isEmpty()) {
			rs.setException(new AppSqlException(err));
			return rs.getResponseEntity();
		}
		try {
			rs.setData(quanLySuCoService.suCoMNVSendSMSDanhSach(input));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
}
