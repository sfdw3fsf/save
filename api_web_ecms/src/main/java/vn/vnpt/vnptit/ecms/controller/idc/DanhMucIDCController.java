package vn.vnpt.vnptit.ecms.controller.idc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.danhmuc.DanhMucParam;
import vn.vnpt.vnptit.ecms.service.idc.DanhMucIDCService;

import java.util.List;

@RestController
@RequestMapping("idc/danh-muc")
public class DanhMucIDCController {
    private final DanhMucIDCService service;

    public DanhMucIDCController(DanhMucIDCService service) {
        this.service = service;
    }

    @Operation(summary = "Danh mục tỉnh thành")
    @GetMapping("/tinh-thanh")
    public ResponseEntity<ApiResult> listTinhThanh() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDataDanhMuc("TINH-THANH", null, null));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh mục mặt sàn")
    @GetMapping("/mat-san")
    public ResponseEntity<ApiResult> listMatSan() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDataDanhMucMatSan());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh mục hãng sản xuất")
    @GetMapping("/hang-sx")
    public ResponseEntity<ApiResult> listHangSX() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDataDanhMucHangSX());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh mục chung")
    @PostMapping("/common")
    public ResponseEntity<ApiResult> listChung(@RequestBody DanhMucParam param) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDataDanhMuc(param.getLoaiDanhMuc(), param.getThamSo1(), param.getThamSo2()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh mục chung (Batch V2)")
    @PostMapping("/v2/common")
    public ResponseEntity<ApiResult> listChungV2(@RequestBody List<DanhMucParam> params) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDataDanhMucV2(params));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
