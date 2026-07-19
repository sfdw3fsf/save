package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.service.idc.CapPhatIPIDCService;
import vn.vnpt.vnptit.ecms.dto.idc.*;

@RestController
@RequestMapping("/idc/cap-phat-thu-hoi-ip")
public class CapPhatThuHoiIPController {

    @Autowired
    CapPhatIPIDCService capPhatIPIDCService;

    @Operation(summary = "Lấy chi tiết công việc cấp phát & thu hồi IP")
    @RequestMapping(value = "/chi-tiet-cong-viec", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getChiTietCongViecCapPhatThuHoiIP(@RequestParam Long phieutcId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(capPhatIPIDCService.getChiTietCongViecCapPhatThuHoiIP(phieutcId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách IP sẵn sàng cấp phát / thu hồi")
    @PostMapping("/danh-sach-ip-cap-phat")
    public ResponseEntity<ApiResult> getDanhSachIPCapPhat(@RequestBody DanhSachIPCapPhatDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(capPhatIPIDCService.getDanhSachIPCapPhat(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách dải IP sẵn sàng cấp phát / thu hồi")
    @PostMapping("/danh-sach-dai-ip-cap-phat")
    public ResponseEntity<ApiResult> getDanhSachDaiIPCapPhat(@RequestBody DanhSachDaiIPCapPhatDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(capPhatIPIDCService.getDanhSachDaiIPCapPhat(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách IP_STORE đã thi công")
    @PostMapping("/danh-sach-ip-store-thi-cong")
    public ResponseEntity<ApiResult> getDanhSachIPStoreThiCong(@RequestBody DanhSachIPStoreThiCongDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(capPhatIPIDCService.getDanhSachIPStoreThiCong(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy chi tiết IP theo THICONGIPSTORE_ID")
    @PostMapping("/chi-tiet-ip-theo-thicongipstore")
    public ResponseEntity<ApiResult> getChiTietIPTheoThicongipstore(@RequestBody ChiTietIPTheoThicongipstoreDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(capPhatIPIDCService.getChiTietIPTheoThicongipstore(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thực hiện cấp phát IP")
    @PostMapping("/cap-phat-ip")
    public ResponseEntity<ApiResult> capPhatIP(@RequestBody CapPhatIPDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(capPhatIPIDCService.capPhatIP(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cấp IP cho thiết bị và port")
    @PostMapping("/cap-phat-ip-thiet-bi-port")
    public ResponseEntity<ApiResult> capPhatIPThietBiPort(@RequestBody CapPhatIPThietBiPortDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(capPhatIPIDCService.capPhatIPThietBiPort(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa cấp phát IPSTORE hoặc IP")
    @PostMapping("/xoa-cap-phat-ipstore-ip")
    public ResponseEntity<ApiResult> deleteCapPhatIPStoreIP(@RequestBody DeleteCapPhatIPStoreIPDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(capPhatIPIDCService.deleteCapPhatIPStoreIP(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa thu hồi IPSTORE hoặc IP")
    @PostMapping("/xoa-thu-hoi-ipstore-ip")
    public ResponseEntity<ApiResult> deleteThuHoiIPStoreIP(@RequestBody DeleteThuHoiIPStoreIPDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(capPhatIPIDCService.deleteThuHoiIPStoreIP(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thiết bị cấp phát")
    @PostMapping("/danh-sach-thiet-bi-cap-phat")
    public ResponseEntity<ApiResult> getDanhSachThietBiCapPhat(@RequestBody DanhSachThietBiCapPhatDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(capPhatIPIDCService.getDanhSachThietBiCapPhat(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách port theo thiết bị ID")
    @PostMapping("/danh-sach-port-theo-thiet-bi")
    public ResponseEntity<ApiResult> getDanhSachPortTheoThietBi(@RequestBody DanhSachPortTheoThietBiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(capPhatIPIDCService.getDanhSachPortTheoThietBi(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy kết quả cấp phát & thu hồi IP")
    @PostMapping("/ket-qua-cap-phat-thu-hoi-ip")
    public ResponseEntity<ApiResult> getKetQuaCapPhatThuHoiIP(@RequestBody KetQuaDanhSachTacNghiepDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(capPhatIPIDCService.getKetQuaCapPhatThuHoiIP(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thu hồi IP từ thiết bị và port")
    @PostMapping("/thu-hoi-ip-thiet-bi-port")
    public ResponseEntity<ApiResult> thuHoiIPThietBiPort(@RequestBody ThuHoiIPThietBiPortDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(capPhatIPIDCService.thuHoiIPThietBiPort(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật thông tin phiếu thi công")
    @PostMapping("/cap-nhat-thong-tin-phieu-tc")
    public ResponseEntity<ApiResult> capNhatThongTinPhieuTC(@RequestBody CapNhatThongTinPhieuTCDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(capPhatIPIDCService.capNhatThongTinPhieuTC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
