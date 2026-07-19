
package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.TaiNguyenNFSDtoIn;
import vn.vnpt.vnptit.ecms.service.hatang.TaiNguyenNFSService;


import javax.validation.Valid;

@RestController
@RequestMapping("danhmuc/tai-nguyen-nfs")

public class TaiNguyenNFSController {
    private final TaiNguyenNFSService service;

    public TaiNguyenNFSController(TaiNguyenNFSService service) {
        this.service = service;
    }

    @Operation(summary = "Danh sách Tài nguyên NFS")
    @GetMapping("/danh-sach")
    public ResponseEntity<ApiResult> danhSach() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.danhSachTaiNguyenNFS());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật Tài nguyên NFS")
    @PostMapping("/cap-nhat")
    public ResponseEntity<ApiResult> capNhat(@Valid @RequestBody TaiNguyenNFSDtoIn input) throws AppSqlException {
        return service.capNhatTaiNguyenNFS(input);
    }
    @Operation(summary = "Xóa Danh mục Tài nguyên NFS")
    @PostMapping("/xoa")
    public ResponseEntity<ApiResult> xoa(@RequestBody TaiNguyenNFSDtoIn input) throws AppSqlException {
        return service.xoaTaiNguyenNFS(input);
    }

    @Operation(summary = "Danh sách biến động NFS")
    @GetMapping("/bien-dong")
    public ResponseEntity<ApiResult> listBienDong(@Valid @RequestParam("id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getNFSSuDung("BIEN-DONG", id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách máy ảo NFS")
    @GetMapping("/may-ao")
    public ResponseEntity<ApiResult> listMayAo(@Valid @RequestParam("id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getNFSSuDung("MAY-AO", id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lịch sử tác động NFS")
    @GetMapping("/history")
    public ResponseEntity<ApiResult> listHistory(@Valid @RequestParam("id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getNFSSuDung("HISTORY", id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
