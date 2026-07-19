package vn.vnpt.vnptit.qlsc.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.qlsc.dto.DanhMucDoiTac.*;
import vn.vnpt.vnptit.qlsc.service.DanhMucDoiTacService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("danhmuc-doitac")
public class DanhMucDoiTacController {
    private final DanhMucDoiTacService danhMucDoiTacService;
    public DanhMucDoiTacController(DanhMucDoiTacService danhMucDoiTacService) {
        this.danhMucDoiTacService = danhMucDoiTacService;
    }

    @Operation(summary = "Danh sách đối tác")
    @RequestMapping(value = "/ds-doitac", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachDoiTac() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucDoiTacService.dsDoiTac());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy số tòa nhà của đối tác")
    @RequestMapping(value = "/so-toanha-doitac", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> SoToaNhaDoiTac(@RequestParam("doiTacId") Long doiTacId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucDoiTacService.soToaNhaDoiTac(doiTacId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm đối tác")
    @RequestMapping(value = "/them-doitac", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> ThemDoiTac(@Valid @RequestBody ThemDoiTacDto input) {
        ApiResult apiResult = new ApiResult();
        try {
            ArrayList<Map<String, Object>> rs = danhMucDoiTacService.themDoiTac(input);
            Map<String, Object> map = rs.get(0);
            apiResult.setErrorCode(map.get("CODE_ERROR").toString());
            apiResult.setMessage(map.get("CODE_MESSAGE").toString());
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Cập nhật đối tác")
    @RequestMapping(value = "/capnhat-doitac", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> CapNhatDoiTac(@Valid @RequestBody CapNhatDoiTacDto input) {
        ApiResult apiResult = new ApiResult();
        try {
            ArrayList<Map<String, Object>> rs = danhMucDoiTacService.capNhatDoiTac(input);
            Map<String, Object> map = rs.get(0);
            apiResult.setErrorCode(map.get("CODE_ERROR").toString());
            apiResult.setMessage(map.get("CODE_MESSAGE").toString());
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Xóa đối tác")
    @RequestMapping(value = "/xoa-doitac/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> XoaChuDauTu(@PathVariable(name = "id", required = true) Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            ArrayList<Map<String, Object>> rs = danhMucDoiTacService.xoaDoiTac(id);
            Map<String, Object> map = rs.get(0);
            apiResult.setErrorCode(map.get("CODE_ERROR").toString());
            apiResult.setMessage(map.get("CODE_MESSAGE").toString());
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }
}
