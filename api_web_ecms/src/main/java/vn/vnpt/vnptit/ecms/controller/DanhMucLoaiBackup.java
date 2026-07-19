package vn.vnpt.vnptit.ecms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.vnptit.ecms.service.DanhMucLoaiBackupService;
import org.springframework.web.bind.annotation.PostMapping;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import vn.vnpt.message.ApiResult;
import vn.vnpt.database.AppSqlException;

import javax.validation.Valid;
import vn.vnpt.vnptit.ecms.dto.DanhMucLoaiBackupDtoIn;

@RestController
@RequestMapping("danhmuc-loai-backup")
public class DanhMucLoaiBackup {
    @Autowired 
    private DanhMucLoaiBackupService danhMucLoaiBackupService;

    @Operation(summary = "Thêm danh mục loại backup")
    @PostMapping("insert-loai-backup")
    public ResponseEntity<ApiResult> insertLoaiBackup(@Valid @RequestBody DanhMucLoaiBackupDtoIn obj) {
        ApiResult rs = new ApiResult();
        if (obj.getTen() == null || obj.getTen().isEmpty() ) {
            rs.setException(new AppSqlException("Tên loại backup không được để trống"));
            return rs.getResponseEntity();
        }
        try {
            rs.setData(danhMucLoaiBackupService.insertLoaiBackup(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật danh mục phân loại hạ tầng")
    @PostMapping("update-loai-backup")
    public ResponseEntity<ApiResult> updateLoaiBackup(@Valid @RequestBody DanhMucLoaiBackupDtoIn obj) {
        ApiResult rs = new ApiResult();
        if (obj.getTen() == null || obj.getTen().isEmpty() ) {
            rs.setException(new AppSqlException("Tên loại backup không được để trống"));
            return rs.getResponseEntity();
        }
        try {
            rs.setData(danhMucLoaiBackupService.updateLoaiBackup(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa danh mục loại backup")
    @PostMapping("delete-loai-backup")
    public ResponseEntity<ApiResult> deleteLoaiBackup(@Valid @RequestBody DanhMucLoaiBackupDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiBackupService.deleteLoaiBackup(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh mục phân loại hạ tầng")
    @PostMapping("get-loai-backup")
    public ResponseEntity<ApiResult> getLoaiBackup(@Valid @RequestBody DanhMucLoaiBackupDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiBackupService.getLoaiBackup(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy tất cả danh mục phân loại hạ tầng")
    @PostMapping("get-all-loai-backup")
    public ResponseEntity<ApiResult> getAllLoaiBackup() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiBackupService.getAllLoaiBackup());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
