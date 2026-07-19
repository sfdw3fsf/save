package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DichvuHtDto;
import vn.vnpt.vnptit.ecms.dto.idc.DongBoDichvuHtDto;
import vn.vnpt.vnptit.ecms.dto.idc.NangLucDvhtDto;
import vn.vnpt.vnptit.ecms.service.idc.DichvuHtService;

@RestController("idcDichvuHtController")
@RequestMapping("/dich-vu-ha-tang")
@Tag(name = "Infrastructure Service", description = "Dịch vụ hạ tầng")
public class DichvuHtController {
    private final DichvuHtService dichvuHtService;

    public DichvuHtController(DichvuHtService dichvuHtService) {
        this.dichvuHtService = dichvuHtService;
    }

    @Operation(summary = "Lấy chi tiết dịch vụ hạ tầng")
    @PostMapping("/chi-tiet")
    public ResponseEntity<ApiResult> getDetail(@RequestBody DichvuHtDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dichvuHtService.getDetailDichvuHt(dto.getDichvuhtId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật dịch vụ hạ tầng")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsert(@RequestBody DichvuHtDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dichvuHtService.upsertDichvuHt(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa dịch vụ hạ tầng")
    @PostMapping("/delete")
    public ResponseEntity<ApiResult> delete(@RequestBody DichvuHtDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dichvuHtService.deleteDichvuHt(dto.getDichvuhtId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    // ==================== Đồng bộ Dịch vụ hạ tầng ====================

    @Operation(summary = "Đồng bộ dịch vụ hạ tầng theo tham số nội bộ")
    @PostMapping("/dong-bo")
    public ResponseEntity<ApiResult> dongBoDichvuHt(@RequestBody DongBoDichvuHtDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dichvuHtService.dongBoDichvuHt(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    // ==================== Năng lực Dịch vụ hạ tầng ====================

    @Operation(summary = "Lấy chi tiết năng lực dịch vụ hạ tầng")
    @PostMapping("/nang-luc/chi-tiet")
    public ResponseEntity<ApiResult> getDetailNangLuc(@RequestBody NangLucDvhtDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dichvuHtService.getDetailNangLucDvht(dto.getDichvuhtId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật năng lực dịch vụ hạ tầng")
    @PostMapping("/nang-luc/upsert")
    public ResponseEntity<ApiResult> upsertNangLuc(@RequestBody NangLucDvhtDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dichvuHtService.upsertNangLucDvht(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
