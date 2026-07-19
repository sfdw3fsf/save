package vn.vnpt.vnptit.ecms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.DanhMucGoiDichVuThroughPutDtoIn;
import vn.vnpt.vnptit.ecms.service.DanhMucGoiDichVuThroughPutService;
import vn.vnpt.database.AppSqlException;
import javax.validation.Valid;

@RestController
@RequestMapping("throughput")
public class DanhMucGoiDichVuThroughPut {
    @Autowired
    private DanhMucGoiDichVuThroughPutService danhMucGoiDichVuThroughPutService;

    @Operation(summary = "Thêm danh mục gói dịch vụ throughput")
    @PostMapping("insert-throughput")
    public ResponseEntity<ApiResult> insertThroughput(@Valid @RequestBody DanhMucGoiDichVuThroughPutDtoIn obj) {
        ApiResult rs = new ApiResult();
        if (obj.getTen() == null || obj.getTen().isEmpty() || obj.getGiatri() == null) {
            rs.setException(new AppSqlException("Tên gói dịch vụ và giá trị không được để trống"));
            return rs.getResponseEntity();
        }
        try {
            rs.setData(danhMucGoiDichVuThroughPutService.insertThroughput(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật danh mục gói dịch vụ throughput")
    @PostMapping("update-throughput")
    public ResponseEntity<ApiResult> updateThroughput(@Valid @RequestBody DanhMucGoiDichVuThroughPutDtoIn obj) {
        ApiResult rs = new ApiResult();
        if (obj.getTen() == null || obj.getTen().isEmpty() || obj.getGiatri() == null) {
            rs.setException(new AppSqlException("Tên gói dịch vụ và giá trị không được để trống"));
            return rs.getResponseEntity();
        }
        try {
            rs.setData(danhMucGoiDichVuThroughPutService.updateThroughput(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa danh mục gói dịch vụ throughput")
    @PostMapping("delete-throughput")
    public ResponseEntity<ApiResult> deleteThroughput(@Valid @RequestBody DanhMucGoiDichVuThroughPutDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucGoiDichVuThroughPutService.deleteThroughput(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh mục gói dịch vụ throughput")
    @PostMapping("get-throughput")
    public ResponseEntity<ApiResult> getThroughput(@Valid @RequestBody DanhMucGoiDichVuThroughPutDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucGoiDichVuThroughPutService.getThroughput(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy tất cả danh mục gói dịch vụ throughput")
    @PostMapping("get-all-throughput")
    public ResponseEntity<ApiResult> getAllThroughput() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucGoiDichVuThroughPutService.getAllThroughput());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
