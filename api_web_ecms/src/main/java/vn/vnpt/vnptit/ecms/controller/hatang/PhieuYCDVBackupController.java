package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.PhieuYCDVBackupDto;
import vn.vnpt.vnptit.ecms.service.hatang.PhieuYCDVBackupService;

// import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/hatang/phieuyc-dv-backup")
public class PhieuYCDVBackupController {

    @Autowired
    private PhieuYCDVBackupService phieuYCDVBackupService;

    @Operation(summary = "Lưu thông tin phiếu yêu cầu Backup")
    @PostMapping("/save")
    public ResponseEntity<ApiResult> savePhieuYCDVBackup(@RequestBody PhieuYCDVBackupDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(phieuYCDVBackupService.savePhieuYCDVBackup(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lay dich vu backup")
    @PostMapping("/get-danhsach")
    public ResponseEntity<ApiResult> dsDichvuBackup(@RequestBody PhieuYCDVBackupDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(phieuYCDVBackupService.dsDichvuBackup(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}