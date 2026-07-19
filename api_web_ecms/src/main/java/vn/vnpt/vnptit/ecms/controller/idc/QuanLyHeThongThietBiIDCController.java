package vn.vnpt.vnptit.ecms.controller.idc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.HeThongThietBiDto;
import vn.vnpt.vnptit.ecms.dto.idc.DeleteMultiDto;
import vn.vnpt.vnptit.ecms.service.idc.QuanLyHeThongThietBiIDCService;

@RestController
@RequestMapping("/idc/quanly-hethong-thietbi")
public class QuanLyHeThongThietBiIDCController {
    
    @Autowired
    private QuanLyHeThongThietBiIDCService quanLyHeThongThietBiIDCService;

    @Operation(summary = "Lấy danh sách hệ thống thiết bị IDC")
    @GetMapping("")
    public ResponseEntity<ApiResult> getDanhSachHeThongThietBi() {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(quanLyHeThongThietBiIDCService.getDanhSachHeThongThietBi());
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Tìm kiếm hệ thống thiết bị IDC theo điều kiện")
    @PostMapping("/search")
    public ResponseEntity<ApiResult> searchHeThongThietBi(@RequestBody HeThongThietBiDto searchDto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(quanLyHeThongThietBiIDCService.searchHeThongThietBi(searchDto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật hệ thống thiết bị IDC")
    @PostMapping("")
    public ResponseEntity<ApiResult> upsertHeThongThietBi(@RequestBody HeThongThietBiDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(quanLyHeThongThietBiIDCService.upsertHeThongThietBi(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Xóa nhiều hệ thống thiết bị IDC")
    @PostMapping("/delete-multi")
    public ResponseEntity<ApiResult> deleteMultiHeThongThietBi(@RequestBody DeleteMultiDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(quanLyHeThongThietBiIDCService.deleteMultiHeThongThietBi(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }
}
