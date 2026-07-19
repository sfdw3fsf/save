package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.CapPhatRackRequestDTO;
import vn.vnpt.vnptit.ecms.dto.idc.KetQuaDanhSachTacNghiepDto;
import vn.vnpt.vnptit.ecms.dto.idc.KetQuaThiCongRackDto;
import vn.vnpt.vnptit.ecms.service.idc.CapPhatRackService;
import vn.vnpt.vnptit.ecms.service.idc.ThuHoiRackService;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;

@RestController
@RequestMapping(path = "/idc/thi-cong/thu-hoi-rack")
public class ThuHoiRackController {

    private final ThuHoiRackService service;

    public ThuHoiRackController(ThuHoiRackService service) {
        this.service = service;
    }

    @Operation(summary = "Thi cong thu hoi Rack")
    @PostMapping("/thuhoi-rack")
    public ResponseEntity<ApiResult> thiCongThuHoiRack(@Valid @RequestBody CapPhatRackRequestDTO request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.thiCongThuHoiRack(request));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xoa thi cong thu hoi Rack")
    @PostMapping("/xoa-thuhoi-rack")
    public ResponseEntity<ApiResult> xoaThiCongThuHoiRack(@Valid @RequestBody CapPhatRackRequestDTO request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.xoaThiCongThuHoiRack(request));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lay thong tin Rack dang thu hoi trong phieu thi cong")
    @PostMapping("/ds-racks-dang-thuhoi/{id}")
    public ResponseEntity<ApiResult> dsRacksDangThuHoi(@PathVariable(name = "id") Long idPhieuThiCong) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsRacksDaCapPhat(idPhieuThiCong));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tra phieu thi cong")
    @PostMapping("/tra-phieu/{id}")
    public ResponseEntity<ApiResult> traPhieu(@PathVariable(name = "id") Long idPhieuThiCong) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.traPhieu(idPhieuThiCong));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hoan thanh thi cong")
    @PostMapping("/hoan-thanh/{id}")
    public ResponseEntity<ApiResult> hoanThanh(@PathVariable(name = "id") Long idPhieuThiCong) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.hoanThanh(idPhieuThiCong));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "DS ket qua thi cong thu hoi Rack")
    @PostMapping("/ds-ketqua-thuhoi-rack")
    public ResponseEntity<ApiResult> dsKetQuaThiCongThuHoiRack(@RequestBody KetQuaThiCongRackDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsKetQuaThiCong(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lay thong tin Rack co the thu hoi")
    @PostMapping("/ds-sansang-thuhoi-rack")
    public ResponseEntity<ApiResult> dsSanSangThuHoiRack(@RequestParam(name = "phieutc_id") Long idPhieuThiCong) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsSanSangThuHoi(idPhieuThiCong));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
