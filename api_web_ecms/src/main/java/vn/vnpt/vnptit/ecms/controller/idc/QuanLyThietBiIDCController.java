package vn.vnpt.vnptit.ecms.controller.idc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.vnptit.ecms.dto.idc.*;
import vn.vnpt.vnptit.ecms.service.idc.QuanLyThietBiIDCService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/idc/quanly-thietbi")
public class QuanLyThietBiIDCController {

    @Autowired
    private QuanLyThietBiIDCService quanLyThietBiIDCService;

    @Operation(summary = "Lấy danh sách thiết bị IDC")
    @GetMapping("")
    public ResponseEntity<ApiResult> getDanhSachThietBi() {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(quanLyThietBiIDCService.getDanhSachThietBi());
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật thiết bị IDC")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsertThietBi(@RequestBody ThietBiUpsertDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(quanLyThietBiIDCService.upsertThietBi(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Xóa thiết bị IDC theo ID")
    @PostMapping("/delete/{id}")
    public ResponseEntity<ApiResult> deleteThietBi(@PathVariable("id") Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(quanLyThietBiIDCService.deleteThietBi(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Lấy chi tiết thiết bị IDC theo ID")
    @GetMapping("/detail/{id}")
    public ResponseEntity<ApiResult> getChiTietThietBi(@PathVariable("id") Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(quanLyThietBiIDCService.getChiTietThietBi(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Tìm kiếm thiết bị IDC theo nhiều tiêu chí (POST body)")
    @PostMapping("/search")
    public ResponseEntity<ApiResult> searchThietBi(@RequestBody JsonNode searchJsonData) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(quanLyThietBiIDCService.searchThietBi(searchJsonData));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Danh sách port theo thiết bị ID")
    @GetMapping("/{id}/ports")
    public ResponseEntity<ApiResult> getDanhSachPortTheoThietBi(@PathVariable("id") Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(quanLyThietBiIDCService.getDanhSachPortTheoThietBi(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }
    // ========== CÁC CHỨC NĂNG HỆ THỐNG THIẾT BỊ IDC ==========

    @Operation(summary = "Lấy danh sách hệ thống thiết bị IDC")
    @PostMapping("/hethong-thietbi-idc")
    public ResponseEntity<ApiResult> getDanhSachHeThongThietBi(@RequestBody HeThongThietBiDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(quanLyThietBiIDCService.getDanhSachHeThongThietBi(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }


    // ========== CÁC CHỨC NĂNG TÁC NGHIỆP THIẾT BỊ IDC ==========

    @Operation(summary = "Thêm thi công hệ thống thiết bị idc")
    @PostMapping("/them-thi-cong-hethong-idc")
    public ResponseEntity<ApiResult> themThiCongHeThongIDC(@RequestBody ThiCongHeThongDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyThietBiIDCService.themThiCongHeThongIDC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm thi công thiết bị idc")
    @PostMapping("/them-thi-cong-thietbi-idc")
    public ResponseEntity<ApiResult> themThiCongThietBiIDC(@RequestBody ThiCongThietBiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyThietBiIDCService.themThiCongThietBiIDC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thi công hệ thống")
    @PostMapping("/danhsach-thi-cong-hethong-idc")
    public ResponseEntity<ApiResult> getDanhSachThiCongHeThongIDC(@RequestBody ThiCongHeThongDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyThietBiIDCService.layDanhSachThiCongHeThongIDC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thi công thiết bị")
    @PostMapping("/danhsach-thi-cong-thietbi-idc")
    public ResponseEntity<ApiResult> getDanhSachThiCongThietBiIDC(@RequestBody ThiCongThietBiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyThietBiIDCService.layDanhSachThiCongThietBiIDC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thiết bị theo hệ thống thiết bị idc")
    @PostMapping("/danhsach-thietbi-theo-hethong-idc")
    public ResponseEntity<ApiResult> layDanhSachThietBiTheoHeThongIDC(@RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyThietBiIDCService.layDanhSachThietBiTheoHeThongIDC(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Chuyển phiếu thi công")
    @PostMapping("/chuyen-phieu-thi-cong")
    public ResponseEntity<ApiResult> chuyenPhieuThiCongIDC(@RequestBody PhieuThiCongHeThongKhDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyThietBiIDCService.chuyenPhieuThiCongIDC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Trả phiếu thi công")
    @PostMapping("/tra-phieu-thi-cong")
    public ResponseEntity<ApiResult> traPhieuThiCongIDC(@RequestBody PhieuThiCongHeThongKhDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyThietBiIDCService.traPhieuThiCongIDC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hoàn thành phiếu thi công")
    @PostMapping("/hoan-thanh-phieu-thi-cong")
    public ResponseEntity<ApiResult> hoanThanhPhieuThiCongIDC(@RequestBody PhieuThiCongHeThongKhDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyThietBiIDCService.hoanThanhPhieuThiCongIDC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kiểm tra thiết bị đã lắp đặt")
    @PostMapping("/kiem-tra-thietbi-da-lap-dat")
    public ResponseEntity<ApiResult> kiemTraThietBiDaLapDatIDC(@RequestBody ThietBiKhachHangDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyThietBiIDCService.kiemTraThietBiDaLapDatIDC(dto));
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
            rs.setData(quanLyThietBiIDCService.kiemTraUnitKhaDungIDC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Gỡ thiết bị khách hàng")
    @PostMapping("/go-thietbi-idc")
    public ResponseEntity<ApiResult> goThietBiIDC(@RequestBody PhieuThiCongHeThongKhDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyThietBiIDCService.goThietBiIDC(dto));
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
            rs.setData(quanLyThietBiIDCService.thayDoiUnitThietBiIDC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lắp đặt thiết bị idc")
    @PostMapping("/lap-dat-thietbi-idc")
    public ResponseEntity<ApiResult> lapDatThietBiIDC(@RequestBody PhieuThiCongHeThongKhDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyThietBiIDCService.lapDatThietBiIDC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Import danh sách thiết bị từ file")
    @PostMapping("/import")
    public ResponseEntity<ApiResult> importThietBi(@RequestBody List<Map<String, Object>> thietBiList) {
        ApiResult apiResult = new ApiResult();
        try {
            if (thietBiList == null || thietBiList.isEmpty()) {
                apiResult.setException(new AppSqlException("Danh sách thiết bị không được rỗng"));
                return apiResult.getResponseEntity();
            }
            apiResult.setData(quanLyThietBiIDCService.importThietBi(thietBiList));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }
}
