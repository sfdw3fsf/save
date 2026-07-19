package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.danhmuc.DanhMucDtoIn;
import vn.vnpt.vnptit.ecms.service.danhmuc.TaiNguyenService;

@RestController
@RequestMapping("danhmuc/tai-nguyen")
public class TaiNguyenController {
    private final TaiNguyenService taiNguyenService;

    public TaiNguyenController(TaiNguyenService taiNguyenService) {
        this.taiNguyenService = taiNguyenService;
    }

    @Operation(summary = "Danh sách trạng thái tài nguyên")
    @GetMapping("/danh-sach")
    public ResponseEntity<ApiResult> danhSachModule() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(taiNguyenService.getTrangThaiTaiNguyen());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật Danh mục trạng thái tài nguyên")
    @PostMapping("/cap-nhat")
    public ResponseEntity<ApiResult> capNhatTaiNguyen(@RequestBody DanhMucDtoIn input) throws AppSqlException {
        return taiNguyenService.capNhatTrangThaiTaiNguyen(input);
    }
    @Operation(summary = "Xóa Danh mục trạng thái tài nguyên")
    @PostMapping("/xoa")
    public ResponseEntity<ApiResult> xoaTaiNguyen(@RequestBody DanhMucDtoIn input) throws AppSqlException {
        return taiNguyenService.xoaTrangThaiTaiNguyen(input);
    }

}
