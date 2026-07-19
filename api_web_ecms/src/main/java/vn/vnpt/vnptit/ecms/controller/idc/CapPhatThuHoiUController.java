package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.*;
import vn.vnpt.vnptit.ecms.service.idc.CapPhatThuHoiUService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/idc/thi-cong/cap-phat-thu-hoi-u")
public class CapPhatThuHoiUController {
    private final CapPhatThuHoiUService service;

    public CapPhatThuHoiUController(CapPhatThuHoiUService service) {
        this.service = service;
    }

    @Operation(summary = "DS Rack san sang cap phat U")
    @PostMapping("/ds-rack-sansang-capphat-u/{id}")
    public ResponseEntity<ApiResult> dsRackSanSangCapPhatU(@PathVariable(name = "id") Long idPhieuThiCong) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsRackSanSangCapPhatU(idPhieuThiCong));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "DS Rack san sang Thu hoi U")
    @PostMapping("/ds-rack-sansang-thuhoi-u/{id}")
    public ResponseEntity<ApiResult> dsRackSanSangThuHoiU(@PathVariable(name = "id") Long idPhieuThiCong) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsRackSanSangThuHoiU(idPhieuThiCong));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "DS Rack trong phieu cap phat thu hoi U")
    @PostMapping("/ds-rack-trongphieu-capphat-thuhoi-u/{id}")
    public ResponseEntity<ApiResult> dsRackTrongPhieuCapPhatThuHoiU(@PathVariable(name = "id") Long idPhieuThiCong) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsRackTrongPhieuCapPhatThuHoiU(idPhieuThiCong));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "DS U san sang cap phat")
    @PostMapping("/ds-u-sansang-capphat")
    public ResponseEntity<ApiResult> dsSanSangCapPhat(  @RequestParam(name = "idPhieuThiCong") Long idPhieuThiCong,
                                                        @RequestParam(name = "rackId") Long rackId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsUSanSangCapPhat(idPhieuThiCong, rackId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cap phat U")
    @PostMapping("/capphat-u")
    public ResponseEntity<ApiResult> capPhat(@Valid @RequestBody CapPhatURequestV2DTO request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.capPhat(request));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xoa cap phat U")
    @PostMapping("/xoa-capphat-u")
    public ResponseEntity<ApiResult> xoaCapPhat(@Valid @RequestBody XoaCapPhatThuHoiURequestDTO request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.xoaCapPhat(request));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "DS U trong phieu thi cong da cap phat")
    @PostMapping("/ds-u-trong-phieutc-capphat/{id}")
    public ResponseEntity<ApiResult> dsUTrongPhieuCapPhat(@PathVariable(name = "id") Long idPhieuThiCong) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsUTrongPhieuCapPhat(idPhieuThiCong));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tra phieu")
    @PostMapping("/tra-phieu/{id}")
    public ResponseEntity<ApiResult> traPhieu(@PathVariable(name = "id") Long idPhieuThiCong,
                                              @RequestBody @Valid TraPhieuCapPhatThuHoiRequestDTO request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.traPhieu(idPhieuThiCong, request));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hoan thanh cap phat U")
    @PostMapping("/hoan-thanh-capphat/{id}")
    public ResponseEntity<ApiResult> hoanThanhCapPhat(@PathVariable(name = "id") Long idPhieuThiCong) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.hoanThanhCapPhat(idPhieuThiCong));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "DS ket qua thi cong cap phat Rack")
    @PostMapping("/ds-ketqua-u")
    public ResponseEntity<ApiResult> dsKetQua(@RequestBody KetQuaThiCongUDTO request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsKetQua(request));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "DS U san sang thu hoi")
    @PostMapping("/ds-u-sansang-thuhoi")
    public ResponseEntity<ApiResult> dsSanSangThuHoi(@RequestParam(name = "idPhieuThiCong") Long idPhieuThiCong,
                                                     @RequestParam(name = "rackId") Long rackId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsSanSangThuHoi(idPhieuThiCong, rackId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thu hoi U")
    @PostMapping("/thuhoi-u")
    public ResponseEntity<ApiResult> thuHoi(@Valid @RequestBody CapPhatURequestV2DTO request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.thuHoi(request));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xoa thu hoi U")
    @PostMapping("/xoa-thuhoi-u")
    public ResponseEntity<ApiResult> xoaThuHoi(@Valid @RequestBody XoaCapPhatThuHoiURequestDTO request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.xoaThuHoi(request));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "DS U trong phieu thi cong thu hoi")
    @PostMapping("/ds-u-trong-phieutc-thuhoi/{id}")
    public ResponseEntity<ApiResult> dsUTrongPhieuThuHoi(@PathVariable(name = "id") Long idPhieuThiCong) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsUTrongPhieuThuHoi(idPhieuThiCong));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hoan thanh thu hoi U")
    @PostMapping("/hoan-thanh-thuhoi/{id}")
    public ResponseEntity<ApiResult> hoanThanhThuHoi(@PathVariable(name = "id") Long idPhieuThiCong) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.hoanThanhThuHoi(idPhieuThiCong));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
