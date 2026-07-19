package vn.vnpt.vnptit.ecms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.vnptit.ecms.service.DanhMucTinhTrangHoatDongService;
import org.springframework.web.bind.annotation.PostMapping;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import vn.vnpt.message.ApiResult;
import vn.vnpt.database.AppSqlException;

import javax.validation.Valid;
import vn.vnpt.vnptit.ecms.dto.DanhMucTinhTrangHoatDongDtoIn;

@RestController
@RequestMapping("danhmuc-tinh-trang-hoat-dong")
public class DanhMucTinhTrangHoatDong {
    @Autowired 
    private DanhMucTinhTrangHoatDongService danhMucTinhTrangHoatDongService;

    @Operation(summary = "Thêm danh mục tinh trạng hoạt động")
    @PostMapping("insert-tinh-trang-hoat-dong")
    public ResponseEntity<ApiResult> insertTinhTrangHoatDong(@Valid @RequestBody DanhMucTinhTrangHoatDongDtoIn obj) {
        ApiResult rs = new ApiResult();
        if (obj.getTen() == null || obj.getTen().isEmpty() ) {
            rs.setException(new AppSqlException("Tên tinh trạng hoạt động không được để trống"));
            return rs.getResponseEntity();
        }
        try {
            rs.setData(danhMucTinhTrangHoatDongService.insertTinhTrangHoatDong(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật danh mục phân loại hạ tầng")
    @PostMapping("update-tinh-trang-hoat-dong")
    public ResponseEntity<ApiResult> updateTinhTrangHoatDong(@Valid @RequestBody DanhMucTinhTrangHoatDongDtoIn obj) {
        ApiResult rs = new ApiResult();
        if (obj.getTen() == null || obj.getTen().isEmpty() ) {
            rs.setException(new AppSqlException("Tên tinh trạng hoạt động không được để trống"));
            return rs.getResponseEntity();
        }
        try {
            rs.setData(danhMucTinhTrangHoatDongService.updateTinhTrangHoatDong(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa danh mục loại backup")
    @PostMapping("delete-tinh-trang-hoat-dong")
    public ResponseEntity<ApiResult> deleteTinhTrangHoatDong(@Valid @RequestBody DanhMucTinhTrangHoatDongDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucTinhTrangHoatDongService.deleteTinhTrangHoatDong(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh mục phân loại hạ tầng")
    @PostMapping("get-tinh-trang-hoat-dong")
    public ResponseEntity<ApiResult> getTinhTrangHoatDong(@Valid @RequestBody DanhMucTinhTrangHoatDongDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucTinhTrangHoatDongService.getTinhTrangHoatDong(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy tất cả danh mục phân loại hạ tầng")
    @PostMapping("get-all-tinh-trang-hoat-dong")
    public ResponseEntity<ApiResult> getAllTinhTrangHoatDong() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucTinhTrangHoatDongService.getAllTinhTrangHoatDong());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
