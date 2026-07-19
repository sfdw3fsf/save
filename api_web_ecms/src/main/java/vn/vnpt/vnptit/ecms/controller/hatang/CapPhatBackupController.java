package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.hatang.CapPhatBackupDto;
import vn.vnpt.vnptit.ecms.service.hatang.CapPhatBackUpService;

import javax.validation.Valid;


@RestController
@RequestMapping("hatang/capphat-backup")
public class CapPhatBackupController {
    private final CapPhatBackUpService capPhatBackUpService;
    public CapPhatBackupController(CapPhatBackUpService service) {
        this.capPhatBackUpService = service;
    }


    @Operation(summary = "Liệt kê danh sách phiếu cấp phát hạ tầng backup")
    @RequestMapping(value = "/danhsach-capphat-backup", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDSPhieuCapPhatBackup() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(capPhatBackUpService.layDSPhieuCapPhatBackup());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Chỉnh sửa phếu cấp phát backup")
    @RequestMapping(value = "/capnhat-capphat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatCapPhatBackup(@Valid @RequestBody CapPhatBackupDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(capPhatBackUpService.capNhatCapPhatBackup(dto)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xác nhận cấp phát backup")
    @GetMapping("/xacnhan-capphat-backup")
    public ResponseEntity<ApiResult> xacNhanCapPhatBackup(@Valid @RequestParam("id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(capPhatBackUpService.xacNhanCapPhatBackup(id)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách người sử dụng")
    @GetMapping("/ds-nguoisudung")
    public ResponseEntity<ApiResult> dsNguoisudung(@Valid @RequestParam("phanvungId") Number phanvungId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(capPhatBackUpService.layDSNguoiSuDung(phanvungId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách Kho hạ tầng backup")
    @RequestMapping(value = "/ds-kho-hatang-backup", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDSKhoHaTangBackup() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(capPhatBackUpService.layDSKhoHaTangBackup());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
