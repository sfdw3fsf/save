package vn.vnpt.vnptit.ecms.controller.idc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.vnptit.ecms.dto.idc.ThanhNguonTrenRackBatchDto;
import vn.vnpt.vnptit.ecms.dto.idc.ThanhNguonTrenRackDto;
import vn.vnpt.vnptit.ecms.service.idc.ThanhNguonTrenRackService;

@RestController
@RequestMapping("/idc/thanhnguon-trenrack")
public class DanhMucThanhNguonTrenRackController {

    private final ThanhNguonTrenRackService thanhNguonTrenRackService;

    public DanhMucThanhNguonTrenRackController(ThanhNguonTrenRackService thanhNguonTrenRackService) {
        this.thanhNguonTrenRackService = thanhNguonTrenRackService;
    }

    @Operation(summary = "Lấy danh sách thanh nguồn trên rack")
    @GetMapping("")
    public ResponseEntity<ApiResult> getListThanhNguonTrenRack() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thanhNguonTrenRackService.getListThanhNguonTrenRack());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật thanh nguồn trên rack")
    @PostMapping("")
    public ResponseEntity<ApiResult> upsertThanhNguonTrenRack(@RequestBody ThanhNguonTrenRackDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thanhNguonTrenRackService.upsertThanhNguonTrenRack(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa thanh nguồn trên rack")
    @PostMapping("/delete/{thanhNguonId}")
    public ResponseEntity<ApiResult> deleteThanhNguonTrenRack(@PathVariable Long thanhNguonId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thanhNguonTrenRackService.deleteThanhNguonTrenRack(thanhNguonId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Insert nhiều thanh nguồn trên rack từ JSON")
    @PostMapping("/batch-insert")
    public ResponseEntity<ApiResult> batchInsertThanhNguonTrenRack(@RequestBody ThanhNguonTrenRackBatchDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thanhNguonTrenRackService.batchInsertThanhNguonTrenRack(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kiểm tra có cho phép chỉnh sửa loại thanh nguồn thuộc thanh nguồn đã tồn tại không")
    @GetMapping("/check-edit-loai-tn/{thanhNguonId}")
    public ResponseEntity<ApiResult> checkEditLoaiTn(@PathVariable Long thanhNguonId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thanhNguonTrenRackService.checkEditLoaiTn(thanhNguonId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách ổ cắm theo thanh nguồn ID cho Popup info")
    @GetMapping("/ocam-info/{thanhNguonId}")
    public ResponseEntity<ApiResult> getOcamInfoByThanhNguonId(@PathVariable Long thanhNguonId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thanhNguonTrenRackService.getOcamInfoByThanhNguonId(thanhNguonId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate dữ liệu trước khi thêm mới nhiều thanh nguồn trên rack")
    @PostMapping("/validate-batch")
    public ResponseEntity<ApiResult> validateBatchThanhNguonTrenRack(@RequestBody ThanhNguonTrenRackBatchDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thanhNguonTrenRackService.validateBatchThanhNguonTrenRack(dto.getDanhSachThanhNguon()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thanh nguồn trên rack theo rack ID")
    @PostMapping("/by-rack")
    public ResponseEntity<ApiResult> getListThanhNguonTrenRackByRackId(@RequestBody DataKeyInput dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thanhNguonTrenRackService.getListThanhNguonTrenRackByRackId(dto.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}