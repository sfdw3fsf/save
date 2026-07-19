package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.CumSPDVDto;
import vn.vnpt.vnptit.ecms.dto.hatang.CumSpdvChiTietDto;
import vn.vnpt.vnptit.ecms.dto.hatang.CumSpdvDanhSachThanhPhanDto;
import vn.vnpt.vnptit.ecms.dto.hatang.CumSpdvLuuDto;
import vn.vnpt.vnptit.ecms.dto.hatang.CumSpdvLuuThanhPhanDto;
import vn.vnpt.vnptit.ecms.dto.hatang.CumSpdvTheoDoiTuongDto;
import vn.vnpt.vnptit.ecms.dto.hatang.TimKiemThanhPhanDto;
import vn.vnpt.vnptit.ecms.service.hatang.CumSPDVService;

@RestController
@RequestMapping("/hatang/cum-spdv")
public class CumSPDVController {

    @Autowired
    private CumSPDVService cumSPDVService;

    @Operation(summary = "Lấy danh sách cụm sản phẩm dịch vụ")
    @PostMapping("/danh-sach")
    public ResponseEntity<ApiResult> getDsCumSPDV(@RequestBody CumSPDVDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cumSPDVService.getDsCumSPDV(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách cụm SPDV theo đối tượng")
    @PostMapping("/danh-sach-theo-doi-tuong")
    public ResponseEntity<ApiResult> getDsCumSPDVTheoDoiTuong(@RequestBody CumSpdvTheoDoiTuongDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cumSPDVService.getDsCumSPDVTheoDoiTuong(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa cụm sản phẩm dịch vụ")
    @PostMapping("/xoa")
    public ResponseEntity<ApiResult> xoaCumSPDV(@RequestBody CumSPDVDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cumSPDVService.xoaCumSPDV(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách sản phẩm dịch vụ")
    @PostMapping("/danh-sach-spdv")
    public ResponseEntity<ApiResult> getDsSPDV(@RequestBody CumSPDVDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cumSPDVService.getDsSPDV(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tìm kiếm thành phần theo type")
    @PostMapping("/tim-kiem-thanh-phan")
    public ResponseEntity<ApiResult> timKiemThanhPhan(@RequestBody TimKiemThanhPhanDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cumSPDVService.timKiemThanhPhan(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Chi tiết cụm SPDV")
    @PostMapping("/chi-tiet")
    public ResponseEntity<ApiResult> chiTiet(@RequestBody CumSpdvChiTietDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cumSPDVService.chiTietCumSpdv(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lưu (upsert) thông tin cụm SPDV")
    @PostMapping("/luu")
    public ResponseEntity<ApiResult> luu(@RequestBody CumSpdvLuuDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cumSPDVService.luuCumSpdv(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách thành phần của cụm SPDV")
    @PostMapping("/danh-sach-thanh-phan")
    public ResponseEntity<ApiResult> danhSachThanhPhan(@RequestBody CumSpdvDanhSachThanhPhanDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cumSPDVService.danhSachThanhPhanCumSpdv(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lưu danh sách thành phần của cụm SPDV")
    @PostMapping("/luu-thanh-phan")
    public ResponseEntity<ApiResult> luuThanhPhan(@RequestBody CumSpdvLuuThanhPhanDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cumSPDVService.luuThanhPhanCumSpdv(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
