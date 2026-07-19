package vn.vnpt.vnptit.ecms.controller.idc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.vnptit.ecms.dto.idc.HeThongThietBiKhachHangDto;
import vn.vnpt.vnptit.ecms.dto.idc.PhieuThiCongHeThongKhDto;
import vn.vnpt.vnptit.ecms.dto.idc.ThiCongHeThongDto;
import vn.vnpt.vnptit.ecms.dto.idc.ThiCongThietBiDto;
import vn.vnpt.vnptit.ecms.dto.idc.ThietBiKhachHangDto;
import vn.vnpt.vnptit.ecms.dto.idc.UpsertListThietBiPortKhachHangDto;
import vn.vnpt.vnptit.ecms.dto.idc.searchRackDto;
import vn.vnpt.vnptit.ecms.service.idc.ThietBiKhachHangService;

@RestController
@RequestMapping("idc/thietbi-hethong-khachhang")
public class ThietBiKhachHangController {
    private final ThietBiKhachHangService service;

    public ThietBiKhachHangController(ThietBiKhachHangService service) {
        this.service = service;
    }

    @Operation(summary = "Danh sách hệ thống thiết bị khách hàng")
    @PostMapping("/danhsach-hethong-thietbi-khachhang")
    public ResponseEntity<ApiResult> listHeThongThietBiKhachHang(@RequestBody HeThongThietBiKhachHangDto searchDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDanhSachHeThongThietBiKhachHang(
                    searchDto.getTenHeThong(),
                    searchDto.getTrangThaiVanHanh(),
                    searchDto.getIdcId(),
                    searchDto.getMangTbiId(),
                    searchDto.getDonViQLId(),
                    searchDto.getNguoiQLId(),
                    searchDto.getHdtbId(),
                    searchDto.getTinhId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Insert/ Update hệ thống thiết bị khách hàng")
    @PostMapping("/upsert-hethong-thietbi-khachhang")
    public ResponseEntity<ApiResult> upsertHeThongThietBiKhachHang(@RequestBody HeThongThietBiKhachHangDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upserHeThongThietBiKhachHang(
                    dto.getHeThongTbiId(),
                    dto.getMangTbiId(),
                    dto.getLoaiKHId(),
                    dto.getTenHeThong(),
                    dto.getGhiChu(),
                    dto.getDonViQLId(),
                    dto.getNguoiQLId(),
                    dto.getIdcId(),
                    dto.getNgayLD(),
                    dto.getCanCuYC(),
                    dto.getTrangThaiTSId(),
                    dto.getTrangThaiVHId(),
                    dto.getSuDung(),
                    dto.getMoTa(),
                    dto.getHdtbId(),
                    dto.getTinhId(),
                    dto.getNgayThaoGo()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa hệ thống thiết bị khách hàng")
    @PostMapping("/xoa-hethong-thietbi-khachhang")
    public ResponseEntity<ApiResult> deleteHeThongThietBiKhachHang(@RequestBody HeThongThietBiKhachHangDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.deleteHeThongThietBiKhachHang(dto.getHeThongTbiId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách thiết bị khách hàng")
    @PostMapping("/danhsach-thietbi-khachhang")
    public ResponseEntity<ApiResult> listThietBiKhachHang(@RequestBody ThietBiKhachHangDto searchDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getThietBiKhachHang(
                    searchDto.getThietBiKHId(),
                    searchDto.getTen(),
                    searchDto.getLoaiThietBiId(),
                    searchDto.getLoaiKhachHang(),
                    searchDto.getSerialNumber(),
                    searchDto.getToaNhaId(),
                    searchDto.getTrangThaiVanHanhId(),
                    searchDto.getHdtbId(),
                    searchDto.getTinhId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Insert/ Update thiết bị khách hàng")
    @PostMapping("/upsert-thietbi-khachhang")
    public ResponseEntity<ApiResult> upsertThietBiKhachHang(@RequestBody ThietBiKhachHangDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upserThietBiKhachHang(
                    dto.getThietBiKHId(),
                    dto.getHeThongTbiId(),
                    dto.getMangTbiId(),
                    dto.getLoaiThietBiId(),
                    dto.getThanhPhanThietBiIds(),
                    dto.getTen(),
                    dto.getModel(),
                    dto.getCauHinh(),
                    dto.getSerialNumber(),
                    dto.getPartNumber(),
                    dto.getCsDinhDanh(),
                    dto.getSoPortConsole(),
                    dto.getSoPortInterface(),
                    dto.getHangSxId(),
                    dto.getNoiSanXuatId(),
                    dto.getNamSx(),
                    dto.getIconPath(),
                    dto.getLoaiKhachHang(),
                    dto.getDonViQLId(),
                    dto.getNguoiQLId(),
                    dto.getPhamViIDCId(),
                    dto.getPhongId(),
                    dto.getIdcId(),
                    dto.getIdcRackId(),
                    dto.getUpUnitId(),
                    dto.getDownUnitId(),
                    dto.getNgayLapDat(),
                    dto.getCanCuLD(),
                    dto.getTrangThaiSD(),
                    dto.getTrangThaiTsId(),
                    dto.getMaTs(),
                    dto.getHieuLuc(),
                    dto.getTsNoiBo(),
                    dto.getMoTa(),
                    dto.getNgayCn(),
                    dto.getHdtbId(),
                    dto.getTinhId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa thiết bị khách hàng")
    @PostMapping("/xoa-thietbi-khachhang")
    public ResponseEntity<ApiResult> deleteThietBiKhachHang(@RequestBody ThietBiKhachHangDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.deleteThietBiKhachHang(dto.getThietBiKHId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();

    }

    @Operation(summary = "Lịch sử thay đổi thiết bị khách hàng")
    @PostMapping("/lichsu-thaydoi-thietbi-khachhang")
    public ResponseEntity<ApiResult> getLichSuThayDoiThietBiKhachHang(@RequestBody ThietBiKhachHangDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getLichSuThietBiKhachHang(dto.getThietBiKHId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách port thiêt bị khách hàng")
    @PostMapping("/danhsach-port-thietbi-khachhang")
    public ResponseEntity<ApiResult> getDanhSachPortThietBiKhachHang(@RequestBody ThietBiKhachHangDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getPortThietBiKhachHang(dto.getThietBiKHId(), dto.getLoaiCongId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xoá port thiết bị khách hàng")
    @PostMapping("/xoa-port-thietbi-khachhang")
    public ResponseEntity<ApiResult> deletePortThietBiKhachHang(@RequestBody ThietBiKhachHangDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.deletePortThietBiKhachHang(dto.getPortId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Upsert danh sách port thiết bị khách hàng (JSON)")
    @PostMapping("/upsert-list-port-thietbi-khachhang")
    public ResponseEntity<ApiResult> upsertListPortThietBiKhachHang(
            @RequestBody UpsertListThietBiPortKhachHangDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertListThietBiPortKhachHang(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tìm kiếm rack IDC")
    @PostMapping("/timkiem-rack-idc")
    public ResponseEntity<ApiResult> searchRackIdc(@RequestBody searchRackDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.searchRackIdc(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thiết bị theo hệ thống thiết bị khách hàng")
    @PostMapping("/danhsach-thietbi-theo-hethong-khachhang")
    public ResponseEntity<ApiResult> getDevicesBySystemCustomer(@RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDevicesBySystemCustomer(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm thi công hệ thống thiết bị khách hàng")
    @PostMapping("/them-thi-cong-hethong-khachhang")
    public ResponseEntity<ApiResult> themThiCongHeThongKhachHang(@RequestBody ThiCongHeThongDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.themThiCongHeThongKhachHang(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm thi công thiết bị khách hàng")
    @PostMapping("/them-thi-cong-thietbi-khachhang")
    public ResponseEntity<ApiResult> themThiCongThietBiKhachHang(@RequestBody ThiCongThietBiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.themThiCongThietBiKhachHang(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thi công hệ thống")
    @PostMapping("/danhsach-thi-cong-hethong-khachhang")
    public ResponseEntity<ApiResult> getDanhSachThiCongHeThongKhachHang(@RequestBody ThiCongHeThongDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDanhSachThiCongHeThong(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thi công thiết bị")
    @PostMapping("/danhsach-thi-cong-thietbi-khachhang")
    public ResponseEntity<ApiResult> getDanhSachThiCongThietBiKhachHang(@RequestBody ThiCongThietBiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDanhSachThiCongThietBi(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Chuyển phiếu thi công")
    @PostMapping("/chuyen-phieu-thi-cong")
    public ResponseEntity<ApiResult> chuyenPhieuThiCong(@RequestBody PhieuThiCongHeThongKhDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.chuyenPhieuThiCong(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Trả phiếu thi công")
    @PostMapping("/tra-phieu-thi-cong")
    public ResponseEntity<ApiResult> traPhieuThiCong(@RequestBody PhieuThiCongHeThongKhDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.traPhieuThiCong(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hoàn thành phiếu thi công")
    @PostMapping("/hoan-thanh-phieu-thi-cong")
    public ResponseEntity<ApiResult> hoanThanhPhieuThiCong(@RequestBody PhieuThiCongHeThongKhDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.hoanThanhPhieuThiCong(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kiểm tra thiết bị đã lắp đặt")
    @PostMapping("/kiem-tra-thietbi-da-lap-dat")
    public ResponseEntity<ApiResult> kiemTraThietBiDaLapDat(@RequestBody ThietBiKhachHangDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.kiemTraThietBiDaLapDat(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kiểm tra U khả dụng")
    @PostMapping("/kiem-tra-u-kha-dung")
    public ResponseEntity<ApiResult> kiemTraUKhaDung(@RequestBody PhieuThiCongHeThongKhDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.kiemTraUnitKhaDung(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Gỡ thiết bị khách hàng")
    @PostMapping("/go-thietbi-khachhang")
    public ResponseEntity<ApiResult> goThietBiKhachHang(@RequestBody PhieuThiCongHeThongKhDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.goThietBiKhachHang(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thay đổi vị trí lắp đặt")
    @PostMapping("/thay-doi-vi-tri-lap-dat")
    public ResponseEntity<ApiResult> thayDoiViTriLapDat(@RequestBody PhieuThiCongHeThongKhDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.thayDoiUnitThietBiKhachHang(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lắp đặt thiết bị khách hàng")
    @PostMapping("/lap-dat-thietbi-khachhang")
    public ResponseEntity<ApiResult> lapDatThietBiKhachHang(@RequestBody PhieuThiCongHeThongKhDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.lapDatThietBiKhachHang(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách phiếu thi công hệ thống")
    @PostMapping("/danhsach-phieu-thi-cong-hethong")
    public ResponseEntity<ApiResult> getDanhSachPhieuThiCongHeThong() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDanhSachPhieuThiCongHeThong());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy chi tiết thi cong hệ thống")
    @PostMapping("/chitiet-thi-cong-hethong")
    public ResponseEntity<ApiResult> getChiTietThiCongHeThong(@RequestBody PhieuThiCongHeThongKhDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getThiCongById(dto.getPhieuThiCongId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }

        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy ds thiết bị khách hàng")
    @PostMapping("/khachhang/{loaitb_id}")
    public ResponseEntity<ApiResult> dsThietbiKhachhang(
            @PathVariable("loaitb_id") int loaiTbId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDanhSachThietBiKhachHang(loaiTbId));
        } catch (Exception e) {
            if (e instanceof AppSqlException) {
                rs.setException((AppSqlException) e);
            } else {
                rs.setException(new AppSqlException(e));
            }
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy chi tiết hợp đồng")
    @PostMapping("/hopdong/{hdtb_id}")
    public ResponseEntity<ApiResult> hopdongThueBaoDetail(
            @PathVariable("hdtb_id") int hdtbId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getHdtbDetail(hdtbId));
        } catch (Exception e) {
            if (e instanceof AppSqlException) {
                rs.setException((AppSqlException) e);
            } else {
                rs.setException(new AppSqlException(e));
            }
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy ds nghiệp vụ IDC")
    @PostMapping("/nghiep-vu-idc/{thietbi_id}")
    public ResponseEntity<ApiResult> dsNghiepvuIDC(
            @PathVariable("thietbi_id") int thietBiId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDanhSachNghiepvuIDC(thietBiId));
        } catch (Exception e) {
            if (e instanceof AppSqlException) {
                rs.setException((AppSqlException) e);
            } else {
                rs.setException(new AppSqlException(e));
            }
        }
        return rs.getResponseEntity();
    }
}