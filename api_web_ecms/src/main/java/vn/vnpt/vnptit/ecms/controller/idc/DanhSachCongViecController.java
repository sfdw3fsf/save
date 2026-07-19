package vn.vnpt.vnptit.ecms.controller.idc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DanhSachCongViecDto;
import vn.vnpt.vnptit.ecms.dto.idc.DanhSachCongViecV2Dto;
import vn.vnpt.vnptit.ecms.service.idc.DanhSachCongViecService;

@RestController
@RequestMapping("/idc/danhsach-congviec")
public class DanhSachCongViecController {
    @Autowired
    private DanhSachCongViecService danhSachCongViecService;

    @Operation(summary = "Lấy danh sách công việc kèm chức năng filter")
    @PostMapping()
    public ResponseEntity<ApiResult> getDanhSachCongViec(@RequestBody DanhSachCongViecDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(danhSachCongViecService.getDanhSachCongViec(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách công việc kèm chức năng filter theo hệ thống thiết bị")
    @PostMapping("/hethong-thietbi")
    public ResponseEntity<ApiResult> getDanhSachCongViecTheoHeThongThietBi(@RequestBody DanhSachCongViecDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(danhSachCongViecService.getDanhSachCongViecTheoHeThongThietBi(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách công việc kèm chức năng filter theo thi công rack")
    @PostMapping("/thicong-rack")
    public ResponseEntity<ApiResult> getDanhSachCongViecTheoThiCongRack(@RequestBody DanhSachCongViecDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(danhSachCongViecService.getDanhSachCongViecTheoThiCongRack(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Lấy chi tiết công việc")
    @PostMapping("/chitiet/{id}")
    public ResponseEntity<ApiResult> getChiTietCongViec(@PathVariable("id") Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(danhSachCongViecService.getChiTietCongViec(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách công việc v2 với filter mở rộng (IDC, phân loại, ngày yêu cầu hoàn thành, tìm kiếm khách hàng và công việc)")
    @PostMapping("/v2")
    public ResponseEntity<ApiResult> getDanhSachCongViecV2(@RequestBody DanhSachCongViecV2Dto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(danhSachCongViecService.getDanhSachCongViecV2(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }
}
