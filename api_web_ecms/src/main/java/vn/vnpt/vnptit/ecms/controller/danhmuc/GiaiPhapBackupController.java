
package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.danhmuc.DanhMucDtoIn;
import vn.vnpt.vnptit.ecms.service.danhmuc.GiaiPhapBackupService;

import javax.validation.Valid;

@RestController
@RequestMapping("danhmuc/giai-phap-backup")
public class GiaiPhapBackupController {
    private final GiaiPhapBackupService service;

    public GiaiPhapBackupController(GiaiPhapBackupService service) {
        this.service = service;
    }

    @Operation(summary = "Danh sách giải pháp  Backup ")
    @GetMapping("/danh-sach")
    public ResponseEntity<ApiResult> danhSachModule() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getData());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật Danh mục Backup giải pháp")
    @PostMapping("/cap-nhat")
    public ResponseEntity<ApiResult> capNhatGiaiPhapBackup(@Valid @RequestBody DanhMucDtoIn input) throws AppSqlException {
        return service.capNhatGiaiPhapBackup(input);
    }
    @Operation(summary = "Xóa Danh mục Backup giải pháp")
    @PostMapping("/xoa")
    public ResponseEntity<ApiResult> xoaGiaiPhapBackup(@RequestBody DanhMucDtoIn input) throws AppSqlException {
        return service.xoaGiaiPhapBackup(input);
    }

}
