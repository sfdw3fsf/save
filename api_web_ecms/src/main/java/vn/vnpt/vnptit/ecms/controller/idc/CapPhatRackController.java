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

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/idc/thi-cong/cap-phat-rack")
public class CapPhatRackController {

    private final CapPhatRackService capPhatRackService;

    public CapPhatRackController(CapPhatRackService capPhatRackService) {
        this.capPhatRackService = capPhatRackService;
    }

    @Operation(summary = "Thi cong cap phat Rack")
    @PostMapping("/capphat-rack")
    public ResponseEntity<ApiResult> thiCongCapPhatRack(@Valid @RequestBody CapPhatRackRequestDTO request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(capPhatRackService.thiCongCapPhatRack(request));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xoa thi cong cap phat Rack")
    @PostMapping("/xoa-capphat-rack")
    public ResponseEntity<ApiResult> xoaThiCongCapPhatRack(@Valid @RequestBody CapPhatRackRequestDTO request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(capPhatRackService.xoaThiCongCapPhatRack(request));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lay thong tin Rack da cap phat trong phieu thi cong")
    @PostMapping("/ds-racks-da-capphat/{id}")
    public ResponseEntity<ApiResult> dsRacksDaCapPhat(@PathVariable(name = "id") Long idPhieuThiCong) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(capPhatRackService.dsRacksDaCapPhat(idPhieuThiCong));
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
            rs.setData(capPhatRackService.traPhieu(idPhieuThiCong));
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
            rs.setData(capPhatRackService.hoanThanh(idPhieuThiCong));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "DS ket qua thi cong cap phat Rack")
    @PostMapping("/ds-ketqua-capphat-rack")
    public ResponseEntity<ApiResult> dsKetQuaThiCongCapPhatRack(@RequestBody KetQuaThiCongRackDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(capPhatRackService.dsKetQuaThiCongCapPhatRack(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
