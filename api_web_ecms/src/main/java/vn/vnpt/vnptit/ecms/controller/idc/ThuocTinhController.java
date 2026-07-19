package vn.vnpt.vnptit.ecms.controller.idc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.GetThuocTinhRequestDto;
import vn.vnpt.vnptit.ecms.dto.idc.ThuocTinhDTO;
import vn.vnpt.vnptit.ecms.service.idc.ThuocTinhService;

@RestController
@RequestMapping("/thuoc-tinh")
public class ThuocTinhController {
    @Autowired
    private ThuocTinhService service;

    // ====== Thuộc tính

    @PostMapping("/get")
    @Operation(summary = "Lấy thuộc tính")
    public ResponseEntity<ApiResult> getThuocTinh(
            @RequestBody GetThuocTinhRequestDto request) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(service.getThuocTinh(request.getKieuTT(), request.getDuLieuId()));
        } catch (AppSqlException e) {
            apiResult.setMessageDetail("Có lỗi xảy ra khi lấy thuộc tính");
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping("/create")
    @Operation(summary = "Thêm thuộc tính")
    public ResponseEntity<ApiResult> addThuocTinh(
            @RequestBody ThuocTinhDTO dto) {
        ApiResult apiResult = new ApiResult();
        try {
            Object response = service.insertThuocTinh(dto);
            apiResult.setData(response);
        } catch (AppSqlException e) {
            apiResult.setMessageDetail("Có lỗi xảy ra khi thêm thuộc tính");
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping("/update")
    @Operation(summary = "Cập nhật thuộc tính")
    public ResponseEntity<ApiResult> updateThuocTinh(
            @RequestParam("thuocTinhId") Long thuocTinhId,
            @RequestBody ThuocTinhDTO dto) {
        ApiResult apiResult = new ApiResult();
        try {
            Object response = service.updateThuocTinh(thuocTinhId, dto);
            apiResult.setData(response);
        } catch (AppSqlException e) {
            apiResult.setMessageDetail("Có lỗi xảy ra khi cập nhập thuộc tính");
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping("/delete")
    @Operation(summary = "Xoá thuộc tính")
    public ResponseEntity<ApiResult> deleteThuocTinh(
            @RequestParam("thuocTinhId") Long thuocTinhId) {
        ApiResult apiResult = new ApiResult();
        try {
            Object response = service.deleteThuocTinh(thuocTinhId);
            apiResult.setData(response);
        } catch (AppSqlException e) {
            apiResult.setMessageDetail("Có lỗi xảy ra khi xóa thuộc tính");
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }
}
