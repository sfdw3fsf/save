package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.ThuocTinhDTO;
import vn.vnpt.vnptit.ecms.service.idc.SoDoMatSanService;

@RestController
@RequestMapping("/so-do-mat-san")
public class SoDoMatSanController {
    private final SoDoMatSanService service;

    public SoDoMatSanController(SoDoMatSanService service) {
        this.service = service;
    }

    @GetMapping("/mat-san/{matSanId}")
    @Operation(summary = "Lấy chi tiết mặt sàn")
    public ResponseEntity<ApiResult> getDanhSachThietBi(
            @PathVariable("matSanId") Long matSanId
    ) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(service.getMatSanDetail(matSanId));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }
    @GetMapping("/toa-nha/{toaNhaId}/list-mat-san")
    @Operation(summary = "Lay danh sach mat san theo toa nha")
    public ResponseEntity<ApiResult> getListMatSanByToaNha(
            @PathVariable("toaNhaId") Long toaNhaId
    ){
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(service.getListMatSanByToaNha(toaNhaId));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @GetMapping("/mat-san/{matSanId}/nang-luc-khong-gian")
    @Operation(summary = "Lấy năng lực không gian của mặt sàn")
    public ResponseEntity<ApiResult> getNangLucKhongGian(
            @PathVariable("matSanId") Long matSanId
    ) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(service.getNangLucKhongGian(matSanId));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @GetMapping("/mat-san/{matSanId}/nang-luc-dien")
    @Operation(summary = "Lấy năng lực điện của mặt sàn")
    public ResponseEntity<ApiResult> getNangLucDien(
            @PathVariable("matSanId") Long matSanId
    ) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(service.getNangLucDien(matSanId));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }
    @GetMapping("/mat-san/{matSanId}/list-phong")
    @Operation(summary = "Lấy danh sách phòng/zone theo mặt sàn")
    public ResponseEntity<ApiResult> getListPhongByMatSan(
            @PathVariable("matSanId") Long matSanId
    ){
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(service.getListPhongByMatSan(matSanId));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }
    @GetMapping("/phong-zone/{phongId}")
    @Operation(summary = "Lấy chi tiết mặt sàn")
    public ResponseEntity<ApiResult> getPhongZoneDetail(
            @PathVariable("phongId") Long phongId
    ) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(service.getPhongZoneDetail(phongId));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }
    @GetMapping("/mat-san/{matSanId}/thuoc-tinh")
    @Operation(summary = "Lấy thuộc tính mặt sàn")
    public ResponseEntity<ApiResult> getThuocTinhMatSan(
            @PathVariable("matSanId") Long matSanId
    ) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(service.getThuocTinhMatSan(matSanId));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }
    @PostMapping("/mat-san/{matSanId}/thuoc-tinh")
    @Operation(summary = "Thêm thuộc tính mặt sàn")
    public ResponseEntity<ApiResult> addThuocTinhMatSan(
            @PathVariable("matSanId") Long matSanId,
            @RequestBody ThuocTinhDTO dto
    ) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult = service.insertThuocTinh(matSanId, dto);
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }
    @PostMapping("/thuoc-tinh/{thuocTinhId}")
    @Operation(summary = "Cập nhật thuộc tính mặt sàn")
    public ResponseEntity<ApiResult> updateThuocTinhMatSan(
            @PathVariable("thuocTinhId") Long thuocTinhId,
            @RequestBody ThuocTinhDTO dto
    ) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult = service.updateThuocTinh(thuocTinhId, dto);
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping("/thuoc-tinh/{thuocTinhId}/delete")
    @Operation(summary = "Xoá thuộc tính mặt sàn")
    public ResponseEntity<ApiResult> deleteThuocTinhMatSan(
            @PathVariable("thuocTinhId") Long thuocTinhId
    ) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult = service.deleteThuocTinh(thuocTinhId);
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

}