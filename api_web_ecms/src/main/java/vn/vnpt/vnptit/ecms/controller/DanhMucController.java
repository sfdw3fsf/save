package vn.vnpt.vnptit.ecms.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.DonViDtoInp;
import vn.vnpt.vnptit.ecms.dto.danhmuc.DanhMucParam;
import vn.vnpt.vnptit.ecms.service.DanhMucService;

@RestController
@RequestMapping("danhmuc")
public class DanhMucController {
	private final DanhMucService danhMucService;

	public DanhMucController(DanhMucService danhMucService) {
		this.danhMucService = danhMucService;
	}
    @Operation(summary = "Danh mục tỉnh thành")
    @GetMapping("/tinh-thanh")
    public ResponseEntity<ApiResult> listTinhThanh() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("TINH-THANH", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Find đơn vị")
    @PostMapping("/find-don-vi")
    public ResponseEntity<ApiResult> findDonVi(@RequestBody DanhMucParam param) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("FIND-DON-VI", param.getThamSo1(), param.getThamSo2()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Find sản phẩm dịch vụ")
    @PostMapping("/find-san-pham-dich-vu")
    public ResponseEntity<ApiResult> findSanPhamDichVu(@RequestBody DanhMucParam param) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("HATANG_SPDICHVU", param.getThamSo1(), param.getThamSo2()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Danh sách nhân viên thuộc đơn vị")
    @GetMapping("/nhan-vien-don-vi")
    public ResponseEntity<ApiResult> danhSachNhanVien(@Valid @RequestParam("donViId") Integer donViId) {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("NVIEN-DON-VI", donViId.toString(), null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Danh sách nhân viên thuộc đơn vị")
    @GetMapping("/don-vi-nhan-vien")
    public ResponseEntity<ApiResult> danhSachDonvi() {
        Integer donViId=AppRequestContext.getCurrent().getDonViID();
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("LIST-DON-VI", donViId.toString(), null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy thông tin đơn vị cha")
    @GetMapping("/don-vi-cha")
    public ResponseEntity<ApiResult> getDonviCha(@Valid @RequestParam("donViId") Long donViId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("DON-VI-CHA", donViId.toString(), null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin đơn vị chi tiết bởi SPDV id")
    @GetMapping("/chi-tiet-donvi-by-spdv-id")
    public ResponseEntity<ApiResult> getChiTietDonViBySpdvId(@Valid @RequestParam("spdvId") Long spdvId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("TT-DON-VI_BY_SPDV_ID", spdvId.toString(), null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh mục Công nghệ ")
    @GetMapping("/ha-tang-cong-nghe")
    public ResponseEntity<ApiResult> listCongNghe() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("HATANG_CONGNGHE", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @GetMapping("/ha-tang-phan-loai")
    public ResponseEntity<ApiResult> listPhanLoai() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("HATANG_PHANLOAI", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @GetMapping("/trang-thai-may-ao")
    public ResponseEntity<ApiResult> listTrangThaiMayAo() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("TRANGTHAI-MAYAO", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @GetMapping("/vai-tro")
    public ResponseEntity<ApiResult> listVaiTro() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("VAI-TRO", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @GetMapping("/he-dieu-hanh")
    public ResponseEntity<ApiResult> listHeDieuHanh() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("HE-DIEU-HANH", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping("/ha-tang-phan-loai-tinh-toan")
    public ResponseEntity<ApiResult> listPhanLoaiTinhToan() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("HATANG_PHANLOAITT", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @GetMapping("/ha-tang-tai-nguyen")
    public ResponseEntity<ApiResult> listTaiNguyen() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("TRANGTHAITAINGUYEN", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Danh sách VRF thuộc IDC")
    @GetMapping("/vrf-by-idc")
    public ResponseEntity<ApiResult> danhSachVRF(@Valid @RequestParam("idcId") Long idcId) {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("VRF-IDC", idcId.toString(), null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Danh sách Hạ tầng ảo hóa")
    @GetMapping("/ha-tang-ao-hoa")
    public ResponseEntity<ApiResult> danhSachHaTangAoHoa() {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("HATANG-AOHOA", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách SPDV")
    @GetMapping("/spdv")
    public ResponseEntity<ApiResult> danhSachSPDV() {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("SPDV", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách SPDV by Cụm dv id")
    @GetMapping("/spdv-by-cumdichvu")
    public ResponseEntity<ApiResult> danhSachSPDVByCumdichvuId(@Valid @RequestParam("cumDvId") Long cumDvId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("SPDV_BY_CUMDICHVU_ID", cumDvId.toString(), null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách Cụm dịch vụ")
    @GetMapping("/cumdichvu")
    public ResponseEntity<ApiResult> danhSachCumDichVu() {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("CUM-DICH-VU", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách Hạ tầng idg")
    @GetMapping("/ha-tang-idg")
    public ResponseEntity<ApiResult> danhSachHaTangIDG() {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("HATANG-IDG", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách vệ tinh theo Đơn vị ID")
    @RequestMapping(value = "/layDsVeTinh", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsVeTinh(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(danhMucService.layDsVeTinh(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }
	
    @Operation(summary = "Lấy danh sách vệ tinh theo Đơn vị ID trong TOKEN")
    @RequestMapping(value = "/layDsVeTinhTrongToken", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsVeTinhTrongToken() {
    	System.out.println("danhmuc/layDsVeTinhTrongToken");
    	System.out.println("donvi: " + AppRequestContext.getCurrent().getDonViID());
    	
        ApiResult rs = new ApiResult();
        try {
			rs.setData(danhMucService.layDsVeTinh(AppRequestContext.getCurrent().getDonViID()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách vệ tinh theo tổ quản lý")
    @RequestMapping(value = "/layDsVeTinhTheoToQl", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsVeTinhTheoToQl(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsVeTinhTheoToQl(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Trung tâm Viễn thông theo Phân Vùng ID")
    @RequestMapping(value = "/layDsTTVT", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsTTVT(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(danhMucService.layDsTTVT(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }
	
    @Operation(summary = "Lấy danh sách Trung tâm Viễn thông theo Phân Vùng ID trong TOKEN")
    @RequestMapping(value = "/layDsTTVTTrongToken", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsTTVTTrongToken() {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(danhMucService.layDsTTVT(AppRequestContext.getCurrent().getPhanVungID()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }
	
    @Operation(summary = "Lấy danh sách Trung tâm Viễn thông theo Nhân viên ID")
    @RequestMapping(value = "/layDsTTVTTheoNV", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsTTVTTheoNV(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(new ResultOutput(danhMucService.layDsTTVTTheoNV(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }
	
    @Operation(summary = "Lấy danh sách Trung tâm Viễn thông theo Nhân viên ID trong TOKEN")
    @RequestMapping(value = "/layDsTTVTTheoNVTrongToken", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsTTVTTheoNVTrongToken() {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(danhMucService.layDsTTVTTheoNV(AppRequestContext.getCurrent().getNhanVienID()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }
	
    @Operation(summary = "Lấy danh sách Tổ kỹ thuật theo Đơn vị ID")
    @RequestMapping(value = "/layDsToKT", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsToKT(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(danhMucService.layDsToKT(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }
	
    @Operation(summary = "Lấy danh sách Tổ kỹ thuật theo Đơn vị ID trong TOKEN")
    @RequestMapping(value = "/layDsToKTTrongToken", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsToKTTrongToken() {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(danhMucService.layDsToKT(AppRequestContext.getCurrent().getDonViID()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }
	
    @Operation(summary = "Lấy danh sách Tổ kỹ thuật theo Nhân viên ID")
    @RequestMapping(value = "/layDsToKTTheoNV", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsToKTTheoNV(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(new ResultOutput(danhMucService.layDsToKTTheoNV(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }
	
    @Operation(summary = "Lấy danh sách Tổ kỹ thuật theo Nhân viên ID trong TOKEN")
    @RequestMapping(value = "/layDsToKTTheoNVTrongToken", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsToKTTheoNVTrongToken() {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(danhMucService.layDsToKTTheoNV(AppRequestContext.getCurrent().getNhanVienID()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Tổ kỹ thuật theo Nhân viên ID trong TOKEN và đơn vị")
    @RequestMapping(value = "/layDsToKTTheoNVTrongTokenVaDonVi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsToKTTheoNVTrongTokenVaDonVi(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsToKTTheoNVTrongToken(AppRequestContext.getCurrent().getNhanVienID(), item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Tổ kỹ thuật theo phân vùng")
    @RequestMapping(value = "/layDsToKTTheoPhanVung", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsToKTTheoPhanVung() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsToKTTheoPhanVung());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thiết bị OLT theo vệ tinh")
    @RequestMapping(value = "/layDsOltTheoVeTinh", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsOltTheoVeTinh(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsOltTheoVeTinh(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách card olt theo id thiết bị OLT")
    @RequestMapping(value = "/layDsCardOltTheoOlt", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsCardOltTheoOlt(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsCardOltTheoOlt(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách port olt theo id card OLT")
    @RequestMapping(value = "/layDsPortOltTheoCardOlt", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsPortOltTheoCardOlt(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsPortOltTheoCardOlt(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách đơn vị theo theo id và loại dịch vụ")
    @RequestMapping(value = "/layDsDonVi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsDonViByIdAndLoaiDV(@Valid @RequestBody DonViDtoInp obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsDonViByIdAndLoaiDV(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách đơn vị con theo theo id và loại dịch vụ")
    @RequestMapping(value = "/layDsDonViCon", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsDonViConByIdAndLoaiDV(@Valid @RequestBody DonViDtoInp obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsDonViConByIdAndLoaiDV(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
	/**
	 * @author LOCNV
	 * @date 25/08/2021
	 */
    @Operation(summary = "Lấy danh sách tủ rack")
    @RequestMapping(value = "/layDsTuRack", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsTuRack() {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(danhMucService.layDsTuRack());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách tủ rack theo đơn vị ID")
    @RequestMapping(value = "/layDsTuRackTheoDonVi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsTuRackTheoDonVi(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(danhMucService.layDsTuRackTheoDonVi(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách ewsd theo tủ rack ID")
    @RequestMapping(value = "/layDsEwsdTheoTuRack", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsEwsdTheoTuRack(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(danhMucService.layDsEwsdTheoTuRack(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách ewsd shelf theo ewsd ID")
    @RequestMapping(value = "/layDsShelfEwsdTheoEwsd", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsShelfEwsdTheoEwsd(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(danhMucService.layDsShelfEwsdTheoEwsd(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách đơn vị theo cây")
    @RequestMapping(value = "/layCayDonVi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layCayDonVi() {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(danhMucService.layCayDonVi());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy thông tin đơn vị theo đơn vị ID")
    @RequestMapping(value = "/layThongTinDonVi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layThongTinDonVi(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(danhMucService.layThongTinDonVi(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }
    
	/**
	 * END LOCNV
	 */

    @Operation(summary = "Lấy danh sách trạm VT")
    @RequestMapping(value = "/layDsTramVT", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsTramVT(@Valid @RequestBody DonViDtoInp obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsTramVT(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách giao diện")
                @RequestMapping(value = "/layDsGiaoDien", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsGiaoDien() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsGiaoDien());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách nhóm thiết bị")
    @RequestMapping(value = "/layDsNhomTbi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsNhomTbi() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsNhomTbi());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách loại thiết bị của nhóm thiết bị")
    @RequestMapping(value = "/layDsLoaiTbiTheoNhom", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsLoaiTbiTheoNhom(@RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsLoaiTbiTheoNhom(item));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Lấy danh sách loại SFP")
    @RequestMapping(value = "/layDsLoaiSFP", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsLoaiSFP() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsLoaiSFP());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping("/ha-tang-idc")
    public ResponseEntity<ApiResult> IDCList() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("IDC", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping("/loai-ha-tang")
    public ResponseEntity<ApiResult> loaiHaTangList() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("LOAIHATANG", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping("/ha-tang-mang-by-idc-id")
    public ResponseEntity<ApiResult> getHaTangMangByIdcId(@RequestParam(value = "id") Number id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("HATANGMANG_BY_IDC_ID", id.toString(), null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Danh mục dùng chung hạ tầng mạng")
    @PostMapping("/hatang_mang")
    public ResponseEntity<ApiResult> danhMucHaTangMang(@RequestBody DanhMucParam param) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc(param.getLoaiDanhMuc().toUpperCase(), param.getThamSo1(), param.getThamSo2()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/thietbi-tudia-by-idc-id")
    public ResponseEntity<ApiResult> getThietBiTuDiaByIdcId(@RequestParam(value = "id") Number id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("THIETBI_TUDIA_BY_IDC_ID", id.toString(), null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping("/hatang-backup")
    public ResponseEntity<ApiResult> haTangBackupList() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("HATANGBACKUP", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping("/loai-backup")
    public ResponseEntity<ApiResult> loaiBackupList() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("BACKUP_LOAI", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping("/tansuat-backup")
    public ResponseEntity<ApiResult> tanSuatBackupList() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("BACKUP_TANSUAT", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/ip-cungcapdv")
    public ResponseEntity<ApiResult> ipCungCapDichVuList() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("IP_CUNGCAPDV", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/ip-quantri")
    public ResponseEntity<ApiResult> ipQuanTriList() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("IP_QUANTRI", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/luutru-tudung")
    public ResponseEntity<ApiResult> haTangLuuTruTuDungList() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("HATANGLUUTRU_TUDUNG", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/luutru-tudia")
    public ResponseEntity<ApiResult> haTangLuuTruTuDiaList() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("HATANGLUUTRU_TUDIA", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/phieu-yeucau")
    public ResponseEntity<ApiResult> phieuYeuCauList() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("PHIEUYEUCAU", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping("/ha-tang-idc-toa-nha")
    public ResponseEntity<ApiResult> IDCBuilding(@RequestParam(value = "idc_id", required = false) String idc_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("IDC_TOA_NHA", idc_id, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping("/ha-tang-idc-mat-san")
    public ResponseEntity<ApiResult> IDCFloor(@RequestParam(value = "building_id", required = false) String building_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("IDC_MAT_SAN", building_id, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping("/ha-tang-idc-zone")
    public ResponseEntity<ApiResult> IDCZone(@RequestParam(value = "floor_id", required = false) String floor_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("IDC_ZONE", floor_id, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping("/ha-tang-idc-rack")
    public ResponseEntity<ApiResult> IDCRack(@RequestParam(value = "zone_id", required = false) String zone_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("IDC_RACK", zone_id, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping("/iops")
    public ResponseEntity<ApiResult> listIOPS() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("DICHVU_IOPS", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping("/throughtput")
    public ResponseEntity<ApiResult> listThroughtput() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.getDataDanhMuc("DICHVU_THROUGHTPUT", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
