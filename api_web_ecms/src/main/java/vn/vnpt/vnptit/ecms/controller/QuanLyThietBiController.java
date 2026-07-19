package vn.vnpt.vnptit.ecms.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.CapNhatRacksDslamInput;
import vn.vnpt.vnptit.ecms.dto.QLTB_BrasInput;
import vn.vnpt.vnptit.ecms.dto.QLTB_BrasInsertInput;
import vn.vnpt.vnptit.ecms.dto.QLTB_CardGponInsert;
import vn.vnpt.vnptit.ecms.dto.QLTB_CardGponUpdate;
import vn.vnpt.vnptit.ecms.dto.QLTB_CardGpon_KT_ThayCardInput;
import vn.vnpt.vnptit.ecms.dto.QLTB_CardGpon_KT_ViTriInput;
import vn.vnpt.vnptit.ecms.dto.QLTB_CardManeInsert;
import vn.vnpt.vnptit.ecms.dto.QLTB_CardManeUpdate;
import vn.vnpt.vnptit.ecms.dto.QLTB_CaySoDoInput;
import vn.vnpt.vnptit.ecms.dto.QLTB_KT_SerialInput;
import vn.vnpt.vnptit.ecms.dto.QLTB_TBI_GOKETNOI;
import vn.vnpt.vnptit.ecms.dto.QLTB_TBI_KETNOI;
import vn.vnpt.vnptit.ecms.dto.QLTB_TBI_MAKETNOI;
import vn.vnpt.vnptit.ecms.dto.QLTB_timKiemThietBi;
import vn.vnpt.vnptit.ecms.service.QuanLyThietBiService;

@RestController
@RequestMapping("quanlythietbi")
public class QuanLyThietBiController {
	private final QuanLyThietBiService quanLyThietBiService;

	public QuanLyThietBiController(QuanLyThietBiService quanLyThietBiService) {
		this.quanLyThietBiService = quanLyThietBiService;
	}

