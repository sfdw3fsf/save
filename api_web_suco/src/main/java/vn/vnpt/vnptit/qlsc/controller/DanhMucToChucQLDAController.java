package vn.vnpt.vnptit.qlsc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.qlsc.service.DanhMucToChucQLDAService;
import vn.vnpt.vnptit.qlsc.dto.DanhMucToChucQLDA.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("danhmuc-tochuc-qlda")
public class DanhMucToChucQLDAController {
    private final DanhMucToChucQLDAService danhMucToChucQLDAService;
    public DanhMucToChucQLDAController(DanhMucToChucQLDAService danhMucToChucQLDAService) {
        this.danhMucToChucQLDAService = danhMucToChucQLDAService;
    }

    @Operation(summary = "Kiểm tra tham số mặc định")
    @RequestMapping(value = "/kiemtra-thamso-md", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> KiemTraThamSoMD(@RequestParam("maTS") String maTS) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucToChucQLDAService.kiemTraThamSoMD(maTS));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách ngân hàng")
    @RequestMapping(value = "/ds-nganhang", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachNganHang() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucToChucQLDAService.dsNganHang());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách loại chủ đầu tư")
    @RequestMapping(value = "/ds-loai-chu-dautu", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachLoaiChuDauTu() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucToChucQLDAService.dsLoaiChuDauTu());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách chủ đầu tư")
    @RequestMapping(value = "/ds-chu-dautu", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachChuDauTu() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucToChucQLDAService.dsChuDauTu());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy số dự án của chủ đầu tư")
    @RequestMapping(value = "/so-duan-chu-dautu", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> SoDuAnChuDauTu(@RequestParam("chuDtuId") Long chuDtuId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucToChucQLDAService.soDuAnChuDauTu(chuDtuId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật địa chỉ")
    @RequestMapping(value = "/capnhat-diachi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> CapNhatDiaChi(@Valid @RequestBody CapNhatDiaChiDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucToChucQLDAService.capNhatDiaChi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm chủ đầu tư")
    @RequestMapping(value = "/them-chu-dautu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> ThemChuDauTu(@Valid @RequestBody ThemChuDauTuDto input) {
        ApiResult apiResult = new ApiResult();
        try {
            ArrayList<Map<String, Object>> rs = danhMucToChucQLDAService.themChuDauTu(input);
            Map<String, Object> map = rs.get(0);
            apiResult.setErrorCode(map.get("CODE_ERROR").toString());
            apiResult.setMessage(map.get("CODE_MESSAGE").toString());
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Cập nhật chủ đầu tư")
    @RequestMapping(value = "/capnhat-chu-dautu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> ThemChuDauTu(@Valid @RequestBody CapNhatChuDauTuDto input) {
        ApiResult apiResult = new ApiResult();
        try {
            ArrayList<Map<String, Object>> rs = danhMucToChucQLDAService.capNhatChuDauTu(input);
            Map<String, Object> map = rs.get(0);
            apiResult.setErrorCode(map.get("CODE_ERROR").toString());
            apiResult.setMessage(map.get("CODE_MESSAGE").toString());
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Xóa chủ đầu tư")
    @RequestMapping(value = "/xoa-chu-dautu/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> XoaChuDauTu(@PathVariable(name = "id", required = true) Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            ArrayList<Map<String, Object>> rs = danhMucToChucQLDAService.xoaChuDauTu(id);
            Map<String, Object> map = rs.get(0);
            apiResult.setErrorCode(map.get("CODE_ERROR").toString());
            apiResult.setMessage(map.get("CODE_MESSAGE").toString());
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }
}
