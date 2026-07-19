package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.service.hatang.ThietBiMangService;

@Api(value = "Quản lý Thiết bị mạng - Controller", description = "Quản lý kho tài nguyên vật lý - Quản lý Thiết bị mạng")
@RestController
@RequestMapping("quanly-tb-mang")
public class ThietBiMangController {
    private final ThietBiMangService service;

    public ThietBiMangController(ThietBiMangService service) {
        this.service = service;
    }

    @Operation(summary = "Danh sách Thiết bị mạng")
    @PostMapping("/danh-sach")
    public ResponseEntity<ApiResult> danhSach() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getThietBiMang());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách Thiết bị mạng theo ID")
    @PostMapping("/danh-sach-theo-id")
    public ResponseEntity<ApiResult> danhSachTheoId(@RequestBody Map<String, Long> request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getThietBiMangById(request.get("id")));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