	@Operation(summary = "Lấy thông tin BRAS")
	@RequestMapping(value = "/getBras", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> getBras(int id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getBras(id));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Thêm mới thông tin BRAS")
	@RequestMapping(value = "/themBras", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> themBras(@Valid @RequestBody QLTB_BrasInsertInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.themBras(item));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
	@Operation(summary = "Thêm mới thông tin BRAS")
	@RequestMapping(value = "/themBrasNew", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> themBrasNew(@Valid @RequestBody QLTB_BrasInput item) {
		ApiResult rs = new ApiResult();
		try {
			item.setPhanvung_id(AppRequestContext.getCurrent().getPhanVungID());
			rs.setData(quanLyThietBiService.themBrasNew(item));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
	@Operation(summary = "Cập nhật thông tin BRAS")
	@RequestMapping(value = "/capNhatBras", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capNhatBras(@Valid @RequestBody QLTB_BrasInput item) {
		ApiResult rs = new ApiResult();
		try {
			item.setPhanvung_id(AppRequestContext.getCurrent().getPhanVungID());
			rs.setData(new ResultOutput(quanLyThietBiService.capNhatBras(item)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa thông tin BRAS")
	@RequestMapping(value = "/xoaBras", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> xoaBras(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLyThietBiService.xoaBras(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy mã kết nối của thiết bị")
	@RequestMapping(value = "/getMaKetNoiThietBi", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getMaKetNoiThietBi(@Valid @RequestBody QLTB_TBI_MAKETNOI item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getMaKetNoiThietBi(item.getLoaitbi_id_n(), item.getLoaitbi_id_d()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Kết nối thiết bị")
	@RequestMapping(value = "/ketNoiThietBi", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> ketNoiThietBi(@Valid @RequestBody QLTB_TBI_KETNOI item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLyThietBiService.ketNoiThietBi(item.getPorvl_id_n(), item.getLoaitbi_id_n(),
					item.getPorvl_id_d(), item.getLoaitbi_id_d(), item.getThietbi_id_n(), item.getThietbi_id_d(),
					item.getLink_code())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Gỡ kết nối thiết bị")
	@RequestMapping(value = "/goKetNoiThietBi", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> goKetNoiThietBi(@Valid @RequestBody QLTB_TBI_GOKETNOI item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLyThietBiService.goKetNoiThietBi(item.getPorvl_id_n(),
					item.getLoaitbi_id_n(), item.getPorvl_id_d(), item.getLoaitbi_id_d())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật danh sách RACKS cho DSLAM")
	@RequestMapping(value = "/capNhatRacksDslam", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capNhatRacksDslam(@Valid @RequestBody CapNhatRacksDslamInput item) {
		ApiResult rs = new ApiResult();
		try {
			Integer[] racks_id = item.getRacks_id();
			String msg = "";
			if (racks_id.length == 0)
				msg = "Danh sách RACKS không được để trống";
			else {
				for (Integer rack_id : racks_id) {
					if (rack_id == null || rack_id < 1) {
						msg = "Danh sách RACKS có thông không hợp lệ";
						break;
					}
				}
			}
			if (msg.equals(""))
				rs.setData(new ResultOutput(quanLyThietBiService.capNhatRacksDslam(item.getDslam_id(), racks_id)));
			else
				rs.setDataInvalidMessage(msg);
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách RACK đã gán")
	@RequestMapping(value = "/getDsRackDaGan", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDsRackDaGan(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getDsRackDaGan(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách RACK chưa gán")
	@RequestMapping(value = "/getDsRackChuaGan", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDsRackChuaGan(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getDsRackChuaGan(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy thông tin kết nối theo portvl_id")
	@RequestMapping(value = "/getThongTinKetNoi", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getThongTinKetNoi(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getThongTinKetNoi(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy cây danh mục thiết bị")
	@RequestMapping(value = "/getCayDanhMucThietBi", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getCayDanhMucThietBi() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getCayDanhMucThietBi());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy cây danh mục đơn vị V2")
	@RequestMapping(value = "/getCayDanhMucDonViV2", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getCayDanhMucDonViV2(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getCayDanhMucDonViV2(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy cây danh mục thiết bị V2")
	@RequestMapping(value = "/getCayDanhMucThietBiV2", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getCayDanhMucThietBiV2(@Valid @RequestBody QLTB_CaySoDoInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getCayDanhMucThietBiV2(item.getId(), item.getKieuTu()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Tìm kiếm thiết bị trên cây danh mục V2")
	@RequestMapping(value = "/timKiemCayDanhMucThietBiV2", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> timKiemCayDanhMucThietBiV2(@Valid @RequestBody QLTB_timKiemThietBi item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.timKiemCayDanhMucThietBiV2(item.getKieuTu(), item.getText()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh mục tủ rack theo kiểu tủ")
	@RequestMapping(value = "/getDanhMucTuRackTheoKieuTu", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDanhMucTuRackTheoKieuTu(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getDanhMucTuRackTheoKieuTu(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh mục tủ rack")
	@RequestMapping(value = "/getDanhMucTuRack", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDanhMucTuRack() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getDanhMucTuRack());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh mục tủ ODF")
	@RequestMapping(value = "/getDanhMucOdf", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDanhMucOdf() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getDanhMucOdf());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh mục BRAS")
	@RequestMapping(value = "/getDanhMucBras", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDanhMucBras() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getDanhMucBras());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh mục Gpon")
	@RequestMapping(value = "/getDanhMucGpon", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDanhMucGpon() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getDanhMucGpon());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh mục Mane")
	@RequestMapping(value = "/getDanhMucMane", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDanhMucMane() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getDanhMucMane());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh mục Switch")
	@RequestMapping(value = "/getDanhMucSwitch", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDanhMucSwitch() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getDanhMucSwitch());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh mục Dslam")
	@RequestMapping(value = "/getDanhMucDslam", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDanhMucDslam() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getDanhMucDslam());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh mục Frame")
	@RequestMapping(value = "/getDanhMucFrame", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDanhMucFrame() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getDanhMucFrame());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh mục Ewsd")
	@RequestMapping(value = "/getDanhMucEwsd", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDanhMucEwsd() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getDanhMucEwsd());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách Card Ewsd")
	@RequestMapping(value = "/getDsCardEwsd", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDsCardEwsd() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getDsCardEwsd());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy cây danh mục thiết bị truyền dẫn")
	@RequestMapping(value = "/getCayDanhMucThietBiTD", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getCayDanhMucThietBiTD() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getCayDanhMucThietBiTD());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy thông tin card gpon")
	@RequestMapping(value = "/getThongTinCardGpon", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getThongTinCardGpon(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getThongTinCardGpon(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Thêm thông tin card gpon")
	@RequestMapping(value = "/themThongTinCardGpon", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> themThongTinCardGpon(@Valid @RequestBody QLTB_CardGponInsert item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.themThongTinCardGpon(item.getGpon_id(), item.getLoaicard_id(),
					item.getVitri(), item.getTen_card(), item.getSerial(), item.getPartnumber(), item.getIstaoportlg() ? 1 : 0));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật thông tin card gpon")
	@RequestMapping(value = "/capNhatThongTinCardGpon", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capNhatThongTinCardGpon(@Valid @RequestBody QLTB_CardGponUpdate item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLyThietBiService.capNhatThongTinCardGpon(item.getCardgp_id(),
					item.getGpon_id(), item.getLoaicard_id(), item.getVitri(), item.getTen_card(), item.getSerial(),
					item.getPartnumber(), item.getIstaoportlg() ? 1 : 0)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa thông tin card gpon")
	@RequestMapping(value = "/xoaThongTinCardGpon", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> xoaThongTinCardGpon(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLyThietBiService.xoaThongTinCardGpon(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Kiểm tra vị trí GPON")
	@RequestMapping(value = "/kiemTraVitriCardGpon", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> kiemTraVitriCardGpon(@Valid @RequestBody QLTB_CardGpon_KT_ViTriInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLyThietBiService.kiemTraVitriCardGpon(item.getGpon_id(), item.getVitri())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Kiểm tra serial thiết bị")
	@RequestMapping(value = "/kiemTraSerialThietBi", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> kiemTraSerialThietBi(@Valid @RequestBody QLTB_KT_SerialInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLyThietBiService.kiemTraSerialThietBi(item.getSerial())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Kiểm tra thay card GPON")
	@RequestMapping(value = "/kiemTraThayCardGpon", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> kiemTraThayCardGpon(@Valid @RequestBody QLTB_CardGpon_KT_ThayCardInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLyThietBiService.kiemTraThayCardGpon(item.getCardgp_id(), item.getSoport_moi())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách port card GPON")
	@RequestMapping(value = "/getDsPortCardGpon", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDsPortCardGpon(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getDsPortCardGpon(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Kiểm tra xóa card GPON")
	@RequestMapping(value = "/kiemTraXoaCardGpon", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> kiemTraXoaCardGpon(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLyThietBiService.kiemTraXoaCardGpon(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Kiểm tra port card GPON đang sử dụng")
	@RequestMapping(value = "/kiemTraPortCardGponDangSuDung", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> kiemTraPortCardGponDangSuDung(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLyThietBiService.kiemTraPortCardGponDangSuDung(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	//Mane
	@Operation(summary = "Lấy thông tin card mane")
	@RequestMapping(value = "/getThongTinCardMane", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getThongTinCardMane(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getThongTinCardMane(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Thêm thông tin card mane")
	@RequestMapping(value = "/themThongTinCardMane", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> themThongTinCardMane(@Valid @RequestBody QLTB_CardManeInsert item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.themThongTinCardMane(item.getDslam_id(), item.getLoaicard_id(),
					item.getVitri(), item.getTen_card(), item.getSerial(), item.getPartnumber()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật thông tin card Mane")
	@RequestMapping(value = "/capNhatThongTinCardMane", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capNhatThongTinCardMane(@Valid @RequestBody QLTB_CardManeUpdate item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLyThietBiService.capNhatThongTinCardMane(item.getCardmane_id(),
					item.getDslam_id(), item.getLoaicard_id(), item.getVitri(), item.getTen_card(), item.getSerial(),
					item.getPartnumber())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa thông tin card mane")
	@RequestMapping(value = "/xoaThongTinCardMane", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> xoaThongTinCardMane(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLyThietBiService.xoaThongTinCardMane(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách port card mane")
	@RequestMapping(value = "/getDsPortCardMane", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDsPortCardMane(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.getDsPortCardMane(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Kiểm tra port card mane đang sử dụng")
	@RequestMapping(value = "/kiemTraPortCardManeDangSuDung", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> kiemTraPortCardManeDangSuDung(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(quanLyThietBiService.kiemTraPortCardManeDangSuDung(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Kiểm tra tính năng thiết bị L2SW")
	@RequestMapping(value = "/kiemTraTinhNangThietBiL2SW", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> kiemTraTinhNangThietBiL2SW() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(quanLyThietBiService.kiemTraTinhNangThietBiL2SW());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
}
